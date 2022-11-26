package org.glassfish.tyrus.container.jdk.client;

import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/** @deprecated */
public class SslEngineConfigurator {
   private final Object sync;
   protected volatile SslContextConfigurator sslContextConfiguration;
   protected volatile SSLContext sslContext;
   protected String[] enabledCipherSuites;
   protected String[] enabledProtocols;
   protected boolean clientMode;
   protected boolean needClientAuth;
   protected boolean wantClientAuth;
   private boolean isProtocolConfigured;
   private boolean isCipherConfigured;

   public SslEngineConfigurator(SSLContext sslContext) {
      this(sslContext, true, false, false);
   }

   public SslEngineConfigurator(SSLContext sslContext, boolean clientMode, boolean needClientAuth, boolean wantClientAuth) {
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

   public SslEngineConfigurator(SslContextConfigurator sslContextConfiguration) {
      this(sslContextConfiguration, true, false, false);
   }

   public SslEngineConfigurator(SslContextConfigurator sslContextConfiguration, boolean clientMode, boolean needClientAuth, boolean wantClientAuth) {
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

   public SslEngineConfigurator(SslEngineConfigurator pattern) {
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
      this.enabledCipherSuites = pattern.enabledCipherSuites;
      this.enabledProtocols = pattern.enabledProtocols;
      this.isCipherConfigured = pattern.isCipherConfigured;
      this.isProtocolConfigured = pattern.isProtocolConfigured;
   }

   protected SslEngineConfigurator() {
      this.sync = new Object();
      this.enabledCipherSuites = null;
      this.enabledProtocols = null;
      this.isProtocolConfigured = false;
      this.isCipherConfigured = false;
   }

   public SSLEngine createSSLEngine() {
      if (this.sslContext == null) {
         synchronized(this.sync) {
            if (this.sslContext == null) {
               this.sslContext = this.sslContextConfiguration.createSSLContext();
            }
         }
      }

      SSLEngine sslEngine = this.sslContext.createSSLEngine();
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
         sslEngine.setWantClientAuth(true);
      }

      if (this.needClientAuth) {
         sslEngine.setNeedClientAuth(true);
      }

      return sslEngine;
   }

   public boolean isClientMode() {
      return this.clientMode;
   }

   public SslEngineConfigurator setClientMode(boolean clientMode) {
      this.clientMode = clientMode;
      return this;
   }

   public boolean isNeedClientAuth() {
      return this.needClientAuth;
   }

   public SslEngineConfigurator setNeedClientAuth(boolean needClientAuth) {
      this.needClientAuth = needClientAuth;
      return this;
   }

   public boolean isWantClientAuth() {
      return this.wantClientAuth;
   }

   public SslEngineConfigurator setWantClientAuth(boolean wantClientAuth) {
      this.wantClientAuth = wantClientAuth;
      return this;
   }

   public String[] getEnabledCipherSuites() {
      return (String[])this.enabledCipherSuites.clone();
   }

   public SslEngineConfigurator setEnabledCipherSuites(String[] enabledCipherSuites) {
      this.enabledCipherSuites = (String[])enabledCipherSuites.clone();
      return this;
   }

   public String[] getEnabledProtocols() {
      return (String[])this.enabledProtocols.clone();
   }

   public SslEngineConfigurator setEnabledProtocols(String[] enabledProtocols) {
      this.enabledProtocols = (String[])enabledProtocols.clone();
      return this;
   }

   public boolean isCipherConfigured() {
      return this.isCipherConfigured;
   }

   public SslEngineConfigurator setCipherConfigured(boolean isCipherConfigured) {
      this.isCipherConfigured = isCipherConfigured;
      return this;
   }

   public boolean isProtocolConfigured() {
      return this.isProtocolConfigured;
   }

   public SslEngineConfigurator setProtocolConfigured(boolean isProtocolConfigured) {
      this.isProtocolConfigured = isProtocolConfigured;
      return this;
   }

   public SSLContext getSslContext() {
      if (this.sslContext == null) {
         synchronized(this.sync) {
            if (this.sslContext == null) {
               this.sslContext = this.sslContextConfiguration.createSSLContext();
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

   public SslEngineConfigurator copy() {
      return new SslEngineConfigurator(this);
   }
}
