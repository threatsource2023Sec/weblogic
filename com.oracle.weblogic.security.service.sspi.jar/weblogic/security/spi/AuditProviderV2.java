package weblogic.security.spi;

public interface AuditProviderV2 extends SecurityProvider {
   AuditChannelV2 getAuditChannel();
}
