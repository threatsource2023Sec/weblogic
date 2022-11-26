package weblogic.security.spi;

import javax.security.auth.login.AppConfigurationEntry;

public interface AuthenticationProvider extends SecurityProvider {
   AppConfigurationEntry getLoginModuleConfiguration();

   AppConfigurationEntry getAssertionModuleConfiguration();

   PrincipalValidator getPrincipalValidator();

   IdentityAsserter getIdentityAsserter();
}
