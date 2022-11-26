package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.List;
import javax.naming.NamingException;
import weblogic.cluster.ClusterLogger;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.MigrationData;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerMigrationRuntimeMBean;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.work.WorkManagerFactory;

public final class ServerMigrationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServerMigrationRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ServerMigrationRuntimeMBeanImpl singleton;
   private List migrationList = MigrationUtils.createServerMigrationHistoryList();

   private ServerMigrationRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super("ServerMigrationRuntime", parent, true);
   }

   static synchronized void initialize() throws ManagementException {
      RuntimeMBean parent = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getClusterRuntime();
      if (parent != null) {
         if (singleton == null) {
            singleton = new ServerMigrationRuntimeMBeanImpl(parent);
         }

      }
   }

   public static synchronized ServerMigrationRuntimeMBeanImpl getInstance() {
      Debug.assertion(singleton != null, "Cannot use ServerMigrationRuntimeMBeanImpl without initialization");
      return singleton;
   }

   public boolean isClusterMaster() {
      return MigratableServerService.theOne().isClusterMaster();
   }

   public String getClusterMasterName() throws ManagementException {
      try {
         return MigratableServerService.theOne().findClusterMaster();
      } catch (LeasingException var2) {
         throw new ManagementException("Unable to determine ClusterMaster due to " + var2.getMessage());
      }
   }

   public MigrationDataRuntimeMBean[] getMigrationData() {
      if (this.migrationList.size() == 0) {
         return null;
      } else {
         MigrationDataRuntimeMBean[] data = new MigrationDataRuntimeMBean[this.migrationList.size()];
         this.migrationList.toArray(data);
         return data;
      }
   }

   void migrationCompleted(MigratableServerState state) {
      MigrationDataRuntimeMBean[] mbeans = this.getMigrationData();
      if (mbeans != null) {
         for(int count = 0; count < mbeans.length; ++count) {
            if (mbeans[count].getServerName().equals(state.getServer().getName()) && mbeans[count].getStatus() == 1) {
               MigrationData data = new MigrationDataImpl(state, 0, mbeans[count].getMigrationStartTime());
               ((MigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
               this.updateAdminServer(data);
               return;
            }
         }
      }

   }

   void migrationStarted(MigratableServerState migratableServerState) {
      MigrationDataRuntimeMBean[] mbeans = this.getMigrationData();
      if (mbeans != null) {
         for(int count = 0; count < mbeans.length; ++count) {
            if (mbeans[count].getServerName().equals(migratableServerState.getServer().getName()) && mbeans[count].getStatus() == 1) {
               MigrationData data = new MigrationDataImpl(migratableServerState, 1, mbeans[count].getMigrationStartTime());
               ((MigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
               this.updateAdminServer(data);
               return;
            }
         }
      }

      try {
         MigrationData data = new MigrationDataImpl(migratableServerState, 1, System.currentTimeMillis());
         this.updateAdminServer(data);
         this.migrationList.add(new MigrationDataRuntimeMBeanImpl(this, data));
      } catch (ManagementException var5) {
         ClusterLogger.logErrorReportingMigrationRuntimeInfo(var5);
      }

   }

   private void updateAdminServer(final MigrationData data) {
      try {
         final String adminServerName = ManagementService.getRuntimeAccess(kernelId).getAdminServerName();
         if (adminServerName == null) {
            return;
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               DomainMigrationHistory remote = ServerMigrationRuntimeMBeanImpl.this.getDomainMigrationHistoryRemote(adminServerName);
               if (remote != null) {
                  remote.update(data);
               }
            }
         });
      } catch (RemoteRuntimeException var3) {
      }

   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private DomainMigrationHistory getDomainMigrationHistoryRemote(String adminServerName) {
      try {
         Environment env = new Environment();
         env.setProviderUrl(getURLManagerService().findAdministrationURL(adminServerName));
         DomainMigrationHistory remote = (DomainMigrationHistory)PortableRemoteObject.narrow(env.getInitialReference(DomainMigrationHistoryImpl.class), DomainMigrationHistory.class);
         return remote;
      } catch (UnknownHostException var4) {
         return null;
      } catch (NamingException var5) {
         ClusterLogger.logErrorReportingMigrationRuntimeInfo(var5);
         return null;
      }
   }

   private static final class MigrationDataImpl extends ServerMachineMigrationDataImpl implements MigrationData {
      private final String machineMigratedFrom;
      private final String clusterMasterName;
      private final String clusterName;
      private int status;
      private long endTime;
      private long startTime;

      MigrationDataImpl(MigratableServerState state, int status, long startTime) {
         this.serverName = state.getServer().getName();
         if (state.getPreviousMachine() != null) {
            this.machineMigratedFrom = state.getPreviousMachine().getName();
         } else {
            this.machineMigratedFrom = null;
         }

         this.machineMigratedTo = state.getCurrentMachine().getName();
         this.clusterMasterName = ManagementService.getRuntimeAccess(ServerMigrationRuntimeMBeanImpl.kernelId).getServerName();
         this.clusterName = ManagementService.getRuntimeAccess(ServerMigrationRuntimeMBeanImpl.kernelId).getServerRuntime().getClusterRuntime().getName();
         this.startTime = startTime;
         this.status = status;
         if (status == 2 || status == 0) {
            this.endTime = System.currentTimeMillis();
         }

      }

      public int getStatus() {
         return this.status;
      }

      public String getMachineMigratedFrom() {
         return this.machineMigratedFrom;
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

      public String getMigrationType() {
         return "server";
      }
   }
}
