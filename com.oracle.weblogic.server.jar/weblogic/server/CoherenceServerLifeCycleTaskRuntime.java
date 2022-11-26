package weblogic.server;

import java.io.PrintWriter;
import weblogic.management.ManagementException;
import weblogic.management.runtime.CoherenceServerLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerTask;

public class CoherenceServerLifeCycleTaskRuntime extends DomainRuntimeMBeanDelegate implements CoherenceServerLifeCycleTaskRuntimeMBean {
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

   public CoherenceServerLifeCycleTaskRuntime(CoherenceServerLifeCycleRuntime serverLifeCycle, String tName, String operationName) throws ManagementException {
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
      return this.isRunning() ? "TASK IN PROGRESS" : (this.getError() != null ? "FAILED" : "TASK COMPLETED");
   }

   public boolean isRunning() {
      return this.nmTask != null ? !this.nmTask.isFinished() : this.isRunning;
   }

   public long getBeginTime() {
      return this.beginTime;
   }

   public long getEndTime() {
      if (this.nmTask != null) {
         return this.nmTask.getEndTime();
      } else {
         return this.isRunning ? -1L : this.endTime;
      }
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return new TaskRuntimeMBean[0];
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
