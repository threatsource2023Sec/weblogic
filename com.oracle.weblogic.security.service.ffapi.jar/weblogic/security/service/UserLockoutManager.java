package weblogic.security.service;

public interface UserLockoutManager {
   String getName();

   boolean isLockoutEnabled();

   long getLastLoginFailure(String var1);

   long getLoginFailureCount(String var1);

   long getLastLoginFailure(String var1, String var2);

   long getLoginFailureCount(String var1, String var2);

   long getUserLockoutTotalCount();

   long getInvalidLoginAttemptsTotalCount();

   long getLoginAttemptsWhileLockedTotalCount();

   long getInvalidLoginUsersHighCount();

   long getUnlockedUsersTotalCount();

   long getLockedUsersCurrentCount();

   boolean isLockedOut(String var1);

   void clearLockout(String var1);

   boolean isLockedOut(String var1, String var2);

   void clearLockout(String var1, String var2);
}
