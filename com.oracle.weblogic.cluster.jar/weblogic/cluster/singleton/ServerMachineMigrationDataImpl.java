package weblogic.cluster.singleton;

import weblogic.management.provider.ServerMachineMigrationData;

public class ServerMachineMigrationDataImpl implements ServerMachineMigrationData {
   protected String serverName;
   protected String machineMigratedTo;

   public ServerMachineMigrationDataImpl() {
   }

   public ServerMachineMigrationDataImpl(String serverName, String machineMigratedTo) {
      this.serverName = serverName;
      this.machineMigratedTo = machineMigratedTo;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getMachineMigratedTo() {
      return this.machineMigratedTo;
   }

   public String toString() {
      return this.serverName + " --> " + this.machineMigratedTo;
   }
}
