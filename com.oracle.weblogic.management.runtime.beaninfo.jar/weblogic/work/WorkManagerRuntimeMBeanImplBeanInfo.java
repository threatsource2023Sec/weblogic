package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public class WorkManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WorkManagerRuntimeMBean.class;

   public WorkManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WorkManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.WorkManagerRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("WorkManager Runtime information. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WorkManagerRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ApplicationName", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the application this WorkManager is associated with</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CapacityRuntime")) {
         getterName = "getCapacityRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("CapacityRuntime", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CapacityRuntime", currentResult);
         currentResult.setValue("description", "<p>Returns the Capacity value associated with this WorkManager.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CompletedDaemonRequests")) {
         getterName = "getCompletedDaemonRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedDaemonRequests", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedDaemonRequests", currentResult);
         currentResult.setValue("description", "<p>The number of daemon requests that have been processed</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("CompletedRequests")) {
         getterName = "getCompletedRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedRequests", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedRequests", currentResult);
         currentResult.setValue("description", "<p>The number of requests that have been processed, including daemon requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>Returns the HealthState mbean for the work manager. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxThreadsConstraintRuntime")) {
         getterName = "getMaxThreadsConstraintRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraintRuntime", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxThreadsConstraintRuntime", currentResult);
         currentResult.setValue("description", "<p>Runtime information on MaxThreadsConstraint associated with this WorkManager</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraintRuntime")) {
         getterName = "getMinThreadsConstraintRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintRuntime", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintRuntime", currentResult);
         currentResult.setValue("description", "<p>Runtime information on MinThreadsConstraint associated with this WorkManager</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleName", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the module this WorkManager is associated with</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the partition that the work manager is associated with.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PendingDaemonRequests")) {
         getterName = "getPendingDaemonRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingDaemonRequests", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingDaemonRequests", currentResult);
         currentResult.setValue("description", "<p>The number of daemon requests in progress.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PendingRequests")) {
         getterName = "getPendingRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequests", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequests", currentResult);
         currentResult.setValue("description", "<p>The number of waiting requests in the queue, including daemon requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestClassRuntime")) {
         getterName = "getRequestClassRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestClassRuntime", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestClassRuntime", currentResult);
         currentResult.setValue("description", "<p>Runtime information on RequestClass associated with this WorkManager</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StuckThreadCount")) {
         getterName = "getStuckThreadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("StuckThreadCount", WorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StuckThreadCount", currentResult);
         currentResult.setValue("description", "<p>The number of threads that are considered to be stuck on the basis of any stuck thread constraints.</p> ");
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
