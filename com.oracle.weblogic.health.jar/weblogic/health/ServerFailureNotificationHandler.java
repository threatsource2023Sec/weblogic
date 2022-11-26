package weblogic.health;

public interface ServerFailureNotificationHandler {
   void failed(String var1);

   void failedForceShutdown(String var1);

   void exitImmediately(Throwable var1);

   void haltImmediately();
}
