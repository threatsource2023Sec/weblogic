package weblogic.xml.jaxp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.ParserAdapter;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.ReParsingEntityNotChangedException;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.sax.XMLInputSource;
import weblogic.xml.util.Debug;
import weblogic.xml.util.InputSourceUtil;

public class RegistryXMLReader implements XMLReader {
   static Debug.DebugFacility dbg;
   private RegistryEntityResolver registry;
   private final ChainingEntityResolver chainingEntityResolver = new ChainingEntityResolver();
   private ReParsingContentHandler contentHandlerProxy = new ReParsingContentHandler();
   private ReParsingDTDHandler dtdHandlerProxy = new ReParsingDTDHandler();
   private ReParsingErrorHandler errorHandlerProxy = new ReParsingErrorHandler();
   private XMLReader xmlReader;
   private SAXFactoryProperties saxFactoryProperties;
   private XMLInputSource xmlInputSource = null;
   private SAXParser saxParser = null;
   private ReParsingLexicalHandler lexicalHandlerProxy = new ReParsingLexicalHandler();
   private ReParsingDeclHandler declHandlerProxy = new ReParsingDeclHandler();
   private Map cache = new HashMap();

   public RegistryXMLReader() {
      try {
         this.registry = new RegistryEntityResolver();
         this.chainingEntityResolver.pushEntityResolver(this.registry);
         this.saxFactoryProperties = new SAXFactoryProperties();
         this.saxFactoryProperties.put("Namespaceaware", true);
         this.xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
         this.xmlReader.setEntityResolver(this.chainingEntityResolver);
      } catch (XMLRegistryException var2) {
         throw new FactoryConfigurationError(var2, "Could not access XML Registry. " + var2.getMessage());
      } catch (ParserConfigurationException var3) {
         throw new FactoryConfigurationError(var3, "Could not create parser. " + var3.getMessage());
      } catch (SAXException var4) {
         throw new FactoryConfigurationError(var4, "Could not create parser. " + var4.getMessage());
      }
   }

   protected RegistryXMLReader(SAXParser saxParser, SAXFactoryProperties saxFactoryProperties) {
      try {
         this.registry = new RegistryEntityResolver();
         this.chainingEntityResolver.pushEntityResolver(this.registry);
         this.saxFactoryProperties = saxFactoryProperties;
         if (saxParser != null) {
            this.xmlReader = saxParser.getXMLReader();
            this.xmlReader.setEntityResolver(this.chainingEntityResolver);
         }

      } catch (XMLRegistryException var4) {
         throw new FactoryConfigurationError(var4, "Could not access XML Registry. " + var4.getMessage());
      } catch (SAXException var5) {
         throw new FactoryConfigurationError(var5, "Could not create parser. " + var5.getMessage());
      }
   }

   public RegistryEntityResolver getRegistry() {
      return this.registry;
   }

   public void parse(InputSource source) throws SAXException, IOException {
      if (source == null) {
         throw new IllegalArgumentException("InputSource is missing.");
      } else {
         try {
            try {
               this.xmlReader = this.getXMLReaderInternal(source);
            } catch (ParserConfigurationException var52) {
               throw new SAXException(var52);
            }

            boolean invalidation = this.isHandleEntityInvalidation(source);
            if (!invalidation) {
               if (this.errorHandlerProxy.getErrorHandler() != null) {
                  this.xmlReader.setErrorHandler(this.errorHandlerProxy.getErrorHandler());
               }

               if (this.contentHandlerProxy.getContentHandler() != null) {
                  this.xmlReader.setContentHandler(this.contentHandlerProxy.getContentHandler());
               }

               if (this.dtdHandlerProxy.getDTDHandler() != null) {
                  this.xmlReader.setDTDHandler(this.dtdHandlerProxy.getDTDHandler());
               }

               this.xmlReader.parse(source);
            } else {
               boolean interrupted = false;
               ReParsingEventQueue queue = new ReParsingEventQueue();

               try {
                  ReParsingStatus status = new ReParsingStatus();
                  this.registry.hookStatus(status);
                  this.errorHandlerProxy.hookStatus(status);
                  this.errorHandlerProxy.registerQueue(queue);
                  this.contentHandlerProxy.registerQueue(queue);
                  this.dtdHandlerProxy.registerQueue(queue);
                  this.delegateExtendedHandlers(queue);
                  SAXException lastParsingError = null;
                  Document doc = null;

                  try {
                     InputSourceUtil.transformInputSource(source);
                  } catch (Exception var51) {
                     status.inLastReParsing = true;
                  }

                  int i = 0;

                  while(true) {
                     if (i >= 2) {
                        status.inLastReParsing = true;
                     }

                     try {
                        this.xmlReader.parse(source);
                        lastParsingError = null;
                     } catch (SAXParseException var54) {
                        lastParsingError = var54;
                        status.error = var54;
                     } catch (ReParsingEntityNotChangedException var55) {
                        interrupted = true;
                        break;
                     }

                     if (!status.needReParse()) {
                        break;
                     }

                     status.prepareReParsing();
                     queue.shiftLastEvents();

                     try {
                        this.saxParser.reset();
                        this.xmlReader = this.saxParser.getXMLReader();
                        this.setupXMLReader();
                        this.delegateExtendedHandlers(queue);
                        InputSourceUtil.resetInputSource(source);
                     } catch (Exception var53) {
                        break;
                     }

                     ++i;
                  }

                  if (lastParsingError != null) {
                     throw lastParsingError;
                  }
               } finally {
                  this.registry.hookStatus((ReParsingStatus)null);
                  this.errorHandlerProxy.hookStatus((ReParsingStatus)null);
                  if (!interrupted) {
                     queue.shiftLastEvents();
                  }

                  try {
                     queue.reSendLastEvents();
                  } finally {
                     queue.clearAllEvents();
                     this.errorHandlerProxy.registerQueue((ReParsingEventQueue)null);
                     this.contentHandlerProxy.registerQueue((ReParsingEventQueue)null);
                     this.dtdHandlerProxy.registerQueue((ReParsingEventQueue)null);
                     this.cleanUpExtendedHandlers();
                  }
               }
            }
         } finally {
            if (this.xmlInputSource != null) {
               this.xmlInputSource = null;
            }

         }

      }
   }

   private boolean isHandleEntityInvalidation(InputSource source) {
      String invalidation = null;

      try {
         if (this.registry.hasDocumentSpecificEntityEntries() && this.registry.hasHandleEntityInvalidationSetSupport()) {
            if (this.xmlInputSource == null) {
               this.xmlInputSource = new XMLInputSource(source);
            }

            String publicId = this.xmlInputSource.getPublicIdInternal();
            String systemId = this.xmlInputSource.getDoctypeSystemIdInternal();
            invalidation = this.registry.getHandleEntityInvalidation(publicId, systemId);
         }

         if (invalidation == null) {
            invalidation = this.registry.getHandleEntityInvalidation();
         }
      } catch (Exception var5) {
      }

      return "true".equals(invalidation);
   }

   public void parse(String systemId) throws SAXException, IOException {
      if (systemId == null) {
         throw new IllegalArgumentException("System ID is missing.");
      } else {
         this.parse(new InputSource(systemId));
      }
   }

   public void setErrorHandler(ErrorHandler h) {
      this.errorHandlerProxy.setErrorHandler(h);
      if (this.xmlReader != null) {
         this.xmlReader.setErrorHandler(h);
      }

   }

   public void setEntityResolver(EntityResolver resolver) {
      if (dbg.areDebuggingAt(2)) {
         int nresolvers = this.chainingEntityResolver.getResolverCount();
         dbg.assertion(nresolvers == 1 || nresolvers == 2);
      }

      if (this.chainingEntityResolver.getResolverCount() == 2) {
         this.chainingEntityResolver.popEntityResolver();
      }

      this.chainingEntityResolver.pushEntityResolver(resolver);
   }

   public void setDTDHandler(DTDHandler h) {
      this.dtdHandlerProxy.setDTDHandler(h);
      if (this.xmlReader != null) {
         this.xmlReader.setDTDHandler(h);
      }

   }

   public void setContentHandler(ContentHandler handler) {
      this.contentHandlerProxy.setContentHandler(handler);
      if (this.xmlReader != null) {
         this.xmlReader.setContentHandler(handler);
      }

   }

   public ContentHandler getContentHandler() {
      return this.contentHandlerProxy.getContentHandler();
   }

   public DTDHandler getDTDHandler() {
      return this.dtdHandlerProxy.getDTDHandler();
   }

   public EntityResolver getEntityResolver() {
      return this.chainingEntityResolver.peekEntityResolver();
   }

   public ErrorHandler getErrorHandler() {
      return this.errorHandlerProxy.getErrorHandler();
   }

   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      Boolean feature = this.saxFactoryProperties.getFeature(name);
      if (feature == null && this.xmlReader != null) {
         return this.xmlReader.getFeature(name);
      } else if (feature == null) {
         throw new SAXNotRecognizedException("You have specified a document-specific parser in the console. Features for such parsers are set at the parse time.");
      } else {
         return feature;
      }
   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      Object property = this.saxFactoryProperties.getProperty(name);
      if (property == null && this.xmlReader != null) {
         return this.xmlReader.getProperty(name);
      } else if (property == null) {
         throw new SAXNotRecognizedException("You have specified a custom parser in the console. Properties for such parsers are set at the parse time.");
      } else {
         return property;
      }
   }

   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.saxFactoryProperties.setFeature(name, value);
      if (this.xmlReader != null) {
         this.xmlReader.setFeature(name, value);
      }

   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      this.saxFactoryProperties.setProperty(name, value);
      if (this.xmlReader != null) {
         this.xmlReader.setProperty(name, value);
      }

   }

   private XMLReader getXMLReaderInternal(InputSource source) throws ParserConfigurationException, SAXException, IOException {
      if (this.xmlInputSource != null) {
         this.xmlInputSource = null;
      }

      if (this.registry.hasDocumentSpecificParserEntries()) {
         this.xmlInputSource = new XMLInputSource(source);
         this.xmlReader = this.getXMLReader(this.xmlInputSource);
      } else {
         this.xmlReader = this.getXMLReader((XMLInputSource)null);
      }

      this.setupXMLReader();
      return this.xmlReader;
   }

   private XMLReader getXMLReader(XMLInputSource inputSource) throws ParserConfigurationException, SAXException {
      XMLReader localXMLReader = null;
      SAXParserFactory factory = null;
      ParserCreationHelper helper = new ParserCreationHelper(this.registry);
      if (inputSource != null) {
         Parser parser = helper.getCustomParser(inputSource);
         if (parser != null) {
            localXMLReader = new ParserAdapter(parser);
         }
      }

      if (localXMLReader == null) {
         if (inputSource != null) {
            factory = helper.getCustomSAXParserFactory(inputSource);
         }

         if (factory == null) {
            factory = helper.getDefaultSAXParserFactory();
         }

         String facName = factory.getClass().getName();
         if (this.cache.containsKey(facName)) {
            this.saxParser = (SAXParser)this.cache.get(facName);
            localXMLReader = this.saxParser.getXMLReader();
         } else {
            factory.setValidating(this.saxFactoryProperties.get("Validating"));
            if (this.saxFactoryProperties.isSetExplicitly("Namespaceaware")) {
               factory.setNamespaceAware(this.saxFactoryProperties.get("Namespaceaware"));
            }

            Schema s = (Schema)this.saxFactoryProperties.getProperty("Schema");
            if (s != null) {
               try {
                  factory.setSchema(s);
               } catch (UnsupportedOperationException var9) {
                  XMLLogger.logWarning(var9.getMessage());
               }
            }

            if (this.saxFactoryProperties.get("XIncludeAware")) {
               try {
                  factory.setXIncludeAware(true);
               } catch (UnsupportedOperationException var8) {
                  XMLLogger.logXMLRegistryException(var8.getMessage());
               }
            }

            this.saxParser = factory.newSAXParser();
            this.cache.put(facName, this.saxParser);
            localXMLReader = this.saxParser.getXMLReader();
         }
      }

      return (XMLReader)localXMLReader;
   }

   private void setupXMLReader() throws SAXException {
      this.xmlReader.setContentHandler(this.contentHandlerProxy);
      this.xmlReader.setDTDHandler(this.dtdHandlerProxy);
      this.xmlReader.setErrorHandler(this.errorHandlerProxy);
      this.xmlReader.setEntityResolver(this.chainingEntityResolver);
      Enumeration e = this.saxFactoryProperties.features();

      String name;
      while(e.hasMoreElements()) {
         name = (String)e.nextElement();
         Boolean valueBoolean = this.saxFactoryProperties.getFeature(name);

         try {
            this.xmlReader.setFeature(name, valueBoolean);
         } catch (SAXNotRecognizedException var6) {
            XMLLogger.logPropertyNotAccepted(name, valueBoolean.toString(), var6.getMessage());
         } catch (SAXNotSupportedException var7) {
            XMLLogger.logPropertyNotAccepted(name, valueBoolean.toString(), var7.getMessage());
         }
      }

      e = this.saxFactoryProperties.properties();

      while(e.hasMoreElements()) {
         name = (String)e.nextElement();
         Object valueObject = this.saxFactoryProperties.getProperty(name);

         try {
            this.xmlReader.setProperty(name, valueObject);
         } catch (SAXNotRecognizedException var8) {
            XMLLogger.logPropertyNotAccepted(name, valueObject == null ? "null" : valueObject.toString(), var8.getMessage());
         } catch (SAXNotSupportedException var9) {
            XMLLogger.logPropertyNotAccepted(name, valueObject == null ? "null" : valueObject.toString(), var9.getMessage());
         }
      }

   }

   private void delegateExtendedHandlers(ReParsingEventQueue queue) throws SAXNotRecognizedException, SAXNotSupportedException {
      String name = "http://xml.org/sax/properties/lexical-handler";
      Object valueObject = null;

      try {
         valueObject = this.xmlReader.getProperty(name);
      } catch (SAXNotRecognizedException var7) {
      } catch (SAXNotSupportedException var8) {
      }

      if (valueObject != null && valueObject instanceof LexicalHandler && !(valueObject instanceof ReParsingLexicalHandler)) {
         this.lexicalHandlerProxy.setLexicalHandler((LexicalHandler)valueObject);
         this.lexicalHandlerProxy.registerQueue(queue);
         this.xmlReader.setProperty(name, this.lexicalHandlerProxy);
      }

      name = "http://xml.org/sax/properties/declaration-handler";
      valueObject = null;

      try {
         valueObject = this.xmlReader.getProperty(name);
      } catch (SAXNotRecognizedException var5) {
      } catch (SAXNotSupportedException var6) {
      }

      if (valueObject != null && valueObject instanceof DeclHandler && !(valueObject instanceof ReParsingDeclHandler)) {
         this.declHandlerProxy.setDeclHandler((DeclHandler)valueObject);
         this.declHandlerProxy.registerQueue(queue);
         this.xmlReader.setProperty(name, this.declHandlerProxy);
      }

   }

   private void cleanUpExtendedHandlers() {
      this.lexicalHandlerProxy.setLexicalHandler((LexicalHandler)null);
      this.lexicalHandlerProxy.registerQueue((ReParsingEventQueue)null);
      this.declHandlerProxy.setDeclHandler((DeclHandler)null);
      this.declHandlerProxy.registerQueue((ReParsingEventQueue)null);
   }

   void reset() {
      this.contentHandlerProxy.setContentHandler((ContentHandler)null);
      this.dtdHandlerProxy.setDTDHandler((DTDHandler)null);
      this.errorHandlerProxy.setErrorHandler((ErrorHandler)null);
      this.resetCachedSAXParsers();
   }

   private void resetCachedSAXParsers() {
      Iterator iterator = this.cache.values().iterator();

      while(iterator.hasNext()) {
         SAXParser saxparser = (SAXParser)iterator.next();
         saxparser.reset();
      }

   }

   static {
      dbg = XMLContext.dbg;
   }
}
