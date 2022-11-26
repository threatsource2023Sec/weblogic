package weblogic.security.service.internal;

public interface UserLockoutAdministrationService {
   long getLastLoginFailure(String var1);

   long getLoginFailureCount(String var1);

   boolean isLockedOut(String var1);

   void clearLockout(String var1);

   long getLastLoginFailure(String var1, String var2);

   long getLoginFailureCount(String var1, String var2);

   boolean isLockedOut(String var1, String var2);

   void clearLockout(String var1, String var2);

   long getUserLockoutTotalCount();

   long getInvalidLoginAttemptsTotalCount();

   long getLoginAttemptsWhileLockedTotalCount();

   long getInvalidLoginUsersHighCount();

   long getUnlockedUsersTotalCount();

   long getLockedUsersCurrentCount();
}
