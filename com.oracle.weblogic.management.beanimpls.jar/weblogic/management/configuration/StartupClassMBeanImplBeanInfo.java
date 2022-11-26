package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class StartupClassMBeanImplBeanInfo extends ClassDeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = StartupClassMBean.class;

   public StartupClassMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public StartupClassMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.StartupClassMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Provides methods that configure startup classes. A startup class is a Java program that is automatically loaded and executed when a WebLogic Server instance is started or restarted.  By default, startup classes are loaded and executed after all other server subsystems have initialized and after the server deploys modules. For any startup class, you can override the default and specify that the server loads and executes it and before it deploys JDBC connection pools and before it deploys Web applications and EJBs. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.StartupClassMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FailureIsFatal")) {
         getterName = "getFailureIsFatal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailureIsFatal";
         }

         currentResult = new PropertyDescriptor("FailureIsFatal", StartupClassMBean.class, getterName, setterName);
         descriptors.put("FailureIsFatal", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a failure in this startup class prevents the targeted server(s) from starting.</p>  <p>If you specify that failure is <b>not</b> fatal, if the startup class fails, the server continues its startup process.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("LoadAfterAppsRunning")) {
         getterName = "getLoadAfterAppsRunning";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadAfterAppsRunning";
         }

         currentResult = new PropertyDescriptor("LoadAfterAppsRunning", StartupClassMBean.class, getterName, setterName);
         descriptors.put("LoadAfterAppsRunning", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the targeted servers load and run this startup class after applications and EJBs are running.</p>  <p>If you enable this feature for a startup class, a server loads and runs the startup class after the activate phase. At this point, JMS and JDBC services are available. (Deployment for applications and EJBs consists of three phases: prepare, admin and activate.)</p>  <p>Enable this feature if the startup class needs to be invoked after applications are running and ready to service client requests.</p>  <p>If you do not enable this feature, LoadBeforeAppDeployments or LoadBeforeAppActivation, a server instance loads startup classes when applications go to the admin state.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.DeploymentMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (!descriptors.containsKey("LoadBeforeAppActivation")) {
         getterName = "getLoadBeforeAppActivation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBeforeAppActivation";
         }

         currentResult = new PropertyDescriptor("LoadBeforeAppActivation", StartupClassMBean.class, getterName, setterName);
         descriptors.put("LoadBeforeAppActivation", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the targeted servers load and run this startup class after activating JMS and JDBC services and before activating applications and EJBs.</p>  <p>If you enable this feature for a startup class, a server loads and runs the startup class before the activate phase. At this point, JMS and JDBC services are available. (Deployment for applications and EJBs consists of three phases: prepare, admin and activate.)</p>  <p>Enable this feature if the startup class needs to be invoked after JDBC connection pools are available but before the applications are activated and ready to service client requests.</p>  <p>If you do not enable this feature, LoadBeforeAppDeployments or LoadAfterAppsRunning, a server instance loads startup classes when applications go to the admin state.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.DeploymentMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBeforeAppDeployments")) {
         getterName = "getLoadBeforeAppDeployments";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBeforeAppDeployments";
         }

         currentResult = new PropertyDescriptor("LoadBeforeAppDeployments", StartupClassMBean.class, getterName, setterName);
         descriptors.put("LoadBeforeAppDeployments", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the targeted servers load and run this startup class before activating JMS and JDBC services and before starting deployment for applications and EJBs.</p>  <p>If you enable this feature for a startup class, a server loads and runs the startup class before the deployment prepare phase. At this point, JMS and JDBC services are not yet available. (Deployment for applications and EJBs consists of three phases: prepare, admin and activate.)</p>  <p>If you do not enable this feature, LoadBeforeAppActivation or LoadAfterAppsRunning, a server instance loads startup classes when applications go to the admin state.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.DeploymentMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
