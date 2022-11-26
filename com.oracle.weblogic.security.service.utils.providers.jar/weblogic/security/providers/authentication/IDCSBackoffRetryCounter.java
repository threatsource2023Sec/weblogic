package weblogic.security.providers.authentication;

public interface IDCSBackoffRetryCounter {
   int getRetryCount();

   void increaseRetryCount(int var1);

   int getSuccessfulBackoffs();

   void increaseSuccessfulBackoffs();

   int getFailedBackoffs();

   void increaseFailedBackoffs();

   IDCSBackoffRetryCounter reset();
}
