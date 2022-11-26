package weblogic.management.rest.lib.utils;

import java.security.AccessController;
import javax.servlet.http.HttpServletRequest;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RDBMSSecurityStoreMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;

public class SecurityUtils {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static AuthorizationManager authorizer;

   public static void checkPermission() {
      SecurityServiceManager.checkKernelPermission();
   }

   public static boolean isFileUploadAllowed(HttpServletRequest request) throws Exception {
      return isAccessAllowed(request, new AdminResource("FileUpload", (String)null, (String)null), new ResourceIDDContextWrapper());
   }

   public static boolean isAccessAllowed(HttpServletRequest request, Resource resource) throws Exception {
      return isAccessAllowed(request, resource, (ContextHandler)null);
   }

   public static boolean isAccessAllowed(HttpServletRequest request, Resource resource, ContextHandler context) throws Exception {
      return getAuthorizer().isAccessAllowed(SecurityServiceManager.getCurrentSubject(kernelId), resource, context);
   }

   public static boolean isSecurityMBean(Object bean) {
      return bean instanceof RealmMBean || bean instanceof ProviderMBean || bean instanceof RDBMSSecurityStoreMBean || bean instanceof UserLockoutManagerMBean;
   }

   private static AuthorizationManager getAuthorizer() throws Exception {
      if (authorizer == null) {
         authorizer = SecurityServiceManager.getAuthorizationManager(kernelId, SecurityServiceManager.getAdministrativeRealmName());
      }

      return authorizer;
   }
}
