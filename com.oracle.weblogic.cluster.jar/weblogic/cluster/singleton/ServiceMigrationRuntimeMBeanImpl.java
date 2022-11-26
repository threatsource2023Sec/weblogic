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
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationRuntimeMBean;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.WorkManagerFactory;

public final class ServiceMigrationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServiceMigrationRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ServiceMigrationRuntimeMBeanImpl singleton;
   private List migrationList = MigrationUtils.createServiceMigrationHistoryList();

   private ServiceMigrationRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super("ServiceMigrationRuntime", parent, true);
   }

   static synchronized void initialize() throws ManagementException {
      RuntimeMBean parent = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getClusterRuntime();
      if (parent != null) {
         if (singleton == null) {
            singleton = new ServiceMigrationRuntimeMBeanImpl(parent);
         }

      }
   }

   public static synchronized ServiceMigrationRuntimeMBeanImpl getInstance() {
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

   public ServiceMigrationDataRuntimeMBean[] getMigrationData() {
      if (this.migrationList.size() == 0) {
         return null;
      } else {
         ServiceMigrationDataRuntimeMBean[] data = new ServiceMigrationDataRuntimeMBean[this.migrationList.size()];
         this.migrationList.toArray(data);
         return data;
      }
   }

   synchronized void migrationCompleted(String name, String from, String to) {
      ServiceMigrationDataRuntimeMBean[] mbeans = this.getMigrationData();
      if (mbeans != null) {
         for(int count = 0; count < mbeans.length; ++count) {
            if (mbeans[count].getServerName().equals(name) && mbeans[count].getStatus() == 1) {
               MigrationData data = new MigrationDataImpl(name, from, to, 0, mbeans[count].getMigrationStartTime());
               ((ServiceMigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
               this.updateAdminServer(data);
               return;
            }
         }
      }

   }

   synchronized void migrationStarted(String name, String from, String to) {
      ServiceMigrationDataRuntimeMBean[] mbeans = this.getMigrationData();
      if (mbeans != null) {
         for(int count = 0; count < mbeans.length; ++count) {
            if (mbeans[count].getServerName().equals(name) && mbeans[count].getStatus() == 1) {
               MigrationData data = new MigrationDataImpl(name, from, to, 1, mbeans[count].getMigrationStartTime());
               ((ServiceMigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
               this.updateAdminServer(data);
               return;
            }
         }
      }

      try {
         MigrationData data = new MigrationDataImpl(name, from, to, 1, System.currentTimeMillis());
         this.updateAdminServer(data);
         this.migrationList.add(new ServiceMigrationDataRuntimeMBeanImpl(this, data));
      } catch (ManagementException var7) {
         ClusterLogger.logErrorReportingMigrationRuntimeInfo(var7);
      } catch (IllegalArgumentException var8) {
         ClusterLogger.logErrorReportingMigrationRuntimeInfo(var8);
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
               DomainMigrationHistory remote = ServiceMigrationRuntimeMBeanImpl.this.getDomainMigrationHistoryRemote(adminServerName);
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

   private static final class MigrationDataImpl implements MigrationData {
      private final String serverName;
      private final String machineMigratedFrom;
      private final String machineMigratedTo;
      private final String clusterMasterName;
      private final String clusterName;
      private int status;
      private long endTime;
      private long startTime;

      MigrationDataImpl(String name, String from, String to, int status, long startTime) {
         this.serverName = name;
         this.machineMigratedFrom = from;
         this.machineMigratedTo = to;
         this.clusterMasterName = ManagementService.getRuntimeAccess(ServiceMigrationRuntimeMBeanImpl.kernelId).getServerName();
         this.clusterName = ManagementService.getRuntimeAccess(ServiceMigrationRuntimeMBeanImpl.kernelId).getServerRuntime().getClusterRuntime().getName();
         this.startTime = startTime;
         this.status = status;
         if (status == 2 || status == 0) {
            this.endTime = System.currentTimeMillis();
         }

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

      public String getMigrationType() {
         return "service";
      }

      public String toString() {
         return this.getServerName() + " " + this.getMachineMigratedFrom() + " " + this.getMachineMigratedTo() + " " + this.getMigrationStartTime() + " " + this.getMigrationType();
      }
   }
}
