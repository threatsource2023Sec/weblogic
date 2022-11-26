package weblogic.management.internal;

import java.io.File;
import java.io.IOException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.partition.admin.PartitionFileSystemUtil;
import weblogic.utils.FileUtils;

public class PartitionFileSystemHelper {
   public static void createRGTDirectory(ResourceGroupTemplateMBean rgt) {
      File RGTDirectory = new File(getRGTDirName(rgt));
      if (!RGTDirectory.exists()) {
         RGTDirectory.mkdirs();
      }

   }

   public static void deleteRGTDirectory(ResourceGroupTemplateMBean rgt) {
      File RGTDirectory = new File(getRGTDirName(rgt));
      if (RGTDirectory.exists()) {
         FileUtils.remove(RGTDirectory);
      }

   }

   public static String getRGTDirName(ResourceGroupTemplateMBean rgt) {
      return DomainDir.getConfigDir() + File.separator + "resource-group-templates" + File.separator + FileUtils.mapNameToFileName(rgt.getName(), false);
   }

   public static void checkDomainContentFile() throws ManagementException {
      try {
         if (!PartitionFileSystemUtil.domainContentFileExists()) {
            PartitionFileSystemUtil.createDomainContentFile();
         }

      } catch (IOException var1) {
         throw new ManagementException("Error creating domain directory content file:" + var1.toString());
      }
   }

   public static void checkPartitionFileSystem(PartitionMBean partition) throws ManagementException {
      PartitionFileSystemMBean pfs = partition.getSystemFileSystem();
      PartitionFileSystemMBean upfs = partition.getUserFileSystem();
      String pfsRoot = pfs.getRoot();
      String upfsRoot = upfs.getRoot();
      String partitionID = partition.getPartitionID();
      String partitionName = partition.getName();
      File pfsRootFile = new File(pfsRoot);
      File upfsRootFile = new File(upfsRoot);

      String fileSystemID;
      try {
         if (pfsRootFile.exists()) {
            if (PartitionFileSystemUtil.getPartitionIDFile(pfsRoot).exists()) {
               fileSystemID = PartitionFileSystemUtil.getID(pfsRoot);
               if (!fileSystemID.equals(partitionID)) {
                  throw new ManagementException("Partition file system does not match partition ID");
               }
            } else {
               PartitionFileSystemUtil.createPartitionFileSystem(pfsRoot, partitionName);
               PartitionFileSystemUtil.createIDFile(pfsRoot, partitionID);
            }
         } else {
            if (!pfs.isCreateOnDemand()) {
               throw new ManagementException("Partition system file system does not exist and create on demand flag is not set for partition file system.");
            }

            PartitionFileSystemUtil.createPartitionFileSystem(pfsRoot, partitionName);
            PartitionFileSystemUtil.createIDFile(pfsRoot, partitionID);
         }
      } catch (IOException var11) {
         throw new ManagementException("Error creating partition system file system:" + var11.toString());
      }

      try {
         if (upfsRootFile.exists()) {
            if (PartitionFileSystemUtil.getPartitionIDFile(upfsRoot).exists()) {
               fileSystemID = PartitionFileSystemUtil.getID(upfsRoot);
               if (!fileSystemID.equals(partitionID)) {
                  throw new ManagementException("Partition file system does not match partition ID");
               }

               PartitionFileSystemUtil.createPartitionFileSystem(upfsRoot, partitionName, false);
            } else {
               PartitionFileSystemUtil.createPartitionFileSystem(upfsRoot, partitionName, false);
               PartitionFileSystemUtil.createIDFile(upfsRoot, partitionID);
            }
         } else {
            if (!upfs.isCreateOnDemand()) {
               throw new ManagementException("Partition user file system does not exist and create on demand flag is not set for partition user file system.");
            }

            PartitionFileSystemUtil.createPartitionFileSystem(upfsRoot, partitionName, false);
            PartitionFileSystemUtil.createIDFile(upfsRoot, partitionID);
         }

      } catch (IOException var10) {
         throw new ManagementException("Error creating partition user file system:" + var10.toString());
      }
   }

   public static void removePartitionFileSystem(PartitionMBean partition) throws ManagementException {
      PartitionFileSystemMBean pfs = partition.getSystemFileSystem();
      PartitionFileSystemMBean upfs = partition.getUserFileSystem();
      String pfsRoot = pfs.getRoot();
      String upfsRoot = upfs.getRoot();
      String partitionID = partition.getPartitionID();
      String partitionName = partition.getName();
      File pfsRootFile = new File(pfsRoot);
      File upfsRootFile = new File(upfsRoot);
      if (pfsRootFile.exists()) {
         if (pfs.isPreserved()) {
            try {
               PartitionFileSystemUtil.backupPartitionFileSystem(pfsRoot, partitionID);
            } catch (IOException var15) {
               throw new ManagementException("Error renaming partition file system directory:" + var15.toString());
            }

            try {
               PartitionFileSystemUtil.backupPartitionConfigFileSystem(partitionName, partitionID);
            } catch (IOException var14) {
               throw new ManagementException("Error renaming partition config file system directory:" + var14.toString());
            }
         } else {
            try {
               PartitionFileSystemUtil.deletePartitionFileSystem(pfsRoot);
            } catch (IOException var13) {
               throw new ManagementException("Error deleting partition file system directory:" + var13.toString());
            }

            try {
               PartitionFileSystemUtil.deletePartitionConfigFileSystem(partitionName);
            } catch (IOException var12) {
               throw new ManagementException("Error deleting partition file system config directory:" + var12.toString());
            }
         }
      }

      if (upfsRootFile.exists()) {
         if (upfs.isPreserved()) {
            try {
               PartitionFileSystemUtil.backupPartitionFileSystem(upfsRoot, partitionID);
            } catch (IOException var11) {
               throw new ManagementException("Error renaming partition file system directory:" + var11.toString());
            }
         } else {
            try {
               PartitionFileSystemUtil.deletePartitionFileSystem(upfsRoot);
            } catch (IOException var10) {
               throw new ManagementException("Error deleting partition file system directory:" + var10.toString());
            }
         }
      }

   }
}
