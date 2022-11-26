package weblogic.management.internal;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.AuditCreateConfigurationEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditCreateConfigurationEventImpl extends AuditConfigurationBaseEventImpl implements AuditCreateConfigurationEvent {
   public AuditCreateConfigurationEventImpl(AuditSeverity severity, AuthenticatedSubject subject, String objectName) {
      super(severity, "Create Configuration Audit Event", subject, objectName);
   }
}
