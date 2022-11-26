package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ServerStartMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerStartMBean.class;

   public ServerStartMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerStartMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ServerStartMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean is used to configure the attributes necessary to start up a server on a remote machine. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ServerStartMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Arguments")) {
         getterName = "getArguments";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArguments";
         }

         currentResult = new PropertyDescriptor("Arguments", ServerStartMBean.class, getterName, setterName);
         descriptors.put("Arguments", currentResult);
         currentResult.setValue("description", "<p>The arguments to use when starting this server.</p>  <p>These are the first arguments appended immediately after <code>java</code> portion of the startup command. For example, you can set Java heap memory or specify any <code>weblogic.Server</code> option.</p>  <p>This property should not be used to specify weblogic.management.username or weblogic.management.password as these values will be ignored during server startup.  Instead the username and password properties should be set. This will also enable Node Manager to properly encrypt these values on the Managed Server's machine.</p>  <p>Separate arguments with a space.</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.Arguments property. Node Manager will pass this value to a start script using the JAVA_OPTIONS environment variable.  When issuing a Java command line to start the server, Node Manager will pass the arguments as options.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeaHome")) {
         getterName = "getBeaHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBeaHome";
         }

         currentResult = new PropertyDescriptor("BeaHome", ServerStartMBean.class, getterName, setterName);
         descriptors.put("BeaHome", currentResult);
         currentResult.setValue("description", "<p></p> <p>The BEA home directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the directory on the Node Manager machine under which all of Oracle's BEA products were installed. For example, <code>c:&#92;bea</code>.</p> ");
         currentResult.setValue("deprecated", "12.1.3.0 replaced by ServerStartMBean.getMWHome ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BootProperties")) {
         getterName = "getBootProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("BootProperties", ServerStartMBean.class, getterName, setterName);
         descriptors.put("BootProperties", currentResult);
         currentResult.setValue("description", "<p>Get the boot properties to be used for a server.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.1.0.0");
      }

      if (!descriptors.containsKey("ClassPath")) {
         getterName = "getClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassPath";
         }

         currentResult = new PropertyDescriptor("ClassPath", ServerStartMBean.class, getterName, setterName);
         descriptors.put("ClassPath", currentResult);
         currentResult.setValue("description", "<p>The classpath (path on the machine running Node Manager) to use when starting this server.</p>  <p>At a minimum you will need to specify the following values for the class path option: <code>WL_HOME/server/lib/weblogic_sp.jar;WL_HOME/server/lib/weblogic.jar</code></p>  <p>where <code>WL_HOME</code> is the directory in which you installed WebLogic Server on the Node Manager machine.</p>  <p>The shell environment determines which character you use to separate path elements. On Windows, you typically use a semicolon (;). In a BASH shell, you typically use a colon (:).</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.ClassPath property. Node Manager will pass this value to a start script using the CLASSPATH environment variable.  When issuing a Java command line to start the server, Node Manager will pass -Djava.class.path.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaHome")) {
         getterName = "getJavaHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaHome";
         }

         currentResult = new PropertyDescriptor("JavaHome", ServerStartMBean.class, getterName, setterName);
         descriptors.put("JavaHome", currentResult);
         currentResult.setValue("description", "<p>The Java home directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the parent directory of the JDK's <code>bin</code> directory. For example, <code>c:&#92;bea&#92;jdk141</code>.</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.JavaHome or property.</p> <p>Node Manager will pass this value to a start script using the JAVA_HOME environment variable.  When issuing a Java command line to start the server, Node Manager will use the Java executable from the specified location. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaVendor")) {
         getterName = "getJavaVendor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaVendor";
         }

         currentResult = new PropertyDescriptor("JavaVendor", ServerStartMBean.class, getterName, setterName);
         descriptors.put("JavaVendor", currentResult);
         currentResult.setValue("description", "<p>The Java Vendor value to use when starting this server. </p> <p> If the server is part of a cluster and configured for automatic migration across possibly different platforms with different vendors providing the JDKs, then, both JavaVendor and JavaHome should be set in the generated configuration file instead.</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.JavaVendor property.</p>  <p>Node Manager does not pass this value invoking a Java command line to start the server.  It does pass this value in the environment variable JAVA_VENDOR to the start script. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MWHome")) {
         getterName = "getMWHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMWHome";
         }

         currentResult = new PropertyDescriptor("MWHome", ServerStartMBean.class, getterName, setterName);
         descriptors.put("MWHome", currentResult);
         currentResult.setValue("description", "<p></p> <p>The MWHome directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the directory on the Node Manager machine under which all of Oracle's Middleware products were installed. For example, <code>c:&#92;bea</code>.</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.MWHome property.</p> <p> Node Manager does not pass this value to start scripts. It does specify -Dbea.home when invoking a Java command line to start the server.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ServerStartMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", ServerStartMBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "<p>The password of the username used to boot the server and perform server health monitoring.</p>  <p>As of 8.1 sp4, when you get the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Retrieves the value of the <code>PasswordEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p>When you set the value of this attribute, WebLogic Server does the following:</p> <ol> <li>Encrypts the value.</li> <li>Sets the value of the <code>PasswordEncrypted</code> attribute to the encrypted value.</li> </ol>  <p>Using this attribute (<code>Password</code>) is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory.</p>  <p>Instead of using this attribute, use <code>PasswordEncrypted</code>.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPasswordEncrypted()")};
         currentResult.setValue("see", seeObjectArray);
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

         currentResult = new PropertyDescriptor("PasswordEncrypted", ServerStartMBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted password of the username used to boot the server and perform server health monitoring.</p>  <p>To set this attribute, use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the value. Then set this attribute to the output of the encrypt() method.</p>  <p>To compare a password that a user enters with the encrypted value of this attribute, go to the same WebLogic Server instance that you used to set and encrypt this attribute and use <code>weblogic.management.EncryptionHelper.encrypt()</code> to encrypt the user-supplied password. Then compare the encrypted values.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RootDirectory")) {
         getterName = "getRootDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRootDirectory";
         }

         currentResult = new PropertyDescriptor("RootDirectory", ServerStartMBean.class, getterName, setterName);
         descriptors.put("RootDirectory", currentResult);
         currentResult.setValue("description", "<p>The directory that this server uses as its root directory. This directory must be on the computer that hosts Node Manager. If you do not specify a Root Directory value, the domain directory is used by default. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityPolicyFile")) {
         getterName = "getSecurityPolicyFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityPolicyFile";
         }

         currentResult = new PropertyDescriptor("SecurityPolicyFile", ServerStartMBean.class, getterName, setterName);
         descriptors.put("SecurityPolicyFile", currentResult);
         currentResult.setValue("description", "<p>The security policy file (directory and filename on the machine running Node Manager) to use when starting this server.</p>  <p>This value can also be specified conveniently in the nodemanager .properties file using the weblogic.startup.SecurityPolicyFile property .</p>  <p>When Node Manager is using a start script, the security policy file will be defined in an environment variable, SECURITY_POLICY.  Additionally, when Node Manager is launching the process directly using a Java command, the security policy file will be defined with -Djava.security.policy= </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StartupProperties")) {
         getterName = "getStartupProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("StartupProperties", ServerStartMBean.class, getterName, setterName);
         descriptors.put("StartupProperties", currentResult);
         currentResult.setValue("description", "<p>Get the boot properties to be used for a server.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ServerStartMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Username")) {
         getterName = "getUsername";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUsername";
         }

         currentResult = new PropertyDescriptor("Username", ServerStartMBean.class, getterName, setterName);
         descriptors.put("Username", currentResult);
         currentResult.setValue("description", "<p>The user name to use when booting this server.</p>  <p>The Administration Console inserts the user name that you supplied when you logged in to the console. The Domain Configuration Wizard inserts the user name that you defined when you created the domain.</p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ServerStartMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
         mth = ServerStartMBean.class.getMethod("addTag", String.class);
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
         mth = ServerStartMBean.class.getMethod("removeTag", String.class);
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
      Method mth = ServerStartMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = ServerStartMBean.class.getMethod("restoreDefaultValue", String.class);
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
