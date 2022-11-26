package weblogic.diagnostics.descriptor;

public interface WLDFLogActionBean extends WLDFNotificationBean {
   String ALERT_TEXT = "Alert";
   String CRITICAL_TEXT = "Critical";
   String EMERGENCY_TEXT = "Emergency";
   String ERROR_TEXT = "Error";
   String WARNING_TEXT = "Warning";
   String INFO_TEXT = "Info";
   String NOTICE_TEXT = "Notice";
   String DEFAULT_SEVERITY_TEXT = "Notice";
   String[] ALLOWED_LOG_SEVERITIES = new String[]{"Alert", "Critical", "Emergency", "Error", "Info", "Notice", "Warning"};

   String getSeverity();

   void setSeverity(String var1);

   String getMessage();

   void setMessage(String var1);

   String getSubsystemName();

   void setSubsystemName(String var1);
}
