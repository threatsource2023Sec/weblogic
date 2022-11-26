package weblogic.management.mbeanservers.edit.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import weblogic.management.internal.ImportExportLogger;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.provider.internal.ImportExportHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class ImportExportPartitionTaskImpl implements ImportExportPartitionTask, Runnable {
   private String partitionName;
   private String archiveFileName;
   private Boolean createNew;
   private Boolean includeAppsNLibs;
   private String keyFile;
   private PortablePartitionManagerMBean.Operation operation;
   private int state = -1;
   private final ConfigurationManagerMBean manager;
   private Exception error;

   public ImportExportPartitionTaskImpl(String partitionName, String archiveFileName, Boolean booleanVal, String keyFile, PortablePartitionManagerMBean.Operation operation, ConfigurationManagerMBean manager) {
      this.partitionName = partitionName;
      this.archiveFileName = archiveFileName;
      switch (operation) {
         case EXPORT_PARTITION:
            this.includeAppsNLibs = booleanVal;
            break;
         case IMPORT_PARTITION:
            this.createNew = booleanVal;
      }

      this.keyFile = keyFile;
      this.operation = operation;
      this.manager = manager;
   }

   public int getState() {
      return this.state;
   }

   public Exception getError() {
      return this.error;
   }

   public final void setError(Exception ex) {
      this.error = ex;
   }

   public void run() {
      this.state = this.operation.nextState;

      try {
         switch (this.operation) {
            case EXPORT_PARTITION:
               AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               ImportExportHelper.exportPartition(this.partitionName, this.archiveFileName, this.includeAppsNLibs, this.keyFile);
               break;
            case IMPORT_PARTITION:
               ImportPartitionManager importPartitionManager = (ImportPartitionManager)GlobalServiceLocator.getServiceLocator().getService(ImportPartitionManager.class, new Annotation[0]);
               importPartitionManager.importPartition(this.archiveFileName, this.partitionName, this.createNew, this.keyFile, this.manager);
         }

         this.state = this.operation.nextSuccessState;
         ImportExportLogger.logOperationSucceeded(this.operation.name(), this.partitionName);
      } catch (Exception var4) {
         Exception e = var4;
         this.state = this.operation.nextFailureState;
         this.setError(var4);

         try {
            throw e;
         } catch (Exception var3) {
            ImportExportLogger.logOperationFailException(this.operation.name(), this.partitionName, var3);
         }
      }

   }
}
