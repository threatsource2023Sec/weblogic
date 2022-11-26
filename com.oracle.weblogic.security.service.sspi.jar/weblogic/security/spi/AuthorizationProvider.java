package weblogic.security.spi;

public interface AuthorizationProvider extends SecurityProvider {
   AccessDecision getAccessDecision();
}
