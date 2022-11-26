package weblogic.management.runtime;

import java.io.IOException;
import weblogic.management.ManagementException;

public interface RolloutServiceRuntimeMBean extends RuntimeMBean {
   WorkflowTaskRuntimeMBean rolloutUpdate(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws ManagementException;

   RolloutTaskRuntimeMBean rolloutOracleHome(String var1, String var2, String var3, String var4, String var5) throws ManagementException;

   RolloutTaskRuntimeMBean initializeRolloutOracleHome(String var1, String var2, String var3, String var4, String var5) throws ManagementException;

   WorkflowTaskRuntimeMBean rolloutJavaHome(String var1, String var2, String var3) throws ManagementException;

   WorkflowTaskRuntimeMBean rolloutApplications(String var1, String var2, String var3) throws ManagementException;

   WorkflowTaskRuntimeMBean rollingRestart(String var1, String var2) throws ManagementException;

   WorkflowTaskRuntimeMBean shutdownServer(String var1, int var2, boolean var3, boolean var4) throws ManagementException;

   WorkflowTaskRuntimeMBean startServer(String var1, int var2, boolean var3, boolean var4) throws ManagementException;

   WorkflowTaskRuntimeMBean restartNodeManager(String var1, Boolean var2, long var3) throws ManagementException;

   WorkflowTaskRuntimeMBean execScript(String var1, String var2, long var3) throws ManagementException;

   WorkflowTaskRuntimeMBean getWorkflowTask(String var1) throws ManagementException;

   void deleteWorkflow(String var1) throws IOException;

   WorkflowTaskRuntimeMBean executeWorkflow(WorkflowTaskRuntimeMBean var1) throws ManagementException;

   WorkflowTaskRuntimeMBean revertWorkflow(WorkflowTaskRuntimeMBean var1) throws ManagementException;

   WorkflowTaskRuntimeMBean[] getCompleteWorkflows() throws ManagementException;

   WorkflowTaskRuntimeMBean lookupCompleteWorkflow(String var1) throws ManagementException;

   WorkflowTaskRuntimeMBean[] getActiveWorkflows() throws ManagementException;

   WorkflowTaskRuntimeMBean lookupActiveWorkflow(String var1) throws ManagementException;

   WorkflowTaskRuntimeMBean[] getInactiveWorkflows() throws ManagementException;

   WorkflowTaskRuntimeMBean lookupInactiveWorkflow(String var1) throws ManagementException;

   WorkflowTaskRuntimeMBean[] getAllWorkflows() throws ManagementException;

   WorkflowTaskRuntimeMBean lookupAllWorkflow(String var1) throws ManagementException;

   WorkflowTaskRuntimeMBean[] getStoppedWorkflows() throws ManagementException;

   WorkflowTaskRuntimeMBean lookupStoppedWorkflow(String var1) throws ManagementException;
}
