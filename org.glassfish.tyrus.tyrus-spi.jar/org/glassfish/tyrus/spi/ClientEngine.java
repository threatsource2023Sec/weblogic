package org.glassfish.tyrus.spi;

public interface ClientEngine {
   UpgradeRequest createUpgradeRequest(TimeoutHandler var1);

   ClientUpgradeInfo processResponse(UpgradeResponse var1, Writer var2, Connection.CloseListener var3);

   void processError(Throwable var1);

   public static enum ClientUpgradeStatus {
      ANOTHER_UPGRADE_REQUEST_REQUIRED,
      UPGRADE_REQUEST_FAILED,
      SUCCESS;
   }

   public interface ClientUpgradeInfo {
      ClientUpgradeStatus getUpgradeStatus();

      Connection createConnection();
   }

   public interface TimeoutHandler {
      void handleTimeout();
   }
}
