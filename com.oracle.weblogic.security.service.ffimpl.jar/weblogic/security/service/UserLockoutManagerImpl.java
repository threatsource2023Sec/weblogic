package weblogic.security.service;

import com.bea.security.css.CSS;
import java.security.AccessController;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.internal.UserLockoutAdministrationService;
import weblogic.security.shared.LoggerWrapper;

public class UserLockoutManagerImpl implements UserLockoutManager {
   private UserLockoutAdministrationService ulaService = null;
   private String user_lockout_manager_name;
   private boolean lockout_enabled = false;
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityUserLockout");
   private UserLockoutManagerMBean userLockoutManagerMBean;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected void init(RealmServices realmServices, UserLockoutManagerMBean userLockoutManagerMBean) {
      this.userLockoutManagerMBean = userLockoutManagerMBean;

      SecurityServiceRuntimeException ssre;
      try {
         CSS css = realmServices.getCSS();
         this.ulaService = (UserLockoutAdministrationService)css.getService("UserLockoutAdministrationService");
         if (this.ulaService == null) {
            if (log.isDebugEnabled()) {
               log.debug("UserLockoutManager: UserLockoutAdministrationService is null");
            }

            ssre = new SecurityServiceRuntimeException(SecurityLogger.getNullObjectReturned("SecurityServiceManager", "Common UserLockoutAdminstrationService"));
            throw ssre;
         }
      } catch (Exception var5) {
         if (log.isDebugEnabled()) {
            SecurityLogger.logStackTrace(var5);
         }

         ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common UserLockoutAdminstrationService", var5.toString()));
         ssre.initCause(var5);
         throw ssre;
      }

      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager: obtained UserLockoutAdministrationService");
      }

      this.user_lockout_manager_name = userLockoutManagerMBean.getName();
      this.lockout_enabled = userLockoutManagerMBean.isLockoutEnabled();
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager init done");
      }

   }

   public String getName() {
      return this.user_lockout_manager_name;
   }

   public boolean isLockoutEnabled() {
      return this.lockout_enabled;
   }

   public long getLastLoginFailure(String userName) {
      return this.getLastLoginFailure(userName, (String)null);
   }

   public long getLoginFailureCount(String userName) {
      return this.getLoginFailureCount(userName, (String)null);
   }

   public long getLastLoginFailure(String userName, String identityDomain) {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getLastLoginFailure(" + (userName != null ? userName : "null") + " [" + identityDomain + "])");
      }

      return this.ulaService.getLastLoginFailure(userName, identityDomain);
   }

   public long getLoginFailureCount(String userName, String identityDomain) {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getLoginFailureCount(" + (userName != null ? userName : "null") + " [" + identityDomain + "])");
      }

      return this.ulaService.getLoginFailureCount(userName, identityDomain);
   }

   public long getUserLockoutTotalCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getUserLockoutTotalCount");
      }

      return this.ulaService.getUserLockoutTotalCount();
   }

   public long getInvalidLoginAttemptsTotalCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getInvalidLoginAttemptsTotalCount");
      }

      return this.ulaService.getInvalidLoginAttemptsTotalCount();
   }

   public long getLoginAttemptsWhileLockedTotalCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getLoginAttemptsWhileLockedTotalCount");
      }

      return this.ulaService.getLoginAttemptsWhileLockedTotalCount();
   }

   public long getInvalidLoginUsersHighCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getInvalidLoginUsersHighCount");
      }

      return this.ulaService.getInvalidLoginUsersHighCount();
   }

   public long getUnlockedUsersTotalCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getUnlockedUsersTotalCount");
      }

      return this.ulaService.getUnlockedUsersTotalCount();
   }

   public long getLockedUsersCurrentCount() {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager getLockedUsersCurrentCount");
      }

      return this.ulaService.getLockedUsersCurrentCount();
   }

   public boolean isLockedOut(String userName) {
      return this.isLockedOut(userName, (String)null);
   }

   public void clearLockout(String userName) {
      this.clearLockout(userName, (String)null);
   }

   public boolean isLockedOut(String userName, String identityDomain) {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager isLockedOut(" + userName + " [" + identityDomain + "])");
      }

      return this.ulaService.isLockedOut(userName, identityDomain);
   }

   public void clearLockout(String userName, String identityDomain) {
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManager clearLockout(" + userName + " [" + identityDomain + "])");
      }

      this.ulaService.clearLockout(userName, identityDomain);
   }
}
