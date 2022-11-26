package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ResourceLocator;
import com.oracle.weblogic.lifecycle.provisioning.api.ResourceLocatorException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class AbstractResourceLocator implements ResourceLocator {
   public final URL getResourceRelativeTo(URI baseUri, URI relativeURI) throws ResourceLocatorException {
      return this.getResourceRelativeTo(baseUri, relativeURI, (ResourceLocator.URIResolver)null);
   }

   public URL getResourceRelativeTo(URI base, URI relativeURI, ResourceLocator.URIResolver resolver) throws ResourceLocatorException {
      Objects.requireNonNull(base);
      Objects.requireNonNull(relativeURI);
      if (!base.isAbsolute()) {
         throw new IllegalArgumentException("!base.isAbsolute()");
      } else {
         Object uriResolver;
         if (resolver == null) {
            uriResolver = new DefaultURIResolver();
         } else {
            uriResolver = resolver;
         }

         URI resolvedURI = ((ResourceLocator.URIResolver)uriResolver).resolve(base, relativeURI);
         if (resolvedURI == null) {
            throw new IllegalStateException("resolver.resolve() == null");
         } else {
            URL returnValue = null;

            try {
               returnValue = this.toURL(resolvedURI);
               return returnValue;
            } catch (IOException var8) {
               throw new ResourceLocatorException(var8);
            }
         }
      }
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
