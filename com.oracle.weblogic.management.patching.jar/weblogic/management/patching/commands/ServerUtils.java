package weblogic.management.patching.commands;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.naming.NamingException;
import weblogic.cluster.RemoteClusterServicesOperations;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jndi.Environment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PartitionUserFileSystemMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.ArrayUtils;

public class ServerUtils {
   private static final long DEFAULT_RECONNECT_TIMEOUT;
   private static final long DEFAULT_POLL_INTERVAL;
   private static final String READY_APP_URL = "/weblogic/ready";
   private static final AuthenticatedSubject kernelId;
   private static boolean ENABLE;

   private void validateServer(String serverName) throws CommandException {
      ServerMBean serverMBean = getServer(serverName);
      if (serverMBean == null) {
         throw new CommandException(PatchingMessageTextFormatter.getInstance().getInvalidServer(serverName));
      }
   }

   protected List castFailoverGroups(ArrayList failoverGroupsToCast) {
      if (failoverGroupsToCast == null) {
         return null;
      } else {
         List castFailoverGroups = new ArrayList();
         castFailoverGroups.addAll(failoverGroupsToCast);
         return castFailoverGroups;
      }
   }

   public String failoverGroupsToString(ArrayList failoverGroups) {
      return this.failoverGroupsToString(this.castFailoverGroups(failoverGroups));
   }

   public String failoverGroupsToString(List failoverGroups) {
      StringBuffer sb = new StringBuffer();
      if (failoverGroups == null) {
         sb.append("null");
      } else {
         sb.append("[");

         for(Iterator var3 = failoverGroups.iterator(); var3.hasNext(); sb.append("], ")) {
            List group = (List)var3.next();
            sb.append("group[");
            if (group == null) {
               sb.append("null");
            } else {
               Iterator var5 = group.iterator();

               while(var5.hasNext()) {
                  String server = (String)var5.next();
                  sb.append(server);
                  sb.append(",");
               }
            }
         }

         sb.append("]");
      }

      return sb.toString();
   }

   public List createFailoverGroups(String[] pendingServers, String[] updatingServers) {
      if (pendingServers == null && updatingServers == null) {
         return null;
      } else if (pendingServers != null && updatingServers != null) {
         ArrayList alistPendingServers = new ArrayList();
         ArrayUtils.addAll(alistPendingServers, pendingServers);
         ArrayList alistUpdatingServers = new ArrayList();
         ArrayUtils.addAll(alistUpdatingServers, updatingServers);
         ArrayList failoverGroups = new ArrayList();
         failoverGroups.add(alistPendingServers);
         failoverGroups.add(alistUpdatingServers);
         return failoverGroups;
      } else {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().getSingleNullFailoverGroup(pendingServers == null ? "null" : ArrayUtils.toString(pendingServers), updatingServers == null ? "null" : ArrayUtils.toString(updatingServers)));
      }
   }

   public void applyFailoverGroups(String serverName, ArrayList failoverGroups) throws CommandException {
      this.applyFailoverGroups(serverName, this.castFailoverGroups(failoverGroups));
   }

   public void applyFailoverGroups(String partitionName, String serverName, List failoverGroups) throws CommandException {
      this.validateServer(serverName);
      boolean ret = false;

      try {
         RemoteClusterServicesOperations remoteCluster = this.getClusterServiceOperationsRemote(serverName, 0L);
         remoteCluster.setFailoverServerGroups(partitionName, failoverGroups);
         ret = true;
      } catch (Exception var7) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to update failovergroups on server " + serverName + " for partition: " + partitionName, var7);
         }

         CommandFailedNoTraceException cfnte = new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getUpdateFailoverGroupsError(serverName, var7.getClass() + ":" + var7.getMessage()));
         cfnte.initCause(var7);
         throw cfnte;
      }
   }

   public void applyFailoverGroups(String serverName, List failoverGroups) throws CommandException {
      this.validateServer(serverName);
      boolean ret = false;

      try {
         RemoteClusterServicesOperations remoteCluster = this.getClusterServiceOperationsRemote(serverName, 0L);
         remoteCluster.setFailoverServerGroups(failoverGroups);
         ret = true;
      } catch (Exception var6) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to update failovergroups on server " + serverName, var6);
         }

         CommandFailedNoTraceException cfnte = new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getUpdateFailoverGroupsError(serverName, var6.getClass() + ":" + var6.getMessage()));
         cfnte.initCause(var6);
         throw cfnte;
      }
   }

   public void enableSessionHandling(String serverName, ArrayList failoverGroups) throws RemoteException, NamingException {
      this.enableSessionHandling(serverName, this.castFailoverGroups(failoverGroups));
   }

   public void enableSessionHandling(String serverName, List failoverGroups) throws RemoteException, NamingException {
      this.applySessionHandlingSetting(ENABLE, serverName, failoverGroups);
   }

   public void applySessionHandlingSetting(boolean enable, String serverName, List failoverGroups) throws RemoteException, NamingException {
      RemoteClusterServicesOperations remoteCluster = this.getClusterServiceOperationsRemote(serverName, 0L);
      remoteCluster.setSessionLazyDeserializationEnabled(enable);
      remoteCluster.setCleanupOrphanedSessionsEnabled(enable);
      remoteCluster.setSessionStateQueryProtocolEnabled(enable);
      remoteCluster.setSessionReplicationOnShutdownEnabled(enable);
      remoteCluster.setFailoverServerGroups(failoverGroups);
   }

   public void applySessionHandlingSetting(final boolean enable, final String partitionName, final String serverName, final List failoverGroups, final boolean disableTimeout, final int sessionHandlingTimeout) throws RemoteException, NamingException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext("DOMAIN");

      try {
         ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public Object call() throws Exception {
               RemoteClusterServicesOperations remoteCluster = ServerUtils.this.getClusterServiceOperationsRemote(serverName, 0L);
               remoteCluster.setSessionLazyDeserializationEnabled(partitionName, enable);
               remoteCluster.setCleanupOrphanedSessionsEnabled(partitionName, enable);
               if (disableTimeout && !enable) {
                  remoteCluster.disableSessionStateQueryProtocolAfter(partitionName, sessionHandlingTimeout);
               } else {
                  remoteCluster.setSessionStateQueryProtocolEnabled(partitionName, enable);
               }

               remoteCluster.setSessionReplicationOnShutdownEnabled(partitionName, enable);
               remoteCluster.setFailoverServerGroups(partitionName, failoverGroups);
               return true;
            }
         });
      } catch (ExecutionException var11) {
         Throwable cause = var11.getCause();
         if (cause instanceof RemoteException) {
            throw (RemoteException)cause;
         } else {
            throw new RemoteException(var11.getMessage());
         }
      }
   }

   public void applySessionHandlingSetting(boolean enable, String serverName, List failoverGroups, boolean disableTimeout, int sessionHandlingTimeout) throws RemoteException, NamingException {
      RemoteClusterServicesOperations remoteCluster = this.getClusterServiceOperationsRemote(serverName, 0L);
      remoteCluster.setSessionLazyDeserializationEnabled(enable);
      remoteCluster.setCleanupOrphanedSessionsEnabled(enable);
      if (disableTimeout && !enable) {
         remoteCluster.disableSessionStateQueryProtocolAfter(sessionHandlingTimeout);
      } else {
         remoteCluster.setSessionStateQueryProtocolEnabled(enable);
      }

      remoteCluster.setSessionReplicationOnShutdownEnabled(enable);
      remoteCluster.setFailoverServerGroups(failoverGroups);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private RemoteClusterServicesOperations getClusterServiceOperationsRemote(String serverName, long responseReadTimeout) throws UnknownHostException, NamingException {
      int timeout = ManagementService.getRuntimeAccess(kernelId).getDomain().getJMX().getInvocationTimeoutSeconds();
      Environment env = new Environment();
      if (responseReadTimeout > 0L) {
         env.setResponseReadTimeout(responseReadTimeout);
      } else if (timeout > 0) {
         env.setResponseReadTimeout((long)(timeout * 1000));
      }

      if (timeout > 0) {
         env.setConnectionTimeout((long)(timeout * 1000));
      }

      String adminstrationURL = null;

      try {
         adminstrationURL = getURLManagerService().findAdministrationURL(serverName);
      } catch (UnknownHostException var8) {
         if (this.isRunning(serverName)) {
            adminstrationURL = this.checkServerReconnectForURL(serverName);
         }

         if (adminstrationURL == null) {
            throw var8;
         }
      }

      env.setProviderUrl(adminstrationURL);
      RemoteClusterServicesOperations remote = (RemoteClusterServicesOperations)PortableRemoteObject.narrow(env.getContext().lookup("weblogic.cluster.RemoteClusterServicesOperations"), RemoteClusterServicesOperations.class);
      return remote;
   }

   private String checkServerReconnectForURL(final String serverName) {
      String adminServerName = this.getServerMBean().getName();
      ServerLifeCycleRuntimeMBean lifeCycleRuntime = this.getServerLifeCycleRuntimeMBeanWithoutValidation(adminServerName);
      String adminServerState = lifeCycleRuntime.getState();
      ServerRuntimeMBean serverRuntime = this.getServerRuntimeMBean();

      while(!"RUNNING".equals(lifeCycleRuntime.getState()) && !serverRuntime.isStartupAbortedInAdminState()) {
         try {
            String administrationURL = getURLManagerService().findAdministrationURL(serverName);
            if (administrationURL != null) {
               return administrationURL;
            }
         } catch (RemoteException var13) {
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var12) {
         }
      }

      TimeoutUtils timeoutUtils = new TimeoutUtils();
      long timeout = timeoutUtils.convertTimeoutByPercentageFactor(DEFAULT_RECONNECT_TIMEOUT);
      long pollInterval = timeoutUtils.convertIntervalByFactorIfLarger(DEFAULT_POLL_INTERVAL);
      StatusPollerResult serverReconnectStatusPoller = new StatusPollerResult() {
         public String administrationURL;

         public boolean checkStatus() {
            if (ServerUtils.this.isRunning(serverName)) {
               try {
                  this.administrationURL = ServerUtils.getURLManagerService().findAdministrationURL(serverName);
                  if (this.administrationURL != null) {
                     return true;
                  }
               } catch (RemoteException var2) {
               }

               return false;
            } else {
               return true;
            }
         }

         public String getPollingDescription() {
            return "ServerReconnect";
         }

         public String getResult() {
            return this.administrationURL;
         }
      };
      (new TimedStatusPoller()).pollStatusWithTimeout(timeout, pollInterval, serverReconnectStatusPoller);
      return (String)serverReconnectStatusPoller.getResult();
   }

   public static ServerLifeCycleRuntimeMBean getServerLifeCycleRuntimeMBean(String serverName) throws CommandException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         if (getServer(serverName) == null) {
            throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getInvalidServer(serverName));
         } else {
            return runtime.lookupServerLifecycleRuntime(serverName);
         }
      } else {
         return null;
      }
   }

   public ServerLifeCycleRuntimeMBean getServerLifeCycleRuntimeMBeanWithoutValidation(String serverName) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         DomainAccess runtime = ManagementService.getDomainAccess(kernelId);
         return runtime.lookupServerLifecycleRuntime(serverName);
      } else {
         return null;
      }
   }

   public static ServerMBean getServer(String name) {
      ServerMBean[] servers = ManagementService.getRuntimeAccess(kernelId).getDomain().getServers();
      ServerMBean[] var2 = servers;
      int var3 = servers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServerMBean mbean = var2[var4];
         if (mbean.getName().equals(name)) {
            return mbean;
         }
      }

      return null;
   }

   public static long getServerActivationTime() {
      long activationTime = -1L;
      ServerRuntimeMBean srmb = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      if (srmb != null) {
         activationTime = srmb.getActivationTime();
      }

      return activationTime;
   }

   public boolean isRunning(String serverName) {
      boolean isRunning = false;
      ServerLifeCycleRuntimeMBean slrmb = null;
      slrmb = this.getServerLifeCycleRuntimeMBeanWithoutValidation(serverName);
      if (slrmb != null) {
         String state = slrmb.getState();
         if (!"SHUTDOWN".equals(state) && !"FAILED_NOT_RESTARTABLE".equals(state) && !"FAILED".equals(state)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Server " + serverName + " found to be running in state: " + state);
            }

            isRunning = true;
         }
      }

      return isRunning;
   }

   public boolean isAtLeastAdminState(String serverName) {
      boolean isAtLeastAdminState = false;
      ServerLifeCycleRuntimeMBean slrmb = null;
      slrmb = this.getServerLifeCycleRuntimeMBeanWithoutValidation(serverName);
      if (slrmb != null) {
         String state = slrmb.getState();
         if ("ADMIN".equals(state) || "RUNNING".equals(state)) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Server " + serverName + " found to be at least admin in state: " + state);
            }

            isAtLeastAdminState = true;
         }
      }

      return isAtLeastAdminState;
   }

   public ServerMBean getServerMBean() {
      return ManagementService.getRuntimeAccess(kernelId).getServer();
   }

   public ServerRuntimeMBean getServerRuntimeMBean() {
      return ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   public String getPartitionSystemRoot(String partitionName) {
      PartitionMBean partitionMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      PartitionFileSystemMBean pfs = partitionMBean.getSystemFileSystem();
      String rootDirectory = pfs.getRoot();
      return rootDirectory;
   }

   public String getPartitionUserRoot(String partitionName) {
      PartitionMBean partitionMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      PartitionUserFileSystemMBean pfs = partitionMBean.getUserFileSystem();
      String rootDirectory = pfs.getRoot();
      return rootDirectory;
   }

   public String resolvePath(String serverName, String partitionName, String path) {
      String rootDir = this.getPartitionUserRoot(partitionName);
      String relPath = path;
      String domainRoot = this.getRootDirectoryForServer(serverName);
      if (path.contains(rootDir)) {
         relPath = this.getRelativePath(path, domainRoot);
      } else if (!Paths.get(path).isAbsolute()) {
         relPath = this.getRelativePath(rootDir, domainRoot) + File.separator + path;
      }

      return relPath;
   }

   public String getRootDirectoryForServer(String serverName) {
      return getServer(serverName).getRootDirectory();
   }

   public String getRelativePath(String path, String rootDir) {
      String relativeDirectory = path;
      if (path.contains(rootDir)) {
         relativeDirectory = (new File(rootDir)).toURI().relativize((new File(path)).toURI()).getPath();
      }

      return relativeDirectory;
   }

   public String getStagingModeForServer(String serverName) {
      String serverStagingMode = null;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean[] serverMBeans = domain.getServers();
      if (serverName != null && !serverName.isEmpty() && serverMBeans != null) {
         ServerMBean[] var5 = serverMBeans;
         int var6 = serverMBeans.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ServerMBean serverMBean = var5[var7];
            if (serverName.equals(serverMBean.getName())) {
               serverStagingMode = serverMBean.getStagingMode();
               if (serverStagingMode == null || serverStagingMode.isEmpty()) {
                  serverStagingMode = null;
               }
               break;
            }
         }
      }

      return serverStagingMode;
   }

   public static void pollReadyApp(String serverName, long readyCheckAppsTimeoutInMin) throws CommandException {
      long endTime = System.currentTimeMillis() + readyCheckAppsTimeoutInMin * 60L * 1000L;

      while(!areAppsReady(serverName)) {
         long remaining = endTime - System.currentTimeMillis();
         if (remaining < 0L) {
            throw new CommandException(PatchingLogger.timeoutPollingReadyApp(serverName));
         }

         try {
            Thread.sleep(5000L);
         } catch (InterruptedException var8) {
         }
      }

   }

   private static boolean areAppsReady(String serverName) {
      boolean ret = false;
      int responseCode = -1;
      HttpURLConnection conn = null;

      try {
         String serverURL = findAdministrationURL(serverName);
         URL url = new URL(serverURL + "/weblogic/ready");
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("connecting to " + url.toString() + " for server: " + serverName);
         }

         conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("GET");
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.connect();
         responseCode = conn.getResponseCode();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("got response: " + responseCode + " for server: " + serverName);
         }
      } catch (UnknownHostException var16) {
         PatchingLogger.logFindURLError(var16);
      } catch (IOException var17) {
         PatchingLogger.logErrorOpeningURL(var17);
      } finally {
         if (conn != null) {
            try {
               conn.disconnect();
            } catch (Exception var15) {
            }
         }

      }

      if (responseCode == 200) {
         ret = true;
      }

      return ret;
   }

   private static String findAdministrationURL(String serverName) throws UnknownHostException {
      URLManagerService service = (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
      String serverURL = service.findAdministrationURL(serverName);
      if (serverURL == null) {
         throw new UnknownHostException("WLS cannot identify administration URL based on " + serverName);
      } else {
         return service.normalizeToHttpProtocol(serverURL);
      }
   }

   static {
      DEFAULT_RECONNECT_TIMEOUT = TimeUnit.MILLISECONDS.convert(10L, TimeUnit.MINUTES);
      DEFAULT_POLL_INTERVAL = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.SECONDS);
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ENABLE = true;
   }
}
