package weblogic.management.deploy.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;

public class AppRuntimeStateRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AppRuntimeStateRuntimeMBean.class;

   public AppRuntimeStateRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AppRuntimeStateRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.AppRuntimeStateRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("Provides access to runtime state for deployed applications. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.AppRuntimeStateRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationIds")) {
         getterName = "getApplicationIds";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationIds", AppRuntimeStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationIds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.6.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentConfigOverridden")) {
         getterName = "isDeploymentConfigOverridden";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentConfigOverridden", AppRuntimeStateRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentConfigOverridden", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.6.0");
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
      Method mth = AppRuntimeStateRuntimeMBean.class.getMethod("isAdminMode", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", "is the application id "), createParameterDescriptor("target", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates if the application should only be available through the administration port. This is the desired state of the application. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("isActiveVersion", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", "is the application id ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates if the application is the active version, the one that new sessions will use. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getRetireTimeMillis", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The time when the application will be retired. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getRetireTimeoutSeconds", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The amount of time the application is given to retire. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getIntendedState", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The state the application should be in. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getIntendedState", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The state the application should be in on a specific target. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getCurrentState", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("target", "logical target where the app is deployed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Aggregate state for the application. This is defined as the most advanced state of the application's modules on the named target. ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.6.0", (String)null, this.targetVersion)) {
         mth = AppRuntimeStateRuntimeMBean.class.getMethod("getCurrentStateOnClusterSnapshot", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appId", "Id of the application "), createParameterDescriptor("clusterName", "Name of the cluster ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.6.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Aggregate state for the application on the currently active snapshot of the cluster. If appId is not found or the clusterName does not exist, null is returned. If the appId is valid and clusterName is valid too, the result includes highest state of appId on any of the cluster members that are currently active ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.6.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.6.0", (String)null, this.targetVersion)) {
         mth = AppRuntimeStateRuntimeMBean.class.getMethod("getCurrentStateOnDemand", String.class, String.class, Long.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("Inferred", "appId Id of the MSI-D style application "), createParameterDescriptor("target", "Name of Target (Cluster or Server) "), createParameterDescriptor("timeout", "amount of time in milliseconds before the command times out. A value of 0 will wait indefinitely ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.6.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This API will query Application States for an MSI-D style app from specified Target on-demand. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.6.0");
         }
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getMultiVersionStateOnDemand", String[].class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configuredIds", "A list of application ids or library's deployment ids as configured in config.xml. This is the list for which state is being requested "), createParameterDescriptor("timeout", "amount of time in milliseconds before the command times out. Though not recommended, a value of 0 may be used to wait indefinitely ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>For each configured ids provided, identify all inferred ids on each of managed server to which the configured ids are targeted.</p>  <p>Admin Server contacts all relevant servers identified by target on demand. Some of the managed servers might be slow in responding or may not respond at all. The use of timeout ensures an upper limit on the time Admin Server will wait for response from managed servers. Though not recommended, a value of 0 timeout may be used to indefinitely wait for response from all managed servers. Admin Server only contacts the managed server that are known to be running at this time.</p>  <p>This result also includes servers that did not respond. But it does not include servers that were known to be shutdown at the time of request and were never contacted.</p>  <p>Here is an example result:</p> <code> ({\"landscapedesign#V2.0\", \"soilmanagement\"}, 100L) &lt;multi-version-state xmlns=\"http://xmlns.oracle.com/weblogic/multi-version-state\" version=\"1.0\"&gt; &lt;unresponsive&gt; &lt;target&gt;ms5&lt;/target&gt; &lt;target&gt;ms10&lt;/target&gt; &lt;/unresponsive&gt; &lt;configured-id id=\"landscapedesign#V2.0\"&gt; &lt;inferred-id id=\"landscapedesign#V2.0.2\"&gt; &lt;state value=\"STATE_ACTIVE\"&gt; &lt;target&gt;ms1&lt;/target&gt; &lt;target&gt;ms2&lt;/target&gt; &lt;target&gt;ms3&lt;/target&gt; &lt;target&gt;ms4&lt;/target&gt; &lt;/state&gt; &lt;/inferred-id&gt; &lt;/configured-id&gt; &lt;configured-id id=\"soilmanagement\"&gt; &lt;inferred-id id=\"soilmanagement#1\"&gt; &lt;state value=\"STATE_ACTIVE\"/&gt; &lt;target&gt;ms1&lt;/target&gt; &lt;target&gt;ms2&lt;/target&gt; &lt;target&gt;ms3&lt;/target&gt; &lt;target&gt;ms4&lt;/target&gt; &lt;target&gt;ms6&lt;/target&gt; &lt;target&gt;ms7&lt;/target&gt; &lt;target&gt;ms8&lt;/target&gt; &lt;target&gt;ms9&lt;/target&gt; &lt;/state&gt; &lt;/inferred-id&gt; &lt;/configured-id&gt; &lt;/multi-version-state&gt; </code> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getModuleIds", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Names of the modules contained in the application. This does not include submodules. ");
         currentResult.setValue("role", "operation");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", seeObjectArray);
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getSubmoduleIds", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Submodules associated with this module. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.deploy.api.shared.WebLogicModuleType")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getModuleType", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates the type of module: EAR, WAR, etc. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.deploy.api.shared.WebLogicModuleType")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getCurrentState", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null), createParameterDescriptor("target", "logical target where module is deployed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Aggregate state for the module. This is defined as the most advanced state of the module on all servers associated with the named target. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getModuleTargets", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Logical targets where the module is deployed. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getCurrentState", String.class, String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null), createParameterDescriptor("subModuleId", (String)null), createParameterDescriptor("target", "logical target where module is deployed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Aggregate state for a submodule. This is defined as the most advanced state of the submodule on all servers associated with the named target. ");
         currentResult.setValue("role", "operation");
      }

      mth = AppRuntimeStateRuntimeMBean.class.getMethod("getModuleTargets", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appid", (String)null), createParameterDescriptor("moduleid", (String)null), createParameterDescriptor("subModuleId", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Logical targets where the submodule is deployed. ");
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
