package weblogic.security.spi;

import javax.security.auth.Subject;

public interface AuditApplicationVersionEvent extends AuditEvent {
   String getOperationType();

   Subject getSubject();

   Resource getResource();
}
