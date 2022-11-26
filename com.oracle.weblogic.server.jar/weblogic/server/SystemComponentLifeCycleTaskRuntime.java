package weblogic.server;

import java.io.PrintWriter;
import weblogic.management.ManagementException;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.SystemComponentLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerTask;

public class SystemComponentLifeCycleTaskRuntime extends DomainRuntimeMBeanDelegate implements SystemComponentLifeCycleTaskRuntimeMBean {
   private long beginTime = System.currentTimeMillis();
   private long endTime;
   private static int num = 0;
   private String description = null;
   private boolean isRunning = true;
   private String status = "TASK IN PROGRESS";
   private Exception exception;
   private NodeManagerTask nmTask = null;
   private String serverName = null;
   private String operation = null;

   public SystemComponentLifeCycleTaskRuntime(SystemComponentLifeCycleRuntime serverLifeCycle, String tName, String operationName) throws ManagementException {
      super("_" + getSequenceNumber() + "_" + operationName, serverLifeCycle, true, "Tasks");
      this.description = tName;
      this.serverName = serverLifeCycle.getName();
      this.operation = operationName;
   }

   private static synchronized int getSequenceNumber() {
      return num++;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getOperation() {
      return this.operation;
   }

   public String getDescription() {
      return this.description;
   }

   public String getStatus() {
      return this.nmTask != null ? this.nmTask.getStatus() : this.status;
   }

   public boolean isRunning() {
      return this.nmTask != null ? !this.nmTask.isFinished() : this.isRunning;
   }

   public long getBeginTime() {
      return this.nmTask != null ? this.nmTask.getBeginTime() : this.beginTime;
   }

   public long getEndTime() {
      if (this.nmTask != null) {
         return this.nmTask.getEndTime();
      } else {
         return this.isRunning ? -1L : this.endTime;
      }
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return null;
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void cancel() throws Exception {
      throw new UnsupportedOperationException();
   }

   public void printLog(PrintWriter out) {
      throw new UnsupportedOperationException();
   }

   public Exception getError() {
      return this.nmTask != null ? this.nmTask.getError() : this.exception;
   }

   public boolean isSystemTask() {
      return false;
   }

   public void setSystemTask(boolean sys) {
      throw new UnsupportedOperationException();
   }

   public void setNMTask(NodeManagerTask nmTask) {
      this.nmTask = nmTask;
   }

   public void setError(Exception e) {
      this.exception = e;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public void setEndTime(long time) {
      this.endTime = time;
   }

   public void setIsRunning(boolean b) {
      this.isRunning = b;
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return "TASK COMPLETED".equals(this.getStatus()) ? "success" : "failed";
      }
   }
}
