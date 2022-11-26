package weblogic.cluster.migration.management;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.MigrationTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImplBeanInfo;

public class MigrationTaskBeanInfo extends TaskRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MigrationTaskRuntimeMBean.class;

   public MigrationTaskBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MigrationTaskBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.migration.management.MigrationTask");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.cluster.migration.management");
      String description = (new String("<p>Exposes monitoring information about an ongoing and potentially long-running administrative process.  This should be taken to mean, at minimum, any OAM operation involving IO.  Examples include starting and stopping servers, deploying and undeploying applications, or migrating services.</p>  <p>An MBean operation method of this sort should place the actual work on an ExecuteQueue and immediately return an instance of TaskRuntimeMBean to the caller.  The caller can then use this to track the task's progress as desired, and if appropriate, provide facilities for user interaction with the task, e.g. \"cancel\" or \"continue anyway.\"  OA&M clients can also query for all instances of TaskRuntimeMBean to get a summary of both currently-running and recently-completed tasks.</p> <p>Instance of TaskRuntimeMBean continue to exist in the MBeanServer after the completion of the work they describe.  They will eventually either be explicitly deregistered by an OA&M client, or removed by a scavenger process which periodically purges TaskRuntimeMBeans that have been completed for some time.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MigrationTaskRuntimeMBean");
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
         currentResult = new PropertyDescriptor("BeginTime", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeginTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was started.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>A description of this task.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DestinationServer")) {
         getterName = "getDestinationServer";
         setterName = null;
         currentResult = new PropertyDescriptor("DestinationServer", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DestinationServer", currentResult);
         currentResult.setValue("description", "<p>Provides the identity of the server to which the migration is moving.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EndTime")) {
         getterName = "getEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("EndTime", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EndTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was completed.</p>  <p>A value of <code>-1</code> indicates that the task is currently running.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("Error")) {
         getterName = "getError";
         setterName = null;
         currentResult = new PropertyDescriptor("Error", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Error", currentResult);
         currentResult.setValue("description", "<p>Returns an exception describing the error, if any, that occurred while performing this task.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("restName", "taskError");
      }

      if (!descriptors.containsKey("MigratableTarget")) {
         getterName = "getMigratableTarget";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratableTarget", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MigratableTarget", currentResult);
         currentResult.setValue("description", "<p>Provides the MigratableTarget object that is being migrated.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentTask")) {
         getterName = "getParentTask";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentTask", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentTask", currentResult);
         currentResult.setValue("description", "<p>The task of which this task is a part.</p>  <p>A value of <code>null</code> indicates that this task is not a subtask.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSubTasks")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("restRelationship", "reference");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Progress")) {
         getterName = "getProgress";
         setterName = null;
         currentResult = new PropertyDescriptor("Progress", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Progress", currentResult);
         currentResult.setValue("description", "<p>The progress of this task.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("SourceServer")) {
         getterName = "getSourceServer";
         setterName = null;
         currentResult = new PropertyDescriptor("SourceServer", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SourceServer", currentResult);
         currentResult.setValue("description", "<p>Provides the identity of the server from which the migration is moving.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "<p>The status of this task.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("restName", "taskStatus");
      }

      if (!descriptors.containsKey("StatusCode")) {
         getterName = "getStatusCode";
         setterName = null;
         currentResult = new PropertyDescriptor("StatusCode", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StatusCode", currentResult);
         currentResult.setValue("description", "<p>Provides an int describing the status of this Task.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubTasks")) {
         getterName = "getSubTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("SubTasks", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SubTasks", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TaskRuntimeMBeans</code> that describes a set of parallel tasks which are components of this task.</p>  <p>A value of <code>null</code> indicates that this task has no subtasks.</p>  <p>A simple example of a task with subtasks would be one which monitors a user's request to start a cluster; that task should return a set of subtasks indicating the individual server-startup processes which compose the overall cluster-startup task.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JTA")) {
         getterName = "isJTA";
         setterName = null;
         currentResult = new PropertyDescriptor("JTA", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JTA", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the migration task moves a JTA recovery manager.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Running")) {
         getterName = "isRunning";
         setterName = null;
         currentResult = new PropertyDescriptor("Running", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Running", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Task is still running.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemTask")) {
         getterName = "isSystemTask";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemTask", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SystemTask", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this task was initiated by the server versus a user.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Terminal")) {
         getterName = "isTerminal";
         setterName = null;
         currentResult = new PropertyDescriptor("Terminal", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Terminal", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Task was successful, failed or was canceled.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WaitingForUser")) {
         getterName = "isWaitingForUser";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForUser", MigrationTaskRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForUser", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Task is waiting for user input.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
      Method mth = MigrationTaskRuntimeMBean.class.getMethod("continueWithSourceServerDown", Boolean.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("isSourceServerDown", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the user input to continue if the task is in the wait state STATUS_AWAITING_IS_SOURCE_DOWN.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigrationTaskRuntimeMBean.class.getMethod("continueWithDestinationServerDown", Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("isDestinationServerDown", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides the user input to continue if the task is in the wait state STATUS_AWAITING_IS_DESINATION_DOWN.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigrationTaskRuntimeMBean.class.getMethod("cancel");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Attempts to cancel this task.</p>  <p>An exception is thrown to indicate failure to cancel the task. Not all tasks can be cancelled.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = MigrationTaskRuntimeMBean.class.getMethod("printLog", PrintWriter.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("out", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
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
