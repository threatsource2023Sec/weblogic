package org.glassfish.tyrus.client.auth;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.tyrus.core.Beta;

@Beta
public class AuthConfig {
   static final Charset CHARACTER_SET = Charset.forName("iso-8859-1");
   static final String BASIC = "Basic";
   static final String DIGEST = "Digest";
   private final Map authenticators;

   private AuthConfig(Map authenticators) {
      TreeMap map = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      map.putAll(authenticators);
      this.authenticators = Collections.unmodifiableMap(map);
   }

   public Map getAuthenticators() {
      return this.authenticators;
   }

   public static Builder builder() {
      return AuthConfig.Builder.create();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("AuthConfig{");
      boolean first = true;
      Iterator var3 = this.authenticators.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry authenticator = (Map.Entry)var3.next();
         if (first) {
            first = false;
         } else {
            sb.append(", ");
         }

         sb.append((String)authenticator.getKey());
         sb.append("->");
         sb.append(((Authenticator)authenticator.getValue()).getClass().getName());
      }

      sb.append("}");
      return sb.toString();
   }

   // $FF: synthetic method
   AuthConfig(Map x0, Object x1) {
      this(x0);
   }

   public static final class Builder {
      private final Map authenticators;

      private Builder() {
         this.authenticators = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         this.authenticators.put("Basic", new BasicAuthenticator());
         this.authenticators.put("Digest", new DigestAuthenticator());
      }

      public static Builder create() {
         return new Builder();
      }

      public final Builder registerAuthProvider(String scheme, Authenticator authenticator) {
         this.authenticators.put(scheme, authenticator);
         return this;
      }

      public final Builder disableProvidedBasicAuth() {
         if (this.authenticators.get("Basic") != null && this.authenticators.get("Basic") instanceof BasicAuthenticator) {
            this.authenticators.remove("Basic");
         }

         return this;
      }

      public final Builder disableProvidedDigestAuth() {
         if (this.authenticators.get("Digest") != null && this.authenticators.get("Digest") instanceof DigestAuthenticator) {
            this.authenticators.remove("Digest");
         }

         return this;
      }

      public AuthConfig build() {
         return new AuthConfig(this.authenticators);
      }
   }
}
