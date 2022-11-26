package weblogic.cluster.singleton;

import weblogic.management.ManagementException;
import weblogic.management.provider.MigrationData;
import weblogic.management.provider.ServerMachineMigrationData;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class MigrationDataRuntimeMBeanImpl extends RuntimeMBeanDelegate implements MigrationDataRuntimeMBean {
   private static int count;
   private String serverName;
   private String clusterMasterName;
   private String machineMigratedTo;
   private String machineMigratedFrom;
   private String clusterName;
   private int status;
   private long startTime;
   private long endTime;
   private String[] machinesAttempted;

   MigrationDataRuntimeMBeanImpl(RuntimeMBean parent, MigrationData data) throws ManagementException {
      super("MigrationData-" + data.getServerName() + data.getMigrationStartTime(), parent, true);
      this.initialize(data);
   }

   public MigrationDataRuntimeMBeanImpl(MigrationData data) throws ManagementException {
      super("MigrationData-" + data.getServerName() + data.getMigrationStartTime());
      this.initialize(data);
   }

   void initialize(MigrationData state) {
      this.serverName = state.getServerName();
      this.machineMigratedFrom = state.getMachineMigratedFrom();
      this.machineMigratedTo = state.getMachineMigratedTo();
      this.clusterMasterName = state.getClusterMasterName();
      this.clusterName = state.getClusterName();
      this.status = 1;
      this.startTime = state.getMigrationStartTime();
      this.machinesAttempted = new String[1];
      this.machinesAttempted[0] = this.machineMigratedTo;
   }

   private static synchronized int getCount() {
      return count++;
   }

   public String getServerName() {
      return this.serverName;
   }

   public int getStatus() {
      return this.status;
   }

   public String getMachineMigratedFrom() {
      return this.machineMigratedFrom;
   }

   public synchronized String[] getMachinesAttempted() {
      return this.machinesAttempted;
   }

   public String getMachineMigratedTo() {
      return this.machineMigratedTo;
   }

   public long getMigrationStartTime() {
      return this.startTime;
   }

   public long getMigrationEndTime() {
      return this.endTime;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public String getClusterMasterName() {
      return this.clusterMasterName;
   }

   public synchronized void update(MigrationData data) {
      this.machineMigratedFrom = data.getMachineMigratedFrom();
      this.machineMigratedTo = data.getMachineMigratedTo();
      this.status = data.getStatus();
      if (this.status == 1) {
         String[] temp = this.machinesAttempted;
         this.machinesAttempted = new String[this.machinesAttempted.length + 1];
         System.arraycopy(temp, 0, this.machinesAttempted, 0, temp.length);
         this.machinesAttempted[this.machinesAttempted.length - 1] = this.machineMigratedTo;
      }

      this.endTime = data.getMigrationEndTime();
   }

   public ServerMachineMigrationData toServerMachineMigrationData() {
      return new ServerMachineMigrationDataImpl(this.serverName, this.machineMigratedTo);
   }
}
