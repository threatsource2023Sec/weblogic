package weblogic.management.workflow.mbean;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowLifecycleManager;
import weblogic.management.workflow.WorkflowProgress;
import weblogic.management.workflow.WorkflowStateChangeListener;
import weblogic.management.workflow.WorkflowProgress.State;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StringUtils;

public abstract class WorkflowLifecycleRuntimeMBeanDelegate extends RuntimeMBeanDelegate {
   private final WorkflowLifecycleManager manager = (WorkflowLifecycleManager)GlobalServiceLocator.getServiceLocator().getService(WorkflowLifecycleManager.class, new Annotation[0]);
   private final WorkflowProgressMBeanRegistry registry = new WorkflowProgressMBeanRegistry();

   public WorkflowLifecycleRuntimeMBeanDelegate(String serviceName) throws ManagementException {
      super(serviceName);
   }

   public WorkflowLifecycleRuntimeMBeanDelegate(String serviceName, RuntimeMBean parent) throws ManagementException {
      super(serviceName, parent);
   }

   protected WorkflowLifecycleManager getManager() {
      return this.manager;
   }

   protected String getServiceName() {
      return super.getName();
   }

   protected WorkflowProgressMBeanDelegate startWorkflow(WorkflowBuilder builder) throws ManagementException {
      WorkflowProgress progress = this.manager.startWorkflow(builder, super.getName());
      progress.registerListener(this.registry, false);
      WorkflowProgressMBeanDelegate result = new WorkflowProgressMBeanDelegate(progress, this);
      this.registry.register(result);
      return result;
   }

   public boolean canResume(WorkflowProgressMBeanDelegate delegate) {
      return delegate.canResume();
   }

   public WorkflowProgressMBeanDelegate executeWorkflow(WorkflowProgressMBeanDelegate delegate) {
      this.manager.executeWorkflow(delegate.getWorkflowProgress());
      return delegate;
   }

   public WorkflowProgressMBeanDelegate revertWorkflow(WorkflowProgressMBeanDelegate delegate) {
      this.manager.revertWorkflow(delegate.getWorkflowProgress());
      return delegate;
   }

   public synchronized WorkflowProgressMBeanDelegate lookupAllWorkflow(String id) throws ManagementException {
      WorkflowProgressMBeanDelegate result = this.registry.get(id);
      if (result == null) {
         WorkflowProgress progress = this.manager.getWorkflowProgress(id);
         if (progress != null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            result = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(result);
         }
      }

      return result;
   }

   public synchronized List getCompleteWorkflows() throws ManagementException {
      List prgs = this.manager.getCompleteWorkflows();
      List result = new ArrayList(prgs.size());

      WorkflowProgressMBeanDelegate prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = this.registry.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(prgBean);
         }
      }

      return result;
   }

   public synchronized WorkflowProgressMBeanDelegate[] getStoppedWorkflows() throws ManagementException {
      List prgs = this.manager.getStoppedWorkflows();
      List result = new ArrayList(prgs.size());

      WorkflowProgressMBeanDelegate prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = this.registry.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(prgBean);
         }
      }

      return (WorkflowProgressMBeanDelegate[])result.toArray(new WorkflowProgressMBeanDelegate[result.size()]);
   }

   public synchronized List getActiveWorkflows() throws ManagementException {
      List prgs = this.manager.getActiveWorkflows();
      List result = new ArrayList(prgs.size());

      WorkflowProgressMBeanDelegate prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = this.registry.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(prgBean);
         }
      }

      return result;
   }

   public synchronized List getInactiveWorkflows() throws ManagementException {
      List prgs = this.manager.getInactiveWorkflows();
      List result = new ArrayList(prgs.size());

      WorkflowProgressMBeanDelegate prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = this.registry.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(prgBean);
         }
      }

      return result;
   }

   public synchronized List getAllWorkflows() throws ManagementException {
      List prgs = this.manager.getAllWorkflows();
      List result = new ArrayList(prgs.size());

      WorkflowProgressMBeanDelegate prgBean;
      for(Iterator var3 = prgs.iterator(); var3.hasNext(); result.add(prgBean)) {
         WorkflowProgress progress = (WorkflowProgress)var3.next();
         prgBean = this.registry.get(progress.getWorkflowId());
         if (prgBean == null && StringUtils.strcmp(this.getName(), progress.getServiceName())) {
            progress.registerListener(this.registry, false);
            prgBean = new WorkflowProgressMBeanDelegate(progress, this);
            this.registry.register(prgBean);
         }
      }

      return result;
   }

   static class WorkflowProgressMBeanRegistry implements WorkflowStateChangeListener {
      private final Map taskMap = new HashMap();

      synchronized void register(WorkflowProgressMBeanDelegate mbean) {
         this.taskMap.put(mbean.getWorkflowId(), mbean);
      }

      synchronized WorkflowProgressMBeanDelegate get(String workflowId) {
         return (WorkflowProgressMBeanDelegate)this.taskMap.get(workflowId);
      }

      public void workflowStateChanged(WorkflowProgress.State originalState, WorkflowProgress.State newState, String workflowId, String workUnitId) {
         if (newState == State.DELETED) {
            synchronized(this) {
               this.taskMap.remove(workflowId);
            }
         }

      }
   }
}
