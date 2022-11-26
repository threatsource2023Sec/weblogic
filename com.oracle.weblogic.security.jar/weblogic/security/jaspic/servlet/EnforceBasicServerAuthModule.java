package weblogic.security.jaspic.servlet;

public class EnforceBasicServerAuthModule extends BasicServerAuthModule {
   protected boolean enforceCredentials() {
      return true;
   }
}
