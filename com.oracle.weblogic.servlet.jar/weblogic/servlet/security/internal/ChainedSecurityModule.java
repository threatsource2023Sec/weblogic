package weblogic.servlet.security.internal;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.spi.SubjectHandle;

final class ChainedSecurityModule extends SecurityModule {
   private final SecurityModule[] modules;

   public ChainedSecurityModule(ServletSecurityContext ctx, WebAppSecurity was, String[] authMethods) {
      super(was, ctx);
      this.modules = this.getSecurityModules(authMethods);
   }

   private SecurityModule[] getSecurityModules(String[] authMethods) {
      if (authMethods != null && authMethods.length != 0) {
         SecurityModule[] mod = new SecurityModule[authMethods.length];

         for(int i = 0; i < authMethods.length; ++i) {
            mod[i] = createModule(this.getSecurityContext(), this.webAppSecurity, i != authMethods.length - 1, authMethods[i]);
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug(this.getSecurityContext() + " ChainedSecuirtyModule adding " + mod[i]);
            }
         }

         return mod;
      } else {
         return null;
      }
   }

   boolean checkAccess(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, boolean applySAF) throws IOException, ServletException {
      SecurityModule lastModule = this.modules[this.modules.length - 1];
      if (!this.webAppSecurity.checkTransport(cons, req, rsp)) {
         return lastModule instanceof FormSecurityModule && lastModule.checkAccess(req, rsp, session, cons, applySAF);
      } else {
         SubjectHandle subject = getCurrentUser(this.getSecurityContext(), req, session);

         for(int i = 0; i < this.modules.length; ++i) {
            if (DEBUG_SEC.isDebugEnabled()) {
               DEBUG_SEC.debug(this.getSecurityContext() + " ChainedSecuirtyModule checking access with " + this.modules[i]);
            }

            if (this.modules.length - 1 == i) {
               if (lastModule instanceof FormSecurityModule) {
                  return lastModule.checkAccess(req, rsp, session, cons, applySAF);
               }

               return lastModule.checkUserPerm(req, rsp, session, cons, subject, applySAF);
            }

            if (!req.getRequestURI().endsWith("/j_security_check") || this.modules[i] instanceof FormSecurityModule) {
               if (this.modules[i].checkUserPerm(req, rsp, session, cons, subject, false)) {
                  return true;
               }

               if (this.getSecurityContext().isInvalidateOnRelogin()) {
                  session = getUserSession(req, false);
               }
            }
         }

         return false;
      }
   }

   protected boolean checkUserPerm(HttpServletRequest req, HttpServletResponse rsp, SessionSecurityData session, ResourceConstraint cons, SubjectHandle subject, boolean applySAF) {
      return false;
   }

   protected void setAuthRealmBanner(String name) {
      super.setAuthRealmBanner(name);
      if (this.modules != null) {
         SecurityModule[] var2 = this.modules;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            SecurityModule m = var2[var4];
            m.setAuthRealmBanner(name);
         }

      }
   }

   boolean isLastChainedSecurityModule(SecurityModule module) {
      return this.modules[this.modules.length - 1] == module;
   }
}
