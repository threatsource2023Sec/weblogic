package weblogic.security.securityapi;

import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJBContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.authorization.spi.CallerDetailsResolver;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.principal.WLSUserImpl;

public class WlsCallerDetailsResolver implements CallerDetailsResolver {
   private HttpServletRequest req;

   public WlsCallerDetailsResolver(HttpServletRequest req) {
      this.req = req;
   }

   public Set getAllDeclaredCallerRoles() {
      throw new UnsupportedOperationException();
   }

   public Principal getCallerPrincipal() {
      if (WlsSecurityContextImpl.getCurrentSubject() == null) {
         SecurityLogger.logCurrentSubjectIsNull("getCallerPrincipal");
         return null;
      } else {
         Principal p = SubjectUtils.getUserPrincipal(WlsSecurityContextImpl.getCurrentSubject());
         return p;
      }
   }

   public Set getPrincipalsByType(Class pType) {
      if (pType == null) {
         throw new NullPointerException("A null PrincipalType is not allowed for getPrincipalsByType!");
      } else if (WlsSecurityContextImpl.getCurrentSubject() == null) {
         SecurityLogger.logCurrentSubjectIsNull("getPrincipalsByType");
         return null;
      } else {
         Set principals = WlsSecurityContextImpl.getCurrentSubject().getPrincipals(WLSUserImpl.class);
         Set principalSet = new HashSet();
         if (principals != null && principals.size() > 0) {
            Iterator var4 = principals.iterator();

            while(var4.hasNext()) {
               WLSUserImpl wlsUser = (WLSUserImpl)var4.next();
               if (wlsUser.getOriginalPrincipal() != null && pType.isAssignableFrom(wlsUser.getOriginalPrincipal().getClass())) {
                  principalSet.add(wlsUser.getOriginalPrincipal());
               }
            }
         }

         return principalSet;
      }
   }

   public boolean isCallerInRole(String role) {
      if (this.req != null && this.req.isUserInRole(role)) {
         SecurityLogger.logIsCallerInRole(role, "HttpServletRequest.isUserInRole()");
         return true;
      } else {
         EJBContext ejbContext = getEJBContext();
         if (ejbContext != null && ejbContext.isCallerInRole(role)) {
            SecurityLogger.logIsCallerInRole(role, "EJBContext.isCallerInRole()");
            return true;
         } else {
            return false;
         }
      }
   }

   private static EJBContext getEJBContext() {
      try {
         return (EJBContext)(new InitialContext()).lookup("java:comp/EJBContext");
      } catch (NamingException var1) {
         return null;
      }
   }
}
