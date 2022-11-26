package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TLSMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TLSMBean.class;

   public TLSMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TLSMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.TLSMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>A <code>TLSMBean</code> contains SSL/TLS connection parameters and may be organized in a user-defined inheritance hierarchy and referenced by name.</p>  TODO: since 7.0.0.0 TODO: delegateBean TODO: validator TLSValidator.validateTLS ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.TLSMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Ciphersuites")) {
         getterName = "getCiphersuites";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCiphersuites";
         }

         currentResult = new PropertyDescriptor("Ciphersuites", TLSMBean.class, getterName, setterName);
         descriptors.put("Ciphersuites", currentResult);
         currentResult.setValue("description", "<p>Specifies the cipher suites for the TLS connection.</p>  <p>The strongest negotiated cipher suite is chosen during the SSL handshake. The set of cipher suites used by default by JSSE depends on the specific JDK version with which WebLogic Server is configured.</p>  <p>For a list of possible values, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=fmw122100&amp;id=SECMG502\">Cipher Suites</a>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostnameVerifier")) {
         getterName = "getHostnameVerifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerifier";
         }

         currentResult = new PropertyDescriptor("HostnameVerifier", TLSMBean.class, getterName, setterName);
         descriptors.put("HostnameVerifier", currentResult);
         currentResult.setValue("description", "<p>The name of the class that implements the <code>weblogic.security.SSL.HostnameVerifier</code> interface.</p>  <p>This class verifies whether the connection to the host with the hostname from URL should be allowed. The class is used to prevent man-in-the-middle attacks. The <code>weblogic.security.SSL.HostnameVerifier</code> has a <code>verify()</code> method that WebLogic Server calls on the client during the SSL handshake.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityKeyStoreFileName")) {
         getterName = "getIdentityKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStoreFileName", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The source of the identity keystore.</p>  <p>For a JKS keystore, the source is the path and file name. For an Oracle Key Store Service (KSS) keystore, the source is the KSS URI.</p>  <p>If using a JKS keystore, the keystore path name must either be absolute or relative to where the server was booted.</p>  <p>If using a KSS keystore, the keystore URI must be of the form:</p>  <p><code>kss://system/<i>keystorename</i></code></p>  <p>where <code><i>keystorename</i></code> is the name of the keystore registered in KSS.</p>  <p>The value in this attribute is only used for a server if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("IdentityKeyStorePassPhrase")) {
         getterName = "getIdentityKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStorePassPhrase", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted identity keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>IdentityKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>IdentityKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>IdentityKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>IdentityKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getIdentityKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityKeyStorePassPhraseEncrypted")) {
         getterName = "getIdentityKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStorePassPhraseEncrypted", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityKeyStoreType")) {
         getterName = "getIdentityKeyStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStoreType";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStoreType", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStoreType", currentResult);
         currentResult.setValue("description", "<p>The type of the keystore. Generally, this is <code>JKS</code>. If using the Oracle Key Store Service, this would be <code>KSS</code></p>  <p>If empty or null, then the JDK's default keystore type (specified in <code>java.security</code>) is used. The identity key store type is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityPrivateKeyAlias")) {
         getterName = "getIdentityPrivateKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityPrivateKeyAlias";
         }

         currentResult = new PropertyDescriptor("IdentityPrivateKeyAlias", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityPrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The alias of the private key representing the identity for the associated connection. The alias refers to a private key in {@link #getIdentityKeyStoreFileName()}</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityPrivateKeyPassPhrase")) {
         getterName = "getIdentityPrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityPrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("IdentityPrivateKeyPassPhrase", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The pass phrase of the private key referred by the alias in {@link #getIdentityPrivateKeyAlias()}.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityPrivateKeyPassPhraseEncrypted")) {
         getterName = "getIdentityPrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityPrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("IdentityPrivateKeyPassPhraseEncrypted", TLSMBean.class, getterName, setterName);
         descriptors.put("IdentityPrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted form of the pass phrase used to retrieve the identity private key from the keystore. </p> TODO: derivedDefault ((ServerTemplateMBean)getParent()).getSSL().getServerPrivateKeyPassPhraseEncrypted() ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InboundCertificateValidation")) {
         getterName = "getInboundCertificateValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInboundCertificateValidation";
         }

         currentResult = new PropertyDescriptor("InboundCertificateValidation", TLSMBean.class, getterName, setterName);
         descriptors.put("InboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the client certificate validation rules for inbound SSL.</p>  <p>This attribute only applies to TLS connections using 2-way SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, "BuiltinSSLValidationOnly");
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumTLSProtocolVersion")) {
         getterName = "getMinimumTLSProtocolVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinimumTLSProtocolVersion";
         }

         currentResult = new PropertyDescriptor("MinimumTLSProtocolVersion", TLSMBean.class, getterName, setterName);
         descriptors.put("MinimumTLSProtocolVersion", currentResult);
         currentResult.setValue("description", "Get the minimum SSL/TLS protocol version to be used in a TLS connection. TODO: derivedDefault ((ServerTemplateMBean)(getParent())).getSSL().getMinimumTLSProtocolVersion() ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMinimumTLSProtocolVersion(String)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", TLSMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this set of TLS connection parameters.</p> ");
         setPropertyDescriptorDefault(currentResult, "<unknown>");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundCertificateValidation")) {
         getterName = "getOutboundCertificateValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundCertificateValidation";
         }

         currentResult = new PropertyDescriptor("OutboundCertificateValidation", TLSMBean.class, getterName, setterName);
         descriptors.put("OutboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the server certificate validation rules for outbound SSL.</p>  <p>This attribute always applies to outbound SSL that is part of WebLogic Server (that is, an Administration Server talking to the Node Manager). It does not apply to application code in the server that is using outbound SSL unless the application code uses a <code>weblogic.security.SSL.ServerTrustManager</code> that is configured to use outbound SSL validation.</p> ");
         setPropertyDescriptorDefault(currentResult, "BuiltinSSLValidationOnly");
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStoreFileName")) {
         getterName = "getTrustKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("TrustKeyStoreFileName", TLSMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The source of the trust keystore.</p>  <p>For a JKS keystore, the source is the path and file name. For an Oracle Key Store Service (KSS) keystore, the source is the KSS URI.</p>  <p>If using a JKS keystore, the keystore path name must either be absolute or relative to where the server was booted.</p>  <p>If using a KSS keystore, the keystore URI must be of the form:</p>  <p><code>kss://system/<i>keystorename</i></code></p>  <p>where <code><i>keystorename</i></code> is the name of the keystore registered in KSS.</p>  <p>The value in this attribute is only used for a server if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStorePassPhrase")) {
         getterName = "getTrustKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("TrustKeyStorePassPhrase", TLSMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted trust keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code>.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>TrustKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>TrustKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>TrustKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>TrustKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getTrustKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStorePassPhraseEncrypted")) {
         getterName = "getTrustKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("TrustKeyStorePassPhraseEncrypted", TLSMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStoreType")) {
         getterName = "getTrustKeyStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStoreType";
         }

         currentResult = new PropertyDescriptor("TrustKeyStoreType", TLSMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStoreType", currentResult);
         currentResult.setValue("description", "<p>The type of the trust keystore. Generally, this is <code>JKS</code>. If using the Oracle Key Store Service, this would be <code>KSS</code></p>  <p>If empty or null, then the JDK's default keystore type (specified in <code>java.security</code>) is used. The trust key store type is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Usage")) {
         getterName = "getUsage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsage";
         }

         currentResult = new PropertyDescriptor("Usage", TLSMBean.class, getterName, setterName);
         descriptors.put("Usage", currentResult);
         currentResult.setValue("description", "<p>The declared usages of this SSL/TLS configuration.</p> ");
         setPropertyDescriptorDefault(currentResult, "ForClientOrServer");
         currentResult.setValue("legalValues", new Object[]{"ForServer", "ForClient", "ForClientOrServer"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowUnencryptedNullCipher")) {
         getterName = "isAllowUnencryptedNullCipher";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowUnencryptedNullCipher";
         }

         currentResult = new PropertyDescriptor("AllowUnencryptedNullCipher", TLSMBean.class, getterName, setterName);
         descriptors.put("AllowUnencryptedNullCipher", currentResult);
         currentResult.setValue("description", "<p>Specifies whether NULL ciphers are allowed for the TLS connection.</p>  <p>When a SSL server and a SSL client try to negotiate a commonly supported Cipher, there is a chance that they may end up with nothing in common. A NullCipher is a cipher providing no encryption for the SSL message between the client and server, and it may temporarily be used in the development environment if the SSL server and client share no common cipher for some reason. This is not a standard SSL feature, some SSL provider supports this feature.</p>  <p>The AllowUnEncryptedNullCipher flag is used to control whether the NullCipher feature is enabled or not, if true, the SSL message may be unencrypted when SSL server and client shares no common cipher.</p>  <p>This AllowUnEncryptedNullCipher flag is only effective to SSL providers which support the NullCipher feature.</p>  <p>Warning: this NullCipher feature should NOT be enabled for a production environment, it may leads to unencrypted SSL message</p>  <p>By default, the AllowUnEncryptedNullCipher is false</p> * ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertificateEnforced")) {
         getterName = "isClientCertificateEnforced";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertificateEnforced";
         }

         currentResult = new PropertyDescriptor("ClientCertificateEnforced", TLSMBean.class, getterName, setterName);
         descriptors.put("ClientCertificateEnforced", currentResult);
         currentResult.setValue("description", "<p>Specifies whether clients must present digital certificates from a trusted certificate authority.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostnameVerificationIgnored")) {
         getterName = "isHostnameVerificationIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerificationIgnored";
         }

         currentResult = new PropertyDescriptor("HostnameVerificationIgnored", TLSMBean.class, getterName, setterName);
         descriptors.put("HostnameVerificationIgnored", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to ignore the installed implementation of the <code>weblogic.security.SSL.HostnameVerifier</code> interface (when this server is acting as a client to another application server).</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLv2HelloEnabled")) {
         getterName = "isSSLv2HelloEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLv2HelloEnabled";
         }

         currentResult = new PropertyDescriptor("SSLv2HelloEnabled", TLSMBean.class, getterName, setterName);
         descriptors.put("SSLv2HelloEnabled", currentResult);
         currentResult.setValue("description", "Indicates whether SSLv2Hello is enabled in a TLS connection. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TwoWaySSLEnabled")) {
         getterName = "isTwoWaySSLEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTwoWaySSLEnabled";
         }

         currentResult = new PropertyDescriptor("TwoWaySSLEnabled", TLSMBean.class, getterName, setterName);
         descriptors.put("TwoWaySSLEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to use two way SSL.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
