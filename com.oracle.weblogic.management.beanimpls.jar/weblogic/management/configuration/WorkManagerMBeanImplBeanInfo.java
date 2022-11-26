package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WorkManagerMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WorkManagerMBean.class;

   public WorkManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WorkManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WorkManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configuration MBean representing WorkManager parameters. A WorkManager configuration can have a RequestClass(FairShare, ResponseTime, ContextBased), MinThreadsConstraint, MaxThreadsConstraint, Capacity and ShutdownTrigger. All these are optional and need to be configured as needed. An empty WorkManager without configuration gets its own default fair share. The default fair share value is 50. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WorkManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("Capacity")) {
         getterName = "getCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCapacity";
         }

         currentResult = new PropertyDescriptor("Capacity", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("Capacity", currentResult);
         currentResult.setValue("description", "<p>The total number of requests that can be queued or executing before WebLogic Server begins rejecting requests.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("CapacityMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ContextRequestClass")) {
         getterName = "getContextRequestClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setContextRequestClass";
         }

         currentResult = new PropertyDescriptor("ContextRequestClass", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("ContextRequestClass", currentResult);
         currentResult.setValue("description", "<p>The mapping of Request Classes to security names and groups.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ContextRequestClassMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FairShareRequestClass")) {
         getterName = "getFairShareRequestClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFairShareRequestClass";
         }

         currentResult = new PropertyDescriptor("FairShareRequestClass", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("FairShareRequestClass", currentResult);
         currentResult.setValue("description", "<p>Get the FairShareRequestClass for this WorkManager</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("FairShareRequestClassMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreStuckThreads")) {
         getterName = "getIgnoreStuckThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreStuckThreads";
         }

         currentResult = new PropertyDescriptor("IgnoreStuckThreads", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("IgnoreStuckThreads", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this Work Manager ignores \"stuck\" threads. Typically, stuck threads will cause the associated Work Manager to take some action: either switching the application to Admin mode, shutting down the server, or shutting down the Work Manager. If this flag is set, then no thread in this Work Manager is ever considered stuck. </p> <p>If you do not explicitly specify IGNORE_STUCK_THREADS=TRUE, the default behavior is that upon encountering stuck threads, the server will take one of the aforementioned actions. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("WorkManagerShutdownTriggerMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxThreadsConstraint")) {
         getterName = "getMaxThreadsConstraint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxThreadsConstraint";
         }

         currentResult = new PropertyDescriptor("MaxThreadsConstraint", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraint", currentResult);
         currentResult.setValue("description", "<p>The maximum number of concurrent threads that can be allocated to execute requests.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("MaxThreadsConstraintMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraint")) {
         getterName = "getMinThreadsConstraint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinThreadsConstraint";
         }

         currentResult = new PropertyDescriptor("MinThreadsConstraint", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("MinThreadsConstraint", currentResult);
         currentResult.setValue("description", "<p>The minimum number of threads allocated to resolve deadlocks.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("MinThreadsConstraintMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseTimeRequestClass")) {
         getterName = "getResponseTimeRequestClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResponseTimeRequestClass";
         }

         currentResult = new PropertyDescriptor("ResponseTimeRequestClass", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("ResponseTimeRequestClass", currentResult);
         currentResult.setValue("description", "<p>The response time goal (in milliseconds).</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResponseTimeRequestClassMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManagerShutdownTrigger")) {
         getterName = "getWorkManagerShutdownTrigger";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerShutdownTrigger", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("WorkManagerShutdownTrigger", currentResult);
         currentResult.setValue("description", "<p>Configure a shutdown trigger for this WorkManager. Specifies the condition to be used to shutdown the WorkManager. The Server health monitoring periodically checks to see if the conidtion is met and shuts down the work manager if needed.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWorkManagerShutdownTrigger");
         currentResult.setValue("creator", "createWorkManagerShutdownTrigger");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ApplicationScope")) {
         getterName = "isApplicationScope";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationScope";
         }

         currentResult = new PropertyDescriptor("ApplicationScope", WorkManagerMBean.class, getterName, setterName);
         descriptors.put("ApplicationScope", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this work manager should be scoped to an application. By default, work managers are scoped to an application. Set this value to false if the work manager is to be scoped to a domain</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = WorkManagerMBean.class.getMethod("createWorkManagerShutdownTrigger");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Configure the shutdown trigger for the WorkManager.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WorkManagerShutdownTrigger");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = WorkManagerMBean.class.getMethod("destroyWorkManagerShutdownTrigger");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove the configured shutdown trigger for the WorkManager.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WorkManagerShutdownTrigger");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

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
