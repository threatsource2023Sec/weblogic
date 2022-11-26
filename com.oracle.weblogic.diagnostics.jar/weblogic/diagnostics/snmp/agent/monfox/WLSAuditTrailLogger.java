package weblogic.diagnostics.snmp.agent.monfox;

import java.security.AccessController;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.ext.audit.SnmpAuditTrailLogger;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.v3.usm.USMUser;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.Auditor;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.AuditSeverity;

public final class WLSAuditTrailLogger implements SnmpAuditTrailLogger {
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final String SNMP_ERROR_REPORT = "SNMP_ERROR_REPORT";
   private static final String SNMP_ERROR_RESPONSE = "SNMP_ERROR_RESPONSE";
   private static final String SNMP_OBJECT_ACCESS = "SNMP_OBJECT_ACCESS";
   private static final String SNMP_AUTH_OPERATION = "SNMP_AUTH_OPERATION";
   private static final String SNMP_PRIV_OPERATION = "SNMP_PRIV_OPERATION";
   private static final String YES = "Yes";
   private static final String NO = "No";
   private static final String EVENT_TYPE_ATTR = "EVENT_TYPE";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String SEVERITY_ATTR = "Severity";
   private static final String VARBIND_ATTR = "Variables";
   private static final String VERSION_ATTR = "SnmpV";
   private static final String CRYPT_ATTR = "Crypt-Op";
   private static final String ENCRYPT_OP = "Encrypt";
   private static final String DECRYPT_OP = "Decrypt";
   private static final String PROTOCOL_ATTR = "Protocol";
   private static final String INCOMING_ATTR = "Incoming";
   private static final String MSGTYPE_ATTR = "MsgType";
   private static final String OBJNAME_ATTR = "Object";
   private static final String INSTANCE_ATTR = "Instance";
   private static final String OBJVAL_ATTR = "Value";
   private static final String ERRORSTATUS_ATTR = "Error-Status";
   private static final String ERRORINDEX_ATTR = "Error-Index";
   private int authFailureCount;
   private int privFailureCount;
   private Auditor auditor = null;
   private static WLSAuditTrailLogger singleton;

   private WLSAuditTrailLogger() {
      String realmName = "weblogicDEFAULT";
      this.auditor = (Auditor)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUDIT);
   }

   public static synchronized WLSAuditTrailLogger getInstance() {
      if (singleton == null) {
         singleton = new WLSAuditTrailLogger();
      }

      return singleton;
   }

   private void logEvent(SnmpBaseAuditEvent event) {
      if (this.auditor != null) {
         this.auditor.writeEvent(event);
      }

   }

   private String algToString(int alg) {
      switch (alg) {
         case 0:
            return "MD5";
         case 1:
            return "SHA";
         case 2:
            return "DES";
         case 3:
         default:
            return "Unknown:" + alg;
         case 4:
            return "AES128";
         case 5:
            return "AES192";
         case 6:
            return "AES256";
      }
   }

   public void logPrivOperation(String sec_name, int snmp_version, int crypt_type, int priv_protocol, int result) {
      if (result != 1) {
         ++this.privFailureCount;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("privFailureCount: " + this.privFailureCount + ", [" + sec_name + ", " + Snmp.versionToString(snmp_version) + ", " + crypt_type + ", " + USMUser.PrivProtocolToString(priv_protocol) + "]");
         }
      }

      this.logEvent(new SnmpPrivOperationEvent(sec_name, snmp_version, crypt_type, priv_protocol, result));
   }

   public void logAuthOperation(String sec_name, int snmp_version, int auth_type, int auth_protocol, int result) {
      if (result != 1) {
         ++this.authFailureCount;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("authFailureCount: " + this.authFailureCount + ", [" + sec_name + ", " + Snmp.versionToString(snmp_version) + ", " + auth_type + ", " + USMUser.AuthProtocolToString(auth_protocol) + "]");
         }
      }

      this.logEvent(new SnmpAuthOperationEvent(sec_name, snmp_version, auth_type, auth_protocol, result));
   }

   public void logObjectAccess(String sec_name, int snmp_version, int msg_type, String object_name, SnmpOid instance_or_index, String object_value) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("log object access, version: " + Snmp.versionToString(snmp_version) + ", msg_type: " + SnmpPDU.typeToString(msg_type) + ", [" + object_name + ", " + object_value + "]");
      }

      this.logEvent(new SnmpObjectAccessEvent(sec_name, snmp_version, msg_type, object_name, instance_or_index, object_value));
   }

   public void logErrorResponse(String sec_name, int snmp_version, int msg_type, SnmpVarBindList request_varbinds, int error_status, int error_index) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("log error response, name: " + sec_name + ", version: " + Snmp.versionToString(snmp_version) + ", msg_type: " + SnmpPDU.typeToString(msg_type) + ", error status: " + Snmp.errorStatusToString(error_status) + ", error index: " + error_index);
      }

      this.logEvent(new SnmpErrorResponseEvent(sec_name, snmp_version, msg_type, request_varbinds, error_status, error_index));
   }

   public void logErrorReport(String sec_name, SnmpVarBindList report_varbinds) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("log error report, name: " + sec_name + ", var bindings: " + report_varbinds.toString());
      }

      this.logEvent(new SnmpErrorReportAuditEvent(sec_name, report_varbinds));
   }

   public int getFailedAuthenticationCount() {
      return this.authFailureCount;
   }

   public int getFailedEncryptionCount() {
      return this.privFailureCount;
   }

   class SnmpErrorReportAuditEvent extends SnmpBaseAuditEvent {
      private SnmpVarBindList report_varbinds;

      SnmpErrorReportAuditEvent(String sec_name, SnmpVarBindList report_varbinds) {
         super("SNMP_ERROR_REPORT", AuditSeverity.ERROR, sec_name);
         this.report_varbinds = report_varbinds;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("Variables");
         buf.append(" = ");
         buf.append(this.report_varbinds != null ? this.report_varbinds.toString() : "null");
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpErrorResponseEvent extends SnmpAuditEvent {
      private int msg_type;
      private SnmpVarBindList request_varbinds;
      private int error_status;
      private int error_index;

      SnmpErrorResponseEvent(String sec_name, int snmp_version, int msg_type, SnmpVarBindList request_varbinds, int error_status, int error_index) {
         super("SNMP_ERROR_RESPONSE", AuditSeverity.ERROR, sec_name, snmp_version);
         this.msg_type = msg_type;
         this.request_varbinds = request_varbinds;
         this.error_status = error_status;
         this.error_index = error_index;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("MsgType");
         buf.append(" = ");
         buf.append(this.msg_type);
         buf.append(">");
         buf.append("<");
         buf.append("Variables");
         buf.append(" = ");
         buf.append(this.request_varbinds.toString());
         buf.append(">");
         buf.append("<");
         buf.append("Error-Status");
         buf.append(" = ");
         buf.append(Snmp.errorStatusToString(this.error_status));
         buf.append(">");
         buf.append("<");
         buf.append("Error-Index");
         buf.append(" = ");
         buf.append(this.error_index);
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpObjectAccessEvent extends SnmpAuditEvent {
      private int msg_type;
      private String object_name;
      private SnmpOid instance_or_index;
      private String object_value;

      SnmpObjectAccessEvent(String sec_name, int snmp_version, int msg_type, String object_name, SnmpOid instance_or_index, String object_value) {
         super("SNMP_OBJECT_ACCESS", AuditSeverity.INFORMATION, sec_name, snmp_version);
         this.msg_type = msg_type;
         this.object_name = object_name;
         this.instance_or_index = instance_or_index;
         this.object_value = object_value;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("MsgType");
         buf.append(" = ");
         buf.append(this.msg_type);
         buf.append(">");
         buf.append("<");
         buf.append("Object");
         buf.append(" = ");
         buf.append(this.object_name);
         buf.append(">");
         buf.append("<");
         buf.append("Instance");
         buf.append(" = ");
         buf.append(this.instance_or_index);
         buf.append(">");
         buf.append("<");
         buf.append("Value");
         buf.append(" = ");
         buf.append(this.object_value);
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpAuthOperationEvent extends SnmpAuditEvent {
      private int auth_type;
      private int auth_protocol;
      private int result;

      SnmpAuthOperationEvent(String sec_name, int snmp_version, int auth_type, int auth_protocol, int result) {
         super("SNMP_AUTH_OPERATION", result == 1 ? AuditSeverity.SUCCESS : AuditSeverity.FAILURE, sec_name, snmp_version);
         this.auth_type = auth_type;
         this.auth_protocol = auth_protocol;
         this.result = result;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("Incoming");
         buf.append(" = ");
         buf.append(this.auth_type == 1 ? "Yes" : "No");
         buf.append(">");
         buf.append("<");
         buf.append("Protocol");
         buf.append(" = ");
         buf.append(WLSAuditTrailLogger.this.algToString(this.auth_protocol));
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpPrivOperationEvent extends SnmpAuditEvent {
      private int crypt_type;
      private int priv_protocol;
      private int result;

      SnmpPrivOperationEvent(String sec_name, int snmp_version, int crypt_type, int priv_protocol, int result) {
         super("SNMP_PRIV_OPERATION", result == 1 ? AuditSeverity.SUCCESS : AuditSeverity.FAILURE, sec_name, snmp_version);
         this.crypt_type = crypt_type;
         this.priv_protocol = priv_protocol;
         this.result = result;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("Crypt-Op");
         buf.append(" = ");
         buf.append(this.crypt_type == 2 ? "Encrypt" : "Decrypt");
         buf.append(">");
         buf.append("<");
         buf.append("Protocol");
         buf.append(" = ");
         buf.append(WLSAuditTrailLogger.this.algToString(this.priv_protocol));
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpAuditEvent extends SnmpBaseAuditEvent {
      private int snmp_version;

      public SnmpAuditEvent(String eventType, AuditSeverity severity, String sec_name, int snmp_version) {
         super(eventType, severity, sec_name);
         this.snmp_version = snmp_version;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         super.writeAttributes(buf);
         buf.append("<");
         buf.append("SnmpV");
         buf.append(" = ");
         buf.append(this.snmp_version);
         buf.append(">");
         return buf.toString();
      }
   }

   class SnmpBaseAuditEvent implements AuditEvent {
      private String eventType;
      private AuditSeverity severity;
      private String sec_name;

      public SnmpBaseAuditEvent(String eventType, AuditSeverity severity, String sec_name) {
         this.eventType = eventType;
         this.severity = severity;
         this.sec_name = sec_name;
      }

      public String getEventType() {
         return this.eventType;
      }

      public Exception getFailureException() {
         return null;
      }

      public AuditSeverity getSeverity() {
         return this.severity;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("<");
         this.writeAttributes(buf);
         buf.append(">");
         return buf.toString();
      }

      protected void writeAttributes(StringBuffer buf) {
         buf.append("<");
         buf.append("EVENT_TYPE");
         buf.append(" = ");
         buf.append(this.eventType);
         buf.append("><");
         buf.append("Subject");
         buf.append(" = ");
         buf.append(this.sec_name);
         buf.append("><");
         buf.append("Severity");
         buf.append(" = ");
         buf.append(this.severity.getSeverityString());
         buf.append(">");
      }
   }
}
