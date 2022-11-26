package weblogic.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.runtime.PartitionLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;
import weblogic.t3.srvr.PartitionLifecycleLogger;

public class PartitionLifeCycleTaskRuntime extends TaskRuntimeMBeanImpl implements PartitionLifeCycleTaskRuntimeMBean {
   private static final boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();
   public static final String STATUS_PROPERTY_NAME = "Status";
   private static final AtomicInteger seqNum = new AtomicInteger(0);
   private PartitionRuntimeMBean.Operation operation;
   private final String partitionName;
   private final String serverName;
   private Exception exception;

   public PartitionLifeCycleTaskRuntime(PartitionLifeCycleRuntime partitionLifeCycle, String description, PartitionRuntimeMBean.Operation operation, String serverName) throws ManagementException {
      super("_" + getSequenceNumber() + "_" + operation.toString(), partitionLifeCycle, true);
      this.partitionName = partitionLifeCycle.getName();
      this.operation = operation;
      this.description = description;
      this.beginTime = System.currentTimeMillis();
      this.status = PartitionLifeCycleTaskRuntime.Status.IN_PROGRESS.toString();
      this.running = true;
      this.serverName = serverName;
   }

   public String getOperation() {
      return this.operation.toString();
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void cancel() throws Exception {
      throw new Exception("Operation cannot be canceled");
   }

   public void printLog(PrintWriter out) {
   }

   public void registerSubTasks(TaskRuntimeMBean[] newSubTasks) {
      PropertyChangeListener listener = new ChildStatusChangeListener();
      TaskRuntimeMBean[] var3 = newSubTasks;
      int var4 = newSubTasks.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TaskRuntimeMBean subtask = var3[var5];
         subtask.addPropertyChangeListener(listener);
      }

      this.setSubTasks(newSubTasks);
   }

   public void unregister() throws ManagementException {
      if (this.subTasks != null && this.subTasks.length > 0) {
         TaskRuntimeMBean[] var1 = this.subTasks;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            TaskRuntimeMBean subtask = var1[var3];

            try {
               if (DEBUG) {
                  debug("Unregistering subtask: " + subtask.getName() + " of parent task: " + this.getName());
               }

               ((TaskRuntimeMBeanImpl)subtask).unregister();
            } catch (ManagementException var6) {
               if (DEBUG) {
                  debug("An exception occured while unregistering subtask: " + var6.getMessage());
               }

               var6.printStackTrace();
            }
         }
      }

      super.unregister();
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
            if (st instanceof PartitionLifeCycleTaskRuntimeMBean) {
               PartitionLifeCycleTaskRuntimeMBean subtask = (PartitionLifeCycleTaskRuntimeMBean)st;
               if (subtask.getStatus().equals(PartitionLifeCycleTaskRuntime.Status.FAILED.toString())) {
                  subTaskStr.append(subtask.getName()).append(",");
                  PartitionLifecycleLogger.failedSubTask(st.getName(), ((PartitionLifeCycleTaskRuntimeMBean)st).getPartitionName(), ((PartitionLifeCycleTaskRuntimeMBean)st).getServerName(), st.getError().getMessage());
               } else {
                  PartitionLifecycleLogger.successfulSubTask(st.getName(), ((PartitionLifeCycleTaskRuntimeMBean)st).getPartitionName(), st.getStatus(), ((PartitionLifeCycleTaskRuntimeMBean)st).getServerName());
               }
            }
         }
      }

      if (subTaskStr.length() > 0) {
         this.setError(new PartitionLifeCycleException("Partition lifecycle operation failed for sub tasks :" + subTaskStr.substring(0, subTaskStr.length() - 1)));
         PartitionLifecycleLogger.failedTask(this.getName(), this.getPartitionName(), this.getError().getMessage());
      } else {
         PartitionLifecycleLogger.successfulTask(this.getName(), this.getPartitionName(), this.getStatus(), this.getServerName());
      }

   }

   private boolean isWorse(String status1, String status2) {
      if (status1.equals(status2)) {
         return false;
      } else if (status1.equals(PartitionLifeCycleTaskRuntime.Status.FAILED.toString())) {
         return true;
      } else {
         return status1.equals(PartitionLifeCycleTaskRuntime.Status.IN_PROGRESS.toString()) && status2.equals(PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
      }
   }

   public void setError(Exception e) {
      this.exception = e;
   }

   public Exception getError() {
      return this.exception;
   }

   public void setStatus(String newStatus) {
      synchronized(this) {
         String oldStatus = this.status;
         this.status = newStatus;
         this.running = newStatus.equals(PartitionLifeCycleTaskRuntime.Status.IN_PROGRESS.toString());
         this._postSet("Status", oldStatus, newStatus);
         if (DEBUG) {
            debug("postSet fired for task " + this.getName() + " with old status " + oldStatus + " with new status " + newStatus);
         }

      }
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

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString().equals(this.getStatus()) ? "success" : "failed";
      }
   }

   static PartitionLifeCycleTaskRuntime preCompleted(PartitionLifeCycleRuntime partitionLifeCycle, String description, PartitionRuntimeMBean.Operation operation) throws ManagementException {
      PartitionLifeCycleTaskRuntime result = new PartitionLifeCycleTaskRuntime(partitionLifeCycle, description, operation, "");
      result.setStatus(PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString());
      result.setEndTime(System.currentTimeMillis());
      return result;
   }

   private static void debug(String debugMessage) {
      PartitionLifecycleDebugger.debug("<PartitionLifecycleTaskRuntime> " + debugMessage);
   }

   private class ChildStatusChangeListener implements PropertyChangeListener {
      private ChildStatusChangeListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if ("Status".equals(evt.getPropertyName()) && evt.getSource() instanceof PartitionLifeCycleTaskRuntimeMBean) {
            synchronized(this) {
               if (PartitionLifeCycleTaskRuntime.DEBUG) {
                  PartitionLifeCycleTaskRuntime.debug("Event is : " + ((PartitionLifeCycleTaskRuntimeMBean)evt.getSource()).getName());
               }

               String originalStatus = PartitionLifeCycleTaskRuntime.this.status;
               if (PartitionLifeCycleTaskRuntime.DEBUG) {
                  PartitionLifeCycleTaskRuntime.debug("The original status of parent task : " + originalStatus);
               }

               String worstStatusFromSubtasks = PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString();
               if (PartitionLifeCycleTaskRuntime.this.getSubTasks() != null && PartitionLifeCycleTaskRuntime.this.getSubTasks().length > 0) {
                  TaskRuntimeMBean[] var5 = PartitionLifeCycleTaskRuntime.this.getSubTasks();
                  int var6 = var5.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     TaskRuntimeMBean st = var5[var7];
                     if (st instanceof PartitionLifeCycleTaskRuntimeMBean) {
                        PartitionLifeCycleTaskRuntimeMBean subtask = (PartitionLifeCycleTaskRuntimeMBean)st;
                        if (subtask.getStatus().equals(PartitionLifeCycleTaskRuntime.Status.IN_PROGRESS.toString())) {
                           PartitionLifeCycleTaskRuntime.this.setStatus(PartitionLifeCycleTaskRuntime.Status.IN_PROGRESS.toString());
                           if (PartitionLifeCycleTaskRuntime.DEBUG) {
                              PartitionLifeCycleTaskRuntime.debug("The status of parent task : " + PartitionLifeCycleTaskRuntime.this.getStatus());
                           }

                           return;
                        }

                        String status = subtask.getStatus();
                        if (PartitionLifeCycleTaskRuntime.DEBUG) {
                           PartitionLifeCycleTaskRuntime.debug("The subtask " + subtask + " status : " + status);
                        }

                        if (PartitionLifeCycleTaskRuntime.this.isWorse(status, worstStatusFromSubtasks)) {
                           worstStatusFromSubtasks = status;
                        }
                     }
                  }
               }

               String newStatus = worstStatusFromSubtasks.equals(PartitionLifeCycleTaskRuntime.Status.FAILED.toString()) ? PartitionLifeCycleTaskRuntime.Status.FAILED.toString() : PartitionLifeCycleTaskRuntime.Status.SUCCEEDED.toString();
               if (PartitionLifeCycleTaskRuntime.DEBUG) {
                  PartitionLifeCycleTaskRuntime.debug("newStatus : " + newStatus);
               }

               if (!originalStatus.equals(newStatus)) {
                  if (PartitionLifeCycleTaskRuntime.DEBUG) {
                     PartitionLifeCycleTaskRuntime.debug("Setting the final state of parent task : " + PartitionLifeCycleTaskRuntime.this.getStatus());
                  }

                  PartitionLifeCycleTaskRuntime.this.setStatus(newStatus);
                  PartitionLifeCycleTaskRuntime.this.setEndTime(System.currentTimeMillis());
                  PartitionLifeCycleTaskRuntime.this.setIsRunning(false);
                  PartitionLifeCycleTaskRuntime.this.setErrorForParentTask();
               }

            }
         }
      }

      // $FF: synthetic method
      ChildStatusChangeListener(Object x1) {
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
