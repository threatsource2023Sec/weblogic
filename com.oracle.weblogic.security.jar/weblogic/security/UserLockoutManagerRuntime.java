package weblogic.security;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.UserLockoutManagerRuntimeMBean;
import weblogic.security.service.UserLockoutManager;

public final class UserLockoutManagerRuntime extends RuntimeMBeanDelegate implements UserLockoutManagerRuntimeMBean {
   private UserLockoutManager userLockoutManager;

   public UserLockoutManagerRuntime(UserLockoutManager userLockoutManager, RealmRuntimeMBean realmRuntime) throws ManagementException {
      super(userLockoutManager.getName(), realmRuntime);
      this.userLockoutManager = userLockoutManager;
   }

   public long getUserLockoutTotalCount() {
      return this.userLockoutManager.getUserLockoutTotalCount();
   }

   public long getInvalidLoginAttemptsTotalCount() {
      return this.userLockoutManager.getInvalidLoginAttemptsTotalCount();
   }

   public long getLoginAttemptsWhileLockedTotalCount() {
      return this.userLockoutManager.getLoginAttemptsWhileLockedTotalCount();
   }

   public long getInvalidLoginUsersHighCount() {
      return this.userLockoutManager.getInvalidLoginUsersHighCount();
   }

   public long getUnlockedUsersTotalCount() {
      return this.userLockoutManager.getUnlockedUsersTotalCount();
   }

   public long getLockedUsersCurrentCount() {
      return this.userLockoutManager.getLockedUsersCurrentCount();
   }

   public boolean isLockedOut(String userName) {
      return this.userLockoutManager.isLockedOut(userName);
   }

   public void clearLockout(String userName) {
      this.userLockoutManager.clearLockout(userName);
   }

   public long getLastLoginFailure(String userName) {
      return this.userLockoutManager.getLastLoginFailure(userName);
   }

   public long getLoginFailureCount(String userName) {
      return this.userLockoutManager.getLoginFailureCount(userName);
   }

   public boolean isLockedOut(String userName, String identityDomain) {
      return this.userLockoutManager.isLockedOut(userName, identityDomain);
   }

   public void clearLockout(String userName, String identityDomain) {
      this.userLockoutManager.clearLockout(userName, identityDomain);
   }

   public long getLastLoginFailure(String userName, String identityDomain) {
      return this.userLockoutManager.getLastLoginFailure(userName, identityDomain);
   }

   public long getLoginFailureCount(String userName, String identityDomain) {
      return this.userLockoutManager.getLoginFailureCount(userName, identityDomain);
   }
}
