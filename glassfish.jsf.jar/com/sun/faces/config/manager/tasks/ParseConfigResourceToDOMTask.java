package com.sun.faces.config.manager.tasks;

import com.sun.faces.config.ConfigManager;
import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.manager.DbfFactory;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.config.processor.FacesFlowDefinitionConfigProcessor;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Timer;
import com.sun.faces.util.Util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class ParseConfigResourceToDOMTask implements Callable {
   public static final String WEB_INF_MARKER = "com.sun.faces.webinf";
   private static final Logger LOGGER;
   private static final String JAVAEE_SCHEMA_LEGACY_DEFAULT_NS = "http://java.sun.com/xml/ns/javaee";
   private static final String JAVAEE_SCHEMA_DEFAULT_NS = "http://xmlns.jcp.org/xml/ns/javaee";
   private static final String EMPTY_FACES_CONFIG = "com/sun/faces/empty-faces-config.xml";
   private static final String FACES_CONFIG_TAGNAME = "faces-config";
   private static final String FACELET_TAGLIB_TAGNAME = "facelet-taglib";
   private static final String FACES_CONFIG_1_X_DEFAULT_NS = "http://java.sun.com/JSF/Configuration";
   private static final String FACELETS_1_0_DEFAULT_NS = "http://java.sun.com/JSF/Facelet";
   private static final String FACES_TO_1_1_PRIVATE_XSL = "/com/sun/faces/jsf1_0-1_1toSchema.xsl";
   private static final String FACELETS_TO_2_0_XSL = "/com/sun/faces/facelets1_0-2_0toSchema.xsl";
   private ServletContext servletContext;
   private URI documentURI;
   private DocumentBuilderFactory factory;
   private boolean validating;

   public ParseConfigResourceToDOMTask(ServletContext servletContext, boolean validating, URI documentURI) throws Exception {
      this.servletContext = servletContext;
      this.documentURI = documentURI;
      this.validating = validating;
   }

   public DocumentInfo call() throws Exception {
      try {
         Timer timer = Timer.getInstance();
         if (timer != null) {
            timer.startTiming();
         }

         Document document = this.getDocument();
         if (timer != null) {
            timer.stopTiming();
            timer.logResult("Parse " + this.documentURI.toURL().toExternalForm());
         }

         return new DocumentInfo(document, this.documentURI);
      } catch (Exception var3) {
         throw new ConfigurationException(MessageFormat.format("Unable to parse document ''{0}'': {1}", this.documentURI.toURL().toExternalForm(), var3.getMessage()), var3);
      }
   }

   private Document getDocument() throws Exception {
      DocumentBuilder db = this.getNonValidatingBuilder();
      URL documentURL = this.documentURI.toURL();
      InputSource is = new InputSource(getInputStream(documentURL));
      is.setSystemId(this.documentURI.toURL().toExternalForm());
      Document doc = null;

      try {
         doc = db.parse(is);
      } catch (SAXParseException var16) {
         InputStream stream = is.getByteStream();
         stream.close();
         is = new InputSource(getInputStream(documentURL));
         stream = is.getByteStream();
         if (this.streamIsZeroLengthOrEmpty(stream) && documentURL.toExternalForm().endsWith("faces-config.xml")) {
            ClassLoader loader = this.getClass().getClassLoader();
            is = new InputSource(getInputStream(loader.getResource("com/sun/faces/empty-faces-config.xml")));
            doc = db.parse(is);
         }
      }

      String documentNS = null;
      if (null == doc) {
         if (FacesFlowDefinitionConfigProcessor.uriIsFlowDefinition(this.documentURI)) {
            documentNS = "http://xmlns.jcp.org/xml/ns/javaee";
            doc = FacesFlowDefinitionConfigProcessor.synthesizeEmptyFlowDefinition(this.documentURI);
         }
      } else {
         Element documentElement = doc.getDocumentElement();
         documentNS = documentElement.getNamespaceURI();
         String rootElementTagName = documentElement.getTagName();
         boolean isNonFacesConfigDocument = !"faces-config".equals(rootElementTagName) && !"facelet-taglib".equals(rootElementTagName);
         if (isNonFacesConfigDocument) {
            ClassLoader loader = this.getClass().getClassLoader();
            is = new InputSource(getInputStream(loader.getResource("com/sun/faces/empty-faces-config.xml")));
            doc = db.parse(is);
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, MessageFormat.format("Config document {0} with namespace URI {1} is not a faces-config or facelet-taglib file.  Ignoring.", this.documentURI.toURL().toExternalForm(), documentNS));
            }

            return doc;
         }
      }

      Document returnDoc;
      if (this.validating && documentNS != null) {
         DOMSource domSource = new DOMSource(doc, documentURL.toExternalForm());
         Node documentElement = ((Document)domSource.getNode()).getDocumentElement();
         Attr version;
         Schema schema;
         DocumentBuilder builder;
         switch (documentNS) {
            case "http://xmlns.jcp.org/xml/ns/javaee":
               version = (Attr)documentElement.getAttributes().getNamedItem("version");
               if (version == null) {
                  throw new ConfigurationException("No document version available.");
               }

               switch (version.getValue()) {
                  case "2.2":
                     if ("facelet-taglib".equals(documentElement.getLocalName())) {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACELET_TAGLIB_22);
                     } else {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_22);
                     }
                     break;
                  case "2.3":
                     if ("facelet-taglib".equals(documentElement.getLocalName())) {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACELET_TAGLIB_22);
                     } else {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_23);
                     }
                     break;
                  default:
                     throw new ConfigurationException("Unknown Schema version: " + versionStr);
               }

               builder = this.getBuilderForSchema(schema);
               if (builder.isValidating()) {
                  builder.getSchema().newValidator().validate(domSource);
                  returnDoc = (Document)domSource.getNode();
               } else {
                  returnDoc = (Document)domSource.getNode();
               }
               break;
            case "http://java.sun.com/xml/ns/javaee":
               version = (Attr)documentElement.getAttributes().getNamedItem("version");
               if (version == null) {
                  throw new ConfigurationException("No document version available.");
               }

               switch (version.getValue()) {
                  case "2.0":
                     if ("facelet-taglib".equals(documentElement.getLocalName())) {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACELET_TAGLIB_20);
                     } else {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_20);
                     }
                     break;
                  case "2.1":
                     if ("facelet-taglib".equals(documentElement.getLocalName())) {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACELET_TAGLIB_20);
                     } else {
                        schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_21);
                     }
                     break;
                  case "1.2":
                     schema = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_12);
                     break;
                  default:
                     throw new ConfigurationException("Unknown Schema version: " + versionStr);
               }

               builder = this.getBuilderForSchema(schema);
               if (builder.isValidating()) {
                  builder.getSchema().newValidator().validate(domSource);
                  returnDoc = (Document)domSource.getNode();
               } else {
                  returnDoc = (Document)domSource.getNode();
               }
               break;
            default:
               DOMResult domResult = new DOMResult();
               Transformer transformer = getTransformer(documentNS);
               transformer.transform(domSource, domResult);
               ((Document)domResult.getNode()).setDocumentURI(((Document)domSource.getNode()).getDocumentURI());
               Schema schemaToApply;
               switch (documentNS) {
                  case "http://java.sun.com/JSF/Configuration":
                     schemaToApply = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACES_11);
                     break;
                  case "http://java.sun.com/JSF/Facelet":
                     schemaToApply = DbfFactory.getSchema(this.servletContext, DbfFactory.FacesSchema.FACELET_TAGLIB_20);
                     break;
                  default:
                     throw new IllegalStateException();
               }

               builder = this.getBuilderForSchema(schemaToApply);
               if (builder.isValidating()) {
                  builder.getSchema().newValidator().validate(new DOMSource(domResult.getNode()));
                  returnDoc = (Document)domResult.getNode();
               } else {
                  returnDoc = (Document)domResult.getNode();
               }
         }
      } else {
         returnDoc = doc;
      }

      if (documentURL.toExternalForm().contains("/WEB-INF/faces-config.xml")) {
         Attr webInf = returnDoc.createAttribute("com.sun.faces.webinf");
         webInf.setValue("true");
         returnDoc.getDocumentElement().getAttributes().setNamedItem(webInf);
      }

      return returnDoc;
   }

   private boolean streamIsZeroLengthOrEmpty(InputStream is) throws IOException {
      boolean isZeroLengthOrEmpty = 0 == is.available();
      int size = true;
      byte[] b = new byte[1024];

      while(!isZeroLengthOrEmpty && -1 != is.read(b, 0, 1024)) {
         String s = (new String(b, "UTF-8")).trim();
         isZeroLengthOrEmpty = 0 == s.length();
         b[0] = 0;

         for(int i = 1; i < 1024; i += i) {
            System.arraycopy(b, 0, b, i, 1024 - i < i ? 1024 - i : i);
         }
      }

      return isZeroLengthOrEmpty;
   }

   private static Transformer getTransformer(String documentNS) throws Exception {
      TransformerFactory factory = Util.createTransformerFactory();
      String xslToApply;
      switch (documentNS) {
         case "http://java.sun.com/JSF/Configuration":
            xslToApply = "/com/sun/faces/jsf1_0-1_1toSchema.xsl";
            break;
         case "http://java.sun.com/JSF/Facelet":
            xslToApply = "/com/sun/faces/facelets1_0-2_0toSchema.xsl";
            break;
         default:
            throw new IllegalStateException();
      }

      return factory.newTransformer(new StreamSource(getInputStream(ConfigManager.class.getResource(xslToApply))));
   }

   private static InputStream getInputStream(URL url) throws IOException {
      URLConnection connection = url.openConnection();
      connection.setUseCaches(false);
      return new BufferedInputStream(connection.getInputStream());
   }

   private DocumentBuilder getNonValidatingBuilder() throws Exception {
      DocumentBuilderFactory tFactory = DbfFactory.getFactory();
      tFactory.setValidating(false);
      DocumentBuilder tBuilder = tFactory.newDocumentBuilder();
      tBuilder.setEntityResolver(DbfFactory.FACES_ENTITY_RESOLVER);
      tBuilder.setErrorHandler(DbfFactory.FACES_ERROR_HANDLER);
      return tBuilder;
   }

   private DocumentBuilder getBuilderForSchema(Schema schema) throws Exception {
      this.factory = DbfFactory.getFactory();

      try {
         this.factory.setSchema(schema);
      } catch (UnsupportedOperationException var3) {
         return this.getNonValidatingBuilder();
      }

      DocumentBuilder builder = this.factory.newDocumentBuilder();
      builder.setEntityResolver(DbfFactory.FACES_ENTITY_RESOLVER);
      builder.setErrorHandler(DbfFactory.FACES_ERROR_HANDLER);
      return builder;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
