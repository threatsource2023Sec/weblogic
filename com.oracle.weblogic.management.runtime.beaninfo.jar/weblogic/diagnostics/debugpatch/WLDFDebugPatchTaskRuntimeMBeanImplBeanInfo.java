package weblogic.diagnostics.debugpatch;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.TaskRuntimeMBeanImplBeanInfo;
import weblogic.management.runtime.WLDFDebugPatchTaskRuntimeMBean;

public class WLDFDebugPatchTaskRuntimeMBeanImplBeanInfo extends TaskRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFDebugPatchTaskRuntimeMBean.class;

   public WLDFDebugPatchTaskRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFDebugPatchTaskRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.debugpatch.WLDFDebugPatchTaskRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.debugpatch");
      String description = (new String("This interface provides monitoring over activation and deactivation tasks for dynamic debug patches ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFDebugPatchTaskRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ApplicationName", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeginTime")) {
         getterName = "getBeginTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeginTime", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeginTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was started.</p> ");
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>A description of this task.</p> ");
      }

      if (!descriptors.containsKey("EndTime")) {
         getterName = "getEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("EndTime", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EndTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was completed.</p>  <p>A value of <code>-1</code> indicates that the task is currently running.</p> ");
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("Error")) {
         getterName = "getError";
         setterName = null;
         currentResult = new PropertyDescriptor("Error", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Error", currentResult);
         currentResult.setValue("description", "<p>Returns an exception describing the error, if any, that occurred while performing this task.</p> ");
         currentResult.setValue("restName", "taskError");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleName", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentTask")) {
         getterName = "getParentTask";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentTask", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentTask", currentResult);
         currentResult.setValue("description", "<p>The task of which this task is a part.</p>  <p>A value of <code>null</code> indicates that this task is not a subtask.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSubTasks")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("restRelationship", "reference");
      }

      if (!descriptors.containsKey("Patches")) {
         getterName = "getPatches";
         setterName = null;
         currentResult = new PropertyDescriptor("Patches", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Patches", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Progress")) {
         getterName = "getProgress";
         setterName = null;
         currentResult = new PropertyDescriptor("Progress", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Progress", currentResult);
         currentResult.setValue("description", "<p>The progress of this task.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "<p>The status of this task.</p> ");
         currentResult.setValue("restName", "taskStatus");
      }

      if (!descriptors.containsKey("SubTasks")) {
         getterName = "getSubTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("SubTasks", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubTasks", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TaskRuntimeMBeans</code> that describes a set of parallel tasks which are components of this task.</p>  <p>A value of <code>null</code> indicates that this task has no subtasks.</p>  <p>A simple example of a task with subtasks would be one which monitors a user's request to start a cluster; that task should return a set of subtasks indicating the individual server-startup processes which compose the overall cluster-startup task.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("ActivationTask")) {
         getterName = "isActivationTask";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationTask", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActivationTask", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Running")) {
         getterName = "isRunning";
         setterName = null;
         currentResult = new PropertyDescriptor("Running", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Running", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the task is still running.</p> ");
      }

      if (!descriptors.containsKey("SystemTask")) {
         getterName = "isSystemTask";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemTask", WLDFDebugPatchTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SystemTask", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this task was initiated by the server versus a user.</p> ");
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
      Method mth = WLDFDebugPatchTaskRuntimeMBean.class.getMethod("cancel");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Attempts to cancel this task.</p>  <p>An exception is thrown to indicate failure to cancel the task. Not all tasks can be cancelled.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WLDFDebugPatchTaskRuntimeMBean.class.getMethod("printLog", PrintWriter.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("out", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Prints information that was logged during the processing of the task (e.g. server startup log) to the given Writer.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
