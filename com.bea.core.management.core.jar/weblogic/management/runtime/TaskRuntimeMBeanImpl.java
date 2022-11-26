package weblogic.management.runtime;

import java.io.PrintWriter;
import weblogic.management.ManagementException;

public abstract class TaskRuntimeMBeanImpl extends RuntimeMBeanDelegate implements TaskRuntimeMBean {
   protected String description;
   protected String status;
   protected long beginTime;
   protected long endTime;
   protected TaskRuntimeMBean[] subTasks;
   protected Exception error;
   protected boolean running;
   protected boolean systemTask;

   public TaskRuntimeMBeanImpl(String nameArg, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      super(nameArg, parentArg, registerNow);
   }

   public String getDescription() {
      return this.description;
   }

   public String getStatus() {
      return this.status;
   }

   public boolean isRunning() {
      return this.running;
   }

   public long getBeginTime() {
      return this.beginTime;
   }

   public long getEndTime() {
      return this.endTime;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return this.subTasks;
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void cancel() throws Exception {
   }

   public void printLog(PrintWriter out) {
   }

   public Exception getError() {
      return this.error;
   }

   public boolean isSystemTask() {
      return this.systemTask;
   }

   public void setSystemTask(boolean sys) {
      this.systemTask = sys;
   }

   protected void setDescription(String description) {
      this.description = description;
   }

   protected void setStatus(String status) {
      this.status = status;
   }

   protected void setBeginTime(long beginTime) {
      this.beginTime = beginTime;
   }

   protected void setEndTime(long endTime) {
      this.endTime = endTime;
   }

   protected void setSubTasks(TaskRuntimeMBean[] subTasks) {
      this.subTasks = subTasks;
   }

   protected void setError(Exception error) {
      this.error = error;
   }
}
