package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.DeploymentEventInfo;

public class DeploymentEventInfoImpl implements DeploymentEventInfo {
   private String taskId = null;
   private String appName = null;
   private long requestId = -1L;
   private boolean hasRequestId = false;

   public DeploymentEventInfoImpl(String taskId, long requestId, String appName) {
      this.taskId = taskId;
      this.requestId = requestId;
      this.appName = appName;
      this.hasRequestId = true;
   }

   public DeploymentEventInfoImpl(String taskId) {
      this.taskId = taskId;
   }

   public DeploymentEventInfoImpl(long requestId) {
      this.requestId = requestId;
      this.hasRequestId = true;
   }

   public String getAppName() {
      return this.appName;
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public String getTaskId() {
      return this.taskId;
   }

   public void setTaskId(String taskId) {
      this.taskId = taskId;
   }

   public long getRequestId() {
      return this.requestId;
   }

   public void setRequestId(long requestId) {
      this.requestId = requestId;
   }

   public boolean hasRequestId() {
      return this.hasRequestId;
   }

   public boolean isPopulated() {
      return this.appName != null && this.hasRequestId && this.taskId != null;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("requestId=");
      sb.append(this.requestId);
      sb.append("taskId=");
      sb.append(this.taskId);
      if (this.appName != null) {
         sb.append("appName=");
         sb.append(this.appName);
      }

      return sb.toString();
   }
}
