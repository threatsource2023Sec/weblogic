package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.TransformationException;
import com.oracle.weblogic.lifecycle.provisioning.api.Transformer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import weblogic.utils.XXEUtils;

@Service
@Singleton
public class DocumentFactory {
   protected Logger getLogger() {
      return Logger.getLogger(DocumentFactory.class.getName());
   }

   public final Document getDocument(URI absoluteDocumentURI) throws IOException, ParserConfigurationException, SAXException, TransformationException {
      return this.getDocument(absoluteDocumentURI, true);
   }

   public final Document getDocument(URI absoluteDocumentURI, boolean transform) throws IOException, ParserConfigurationException, SAXException, TransformationException {
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(DocumentFactory.class.getName(), "getDocument", new Object[]{absoluteDocumentURI, transform});
      }

      Document returnValue = null;
      if (absoluteDocumentURI != null) {
         if (!absoluteDocumentURI.isAbsolute()) {
            throw new IllegalArgumentException("!absoluteDocumentURI.isAbsolute()");
         }

         URL absoluteDocumentURL = this.toURL(absoluteDocumentURI);
         if (absoluteDocumentURL != null) {
            InputStream inputStream = absoluteDocumentURL.openStream();
            Throwable var7 = null;

            try {
               if (inputStream != null) {
                  DocumentBuilder documentBuilder = this.newDocumentBuilder();
                  if (documentBuilder == null) {
                     throw new IllegalStateException("newDocumentBuilder() == null");
                  }

                  synchronized(documentBuilder) {
                     returnValue = documentBuilder.parse(inputStream);
                  }

                  if (returnValue != null) {
                     returnValue.setDocumentURI(absoluteDocumentURI.toString());
                     if (transform) {
                        Transformer transformer = this.getTransformerFor(returnValue);
                        if (transformer != null) {
                           synchronized(transformer) {
                              returnValue = (Document)transformer.transform(returnValue);
                           }

                           if (returnValue != null) {
                              String documentUriString = returnValue.getDocumentURI();
                              if (documentUriString == null || documentUriString.trim().isEmpty()) {
                                 returnValue.setDocumentURI(absoluteDocumentURI.toString());
                              }
                           }
                        }
                     }
                  }
               }
            } catch (Throwable var23) {
               var7 = var23;
               throw var23;
            } finally {
               if (inputStream != null) {
                  if (var7 != null) {
                     try {
                        inputStream.close();
                     } catch (Throwable var20) {
                        var7.addSuppressed(var20);
                     }
                  } else {
                     inputStream.close();
                  }
               }

            }
         }
      }

      if (logger.isLoggable(Level.FINER)) {
         logger.exiting(DocumentFactory.class.getName(), "getDocument", returnValue);
      }

      return returnValue;
   }

   protected Transformer getTransformerFor(Document document) {
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(DocumentFactory.class.getName(), "getTransformerFor", document);
      }

      DocumentTransformer returnValue;
      if (document == null) {
         returnValue = null;
      } else {
         returnValue = new DocumentTransformer();
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(DocumentFactory.class.getName(), "getTransformerFor", returnValue);
      }

      return returnValue;
   }

   protected DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
      Logger logger = this.getLogger();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(DocumentFactory.class.getName(), "newDocumentBuilder");
      }

      DocumentBuilderFactory factory = null;
      DocumentBuilder returnValue = null;
      Class var4 = DocumentBuilderFactory.class;
      synchronized(DocumentBuilderFactory.class) {
         factory = XXEUtils.createDocumentBuilderFactoryInstance();
      }

      assert factory != null;

      synchronized(factory) {
         factory.setNamespaceAware(true);
         returnValue = factory.newDocumentBuilder();
      }

      assert returnValue != null;

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(DocumentFactory.class.getName(), "newDocumentBuilder", returnValue);
      }

      return returnValue;
   }

   protected URL toURL(URI uri) throws IOException {
      URL returnValue;
      if (uri == null) {
         returnValue = null;
      } else {
         returnValue = uri.toURL();
      }

      return returnValue;
   }
}
