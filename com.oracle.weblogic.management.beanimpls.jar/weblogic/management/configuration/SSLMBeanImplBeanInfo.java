package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SSLMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SSLMBean.class;

   public SSLMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SSLMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SSLMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents the configuration of the SSL protocol.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SSLMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("CertAuthenticator")) {
         getterName = "getCertAuthenticator";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCertAuthenticator";
         }

         currentResult = new PropertyDescriptor("CertAuthenticator", SSLMBean.class, getterName, setterName);
         descriptors.put("CertAuthenticator", currentResult);
         currentResult.setValue("description", "<p>The name of the Java class that implements the <code>weblogic.security.acl.CertAuthenticator</code> class, which is deprecated in this release of WebLogic Server. This field is for Compatibility security only, and is only used when the Realm Adapter Authentication provider is configured.</p>  <p>The <code>weblogic.security.acl.CertAuthenticator</code> class maps the digital certificate of a client to a WebLogic Server user. The class has an <code>authenticate()</code> method that WebLogic Server calls after validating the digital certificate presented by the client.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("CertificateCacheSize")) {
         getterName = "getCertificateCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCertificateCacheSize";
         }

         currentResult = new PropertyDescriptor("CertificateCacheSize", SSLMBean.class, getterName, setterName);
         descriptors.put("CertificateCacheSize", currentResult);
         currentResult.setValue("description", "<p>The number of certificates held that have not been redeemed by tokens.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("Ciphersuites")) {
         getterName = "getCiphersuites";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCiphersuites";
         }

         currentResult = new PropertyDescriptor("Ciphersuites", SSLMBean.class, getterName, setterName);
         descriptors.put("Ciphersuites", currentResult);
         currentResult.setValue("description", "<p>Indicates the cipher suites being used on a particular WebLogic Server.</p>  <p>The strongest negotiated cipher suite is chosen during the SSL handshake. The set of cipher suites used by default by JSEE depends on the specific JDK version with which WebLogic Server is configured.</p>  <p>For a list of possible values, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=SECMG502\" rel=\"noopener noreferrer\" target=\"_blank\">Cipher Suites</a>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ClientCertAlias")) {
         getterName = "getClientCertAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertAlias";
         }

         currentResult = new PropertyDescriptor("ClientCertAlias", SSLMBean.class, getterName, setterName);
         descriptors.put("ClientCertAlias", currentResult);
         currentResult.setValue("description", "Determines the alias of the client SSL certificate to be used as identity for outbound SSL connections. The certificate is assumed to be stored in the server configured keystore. <p/> Note that to use the client SSL certificate, <code>{@link #setUseClientCertForOutbound}</code> must be enabled. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setClientCertAlias"), BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertPrivateKeyPassPhrase")) {
         getterName = "getClientCertPrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertPrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("ClientCertPrivateKeyPassPhrase", SSLMBean.class, getterName, setterName);
         descriptors.put("ClientCertPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the private key for the client SSL certificate specified in <code>{@link #getClientCertAlias}</code> from the server configured keystore. This passphrase is assigned to the private key when the private key is generated.</p>  <p>Note that this attribute is usually used when outbound SSL connections specify a client SSL certificate identity.</p>  <p>Note that when you get the value of this attribute, WebLogic Server does the following:</p>  <ol> <li>Retrieves the value of the <code>ClientCertPrivateKeyPassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted passphrase.</li> </ol> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setClientCertPrivateKeyPassPhrase"), BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertAlias")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertPrivateKeyPassPhraseEncrypted")) {
         getterName = "getClientCertPrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertPrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("ClientCertPrivateKeyPassPhraseEncrypted", SSLMBean.class, getterName, setterName);
         descriptors.put("ClientCertPrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the private key for the client SSL certificate specified in <code>{@link #getClientCertAlias}</code> from the server configured keystore. This passphrase is assigned to the private key when the private key is generated.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute, and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p>  <p>Note that this attribute is usually used when outbound SSL connections specify a client SSL certificate identity.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setClientCertPrivateKeyPassPhraseEncrypted"), BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertAlias"), BeanInfoHelper.encodeEntities("#getClientCertPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExcludedCiphersuites")) {
         getterName = "getExcludedCiphersuites";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludedCiphersuites";
         }

         currentResult = new PropertyDescriptor("ExcludedCiphersuites", SSLMBean.class, getterName, setterName);
         descriptors.put("ExcludedCiphersuites", currentResult);
         currentResult.setValue("description", "<p>List of cipher suites not to be used by WebLogic Server.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setExcludedCiphersuites()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExportKeyLifespan")) {
         getterName = "getExportKeyLifespan";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExportKeyLifespan";
         }

         currentResult = new PropertyDescriptor("ExportKeyLifespan", SSLMBean.class, getterName, setterName);
         descriptors.put("ExportKeyLifespan", currentResult);
         currentResult.setValue("description", "<p>Indicates the number of times WebLogic Server can use an exportable key between a domestic server and an exportable client before generating a new key. The more secure you want WebLogic Server to be, the fewer times the key should be used before generating a new key.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(500));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostnameVerifier")) {
         getterName = "getHostnameVerifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerifier";
         }

         currentResult = new PropertyDescriptor("HostnameVerifier", SSLMBean.class, getterName, setterName);
         descriptors.put("HostnameVerifier", currentResult);
         currentResult.setValue("description", "<p>The name of the class that implements the <code>weblogic.security.SSL.HostnameVerifier</code> interface.</p>  <p>This class verifies whether the connection to the host with the hostname from URL should be allowed. The class is used to prevent man-in-the-middle attacks. The <code>weblogic.security.SSL.HostnameVerifier</code> has a <code>verify()</code> method that WebLogic Server calls on the client during the SSL handshake.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityAndTrustLocations")) {
         getterName = "getIdentityAndTrustLocations";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityAndTrustLocations";
         }

         currentResult = new PropertyDescriptor("IdentityAndTrustLocations", SSLMBean.class, getterName, setterName);
         descriptors.put("IdentityAndTrustLocations", currentResult);
         currentResult.setValue("description", "<p>Indicates where SSL should find the server's identity (certificate and private key) as well as the server's trust (trusted CAs).</p>  <ul> <li> <p>If set to <code>KEYSTORES</code>, then SSL retrieves the identity and trust from the server's keystores (that are configured on the Server).</p> </li>  <li> <p>If set to <code>FILES_OR_KEYSTORE_PROVIDERS</code>, then SSL first looks in the deprecated KeyStore providers for the identity and trust. If not found, then it looks in the flat files indicated by the SSL Trusted CA File Name, Server Certificate File Name, and Server Key File Name attributes.</p> </li> </ul>  <p>Domains created in WebLogic Server version 8.1 or later, default to <code>KEYSTORES</code>. Domains created before WebLogic Server version 8.1, default to <code>FILES_OR_KEYSTORE_PROVIDERS.</code></p> ");
         setPropertyDescriptorDefault(currentResult, "KeyStores");
         currentResult.setValue("legalValues", new Object[]{"KeyStores", "FilesOrKeyStoreProviders"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InboundCertificateValidation")) {
         getterName = "getInboundCertificateValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInboundCertificateValidation";
         }

         currentResult = new PropertyDescriptor("InboundCertificateValidation", SSLMBean.class, getterName, setterName);
         descriptors.put("InboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the client certificate validation rules for inbound SSL.</p>  <p>This attribute only applies to ports and network channels using 2-way SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, "BuiltinSSLValidationOnly");
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", SSLMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The TCP/IP port at which this server listens for SSL connection requests.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getListenPort()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getAdministrationPort()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkAccessPointMBean#getListenPort()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(7002));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeoutMillis")) {
         getterName = "getLoginTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("LoginTimeoutMillis", SSLMBean.class, getterName, setterName);
         descriptors.put("LoginTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of milliseconds that WebLogic Server waits for an SSL connection before timing out. SSL connections take longer to negotiate than regular connections.</p>  <p>If clients are connecting over the Internet, raise the default number to accommodate additional network latency.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getLoginTimeoutMillis()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean#getLoginTimeoutMillisSSL()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(25000));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinimumTLSProtocolVersion")) {
         getterName = "getMinimumTLSProtocolVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinimumTLSProtocolVersion";
         }

         currentResult = new PropertyDescriptor("MinimumTLSProtocolVersion", SSLMBean.class, getterName, setterName);
         descriptors.put("MinimumTLSProtocolVersion", currentResult);
         currentResult.setValue("description", "Get the minimum SSL/TLS protocol version currently configured. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMinimumTLSProtocolVersion(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SSLMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("OutboundCertificateValidation")) {
         getterName = "getOutboundCertificateValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundCertificateValidation";
         }

         currentResult = new PropertyDescriptor("OutboundCertificateValidation", SSLMBean.class, getterName, setterName);
         descriptors.put("OutboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the server certificate validation rules for outbound SSL.</p>  <p>This attribute always applies to outbound SSL that is part of WebLogic Server (that is, an Administration Server talking to the Node Manager). It does not apply to application code in the server that is using outbound SSL unless the application code uses a <code>weblogic.security.SSL.ServerTrustManager</code> that is configured to use outbound SSL validation.</p> ");
         setPropertyDescriptorDefault(currentResult, "BuiltinSSLValidationOnly");
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyAlias")) {
         getterName = "getOutboundPrivateKeyAlias";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundPrivateKeyAlias", SSLMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the outbound private key in the keystore. This private key is associated with either a server or a client digital certificate. This attribute value is derived from other settings and cannot be physically set.</p>  <p>The returned value is determined as follows:</p> <ul> <li>If the <code>{@link #isUseClientCertForOutbound}</code> returns true, the value from <code>{@link #getClientCertAlias}</code> is returned.</li> <li> Otherwise, the value from <code>{@link #getServerPrivateKeyAlias}</code> is returned.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertAlias"), BeanInfoHelper.encodeEntities("#getServerPrivateKeyAlias")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyPassPhrase")) {
         getterName = "getOutboundPrivateKeyPassPhrase";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundPrivateKeyPassPhrase", SSLMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the outbound private key from the keystore. This passphrase is assigned to the private key when it is generated. This attribute value is derived from other settings and cannot be physically set.</p>  <p>The returned value is determined as follows:</p> <ul> <li>If the <code>{@link #isUseClientCertForOutbound}</code> returns true, the value from <code>{@link #getClientCertPrivateKeyPassPhrase}</code> is returned.</li> <li> Otherwise, the value from <code>{@link #getServerPrivateKeyPassPhrase}</code> is returned.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertPrivateKeyPassPhrase"), BeanInfoHelper.encodeEntities("#getServerPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyPassPhraseEncrypted")) {
         getterName = "getOutboundPrivateKeyPassPhraseEncrypted";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundPrivateKeyPassPhraseEncrypted", SSLMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the encrypted outbound private key from the keystore. This passphrase is assigned to the private key when it is generated. This attribute value is derived from other settings and cannot be physically set.</p>  <p>The returned value is determined as follows:</p> <ul> <li>If the <code>{@link #isUseClientCertForOutbound}</code> returns true, the value from <code>{@link #getClientCertPrivateKeyPassPhraseEncrypted}</code> is returned.</li> <li> Otherwise, the value from <code>{@link #getServerPrivateKeyPassPhraseEncrypted}</code> is returned.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getClientCertPrivateKeyPassPhraseEncrypted"), BeanInfoHelper.encodeEntities("#isUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertPrivateKeyPassPhrase"), BeanInfoHelper.encodeEntities("#getServerPrivateKeyPassPhraseEncrypted"), BeanInfoHelper.encodeEntities("#getServerPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerCertificateChainFileName")) {
         getterName = "getServerCertificateChainFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerCertificateChainFileName";
         }

         currentResult = new PropertyDescriptor("ServerCertificateChainFileName", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerCertificateChainFileName", currentResult);
         currentResult.setValue("description", "<p>The full directory location and name of the file containing an ordered list of certificate authorities trusted by WebLogic Server.</p>  <p>The <code>.pem</code> file extension indicates that method that should be used to read the file. Note that as of WebLogic Server version 7.0, the digital certificate for WebLogic Server should not be stored in a file.</p> ");
         setPropertyDescriptorDefault(currentResult, "server-certchain.pem");
         currentResult.setValue("deprecated", "7.0.0.0 server certificates (and chains) should be stored in keystores. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerCertificateFileName")) {
         getterName = "getServerCertificateFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerCertificateFileName";
         }

         currentResult = new PropertyDescriptor("ServerCertificateFileName", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerCertificateFileName", currentResult);
         currentResult.setValue("description", "<p>The full directory location of the digital certificate file (<code>.der</code> or <code>.pem</code>) for the server.</p>  <p>The pathname should either be absolute or relative to the directory from which the server is booted. This field provides backward compatibility for security configurations that stored digital certificates in files.</p>  <p>The file extension ( <code>.der</code> or <code>.pem</code>) tells WebLogic Server how to read the contents of the file.</p> ");
         setPropertyDescriptorDefault(currentResult, "server-cert.der");
         currentResult.setValue("deprecated", "8.1.0.0 server certificates (and chains) should be stored in keystores. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerKeyFileName")) {
         getterName = "getServerKeyFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerKeyFileName";
         }

         currentResult = new PropertyDescriptor("ServerKeyFileName", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerKeyFileName", currentResult);
         currentResult.setValue("description", "<p>The full directory location of the private key file (<code>.der</code> or <code>.pem</code>) for the server.</p>  <p>The pathname should either be absolute or relative to the directory from which the server is booted. This field provides backward compatibility for security configurations that store private keys in files. For a more secure deployment, Oracle recommends saving private keys in keystores.</p>  <p>The file extension (<code>.der</code> or <code>.pem</code>) indicates the method that should be used to read the file.</p> ");
         setPropertyDescriptorDefault(currentResult, "server-key.der");
         currentResult.setValue("deprecated", "8.1.0.0 private keys should be stored in keystores. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerPrivateKeyAlias")) {
         getterName = "getServerPrivateKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerPrivateKeyAlias";
         }

         currentResult = new PropertyDescriptor("ServerPrivateKeyAlias", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerPrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the server's private key in the keystore. This private key is associated with the server's digital certificate.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerPrivateKeyPassPhrase")) {
         getterName = "getServerPrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerPrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("ServerPrivateKeyPassPhrase", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the server's private key from the keystore. This passphrase is assigned to the private key when it is generated.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerPrivateKeyPassPhraseEncrypted")) {
         getterName = "getServerPrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerPrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("ServerPrivateKeyPassPhraseEncrypted", SSLMBean.class, getterName, setterName);
         descriptors.put("ServerPrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the server's private key from the keystore. This passphrase is assigned to the private key when it is generated.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", SSLMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TrustedCAFileName")) {
         getterName = "getTrustedCAFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustedCAFileName";
         }

         currentResult = new PropertyDescriptor("TrustedCAFileName", SSLMBean.class, getterName, setterName);
         descriptors.put("TrustedCAFileName", currentResult);
         currentResult.setValue("description", "<p>The full directory location of the file that specifies the certificate authorities trusted by the server.</p>  <p>The pathname should either be absolute or relative to the directory from which the server is booted. This field provides backward compatibility for security configurations that store trusted certificate authorities in files.</p>  <p>The file specified in this attribute can contain a single digital certificate or multiple digital certificates. The file extension ( <code>.der</code> or <code>.pem</code>) tells WebLogic Server how to read the contents of the file.</p> ");
         setPropertyDescriptorDefault(currentResult, "trusted-ca.pem");
         currentResult.setValue("deprecated", "8.1.0.0 trusted CAs should be stored in keystores. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AcceptKSSDemoCertsEnabled")) {
         getterName = "isAcceptKSSDemoCertsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcceptKSSDemoCertsEnabled";
         }

         currentResult = new PropertyDescriptor("AcceptKSSDemoCertsEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("AcceptKSSDemoCertsEnabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AllowUnencryptedNullCipher")) {
         getterName = "isAllowUnencryptedNullCipher";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowUnencryptedNullCipher";
         }

         currentResult = new PropertyDescriptor("AllowUnencryptedNullCipher", SSLMBean.class, getterName, setterName);
         descriptors.put("AllowUnencryptedNullCipher", currentResult);
         currentResult.setValue("description", "<p>Test if the AllowUnEncryptedNullCipher is enabled</p> <p>see <code>setAllowUnencryptedNullCipher(boolean enable)</code> for the NullCipher feature.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (!descriptors.containsKey("ClientCertificateEnforced")) {
         getterName = "isClientCertificateEnforced";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertificateEnforced";
         }

         currentResult = new PropertyDescriptor("ClientCertificateEnforced", SSLMBean.class, getterName, setterName);
         descriptors.put("ClientCertificateEnforced", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not clients must present digital certificates from a trusted certificate authority to WebLogic Server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClientInitSecureRenegotiationAccepted")) {
         getterName = "isClientInitSecureRenegotiationAccepted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientInitSecureRenegotiationAccepted";
         }

         currentResult = new PropertyDescriptor("ClientInitSecureRenegotiationAccepted", SSLMBean.class, getterName, setterName);
         descriptors.put("ClientInitSecureRenegotiationAccepted", currentResult);
         currentResult.setValue("description", "Indicate whether TLS client initiated secure renegotiation is accepted. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", SSLMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", SSLMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the server can be reached through the default SSL listen port.</p>  <p>If the administration port is enabled for the WebLogic Server domain, then administrative traffic travels over the administration port and application traffic travels over the Listen Port and SSL Listen Port. If the administration port is disabled, then all traffic travels over the Listen Port and SSL Listen Port.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("HandlerEnabled")) {
         getterName = "isHandlerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHandlerEnabled";
         }

         currentResult = new PropertyDescriptor("HandlerEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("HandlerEnabled", currentResult);
         currentResult.setValue("description", "<p>Not used.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("HostnameVerificationIgnored")) {
         getterName = "isHostnameVerificationIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerificationIgnored";
         }

         currentResult = new PropertyDescriptor("HostnameVerificationIgnored", SSLMBean.class, getterName, setterName);
         descriptors.put("HostnameVerificationIgnored", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to ignore the installed implementation of the <code>weblogic.security.SSL.HostnameVerifier</code> interface (when this server is acting as a client to another application server).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JSSEEnabled")) {
         getterName = "isJSSEEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJSSEEnabled";
         }

         currentResult = new PropertyDescriptor("JSSEEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("JSSEEnabled", currentResult);
         currentResult.setValue("description", "Determines whether the SSL implementation in Weblogic Server is JSSE based. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("KeyEncrypted")) {
         getterName = "isKeyEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyEncrypted";
         }

         currentResult = new PropertyDescriptor("KeyEncrypted", SSLMBean.class, getterName, setterName);
         descriptors.put("KeyEncrypted", currentResult);
         currentResult.setValue("description", "<p>Indicates whether or not the private key for the WebLogic Server has been encrypted with a password. This attribute is no longer used as of WebLogic Server version 7.0.</p>  <ul> <li> <p>If the attribute is set to <code>true,</code> the private key requires a password be supplied in order to use the key.</p> </li>  <li> <p>If the attribute is set to <code>false</code>, the private key is unencrypted and may be used without providing a password.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("8.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SSLRejectionLoggingEnabled")) {
         getterName = "isSSLRejectionLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLRejectionLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("SSLRejectionLoggingEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("SSLRejectionLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether warning messages are logged in the server log when SSL connections are rejected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "8.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SSLv2HelloEnabled")) {
         getterName = "isSSLv2HelloEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLv2HelloEnabled";
         }

         currentResult = new PropertyDescriptor("SSLv2HelloEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("SSLv2HelloEnabled", currentResult);
         currentResult.setValue("description", "Indicate whether SSLv2Hello is enabled ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TwoWaySSLEnabled")) {
         getterName = "isTwoWaySSLEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTwoWaySSLEnabled";
         }

         currentResult = new PropertyDescriptor("TwoWaySSLEnabled", SSLMBean.class, getterName, setterName);
         descriptors.put("TwoWaySSLEnabled", currentResult);
         currentResult.setValue("description", "<p>The form of SSL that should be used.</p>  <p>By default, WebLogic Server is configured to use one-way SSL (implied by the <code>Client Certs Not Requested</code> value). Selecting <code>Client Certs Requested But Not Enforced</code> enables two-way SSL. With this option, the server requests a certificate from the client, but the connection continues if the client does not present a certificate. Selecting <code>Client Certs Requested And Enforced</code> also enables two-way SSL and requires a client to present a certificate. However, if a certificate is not presented, the SSL connection is terminated.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseClientCertForOutbound")) {
         getterName = "isUseClientCertForOutbound";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseClientCertForOutbound";
         }

         currentResult = new PropertyDescriptor("UseClientCertForOutbound", SSLMBean.class, getterName, setterName);
         descriptors.put("UseClientCertForOutbound", currentResult);
         currentResult.setValue("description", "Determines whether to use the configured client SSL certificate as identity for outbound SSL connections. <p/> Note that to use a client SSL certificate, one must be specified in <code>{@link #setClientCertAlias}</code>. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setUseClientCertForOutbound"), BeanInfoHelper.encodeEntities("#getClientCertAlias")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("UseJava")) {
         getterName = "isUseJava";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseJava";
         }

         currentResult = new PropertyDescriptor("UseJava", SSLMBean.class, getterName, setterName);
         descriptors.put("UseJava", currentResult);
         currentResult.setValue("description", "<p>Enables the use of native Java libraries.</p>  <p>WebLogic Server provides a pure-Java implementation of the SSL protocol. Native libraries enhance the performance for SSL operations on the Solaris, Windows NT, and IBM AIX platforms.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("UseServerCerts")) {
         getterName = "isUseServerCerts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseServerCerts";
         }

         currentResult = new PropertyDescriptor("UseServerCerts", SSLMBean.class, getterName, setterName);
         descriptors.put("UseServerCerts", currentResult);
         currentResult.setValue("description", "Sets whether the client should use the server certificates/key as the client identity when initiating an outbound connection over https. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SSLMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = SSLMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SSLMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = SSLMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
