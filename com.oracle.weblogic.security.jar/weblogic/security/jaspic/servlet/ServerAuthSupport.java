package weblogic.security.jaspic.servlet;

public interface ServerAuthSupport {
   String OPTION_SERVER_AUTH_SUPPORT = "com.oracle.weblogic.servlet.auth_support";

   String getRealmBanner();

   String getErrorPage(int var1);

   boolean isEnforceBasicAuth();
}
