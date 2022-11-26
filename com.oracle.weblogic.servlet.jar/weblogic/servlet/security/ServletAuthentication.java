package weblogic.servlet.security;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AppContextHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.services.AppContext;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionData;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.provider.WlsSecurityProvider;
import weblogic.servlet.security.internal.CertSecurityModule;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;

public final class ServletAuthentication {
   private static final DebugLogger DEBUG_IA = DebugLogger.getDebugLogger("DebugWebAppIdentityAssertion");
   private static final DebugLogger DEBUG_SEC = DebugLogger.getDebugLogger("DebugWebAppSecurity");
   private static final SecurityProvider provider = WebServerRegistry.getInstance().getSecurityProvider();
   private String usernameField;
   private String passwordField;
   public static final int AUTHENTICATED = 0;
   public static final int FAILED_AUTHENTICATION = 1;
   public static final int NEEDS_CREDENTIALS = 2;

   public ServletAuthentication(String usernameField, String passwordField) {
      this.usernameField = usernameField;
      this.passwordField = passwordField;
   }

   public static void done(HttpServletRequest request) {
      logout(request);
   }

   /** @deprecated */
   @Deprecated
   public static boolean logout(HttpServletRequest req) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      ServletResponseImpl resi = reqi.getResponse();
      WebAppServletContext curContext = reqi.getContext();
      HttpServer httpServer = curContext.getServer();
      SessionInternal curSession = getSessionInternal(req, reqi);
      if (curSession == null) {
         return false;
      } else {
         doLogout(curContext, httpServer, curSession, reqi, resi);
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public static boolean logout(HttpSession session) {
      if (session == null) {
         return false;
      } else {
         SessionInternal sessionInternal = session instanceof SessionInternal ? (SessionInternal)session : null;
         if (sessionInternal == null) {
            return false;
         } else {
            SessionContext sessionContext = sessionInternal.getContext();
            if (sessionContext == null) {
               return false;
            } else {
               WebAppServletContext curContext = sessionContext.getServletContext();
               if (curContext == null) {
                  return false;
               } else {
                  HttpServer httpServer = curContext.getServer();
                  if (httpServer == null) {
                     return false;
                  } else {
                     doLogout(curContext, httpServer, sessionInternal, (ServletRequestImpl)null, (ServletResponseImpl)null);
                     return true;
                  }
               }
            }
         }
      }
   }

   private static void doLogout(WebAppServletContext curContext, HttpServer httpServer, SessionInternal curSession, ServletRequestImpl reqi, ServletResponseImpl resi) {
      removeAuthenticatedUser(curContext, curSession);
      String sessionid = curSession.getIdWithServerInfo();
      WebAppServletContext[] webapps = httpServer.getServletContextManager().getAllContexts();
      if (webapps != null) {
         for(int i = 0; i < webapps.length; ++i) {
            WebAppServletContext ctx = webapps[i];
            if (ctx != null && ctx != curContext) {
               SessionContext sctx = ctx.getSessionContext();
               SessionInternal sess = sctx.getSessionInternal(sessionid, (ServletRequestImpl)null, (ServletResponseImpl)null);
               if (sess != null) {
                  removeAuthenticatedUser(curContext, sess);
               }
            }
         }
      }

      httpServer.getSessionLogin().unregister(curSession.getInternalId());
      popCurrentSubject();
   }

   private static void removeAuthenticatedUser(WebAppServletContext context, SessionInternal curSession) {
      curSession.removeInternalAttribute("weblogic.authuser");
      curSession.removeInternalAttribute("weblogic.authuser.associated.data");
      curSession.removeInternalAttribute(context.getSessionContext().getConfigMgr().getWLSAuthCookieName());
   }

   public static boolean invalidateAll(HttpServletRequest req) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      WebAppServletContext curContext = reqi.getContext();
      HttpServer httpServer = curContext.getServer();
      SessionInternal curSession = getSessionInternal(req, reqi);
      if (curSession == null) {
         return false;
      } else {
         String sessionId = curSession.getIdWithServerInfo();
         curSession.invalidate();
         WebAppServletContext[] webapps = httpServer.getServletContextManager().getAllContexts();
         if (webapps != null) {
            for(int i = 0; i < webapps.length; ++i) {
               WebAppServletContext ctx = webapps[i];
               if (ctx != null && ctx != curContext) {
                  SessionContext sctx = ctx.getSessionContext();
                  HttpSession sess = sctx.getSessionInternal(sessionId, (ServletRequestImpl)null, (ServletResponseImpl)null);
                  if (sess != null) {
                     ctx.invalidateSession(sess);
                  }
               }
            }
         }

         reqi.getSessionHelper().killOldSession();
         httpServer.getSessionLogin().unregister(curSession.getInternalId());
         popCurrentSubject();
         return true;
      }
   }

   public static void killCookie(HttpServletRequest req) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      reqi.getSessionHelper().killOldSession();
      popCurrentSubject();
   }

   public static int strong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      String realmName = reqi.getContext().getSecurityRealmName();
      return strong(request, response, realmName);
   }

   /** @deprecated */
   @Deprecated
   public static int strong(HttpServletRequest request, HttpServletResponse response, String realmName) throws ServletException, IOException {
      try {
         return assertIdentity(request, response, realmName);
      } catch (LoginException var4) {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug("Login failed for request: " + request.toString(), var4);
         }

         return 1;
      }
   }

   /** @deprecated */
   @Deprecated
   public static int assertIdentity(HttpServletRequest request, HttpServletResponse response, String realmName) throws ServletException, IOException, LoginException {
      return assertIdentity(request, response, realmName, (AppContext)null);
   }

   /** @deprecated */
   @Deprecated
   public static int assertIdentity(HttpServletRequest request, HttpServletResponse response, String realmName, AppContext appContext) throws ServletException, IOException, LoginException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      if (reqi == null) {
         throw new IllegalArgumentException("The request wrapper doesn't allow access to original request");
      } else {
         AuthenticatedSubject subject = null;

         try {
            CertSecurityModule.Token token = CertSecurityModule.findToken(request, reqi.getConnection(), reqi.getContext().getSecurityContext());
            if (token == null) {
               return 2;
            }

            if (DEBUG_IA.isDebugEnabled()) {
               DEBUG_IA.debug("assertIdentity with tokem.type: " + token.type + " token.value: " + token.value);
            }

            ContextHandler ctxHandler = null;
            if (appContext == null) {
               ctxHandler = WlsSecurityProvider.getContextHandler(request, response);
            } else {
               ctxHandler = AppContextHandler.getInstance(appContext);
            }

            PrincipalAuthenticator pa = getPrincipalAuthenticator(realmName);
            subject = pa.assertIdentity(token.type, token.value, (ContextHandler)ctxHandler);
         } catch (SecurityException var9) {
            if (DEBUG_IA.isDebugEnabled()) {
               DEBUG_IA.debug("Indentity assertion failed", var9);
            }

            HTTPLogger.logCertAuthenticationError(request.getRequestURI(), var9);
         }

         if (subject != null && !SubjectUtils.isUserAnonymous(subject)) {
            SessionInternal session = getSessionInternal(request, reqi);
            session.setInternalAttribute("weblogic.authuser", subject);
            pushSubject(WlsSecurityProvider.toSubjectHandle(subject));
            SecurityModule.setupAuthCookie(reqi.getContext().getSecurityContext(), request, session, session.getInternalId());
            return 0;
         } else {
            return 1;
         }
      }
   }

   private static PrincipalAuthenticator getPrincipalAuthenticator(final String realmName) {
      return (PrincipalAuthenticator)AccessController.doPrivileged(new PrivilegedAction() {
         public PrincipalAuthenticator run() {
            return WebServerRegistry.getInstance().getSecurityProvider().getSecurityService(realmName);
         }
      });
   }

   public int weak(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String username = request.getParameter(this.usernameField);
      String password = request.getParameter(this.passwordField);
      return weak(username, password, request, (HttpServletResponse)null);
   }

   /** @deprecated */
   @Deprecated
   public static int weak(String username, String password, HttpServletRequest request, HttpServletResponse response) {
      try {
         return login(username, password, request, response);
      } catch (LoginException var5) {
         return 1;
      }
   }

   /** @deprecated */
   @Deprecated
   public static int login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws LoginException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      if (response == null) {
         response = reqi.getResponse();
      }

      ServletSecurityContext context = reqi.getContext().getSecurityContext();
      SubjectHandle subject = SecurityModule.checkAuthenticate(context, request, (HttpServletResponse)response, username, password);
      if (subject == null) {
         return 1;
      } else {
         SessionInternal session = getSessionInternal(request, reqi);
         context.getSessionRegistry().setUser(session.getInternalId(), subject);
         session.setInternalAttribute("weblogic.authuser", provider.unwrapSubject(subject));
         pushSubject(subject);
         SecurityModule.setupAuthCookie(reqi.getContext().getSecurityContext(), request, session, session.getInternalId());
         return 0;
      }
   }

   /** @deprecated */
   @Deprecated
   public static int weak(String username, String password, HttpSession session) {
      if (session != null && session instanceof SessionInternal) {
         SessionInternal data = (SessionInternal)session;
         WebAppServletContext ctx = data.getContext().getServletContext();
         HttpServer httpServer = ctx.getServer();
         String securityRealmName = ctx.getSecurityRealmName();
         SubjectHandle subject = null;
         SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);

         try {
            subject = getSecurityManager(ctx).getAppSecurityProvider().authenticate(handler, ctx.getSecurityRealmName(), (HttpServletRequest)null, (HttpServletResponse)null);
         } catch (LoginException var10) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("Login failed", var10);
            }

            return 1;
         }

         if (subject == null) {
            return 1;
         } else {
            httpServer.getSessionLogin().setUser(data.getInternalId(), subject);
            data.setInternalAttribute("weblogic.authuser", provider.unwrapSubject(subject));
            pushSubject(subject);
            return 0;
         }
      } else {
         return 1;
      }
   }

   /** @deprecated */
   @Deprecated
   public static int authObject(String username, Object credential, HttpServletRequest request) {
      HttpSession session = request.getSession(true);
      return session == null ? 1 : authObject(username, credential, session, request);
   }

   /** @deprecated */
   @Deprecated
   public static int authObject(String username, Object credential, HttpSession session, HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      WebAppServletContext ctx = reqi.getContext();
      SubjectHandle subject = SecurityModule.checkAuthenticate(ctx.getSecurityContext(), request, reqi.getResponse(), username, credential, false);
      if (subject == null) {
         return 1;
      } else {
         SessionInternal data;
         if (session != null && session instanceof SessionInternal) {
            data = (SessionInternal)session;
         } else {
            data = getSessionInternal(request, reqi);
         }

         ctx.getServer().getSessionLogin().setUser(data.getInternalId(), subject);
         data.setInternalAttribute("weblogic.authuser", provider.unwrapSubject(subject));
         pushSubject(subject);
         return 0;
      }
   }

   public static int authenticate(CallbackHandler handler, HttpServletRequest request) {
      try {
         return login(handler, request);
      } catch (LoginException var3) {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug("Login failed for request: " + request.toString(), var3);
         }

         return 1;
      }
   }

   public static int login(CallbackHandler handler, HttpServletRequest request) throws LoginException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      String securityRealmName = reqi.getContext().getSecurityRealmName();
      SubjectHandle subject = getSecurityManager(reqi.getContext()).getAppSecurityProvider().authenticate(handler, securityRealmName, request, reqi.getResponse());
      if (subject == null) {
         return 1;
      } else {
         SessionInternal session = getSessionInternal(request, reqi);
         if (session != null) {
            if (reqi.getContext().getSecurityContext().getSessionRegistry() != null) {
               reqi.getContext().getSecurityContext().getSessionRegistry().setUser(session.getInternalId(), subject);
            }

            session.setInternalAttribute("weblogic.authuser", provider.unwrapSubject(subject));
         } else {
            String sessionid = request.getRequestedSessionId();
            if (sessionid != null && subject != null && !subject.isAnonymous() && !subject.isKernel()) {
               sessionid = SessionData.getID(sessionid);
               reqi.getContext().getSecurityContext().getSessionRegistry().setUser(sessionid, subject);
            }
         }

         pushSubject(subject);
         SecurityModule.setupAuthCookie(reqi.getContext().getSecurityContext(), request, session, session.getInternalId());
         return 0;
      }
   }

   public static void generateNewSessionID(HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      reqi.getSessionHelper().updateSessionId();
      SessionInternal session = getSessionInternal(request, reqi);
      SecurityModule.setupAuthCookie(reqi.getContext().getSecurityContext(), request, session, session.getInternalId());
   }

   public static Cookie getSessionCookie(HttpServletRequest request, HttpServletResponse response) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      ServletResponseImpl resi = reqi.getResponse();
      return resi.getCookie(reqi.getContext().getSessionContext().getConfigMgr().getCookieName());
   }

   public static void runAs(Subject subject, HttpServletRequest request) {
      runAs(AuthenticatedSubject.getFromSubject(subject), request);
   }

   public static void runAs(Subject subject, Map associatedData, HttpServletRequest request) {
      runAs(AuthenticatedSubject.getFromSubject(subject), associatedData, request);
   }

   public static void runAs(AuthenticatedSubject subject, HttpServletRequest request) {
      runAs((AuthenticatedSubject)subject, (Map)null, request);
   }

   public static void runAs(AuthenticatedSubject subject, Map associatedData, HttpServletRequest request) {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      WebAppServletContext ctx = reqi.getContext();
      SessionInternal session = getSessionInternal(request, reqi);
      SubjectHandle handle = WlsSecurityProvider.toSubjectHandle(subject, associatedData);
      session.setInternalAttribute("weblogic.authuser", subject);
      if (handle.getAssociatedData() != null) {
         session.setInternalAttribute("weblogic.authuser.associated.data", handle.getAssociatedData());
      }

      if (ctx.getSecurityContext().getSessionRegistry() != null) {
         ctx.getSecurityContext().getSessionRegistry().setUser(session.getInternalId(), handle);
      }

      pushSubject(handle);
      SecurityModule.setupAuthCookie(ctx.getSecurityContext(), request, session, session.getInternalId());
   }

   public static Map getUserAssociatedData(HttpServletRequest request) {
      Map associatedData = null;
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(request);
      SubjectHandle subject = SecurityModule.getCurrentUser(reqi.getContext().getSecurityContext(), request);
      if (subject != null) {
         associatedData = subject.getAssociatedData();
      }

      return associatedData;
   }

   public static String getTargetURLForFormAuthentication(HttpSession session) {
      return ((WebAppServletContext)session.getServletContext()).getConfigManager().isServletAuthFromURL() ? (String)((SessionInternal)session).getInternalAttribute("weblogic.formauth.targeturl") : (String)((SessionInternal)session).getInternalAttribute("weblogic.formauth.targeturi");
   }

   public static String getTargetURIForFormAuthentication(HttpSession session) {
      return (String)((SessionInternal)session).getInternalAttribute("weblogic.formauth.targeturi");
   }

   private static SessionInternal getSessionInternal(HttpServletRequest req, ServletRequestImpl reqi) {
      HttpSession sess = req.getSession(true);
      SessionInternal s = sess instanceof SessionInternal ? (SessionInternal)sess : (SessionInternal)reqi.getSession(false);
      if (s == null) {
         throw new AssertionError("Internal type of session is not available.");
      } else {
         return s;
      }
   }

   private static WebAppSecurity getSecurityManager(final WebAppServletContext ctx) {
      return (WebAppSecurity)AccessController.doPrivileged(new PrivilegedAction() {
         public WebAppSecurity run() {
            return ctx.getSecurityManager();
         }
      });
   }

   private static void popCurrentSubject() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            SubjectHandle subject = ServletAuthentication.provider.getCurrentSubject();
            if (subject != null && !subject.isAnonymous()) {
               ServletAuthentication.provider.popSubject();
               ServletAuthentication.provider.pushSubject(ServletAuthentication.provider.getAnonymousSubject());
            }

            return null;
         }
      });
   }

   private static void pushSubject(final SubjectHandle subject) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            ServletAuthentication.provider.pushSubject(subject);
            return null;
         }
      });
   }
}
