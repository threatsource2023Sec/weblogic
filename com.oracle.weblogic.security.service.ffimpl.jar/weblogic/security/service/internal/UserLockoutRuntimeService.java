package weblogic.security.service.internal;

public interface UserLockoutRuntimeService {
   boolean isLocked(String var1);

   void logFailure(String var1);

   void logSuccess(String var1);

   boolean isLocked(String var1, String var2);

   void logFailure(String var1, String var2);

   void logSuccess(String var1, String var2);
}
