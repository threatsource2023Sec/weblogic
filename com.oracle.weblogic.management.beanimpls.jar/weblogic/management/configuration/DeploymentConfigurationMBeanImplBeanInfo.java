package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DeploymentConfigurationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentConfigurationMBean.class;

   public DeploymentConfigurationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentConfigurationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DeploymentConfigurationMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Specifies the domain-level deployment configuration attributes. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DeploymentConfigurationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultMultiVersionAppRetireTimeout")) {
         getterName = "getDefaultMultiVersionAppRetireTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMultiVersionAppRetireTimeout";
         }

         currentResult = new PropertyDescriptor("DefaultMultiVersionAppRetireTimeout", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("DefaultMultiVersionAppRetireTimeout", currentResult);
         currentResult.setValue("description", "<p>Specifies the default retire timeout to be used for multi version apps. The value is in seconds</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentConfigOverrides")) {
         getterName = "getDeploymentConfigOverrides";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentConfigOverrides", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("DeploymentConfigOverrides", currentResult);
         currentResult.setValue("description", "<p>Specifies configuration related to deployment-config-overrides.xml</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentServiceMessageRetryCount")) {
         getterName = "getDeploymentServiceMessageRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentServiceMessageRetryCount";
         }

         currentResult = new PropertyDescriptor("DeploymentServiceMessageRetryCount", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("DeploymentServiceMessageRetryCount", currentResult);
         currentResult.setValue("description", "The maximum number of times the Deployment Service will retry sending a message if a connection exception occurs during the during a deployment or activate changes operation. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentServiceMessageRetryInterval")) {
         getterName = "getDeploymentServiceMessageRetryInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentServiceMessageRetryInterval";
         }

         currentResult = new PropertyDescriptor("DeploymentServiceMessageRetryInterval", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("DeploymentServiceMessageRetryInterval", currentResult);
         currentResult.setValue("description", "The number of milliseconds between retry attempts if a connection exception occurs during the during the deployment or activate changes operation. ");
         setPropertyDescriptorDefault(currentResult, new Integer(5000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("DeploymentValidationPlugin")) {
         getterName = "getDeploymentValidationPlugin";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentValidationPlugin", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("DeploymentValidationPlugin", currentResult);
         currentResult.setValue("description", "<p>Gets the deployment validation plug-in information.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LongRunningRetireThreadDumpCount")) {
         getterName = "getLongRunningRetireThreadDumpCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongRunningRetireThreadDumpCount";
         }

         currentResult = new PropertyDescriptor("LongRunningRetireThreadDumpCount", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("LongRunningRetireThreadDumpCount", currentResult);
         currentResult.setValue("description", "The maximum number of times the thread dump generation will be retried on a long running Retire operation ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LongRunningRetireThreadDumpInterval")) {
         getterName = "getLongRunningRetireThreadDumpInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongRunningRetireThreadDumpInterval";
         }

         currentResult = new PropertyDescriptor("LongRunningRetireThreadDumpInterval", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("LongRunningRetireThreadDumpInterval", currentResult);
         currentResult.setValue("description", "The number of milliseconds between thread dump retry on a long running Retire operation. ");
         setPropertyDescriptorDefault(currentResult, new Long(60000L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("LongRunningRetireThreadDumpStartTime")) {
         getterName = "getLongRunningRetireThreadDumpStartTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongRunningRetireThreadDumpStartTime";
         }

         currentResult = new PropertyDescriptor("LongRunningRetireThreadDumpStartTime", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("LongRunningRetireThreadDumpStartTime", currentResult);
         currentResult.setValue("description", "The number of milliseconds a thread dump generation will start for a long running Retire operation. ");
         setPropertyDescriptorDefault(currentResult, new Long(1200000L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("MaxAppVersions")) {
         getterName = "getMaxAppVersions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxAppVersions";
         }

         currentResult = new PropertyDescriptor("MaxAppVersions", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("MaxAppVersions", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum number of application versions for each application.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetiredTasks")) {
         getterName = "getMaxRetiredTasks";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetiredTasks";
         }

         currentResult = new PropertyDescriptor("MaxRetiredTasks", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("MaxRetiredTasks", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the maximum number of retired tasks to save information for.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RemoteDeployerEJBEnabled")) {
         getterName = "isRemoteDeployerEJBEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteDeployerEJBEnabled";
         }

         currentResult = new PropertyDescriptor("RemoteDeployerEJBEnabled", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("RemoteDeployerEJBEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Remote Deployer EJB is automatically deployed in the current domain. The Remote Deployer EJB is only used by the weblogic.Deployer tool in the WLS 9.0 and 9.1 releases when the -remote option is specified.</p>  <p>If the Remote Deployer EJB is not deployed, you will not be able to use the -remote option in weblogic.Deployer running in a 9.0 or 9.1 installation. You can still use the -remote option from weblogic.Deployer in 9.2 or later releases.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestageOnlyOnRedeploy")) {
         getterName = "isRestageOnlyOnRedeploy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestageOnlyOnRedeploy";
         }

         currentResult = new PropertyDescriptor("RestageOnlyOnRedeploy", DeploymentConfigurationMBean.class, getterName, setterName);
         descriptors.put("RestageOnlyOnRedeploy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether applications with staging mode of STAGE are restaged only during redeploy operation. If set to true, then applications will never restage during server startup and will only be restaged on an explicit redeploy operation.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
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
