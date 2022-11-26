package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface MemberDeathDetector {
   void start();

   void stop();

   String removeMember(String var1);

   public static class ServerMigrationStateValidator {
      public static boolean canMigrateServer(String serverState) {
         return serverState != null && (serverState.equals("FAILED_NOT_RESTARTABLE") || serverState.equals("FAILED_MIGRATABLE"));
      }

      public static boolean canMigrateLease(String serverState) {
         return serverState != null && (serverState.equals("FAILED_NOT_RESTARTABLE") || serverState.equals("FAILED_RESTARTING") || serverState.equals("FAILED") || serverState.equals("ACTIVATE_LATER") || serverState.equals("STARTING"));
      }
   }
}
