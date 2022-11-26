package weblogic.cluster.singleton;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ServerMachineMigrationData;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.collections.ConcurrentHashMap;

class MigratableServersMonitorImpl implements NakedTimerListener, MigratableServiceConstants {
   private final boolean DEBUG = MigrationDebugLogger.isDebugEnabled();
   private final int leaseRenewInterval;
   private Timer timer;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private HashMap serverMachineTable;
   private final ConcurrentHashMap migratableServerMap = new ConcurrentHashMap();
   private ConcurrentHashMap previouslyUnresponsiveServers;
   private TimerManager timerManager = null;
   private final Leasing leasingService;

   MigratableServersMonitorImpl(Leasing leasingService, int leaseRenewInterval) {
      this.leasingService = leasingService;
      this.leaseRenewInterval = leaseRenewInterval;
      this.previouslyUnresponsiveServers = new ConcurrentHashMap();
      this.serverMachineTable = new HashMap();
   }

   void start() {
      this.populateServerMachineTableFromAdminServer();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      MachineMBean[] machines = domain.getMachines();
      ClusterMBean myCluster = domain.lookupServer(LocalServerIdentity.getIdentity().getServerName()).getCluster();
      if (this.DEBUG) {
         this.p("Processing Cluster " + myCluster.getName());
      }

      ServerMBean[] servers = myCluster.getServers();

      for(int i = 0; i < machines.length; ++i) {
         if (this.DEBUG) {
            this.p("Processing Machine " + machines[i].getName());
         }

         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         NodeManagerLifecycleService nmr = generator.getInstance(machines[i]);

         for(int j = 0; j < servers.length; ++j) {
            if (this.DEBUG) {
               this.p("Processing Server[" + j + "] " + servers[j].getName());
            }

            if (!servers[j].isAutoMigrationEnabled()) {
               if (this.DEBUG) {
                  this.p("Skipping server with Auto Migration Diabled " + servers[j].toString());
               }
            } else {
               String state;
               try {
                  state = nmr.getState(servers[j]);
               } catch (IOException var12) {
                  if (this.DEBUG) {
                     this.p("IOException from machine " + machines[i].getName());
                  }
                  break;
               }

               if (state.indexOf("FAILED") == -1 && state.indexOf("SHUTDOWN") == -1 && state.indexOf("UNKNOWN") == -1) {
                  synchronized(this) {
                     if (this.serverMachineTable.get(servers[j].getName()) == null) {
                        ClusterLogger.logMonitoringMigratableServer(servers[j].getName());
                     }

                     this.serverMachineTable.put(servers[j].getName(), machines[i].getName());
                  }

                  if (this.DEBUG) {
                     this.p("Found " + servers[j] + " living on " + machines[i]);
                  }
               } else if (this.DEBUG) {
                  this.p(servers[j] + " is not active on " + machines[i]);
               }
            }
         }
      }

      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("SingletonServiceTimerManager", "weblogic.kernel.System");
      this.timer = this.timerManager.schedule(this, 0L, (long)this.leaseRenewInterval);
   }

   void stop() {
      if (this.timer != null) {
         this.timer.cancel();
      }

   }

   public void timerExpired(Timer timer) {
      String[] servers = this.leasingService.findExpiredLeases();
      ConcurrentHashMap nextMapOfPreviouslyUnresponsiveServers = new ConcurrentHashMap(servers.length * 2);

      for(int i = 0; i < servers.length; ++i) {
         if (!"CLUSTER_MASTER".equals(servers[i]) && !"SINGLETON_MASTER".equals(servers[i])) {
            nextMapOfPreviouslyUnresponsiveServers.put(servers[i], servers[i]);
            this.previouslyUnresponsiveServers.remove(servers[i]);
            MigratableServerState serverState = this.findOrCreateMigratableServerStateInfo(servers[i]);
            if (serverState != null) {
               try {
                  if (this.DEBUG) {
                     this.p("Sending server-unresponsive to " + serverState);
                  }

                  serverState.serverUnresponsive();
               } catch (ServerMigrationException var10) {
                  ClusterLogger.logFailedToAutomaticallyMigrateServers2(serverState.getServer().getName(), var10);
               }
            }
         }
      }

      Set serversThatAreNoLongerUnresponsive = this.previouslyUnresponsiveServers.keySet();
      Iterator iter = serversThatAreNoLongerUnresponsive.iterator();

      while(iter.hasNext()) {
         String serverName = (String)iter.next();
         if (!"CLUSTER_MASTER".equals(serverName)) {
            MigratableServerState serverState = this.findOrCreateMigratableServerStateInfo(serverName);
            if (serverState != null) {
               serverState.markAsMigratableAgain();

               try {
                  if (this.DEBUG) {
                     this.p("Sending server-previouslyUnresponsive to " + serverState);
                  }

                  serverState.serverPreviouslyUnresponsive();
               } catch (ServerMigrationException var9) {
                  ClusterLogger.logFailedToAutomaticallyMigrateServers2(serverState.getServer().getName(), var9);
               }
            }
         }
      }

      this.previouslyUnresponsiveServers = nextMapOfPreviouslyUnresponsiveServers;
   }

   private MigratableServerState findOrCreateMigratableServerStateInfo(String serverName) {
      MigratableServerState info = (MigratableServerState)this.migratableServerMap.get(serverName);
      if (info == null) {
         info = this.createMigratableServerStateInfo(serverName);
         if (info == null) {
            return null;
         }

         this.migratableServerMap.put(serverName, info);
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug(serverName + " failed to renew its lease");
         }
      }

      return info;
   }

   private MigratableServerState createMigratableServerStateInfo(String serverName) {
      String currentMachine = this.getCurrentMachine(serverName);
      if (currentMachine == null) {
         return null;
      } else {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         MachineMBean machine = this.getMachine(domain, currentMachine);
         ServerMBean server = domain.lookupServer(serverName);
         return server != null && machine != null ? new MigratableServerState(server, machine, this) : null;
      }
   }

   public synchronized void setServerLocation(String server, String machine) throws RemoteException {
      if (this.serverMachineTable.get(server) == null) {
         ClusterLogger.logMonitoringMigratableServer(server);
      }

      this.serverMachineTable.put(server, machine);
      if (this.DEBUG) {
         this.p("Got a report: " + server + " is now on " + machine);
      }

   }

   public synchronized String getCurrentMachine(String server) {
      String currentMachine = (String)this.serverMachineTable.get(server);
      return currentMachine != null ? currentMachine : this.getConfiguredMachine(server);
   }

   private String getConfiguredMachine(String serverName) {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
      if (server == null) {
         return null;
      } else {
         return server.getMachine() == null ? null : server.getMachine().getName();
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private DomainMigrationHistory getDomainMigrationHistoryRemote() {
      try {
         String adminServerName = ManagementService.getRuntimeAccess(kernelId).getAdminServerName();
         if (adminServerName == null) {
            return null;
         } else {
            Environment env = new Environment();
            env.setProviderUrl(getURLManagerService().findAdministrationURL(adminServerName));
            DomainMigrationHistory remote = (DomainMigrationHistory)PortableRemoteObject.narrow(env.getInitialReference(DomainMigrationHistoryImpl.class), DomainMigrationHistory.class);
            return remote;
         }
      } catch (UnknownHostException var4) {
         return null;
      } catch (NamingException var5) {
         ClusterLogger.logErrorReportingMigrationRuntimeInfo(var5);
         return null;
      }
   }

   private synchronized void populateServerMachineTableFromAdminServer() {
      try {
         DomainMigrationHistory domainMigrationHistoryRemote = this.getDomainMigrationHistoryRemote();
         if (domainMigrationHistoryRemote != null) {
            ServerMachineMigrationData[] migrations = domainMigrationHistoryRemote.getServerMachineMigrations();

            for(int i = 0; i < migrations.length; ++i) {
               this.serverMachineTable.put(migrations[i].getServerName(), migrations[i].getMachineMigratedTo());
            }

            removeMigratedServersShutdownState(this.serverMachineTable);
         }
      } catch (Exception var4) {
         if (this.DEBUG) {
            this.p("populateServerMachineTableFromAdminServer e: " + var4);
         }
      }

   }

   private static void removeMigratedServersShutdownState(Map _serverMachineTable) {
      ClusterServices clusterServices = Locator.locate();
      if (clusterServices != null) {
         Iterator itr = _serverMachineTable.entrySet().iterator();

         while(true) {
            String state;
            do {
               if (!itr.hasNext()) {
                  return;
               }

               Map.Entry entry = (Map.Entry)itr.next();
               String serverName = (String)entry.getKey();
               String machineName = (String)entry.getValue();
               state = clusterServices.getMigratableServerStateOnMachine(serverName, machineName);
            } while(!"SHUTDOWN".equals(state) && !"FORCE_SHUTTING_DOWN".equals(state));

            itr.remove();
         }
      }
   }

   private void p(Object o) {
      System.out.println("<" + new Date(System.currentTimeMillis()) + "><MigratableServersMonitorImpl> " + o);
   }

   MachineMBean getMachine(String machineName) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return this.getMachine(domain, machineName);
   }

   private MachineMBean getMachine(DomainMBean domain, String machineName) {
      return domain.lookupMachine(machineName);
   }

   public synchronized void removeServerLocation(String serverName) {
      this.serverMachineTable.remove(serverName);
   }
}
