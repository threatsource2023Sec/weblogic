package weblogic.management.internal;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.AuditDeleteConfigurationEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditDeleteConfigurationEventImpl extends AuditConfigurationBaseEventImpl implements AuditDeleteConfigurationEvent {
   public AuditDeleteConfigurationEventImpl(AuditSeverity severity, AuthenticatedSubject subject, String objectName) {
      super(severity, "Delete Configuration Audit Event", subject, objectName);
   }
}
