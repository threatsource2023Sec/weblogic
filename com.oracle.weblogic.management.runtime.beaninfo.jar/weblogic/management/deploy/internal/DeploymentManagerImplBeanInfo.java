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
import weblogic.management.runtime.DeploymentManagerMBean;

public class DeploymentManagerImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentManagerMBean.class;

   public DeploymentManagerImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentManagerImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.internal.DeploymentManagerImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.6.0");
      beanDescriptor.setValue("notificationTranslator", "weblogic.management.deploy.internal.DeploymentManagerNotificationTranslator");
      beanDescriptor.setValue("package", "weblogic.management.deploy.internal");
      String description = (new String("<p>This MBean provides deployment operations.</p>  <p>A DeploymentManager object is a stateless interface into the WebLogic Server deployment framework. It currently provides access to the AppDeploymentRuntimeMBeans that allow the user to start and stop deployments. In the future, this MBean may be enhanced with operations to support deployment applications to the domain, as well as extended WebLogic Server deployment features, such as production redeployment and partial deployment of modules in an enterprise application.</p>  <p>This MBean emits notifications when an application is created or removed and when the application state changes.  The notification types are:</p> <ul> <li>appdeployment.created</li> <li>appdeployment.deleted</li> <li>appdeployment.state.new</li> <li>appdeployment.state.prepared</li> <li>appdeployment.state.admin</li> <li>appdeployment.state.active</li> <li>appdeployment.state.retired</li> <li>appdeployment.state.failed</li> <li>appdeployment.state.update.pending</li> <li>appdeployment.state.unknown</li> </ul>  <p>The userdata is the object name of the application.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DeploymentManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AppDeploymentRuntimes")) {
         getterName = "getAppDeploymentRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeploymentRuntimes", DeploymentManagerMBean.class, getterName, setterName);
         descriptors.put("AppDeploymentRuntimes", currentResult);
         currentResult.setValue("description", "<p>Provides access to the applications that are deployed in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentProgressObjects")) {
         getterName = "getDeploymentProgressObjects";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentProgressObjects", DeploymentManagerMBean.class, getterName, setterName);
         descriptors.put("DeploymentProgressObjects", currentResult);
         currentResult.setValue("description", "<p>Provides access to the deployment operations that have been performed on this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibDeploymentRuntimes")) {
         getterName = "getLibDeploymentRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("LibDeploymentRuntimes", DeploymentManagerMBean.class, getterName, setterName);
         descriptors.put("LibDeploymentRuntimes", currentResult);
         currentResult.setValue("description", "Provides access to the libraries that are deployed to the domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumDeploymentProgressObjectsCount")) {
         getterName = "getMaximumDeploymentProgressObjectsCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaximumDeploymentProgressObjectsCount";
         }

         currentResult = new PropertyDescriptor("MaximumDeploymentProgressObjectsCount", DeploymentManagerMBean.class, getterName, setterName);
         descriptors.put("MaximumDeploymentProgressObjectsCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of progress objects allowed.</p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentManagerMBean.class.getMethod("removeDeploymentProgressObject", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application that the progress object is for ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a progress object.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "DeploymentProgressObjects");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentManagerMBean.class.getMethod("lookupAppDeploymentRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Finds the application deployment runtime MBean for an application.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "AppDeploymentRuntimes");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("lookupAppDeploymentRuntime", String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("deploymentOptions", "Used to specify a partition. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Finds the application deployment runtime MBean for an application based on the deploymentOptions parameter.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "AppDeploymentRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = DeploymentManagerMBean.class.getMethod("lookupLibDeploymentRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("libName", "The name of the library ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find the library deployment runtime MBean for a library. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "LibDeploymentRuntimes");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DeploymentManagerMBean.class.getMethod("deploy", String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the application "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root directory for the application or module. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>A convenience method for distributing and starting an application.</p>  <p>The application source files and deployment plan are distributed to the deployment targets using the default options. This is a synchronous operation that returns when the deploy operation has completed. If distribution is successful, the application is then started on each of the targets.  Successful deployment requires that all targets receive both the archive and deployment plan.</p>  <p>The default options are</p> <ul> <li>clusterDeploymentTimeout: 3600000 milliseconds</li> <li>gracefulIgnoreSessions: false</li> <li>gracefulProductionToAdmin: false</li> <li>retireGracefully: true</li> <li>retireTimeout: no timeout</li> <li>adminMode: false</li> <li>timeout: no timeout</li> </ul>  <p>If any modules are currently running, this method immediately restarts those modules using the newer files. Restarting modules in this manner is not the same as redeployment, because session state is not preserved for the active modules.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentManagerMBean.class.getMethod("deploy", String.class, String.class, String[].class, String.class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the application "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root directory for the application or module. "), createParameterDescriptor("targets", "The targets on which to deploy the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be redeployed on all configured targets. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>A convenience method for distributing and starting an application. The application source files and deployment plan are distributed to the deployment targets using the options specified. This is an asynchronous operation that returns immediately. If distribution is successful, the application is then started on each of the targets. Successful deployment requires that all targets receive both the archive and deployment plan.</p>  <p>If any modules are currently running, this method immediately restarts those modules using the newer files. Restarting modules in this manner is not the same as redeployment, because session state is not preserved for the active modules.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentManagerMBean.class.getMethod("distribute", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the application "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root directory for the application or module. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Distribute an application.</p>  <p>The application source files and deployment plan are distributed to the deployment targets using the default options. This is a synchronous operation that returns when the distribute operation has completed.</p>  <p>The default options are:</p> <ul> <li>clusterDeploymentTimeout: 3600000 milliseconds</li> <li>gracefulIgnoreSessions: false</li> <li>gracefulProductionToAdmin: false</li> <li>retireGracefully: true</li> <li>retireTimeout: no timeout</li> <li>adminMode: false,</li> <li>timeout: no timeout</li> </ul> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentManagerMBean.class.getMethod("distribute", String.class, String.class, String[].class, String.class, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the application "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root directory for the application or module. "), createParameterDescriptor("targets", "The targets on which to deploy the application. This would be server names, cluster names, or module names in a similar format to weblogic.Deployer (i.e. module1@server1). If null, the application will be distributed on all configured targets. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Distribute an application. The application source files and deployment plan are distributed to the deployment targets using the options specified. This is an asynchronous operation that returns immediately.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("appendToExtensionLoader", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("codeSourcePath", "A jar file to add to the extension loader ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Appends a code source to the search path of the WebLogic Extension Loader, which is also referred to as the WebLogic Domain Loader. The code source file is distributed to all targets using default options. When the operation is complete, classes and resources in the jar file will be visible for class loading at the extension loader. Since the jar is now in place on targeted servers, the classes and resources will continue to be available even after a targeted server restarts. This is an asynchronous operation that returns immediately.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("appendToExtensionLoader", String.class, String[].class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("codeSourcePath", "A jar file to add to the extension loader "), createParameterDescriptor("targets", "The targets on which to distribute the code source. This would be server names or cluster names. If null, the code source will be distributed on all configured targets. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. If null, default options will be used. The values should all be of type The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, retireTimeout seconds -1 (no timeout), timeout milliseconds 0 (no timeout) ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException.  All other errors are reported via the returned  DeploymentProgressObjectMBean.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Appends a code source to the search path of the WebLogic Extension Loader, which is also referred to as the WebLogic Domain Loader. The code source file is distributed to the targets using the options specified.  When the operation is complete, classes and resources in the jar file will be visible for class loading at the extension loader. Since the jar is now in place on targeted servers, the classes and resources will continue to be available even after a targeted server restarts. This is an asynchronous operation that returns immediately.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("undeploy", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The application name "), createParameterDescriptor("template", "The resource group template ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Undeploy the application that is deployed to a resource group template using the default options. This is a synchronous operation that returns when the undeploy operation has completed. The default options are clusterDeploymentTimeout: 3600000 milliseconds, gracefulIgnoreSessions: false, gracefulProductionToAdmin: false, retireGracefully: true, retireTimeout: no timeout, adminMode: false, timeout: no timeout</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("undeploy", String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The application name "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) resourceGroupTemplate this value is required ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Undeploy the application that is deployed to a resource group template in the background with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p>  <p>Note that this API is only for undeploying applications that are deployed to a resource group template, and a resourceGroupTemplate property value must be provided in deploymentOptions. AppDeploymentRuntimeMBean should be used for undeploying applications that are not deployed to a resource group template.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("redeploy", String.class, String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The application name "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) resourceGroupTemplate this value is required ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Redeploy the application that is deployed to a resource group template in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p>  <p>Note that this API is only for redeploying applications or libraries that are deployed to a resource group template, and a resourceGroupTemplate property value must be provided in deploymentOptions. AppDeploymentRuntimeMBean should be used for redeploying applications that are not deployed to a resource group template, and LibDeploymentRuntimeMBean should be used for redeploying libraries that are not deployed to a resource group template.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("redeploy", String.class, String.class, String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The application name "), createParameterDescriptor("applicationPath", "A supported Java EE application or module archive, or a root diectory for the application or module. May be null. "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) resourceGroupTemplate this value is required ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Redeploy the application that is deployed to a resource group template in the background for the targets specified with the options specified.  This is an asynchronous operation that returns immediately.  The returned {@link DeploymentProgressObjectMBean} can be used to determine when the operation is completed.</p>  <p>Note that this API is only for redeploying applications or libraries that are deployed to a resource group template, and a resourceGroupTemplate property value must be provided in deploymentOptions. AppDeploymentRuntimeMBean should be used for redeploying applications that are not deployed to a resource group template, and LibDeploymentRuntimeMBean should be used for redeploying libraries that are not deployed to a resource group template.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("update", String.class, String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The application name "), createParameterDescriptor("plan", "The deployment plan for this application.  May be null. "), createParameterDescriptor("deploymentOptions", "Allows for overriding the deployment options. The keys, units and default values for options are clusterDeploymentTimeout milliseconds 3600000, gracefulIgnoreSessions boolean false, gracefulProductionToAdmin boolean false, retireGracefully boolean true, retireTimeout seconds -1 (no timeout), adminMode boolean false, timeout milliseconds 0 (no timeout) resourceGroupTemplate this value is required ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Allows for updating an application configuration using an changed deployment plan for applications deployed to a resource group template.</p>  <p>A configuration update is equivalent to replacing the application's deployment plan. The deployment plan is redeployed in-place. A new version of the application is not started, even if the new deployment plan has a different version.</p>  <p>The update succeeds only if changes to the deployment plan do not require the application to be restarted. Configuration changes in the new deployment plan must be limited to tuning the application. Changes to resource bindings causes the update to fail. Use {@link #redeploy(String, String, Properties)} to apply resource binding changes to a production application.</p>  <p>This method targets only root modules. Module level targeting is not supported.</p>  <p>Note that this API is only for updating applications that are deployed to a resource group template, and a resourceGroupTemplate property value must be provided in deploymentOptions. AppDeploymentRuntimeMBean should be used for updating applications that are not deployed to a resource group template.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = DeploymentManagerMBean.class.getMethod("purgeCompletedDeploymentProgressObjects");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes progress objects for completed operations.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("confirmApplicationName", Boolean.class, String.class, String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("isRedeployment", "should be true in the case where this function is called for a redeployment. "), createParameterDescriptor("appSource", "is a the path to the application. "), createParameterDescriptor("optional", "alternate application descriptor path "), createParameterDescriptor("tentativeName", "The name proposed for the application (provided by the user, or through the manifest). Null if no name was specified by the user or through the manifest. "), createParameterDescriptor("tentativeApplicationId", "The tentative application ID. May only be null if the application is not versioned. If tentativeName is null and the application is versioned, this should be just the version string (which includes both the implementation and specification versions). ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Used by the deployment subsystem to confirm an application name. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DeploymentManagerMBean.class.getMethod("confirmApplicationName", Boolean.class, String.class, String.class, String.class, String.class, Properties.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("isRedeployment", "should be true in the case where this function is called for a redeployment. "), createParameterDescriptor("appSource", "is a the path to the application. "), createParameterDescriptor("optional", "alternate app descriptor. "), createParameterDescriptor("tentativeName", "The name proposed for the application (provided by the user, or through the manifest). Null if no name was specified by the user or through the manifest. "), createParameterDescriptor("tentativeApplicationId", "The tentative application ID. May only be null if the application is not versioned. If tentativeName is null and the application is versioned, this should be just the version string (which includes both the implementation and specification versions). "), createParameterDescriptor("deploymentOptions", "deployment options ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("RuntimeException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Used by the deployment subsystem to confirm an application name. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
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
