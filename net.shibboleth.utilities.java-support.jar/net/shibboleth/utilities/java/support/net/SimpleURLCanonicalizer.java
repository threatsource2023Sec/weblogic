package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Strings;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public final class SimpleURLCanonicalizer {
   private static Map schemePortMap = new HashMap();

   private SimpleURLCanonicalizer() {
   }

   public static void registerSchemePortMapping(@Nonnull @NotEmpty String scheme, @Nonnull Integer port) {
      String trimmedScheme = (String)Constraint.isNotNull(StringSupport.trimOrNull(scheme), "Scheme cannot be null");
      Constraint.isNotNull(port, "Port cannot be null");
      schemePortMap.put(trimmedScheme.toLowerCase(), port);
   }

   public static void deregisterSchemePortMapping(@Nonnull String scheme) {
      String trimmedScheme = (String)Constraint.isNotNull(StringSupport.trimOrNull(scheme), "Scheme cannot be null");
      schemePortMap.remove(trimmedScheme.toLowerCase());
   }

   @Nullable
   public static Integer getRegisteredPort(@Nonnull @NotEmpty String scheme) {
      String trimmedScheme = (String)Constraint.isNotNull(StringSupport.trimOrNull(scheme), "Scheme cannot be null");
      return (Integer)schemePortMap.get(trimmedScheme.toLowerCase());
   }

   @Nonnull
   @NotEmpty
   public static String canonicalize(@Nonnull @NotEmpty String url) throws MalformedURLException {
      Constraint.isFalse(Strings.isNullOrEmpty(url), "URL was null or empty");
      URLBuilder urlBuilder = new URLBuilder(url);
      canonicalize(urlBuilder);
      return urlBuilder.buildURL();
   }

   private static void canonicalize(@Nonnull URLBuilder url) {
      if (url.getScheme() != null) {
         url.setScheme(url.getScheme().toLowerCase());
         String scheme = url.getScheme();
         Integer port = getRegisteredPort(scheme);
         if (port != null && port.equals(url.getPort())) {
            url.setPort((Integer)null);
         }
      }

      if (url.getHost() != null) {
         url.setHost(url.getHost().toLowerCase());
      }

   }

   static {
      registerSchemePortMapping("ftp", 23);
      registerSchemePortMapping("http", 80);
      registerSchemePortMapping("https", 443);
      registerSchemePortMapping("ldap", 389);
      registerSchemePortMapping("ldaps", 636);
   }
}
