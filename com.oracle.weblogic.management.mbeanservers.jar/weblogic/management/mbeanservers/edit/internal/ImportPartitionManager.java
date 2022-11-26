package weblogic.management.mbeanservers.edit.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.provider.internal.ConfigReader;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.management.provider.internal.EditSessionConfigurationManagerService;
import weblogic.management.provider.internal.ImportExportHelper;
import weblogic.utils.FileUtils;

@Service
public class ImportPartitionManager {
   @Inject
   EditSessionConfigurationManagerService service;
   private static final long STATUS_CHECK_INTERVAL = 10000L;
   private static final Logger LOGGER = Logger.getLogger(ImportPartitionManager.class.getName());

   public PartitionMBean importPartition(String partitionArchive, String partitionName, Boolean createNew, String keyFile, ConfigurationManagerMBean manager) throws Exception {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "importPartition begins " + partitionName + " archive=" + partitionArchive + " createNew=" + createNew + " keyFile=" + keyFile);
      }

      ZipFile pArchive = new ZipFile(partitionArchive);
      ZipEntry migrateZipEntry = pArchive.getEntry("config/migrate-config.xml");
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "migrateZipEntry=" + migrateZipEntry);
      }

      if (migrateZipEntry != null) {
         MigrationToPartitionManager migrateMgr = new MigrationToPartitionManager();
         return migrateMgr.migrateToPartition(partitionArchive, partitionName, createNew, keyFile, manager);
      } else {
         DomainMBean toImportDomain = manager.startEdit(-1, -1);
         PartitionMBean partitionMBean = null;
         String partitionArchivePath = (new File(partitionArchive)).getParent();
         File secretKeyFile = null;
         Set writtenFileSet = new HashSet();

         try {
            Enumeration zes = pArchive.entries();
            String newConfig = null;
            String attributesString = null;
            this.validateZipToImport(pArchive);
            DomainMBean domain = (DomainMBean)((Descriptor)toImportDomain.getDescriptor().clone()).getRootBean();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "domain=" + domain);
            }

            label204:
            while(true) {
               InputStream ins;
               while(true) {
                  if (!zes.hasMoreElements()) {
                     String configChangeFile = newConfig.replace("${DOMAIN_NAME}", domain.getName());
                     configChangeFile = configChangeFile.replace("${DOMAIN_VERSION}", domain.getDomainVersion());
                     InputStream is = new ByteArrayInputStream(configChangeFile.getBytes());
                     EditableDescriptorManager descMgr = (EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true);
                     ArrayList errs = new ArrayList();
                     Descriptor d = descMgr.createDescriptor(new ConfigReader(is), errs, false);
                     DomainMBean dMbean = (DomainMBean)d.getRootBean();
                     ImportExportHelper.setOperationType("Import");
                     ImportExportHelper.setCONFIG_TO_SCRIPT_SECRET_FILE(secretKeyFile.getAbsolutePath());
                     int noOfRGTBeforeImport = domain.getResourceGroupTemplates().length;
                     ImportExportHelper.importResourceGroupTemplate(dMbean, domain, createNew, pArchive, attributesString, writtenFileSet, keyFile);
                     int noOfRGTAfterImport = domain.getResourceGroupTemplates().length;
                     boolean var10000 = noOfRGTAfterImport > noOfRGTBeforeImport;
                     this.activate(manager);
                     break label204;
                  }

                  ZipEntry ze = (ZipEntry)zes.nextElement();
                  ins = pArchive.getInputStream(ze);
                  if (ze.getName().equals("partition-config.xml")) {
                     newConfig = ImportExportHelper.getStringFromInputStream(ins);
                     break;
                  }

                  if (ze.getName().endsWith("-attributes.json")) {
                     attributesString = ImportExportHelper.getStringFromInputStream(ins);
                     File changeAttributeFile = new File(partitionArchivePath + File.separator + ze.getName());
                     if (changeAttributeFile.exists()) {
                        attributesString = new String(Files.readAllBytes(changeAttributeFile.toPath()));
                     }
                     break;
                  }

                  if (ze.getName().contains("expPartSecret")) {
                     secretKeyFile = File.createTempFile("expPartSecret", "");
                     Files.copy(ins, secretKeyFile.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                     writtenFileSet.add(secretKeyFile);
                     break;
                  }

                  if (!ze.getName().startsWith("pfs/")) {
                     Path toCreate = Paths.get(DomainDir.getPendingDir(), ImportExportHelper.removeRootFromPath(ze.getName()));
                     if (ze.isDirectory()) {
                        if (!Files.exists(toCreate, new LinkOption[0])) {
                           Files.createDirectories(toCreate);
                        }
                     } else {
                        FileUtils.writeToFile(ins, toCreate.toFile());
                     }

                     writtenFileSet.add(toCreate.toFile());
                     break;
                  }
               }

               ins.close();
            }
         } catch (Exception var30) {
            if (PortablePartitionManagerMBeanImpl.isDebugEnabled()) {
               PortablePartitionManagerMBeanImpl.debug(var30.getLocalizedMessage());
            }

            Iterator var14 = writtenFileSet.iterator();

            while(var14.hasNext()) {
               File writtenFile = (File)var14.next();
               FileUtils.remove(writtenFile);
            }

            this.undoUnactivatedChanges(manager);
            if (var30 instanceof BeanAlreadyExistsException) {
               throw new BeanAlreadyExistsException(var30.getMessage());
            }

            throw new Exception(var30);
         } finally {
            pArchive.close();
            if (secretKeyFile != null && secretKeyFile.exists()) {
               secretKeyFile.delete();
            }

         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "importPartition end " + partitionMBean);
         }

         return (PartitionMBean)partitionMBean;
      }
   }

   private void validateZipToImport(ZipFile exportedZip) throws IllegalArgumentException {
      if (exportedZip.size() <= 0) {
         throw new IllegalArgumentException("Passed Zip for importPartition is empty");
      } else if (exportedZip.getEntry("partition-config.xml") == null) {
         throw new IllegalArgumentException(String.format("Passed Zip for importPartition should have %s entry", "partition-config.xml"));
      }
   }

   private boolean activate(ConfigurationManagerMBean manager) throws Exception {
      ActivationTaskMBean actTsk = manager.activate(-1L);
      actTsk.waitForTaskCompletion();
      if (actTsk.getError() != null) {
         if (PortablePartitionManagerMBeanImpl.isDebugEnabled()) {
            PortablePartitionManagerMBeanImpl.debug("<ImportPartitionManager> Activation of task failed : " + actTsk.getError());
         }

         this.undoUnactivatedChanges(manager);
         throw actTsk.getError();
      } else {
         return actTsk.getState() == 4;
      }
   }

   private void undoUnactivatedChanges(ConfigurationManagerMBean manager) throws Exception {
      if (manager.haveUnactivatedChanges()) {
         manager.undoUnactivatedChanges();
      }

      manager.cancelEdit();
   }
}
