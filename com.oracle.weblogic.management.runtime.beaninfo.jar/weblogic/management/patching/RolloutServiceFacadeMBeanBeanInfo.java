package weblogic.management.patching;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.RolloutServiceRuntimeMBean;
import weblogic.management.runtime.WorkflowTaskRuntimeMBean;

public class RolloutServiceFacadeMBeanBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RolloutServiceRuntimeMBean.class;

   public RolloutServiceFacadeMBeanBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RolloutServiceFacadeMBeanBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.patching.RolloutServiceFacadeMBean");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.patching");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.RolloutServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("ActiveWorkflows")) {
         getterName = "getActiveWorkflows";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveWorkflows", RolloutServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveWorkflows", currentResult);
         currentResult.setValue("description", "Lists all currently running workflows. Workflows are active elements which can be finished in separated threads. Returned list is just snapshot of current state and it means that workflow can be finished before you process result. ");
         currentResult.setValue("relationship", "containment");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for List<WorkflowTaskRuntimeMBean>");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("AllWorkflows")) {
         getterName = "getAllWorkflows";
         setterName = null;
         currentResult = new PropertyDescriptor("AllWorkflows", RolloutServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AllWorkflows", currentResult);
         currentResult.setValue("description", "Lists all workflows. ");
         currentResult.setValue("relationship", "containment");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("CompleteWorkflows")) {
         getterName = "getCompleteWorkflows";
         setterName = null;
         currentResult = new PropertyDescriptor("CompleteWorkflows", RolloutServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompleteWorkflows", currentResult);
         currentResult.setValue("description", "Lists all completed workflows. ");
         currentResult.setValue("relationship", "containment");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for List<WorkflowTaskRuntimeMBean>");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("InactiveWorkflows")) {
         getterName = "getInactiveWorkflows";
         setterName = null;
         currentResult = new PropertyDescriptor("InactiveWorkflows", RolloutServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InactiveWorkflows", currentResult);
         currentResult.setValue("description", "Lists all currently not running workflows - stopped and complete. Workflows are active elements which can be finished in separated threads. Returned list is just snapshot of current state and it means that workflow can be finished before you process result. ");
         currentResult.setValue("relationship", "containment");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("StoppedWorkflows")) {
         getterName = "getStoppedWorkflows";
         setterName = null;
         currentResult = new PropertyDescriptor("StoppedWorkflows", RolloutServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StoppedWorkflows", currentResult);
         currentResult.setValue("description", "Lists all completed workflows. ");
         currentResult.setValue("relationship", "containment");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RolloutServiceRuntimeMBean.class.getMethod("lookupCompleteWorkflow", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "the ID of the workflow to seek ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a WorkflowProgress object if the workflow id is found and the workflow is complete. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CompleteWorkflows");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("lookupActiveWorkflow", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "the ID of the workflow to seek ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a WorkflowProgress object if the workflow id is found and the workflow is active. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ActiveWorkflows");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("lookupInactiveWorkflow", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "the ID of the workflow to seek ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a WorkflowProgress object if the workflow id is found and the workflow is inactive. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "InactiveWorkflows");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("lookupAllWorkflow", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "the ID of the workflow to seek ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a WorkflowProgress object if the workflow id is found ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "AllWorkflows");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("lookupStoppedWorkflow", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "the ID of the workflow to seek ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a WorkflowProgress object if the workflow id is found and the workflow is stopped. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "StoppedWorkflows");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = RolloutServiceRuntimeMBean.class.getMethod("rolloutUpdate", String.class, String.class, String.class, String.class, String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("rolloutOracleHome", "the location of the new OracleHome to use "), createParameterDescriptor("backupOracleHome", "the location where the current OracleHome should be moved "), createParameterDescriptor("isRollback", "true if the new OracleHome is a lower patch version  than the current one "), createParameterDescriptor("javaHome", "the path to the new JavaHome directory "), createParameterDescriptor("applicationProperties", "the path of a formatted text file containing the information needed to upgrade applications "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates and starts a workflow that can be used to update OracleHome, JavaHome, Applications, or any combination of those. rolloutUpdate(target, [patchedOracleHome, backupOracleHome, isRollback], [javaHome], [applicationProperties], [options]) ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("rolloutOracleHome", String.class, String.class, String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("rolloutOracleHome", "the location of the new OracleHome to use "), createParameterDescriptor("backupOracleHome", "the location where the current OracleHome should be moved "), createParameterDescriptor("isRollback", "true if the new OracleHome is a lower patch version  than the current one "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates and starts a workflow that can be used to update OracleHome. rolloutUpdate(target, [patchedOracleHome, backupOracleHome, isRollback], [options]) ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("initializeRolloutOracleHome", String.class, String.class, String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("rolloutOracleHome", "the location of the new OracleHome to use "), createParameterDescriptor("backupOracleHome", "the location where the current OracleHome should be moved "), createParameterDescriptor("isRollback", "true if the new OracleHome is a lower patch version  than the current one "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates but does not start a workflow that can be used to update OracleHome. rolloutUpdate(target, [patchedOracleHome, backupOracleHome, isRollback], [options]) ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("rolloutJavaHome", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("javaHome", "the path to the new JavaHome directory "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates and starts a workflow that can be used to update JavaHome. rolloutUpdate(target, [javaHome], [options]) ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("rolloutApplications", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("applicationProperties", "the path of a formatted text file containing the information needed to upgrade applications "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates and starts a workflow that can be used to update Applications. rolloutUpdate(target, [applicationProperties], [options]) ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("rollingRestart", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "can be the name of a domain, clusters or servers "), createParameterDescriptor("options", "comma separated list of &lt;name&gt;=&lt;value&gt; options ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates and starts a workflow that can be used to update restart servers, one at a time. rolloutUpdate(target, [patchedOracleHome, backupOracleHome, isRollback], [javaHome], [applicationProperties], [options]) ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("shutdownServer", String.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "Name of the server that needs to be shutdown "), createParameterDescriptor("sessionTimeout", "shutdownTimeout Time limit (in seconds) for server to complete a graceful shutdown. The default is 0 indicating no timeout. "), createParameterDescriptor("ignoreSessions", "Drop sessions immediately rather than waiting for them to complete or timeout. "), createParameterDescriptor("waitForAllSessions", "Wait for all sessions (even sessions backed by a server) to complete. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Shutdowns the named server. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("startServer", String.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "Name of managed server to be started "), createParameterDescriptor("sessionTimeout", "(required for revert) shutdownTimeout Time limit (in seconds) for server to complete a graceful shutdown. "), createParameterDescriptor("ignoreSessions", "(required for revert) Drop sessions immediately rather than waiting for them to complete or timeout. "), createParameterDescriptor("waitForAllSessions", "Wait for all sessions (even sessions backed by a server) to complete. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Starts the managed server (using the new path location). ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("restartNodeManager", String.class, Boolean.class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("machineName", "Name of MachineMBean where NodeManager runs "), createParameterDescriptor("isAdminServer", "true if the affected server is the AdminServer "), createParameterDescriptor("timeoutMillis", "How long to wait for the client to reconnect to the NodeManager after it restarts.  Timeout exceeded will consider the task failed and the NodeManager not reachable. Default is 3 minutes. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Restart NodeManager.  The java based NodeManager process will exit and the startNodeManager script will detect the need to restart and will restart the NodeManager using the same original call path that was used to start the NodeManager.  If this call path was using a symbolic link, then the NodeManager will be started from the location of the symbolic link which may have the affect of starting the NodeManager with an updated set of binaries or scripts. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("execScript", String.class, String.class, Long.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("machineName", "Name of MachineMBean where the NodeManager runs "), createParameterDescriptor("scriptName", "Name of script to run "), createParameterDescriptor("timeoutMillis", "millis to wait for script exec to complete.  Once the specified time has elapsed the script process is halted and the NodeManager returns the error denoting the timeout.  Default is 0 where we block until completion. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "NEVER");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Run a custom script from the domain/bin/patching directory on the specified Machine. ");
         currentResult.setValue("role", "operation");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("getWorkflowTask", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("id", "of executed workflow ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns {@code WorkflowTaskRuntimeMBean} for given id. Progress must NOT be yet deleted and must be started by this service. ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("deleteWorkflow", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("workflowId", "is ID of workflow to be deleted - {@code WorkflowTaskRuntimeMBean.getWorkflowId()} ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("IOException")};
         currentResult.setValue("throws", roleObjectArray);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Delete workflow data of finished workflow ");
         currentResult.setValue("role", "operation");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("executeWorkflow", WorkflowTaskRuntimeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wfTask", "which should be executed - continued. {@code delegate.isStopped()} must be {@code null} ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Continue in workflow execution. Can be called only for terminated workflows. ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      mth = RolloutServiceRuntimeMBean.class.getMethod("revertWorkflow", WorkflowTaskRuntimeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wfTask", "which should be reverted. {@code delegate.isStopped()} must be {@code null} ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Reverts workflow execution. Can be called only for terminated workflows. ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("admin")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
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
