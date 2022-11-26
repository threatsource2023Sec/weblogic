package weblogic.security.spi;

public interface AuditInvokeConfigurationEvent extends AuditConfigurationEvent {
   String INVOKE_EVENT = "Invoke Configuration Audit Event";

   String getMethodName();

   String getParameters();
}
