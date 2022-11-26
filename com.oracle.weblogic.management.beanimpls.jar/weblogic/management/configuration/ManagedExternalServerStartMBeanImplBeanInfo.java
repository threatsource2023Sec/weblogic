package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ManagedExternalServerStartMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedExternalServerStartMBean.class;

   public ManagedExternalServerStartMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedExternalServerStartMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ManagedExternalServerStartMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("since", "10.3.4.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean is used to configure the attributes necessary to start up a server on a remote machine. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ManagedExternalServerStartMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("Arguments")) {
         getterName = "getArguments";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArguments";
         }

         currentResult = new PropertyDescriptor("Arguments", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("Arguments", currentResult);
         currentResult.setValue("description", "<p>The arguments to use when starting this server.</p>  <p>These are the first arguments appended immediately after <code>java</code> portion of the startup command. For example, you can set Java heap memory or specify any <code>weblogic.nodemanager.server.provider.WeblogicCacheServer</code> option.</p>  <p>Separate arguments with a space.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("BeaHome")) {
         getterName = "getBeaHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBeaHome";
         }

         currentResult = new PropertyDescriptor("BeaHome", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("BeaHome", currentResult);
         currentResult.setValue("description", "<p>The BEA home directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the directory on the Node Manager machine under which all of Oracle's BEA products were installed. For example, <code>c:&#92Oracle&#92Middleware&#92</code>.</p> ");
         currentResult.setValue("deprecated", "12.1.3.0 replaced by #getMWHome ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("BootProperties")) {
         getterName = "getBootProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("BootProperties", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("BootProperties", currentResult);
         currentResult.setValue("description", "<p>Get the boot properties to be used for a server</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassPath")) {
         getterName = "getClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassPath";
         }

         currentResult = new PropertyDescriptor("ClassPath", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("ClassPath", currentResult);
         currentResult.setValue("description", "<p>The classpath (path on the machine running Node Manager) to use when starting this server.</p>  <p>If you need to add user classes to the classpath, in addition you will need to add the following: <code>FEATURES_HOME/weblogic.server.modules.coherence.server_10.3.4.0.jar:COHERENCE_HOME/lib/coherence.jar</code></p>  <p>where <code>FEATURES_HOME</code> is the features directory (typically <code>$MW_HOME/modules/features</code>) and <code>COHERENCE_HOME</code> the coherence directory (typically <code>$MW_HOME/coherence_3.6</code>) on the Node Manager machine. If you do not specify a classpath the above will be used automatically. </p>  <p>The operating system determines which character separates path elements. On Windows, use a semicolon (;). On UNIX a colon (:).</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("JavaHome")) {
         getterName = "getJavaHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaHome";
         }

         currentResult = new PropertyDescriptor("JavaHome", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("JavaHome", currentResult);
         currentResult.setValue("description", "<p>The Java home directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the parent directory of the JDK's <code>bin</code> directory. For example, <code>c:&#92;bea&#92;jdk141</code>.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("JavaVendor")) {
         getterName = "getJavaVendor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaVendor";
         }

         currentResult = new PropertyDescriptor("JavaVendor", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("JavaVendor", currentResult);
         currentResult.setValue("description", "<p>The Java Vendor value to use when starting this server. </p> <p> If the server is part of a cluster and configured for automatic migration across possibly different platforms with different vendors providing the JDKs, then, both JavaVendor and JavaHome should be set in the generated configuration file instead.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MWHome")) {
         getterName = "getMWHome";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMWHome";
         }

         currentResult = new PropertyDescriptor("MWHome", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("MWHome", currentResult);
         currentResult.setValue("description", "<p>The MWHome directory (path on the machine running Node Manager) to use when starting this server.</p>  <p>Specify the directory on the Node Manager machine under which all of Oracle's MW products were installed. For example, <code>c:&#92;Oracle&#92;Middleware&#92;</code>.</p> ");
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

         currentResult = new PropertyDescriptor("Name", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("RootDirectory")) {
         getterName = "getRootDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRootDirectory";
         }

         currentResult = new PropertyDescriptor("RootDirectory", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("RootDirectory", currentResult);
         currentResult.setValue("description", "<p>The directory that this server uses as its root directory. This directory must be on the computer that hosts the Node Manager. If you do not specify a Root Directory value, the domain directory is used by default. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("StartupProperties")) {
         getterName = "getStartupProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("StartupProperties", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("StartupProperties", currentResult);
         currentResult.setValue("description", "<p>Get the boot properties to be used for a server</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ManagedExternalServerStartMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ManagedExternalServerStartMBean.class, getterName, setterName);
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
         mth = ManagedExternalServerStartMBean.class.getMethod("addTag", String.class);
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
         mth = ManagedExternalServerStartMBean.class.getMethod("removeTag", String.class);
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
      Method mth = ManagedExternalServerStartMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = ManagedExternalServerStartMBean.class.getMethod("restoreDefaultValue", String.class);
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
