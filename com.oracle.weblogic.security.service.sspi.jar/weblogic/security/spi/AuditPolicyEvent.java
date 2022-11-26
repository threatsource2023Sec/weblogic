package weblogic.security.spi;

import javax.security.auth.Subject;

public interface AuditPolicyEvent extends AuditEvent {
   Subject getSubject();

   Resource getResource();
}
