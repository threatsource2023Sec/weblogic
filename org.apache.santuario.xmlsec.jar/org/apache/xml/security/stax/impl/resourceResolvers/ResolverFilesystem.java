package org.apache.xml.security.stax.impl.resourceResolvers;

import java.io.InputStream;
import java.net.URI;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverFilesystem implements ResourceResolver, ResourceResolverLookup {
   private String uri;
   private String baseURI;

   public ResolverFilesystem() {
   }

   public ResolverFilesystem(String uri, String baseURI) {
      this.uri = uri;
      this.baseURI = baseURI;
   }

   public ResourceResolverLookup canResolve(String uri, String baseURI) {
      if (uri == null) {
         return null;
      } else {
         return !uri.startsWith("file:") && (baseURI == null || !baseURI.startsWith("file:")) ? null : this;
      }
   }

   public ResourceResolver newInstance(String uri, String baseURI) {
      return new ResolverFilesystem(uri, baseURI);
   }

   public boolean isSameDocumentReference() {
      return false;
   }

   public boolean matches(XMLSecStartElement xmlSecStartElement) {
      return false;
   }

   public InputStream getInputStreamFromExternalReference() throws XMLSecurityException {
      try {
         URI tmp;
         if (this.baseURI != null && !"".equals(this.baseURI)) {
            tmp = (new URI(this.baseURI)).resolve(this.uri);
         } else {
            tmp = new URI(this.uri);
         }

         if (tmp.getFragment() != null) {
            tmp = new URI(tmp.getScheme(), tmp.getSchemeSpecificPart(), (String)null);
         }

         return tmp.toURL().openStream();
      } catch (Exception var2) {
         throw new XMLSecurityException(var2);
      }
   }
}
