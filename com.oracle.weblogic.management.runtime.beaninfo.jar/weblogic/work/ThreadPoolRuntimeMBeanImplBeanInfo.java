package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ThreadPoolRuntimeMBean;

public class ThreadPoolRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ThreadPoolRuntimeMBean.class;

   public ThreadPoolRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ThreadPoolRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.ThreadPoolRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("This bean is used to monitor the self-tuning queue ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ThreadPoolRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CompletedRequestCount")) {
         getterName = "getCompletedRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedRequestCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedRequestCount", currentResult);
         currentResult.setValue("description", "<p>The number of completed requests in the priority queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteThreadIdleCount")) {
         getterName = "getExecuteThreadIdleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreadIdleCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreadIdleCount", currentResult);
         currentResult.setValue("description", "<p>The number of idle threads in the pool. This count does not include standby threads and stuck threads. The count indicates threads that are ready to pick up new work when it arrives</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteThreadTotalCount")) {
         getterName = "getExecuteThreadTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreadTotalCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreadTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of threads in the pool.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ExecuteThreads")) {
         getterName = "getExecuteThreads";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteThreads", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecuteThreads", currentResult);
         currentResult.setValue("description", "<p>An array of the threads currently processing work in the active thread pool.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ExecuteThread")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for ExecuteThread");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of this pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HoggingThreadCount")) {
         getterName = "getHoggingThreadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("HoggingThreadCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HoggingThreadCount", currentResult);
         currentResult.setValue("description", "<p> The threads that are being held by a request right now. These threads will either be declared as stuck after the configured timeout or will return to the pool before that. The self-tuning mechanism will backfill if necessary. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraintsCompleted")) {
         getterName = "getMinThreadsConstraintsCompleted";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintsCompleted", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintsCompleted", currentResult);
         currentResult.setValue("description", "<p>Number of requests with min threads constraint picked up out of order for execution immediately since their min threads requirement was not met. This does not include the case where threads are idle during schedule.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraintsPending")) {
         getterName = "getMinThreadsConstraintsPending";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintsPending", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintsPending", currentResult);
         currentResult.setValue("description", "<p>Number of requests that should be executed now to satisfy the min threads requirement.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("OverloadRejectedRequestsCount")) {
         getterName = "getOverloadRejectedRequestsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OverloadRejectedRequestsCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverloadRejectedRequestsCount", currentResult);
         currentResult.setValue("description", "<p>Number of requests rejected due to configured Shared Capacity for work managers have been reached.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0.0");
      }

      if (!descriptors.containsKey("PendingUserRequestCount")) {
         getterName = "getPendingUserRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingUserRequestCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingUserRequestCount", currentResult);
         currentResult.setValue("description", "<p>The number of pending user requests in the priority queue. The priority queue contains requests from internal subsystems and users. This is just the count of all user requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueueLength")) {
         getterName = "getQueueLength";
         setterName = null;
         currentResult = new PropertyDescriptor("QueueLength", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("QueueLength", currentResult);
         currentResult.setValue("description", "<p>The number of pending requests in the priority queue. This is the total of internal system requests and user requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SharedCapacityForWorkManagers")) {
         getterName = "getSharedCapacityForWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("SharedCapacityForWorkManagers", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SharedCapacityForWorkManagers", currentResult);
         currentResult.setValue("description", "<p>Maximum amount of requests that can be accepted in the priority queue. Note that a request with higher priority will be accepted in place of a lower priority request already in the queue even after the threshold is reached. The lower priority request is kept waiting in the queue till all high priority requests are executed. Also note that further enqueues of the low priority requests are rejected right away. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StandbyThreadCount")) {
         getterName = "getStandbyThreadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("StandbyThreadCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StandbyThreadCount", currentResult);
         currentResult.setValue("description", "<p> The number of threads in the standby pool. Threads that are not needed to handle the present work load are designated as standby and added to the standby pool. These threads are activated when more threads are needed. </p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StuckThreadCount")) {
         getterName = "getStuckThreadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("StuckThreadCount", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StuckThreadCount", currentResult);
         currentResult.setValue("description", "<p>Number of stuck threads in the thread pool.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Throughput")) {
         getterName = "getThroughput";
         setterName = null;
         currentResult = new PropertyDescriptor("Throughput", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Throughput", currentResult);
         currentResult.setValue("description", "<p>The mean number of requests completed per second.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Suspended")) {
         getterName = "isSuspended";
         setterName = null;
         currentResult = new PropertyDescriptor("Suspended", ThreadPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Suspended", currentResult);
         currentResult.setValue("description", "<p>Indicates if the RequestManager is suspended. A suspended manager will not dequeue work and dispatch threads till it is resumed.</p> ");
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
      Method mth = ThreadPoolRuntimeMBean.class.getMethod("getExecuteThread", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for ExecuteThread");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The execute thread with the given thread name. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
         currentResult.setValue("excludeFromRest", "No default REST mapping for ExecuteThread");
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
