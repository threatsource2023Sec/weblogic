package weblogic.security.utils;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSocket;

public final class TrustManagerEnvironment {
   private static ThreadLocal threadInstance = new ThreadLocal();
   private TrustManagerEnvironment parentEnv;
   private X509Certificate[] trustedCAs;
   private SSLSocket sslSocket;

   public static void push(X509Certificate[] trustedCAs, SSLSocket sslSocket) {
      TrustManagerEnvironment newEnv = new TrustManagerEnvironment(getInstance(), trustedCAs, sslSocket);
      threadInstance.set(newEnv);
   }

   public static void pop() {
      TrustManagerEnvironment env = getInstance();
      if (env != null) {
         threadInstance.set(env._getParentEnv());
      }
   }

   public static X509Certificate[] getTrustedCAs() {
      TrustManagerEnvironment env = getInstance();
      return env == null ? null : env._getTrustedCAs();
   }

   public static SSLSocket getSSLSocket() {
      TrustManagerEnvironment env = getInstance();
      return env == null ? null : env._getSSLSocket();
   }

   private static TrustManagerEnvironment getInstance() {
      return (TrustManagerEnvironment)threadInstance.get();
   }

   private TrustManagerEnvironment(TrustManagerEnvironment parentEnv, X509Certificate[] trustedCAs, SSLSocket sslSocket) {
      this.parentEnv = parentEnv;
      this.trustedCAs = trustedCAs;
      this.sslSocket = sslSocket;
   }

   private TrustManagerEnvironment _getParentEnv() {
      return this.parentEnv;
   }

   private X509Certificate[] _getTrustedCAs() {
      return this.trustedCAs;
   }

   private SSLSocket _getSSLSocket() {
      return this.sslSocket;
   }
}
