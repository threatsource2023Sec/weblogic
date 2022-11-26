package weblogic.management.internal;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.AuditInvokeConfigurationEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditInvokeConfigurationEventImpl extends AuditConfigurationBaseEventImpl implements AuditInvokeConfigurationEvent {
   private static final String OPERATION_ATTR = "Operation";
   private static final String PARAMETERS_ATTR = "Parameters";
   private String methodName;
   private String params;

   public AuditInvokeConfigurationEventImpl(AuditSeverity severity, AuthenticatedSubject subject, String objectName, String operationName, String parameters) {
      super(severity, "Invoke Configuration Audit Event", subject, objectName);
      this.methodName = operationName;
      this.params = parameters;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String getParameters() {
      return this.params;
   }

   public void writeAttributes(StringBuffer buf) {
      super.writeAttributes(buf);
      buf.append("<");
      buf.append("Operation");
      buf.append(" = ");
      buf.append(this.methodName);
      buf.append("><");
      buf.append("Parameters");
      buf.append(" = ");
      buf.append(this.params);
      buf.append(">");
   }
}
