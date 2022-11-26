package weblogic.management.deploy;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImplBeanInfo;

public class DeploymentTaskRuntimeBeanInfo extends TaskRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DeploymentTaskRuntimeMBean.class;

   public DeploymentTaskRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeploymentTaskRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.deploy.DeploymentTaskRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.deploy.api.spi.WebLogicDeploymentManager} ");
      beanDescriptor.setValue("package", "weblogic.management.deploy");
      String description = (new String("Base interface for deployment task MBeans. These MBeans track the progress of a deployment task. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.DeploymentTaskRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ApplicationId")) {
         getterName = "getApplicationId";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationId", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationId", currentResult);
         currentResult.setValue("description", "<p>The ID for the application that was specified to DeployerRuntime.activate</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ApplicationName")) {
         getterName = "getApplicationName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationName", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationName", currentResult);
         currentResult.setValue("description", "<p>The name for the application that was specified to DeployerRuntime.activate</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ApplicationVersionIdentifier")) {
         getterName = "getApplicationVersionIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationVersionIdentifier", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationVersionIdentifier", currentResult);
         currentResult.setValue("description", "Returns the associated AppDeploymentMBean's VersionIdentifier. This is valid only for AppDeploymentMBean or LibraryMBean and for all other for all other types of BasicDeploymentMBeans, it will return null. And also if there is no BasicDeploymentMBean associated with this task, it will return null. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("BeginTime")) {
         getterName = "getBeginTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeginTime", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("BeginTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was started.</p> ");
         currentResult.setValue("restName", "startTimeAsLong");
      }

      if (!descriptors.containsKey("CancelState")) {
         getterName = "getCancelState";
         setterName = null;
         currentResult = new PropertyDescriptor("CancelState", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("CancelState", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentData")) {
         getterName = "getDeploymentData";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentData", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("DeploymentData", currentResult);
         currentResult.setValue("description", "<p>Provides the data associated with this task</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentMBean")) {
         getterName = "getDeploymentMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentMBean", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("DeploymentMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("DeploymentObject")) {
         getterName = "getDeploymentObject";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentObject", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("DeploymentObject", currentResult);
         currentResult.setValue("description", "<p>Lists the Application MBean involved in this task. This returns the Admin MBean, the one based on config.xml, that applies to all servers this application is associated with.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>A description of this task.</p> ");
      }

      if (!descriptors.containsKey("EndTime")) {
         getterName = "getEndTime";
         setterName = null;
         currentResult = new PropertyDescriptor("EndTime", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("EndTime", currentResult);
         currentResult.setValue("description", "<p>The time at which this task was completed.</p>  <p>A value of <code>-1</code> indicates that the task is currently running.</p> ");
         currentResult.setValue("restName", "endTimeAsLong");
      }

      if (!descriptors.containsKey("Error")) {
         getterName = "getError";
         setterName = null;
         currentResult = new PropertyDescriptor("Error", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Error", currentResult);
         currentResult.setValue("description", "<p>Returns an exception describing the error, if any, that occurred while performing this task.</p> ");
         currentResult.setValue("restName", "taskError");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         currentResult = new PropertyDescriptor("Id", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "<p>Provides a reference id assigned to a task.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("NotificationLevel")) {
         getterName = "getNotificationLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotificationLevel";
         }

         currentResult = new PropertyDescriptor("NotificationLevel", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("NotificationLevel", currentResult);
         currentResult.setValue("description", "<p>Provides the notification level applied to this task.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowedSet", seeObjectArray);
      }

      if (!descriptors.containsKey("ParentTask")) {
         getterName = "getParentTask";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentTask", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("ParentTask", currentResult);
         currentResult.setValue("description", "<p>The task of which this task is a part.</p>  <p>A value of <code>null</code> indicates that this task is not a subtask.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getSubTasks")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("restRelationship", "reference");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Progress")) {
         getterName = "getProgress";
         setterName = null;
         currentResult = new PropertyDescriptor("Progress", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Progress", currentResult);
         currentResult.setValue("description", "<p>The progress of this task.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Source")) {
         getterName = "getSource";
         setterName = null;
         currentResult = new PropertyDescriptor("Source", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Source", currentResult);
         currentResult.setValue("description", "<p>Provides the name of the source file that was specified to DeployerRuntime.activate.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>Provides notice of the overall state of this task.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Status")) {
         getterName = "getStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("Status", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Status", currentResult);
         currentResult.setValue("description", "<p>The status of this task.</p> ");
         currentResult.setValue("restName", "taskStatus");
      }

      if (!descriptors.containsKey("SubTasks")) {
         getterName = "getSubTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("SubTasks", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("SubTasks", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TaskRuntimeMBeans</code> that describes a set of parallel tasks which are components of this task.</p>  <p>A value of <code>null</code> indicates that this task has no subtasks.</p>  <p>A simple example of a task with subtasks would be one which monitors a user's request to start a cluster; that task should return a set of subtasks indicating the individual server-startup processes which compose the overall cluster-startup task.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("Targets", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>Provides target based deployment status information for this deployment. For distributed deployment, there is one TargetStatus for each target</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Task")) {
         getterName = "getTask";
         setterName = null;
         currentResult = new PropertyDescriptor("Task", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Task", currentResult);
         currentResult.setValue("description", "<p>Indicates a specific task associated with this MBean</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TaskMessages")) {
         getterName = "getTaskMessages";
         setterName = null;
         currentResult = new PropertyDescriptor("TaskMessages", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("TaskMessages", currentResult);
         currentResult.setValue("description", "<p>Provides an ordered list of messages generated for the task. Each member in the list is a String object.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InUse")) {
         getterName = "isInUse";
         setterName = null;
         currentResult = new PropertyDescriptor("InUse", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("InUse", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the MBean is free for deletion or timeout.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NewSource")) {
         getterName = "isNewSource";
         setterName = null;
         currentResult = new PropertyDescriptor("NewSource", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("NewSource", currentResult);
         currentResult.setValue("description", "Indicates whether a new source for the application was specified in an deployment request. ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PendingActivation")) {
         getterName = "isPendingActivation";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingActivation", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("PendingActivation", currentResult);
         currentResult.setValue("description", "Tells us if the task in a pending state due to it being requested while a non-exclusive lock is held on the domain configuration. The task will be processed only after the session is activated ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Retired")) {
         getterName = "isRetired";
         setterName = null;
         currentResult = new PropertyDescriptor("Retired", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Retired", currentResult);
         currentResult.setValue("description", "Tells us if the task is retired after completion of the task. This will allow Deployer tool to remove it when user makes purgeTasks call. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("Running")) {
         getterName = "isRunning";
         setterName = null;
         currentResult = new PropertyDescriptor("Running", DeploymentTaskRuntimeMBean.class, getterName, setterName);
         descriptors.put("Running", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the task is still running.</p> ");
      }

      if (!descriptors.containsKey("SystemTask")) {
         getterName = "isSystemTask";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemTask", DeploymentTaskRuntimeMBean.class, getterName, setterName);
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
      Method mth = DeploymentTaskRuntimeMBean.class.getMethod("findTarget", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "is the name of a target (server or cluster name) ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lists the status for a specific target of this deployment.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentTaskRuntimeMBean.class.getMethod("cancel");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Attempts to cancel the task. Any actions which have yet to start will be inhibited. Any completed actions will remain in place.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentTaskRuntimeMBean.class.getMethod("start");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Initiates the request. When invoking {@link DeployerRuntimeMBean#activate(String, String, String, DeploymentData, String, boolean)}, {@link DeployerRuntimeMBean#deactivate(String, DeploymentData, String, boolean)}, {@link DeployerRuntimeMBean#unprepare(String, DeploymentData, String, boolean)}, or {@link DeployerRuntimeMBean#remove(String, DeploymentData, String, boolean)} with the startTask option set to false, this method is used to initiate the task. throws ManagementException if task is already started or any failures occur during task processing.</p> ");
         currentResult.setValue("role", "operation");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DeploymentTaskRuntimeMBean.class.getMethod("printLog", PrintWriter.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("out", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Prints information that was logged during the processing of the task (e.g. server startup log) to the given Writer.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = DeploymentTaskRuntimeMBean.class.getMethod("waitForTaskCompletion", Long.TYPE);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
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
