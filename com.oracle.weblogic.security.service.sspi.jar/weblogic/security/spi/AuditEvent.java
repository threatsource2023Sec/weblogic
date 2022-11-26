package weblogic.security.spi;

public interface AuditEvent {
   Exception getFailureException();

   AuditSeverity getSeverity();

   String toString();

   String getEventType();
}
