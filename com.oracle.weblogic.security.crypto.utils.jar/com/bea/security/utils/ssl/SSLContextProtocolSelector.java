package com.bea.security.utils.ssl;

import java.security.Provider;
import java.security.Security;
import java.util.Locale;

public final class SSLContextProtocolSelector {
   public static final String WLS_SSLCONTEXT_PROTOCOL_SYSPROPNAME = "weblogic.security.ssl.sslcontext.protocol";
   private static final String DEFAULT_PROTOCOL = "TLS";

   private static String getSSLContextProtocolSupported(Provider provider, String protocol) {
      return null != provider.getService("SSLContext", protocol) ? protocol : null;
   }

   public static String getSSLContextProtocol() {
      String overrideAlgorithm = System.getProperty("weblogic.security.ssl.sslcontext.protocol");
      if (null != overrideAlgorithm) {
         return overrideAlgorithm;
      } else {
         Provider[] providers = Security.getProviders("SSLContext.TLS");
         if (null != providers && providers.length > 0 && null != providers[0]) {
            Provider provider = providers[0];
            String providerName = provider.getName();
            if (null != providerName && providerName.toUpperCase(Locale.US).contains("IBM")) {
               String algorithm;
               if ((algorithm = getSSLContextProtocolSupported(provider, "SSL_TLSv2")) != null) {
                  return algorithm;
               } else {
                  return (algorithm = getSSLContextProtocolSupported(provider, "SSL_TLS")) != null ? algorithm : "TLS";
               }
            } else {
               return "TLS";
            }
         } else {
            return "TLS";
         }
      }
   }
}
