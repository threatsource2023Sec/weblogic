package weblogic.security.spi;

public interface AuditProvider extends SecurityProvider {
   AuditChannel getAuditChannel();
}
