package weblogic.diagnostics.descriptor;

public interface WLDFThreadDumpActionBean extends WLDFNotificationBean {
   int getThreadDumpCount();

   void setThreadDumpCount(int var1);

   int getThreadDumpDelaySeconds();

   void setThreadDumpDelaySeconds(int var1);
}
