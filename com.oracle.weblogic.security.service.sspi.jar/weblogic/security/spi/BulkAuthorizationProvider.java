package weblogic.security.spi;

public interface BulkAuthorizationProvider extends AuthorizationProvider {
   BulkAccessDecision getBulkAccessDecision();
}
