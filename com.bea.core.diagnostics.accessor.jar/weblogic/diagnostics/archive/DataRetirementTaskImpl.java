package weblogic.diagnostics.archive;

import java.io.PrintWriter;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBeanImpl;

public class DataRetirementTaskImpl extends TaskRuntimeMBeanImpl implements DataRetirementTaskRuntimeMBean, Runnable {
   public static final int PENDING = 0;
   public static final int EXECUTING = 1;
   public static final int COMPLETED = 2;
   public static final int CANCELLED = 3;
   public static final int FAILED = 4;
   private static final DiagnosticsTextTextFormatter fm = DiagnosticsTextTextFormatter.getInstance();
   private static final String[] STATUS_NAMES = new String[]{"Pending", "Executing", "Completed", "Cancelled", "Failed"};
   private int taskStatus;
   private long startTime;
   private long endTime;
   private boolean systemTask;
   private Exception taskError;
   private DataRetirementTaskRuntimeMBean[] subtasks;
   private DataRetirementTaskRuntimeMBean parentTask;
   private long retiredRecordsCount;
   private static int count = 0;

   public DataRetirementTaskImpl(String name) throws ManagementException {
      this(name, (DataRetirementTaskRuntimeMBean[])null);
   }

   public DataRetirementTaskImpl(String name, DataRetirementTaskRuntimeMBean[] subtasks) throws ManagementException {
      super(name + "_" + getCount(), (RuntimeMBean)null, true);
      this.taskStatus = 0;
      this.subtasks = subtasks;
      int cnt = subtasks != null ? subtasks.length : 0;

      for(int i = 0; i < cnt; ++i) {
         ((DataRetirementTaskImpl)subtasks[i]).parentTask = this;
      }

   }

   protected synchronized void setError(Exception error) {
      this.taskError = error;
      this.taskStatus = 4;
   }

   protected synchronized void setBeginTime(long time) {
      this.startTime = time;
      if (this.taskStatus == 0) {
         this.taskStatus = 1;
      }

   }

   protected synchronized void setEndTime(long time) {
      this.endTime = time;
      if (this.taskStatus == 1) {
         this.taskStatus = 2;
      }

   }

   public String getDescription() {
      return fm.getDataRetirementTaskDescriptionText();
   }

   public synchronized String getStatus() {
      return STATUS_NAMES[this.taskStatus];
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else if (this.taskStatus == 0) {
         return "pending";
      } else {
         return this.taskStatus == 2 ? "success" : "failed";
      }
   }

   public synchronized boolean isRunning() {
      return this.taskStatus == 1;
   }

   public synchronized long getBeginTime() {
      return this.startTime;
   }

   public synchronized long getEndTime() {
      return this.endTime;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return this.subtasks;
   }

   public TaskRuntimeMBean getParentTask() {
      return this.parentTask;
   }

   public synchronized void cancel() throws Exception {
      this.taskStatus = 3;
      int cnt = this.subtasks != null ? this.subtasks.length : 0;
      Exception e = null;

      for(int i = 0; i < cnt; ++i) {
         try {
            this.subtasks[i].cancel();
         } catch (Exception var5) {
            e = var5;
         }
      }

      if (e != null) {
         throw e;
      }
   }

   public synchronized Exception getError() {
      return this.taskError;
   }

   public boolean isSystemTask() {
      return this.systemTask;
   }

   public void setSystemTask(boolean status) {
      this.systemTask = status;
   }

   public long getRetiredRecordsCount() {
      return this.retiredRecordsCount;
   }

   public void incrementRetiredRecordsCount(long count) {
      this.retiredRecordsCount += count;
   }

   public void printLog(PrintWriter writer) {
   }

   public void run() {
      this.setBeginTime(System.currentTimeMillis());
      int cnt = this.subtasks != null ? this.subtasks.length : 0;

      for(int i = 0; i < cnt; ++i) {
         try {
            Runnable task = (Runnable)this.subtasks[i];
            task.run();
         } catch (Exception var4) {
            this.setError(var4);
         }
      }

      this.setEndTime(System.currentTimeMillis());
   }

   private static synchronized int getCount() {
      return ++count;
   }
}
