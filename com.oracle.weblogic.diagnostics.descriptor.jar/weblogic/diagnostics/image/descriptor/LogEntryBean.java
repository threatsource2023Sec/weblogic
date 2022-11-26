package weblogic.diagnostics.image.descriptor;

public interface LogEntryBean {
   String getFormattedDate();

   void setFormattedDate(String var1);

   String getMessageId();

   void setMessageId(String var1);

   String getMachineName();

   void setMachineName(String var1);

   String getServerName();

   void setServerName(String var1);

   String getThreadName();

   void setThreadName(String var1);

   String getUserId();

   void setUserId(String var1);

   String getTransactionId();

   void setTransactionId(String var1);

   int getSeverity();

   void setSeverity(int var1);

   String getSubsystem();

   void setSubsystem(String var1);

   long getTimestamp();

   void setTimestamp(long var1);

   String getLogMessage();

   void setLogMessage(String var1);

   String getStackTrace();

   void setStackTrace(String var1);

   String getDiagnosticContextId();

   void setDiagnosticContextId(String var1);
}
