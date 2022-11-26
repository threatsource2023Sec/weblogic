package org.glassfish.grizzly.ssl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.Grizzly;

public class SSLEngineConfigurator implements SSLEngineFactory {
   private static final Logger LOGGER = Grizzly.logger(SSLEngineConfigurator.class);
   private final Object sync;
   protected volatile SSLContextConfigurator sslContextConfiguration;
   protected volatile SSLContext sslContext;
   protected String[] enabledCipherSuites;
   protected String[] enabledProtocols;
   protected boolean clientMode;
   protected boolean needClientAuth;
   protected boolean wantClientAuth;
   private boolean isProtocolConfigured;
   private boolean isCipherConfigured;

   public SSLEngineConfigurator(SSLContext sslContext) {
      this(sslContext, true, false, false);
   }

   public SSLEngineConfigurator(SSLContext sslContext, boolean clientMode, boolean needClientAuth, boolean wantClientAuth) {
      this.sync = new Object();
      this.enabledCipherSuites = null;
      this.enabledProtocols = null;
      this.isProtocolConfigured = false;
      this.isCipherConfigured = false;
      if (sslContext == null) {
         throw new IllegalArgumentException("SSLContext can not be null");
      } else {
         this.sslContextConfiguration = null;
         this.sslContext = sslContext;
         this.clientMode = clientMode;
         this.needClientAuth = needClientAuth;
         this.wantClientAuth = wantClientAuth;
      }
   }

   public SSLEngineConfigurator(SSLContextConfigurator sslContextConfiguration) {
      this(sslContextConfiguration, true, false, false);
   }

   public SSLEngineConfigurator(SSLContextConfigurator sslContextConfiguration, boolean clientMode, boolean needClientAuth, boolean wantClientAuth) {
      this.sync = new Object();
      this.enabledCipherSuites = null;
      this.enabledProtocols = null;
      this.isProtocolConfigured = false;
      this.isCipherConfigured = false;
      if (sslContextConfiguration == null) {
         throw new IllegalArgumentException("SSLContextConfigurator can not be null");
      } else {
         this.sslContextConfiguration = sslContextConfiguration;
         this.clientMode = clientMode;
         this.needClientAuth = needClientAuth;
         this.wantClientAuth = wantClientAuth;
      }
   }

   public SSLEngineConfigurator(SSLEngineConfigurator pattern) {
      this.sync = new Object();
      this.enabledCipherSuites = null;
      this.enabledProtocols = null;
      this.isProtocolConfigured = false;
      this.isCipherConfigured = false;
      this.sslContextConfiguration = pattern.sslContextConfiguration;
      this.sslContext = pattern.sslContext;
      this.clientMode = pattern.clientMode;
      this.needClientAuth = pattern.needClientAuth;
      this.wantClientAuth = pattern.wantClientAuth;
      this.enabledCipherSuites = pattern.enabledCipherSuites != null ? (String[])Arrays.copyOf(pattern.enabledCipherSuites, pattern.enabledCipherSuites.length) : null;
      this.enabledProtocols = pattern.enabledProtocols != null ? (String[])Arrays.copyOf(pattern.enabledProtocols, pattern.enabledProtocols.length) : null;
      this.isCipherConfigured = pattern.isCipherConfigured;
      this.isProtocolConfigured = pattern.isProtocolConfigured;
   }

   protected SSLEngineConfigurator() {
      this.sync = new Object();
      this.enabledCipherSuites = null;
      this.enabledProtocols = null;
      this.isProtocolConfigured = false;
      this.isCipherConfigured = false;
   }

   public SSLEngine createSSLEngine() {
      return this.createSSLEngine((String)null, -1);
   }

   public SSLEngine createSSLEngine(String peerHost, int peerPort) {
      if (this.sslContext == null) {
         synchronized(this.sync) {
            if (this.sslContext == null) {
               this.sslContext = this.sslContextConfiguration.createSSLContext(true);
            }
         }
      }

      SSLEngine sslEngine = this.sslContext.createSSLEngine(peerHost, peerPort);
      this.configure(sslEngine);
      return sslEngine;
   }

   public SSLEngine configure(SSLEngine sslEngine) {
      if (this.enabledCipherSuites != null) {
         if (!this.isCipherConfigured) {
            this.enabledCipherSuites = configureEnabledCiphers(sslEngine, this.enabledCipherSuites);
            this.isCipherConfigured = true;
         }

         sslEngine.setEnabledCipherSuites(this.enabledCipherSuites);
      }

      if (this.enabledProtocols != null) {
         if (!this.isProtocolConfigured) {
            this.enabledProtocols = configureEnabledProtocols(sslEngine, this.enabledProtocols);
            this.isProtocolConfigured = true;
         }

         sslEngine.setEnabledProtocols(this.enabledProtocols);
      }

      sslEngine.setUseClientMode(this.clientMode);
      if (this.wantClientAuth) {
         sslEngine.setWantClientAuth(this.wantClientAuth);
      }

      if (this.needClientAuth) {
         sslEngine.setNeedClientAuth(this.needClientAuth);
      }

      return sslEngine;
   }

   public boolean isClientMode() {
      return this.clientMode;
   }

   public SSLEngineConfigurator setClientMode(boolean clientMode) {
      this.clientMode = clientMode;
      return this;
   }

   public boolean isNeedClientAuth() {
      return this.needClientAuth;
   }

   public SSLEngineConfigurator setNeedClientAuth(boolean needClientAuth) {
      this.needClientAuth = needClientAuth;
      return this;
   }

   public boolean isWantClientAuth() {
      return this.wantClientAuth;
   }

   public SSLEngineConfigurator setWantClientAuth(boolean wantClientAuth) {
      this.wantClientAuth = wantClientAuth;
      return this;
   }

   public String[] getEnabledCipherSuites() {
      return this.enabledCipherSuites != null ? (String[])Arrays.copyOf(this.enabledCipherSuites, this.enabledCipherSuites.length) : null;
   }

   public SSLEngineConfigurator setEnabledCipherSuites(String[] enabledCipherSuites) {
      this.enabledCipherSuites = enabledCipherSuites != null ? (String[])Arrays.copyOf(enabledCipherSuites, enabledCipherSuites.length) : null;
      return this;
   }

   public String[] getEnabledProtocols() {
      return this.enabledProtocols != null ? (String[])Arrays.copyOf(this.enabledProtocols, this.enabledProtocols.length) : null;
   }

   public SSLEngineConfigurator setEnabledProtocols(String[] enabledProtocols) {
      this.enabledProtocols = enabledProtocols != null ? (String[])Arrays.copyOf(enabledProtocols, enabledProtocols.length) : null;
      return this;
   }

   public boolean isCipherConfigured() {
      return this.isCipherConfigured;
   }

   public SSLEngineConfigurator setCipherConfigured(boolean isCipherConfigured) {
      this.isCipherConfigured = isCipherConfigured;
      return this;
   }

   public boolean isProtocolConfigured() {
      return this.isProtocolConfigured;
   }

   public SSLEngineConfigurator setProtocolConfigured(boolean isProtocolConfigured) {
      this.isProtocolConfigured = isProtocolConfigured;
      return this;
   }

   public SSLContext getSslContext() {
      if (this.sslContext == null) {
         synchronized(this.sync) {
            if (this.sslContext == null) {
               this.sslContext = this.sslContextConfiguration.createSSLContext(true);
            }
         }
      }

      return this.sslContext;
   }

   private static String[] configureEnabledProtocols(SSLEngine sslEngine, String[] requestedProtocols) {
      String[] supportedProtocols = sslEngine.getSupportedProtocols();
      String[] protocols = null;
      ArrayList list = null;
      String[] var5 = supportedProtocols;
      int var6 = supportedProtocols.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String supportedProtocol = var5[var7];
         String[] var9 = requestedProtocols;
         int var10 = requestedProtocols.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            String protocol = var9[var11];
            protocol = protocol.trim();
            if (supportedProtocol.equals(protocol)) {
               if (list == null) {
                  list = new ArrayList();
               }

               list.add(protocol);
               break;
            }
         }
      }

      if (list != null) {
         protocols = (String[])list.toArray(new String[list.size()]);
      }

      return protocols;
   }

   private static String[] configureEnabledCiphers(SSLEngine sslEngine, String[] requestedCiphers) {
      String[] supportedCiphers = sslEngine.getSupportedCipherSuites();
      String[] ciphers = null;
      ArrayList list = null;
      String[] var5 = supportedCiphers;
      int var6 = supportedCiphers.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String supportedCipher = var5[var7];
         String[] var9 = requestedCiphers;
         int var10 = requestedCiphers.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            String cipher = var9[var11];
            cipher = cipher.trim();
            if (supportedCipher.equals(cipher)) {
               if (list == null) {
                  list = new ArrayList();
               }

               list.add(cipher);
               break;
            }
         }
      }

      if (list != null) {
         ciphers = (String[])list.toArray(new String[list.size()]);
      }

      return ciphers;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("SSLEngineConfigurator");
      sb.append("{clientMode=").append(this.clientMode);
      sb.append(", enabledCipherSuites=").append(this.enabledCipherSuites == null ? "null" : Arrays.asList(this.enabledCipherSuites).toString());
      sb.append(", enabledProtocols=").append(this.enabledProtocols == null ? "null" : Arrays.asList(this.enabledProtocols).toString());
      sb.append(", needClientAuth=").append(this.needClientAuth);
      sb.append(", wantClientAuth=").append(this.wantClientAuth);
      sb.append(", isProtocolConfigured=").append(this.isProtocolConfigured);
      sb.append(", isCipherConfigured=").append(this.isCipherConfigured);
      sb.append('}');
      return sb.toString();
   }

   public SSLEngineConfigurator copy() {
      return new SSLEngineConfigurator(this);
   }
}
