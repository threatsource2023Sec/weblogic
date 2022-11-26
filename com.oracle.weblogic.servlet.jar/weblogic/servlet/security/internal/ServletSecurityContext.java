package weblogic.servlet.security.internal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.SecurityRole;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.servlet.spi.ApplicationSecurity;

public interface ServletSecurityContext {
   ServletContext getServletContext();

   String getLogContext();

   ApplicationSecurity getAppSecurityProvider();

   String getSecurityRealmName();

   boolean isRetainOriginalURL();

   String getDefaultEncoding();

   boolean isReloginEnabled();

   boolean isAdminMode();

   String getAuthRealmName();

   String getRealmAuthMethods();

   void registerContextPath(String var1);

   void securedExecute(HttpServletRequest var1, HttpServletResponse var2) throws Throwable;

   boolean isInternalApp();

   String getContextName();

   SecurityRole getSecurityRole(String var1);

   boolean isAllowAllRoles();

   int getFrontEndHTTPSPort();

   RequestDispatcher createAuthFilterRequestDispatcher(String var1);

   ServletBean lookupServlet(String var1);

   String getContextURI();

   String getApplicationId();

   void removeAuthUserFromSession(HttpServletRequest var1, String var2);

   String getServerName();

   boolean isInvalidateOnRelogin();

   boolean isSessionCookiesEnabled();

   SessionRegistry getSessionRegistry();

   boolean isAuthCookieEnabled();

   void removeAllWlsAuthCookies(HttpServletRequest var1, SessionSecurityData var2, String var3, String var4);

   String getErrorPage(int var1);

   String getCookiePath();

   String getCookieDomain();

   String getWLSAuthCookieName();

   int getWLSAuthCookieIdLength();

   ServletObjectsFacade getRequestFacade();

   String getUrlMatchMap();

   boolean isCaseInsensitive();

   boolean useJACC(String var1);

   ServletConfig getServletConfig(HttpServletRequest var1);
}
