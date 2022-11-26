package weblogic.servlet.security.internal;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.jaspic.SecurityServices;
import weblogic.security.jaspic.servlet.JaspicSecurityModule;
import weblogic.security.jaspic.servlet.JaspicUtilities;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ServletNestedRuntimeException;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.session.HTTPSessionLogger;
import weblogic.servlet.spi.ApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Encoder;

public abstract class SecurityModule {
   public static final String SESSION_AUTH_USER = "weblogic.authuser";
   public static final String SESSION_AUTH_USER_DATA = "weblogic.authuser.associated.data";
   public static final String SESSION_FORM_URL = "weblogic.formauth.targeturl";
   public static final String SESSION_FORM_URI = "weblogic.formauth.targeturi";
   public static final String SESSION_FORM_METHOD = "weblogic.formauth.method";
   public static final int AUTHENTICATED = 0;
   public static final int FAILED_AUTHENTICATION = 1;
   public static final int NEEDS_CREDENTIALS = 2;
   static final String SESSION_FORM_QUERY = "weblogic.formauth.queryparams";
   static final String SESSION_FORM_BYTEARRAY = "weblogic.formauth.bytearray";
   static final String SESSION_FORM_REQHEADNAMES = "weblogic.formauth.reqheadernames";
   static final String SESSION_FORM_REQHEADVALUES = "weblogic.formauth.reqheadervalues";
   static final String SESSION_POST_COOKIE = "weblogic.formauth.postcookie";
   static final String SESSION_FORM_IMMEDIATE = "weblogic.formauth.immediate";
   public static final String REQUEST_AUTH_RESULT = "weblogic.auth.result";
   public static final int REQUEST_PRE_AUTH = -1;
   private static final String NULL_AUTH_COOKIE = "null";
   private static final String AUTHTYPE_UNSPECIFIED = "Authtype_Unspecified";
   public static final String ASSERTION_AUTH = "ASSERTION";
   public static final String REALM_AUTH = "REALM";
   protected final WebAppSecurity webAppSecurity;
   protected static final DebugLogger DEBUG_SEC = DebugLogger.getDebugLogger("DebugWebAppSecurity");
   public String authRealmBanner;
   protected boolean delegateControl;
   private ServletSecurityContext securityContext;
   public static final String WEBFLOW_RESOURCE = "webflow_resource";
   public static final String ENABLE_DIGEST_DEFAULT_TO_BASIC = "weblogic.servlet.security.enableDigestDefaultToBasicAuth";
   private static final Boolean defaultToBasic = Boolean.getBoolean("weblogic.servlet.security.enableDigestDefaultToBasicAuth");
   public static boolean ignorePluginParamsForCookiePath = Boolean.getBoolean("weblogic.cookies.ignorePluginParamsForCookiePath");

   SecurityModule(WebAppSecurity was, ServletSecurityContext ctx) {
      this.authRealmBanner = null;
      this.delegateControl = false;
      this.webAppSecurity = was;
      this.securityContext = ctx;
   }

   protected SecurityModule(ServletSecurityContext ctx, WebAppSecurity was, boolean control) {
      this(was, ctx);
      this.delegateControl = control;
   }

   protected ServletSecurityContext getSecurityContext() {
      return this.securityContext;
   }

   protected ServletObjectsFacade getRequestFacade() {
      return this.securityContext.getRequestFacade();
   }

   protected boolean isReloginEnabled() {
      return this.securityContext.isReloginEnabled();
   }

   boolean checkAccess(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, boolean applyAuthFilters) throws IOException, ServletException {
      if (!this.webAppSecurity.checkTransport(cons, req, rsp)) {
         return false;
      } else {
         SubjectHandle subject = getCurrentUser(this.getSecurityContext(), req, session);
         return this.checkUserPerm(req, rsp, session, cons, subject, applyAuthFilters);
      }
   }

   boolean postCheckAccess(HttpServletResponse response) throws IOException {
      return false;
   }

   protected HttpServletRequest getWrappedRequest(HttpServletRequest request) throws ServletException {
      return null;
   }

   protected HttpServletResponse getWrappedResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      return null;
   }

   protected abstract boolean checkUserPerm(HttpServletRequest var1, HttpServletResponse var2, SessionSecurityData var3, ResourceConstraint var4, SubjectHandle var5, boolean var6) throws IOException, ServletException;

   protected boolean postInvoke(HttpServletRequest request, HttpServletResponse response, SubjectHandle subjectHandle) throws ServletException {
      return true;
   }

   public static SubjectHandle getCurrentUser(ServletSecurityContext securityContext, HttpServletRequest request) {
      SubjectHandle subject = findUserFromRequest(request);
      return subject != null ? subject : getCurrentUser(securityContext, request, (SessionSecurityData)request.getSession(false));
   }

   public static SubjectHandle getCurrentUser(ServletSecurityContext context, HttpServletRequest request, SessionSecurityData session) {
      SubjectHandle subject = findUserFromRequest(request);
      if (subject != null) {
         return subject;
      } else {
         SessionRegistry loginSession = context.getSessionRegistry();
         String reqId = null;

         String wlsAuthCookieName;
         try {
            if (session != null) {
               String sessionID = session.getInternalId();
               subject = loginSession.getUser(sessionID);
               if (subject == null) {
                  ServletRequestImpl reqImpl = ServletRequestImpl.getOriginalRequest(request);
                  if (reqImpl != null) {
                     reqId = reqImpl.getIncomingSessionCookieValue();
                  }

                  if (reqId != null) {
                     subject = loginSession.getUser(reqId);
                  }
               }

               if (subject != null) {
                  session.setInternalAttribute("weblogic.authuser", WebAppSecurity.getProvider().unwrapSubject(subject));
                  if (subject.getAssociatedData() != null) {
                     session.setInternalAttribute("weblogic.authuser.associated.data", subject.getAssociatedData());
                  }
               } else {
                  Object authSubject = session.getInternalAttribute("weblogic.authuser");
                  Object authSubjectData = session.getInternalAttribute("weblogic.authuser.associated.data");
                  if (authSubject != null) {
                     subject = WebAppSecurity.getProvider().wrapSubject(authSubject, authSubjectData);
                     loginSession.setUser(sessionID, subject);
                  }
               }

               wlsAuthCookieName = context.getWLSAuthCookieName();
               String authCookieId = (String)session.getInternalAttribute(wlsAuthCookieName);
               if (authCookieId == null) {
                  authCookieId = loginSession.getCookieId(sessionID);
                  if (authCookieId != null) {
                     session.setInternalAttribute(wlsAuthCookieName, authCookieId);
                  }
               } else {
                  loginSession.addCookieId(sessionID, authCookieId);
               }
            } else {
               reqId = request.getRequestedSessionId();
               if (reqId != null) {
                  subject = loginSession.getUser(reqId);
               }
            }
         } catch (IllegalStateException var9) {
            wlsAuthCookieName = session == null ? "null" : session.getInternalId();
            HTTPSessionLogger.logSessionExpired(wlsAuthCookieName, var9);
         }

         return subject;
      }
   }

   private static SubjectHandle findUserFromRequest(HttpServletRequest request) {
      SubjectHandle subject = null;
      if (request instanceof ServletRequestImpl) {
         subject = ((ServletRequestImpl)request).getCurrentSubject();
      }

      return subject;
   }

   public static SubjectHandle checkAuthenticate(ServletSecurityContext securityContext, HttpServletRequest request, HttpServletResponse response, String username, Object credential, boolean handleException) {
      try {
         return checkAuthenticate(securityContext, request, response, username, credential);
      } catch (LoginException var7) {
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug("Login failed for request: " + request.toString(), var7);
         }

         if (handleException) {
            recordErrorPageAttributes(request, var7);
         }

         return null;
      }
   }

   static void recordErrorPageAttributes(HttpServletRequest request, LoginException se) {
      request.setAttribute("javax.servlet.error.exception_type", se.getClass());
      request.setAttribute("javax.servlet.error.exception", se);
      request.setAttribute("javax.servlet.error.message", se.getMessage());
      SessionSecurityData session = (SessionSecurityData)request.getSession(false);
      if (session != null) {
         String uri = (String)session.getInternalAttribute("weblogic.formauth.targeturi");
         request.setAttribute("javax.servlet.error.request_uri", uri == null ? request.getRequestURI() : uri);
      }

      request.setAttribute("javax.servlet.jsp.jspException", se);
      request.setAttribute("javax.servlet.error.status_code", 403);
   }

   public static SubjectHandle checkAuthenticate(final ServletSecurityContext securityContext, HttpServletRequest request, HttpServletResponse response, String username, Object credential) throws LoginException {
      SessionSecurityData session = (SessionSecurityData)request.getSession(false);
      SubjectHandle subject = getCurrentUser(securityContext, request, session);
      if (subject != null) {
         if ((username == null || username.equals(subject.getUsername())) && !isReAuthenticateRequired(securityContext, session)) {
            return subject;
         }

         logout(securityContext, session);
      }

      if (username == null) {
         return null;
      } else {
         ApplicationSecurity as = (ApplicationSecurity)AccessController.doPrivileged(new PrivilegedAction() {
            public ApplicationSecurity run() {
               return securityContext.getAppSecurityProvider();
            }
         });
         subject = as.authenticateAndSaveCredential(username, credential, securityContext.getSecurityRealmName(), request, response);
         if (DEBUG_SEC.isDebugEnabled()) {
            DEBUG_SEC.debug(securityContext.getLogContext() + " authenticated user: " + getUsername(subject));
         }

         return subject;
      }
   }

   protected static String getUsername(SubjectHandle subject) {
      return subject == null ? "anonymous" : subject.getUsername();
   }

   protected void setAuthRealmBanner(String name) {
      this.authRealmBanner = "Basic realm=\"" + name + "\"";
   }

   public static String constructAuthRealmBanner(String name) {
      return "Basic realm=\"" + name + "\"";
   }

   static void logout(ServletSecurityContext context, SessionSecurityData session) {
      if (session != null) {
         context.getSessionRegistry().unregister(session.getInternalId());
         session.removeInternalAttribute("weblogic.authuser");
         session.removeInternalAttribute("weblogic.authuser.associated.data");
      }
   }

   protected final void login(HttpServletRequest request, SubjectHandle subject, SessionSecurityData session) {
      if (subject != null && !subject.isAnonymous() && !subject.isKernel()) {
         if (session == null) {
            session = getUserSession(request, true);
         } else if (!((HttpSession)session).isNew() && this.webAppSecurity.isChangeSessionIdOnReauthentication()) {
            this.getRequestFacade().updateSessionId(request);
         }

         session.setInternalAttribute("weblogic.authuser", WebAppSecurity.getProvider().unwrapSubject(subject));
         String id = session.getInternalId();
         this.securityContext.getSessionRegistry().setUser(id, subject);
         setupAuthCookie(this.securityContext, request, session, id, true);
      }
   }

   public static void setupAuthCookie(ServletSecurityContext context, HttpServletRequest request, SessionSecurityData session, String id) {
      setupAuthCookie(context, request, session, id, false);
   }

   public static void setupAuthCookie(ServletSecurityContext context, HttpServletRequest request, SessionSecurityData session, String id, boolean login) {
      if (context.isAuthCookieEnabled()) {
         if (request.isSecure()) {
            String authCookieID = context.getSessionRegistry().getCookieId(id);
            String wlsAuthCookieName = context.getWLSAuthCookieName();
            if (!login && authCookieID != null) {
               session.setInternalAttribute(wlsAuthCookieName, authCookieID);
            } else {
               if (authCookieID == null) {
                  authCookieID = (String)session.getInternalAttribute(wlsAuthCookieName);
                  if (!login && authCookieID != null) {
                     context.getSessionRegistry().addCookieId(id, authCookieID);
                     return;
                  }
               }

               boolean isExistingAuthCookie = authCookieID != null;
               authCookieID = generateNewId(context);
               session.setInternalAttribute(wlsAuthCookieName, authCookieID);
               if (login && isExistingAuthCookie) {
                  context.removeAllWlsAuthCookies(request, session, id, wlsAuthCookieName);
               }

               Cookie authCookie = new Cookie(wlsAuthCookieName, authCookieID);
               authCookie.setSecure(true);
               authCookie.setMaxAge(-1);
               if (!ignorePluginParamsForCookiePath) {
                  authCookie.setPath(context.getRequestFacade().processProxyPathHeaders(request, context.getCookiePath()));
               } else {
                  authCookie.setPath(context.getCookiePath());
               }

               String domain = context.getCookieDomain();
               if (domain != null) {
                  authCookie.setDomain(domain);
               }

               context.getRequestFacade().addResponseCookie(request, authCookie);
               context.getSessionRegistry().addCookieId(id, authCookieID);
            }
         }
      }
   }

   private static String generateNewId(ServletSecurityContext context) {
      BASE64Encoder be = new BASE64Encoder();
      int authCookieIdLength = context.getWLSAuthCookieIdLength();
      String id = be.encodeBuffer(WebAppSecurity.getProvider().getRandomBytesFromSalt(authCookieIdLength));
      id = id.substring(0, authCookieIdLength);
      id = id.replace('/', '.');
      id = id.replace('+', '-');
      id = id.replace('=', '_');
      return id;
   }

   protected boolean needToCheckAuthCookie(HttpServletRequest request, ServletSecurityContext context, SessionSecurityData session) {
      return request.isSecure() && context.isAuthCookieEnabled() && context.isSessionCookiesEnabled() && this.getAuthCookieId(session, context.getWLSAuthCookieName()) != null;
   }

   protected boolean wlsAuthCookieMissing(HttpServletRequest req, SessionSecurityData session) {
      if (this.getSecurityContext().isAuthCookieEnabled() && req.isSecure()) {
         if (!this.getSecurityContext().isSessionCookiesEnabled()) {
            return false;
         } else {
            String wlsAuthCookieName = this.securityContext.getWLSAuthCookieName();
            String authCookieID = this.getAuthCookieId(session, wlsAuthCookieName);
            if (authCookieID != null && authCookieID != "null") {
               Cookie[] cookies = req.getCookies();
               if (cookies != null) {
                  Cookie[] var6 = cookies;
                  int var7 = cookies.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     Cookie cookie = var6[var8];
                     if (cookie.getName().equals(wlsAuthCookieName) && cookie.getValue().equals(authCookieID)) {
                        return false;
                     }
                  }
               }

               Cookie newResponseCookie = this.getRequestFacade().getResponseCookie(req, wlsAuthCookieName);
               return newResponseCookie == null || !newResponseCookie.getValue().equals(authCookieID);
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private String getAuthCookieId(SessionSecurityData session, String wlsAuthCookieName) {
      if (session == null) {
         return null;
      } else {
         String authCookieID = this.getSecurityContext().getSessionRegistry().getCookieId(session.getInternalId());
         if (authCookieID == null) {
            authCookieID = (String)session.getInternalAttribute(wlsAuthCookieName);
         } else {
            session.setInternalAttribute(wlsAuthCookieName, authCookieID);
         }

         return authCookieID;
      }
   }

   protected void setAuthCookieForReAuth(ServletSecurityContext context, SessionSecurityData session, SecurityModule module) {
      if (this.webAppSecurity.isLastSecurityModule(module) && session != null) {
         context.getSessionRegistry().addCookieId(session.getInternalId(), "null");
         session.setInternalAttribute(context.getWLSAuthCookieName(), "null");
      }

   }

   protected static boolean isReAuthenticateRequired(ServletSecurityContext context, SessionSecurityData session) {
      if (session != null) {
         return context.getSessionRegistry().getCookieId(session.getInternalId()) == "null";
      } else {
         return false;
      }
   }

   void sendForbiddenResponse(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      if (!this.delegateControl) {
         this.sendError(rsp, 403);
      }
   }

   private void sendError(HttpServletResponse rsp, int code) throws IOException {
      rsp.sendError(code);
   }

   void sendUnauthorizedResponse(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      if (!this.delegateControl) {
         this.sendError(rsp, 401);
      }
   }

   public void sendError(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      if (!this.delegateControl) {
         rsp.setHeader("WWW-Authenticate", this.authRealmBanner);
         this.sendUnauthorizedResponse(req, rsp);
      }
   }

   protected boolean isForbidden(ResourceConstraint cons) {
      return this.webAppSecurity.isFullSecurityDelegationRequired() && cons != null && cons.isForbidden();
   }

   public static final SessionSecurityData getUserSession(HttpServletRequest request, boolean createIfNull) {
      SubjectHandle subject = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
         public SubjectHandle run() {
            return WebAppSecurity.getProvider().getCurrentSubject();
         }
      });
      if (!subject.isKernel()) {
         return (SessionSecurityData)request.getSession(createIfNull);
      } else {
         SessionRetrievalAction action = new SessionRetrievalAction(request, createIfNull);
         Throwable e = (Throwable)WebAppSecurity.getProvider().getAnonymousSubject().run((PrivilegedAction)action);
         if (e != null) {
            if (e instanceof ServletNestedRuntimeException) {
               throw (ServletNestedRuntimeException)e;
            } else {
               HTTPSessionLogger.logUnexpectedError(e.getMessage(), e);
               throw new ServletNestedRuntimeException("Failed to retrieve session: " + e.getMessage(), e);
            }
         } else {
            return action.getUserSession();
         }
      }
   }

   private static String[] validateAuthMethods(String s) {
      if (s == null) {
         throw new IllegalArgumentException("NULL auth-method list");
      } else {
         String[] tokens = StringUtils.splitCompletely(s, ", ");

         for(int i = 0; i < tokens.length; ++i) {
            if (!tokens[i].equals("BASIC") && !tokens[i].equals("FORM") && !tokens[i].equals("CLIENT_CERT") && !tokens[i].equals("DIGEST") && !tokens[i].equals("ASSERTION") && !tokens[i].equals("BASIC_ENFORCE") && !tokens[i].equals("BASIC_PLAIN")) {
               throw new IllegalArgumentException("Invalid auth-method list - " + s);
            }

            if ((tokens[i].equals("BASIC") || tokens[i].equals("BASIC_ENFORCE") || tokens[i].equals("BASIC_PLAIN") || tokens[i].equals("FORM")) && i != tokens.length - 1) {
               throw new IllegalArgumentException("Invalid auth-method list - '" + tokens[i] + " ' has to be at the end in '" + s + "'");
            }
         }

         return tokens;
      }
   }

   static SecurityModule createModule(ServletSecurityContext context, WebAppSecurity security, boolean isRecursiveCall) {
      ServerAuthConfig serverAuthConfig = JaspicUtilities.getServerAuthConfig(context, "HttpServlet", getAppContextId(context), security.getJaspicListener());
      if (security.isJaspicEnabled() && serverAuthConfig != null && !isRecursiveCall) {
         return new JaspicSecurityModule(serverAuthConfig, context, security);
      } else {
         ServletContext sc = security.getSecurityContext().getServletContext();
         return sc instanceof WebAppServletContext && ((WebAppServletContext)sc).isJSR375Application() && ((WebAppServletContext)sc).isStopped() ? null : createModule(context, security, false, getAuthMethod(security));
      }
   }

   private static String getAuthMethod(WebAppSecurity security) {
      String authMethod = security.getAuthMethod();
      if (authMethod == null || authMethod.length() < 1) {
         authMethod = "BASIC";
      }

      return authMethod;
   }

   public static String getAppContextId(ServletSecurityContext ctx) {
      return ctx.getServletContext().getVirtualServerName() + " " + ctx.getServletContext().getContextPath();
   }

   public static void signPrincipals(Subject subject, WebAppSecurity webAppSecurity) {
      SecurityServices securityServices = webAppSecurity.getJaspicSecurityServices();
      securityServices.signPrincipals(subject.getPrincipals());
   }

   public static void setAuthType(MessageInfo messageInfo, WebAppSecurity webAppSecurity) {
      String authType = (String)messageInfo.getMap().get("javax.servlet.http.authType");
      if (authType != null) {
         webAppSecurity.setAuthMethod(authType);
      } else if (webAppSecurity.getCachedAuthType() != null) {
         webAppSecurity.setAuthMethod(webAppSecurity.getCachedAuthType());
      } else {
         webAppSecurity.setAuthMethod("Authtype_Unspecified");
      }

   }

   static SecurityModule createModule(ServletSecurityContext context, WebAppSecurity security, boolean isControlled, String authMethod) {
      Object module;
      if (authMethod.equals("BASIC")) {
         module = new BasicSecurityModule(context, security, isControlled);
      } else if (authMethod.equals("FORM")) {
         module = new FormSecurityModule(context, security);
      } else if (authMethod.equals("CLIENT_CERT")) {
         module = new CertSecurityModule(context, security, isControlled, false);
      } else if (authMethod.equals("DIGEST")) {
         if (!defaultToBasic) {
            throw new IllegalArgumentException(HTTPLogger.logDigestAuthNotImplementedLoggable(context.getLogContext()).getMessageText());
         }

         HTTPLogger.logDigestAuthNotSupported(context.getLogContext());
         module = new BasicSecurityModule(context, security, isControlled);
      } else if (authMethod.equals("ASSERTION")) {
         module = new CertSecurityModule(context, security, isControlled, true);
      } else if (!authMethod.equals("BASIC_ENFORCE") && !authMethod.equals("BASIC_PLAIN")) {
         if (authMethod.equals("REALM")) {
            module = new ChainedSecurityModule(context, security, validateAuthMethods(context.getRealmAuthMethods()));
         } else {
            module = new ChainedSecurityModule(context, security, validateAuthMethods(authMethod));
         }
      } else {
         module = new Basic2SecurityModule(context, security, isControlled, authMethod);
      }

      ((SecurityModule)module).setAuthRealmBanner(context.getAuthRealmName());
      if (DEBUG_SEC.isDebugEnabled()) {
         DEBUG_SEC.debug(context + " creating " + module);
      }

      return (SecurityModule)module;
   }

   boolean isAuthorized(HttpServletRequest request, HttpServletResponse response, ResourceConstraint constraint, boolean applyAuthFilters) throws IOException, ServletException {
      SessionSecurityData session = getUserSession(request, false);
      boolean authorized = this.checkAccess(request, response, session, constraint, applyAuthFilters);
      if (authorized && session != null) {
         this.getSecurityContext().registerContextPath(session.getInternalId());
      }

      return authorized;
   }

   private static class SessionRetrievalAction implements PrivilegedAction {
      private final HttpServletRequest request;
      private final boolean flag;
      private SessionSecurityData session = null;

      SessionRetrievalAction(HttpServletRequest req, boolean b) {
         this.request = req;
         this.flag = b;
      }

      public SessionSecurityData getUserSession() {
         return this.session;
      }

      public Object run() {
         try {
            this.session = (SessionSecurityData)this.request.getSession(this.flag);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }
}
