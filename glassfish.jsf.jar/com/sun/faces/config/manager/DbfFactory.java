package com.sun.faces.config.manager;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
   private static final String AS_INSTALL_ROOT = "com.sun.aas.installRoot";
   private static final String AS_SCHEMA_DIR;
   private static final String AS_DTD_DIR;
   private static final String FACELET_TAGLIB_2_0_XSD = "/com/sun/faces/web-facelettaglibrary_2_0.xsd";
   private static final String FACELET_TAGLIB_2_2_XSD = "/com/sun/faces/web-facelettaglibrary_2_2.xsd";
   private static final String FACES_2_0_XSD = "/com/sun/faces/web-facesconfig_2_0.xsd";
   private static final String FACES_2_1_XSD = "/com/sun/faces/web-facesconfig_2_1.xsd";
   private static final String FACES_2_2_XSD = "/com/sun/faces/web-facesconfig_2_2.xsd";
   private static final String FACES_2_3_XSD = "/com/sun/faces/web-facesconfig_2_3.xsd";
   private static final String FACES_1_2_XSD = "/com/sun/faces/web-facesconfig_1_2.xsd";
   private static final String FACES_1_1_XSD = "/com/sun/faces/web-facesconfig_1_1.xsd";
   private static final String FACELET_TAGLIB_2_0_XSD_FILE;
   private static final String FACELET_TAGLIB_2_2_XSD_FILE;
   private static final String FACES_2_0_XSD_FILE;
   private static final String FACES_2_1_XSD_FILE;
   private static final String FACES_2_2_XSD_FILE;
   private static final String FACES_2_3_XSD_FILE;
   private static final String FACES_1_2_XSD_FILE;
   public static final EntityResolver FACES_ENTITY_RESOLVER;
   private static final String SCHEMA_MAP = "com.sun.faces.config.schemaMap";
   public static final FacesErrorHandler FACES_ERROR_HANDLER;

   public static DocumentBuilderFactory getFactory() {
      DocumentBuilderFactory factory = Util.createDocumentBuilderFactory();
      factory.setNamespaceAware(true);
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(true);
      return factory;
   }

   public static Schema getSchema(ServletContext servletContext, FacesSchema schemaId) {
      Map schemaMap = getSchemaMap(servletContext);
      if (!schemaMap.containsKey(schemaId)) {
         loadSchema(schemaMap, schemaId);
      }

      return (Schema)schemaMap.get(schemaId);
   }

   private static Map getSchemaMap(ServletContext servletContext) {
      Map schemaMap = (Map)servletContext.getAttribute("com.sun.faces.config.schemaMap");
      if (schemaMap == null) {
         synchronized(servletContext) {
            schemaMap = Collections.synchronizedMap(new EnumMap(FacesSchema.class));
            servletContext.setAttribute("com.sun.faces.config.schemaMap", schemaMap);
         }
      }

      return schemaMap;
   }

   public static void removeSchemaMap(ServletContext servletContext) {
      servletContext.removeAttribute("com.sun.faces.config.schemaMap");
   }

   private static void loadSchema(Map schemaMap, FacesSchema schemaId) {
      try {
         URL url;
         URLConnection conn;
         InputStream in;
         SchemaFactory factory;
         File f;
         Schema schema;
         switch (schemaId) {
            case FACES_12:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_1_2.xsd");
               if (url == null) {
                  f = new File(FACES_1_2_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facesconfig_1_2.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACES_11:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_1_1.xsd");
               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACES_21:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_2_1.xsd");
               if (url == null) {
                  f = new File(FACES_2_1_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facesconfig_2_1.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACES_22:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_2_2.xsd");
               if (url == null) {
                  f = new File(FACES_2_2_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facesconfig_2_2.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACES_23:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_2_3.xsd");
               if (url == null) {
                  f = new File(FACES_2_3_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facesconfig_2_3.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACES_20:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facesconfig_2_0.xsd");
               if (url == null) {
                  f = new File(FACES_2_0_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facesconfig_2_0.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACELET_TAGLIB_20:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facelettaglibrary_2_0.xsd");
               if (url == null) {
                  f = new File(FACELET_TAGLIB_2_0_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facelettaglibrary_2_0.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            case FACELET_TAGLIB_22:
               url = DbfFactory.class.getResource("/com/sun/faces/web-facelettaglibrary_2_2.xsd");
               if (url == null) {
                  f = new File(FACELET_TAGLIB_2_2_XSD_FILE);
                  if (!f.exists()) {
                     throw new IllegalStateException("Unable to find web-facelettaglibrary_2_2.xsd");
                  }

                  url = f.toURI().toURL();
               }

               conn = url.openConnection();
               conn.setUseCaches(false);
               in = conn.getInputStream();
               factory = Util.createSchemaFactory("http://www.w3.org/2001/XMLSchema");
               factory.setResourceResolver((LSResourceResolver)FACES_ENTITY_RESOLVER);
               schema = factory.newSchema(new StreamSource(in));
               schemaMap.put(schemaId, schema);
               break;
            default:
               throw new ConfigurationException("Unrecognized Faces Version: " + schemaId.toString());
         }

      } catch (IOException | SAXException | ConfigurationException | IllegalStateException var9) {
         throw new ConfigurationException(var9);
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      AS_SCHEMA_DIR = System.getProperty("com.sun.aas.installRoot") + File.separatorChar + "lib" + File.separatorChar + "schemas" + File.separatorChar;
      AS_DTD_DIR = System.getProperty("com.sun.aas.installRoot") + File.separatorChar + "lib" + File.separatorChar + "dtds" + File.separatorChar;
      FACELET_TAGLIB_2_0_XSD_FILE = AS_SCHEMA_DIR + "web-facelettaglibrary_2_0.xsd";
      FACELET_TAGLIB_2_2_XSD_FILE = AS_SCHEMA_DIR + "web-facelettaglibrary_2_2.xsd";
      FACES_2_0_XSD_FILE = AS_SCHEMA_DIR + "web-facesconfig_2_0.xsd";
      FACES_2_1_XSD_FILE = AS_SCHEMA_DIR + "web-facesconfig_2_1.xsd";
      FACES_2_2_XSD_FILE = AS_SCHEMA_DIR + "web-facesconfig_2_2.xsd";
      FACES_2_3_XSD_FILE = AS_SCHEMA_DIR + "web-facesconfig_2_3.xsd";
      FACES_1_2_XSD_FILE = AS_SCHEMA_DIR + "web-facesconfig_1_2.xsd";
      FACES_ENTITY_RESOLVER = new FacesEntityResolver();
      FACES_ERROR_HANDLER = new FacesErrorHandler();
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
      private static final String[][] DTD_SCHEMA_INFO;
      private HashMap entities = new HashMap(12, 1.0F);

      public FacesEntityResolver() {
         String[][] var1 = DTD_SCHEMA_INFO;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String[] aDTD_SCHEMA_INFO = var1[var3];
            URL url = this.getClass().getResource(aDTD_SCHEMA_INFO[1]);
            if (url == null) {
               if (DbfFactory.LOGGER.isLoggable(Level.FINE)) {
                  DbfFactory.LOGGER.log(Level.FINE, "jsf.config.cannot_resolve_entities", new Object[]{aDTD_SCHEMA_INFO[1], aDTD_SCHEMA_INFO[0]});
               }

               String path = aDTD_SCHEMA_INFO[2];
               if (path != null) {
                  File f = new File(path);
                  if (f.exists()) {
                     try {
                        url = f.toURI().toURL();
                     } catch (MalformedURLException var9) {
                        if (DbfFactory.LOGGER.isLoggable(Level.SEVERE)) {
                           DbfFactory.LOGGER.log(Level.SEVERE, var9.toString(), var9);
                        }
                     }

                     if (url == null) {
                        if (DbfFactory.LOGGER.isLoggable(Level.FINE)) {
                           DbfFactory.LOGGER.log(Level.FINE, "jsf.config.cannot_resolve_entities", new Object[]{aDTD_SCHEMA_INFO[1], aDTD_SCHEMA_INFO[2]});
                        }
                     } else {
                        this.entities.put(aDTD_SCHEMA_INFO[0], url.toString());
                     }
                  }
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
            } catch (SAXException | IOException var7) {
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
               } catch (SAXException | IOException var8) {
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

      static {
         DTD_SCHEMA_INFO = new String[][]{{"web-facesconfig_1_0.dtd", "/com/sun/faces/web-facesconfig_1_0.dtd", DbfFactory.AS_DTD_DIR + "web-facesconfig_1_0.dtd"}, {"web-facesconfig_1_1.dtd", "/com/sun/faces/web-facesconfig_1_1.dtd", DbfFactory.AS_DTD_DIR + "web-facesconfig_1_1.dtd"}, {"web-facesconfig_2_0.xsd", "/com/sun/faces/web-facesconfig_2_0.xsd", DbfFactory.FACES_2_0_XSD_FILE}, {"web-facesconfig_2_1.xsd", "/com/sun/faces/web-facesconfig_2_1.xsd", DbfFactory.FACES_2_1_XSD_FILE}, {"web-facesconfig_2_2.xsd", "/com/sun/faces/web-facesconfig_2_2.xsd", DbfFactory.FACES_2_2_XSD_FILE}, {"web-facesconfig_2_3.xsd", "/com/sun/faces/web-facesconfig_2_3.xsd", DbfFactory.FACES_2_3_XSD_FILE}, {"facelet-taglib_1_0.dtd", "/com/sun/faces/facelet-taglib_1_0.dtd", null}, {"web-facelettaglibrary_2_0.xsd", "/com/sun/faces/web-facelettaglibrary_2_0.xsd", DbfFactory.FACELET_TAGLIB_2_0_XSD_FILE}, {"web-facesconfig_1_2.xsd", "/com/sun/faces/web-facesconfig_1_2.xsd", DbfFactory.FACES_1_2_XSD_FILE}, {"web-facesconfig_1_1.xsd", "/com/sun/faces/web-facesconfig_1_1.xsd", null}, {"javaee_5.xsd", "/com/sun/faces/javaee_5.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_5.xsd"}, {"javaee_6.xsd", "/com/sun/faces/javaee_6.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_6.xsd"}, {"javaee_7.xsd", "/com/sun/faces/javaee_7.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_7.xsd"}, {"javaee_8.xsd", "/com/sun/faces/javaee_8.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_8.xsd"}, {"javaee_web_services_client_1_2.xsd", "/com/sun/faces/javaee_web_services_client_1_2.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_web_services_client_1_2.xsd"}, {"javaee_web_services_client_1_3.xsd", "/com/sun/faces/javaee_web_services_client_1_3.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_web_services_client_1_3.xsd"}, {"javaee_web_services_client_1_4.xsd", "/com/sun/faces/javaee_web_services_client_1_4.xsd", DbfFactory.AS_SCHEMA_DIR + "javaee_web_services_client_1_4.xsd"}, {"xml.xsd", "/com/sun/faces/xml.xsd", DbfFactory.AS_SCHEMA_DIR + "xml.xsd"}, {"datatypes.dtd", "/com/sun/faces/datatypes.dtd", DbfFactory.AS_SCHEMA_DIR + "datatypes.dtd"}, {"XMLSchema.dtd", "/com/sun/faces/XMLSchema.dtd", DbfFactory.AS_SCHEMA_DIR + "XMLSchema.dtd"}};
      }
   }

   public static enum FacesSchema {
      FACES_20,
      FACES_21,
      FACES_22,
      FACES_23,
      FACES_12,
      FACES_11,
      FACELET_TAGLIB_20,
      FACELET_TAGLIB_22;
   }
}
