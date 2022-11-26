package weblogic.management.mbeanservers.edit.internal;

import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class PortablePartitionManagerMBeanImpl extends ServiceImpl implements PortablePartitionManagerMBean {
   private final ConfigurationManagerMBean manager;
   private final DomainRuntimeServiceMBean domainRuntimeService;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugPartitionPortability");

   PortablePartitionManagerMBeanImpl(ConfigurationManagerMBean cm) {
      super("PortablePartitionManager", PortablePartitionManagerMBean.class.getName(), (Service)null);
      this.manager = cm;
      this.domainRuntimeService = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
   }

   public ResourceGroupMigrationTaskMBean migrateResourceGroup(TargetMBean target, TargetMBean currentTarget, TargetMBean newTarget, long timeout) {
      ResourceGroupMigrationTaskImpl task = new ResourceGroupMigrationTaskImpl(target, currentTarget, newTarget, timeout, this.domainRuntimeService);
      task.run();
      return new ResourceGroupMigrationTaskMBeanImpl(task);
   }

   public ImportExportPartitionTaskMBean importPartition(String archiveFileName, String partitionName, Boolean createNew, String keyFile) throws Exception {
      ImportExportPartitionTaskImpl task = new ImportExportPartitionTaskImpl(partitionName, archiveFileName, createNew, keyFile, PortablePartitionManagerMBean.Operation.IMPORT_PARTITION, this.manager);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(task));
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("<PortablePartitionManagerImpl> : importPartition  partitionName: " + partitionName + " archiveFileName " + archiveFileName + " createNew " + createNew + " keyFile " + keyFile);
      }

      return new ImportExportPartitionTaskMBeanImpl(task);
   }

   public ImportExportPartitionTaskMBean exportPartition(String partitionName, String expArchPath, Boolean includeAppsNLibs, String keyFile) throws Exception {
      ImportExportPartitionTaskImpl task = new ImportExportPartitionTaskImpl(partitionName, expArchPath, includeAppsNLibs, keyFile, PortablePartitionManagerMBean.Operation.EXPORT_PARTITION, this.manager);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(task));
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("<PortablePartitionManagerImpl> : exportPartition  partitionName: " + partitionName + " expArchPath " + expArchPath + " includeAppsNLibs " + includeAppsNLibs + " keyFile " + keyFile);
      }

      return new ImportExportPartitionTaskMBeanImpl(task);
   }

   public ImportExportPartitionTaskMBean exportPartition(String partitionName, String expArchPath, Boolean includeAppsNLibs) throws Exception {
      return this.exportPartition(partitionName, expArchPath, includeAppsNLibs, (String)null);
   }

   public ImportExportPartitionTaskMBean exportPartition(String partitionName, String expArchPath) throws Exception {
      return this.exportPartition(partitionName, expArchPath, true, (String)null);
   }

   public ImportExportPartitionTaskMBean exportPartition(String partitionName, String expArchPath, String keyFile) throws Exception {
      return this.exportPartition(partitionName, expArchPath, true, keyFile);
   }

   public static final boolean isDebugEnabled() {
      return debugLogger.isDebugEnabled();
   }

   public static void debug(String msg) {
      debugLogger.debug(msg);
   }

   public ImportExportPartitionTaskMBean importPartition(String archiveFileName, Boolean createNew) throws Exception {
      return this.importPartition(archiveFileName, (String)null, createNew, (String)null);
   }

   public ImportExportPartitionTaskMBean importPartition(String archiveFileName, Boolean createNew, String keyFile) throws Exception {
      return this.importPartition(archiveFileName, (String)null, createNew, keyFile);
   }

   public ImportExportPartitionTaskMBean importPartition(String archiveFileName, String partitionName, Boolean createNew) throws Exception {
      return this.importPartition(archiveFileName, partitionName, createNew, (String)null);
   }
}
