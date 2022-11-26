package weblogic.security.securityapi;

import java.net.MalformedURLException;
import java.security.AccessController;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.soteria.authorization.spi.ResourceAccessResolver;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.ResourceBase;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.URLResource;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppContextHandler;

public class WlsResourceAccessResolver implements ResourceAccessResolver {
   private HttpServletRequest req;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String REALM_NAME = SecurityServiceManager.getContextSensitiveRealmName();

   public WlsResourceAccessResolver(HttpServletRequest req) {
      this.req = req;
   }

   public boolean hasAccessToWebResource(String resource, String... methods) {
      if (this.req != null && methods != null) {
         WebAppServletContext ctx = (WebAppServletContext)this.req.getServletContext();

         try {
            if (!ctx.isServletRegistered(resource) && ctx.getResource(resource) == null) {
               return false;
            }
         } catch (MalformedURLException var9) {
            var9.printStackTrace();
            return false;
         }

         String[] var5 = methods;
         int var6 = methods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String method = var5[var7];
            ResourceBase urlResource = new URLResource(ctx.getApplicationId(), this.req.getContextPath(), resource, method, (String)null);
            if (SecurityServiceManager.getAuthorizationManager(KERNEL_ID, REALM_NAME).isAccessAllowed(WlsSecurityContextImpl.getCurrentSubject(), urlResource, new WebAppContextHandler(this.req, (HttpServletResponse)null))) {
               return true;
            }
         }

         return false;
      } else {
         SecurityLogger.logNullArgForCheckAccessToWebResource(resource, Arrays.toString(methods));
         return false;
      }
   }
}
