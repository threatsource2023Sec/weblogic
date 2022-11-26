package com.bea.wls.redef.runtime;

import com.bea.wls.redef.RedefinitionTask;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;

public class ClassRedefinitionTaskRuntimeImpl extends TaskRuntimeMBeanImpl implements ClassRedefinitionTaskRuntimeMBean {
   private RedefinitionTask _task;

   public ClassRedefinitionTaskRuntimeImpl(String name, RuntimeMBean parent, RedefinitionTask task) throws ManagementException {
      super(name, parent, true);
      this.setDescription("Class redefinition task");
      this._task = task;
   }

   public int getCandidateClassesCount() {
      return this._task.getCandidateClassesCount();
   }

   public int getProcessedClassesCount() {
      return this._task.getProcessedClassesCount();
   }

   public String getStatus() {
      return this._task.getStatus().name();
   }

   public boolean isDeletable() {
      return this._task.isDeletable();
   }

   public boolean isRunning() {
      return this._task.getStatus() == RedefinitionTask.Status.RUNNING;
   }

   public void cancel() throws Exception {
      RedefinitionTask.Status status = this._task.getStatus();
      switch (status) {
         case SCHEDULED:
         case RUNNING:
            this._task.setStatus(RedefinitionTask.Status.CANCELLED);
            return;
         default:
            throw new ManagementException("Class redefinition task " + this.getName() + " is neither scheduled nor running.");
      }
   }

   public long getBeginTime() {
      return this._task.getBeginTime();
   }

   public long getEndTime() {
      return this._task.getEndTime();
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else if (this._task.getStatus() == RedefinitionTask.Status.SCHEDULED) {
         return "pending";
      } else {
         return this._task.getStatus() == RedefinitionTask.Status.FINISHED ? "success" : "failed";
      }
   }
}
