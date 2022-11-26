package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SecureModeMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SecureModeMBean.class;

   public SecureModeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SecureModeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SecureModeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Controls the behavior of Secure Mode in the current WebLogic Server domain. Attributes control whether secure mode is enabled and control the validation that is performed during startup.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SecureModeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SecureModeMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", SecureModeMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", SecureModeMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("RestrictiveJMXPolicies")) {
         getterName = "isRestrictiveJMXPolicies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestrictiveJMXPolicies";
         }

         currentResult = new PropertyDescriptor("RestrictiveJMXPolicies", SecureModeMBean.class, getterName, setterName);
         descriptors.put("RestrictiveJMXPolicies", currentResult);
         currentResult.setValue("description", "<p>Returns whether restrictive policies will be used for JMX authorization.</p>  <p>If secure mode is enabled and restrictive policies are enabled, then the default policies for JMX only allow MBean access to the standard WLS roles (Admin, Deployer, Operator, or Monitor). If changed as part of a non-dynamic activation, then the ServerSecurityRuntimeMBean.resetDefaultPolicies method should also be invoked. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecureModeEnabled")) {
         getterName = "isSecureModeEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecureModeEnabled";
         }

         currentResult = new PropertyDescriptor("SecureModeEnabled", SecureModeMBean.class, getterName, setterName);
         descriptors.put("SecureModeEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns whether the domain will run in secure mode.</p>  <p>In secure mode, the configuration defaults are those recommended for securing a domain. The authorization policies for JNDI and MBean access are more restrictive in secure mode. In addition, WLS will validate the domain configuration and log warnings and errors for any insecure settings.</p>  <p> Secure mode requires the domain to be in production mode. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WarnOnAuditing")) {
         getterName = "isWarnOnAuditing";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWarnOnAuditing";
         }

         currentResult = new PropertyDescriptor("WarnOnAuditing", SecureModeMBean.class, getterName, setterName);
         descriptors.put("WarnOnAuditing", currentResult);
         currentResult.setValue("description", "<p>Returns whether warnings should be logged if auditing not enabled. </p>  <p>If secure mode is enabled and warnings are enabled, then messages will be logged if auditing is not enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WarnOnInsecureApplications")) {
         getterName = "isWarnOnInsecureApplications";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWarnOnInsecureApplications";
         }

         currentResult = new PropertyDescriptor("WarnOnInsecureApplications", SecureModeMBean.class, getterName, setterName);
         descriptors.put("WarnOnInsecureApplications", currentResult);
         currentResult.setValue("description", "<p>Returns whether warnings should be logged if applications are not secure. </p>  <p>If secure mode is enabled and warnings are enabled, then messages will be logged for insecure application elements.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WarnOnInsecureFileSystem")) {
         getterName = "isWarnOnInsecureFileSystem";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWarnOnInsecureFileSystem";
         }

         currentResult = new PropertyDescriptor("WarnOnInsecureFileSystem", SecureModeMBean.class, getterName, setterName);
         descriptors.put("WarnOnInsecureFileSystem", currentResult);
         currentResult.setValue("description", "<p>Returns whether warnings should be logged if the File System is not secure. </p>  <p>If secure mode is enabled and warnings are enabled, then messages will be logged for insecure file system setting.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WarnOnInsecureSSL")) {
         getterName = "isWarnOnInsecureSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWarnOnInsecureSSL";
         }

         currentResult = new PropertyDescriptor("WarnOnInsecureSSL", SecureModeMBean.class, getterName, setterName);
         descriptors.put("WarnOnInsecureSSL", currentResult);
         currentResult.setValue("description", "<p>Returns whether warnings should be logged if the SSL configuration is not secure. </p>  <p>If secure mode is enabled and warnings are enabled, then messages will be logged for insecure SSL configuration settings.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WarnOnJavaSecurityManager")) {
         getterName = "isWarnOnJavaSecurityManager";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWarnOnJavaSecurityManager";
         }

         currentResult = new PropertyDescriptor("WarnOnJavaSecurityManager", SecureModeMBean.class, getterName, setterName);
         descriptors.put("WarnOnJavaSecurityManager", currentResult);
         currentResult.setValue("description", "<p>Returns whether warnings should be logged if the Java Security Manager is not enabled. </p>  <p>If secure mode is enabled and warning is enabled, then a messages will be logged if the Java Security Manager is not enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
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
         mth = SecureModeMBean.class.getMethod("addTag", String.class);
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
         mth = SecureModeMBean.class.getMethod("removeTag", String.class);
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
      Method mth = SecureModeMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = SecureModeMBean.class.getMethod("restoreDefaultValue", String.class);
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
