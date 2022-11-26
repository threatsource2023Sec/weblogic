package com.sun.faces.config;

import com.sun.faces.util.FacesLogger;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DbfFactory {
   private static final Logger LOGGER;
   private static final String FACES_1_2_XSD = "/com/sun/faces/web-facesconfig_1_2.xsd";
   private static final String FACES_1_1_XSD = "/com/sun/faces/web-facesconfig_1_1.xsd";
   private static Schema FACES_12_SCHEMA;
   private static Schema FACES_11_SCHEMA;
   public static final EntityResolver FACES_ENTITY_RESOLVER;
   public static final FacesErrorHandler FACES_ERROR_HANDLER;

   public static DocumentBuilderFactory getFactory() {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(true);
      return factory;
   }

   private static void initSchema() {
      try {
         URL url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_1_2.xsd");
         URLConnection conn = url.openConnection();
         conn.setUseCaches(false);
         InputStream in = conn.getInputStream();
         SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
         FACES_12_SCHEMA = factory.newSchema(new StreamSource(in));
         url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_1_1.xsd");
         conn = url.openConnection();
         conn.setUseCaches(false);
         in = conn.getInputStream();
         factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
         FACES_11_SCHEMA = factory.newSchema(new StreamSource(in));
      } catch (Exception var4) {
         throw new ConfigurationException(var4);
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      FACES_ENTITY_RESOLVER = new FacesEntityResolver();
      FACES_ERROR_HANDLER = new FacesErrorHandler();
      initSchema();
   }

   private static final class Input implements LSInput {
      InputStream in;

      public Input(InputStream in) {
         this.in = in;
      }

      public Reader getCharacterStream() {
         return null;
      }

      public void setCharacterStream(Reader characterStream) {
      }

      public InputStream getByteStream() {
         return this.in;
      }

      public void setByteStream(InputStream byteStream) {
      }

      public String getStringData() {
         return null;
      }

      public void setStringData(String stringData) {
      }

      public String getSystemId() {
         return null;
      }

      public void setSystemId(String systemId) {
      }

      public String getPublicId() {
         return null;
      }

      public void setPublicId(String publicId) {
      }

      public String getBaseURI() {
         return null;
      }

      public void setBaseURI(String baseURI) {
      }

      public String getEncoding() {
         return null;
      }

      public void setEncoding(String encoding) {
      }

      public boolean getCertifiedText() {
         return false;
      }

      public void setCertifiedText(boolean certifiedText) {
      }
   }

   private static class FacesErrorHandler implements ErrorHandler {
      private FacesErrorHandler() {
      }

      public void warning(SAXParseException exception) throws SAXException {
      }

      public void error(SAXParseException exception) throws SAXException {
         throw exception;
      }

      public void fatalError(SAXParseException exception) throws SAXException {
         throw exception;
      }

      // $FF: synthetic method
      FacesErrorHandler(Object x0) {
         this();
      }
   }

   private static class FacesEntityResolver extends DefaultHandler implements LSResourceResolver {
      private static final String[][] DTD_SCHEMA_INFO = new String[][]{{"web-facesconfig_1_0.dtd", "/com/sun/faces/web-facesconfig_1_0.dtd"}, {"web-facesconfig_1_1.dtd", "/com/sun/faces/web-facesconfig_1_1.dtd"}, {"web-facesconfig_1_2.xsd", "/com/sun/faces/web-facesconfig_1_2.xsd"}, {"web-facesconfig_1_1.xsd", "/com/sun/faces/web-facesconfig_1_1.xsd"}, {"javaee_5.xsd", "/com/sun/faces/javaee_5.xsd"}, {"javaee_web_services_client_1_2.xsd", "/com/sun/faces/javaee_web_services_client_1_2.xsd"}, {"xml.xsd", "/com/sun/faces/xml.xsd"}, {"datatypes.dtd", "/com/sun/faces/datatypes.dtd"}, {"XMLSchema.dtd", "/com/sun/faces/XMLSchema.dtd"}};
      private HashMap entities = new HashMap(6, 1.0F);

      public FacesEntityResolver() {
         String[][] arr$ = DTD_SCHEMA_INFO;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String[] aDTD_SCHEMA_INFO = arr$[i$];
            URL url = this.getClass().getResource(aDTD_SCHEMA_INFO[1]);
            if (url == null) {
               if (DbfFactory.LOGGER.isLoggable(Level.WARNING)) {
                  DbfFactory.LOGGER.log(Level.WARNING, "jsf.config.cannot_resolve_entities", new Object[]{aDTD_SCHEMA_INFO[1], aDTD_SCHEMA_INFO[0]});
               }
            } else {
               this.entities.put(aDTD_SCHEMA_INFO[0], url.toString());
            }
         }

      }

      public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
         if (systemId == null) {
            try {
               InputSource result = super.resolveEntity(publicId, (String)null);
               return result;
            } catch (Exception var7) {
               throw new SAXException(var7);
            }
         } else {
            String grammarName = systemId.substring(systemId.lastIndexOf(47) + 1);
            String entityURL = (String)this.entities.get(grammarName);
            InputSource source;
            if (entityURL == null) {
               if (DbfFactory.LOGGER.isLoggable(Level.FINE)) {
                  DbfFactory.LOGGER.log(Level.FINE, "Unknown entity, deferring to superclass.");
               }

               try {
                  source = super.resolveEntity(publicId, systemId);
               } catch (Exception var8) {
                  throw new SAXException(var8);
               }
            } else {
               try {
                  source = new InputSource((new URL(entityURL)).openStream());
               } catch (Exception var9) {
                  if (DbfFactory.LOGGER.isLoggable(Level.WARNING)) {
                     DbfFactory.LOGGER.log(Level.WARNING, "jsf.config.cannot_create_inputsource", entityURL);
                  }

                  source = null;
               }
            }

            if (source != null) {
               source.setSystemId(entityURL);
               if (publicId != null) {
                  source.setPublicId(publicId);
               }
            }

            return source;
         }
      }

      public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
         try {
            InputSource source = this.resolveEntity(publicId, systemId);
            return source != null ? new Input(source.getByteStream()) : null;
         } catch (Exception var7) {
            throw new ConfigurationException(var7);
         }
      }

      public Map getKnownEntities() {
         return Collections.unmodifiableMap(this.entities);
      }
   }

   public static enum FacesSchema {
      FACES_12(DbfFactory.FACES_12_SCHEMA),
      FACES_11(DbfFactory.FACES_11_SCHEMA);

      private Schema schema;

      private FacesSchema(Schema schema) {
         this.schema = schema;
      }

      public Schema getSchema() {
         return this.schema;
      }
   }
}
