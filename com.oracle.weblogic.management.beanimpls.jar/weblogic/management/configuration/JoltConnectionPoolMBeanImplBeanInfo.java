package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JoltConnectionPoolMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JoltConnectionPoolMBean.class;

   public JoltConnectionPoolMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JoltConnectionPoolMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JoltConnectionPoolMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean defines a Jolt connection pool.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JoltConnectionPoolMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] roleObjectArrayGet;
      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("ApplicationPassword")) {
         getterName = "getApplicationPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationPassword";
         }

         currentResult = new PropertyDescriptor("ApplicationPassword", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("ApplicationPassword", currentResult);
         currentResult.setValue("description", "<p>The application password for this Jolt connection pool. (This is required only when the security level in the Tuxedo domain is <code>USER_AUTH</code>, <code>ACL</code> or <code>MANDATORY_ACL</code>).</p> <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>ApplicationPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>ApplicationPasswordEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>ApplicationPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>ApplicationPasswordEncrypted</code>.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getApplicationPasswordEncrypted()")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationPasswordEncrypted")) {
         getterName = "getApplicationPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("ApplicationPasswordEncrypted", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("ApplicationPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted application password for this connection pool.</p> <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailoverAddresses")) {
         getterName = "getFailoverAddresses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailoverAddresses";
         }

         currentResult = new PropertyDescriptor("FailoverAddresses", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("FailoverAddresses", currentResult);
         currentResult.setValue("description", "<p>The list of Jolt Server Listeners (JSLs) addresses that is used if the connection pool cannot estabilish connections to the Primary Addresses, or if the primary connections fail.</p>  <p>The format of each address is: <code>//hostname:port</code>. Multiple addresses should be separated by commas.</p>  <p>These JSLs need not reside on the same host as the primary JSLs.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyPassPhrase")) {
         getterName = "getKeyPassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyPassPhrase";
         }

         currentResult = new PropertyDescriptor("KeyPassPhrase", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("KeyPassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted identity passphrase.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>KeyPassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>KeyPassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>KeyPassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>KeyPassPhraseEncrypted</code>.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getKeyPassPhraseEncrypted()")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyPassPhraseEncrypted")) {
         getterName = "getKeyPassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyPassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("KeyPassPhraseEncrypted", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("KeyPassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns encrypted identity pass phrase.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyStoreName")) {
         getterName = "getKeyStoreName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyStoreName";
         }

         currentResult = new PropertyDescriptor("KeyStoreName", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("KeyStoreName", currentResult);
         currentResult.setValue("description", "<p>The path and file name of the keystore containing the private key used in SSL mutual authentication.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyStorePassPhrase")) {
         getterName = "getKeyStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("KeyStorePassPhrase", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("KeyStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted identity keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>KeyStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>KeyStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>KeyStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>KeyStorePassPhraseEncrypted</code>.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getKeyStorePassPhraseEncrypted()")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyStorePassPhraseEncrypted")) {
         getterName = "getKeyStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("KeyStorePassPhraseEncrypted", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("KeyStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumPoolSize")) {
         getterName = "getMaximumPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumPoolSize";
         }

         currentResult = new PropertyDescriptor("MaximumPoolSize", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("MaximumPoolSize", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connections that can be made from this Jolt connection pool.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumPoolSize")) {
         getterName = "getMinimumPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinimumPoolSize";
         }

         currentResult = new PropertyDescriptor("MinimumPoolSize", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("MinimumPoolSize", currentResult);
         currentResult.setValue("description", "<p>The minimum number of connections to be added to this Jolt connection pool when WebLogic Server starts.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryAddresses")) {
         getterName = "getPrimaryAddresses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryAddresses";
         }

         currentResult = new PropertyDescriptor("PrimaryAddresses", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("PrimaryAddresses", currentResult);
         currentResult.setValue("description", "<p>The list of addresses for the primary Jolt Server Listeners (JSLs) on the Tuxedo system.</p>  <p>The format of each address is: <code>//hostname:port</code>. Multiple addresses should be separated by commas.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecvTimeout")) {
         getterName = "getRecvTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecvTimeout";
         }

         currentResult = new PropertyDescriptor("RecvTimeout", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("RecvTimeout", currentResult);
         currentResult.setValue("description", "<p>The number of seconds the client waits to receive a response before timing out.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustStoreName")) {
         getterName = "getTrustStoreName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustStoreName";
         }

         currentResult = new PropertyDescriptor("TrustStoreName", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("TrustStoreName", currentResult);
         currentResult.setValue("description", "<p>The path and file name of the keystore containing the trust certificates.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustStorePassPhrase")) {
         getterName = "getTrustStorePassPhrase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustStorePassPhrase";
         }

         currentResult = new PropertyDescriptor("TrustStorePassPhrase", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("TrustStorePassPhrase", currentResult);
         currentResult.setValue("description", "<p>The encrypted trust keystore's passphrase. If empty or null, then the keystore will be opened without a passphrase.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>TrustStorePassPhraseEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>TrustStorePassPhraseEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>TrustStorePassPhrase</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>TrustStorePassPhraseEncrypted</code>.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getTrustStorePassPhraseEncrypted()")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrustStorePassPhraseEncrypted")) {
         getterName = "getTrustStorePassPhraseEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrustStorePassPhraseEncrypted";
         }

         currentResult = new PropertyDescriptor("TrustStorePassPhraseEncrypted", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("TrustStorePassPhraseEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns encrypted pass phrase defined when creating the keystore.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserName";
         }

         currentResult = new PropertyDescriptor("UserName", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "<p>A user name that applications specify to connect to this Jolt connection pool. If Security Context is enabled, this name must be the name of an authorized Tuxedo user. (Specifying a Tuxedo user name is required if the Tuxedo authentication level is <code>USER_AUTH</code>.)</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserPassword")) {
         getterName = "getUserPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserPassword";
         }

         currentResult = new PropertyDescriptor("UserPassword", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("UserPassword", currentResult);
         currentResult.setValue("description", "<p>The user password for this Jolt connection pool.</p> <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>UserPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>UserPasswordEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>UserPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>UserPasswordEncrypted</code>.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getUserPasswordEncrypted()")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserPasswordEncrypted")) {
         getterName = "getUserPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("UserPasswordEncrypted", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("UserPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The user password for this connection pool.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserRole")) {
         getterName = "getUserRole";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserRole";
         }

         currentResult = new PropertyDescriptor("UserRole", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("UserRole", currentResult);
         currentResult.setValue("description", "<p>The Tuxedo user role for this Jolt connection pool. (This is required only when the security level in the Tuxedo domain is <code>USER_AUTH</code>, <code>ACL</code>, or <code>MANDATORY_ACL</code>).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityContextEnabled")) {
         getterName = "isSecurityContextEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityContextEnabled";
         }

         currentResult = new PropertyDescriptor("SecurityContextEnabled", JoltConnectionPoolMBean.class, getterName, setterName);
         descriptors.put("SecurityContextEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this Jolt connection pool passes the connection pool's security context (user name, password and other information) from the WebLogic Server user to the Tuxedo domain.</p>  <p>If you enable the connection pool to pass the security context, you must start the Jolt Service Handler (JSH) with the <code>-a</code> option. When the JSH gets a message with the caller's identity, it calls <code>impersonate_user()</code> to get the appkey for the user. JSH caches the appkey, so the next time the caller makes a request, the appkey is retrieved from the cache and the request is forwarded to the service. A cache is maintained by each JSH, which means that there will be a cache maintained for all the session pools connected to the same JSH.</p>  <p>You must enable Security Context if Tuxedo requires secured connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JoltConnectionPoolMBean.class.getMethod("addPrimaryAddress", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("address", "The feature to be added to the PrimaryAddress attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the PrimaryAddress attribute of the JoltConnectionPoolMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "PrimaryAddresses");
      }

      mth = JoltConnectionPoolMBean.class.getMethod("removePrimaryAddress", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("address", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "PrimaryAddresses");
      }

      mth = JoltConnectionPoolMBean.class.getMethod("addFailoverAddress", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("address", "The feature to be added to the FailoverAddress attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a feature to the FailoverAddress attribute of the JoltConnectionPoolMBean object</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "FailoverAddresses");
      }

      mth = JoltConnectionPoolMBean.class.getMethod("removeFailoverAddress", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("address", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "FailoverAddresses");
      }

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
