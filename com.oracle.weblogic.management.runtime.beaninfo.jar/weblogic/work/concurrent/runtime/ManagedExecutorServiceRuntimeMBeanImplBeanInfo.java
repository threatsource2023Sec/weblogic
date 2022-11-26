package weblogic.work.concurrent.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean;

public class ManagedExecutorServiceRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedExecutorServiceRuntimeMBean.class;

   public ManagedExecutorServiceRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedExecutorServiceRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.concurrent.runtime.ManagedExecutorServiceRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work.concurrent.runtime");
      String description = (new String("ManagedExecutorService Runtime information. It can be the information of a partition level MES, an application level MES or a regular JSR236 MES. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ApplicationName", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the application this ManagedExecutor is associated with </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedLongRunningRequests")) {
         getterName = "getCompletedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedLongRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks which is successfully completed. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CompletedShortRunningRequests")) {
         getterName = "getCompletedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedShortRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of short running tasks which is successfully completed. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailedRequests")) {
         getterName = "getFailedRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedRequests", currentResult);
         currentResult.setValue("description", "Total number of tasks which terminated abnormally by throwing exception, including both long and short term tasks. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleName", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the module this ManagedExecutor is associated with </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p> Get the name of the partition this ManagedExecutor is associated with </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("RejectedLongRunningRequests")) {
         getterName = "getRejectedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedLongRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks rejected by max-concurrent-long-running-requests ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RejectedShortRunningRequests")) {
         getterName = "getRejectedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RejectedShortRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RejectedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of short running tasks rejected by workmanager overload policy ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RunningLongRunningRequests")) {
         getterName = "getRunningLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("RunningLongRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RunningLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of long running tasks which is currently running. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubmitedShortRunningRequests")) {
         getterName = "getSubmitedShortRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("SubmitedShortRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubmitedShortRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of submitted short running tasks ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubmittedLongRunningRequests")) {
         getterName = "getSubmittedLongRunningRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("SubmittedLongRunningRequests", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubmittedLongRunningRequests", currentResult);
         currentResult.setValue("description", "Total number of submitted long running tasks ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManager", ManagedExecutorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Runtime information on the associated WorkManager. ");
         currentResult.setValue("relationship", "containment");
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
