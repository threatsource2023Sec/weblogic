package com.oracle.weblogic.lifecycle.orchestration;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.Orchestrator;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.core.LifecycleConfigFactory;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.JsonObject;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowLifecycleManager;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowProgress.State;

@Service
public class OrchestratorImpl implements Orchestrator {
   static final String LIFECYCLE_SERVICE = "LifecycleService";
   @Inject
   private WorkflowLifecycleManager workflowManager;
   @Inject
   private LifecycleManager lifecycleManager;
   @Inject
   private RuntimeManager runtimeManager;
   @Inject
   LifecycleConfigFactory lifecycleConfigFactory;
   private Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   static final String WORKFLOW2 = "WORKFLOW2";
   static final String WORKFLOW2_ID = "orchestration-workflow-2";
   static final String WORKFLOW1ROLLBACK_ID = "orchestration-workflow-rollback";
   private LifecycleConfig lifecycleConfig;

   public Environment orchestrate(String envName, JsonObject orchestrationObject) throws LifecycleException {
      if (envName != null && !envName.isEmpty()) {
         if (orchestrationObject == null) {
            throw new IllegalArgumentException("Invalid orchestrationObject: null");
         } else {
            this.logger.log(Level.INFO, "orchestrate envName = " + envName + ", orchestrationOjbect = " + orchestrationObject.toString());
            boolean isCreateNewEnv = true;
            List envList = this.lifecycleManager.getEnvironments();
            if (envList != null) {
               Iterator var5 = envList.iterator();

               while(var5.hasNext()) {
                  Environment env = (Environment)var5.next();
                  if (envName.equals(env.getName())) {
                     List associationList = env.getAssociations();
                     if (associationList != null && !associationList.isEmpty()) {
                        throw new IllegalArgumentException("Invalid envName: environment with name " + envName + " already exists and already contains partitions.");
                     }

                     isCreateNewEnv = false;
                  }
               }
            }

            LifecycleWorkflowBuilderFactory builderFactory = LifecycleWorkflowBuilderFactory.getInstance();
            WorkflowBuilder workflowBuilder = builderFactory.getLifecycleWorkflowBuilder(isCreateNewEnv, envName, orchestrationObject, this.runtimeManager);
            WorkflowProgress progress = this.workflowManager.startWorkflow(workflowBuilder, "LifecycleService");

            try {
               progress.waitFor();
            } catch (Exception var20) {
               throw new LifecycleException("Exception thrown during orchestrate", var20);
            }

            String progressString = progress.getProgressString();
            String progressName = progress.getName();
            String serviceName = progress.getServiceName();
            Date startTime = progress.getStartTime();
            Date endTime = progress.getEndTime();
            int numCompletedCommands = progress.getNumCompletedCommands();
            int numTotalCommands = progress.getNumTotalCommands();
            WorkflowProgress.State state = progress.getState();
            String id = progress.getWorkflowId();
            boolean isComplete = progress.isComplete();
            boolean canResume = progress.canResume();
            this.logger.log(Level.INFO, "WorkflowProgress:\n   progressString:       {0}\n   progressName:         {1}\n   serviceName:          {2}\n   startTime:            {3}\n   endTime:              {4}\n   numCompletedCommands: {5}\n   numTotalCommands:     {6}\n   state:                {7}\n   id:                   {8}\n   isComplete:           {9}\n   canResume:            {10}", new Object[]{progressString, progressName, serviceName, startTime, endTime, numCompletedCommands, numTotalCommands, state, id, isComplete, canResume});
            Environment env = null;
            if (state.equals(State.SUCCESS)) {
               env = this.lifecycleManager.getEnvironment(envName);
               return env;
            } else if (state.equals(State.REVERTED)) {
               this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Reverting all previously successful commands. Check server log for more details.");
               throw new LifecycleException(progressString);
            } else if (state.equals(State.REVERT_FAIL)) {
               this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Error encountred during revert of all previously successful commands. Check server log for more details.");
               throw new LifecycleException(progressString);
            } else {
               this.logger.log(Level.WARNING, "Error during lifecycle orchestration.  The WorkflowProgress State = {0}. Check the server log for more details", state);
               throw new LifecycleException(progressString);
            }
         }
      } else {
         throw new IllegalArgumentException("Invalid envName: null or empty");
      }
   }

   public boolean deleteOrchestration(String envName, JsonObject orchestrationObject) throws LifecycleException {
      String orchestrationName = null;
      if (orchestrationObject != null) {
         orchestrationName = orchestrationObject.getString("orchestrationName");
      }

      return this.deleteOrchestration(envName, orchestrationName, orchestrationObject);
   }

   public boolean deleteOrchestration(String envName, String orchestrationName) throws LifecycleException {
      return this.deleteOrchestration(envName, orchestrationName, (JsonObject)null);
   }

   private boolean deleteOrchestration(String envName, String orchestrationName, JsonObject orchestrationObject) throws LifecycleException {
      String className = OrchestratorImpl.class.getName();
      String methodName = "deleteOrchestration";
      if (this.logger != null && this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(className, "deleteOrchestration", new Object[]{envName, orchestrationName, orchestrationObject});
      }

      if (orchestrationName != null && orchestrationName.equals("deleteAll")) {
         Environment env = this.getEnvironment(envName);
         LifecycleWorkflowBuilderFactory builderFactory = LifecycleWorkflowBuilderFactory.getInstance();
         WorkflowBuilder workflowBuilder = builderFactory.getLifecycleWorkflowDeleteAllBuilder(env, orchestrationObject);
         WorkflowProgress progress = this.workflowManager.startWorkflow(workflowBuilder, "lifecycle_orchestration_delete_all");

         try {
            progress.waitFor();
         } catch (Exception var22) {
            throw new LifecycleException("Exception thrown during orchestrate", var22);
         }

         String progressString = progress.getProgressString();
         String progressName = progress.getName();
         String serviceName = progress.getServiceName();
         Date startTime = progress.getStartTime();
         Date endTime = progress.getEndTime();
         int numCompletedCommands = progress.getNumCompletedCommands();
         int numTotalCommands = progress.getNumTotalCommands();
         WorkflowProgress.State state = progress.getState();
         String id = progress.getWorkflowId();
         boolean isComplete = progress.isComplete();
         boolean canResume = progress.canResume();
         this.logger.log(Level.INFO, "WorkflowProgress:\n   progressString:       {0}\n   progressName:         {1}\n   serviceName:          {2}\n   startTime:            {3}\n   endTime:              {4}\n   numCompletedCommands: {5}\n   numTotalCommands:     {6}\n   state:                {7}\n   id:                   {8}\n   isComplete:           {9}\n   canResume:            {10}", new Object[]{progressString, progressName, serviceName, startTime, endTime, numCompletedCommands, numTotalCommands, state, id, isComplete, canResume});
         boolean isSuccessful = false;
         if (state.equals(State.SUCCESS)) {
            isSuccessful = true;
            if (this.logger != null && this.logger.isLoggable(Level.FINER)) {
               this.logger.exiting(className, "deleteOrchestration", isSuccessful);
            }

            return isSuccessful;
         } else if (state.equals(State.REVERTED)) {
            this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Reverting all previously successful commands. Check server log for more details.");
            throw new LifecycleException(progressString);
         } else if (state.equals(State.REVERT_FAIL)) {
            this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Error encountred during revert of all previously successful commands. Check server log for more details.");
            throw new LifecycleException(progressString);
         } else {
            this.logger.log(Level.WARNING, "Error during lifecycle orchestration.  The WorkflowProgress State = {0}. Check the server log for more details", state);
            throw new LifecycleException(progressString);
         }
      } else {
         throw new IllegalArgumentException("Invalid orchestration name: " + orchestrationName + "  Supported orchestration delete operations are: " + "deleteAll");
      }
   }

   public boolean deleteOrchestration(String envName, String partitionName, String type, String orchestrationName) throws LifecycleException {
      Environment env = this.getEnvironment(envName);
      LifecycleWorkflowBuilderFactory builderFactory = LifecycleWorkflowBuilderFactory.getInstance();
      WorkflowBuilder workflowBuilder = builderFactory.getLifecycleWorkflowDeleteBuilder(env, partitionName, type);
      WorkflowProgress progress = this.workflowManager.startWorkflow(workflowBuilder, "lifecycle_orchestration_delete");

      try {
         progress.waitFor();
      } catch (Exception var21) {
         throw new LifecycleException("Exception thrown during orchestrate", var21);
      }

      String progressString = progress.getProgressString();
      String progressName = progress.getName();
      String serviceName = progress.getServiceName();
      Date startTime = progress.getStartTime();
      Date endTime = progress.getEndTime();
      int numCompletedCommands = progress.getNumCompletedCommands();
      int numTotalCommands = progress.getNumTotalCommands();
      WorkflowProgress.State state = progress.getState();
      String id = progress.getWorkflowId();
      boolean isComplete = progress.isComplete();
      boolean canResume = progress.canResume();
      this.logger.log(Level.INFO, "WorkflowProgress:\n   progressString:       {0}\n   progressName:         {1}\n   serviceName:          {2}\n   startTime:            {3}\n   endTime:              {4}\n   numCompletedCommands: {5}\n   numTotalCommands:     {6}\n   state:                {7}\n   id:                   {8}\n   isComplete:           {9}\n   canResume:            {10}", new Object[]{progressString, progressName, serviceName, startTime, endTime, numCompletedCommands, numTotalCommands, state, id, isComplete, canResume});
      boolean isSuccessful = false;
      if (state.equals(State.SUCCESS)) {
         isSuccessful = true;
      } else if (state.equals(State.REVERTED)) {
         this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Reverting all previously successful commands. Check server log for more details.");
      } else if (state.equals(State.REVERT_FAIL)) {
         this.logger.log(Level.WARNING, "One of the lifecycle commands (CreateEnvironment, CreatePartition, AddPartitionToEnv, AssociatePartition) failed during orchestration. Error encountred during revert of all previously successful commands. Check server log for more details.");
      } else {
         this.logger.log(Level.WARNING, "Error during lifecycle orchestration.  The WorkflowProgress State = {0}. Check the server log for more details", state);
      }

      return isSuccessful;
   }

   private Environment getEnvironment(String envName) {
      if (envName != null && !envName.isEmpty()) {
         List envList = this.lifecycleManager.getEnvironments();
         if (envList != null && !envList.isEmpty()) {
            Environment envFound = null;
            Iterator var4 = envList.iterator();

            while(var4.hasNext()) {
               Environment e = (Environment)var4.next();
               if (e != null && envName.equals(e.getName())) {
                  envFound = e;
                  break;
               }
            }

            if (envFound == null) {
               throw new IllegalArgumentException("Environment named " + envName + " does not exist in Lifecycle config");
            } else {
               return envFound;
            }
         } else {
            throw new IllegalArgumentException("Environment named " + envName + " does not exist in Lifecycle config");
         }
      } else {
         throw new IllegalArgumentException("Invalid envName: null or empty");
      }
   }

   public Environment orchestrate(String editSessionName, String envName, JsonObject orchestrationObject) throws LifecycleException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public boolean deleteOrchestration(String editSessionName, String envName, String orchestrationName) throws LifecycleException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   static WorkflowProgress.State logProgress(Logger logger, WorkflowProgress progress, String workflow) {
      String progressString = progress.getProgressString();
      String progressName = progress.getName();
      String serviceName = progress.getServiceName();
      Date startTime = progress.getStartTime();
      Date endTime = progress.getEndTime();
      int numCompletedCommands = progress.getNumCompletedCommands();
      int numTotalCommands = progress.getNumTotalCommands();
      WorkflowProgress.State state = progress.getState();
      String id = progress.getWorkflowId();
      boolean isComplete = progress.isComplete();
      boolean canResume = progress.canResume();
      logger.log(Level.INFO, "WorkflowProgress of workflow: " + workflow + "\n   progressString:       {0}\n   progressName:         {1}\n   serviceName:          {2}\n   startTime:            {3}\n   endTime:              {4}\n   numCompletedCommands: {5}\n   numTotalCommands:     {6}\n   state:                {7}\n   id:                   {8}\n   isComplete:           {9}\n   canResume:            {10}", new Object[]{progressString, progressName, serviceName, startTime, endTime, numCompletedCommands, numTotalCommands, state, id, isComplete, canResume});
      return state;
   }
}
