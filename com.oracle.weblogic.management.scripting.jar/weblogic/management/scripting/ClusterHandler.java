package weblogic.management.scripting;

import java.util.ArrayList;
import java.util.Locale;
import javax.management.MBeanServerConnection;
import javax.naming.NamingException;
import weblogic.cluster.singleton.ServerMigrationCoordinator;
import weblogic.cluster.singleton.ServerMigrationException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.MigrationException;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.Debug;

public class ClusterHandler {
   private static final String JMS = "jms";
   private static final String JTA = "jta";
   private static final String SERVER = "server";
   private static final String ALL = "all";
   private WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;

   ClusterHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   void migrate(String sourceName, String destinationName, String sourceDown, String destinationDown, String migrationType) throws ScriptException {
      if (migrationType.toLowerCase(Locale.US).equals("jms")) {
         this.doMigration(sourceName, destinationName, sourceDown, destinationDown);
      } else if (migrationType.toLowerCase(Locale.US).equals("server")) {
         this.doManualMigration(sourceName, destinationName, sourceDown, destinationDown);
      } else if (migrationType.toLowerCase(Locale.US).equals("all")) {
         this.doMigrateAll(sourceName, destinationName, sourceDown, destinationDown, false);
      } else if (migrationType.toLowerCase(Locale.US).equals("jta")) {
         this.doMigrateAll(sourceName, destinationName, sourceDown, destinationDown, true);
      }

   }

   void doMigration(String migratableTargetName, String destinationServerName, String sourceDown, String destinationDown) throws ScriptException {
      ManagementTextTextFormatter texter = ManagementTextTextFormatter.getInstance();
      MigratableServiceCoordinatorRuntimeMBean coordinator = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getMigratableServiceCoordinatorRuntime();
      Debug.assertion(coordinator != null, "coordinator must not be null");
      DomainMBean domainConfig = this.ctx.domainRuntimeServiceMBean.getDomainConfiguration();
      Debug.assertion(domainConfig != null, "domainConfig must not be null");
      MigratableTargetMBean migratableTarget = domainConfig.lookupMigratableTarget(migratableTargetName);
      SingletonServiceMBean singletonService = domainConfig.lookupSingletonService(migratableTargetName);
      if (migratableTarget == null && singletonService == null) {
         this.ctx.println(texter.getMigrationUnknownMigratableTarget(migratableTargetName));
      } else {
         ServerMBean destinationServer = domainConfig.lookupServer(destinationServerName);
         if (destinationServer == null) {
            this.ctx.println(texter.getMigrationUnknownDestinationServer(destinationServerName));
         } else {
            boolean found = false;
            int i;
            if (migratableTarget != null) {
               for(i = 0; i < migratableTarget.getAllCandidateServers().length && !found; ++i) {
                  found = migratableTarget.getAllCandidateServers()[i].getName().equals(destinationServer.getName());
               }
            } else {
               for(i = 0; i < singletonService.getAllCandidateServers().length && !found; ++i) {
                  found = singletonService.getAllCandidateServers()[i].getName().equals(destinationServer.getName());
               }
            }

            if (found) {
               this.ctx.println(texter.getMigrationStarted("", migratableTargetName, destinationServerName));

               try {
                  if (singletonService != null) {
                     coordinator.migrateSingleton(singletonService, destinationServer);
                  } else {
                     coordinator.migrate(migratableTarget, destinationServer, !this.getBoolean(sourceDown), !this.getBoolean(destinationDown));
                  }

                  this.ctx.println(this.txtFmt.getMigrationSucceeded());
               } catch (MigrationException var13) {
                  this.ctx.println(texter.getMigrationFailed("", var13.getMessage()));
                  this.ctx.throwWLSTException(this.txtFmt.getMigrationFailed(var13.getMessage()), var13);
               }

            } else {
               if (migratableTarget != null && migratableTarget.getConstrainedCandidateServers().length > 0) {
                  this.ctx.println(texter.getMigrationErrorDestinationNotAmongCandidateServers(destinationServerName, migratableTargetName));
               } else {
                  this.ctx.println(texter.getMigrationErrorDestinationNotAmongClusterMembers(destinationServerName, migratableTargetName));
               }

            }
         }
      }
   }

   void doManualMigration(String serverName, String machineName, String sourceDown, String destinationDown) throws ScriptException {
      try {
         DomainMBean domain = this.ctx.domainRuntimeServiceMBean.getDomainConfiguration();
         ServerMigrationCoordinator coordinator = null;
         this.ctx.println(this.txtFmt.getMigratingSingletonServices(serverName, machineName));

         try {
            coordinator = (ServerMigrationCoordinator)this.ctx.iContext.lookup("weblogic/cluster/singleton/ServerMigrationCoordinator");
         } catch (NamingException var11) {
            this.ctx.throwWLSTException(this.txtFmt.getFailedToGetMigrationCoordinator(), var11);
         } catch (Throwable var12) {
            WLScriptContext var10001 = this.ctx;
            this.ctx.throwWLSTException("Unexpected Error: ", var12);
         }

         ServerMBean serverConfig = domain.lookupServer(serverName);
         ManagementTextTextFormatter fmt;
         if (serverConfig == null) {
            fmt = ManagementTextTextFormatter.getInstance();
            this.ctx.println(fmt.IncorrectMigratableServerName(serverName));
            return;
         }

         if (!serverConfig.isAutoMigrationEnabled()) {
            this.ctx.println(this.txtFmt.getAutoMigrationMustBeEnabled());
         }

         if (serverConfig.getCluster() == null) {
            fmt = ManagementTextTextFormatter.getInstance();
            this.ctx.println(fmt.MigratableServerIsNotInCluster(serverName));
            return;
         }

         MachineMBean destinationMachine = domain.lookupMachine(machineName);
         if (destinationMachine == null) {
            ManagementTextTextFormatter fmt = ManagementTextTextFormatter.getInstance();
            this.ctx.println(fmt.IncorrectDestinationMachine(machineName));
            return;
         }

         try {
            boolean sd = false;
            boolean dd = false;
            if (sourceDown.toLowerCase(Locale.US).equals("true")) {
               sd = true;
            }

            if (destinationDown.toLowerCase(Locale.US).equals("true")) {
               dd = true;
            }

            coordinator.migrate(serverConfig.getName(), serverConfig.getMachine().getName(), destinationMachine.getName(), sd, dd);
         } catch (ServerMigrationException var13) {
            this.migrationFailed(serverConfig.getMachine().getName(), machineName, serverName, var13);
            return;
         }

         this.migrationSucceeded(serverName);
      } catch (Throwable var14) {
         this.ctx.throwWLSTException(this.txtFmt.getFailedMigration(), var14);
      }

   }

   private void migrationSucceeded(String serverName) {
      ManagementTextTextFormatter fmt = ManagementTextTextFormatter.getInstance();
      this.ctx.println(fmt.getMigrationSucceeded(serverName));
   }

   private void migrationFailed(String source, String machineName, String serverName, ServerMigrationException e) {
      ManagementTextTextFormatter fmt = ManagementTextTextFormatter.getInstance();
      switch (e.getStatus()) {
         case -2:
            this.ctx.println(fmt.getMigrationInProgress(serverName));
            break;
         case -1:
         case 0:
         default:
            this.ctx.println(fmt.getMigrationFailed(serverName, e.toString()));
            break;
         case 1:
            this.ctx.println(fmt.getSourceMachineDown(source, serverName));
            break;
         case 2:
            this.ctx.println(fmt.getDestinationMachineDown(machineName));
      }

   }

   private boolean getBoolean(String value) {
      return value.toLowerCase(Locale.US).equals("true");
   }

   void doMigrateAll(String serverName, String destinationName, String sourceDown, String destinationDown) throws ScriptException {
      this.doMigrateAll(serverName, destinationName, sourceDown, destinationDown, false);
   }

   void doMigrateAll(String serverName, String destinationName, String sourceDown, String destinationDown, boolean onlyJta) throws ScriptException {
      ManagementTextTextFormatter texter = ManagementTextTextFormatter.getInstance();
      MBeanServerConnection con = this.ctx.runtimeMSC;
      MigratableServiceCoordinatorRuntimeMBean coordinator = null;
      DomainMBean domainConfig = null;
      boolean _sourceDown = this.getBoolean(sourceDown);
      boolean _destinationDown = this.getBoolean(destinationDown);
      if (!onlyJta) {
         this.ctx.println(this.txtFmt.getMigratingJmsJta(serverName, destinationName));
      } else {
         this.ctx.println(this.txtFmt.getMigratingOnlyJta(serverName, destinationName));
      }

      try {
         coordinator = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().getMigratableServiceCoordinatorRuntime();
         domainConfig = this.ctx.domainRuntimeServiceMBean.getDomainConfiguration();
      } catch (Throwable var19) {
      }

      Debug.assertion(coordinator != null, "coordinator must not be null");
      ServerMBean source = domainConfig.lookupServer(serverName);
      if (source == null) {
         this.ctx.println(this.txtFmt.getCouldNotLocateServer(serverName));
      } else if (source.getCluster() == null) {
         this.ctx.println(this.txtFmt.getServerDoesNotBelongToCluster(serverName));
      } else {
         ServerMBean destinationServer = getServer(domainConfig, destinationName);
         if (destinationServer == null) {
            this.ctx.println(texter.getMigrationUnknownDestinationServer(destinationName));
         } else {
            JTAMigratableTargetMBean jtaMigratable = source.getJTAMigratableTarget();
            if (this.verifyServiceMigration(jtaMigratable, destinationServer, texter, destinationName)) {
               MigratableTargetMBean[] targets = domainConfig.getMigratableTargets();
               ArrayList list = new ArrayList();

               int size;
               for(size = 0; size < targets.length; ++size) {
                  if (targets[size].getUserPreferredServer().getName().equals(serverName)) {
                     if (!this.verifyServiceMigration(targets[size], destinationServer, texter, destinationName)) {
                        return;
                     }

                     list.add(targets[size]);
                  }
               }

               size = list.size();

               try {
                  if (!onlyJta) {
                     for(int i = 0; i < size; ++i) {
                        coordinator.migrate((MigratableTargetMBean)list.get(i), destinationServer, !_sourceDown, !_destinationDown);
                     }
                  }

                  coordinator.migrateJTA(jtaMigratable, destinationServer, !_sourceDown, !_destinationDown);
                  this.ctx.println(this.txtFmt.getMigrationSucceeded());
               } catch (MigrationException var20) {
                  this.ctx.println(texter.getMigrationFailed("", var20.getMessage()));
                  this.ctx.throwWLSTException(this.txtFmt.getMigrationFailed(var20.getMessage()), var20);
               }

            }
         }
      }
   }

   private static ServerMBean getServer(DomainMBean domain, String name) {
      ServerMBean[] servers = domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         if (servers[i].getName().equals(name)) {
            return servers[i];
         }
      }

      return null;
   }

   private boolean verifyServiceMigration(MigratableTargetMBean migratable, ServerMBean destinationServer, ManagementTextTextFormatter texter, String destinationName) {
      boolean found = false;

      for(int i = 0; i < migratable.getAllCandidateServers().length && !found; ++i) {
         found = migratable.getAllCandidateServers()[i].getName().equals(destinationServer.getName());
      }

      if (!found) {
         if (migratable.getConstrainedCandidateServers().length > 0) {
            this.ctx.println(texter.getMigrationErrorDestinationNotAmongCandidateServers(destinationName, migratable.getName()));
         } else {
            this.ctx.println(texter.getMigrationErrorDestinationNotAmongClusterMembers(destinationName, migratable.getName()));
         }
      }

      return true;
   }
}
