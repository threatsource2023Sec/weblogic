package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class BridgeDestinationCommonMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = BridgeDestinationCommonMBean.class;

   public BridgeDestinationCommonMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BridgeDestinationCommonMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.BridgeDestinationCommonMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents a bridge destination for a messaging bridge instance. Each messaging bridge instance consists of the following destination types:</p>  <ul> <li> <p>Source: The message producing destination. A bridge instance consumes messages from the source destination.</p> </li>  <li> <p>Target: The destination where a bridge instance forwards messages produced by the source destination.</p> </li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.BridgeDestinationCommonMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AdapterJNDIName")) {
         getterName = "getAdapterJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdapterJNDIName";
         }

         currentResult = new PropertyDescriptor("AdapterJNDIName", BridgeDestinationCommonMBean.class, getterName, setterName);
         descriptors.put("AdapterJNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of the adapter used to communicate with the specified destination.</p>  <p>This name is specified in the adapter's deployment descriptor file and is used by the WebLogic Server Connector container to bind the adapter in WebLogic Server JNDI.</p> ");
         setPropertyDescriptorDefault(currentResult, "eis.jms.WLSConnectionFactoryJNDIXA");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Classpath")) {
         getterName = "getClasspath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClasspath";
         }

         currentResult = new PropertyDescriptor("Classpath", BridgeDestinationCommonMBean.class, getterName, setterName);
         descriptors.put("Classpath", currentResult);
         currentResult.setValue("description", "<p>The <tt>CLASSPATH</tt> of the bridge destination.</p>  <ul> <li> <p>Used mainly to connect to WebLogic Server 6.0 or earlier.</p> </li>  <li> <p>When connecting to a destination that is running on WebLogic Server 6.0 or earlier, the bridge destination must supply a <tt>CLASSPATH</tt> that indicates the locations of the classes for the earlier WebLogic Server implementation.</p> </li>  </ul> ");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("deprecated", "- no longer support interoperability with WLS 5.1 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserName";
         }

         currentResult = new PropertyDescriptor("UserName", BridgeDestinationCommonMBean.class, getterName, setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "<p>The optional user name the adapter uses to access the bridge destination.</p>  <p>All operations on the specified destination are done using this user name and the corresponding password. Therefore, the User Name/Password for the source and target destinations must have permission to the access the underlying destinations in order for the messaging bridge to work.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserPassword")) {
         getterName = "getUserPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserPassword";
         }

         currentResult = new PropertyDescriptor("UserPassword", BridgeDestinationCommonMBean.class, getterName, setterName);
         descriptors.put("UserPassword", currentResult);
         currentResult.setValue("description", "<p>The user password that the adapter uses to access the bridge destination.</p>  <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol><li>Retrieves the value of the <code>UserPasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol><li>Encrypts the value.</li> <li>Sets the value of the <code>UserPasswordEncrypted</code> attribute to the encrypted value.</li> </ol> <p>Using this attribute (<code>UserPassword</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>UserPasswordEncrypted</code>.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getUserPasswordEncrypted")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserPasswordEncrypted")) {
         getterName = "getUserPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("UserPasswordEncrypted", BridgeDestinationCommonMBean.class, getterName, setterName);
         descriptors.put("UserPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted user password that the adapter uses to access the bridge destination.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
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
