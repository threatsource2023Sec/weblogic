package weblogic.cluster.singleton;

import weblogic.management.ManagementException;
import weblogic.management.provider.MigrationData;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;

public class ServiceMigrationDataRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServiceMigrationDataRuntimeMBean {
   private static int count;
   private String serviceName;
   private String coordinatorName;
   private String migratedTo;
   private String migratedFrom;
   private String clusterName;
   private int status;
   private long startTime;
   private long endTime;
   private String[] destinationsAttempted;

   ServiceMigrationDataRuntimeMBeanImpl(RuntimeMBean parent, MigrationData data) throws ManagementException {
      super("MigrationData-" + data.getServerName() + data.getMigrationStartTime() + parent.toString() + count++, parent, true);
      this.initialize(data);
   }

   public ServiceMigrationDataRuntimeMBeanImpl(MigrationData data) throws ManagementException {
      super("MigrationData-" + data.getServerName() + data.getMigrationStartTime() + "-adminServer" + count++);
      this.initialize(data);
   }

   void initialize(MigrationData state) {
      this.serviceName = state.getServerName();
      this.migratedFrom = state.getMachineMigratedFrom();
      this.migratedTo = state.getMachineMigratedTo();
      this.coordinatorName = state.getClusterMasterName();
      this.clusterName = state.getClusterName();
      this.status = 1;
      this.startTime = state.getMigrationStartTime();
      this.destinationsAttempted = new String[1];
      this.destinationsAttempted[0] = this.migratedTo;
   }

   public String getServerName() {
      return this.serviceName;
   }

   public int getStatus() {
      return this.status;
   }

   public String getMigratedFrom() {
      return this.migratedFrom;
   }

   public synchronized String[] getDestinationsAttempted() {
      return this.destinationsAttempted;
   }

   public String getMigratedTo() {
      return this.migratedTo;
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

   public String getCoordinatorName() {
      return this.coordinatorName;
   }

   public synchronized void update(MigrationData data) {
      this.migratedFrom = data.getMachineMigratedFrom();
      this.migratedTo = data.getMachineMigratedTo();
      this.status = data.getStatus();
      if (this.status == 1) {
         String[] temp = this.destinationsAttempted;
         this.destinationsAttempted = new String[this.destinationsAttempted.length + 1];
         System.arraycopy(temp, 0, this.destinationsAttempted, 0, temp.length);
         this.destinationsAttempted[this.destinationsAttempted.length - 1] = this.migratedTo;
      }

      this.endTime = data.getMigrationEndTime();
   }
}
