package weblogic.security.spi;

import javax.security.auth.Subject;

public interface AuditConfigurationEvent extends AuditEvent, AuditContext {
   Subject getSubject();

   String getObjectName();
}
