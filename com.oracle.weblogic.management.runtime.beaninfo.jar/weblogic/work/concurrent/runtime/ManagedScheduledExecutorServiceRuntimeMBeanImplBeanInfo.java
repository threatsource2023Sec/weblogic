package weblogic.work.concurrent.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean;

public class ManagedScheduledExecutorServiceRuntimeMBeanImplBeanInfo extends ManagedExecutorServiceRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedScheduledExecutorServiceRuntimeMBean.class;

   public ManagedScheduledExecutorServiceRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedScheduledExecutorServiceRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.concurrent.runtime.ManagedScheduledExecutorServiceRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work.concurrent.runtime");
      String description = (new String("ManagedScheduledExecutorService Runtime information. It can be the information of a partition level MSES, an application level MSES or a regular JSR236 MSES. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the application this ManagedExecutor is associated with </p> ");
      }

      if (!descriptors.containsKey("CompletedLongRunningRequests")) {
         getterName = "getCompletedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedLongRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks which is successfully completed. ");
      }

      if (!descriptors.containsKey("CompletedShortRunningRequests")) {
         getterName = "getCompletedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedShortRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of short running tasks which is successfully completed. ");
      }

      if (!descriptors.containsKey("FailedRequests")) {
         getterName = "getFailedRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedRequests", currentResult);
         currentResult.setValue("description", "Total number of tasks which terminated abnormally by throwing exception, including both long and short term tasks. ");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleName", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the module this ManagedExecutor is associated with </p> ");
      }

      if (!descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the partition this ManagedExecutor is associated with </p> ");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("RejectedLongRunningRequests")) {
         getterName = "getRejectedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedLongRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks rejected by max-concurrent-long-running-requests ");
      }

      if (!descriptors.containsKey("RejectedShortRunningRequests")) {
         getterName = "getRejectedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedShortRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of short running tasks rejected by workmanager overload policy ");
      }

      if (!descriptors.containsKey("RunningLongRunningRequests")) {
         getterName = "getRunningLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RunningLongRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RunningLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks which is currently running. ");
      }

      if (!descriptors.containsKey("SubmitedShortRunningRequests")) {
         getterName = "getSubmitedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("SubmitedShortRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubmitedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of submitted short running tasks ");
      }

      if (!descriptors.containsKey("SubmittedLongRunningRequests")) {
         getterName = "getSubmittedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("SubmittedLongRunningRequests", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubmittedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of submitted long running tasks ");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManager", ManagedScheduledExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Runtime information on the associated WorkManager. ");
         currentResult.setValue("relationship", "containment");
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
