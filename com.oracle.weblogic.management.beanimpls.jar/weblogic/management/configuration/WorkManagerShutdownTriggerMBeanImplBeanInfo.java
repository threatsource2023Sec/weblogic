package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WorkManagerShutdownTriggerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WorkManagerShutdownTriggerMBean.class;

   public WorkManagerShutdownTriggerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WorkManagerShutdownTriggerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WorkManagerShutdownTriggerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean is used to configure the trigger that shuts down the WorkManager. The trigger specifies the number of threads that need to be stuck for a certain amount of time for the trigger to shutdown the WorkManager automatically. A shutdown WorkManager refuses new work and completes pending work. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WorkManagerShutdownTriggerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MaxStuckThreadTime")) {
         getterName = "getMaxStuckThreadTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxStuckThreadTime";
         }

         currentResult = new PropertyDescriptor("MaxStuckThreadTime", WorkManagerShutdownTriggerMBean.class, getterName, setterName);
         descriptors.put("MaxStuckThreadTime", currentResult);
         currentResult.setValue("description", "Time after which a executing thread is declared as stuck. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StuckThreadCount")) {
         getterName = "getStuckThreadCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStuckThreadCount";
         }

         currentResult = new PropertyDescriptor("StuckThreadCount", WorkManagerShutdownTriggerMBean.class, getterName, setterName);
         descriptors.put("StuckThreadCount", currentResult);
         currentResult.setValue("description", "Number of stuck threads after which the WorkManager is shutdown ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResumeWhenUnstuck")) {
         getterName = "isResumeWhenUnstuck";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResumeWhenUnstuck";
         }

         currentResult = new PropertyDescriptor("ResumeWhenUnstuck", WorkManagerShutdownTriggerMBean.class, getterName, setterName);
         descriptors.put("ResumeWhenUnstuck", currentResult);
         currentResult.setValue("description", "Whether to resume work manager once the stuck threads were cleared ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
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
