package weblogic.security.jaspic.servlet;

public class NoEnforceBasicServerAuthModule extends BasicServerAuthModule {
   protected boolean enforceCredentials() {
      return false;
   }
}
