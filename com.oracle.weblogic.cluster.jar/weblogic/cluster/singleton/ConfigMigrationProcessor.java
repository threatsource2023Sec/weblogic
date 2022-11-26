package weblogic.cluster.singleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class ConfigMigrationProcessor implements ConfigurationProcessor {
   private static final String MIG_TARGET_TYPE = "MigratableTarget";
   private static final HashMap migTar2ServerMap = new HashMap();
   private static final String CONNECTION_POOL_NAME = "_autogen_SingletonConnPool";
   private static final String JDBC_SYSTEM_RESOURCE = "_autogen_SingletonJDBCSystemResource";

   public static final ConfigMigrationProcessor getInstance() {
      return ConfigMigrationProcessor.SingletonMaker.config;
   }

   private ConfigMigrationProcessor() {
   }

   public void updateConfiguration(DomainMBean dd) throws UpdateException {
      try {
         this.changeJMSServerTargets(dd);
         this.changeJTAMigratableTargets(dd);
      } catch (Exception var3) {
         throw new UpdateException(var3);
      }
   }

   public void setConnectionPoolProperties(String username, String password, String driverName, String url, String properties, DomainMBean domain) throws InvalidAttributeValueException, DistributedManagementException {
      JDBCSystemResourceMBean dataSource = null;
      ClusterMBean[] clusters = domain.getClusters();

      for(int i = 0; i < clusters.length; ++i) {
         if (this.isMigratableCluster(clusters[i])) {
            dataSource = clusters[i].getDataSourceForAutomaticMigration();
            String name = clusters[i].getName() + "_autogen_SingletonJDBCSystemResource";
            if (dataSource == null) {
               dataSource = domain.createJDBCSystemResource(name, name + "-jdbc.xml");
            }

            JDBCDataSourceBean resource = dataSource.getJDBCResource();
            resource.setName(name);
            JDBCDriverParamsBean driverParams = resource.getJDBCDriverParams();
            driverParams.setDriverName(driverName);
            driverParams.setPassword(password);
            driverParams.setUrl(url);
            Properties p = new Properties();
            p.put("user", username);
            String[] jndiNames = new String[]{"_autogen_SingletonConnPool"};
            resource.getJDBCDataSourceParams().setJNDINames(jndiNames);
            JDBCUtil.setDriverProperties(resource, p);
            dataSource.setTargets(new TargetMBean[]{clusters[i]});
            clusters[i].setDataSourceForAutomaticMigration(dataSource);
         }
      }

   }

   public void createMachine(DomainMBean domain, String name, String listenAddress, String listenPort, String type, String serverName) throws InvalidAttributeValueException {
      MachineMBean[] machines = domain.getMachines();

      for(int i = 0; i < machines.length; ++i) {
         if (machines[i].getName().equals(name)) {
            return;
         }
      }

      MachineMBean machine = domain.createMachine(name);
      NodeManagerMBean nodeManager = machine.getNodeManager();
      nodeManager.setListenAddress(listenAddress);
      nodeManager.setListenPort(Integer.parseInt(listenPort));
      if (type.length() > 0) {
         nodeManager.setNMType(type);
      }

      domain.lookupServer(serverName).setMachine(machine);
   }

   public void setCandidateMachinesForAutomaticMigration(DomainMBean domain) {
      ClusterMBean[] clusters = domain.getClusters();

      for(int i = 0; i < clusters.length; ++i) {
         if (this.isMigratableCluster(clusters[i])) {
            this.setCandidateMachines(clusters[i]);
         }
      }

   }

   private void setCandidateMachines(ClusterMBean cluster) {
      ServerMBean[] servers = cluster.getServers();
      HashSet set = new HashSet();

      for(int i = 0; i < servers.length; ++i) {
         MachineMBean machine = servers[i].getMachine();
         if (machine != null) {
            set.add(machine);
         }
      }

      MachineMBean[] machines = new MachineMBean[set.size()];
      cluster.setCandidateMachinesForMigratableServers((MachineMBean[])((MachineMBean[])set.toArray(machines)));
   }

   private boolean isMigratableCluster(ClusterMBean cluster) {
      ServerMBean[] servers = cluster.getServers();

      for(int i = 0; i < servers.length; ++i) {
         if (servers[i].isAutoMigrationEnabled()) {
            return true;
         }
      }

      return false;
   }

   private void changeJMSServerTargets(DomainMBean dd) throws Exception {
      JMSServerMBean[] servers = dd.getJMSServers();

      for(int i = 0; i < servers.length; ++i) {
         TargetMBean[] targets = servers[i].getTargets();

         for(int j = 0; j < targets.length; ++j) {
            String type = targets[j].getType();
            if (type.equals("MigratableTarget")) {
               MigratableTargetMBean migratable = (MigratableTargetMBean)targets[j];
               TargetMBean[] newTargets = new TargetMBean[]{migratable.getUserPreferredServer()};
               servers[i].setTargets(newTargets);
            }
         }
      }

      this.deleteMigratableTargets(dd);
   }

   private void deleteMigratableTargets(DomainMBean dd) {
      ClusterMBean[] clusters = dd.getClusters();

      for(int i = 0; i < clusters.length; ++i) {
         MigratableTargetMBean[] migratables = clusters[i].getMigratableTargets();

         for(int j = 0; j < migratables.length; ++j) {
            this.addMigratableTargetToMap(migratables[j]);
            dd.destroyMigratableTarget(migratables[j]);
         }
      }

   }

   private void changeJTAMigratableTargets(DomainMBean dd) throws Exception {
      ClusterMBean[] clusters = dd.getClusters();

      for(int i = 0; i < clusters.length; ++i) {
         ServerMBean[] servers = clusters[i].getServers();

         for(int j = 0; j < servers.length; ++j) {
            JTAMigratableTargetMBean migratable = servers[j].getJTAMigratableTarget();
            if (!servers[j].isAutoMigrationEnabled()) {
               servers[j].setAutoMigrationEnabled(true);
            }

            this.addMigratableTargetToMap(migratable);
            dd.destroyMigratableTarget(migratable);
         }
      }

   }

   private void addMigratableTargetToMap(MigratableTargetMBean mt) {
      ServerMBean userPreferredSrvr = mt.getUserPreferredServer();
      migTar2ServerMap.put(mt.getName(), userPreferredSrvr.getName());
   }

   static HashMap getMTToServerMapping() {
      return migTar2ServerMap;
   }

   // $FF: synthetic method
   ConfigMigrationProcessor(Object x0) {
      this();
   }

   private static class SingletonMaker {
      private static final ConfigMigrationProcessor config = new ConfigMigrationProcessor();
   }
}
