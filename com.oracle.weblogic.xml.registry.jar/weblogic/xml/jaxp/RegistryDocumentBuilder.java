package weblogic.xml.jaxp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.ReParsingEntityNotChangedException;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.sax.XMLInputSource;
import weblogic.xml.util.InputSourceUtil;

public class RegistryDocumentBuilder extends DocumentBuilder {
   private DocumentBuilder builder;
   private DOMFactoryProperties factoryProperties;
   private final ChainingEntityResolver chainingEntityResolver = new ChainingEntityResolver();
   private ReParsingErrorHandler errorHandlerProxy = new ReParsingErrorHandler();
   private RegistryEntityResolver registry;
   private InputStream jaxpSchemaSource = null;
   private XMLInputSource xmlInputSource = null;
   private Map cache = new HashMap();
   private static final String SAX_EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
   private static final String SAX_EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
   private static final String XERCES2_EXTERNAL_GENERAL_ENTITIES = "http://xerces.apache.org/xerces2-j/features.html#external-general-entities";
   private static final String XERCES2_EXTERNAL_PARAMETER_ENTITIES = "http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities";
   private static final String XERCES1_EXTERNAL_GENERAL_ENTITIES = "http://xerces.apache.org/xerces-j/features.html#external-general-entities";
   private static final String XERCES1_EXTERNAL_PARAMETER_ENTITIES = "http://xerces.apache.org/xerces-j/features.html#external-parameter-entities";
   static Boolean allow_external_dtd = new Boolean("true".equalsIgnoreCase(System.getProperty("weblogic.xml.jaxp.allow.externalDTD")));

   protected RegistryDocumentBuilder(DOMFactoryProperties factoryProperties) {
      try {
         this.factoryProperties = factoryProperties;
         this.registry = new RegistryEntityResolver();
         this.chainingEntityResolver.pushEntityResolver(this.registry);
      } catch (XMLRegistryException var3) {
         throw new FactoryConfigurationError(var3, "Could not access XML Registry. " + var3.getMessage());
      }
   }

   public DOMImplementation getDOMImplementation() {
      if (this.builder == null) {
         try {
            this.builder = this.getDocumentBuilder((InputSource)null);
         } catch (ParserConfigurationException var2) {
            XMLLogger.logParserConfigurationException(var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logParserConfigurationException(var3.getMessage());
            return null;
         }
      }

      return this.builder.getDOMImplementation();
   }

   public boolean isNamespaceAware() {
      return this.factoryProperties.get("Namespaceaware");
   }

   public boolean isValidating() {
      return this.factoryProperties.get("Validating");
   }

   public Document newDocument() {
      if (this.builder == null) {
         try {
            this.builder = this.getDocumentBuilder((InputSource)null);
         } catch (ParserConfigurationException var2) {
            XMLLogger.logParserConfigurationException(var2.getMessage());
            return null;
         } catch (IOException var3) {
            XMLLogger.logParserConfigurationException(var3.getMessage());
            return null;
         }
      }

      return this.builder.newDocument();
   }

   public Document parse(InputSource source) throws SAXException, IOException {
      if (source == null) {
         throw new IllegalArgumentException("InputSource is missing for DocumentBuilder.parse(InputSource)");
      } else {
         Document i;
         try {
            try {
               this.builder = this.getDocumentBuilder(source);
            } catch (ParserConfigurationException var52) {
               throw new SAXException(var52);
            }

            boolean invalidation = this.isHandleEntityInvalidation(source);
            if (!invalidation) {
               if (this.errorHandlerProxy.getErrorHandler() != null) {
                  this.builder.setErrorHandler(this.errorHandlerProxy.getErrorHandler());
               }

               Document var58 = this.builder.parse(source);
               return var58;
            }

            boolean interrupted = false;
            ReParsingEventQueue queue = new ReParsingEventQueue();

            try {
               ReParsingStatus status = new ReParsingStatus();
               this.registry.hookStatus(status);
               this.errorHandlerProxy.hookStatus(status);
               this.errorHandlerProxy.registerQueue(queue);
               SAXException lastParsingError = null;
               Document doc = null;

               try {
                  InputSourceUtil.transformInputSource(source);
               } catch (Exception var51) {
                  status.inLastReParsing = true;
               }

               i = false;

               while(true) {
                  if (i >= 2) {
                     status.inLastReParsing = true;
                  }

                  try {
                     doc = this.builder.parse(source);
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
                     if (this.allowCached(this.builder)) {
                        this.builder.reset();
                        this.setupDocumentBuilder(this.builder);
                     } else {
                        this.builder = this.getDocumentBuilder(source);
                     }

                     InputSourceUtil.resetInputSource(source);
                  } catch (Exception var53) {
                     break;
                  }

                  ++i;
               }

               if (lastParsingError != null) {
                  throw lastParsingError;
               }

               i = doc;
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
               }
            }
         } finally {
            if (this.jaxpSchemaSource != null) {
               this.jaxpSchemaSource.reset();
            }

            if (this.xmlInputSource != null) {
               this.xmlInputSource = null;
            }

         }

         return i;
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

   public void setEntityResolver(EntityResolver er) {
      if (this.chainingEntityResolver.getResolverCount() == 2) {
         this.chainingEntityResolver.popEntityResolver();
      }

      this.chainingEntityResolver.pushEntityResolver(er);
   }

   public void setErrorHandler(ErrorHandler eh) {
      this.errorHandlerProxy.setErrorHandler(eh);
      if (this.builder != null) {
         this.builder.setErrorHandler(this.errorHandlerProxy);
      }

   }

   private DocumentBuilder getDocumentBuilder(InputSource inputSource) throws ParserConfigurationException, IOException {
      DocumentBuilder localBuilder = null;
      DocumentBuilderFactory localFactory = null;
      if (this.xmlInputSource != null) {
         this.xmlInputSource = null;
      }

      if (inputSource != null && this.registry.hasDocumentSpecificParserEntries()) {
         this.xmlInputSource = new XMLInputSource(inputSource);
         localFactory = this.getCustomDocumentBuilderFactory(this.xmlInputSource);
      }

      if (localFactory == null) {
         localFactory = this.getDefaultDocumentBuilderFactory();
      }

      String facName = localFactory.getClass().getName();
      if (this.cache.containsKey(facName)) {
         localBuilder = (DocumentBuilder)this.cache.get(facName);
      } else {
         localBuilder = localFactory.newDocumentBuilder();
         if (this.allowCached(localBuilder)) {
            this.cache.put(facName, localBuilder);
         }
      }

      this.setupDocumentBuilder(localBuilder);
      return localBuilder;
   }

   private boolean allowCached(DocumentBuilder builder) {
      return builder.getClass().getName().indexOf("org.apache.xerces") == -1 || !builder.isValidating();
   }

   private DocumentBuilderFactory getCustomDocumentBuilderFactory(XMLInputSource inputSource) throws IllegalArgumentException {
      DocumentBuilderFactory localFactory = null;
      String publicId = inputSource.getPublicIdInternal();
      String systemId = inputSource.getDoctypeSystemIdInternal();
      String rootTag = inputSource.getRootTagInternal();

      try {
         localFactory = this.registry.getDocumentBuilderFactory(publicId, systemId, rootTag);
         if (localFactory != null) {
            this.setupDocumentBuilderFactory(localFactory);
         }
      } catch (XMLRegistryException var7) {
         if (this.registry.hasDocumentSpecificParserEntries()) {
            XMLLogger.logXMLRegistryException(var7.getMessage());
         }
      }

      return localFactory;
   }

   private DocumentBuilderFactory getDefaultDocumentBuilderFactory() throws IllegalArgumentException {
      DocumentBuilderFactory localFactory = null;

      try {
         localFactory = this.registry.getDocumentBuilderFactory();
      } catch (XMLRegistryException var3) {
         XMLLogger.logXMLRegistryException(var3.getMessage());
      }

      if (localFactory == null) {
         localFactory = new WebLogicDocumentBuilderFactory();
      }

      this.setupDocumentBuilderFactory((DocumentBuilderFactory)localFactory);
      return (DocumentBuilderFactory)localFactory;
   }

   private void setupDocumentBuilder(DocumentBuilder builder) {
      builder.setErrorHandler(this.errorHandlerProxy);
      builder.setEntityResolver(this.chainingEntityResolver);
   }

   private void preventXXE(DocumentBuilderFactory factory) {
      if (!allow_external_dtd) {
         try {
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
         } catch (Throwable var8) {
         }

         try {
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
         } catch (Throwable var7) {
            try {
               factory.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-general-entities", false);
               factory.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities", false);
            } catch (Throwable var6) {
               try {
                  factory.setFeature("http://xerces.apache.org/xerces-j/features.html#external-general-entities", false);
                  factory.setFeature("http://xerces.apache.org/xerces-j/features.html#external-parameter-entities", false);
               } catch (Throwable var5) {
               }
            }
         }

      }
   }

   private void setupDocumentBuilderFactory(DocumentBuilderFactory factory) {
      this.preventXXE(factory);
      factory.setCoalescing(this.factoryProperties.get("Coalescing"));
      factory.setExpandEntityReferences(this.factoryProperties.get("ExpandEntityReferences"));
      factory.setIgnoringComments(this.factoryProperties.get("IgnoringComments"));
      factory.setIgnoringElementContentWhitespace(this.factoryProperties.get("IgnoringElementContentWhitespace"));
      if (this.factoryProperties.isSetExplicitly("Namespaceaware")) {
         factory.setNamespaceAware(this.factoryProperties.get("Namespaceaware"));
      }

      factory.setValidating(this.factoryProperties.get("Validating"));
      if (this.factoryProperties.get("XIncludeAware")) {
         try {
            factory.setXIncludeAware(true);
         } catch (UnsupportedOperationException var12) {
            XMLLogger.logXMLRegistryException(var12.getMessage());
         }
      }

      Schema s = (Schema)this.factoryProperties.getAttribute("Schema");
      if (s != null) {
         try {
            factory.setSchema(s);
         } catch (UnsupportedOperationException var11) {
            XMLLogger.logWarning(var11.getMessage());
         }
      }

      Iterator e = this.factoryProperties.attributes();

      String name;
      while(e.hasNext()) {
         name = (String)e.next();
         Object valueObject = this.factoryProperties.getAttribute(name);
         if (!"Schema".equals(name)) {
            try {
               factory.setAttribute(name, valueObject);
            } catch (IllegalArgumentException var10) {
               XMLLogger.logPropertyNotAccepted(name, valueObject.toString(), var10.getMessage());
            }
         }
      }

      List filteredList = new ArrayList();
      Iterator e = this.factoryProperties.properties();

      while(e.hasNext()) {
         name = (String)e.next();
         if (!name.equals("Coalescing") && !name.equals("ExpandEntityReferences") && !name.equals("IgnoringComments") && !name.equals("IgnoringElementContentWhitespace") && !name.equals("Namespaceaware") && !name.equals("Validating") && !name.equals("XIncludeAware")) {
            filteredList.add(name);
         }
      }

      e = filteredList.iterator();

      while(e.hasNext()) {
         String key = (String)e.next();

         try {
            factory.setFeature(key, this.factoryProperties.get(key));
         } catch (ParserConfigurationException var9) {
            XMLLogger.logPropertyNotAccepted(key, String.valueOf(this.factoryProperties.get(key)), var9.getMessage());
         }
      }

   }

   public Schema getSchema() {
      return (Schema)this.factoryProperties.getAttribute("Schema");
   }

   public boolean isXIncludeAware() {
      return this.factoryProperties.get("XIncludeAware");
   }

   void setJaxpSchemaSource(InputStream stream) {
      this.jaxpSchemaSource = stream;
   }

   public void reset() {
      this.errorHandlerProxy.setErrorHandler((ErrorHandler)null);
      Iterator iterator = this.cache.values().iterator();

      while(iterator.hasNext()) {
         DocumentBuilder docBuilder = (DocumentBuilder)iterator.next();
         docBuilder.reset();
      }

   }
}
