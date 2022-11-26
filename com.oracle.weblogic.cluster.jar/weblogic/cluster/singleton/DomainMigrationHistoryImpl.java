package weblogic.cluster.singleton;

import java.security.AccessController;
import java.util.List;
import weblogic.cluster.ClusterLogger;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.ManagementException;
import weblogic.management.provider.DomainMigrationHistory;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.MigrationData;
import weblogic.management.provider.ServerMachineMigrationData;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DomainMigrationHistoryImpl implements DomainMigrationHistory, BeanUpdateListener {
   private List serverMigrationList = MigrationUtils.createServerMigrationHistoryList();
   private List serviceMigrationList = MigrationUtils.createServiceMigrationHistoryList();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private DomainMigrationHistoryImpl() {
      ManagementService.getRuntimeAccess(kernelId).getDomain().addBeanUpdateListener(this);
   }

   public synchronized void update(MigrationData data) {
      int count;
      if (data.getMigrationType().equals("server")) {
         MigrationDataRuntimeMBean[] mbeans = this.getMigrationDataRuntimes();
         if (mbeans != null) {
            for(count = 0; count < mbeans.length; ++count) {
               if (mbeans[count].getServerName().equals(data.getServerName()) && mbeans[count].getMigrationStartTime() == data.getMigrationStartTime()) {
                  ((MigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
                  return;
               }
            }
         }

         try {
            this.serverMigrationList.add(new MigrationDataRuntimeMBeanImpl(data));
         } catch (ManagementException var6) {
            ClusterLogger.logErrorUpdatingMigrationRuntimeInfo(var6);
         }
      } else if (data.getMigrationType().equals("service")) {
         ServiceMigrationDataRuntimeMBean[] mbeans = this.getServiceMigrationDataRuntimes();
         if (mbeans != null) {
            for(count = 0; count < mbeans.length; ++count) {
               if (mbeans[count].getServerName().equals(data.getServerName()) && mbeans[count].getMigrationStartTime() == data.getMigrationStartTime()) {
                  ((ServiceMigrationDataRuntimeMBeanImpl)mbeans[count]).update(data);
                  return;
               }
            }
         }

         try {
            this.serviceMigrationList.add(new ServiceMigrationDataRuntimeMBeanImpl(data));
         } catch (ManagementException var4) {
            ClusterLogger.logErrorUpdatingMigrationRuntimeInfo(var4);
         } catch (IllegalArgumentException var5) {
            ClusterLogger.logErrorUpdatingMigrationRuntimeInfo(var5);
         }
      } else {
         ClusterLogger.logUnknownMigrationDataType(data.getMigrationType());
      }

   }

   public synchronized MigrationDataRuntimeMBean[] getMigrationDataRuntimes() {
      if (this.serverMigrationList.size() == 0) {
         return null;
      } else {
         MigrationDataRuntimeMBean[] data = new MigrationDataRuntimeMBean[this.serverMigrationList.size()];
         this.serverMigrationList.toArray(data);
         return data;
      }
   }

   public synchronized ServerMachineMigrationData[] getServerMachineMigrations() {
      ServerMachineMigrationData[] data = new ServerMachineMigrationData[this.serverMigrationList.size()];

      for(int i = 0; i < this.serverMigrationList.size(); ++i) {
         data[i] = ((MigrationDataRuntimeMBeanImpl)this.serverMigrationList.get(i)).toServerMachineMigrationData();
      }

      return data;
   }

   public synchronized ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes() {
      if (this.serviceMigrationList.size() == 0) {
         return null;
      } else {
         ServiceMigrationDataRuntimeMBean[] data = new ServiceMigrationDataRuntimeMBean[this.serviceMigrationList.size()];
         this.serviceMigrationList.toArray(data);
         return data;
      }
   }

   static DomainMigrationHistoryImpl createInstance() {
      return new DomainMigrationHistoryImpl();
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      BeanUpdateEvent.PropertyUpdate[] var3 = updates;
      int var4 = updates.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BeanUpdateEvent.PropertyUpdate update = var3[var5];
         List list;
         if (update.getPropertyName().equals("ServiceMigrationHistorySize")) {
            list = MigrationUtils.createServiceMigrationHistoryList();
            list.addAll(this.serviceMigrationList);
            this.serviceMigrationList = list;
         } else if (update.getPropertyName().equals("ServerMigrationHistorySize")) {
            list = MigrationUtils.createServerMigrationHistoryList();
            list.addAll(this.serverMigrationList);
            this.serverMigrationList = list;
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }
}
