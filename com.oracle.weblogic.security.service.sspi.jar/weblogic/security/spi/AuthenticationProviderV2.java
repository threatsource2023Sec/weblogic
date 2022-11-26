package weblogic.security.spi;

import javax.security.auth.login.AppConfigurationEntry;

public interface AuthenticationProviderV2 extends SecurityProvider {
   AppConfigurationEntry getLoginModuleConfiguration();

   AppConfigurationEntry getAssertionModuleConfiguration();

   PrincipalValidator getPrincipalValidator();

   IdentityAsserterV2 getIdentityAsserter();
}
