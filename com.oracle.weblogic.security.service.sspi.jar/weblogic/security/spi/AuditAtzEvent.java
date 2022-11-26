package weblogic.security.spi;

import javax.security.auth.Subject;

public interface AuditAtzEvent extends AuditEvent, AuditContext {
   Subject getSubject();

   Resource getResource();
}
