package weblogic.scheduler;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JobRuntimeMBean;

public class JobRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JobRuntimeMBean.class;

   public JobRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JobRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.scheduler.JobRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.scheduler");
      String description = (new String("RuntimeMBean that provides information about a particular job. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JobRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Get the description of the submitted <code>commonj.timers.TimerListener</code>. Returns <code>commonj.timers.TimerListener#toString</code>. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ID")) {
         getterName = "getID";
         setterName = null;
         currentResult = new PropertyDescriptor("ID", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ID", currentResult);
         currentResult.setValue("description", "The unique ID corresponding to this job ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastLocalExecutionTime")) {
         getterName = "getLastLocalExecutionTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastLocalExecutionTime", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastLocalExecutionTime", currentResult);
         currentResult.setValue("description", "Returns the most recent execution time of this job in the local server. Note that multiple executions of the same job are load-balanced across the cluster and this time indicates when the job was last executed locally. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalExecutionCount")) {
         getterName = "getLocalExecutionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalExecutionCount", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LocalExecutionCount", currentResult);
         currentResult.setValue("description", "Returns the number of times this job was executed locally. Job executions are load-balanced across the cluster. This count specifies the number of executions of the job in the local server. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Period")) {
         getterName = "getPeriod";
         setterName = null;
         currentResult = new PropertyDescriptor("Period", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Period", currentResult);
         currentResult.setValue("description", "Returns the specified periodicity of this job ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "Returns the state of the task. A Job is either in running state or in cancelled state ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timeout")) {
         getterName = "getTimeout";
         setterName = null;
         currentResult = new PropertyDescriptor("Timeout", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Timeout", currentResult);
         currentResult.setValue("description", "Returns when the job will be executed next ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimerListener")) {
         getterName = "getTimerListener";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerListener", JobRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TimerListener", currentResult);
         currentResult.setValue("description", "Get the {@link commonj.timers.TimerListener} associated with this job. This call involves a database roundtrip and should be used too frequently. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Serializable");
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
      Method mth = JobRuntimeMBean.class.getMethod("cancel");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Cancel this job and prevent it from executing again in any server, not just this server. ");
         currentResult.setValue("role", "operation");
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
