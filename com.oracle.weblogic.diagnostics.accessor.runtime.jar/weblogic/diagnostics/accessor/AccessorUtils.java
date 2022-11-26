package weblogic.diagnostics.accessor;

import java.io.File;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.connector.external.RAUtil;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.configuration.WebServerLogMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JMSRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.management.runtime.SAFRuntimeMBean;
import weblogic.management.runtime.WLDFArchiveRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.servlet.utils.ServletAccessorHelper;

public final class AccessorUtils implements AccessorConstants {
   private static final DebugLogger ACCESSOR_DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static AuthorizationManager am = null;
   private static final AdminResource LOG_VIEW_RESOURCE = new AdminResource("ViewLog", (String)null, (String)null);
   private static final RuntimeAccess runtimeAccess;

   public static void ensureUserAuthorized() throws ManagementException {
      if (am == null) {
         am = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, SecurityServiceManager.getAdministrativeRealmName(), ServiceType.AUTHORIZE);
      }

      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!am.isAccessAllowed(user, LOG_VIEW_RESOURCE, new ResourceIDDContextWrapper())) {
         Loggable l = DiagnosticsLogger.logUserNotAuthorizedToViewLogsLoggable(user.toString());
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   public static boolean isAdminServer() {
      return runtimeAccess.isAdminServer();
   }

   public static Set getAvailableVirtualHosts() {
      VirtualHostMBean[] hosts = runtimeAccess.getDomain().getVirtualHosts();
      HashSet resultSet = new HashSet();
      if (hosts != null) {
         for(int i = 0; i < hosts.length; ++i) {
            if (isDeploymentAvailableOnCurrentServer(hosts[i])) {
               resultSet.add(hosts[i]);
            }
         }
      }

      return resultSet;
   }

   public static Set getAvailableJMSServers() {
      HashSet resultSet = new HashSet();
      JMSRuntimeMBean jmsRuntime = runtimeAccess.getServerRuntime().getJMSRuntime();
      if (jmsRuntime != null) {
         JMSServerRuntimeMBean[] jmsServers = jmsRuntime.getJMSServers();
         if (jmsServers != null) {
            for(int i = 0; i < jmsServers.length; ++i) {
               JMSServerRuntimeMBean jmsServer = jmsServers[i];
               if (jmsServer.getLogRuntime() != null) {
                  resultSet.add(jmsServer.getName());
               } else if (ACCESSOR_DEBUG.isDebugEnabled()) {
                  ACCESSOR_DEBUG.debug("Missing LogRuntimeMBean for " + jmsServer.getName() + ". Accessor will not be created.");
               }
            }
         }
      }

      return resultSet;
   }

   public static Set getAvailableSAFAgents() {
      HashSet resultSet = new HashSet();
      SAFRuntimeMBean safRuntime = runtimeAccess.getServerRuntime().getSAFRuntime();
      if (safRuntime != null) {
         SAFAgentRuntimeMBean[] agents = safRuntime.getAgents();
         if (agents != null) {
            for(int i = 0; i < agents.length; ++i) {
               SAFAgentRuntimeMBean safAgent = agents[i];
               if (safAgent.getLogRuntime() != null) {
                  resultSet.add(safAgent.getName());
               } else if (ACCESSOR_DEBUG.isDebugEnabled()) {
                  ACCESSOR_DEBUG.debug("Missing LogRuntimeMBean for " + safAgent.getName() + ". Accessor will not be created.");
               }
            }
         }
      }

      return resultSet;
   }

   public static Set getAvailableCustomArchives() {
      WLDFArchiveRuntimeMBean[] archives = runtimeAccess.getServerRuntime().getWLDFRuntime().getWLDFArchiveRuntimes();
      HashSet resultSet = new HashSet();
      if (archives != null) {
         for(int i = 0; i < archives.length; ++i) {
            if (archives[i].getName().startsWith("CUSTOM/")) {
               resultSet.add(archives[i]);
            }
         }
      }

      return resultSet;
   }

   public static boolean isDeploymentAvailableOnCurrentServer(DeploymentMBean deployment) {
      TargetMBean[] targets = deployment.getTargets();
      if (targets == null) {
         return false;
      } else {
         for(int i = 0; i < targets.length; ++i) {
            TargetMBean target = targets[i];
            String targetName = targets[i].getName();
            if (target instanceof ServerMBean && targetName.equals(runtimeAccess.getServerName())) {
               return true;
            }

            if (target instanceof ClusterMBean) {
               ClusterMBean cl = runtimeAccess.getServer().getCluster();
               if (cl != null && cl.getName().equals(targetName)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean compareMaps(Map a, Map b) {
      if (a.size() != b.size()) {
         return false;
      } else {
         Set currentKeys = a.keySet();
         Iterator iter = currentKeys.iterator();

         Object currentValue;
         Object oldValue;
         label34:
         do {
            do {
               if (!iter.hasNext()) {
                  return true;
               }

               Object key = iter.next();
               currentValue = a.get(key);
               oldValue = b.get(key);
               if (currentValue == null || oldValue == null) {
                  continue label34;
               }
            } while(currentValue.equals(oldValue));

            return false;
         } while(currentValue == null && oldValue == null);

         return false;
      }
   }

   public static String getDiagnosticStoreDirectory() {
      ServerMBean serverConfig = runtimeAccess.getServer();
      WLDFServerDiagnosticMBean wldfConfig = serverConfig.getServerDiagnosticConfig();
      String storeDir = wldfConfig.getDiagnosticStoreDir();
      File f = new File(storeDir);
      if (!f.isAbsolute()) {
         storeDir = DomainDir.getPathRelativeServerDir(runtimeAccess.getServerName(), storeDir);
         f = new File(storeDir);
      }

      if (!f.exists()) {
         f.mkdirs();
      }

      storeDir = f.getAbsolutePath();
      return storeDir;
   }

   static Map getParamsForServerLog() {
      LogFileMBean log = runtimeAccess.getServer().getLog();
      String logFileName = log.getLogFilePath();
      String rotationDir = log.getLogRotationDirPath();
      Map r = new HashMap();
      r.put("logFilePath", logFileName);
      r.put("storeDir", getDiagnosticStoreDirectory());
      r.put("logRotationDir", rotationDir);
      return r;
   }

   static Map getParamsForDataSourceLog() {
      LogFileMBean log = runtimeAccess.getServer().getDataSource().getDataSourceLogFile();
      String logFileName = log.getLogFilePath();
      String rotationDir = log.getLogRotationDirPath();
      Map r = new HashMap();
      r.put("logFilePath", logFileName);
      r.put("storeDir", getDiagnosticStoreDirectory());
      r.put("logRotationDir", rotationDir);
      return r;
   }

   static Map getParamsForDomainLog() {
      LogFileMBean log = runtimeAccess.getDomain().getLog();
      String logFileName = log.getLogFilePath();
      String rotationDir = log.getLogRotationDirPath();
      Map r = new HashMap();
      r.put("logFilePath", logFileName);
      r.put("storeDir", getDiagnosticStoreDirectory());
      r.put("logRotationDir", rotationDir);
      return r;
   }

   static Map getParamsForHTTPAccessLog(String[] tokens) throws UnknownLogTypeException {
      WebServerMBean webServer = runtimeAccess.getServer().getWebServer();
      String virtualHostName = tokens.length == 2 ? tokens[1] : null;
      if (virtualHostName != null && virtualHostName.length() > 0) {
         Iterator virtualHosts = getAvailableVirtualHosts().iterator();

         while(virtualHosts.hasNext()) {
            WebServerMBean vh = (WebServerMBean)virtualHosts.next();
            if (vh.getName().equals(virtualHostName)) {
               webServer = vh;
               break;
            }
         }
      }

      return getParamsForHTTPAccessLog(webServer);
   }

   public static Map getParamsForHTTPAccessLog(WebServerMBean webServer) {
      WebServerLogMBean log = webServer.getWebServerLog();
      String webServerName = webServer.getName();
      String logFileName = ServletAccessorHelper.getAccessLogFilePath(webServerName);
      String rotationDir = ServletAccessorHelper.getAccessLogRotationDirPath(webServerName);
      Map r = new HashMap();
      r.put("logFilePath", logFileName);
      r.put("storeDir", getDiagnosticStoreDirectory());
      r.put("logRotationDir", rotationDir);
      if (log.getLogFileFormat().equals("extended")) {
         r.put("elfFields", log.getELFFields());
      }

      return r;
   }

   static Map getParamsForDiagnosticDataArchive() {
      Map r = new HashMap();
      WLDFServerDiagnosticMBean wldfConfig = runtimeAccess.getServer().getServerDiagnosticConfig();
      String archiveType = wldfConfig.getDiagnosticDataArchiveType();
      r.put("storeDir", getDiagnosticStoreDirectory());
      if (archiveType.equals("JDBCArchive")) {
         JDBCSystemResourceMBean jdbcResourceMBean = wldfConfig.getDiagnosticJDBCResource();
         String jndiName = null;
         String schemaName = wldfConfig.getDiagnosticJDBCSchemaName();
         if (jdbcResourceMBean != null) {
            JDBCDataSourceBean jdbcResource = jdbcResourceMBean.getJDBCResource();
            JDBCDataSourceParamsBean jdbcParams = jdbcResource.getJDBCDataSourceParams();
            String[] jndiNames = jdbcParams.getJNDINames();
            if (jndiNames != null && jndiNames.length > 0) {
               jndiName = jndiNames[0];
            }
         }

         if (jndiName != null) {
            r.put("jndiName", jndiName);
            if (schemaName != null) {
               r.put("schemaName", schemaName);
            }
         } else {
            DiagnosticsLogger.logIncompleteJDBCArchiveConfiguration();
         }
      }

      return r;
   }

   static Map getParamsForGenericDataArchive() {
      Map r = new HashMap();
      r.put("storeDir", getDiagnosticStoreDirectory());
      return r;
   }

   static Map getParamsForWebAppLog(String[] tokens) throws UnknownLogTypeException {
      Map r = new HashMap();
      int len = tokens.length;
      String webServerName = len >= 2 ? tokens[1] : null;
      String contextPath = "";
      if (len >= 3) {
         for(int i = 2; i < tokens.length; ++i) {
            contextPath = contextPath + "/" + tokens[i];
         }
      } else {
         contextPath = "/";
      }

      String logFileName = ServletAccessorHelper.getLogFileName(webServerName, contextPath);
      if (logFileName == null) {
         throw new UnknownLogTypeException("Logs dont exist for the webapp");
      } else {
         String logRotationDir = ServletAccessorHelper.getLogFileRotationDir(webServerName, contextPath);
         r.put("logFilePath", logFileName);
         r.put("storeDir", getDiagnosticStoreDirectory());
         r.put("logRotationDir", logRotationDir);
         return r;
      }
   }

   static Map getParamsForConnectorLog(String logicalName) throws UnknownLogTypeException {
      Map r = new HashMap();
      int index = "ConnectorLog".length() + 1;
      String key = logicalName.substring(index);
      String logFileName = RAUtil.getLogFileName(key);
      if (logFileName == null) {
         throw new UnknownLogTypeException("Logs dont exist for the outbound connection " + logicalName);
      } else {
         String logRotationDir = RAUtil.getLogFileRotationDir(key);
         r.put("logFilePath", logFileName);
         r.put("storeDir", getDiagnosticStoreDirectory());
         r.put("logRotationDir", logRotationDir);
         return r;
      }
   }

   static Map getParamsForJMSMessageLog(String logicalName) throws UnknownLogTypeException {
      String jmsServerName = getJMSServerName(logicalName);
      JMSServerRuntimeMBean[] jmsServers = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().getJMSRuntime().getJMSServers();
      JMSServerRuntimeMBean jmsServer = null;
      if (jmsServers != null) {
         JMSServerRuntimeMBean[] var4 = jmsServers;
         int var5 = jmsServers.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            JMSServerRuntimeMBean jmsSrvr = var4[var6];
            if (jmsSrvr.getName().equals(jmsServerName)) {
               jmsServer = jmsSrvr;
            }
         }
      }

      if (jmsServer == null) {
         throw new UnknownLogTypeException("JMS Server does not exist " + jmsServerName);
      } else {
         return getParamsForJMSMessageLog(jmsServer);
      }
   }

   private static String getJMSServerName(String logicalName) {
      int index = "JMSMessageLog".length() + 1;
      return logicalName.substring(index);
   }

   public static Map getParamsForJMSMessageLog(JMSServerRuntimeMBean jmsServer) {
      LogRuntimeMBean logRuntime = jmsServer.getLogRuntime();
      if (logRuntime == null) {
         return null;
      } else {
         String logFileName = logRuntime.getCurrentLogFile();
         String logRotationDir = logRuntime.getLogRotationDir();
         Map r = new HashMap();
         r.put("logFilePath", logFileName);
         r.put("logRotationDir", logRotationDir);
         r.put("storeDir", getDiagnosticStoreDirectory());
         return r;
      }
   }

   static Map getParamsForJMSSAFMessageLog(String logicalName) throws UnknownLogTypeException {
      String safAgentName = getSAFAgentName(logicalName);
      SAFAgentRuntimeMBean[] safAgents = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().getSAFRuntime().getAgents();
      SAFAgentRuntimeMBean safAgent = null;
      if (safAgents != null) {
         SAFAgentRuntimeMBean[] var4 = safAgents;
         int var5 = safAgents.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            SAFAgentRuntimeMBean agent = var4[var6];
            if (agent.getName().equals(safAgentName)) {
               safAgent = agent;
            }
         }
      }

      if (safAgent == null) {
         throw new UnknownLogTypeException("SAF Agent does not exist " + safAgent);
      } else {
         return getParamsForJMSSAFMessageLog(safAgent);
      }
   }

   public static String getSAFAgentName(String logicalName) {
      int index = "JMSSAFMessageLog".length() + 1;
      return logicalName.substring(index);
   }

   public static Map getParamsForJMSSAFMessageLog(SAFAgentRuntimeMBean safAgent) {
      LogRuntimeMBean logRuntime = safAgent.getLogRuntime();
      if (logRuntime == null) {
         return null;
      } else {
         String logFileName = logRuntime.getCurrentLogFile();
         String logRotationDir = logRuntime.getLogRotationDir();
         Map r = new HashMap();
         r.put("logFilePath", logFileName);
         r.put("logRotationDir", logRotationDir);
         r.put("storeDir", getDiagnosticStoreDirectory());
         return r;
      }
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }
}
