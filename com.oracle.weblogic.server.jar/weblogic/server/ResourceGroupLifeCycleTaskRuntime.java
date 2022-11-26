package weblogic.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.management.ManagementException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.runtime.ResourceGroupLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;
import weblogic.t3.srvr.PartitionLifecycleLogger;

public class ResourceGroupLifeCycleTaskRuntime extends TaskRuntimeMBeanImpl implements ResourceGroupLifeCycleTaskRuntimeMBean {
   private static final AtomicInteger seqNum = new AtomicInteger(0);
   public static final String STATUS_PROPERTY_NAME = "Status";
   private final ResourceGroupLifecycleOperations.RGOperation operation;
   private final String resourceGroupName;
   private final String serverName;

   public String getServerName() {
      return this.serverName;
   }

   public ResourceGroupLifeCycleTaskRuntime(ResourceGroupLifeCycleRuntime resourceGroupLifeCycle, String description, ResourceGroupLifecycleOperations.RGOperation operation, String serverName, PropertyChangeListener changeListener) throws ManagementException {
      super("_" + getSequenceNumber() + "_" + operation.toString(), resourceGroupLifeCycle, true);
      this.addPropertyChangeListener(changeListener);
      this.resourceGroupName = resourceGroupLifeCycle.getName();
      this.serverName = serverName;
      this.operation = operation;
      this.description = description;
      this.beginTime = System.currentTimeMillis();
      this.status = ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString();
      this.running = true;
   }

   public ResourceGroupLifeCycleTaskRuntime(ResourceGroupLifeCycleRuntime resourceGroupLifeCycle, String description, ResourceGroupLifecycleOperations.RGOperation operation, String serverName) throws ManagementException {
      super("_" + getSequenceNumber() + "_" + operation.toString(), resourceGroupLifeCycle, true);
      this.resourceGroupName = resourceGroupLifeCycle.getName();
      this.serverName = serverName;
      this.operation = operation;
      this.description = description;
      this.beginTime = System.currentTimeMillis();
      this.status = ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString();
      this.running = true;
   }

   public String getOperation() {
      return this.operation.toString();
   }

   public ResourceGroupLifecycleOperations.RGOperation getOp() {
      return this.operation;
   }

   public String getResourceGroupName() {
      return this.resourceGroupName;
   }

   public void cancel() throws Exception {
      throw new Exception("Operation cannot be canceled");
   }

   public void printLog(PrintWriter out) {
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return this.status.equals(ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString()) ? "success" : "failed";
      }
   }

   public void registerSubTasks(TaskRuntimeMBean[] subtasksToRun) {
      PropertyChangeListener listener = new ChildStateChangeListener();
      TaskRuntimeMBean[] var3 = subtasksToRun;
      int var4 = subtasksToRun.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TaskRuntimeMBean subtask = var3[var5];
         if (subtask != null) {
            subtask.addPropertyChangeListener(listener);
         }
      }

      this.setSubTasks(subtasksToRun);
   }

   public void setStatus(String newStatus) {
      synchronized(this) {
         String oldStatus = this.status;
         this.status = newStatus;
         this.running = newStatus.equals(ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString());
         this._postSet("Status", oldStatus, newStatus);
      }
   }

   private static int getSequenceNumber() {
      return seqNum.incrementAndGet();
   }

   private void setErrorForParentTask() {
      StringBuilder subTaskStr = new StringBuilder();
      if (this.getSubTasks() != null && this.getSubTasks().length > 0) {
         TaskRuntimeMBean[] var2 = this.getSubTasks();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TaskRuntimeMBean st = var2[var4];
            if (st instanceof ResourceGroupLifeCycleTaskRuntimeMBean) {
               ResourceGroupLifeCycleTaskRuntimeMBean subtask = (ResourceGroupLifeCycleTaskRuntimeMBean)st;
               if (subtask.getStatus().equals(ResourceGroupLifeCycleTaskRuntime.Status.FAILED.toString())) {
                  subTaskStr.append(subtask.getName()).append(",");
                  PartitionLifecycleLogger.failedRGSubTask(st.getName(), ((ResourceGroupLifeCycleTaskRuntimeMBean)st).getResourceGroupName(), ((ResourceGroupLifeCycleTaskRuntimeMBean)st).getServerName(), st.getError().getMessage());
               } else {
                  PartitionLifecycleLogger.successfulRGSubTask(st.getName(), ((ResourceGroupLifeCycleTaskRuntimeMBean)st).getResourceGroupName(), st.getStatus(), ((ResourceGroupLifeCycleTaskRuntimeMBean)st).getServerName());
               }
            }
         }
      }

      if (subTaskStr.length() > 0) {
         this.setError(new ResourceGroupLifecycleException("Resource group lifecycle operation failed for sub tasks :" + subTaskStr.substring(0, subTaskStr.length() - 1)));
         PartitionLifecycleLogger.failedRGTask(this.getName(), this.getResourceGroupName(), this.getError().getMessage());
      } else {
         PartitionLifecycleLogger.successfulRGTask(this.getName(), this.getResourceGroupName(), this.getStatus(), this.getServerName());
      }

   }

   private boolean isWorse(String status1, String status2) {
      if (status1.equals(status2)) {
         return false;
      } else if (status1.equals(ResourceGroupLifeCycleTaskRuntime.Status.FAILED.toString())) {
         return true;
      } else {
         return status1.equals(ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString()) && status2.equals(ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
      }
   }

   private boolean isWorseOldLifecycle(String status1, String status2) {
      if (status1.equals(status2)) {
         return false;
      } else if (status1.equals(ResourceGroupLifeCycleTaskRuntime.Status.FAILED.name())) {
         return true;
      } else {
         return status1.equals(ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.name()) && status2.equals(ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.name());
      }
   }

   public void setError(Exception e) {
      this.error = e;
   }

   public void setEndTime(long time) {
      this.endTime = time;
   }

   public void setBeginTime(long time) {
      this.beginTime = time;
   }

   public void setIsRunning(boolean b) {
      this.running = b;
   }

   static ResourceGroupLifeCycleTaskRuntime preCompleted(ResourceGroupLifeCycleRuntime rgLifeCycle, String description, ResourceGroupLifecycleOperations.RGOperation operation) throws ManagementException {
      ResourceGroupLifeCycleTaskRuntime result = new ResourceGroupLifeCycleTaskRuntime(rgLifeCycle, description, operation, "");
      result.setStatus(ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
      result.setEndTime(System.currentTimeMillis());
      return result;
   }

   private class ChildStateChangeListener implements PropertyChangeListener {
      private ChildStateChangeListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if ("Status".equals(evt.getPropertyName()) && evt.getSource() instanceof ResourceGroupLifeCycleTaskRuntimeMBean) {
            synchronized(this) {
               String originalStatus = ResourceGroupLifeCycleTaskRuntime.this.status;
               String worstStateFromSubtasks = ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString();
               if (ResourceGroupLifeCycleTaskRuntime.this.getSubTasks() != null && ResourceGroupLifeCycleTaskRuntime.this.getSubTasks().length > 0) {
                  TaskRuntimeMBean[] var5 = ResourceGroupLifeCycleTaskRuntime.this.getSubTasks();
                  int var6 = var5.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     TaskRuntimeMBean st = var5[var7];
                     if (st instanceof ResourceGroupLifeCycleTaskRuntimeMBean) {
                        ResourceGroupLifeCycleTaskRuntimeMBean subtask = (ResourceGroupLifeCycleTaskRuntimeMBean)st;
                        if (subtask.getStatus().equals(ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString())) {
                           ResourceGroupLifeCycleTaskRuntime.this.setStatus(ResourceGroupLifeCycleTaskRuntime.Status.IN_PROGRESS.toString());
                           return;
                        }

                        if (ResourceGroupLifeCycleTaskRuntime.this.isWorse(subtask.getStatus(), worstStateFromSubtasks.toString())) {
                           worstStateFromSubtasks = subtask.getStatus();
                        }
                     }
                  }
               }

               String newStatus = worstStateFromSubtasks.equals(ResourceGroupLifeCycleTaskRuntime.Status.FAILED.toString()) ? ResourceGroupLifeCycleTaskRuntime.Status.FAILED.toString() : ResourceGroupLifeCycleTaskRuntime.Status.SUCCEEDED.toString();
               if (!originalStatus.equals(newStatus)) {
                  ResourceGroupLifeCycleTaskRuntime.this.setStatus(newStatus);
                  ResourceGroupLifeCycleTaskRuntime.this.setEndTime(System.currentTimeMillis());
                  ResourceGroupLifeCycleTaskRuntime.this.setIsRunning(false);
                  ResourceGroupLifeCycleTaskRuntime.this.setErrorForParentTask();
               }

            }
         }
      }

      // $FF: synthetic method
      ChildStateChangeListener(Object x1) {
         this();
      }
   }

   public static enum Status {
      FAILED(true, "FAILED"),
      IN_PROGRESS(false, "TASK IN PROGRESS"),
      SUCCEEDED(true, "TASK COMPLETED");

      private final boolean isFinal;
      private final String userFriendlyStatus;

      private Status(boolean isFinal, String userFriendlyStatus) {
         this.isFinal = isFinal;
         this.userFriendlyStatus = userFriendlyStatus;
      }

      public boolean isWorse(Status other) {
         return this.compareTo(other) < 0;
      }

      public boolean isFinal() {
         return this.isFinal;
      }

      public String toString() {
         return this.userFriendlyStatus;
      }
   }
}
