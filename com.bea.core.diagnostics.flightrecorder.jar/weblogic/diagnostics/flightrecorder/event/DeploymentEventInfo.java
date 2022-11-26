package weblogic.diagnostics.flightrecorder.event;

public interface DeploymentEventInfo {
   boolean isPopulated();

   boolean hasRequestId();

   String getAppName();

   void setAppName(String var1);

   long getRequestId();

   void setRequestId(long var1);

   String getTaskId();

   void setTaskId(String var1);
}
