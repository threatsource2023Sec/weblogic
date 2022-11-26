package weblogic.security.jaspic.servlet;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.jacc.PolicyContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jaspic.MessageInfoImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.provider.WlsSubjectHandle;
import weblogic.servlet.security.internal.ResourceConstraint;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.ServletSecurityContext;
import weblogic.servlet.security.internal.SessionSecurityData;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.ApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;

public class JaspicSecurityModule extends SecurityModule {
   private static final String KEY_MUST_AUTHENTICATE = "javax.security.auth.message.MessagePolicy.isMandatory";
   private static final String KEY_CURRENT_USER = "com.oracle.weblogic.servlet.current_subject";
   private static final String MESSAGE_INFO = "__javax.security.auth.message.MessageInfo";
   private ServerAuthSupport samSupport = new ServerAuthSupport() {
      public String getRealmBanner() {
         return JaspicSecurityModule.this.authRealmBanner;
      }

      public String getErrorPage(int code) {
         return JaspicSecurityModule.this.getSecurityContext().getErrorPage(code);
      }

      public boolean isEnforceBasicAuth() {
         return WebAppSecurity.getProvider().getEnforceValidBasicAuthCredentials();
      }
   };
   private ServerAuthConfig serverConfig;
   private static final String OPTION_POLICY_CONTEXT = "javax.security.jacc.PolicyContext";

   public JaspicSecurityModule(ServerAuthConfig serverAuthConfig, ServletSecurityContext ctx, WebAppSecurity was) {
      super(ctx, was, false);
      this.setAuthRealmBanner(ctx.getAuthRealmName());
      this.serverConfig = serverAuthConfig;
   }

   public static Map createOptionsMap(ServerAuthSupport samSupport) {
      Map map = new HashMap();
      map.put("com.oracle.weblogic.servlet.auth_support", samSupport);
      map.put("javax.security.jacc.PolicyContext", PolicyContext.getContextID());
      return map;
   }

   protected boolean checkUserPerm(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, SubjectHandle originalSubject, boolean applyAuthFilters) throws IOException, ServletException {
      if (this.getRequestFacade().isRequestForProxyServlet(req)) {
         return true;
      } else {
         if (this.wlsAuthCookieMissing(req, session)) {
            originalSubject = null;
         }

         MessageInfoImpl messageInfo = new MessageInfoImpl(req, rsp, createMap(this.webAppSecurity, req, rsp, originalSubject, this.getSecurityContext(), cons));
         Subject subject = new Subject();
         boolean registerSessionFlag = false;

         try {
            String authContextID = this.serverConfig.getAuthContextID(messageInfo);
            ServerAuthContext serverContext = this.serverConfig.getAuthContext(authContextID, (Subject)null, createOptionsMap(this.samSupport));
            if (session != null && session.getInternalAttribute("weblogic.authuser") != null && originalSubject != null && originalSubject instanceof WlsSubjectHandle) {
               subject = ((AuthenticatedSubject)WebAppSecurity.getProvider().unwrapSubject(originalSubject)).getSubject();
            } else {
               AuthStatus status = serverContext.validateRequest(messageInfo, subject, (Subject)null);
               if (status != AuthStatus.SUCCESS) {
                  return false;
               }

               setAuthType(messageInfo, this.webAppSecurity);
               String registerSession = null;
               registerSession = (String)messageInfo.getMap().get("javax.servlet.http.registerSession");
               if (registerSession != null && Boolean.valueOf(registerSession)) {
                  registerSessionFlag = true;
               }

               req.setAttribute("__javax.security.auth.message.MessageInfo", messageInfo);
               signPrincipals(subject, this.webAppSecurity);
            }

            SubjectHandle updatedSubject = this.webAppSecurity.toSubjectHandle(subject);
            if (!updatedSubject.isAnonymous()) {
               if (registerSessionFlag) {
                  session = (SessionSecurityData)req.getSession(false);
                  this.login(req, updatedSubject, session);
               } else if (req instanceof ServletRequestImpl) {
                  ((ServletRequestImpl)req).setCurrentSubject(updatedSubject);
               }
            }

            if (!this.webAppSecurity.hasPermission(req, rsp, updatedSubject, cons)) {
               rsp.sendError(403, this.getSecurityContext().getErrorPage(403));
               serverContext.secureResponse(messageInfo, subject);
               return false;
            } else {
               return true;
            }
         } catch (AuthException var14) {
            rsp.setStatus(500);
            throw new ServletException(var14);
         }
      }
   }

   protected boolean postInvoke(HttpServletRequest request, HttpServletResponse response, SubjectHandle subjectHandle) throws ServletException {
      boolean result = false;
      Subject subject = this.webAppSecurity.toSubject(subjectHandle);
      MessageInfoImpl messageInfo = (MessageInfoImpl)request.getAttribute("__javax.security.auth.message.MessageInfo");

      try {
         if (messageInfo != null) {
            String authContextID = this.serverConfig.getAuthContextID(messageInfo);
            ServerAuthContext serverContext = this.serverConfig.getAuthContext(authContextID, (Subject)null, createOptionsMap(this.samSupport));
            return AuthStatus.SEND_SUCCESS == serverContext.secureResponse(messageInfo, subject);
         } else {
            return result;
         }
      } catch (AuthException var9) {
         throw new ServletException(var9);
      }
   }

   protected HttpServletRequest getWrappedRequest(HttpServletRequest request) throws ServletException {
      MessageInfo messageInfo = (MessageInfo)request.getAttribute("__javax.security.auth.message.MessageInfo");

      try {
         if (messageInfo != null) {
            HttpServletRequest wrappedRequest = (HttpServletRequest)messageInfo.getRequestMessage();
            if (wrappedRequest != null && !request.equals(wrappedRequest)) {
               return wrappedRequest;
            }
         }

         return null;
      } catch (Exception var4) {
         throw new ServletException(var4);
      }
   }

   protected HttpServletResponse getWrappedResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      MessageInfo messageInfo = (MessageInfo)request.getAttribute("__javax.security.auth.message.MessageInfo");

      try {
         if (messageInfo != null) {
            HttpServletResponse wrappedResponse = (HttpServletResponse)messageInfo.getResponseMessage();
            if (wrappedResponse != null && !response.equals(wrappedResponse)) {
               return wrappedResponse;
            }
         }

         return null;
      } catch (Exception var5) {
         throw new ServletException(var5);
      }
   }

   public static Map createMap(WebAppSecurity webAppSecurityLocal, HttpServletRequest req, HttpServletResponse rsp, SubjectHandle originalSubject, final ServletSecurityContext securityContextLocal, ResourceConstraint cons) {
      Map map = new HashMap();
      if (mustAuthenticate(webAppSecurityLocal, req, rsp, (SubjectHandle)null, cons)) {
         map.put("javax.security.auth.message.MessagePolicy.isMandatory", "true");
      }

      if (originalSubject != null && !originalSubject.isAnonymous()) {
         Subject subject = new Subject();
         ApplicationSecurity as = (ApplicationSecurity)AccessController.doPrivileged(new PrivilegedAction() {
            public ApplicationSecurity run() {
               return securityContextLocal.getAppSecurityProvider();
            }
         });
         as.populateSubject(subject, originalSubject);
         map.put("com.oracle.weblogic.servlet.current_subject", subject);
      }

      return map;
   }

   private static boolean mustAuthenticate(WebAppSecurity webAppSecurityLocal, HttpServletRequest req, HttpServletResponse rsp, SubjectHandle originalSubject, ResourceConstraint cons) {
      return !webAppSecurityLocal.hasPermission(req, rsp, originalSubject, cons);
   }
}
