package weblogic.security.internal;

import java.security.AccessController;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.InvalidParameterException;
import weblogic.security.service.NotYetInitializedException;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerWrapper;

public final class UserLockoutManagerMBeanCustomizerImpl {
   private UserLockoutManagerMBean userLockoutManager;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityUserLockout");
   private PrincipalAuthenticator pa;

   public UserLockoutManagerMBeanCustomizerImpl(UserLockoutManagerMBean userLockoutManager) {
      this.userLockoutManager = userLockoutManager;
      if (log.isDebugEnabled()) {
         log.debug("UserLockoutManagerMBeanCustomizerImpl will use common security");
      }

   }

   private UserLockoutManagerMBean getUserLockoutManager() {
      return this.userLockoutManager;
   }

   private boolean initPA() {
      if (this.pa != null) {
         return true;
      } else {
         String realmName = this.getUserLockoutManager().getRealm().getName();
         if (realmName == null && log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl delaying initialization since its realm is not yet available.");
            return false;
         } else {
            try {
               this.pa = SecurityServiceManager.getPrincipalAuthenticator(kernelId, realmName);
            } catch (InvalidParameterException var3) {
            } catch (NotYetInitializedException var4) {
            }

            if (this.pa == null) {
               if (log.isDebugEnabled()) {
                  log.debug("UserLockoutManagerMBeanCustomizerImpl delaying initialization since it cannot get a PrincipalAuthenticator for realm " + realmName + ".");
               }

               return false;
            } else {
               if (log.isDebugEnabled()) {
                  log.debug("UserLockoutManagerMBeanCustomizerImpl initialized for realm " + realmName);
               }

               return true;
            }
         }
      }
   }

   public long getUserLockoutTotalCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getUserLockoutTotalCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getUserLockoutTotalCount returning 0.");
         }

         return 0L;
      }
   }

   public long getInvalidLoginAttemptsTotalCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getInvalidLoginAttemptsTotalCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getInvalidLoginAttemptsTotalCount returning 0.");
         }

         return 0L;
      }
   }

   public long getLoginAttemptsWhileLockedTotalCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getLoginAttemptsWhileLockedTotalCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getLoginAttemptsWhileLockedTotalCount returning 0.");
         }

         return 0L;
      }
   }

   public long getInvalidLoginUsersHighCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getInvalidLoginUsersHighCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getInvalidLoginUsersHighCount returning 0.");
         }

         return 0L;
      }
   }

   public long getUnlockedUsersTotalCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getUnlockedUsersTotalCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getUnlockedUserTotalCount returning 0.");
         }

         return 0L;
      }
   }

   public long getLockedUsersCurrentCount() {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getLockedUsersCurrentCount();
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getLockedUsersCurrentCount returning 0.");
         }

         return 0L;
      }
   }

   public boolean isLockedOut(String userName) {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().isLockedOut(userName);
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. isLockedOut returning false.");
         }

         return false;
      }
   }

   public void clearLockout(String userName) {
      if (this.initPA()) {
         this.pa.getUserLockoutAdministrationService().clearLockout(userName);
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. clearLockout returning.");
         }

      }
   }

   public long getLastLoginFailure(String userName) {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getLastLoginFailure(userName);
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getLastLoginFailure returning 0.");
         }

         return 0L;
      }
   }

   public long getLoginFailureCount(String userName) {
      if (this.initPA()) {
         return this.pa.getUserLockoutAdministrationService().getLoginFailureCount(userName);
      } else {
         if (log.isDebugEnabled()) {
            log.debug("UserLockoutManagerMBeanCustomizerImpl not yet initialized. getLoginFailureCount returning 0.");
         }

         return 0L;
      }
   }
}
