package weblogic.management.partition.admin;

import java.io.File;
import weblogic.invocation.PartitionTable;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PartitionUserFileSystemManagerMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PartitionUserFileSystemManagerMBeanImpl extends RuntimeMBeanDelegate implements PartitionUserFileSystemManagerMBean {
   private static final String SEP = "/";
   private String partitionName;

   public PartitionUserFileSystemManagerMBeanImpl(RuntimeMBean parent, String name) throws ManagementException {
      super(name, parent);
      this.partitionName = name;
   }

   public boolean exists(String filePath) throws ManagementException {
      File file = this.getPathToPartition(filePath, false);
      return file.exists();
   }

   public boolean mkdir(String dirPath) throws ManagementException {
      File dir = this.getPathToPartition(dirPath, true);
      if (dir.exists()) {
         throw new ManagementException("User file system path already exists.");
      } else {
         return dir.mkdirs();
      }
   }

   public String[] list(String dirPath) throws ManagementException {
      File dir = this.getPathToPartition(dirPath, false);
      if (dir.isDirectory()) {
         return dir.list();
      } else {
         throw new ManagementException("Provided user file system path is not a directory.");
      }
   }

   public boolean deleteFile(String filePath) throws ManagementException {
      File file = this.getPathToPartition(filePath, true);
      if (file.isDirectory() && file.list().length != 0) {
         throw new ManagementException("Provided user file system path is non-empty directory and cannot be deleted.");
      } else {
         return file.delete();
      }
   }

   public boolean rmdir(String dirPath) throws ManagementException {
      File dir = this.getPathToPartition(dirPath, true);
      if (dir.isDirectory() && dir.list().length != 0) {
         throw new ManagementException("Provided user file system path is non-empty directory and cannot be deleted.");
      } else {
         return dir.delete();
      }
   }

   private File getPathToPartition(String path, boolean prohibitSelf) throws ManagementException {
      validatePath(path, prohibitSelf);
      return new File(this.getUserFileSystemRoot() + "/" + path);
   }

   private String getUserFileSystemRoot() {
      return PartitionTable.getInstance().lookupByName(this.partitionName).getPartitionUserRoot();
   }

   protected static void validatePath(String path, boolean prohibitSelf) throws ManagementException {
      if (path == null) {
         throw new ManagementException("User file system path cannot be null.");
      } else if (prohibitSelf && path.equals(".")) {
         throw new ManagementException("User file system path must be a relative path and may not refer to the current directory.");
      } else if (path.contains("../") || path.contains("/..") || path.equals("..") || path.startsWith("/")) {
         throw new ManagementException("User file system path must be relative path.");
      }
   }
}
