package org.opensaml.saml.metadata.resolver.index.impl;

import java.net.MalformedURLException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.net.SimpleURLCanonicalizer;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class MetadataIndexSupport {
   private MetadataIndexSupport() {
   }

   @Nonnull
   public static String canonicalizeLocationURI(@Nonnull String input) throws MalformedURLException {
      String uri = StringSupport.trimOrNull(input);
      if (uri == null) {
         throw new MalformedURLException("URL input was null or empty");
      } else {
         URLBuilder urlBuilder = new URLBuilder(uri);
         urlBuilder.setUsername((String)null);
         urlBuilder.setPassword((String)null);
         urlBuilder.getQueryParams().clear();
         urlBuilder.setFragment((String)null);
         return SimpleURLCanonicalizer.canonicalize(urlBuilder.buildURL());
      }
   }

   @Nullable
   public static String trimURLPathSegment(@Nullable String input) {
      String path = StringSupport.trimOrNull(input);
      if (path != null && !"/".equals(path)) {
         int idx = path.lastIndexOf("/");
         if (idx > 0) {
            return path.endsWith("/") ? path.substring(0, idx) : path.substring(0, idx + 1);
         } else {
            return idx == 0 ? "/" : null;
         }
      } else {
         return null;
      }
   }
}
