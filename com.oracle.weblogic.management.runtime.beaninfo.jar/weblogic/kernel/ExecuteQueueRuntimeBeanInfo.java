package weblogic.kernel;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ExecuteQueueRuntimeMBean;

public class ExecuteQueueRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ExecuteQueueRuntimeMBean.class;

   public ExecuteQueueRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ExecuteQueueRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.kernel.ExecuteQueueRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.kernel");
      String description = (new String("This bean is used to monitor an execute queue and its associated thread pool. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ExecuteQueueRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ExecuteThreadCurrentIdleCount")) {
         getterName = "getExecuteThreadCurrentIdleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreadCurrentIdleCount", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreadCurrentIdleCount", currentResult);
         currentResult.setValue("description", "<p>The number of idle threads assigned to the queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteThreadTotalCount")) {
         getterName = "getExecuteThreadTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreadTotalCount", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreadTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of execute threads assigned to the queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteThreads")) {
         getterName = "getExecuteThreads";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreads", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreads", currentResult);
         currentResult.setValue("description", "<p>The execute threads currently assigned to the queue.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for ExecuteThread");
      }

      if (!descriptors.containsKey("PendingRequestCurrentCount")) {
         getterName = "getPendingRequestCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequestCurrentCount", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequestCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The number of waiting requests in the queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRequestOldestTime")) {
         getterName = "getPendingRequestOldestTime";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequestOldestTime", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequestOldestTime", currentResult);
         currentResult.setValue("description", "<p>The time since the longest waiting request was placed in the queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServicedRequestTotalCount")) {
         getterName = "getServicedRequestTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ServicedRequestTotalCount", ExecuteQueueRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServicedRequestTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of requests that have been processed by the queue.</p> ");
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
