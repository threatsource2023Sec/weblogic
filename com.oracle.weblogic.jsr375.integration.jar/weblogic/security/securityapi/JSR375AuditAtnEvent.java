package weblogic.security.securityapi;

import com.bea.common.security.utils.UsernameUtils;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditAtnEventV2;
import weblogic.security.spi.AuditSeverity;

public class JSR375AuditAtnEvent implements AuditAtnEventV2 {
   private AuditSeverity severity;
   private String username;
   private ContextHandler handler;
   private AuditAtnEventV2.AtnEventTypeV2 atnEventType;
   private static final String eventType = "Event Type = JSR375 Authentication Audit Event";
   private Exception exception;

   JSR375AuditAtnEvent(AuditSeverity severity, String username, ContextHandler handler, AuditAtnEventV2.AtnEventTypeV2 atnEventType, Exception exception) {
      this.severity = severity;
      this.handler = handler;
      this.atnEventType = atnEventType;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = JSR375 Authentication Audit Event";
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public ContextHandler getContext() {
      return this.handler;
   }

   public AuditAtnEventV2.AtnEventTypeV2 getAtnEventType() {
      return this.atnEventType;
   }

   public String getUsername() {
      return this.username;
   }

   public String toString() {
      String toString = "<<Event Type = JSR375 Authentication Audit Event><" + UsernameUtils.getTruncatedUsername(this.username) + "><" + this.atnEventType.toString() + ">>";
      return toString;
   }
}
