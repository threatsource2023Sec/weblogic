package weblogic.management.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public final class EditDirectoryManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final String EDIT_LOCK_FILENAME = "edit.lok";
   private static final String EDIT_DIRECTORY_NAME = "edit";
   private static final String PENDING_DIRECTORY_NAME = "pending";
   private static final String sep;
   private static final String DELETED_FILE_INDEX = "__deleted_files_index__";
   public static final String ORIGINAL_DIRECTORY_NAME = "original";
   private final String editDir;
   private final String pendingDir;
   private final String configDir;
   private final String originalDir;
   private final File deletedFileIndex;
   private final boolean globalDomain;
   private final boolean globalSession;

   public static List findPartitionEditLocks() {
      List locks = new ArrayList();
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return locks;
   }

   public static List findDomainEditLocks() {
      return findFiles("edit.lok", new File(DomainDir.getEditDir()));
   }

   public static File findGlobalEditLock() {
      return new File(DomainDir.getPathRelativeRootDir("edit.lok"));
   }

   public static String getGlobalEditDirectory() {
      return getDomainEditDirectory("default");
   }

   public static String getDomainEditDirectory(String sessionName) {
      return getEditDirectory("DOMAIN", sessionName);
   }

   public static String getEditDirectory(String partitionName, String sessionName) throws IllegalArgumentException {
      boolean globalPartition = "DOMAIN".equals(partitionName);
      String dirName;
      if ("default".equals(sessionName) && globalPartition) {
         dirName = DomainDir.getRootDir();
      } else if (globalPartition) {
         dirName = DomainDir.getEditDir() + File.separator + FileUtils.mapNameToFileName(sessionName);
      } else {
         dirName = getPartitionEditDirectory(partitionName) + File.separator + FileUtils.mapNameToFileName(sessionName);
      }

      if (!"default".equals(sessionName) || "DOMAIN".equals(partitionName)) {
         ensureDirCreated(dirName, partitionName);
      }

      return dirName;
   }

   private static void ensureDirCreated(String dirName, String partitionName) {
      File file = new File(dirName);
      if (!file.isDirectory()) {
         if (!"DOMAIN".equals(partitionName)) {
            DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
            PartitionMBean partitionMBean = domainMBean.lookupPartition(partitionName);
            if (partitionMBean == null) {
               throw new IllegalStateException("Failed to create edit session directory in partition " + partitionName + ", because the partition does not exist.");
            }

            try {
               PartitionFileSystemHelper.checkPartitionFileSystem(partitionMBean);
            } catch (ManagementException var6) {
               throw new IllegalStateException("Failed to create edit session directory in partition " + partitionName, var6);
            }
         }

         boolean result = file.mkdirs();
         if (!result) {
            throw new IllegalStateException("Failed to create directory: " + file.getAbsolutePath());
         }
      }

   }

   public static void ensureEditDirectoryCreated(String partitionName, String sessionName) {
      String editDirectory = getEditDirectory(partitionName, sessionName);
      ensureDirCreated(editDirectory, partitionName);
   }

   private static String getPartitionEditDirectory(String partitionName) {
      PartitionTable partitionTable = PartitionTable.getInstance();
      PartitionTableEntry partitionTableEntry = partitionTable.lookupByName(partitionName);
      String partitionRoot = (new File(partitionTableEntry.getPartitionRoot())).getParent();
      return partitionRoot + File.separator + "edit";
   }

   private static List findFiles(String file, File dir) {
      List files = new ArrayList();
      File[] list = dir.listFiles();
      if (list != null) {
         File[] var4 = list;
         int var5 = list.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File f = var4[var6];
            if (f.isDirectory()) {
               files.addAll(findFiles(file, f));
            } else if (file.equals(f.getName()) && f.isFile()) {
               files.add(f);
            }
         }
      }

      return files;
   }

   public static String getEditLock(String partitionName, String sessionName) {
      return getEditDirectory(partitionName, sessionName) + File.separator + "edit.lok";
   }

   public static EditDirectoryManager getGlobalDirectoryManager() {
      return getDomainDirectoryManager("default");
   }

   public static EditDirectoryManager getDomainDirectoryManager(String sessionName) {
      return getDirectoryManager("DOMAIN", sessionName);
   }

   public static EditDirectoryManager getDirectoryManager(String partitionName, String sessionName) {
      return "DOMAIN".equals(partitionName) && "default".equals(sessionName) ? new EditDirectoryManager(getGlobalEditDirectory(), DomainDir.getPendingDir(), true, true) : new EditDirectoryManager(getEditDirectory(partitionName, sessionName), "default".equals(sessionName));
   }

   private EditDirectoryManager(String editDir, boolean globalSession) {
      this(editDir, editDir + sep + "pending", false, globalSession);
   }

   private EditDirectoryManager(String editDir, String pendingDir, boolean globalDomain, boolean globalSession) {
      this.editDir = editDir;
      this.pendingDir = pendingDir;
      this.originalDir = editDir + sep + "original";
      this.globalDomain = globalDomain;
      this.globalSession = globalSession;
      this.configDir = DomainDir.getConfigDir();
      this.deletedFileIndex = new File(pendingDir, "__deleted_files_index__");
   }

   public boolean deleteFile(String relativePath) {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending != null && pending.delete();
   }

   private List find(File directory) {
      return this.find(directory, true);
   }

   private List find(File directory, boolean stepIntoSubDirectories) {
      if (!directory.exists()) {
         return Collections.emptyList();
      } else if (!directory.isDirectory()) {
         throw new IllegalArgumentException(directory + " is not a directory.");
      } else {
         ArrayList result = new ArrayList();
         File[] files = directory.listFiles();
         if (files != null) {
            File[] var5 = files;
            int var6 = files.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               File f = var5[var7];
               if (f.isDirectory()) {
                  if (stepIntoSubDirectories) {
                     result.addAll(this.find(f));
                  }
               } else if (!f.equals(this.deletedFileIndex)) {
                  result.add(f);
               }
            }
         }

         return result;
      }
   }

   public List getAllPendingFiles() {
      return this.find(new File(this.pendingDir));
   }

   public File[] getAllPendingFilesAsArray() {
      List files = this.getAllPendingFiles();
      return (File[])files.toArray(new File[files.size()]);
   }

   public InputStream getConfigAsStream() throws IOException {
      return this.getPendingFileAsStream(BootStrap.getDefaultConfigFileName());
   }

   public File getConfigFile() {
      return this.getPendingFile(BootStrap.getDefaultConfigFileName());
   }

   public String getEditLock() {
      return this.editDir + (this.editDir.endsWith(sep) ? "" : sep) + "edit.lok";
   }

   public InputStream getPendingFileAsStream(String relativePath) throws IOException {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending != null ? new FileInputStream(pending) : new FileInputStream(this.configDir + sep + relativePath);
   }

   public InputStream getPendingConfigFileAsStream() throws IOException {
      String relativePath = BootStrap.getDefaultConfigFileName();
      File pending = this.getPendingFile(relativePath);
      return pending != null ? new FileInputStream(pending) : null;
   }

   private File getPendingFile(String filename) {
      String path = this.pendingDir + sep + filename;
      File pending = new File(path);
      return pending.exists() ? pending : null;
   }

   private void validateRelativePath(String relativePath) {
      if (relativePath == null) {
         throw new AssertionError("filename should not be null");
      }
   }

   public Set getCandidateFilesForDeletion() {
      Set candidateFilesForDeletion = new LinkedHashSet();
      if (!this.deletedFileIndex.exists()) {
         return candidateFilesForDeletion;
      } else {
         BufferedReader br = null;

         try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(this.deletedFileIndex)));

            String filePath;
            while((filePath = br.readLine()) != null) {
               filePath = filePath.trim();
               if (filePath.length() > 0) {
                  File file = new File(filePath);
                  if (DomainDir.isFileRelativeToFMWConfigDir(file)) {
                     candidateFilesForDeletion.add(file);
                  }
               }
            }
         } catch (IOException var12) {
            throw new RuntimeException(var12);
         } finally {
            if (br != null) {
               try {
                  br.close();
               } catch (Exception var11) {
                  throw new RuntimeException(var11);
               }
            }

         }

         return candidateFilesForDeletion;
      }
   }

   public boolean configExists() {
      return this.pendingFileExists(BootStrap.getDefaultConfigFileName());
   }

   public boolean pendingFileExists(String relativePath) {
      this.validateRelativePath(relativePath);
      return this.getPendingFile(relativePath) != null;
   }

   public String convertPendingDirectoryToConfig(String filePath) {
      String prefix = this.pendingDir + sep;
      return !filePath.startsWith(prefix) && !(new File(filePath)).getPath().startsWith((new File(prefix)).getPath()) ? filePath : DomainDir.getConfigDir() + sep + filePath.substring(prefix.length(), filePath.length());
   }

   public boolean fileExists(String relativePath) {
      this.validateRelativePath(relativePath);
      return this.getPendingFile(relativePath) != null;
   }

   public boolean deleteAllPending() {
      return this.deleteAll(this.pendingDir);
   }

   public boolean deleteAllOriginal() {
      return this.deleteAll(this.originalDir);
   }

   private boolean deleteAll(String directory) {
      boolean allDeleted = true;
      File dir = new File(directory);
      if (!this.remove(dir, true)) {
         allDeleted = false;
      }

      return allDeleted;
   }

   private boolean remove(File dir, boolean removeSubdirectories) {
      if (!dir.exists()) {
         return true;
      } else {
         boolean allDeleted = true;
         File[] files = dir.listFiles();
         if (files == null) {
            allDeleted = false;
         }

         for(int i = 0; files != null && i < files.length; ++i) {
            File f = files[i];
            if (f.isDirectory() && removeSubdirectories && !this.remove(f, removeSubdirectories)) {
               allDeleted = false;
            }

            if (!f.delete()) {
               if (!f.isDirectory()) {
                  allDeleted = false;
                  ManagementLogger.logPendingDeleteFileFailed(f.getAbsolutePath());
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Failed to delete file " + f);
               }
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Deleted file " + f);
            }
         }

         return allDeleted;
      }
   }

   public String getPendingDirectory() {
      return this.pendingDir;
   }

   public boolean hasPendingConfigs() {
      File pending = new File(this.pendingDir);
      File[] files = pending.listFiles();
      return pending.exists() && files != null && files.length > 0;
   }

   public String getOriginalDirectory() {
      return this.originalDir;
   }

   public File getOriginalDirectoryFile() {
      return new File(this.originalDir);
   }

   public boolean createOriginalDir() {
      return this.getOriginalDirectoryFile().mkdirs();
   }

   public boolean originalDirExists() {
      return this.dirExists(this.originalDir);
   }

   private boolean dirExists(String path) {
      this.validateRelativePath(path);
      return (new File(path)).isDirectory();
   }

   public String getOriginalConfig() {
      return this.originalDir + sep + BootStrap.getDefaultConfigFileName();
   }

   public File getOriginalConfigFile() {
      return new File(this.getOriginalConfig());
   }

   public FileInputStream getOriginalConfigAsStream() throws FileNotFoundException {
      return new FileInputStream(this.getOriginalConfigFile());
   }

   public String getPendingFilePath(String file) {
      return this.getPendingFilePath("", file);
   }

   public String getPendingFilePath(String subDir, String file) {
      return subDir != null && !"".equals(subDir) ? this.pendingDir + sep + subDir + sep + file : this.pendingDir + sep + file;
   }

   public void destroy() {
      if (this.globalDomain && this.globalSession) {
         this.deleteAllPending();
         this.deleteAllOriginal();
      } else {
         this.delete(new File(this.editDir));
      }

   }

   private void delete(File f) {
      if (f != null) {
         if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
               File[] var3 = files;
               int var4 = files.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  File file = var3[var5];
                  if (file.isDirectory()) {
                     this.delete(file);
                  } else {
                     boolean delete = file.delete();
                     if (!delete) {
                        throw new IllegalStateException("Cannot delete file: " + f.getAbsolutePath());
                     }
                  }
               }
            }

            boolean delete = f.delete();
            if (!delete) {
               throw new IllegalStateException("Cannot delete directory: " + f.getAbsolutePath());
            }
         } else if (f.exists()) {
            boolean delete = f.delete();
            if (!delete) {
               throw new IllegalStateException("Cannot delete file: " + f.getAbsolutePath());
            }
         }

      }
   }

   public File[] getAllCAMFilesAsArray() {
      List files = this.getAllCAMFiles();
      return (File[])files.toArray(new File[files.size()]);
   }

   public List getAllCAMFiles() {
      File pending = new File(this.pendingDir + sep + "fmwconfig" + sep + "components");
      return this.find(pending);
   }

   public Set getCAMCandidateFilesForDeletion() {
      Set allFiles = this.getCandidateFilesForDeletion();
      if (allFiles.size() == 0) {
         return allFiles;
      } else {
         Set camFiles = new HashSet();
         Iterator var3 = allFiles.iterator();

         while(var3.hasNext()) {
            File f = (File)var3.next();
            if (DomainDir.isFileRelativeToCAMConfigDir(f)) {
               camFiles.add(f);
            }
         }

         return camFiles;
      }
   }

   public void addCandidateFileForDeletion(File candidate) throws IOException {
      if (candidate != null && candidate.exists()) {
         if (!DomainDir.isFileRelativeToFMWConfigDir(candidate)) {
            throw new IllegalArgumentException("" + candidate + " cannot be marked for deletion, as it doesn't reside under: " + DomainDir.getFMWConfigDir());
         } else {
            BufferedWriter bw = null;

            try {
               if (!this.deletedFileIndex.exists()) {
                  this.deletedFileIndex.createNewFile();
               }

               bw = new BufferedWriter(new FileWriter(this.deletedFileIndex, true));
               bw.write(candidate.getCanonicalPath());
               bw.newLine();
               bw.flush();
            } finally {
               if (bw != null) {
                  bw.close();
               }

            }

         }
      }
   }

   static {
      sep = File.separator;
   }
}
