package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.management.ObjectName;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class AppDeploymentConfigurationManagerMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = AppDeploymentConfigurationManagerMBean.class;

   public AppDeploymentConfigurationManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AppDeploymentConfigurationManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationManagerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12c");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("This class supports read/write access to an application's deployment descriptors.  The user invokes editDeploymentConfiguration to get the MBean for the top level of the bean tree that corresponds to the application's deployment descriptors.  This MBean provides access to MBeans for individual descriptors, which can be modified. When modifications are completed, the user can invoke save & activate on the MBean for the ConfigurationManager. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.AppDeploymentConfigurationManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DeploymentConfigurations")) {
         getterName = "getDeploymentConfigurations";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentConfigurations", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentConfigurations", currentResult);
         currentResult.setValue("description", "Return the array of AppDeploymentConfigurationMBean's for the apps that are deployed ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("UnactivatedDeployments")) {
         getterName = "getUnactivatedDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("UnactivatedDeployments", AppDeploymentConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("UnactivatedDeployments", currentResult);
         currentResult.setValue("description", "Return the array of AppDeploymentMBean's that have configuration changes that have been saved, but not activated. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
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
      Method mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfigurationForPartition", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("partitionName", "The name of the partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class, String.class, Boolean.TYPE, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("partitionName", "The name of the partition "), createParameterDescriptor("resourceGroupName", "The name of the resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration  This API is for EM internal use and will be removed in 12.2.2. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfigurationForResourceGroupTemplate", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("resourceGroupTemplateName", "The name of the resource group template ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("resourceGroupTemplateName", "The name of the resource group template ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration  This API is for EM internal use and will be removed in 12.2.2. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("loadDeploymentConfiguration", ObjectName.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appObjectName", "The ObjectName that his this content: com.bea:Name=<app name>,Type=AppDeployment,Partition=<partition name>,ResourceGroup=<resource group name>,... "), createParameterDescriptor("plan", "The path for the deployment plan ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin a read-only session for getting an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("appLocation", "The location of the application "), createParameterDescriptor("plan", "The path for the deployment plan ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration when the application is not deployed. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("appLocation", "The location of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration when the application is not deployed. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfigurationForPartition", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("partitionName", "The name of the partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class, Boolean.TYPE, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("partitionName", "The name of the partition "), createParameterDescriptor("resourceGroupName", "The name of the resource group ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration  This API is for EM internal use and will be removed in 12.2.2. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfigurationForResourceGroupTemplate", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("resourceGroupTemplateName", "The name of the resource group template ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", String.class, String.class, Boolean.TYPE, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application "), createParameterDescriptor("plan", "The path for the deployment plan "), createParameterDescriptor("enableScaffolding", "Whether to enable scaffolding "), createParameterDescriptor("resourceGroupTemplateName", "The name of the resource group template ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "NEVER");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration  This API is for EM internal use and will be removed in 12.2.2. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("editDeploymentConfiguration", ObjectName.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appObjectName", "The ObjectName that his this content: com.bea:Name=<app name>,Type=AppDeployment,Partition=<partition name>,ResourceGroup=<resource group name>,... "), createParameterDescriptor("plan", "The path for the deployment plan ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Begin an edit session for changing an application's deployment configuration ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = AppDeploymentConfigurationManagerMBean.class.getMethod("releaseDeploymentConfiguration", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("appName", "The name of the application ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Release an application's deployment configuration ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = AppDeploymentConfigurationManagerMBean.class.getMethod("getUnactivatedDeployments", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "The name of the partition ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Return the array of AppDeploymentMBean's that have configuration changes that have been saved, but not activated. ");
            currentResult.setValue("exclude", Boolean.TRUE);
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
