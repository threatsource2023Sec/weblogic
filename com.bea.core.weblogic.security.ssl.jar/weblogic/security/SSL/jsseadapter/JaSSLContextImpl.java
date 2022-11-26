package weblogic.security.SSL.jsseadapter;

import com.bea.security.utils.ssl.SSLContextProtocolSelector;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import weblogic.security.SSL.SSLEngineFactory;
import weblogic.security.SSL.WeblogicSSLEngine;
import weblogic.security.utils.SSLHostnameVerifier;
import weblogic.security.utils.SSLTrustValidator;
import weblogic.security.utils.SSLTruster;

public final class JaSSLContextImpl extends JaSSLContext {
   private String specifiedProviderName;
   private Provider specifiedProvider;
   private SecureRandom secureRandom;
   private SSLContext sslContext;
   private int protocolVersion;
   private String minimumTLSProtocolVersion;
   private boolean sSLv2HelloEnabled;
   private static final String SSLCONTEXT_PROTOCOL = SSLContextProtocolSelector.getSSLContextProtocol();
   private volatile SSLTruster truster;
   private volatile SSLHostnameVerifier hostnameVerifier;
   private int enforceConstraintsLevel;
   private boolean enableUnencryptedNullCipher;
   private int exportRefreshCount;
   private Vector trustedCAs = new Vector();
   private PrivateKey identityPrivateKey;
   private X509Certificate[] certChain;
   private String keyStoreType;
   private Map enabledProtocolsCache = new ConcurrentHashMap(2);

   public void addTrustedCA(X509Certificate trustedCACert) throws CertificateException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "addTrustedCA called.");
      }

      this.trustedCAs.add(trustedCACert);
   }

   public X509Certificate[] getTrustedCAs() {
      X509Certificate[] certs = new X509Certificate[this.trustedCAs.size()];
      return (X509Certificate[])this.trustedCAs.toArray(certs);
   }

   public PrivateKey inputPrivateKey(InputStream keyStream, char[] password) throws KeyManagementException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "inputPrivateKey called.");
      }

      return JaSSLSupport.getLocalIdentityPrivateKey(keyStream, password);
   }

   public X509Certificate[] inputCertChain(InputStream certStream) throws KeyManagementException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "inputCertChain called.");
      }

      String streamContent = null;
      byte[] certBytes = null;
      Collection certs = new ArrayList();

      try {
         certBytes = JaSSLSupport.readFully(certStream);
         if (certBytes != null) {
            streamContent = new String(certBytes);
         }
      } catch (IOException var11) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, var11, "Error reading data from from the certificate inputstream: " + var11.getMessage());
         }
      }

      if (streamContent != null) {
         String certEntryPattern = "-----BEGIN CERTIFICATE-----.+?-----END CERTIFICATE-----";
         Matcher m = Pattern.compile("-----BEGIN CERTIFICATE-----.+?-----END CERTIFICATE-----", 32).matcher(streamContent);
         CertificateFactory certificateFactory = null;

         try {
            certificateFactory = CertificateFactory.getInstance("X.509");

            int g;
            for(g = 0; m.find(); ++g) {
               String certEntry = m.group();
               ((Collection)certs).add(certificateFactory.generateCertificate(new ByteArrayInputStream(certEntry.getBytes())));
            }

            boolean certFound = g > 0;
            if (!certFound) {
               certs = certificateFactory.generateCertificates(new ByteArrayInputStream(certBytes));
            }
         } catch (CertificateException var10) {
            if (JaLogger.isLoggable(Level.SEVERE)) {
               JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, var10, "Error retrieving certifcate(s) from inputstream: " + var10.getMessage());
            }
         }
      }

      return (X509Certificate[])((X509Certificate[])((Collection)certs).toArray(new X509Certificate[((Collection)certs).size()]));
   }

   public void loadLocalIdentity(InputStream identityStream, char[] password) throws KeyManagementException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "loadLocalIdentity called, ignored by JSSE for now.");
      }

   }

   public void loadTrustedCerts(InputStream certStream) throws CertificateException, KeyManagementException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "loadTrustedCerts called.");
      }

      X509Certificate[] certs = this.inputCertChain(certStream);
      X509Certificate[] var3 = certs;
      int var4 = certs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         X509Certificate c = var3[var5];
         this.trustedCAs.add(c);
      }

   }

   public void addIdentity(X509Certificate[] certificateChain, PrivateKey privateKey) {
      this.addIdentity(certificateChain, privateKey, (String)null);
   }

   public void addIdentity(X509Certificate[] certificateChain, PrivateKey privateKey, String keyStoreType) {
      this.identityPrivateKey = privateKey;
      this.certChain = certificateChain;
      this.keyStoreType = keyStoreType;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "addIdentity called.");
      }

   }

   public boolean doKeysMatch(PublicKey publicKey, PrivateKey privateKey) throws KeyManagementException {
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "doKeysMatch called.");
      }

      return true;
   }

   public void setExportRefreshCount(int count) {
      this.exportRefreshCount = count;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set exportRefreshCount to {0}.", count);
      }

   }

   public void setProtocolVersion(int version) throws IllegalArgumentException {
      this.protocolVersion = version;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set protocolVersion to {0}.", this.protocolVersion);
      }

   }

   public SSLServerSocketFactory getSSLServerSocketFactory() {
      return new JaSSLServerSocketFactory(this, false);
   }

   public SSLSocketFactory getSSLSocketFactory() {
      return this.getConfiguredSSLSocketFactory();
   }

   public void setTrustManager(SSLTruster truster) {
      this.truster = truster;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set weblogic.security.utils.SSLTruster to {0}.", truster);
      }

   }

   public SSLTruster getTrustManager() {
      return this.truster;
   }

   public void setHostnameVerifier(SSLHostnameVerifier verifier) {
      this.hostnameVerifier = verifier;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set weblogic.security.utils.SSLHostnameVerifier to {0}.", verifier);
      }

   }

   public SSLHostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public void enforceConstraints(int value) {
      this.enforceConstraintsLevel = value;
      JaSSLSupport.setX509BasicConstraintsStrict(2 == value || 3 == value);
      JaSSLSupport.setNoV1CAs(4 == value || 3 == value);
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set enforceConstraints level to {0}.", value);
      }

   }

   public boolean isJsseEnabled() {
      return true;
   }

   public void setSendEmptyCertRequest(boolean enabled) {
   }

   public String getDescription() {
      return "JSSE SSL (default strength)";
   }

   public Class getListenerThreadFactoryClass() {
      return null;
   }

   public void enableUnencryptedNullCipher(boolean enable) {
      this.enableUnencryptedNullCipher = enable;
      JaSSLSupport.isUnEncrytedNullCipherAllowed();
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Set enableUnencryptedNullCipher to {0}.", enable);
      }

   }

   public boolean isUnencryptedNullCipherEnabled() {
      return this.enableUnencryptedNullCipher;
   }

   public SSLServerSocketFactory getSSLNioServerSocketFactory() {
      return new JaSSLServerSocketFactory(this, true);
   }

   public SSLSocketFactory getSSLNioSocketFactory() {
      return this.getSSLSocketFactory();
   }

   public JaSSLEngine createSSLEngine() throws SSLException {
      try {
         JaSSLEngine sslEngine = new JaSSLEngine(this, this.getSSLContext().createSSLEngine());
         this.configureSslEngine(sslEngine);
         return sslEngine;
      } catch (Exception var2) {
         throw new SSLException(var2);
      }
   }

   public JaSSLEngine createSSLEngine(String peerHost, int peerPort) throws SSLException {
      try {
         JaSSLEngine sslEngine = new JaSSLEngine(this, this.getSSLContext().createSSLEngine(peerHost, peerPort));
         this.configureSslEngine(sslEngine);
         return sslEngine;
      } catch (Exception var4) {
         throw new SSLException(var4);
      }
   }

   public WeblogicSSLEngine createSSLEngine(String peerHost, int peerPort, boolean useClientMode) throws SSLException {
      try {
         JaSSLEngine sslEngine = new JaSSLEngine(this, this.getSSLContext().createSSLEngine(peerHost, peerPort));
         this.configureSslEngine(sslEngine, useClientMode);
         return sslEngine;
      } catch (Exception var5) {
         throw new SSLException(var5);
      }
   }

   public String[] getDefaultCipherSuites() {
      SSLContext sslCtx = this.getSSLContext();
      SSLEngine tempEngine = sslCtx.createSSLEngine();
      return JaCipherSuiteNameMap.fromJsse(tempEngine.getEnabledCipherSuites());
   }

   public String[] getSupportedCipherSuites() {
      SSLContext sslCtx = this.getSSLContext();
      SSLEngine tempEngine = sslCtx.createSSLEngine();
      return JaCipherSuiteNameMap.fromJsse(tempEngine.getSupportedCipherSuites());
   }

   public String[] getDefaultProtocols() {
      SSLContext sslCtx = this.getSSLContext();
      SSLEngine tempEngine = sslCtx.createSSLEngine();
      return tempEngine.getEnabledProtocols();
   }

   public String[] getSupportedProtocols() {
      SSLContext sslCtx = this.getSSLContext();
      SSLEngine tempEngine = sslCtx.createSSLEngine();
      return tempEngine.getSupportedProtocols();
   }

   synchronized SSLContext getSSLContext() {
      if (null == this.sslContext) {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Expected SSLContext service protocol: {0}", SSLCONTEXT_PROTOCOL);
         }

         Provider provider = this.getSpecifiedProvider();

         try {
            SSLContext c;
            if (null == provider) {
               c = SSLContext.getInstance(SSLCONTEXT_PROTOCOL);
            } else {
               c = SSLContext.getInstance(SSLCONTEXT_PROTOCOL, provider);
            }

            this.initializeContext(c);
            this.sslContext = c;
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Got SSLContext, protocol={0}, provider={1}", c.getProtocol(), c.getProvider().getName());
            }
         } catch (Exception var3) {
            if (JaLogger.isLoggable(Level.FINE)) {
               JaLogger.log(Level.FINE, JaLogger.Component.SSLCONTEXT, var3, "Unable to construct SSLContext, protocol={0}, provider={1}", SSLCONTEXT_PROTOCOL, null == provider ? provider : provider.getName());
            }

            throw new RuntimeException(var3);
         }
      }

      return this.sslContext;
   }

   public void setMinimumTLSProtocolVersion(String minimumTLSProtocolVersion) {
      this.minimumTLSProtocolVersion = minimumTLSProtocolVersion;
   }

   public void setSSLv2HelloEnabled(boolean sSLv2HelloEnabled) {
      this.sSLv2HelloEnabled = sSLv2HelloEnabled;
   }

   synchronized String getSpecifiedProviderName() {
      return this.specifiedProviderName;
   }

   synchronized SecureRandom getSecureRandom() throws NoSuchAlgorithmException {
      if (null == this.secureRandom) {
         String algorithm = "SHA1PRNG";
         Provider provider = this.getSpecifiedProvider();

         try {
            SecureRandom sr;
            if (null == provider) {
               sr = SecureRandom.getInstance("SHA1PRNG");
            } else {
               sr = SecureRandom.getInstance("SHA1PRNG", provider);
            }

            this.secureRandom = sr;
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Got SecureRandom, algorithm={0}, provider={1}", "SHA1PRNG", sr.getProvider().getName());
            }
         } catch (RuntimeException var4) {
            this.log_getSecureRandom(var4, "SHA1PRNG", provider);
            throw var4;
         } catch (NoSuchAlgorithmException var5) {
            this.log_getSecureRandom(var5, "SHA1PRNG", provider);
            throw var5;
         }
      }

      return this.secureRandom;
   }

   private void log_getSecureRandom(Exception e, String algorithm, Provider provider) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLCONTEXT, e, "Unable to get SecureRandom, algorithm={0}, provider={1}", algorithm, null == provider ? provider : provider.getName());
      }

   }

   synchronized boolean hasSpecifiedProvider() {
      return null != this.getSpecifiedProviderName();
   }

   synchronized Provider getSpecifiedProvider() {
      if (!this.hasSpecifiedProvider()) {
         return null;
      } else {
         if (null == this.specifiedProvider) {
            String name = this.getSpecifiedProviderName();

            try {
               this.specifiedProvider = Security.getProvider(name);
               if (null == this.specifiedProvider) {
                  throw new IllegalArgumentException("Specified provider \"" + name + "\" has not been installed.");
               }

               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Got Provider, name={0}", name);
               }
            } catch (RuntimeException var3) {
               if (JaLogger.isLoggable(Level.FINE)) {
                  JaLogger.log(Level.FINE, JaLogger.Component.SSLCONTEXT, var3, "Unable to get specified provider, name={0}.", name);
               }

               throw var3;
            }
         }

         return this.specifiedProvider;
      }
   }

   private void log_getKeyStore(Exception e, String keyStoreType, Provider provider) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLCONTEXT, e, "Unable to get KeyStore, type={0}, provider={1}", keyStoreType, null == provider ? provider : provider.getName());
      }

   }

   private void log_getTrustStore(Exception e, String keyStoreType, Provider provider) {
      if (JaLogger.isLoggable(Level.FINE)) {
         JaLogger.log(Level.FINE, JaLogger.Component.SSLCONTEXT, e, "Unable to get TrustStore, type={0}, provider={1}", keyStoreType, null == provider ? provider : provider.getName());
      }

   }

   private void initializeContext(SSLContext sslC) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
      if (sslC != null) {
         X509Certificate[] CAList = new X509Certificate[this.trustedCAs.size()];
         this.trustedCAs.copyInto(CAList);
         TrustManager[] tms = new TrustManager[]{new JaTrustManager(CAList)};
         KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
         KeyStore identityKs = KeyStore.getInstance(this.keyStoreType != null && !"KSS".equalsIgnoreCase(this.keyStoreType) ? this.keyStoreType : KeyStore.getDefaultType());
         identityKs.load((InputStream)null, (char[])null);
         char[] pwd = null;
         if (null != this.identityPrivateKey) {
            String alias = Integer.toString(this.identityPrivateKey.hashCode());
            pwd = alias.toCharArray();
            identityKs.setKeyEntry(alias, this.identityPrivateKey, pwd, this.certChain);
         }

         kmf.init(identityKs, pwd);
         sslC.init(kmf.getKeyManagers(), tms, (SecureRandom)null);
      }

   }

   private void configureSslEngine(JaSSLEngine sslEngine) {
      this.configureSslEngine(sslEngine, true);
   }

   private void configureSslEngine(JaSSLEngine sslEngine, boolean useClientMode) {
      JaSSLParameters sslParameters = new JaSSLParameters(this.getSSLContext(), useClientMode);
      sslParameters.setUnencryptedNullCipherEnabled(this.isUnencryptedNullCipherEnabled());
      String key = this.minimumTLSProtocolVersion + "_" + (this.sSLv2HelloEnabled && !sslEngine.getUseClientMode());
      String[] enabledProtocols = (String[])this.enabledProtocolsCache.get(key);
      if (enabledProtocols == null) {
         enabledProtocols = JaSSLSupport.getEnabledProtocols(sslEngine.getSupportedProtocols(), this.minimumTLSProtocolVersion, this.sSLv2HelloEnabled && !sslEngine.getUseClientMode());
         if (enabledProtocols != null && enabledProtocols.length > 0) {
            this.enabledProtocolsCache.put(key, enabledProtocols);
         }
      }

      sslParameters.setEnabledProtocols(enabledProtocols);
      sslParameters.configureSslEngine(sslEngine);
      SSLTruster truster = this.getTrustManager();
      if (truster instanceof SSLTrustValidator) {
         SSLTrustValidator sslValidator = (SSLTrustValidator)truster;
         sslEngine.setNeedClientAuth(sslValidator.isPeerCertsRequired());
      }

   }

   private SSLSocketFactory getConfiguredSSLSocketFactory() {
      SSLEngineFactory sslEngineFactory = new JaSSLEngineFactoryImpl(this);

      try {
         Class socketFactory = Class.forName("weblogic.socket.JSSESocketFactory", false, this.getClass().getClassLoader());
         Method m = socketFactory.getMethod("getSSLSocketFactory", SSLEngineFactory.class);
         return (SSLSocketFactory)m.invoke(socketFactory, sslEngineFactory);
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }
}
