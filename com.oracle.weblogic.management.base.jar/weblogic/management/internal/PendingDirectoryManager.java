package weblogic.management.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.bootstrap.BootStrap;
import weblogic.utils.ArrayUtils;

/** @deprecated */
@Deprecated
public class PendingDirectoryManager {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final String sep;
   private final String pendingDir;
   private final String configDir;
   private static final PendingDirectoryManager instance;
   private static final String DELETED_FILE_INDEX = "__deleted_files_index__";
   private final File deletedFileIndex;

   private PendingDirectoryManager(String pending) {
      String rootDir = DomainDir.getRootDir();
      this.pendingDir = pending == null ? DomainDir.getPendingDir() : pending;
      this.configDir = DomainDir.getConfigDir();
      this.deletedFileIndex = new File(this.pendingDir, "__deleted_files_index__");
   }

   public static PendingDirectoryManager getInstance() {
      return instance;
   }

   public static PendingDirectoryManager getInstance(String pendingDir) {
      return pendingDir == null ? instance : new PendingDirectoryManager(pendingDir);
   }

   public InputStream getFileAsStream(String relativePath) throws IOException {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending != null ? new FileInputStream(pending) : new FileInputStream(this.configDir + sep + relativePath);
   }

   public boolean fileExists(String relativePath) {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending != null;
   }

   public OutputStream getFileOutputStream(String relativePath) throws IOException {
      this.validateRelativePath(relativePath);
      String path = this.pendingDir + sep + relativePath;
      File out = new File(path);
      String parent = out.getParent();
      if (parent != null) {
         File dir = new File(parent);
         dir.mkdirs();
      }

      return new FileOutputStream(path);
   }

   public InputStream getConfigAsStream() throws IOException {
      return this.getFileAsStream(BootStrap.getDefaultConfigFileName());
   }

   public boolean configExists() {
      return this.fileExists(BootStrap.getDefaultConfigFileName());
   }

   public OutputStream getConfigOutputStream() throws IOException {
      return this.getFileOutputStream(BootStrap.getDefaultConfigFileName());
   }

   public InputStream getSecurityConfigAsStream(String filename) throws IOException {
      this.validateFilename(filename);
      return this.getFileAsStream("security" + sep + filename);
   }

   public boolean securityConfigExists(String filename) {
      this.validateFilename(filename);
      return this.fileExists("security" + sep + filename);
   }

   public OutputStream getSecurityConfigOutputStream(String filename) throws IOException {
      this.validateFilename(filename);
      return this.getFileOutputStream("security" + sep + filename);
   }

   public InputStream getGlobalDescriptorAsStream(String filename) throws IOException {
      this.validateFilename(filename);
      return this.getFileAsStream(filename);
   }

   public boolean globalDescriptorExists(String filename) {
      this.validateFilename(filename);
      return this.fileExists(filename);
   }

   public OutputStream getGlobalDescriptorOutputStream(String filename) throws IOException {
      this.validateFilename(filename);
      return this.getFileOutputStream(filename);
   }

   public InputStream getDeploymentPlanAsStream(String appName, String filename) throws IOException {
      this.validateAppName(appName);
      this.validateFilename(filename);
      return this.getFileAsStream("deployments" + sep + appName + sep + filename);
   }

   public boolean deploymentPlanExists(String appName, String filename) {
      this.validateAppName(appName);
      this.validateFilename(filename);
      return this.fileExists("deployments" + sep + appName + sep + filename);
   }

   public OutputStream getDeploymentPlanOutputStream(String appName, String filename) throws IOException {
      this.validateAppName(appName);
      this.validateFilename(filename);
      return this.getFileOutputStream("deployments" + sep + appName + sep + filename);
   }

   public File getFile(String relativePath) {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending;
   }

   public boolean deleteAll() {
      boolean allDeleted = true;
      File dir = new File(this.pendingDir);
      if (!this.remove(dir, true)) {
         allDeleted = false;
      }

      return allDeleted;
   }

   public boolean deleteFile(String relativePath) {
      this.validateRelativePath(relativePath);
      File pending = this.getPendingFile(relativePath);
      return pending == null ? false : pending.delete();
   }

   public boolean deleteConfig() {
      return this.deleteFile(BootStrap.getDefaultConfigFileName());
   }

   public boolean deleteSecurityConfig(String filename) {
      this.validateFilename(filename);
      return this.deleteFile("security" + sep + filename);
   }

   public boolean deleteGlobalDescriptor(String filename) {
      this.validateFilename(filename);
      return this.deleteFile(filename);
   }

   public boolean deleteDeploymentPlan(String appName, String filename) {
      this.validateAppName(appName);
      this.validateFilename(filename);
      return this.deleteFile("deployments" + sep + appName + sep + filename);
   }

   public File[] getAllFiles() {
      File pending = new File(this.pendingDir);
      return this.find(pending);
   }

   public File[] getAllCAMFiles() {
      File pending = new File(this.pendingDir + sep + "fmwconfig" + sep + "components");
      return this.find(pending);
   }

   public File getConfigFile() {
      return this.getPendingFile(BootStrap.getDefaultConfigFileName());
   }

   public File[] getConfigFiles() {
      File pending = new File(this.pendingDir);
      return this.find(pending, false);
   }

   public File[] getSecurityConfigFiles() {
      File pending = new File(this.pendingDir + sep + "security");
      return this.find(pending);
   }

   public File[] getDeploymentFiles() {
      File pending = new File(this.pendingDir + sep + "deployments");
      return this.find(pending);
   }

   public String removePendingDirectoryFromPath(String filePath) {
      String prefix = this.pendingDir + sep;
      return !filePath.startsWith(prefix) && !(new File(filePath)).getPath().startsWith((new File(prefix)).getPath()) ? filePath : filePath.substring(prefix.length(), filePath.length());
   }

   public String convertPendingDirectoryToConfig(String filePath) {
      String prefix = this.pendingDir + sep;
      return !filePath.startsWith(prefix) && !(new File(filePath)).getPath().startsWith((new File(prefix)).getPath()) ? filePath : DomainDir.getConfigDir() + sep + filePath.substring(prefix.length(), filePath.length());
   }

   public Set getCandidateFilesForDeletion() {
      Set candidateFilesForDeletion = new LinkedHashSet();
      if (!this.deletedFileIndex.exists()) {
         return candidateFilesForDeletion;
      } else {
         BufferedReader br = null;

         try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(this.deletedFileIndex)));
            String filePath = null;

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

   private void validateRelativePath(String relativePath) {
      if (relativePath == null) {
         throw new AssertionError("filename should not be null");
      }
   }

   private void validateFilename(String filename) {
      if (filename == null) {
         throw new AssertionError("filename should not be null");
      }
   }

   private void validateAppName(String appName) {
      if (appName == null) {
         throw new AssertionError("application name should not be null");
      }
   }

   private File getPendingFile(String filename) {
      String path = this.pendingDir + sep + filename;
      File pending = new File(path);
      return pending.exists() ? pending : null;
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

   private File[] find(File directory) {
      return this.find(directory, true);
   }

   private File[] find(File directory, boolean stepIntoSubDirectories) {
      if (!directory.exists()) {
         return new File[0];
      } else if (!directory.isDirectory()) {
         throw new IllegalArgumentException(directory + " is not a directory.");
      } else {
         ArrayList result = new ArrayList();
         File[] files = directory.listFiles();

         for(int i = 0; i < files.length; ++i) {
            File f = files[i];
            if (f.isDirectory()) {
               if (stepIntoSubDirectories) {
                  ArrayUtils.addAll(result, this.find(f));
               }
            } else if (!f.equals(this.deletedFileIndex)) {
               result.add(f);
            }
         }

         return (File[])((File[])result.toArray(new File[result.size()]));
      }
   }

   static {
      sep = File.separator;
      instance = new PendingDirectoryManager((String)null);
   }
}
