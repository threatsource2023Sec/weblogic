package org.opensaml.security.httpclient.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.httpclient.HttpClientSupport;
import net.shibboleth.utilities.java.support.httpclient.TLSSocketFactoryBuilder;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.opensaml.security.x509.tls.impl.ThreadLocalX509CredentialKeyManager;

public final class SecurityEnhancedHttpClientSupport {
   private SecurityEnhancedHttpClientSupport() {
   }

   @Nonnull
   public static LayeredConnectionSocketFactory buildTLSSocketFactory() {
      return buildTLSSocketFactory(true, false);
   }

   @Nonnull
   public static LayeredConnectionSocketFactory buildTLSSocketFactoryWithClientTLS() {
      return buildTLSSocketFactory(true, true);
   }

   @Nonnull
   public static LayeredConnectionSocketFactory buildTLSSocketFactory(boolean supportTrustEngine, boolean supportClientTLS) {
      TLSSocketFactoryBuilder wrappedFactoryBuilder = new TLSSocketFactoryBuilder();
      if (!supportTrustEngine && !supportClientTLS) {
         return HttpClientSupport.buildStrictTLSSocketFactory();
      } else {
         wrappedFactoryBuilder.setHostnameVerifier(new AllowAllHostnameVerifier());
         if (supportTrustEngine) {
            wrappedFactoryBuilder.setTrustManagers(Collections.singletonList(HttpClientSupport.buildNoTrustX509TrustManager()));
         }

         if (supportClientTLS) {
            wrappedFactoryBuilder.setKeyManagers(Collections.singletonList(new ThreadLocalX509CredentialKeyManager()));
         }

         return new SecurityEnhancedTLSSocketFactory(wrappedFactoryBuilder.build(), new StrictHostnameVerifier());
      }
   }
}
