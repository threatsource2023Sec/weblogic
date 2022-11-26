package weblogic.management.patching;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.patching.commands.PatchingLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RolloutServiceRuntimeMBean;
import weblogic.management.runtime.RolloutTaskRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkflowTaskRuntimeMBean;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowStateChangeListener;
import weblogic.management.workflow.WorkflowProgress.State;
import weblogic.management.workflow.mbean.WorkflowProgressMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;

public class RolloutServiceFacadeMBean extends RuntimeMBeanDelegate implements RolloutServiceRuntimeMBean, WorkflowStateChangeListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final RolloutService service;
   private final Map taskMap = new HashMap();

   public RolloutServiceFacadeMBean(RolloutService service) throws ManagementException {
      super("rollout-service");
      this.service = service;
      ManagementService.getDomainAccess(kernelId).getDomainRuntime().setRolloutService(this);
   }

   public WorkflowTaskRuntimeMBean rolloutUpdate(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String javaHome, String applicationProperties, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.rolloutUpdate(target, rolloutOracleHome, backupOracleHome, isRollback, javaHome, applicationProperties, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public RolloutTaskRuntimeMBean rolloutOracleHome(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.rolloutOracleHome(target, rolloutOracleHome, backupOracleHome, isRollback, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public RolloutTaskRuntimeMBean initializeRolloutOracleHome(String target, String rolloutOracleHome, String backupOracleHome, String isRollback, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.initializeRolloutOracleHome(target, rolloutOracleHome, backupOracleHome, isRollback, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean rolloutJavaHome(String target, String javaHome, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.rolloutJavaHome(target, javaHome, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean rolloutApplications(String target, String applicationProperties, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.rolloutApplications(target, applicationProperties, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean rollingRestart(String target, String options) throws ManagementException {
      RolloutTaskRuntimeMBean result = new RolloutProgressMBeanDelegate(this.service.rollingRestart(target, options), this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean shutdownServer(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) throws ManagementException {
      WorkflowProgress progress = this.service.shutdownServer(serverName, sessionTimeout, ignoreSessions, waitForAllSessions);
      progress.registerListener(this, false);
      WorkflowTaskRuntimeMBean result = new WorkflowProgressMBeanDelegate(progress, this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean startServer(String serverName, int sessionTimeout, boolean ignoreSessions, boolean waitForAllSessions) throws ManagementException {
      WorkflowProgress progress = this.service.startServer(serverName, sessionTimeout, ignoreSessions, waitForAllSessions);
      progress.registerListener(this, false);
      WorkflowTaskRuntimeMBean result = new WorkflowProgressMBeanDelegate(progress, this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean restartNodeManager(String machineName, Boolean isAdminServer, long timeoutMillis) throws ManagementException {
      WorkflowProgress progress = this.service.restartNodeManager(machineName, isAdminServer, timeoutMillis);
      progress.registerListener(this, false);
      WorkflowTaskRuntimeMBean result = new WorkflowProgressMBeanDelegate(progress, this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public WorkflowTaskRuntimeMBean execScript(String machineName, String scriptName, long timeoutMillis) throws ManagementException {
      WorkflowProgress progress = this.service.execScript(machineName, scriptName, timeoutMillis);
      progress.registerListener(this, false);
      WorkflowTaskRuntimeMBean result = new WorkflowProgressMBeanDelegate(progress, this);
      synchronized(this) {
         this.taskMap.put(result.getWorkflowId(), result);
         return result;
      }
   }

   public synchronized WorkflowTaskRuntimeMBean getWorkflowTask(String id) throws ManagementException {
      WorkflowTaskRuntimeMBean result = (WorkflowTaskRuntimeMBean)this.taskMap.get(id);
      if (result == null) {
         WorkflowProgress progress = this.service.getWorkflowProgress(id);
         if (progress != null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               result = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               result = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(((WorkflowTaskRuntimeMBean)result).getWorkflowId(), result);
         }
      }

      return (WorkflowTaskRuntimeMBean)result;
   }

   public WorkflowTaskRuntimeMBean executeWorkflow(WorkflowTaskRuntimeMBean wfTask) throws ManagementException {
      WorkflowProgress workflowProgress = this.service.getWorkflowProgress(wfTask.getWorkflowId());
      this.service.executeWorkflow(workflowProgress);
      return wfTask;
   }

   public WorkflowTaskRuntimeMBean revertWorkflow(WorkflowTaskRuntimeMBean wfTask) throws ManagementException {
      WorkflowProgress workflowProgress = this.service.getWorkflowProgress(wfTask.getWorkflowId());
      this.service.revertWorkflow(workflowProgress);
      return wfTask;
   }

   public synchronized WorkflowTaskRuntimeMBean[] getCompleteWorkflows() throws ManagementException {
      List prgs = this.service.getCompleteWorkflows();
      List result = new ArrayList(prgs.size());

      Object prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean[])result.toArray(new WorkflowTaskRuntimeMBean[result.size()]);
   }

   public synchronized WorkflowTaskRuntimeMBean lookupCompleteWorkflow(String workflowId) throws ManagementException {
      WorkflowProgress prg = this.service.lookupCompleteWorkflow(workflowId);
      WorkflowTaskRuntimeMBean result = this.convertProgressObject(prg);
      return result;
   }

   public synchronized WorkflowTaskRuntimeMBean[] getActiveWorkflows() throws ManagementException {
      List prgs = this.service.getActiveWorkflows();
      List result = new ArrayList(prgs.size());

      Object prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean[])result.toArray(new WorkflowTaskRuntimeMBean[result.size()]);
   }

   public synchronized WorkflowTaskRuntimeMBean lookupActiveWorkflow(String workflowId) throws ManagementException {
      WorkflowProgress prg = this.service.lookupActiveWorkflow(workflowId);
      WorkflowTaskRuntimeMBean result = this.convertProgressObject(prg);
      return result;
   }

   public synchronized WorkflowTaskRuntimeMBean[] getInactiveWorkflows() throws ManagementException {
      List prgs = this.service.getInactiveWorkflows();
      List result = new ArrayList(prgs.size());

      Object prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean[])result.toArray(new WorkflowTaskRuntimeMBean[result.size()]);
   }

   public synchronized WorkflowTaskRuntimeMBean lookupInactiveWorkflow(String workflowId) throws ManagementException {
      WorkflowProgress prg = this.service.lookupInactiveWorkflow(workflowId);
      WorkflowTaskRuntimeMBean result = this.convertProgressObject(prg);
      return result;
   }

   public synchronized WorkflowTaskRuntimeMBean[] getAllWorkflows() throws ManagementException {
      List prgs = this.service.getAllWorkflows();
      List result = new ArrayList(prgs.size());

      Object prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean[])result.toArray(new WorkflowTaskRuntimeMBean[result.size()]);
   }

   public synchronized WorkflowTaskRuntimeMBean lookupAllWorkflow(String workflowId) throws ManagementException {
      WorkflowProgress prg = this.service.lookupAllWorkflow(workflowId);
      WorkflowTaskRuntimeMBean result = this.convertProgressObject(prg);
      return result;
   }

   public synchronized WorkflowTaskRuntimeMBean[] getStoppedWorkflows() throws ManagementException {
      List prgs = this.service.getStoppedWorkflows();
      List result = new ArrayList(prgs.size());

      Object prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean[])result.toArray(new WorkflowProgressMBeanDelegate[result.size()]);
   }

   public synchronized WorkflowTaskRuntimeMBean lookupStoppedWorkflow(String workflowId) throws ManagementException {
      WorkflowProgress prg = this.service.lookupStoppedWorkflow(workflowId);
      WorkflowTaskRuntimeMBean result = this.convertProgressObject(prg);
      return result;
   }

   public WorkflowTaskRuntimeMBean convertProgressObject(WorkflowProgress progress) throws ManagementException {
      WorkflowTaskRuntimeMBean prgBean = null;
      if (progress != null) {
         prgBean = (WorkflowTaskRuntimeMBean)this.taskMap.get(progress.getWorkflowId());
         if (prgBean == null) {
            progress.registerListener(this, false);
            if (progress instanceof RolloutProgress) {
               prgBean = new RolloutProgressMBeanDelegate((RolloutProgress)progress, this);
            } else {
               prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            }

            this.taskMap.put(progress.getWorkflowId(), prgBean);
         }
      }

      return (WorkflowTaskRuntimeMBean)prgBean;
   }

   public synchronized void deleteWorkflow(String workflowId) throws IOException {
      this.service.deleteWorkflow(workflowId);
   }

   public void workflowStateChanged(WorkflowProgress.State originalState, WorkflowProgress.State newState, String workflowId, String workUnitId) {
      if (newState == State.DELETED) {
         WorkflowTaskRuntimeMBean toRemove = (WorkflowTaskRuntimeMBean)this.taskMap.remove(workflowId);
         if (toRemove != null) {
            try {
               ((WorkflowProgressMBeanDelegate)toRemove).unregister();
            } catch (ManagementException var7) {
               PatchingLogger.logCanNotUregisterMBean(var7);
            }
         }
      }

   }
}
