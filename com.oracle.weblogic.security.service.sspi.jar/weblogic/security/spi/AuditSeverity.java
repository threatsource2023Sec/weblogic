package weblogic.security.spi;

public class AuditSeverity {
   public static final AuditSeverity INFORMATION = new AuditSeverity(1, "INFORMATION");
   public static final AuditSeverity WARNING = new AuditSeverity(2, "WARNING");
   public static final AuditSeverity ERROR = new AuditSeverity(3, "ERROR");
   public static final AuditSeverity SUCCESS = new AuditSeverity(4, "SUCCESS");
   public static final AuditSeverity FAILURE = new AuditSeverity(5, "FAILURE");
   public static final AuditSeverity AUDIT_FAILURE = new AuditSeverity(6, "AUDIT_FAILURE");
   private int severityLevel;
   private String severityString;

   private AuditSeverity(int severity, String sevString) {
      this.severityLevel = severity;
      this.severityString = sevString;
   }

   public static AuditSeverity getInstance(String severityString) {
      if (INFORMATION.severityString.equalsIgnoreCase(severityString)) {
         return INFORMATION;
      } else if (WARNING.severityString.equalsIgnoreCase(severityString)) {
         return WARNING;
      } else if (ERROR.severityString.equalsIgnoreCase(severityString)) {
         return ERROR;
      } else if (SUCCESS.severityString.equalsIgnoreCase(severityString)) {
         return SUCCESS;
      } else if (FAILURE.severityString.equalsIgnoreCase(severityString)) {
         return FAILURE;
      } else {
         return AUDIT_FAILURE.severityString.equalsIgnoreCase(severityString) ? AUDIT_FAILURE : null;
      }
   }

   public int getSeverity() {
      return this.severityLevel;
   }

   public String getSeverityString() {
      return this.severityString;
   }
}
