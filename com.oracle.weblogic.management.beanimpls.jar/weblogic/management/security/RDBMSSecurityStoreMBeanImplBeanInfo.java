package weblogic.management.security;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class RDBMSSecurityStoreMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RDBMSSecurityStoreMBean.class;

   public RDBMSSecurityStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RDBMSSecurityStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.RDBMSSecurityStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.security");
      String description = (new String("<p>The MBean that represents configuration attributes for a RDBMS security store. It is used to specify the required and optional properties for connecting to a RDBMS back-end store.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.RDBMSSecurityStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionProperties")) {
         getterName = "getConnectionProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionProperties";
         }

         currentResult = new PropertyDescriptor("ConnectionProperties", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("ConnectionProperties", currentResult);
         currentResult.setValue("description", "<p>The JDBC driver specific connection parameters. This attribute is a comma-delimited list of key-value properties to pass to the driver for configuration of JDBC connection pool, in the form of <code><i>xx</i>Key=<i>xx</i>Value, <i>xx</i>Key=<i>xx</i>Value</code>.</p>  <p>The syntax of the attribute will be validated and an <code>InvalidAttributeValueException</code> is thrown if the check failed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionURL")) {
         getterName = "getConnectionURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionURL";
         }

         currentResult = new PropertyDescriptor("ConnectionURL", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("ConnectionURL", currentResult);
         currentResult.setValue("description", "<p>The URL of the database to which to connect. The format of the URL varies by JDBC driver.</p>  <p>The URL is passed to the JDBC driver to create the physical database connections.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DriverName")) {
         getterName = "getDriverName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDriverName";
         }

         currentResult = new PropertyDescriptor("DriverName", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("DriverName", currentResult);
         currentResult.setValue("description", "<p>The full package name of the JDBC driver class used to create the physical database connections in the connection pool. Note that this driver class must be in the classpath of any server to which it is deployed.</p>  <p>For example:</p> <ul> <li><code>oracle.jdbc.OracleDriver</code></li> <li><code>com.microsoft.sqlserver.jdbc.SQLServerDriver</code></li> </ul> <p>It must be the name of a class that implements the <code>java.sql.Driver</code> interface. The full pathname of the JDBC driver is available in the documentation. </p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSExceptionReconnectAttempts")) {
         getterName = "getJMSExceptionReconnectAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSExceptionReconnectAttempts";
         }

         currentResult = new PropertyDescriptor("JMSExceptionReconnectAttempts", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JMSExceptionReconnectAttempts", currentResult);
         currentResult.setValue("description", "<p>The number of times to attempt to reconnect if the JMS system notifies Kodo of a serious connection error.</p>  <p>The default is 0, and by default the error is logged but ignored. The value cannot be less than 0.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSTopic")) {
         getterName = "getJMSTopic";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSTopic";
         }

         currentResult = new PropertyDescriptor("JMSTopic", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JMSTopic", currentResult);
         currentResult.setValue("description", "The JMS topic to which the Kodo remote commit provider should publish notifications and subscribe for notifications sent from other JVMs. This setting varies depending on the application server in use. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSTopicConnectionFactory")) {
         getterName = "getJMSTopicConnectionFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSTopicConnectionFactory";
         }

         currentResult = new PropertyDescriptor("JMSTopicConnectionFactory", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JMSTopicConnectionFactory", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of a <code>javax.jms.TopicConnectionFactory</code> instance to use for finding JMS topics.</p>  <p>This setting varies depending on the application server in use. Consult the JMS documentation for details about how this parameter should be specified.</p> ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("JNDIPassword")) {
         getterName = "getJNDIPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPassword";
         }

         currentResult = new PropertyDescriptor("JNDIPassword", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JNDIPassword", currentResult);
         currentResult.setValue("description", "<p>The password to authenticate the user defined in the <code>JNDIUsername</code> attribute for Kodo notification.</p> <p>When getting the value of this attribute, WebLogic Server does the following:</p> <ul> <li>Retrieves the value of the <code>JNDIPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String. </li> </ul>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ul> <li>Encrypts the value.</li> <li>Sets the value of the <code>JNDIPasswordEncrypted</code> attribute to the encrypted value.</li> </ul>  <p>Using this attribute (<code>JNDIPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>JNDIPasswordEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getJNDIPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIPasswordEncrypted")) {
         getterName = "getJNDIPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("JNDIPasswordEncrypted", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JNDIPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns the encrypted password to authenticate the user defined in the <code>JNDIUsername</code> attribute for Kodo notification.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIUsername")) {
         getterName = "getJNDIUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIUsername";
         }

         currentResult = new PropertyDescriptor("JNDIUsername", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("JNDIUsername", currentResult);
         currentResult.setValue("description", "The JNDI user name used for Kodo notification. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of this configuration. ");
         setPropertyDescriptorDefault(currentResult, "RDBMSSecurityStore");
         currentResult.setValue("legal", "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NotificationProperties")) {
         getterName = "getNotificationProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotificationProperties";
         }

         currentResult = new PropertyDescriptor("NotificationProperties", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("NotificationProperties", currentResult);
         currentResult.setValue("description", "<p>The comma-delimited list of key-value properties to pass to the JNDI InitialContext on construction, in the form of <code><i>xx</i>Key=<i>xx</i>Value, <i>xx</i>Key=<i>xx</i>Value</code>.</p>  <p>The following are examples of keys:</p> <ul> <li><code>java.naming.provider.url:</code> property for specifying configuration information for the service provider to use. The value of the property should contain a URL string (For example: <code>iiops://localhost:7002</code>).</li> <li><code>java.naming.factory.initial:</code> property for specifying the initial context factory to use. The value of the property should be the fully qualified class name of the factory class that will create an initial context (For example: <code>weblogic.jndi.WLInitialContextFactory</code>).</li> </ul>  <p>When setting the attribute, the syntax of its value is validated, and an <code>InvalidAttributeValueException</code> is thrown if the check fails.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password for the user specified in the <code>Username</code> attribute for connecting to the datastore.</p>  <p>When getting the value of this attribute, WebLogic Server does the following:</p> <ul> <li>Retrieves the value of the <code>PasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ul>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ul> <li>Encrypts the value.</li> <li>Sets the value of the <code>PasswordEncrypted</code> attribute to the encrypted value.</li> </ul>  <p>Note that use of the <code>Password</code> attribute is a potential security risk because the String object that contains the unencrypted password remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>PasswordEncrypted</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>Returns the encrypted password to authenticate the user defined in the <code>Username</code> attribute when connecting to the data store.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Realm")) {
         getterName = "getRealm";
         setterName = null;
         currentResult = new PropertyDescriptor("Realm", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("Realm", currentResult);
         currentResult.setValue("description", "Returns the realm that contains this RDBMS security store. Returns null if this RDBMS security store is not contained by a realm. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Username")) {
         getterName = "getUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsername";
         }

         currentResult = new PropertyDescriptor("Username", RDBMSSecurityStoreMBean.class, getterName, setterName);
         descriptors.put("Username", currentResult);
         currentResult.setValue("description", "The username to use when connecting to the datastore. ");
         currentResult.setValue("legalNull", Boolean.TRUE);
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
