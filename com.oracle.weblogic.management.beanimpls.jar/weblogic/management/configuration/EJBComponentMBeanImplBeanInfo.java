package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class EJBComponentMBeanImplBeanInfo extends ComponentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBComponentMBean.class;

   public EJBComponentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBComponentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.EJBComponentMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 in favor of {@link AppDeploymentMBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("The top level interface for all configuration information that WebLogic Server maintains for an EJB module. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.EJBComponentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ActivatedTargets")) {
         getterName = "getActivatedTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivatedTargets", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("ActivatedTargets", currentResult);
         currentResult.setValue("description", "<p>List of servers and clusters where this module is currently active. This attribute is valid only for modules deployed via the two phase protocol. Modules deployed with the WLS 6.x deployment protocol do not maintain this attribute.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ApplicationMBean#isTwoPhase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("Application")) {
         getterName = "getApplication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplication";
         }

         currentResult = new PropertyDescriptor("Application", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("Application", currentResult);
         currentResult.setValue("description", "<p>The application this component is a part of. This is guaranteed to never be null.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ExtraEjbcOptions")) {
         getterName = "getExtraEjbcOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraEjbcOptions";
         }

         currentResult = new PropertyDescriptor("ExtraEjbcOptions", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("ExtraEjbcOptions", currentResult);
         currentResult.setValue("description", "<p>Returns the extra options passed to ejbc during the dynamic ejbc of a jar file. For example: -J-mx128m By default this value is null. If no ExtraEJBCOptions are specified on the EJBComponent, the default will be pulled from the Server.ExtraEJBCOptions.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ExtraRmicOptions")) {
         getterName = "getExtraRmicOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraRmicOptions";
         }

         currentResult = new PropertyDescriptor("ExtraRmicOptions", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("ExtraRmicOptions", currentResult);
         currentResult.setValue("description", "<p>The extra options passed to rmic during server-side generation are noted here. The default for this attribute must be null. If no ExtraRmicOptions are specified on the EJBComponent, the default will be pulled from Server.ExtraRmicOptions.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ForceGeneration")) {
         getterName = "getForceGeneration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForceGeneration";
         }

         currentResult = new PropertyDescriptor("ForceGeneration", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("ForceGeneration", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the ForceGeneration is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
      }

      if (!descriptors.containsKey("JavaCompiler")) {
         getterName = "getJavaCompiler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompiler";
         }

         currentResult = new PropertyDescriptor("JavaCompiler", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("JavaCompiler", currentResult);
         currentResult.setValue("description", "<p>The path to the Java compiler to use to compile EJBs (e.g. \"sj\" or \"javac\"). Note: the default must be null. If no JavaCompiler is specified on this specific EJBComponent, the default will be pulled in the following order from - EJBContainerMBean - Server.JavaCompiler.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JavaCompilerPostClassPath")) {
         getterName = "getJavaCompilerPostClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPostClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPostClassPath", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPostClassPath", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the options to append to the Java compiler classpath when you compile Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JavaCompilerPreClassPath")) {
         getterName = "getJavaCompilerPreClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPreClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPreClassPath", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPreClassPath", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the options to prepend to the Java compiler classpath when you compile Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
      }

      if (!descriptors.containsKey("KeepGenerated")) {
         getterName = "getKeepGenerated";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepGenerated";
         }

         currentResult = new PropertyDescriptor("KeepGenerated", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("KeepGenerated", currentResult);
         currentResult.setValue("description", "<p>indicates whether KeepGenerated is enabled and the ejbc source files will be kept.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", EJBComponentMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Tags", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("TmpPath")) {
         getterName = "getTmpPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTmpPath";
         }

         currentResult = new PropertyDescriptor("TmpPath", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("TmpPath", currentResult);
         currentResult.setValue("description", "<p>Return the temporary directory where generated files are stored by ejbc. Deprecated: All EJB compiler output is now stored in the EJBCompilerCache subdirectory of the server staging directory. This directory should not be described as \"temporary\" since removing it would cause the EJB compiler to be rerun as necessary the next time the server is restarted.</p> ");
         setPropertyDescriptorDefault(currentResult, "tmp_ejb");
         currentResult.setValue("deprecated", " ");
      }

      if (!descriptors.containsKey("VerboseEJBDeploymentEnabled")) {
         getterName = "getVerboseEJBDeploymentEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerboseEJBDeploymentEnabled";
         }

         currentResult = new PropertyDescriptor("VerboseEJBDeploymentEnabled", EJBComponentMBean.class, getterName, setterName);
         descriptors.put("VerboseEJBDeploymentEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VerboseEJBDeployment is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, "false");
         currentResult.setValue("deprecated", "Deprecated as of 10.3.3.0 in favor of {@link ServerDebugMBean#getDebugEjbDeployment()} ");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", EJBComponentMBean.class, getterName, setterName);
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
      Method mth = EJBComponentMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = EJBComponentMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = EJBComponentMBean.class.getMethod("addTag", String.class);
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
         mth = EJBComponentMBean.class.getMethod("removeTag", String.class);
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = EJBComponentMBean.class.getMethod("activated", TargetMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Indicates whether component has been activated on a server</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      mth = EJBComponentMBean.class.getMethod("refreshDDsIfNeeded", String[].class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = EJBComponentMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = EJBComponentMBean.class.getMethod("restoreDefaultValue", String.class);
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
