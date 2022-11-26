package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.TransformationException;
import com.oracle.weblogic.lifecycle.provisioning.api.Transformer;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

public class DocumentTransformer implements Transformer {
   private Logger logger;
   private Source xslt;
   private Templates compiledStylesheet;
   private TransformerFactory tf;
   private javax.xml.transform.Transformer transformer;

   public DocumentTransformer() {
      Logger logger = this.getLogger();
      if (logger == null) {
         this.logger = Logger.getLogger(DocumentTransformer.class.getName());
      } else {
         this.logger = logger;
      }

   }

   public DocumentTransformer(Source stylesheet) throws TransformerException {
      this();
      this.setStylesheet(stylesheet);
   }

   public final Source getStylesheet() {
      return this.xslt;
   }

   public final void setStylesheet(Source source) throws TransformerException {
      this.xslt = source;
      if (source == null) {
         this.compiledStylesheet = null;
         this.transformer = null;
      } else {
         TransformerFactory transformerFactory = this.getTransformerFactory();
         if (transformerFactory == null) {
            throw new IllegalStateException("getTransformerFactory() == null");
         }

         synchronized(transformerFactory) {
            this.compiledStylesheet = transformerFactory.newTemplates(source);
         }

         javax.xml.transform.Transformer transformer = this.createTransformer(this.compiledStylesheet);
         if (transformer == null) {
            throw new IllegalStateException("createTransformer(Templates) == null");
         }

         this.transformer = transformer;
      }

   }

   public Document transform(Document source) throws TransformationException {
      Logger logger = this.logger;
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(DocumentTransformer.class.getName(), "transform", source);
      }

      Document returnValue;
      if (source == null) {
         returnValue = null;
      } else {
         javax.xml.transform.Transformer transformer = this.transformer;
         if (transformer == null) {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, DocumentTransformer.class.getName(), "transform", "No explicit stylesheet set for a Document with uri {0}; attempting to find a default one", source.getDocumentURI());
            }

            try {
               Templates defaultStylesheet = this.getDefaultStylesheet(source);
               if (logger != null && logger.isLoggable(Level.FINER)) {
                  logger.logp(Level.FINER, DocumentTransformer.class.getName(), "transform", "Default stylesheet: {0}", defaultStylesheet);
               }

               if (defaultStylesheet != null) {
                  transformer = this.createTransformer(defaultStylesheet);
               }
            } catch (TransformerException | IOException var10) {
               throw new TransformationException(var10);
            }
         }

         if (transformer == null) {
            returnValue = source;
         } else {
            DOMResult result = new DOMResult();

            try {
               synchronized(transformer) {
                  transformer.transform(new DOMSource(source), result);
               }
            } catch (TransformerException var9) {
               throw new TransformationException(var9);
            }

            Object domResultNode = result.getNode();
            if (!(domResultNode instanceof Document)) {
               throw new TransformationException(new IllegalStateException("The result of the transformation was not a Document: " + domResultNode));
            }

            returnValue = (Document)domResultNode;
            if (returnValue.getDocumentURI() == null) {
               String sourceDocumentURI = source.getDocumentURI();
               if (sourceDocumentURI != null) {
                  returnValue.setDocumentURI(sourceDocumentURI);
               }
            }

            assert returnValue.getDocumentURI() != null;
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(DocumentTransformer.class.getName(), "transform", returnValue);
      }

      return returnValue;
   }

   protected Logger getLogger() {
      return Logger.getLogger(DocumentTransformer.class.getName());
   }

   protected synchronized TransformerFactory getTransformerFactory() {
      if (this.tf == null) {
         this.tf = TransformerFactory.newInstance();
      }

      return this.tf;
   }

   protected Templates getDefaultStylesheet(Document document) throws IOException, TransformerException {
      Logger logger = this.logger;
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(DocumentTransformer.class.getName(), "getDefaultStylesheet", document);
      }

      Templates returnValue = null;
      if (document != null) {
         String documentUriString;
         synchronized(document) {
            documentUriString = document.getDocumentURI();
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.logp(Level.FINER, DocumentTransformer.class.getName(), "getDefaultStylesheet", "Document URI: {0}", documentUriString);
         }

         if (documentUriString != null && documentUriString.endsWith(".xml")) {
            String stylesheetResourceName = documentUriString.substring(documentUriString.lastIndexOf(47) + 1, documentUriString.length() - ".xml".length()) + ".xslt";

            assert stylesheetResourceName != null;

            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, DocumentTransformer.class.getName(), "getDefaultStylesheet", "{0} --> {1}", new Object[]{documentUriString, stylesheetResourceName});
            }

            URL stylesheetResource = this.getResource(stylesheetResourceName);
            if (stylesheetResource != null) {
               if (logger != null && logger.isLoggable(Level.FINER)) {
                  logger.logp(Level.FINER, DocumentTransformer.class.getName(), "getDefaultStylesheet", "Using stylesheet resource {0} for document {1}", new Object[]{stylesheetResource, documentUriString});
               }

               TransformerFactory tf = this.getTransformerFactory();
               if (tf == null) {
                  throw new IllegalStateException("getTransformerFactory() == null");
               }

               Source xslt = new StreamSource(stylesheetResource.openStream());
               synchronized(tf) {
                  returnValue = tf.newTemplates(xslt);
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(DocumentTransformer.class.getName(), "getDefaultStylesheet", returnValue);
      }

      return returnValue;
   }

   protected URL getResource(String resourceName) {
      return Thread.currentThread().getContextClassLoader().getResource(resourceName);
   }

   protected javax.xml.transform.Transformer createTransformer(Templates stylesheet) throws TransformerException {
      javax.xml.transform.Transformer returnValue;
      if (stylesheet == null) {
         TransformerFactory tf = this.getTransformerFactory();
         if (tf == null) {
            throw new IllegalStateException("getTransformerFactory() == null");
         }

         synchronized(tf) {
            returnValue = tf.newTransformer();
         }
      } else {
         synchronized(stylesheet) {
            returnValue = stylesheet.newTransformer();
         }
      }

      assert returnValue != null;

      URIResolver old = returnValue.getURIResolver();
      URIResolver uriResolver = this.createURIResolver(old);
      if (uriResolver != null && uriResolver != old) {
         returnValue.setURIResolver(uriResolver);
      }

      return returnValue;
   }

   protected URIResolver createURIResolver(URIResolver potentialDelegate) {
      return null;
   }
}
