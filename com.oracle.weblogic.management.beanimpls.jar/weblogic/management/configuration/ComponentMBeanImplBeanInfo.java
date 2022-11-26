package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ComponentMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ComponentMBean.class;

   public ComponentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ComponentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ComponentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("deprecated", "9.0.0.0 in favor of {@link AppDeploymentMBean} ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("A component is a specific type of deployment that is part of a J2EE application (see ApplicationMBean). Typically it is a JAR, WAR, or RAR file that contains application code.  Deploying an application is primarily the sum of its component deployments. Components define the target servers for the application. A target may be a server or cluster. Web Applications may also reference Virtual hosts, which in turn define additional server/cluster targets. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ComponentMBean");
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
         currentResult = new PropertyDescriptor("ActivatedTargets", ComponentMBean.class, getterName, setterName);
         descriptors.put("ActivatedTargets", currentResult);
         currentResult.setValue("description", "<p>List of servers and clusters where this module is currently active. This attribute is valid only for modules deployed via the two phase protocol. Modules deployed with the WLS 6.x deployment protocol do not maintain this attribute.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ApplicationMBean#isTwoPhase")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("Application")) {
         getterName = "getApplication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplication";
         }

         currentResult = new PropertyDescriptor("Application", ComponentMBean.class, getterName, setterName);
         descriptors.put("Application", currentResult);
         currentResult.setValue("description", "<p>The application this component is a part of. This is guaranteed to never be null.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ComponentMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Tags", ComponentMBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Targets", ComponentMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("URI")) {
         getterName = "getURI";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setURI";
         }

         currentResult = new PropertyDescriptor("URI", ComponentMBean.class, getterName, setterName);
         descriptors.put("URI", currentResult);
         currentResult.setValue("description", "<p>A URI that points to the application module, usually on the Administration Server.</p>  <p>URI can only be set if this is a standalone component; the URI for an application is defined in the application's deployment descriptor.</p> * ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ComponentMBean.class, getterName, setterName);
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
      Method mth = ComponentMBean.class.getMethod("addTarget", TargetMBean.class);
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

      mth = ComponentMBean.class.getMethod("removeTarget", TargetMBean.class);
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
         mth = ComponentMBean.class.getMethod("addTag", String.class);
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
         mth = ComponentMBean.class.getMethod("removeTag", String.class);
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
         mth = ComponentMBean.class.getMethod("activated", TargetMBean.class);
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

      mth = ComponentMBean.class.getMethod("refreshDDsIfNeeded", String[].class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ComponentMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ComponentMBean.class.getMethod("restoreDefaultValue", String.class);
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
