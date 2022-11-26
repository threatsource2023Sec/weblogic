package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.DocumentLocator;
import com.oracle.weblogic.lifecycle.provisioning.api.DocumentLocatorException;
import com.oracle.weblogic.lifecycle.provisioning.api.ResourceLocator;
import com.oracle.weblogic.lifecycle.provisioning.api.ResourceLocatorException;
import com.oracle.weblogic.lifecycle.provisioning.api.TransformationException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class AbstractDocumentLocator extends AbstractResourceLocator implements DocumentLocator {
   private final DocumentFactory documentFactory;

   protected AbstractDocumentLocator() {
      this((DocumentFactory)null);
   }

   protected AbstractDocumentLocator(DocumentFactory documentFactory) {
      if (documentFactory == null) {
         this.documentFactory = new DocumentFactory();
      } else {
         this.documentFactory = documentFactory;
      }

      assert this.documentFactory != null;

   }

   protected final DocumentFactory getDocumentFactory() {
      return this.documentFactory;
   }

   public final Document getDocumentRelativeTo(Document document, URI relativeURI) throws DocumentLocatorException {
      return this.getDocumentRelativeTo(document, relativeURI, (ResourceLocator.URIResolver)null);
   }

   public Document getDocumentRelativeTo(Document document, URI relativeURI, ResourceLocator.URIResolver resolver) throws DocumentLocatorException {
      Document returnValue;
      if (document != null && relativeURI != null) {
         String baseDocumentUriString;
         synchronized(document) {
            baseDocumentUriString = document.getDocumentURI();
         }

         if (baseDocumentUriString == null) {
            throw new DocumentLocatorException("document.getDocumentURI() == null");
         }

         URI baseDocumentURI = null;

         try {
            baseDocumentURI = new URI(baseDocumentUriString);
         } catch (URISyntaxException var23) {
            throw new DocumentLocatorException(var23);
         }

         if (!baseDocumentURI.isAbsolute()) {
            throw new DocumentLocatorException("new URI(document.getDocumentURI()).isAbsolute() == false");
         }

         Object uriResolver;
         if (resolver == null) {
            uriResolver = new DefaultURIResolver();
         } else {
            uriResolver = resolver;
         }

         URI documentURI = null;

         try {
            documentURI = ((ResourceLocator.URIResolver)uriResolver).resolve(baseDocumentURI, relativeURI);
         } catch (DocumentLocatorException var21) {
            throw var21;
         } catch (ResourceLocatorException var22) {
            throw new DocumentLocatorException(var22);
         }

         if (documentURI == null) {
            returnValue = null;
         } else {
            if (!documentURI.isAbsolute()) {
               throw new DocumentLocatorException("!resolver.resolve().isAbsolute()");
            }

            DocumentFactory documentFactory = this.getDocumentFactory();

            assert documentFactory != null;

            Document temp = null;

            try {
               temp = documentFactory.getDocument(documentURI);
            } catch (ParserConfigurationException | SAXException | TransformationException | IOException var19) {
               throw new DocumentLocatorException(var19);
            } finally {
               returnValue = temp;
            }
         }
      } else {
         returnValue = null;
      }

      return returnValue;
   }
}
