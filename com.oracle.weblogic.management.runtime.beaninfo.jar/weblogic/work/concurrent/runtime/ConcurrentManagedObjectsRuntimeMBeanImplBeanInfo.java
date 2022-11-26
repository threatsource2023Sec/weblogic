package weblogic.work.concurrent.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;

public class ConcurrentManagedObjectsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConcurrentManagedObjectsRuntimeMBean.class;

   public ConcurrentManagedObjectsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConcurrentManagedObjectsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.concurrent.runtime.ConcurrentManagedObjectsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work.concurrent.runtime");
      String description = (new String("Concurrent Managed Objects Runtime information for the global/domain partition or a common partition. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ManagedExecutorServiceRuntimes")) {
         getterName = "getManagedExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceRuntimes", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all partition level ManagedExecutorServices defined in this partition</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedScheduledExecutorServiceRuntimes")) {
         getterName = "getManagedScheduledExecutorServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceRuntimes", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedScheduledExecutorServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all partition level ManagedScheduledExecutorServices defined in this partition</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedThreadFactoryRuntimes")) {
         getterName = "getManagedThreadFactoryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryRuntimes", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedThreadFactoryRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all partition level ManagedThreadFactories defined in this partition</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RejectedLongRunningRequests")) {
         getterName = "getRejectedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedLongRunningRequests", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p>The number of long-running requests that have been rejected by managed executor services and managed scheduled executor services in this partition on the current server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RejectedNewThreadRequests")) {
         getterName = "getRejectedNewThreadRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedNewThreadRequests", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedNewThreadRequests", currentResult);
         currentResult.setValue("description", "<p>The number of newThread method invocations that have been rejected by managed thread factories in this partition on the current server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunningLongRunningRequests")) {
         getterName = "getRunningLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RunningLongRunningRequests", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RunningLongRunningRequests", currentResult);
         currentResult.setValue("description", "<p>The number of running long-running requests submitted to managed executor services and managed scheduled executor services in this partition on the current server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunningThreadsCount")) {
         getterName = "getRunningThreadsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RunningThreadsCount", ConcurrentManagedObjectsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RunningThreadsCount", currentResult);
         currentResult.setValue("description", "<p>The number of running threads created by managed thread factories in this partition on the current server.</p> ");
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
