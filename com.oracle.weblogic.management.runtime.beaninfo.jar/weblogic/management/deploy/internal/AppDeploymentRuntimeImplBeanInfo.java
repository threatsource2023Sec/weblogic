package weblogic.management.deploy.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.AppDeploymentRuntimeMBean;

public class AppDeploymentRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AppDeploymentRuntimeMBean.class;

   public AppDeploymentRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AppDeploymentRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.AppDeploymentRuntimeImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.6.0");
      beanDescriptor.setValue("notificationTranslator", "weblogic.management.deploy.internal.AppDeploymentRuntimeNotificationTranslator");
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("<p>This MBean provides deployment operations for an application. Currently only start and stop are supported. In the future, this MBean may be enhanced with operations to support deployment applications to the domain as well as extended WLS deployment features such as production redeployment and partial deployment of modules in an enterprise application.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.AppDeploymentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", AppDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>The application's name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationVersion")) {
         getterName = "getApplicationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationVersion", AppDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationVersion", currentResult);
         currentResult.setValue("description", "<p>The application's version identifier.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Modules")) {
         getterName = "getModules";
         setterName = null;
         currentResult = new PropertyDescriptor("Modules", AppDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Modules", currentResult);
         currentResult.setValue("description", "<p>The list of modules for the application. These modules can be used in module level targeting.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", AppDeploymentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The name of the partition the application is in.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
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
      Method mth = AppDeploymentRuntimeMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Start the application using the default options and configured targets. This is a synchronous operation that returns when the start operation has completed. The default options are clusterDeploymentTimeout: 3600000 milliseconds, gracefulIgnoreSessions: false, gracefulProductionToAdmin: false, retireGracefully: true, retireTimeout: no timeout, adminMode: false, timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("start", String[].class, Properties.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to start the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be started on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Start the application in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("stop");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Stop the application using the default options and configured targets. This is a synchronous operation that returns when the stop operation has completed. The default options are clusterDeploymentTimeout: 3600000 milliseconds, gracefulIgnoreSessions: false, gracefulProductionToAdmin: false, retireGracefully: true, retireTimeout: no timeout, adminMode: false, timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("stop", String[].class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to stop the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be stoped on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Stop the application in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("undeploy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undeploy the application using the default options and configured targets. This is a synchronous operation that returns when the undeploy operation has completed. The default options are clusterDeploymentTimeout: 3600000 milliseconds, gracefulIgnoreSessions: false, gracefulProductionToAdmin: false, retireGracefully: true, retireTimeout: no timeout, adminMode: false, timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("undeploy", String[].class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to undeploy the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be undeployed on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Undeploy the application in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("redeploy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Redeploy the application using the default options and configured targets. This is a synchronous operation that returns when the redeploy operation has completed. The default options are clusterDeploymentTimeout: 3600000 milliseconds, gracefulIgnoreSessions: false, gracefulProductionToAdmin: false, retireGracefully: true, retireTimeout: no timeout, adminMode: false, timeout: no timeout</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("redeploy", String[].class, String.class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to redeploy the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be redeployed on all configured targets. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Redeploy the application in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentRuntimeMBean.class.getMethod("redeploy", String[].class, String.class, String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "The targets on which to redeploy the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be redeployed on all configured targets. "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root diectory for the application or module. May be null. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys,units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Redeploy the application in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("getState", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "the target for the application state ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The state of the application for a target. Notifications will be generated for this attribute on state changes. Valid states are those supported by the {@link weblogic.management.runtime.AppRuntimeStateRuntimeMBean} The notification types are appdeployment.created, appdeployment.deleted, appdeployment.state.new, appdeployment.state.prepared, appdeployment.state.admin, appdeployment.state.active, appdeployment.state.retired, appdeployment.state.failed, appdeployment.state.update.pending, and appdeployment.state.unknown.  The userdata is a Map where the keys are target names and the values are the application state for that target.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentRuntimeMBean.class.getMethod("update", String[].class, String.class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("targets", "represent the targets on which to update the application. "), createParameterDescriptor("plan", "The deployment plan containing the new deployment configuration information. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. May be null. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.   All other errors are reported via the returned DeploymentProgressObject.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Allows for updating an application configuration using an changed deployment plan.</p>  <p>A configuration update is equivalent to replacing the application's deployment plan. The deployment plan is redeployed in-place. A new version of the application is not started, even if the new deployment plan has a different version.</p>  <p>The update succeeds only if changes to the deployment plan do not require the application to be restarted. Configuration changes in the new deployment plan must be limited to tuning the application. Changes to resource bindings causes the update to fail. Use {@link #redeploy()} to apply resource binding changes to a production application.</p>  <p>This method targets only root modules. Module level targeting is not supported.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#redeploy()")};
         currentResult.setValue("see", seeObjectArray);
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
