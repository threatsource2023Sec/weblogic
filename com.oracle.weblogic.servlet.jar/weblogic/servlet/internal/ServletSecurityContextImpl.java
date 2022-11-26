package weblogic.servlet.internal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.SecurityRole;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionData;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.security.internal.ServletObjectsFacade;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.security.internal.SessionRegistry;
import weblogic.servlet.security.internal.SessionSecurityData;
import weblogic.servlet.spi.ApplicationSecurity;

public class ServletSecurityContextImpl implements ServletSecurityContext {
   private static final ServletObjectsFacade REQUEST_FACADE = new ServletObjectsFacadeImpl();
   private WebAppServletContext servletContext;

   public ServletContext getServletContext() {
      return this.servletContext;
   }

   public ServletSecurityContextImpl(WebAppServletContext servletContext) {
      this.servletContext = servletContext;
   }

   public String getLogContext() {
      return this.servletContext.getLogContext();
   }

   public ApplicationSecurity getAppSecurityProvider() {
      return this.servletContext.getSecurityManager().getAppSecurityProvider();
   }

   public String getSecurityRealmName() {
      return this.servletContext.getSecurityRealmName();
   }

   public boolean isRetainOriginalURL() {
      return this.servletContext.getConfigManager().isRetainOriginalURL();
   }

   public String getDefaultEncoding() {
      return this.servletContext.getConfigManager().getDefaultEncoding();
   }

   public boolean isReloginEnabled() {
      return this.servletContext.getConfigManager().isReloginEnabled();
   }

   public boolean isAdminMode() {
      return this.servletContext.isAdminMode();
   }

   public String getAuthRealmName() {
      return this.servletContext.getConfigManager().getAuthRealmName();
   }

   public String getRealmAuthMethods() {
      return WebAppConfigManager.getRealmAuthMethods();
   }

   public void registerContextPath(String id) {
      this.servletContext.getServer().getSessionLogin().register(id, this.getServletContext().getContextPath());
   }

   public void securedExecute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
      this.servletContext.securedExecute(request, response, false);
   }

   public boolean isInternalApp() {
      return this.servletContext.isInternalApp();
   }

   public String getContextName() {
      return this.servletContext.getName();
   }

   public SecurityRole getSecurityRole(String roleName) {
      return this.servletContext.getApplicationContext().getSecurityRole(roleName);
   }

   public boolean isAllowAllRoles() {
      return this.servletContext.getConfigManager().isAllowAllRoles();
   }

   public int getFrontEndHTTPSPort() {
      return this.servletContext.getServer().getFrontendHTTPSPort();
   }

   public RequestDispatcher createAuthFilterRequestDispatcher(String authFilterName) {
      ServletStubImpl sstub = new ServletStubImpl(authFilterName, authFilterName, this.servletContext);
      RequestDispatcherImpl requestDispatcher = new RequestDispatcherImpl(sstub, this.servletContext, -1);
      requestDispatcher.disableFilters();
      return requestDispatcher;
   }

   public ServletBean lookupServlet(String servletName) {
      return this.servletContext.getWebAppModule().getWebAppBean().lookupServlet(servletName);
   }

   public String getContextURI() {
      return this.servletContext.getURI();
   }

   public String getApplicationId() {
      return this.servletContext.getApplicationId();
   }

   public void removeAuthUserFromSession(HttpServletRequest request, String sessionid) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      WebAppServletContext[] webapps = this.servletContext.getServer().getServletContextManager().getAllContexts();
      if (webapps != null) {
         for(int i = 0; i < webapps.length; ++i) {
            WebAppServletContext ctx = webapps[i];
            if (ctx != null && ctx != this.getServletContext()) {
               SessionContext sctx = ctx.getSessionContext();
               SessionInternal sess = sctx.getSessionInternal(sessionid, reqi, reqi.getResponse());
               if (sess != null) {
                  sess.removeInternalAttribute("weblogic.authuser");
                  sess.removeInternalAttribute("weblogic.authuser.associated.data");
               }
            }
         }
      }

   }

   public String getServerName() {
      return this.servletContext.getServer().getName();
   }

   public SessionRegistry getSessionRegistry() {
      return this.servletContext.getServer() == null ? null : this.servletContext.getServer().getSessionLogin();
   }

   public boolean isAuthCookieEnabled() {
      return this.servletContext.getServer().isAuthCookieEnabled();
   }

   public void removeAllWlsAuthCookies(HttpServletRequest request, SessionSecurityData session, String id, String wlsAuthCookieName) {
      ServletRequestImpl req = ServletRequestImpl.getOriginalRequest(request);
      ServletResponseImpl res = req.getResponse();
      WebAppServletContext[] webapps = this.servletContext.getServer().getServletContextManager().getAllContexts();

      for(int i = 0; i < webapps.length; ++i) {
         WebAppServletContext wasc = webapps[i];
         if (!wasc.equals(((SessionInternal)session).getContext().getServletContext()) && wasc.getSessionContext().getConfigMgr().getWLSAuthCookieName().equals(wlsAuthCookieName)) {
            SessionContext sc = wasc.getSessionContext();
            SessionData sd = sc.getSessionInternalForAuthentication(id, req, res);
            if (sd != null) {
               if (sd.getInternalAttribute(wlsAuthCookieName) != null) {
                  sd.removeInternalAttribute(wlsAuthCookieName);
               }

               synchronized(sd) {
                  sc.sync(sd);
               }
            }
         }
      }

   }

   public String getUrlMatchMap() {
      return this.servletContext.getUrlMatchMap();
   }

   public String getErrorPage(int code) {
      return ErrorMessages.getErrorPage(code);
   }

   public boolean isInvalidateOnRelogin() {
      return this.servletContext.getSessionContext().getConfigMgr().isInvalidateOnRelogin();
   }

   public boolean isSessionCookiesEnabled() {
      return this.servletContext.getSessionContext().getConfigMgr().isSessionCookiesEnabled();
   }

   public String getCookiePath() {
      return this.servletContext.getSessionContext().getConfigMgr().getCookiePath();
   }

   public String getCookieDomain() {
      return this.servletContext.getSessionContext().getConfigMgr().getCookieDomain();
   }

   public String getWLSAuthCookieName() {
      return this.servletContext.getSessionContext().getConfigMgr().getWLSAuthCookieName();
   }

   public int getWLSAuthCookieIdLength() {
      return this.servletContext.getSessionContext().getConfigMgr().getAuthCookieIDLength();
   }

   public ServletObjectsFacade getRequestFacade() {
      return REQUEST_FACADE;
   }

   public boolean isCaseInsensitive() {
      return WebAppConfigManager.isCaseInsensitive();
   }

   public boolean useJACC(String docRoot) {
      return !this.isInternalApp() && docRoot != null;
   }

   public ServletConfig getServletConfig(HttpServletRequest request) {
      ServletRequestImpl reqImpl = ServletRequestImpl.getOriginalRequest(request);
      return reqImpl.getServletStub();
   }
}
