package weblogic.xml.jaxp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.w3c.dom.Document;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.xml.registry.ReParsingEntityNotChangedException;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.sax.XMLInputSource;
import weblogic.xml.util.Debug;
import weblogic.xml.util.InputSourceUtil;

public class RegistryParser implements Parser {
   static Debug.DebugFacility dbg;
   private RegistryEntityResolver registry;
   private ChainingEntityResolver chainingEntityResolver;
   private ReParsingDocumentHandler documentHandlerProxy = new ReParsingDocumentHandler();
   private ReParsingDTDHandler dtdHandlerProxy = new ReParsingDTDHandler();
   private ReParsingErrorHandler errorHandlerProxy = new ReParsingErrorHandler();
   private Locale locale;
   private Parser parser;
   private SAXFactoryProperties saxFactoryProperties;
   private XMLInputSource xmlInputSource = null;
   private SAXParser saxParser = null;
   private Map cache = new HashMap();

   public RegistryParser() {
      try {
         this.registry = new RegistryEntityResolver();
         this.chainingEntityResolver = new ChainingEntityResolver();
         this.chainingEntityResolver.pushEntityResolver(this.registry);
         this.saxFactoryProperties = new SAXFactoryProperties();
      } catch (XMLRegistryException var3) {
         AssertionError e = new AssertionError("Could not create RegistryParser.");
         e.initCause(var3);
         throw e;
      }
   }

   protected RegistryParser(SAXParser saxParser, SAXFactoryProperties saxFactoryProperties) {
      try {
         this.registry = new RegistryEntityResolver();
         this.chainingEntityResolver = new ChainingEntityResolver();
         this.chainingEntityResolver.pushEntityResolver(this.registry);
         this.saxFactoryProperties = saxFactoryProperties;
         if (saxParser != null) {
            this.parser = saxParser.getParser();
            this.parser.setEntityResolver(this.chainingEntityResolver);
         }

      } catch (XMLRegistryException var4) {
         throw new FactoryConfigurationError(var4, "Could not access XML Registry. " + var4.getMessage());
      } catch (SAXException var5) {
         throw new FactoryConfigurationError(var5, "Could not create parser. " + var5.getMessage());
      } catch (NullPointerException var6) {
         throw new FactoryConfigurationError(var6, "Could not create parser. " + var6.getMessage());
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
               this.parser = this.getParserInternal(source);
            } catch (ParserConfigurationException var52) {
               throw new SAXException(var52);
            }

            boolean invalidation = this.isHandleEntityInvalidation(source);
            if (!invalidation) {
               if (this.errorHandlerProxy.getErrorHandler() != null) {
                  this.parser.setErrorHandler(this.errorHandlerProxy.getErrorHandler());
               }

               if (this.documentHandlerProxy.getDocumentHandler() != null) {
                  this.parser.setDocumentHandler(this.documentHandlerProxy.getDocumentHandler());
               }

               if (this.dtdHandlerProxy.getDTDHandler() != null) {
                  this.parser.setDTDHandler(this.dtdHandlerProxy.getDTDHandler());
               }

               this.parser.parse(source);
            } else {
               boolean interrupted = false;
               ReParsingEventQueue queue = new ReParsingEventQueue();

               try {
                  ReParsingStatus status = new ReParsingStatus();
                  this.registry.hookStatus(status);
                  this.errorHandlerProxy.hookStatus(status);
                  this.errorHandlerProxy.registerQueue(queue);
                  this.documentHandlerProxy.registerQueue(queue);
                  this.dtdHandlerProxy.registerQueue(queue);
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
                        this.parser.parse(source);
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
                        this.parser = this.saxParser.getParser();
                        this.setupParser();
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
                     this.documentHandlerProxy.registerQueue((ReParsingEventQueue)null);
                     this.dtdHandlerProxy.registerQueue((ReParsingEventQueue)null);
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

   public void setDocumentHandler(DocumentHandler h) {
      this.documentHandlerProxy.setDocumentHandler(h);
      if (this.parser != null) {
         this.parser.setDocumentHandler(h);
      }

   }

   public void setErrorHandler(ErrorHandler h) {
      this.errorHandlerProxy.setErrorHandler(h);
      if (this.parser != null) {
         this.parser.setErrorHandler(h);
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
      if (this.parser != null) {
         this.parser.setDTDHandler(h);
      }

   }

   public void setLocale(Locale loc) throws SAXException {
      this.locale = loc;
      if (this.parser != null) {
         this.parser.setLocale(loc);
      }

   }

   private Parser getParserInternal(InputSource source) throws ParserConfigurationException, SAXException, IOException {
      if (this.xmlInputSource != null) {
         this.xmlInputSource = null;
      }

      if (this.registry.hasDocumentSpecificParserEntries()) {
         this.xmlInputSource = new XMLInputSource(source);
         this.parser = this.getParser(this.xmlInputSource);
      } else {
         this.parser = this.getParser((XMLInputSource)null);
      }

      this.setupParser();
      return this.parser;
   }

   private Parser getParser(XMLInputSource inputSource) throws ParserConfigurationException, SAXException {
      Parser localParser = null;
      SAXParserFactory factory = null;
      ParserCreationHelper helper = new ParserCreationHelper(this.registry);
      if (inputSource != null) {
         localParser = helper.getCustomParser(inputSource);
      }

      if (localParser == null) {
         if (inputSource != null) {
            factory = helper.getCustomSAXParserFactory(inputSource);
         }

         if (factory == null) {
            factory = helper.getDefaultSAXParserFactory();
         }

         String facName = factory.getClass().getName();
         if (this.cache.containsKey(facName)) {
            this.saxParser = (SAXParser)this.cache.get(facName);
            localParser = this.saxParser.getParser();
         } else {
            factory.setValidating(this.saxFactoryProperties.get("Validating"));
            if (this.saxFactoryProperties.isSetExplicitly("Namespaceaware")) {
               factory.setNamespaceAware(this.saxFactoryProperties.get("Namespaceaware"));
            }

            Schema s = (Schema)this.saxFactoryProperties.getProperty("Schema");
            if (s != null) {
               factory.setSchema(s);
            }

            if (this.saxFactoryProperties.get("XIncludeAware")) {
               factory.setXIncludeAware(true);
            }

            this.saxParser = factory.newSAXParser();
            this.cache.put(facName, this.saxParser);
            localParser = this.saxParser.getParser();
         }
      }

      return localParser;
   }

   private void setupParser() throws SAXException {
      this.parser.setDocumentHandler(this.documentHandlerProxy);
      this.parser.setErrorHandler(this.errorHandlerProxy);
      this.parser.setDTDHandler(this.dtdHandlerProxy);
      if (this.locale != null) {
         this.parser.setLocale(this.locale);
      }

      this.parser.setEntityResolver(this.chainingEntityResolver);
   }

   void reset() {
      this.documentHandlerProxy.setDocumentHandler((DocumentHandler)null);
      this.errorHandlerProxy.setErrorHandler((ErrorHandler)null);
      this.dtdHandlerProxy.setDTDHandler((DTDHandler)null);
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
