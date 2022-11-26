package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class NodeManagerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = NodeManagerMBean.class;

   public NodeManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NodeManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.NodeManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean is represents a Node Manager that is associated with a machine. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.NodeManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Adapter")) {
         getterName = "getAdapter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdapter";
         }

         currentResult = new PropertyDescriptor("Adapter", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("Adapter", currentResult);
         currentResult.setValue("description", "Gets the node manager client adapter name_version when using a VMM adapter to connect to OVM or other VMM adapter providers ");
         currentResult.setValue("setterDeprecated", "VMM client support is removed since 12.1.2 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdapterName")) {
         getterName = "getAdapterName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdapterName";
         }

         currentResult = new PropertyDescriptor("AdapterName", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("AdapterName", currentResult);
         currentResult.setValue("description", "Gets the node manager client adapter name when using a VMM adapter to connect to OVM or other VMM adapters providers ");
         currentResult.setValue("setterDeprecated", "10.3.4.0 Replaced by {@link weblogic.management.configuration.NodeManagerMBean#getAdapter()} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdapterVersion")) {
         getterName = "getAdapterVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdapterVersion";
         }

         currentResult = new PropertyDescriptor("AdapterVersion", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("AdapterVersion", currentResult);
         currentResult.setValue("description", "Gets the node manager client adapter version when using a VMM adapter to connect to OVM or other VMM adapters providers ");
         currentResult.setValue("setterDeprecated", "10.3.4.0 Replaced by {@link weblogic.management.configuration.NodeManagerMBean#setAdapter(String) setAdapter} ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InstalledVMMAdapters")) {
         getterName = "getInstalledVMMAdapters";
         setterName = null;
         currentResult = new PropertyDescriptor("InstalledVMMAdapters", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("InstalledVMMAdapters", currentResult);
         currentResult.setValue("description", "<p>Gets a list of the names and versions of the installed Virtual Machine Manager (VMM) adapters</p> ");
         currentResult.setValue("deprecated", "VMM client support is removed since 12.1.2 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenAddress";
         }

         currentResult = new PropertyDescriptor("ListenAddress", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The host name or IP address of the NodeManager for the server or clients on the server to use when connecting to the NodeManager instance. </p> ");
         setPropertyDescriptorDefault(currentResult, "localhost");
         currentResult.setValue("secureValue", "127.0.0.1 or ::1");
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setListenPort";
         }

         currentResult = new PropertyDescriptor("ListenPort", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The port number of the NodeManager for the server or clients on the server to use when connecting to the NodeManager instance. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5556));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("NMSocketCreateTimeoutInMillis")) {
         getterName = "getNMSocketCreateTimeoutInMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNMSocketCreateTimeoutInMillis";
         }

         currentResult = new PropertyDescriptor("NMSocketCreateTimeoutInMillis", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("NMSocketCreateTimeoutInMillis", currentResult);
         currentResult.setValue("description", "Returns the timeout value to be used by NodeManagerRuntime when creating a a socket connection to the agent. Default set high as SSH agent may require a high connection establishment time. ");
         setPropertyDescriptorDefault(currentResult, new Integer(180000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.2.0");
      }

      if (!descriptors.containsKey("NMType")) {
         getterName = "getNMType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNMType";
         }

         currentResult = new PropertyDescriptor("NMType", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("NMType", currentResult);
         currentResult.setValue("description", "Returns the node manager type for the server or clients on the server to use when connecting to the NodeManager instance. ");
         setPropertyDescriptorDefault(currentResult, "SSL");
         currentResult.setValue("legalValues", new Object[]{"SSH", "RSH", "Plain", "SSL", "ssh", "rsh", "ssl", "plain"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NodeManagerHome")) {
         getterName = "getNodeManagerHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNodeManagerHome";
         }

         currentResult = new PropertyDescriptor("NodeManagerHome", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("NodeManagerHome", currentResult);
         currentResult.setValue("description", "Returns the node manager home directory that will be used to substitute for the shell command template ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password used by a Node Manager client to connect to the underlying service to which the Node Manager client delegates operations.</p>  <p>When you get the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Retrieves the value of the <code>PasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol> <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Encrypts the value.</li> <li>Sets the value of the <code>PasswordEncrypted</code> attribute to the encrypted value.</li> </ol> <p><b>Caution:</b> Using the (<code>Password</code>) attribute is a potential security risk because the String object (which contains the unencrypted password), remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory. Therefore, you should use the <code>PasswordEncrypted()</code> attribute instead.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPasswordEncrypted")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("setterDeprecated", "VMM client support is removed since 12.1.2 ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted Node Manager client user password.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the <code>encrypt()</code> method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShellCommand")) {
         getterName = "getShellCommand";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShellCommand";
         }

         currentResult = new PropertyDescriptor("ShellCommand", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("ShellCommand", currentResult);
         currentResult.setValue("description", "Returns the local command line to use when invoking SSH or RSH node manager functions. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserName";
         }

         currentResult = new PropertyDescriptor("UserName", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "<p>The Node Manager client user name used to connect to the underlying service to which the client delegates operations. </p> ");
         currentResult.setValue("setterDeprecated", "VMM client support is removed since 12.1.2 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugEnabled")) {
         getterName = "isDebugEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugEnabled";
         }

         currentResult = new PropertyDescriptor("DebugEnabled", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("DebugEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether communication with this Node Manager needs to be debugged. When enabled, connections to the NodeManager from the server or clients on the server will result in more information sent to the server log.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", NodeManagerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
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
         mth = NodeManagerMBean.class.getMethod("addTag", String.class);
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
         mth = NodeManagerMBean.class.getMethod("removeTag", String.class);
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
      Method mth = NodeManagerMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = NodeManagerMBean.class.getMethod("restoreDefaultValue", String.class);
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
