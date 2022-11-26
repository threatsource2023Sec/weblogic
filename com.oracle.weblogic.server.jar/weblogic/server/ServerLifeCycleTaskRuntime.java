package weblogic.server;

import java.io.IOException;
import java.io.PrintWriter;
import weblogic.management.ManagementException;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerTask;

public class ServerLifeCycleTaskRuntime extends DomainRuntimeMBeanDelegate implements ServerLifeCycleTaskRuntimeMBean {
   private long beginTime = System.currentTimeMillis();
   private long endTime;
   private String description = null;
   private boolean isRunning = true;
   private String status = "TASK IN PROGRESS";
   private Exception exception;
   private static int num = 0;
   private boolean sysTask = false;
   private NodeManagerTask nmTask = null;
   private String serverName = null;
   private String operation = null;

   public ServerLifeCycleTaskRuntime(ServerLifeCycleRuntime serverLifeCycle, String tName, String operationName) throws ManagementException {
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

   public void cancel() throws Exception {
      if (this.nmTask == null) {
         throw new Exception("Operation can not be cancelled ...");
      } else {
         this.nmTask.cancel();
      }
   }

   public long getBeginTime() {
      return this.nmTask != null ? this.nmTask.getBeginTime() : this.beginTime;
   }

   public void setEndTime(long time) {
      this.endTime = time;
   }

   public long getEndTime() {
      if (this.nmTask != null) {
         return this.nmTask.getEndTime();
      } else {
         return this.isRunning ? -1L : this.endTime;
      }
   }

   public void unregister() throws ManagementException {
      super.unregister();
      if (this.nmTask != null) {
         try {
            this.nmTask.cleanup();
         } catch (IOException var2) {
            throw new ManagementException(var2);
         }
      }

   }

   public void setError(Exception e) {
      this.exception = e;
   }

   public Exception getError() {
      return this.nmTask != null ? this.nmTask.getError() : this.exception;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getStatus() {
      return this.nmTask != null ? this.nmTask.getStatus() : this.status;
   }

   public void setisRunning(boolean running) {
      this.isRunning = running;
   }

   public boolean isRunning() {
      return this.nmTask != null ? !this.nmTask.isFinished() : this.isRunning;
   }

   public String getDescription() {
      return this.description;
   }

   public NodeManagerTask getNMTask() {
      return this.nmTask;
   }

   public void setNMTask(NodeManagerTask t) {
      this.nmTask = t;
   }

   public void setIsRunning(boolean b) {
      this.isRunning = b;
   }

   public boolean isSystemTask() {
      return this.sysTask;
   }

   public void setSystemTask(boolean sys) {
      this.sysTask = sys;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return null;
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void printLog(PrintWriter out) {
      out.println("This feature is to be replaced as per CR184195");
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else {
         return "TASK COMPLETED".equals(this.getStatus()) ? "success" : "failed";
      }
   }
}
