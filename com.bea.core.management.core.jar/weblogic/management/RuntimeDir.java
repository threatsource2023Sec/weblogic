package weblogic.management;

import java.io.File;
import java.util.Set;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;

public interface RuntimeDir {
   String getRootDir();

   String getRootDirNonCanonical();

   String getPathRelativeRootDir(String var1);

   String getBinDir();

   String getPathRelativeBinDir(String var1);

   String getConfigDir();

   String getPathRelativeConfigDir(String var1);

   String getFMWConfigDir();

   String getPathRelativeToFMWConfigDir(String var1);

   boolean isFileRelativeToFMWConfigDir(File var1);

   String getFMWServersConfigDir();

   String getPathRelativeToFMWServersConfigDir(String var1);

   boolean isFileRelativeToFMWServersConfigDir(File var1);

   String getFMWServerConfigDir(String var1);

   boolean isFileRelativeToFMWServerConfigDir(File var1, String var2);

   Set getLocalServers();

   String getCAMConfigDir();

   String getCAMPendingDir();

   String getPathRelativeToCAMConfigDir(String var1);

   boolean isFileRelativeToCAMConfigDir(File var1);

   String getDeploymentsDir();

   String getPathRelativeDeploymentsDir(String var1);

   String getDiagnosticsDir();

   String getPathRelativeDiagnosticsDir(String var1);

   String getJDBCDir();

   String getPathRelativeJDBCDir(String var1);

   String getJMSDir();

   String getPathRelativeJMSDir(String var1);

   String getConfigSecurityDir();

   String getPathRelativeConfigSecurityDir(String var1);

   String getConfigStartupDir();

   String getPathRelativeConfigStartupDir(String var1);

   String getLibModulesDir();

   String getPathRelativeLibModulesDir(String var1);

   String getInitInfoDir();

   String getPathRelativeInitInfoDir(String var1);

   String getLibDir();

   String getEditDir();

   String getPathRelativeLibDir(String var1);

   String getPendingDir();

   String getPathRelativePendingDir(String var1);

   String getSecurityDir();

   String getPathRelativeSecurityDir(String var1);

   String getServersDir();

   String getServersDirNonCanonical();

   String getDeploymentsDirNonCanonical();

   String getPathRelativeServersDir(String var1);

   String getDirForServer(String var1);

   String getDirForServerNonCanonical(String var1);

   String getPathRelativeServerDir(String var1, String var2);

   String getPathRelativeServerDirNonCanonical(String var1, String var2);

   String getBinDirForServer(String var1);

   String getPathRelativeServersBinDir(String var1, String var2);

   String getCacheDirForServer(String var1);

   String getPathRelativeServersCacheDir(String var1, String var2);

   String getDataDirForServer(String var1);

   String getPathRelativeServersDataDir(String var1, String var2);

   String getLDAPDataDirForServer(String var1);

   String getPathRelativeServersLDAPDataDir(String var1, String var2);

   String getStoreDataDirForServer(String var1);

   String getPathRelativeServersStoreDataDir(String var1, String var2);

   String getLogsDirForServer(String var1);

   String getPathRelativeServersLogsDir(String var1, String var2);

   String getSecurityDirForServer(String var1);

   String getPathRelativeServersSecurityDir(String var1, String var2);

   String getTempDirForServer(String var1);

   String getPathRelativeServersTempDir(String var1, String var2);

   String getTempDir();

   String getPathRelativeTempDir(String var1);

   String getPendingDeploymentsDir(String var1);

   void resetRootDirForExplicitUpgrade(String var1);

   String removeRootDirectoryFromPath(String var1);

   String dumpDirectories();

   String getAppPollerDir();

   String getOldAppPollerDir();

   String getOrchestrationWorkflowDir();

   public static class Current {
      public static RuntimeDir get() {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (cic == null) {
            return DomainDir.getInstance();
         } else {
            String partitionName = cic.getPartitionName();
            PartitionTableEntry pte = PartitionTable.getInstance().lookupByName(partitionName);
            return (RuntimeDir)(pte.getPartitionName().equals(PartitionTable.getInstance().getGlobalPartitionName()) ? DomainDir.getInstance() : new PartitionDir(pte.getPartitionRoot(), partitionName));
         }
      }
   }
}
