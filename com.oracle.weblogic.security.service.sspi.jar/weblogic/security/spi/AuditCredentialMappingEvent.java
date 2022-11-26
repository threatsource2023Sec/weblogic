package weblogic.security.spi;

import javax.security.auth.Subject;

public interface AuditCredentialMappingEvent extends AuditEvent, AuditContext {
   Resource getResource();

   Subject getRequestorSubject();

   Subject getInitiatorSubject();

   String getInitiatorString();

   String[] getCredentialTypes();

   Object[] getCredentials();
}
