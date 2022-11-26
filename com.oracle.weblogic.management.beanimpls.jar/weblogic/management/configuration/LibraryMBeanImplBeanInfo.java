package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class LibraryMBeanImplBeanInfo extends AppDeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LibraryMBean.class;

   public LibraryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LibraryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.LibraryMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Configuration bean for Libraries.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.LibraryMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AbsoluteAltDescriptorDir")) {
         getterName = "getAbsoluteAltDescriptorDir";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsoluteAltDescriptorDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsoluteAltDescriptorDir", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's alternate descriptor directory on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AbsoluteAltDescriptorPath")) {
         getterName = "getAbsoluteAltDescriptorPath";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsoluteAltDescriptorPath", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsoluteAltDescriptorPath", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's alternate descriptor on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("AbsoluteInstallDir")) {
         getterName = "getAbsoluteInstallDir";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsoluteInstallDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsoluteInstallDir", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's installation root directory on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("AbsolutePlanDir")) {
         getterName = "getAbsolutePlanDir";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsolutePlanDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsolutePlanDir", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's deployment plan directory on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("AbsolutePlanPath")) {
         getterName = "getAbsolutePlanPath";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsolutePlanPath", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsolutePlanPath", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's deployment plan on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("AbsoluteSourcePath")) {
         getterName = "getAbsoluteSourcePath";
         setterName = null;
         currentResult = new PropertyDescriptor("AbsoluteSourcePath", LibraryMBean.class, getterName, setterName);
         descriptors.put("AbsoluteSourcePath", currentResult);
         currentResult.setValue("description", "The fully resolved location of this application's source files on the Administration Server. ");
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("AppMBean")) {
         getterName = "getAppMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("AppMBean", LibraryMBean.class, getterName, setterName);
         descriptors.put("AppMBean", currentResult);
         currentResult.setValue("description", "This will be removed after all server code stops using application and component MBeans. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ApplicationIdentifier")) {
         getterName = "getApplicationIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationIdentifier", LibraryMBean.class, getterName, setterName);
         descriptors.put("ApplicationIdentifier", currentResult);
         currentResult.setValue("description", "<p>The Application Identifier of the application version uniquely identifies the application version across all versions of all applications. If the application is not versioned, the Application Identifier is the same as the application name.</p> ");
      }

      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", LibraryMBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>The name of the application.</p> <p>Note that the name of the current MBean is not the name of the application.</p> ");
      }

      if (!descriptors.containsKey("ConfiguredApplicationIdentifier")) {
         getterName = "getConfiguredApplicationIdentifier";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfiguredApplicationIdentifier";
         }

         currentResult = new PropertyDescriptor("ConfiguredApplicationIdentifier", LibraryMBean.class, getterName, setterName);
         descriptors.put("ConfiguredApplicationIdentifier", currentResult);
         currentResult.setValue("description", "This property is only valid for cloned AppDeployemntMBeans of MSI-D style apps. The value refers to the application id as declared in config .xml ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("DeploymentPlan")) {
         getterName = "getDeploymentPlan";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentPlan", LibraryMBean.class, getterName, setterName);
         descriptors.put("DeploymentPlan", currentResult);
         currentResult.setValue("description", "The contents of this application's deployment plan, returned as a byte[] containing the XML. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("sensitive", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
      }

      if (!descriptors.containsKey("DeploymentPlanExternalDescriptors")) {
         getterName = "getDeploymentPlanExternalDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentPlanExternalDescriptors", LibraryMBean.class, getterName, setterName);
         descriptors.put("DeploymentPlanExternalDescriptors", currentResult);
         currentResult.setValue("description", "A zip file containing the external descriptors referenced in the deployment plan. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("sensitive", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("excludeFromRest", "No default REST mapping for byte[]");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LocalAltDescriptorPath")) {
         getterName = "getLocalAltDescriptorPath";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalAltDescriptorPath", LibraryMBean.class, getterName, setterName);
         descriptors.put("LocalAltDescriptorPath", currentResult);
         currentResult.setValue("description", "The location of this application's alternate descriptor on the current server. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("LocalInstallDir")) {
         getterName = "getLocalInstallDir";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalInstallDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("LocalInstallDir", currentResult);
         currentResult.setValue("description", "The location of this application's installation root directory on the current server. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("LocalPlanDir")) {
         getterName = "getLocalPlanDir";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalPlanDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("LocalPlanDir", currentResult);
         currentResult.setValue("description", "The location of this application's deployment plan directory on the current server. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("LocalPlanPath")) {
         getterName = "getLocalPlanPath";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalPlanPath", LibraryMBean.class, getterName, setterName);
         descriptors.put("LocalPlanPath", currentResult);
         currentResult.setValue("description", "The location of this application's deployment plan on the current server. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("LocalSourcePath")) {
         getterName = "getLocalSourcePath";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalSourcePath", LibraryMBean.class, getterName, setterName);
         descriptors.put("LocalSourcePath", currentResult);
         currentResult.setValue("description", "The location of this application's source files on the current server. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", LibraryMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PlanDir")) {
         getterName = "getPlanDir";
         setterName = null;
         currentResult = new PropertyDescriptor("PlanDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("PlanDir", currentResult);
         currentResult.setValue("description", "<p>The location of this application's configuration area. This directory can contain external descriptor files as specified within the deployment plan document.</p>  <p>Rules:</p> If the plan directory is a relative path, it is resolved relative to InstallDir if InstallDir is not null; otherwise, it is resolved relative to the domain root.  <p>Use AbsolutePlanDir to get a fully resolved value.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getInstallDir"), BeanInfoHelper.encodeEntities("#getAbsolutePlanDir")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PlanPath")) {
         getterName = "getPlanPath";
         setterName = null;
         currentResult = new PropertyDescriptor("PlanPath", LibraryMBean.class, getterName, setterName);
         descriptors.put("PlanPath", currentResult);
         currentResult.setValue("description", "<p>The path to the deployment plan document on the Administration Server.</p> <p>Rules:</p> If the plan path is a relative path, it is resolved relative to PlanDir if PlanDir is not null; otherwise, it is resolved relative to the domain root.  <p>Use AbsolutePlanPath to get a fully resolved value.</p> <p>If there is no plan, this returns no plan specified.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getPlanDir"), BeanInfoHelper.encodeEntities("#getAbsolutePlanPath")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PlanStagingMode")) {
         getterName = "getPlanStagingMode";
         setterName = null;
         currentResult = new PropertyDescriptor("PlanStagingMode", LibraryMBean.class, getterName, setterName);
         descriptors.put("PlanStagingMode", currentResult);
         currentResult.setValue("description", "<p>Specifies whether an application's deployment plan is copied from a source on the Administration Server to the Managed Server's staging area during application preparation. </p> <p>Plan staging mode for an application can only be set the first time the application is deployed. Once the plan staging mode for an application is set, it cannot be changed while the application is configured in the domain. The only way to change the plan staging mode is to undeploy and then redeploy the application.</p> <p>This attribute overrides the server's plan staging mode. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerMBean#getStagingMode")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, AppDeploymentMBean.DEFAULT_STAGE);
         currentResult.setValue("legalValues", new Object[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"});
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("RootStagingDir")) {
         getterName = "getRootStagingDir";
         setterName = null;
         currentResult = new PropertyDescriptor("RootStagingDir", LibraryMBean.class, getterName, setterName);
         descriptors.put("RootStagingDir", currentResult);
         currentResult.setValue("description", "The root directory under which this application is staged. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("SourcePath")) {
         getterName = "getSourcePath";
         setterName = null;
         currentResult = new PropertyDescriptor("SourcePath", LibraryMBean.class, getterName, setterName);
         descriptors.put("SourcePath", currentResult);
         currentResult.setValue("description", "<p>The path to the source of the deployable unit on the Administration Server.</p> <p>Rules:</p> <p>If the source path is relative, it is resolved relative to <code><i>InstallDir/app</i></code> if InstallDir is not null; otherwise, it is resolved relative to the domain root.</p>  <p>Use AbsoluteSourcePath to get a fully resolved value.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getInstallDir"), BeanInfoHelper.encodeEntities("#getAbsoluteSourcePath")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("StagingMode")) {
         getterName = "getStagingMode";
         setterName = null;
         currentResult = new PropertyDescriptor("StagingMode", LibraryMBean.class, getterName, setterName);
         descriptors.put("StagingMode", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a deployment's files are copied from a source on the Administration Server to the Managed Server's staging area during application preparation. </p> <p>Staging mode for an application can only be set the first time the application is deployed. Once the staging mode for an application is set, it cannot be changed while the application is configured in the domain. The only way to change the staging mode is to undeploy and then redeploy the application.</p> <p>This attribute overrides the server's staging mode. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerMBean#getStagingMode")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, AppDeploymentMBean.DEFAULT_STAGE);
         currentResult.setValue("legalValues", new Object[]{AppDeploymentMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"});
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", LibraryMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("VersionIdentifier")) {
         getterName = "getVersionIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("VersionIdentifier", LibraryMBean.class, getterName, setterName);
         descriptors.put("VersionIdentifier", currentResult);
         currentResult.setValue("description", "<p>Uniquely identifies the application version across all versions of the same application.</p> <p>If the application is not versioned, this returns null.</p> ");
      }

      if (!descriptors.containsKey("AutoDeployedApp")) {
         getterName = "isAutoDeployedApp";
         setterName = null;
         currentResult = new PropertyDescriptor("AutoDeployedApp", LibraryMBean.class, getterName, setterName);
         descriptors.put("AutoDeployedApp", currentResult);
         currentResult.setValue("description", "If the application was autodeployed (regardless of whether the application was autodeployed in this session or not) ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", LibraryMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("MultiVersionApp")) {
         getterName = "isMultiVersionApp";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMultiVersionApp";
         }

         currentResult = new PropertyDescriptor("MultiVersionApp", LibraryMBean.class, getterName, setterName);
         descriptors.put("MultiVersionApp", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
         mth = LibraryMBean.class.getMethod("addTag", String.class);
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
         mth = LibraryMBean.class.getMethod("removeTag", String.class);
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
      Method mth = LibraryMBean.class.getMethod("createPlan", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("planPath", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a deployment plan in the specified directory. ");
         currentResult.setValue("role", "operation");
      }

      mth = LibraryMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = LibraryMBean.class.getMethod("createPlan");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a deployment plan in a default directory ");
         currentResult.setValue("role", "operation");
      }

      mth = LibraryMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = LibraryMBean.class.getMethod("getStagingMode", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The staging mode associated with this application, if not explicit from the StagingMode property. This method will throw an unchecked IllegalStateEception if not invoked from within the context of a server. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("role", "operation");
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
