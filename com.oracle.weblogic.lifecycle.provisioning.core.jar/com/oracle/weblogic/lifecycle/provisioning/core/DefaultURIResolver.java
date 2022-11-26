package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ResourceLocator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class DefaultURIResolver implements ResourceLocator.URIResolver {
   public URI resolve(URI baseUri, URI relativeUri) {
      Objects.requireNonNull(baseUri);
      Objects.requireNonNull(relativeUri);
      URI returnValue = null;
      if (baseUri.isOpaque() && !relativeUri.isAbsolute() && "jar".equals(baseUri.getScheme())) {
         String ssp = baseUri.getSchemeSpecificPart();

         assert ssp != null;

         String relativePath = relativeUri.normalize().getPath();
         if (relativePath != null) {
            int index;
            if (relativePath.indexOf(47) == 0) {
               index = ssp.lastIndexOf("!/") + 1;
            } else if (!relativePath.startsWith("../")) {
               index = ssp.lastIndexOf(47) + 1;
            } else {
               index = -1;
            }

            if (index >= 1) {
               ssp = ssp.substring(0, index) + relativePath;

               try {
                  returnValue = new URI("jar", ssp, (String)null);
               } catch (URISyntaxException var8) {
                  throw new IllegalArgumentException("relativeUri", var8);
               }
            }
         }
      }

      if (returnValue == null) {
         returnValue = baseUri.resolve(relativeUri);
      }

      return returnValue;
   }
}
