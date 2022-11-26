package org.python.netty.handler.ssl.util;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class InsecureTrustManagerFactory extends SimpleTrustManagerFactory {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(InsecureTrustManagerFactory.class);
   public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();
   private static final TrustManager tm = new X509TrustManager() {
      public void checkClientTrusted(X509Certificate[] chain, String s) {
         InsecureTrustManagerFactory.logger.debug("Accepting a client certificate: " + chain[0].getSubjectDN());
      }

      public void checkServerTrusted(X509Certificate[] chain, String s) {
         InsecureTrustManagerFactory.logger.debug("Accepting a server certificate: " + chain[0].getSubjectDN());
      }

      public X509Certificate[] getAcceptedIssuers() {
         return EmptyArrays.EMPTY_X509_CERTIFICATES;
      }
   };

   private InsecureTrustManagerFactory() {
   }

   protected void engineInit(KeyStore keyStore) throws Exception {
   }

   protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
   }

   protected TrustManager[] engineGetTrustManagers() {
      return new TrustManager[]{tm};
   }
}
