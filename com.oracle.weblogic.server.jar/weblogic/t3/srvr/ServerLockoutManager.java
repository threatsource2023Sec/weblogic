package weblogic.t3.srvr;

import java.security.AccessController;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18ntools.L10nLookup;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.ServerResource;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;

public final class ServerLockoutManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final AuthorizationManager am;
   private boolean isLocked = false;
   private String lockedMessage;
   private static final int LOCKSERVER_CODE = 1;
   private static final int UNLOCKSERVER_CODE = 2;
   private static final int SHUTDOWN_CODE = 3;
   private static final int CANCELSHUTDOWN_CODE = 4;

   ServerLockoutManager() {
      this.am = SecurityServiceManager.getAuthorizationManager(kernelId, SecurityServiceManager.getAdministrativeRealmName());
   }

   public void checkServerLock() throws SecurityException {
      if (this.isLocked) {
         throw new SecurityException(this.lockedMessage);
      }
   }

   public String lockServer(String message) throws SecurityException {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      this.simpleCheckSubject(subject, 1);
      T3SrvrLogger.logLockServerRequested(SubjectUtils.getUsername(subject));
      Resource resource = new ServerResource((String)null, ManagementService.getRuntimeAccess(kernelId).getServerName(), "lock");
      if (!this.am.isAccessAllowed(subject, resource, new ResourceIDDContextWrapper(true))) {
         throw new SecurityException("User: '" + SubjectUtils.getUsername(subject) + "' does not have permission to lock server");
      } else {
         this.lockedMessage = message;
         this.isLocked = true;
         String messageid = T3SrvrLogger.logLockServerHappened();
         return this.getLocalMessage(messageid);
      }
   }

   public String unlockServer() throws SecurityException {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      this.simpleCheckSubject(subject, 2);
      T3SrvrLogger.logUnlockServerRequested(SubjectUtils.getUsername(subject));
      Resource resource = new ServerResource((String)null, ManagementService.getRuntimeAccess(kernelId).getServerName(), "unlock");
      if (!this.am.isAccessAllowed(subject, resource, new ResourceIDDContextWrapper(true))) {
         throw new SecurityException("User: '" + SubjectUtils.getUsername(subject) + "' does not have permission to unlock server");
      } else {
         this.isLocked = false;
         this.lockedMessage = null;
         String messageid = T3SrvrLogger.logUnlockServerHappened();
         return this.getLocalMessage(messageid);
      }
   }

   private void simpleCheckSubject(AuthenticatedSubject subject, int command) throws SecurityException {
      if (subject != null) {
         String cname = SubjectUtils.getUsername(subject);
         if (cname == null || cname.trim().length() <= 0) {
            switch (command) {
               case 1:
                  T3SrvrLogger.logNoLockServerNamelessUser();
                  throw new SecurityException("Cannot disable server logins, the request was from a nameless user (Principal)");
               case 2:
                  T3SrvrLogger.logNoUnlockServerNamelessUser();
                  throw new SecurityException("Cannot enable server logins, the request was from a nameless user (Principal)");
               case 3:
               default:
                  T3SrvrLogger.logNoShutdownNamelessUser();
                  throw new SecurityException("Cannot shutdown the server, the request was from a nameless user (Principal)");
               case 4:
                  T3SrvrLogger.logNoCancelShutdownNamelessUser();
                  throw new SecurityException("Cannot cancel the server shutdown, the request was from a nameless user (Principal)");
            }
         }
      } else {
         switch (command) {
            case 1:
               T3SrvrLogger.logNoLockServerNullUser();
               throw new SecurityException("Cannot disable server logins, the request was from a null Principal");
            case 2:
               T3SrvrLogger.logNoUnlockServerNullUser();
               throw new SecurityException("Cannot enable server logins, the request was from a null Principal");
            case 3:
            default:
               T3SrvrLogger.logNoShutdownNullUser();
               throw new SecurityException("Cannot shutdown, the request was from a null Principal");
            case 4:
               T3SrvrLogger.logNoCancelShutdownNullUser();
               throw new SecurityException("Cannot cancel the server shutdown, the request was from a null user (Principal)");
         }
      }
   }

   private String getLocalMessage(String messageIDStr) {
      String localMessage;
      try {
         int messageid = Integer.parseInt(messageIDStr);
         Localizer liz = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18n.T3SrvrLogLocalizer");
         localMessage = (String)liz.getObject("messagebody", messageid);
      } catch (Exception var5) {
         T3SrvrLogger.logLocalizerProblem(messageIDStr, var5);
         localMessage = "A message regarding the status of server shutdown or logins could not be retrieved, messageid " + messageIDStr;
      }

      return localMessage;
   }
}
