package weblogic.management.patching;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.RolloutTaskRuntimeMBean;
import weblogic.management.workflow.mbean.WorkflowProgressMBeanDelegateBeanInfo;

public class RolloutProgressMBeanDelegateBeanInfo extends WorkflowProgressMBeanDelegateBeanInfo {
   public static final Class INTERFACE_CLASS = RolloutTaskRuntimeMBean.class;

   public RolloutProgressMBeanDelegateBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RolloutProgressMBeanDelegateBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.patching.RolloutProgressMBeanDelegate");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("package", "weblogic.management.patching");
      String description = (new String("TaskRuntimeMBean for any workflow (WorkflowProgress) executed using management orchestration framework. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.RolloutTaskRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("BeginTime")) {
         getterName = "getBeginTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeginTime", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeginTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was started.</p> ");
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>A description of this task.</p> ");
      }

      if (!descriptors.containsKey("EndTime")) {
         getterName = "getEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("EndTime", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EndTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was completed.</p>  <p>A value of <code>-1</code> indicates that the task is currently running.</p> ");
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("Error")) {
         getterName = "getError";
         setterName = null;
         currentResult = new PropertyDescriptor("Error", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Error", currentResult);
         currentResult.setValue("description", "<p>Returns an exception describing the error, if any, that occurred while performing this task.</p> ");
         currentResult.setValue("restName", "taskError");
      }

      if (!descriptors.containsKey("Errors")) {
         getterName = "getErrors";
         setterName = null;
         currentResult = new PropertyDescriptor("Errors", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Errors", currentResult);
         currentResult.setValue("description", "Lists all errors in the workflow. ");
      }

      if (!descriptors.containsKey("NumCompletedCommands")) {
         getterName = "getNumCompletedCommands";
         setterName = null;
         currentResult = new PropertyDescriptor("NumCompletedCommands", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumCompletedCommands", currentResult);
         currentResult.setValue("description", "Number of completed commands in the workflow. Together with {@code getNumTotalCommands()} defines kind of progress information. ");
      }

      if (!descriptors.containsKey("NumTotalCommands")) {
         getterName = "getNumTotalCommands";
         setterName = null;
         currentResult = new PropertyDescriptor("NumTotalCommands", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumTotalCommands", currentResult);
         currentResult.setValue("description", "Number of all commands in the workflow. Together with {@code getNumCompletedCommands()} defines kind of progress information. ");
      }

      if (!descriptors.containsKey("ParentTask")) {
         getterName = "getParentTask";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentTask", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentTask", currentResult);
         currentResult.setValue("description", "<p>The task of which this task is a part.</p>  <p>A value of <code>null</code> indicates that this task is not a subtask.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSubTasks")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("restRelationship", "reference");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Progress")) {
         getterName = "getProgress";
         setterName = null;
         currentResult = new PropertyDescriptor("Progress", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Progress", currentResult);
         currentResult.setValue("description", "<p>The progress of this task.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ProgressString")) {
         getterName = "getProgressString";
         setterName = null;
         currentResult = new PropertyDescriptor("ProgressString", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProgressString", currentResult);
         currentResult.setValue("description", "Human-readable message containing information about the current workflow progress. ");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "<p>The status of this task.</p> ");
         currentResult.setValue("restName", "taskStatus");
      }

      if (!descriptors.containsKey("StatusHistory")) {
         getterName = "getStatusHistory";
         setterName = null;
         currentResult = new PropertyDescriptor("StatusHistory", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StatusHistory", currentResult);
         currentResult.setValue("description", "Provides step by step information (log) about the progress of the workflow. ");
      }

      if (!descriptors.containsKey("SubTasks")) {
         getterName = "getSubTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("SubTasks", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubTasks", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TaskRuntimeMBeans</code> that describes a set of parallel tasks which are components of this task.</p>  <p>A value of <code>null</code> indicates that this task has no subtasks.</p>  <p>A simple example of a task with subtasks would be one which monitors a user's request to start a cluster; that task should return a set of subtasks indicating the individual server-startup processes which compose the overall cluster-startup task.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("TargetedNodes")) {
         getterName = "getTargetedNodes";
         setterName = null;
         currentResult = new PropertyDescriptor("TargetedNodes", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TargetedNodes", currentResult);
         currentResult.setValue("description", "returns the list of nodes targeted by this rollout ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "no default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("UpdatedNodes")) {
         getterName = "getUpdatedNodes";
         setterName = null;
         currentResult = new PropertyDescriptor("UpdatedNodes", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UpdatedNodes", currentResult);
         currentResult.setValue("description", "returns the list of nodes already updated by this rollout ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "no default REST mapping for CompositeData");
      }

      if (!descriptors.containsKey("WorkflowId")) {
         getterName = "getWorkflowId";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkflowId", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkflowId", currentResult);
         currentResult.setValue("description", "Each workflow has a unique id. ");
      }

      if (!descriptors.containsKey("WorkflowName")) {
         getterName = "getWorkflowName";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkflowName", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkflowName", currentResult);
         currentResult.setValue("description", "User defined name of this workflow. ");
      }

      if (!descriptors.containsKey("WorkflowTarget")) {
         getterName = "getWorkflowTarget";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkflowTarget", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkflowTarget", currentResult);
         currentResult.setValue("description", "Returns the target that was specified when the workflow was created. It will typically be either the name of the domain, of one or more clusters, or one or more servers. ");
      }

      if (!descriptors.containsKey("WorkflowType")) {
         getterName = "getWorkflowType";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkflowType", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkflowType", currentResult);
         currentResult.setValue("description", "Returns a string that can be used to identify the different types of workflows. ");
      }

      if (!descriptors.containsKey("Running")) {
         getterName = "isRunning";
         setterName = null;
         currentResult = new PropertyDescriptor("Running", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Running", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the task is still running.</p> ");
      }

      if (!descriptors.containsKey("SystemTask")) {
         getterName = "isSystemTask";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemTask", RolloutTaskRuntimeMBean.class, getterName, (String)setterName);
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
      Method mth = RolloutTaskRuntimeMBean.class.getMethod("waitFor");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Waits for the process to finish. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutTaskRuntimeMBean.class.getMethod("canResume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "It is possible to resume (execute or revert) a workflow if it was already executed and is in a FAIL, REVERT_FAIL, CANCELED or REVERT_CANCELED state. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutTaskRuntimeMBean.class.getMethod("cancel");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalStateException if the command implements CommandCancelInterface but is in a state that cannot be interrupted"), BeanInfoHelper.encodeEntities("weblogic.management.workflow.WorkflowException if workflow is not running.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Sets the cancel flag for the workflow. A canceled workflow will stop as soon as possible. If a command implements the CommandCancelInterface, it will be notified when this flag is set, otherwise, the command will either check it periodically, or the workflow will cancel when the current command completes. </p> <p>The state of a cancelled command will be {@code CANCELED} or {@code REVERT_CANCELED}.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutTaskRuntimeMBean.class.getMethod("printLog", PrintWriter.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("out", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Prints information that was logged during the processing of the task (e.g. server startup log) to the given Writer.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = RolloutTaskRuntimeMBean.class.getMethod("showNextExecuteStep");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the next step that will be executed if the workflow is eligible to be resumed and the user were to call execute on it. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutTaskRuntimeMBean.class.getMethod("showNextRevertStep");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get the next step that will be reverted if the workflow is eligible to be resumed and the user were to call revert on it. ");
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
