package weblogic.servlet.security.internal;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Decoder;

class BasicSecurityModule extends SecurityModule {
   private static final String DISABLE_BASIC_AUTH = "weblogic.servlet.security.disableBasicAuth";

   BasicSecurityModule(ServletSecurityContext ctx, WebAppSecurity was, boolean controller) {
      super(ctx, was, controller);
   }

   boolean postCheckAccess(HttpServletResponse response) throws IOException {
      response.flushBuffer();
      return true;
   }

   protected boolean checkUserPerm(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, SubjectHandle subject, boolean applyAuthFilters) throws IOException, ServletException {
      boolean origUserHasPermission = this.webAppSecurity.hasPermission(req, rsp, subject, cons);
      if (origUserHasPermission) {
         if (this.wlsAuthCookieMissing(req, session)) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("AuthCookie not found - permission denied for " + req);
            }

            this.handleFailure(req, rsp, false);
            this.setAuthCookieForReAuth(this.getSecurityContext(), session, this);
            return false;
         }

         if (this.getRequestFacade().isRequestForProxyServlet(req)) {
            return true;
         }

         if ((!this.enforceValidBasicAuthCredentials() || subject != null && !subject.isAnonymous()) && !isReAuthenticateRequired(this.getSecurityContext(), session)) {
            return true;
         }

         ServletConfig servletConfig = this.getSecurityContext().getServletConfig(req);
         if (this.disableBasicAuthCheck(servletConfig)) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug("BASIC authentication is bypassed for request sent to servlet " + servletConfig.getServletName());
               DEBUG_SEC.debug("The detailed request information is " + req);
            }

            return true;
         }
      }

      boolean forbidden = this.webAppSecurity.isFullSecurityDelegationRequired() && cons != null && cons.isForbidden();
      String[] up = splitAuthHeader(req);
      if (up == null) {
         if (origUserHasPermission) {
            return true;
         } else {
            if (forbidden || subject != null && !this.isReloginEnabled()) {
               this.sendForbiddenResponse(req, rsp);
            } else {
               this.handleFailure(req, rsp, applyAuthFilters);
            }

            return false;
         }
      } else {
         SubjectHandle user = checkAuthenticate(this.getSecurityContext(), req, rsp, up[0], up[1], false);
         if (user == null) {
            if (forbidden || subject != null && !this.isReloginEnabled()) {
               this.sendForbiddenResponse(req, rsp);
            } else {
               this.handleFailure(req, rsp, applyAuthFilters);
            }

            return false;
         } else if (this.webAppSecurity.hasPermission(req, rsp, user, cons)) {
            if (this.wlsAuthCookieMissing(req, session)) {
               if (DEBUG_SEC.isDebugEnabled()) {
                  DEBUG_SEC.debug("AuthCookie not found - permission denied for " + req);
               }

               this.handleFailure(req, rsp, false);
               this.setAuthCookieForReAuth(this.getSecurityContext(), session, this);
               return false;
            } else {
               if (DEBUG_SEC.isDebugEnabled()) {
                  DEBUG_SEC.debug(this.getSecurityContext().getLogContext() + ": user: " + getUsername(user) + " has permissions to access " + req);
               }

               this.login(req, user, session);
               return true;
            }
         } else {
            if (!forbidden && this.isReloginEnabled()) {
               this.handleFailure(req, rsp, applyAuthFilters);
            } else {
               this.sendForbiddenResponse(req, rsp);
            }

            return false;
         }
      }
   }

   protected boolean disableBasicAuthCheck(ServletConfig servletConfig) {
      if (servletConfig == null) {
         return false;
      } else {
         ServletContext servletCtx = this.getSecurityContext().getServletContext();
         if (servletCtx == null) {
            return false;
         } else {
            Collection servletNames = (Collection)servletCtx.getAttribute("weblogic.servlet.security.disableBasicAuth");
            return servletNames == null ? false : servletNames.contains(servletConfig.getServletName());
         }
      }
   }

   protected boolean enforceValidBasicAuthCredentials() {
      return WebAppSecurity.getProvider().getEnforceValidBasicAuthCredentials();
   }

   private void handleFailure(HttpServletRequest req, HttpServletResponse rsp, boolean applyAuthFilters) throws IOException, ServletException {
      if (applyAuthFilters && this.webAppSecurity.hasAuthFilters()) {
         this.webAppSecurity.invokeAuthFilterChain(req, rsp);
      } else {
         this.sendError(req, rsp);
      }
   }

   private static String[] splitAuthHeader(HttpServletRequest request) throws IOException {
      String auth = request.getHeader("Authorization");
      if (auth == null) {
         return null;
      } else {
         String[] authStr = StringUtils.split(auth, ' ');
         if (!authStr[0].equalsIgnoreCase("Basic")) {
            return null;
         } else {
            BASE64Decoder base64 = new BASE64Decoder();
            byte[] mydata = base64.decodeBuffer(authStr[1]);
            String[] s = StringUtils.split(new String(mydata), ':');
            String[] result = new String[]{s[0], s[1]};
            return result;
         }
      }
   }
}
