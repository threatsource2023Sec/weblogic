package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class BaseExecutorServiceMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = BaseExecutorServiceMBean.class;

   public BaseExecutorServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BaseExecutorServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.BaseExecutorServiceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configuration MBean representing common parameters of partition level ManagedExecutorService, partition level ManagedScheduledExecutorService, ManagedExecutorService template and ManagedScheduledExecutorService template. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.BaseExecutorServiceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DispatchPolicy")) {
         getterName = "getDispatchPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDispatchPolicy";
         }

         currentResult = new PropertyDescriptor("DispatchPolicy", BaseExecutorServiceMBean.class, getterName, setterName);
         descriptors.put("DispatchPolicy", currentResult);
         currentResult.setValue("description", "<p> The name of the Work Manager to use for this Concurrent Managed Ojbect (CMO). If a Work Manager is not specified, the default one is used. </p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongRunningPriority")) {
         getterName = "getLongRunningPriority";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongRunningPriority";
         }

         currentResult = new PropertyDescriptor("LongRunningPriority", BaseExecutorServiceMBean.class, getterName, setterName);
         descriptors.put("LongRunningPriority", currentResult);
         currentResult.setValue("description", "<p> An integer that specifies the long-running daemon thread's priority. If a value is specified, all long-running threads are affected. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(10));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConcurrentLongRunningRequests")) {
         getterName = "getMaxConcurrentLongRunningRequests";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentLongRunningRequests";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentLongRunningRequests", BaseExecutorServiceMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p> The maximum number of running long-running tasks submitted to this CMO. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
