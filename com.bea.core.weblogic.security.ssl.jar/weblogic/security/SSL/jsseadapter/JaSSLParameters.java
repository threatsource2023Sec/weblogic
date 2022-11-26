package weblogic.security.SSL.jsseadapter;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

final class JaSSLParameters {
   private String[] enabledCipherSuites;
   private String[] enabledProtocols;
   private boolean needClientAuth;
   private boolean wantClientAuth;
   private boolean useClientMode;
   private boolean enableSessionCreation;
   private boolean unencryptedNullCipherEnabled;
   private Set NULL_CIPHERS;
   private Set ANON_CIPHERS;

   public JaSSLParameters(JaSSLParameters other) {
      this.enabledCipherSuites = new String[0];
      this.enabledProtocols = new String[0];
      this.needClientAuth = false;
      this.wantClientAuth = false;
      this.useClientMode = true;
      this.enableSessionCreation = true;
      this.unencryptedNullCipherEnabled = false;
      this.NULL_CIPHERS = new HashSet();
      this.ANON_CIPHERS = new HashSet();
      if (null == other) {
         throw new IllegalArgumentException("Expected non-null SSLParameters.");
      } else {
         this.setEnabledCipherSuites(other.enabledCipherSuites);
         this.setEnabledProtocols(other.enabledProtocols);
         this.setWantClientAuth(other.wantClientAuth);
         this.setNeedClientAuth(other.needClientAuth);
         this.setUseClientMode(other.useClientMode);
         this.setEnableSessionCreation(other.enableSessionCreation);
      }
   }

   public JaSSLParameters(SSLContext context) {
      this(context, true);
   }

   public JaSSLParameters(SSLContext context, boolean useClientMode) {
      this.enabledCipherSuites = new String[0];
      this.enabledProtocols = new String[0];
      this.needClientAuth = false;
      this.wantClientAuth = false;
      this.useClientMode = true;
      this.enableSessionCreation = true;
      this.unencryptedNullCipherEnabled = false;
      this.NULL_CIPHERS = new HashSet();
      this.ANON_CIPHERS = new HashSet();
      if (null == context) {
         throw new IllegalArgumentException("Expected non-null javax.net.ssl.SSLContext.");
      } else {
         SSLEngine tempSslEngine = context.createSSLEngine();
         this.setEnabledCipherSuites(tempSslEngine.getEnabledCipherSuites());
         this.setEnabledProtocols(tempSslEngine.getEnabledProtocols());
         this.setWantClientAuth(tempSslEngine.getWantClientAuth());
         this.setNeedClientAuth(tempSslEngine.getNeedClientAuth());
         this.setUseClientMode(useClientMode);
      }
   }

   public String[] getEnabledCipherSuites() {
      return cloneArray(this.enabledCipherSuites);
   }

   public void setEnabledCipherSuites(String[] suites) {
      this.enabledCipherSuites = cloneArray(suites);
   }

   public String[] getEnabledProtocols() {
      return cloneArray(this.enabledProtocols);
   }

   public void setEnabledProtocols(String[] protocols) {
      this.enabledProtocols = cloneArray(protocols);
   }

   public void setNeedClientAuth(boolean b) {
      this.needClientAuth = b;
   }

   public boolean getNeedClientAuth() {
      return this.needClientAuth;
   }

   public void setWantClientAuth(boolean b) {
      this.wantClientAuth = b;
   }

   public boolean getWantClientAuth() {
      return this.wantClientAuth;
   }

   public void setUseClientMode(boolean b) {
      this.useClientMode = b;
   }

   public boolean getUseClientMode() {
      return this.useClientMode;
   }

   public void setEnableSessionCreation(boolean b) {
      this.enableSessionCreation = b;
   }

   public boolean getEnableSessionCreation() {
      return this.enableSessionCreation;
   }

   public void configureSslEngine(SSLEngine sslEngine) {
      if (null == sslEngine) {
         throw new IllegalArgumentException("Expected non-null SSLEngine.");
      } else {
         String[] cipherSuites = this.unencryptedNullCipherEnabled ? JaSSLSupport.combineCiphers(this.getEnabledCipherSuites(), this.getNullCiphers(sslEngine)) : this.getEnabledCipherSuites();
         if (JaSSLSupport.isAnonymousCipherAllowed()) {
            cipherSuites = JaSSLSupport.combineCiphers(cipherSuites, this.getAnonymousCiphers(sslEngine));
         }

         sslEngine.setEnabledCipherSuites(cipherSuites);
         if (this.getUseClientMode() && JaSSLSupport.IS_JDK_TLS_CLIENT_PROTOCOLS_CONFIGURED) {
            if (JaLogger.isLoggable(Level.CONFIG)) {
               JaLogger.log(Level.CONFIG, JaLogger.Component.SSLENGINE, "Client mode SSLEngine's TLS protocol(s) is configured based on the system property -Djdk.tls.client.protocols");
            }
         } else {
            sslEngine.setEnabledProtocols(this.getEnabledProtocols());
         }

         sslEngine.setEnableSessionCreation(this.getEnableSessionCreation());
         sslEngine.setUseClientMode(this.getUseClientMode());
         sslEngine.setWantClientAuth(this.getWantClientAuth());
         sslEngine.setNeedClientAuth(this.getNeedClientAuth());
         ALPNRuntimeSupport.setHandshakeApplicationProtocolSelector(sslEngine, (serverEngine, clientProtocols) -> {
            return ALPNRuntimeSupport.chooseApplicationProtocol(clientProtocols, ALPNRuntimeSupport.SUPPORTED_APPLICATION_PROTOCOLS);
         });
      }
   }

   private static String[] cloneArray(String[] source) {
      if (null == source) {
         return source;
      } else {
         String[] result = new String[source.length];
         System.arraycopy(source, 0, result, 0, source.length);
         return result;
      }
   }

   void setUnencryptedNullCipherEnabled(boolean unencryptedNullCipherEnabled) {
      this.unencryptedNullCipherEnabled = unencryptedNullCipherEnabled;
   }

   synchronized String[] getNullCiphers(SSLEngine sslEng) {
      if (sslEng != null && this.NULL_CIPHERS.size() == 0) {
         this.selectCiphers(sslEng, "_NULL_", this.NULL_CIPHERS);
      }

      String[] t = new String[this.NULL_CIPHERS.size()];
      return (String[])this.NULL_CIPHERS.toArray(t);
   }

   synchronized String[] getAnonymousCiphers(SSLEngine sslEng) {
      if (sslEng != null && this.ANON_CIPHERS.size() == 0) {
         this.selectCiphers(sslEng, "_anon_", this.ANON_CIPHERS);
      }

      String[] t = new String[this.ANON_CIPHERS.size()];
      return (String[])this.ANON_CIPHERS.toArray(t);
   }

   private void selectCiphers(SSLEngine sslEng, String searchStr, Set result) {
      if (sslEng instanceof JaSSLEngine) {
         sslEng = ((JaSSLEngine)sslEng).getDelegate();
      }

      String[] supportedCiphers = sslEng.getSupportedCipherSuites();
      String[] var5 = supportedCiphers;
      int var6 = supportedCiphers.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String s = var5[var7];
         if (s.toUpperCase().indexOf(searchStr.toUpperCase()) > -1) {
            result.add(s);
         }
      }

   }
}
