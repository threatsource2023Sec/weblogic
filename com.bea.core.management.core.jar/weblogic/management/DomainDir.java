package weblogic.management;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import weblogic.management.bootstrap.BootStrap;

public class DomainDir {
   private static final Instance INSTANCE = new Instance();
   private static String sep;
   private static String rootDir;
   private static String binDir;
   private static String configDir;
   private static String optConfigDir;
   private static String optConfigJMSDir;
   private static String optConfigJDBCDir;
   private static String optConfigDiagnosticsDir;
   private static String fmwConfigDir;
   private static String fmwServersConfigDir;
   private static String camConfigDir;
   private static String camPendingDir;
   private static String configDiagnosticsDir;
   private static String configJDBCDir;
   private static String configJMSDir;
   private static String configLibDir;
   private static String configSecurityDir;
   private static String configStartupDir;
   private static String editDir;
   private static String initInfoDir;
   private static String libDir;
   private static String pendingDir;
   private static String securityDir;
   private static String serversDir;
   private static String tempDir;
   private static String deploymentsDir;
   private static String libModulesDir;
   private static String appPollerDir;
   private static String oldAppPollerDir;
   private static String orchestrationWorkflowDir;
   private static String partitionsDir;
   private static String rootDirNonCanonical;
   private static String serversDirNonCanonical;
   private static String deploymentsDirNonCanonical;

   public static String getRootDir() {
      return getInstance().getRootDir();
   }

   public static String getRootDirNonCanonical() {
      return getInstance().getRootDirNonCanonical();
   }

   public static String getPathRelativeRootDir(String path) {
      return getInstance().getPathRelativeRootDir(path);
   }

   public static String getBinDir() {
      return getInstance().getBinDir();
   }

   public static String getPathRelativeBinDir(String path) {
      return getInstance().getPathRelativeBinDir(path);
   }

   public static String getConfigDir() {
      return getInstance().getConfigDir();
   }

   public static String getOptConfigDir() {
      return getInstance().getOptConfigDir();
   }

   public static String getOptConfigJMSDir() {
      return getInstance().getOptConfigJMSDir();
   }

   public static String getOptConfigJDBCDir() {
      return getInstance().getOptConfigJDBCDir();
   }

   public static String getOptConfigDiagnosticsDir() {
      return getInstance().getOptConfigDiagnosticsDir();
   }

   public static String getPathRelativeConfigDir(String path) {
      return getInstance().getPathRelativeConfigDir(path);
   }

   public static String getFMWConfigDir() {
      return getInstance().getFMWConfigDir();
   }

   public static String getPathRelativeToFMWConfigDir(String path) {
      return getInstance().getPathRelativeToFMWConfigDir(path);
   }

   public static boolean isFileRelativeToFMWConfigDir(File file) {
      return getInstance().isFileRelativeToFMWConfigDir(file);
   }

   public static String getFMWServersConfigDir() {
      return getInstance().getFMWServersConfigDir();
   }

   public static String getPathRelativeToFMWServersConfigDir(String path) {
      return getInstance().getPathRelativeToFMWServersConfigDir(path);
   }

   public static boolean isFileRelativeToFMWServersConfigDir(File file) {
      return getInstance().isFileRelativeToFMWServersConfigDir(file);
   }

   public static String getFMWServerConfigDir(String serverName) {
      return getInstance().getFMWServerConfigDir(serverName);
   }

   public static boolean isFileRelativeToFMWServerConfigDir(File file, String serverName) {
      return getInstance().isFileRelativeToFMWServerConfigDir(file, serverName);
   }

   public static Set getLocalServers() {
      return getInstance().getLocalServers();
   }

   public static String getCAMConfigDir() {
      return getInstance().getCAMConfigDir();
   }

   public static String getCAMPendingDir() {
      return getInstance().getCAMPendingDir();
   }

   public static String getPathRelativeToCAMConfigDir(String path) {
      return getInstance().getPathRelativeToCAMConfigDir(path);
   }

   public static boolean isFileRelativeToCAMConfigDir(File file) {
      return getInstance().isFileRelativeToCAMConfigDir(file);
   }

   public static String getDeploymentsDir() {
      return getInstance().getDeploymentsDir();
   }

   public static String getPathRelativeDeploymentsDir(String path) {
      return getInstance().getPathRelativeDeploymentsDir(path);
   }

   public static String getDiagnosticsDir() {
      return getInstance().getDiagnosticsDir();
   }

   public static String getPathRelativeDiagnosticsDir(String path) {
      return getInstance().getPathRelativeDiagnosticsDir(path);
   }

   public static String getJDBCDir() {
      return getInstance().getJDBCDir();
   }

   public static String getPathRelativeJDBCDir(String path) {
      return getInstance().getPathRelativeJDBCDir(path);
   }

   public static String getJMSDir() {
      return getInstance().getJMSDir();
   }

   public static String getPathRelativeJMSDir(String path) {
      return getInstance().getPathRelativeJMSDir(path);
   }

   public static String getConfigSecurityDir() {
      return getInstance().getConfigSecurityDir();
   }

   public static String getPathRelativeConfigSecurityDir(String path) {
      return getInstance().getPathRelativeConfigSecurityDir(path);
   }

   public static String getConfigStartupDir() {
      return getInstance().getConfigStartupDir();
   }

   public static String getPathRelativeConfigStartupDir(String path) {
      return getInstance().getPathRelativeConfigStartupDir(path);
   }

   public static String getLibModulesDir() {
      return getInstance().getLibModulesDir();
   }

   public static String getPathRelativeLibModulesDir(String path) {
      return getInstance().getPathRelativeLibModulesDir(path);
   }

   public static String getInitInfoDir() {
      return getInstance().getInitInfoDir();
   }

   public static String getPathRelativeInitInfoDir(String path) {
      return getInstance().getPathRelativeInitInfoDir(path);
   }

   public static String getLibDir() {
      return getInstance().getLibDir();
   }

   public static String getEditDir() {
      return getInstance().getEditDir();
   }

   public static String getPartitionsDir() {
      return getInstance().getPartitionsDir();
   }

   public static String getPathRelativeLibDir(String path) {
      return getInstance().getPathRelativeLibDir(path);
   }

   public static String getPendingDir() {
      return getInstance().getPendingDir();
   }

   public static String getPathRelativePendingDir(String path) {
      return getInstance().getPathRelativePendingDir(path);
   }

   public static String getSecurityDir() {
      return getInstance().getSecurityDir();
   }

   public static String getPathRelativeSecurityDir(String path) {
      return getInstance().getPathRelativeSecurityDir(path);
   }

   public static String getServersDir() {
      return getInstance().getServersDir();
   }

   public static String getServersDirNonCanonical() {
      return getInstance().getServersDirNonCanonical();
   }

   public static String getDeploymentsDirNonCanonical() {
      return getInstance().getDeploymentsDirNonCanonical();
   }

   public static String getPathRelativeServersDir(String path) {
      return getInstance().getPathRelativeServersDir(path);
   }

   public static String getDirForServer(String serverName) {
      return getInstance().getDirForServer(serverName);
   }

   public static String getDirForServerNonCanonical(String serverName) {
      return getInstance().getDirForServerNonCanonical(serverName);
   }

   public static String getPathRelativeServerDir(String serverName, String path) {
      return getInstance().getPathRelativeServerDir(serverName, path);
   }

   public static String getPathRelativeServerDirNonCanonical(String serverName, String path) {
      return getInstance().getPathRelativeServerDirNonCanonical(serverName, path);
   }

   public static String getBinDirForServer(String serverName) {
      return getInstance().getBinDirForServer(serverName);
   }

   public static String getPathRelativeServersBinDir(String serverName, String path) {
      return getInstance().getPathRelativeServersBinDir(serverName, path);
   }

   public static String getCacheDirForServer(String serverName) {
      return getInstance().getCacheDirForServer(serverName);
   }

   public static String getPathRelativeServersCacheDir(String serverName, String path) {
      return getInstance().getPathRelativeServersCacheDir(serverName, path);
   }

   public static String getDataDirForServer(String serverName) {
      return getInstance().getDataDirForServer(serverName);
   }

   public static String getPathRelativeServersDataDir(String serverName, String path) {
      return getInstance().getPathRelativeServersDataDir(serverName, path);
   }

   public static String getLDAPDataDirForServer(String serverName) {
      return getInstance().getLDAPDataDirForServer(serverName);
   }

   public static String getPathRelativeServersLDAPDataDir(String serverName, String path) {
      return getInstance().getPathRelativeServersLDAPDataDir(serverName, path);
   }

   public static String getStoreDataDirForServer(String serverName) {
      return getInstance().getStoreDataDirForServer(serverName);
   }

   public static String getPathRelativeServersStoreDataDir(String serverName, String path) {
      return getInstance().getPathRelativeServersStoreDataDir(serverName, path);
   }

   public static String getLogsDirForServer(String serverName) {
      return getInstance().getLogsDirForServer(serverName);
   }

   public static String getPathRelativeServersLogsDir(String serverName, String path) {
      return getInstance().getPathRelativeServersLogsDir(serverName, path);
   }

   public static String getSecurityDirForServer(String serverName) {
      return getInstance().getSecurityDirForServer(serverName);
   }

   public static String getPathRelativeServersSecurityDir(String serverName, String path) {
      return getInstance().getPathRelativeServersSecurityDir(serverName, path);
   }

   public static String getTempDirForServer(String serverName) {
      return getInstance().getTempDirForServer(serverName);
   }

   public static String getPathRelativeServersTempDir(String serverName, String path) {
      return getInstance().getPathRelativeServersTempDir(serverName, path);
   }

   public static String getTempDir() {
      return getInstance().getTempDir();
   }

   public static String getPathRelativeTempDir(String path) {
      return getInstance().getPathRelativeTempDir(path);
   }

   public static String getPendingDeploymentsDir(String appName) {
      return getInstance().getPendingDeploymentsDir(appName);
   }

   public static void resetRootDirForExplicitUpgrade(String path) {
      getInstance().resetRootDirForExplicitUpgrade(path);
   }

   public static String removeRootDirectoryFromPath(String filePath) {
      return getInstance().removeRootDirectoryFromPath(filePath);
   }

   public static String removeConfigDirectoryFromPath(String filePath) {
      return getInstance().removeConfigDirectoryFromPath(filePath);
   }

   public static String dumpDirectories() {
      return getInstance().dumpDirectories();
   }

   public static String getAppPollerDir() {
      return getInstance().getAppPollerDir();
   }

   public static String getOldAppPollerDir() {
      return getInstance().getOldAppPollerDir();
   }

   public static String getOrchestrationWorkflowDir() {
      return getInstance().getOrchestrationWorkflowDir();
   }

   public static Instance getInstance() {
      return INSTANCE;
   }

   static {
      sep = File.separator;
      rootDirNonCanonical = BootStrap.getRootDirectory();
      if (rootDirNonCanonical != null) {
         try {
            rootDir = (new File(rootDirNonCanonical)).getCanonicalPath();
         } catch (IOException var1) {
            throw new AssertionError(var1);
         }
      }

      DomainDir.Instance.setPaths(rootDir);
      DomainDir.Instance.setPathsNonCanonical(rootDirNonCanonical);
   }

   private static class Instance implements RuntimeDir {
      private Instance() {
      }

      public String getRootDir() {
         return DomainDir.rootDir;
      }

      public String getRootDirNonCanonical() {
         return DomainDir.rootDirNonCanonical;
      }

      public String getPathRelativeRootDir(String path) {
         return this.getRelativePath(this.getRootDir(), path);
      }

      public String getBinDir() {
         return DomainDir.binDir;
      }

      public String getPathRelativeBinDir(String path) {
         return this.getRelativePath(this.getBinDir(), path);
      }

      public String getConfigDir() {
         return DomainDir.configDir;
      }

      public String getOptConfigDir() {
         return DomainDir.optConfigDir;
      }

      public String getOptConfigJMSDir() {
         return DomainDir.optConfigJMSDir;
      }

      public String getOptConfigJDBCDir() {
         return DomainDir.optConfigJDBCDir;
      }

      public String getOptConfigDiagnosticsDir() {
         return DomainDir.optConfigDiagnosticsDir;
      }

      public String getPathRelativeConfigDir(String path) {
         return this.getRelativePath(this.getConfigDir(), path);
      }

      public String getFMWConfigDir() {
         return DomainDir.fmwConfigDir;
      }

      public String getPathRelativeToFMWConfigDir(String path) {
         return this.getRelativePath(this.getFMWConfigDir(), path);
      }

      public boolean isFileRelativeToFMWConfigDir(File file) {
         return this.isFileRelativeToDirectory(file, new File(this.getFMWConfigDir()));
      }

      public String getFMWServersConfigDir() {
         return DomainDir.fmwServersConfigDir;
      }

      public String getPathRelativeToFMWServersConfigDir(String path) {
         return this.getRelativePath(this.getFMWServersConfigDir(), path);
      }

      public boolean isFileRelativeToFMWServersConfigDir(File file) {
         return this.isFileRelativeToDirectory(file, new File(this.getFMWServersConfigDir()));
      }

      public String getFMWServerConfigDir(String serverName) {
         return this.getFMWServersConfigDir() + DomainDir.sep + serverName;
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
               File[] var4 = servers;
               int var5 = servers.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  File server = var4[var6];
                  serverNames.add(server.getName());
               }

               return serverNames;
            } else {
               return serverNames;
            }
         }
      }

      public String getCAMConfigDir() {
         return DomainDir.camConfigDir;
      }

      public String getCAMPendingDir() {
         return DomainDir.camPendingDir;
      }

      public String getPathRelativeToCAMConfigDir(String path) {
         return this.getRelativePath(this.getCAMConfigDir(), path);
      }

      public boolean isFileRelativeToCAMConfigDir(File file) {
         return this.isFileRelativeToDirectory(file, new File(this.getCAMConfigDir()));
      }

      public String getDeploymentsDir() {
         return DomainDir.deploymentsDir;
      }

      public String getPathRelativeDeploymentsDir(String path) {
         return this.getRelativePath(this.getDeploymentsDir(), path);
      }

      public String getDiagnosticsDir() {
         return DomainDir.configDiagnosticsDir;
      }

      public String getPathRelativeDiagnosticsDir(String path) {
         return this.getRelativePath(this.getDiagnosticsDir(), path);
      }

      public String getJDBCDir() {
         return DomainDir.configJDBCDir;
      }

      public String getPathRelativeJDBCDir(String path) {
         return this.getRelativePath(this.getJDBCDir(), path);
      }

      public String getJMSDir() {
         return DomainDir.configJMSDir;
      }

      public String getPathRelativeJMSDir(String path) {
         return this.getRelativePath(this.getJMSDir(), path);
      }

      public String getConfigSecurityDir() {
         return DomainDir.configSecurityDir;
      }

      public String getPathRelativeConfigSecurityDir(String path) {
         return this.getRelativePath(this.getConfigSecurityDir(), path);
      }

      public String getConfigStartupDir() {
         return DomainDir.configStartupDir;
      }

      public String getPathRelativeConfigStartupDir(String path) {
         return this.getRelativePath(this.getConfigStartupDir(), path);
      }

      public String getLibModulesDir() {
         return DomainDir.libModulesDir;
      }

      public String getPathRelativeLibModulesDir(String path) {
         return this.getRelativePath(this.getLibModulesDir(), path);
      }

      public String getInitInfoDir() {
         return DomainDir.initInfoDir;
      }

      public String getPathRelativeInitInfoDir(String path) {
         return this.getRelativePath(this.getInitInfoDir(), path);
      }

      public String getLibDir() {
         return DomainDir.libDir;
      }

      public String getPathRelativeLibDir(String path) {
         return this.getRelativePath(this.getLibDir(), path);
      }

      public String getPartitionsDir() {
         return DomainDir.partitionsDir;
      }

      public String getEditDir() {
         return DomainDir.editDir;
      }

      public String getPendingDir() {
         return DomainDir.pendingDir;
      }

      public String getPathRelativePendingDir(String path) {
         return this.getRelativePath(this.getPendingDir(), path);
      }

      public String getSecurityDir() {
         return DomainDir.securityDir;
      }

      public String getPathRelativeSecurityDir(String path) {
         return this.getRelativePath(this.getSecurityDir(), path);
      }

      public String getServersDir() {
         return DomainDir.serversDir;
      }

      public String getServersDirNonCanonical() {
         return DomainDir.serversDirNonCanonical;
      }

      public String getDeploymentsDirNonCanonical() {
         return DomainDir.deploymentsDirNonCanonical;
      }

      public String getPathRelativeServersDir(String path) {
         return this.getRelativePath(this.getServersDir(), path);
      }

      public String getDirForServer(String serverName) {
         if (serverName == null) {
            throw new AssertionError("server name should not be null");
         } else {
            return this.getServersDir() + DomainDir.sep + serverName;
         }
      }

      public String getDirForServerNonCanonical(String serverName) {
         if (serverName == null) {
            throw new AssertionError("server name should not be null");
         } else {
            return this.getServersDirNonCanonical() + DomainDir.sep + serverName;
         }
      }

      public String getPathRelativeServerDir(String serverName, String path) {
         return this.getRelativePath(this.getDirForServer(serverName), path);
      }

      public String getPathRelativeServerDirNonCanonical(String serverName, String path) {
         return this.getRelativePath(this.getDirForServerNonCanonical(serverName), path);
      }

      public String getBinDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "bin";
      }

      public String getPathRelativeServersBinDir(String serverName, String path) {
         return this.getRelativePath(this.getBinDirForServer(serverName), path);
      }

      public String getCacheDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "cache";
      }

      public String getPathRelativeServersCacheDir(String serverName, String path) {
         return this.getRelativePath(this.getCacheDirForServer(serverName), path);
      }

      public String getDataDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "data";
      }

      public String getPathRelativeServersDataDir(String serverName, String path) {
         return this.getRelativePath(this.getDataDirForServer(serverName), path);
      }

      public String getLDAPDataDirForServer(String serverName) {
         return this.getDataDirForServer(serverName) + DomainDir.sep + "ldap";
      }

      public String getPathRelativeServersLDAPDataDir(String serverName, String path) {
         return this.getRelativePath(this.getLDAPDataDirForServer(serverName), path);
      }

      public String getStoreDataDirForServer(String serverName) {
         return this.getDataDirForServer(serverName) + DomainDir.sep + "store";
      }

      public String getPathRelativeServersStoreDataDir(String serverName, String path) {
         return this.getRelativePath(this.getStoreDataDirForServer(serverName), path);
      }

      public String getLogsDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "logs";
      }

      public String getPathRelativeServersLogsDir(String serverName, String path) {
         return this.getRelativePath(this.getLogsDirForServer(serverName), path);
      }

      public String getSecurityDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "security";
      }

      public String getPathRelativeServersSecurityDir(String serverName, String path) {
         return this.getRelativePath(this.getSecurityDirForServer(serverName), path);
      }

      public String getTempDirForServer(String serverName) {
         return this.getDirForServer(serverName) + DomainDir.sep + "tmp";
      }

      public String getPathRelativeServersTempDir(String serverName, String path) {
         return this.getRelativePath(this.getTempDirForServer(serverName), path);
      }

      public String getTempDir() {
         return DomainDir.tempDir;
      }

      public String getPathRelativeTempDir(String path) {
         return this.getRelativePath(this.getTempDir(), path);
      }

      public String getPendingDeploymentsDir(String appName) {
         String dir = this.getPendingDir() + DomainDir.sep + "deployments" + DomainDir.sep + appName;
         return dir;
      }

      public void resetRootDirForExplicitUpgrade(String path) {
         BootStrap.resetRootDirForExplicitUpgrade(path);
         DomainDir.rootDir = path;
         setPaths(DomainDir.rootDir);
      }

      public String removeRootDirectoryFromPath(String filePath) {
         String rootDirPrefix = (new File(this.getRootDir())).getPath() + File.separator;
         return !filePath.startsWith(rootDirPrefix) && !(new File(filePath)).getPath().startsWith((new File(rootDirPrefix)).getPath()) ? filePath : filePath.substring(rootDirPrefix.length(), filePath.length());
      }

      public String removeConfigDirectoryFromPath(String filePath) {
         String configDirPrefix = (new File(this.getConfigDir())).getPath() + File.separator;
         return !filePath.startsWith(configDirPrefix) && !(new File(filePath)).getPath().startsWith((new File(configDirPrefix)).getPath()) ? filePath : filePath.substring(configDirPrefix.length(), filePath.length());
      }

      public String dumpDirectories() {
         String retVal = "DomainDir Info:\n   rootDir=" + DomainDir.rootDir + "\n   binDir=" + DomainDir.binDir + "\n   configDir=" + DomainDir.configDir + "\n   initInfoDir=" + DomainDir.initInfoDir + "\n   libDir=" + DomainDir.libDir + "\n   pendingDir=" + DomainDir.pendingDir + "\n   securityDir=" + DomainDir.securityDir + "\n   serversDir=" + DomainDir.serversDir + "\n   tempDir=" + DomainDir.tempDir + "\n";
         return retVal;
      }

      private static void setPaths(String rootPath) {
         DomainDir.binDir = rootPath + DomainDir.sep + "bin";
         DomainDir.configDir = rootPath + DomainDir.sep + "config";
         DomainDir.optConfigDir = rootPath + DomainDir.sep + "optconfig";
         DomainDir.optConfigJMSDir = rootPath + DomainDir.sep + "optconfig" + DomainDir.sep + "jms";
         DomainDir.optConfigJDBCDir = rootPath + DomainDir.sep + "optconfig" + DomainDir.sep + "jdbc";
         DomainDir.optConfigDiagnosticsDir = rootPath + DomainDir.sep + "optconfig" + DomainDir.sep + "diagnostics";
         DomainDir.configDiagnosticsDir = DomainDir.configDir + DomainDir.sep + "diagnostics";
         DomainDir.configJDBCDir = DomainDir.configDir + DomainDir.sep + "jdbc";
         DomainDir.configJMSDir = DomainDir.configDir + DomainDir.sep + "jms";
         DomainDir.configLibDir = DomainDir.configDir + DomainDir.sep + "lib";
         DomainDir.configSecurityDir = DomainDir.configDir + DomainDir.sep + "security";
         DomainDir.configStartupDir = DomainDir.configDir + DomainDir.sep + "startup";
         DomainDir.editDir = rootPath + DomainDir.sep + "edit";
         DomainDir.initInfoDir = rootPath + DomainDir.sep + "init-info";
         DomainDir.libDir = rootPath + DomainDir.sep + "lib";
         DomainDir.partitionsDir = rootPath + DomainDir.sep + "partitions";
         DomainDir.pendingDir = rootPath + DomainDir.sep + "pending";
         DomainDir.securityDir = rootPath + DomainDir.sep + "security";
         DomainDir.serversDir = rootPath + DomainDir.sep + "servers";
         DomainDir.tempDir = rootPath + DomainDir.sep + "tmp";
         DomainDir.deploymentsDir = DomainDir.configDir + DomainDir.sep + "deployments";
         DomainDir.libModulesDir = DomainDir.deploymentsDir + DomainDir.sep + "lib_modules";
         DomainDir.appPollerDir = rootPath + DomainDir.sep + "autodeploy";
         DomainDir.oldAppPollerDir = rootPath + DomainDir.sep + "applications";
         DomainDir.fmwConfigDir = DomainDir.configDir + DomainDir.sep + "fmwconfig";
         DomainDir.fmwServersConfigDir = DomainDir.fmwConfigDir + DomainDir.sep + "servers";
         DomainDir.camConfigDir = DomainDir.fmwConfigDir + DomainDir.sep + "components";
         DomainDir.camPendingDir = "pending" + DomainDir.sep + "fmwconfig" + DomainDir.sep + "components";
         DomainDir.orchestrationWorkflowDir = rootPath + DomainDir.sep + "orchestration" + DomainDir.sep + "workflow";
      }

      private static void setPathsNonCanonical(String rootPath) {
         DomainDir.serversDirNonCanonical = rootPath + DomainDir.sep + "servers";
         DomainDir.deploymentsDirNonCanonical = rootPath + DomainDir.sep + "config" + DomainDir.sep + "deployments";
      }

      private String getRelativePath(String path, String filename) {
         if (filename == null) {
            throw new AssertionError("filename should not be null");
         } else {
            return path + DomainDir.sep + filename;
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
         return DomainDir.appPollerDir;
      }

      public String getOldAppPollerDir() {
         return DomainDir.oldAppPollerDir;
      }

      public String getOrchestrationWorkflowDir() {
         return DomainDir.orchestrationWorkflowDir;
      }

      // $FF: synthetic method
      Instance(Object x0) {
         this();
      }
   }
}
