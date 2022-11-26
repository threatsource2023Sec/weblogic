package weblogic.j2ee;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;

public class ComponentConcurrentRuntimeMBeanImplBeanInfo extends ComponentRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ComponentConcurrentRuntimeMBean.class;

   public ComponentConcurrentRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ComponentConcurrentRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.ComponentConcurrentRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee");
      String description = (new String("Component concurrent managed object runtime information. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ComponentConcurrentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("ManagedExecutorServiceRuntimes")) {
         getterName = "getManagedExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceRuntimes", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServiceRuntimes")) {
         getterName = "getManagedScheduledExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceRuntimes", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedScheduledExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedScheduledExecutorServices defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedThreadFactoryRuntimes")) {
         getterName = "getManagedThreadFactoryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryRuntimes", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedThreadFactoryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all ManagedThreadFactorys defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", ComponentConcurrentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
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
