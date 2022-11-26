package weblogic.management;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import weblogic.utils.FileUtils;

public class PartitionDir implements RuntimeDir {
   private static String sep;
   private String rootDir;
   private String configRootDir;
   private String binDir;
   private String configDir;
   private String fmwConfigDir;
   private String fmwServersConfigDir;
   private String camConfigDir;
   private String camPendingDir;
   private String configDiagnosticsDir;
   private String configJDBCDir;
   private String configJMSDir;
   private String configLibDir;
   private String configSecurityDir;
   private String configStartupDir;
   private String initInfoDir;
   private String libDir;
   private String editDir;
   private String pendingDir;
   private String securityDir;
   private String serversDir;
   private String tempDir;
   private String deploymentsDir;
   private String libModulesDir;
   private String appPollerDir;
   private String oldAppPollerDir;
   private String orchestrationWorkflowDir;
   private String rootDirNonCanonical;
   private String serversDirNonCanonical;
   private String deploymentsDirNonCanonical;

   public PartitionDir(String partitionRootDir, String partitionName) {
      this.rootDirNonCanonical = partitionRootDir;
      if (this.rootDirNonCanonical != null) {
         try {
            this.rootDir = (new File(this.rootDirNonCanonical)).getCanonicalPath();
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      this.configRootDir = DomainDir.getConfigDir() + sep + "partitions" + sep + FileUtils.mapNameToFileName(partitionName, false);
      this.setPaths(this.rootDir, this.configRootDir);
      this.setPathsNonCanonical(this.rootDirNonCanonical);
   }

   public String getRootDir() {
      return this.rootDir;
   }

   public String getRootDirNonCanonical() {
      return this.rootDirNonCanonical;
   }

   public String getPathRelativeRootDir(String path) {
      return this.getRelativePath(this.getRootDir(), path);
   }

   public String getBinDir() {
      return this.binDir;
   }

   public String getPathRelativeBinDir(String path) {
      return this.getRelativePath(this.getBinDir(), path);
   }

   public String getConfigDir() {
      return this.configDir;
   }

   public String getPathRelativeConfigDir(String path) {
      return this.getRelativePath(this.getConfigDir(), path);
   }

   public String getFMWConfigDir() {
      return this.fmwConfigDir;
   }

   public String getPathRelativeToFMWConfigDir(String path) {
      return this.getRelativePath(this.getFMWConfigDir(), path);
   }

   public boolean isFileRelativeToFMWConfigDir(File file) {
      return this.isFileRelativeToDirectory(file, new File(this.getFMWConfigDir()));
   }

   public String getFMWServersConfigDir() {
      return this.fmwServersConfigDir;
   }

   public String getPathRelativeToFMWServersConfigDir(String path) {
      return this.getRelativePath(this.getFMWServersConfigDir(), path);
   }

   public boolean isFileRelativeToFMWServersConfigDir(File file) {
      return this.isFileRelativeToDirectory(file, new File(this.getFMWServersConfigDir()));
   }

   public String getFMWServerConfigDir(String serverName) {
      return this.getFMWServersConfigDir() + sep + serverName;
   }

   public boolean isFileRelativeToFMWServerConfigDir(File file, String serverName) {
      return this.isFileRelativeToDirectory(file, new File(this.getFMWServerConfigDir(serverName)));
   }

   public Set getLocalServers() {
      File serversDir = new File(this.getServersDir());
      Set serverNames = new HashSet();
      if (!serversDir.exists()) {
         return serverNames;
      } else {
         File[] servers = serversDir.listFiles(new FileFilter() {
            public boolean accept(File f) {
               return f.isDirectory() && !"domain_bak".equals(f.getName());
            }
         });
         if (servers != null && servers.length != 0) {
            for(int i = 0; i < servers.length; ++i) {
               serverNames.add(servers[i].getName());
            }

            return serverNames;
         } else {
            return serverNames;
         }
      }
   }

   public String getCAMConfigDir() {
      return this.camConfigDir;
   }

   public String getCAMPendingDir() {
      return this.camPendingDir;
   }

   public String getPathRelativeToCAMConfigDir(String path) {
      return this.getRelativePath(this.getCAMConfigDir(), path);
   }

   public boolean isFileRelativeToCAMConfigDir(File file) {
      return this.isFileRelativeToDirectory(file, new File(this.getCAMConfigDir()));
   }

   public String getDeploymentsDir() {
      return this.deploymentsDir;
   }

   public String getPathRelativeDeploymentsDir(String path) {
      return this.getRelativePath(this.getDeploymentsDir(), path);
   }

   public String getDiagnosticsDir() {
      return this.configDiagnosticsDir;
   }

   public String getPathRelativeDiagnosticsDir(String path) {
      return this.getRelativePath(this.getDiagnosticsDir(), path);
   }

   public String getJDBCDir() {
      return this.configJDBCDir;
   }

   public String getPathRelativeJDBCDir(String path) {
      return this.getRelativePath(this.getJDBCDir(), path);
   }

   public String getJMSDir() {
      return this.configJMSDir;
   }

   public String getPathRelativeJMSDir(String path) {
      return this.getRelativePath(this.getJMSDir(), path);
   }

   public String getConfigSecurityDir() {
      return this.configSecurityDir;
   }

   public String getPathRelativeConfigSecurityDir(String path) {
      return this.getRelativePath(this.getConfigSecurityDir(), path);
   }

   public String getConfigStartupDir() {
      return this.configStartupDir;
   }

   public String getPathRelativeConfigStartupDir(String path) {
      return this.getRelativePath(this.getConfigStartupDir(), path);
   }

   public String getLibModulesDir() {
      return this.libModulesDir;
   }

   public String getPathRelativeLibModulesDir(String path) {
      return this.getRelativePath(this.getLibModulesDir(), path);
   }

   public String getInitInfoDir() {
      return this.initInfoDir;
   }

   public String getPathRelativeInitInfoDir(String path) {
      return this.getRelativePath(this.getInitInfoDir(), path);
   }

   public String getLibDir() {
      return this.libDir;
   }

   public String getEditDir() {
      return this.editDir;
   }

   public String getPathRelativeLibDir(String path) {
      return this.getRelativePath(this.getLibDir(), path);
   }

   public String getPendingDir() {
      return this.pendingDir;
   }

   public String getPathRelativePendingDir(String path) {
      return this.getRelativePath(this.getPendingDir(), path);
   }

   public String getSecurityDir() {
      return this.securityDir;
   }

   public String getPathRelativeSecurityDir(String path) {
      return this.getRelativePath(this.getSecurityDir(), path);
   }

   public String getServersDir() {
      return this.serversDir;
   }

   public String getServersDirNonCanonical() {
      return this.serversDirNonCanonical;
   }

   public String getDeploymentsDirNonCanonical() {
      return this.deploymentsDirNonCanonical;
   }

   public String getPathRelativeServersDir(String path) {
      return this.getRelativePath(this.getServersDir(), path);
   }

   public String getDirForServer(String serverName) {
      if (serverName == null) {
         throw new AssertionError("server name should not be null");
      } else {
         return this.getServersDir() + sep + serverName;
      }
   }

   public String getDirForServerNonCanonical(String serverName) {
      if (serverName == null) {
         throw new AssertionError("server name should not be null");
      } else {
         return this.getServersDirNonCanonical() + sep + serverName;
      }
   }

   public String getPathRelativeServerDir(String serverName, String path) {
      return this.getRelativePath(this.getDirForServer(serverName), path);
   }

   public String getPathRelativeServerDirNonCanonical(String serverName, String path) {
      return this.getRelativePath(this.getDirForServerNonCanonical(serverName), path);
   }

   public String getBinDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "bin";
   }

   public String getPathRelativeServersBinDir(String serverName, String path) {
      return this.getRelativePath(this.getBinDirForServer(serverName), path);
   }

   public String getCacheDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "cache";
   }

   public String getPathRelativeServersCacheDir(String serverName, String path) {
      return this.getRelativePath(this.getCacheDirForServer(serverName), path);
   }

   public String getDataDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "data";
   }

   public String getPathRelativeServersDataDir(String serverName, String path) {
      return this.getRelativePath(this.getDataDirForServer(serverName), path);
   }

   public String getLDAPDataDirForServer(String serverName) {
      return this.getDataDirForServer(serverName) + sep + "ldap";
   }

   public String getPathRelativeServersLDAPDataDir(String serverName, String path) {
      return this.getRelativePath(this.getLDAPDataDirForServer(serverName), path);
   }

   public String getStoreDataDirForServer(String serverName) {
      return this.getDataDirForServer(serverName) + sep + "store";
   }

   public String getPathRelativeServersStoreDataDir(String serverName, String path) {
      return this.getRelativePath(this.getStoreDataDirForServer(serverName), path);
   }

   public String getLogsDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "logs";
   }

   public String getPathRelativeServersLogsDir(String serverName, String path) {
      return this.getRelativePath(this.getLogsDirForServer(serverName), path);
   }

   public String getSecurityDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "security";
   }

   public String getPathRelativeServersSecurityDir(String serverName, String path) {
      return this.getRelativePath(this.getSecurityDirForServer(serverName), path);
   }

   public String getTempDirForServer(String serverName) {
      return this.getDirForServer(serverName) + sep + "tmp";
   }

   public String getPathRelativeServersTempDir(String serverName, String path) {
      return this.getRelativePath(this.getTempDirForServer(serverName), path);
   }

   public String getTempDir() {
      return this.tempDir;
   }

   public String getPathRelativeTempDir(String path) {
      return this.getRelativePath(this.getTempDir(), path);
   }

   public String getPendingDeploymentsDir(String appName) {
      String dir = this.getPendingDir() + sep + "deployments" + sep + appName;
      return dir;
   }

   public void resetRootDirForExplicitUpgrade(String path) {
      this.rootDir = path;
      this.setPaths(this.rootDir, this.configRootDir);
   }

   public String removeRootDirectoryFromPath(String filePath) {
      String rootDirPrefix = (new File(this.getRootDir())).getPath() + File.separator;
      return !filePath.startsWith(rootDirPrefix) && !(new File(filePath)).getPath().startsWith((new File(rootDirPrefix)).getPath()) ? filePath : filePath.substring(rootDirPrefix.length(), filePath.length());
   }

   public String dumpDirectories() {
      String retVal = "DomainDir Info:\n   rootDir=" + this.rootDir + "\n   binDir=" + this.binDir + "\n   configDir=" + this.configDir + "\n   initInfoDir=" + this.initInfoDir + "\n   libDir=" + this.libDir + "\n   pendingDir=" + this.pendingDir + "\n   securityDir=" + this.securityDir + "\n   serversDir=" + this.serversDir + "\n   tempDir=" + this.tempDir + "\n";
      return retVal;
   }

   private void setPaths(String rootPath, String configRootPath) {
      this.binDir = rootPath + sep + "bin";
      this.configDir = configRootPath;
      this.configDiagnosticsDir = this.configDir + sep + "diagnostics";
      this.configJDBCDir = this.configDir + sep + "jdbc";
      this.configJMSDir = this.configDir + sep + "jms";
      this.configLibDir = this.configDir + sep + "lib";
      this.configSecurityDir = this.configDir + sep + "security";
      this.configStartupDir = this.configDir + sep + "startup";
      this.initInfoDir = rootPath + sep + "init-info";
      this.libDir = rootPath + sep + "lib";
      this.editDir = rootPath + sep + "edit";
      this.pendingDir = rootPath + sep + "pending";
      this.securityDir = rootPath + sep + "security";
      this.serversDir = rootPath + sep + "servers";
      this.tempDir = rootPath + sep + "tmp";
      this.deploymentsDir = this.configDir + sep + "deployments";
      this.libModulesDir = this.deploymentsDir + sep + "lib_modules";
      this.appPollerDir = rootPath + sep + "autodeploy";
      this.oldAppPollerDir = rootPath + sep + "applications";
      this.fmwConfigDir = this.configDir + sep + "fmwconfig";
      this.fmwServersConfigDir = this.fmwConfigDir + sep + "servers";
      this.camConfigDir = this.fmwConfigDir + sep + "components";
      this.camPendingDir = "pending" + sep + "fmwconfig" + sep + "components";
      this.orchestrationWorkflowDir = rootPath + sep + "orchestration" + sep + "workflow";
   }

   private void setPathsNonCanonical(String rootPath) {
      this.serversDirNonCanonical = rootPath + sep + "servers";
      this.deploymentsDirNonCanonical = rootPath + sep + "config" + sep + "deployments";
   }

   private String getRelativePath(String path, String filename) {
      if (filename == null) {
         throw new AssertionError("filename should not be null");
      } else {
         return path + sep + filename;
      }
   }

   private boolean isFileRelativeToDirectory(File file, File directory) {
      if (file == null) {
         throw new RuntimeException("file should not be null");
      } else if (directory == null) {
         throw new RuntimeException("directory should not be null");
      } else if (!file.isAbsolute()) {
         throw new RuntimeException("file: " + file + " should be represented by an absolute path");
      } else if (directory.exists() && !directory.isDirectory()) {
         throw new RuntimeException("directory: " + directory + " should be a directory");
      } else if (!directory.isAbsolute()) {
         throw new RuntimeException("directory: " + directory + " should be represented by an absolute path");
      } else {
         try {
            URI dirURI = directory.getCanonicalFile().toURI();
            URI fileURI = file.getCanonicalFile().toURI();
            URI resultingURI = dirURI.relativize(fileURI);
            return resultingURI != fileURI;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      }
   }

   private void createDirectory(String path) {
      File dirFile = new File(path);
      if (!dirFile.exists()) {
         dirFile.mkdirs();
      }

   }

   public String getAppPollerDir() {
      return this.appPollerDir;
   }

   public String getOldAppPollerDir() {
      return this.oldAppPollerDir;
   }

   public String getOrchestrationWorkflowDir() {
      return this.orchestrationWorkflowDir;
   }

   public static String getFmwConfigPath(String partitionName) {
      return DomainDir.getConfigDir() + File.separator + "partitions" + File.separator + partitionName + File.separator + "fmwconfig";
   }

   static {
      sep = File.separator;
   }
}
