package weblogic.management.mbeans.custom;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Set;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.logging.MessageLogger;
import weblogic.logging.Severities;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.mbeans.listener.JTAMigratableTargetMBeanUpdateListener;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.protocol.ProtocolManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;

public class ServerTemplate extends Kernel {
   private static final DebugCategory DEBUG_DEPLOYER = Debug.getCategory("weblogic.deployer");
   private static final long serialVersionUID = -2191431493098472191L;
   private static final long READ_DELAY = 250L;
   private static final long READ_MAX_DELAY_COUNT = 120L;
   private static final boolean DEBUG = false;
   private String activeDir = null;
   private transient ClusterMBean cluster;
   private String stageDir;
   private String startupMode;
   private transient CoherenceClusterSystemResourceMBean coherenceClusterSysResMBean;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String uploadDir;
   private int threadPoolSize = 5;
   private int adminPort;
   private boolean messageIdPrefixEnabled;

   private ServerTemplateMBean getServer() {
      return (ServerTemplateMBean)this.getMbean();
   }

   public ServerTemplate(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public static String getNameOfDefaultMigratableTargetFor(ServerMBean s) {
      return s.getName() + " (migratable)";
   }

   public Reader getLogs(String type) {
      String serverName = this.getServer().getName();
      if (serverName != null && !serverName.equals("")) {
         NodeManagerRuntime nm = NodeManagerRuntime.getInstance(this.getServer());

         try {
            return nm.getLog(this.getServer());
         } catch (IOException var6) {
            String message = "Could not get logs for server '" + serverName + "' via Node Manager - reason: " + var6.getMessage();
            return this.printError(message);
         }
      } else {
         String message = "Could not get logs for server '" + serverName + "' via Node Manager - reason: 'Server name is not set'";
         return this.printError(message);
      }
   }

   public String getStagingDirectoryName() {
      if (this.stageDir == null) {
         this.stageDir = this.getDefaultStagingDirName();
         if (DEBUG_DEPLOYER.isEnabled()) {
            Debug.say(this.getMbean().getName() + "\nusing " + this.stageDir + " as staging directory, my name is : " + this.getMbean().getName());
         }
      }

      return this.stageDir;
   }

   public void setStagingDirectoryName(String stageDir) {
      this.stageDir = stageDir;
   }

   public String getUploadDirectoryName() {
      String server_name = this.getMbean().getName();
      if (this.uploadDir == null && server_name != null) {
         this.uploadDir = DomainDir.getPathRelativeServerDirNonCanonical(server_name, "upload");
      }

      return this.uploadDir;
   }

   public void setUploadDirectoryName(String uploadDir) {
      this.uploadDir = uploadDir;
   }

   public String[] getSupportedProtocols() {
      return ProtocolManager.getProtocols();
   }

   public boolean getEnabledForDomainLog() {
      String severity = this.getServer().getLog().getDomainLogBroadcastSeverity();
      int level = Severities.severityStringToNum(severity);
      return level > 0;
   }

   public void setThreadPoolSize(int size) {
      this.threadPoolSize = size;
   }

   public int getThreadPoolSize() {
      return this.threadPoolSize;
   }

   public void setActiveDirectoryName(String path) {
      this.activeDir = path;
   }

   public String getActiveDirectoryName() {
      if (this.activeDir == null) {
         this.activeDir = this.getStagingDirectoryName();
      }

      return this.activeDir;
   }

   public void setCluster(ClusterMBean cluster) {
      ClusterMBean oldCluster = this.cluster;
      this.cluster = cluster;
      if (cluster != null || oldCluster != null) {
         ServerTemplateMBean mbean = this.getServer();
         if (mbean instanceof ServerMBean) {
            if (cluster != null && oldCluster != null) {
               if (cluster.getName().equals(oldCluster.getName())) {
                  return;
               }

               this.deleteDefaultMigratableTarget();
               this.createDefaultMigratableTarget(cluster);
            } else if (cluster == null && oldCluster != null) {
               this.deleteDefaultMigratableTarget();
               mbean.unSet("Cluster");
            } else if (cluster != null && oldCluster == null) {
               this.createDefaultMigratableTarget(cluster);
            }
         } else {
            JTAMigratableTargetMBean jmt;
            if (cluster != null && oldCluster != null) {
               if (cluster.getName().equals(oldCluster.getName())) {
                  return;
               }

               mbean.destroyJTAMigratableTarget();
               mbean.unSet("JTAMigratableTarget");
               jmt = mbean.createJTAMigratableTarget();
               jmt.setCluster(cluster);
               jmt.setStrictOwnershipCheck(false);
               jmt.setMigrationPolicy("manual");
               jmt.addBeanUpdateListener(new JTAMigratableTargetMBeanUpdateListener());
            } else if (cluster == null && oldCluster != null) {
               mbean.destroyJTAMigratableTarget();
               mbean.unSet("JTAMigratableTarget");
               mbean.unSet("Cluster");
            } else if (cluster != null && oldCluster == null) {
               jmt = mbean.getJTAMigratableTarget();
               if (jmt == null) {
                  jmt = mbean.createJTAMigratableTarget();
                  jmt.setCluster(cluster);
                  jmt.setStrictOwnershipCheck(false);
                  jmt.setMigrationPolicy("manual");
                  jmt.addBeanUpdateListener(new JTAMigratableTargetMBeanUpdateListener());
               }
            }

            DomainMBean domain = (DomainMBean)mbean.getParent();
            ServerMBean[] var5 = domain.getServers();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ServerMBean server = var5[var7];
               ServerTemplateMBean srvtemp = server.getServerTemplate();
               if (srvtemp != null && srvtemp.getName().equals(mbean.getName())) {
                  if (((AbstractDescriptorBean)server)._isTransient()) {
                     if (cluster != null && oldCluster != null) {
                        if (cluster.getName().equals(oldCluster.getName())) {
                           return;
                        }

                        this.deleteDefaultMigratableTarget(server);
                        this.createDefaultMigratableTarget(server, cluster);
                     } else if (cluster == null && oldCluster != null) {
                        this.deleteDefaultMigratableTarget(server);
                        this.getMbean().unSet("Cluster");
                     } else if (cluster != null && oldCluster == null) {
                        this.createDefaultMigratableTarget(server, cluster);
                     }
                  } else if (!server.isSet("Cluster")) {
                     if (cluster != null && oldCluster != null) {
                        if (cluster.getName().equals(oldCluster.getName())) {
                           return;
                        }

                        this.deleteDefaultMigratableTarget(server);
                        this.createDefaultMigratableTarget(server, cluster);
                     } else if (cluster == null && oldCluster != null) {
                        this.deleteDefaultMigratableTarget(server);
                        this.getMbean().unSet("Cluster");
                     } else if (cluster != null && oldCluster == null) {
                        this.createDefaultMigratableTarget(server, cluster);
                     }
                  }
               }
            }
         }

      }
   }

   public ClusterMBean getCluster() {
      return this.cluster;
   }

   public void setAdministrationPort(int portArg) {
      this.adminPort = portArg;
   }

   public int getAdministrationPort() {
      return this.adminPort;
   }

   public void setMessageIdPrefixEnabled(boolean prefix) {
      MessageLogger.setUsePrefix(prefix);
      this.messageIdPrefixEnabled = prefix;
   }

   public boolean getMessageIdPrefixEnabled() {
      return this.messageIdPrefixEnabled;
   }

   public String synchronousStart() {
      return this.getStringFromReader(this.start());
   }

   private Reader start() {
      String serverName = this.getServer().getName();
      if (serverName != null && !serverName.equals("")) {
         try {
            NodeManagerRuntime.checkStartPrivileges(serverName, SecurityServiceManager.getCurrentSubject(kernelId));
         } catch (SecurityException var6) {
            return this.printError(var6.getMessage());
         }

         NodeManagerRuntime nm = NodeManagerRuntime.getInstance(this.getServer());

         try {
            nm.start(this.getServer());
            return new StringReader("Server '" + serverName + "' started");
         } catch (IOException var5) {
            String message = "Could not start server '" + serverName + "' via Node Manager - reason: " + var5.getMessage();
            return this.printError(message);
         }
      } else {
         String message = "Could not start server '" + serverName + "' via Node Manager - reason: 'Server name is not set'";
         return this.printError(message);
      }
   }

   public String synchronousKill() {
      return this.getStringFromReader(this.kill());
   }

   private Reader kill() {
      String serverName = this.getServer().getName();
      if (serverName != null && !serverName.equals("")) {
         NodeManagerRuntime nm = NodeManagerRuntime.getInstance(this.getServer());

         try {
            nm.kill(this.getServer());
            return new StringReader("Server killed");
         } catch (IOException var5) {
            String message = "Could not kill server '" + serverName + "' via Node Manager - reason: " + var5.getMessage();
            return this.printError(message);
         }
      } else {
         String message = "Could not start server '" + serverName + "' via Node Manager - reason: 'Server name is not set'";
         return this.printError(message);
      }
   }

   public ServerLifeCycleRuntimeMBean lookupServerLifeCycleRuntime() {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         return runtime.lookupServerLifecycleRuntime(this.getMbean().getName());
      } else {
         return null;
      }
   }

   private String getStringFromReader(Reader r) {
      StringBuffer sb = new StringBuffer();

      try {
         BufferedReader in = null;
         in = new BufferedReader(r);
         String line = null;
         boolean keepReading = true;
         int currentPause = 0;

         while(keepReading) {
            while((line = in.readLine()) != null) {
               currentPause = 0;
               sb.append(line);
               sb.append("\n");
            }

            ++currentPause;
            Thread.sleep(250L);
            if ((long)currentPause == 120L) {
               keepReading = false;
            }
         }

         in.close();
      } catch (Exception var7) {
         sb.append(StackTraceUtils.throwable2StackTrace(var7));
      }

      return sb.toString();
   }

   private Reader printError(String message) {
      ManagementLogger.logNodeManagerError(message);
      return new StringReader("Error: " + message);
   }

   protected void createDefaultMigratableTarget(ClusterMBean cluster) {
      ServerMBean myself = (ServerMBean)this.getMbean();
      this.createDefaultMigratableTarget(myself, cluster);
   }

   protected void createDefaultMigratableTarget(ServerMBean serverMBean, ClusterMBean cluster) {
      DomainMBean domain = (DomainMBean)serverMBean.getParent();
      MigratableTargetConfigProcessor.createDefaultMigratableTargets(domain, serverMBean, cluster);
   }

   protected void deleteDefaultMigratableTarget() {
      ServerMBean myself = (ServerMBean)this.getMbean();
      this.deleteDefaultMigratableTarget(myself);
   }

   protected void deleteDefaultMigratableTarget(ServerMBean serverMBean) {
      serverMBean.destroyJTAMigratableTarget();
      serverMBean.unSet("JTAMigratableTarget");
      MigratableTargetConfigProcessor.destroyDefaultMigratableTarget(serverMBean);
   }

   public Set getServerNames() {
      Set s = new HashSet(1);
      s.add(this.getMbean().getName());
      return s;
   }

   public String getRootDirectory() {
      return DomainDir.getRootDir();
   }

   public String getDefaultStagingDirName() {
      String theName = this.getMbean().getName();
      return theName == null ? null : DomainDir.getPathRelativeServerDir(theName, "stage");
   }

   public String get81StyleDefaultStagingDirName() {
      String theName = this.getMbean().getName();
      return theName == null ? null : DomainDir.getPathRelativeRootDir(theName + File.separator + "stage");
   }

   public void setStartupMode(String startupMode) {
      this.startupMode = startupMode;
   }

   public String getStartupMode() {
      String stMode = System.getProperty("weblogic.management.startupMode");
      return stMode != null ? stMode : this.startupMode;
   }

   public void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean systemResource) {
      if (this.coherenceClusterSysResMBean != null) {
         if (this.coherenceClusterSysResMBean.equals(systemResource)) {
            return;
         }

         try {
            this.coherenceClusterSysResMBean.removeTarget(this.getServer());
         } catch (Exception var3) {
            ManagementLogger.logExceptionInCustomizer(var3);
         }
      }

      this.coherenceClusterSysResMBean = systemResource;
   }

   public CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource() {
      return this.coherenceClusterSysResMBean;
   }
}
