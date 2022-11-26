package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCLocalTuxDomMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCLocalTuxDomMBean.class;

   public WTCLocalTuxDomMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCLocalTuxDomMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCLocalTuxDomMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface provides access to the WTC local Tuxedo Domain configuration attributes.  The methods defined herein are applicable for WTC configuration at the WLS domain level. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCLocalTuxDomMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AccessPoint")) {
         getterName = "getAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessPoint";
         }

         currentResult = new PropertyDescriptor("AccessPoint", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("AccessPoint", currentResult);
         currentResult.setValue("description", "<p>The unique name used to identify this local Tuxedo access point. This name should be unique for all local and remote Tuxedo access points defined within a WTC Service. This allows you to define unique configurations having the same Access Point ID.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAP");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AccessPointId")) {
         getterName = "getAccessPointId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAccessPointId";
         }

         currentResult = new PropertyDescriptor("AccessPointId", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("AccessPointId", currentResult);
         currentResult.setValue("description", "<p>The connection principal name used to identify this local Tuxedo access point when attempting to establish a session connection with remote Tuxedo access points.</p>  <p><i>Note:</i> The AccessPointId must match the corresponding DOMAINID in the *DM_REMOTE_DOMAINS section of your Tuxedo DMCONFIG file.</p> ");
         setPropertyDescriptorDefault(currentResult, "myLAPId");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlockTime")) {
         getterName = "getBlockTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlockTime";
         }

         currentResult = new PropertyDescriptor("BlockTime", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("BlockTime", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds this local Tuxedo access point allows for a blocking call.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 32 bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(60L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CmpLimit")) {
         getterName = "getCmpLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCmpLimit";
         }

         currentResult = new PropertyDescriptor("CmpLimit", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("CmpLimit", currentResult);
         currentResult.setValue("description", "<p>The compression threshold this local Tuxedo access point uses when sending data to a remote Tuxedo access point. Application buffers larger than this size are compressed.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnPrincipalName")) {
         getterName = "getConnPrincipalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnPrincipalName";
         }

         currentResult = new PropertyDescriptor("ConnPrincipalName", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("ConnPrincipalName", currentResult);
         currentResult.setValue("description", "<p>The principal name used to verify the identity of this domain when establishing a connection to another domain.</p>  <p><i>Note:</i> This parameter only applies to domains of type TDOMAIN that are running Oracle  Tuxedo 7.1 or later software. If not specified, the connection principal name defaults to the AccessPointID for this <code>domain.ConnPrincipalName</code>.</p>  <p><i>Note:</i> ConnPrincipalName is not supported in this release.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPolicy")) {
         getterName = "getConnectionPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPolicy";
         }

         currentResult = new PropertyDescriptor("ConnectionPolicy", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("ConnectionPolicy", currentResult);
         currentResult.setValue("description", "<p>The conditions under which this local Tuxedo access point establishes a session connection with a remote Tuxedo access point.</p>  <p>The connection policies are:</p>  <ul> <li>ON_DEMAND: A connection is attempted only when requested by either a client request to a remote service or an administrative connect command.</li> <li>ON_STARTUP: A domain gateway attempts to establish a connection with its remote Tuxedo access points at gateway server initialization time. Remote services (services advertised in JNDI by the domain gateway for this local access point) are advertised only if a connection is successfully established to that remote Tuxedo access point. If there is no active connection to a remote Tuxedo access point, then the remote services are suspended. By default, this connection policy retries failed connections every 60 seconds. Use the MaxRetry and RetryInterval values to specify application specific values.</li> <li>INCOMING_ONLY: A domain gateway does not attempt an initial connection to remote Tuxedo access points at startup and remote services are initially suspended. The domain gateway is available for incoming connections from remote Tuxedo access points and remote services are advertised when the domain gateway for this local Tuxedo access point receives an incoming connection. Connection retry processing is not allowed.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "ON_DEMAND");
         currentResult.setValue("legalValues", new Object[]{"ON_DEMAND", "ON_STARTUP", "INCOMING_ONLY"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityKeyStoreFileName")) {
         getterName = "getIdentityKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStoreFileName", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The path and file name of the identity keystore.</p>  <p>The path name must either be absolute or relative to where the server was booted. The identity key store file name is only used if KeystoreLocation is \"Custom Stores\".</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
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

         currentResult = new PropertyDescriptor("IdentityKeyStorePassPhrase", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The custom identity keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is \"Custom Stores\".</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>IdentityKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>CustomIdentityKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>CustomIdentityKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>CustomIdentityKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getIdentityKeyStorePassPhraseEncrypted")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityKeyStorePassPhraseEncrypted")) {
         getterName = "getIdentityKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("IdentityKeyStorePassPhraseEncrypted", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("IdentityKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The identity keystore's encrypted passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is \"Custom Stores\".</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method. </p>  <p>To compare a password that a user enters with the encrypted value of this attribute, use the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Interoperate")) {
         getterName = "getInteroperate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInteroperate";
         }

         currentResult = new PropertyDescriptor("Interoperate", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("Interoperate", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this local Tuxedo access point interoperates with remote Tuxedo access points that are based upon Tuxedo release 6.5. If this value is set to <code>Yes</code>, the local Tuxedo access point interoperates with a Tuxedo 6.5 domain.</p> ");
         setPropertyDescriptorDefault(currentResult, "No");
         currentResult.setValue("legalValues", new Object[]{"Yes", "No"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAlive")) {
         getterName = "getKeepAlive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAlive";
         }

         currentResult = new PropertyDescriptor("KeepAlive", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("KeepAlive", currentResult);
         currentResult.setValue("description", "<p>Return value tells whether this local Tuxedo access point is configured with Application Level Keep Alive, and it maximum idle time value before wait timer start ticking.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepAliveWait")) {
         getterName = "getKeepAliveWait";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepAliveWait";
         }

         currentResult = new PropertyDescriptor("KeepAliveWait", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("KeepAliveWait", currentResult);
         currentResult.setValue("description", "<p>Return value that tells whether this local Tuxedo access point requires the acknowledgement of Application Level Keep Alive, and how long it will wait without receiving acknowledgement before declare the connection is inaccessible.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyStoresLocation")) {
         getterName = "getKeyStoresLocation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyStoresLocation";
         }

         currentResult = new PropertyDescriptor("KeyStoresLocation", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("KeyStoresLocation", currentResult);
         currentResult.setValue("description", "<p>Provides the configuration rule to be used for finding Local Access Point's identity key store and trust key store. In plain text, it contains information on where the identity key store and trust key store are configured.  When KeyStoreLocation is configured with <code>WLS Store</code>, WTC uses configuration information from the WLS Key Stores configuration.  Otherwise, it uses the key stores information configured in the Local Access Point.</p> ");
         setPropertyDescriptorDefault(currentResult, "Custom Stores");
         currentResult.setValue("legalValues", new Object[]{"WLS Stores", "Custom Stores"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxEncryptBits")) {
         getterName = "getMaxEncryptBits";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxEncryptBits";
         }

         currentResult = new PropertyDescriptor("MaxEncryptBits", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("MaxEncryptBits", currentResult);
         currentResult.setValue("description", "<p>The maximum encryption key length (in bits) this local Tuxedo access point uses when establishing a session connection. A value of <code>0</code> indicates no encryption is used.</p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li>The MaxEncryptBits value must be greater than or equal to the MinEncrypBits value.</li> <li>A MaxEncryptBits of <code>40</code> can be used only with domains running Tuxedo 7.1 or higher.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "128");
         currentResult.setValue("secureValue", "128");
         currentResult.setValue("legalValues", new Object[]{"0", "40", "56", "128", "256"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetries")) {
         getterName = "getMaxRetries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetries";
         }

         currentResult = new PropertyDescriptor("MaxRetries", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("MaxRetries", currentResult);
         currentResult.setValue("description", "<p>The maximum number of times that this local Tuxedo access point tries to establish a session connection to remote Tuxedo access points. Use this value only when Connection Policy is set to <code>ON_STARTUP</code>.</p>  <p><b>Range of Values:</b> Between <code>0</code> and a positive 64 bit integer.</p>  <p><i>Note:</i> Use the minimum value to disable the retry mechanism. Use the maximum value to try until a connection is established.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinEncryptBits")) {
         getterName = "getMinEncryptBits";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinEncryptBits";
         }

         currentResult = new PropertyDescriptor("MinEncryptBits", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("MinEncryptBits", currentResult);
         currentResult.setValue("description", "<p>The minimum encryption key length (in bits) this local Tuxedo access point uses when establishing a session connection. A value of <code>0</code> indicates no encryption is used.</p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li>The MinEncrypBits value must be less than or equal to the MaxEncrypBits value.</li> <li>A MinEncrypBits value of <code>40</code> can be used only with domains running Tuxedo 7.1 or higher.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("secureValue", "40");
         currentResult.setValue("legalValues", new Object[]{"0", "40", "56", "128", "256"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NWAddr")) {
         getterName = "getNWAddr";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNWAddr";
         }

         currentResult = new PropertyDescriptor("NWAddr", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("NWAddr", currentResult);
         currentResult.setValue("description", "<p>The network address and port number of this local Tuxedo access point.</p> Specify the address in one of the following formats: <ul> <li>TCP/IP address in the format <code>//hostname:port_number</code> or <code>//#.#.#.#:port_number</code>.</li> <li>SDP address in the format <code>sdp://hostname:port_number</code> or <code>sdp://#.#.#.#:port_number</code>.</li> </ul>  <i>Notes:</i> <ul> <li>If the hostname is used, the access point finds an address for hostname using the local name resolution facilities (usually DNS). If dotted decimal format is used, each # should be a number from 0 to 255. This dotted decimal number represents the IP address of the local machine. The port_number is the TCP/SDP port number at which the access point listens for incoming requests.</li>  <li>If SDP format address is specified, the transport protocol for this access point is SDP instead of TCP. This feature is only available when WTC and Tuxedo domain gateway are both deployed on Oracle Exalogic platform. Requires Tuxedo 11gR1PS2 and higher.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "//localhost:8901");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrivateKeyAlias")) {
         getterName = "getPrivateKeyAlias";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrivateKeyAlias";
         }

         currentResult = new PropertyDescriptor("PrivateKeyAlias", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("PrivateKeyAlias", currentResult);
         currentResult.setValue("description", "<p>The string alias used to store and retrieve the Local Tuxedo access point's private key in the keystore. This private key is associated with the Local Tuxedo access point's digital certificate.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrivateKeyPassPhrase")) {
         getterName = "getPrivateKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrivateKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("PrivateKeyPassPhrase", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("PrivateKeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The passphrase used to retrieve the server's private key from the keystore. This passphrase is assigned to the private key when it is generated.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>PrivateKeyPassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted passphrase as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>PrivateKeyPassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol>  <p>Using this attribute (<code>PrivateKeyPassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted passphrase) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>getPrivateKeyPassPhraseEncrypted</code>.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrivateKeyPassPhraseEncrypted")) {
         getterName = "getPrivateKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrivateKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("PrivateKeyPassPhraseEncrypted", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("PrivateKeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted passphrase used to retrieve the Local Tuxedo access point's private key from the keystore. This passphrase is assigned to the private key when it is generated.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryInterval")) {
         getterName = "getRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryInterval";
         }

         currentResult = new PropertyDescriptor("RetryInterval", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("RetryInterval", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that this local Tuxedo access point waits between automatic connection attempts to remote Tuxedo access points. Use this value only when Connection Policy is set to <code>ON_STARTUP</code>.</p>  <p><b>Range of Values:</b> Between 0 and a positive 32-bit integer.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(60L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLProtocolVersion")) {
         getterName = "getSSLProtocolVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSSLProtocolVersion";
         }

         currentResult = new PropertyDescriptor("SSLProtocolVersion", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("SSLProtocolVersion", currentResult);
         currentResult.setValue("description", "<p>The protocol version of the SSL connection this local access point uses when establishing a SSL connection. </p>  <p style=\"font-weight: bold\">Value Restrictions:</p>  <ul> <li> TLSv1.0 </li> <li> TLSv1.1 </li> <li> TLSv1.2 </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "TLSv1.2");
         currentResult.setValue("legalValues", new Object[]{"TLSv1.0", "TLSv1.1", "TLSv1.2"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Security")) {
         getterName = "getSecurity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurity";
         }

         currentResult = new PropertyDescriptor("Security", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("Security", currentResult);
         currentResult.setValue("description", "<p>The type of application security enforced.</p>  <p>The types of security are:</p>  <ul> <li>NONE: No security is used.</li> <li>APP_PW: Password security is enforced when a connection is established from a remote domain. The application password is defined in the WTCResourcesMBean.</li> <li>DM_PW: Domain password security is enforced when a connection is established from a remote domain. The domain password is defined in the WTCPasswordsMBean.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "NONE");
         currentResult.setValue("secureValue", "DM_PW");
         currentResult.setValue("legalValues", new Object[]{"NONE", "APP_PW", "DM_PW"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStoreFileName")) {
         getterName = "getTrustKeyStoreFileName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStoreFileName";
         }

         currentResult = new PropertyDescriptor("TrustKeyStoreFileName", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStoreFileName", currentResult);
         currentResult.setValue("description", "<p>The path and file name of the trust keystore.</p>  <p>The path name must either be absolute or relative to where the server was booted. This file name is only used if KeyStores is \"Custom Stores\".</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStorePassPhrase")) {
         getterName = "getTrustKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("TrustKeyStorePassPhrase", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The trust keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is \"Custom Stores\".</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>TrustKeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>TrustKeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>TrustKeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>TrustKeyStorePassPhraseEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getTrustKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustKeyStorePassPhraseEncrypted")) {
         getterName = "getTrustKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("TrustKeyStorePassPhraseEncrypted", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("TrustKeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>The trust keystore's encrypted passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>This attribute is only used if KeyStores is \"Custom Stores\".</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method. </p>  <p>To compare a password that a user enters with the encrypted value of this attribute, use the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSSL")) {
         getterName = "getUseSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSSL";
         }

         currentResult = new PropertyDescriptor("UseSSL", WTCLocalTuxDomMBean.class, getterName, setterName);
         descriptors.put("UseSSL", currentResult);
         currentResult.setValue("description", "<p>Specifies if the connection initiated or accepted by this Local Tuxedo access point uses SSL on top of its transport layer. Values are:</p>  <ul><li>Off: SSL not used.</li> <li>TwoWay: Mutual Authentication with SSL required.</li> <li>OneWay: Server Authentication with SSL required.</li> </ul>  <p><i>Note:</i> If SDP transport is configured for this access point, the configured value of this attribute is ignored and <code>off</code> is used.</p> ");
         setPropertyDescriptorDefault(currentResult, "Off");
         currentResult.setValue("legalValues", new Object[]{"Off", "TwoWay", "OneWay"});
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
