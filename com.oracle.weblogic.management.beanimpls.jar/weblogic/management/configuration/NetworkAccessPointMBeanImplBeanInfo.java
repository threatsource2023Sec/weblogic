package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class NetworkAccessPointMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = NetworkAccessPointMBean.class;

   public NetworkAccessPointMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NetworkAccessPointMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.NetworkAccessPointMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getNetworkAccessPoints")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>A server can specify additional network connections by using a NetworkAccessPointMBean.  The NetworkAccessPointMBean is also used to set the listen address and external DNS name that a server uses for a particular channel.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.NetworkAccessPointMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("AcceptBacklog")) {
         getterName = "getAcceptBacklog";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcceptBacklog";
         }

         currentResult = new PropertyDescriptor("AcceptBacklog", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("AcceptBacklog", currentResult);
         currentResult.setValue("description", "<p>The number of backlogged, new TCP connection requests that this network channel allows. A value of <code>-1</code> indicates that the network channel obtains its backlog configuration from the server's configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerTemplateMBean#getAcceptBacklog")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelWeight")) {
         getterName = "getChannelWeight";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChannelWeight";
         }

         currentResult = new PropertyDescriptor("ChannelWeight", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ChannelWeight", currentResult);
         currentResult.setValue("description", "<p>A weight to give this channel when creating server-to-server connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Ciphersuites")) {
         getterName = "getCiphersuites";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCiphersuites";
         }

         currentResult = new PropertyDescriptor("Ciphersuites", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("Ciphersuites", currentResult);
         currentResult.setValue("description", "<p>Indicates the cipher suites being used on a particular WebLogic Server channel.</p>  <p>The strongest negotiated cipher suite is chosen during the SSL handshake. The set of cipher suites used by default by JSEE depends on the specific JDK version with which WebLogic Server is configured.</p>  <p>For a list of possible values, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=SECMG502\" rel=\"noopener noreferrer\" target=\"_blank\">Cipher Suites</a>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterAddress")) {
         getterName = "getClusterAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterAddress";
         }

         currentResult = new PropertyDescriptor("ClusterAddress", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ClusterAddress", currentResult);
         currentResult.setValue("description", "<p>The address this network channel uses to generate EJB handles and failover addresses for use in a cluster. This value is determined according to the following order of precedence:</p> <ol> <li>If the cluster address is specified via the NAPMBean, then that value is used</li> <li>If this value is not specified, the value of PublicAddress is used.</li> <li>If PublicAddress is not set, this value is derive from the ClusterAddress attribute of the ClusterMbean.</li> <li>If ClusterMbean.clusterAddress is not set, this value is derive from the listen address of the NAPMbean.</li> </ol> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPublicAddress"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ClusterMBean#getClusterAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompleteMessageTimeout")) {
         getterName = "getCompleteMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteMessageTimeout", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CompleteMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time this network channel waits for a complete message to be received. A value of <code>0</code> disables network channel complete message timeout. A value of <code>-1</code> indicates that the network channel obtains this timeout value from the ServerTemplateMBean.</p>  <p>This timeout helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectTimeout")) {
         getterName = "getConnectTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectTimeout";
         }

         currentResult = new PropertyDescriptor("ConnectTimeout", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ConnectTimeout", currentResult);
         currentResult.setValue("description", "<p>The amount of time that this network channel should wait to establish an outbound socket connection before timing out. A value of <code>0</code> disables network channel connect timeout.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(240));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStoreFileName")) {
         getterName = "getCustomIdentityKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStoreFileName", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The source of the identity keystore. For a JKS keystore, the source is the path and file name. For an Oracle Key Store Service (KSS) keystore, the source is the KSS URI.</p>  <p>If using a JKS keystore, the keystore path name must either be absolute or relative to where the server was booted.</p>  <p>If using a KSS keystore, the keystore URI must be of the form:</p>  <p><code>kss://system/<i>keystorename</i></code></p>  <p>where <code><i>keystorename</i></code> is the name of the keystore registered in KSS.</p>  <p>The value in this attribute is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStorePassPhrase")) {
         getterName = "getCustomIdentityKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStorePassPhrase", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted custom identity keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>CustomIdentityKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>CustomIdentityKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>CustomIdentityKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>CustomIdentityKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCustomIdentityKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStorePassPhraseEncrypted")) {
         getterName = "getCustomIdentityKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStorePassPhraseEncrypted", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomIdentityKeyStoreType")) {
         getterName = "getCustomIdentityKeyStoreType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomIdentityKeyStoreType";
         }

         currentResult = new PropertyDescriptor("CustomIdentityKeyStoreType", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomIdentityKeyStoreType", currentResult);
         currentResult.setValue("description", "<p>The type of the keystore. Generally, this is <code>JKS</code>. If using the Oracle Key Store Service, this would be <code>KSS</code></p>  <p>If empty or null, then the JDK's default keystore type (specified in <code>java.security</code>) is used. The custom identity key store type is only used if <code>ServerMBean.KeyStores</code> is <code>CUSTOM_IDENTITY_AND_JAVA_STANDARD_TRUST</code>, <code>CUSTOM_IDENTITY_AND_CUSTOM_TRUST</code> or <code>CUSTOM_IDENTITY_AND_COMMAND_LINE_TRUST</code>.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomPrivateKeyAlias")) {
         getterName = "getCustomPrivateKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomPrivateKeyAlias";
         }

         currentResult = new PropertyDescriptor("CustomPrivateKeyAlias", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomPrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the channel's private key in the keystore. This private key is associated with the server's digital certificate. A value of <code>null</code> indicates that the network channel uses the alias specified in the server's SSL configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getServerPrivateKeyAlias")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomPrivateKeyPassPhrase")) {
         getterName = "getCustomPrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomPrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("CustomPrivateKeyPassPhrase", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the server's private key from the keystore. This passphrase is assigned to the private key when it is generated. A value of <code>null</code> indicates that the network channel uses the pass phrase specified in the server's SSL configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getServerPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomPrivateKeyPassPhraseEncrypted")) {
         getterName = "getCustomPrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomPrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("CustomPrivateKeyPassPhraseEncrypted", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomPrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted form of passphrase used to retrieve the server's private key from the keystore. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomProperties")) {
         getterName = "getCustomProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomProperties";
         }

         currentResult = new PropertyDescriptor("CustomProperties", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("CustomProperties", currentResult);
         currentResult.setValue("description", "Get custom protocol properties specified for this channel. The contents of the map are only know the custom protocol implementators like SIP. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExcludedCiphersuites")) {
         getterName = "getExcludedCiphersuites";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludedCiphersuites";
         }

         currentResult = new PropertyDescriptor("ExcludedCiphersuites", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ExcludedCiphersuites", currentResult);
         currentResult.setValue("description", "<p>List of cipher suites not to be used by WebLogic Server.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostnameVerifier")) {
         getterName = "getHostnameVerifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerifier";
         }

         currentResult = new PropertyDescriptor("HostnameVerifier", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("HostnameVerifier", currentResult);
         currentResult.setValue("description", "<p>The name of the class that implements the <code>weblogic.security.SSL.HostnameVerifier</code> interface.</p>  <p>This class verifies whether the connection to the host with the hostname from URL should be allowed. The class is used to prevent man-in-the-middle attacks. The <code>weblogic.security.SSL.HostnameVerifier</code> has a <code>verify()</code> method that WebLogic Server calls on the client during the SSL handshake.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleConnectionTimeout")) {
         getterName = "getIdleConnectionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleConnectionTimeout";
         }

         currentResult = new PropertyDescriptor("IdleConnectionTimeout", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("IdleConnectionTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time (in seconds) that a connection is allowed to be idle before it is closed by this network channel. A value of <code>-1</code> indicates that the network channel obtains this timeout value from the ServerTemplateMBean.</p>  <p>This timeout helps guard against server deadlock through too many open connections.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InboundCertificateValidation")) {
         getterName = "getInboundCertificateValidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInboundCertificateValidation";
         }

         currentResult = new PropertyDescriptor("InboundCertificateValidation", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("InboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the client certificate validation rules for inbound SSL.</p>  <p>This attribute only applies to ports and network channels using 2-way SSL.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address or DNS name this network channel uses to listen for incoming connections. A value of <code>null</code> indicates that the network channel should obtain this value from the server's configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getListenAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The default TCP port this network channel uses to listen for regular (non-SSL) incoming connections. A value of <code>-1</code> indicates that the network channel should obtain this value from the server's configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeoutMillis")) {
         getterName = "getLoginTimeoutMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeoutMillis";
         }

         currentResult = new PropertyDescriptor("LoginTimeoutMillis", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("LoginTimeoutMillis", currentResult);
         currentResult.setValue("description", "<p>The amount of time that this network channel should wait for a connection before timing out. A value of <code>0</code> disables network channel login timeout. A value of <code>-1</code> indicates that the network channel obtains this timeout value from the server's configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerTemplateMBean#getLoginTimeoutMillis")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(100000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxBackoffBetweenFailures")) {
         getterName = "getMaxBackoffBetweenFailures";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxBackoffBetweenFailures";
         }

         currentResult = new PropertyDescriptor("MaxBackoffBetweenFailures", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("MaxBackoffBetweenFailures", currentResult);
         currentResult.setValue("description", "<p>The maximum back off time between failures while accepting client connections. -1 implies that this value is inherited from the server.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConnectedClients")) {
         getterName = "getMaxConnectedClients";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConnectedClients";
         }

         currentResult = new PropertyDescriptor("MaxConnectedClients", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("MaxConnectedClients", currentResult);
         currentResult.setValue("description", "<p>The maximum number of clients that can be connected on this network channel.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxMessageSize")) {
         getterName = "getMaxMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxMessageSize", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("MaxMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum message size allowable in a message header.</p>  <p>This maximum attempts to prevent a denial of service attack whereby a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p> <p>A client can set this value using the <code>-Dweblogic.MaxMessageSize</code> property.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(100000000));
         currentResult.setValue("legalMin", new Integer(4096));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinimumTLSProtocolVersion")) {
         getterName = "getMinimumTLSProtocolVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinimumTLSProtocolVersion";
         }

         currentResult = new PropertyDescriptor("MinimumTLSProtocolVersion", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("MinimumTLSProtocolVersion", currentResult);
         currentResult.setValue("description", "Get the minimum SSL/TLS protocol version to be used in a network channel. ");
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

         currentResult = new PropertyDescriptor("Name", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this network channel.</p> ");
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

         currentResult = new PropertyDescriptor("OutboundCertificateValidation", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("OutboundCertificateValidation", currentResult);
         currentResult.setValue("description", "<p>Indicates the server certificate validation rules for outbound SSL.</p>  <p>This attribute always applies to outbound SSL that is part of WebLogic Server (that is, an Administration Server talking to the Node Manager). It does not apply to application code in the server that is using outbound SSL unless the application code uses a <code>weblogic.security.SSL.ServerTrustManager</code> that is configured to use outbound SSL validation.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyAlias")) {
         getterName = "getOutboundPrivateKeyAlias";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundPrivateKeyAlias", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the outbound private key in the keystore. This private key is associated with either a server or a client digital certificate. This attribute value is derived from other settings and cannot be physically set.</p>  <p>The returned value is determined as follows:</p> <ul> <li>If <code>{@link #isOutboundPrivateKeyEnabled}</code> and <code>{@link #isChannelIdentityCustomized}</code> return true, the value from <code>{@link #getCustomPrivateKeyAlias}</code> is returned.</li> <li> Otherwise, the value from <code>{@link SSLMBean#getOutboundPrivateKeyAlias}</code> is returned from the <code>{@link ServerMBean}</code> for the channel.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isOutboundPrivateKeyEnabled"), BeanInfoHelper.encodeEntities("#isChannelIdentityCustomized"), BeanInfoHelper.encodeEntities("#getCustomPrivateKeyAlias"), BeanInfoHelper.encodeEntities("SSLMBean#getOutboundPrivateKeyAlias")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyPassPhrase")) {
         getterName = "getOutboundPrivateKeyPassPhrase";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundPrivateKeyPassPhrase", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the outbound private key from the keystore. This passphrase is assigned to the private key when it is generated. This attribute value is derived from other settings and cannot be physically set.</p>  <p>The returned value is determined as follows:</p> <ul> <li>If <code>{@link #isOutboundPrivateKeyEnabled}</code> and <code>{@link #isChannelIdentityCustomized}</code> return true, the value from <code>{@link #getCustomPrivateKeyPassPhrase}</code> is returned.</li> <li> Otherwise, the value from <code>{@link SSLMBean#getOutboundPrivateKeyPassPhrase}</code> is returned from the <code>{@link ServerMBean}</code> for the channel.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isOutboundPrivateKeyEnabled"), BeanInfoHelper.encodeEntities("#isChannelIdentityCustomized"), BeanInfoHelper.encodeEntities("#getCustomPrivateKeyPassPhrase"), BeanInfoHelper.encodeEntities("SSLMBean#getOutboundPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrivateKeyAlias")) {
         getterName = "getPrivateKeyAlias";
         setterName = null;
         currentResult = new PropertyDescriptor("PrivateKeyAlias", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("PrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the channel's private key in the keystore. This private key is associated with the server's digital certificate. This value is derived from other settings on the channel and cannot be physically set.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getServerPrivateKeyAlias"), BeanInfoHelper.encodeEntities("#getCustomPrivateKeyAlias")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrivateKeyPassPhrase")) {
         getterName = "getPrivateKeyPassPhrase";
         setterName = null;
         currentResult = new PropertyDescriptor("PrivateKeyPassPhrase", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("PrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the server's private key from the keystore. This passphrase is assigned to the private key when it is generated. This value is derived from other settings on the channel and cannot be physically set.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getServerPrivateKeyPassPhrase"), BeanInfoHelper.encodeEntities("#getCustomPrivateKeyPassPhrase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Protocol")) {
         getterName = "getProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProtocol";
         }

         currentResult = new PropertyDescriptor("Protocol", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("Protocol", currentResult);
         currentResult.setValue("description", "<p>The protocol this network channel should use for connections.</p> ");
         setPropertyDescriptorDefault(currentResult, "t3");
         currentResult.setValue("secureValue", "t3s");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProxyAddress")) {
         getterName = "getProxyAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProxyAddress";
         }

         currentResult = new PropertyDescriptor("ProxyAddress", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ProxyAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address or DNS name of the HTTP proxy to use for outbound connections on this channel. The HTTP proxy must support the CONNECT tunneling command.</p>  <p>This option is only effective when OutboundEnabled is set on the channel.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProxyPort")) {
         getterName = "getProxyPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProxyPort";
         }

         currentResult = new PropertyDescriptor("ProxyPort", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ProxyPort", currentResult);
         currentResult.setValue("description", "<p>The port of the HTTP proxy to use for outbound connections on this channel. The HTTP proxy must support the CONNECT tunneling command.</p>  <p>This option is only effective when OutboundEnabled and ProxyHost are set on the channel.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(80));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicAddress")) {
         getterName = "getPublicAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicAddress";
         }

         currentResult = new PropertyDescriptor("PublicAddress", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("PublicAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address or DNS name representing the external identity of this network channel. A value of <code>null</code> indicates that the network channel's Listen Address is also its external address. If the Listen Address is <code>null,</code>the network channel obtains its external identity from the server's configuration.</p>  <p>This is required for the configurations which need to cross a firewall doing Network Address Translation.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getExternalDNSName"), BeanInfoHelper.encodeEntities("#getListenAddress"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getListenAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicPort")) {
         getterName = "getPublicPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicPort";
         }

         currentResult = new PropertyDescriptor("PublicPort", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("PublicPort", currentResult);
         currentResult.setValue("description", "<p>The externally published listen port for this network channel. A value of <code>-1</code> indicates that the network channel's Listen Port is also its public listen port. If the Listen Port is <code>-1,</code>the network channel obtains its public listen port from the server's configuration.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResolveDNSName")) {
         getterName = "getResolveDNSName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResolveDNSName";
         }

         currentResult = new PropertyDescriptor("ResolveDNSName", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ResolveDNSName", currentResult);
         currentResult.setValue("description", "<p>Resolve the listen address to use for all T3 communication</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getListenAddress()"), BeanInfoHelper.encodeEntities("#getPublicAddress()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutConnectionWithPendingResponses")) {
         getterName = "getTimeoutConnectionWithPendingResponses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeoutConnectionWithPendingResponses";
         }

         currentResult = new PropertyDescriptor("TimeoutConnectionWithPendingResponses", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("TimeoutConnectionWithPendingResponses", currentResult);
         currentResult.setValue("description", "<p>Determines if connections with pending responses are allowed to timeout. It defaults to false. If set to true, the connection will be timed out for this channel if it exceeds the idleConnectionTimeout value.</p>  <p>Note: This setting only applies to IIOP connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingClientPingSecs")) {
         getterName = "getTunnelingClientPingSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientPingSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientPingSecs", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientPingSecs", currentResult);
         currentResult.setValue("description", "<p>The interval (in seconds) at which this network channel should ping an HTTP-tunneled client to see if its still alive. A value of <code>-1</code> indicates that the network channel obtains this interval from the ServerTemplateMBean. (Requires you to enable tunneling for the network channel.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getTunnelingClientPingSecs"), BeanInfoHelper.encodeEntities("#isTunnelingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TunnelingClientTimeoutSecs")) {
         getterName = "getTunnelingClientTimeoutSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingClientTimeoutSecs";
         }

         currentResult = new PropertyDescriptor("TunnelingClientTimeoutSecs", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("TunnelingClientTimeoutSecs", currentResult);
         currentResult.setValue("description", "<p>The amount of time (in seconds) after which this network channel considers a missing HTTP-tunneled client to be dead. A value of <code>-1</code> indicates that the network channel obtains this timeout value from the ServerTemplateMBean. (Requires you to enable tunneling for the network channel.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerTemplateMBean#getTunnelingClientTimeoutSecs"), BeanInfoHelper.encodeEntities("#isTunnelingEnabled")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseFastSerialization")) {
         getterName = "getUseFastSerialization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseFastSerialization";
         }

         currentResult = new PropertyDescriptor("UseFastSerialization", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("UseFastSerialization", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to use non-standard object serialization for performance. This option works in different ways for different protocols. In particular under IIOP this option uses Java serialization rather than RMI-IIOP serialization. In general using non-standard serialization is not suitable for interop scenarios and may imply some feature loss.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowUnencryptedNullCipher")) {
         getterName = "isAllowUnencryptedNullCipher";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowUnencryptedNullCipher";
         }

         currentResult = new PropertyDescriptor("AllowUnencryptedNullCipher", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("AllowUnencryptedNullCipher", currentResult);
         currentResult.setValue("description", "<p>Test if the AllowUnEncryptedNullCipher is enabled</p> <p>see <code>setAllowUnencryptedNullCipher(boolean enable)</code> for the NullCipher feature.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setAllowUnencryptedNullCipher(boolean)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelIdentityCustomized")) {
         getterName = "isChannelIdentityCustomized";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChannelIdentityCustomized";
         }

         currentResult = new PropertyDescriptor("ChannelIdentityCustomized", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ChannelIdentityCustomized", currentResult);
         currentResult.setValue("description", "<p>Whether or not the channel's custom identity should be used. This setting only has an effect if the server is using a customized keystore. By default the channel's identity is inherited from the server's identity.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.SSLMBean#getServerPrivateKeyAlias"), BeanInfoHelper.encodeEntities("#getCustomPrivateKeyAlias")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertificateEnforced")) {
         getterName = "isClientCertificateEnforced";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertificateEnforced";
         }

         currentResult = new PropertyDescriptor("ClientCertificateEnforced", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ClientCertificateEnforced", currentResult);
         currentResult.setValue("description", "<p>Specifies whether clients must present digital certificates from a trusted certificate authority to WebLogic Server on this channel.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClientInitSecureRenegotiationAccepted")) {
         getterName = "isClientInitSecureRenegotiationAccepted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientInitSecureRenegotiationAccepted";
         }

         currentResult = new PropertyDescriptor("ClientInitSecureRenegotiationAccepted", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("ClientInitSecureRenegotiationAccepted", currentResult);
         currentResult.setValue("description", "Indicate whether TLS client initiated secure renegotiation is accepted. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this channel should be started.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HostnameVerificationIgnored")) {
         getterName = "isHostnameVerificationIgnored";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHostnameVerificationIgnored";
         }

         currentResult = new PropertyDescriptor("HostnameVerificationIgnored", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("HostnameVerificationIgnored", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to ignore the installed implementation of the <code>weblogic.security.SSL.HostnameVerifier</code> interface (when this server is acting as a client to another application server).</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HttpEnabledForThisProtocol")) {
         getterName = "isHttpEnabledForThisProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpEnabledForThisProtocol";
         }

         currentResult = new PropertyDescriptor("HttpEnabledForThisProtocol", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("HttpEnabledForThisProtocol", currentResult);
         currentResult.setValue("description", "<p>Specifies whether HTTP traffic should be allowed over this network channel.</p>  <p>HTTP is generally required by binary protocols for downloading stubs and other resources.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundEnabled")) {
         getterName = "isOutboundEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundEnabled";
         }

         currentResult = new PropertyDescriptor("OutboundEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("OutboundEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether new server-to-server connections may consider this network channel when initiating a connection. This is only relevant if the connection needs to be bound to the network channel's listen address. This will only work for binary protocols that support both outbound and inbound traffic.</p>   <p>When this feature is not enabled, connections are initiated using a local address selected by the underlying hardware.</p>  <p>The default is false.</p>  <p>Outbound channels are selected at runtime either by virtue of the fact of being the only outbound-enabled channel for the required protocol, or by name in <code>weblogic.jndi.Environment#setProviderChannel</code>.</p>  <p>The HTTP protocol is implicitly enabled for all the outbound channels, but you need to use the WLS client library (HTTP client) and set the channel on the connection. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.net.http.HttpURLConnection#setSocketFactory")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundPrivateKeyEnabled")) {
         getterName = "isOutboundPrivateKeyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOutboundPrivateKeyEnabled";
         }

         currentResult = new PropertyDescriptor("OutboundPrivateKeyEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("OutboundPrivateKeyEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the identity specified by {@link #getCustomPrivateKeyAlias} should be used for outbound SSL connections on this channel. In normal circumstances the outbound identity is determined by the caller's environment.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SDPEnabled")) {
         getterName = "isSDPEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSDPEnabled";
         }

         currentResult = new PropertyDescriptor("SDPEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("SDPEnabled", currentResult);
         currentResult.setValue("description", "Enables Socket Direct Protocol (SDP) on this channel. Enable this attribute when configuring session replication enhancements for Managed Servers in a WebLogic cluster for Oracle Exalogic. SDP is removed from JDK11. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "14.1.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SSLv2HelloEnabled")) {
         getterName = "isSSLv2HelloEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLv2HelloEnabled";
         }

         currentResult = new PropertyDescriptor("SSLv2HelloEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("SSLv2HelloEnabled", currentResult);
         currentResult.setValue("description", "Indicate whether SSLv2Hello is enabled in a network channel. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TunnelingEnabled")) {
         getterName = "isTunnelingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTunnelingEnabled";
         }

         currentResult = new PropertyDescriptor("TunnelingEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("TunnelingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether tunneling via HTTP should be enabled for this network channel. This value is not inherited from the server's configuration.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TwoWaySSLEnabled")) {
         getterName = "isTwoWaySSLEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTwoWaySSLEnabled";
         }

         currentResult = new PropertyDescriptor("TwoWaySSLEnabled", NetworkAccessPointMBean.class, getterName, setterName);
         descriptors.put("TwoWaySSLEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this network channel uses two way SSL.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
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
