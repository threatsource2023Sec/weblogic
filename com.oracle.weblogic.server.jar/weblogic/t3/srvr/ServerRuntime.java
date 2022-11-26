package weblogic.t3.srvr;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.glassfish.hk2.api.ServiceHandle;
import weblogic.Home;
import weblogic.version;
import weblogic.cluster.singleton.ClusterMasterDiscoverer;
import weblogic.descriptor.DescriptorBean;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.PartitionRuntimeStateManagerContract;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.ChannelImportExportService;
import weblogic.management.logging.LogBroadcaster;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGOperation;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.Service;
import weblogic.management.runtime.AggregateProgressMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.AsyncReplicationRuntimeMBean;
import weblogic.management.runtime.BatchJobRepositoryRuntimeMBean;
import weblogic.management.runtime.ClassLoaderRuntimeMBean;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.EntityCacheCumulativeRuntimeMBean;
import weblogic.management.runtime.EntityCacheCurrentStateRuntimeMBean;
import weblogic.management.runtime.ExecuteQueueRuntimeMBean;
import weblogic.management.runtime.JDBCServiceRuntimeMBean;
import weblogic.management.runtime.JMSRuntimeMBean;
import weblogic.management.runtime.JTARuntimeMBean;
import weblogic.management.runtime.JVMRuntimeMBean;
import weblogic.management.runtime.JoltConnectionServiceRuntimeMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.management.runtime.LogBroadcasterRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.MANAsyncReplicationRuntimeMBean;
import weblogic.management.runtime.MANReplicationRuntimeMBean;
import weblogic.management.runtime.MailSessionRuntimeMBean;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.MessagingBridgeRuntimeMBean;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PathServiceRuntimeMBean;
import weblogic.management.runtime.PersistentStoreRuntimeMBean;
import weblogic.management.runtime.RequestClassRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFRuntimeMBean;
import weblogic.management.runtime.SNMPAgentRuntimeMBean;
import weblogic.management.runtime.ServerChannelRuntimeMBean;
import weblogic.management.runtime.ServerLogRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.ServerSecurityRuntimeMBean;
import weblogic.management.runtime.ServerStates;
import weblogic.management.runtime.SingleSignOnServicesRuntimeMBean;
import weblogic.management.runtime.SocketRuntime;
import weblogic.management.runtime.ThreadPoolRuntimeMBean;
import weblogic.management.runtime.TimeServiceRuntimeMBean;
import weblogic.management.runtime.TimerRuntimeMBean;
import weblogic.management.runtime.WANReplicationRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.management.runtime.WTCRuntimeMBean;
import weblogic.management.runtime.WebServerRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.management.runtime.WseeClusterFrontEndRuntimeMBean;
import weblogic.management.runtime.WseeWsrmRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.Operation;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.PartitionUtils;
import weblogic.opatch.OPatchUtil;
import weblogic.protocol.AdminServerIdentity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.rjvm.JVMID;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.RemoteLifeCycleOperations;
import weblogic.server.ServerLifeCycleRuntime;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServerLogger;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SocketMuxer;
import weblogic.utils.Classpath;
import weblogic.utils.StackTraceUtils;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public final class ServerRuntime extends RuntimeMBeanDelegate implements ServerRuntimeMBean, HealthFeedback {
   private static final long serialVersionUID = 3862450250430200114L;
   private static ServerRuntime singleton;
   private final WebLogicServer server;
   private ConnectorServiceRuntimeMBean connectorServiceRuntime;
   private JDBCServiceRuntimeMBean jdbcRuntime;
   private JMSRuntimeMBean jmsRuntime;
   private JTARuntimeMBean jtaRuntime;
   private JVMRuntimeMBean jvmRuntime;
   private SAFRuntimeMBean safRuntime;
   private ClusterRuntimeMBean clusterRuntime;
   private EntityCacheCurrentStateRuntimeMBean ecCurrStateRuntime;
   private EntityCacheCumulativeRuntimeMBean ecCumRuntime;
   private EntityCacheCumulativeRuntimeMBean ecHistRuntime;
   private ExecuteQueueRuntimeMBean executeQueueRuntime;
   private ServerSecurityRuntimeMBean serverSecurityRuntime;
   private SingleSignOnServicesRuntimeMBean ssoRuntime;
   private ThreadPoolRuntimeMBean threadPoolRuntime;
   private ClassLoaderRuntimeMBean classLoaderRuntime;
   private final Set executeQueueRuntimes = new HashSet();
   private final CopyOnWriteArraySet workManagerRuntimes = new CopyOnWriteArraySet();
   private final Set minThreadsConstraintRuntimes = new HashSet();
   private final Set maxThreadsConstraintRuntimes = new HashSet();
   private final Set applicationRuntimes = Collections.synchronizedSet(new HashSet());
   private final Set pendingRestartSystemResources = new HashSet();
   private final Map libraryRuntimes = Collections.synchronizedMap(new HashMap());
   private final Set channelRuntimes = new HashSet();
   private final Set webServerRuntimes = new HashSet();
   private final Map persistentStoreRuntimes = new HashMap();
   private MANReplicationRuntimeMBean manReplicationRuntime;
   private WANReplicationRuntimeMBean wanReplicationRuntime;
   private MANAsyncReplicationRuntimeMBean manAsyncReplicationRuntime;
   private AsyncReplicationRuntimeMBean asyncReplicationRuntime = null;
   private WLDFRuntimeMBean wldfRuntime;
   private WTCRuntimeMBean wtcRuntime;
   private JoltConnectionServiceRuntimeMBean joltRuntime;
   private Set pathServiceRuntimes = new HashSet();
   private PathServiceRuntimeMBean domainScopePathServiceRuntimeMBean;
   private String currentMachine = "";
   private final Set requestClassRuntimes = new HashSet();
   private final Set mailSessionRuntimes = new HashSet();
   private boolean restartRequired;
   private TimerRuntimeMBean timerRuntime;
   private TimeServiceRuntimeMBean timeServiceRuntime;
   private ServerLogRuntimeMBean logRuntime;
   private SNMPAgentRuntimeMBean snmpAgentRuntime;
   private Set messagingBridgeRuntimes = new HashSet();
   private WseeWsrmRuntimeMBean wseeWsrmRuntime;
   private WseeClusterFrontEndRuntimeMBean wseeClusterFrontEndRuntime;
   private final Map partitionRuntimes = new HashMap();
   private Set hk2Services;
   private ConcurrentManagedObjectsRuntimeMBean concurrentManagedObjectsRuntime;
   private BatchJobRepositoryRuntimeMBean batchJobRepositoryRuntimeMBean;
   private ServiceHandle partitionManagerService;
   private ConcurrentHashMap domainRGStates = new ConcurrentHashMap();
   private final RuntimeAccess runtimeAccess;
   private ConcurrentHashMap rgOpInProgress = new ConcurrentHashMap();
   private ServiceHandle partitionRuntimeStateManager;
   private AggregateProgressMBean aggregateProgressMBean;
   private boolean sitConfigState;
   private String mwHome;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int stateVal = 9;
   private HealthState healthState = new HealthState(0);
   private Set internalApps;
   private String curDir;

   public static synchronized ServerRuntime init(RuntimeAccess runtimeAccess) throws ManagementException {
      if (singleton != null) {
         throw new IllegalStateException("Attempt to double initialize");
      } else {
         singleton = new ServerRuntime(runtimeAccess);
         return singleton;
      }
   }

   public static synchronized ServerRuntime theOne() {
      return singleton;
   }

   private ServerRuntime(RuntimeAccess runtimeAccess) throws ManagementException {
      super(runtimeAccess.getServerName(), (RuntimeMBean)null, true, runtimeAccess.getServer());
      this.runtimeAccess = runtimeAccess;
      this.server = (WebLogicServer)GlobalServiceLocator.getServiceLocator().getService(WebLogicServer.class, new Annotation[0]);
      runtimeAccess.addRegistrationHandler(this.createRegistrationHandler());
      this.server.initializeServerRuntime(this);
      this.partitionManagerService = GlobalServiceLocator.getServiceLocator().getServiceHandle(PartitionManagerService.class, new Annotation[0]);
      this.partitionRuntimeStateManager = GlobalServiceLocator.getServiceLocator().getServiceHandle(PartitionRuntimeStateManagerContract.class, new Annotation[0]);
   }

   private RegistrationHandler createRegistrationHandler() {
      return new RegistrationHandler() {
         public void registered(RuntimeMBean runtime, DescriptorBean config) {
            if (runtime instanceof ApplicationRuntimeMBean && runtime.getParent() instanceof ServerRuntimeMBean) {
               ServerRuntime.this.applicationRuntimes.add((ApplicationRuntimeMBean)runtime);
            } else if (runtime instanceof LibraryRuntimeMBean && runtime.getParent() instanceof ServerRuntimeMBean) {
               ServerRuntime.this.libraryRuntimes.put(runtime.getName(), (LibraryRuntimeMBean)runtime);
            }

         }

         public void unregistered(RuntimeMBean runtime) {
            if (runtime instanceof ApplicationRuntimeMBean && runtime.getParent() instanceof ServerRuntimeMBean) {
               ServerRuntime.this.applicationRuntimes.remove(runtime);
            } else if (runtime instanceof LibraryRuntimeMBean && runtime.getParent() instanceof ServerRuntimeMBean) {
               ServerRuntime.this.libraryRuntimes.remove(runtime.getName());
            }

         }

         public void registeredCustom(ObjectName oname, Object custom) {
         }

         public void unregisteredCustom(ObjectName custom) {
         }

         public void registered(Service bean) {
         }

         public void unregistered(Service bean) {
         }
      };
   }

   public void suspend() throws ServerLifecycleException {
      this.suspend(0, false);
   }

   public void suspend(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      GracefulShutdownRequest gracefulSuspendRequest = new GracefulShutdownRequest(ignoreSessions, 17);
      this.logAdministratorAddress("Graceful suspend");
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(gracefulSuspendRequest));
      gracefulSuspendRequest.waitForCompletion(timeout * 1000);
      if (gracefulSuspendRequest.getException() != null) {
         throw new ServerLifecycleException(gracefulSuspendRequest.getException());
      } else {
         if (!gracefulSuspendRequest.isCompleted()) {
            T3Srvr.getT3Srvr().setState(8);
            this.forceSuspend();
         }

      }
   }

   public void forceSuspend() throws ServerLifecycleException {
      this.logAdministratorAddress("Force suspend");
      this.server.forceSuspend();
   }

   public void resume() throws ServerLifecycleException {
      this.logAdministratorAddress("Resume");
      this.server.resume();
   }

   public void shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ServerLifecycleException {
      this.logAdministratorAddress("Graceful shutdown");
      SrvrUtilities.setGracefulShutdownOverride((long)timeout * 1000L);
      GracefulShutdownRequest gracefulShutdownRequest;
      if (ignoreSessions) {
         gracefulShutdownRequest = new GracefulShutdownRequest(ignoreSessions);
      } else {
         gracefulShutdownRequest = new GracefulShutdownRequest(this.runtimeAccess.getServer().isIgnoreSessionsDuringShutdown(), waitForAllSessions);
      }

      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(gracefulShutdownRequest));
      gracefulShutdownRequest.waitForCompletion(0);
      ServerLifeCycleTimerThread.waitForForceShutdownCompletionIfNeeded(gracefulShutdownRequest.getException());
      if (gracefulShutdownRequest.getException() != null) {
         throw new ServerLifecycleException(gracefulShutdownRequest.getException());
      }
   }

   public void shutdown(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      this.shutdown(timeout, ignoreSessions, false);
   }

   public void shutdown() throws ServerLifecycleException {
      this.shutdown(0, false);
   }

   public void forceShutdown() throws ServerLifecycleException {
      try {
         ServerLogger.logForceShuttingDownServer();
         this.logAdministratorAddress("Force shutdown");
         this.server.forceShutdown();
      } catch (RuntimeException var2) {
         ServerLogger.logServerRuntimeError(var2.toString());
         ServerLogger.logServerRuntimeError(StackTraceUtils.throwable2StackTrace(var2));
         throw new ServerLifecycleException(var2);
      } catch (Exception var3) {
         throw new ServerLifecycleException(var3);
      }
   }

   public void abortStartupAfterAdminState() throws ServerLifecycleException {
      this.server.abortStartupAfterAdminState();
   }

   private void logAdministratorAddress(String operation) {
      EndPoint endPoint = ServerHelper.getClientEndPointInternal();
      if (endPoint != null) {
         Channel channel = endPoint.getRemoteChannel();
         if (channel != null) {
            ServerLogger.logAdminAddress(operation, channel.getInetAddress().getHostAddress() + ':' + channel.getPublicPort());
         }
      }
   }

   public void start() {
      this.server.getLockoutManager().unlockServer();
   }

   public boolean isStartupAbortedInAdminState() {
      return this.stateVal == 17 && this.server.isAbortStartupAfterAdminState();
   }

   public boolean isShuttingDownDueToFailure() {
      return this.server.isShutdownDueToFailure();
   }

   public void updateRunState(int newRunState) {
      int oldState = this.stateVal;
      int newStateVal;
      if (newRunState != 10 && newRunState != 11) {
         newStateVal = this.stateVal = newRunState;
      } else {
         newStateVal = this.stateVal = 7;
      }

      this._postSet("State", getStateAsString(oldState), getStateAsString(newStateVal));
      this.sendStateToAdminServer(getStateAsString(newStateVal));
   }

   private void sendStateToAdminServer(final String state) {
      try {
         if (this.stateVal == 1) {
            return;
         }

         String adminServerName = this.runtimeAccess.getAdminServerName();
         if (adminServerName == null) {
            return;
         }

         final RemoteLifeCycleOperations remote = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(adminServerName);
         if (remote == null) {
            return;
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               try {
                  remote.setState(ServerRuntime.this.runtimeAccess.getServerName(), state);
               } catch (RemoteRuntimeException var2) {
               } catch (RemoteException var3) {
               }

            }
         });
      } catch (RemoteRuntimeException var4) {
      }

   }

   private static String getStateAsString(int state) {
      return ServerStates.SERVERSTATES[state];
   }

   public String getState() {
      return getStateAsString(this.stateVal);
   }

   public boolean isShuttingDown() {
      return this.stateVal == 7 || this.stateVal == 18;
   }

   public int getStateVal() {
      return this.stateVal;
   }

   public long getActivationTime() {
      return this.server.getStartTime();
   }

   public long getServerStartupTime() {
      return this.server.getStartupTime();
   }

   public String getJVMID() {
      return JVMID.localID().objectToString();
   }

   public ServerIdentity getServerIdentity() {
      return LocalServerIdentity.getIdentity();
   }

   public int getSSLListenPort() {
      return this.runtimeAccess.getServer().getSSL().getListenPort();
   }

   public int getAdministrationPort() {
      return this.runtimeAccess.getServer().getAdministrationPort();
   }

   public int getRestartsTotalCount() {
      return 0;
   }

   public int getOpenSocketsCurrentCount() {
      return SocketMuxer.getMuxer().getNumSockets();
   }

   public SocketRuntime[] getSockets() {
      MuxableSocket[] sockets = SocketMuxer.getMuxer().getSockets();
      SocketRuntime[] returnValue = new SocketRuntime[sockets.length];

      for(int i = 0; i < returnValue.length; ++i) {
         MuxableSocket ms = sockets[i];
         if (ms instanceof SocketRuntime) {
            returnValue[i] = (SocketRuntime)ms;
         } else {
            returnValue[i] = null;
         }
      }

      return returnValue;
   }

   public ServerChannelRuntimeMBean[] getServerChannelRuntimes() {
      int len = this.channelRuntimes.size();
      return (ServerChannelRuntimeMBean[])((ServerChannelRuntimeMBean[])this.channelRuntimes.toArray(new ServerChannelRuntimeMBean[len]));
   }

   public boolean addServerChannelRuntime(ServerChannelRuntimeMBean c) {
      return this.channelRuntimes.add(c);
   }

   public boolean removeServerChannelRuntime(ServerChannelRuntimeMBean c) {
      return this.channelRuntimes.remove(c);
   }

   public long getSocketsOpenedTotalCount() {
      return (long)SocketMuxer.getMuxer().getNumSockets();
   }

   public String getMiddlewareHome() {
      String wlsHome = this.getWeblogicHome();
      if (wlsHome != null) {
         int lastSeparatorIndex = wlsHome.lastIndexOf(File.separatorChar);
         if (lastSeparatorIndex != -1) {
            return wlsHome.substring(0, lastSeparatorIndex);
         }
      }

      return wlsHome;
   }

   /** @deprecated */
   @Deprecated
   public String getOracleHome() {
      return this.getMiddlewareHome();
   }

   public String getWeblogicHome() {
      String homePath = null;

      try {
         homePath = (new File(Home.getPath())).getCanonicalPath();
      } catch (IOException var3) {
         homePath = Home.getPath();
      }

      int lastSeparatorIndex = homePath.lastIndexOf(File.separatorChar);
      return lastSeparatorIndex != -1 ? homePath.substring(0, lastSeparatorIndex) : homePath;
   }

   public String getWeblogicVersion() {
      return version.getVersions();
   }

   public MessagingBridgeRuntimeMBean getMessagingBridgeRuntime() {
      MessagingBridgeRuntimeMBean[] messagingBridges = this.getMessagingBridgeRuntimes();
      return messagingBridges.length > 0 ? messagingBridges[messagingBridges.length - 1] : null;
   }

   public void setMessagingBridgeRuntime(MessagingBridgeRuntimeMBean b) {
      if (!this.messagingBridgeRuntimes.contains(b)) {
         this.messagingBridgeRuntimes.add(b);
      }

   }

   public MessagingBridgeRuntimeMBean[] getMessagingBridgeRuntimes() {
      int len = this.messagingBridgeRuntimes.size();
      return (MessagingBridgeRuntimeMBean[])((MessagingBridgeRuntimeMBean[])this.messagingBridgeRuntimes.toArray(new MessagingBridgeRuntimeMBean[len]));
   }

   public boolean addMessagingBridgeRuntime(MessagingBridgeRuntimeMBean b) {
      return !this.messagingBridgeRuntimes.contains(b) ? this.messagingBridgeRuntimes.add(b) : false;
   }

   public boolean removeMessagingBridgeRuntime(MessagingBridgeRuntimeMBean b) {
      return this.messagingBridgeRuntimes.remove(b);
   }

   public MessagingBridgeRuntimeMBean lookupMessagingBridgeRuntime(String name) {
      MessagingBridgeRuntimeMBean[] mbrs = this.getMessagingBridgeRuntimes();

      for(int i = 0; i < mbrs.length; ++i) {
         if (mbrs[i].getName().equals(name)) {
            return mbrs[i];
         }
      }

      return null;
   }

   public JMSRuntimeMBean getJMSRuntime() {
      return this.jmsRuntime;
   }

   public void setJMSRuntime(JMSRuntimeMBean b) {
      this.jmsRuntime = b;
   }

   public SAFRuntimeMBean getSAFRuntime() {
      return this.safRuntime;
   }

   public void setSAFRuntime(SAFRuntimeMBean b) {
      this.safRuntime = b;
   }

   public JDBCServiceRuntimeMBean getJDBCServiceRuntime() {
      return this.jdbcRuntime;
   }

   public void setJDBCServiceRuntime(JDBCServiceRuntimeMBean b) {
      this.jdbcRuntime = b;
   }

   public JTARuntimeMBean getJTARuntime() {
      return this.jtaRuntime;
   }

   public void setJTARuntime(JTARuntimeMBean b) {
      this.jtaRuntime = b;
   }

   public WTCRuntimeMBean getWTCRuntime() {
      return this.wtcRuntime;
   }

   public void setWTCRuntime(WTCRuntimeMBean b) {
      this.wtcRuntime = b;
   }

   public JoltConnectionServiceRuntimeMBean getJoltRuntime() {
      return this.joltRuntime;
   }

   public void setJoltRuntime(JoltConnectionServiceRuntimeMBean b) {
      this.joltRuntime = b;
   }

   public JVMRuntimeMBean getJVMRuntime() {
      return this.jvmRuntime;
   }

   public void setJVMRuntime(JVMRuntimeMBean b) {
      this.jvmRuntime = b;
   }

   public ClusterRuntimeMBean getClusterRuntime() {
      return this.clusterRuntime;
   }

   public void setClusterRuntime(ClusterRuntimeMBean b) {
      this.clusterRuntime = b;
   }

   public EntityCacheCurrentStateRuntimeMBean getEntityCacheCurrentStateRuntime() {
      return this.ecCurrStateRuntime;
   }

   public void setEntityCacheCurrentStateRuntime(EntityCacheCurrentStateRuntimeMBean b) {
      this.ecCurrStateRuntime = b;
   }

   public EntityCacheCumulativeRuntimeMBean getEntityCacheCumulativeRuntime() {
      return this.ecCumRuntime;
   }

   public void setEntityCacheCumulativeRuntime(EntityCacheCumulativeRuntimeMBean b) {
      this.ecCumRuntime = b;
   }

   public EntityCacheCumulativeRuntimeMBean getEntityCacheHistoricalRuntime() {
      return this.ecHistRuntime;
   }

   public void setEntityCacheHistoricalRuntime(EntityCacheCumulativeRuntimeMBean b) {
      this.ecHistRuntime = b;
   }

   public ThreadPoolRuntimeMBean getThreadPoolRuntime() {
      return this.threadPoolRuntime;
   }

   public void setClassLoaderRuntime(ClassLoaderRuntimeMBean r) {
      this.classLoaderRuntime = r;
   }

   public ClassLoaderRuntimeMBean getClassLoaderRuntime() {
      return this.classLoaderRuntime;
   }

   public void setThreadPoolRuntime(ThreadPoolRuntimeMBean tprmb) {
      this.threadPoolRuntime = tprmb;
   }

   public void setTimerRuntime(TimerRuntimeMBean timerRuntimeMBean) {
      this.timerRuntime = timerRuntimeMBean;
   }

   public TimerRuntimeMBean getTimerRuntime() {
      return this.timerRuntime;
   }

   public void setTimeServiceRuntime(TimeServiceRuntimeMBean timeServiceRuntime) {
      this.timeServiceRuntime = timeServiceRuntime;
   }

   public TimeServiceRuntimeMBean getTimeServiceRuntime() {
      return this.timeServiceRuntime;
   }

   public ExecuteQueueRuntimeMBean getDefaultExecuteQueueRuntime() {
      if (this.executeQueueRuntime != null) {
         return this.executeQueueRuntime;
      } else {
         ExecuteQueueRuntimeMBean eqr = this.getExecuteQueueRuntimeInternal("weblogic.kernel.Default");
         return eqr != null ? eqr : this.getExecuteQueueRuntimeInternal("default");
      }
   }

   public void setDefaultExecuteQueueRuntime(ExecuteQueueRuntimeMBean b) {
      this.executeQueueRuntime = b;
   }

   private ExecuteQueueRuntimeMBean getExecuteQueueRuntimeInternal(String name) {
      ExecuteQueueRuntimeMBean[] r = this.getExecuteQueueRuntimes();

      for(int i = 0; i < r.length; ++i) {
         if (r[i].getName().equals(name)) {
            return r[i];
         }
      }

      return null;
   }

   public ExecuteQueueRuntimeMBean[] getExecuteQueueRuntimes() {
      int len = this.executeQueueRuntimes.size();
      return (ExecuteQueueRuntimeMBean[])((ExecuteQueueRuntimeMBean[])this.executeQueueRuntimes.toArray(new ExecuteQueueRuntimeMBean[len]));
   }

   public boolean addExecuteQueueRuntime(ExecuteQueueRuntimeMBean a) {
      return this.executeQueueRuntimes.add(a);
   }

   public boolean removeExecuteQueueRuntime(ExecuteQueueRuntimeMBean a) {
      return this.executeQueueRuntimes.remove(a);
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      int len = this.workManagerRuntimes.size();
      return (WorkManagerRuntimeMBean[])this.workManagerRuntimes.toArray(new WorkManagerRuntimeMBean[len]);
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean q) {
      return this.workManagerRuntimes.add(q);
   }

   public boolean removeWorkManagerRuntime(WorkManagerRuntimeMBean q) {
      return this.workManagerRuntimes.remove(q);
   }

   public MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String name) {
      MinThreadsConstraintRuntimeMBean[] min = this.getMinThreadsConstraintRuntimes();

      for(int i = 0; i < min.length; ++i) {
         if (min[i].getName().equals(name)) {
            return min[i];
         }
      }

      return null;
   }

   public RequestClassRuntimeMBean lookupRequestClassRuntime(String name) {
      RequestClassRuntimeMBean[] rcs = this.getRequestClassRuntimes();

      for(int i = 0; i < rcs.length; ++i) {
         if (rcs[i].getName().equals(name)) {
            return rcs[i];
         }
      }

      return null;
   }

   public MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String name) {
      MaxThreadsConstraintRuntimeMBean[] max = this.getMaxThreadsConstraintRuntimes();

      for(int i = 0; i < max.length; ++i) {
         if (max[i].getName().equals(name)) {
            return max[i];
         }
      }

      return null;
   }

   public boolean addMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean temp) {
      return this.maxThreadsConstraintRuntimes.add(temp);
   }

   public MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes() {
      int len = this.maxThreadsConstraintRuntimes.size();
      return (MaxThreadsConstraintRuntimeMBean[])((MaxThreadsConstraintRuntimeMBean[])this.maxThreadsConstraintRuntimes.toArray(new MaxThreadsConstraintRuntimeMBean[len]));
   }

   public boolean addMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean temp) {
      return this.minThreadsConstraintRuntimes.add(temp);
   }

   public boolean addRequestClassRuntime(RequestClassRuntimeMBean temp) {
      return this.requestClassRuntimes.add(temp);
   }

   public MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes() {
      int len = this.minThreadsConstraintRuntimes.size();
      return (MinThreadsConstraintRuntimeMBean[])((MinThreadsConstraintRuntimeMBean[])this.minThreadsConstraintRuntimes.toArray(new MinThreadsConstraintRuntimeMBean[len]));
   }

   public RequestClassRuntimeMBean[] getRequestClassRuntimes() {
      int len = this.requestClassRuntimes.size();
      return (RequestClassRuntimeMBean[])((RequestClassRuntimeMBean[])this.requestClassRuntimes.toArray(new RequestClassRuntimeMBean[len]));
   }

   public ServerSecurityRuntimeMBean getServerSecurityRuntime() {
      return this.serverSecurityRuntime;
   }

   public void setServerSecurityRuntime(ServerSecurityRuntimeMBean sbean) {
      this.serverSecurityRuntime = sbean;
   }

   public SingleSignOnServicesRuntimeMBean getSingleSignOnServicesRuntime() {
      return this.ssoRuntime;
   }

   public void setSingleSignOnServicesRuntime(SingleSignOnServicesRuntimeMBean ssoRuntime) {
      this.ssoRuntime = ssoRuntime;
   }

   public String getListenAddress() {
      InetSocketAddress addr = this.getServerChannel("T3");
      if (addr == null || addr.isUnresolved()) {
         addr = this.getServerChannel("T3S");
         if (addr == null || addr.isUnresolved()) {
            addr = this.getServerChannel("ADMIN");
            if (addr == null || addr.isUnresolved()) {
               return null;
            }
         }
      }

      String name = addr.getAddress().toString();
      if (name.startsWith("/")) {
         StringBuffer sbuf = new StringBuffer(addr.getHostName());
         sbuf.append(name);
         name = sbuf.toString();
      }

      return name;
   }

   public InetSocketAddress getServerChannel(String protocol) {
      ChannelImportExportService ios = (ChannelImportExportService)GlobalServiceLocator.getServiceLocator().getService(ChannelImportExportService.class, new Annotation[0]);
      return ios.findServerAddress(protocol);
   }

   public void restartSSLChannels() {
      ServerChannelManager.getServerChannelManager().restartSSLChannels();
   }

   public String getDefaultURL() {
      return ChannelHelper.getDefaultURL();
   }

   public String getAdministrationURL() {
      return ChannelHelper.getLocalAdministrationURL();
   }

   private ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }

   public String getURL(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return this.getChannelHelperService().getURL(p);
   }

   public String getIPv4URL(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return ChannelHelper.getIPv4URL(p);
   }

   public String getIPv6URL(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return ChannelHelper.getIPv6URL(p);
   }

   public int getListenPort() {
      return this.runtimeAccess.getServer().getListenPort();
   }

   public boolean isListenPortEnabled() {
      return this.runtimeAccess.getServer().isListenPortEnabled();
   }

   public boolean isAdministrationPortEnabled() {
      return this.runtimeAccess.getServer().isAdministrationPortEnabled();
   }

   public boolean isSSLListenPortEnabled() {
      return this.runtimeAccess.getServer().getSSL() == null ? false : this.runtimeAccess.getServer().getSSL().isListenPortEnabled();
   }

   public boolean isAdminServerListenPortSecure() {
      if (!this.runtimeAccess.isAdminServer()) {
         String httpUrl = ManagementService.getPropertyService(kernelId).getAdminHttpUrl();
         if (null == httpUrl) {
            return false;
         } else {
            return httpUrl.startsWith("https");
         }
      } else {
         ServerMBean server = this.runtimeAccess.getServer();
         if (server != null) {
            if (server.isAdministrationPortEnabled()) {
               return true;
            }

            String url = getURLManagerService().findAdministrationURL(AdminServerIdentity.getIdentity());

            try {
               URI u = new URI(url);
               return u.getScheme().equals("admin") || u.getScheme().endsWith("s");
            } catch (URISyntaxException var4) {
            }
         }

         return false;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public int getAdminServerListenPort() {
      ServerIdentity adminServerIdentity = AdminServerIdentity.getIdentity();
      if (adminServerIdentity == null) {
         throw new IllegalStateException("Admin server identity is unavailable. The managed server may not be connected to the admin server");
      } else {
         String url = getURLManagerService().findAdministrationURL(adminServerIdentity);

         try {
            return (new URI(url)).getPort();
         } catch (URISyntaxException var4) {
            return 0;
         }
      }
   }

   public String getAdminServerHost() {
      String url = getURLManagerService().findAdministrationURL(AdminServerIdentity.getIdentity());

      try {
         return (new URI(url)).getHost();
      } catch (URISyntaxException var3) {
         return null;
      }
   }

   public String getSSLListenAddress() {
      InetSocketAddress socket = this.getServerChannel("HTTPS");
      if (socket == null) {
         return null;
      } else {
         String name = socket.getAddress().toString();
         if (name.startsWith("/")) {
            StringBuffer sbuf = new StringBuffer(socket.getHostName());
            sbuf.append(name);
            name = sbuf.toString();
         }

         return name;
      }
   }

   public HealthState getHealthState() {
      return this.healthState;
   }

   public CompositeData getHealthStateJMX() throws OpenDataException {
      return this.healthState.toCompositeData();
   }

   public HealthState getOverallHealthState() {
      HealthState consolidatedHealthState = this.getHealthState();
      HealthState[] var2 = this.getSubsystemHealthStates();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         HealthState healthState = var2[var4];
         if ((!"ApplicationRuntime".equals(healthState.getMBeanType()) || !this.isInternalDeployment(healthState.getMBeanName())) && !"MessageDrivenEJBRuntime".equals(healthState.getMBeanType()) && consolidatedHealthState.compareSeverityTo(healthState) < 0) {
            consolidatedHealthState = healthState;
         }
      }

      return consolidatedHealthState;
   }

   public CompositeData getOverallHealthStateJMX() throws OpenDataException {
      return this.getOverallHealthState().toCompositeData();
   }

   private boolean isInternalDeployment(String appName) {
      if (appName != null && appName.length() != 0) {
         int versionDelimiter = appName.indexOf("#");
         if (versionDelimiter > 0) {
            appName = appName.substring(0, versionDelimiter);
         }

         if (this.internalApps == null) {
            HashSet tmpSet = new HashSet();
            DomainMBean domain = this.runtimeAccess.getDomain();
            AppDeploymentMBean[] var5 = domain.getInternalAppDeployments();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               AppDeploymentMBean appDeploymentMBean = var5[var7];
               tmpSet.add(appDeploymentMBean.getApplicationName());
            }

            this.internalApps = tmpSet;
         }

         return this.internalApps.contains(appName);
      } else {
         return false;
      }
   }

   public void setHealthState(int state, String reason) {
      Symptom symptom = new Symptom(SymptomType.UNKNOWN, Symptom.healthStateSeverity(state), this.getName(), reason);
      this.setHealthState(state, symptom);
   }

   public void setHealthState(int state, Symptom symptom) {
      HealthState oldState;
      HealthState newState;
      synchronized(this) {
         oldState = this.healthState;
         newState = new HealthState(state, symptom);
         this.healthState = newState;
      }

      this._postSet("HealthState", oldState, newState);
   }

   public boolean isAdminServer() {
      return this.runtimeAccess.isAdminServer() || !this.runtimeAccess.isAdminServerAvailable();
   }

   public String getCurrentDirectory() {
      if (this.curDir == null) {
         this.curDir = (new File(".")).getAbsolutePath();
      }

      return this.curDir;
   }

   public ApplicationRuntimeMBean[] getApplicationRuntimes() {
      synchronized(this.applicationRuntimes) {
         int len = this.applicationRuntimes.size();
         return (ApplicationRuntimeMBean[])this.applicationRuntimes.toArray(new ApplicationRuntimeMBean[len]);
      }
   }

   public ApplicationRuntimeMBean lookupApplicationRuntime(String name) {
      synchronized(this.applicationRuntimes) {
         Iterator var3 = this.applicationRuntimes.iterator();

         ApplicationRuntimeMBean applicationRuntimeMBean;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            applicationRuntimeMBean = (ApplicationRuntimeMBean)var3.next();
         } while(!applicationRuntimeMBean.getName().equals(name));

         return applicationRuntimeMBean;
      }
   }

   public String[] getPendingRestartSystemResources() {
      int len = this.pendingRestartSystemResources.size();
      return (String[])((String[])this.pendingRestartSystemResources.toArray(new String[len]));
   }

   public boolean addPendingRestartSystemResource(String s) {
      return this.pendingRestartSystemResources.add(s);
   }

   public boolean removePendingRestartSystemResource(String s) {
      return this.pendingRestartSystemResources.remove(s);
   }

   public boolean isRestartPendingForSystemResource(String name) {
      return this.pendingRestartSystemResources.contains(name);
   }

   public LibraryRuntimeMBean[] getLibraryRuntimes() {
      synchronized(this.libraryRuntimes) {
         return (LibraryRuntimeMBean[])this.libraryRuntimes.values().toArray(new LibraryRuntimeMBean[this.libraryRuntimes.size()]);
      }
   }

   public LibraryRuntimeMBean lookupLibraryRuntime(String name) {
      return (LibraryRuntimeMBean)this.libraryRuntimes.get(name);
   }

   public LogBroadcasterRuntimeMBean getLogBroadcasterRuntime() throws ManagementException {
      return LogBroadcaster.getLogBroadcaster();
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public ServerLogRuntimeMBean getServerLogRuntime() {
      return this.logRuntime;
   }

   public void setServerLogRuntime(ServerLogRuntimeMBean bean) {
      this.logRuntime = bean;
   }

   public WLDFRuntimeMBean getWLDFRuntime() {
      return this.wldfRuntime;
   }

   public void setWLDFRuntime(WLDFRuntimeMBean mbean) {
      this.wldfRuntime = mbean;
   }

   public void setMANReplicationRuntime(MANReplicationRuntimeMBean mbean) {
      this.manReplicationRuntime = mbean;
   }

   public MANReplicationRuntimeMBean getMANReplicationRuntime() {
      return this.manReplicationRuntime;
   }

   public void setWANReplicationRuntime(WANReplicationRuntimeMBean mbean) {
      this.wanReplicationRuntime = mbean;
   }

   public WANReplicationRuntimeMBean getWANReplicationRuntime() {
      return this.wanReplicationRuntime;
   }

   public AsyncReplicationRuntimeMBean getAsyncReplicationRuntime() {
      return this.asyncReplicationRuntime;
   }

   public void setAsyncReplicationRuntime(AsyncReplicationRuntimeMBean runtime) {
      this.asyncReplicationRuntime = runtime;
   }

   public String getCurrentMachine() {
      return this.currentMachine;
   }

   public HealthState[] getSubsystemHealthStates() {
      return HealthMonitorService.getHealthStates();
   }

   public HashMap getServerServiceVersions() {
      return SrvrUtilities.getVersionsOnline();
   }

   public void setCurrentMachine(String machineName) {
      this.currentMachine = machineName;
   }

   public MailSessionRuntimeMBean[] getMailSessionRuntimes() {
      return (MailSessionRuntimeMBean[])((MailSessionRuntimeMBean[])this.mailSessionRuntimes.toArray(new MailSessionRuntimeMBean[this.mailSessionRuntimes.size()]));
   }

   public boolean addMailSessionRuntime(MailSessionRuntimeMBean r) {
      return this.mailSessionRuntimes.add(r);
   }

   public boolean removeMailSessionRuntime(MailSessionRuntimeMBean r) {
      return this.mailSessionRuntimes.remove(r);
   }

   public PersistentStoreRuntimeMBean[] getPersistentStoreRuntimes() {
      Collection runtimes = this.persistentStoreRuntimes.values();
      return (PersistentStoreRuntimeMBean[])((PersistentStoreRuntimeMBean[])runtimes.toArray(new PersistentStoreRuntimeMBean[this.persistentStoreRuntimes.size()]));
   }

   public PersistentStoreRuntimeMBean lookupPersistentStoreRuntime(String name) {
      return (PersistentStoreRuntimeMBean)this.persistentStoreRuntimes.get(name);
   }

   public void addPersistentStoreRuntime(PersistentStoreRuntimeMBean r) {
      this.persistentStoreRuntimes.put(r.getName(), r);
   }

   public void removePersistentStoreRuntime(PersistentStoreRuntimeMBean r) {
      this.persistentStoreRuntimes.remove(r.getName());
   }

   public ConnectorServiceRuntimeMBean getConnectorServiceRuntime() {
      return this.connectorServiceRuntime;
   }

   public void setConnectorServiceRuntime(ConnectorServiceRuntimeMBean connectorService) {
      this.connectorServiceRuntime = connectorService;
   }

   public WebServerRuntimeMBean[] getWebServerRuntimes() {
      int len = this.webServerRuntimes.size();
      return (WebServerRuntimeMBean[])((WebServerRuntimeMBean[])this.webServerRuntimes.toArray(new WebServerRuntimeMBean[len]));
   }

   public boolean addWebServerRuntime(WebServerRuntimeMBean c) {
      return this.webServerRuntimes.add(c);
   }

   public boolean removeWebServerRuntime(WebServerRuntimeMBean c) {
      return this.webServerRuntimes.remove(c);
   }

   public boolean isRestartRequired() {
      return this.restartRequired;
   }

   public void setRestartRequired(boolean restartRequired) {
      this.restartRequired = restartRequired;
   }

   public void setPartitionRestartRequired(String partitionName, boolean restartRequired) {
      PartitionRuntimeMBean partitionRuntimeMBean = this.lookupPartitionRuntime(partitionName);
      if (partitionRuntimeMBean != null) {
         partitionRuntimeMBean.setRestartRequired(restartRequired);
      }

   }

   public boolean isPartitionRestartRequired(String partitionName) {
      PartitionRuntimeMBean partitionRuntimeMBean = this.lookupPartitionRuntime(partitionName);
      return partitionRuntimeMBean != null ? partitionRuntimeMBean.isRestartRequired() : false;
   }

   public String[] getPendingRestartPartitions() {
      PartitionRuntimeMBean[] partitionRuntimeMBeans = this.getPartitionRuntimes();
      List pendingRestartPartitions = new ArrayList();
      PartitionRuntimeMBean[] var3 = partitionRuntimeMBeans;
      int var4 = partitionRuntimeMBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PartitionRuntimeMBean partitionRuntimeMBean = var3[var5];
         if (partitionRuntimeMBean.isRestartRequired()) {
            pendingRestartPartitions.add(partitionRuntimeMBean.getName());
         }
      }

      return (String[])pendingRestartPartitions.toArray(new String[pendingRestartPartitions.size()]);
   }

   public String getServerClasspath() {
      return Classpath.get();
   }

   public PathServiceRuntimeMBean getPathServiceRuntime() {
      return this.domainScopePathServiceRuntimeMBean;
   }

   public PathServiceRuntimeMBean[] getPathServiceRuntimes() {
      int len = this.pathServiceRuntimes.size();
      return (PathServiceRuntimeMBean[])this.pathServiceRuntimes.toArray(new PathServiceRuntimeMBean[len]);
   }

   public boolean addPathServiceRuntime(PathServiceRuntimeMBean q, boolean isDomainScope) {
      if (isDomainScope) {
         this.domainScopePathServiceRuntimeMBean = q;
      }

      return this.pathServiceRuntimes.add(q);
   }

   public boolean removePathServiceRuntime(PathServiceRuntimeMBean q, boolean isDomainScope) {
      if (isDomainScope) {
         this.domainScopePathServiceRuntimeMBean = null;
      }

      return this.pathServiceRuntimes.remove(q);
   }

   public void setPathServiceRuntime(PathServiceRuntimeMBean b) {
      throw new UnsupportedOperationException("@deprecated use addPathServiceRuntime(psr, boolean isDomainScope)");
   }

   public boolean isClusterMaster() {
      ClusterMasterDiscoverer cms = (ClusterMasterDiscoverer)GlobalServiceLocator.getServiceLocator().getService(ClusterMasterDiscoverer.class, new Annotation[0]);
      return cms.isClusterMaster();
   }

   public SNMPAgentRuntimeMBean getSNMPAgentRuntime() {
      return this.snmpAgentRuntime;
   }

   public void setSNMPAgentRuntime(SNMPAgentRuntimeMBean agentRuntime) {
      this.snmpAgentRuntime = agentRuntime;
   }

   private void initializeHk2Services() {
      this.hk2Services = new HashSet();
      this.hk2Services.add("EJB");
      this.hk2Services.add("CONNECTOR");
      this.hk2Services.add("JMS");
   }

   public boolean isServiceAvailable(String service) {
      synchronized(this) {
         if (this.hk2Services == null) {
            this.initializeHk2Services();
         }

         return this.hk2Services.contains(service);
      }
   }

   public MANAsyncReplicationRuntimeMBean getMANAsyncReplicationRuntime() {
      return this.manAsyncReplicationRuntime;
   }

   public void setMANAsyncReplicationRuntime(MANAsyncReplicationRuntimeMBean manAsyncReplicationRuntime) {
      this.manAsyncReplicationRuntime = manAsyncReplicationRuntime;
   }

   public int getStableState() {
      return this.server.getStableState();
   }

   public void setWseeWsrmRuntime(WseeWsrmRuntimeMBean r) {
      this.wseeWsrmRuntime = r;
   }

   public WseeWsrmRuntimeMBean getWseeWsrmRuntime() {
      return this.wseeWsrmRuntime;
   }

   public void setWseeClusterFrontEndRuntime(WseeClusterFrontEndRuntimeMBean r) {
      this.wseeClusterFrontEndRuntime = r;
   }

   public WseeClusterFrontEndRuntimeMBean getWseeClusterFrontEndRuntime() {
      return this.wseeClusterFrontEndRuntime;
   }

   public PartitionRuntimeMBean[] getPartitionRuntimes() {
      Collection runtimes = this.partitionRuntimes.values();
      return (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])runtimes.toArray(new PartitionRuntimeMBean[this.partitionRuntimes.size()]));
   }

   public PartitionRuntimeMBean lookupPartitionRuntime(String name) {
      return (PartitionRuntimeMBean)this.partitionRuntimes.get(name);
   }

   public void addPartitionRuntime(PartitionRuntimeMBean r) {
      Collection runtimes = this.partitionRuntimes.values();
      PartitionRuntimeMBean[] oldPartitionRuntimesArray = (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])runtimes.toArray(new PartitionRuntimeMBean[this.partitionRuntimes.size()]));
      this.partitionRuntimes.put(r.getName(), r);
      this._postSet("PartitionRuntimes", oldPartitionRuntimesArray, this.getPartitionRuntimes());
   }

   public void removePartitionRuntime(PartitionRuntimeMBean r) {
      Collection runtimes = this.partitionRuntimes.values();
      PartitionRuntimeMBean[] oldPartitionRuntimesArray = (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])runtimes.toArray(new PartitionRuntimeMBean[this.partitionRuntimes.size()]));
      this.partitionRuntimes.remove(r.getName());
      this._postSet("PartitionRuntimes", oldPartitionRuntimesArray, this.getPartitionRuntimes());
   }

   public ConcurrentManagedObjectsRuntimeMBean getConcurrentManagedObjectsRuntime() {
      return this.concurrentManagedObjectsRuntime;
   }

   public void setConcurrentManagedObjectsRuntime(ConcurrentManagedObjectsRuntimeMBean r) {
      this.concurrentManagedObjectsRuntime = r;
   }

   public BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime() {
      return this.batchJobRepositoryRuntimeMBean;
   }

   public void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean batchJobRepositoryRuntimeMBean) {
      this.batchJobRepositoryRuntimeMBean = batchJobRepositoryRuntimeMBean;
   }

   public void bootPartition(String partitionName) throws PartitionLifeCycleException {
      this.doPartitionOperation(partitionName, Operation.BOOT);
   }

   public void startPartition(String partitionName) throws PartitionLifeCycleException {
      this.doPartitionOperation(partitionName, Operation.START);
   }

   public void startPartitionInAdmin(String partitionName) throws PartitionLifeCycleException {
      this.doPartitionOperation(partitionName, Operation.ADMIN);
   }

   public void forceRestartPartition(String partitionName) throws PartitionLifeCycleException {
      this.doPartitionOperation(partitionName, Operation.FORCE_RESTART);
   }

   public void forceRestartPartition(String partitionName, long restartDelayMillis) throws PartitionLifeCycleException {
      this.doPartitionOperation(partitionName, Operation.FORCE_RESTART, restartDelayMillis);
   }

   public void startResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.START);
   }

   public void startResourceGroupInAdmin(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.ADMIN);
   }

   public void suspendResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.SUSPEND, timeout, ignoreSessions);
   }

   public void suspendResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.SUSPEND, 0, false);
   }

   public void forceSuspendResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.FORCE_SUSPEND);
   }

   public void resumeResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.RESUME);
   }

   public void forceShutdownResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.FORCE_SHUTDOWN);
   }

   public void shutdownResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.SHUTDOWN, timeout, ignoreSessions, waitForAllSessions);
   }

   public void shutdownResourceGroup(String resourceGroupName, int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.SHUTDOWN, 0, false, true);
   }

   public void shutdownResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, RGOperation.SHUTDOWN, 0, false);
   }

   private void validateDomainResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      ResourceGroupMBean rgMBean = this.getDomain().lookupResourceGroup(resourceGroupName);
      if (rgMBean == null) {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exist at domain level");
      } else {
         this.validateCurrentServer(resourceGroupName);
      }
   }

   public String getServerName() {
      return this.runtimeAccess.getServerRuntime().getName();
   }

   private boolean validateCurrentServer(String resourceGroupName) throws ResourceGroupLifecycleException {
      if (this.checkCurrentServer(resourceGroupName)) {
         return true;
      } else {
         throw new ResourceGroupLifecycleException("The Resource group " + resourceGroupName + " does not have " + this.getServerName() + " as a target");
      }
   }

   private boolean checkCurrentServer(String resourceGroupName) throws ResourceGroupLifecycleException {
      ResourceGroupMBean rgMBean = this.lookupDomainResourceGroup(resourceGroupName);
      TargetMBean[] var3 = rgMBean.findEffectiveTargets();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean t = var3[var5];
         if (t.getServerNames().contains(this.getServerName())) {
            return true;
         }
      }

      return false;
   }

   private void validatePartition(String partitionName) throws PartitionLifeCycleException {
      if (this.runtimeAccess.getDomain().lookupPartition(partitionName) == null) {
         throw new PartitionLifeCycleException("Partition " + partitionName + " does not exist at domain level");
      }
   }

   private String lookupPartitionId(String partitionName) throws PartitionLifeCycleException {
      PartitionMBean partition = this.runtimeAccess.getDomain().lookupPartition(partitionName);
      if (partition != null) {
         return partition.getPartitionID();
      } else {
         throw new PartitionLifeCycleException("Could not find partitionId associated with Partition " + partitionName);
      }
   }

   private synchronized void processPartitionRuntimeMBean(String partitionName, PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState, boolean create) throws PartitionLifeCycleException {
      try {
         boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();
         PartitionRuntimeMBean prMBean = this.lookupPartitionRuntime(partitionName);
         if (prMBean == null && create) {
            PartitionRuntimeMBean prMBean = new PartitionRuntimeMBeanImpl(partitionName, this.lookupPartitionId(partitionName));
            if (DEBUG) {
               debug("Partition runtime mbean created for  : " + partitionName);
            }

            prMBean.setState(state);
            prMBean.setSubState(subState);
            this.addPartitionRuntime(prMBean);
            if (DEBUG) {
               debug("The created  partitionRuntimeMBean   is : " + prMBean);
            }
         } else if (prMBean != null) {
            if (DEBUG) {
               debug("setting state for partitionRuntimeMBean   : " + state.name());
            }

            prMBean.setState(state);
            prMBean.setSubState(subState);
            if (DEBUG) {
               debug("set state for partitionRuntimeMBean complete  : " + prMBean + "  " + prMBean.getState());
            }
         }

      } catch (ManagementException var7) {
         throw new PartitionLifeCycleException(var7);
      }
   }

   public void setRgState(String resourceGroupName, ResourceGroupLifecycleOperations.RGState state) throws ResourceGroupLifecycleException {
      if (this.validateCurrentServer(resourceGroupName)) {
         if (state == RGState.SHUTDOWN) {
            this.domainRGStates.remove(resourceGroupName);
         } else {
            this.domainRGStates.put(resourceGroupName, state);
         }

      } else {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exists on this target");
      }
   }

   public void setRgState(String resourceGroupName, String rgState) throws ResourceGroupLifecycleException {
      if (rgState != null && RGState.valueOf(rgState) == null) {
         throw new ResourceGroupLifecycleException("Illegal state change request " + rgState);
      } else {
         this.setRgState(resourceGroupName, RGState.valueOf(rgState));
      }
   }

   public boolean isResourceGroupPresent(String resourceGroupName) throws ResourceGroupLifecycleException {
      return this.checkCurrentServer(resourceGroupName);
   }

   public String getRgState(String resourceGroupName) throws ResourceGroupLifecycleException {
      ResourceGroupLifecycleOperations.RGState rgState = this.getInternalRgState(resourceGroupName);
      return RGState.chooseUserDesiredStateName(rgState);
   }

   public ResourceGroupLifecycleOperations.RGState getInternalRgState(String resourceGroupName) throws ResourceGroupLifecycleException {
      if (this.validateCurrentServer(resourceGroupName)) {
         ResourceGroupLifecycleOperations.RGState rgState = (ResourceGroupLifecycleOperations.RGState)this.domainRGStates.get(resourceGroupName);
         return rgState == null ? RGState.SHUTDOWN : rgState;
      } else {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exists on this target");
      }
   }

   private ResourceGroupMBean lookupDomainResourceGroup(String resourceGroupName) throws ResourceGroupLifecycleException {
      ResourceGroupMBean rgMbean = this.getDomain().lookupResourceGroup(resourceGroupName);
      if (rgMbean == null) {
         throw new ResourceGroupLifecycleException("Resource group " + resourceGroupName + " does not exists");
      } else {
         return rgMbean;
      }
   }

   private DomainMBean getDomain() {
      return this.runtimeAccess.getDomain();
   }

   private void preOp(String resourceGroupName, ResourceGroupLifecycleOperations.RGOperation rgOperation) throws ResourceGroupLifecycleException {
      this.validateDomainResourceGroup(resourceGroupName);
      this.rgOpInProgress.put(resourceGroupName, rgOperation);
      PartitionLifecycleLogger.logResourceGroupOperationInitiated(rgOperation.name(), "DOMAIN", resourceGroupName);
      this.setRgState(resourceGroupName, rgOperation.nextRGState);
   }

   private void postOp(String resourceGroupName, ResourceGroupLifecycleOperations.RGOperation rgOperation) throws ResourceGroupLifecycleException {
      boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();
      this.setRgState(resourceGroupName, rgOperation.nextSuccessRGState);
      if (DEBUG) {
         debug(resourceGroupName + " is at state of " + rgOperation.nextSuccessRGState.name() + " getState returns " + this.getRgState(resourceGroupName));
      }

      PartitionLifecycleLogger.logResourceGroupOperationComplete(rgOperation.name(), "DOMAIN", resourceGroupName);
   }

   protected void doGracefulRGOperation(ResourceGroupLifecycleOperations.RGOperation rGoperation, String resourceGroupName, boolean ignoreSessions, boolean waitForAllSessions, int timeout) throws Throwable {
      GracefulRequest gracefulShutdownRequest = new RGGracefulRequest(rGoperation, (String)null, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
      WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(gracefulShutdownRequest));
      gracefulShutdownRequest.waitForCompletion(timeout * 1000);
      if (gracefulShutdownRequest.getException() != null) {
         throw gracefulShutdownRequest.getException();
      } else {
         if (!gracefulShutdownRequest.isCompleted()) {
            if (rGoperation == RGOperation.SUSPEND) {
               this.forceSuspendResourceGroup(resourceGroupName);
            } else {
               if (rGoperation != RGOperation.SHUTDOWN) {
                  throw new IllegalArgumentException("Unexpected graceful operation " + rGoperation.toString() + " timed out.");
               }

               this.forceShutdownResourceGroup(resourceGroupName);
            }
         }

      }
   }

   private boolean isValidateOpAndState(String partitionName, PartitionRuntimeMBean.Operation operation) throws PartitionLifeCycleException {
      this.validatePartition(partitionName);
      boolean DEBUG = PartitionLifecycleDebugger.isDebugEnabled();
      if (DEBUG) {
         debug(operation.name() + "partitionName");
      }

      PartitionRuntimeMBean prMBean = this.lookupPartitionRuntime(partitionName);
      if (prMBean != null && prMBean.getInternalState() != State.UNKNOWN && prMBean.getInternalState() == operation.nextSuccessState) {
         PartitionLifecycleLogger.logPartitionAlreadyInState(partitionName, prMBean.getState());
         return false;
      } else {
         return true;
      }
   }

   private void preOpPartition(String partitionName, PartitionRuntimeMBean.Operation operation) throws PartitionLifeCycleException {
      PartitionLifecycleLogger.logPartitionOperationInitiated(operation.name(), partitionName);
      if (operation != Operation.FORCE_RESTART) {
         this.processPartitionRuntimeMBean(partitionName, operation.nextState, operation.successSubState, true);
      }

   }

   private void postOpPartition(String partitionName, PartitionRuntimeMBean.Operation operation, PartitionRuntimeMBean.State state, PartitionRuntimeMBean.State subState) throws PartitionLifeCycleException {
      if (operation != Operation.FORCE_RESTART) {
         this.processPartitionRuntimeMBean(partitionName, state, subState, false);
      }

      PartitionLifecycleLogger.logPartitionOperationComplete(operation.name(), partitionName);
   }

   private void doPartitionOperation(String partitionName, PartitionRuntimeMBean.Operation operation, Object... args) throws PartitionLifeCycleException {
      PartitionRuntimeMBean.Operation effectiveOp = operation;

      try {
         if (this.isValidateOpAndState(partitionName, operation)) {
            if (operation == Operation.BOOT && !PartitionUtils.isAdminRelatedActionNeeded(partitionName, this)) {
               PartitionLifecycleLogger.logPartitionOperationSkipBoot(partitionName);
            } else {
               PartitionRuntimeMBean prMBean = this.lookupPartitionRuntime(partitionName);
               if (prMBean != null && prMBean.getInternalState() == State.ADMIN && Operation.START.equals(operation)) {
                  operation = Operation.RESUME;
                  prMBean.resume();
                  PartitionLifecycleLogger.logStartPartitionConvertedToResume(partitionName);
               } else {
                  this.preOpPartition(partitionName, operation);
                  label45:
                  switch (operation) {
                     case BOOT:
                        ((PartitionManagerService)this.partitionManagerService.getService()).bootPartition(partitionName);
                        Iterator var10 = PartitionUtils.getResourceGroupsForThisServer(partitionName, true).iterator();

                        while(true) {
                           if (!var10.hasNext()) {
                              break label45;
                           }

                           String rg = (String)var10.next();
                           this.lookupPartitionRuntime(partitionName).setRgState(rg, RGState.RUNNING);
                        }
                     case START:
                        ((PartitionManagerService)this.partitionManagerService.getService()).startPartition(partitionName);
                        break;
                     case ADMIN:
                        ((PartitionManagerService)this.partitionManagerService.getService()).startPartitionInAdmin(partitionName);
                        break;
                     case FORCE_RESTART:
                        long restartDelayMillis = this.collectRestartDelayFromArgs(args);
                        effectiveOp = this.performForceRestart(partitionName, restartDelayMillis);
                        if (effectiveOp == null) {
                           effectiveOp = Operation.HALT;
                        }
                  }

                  this.postOpPartition(partitionName, operation, effectiveOp.nextSuccessState, effectiveOp.successSubState);
               }
            }
         }
      } catch (Throwable var8) {
         PartitionLifeCycleException pe = new PartitionLifeCycleException(var8);
         PartitionLifecycleLogger.logPartitionOperationException(operation.name(), partitionName, pe);
         this.processPartitionRuntimeMBean(partitionName, operation.nextFailureState, operation.nextFailureState, false);
         throw pe;
      }
   }

   private long collectRestartDelayFromArgs(Object... args) {
      if (args != null && args.length != 0) {
         if (args.length > 1) {
            throw new IllegalArgumentException("Internal forceRestart expects 0 or 1 argument for restart delay but found " + args.length + " arguments");
         } else if (args[0] instanceof Long) {
            return (Long)args[0];
         } else {
            throw new IllegalArgumentException("Internal forceRestart expects Long but found " + args[0].getClass().getName());
         }
      } else {
         return 0L;
      }
   }

   private PartitionRuntimeMBean.Operation performForceRestart(String partitionName, long restartDelay) throws PartitionLifeCycleException {
      PartitionRuntimeMBean partitionRuntimeMBean = this.lookupPartitionRuntime(partitionName);
      PartitionRuntimeMBean.State restartEndState = State.HALTED;
      if (partitionRuntimeMBean != null) {
         restartEndState = partitionRuntimeMBean.getInternalState();
         if (restartEndState == State.SHUTDOWN) {
            restartEndState = partitionRuntimeMBean.getInternalSubState();
         }

         partitionRuntimeMBean.halt();
      }

      if (this.lookupPartitionRuntime(partitionName) != null) {
         PartitionLifecycleLogger.forceRestartHaltedPartitionButNonNullRuntimeMBean(partitionName);
      }

      PartitionRuntimeMBean.Operation postHaltOperation = null;
      switch (restartEndState) {
         case HALTED:
         case HALTING:
            break;
         case BOOTED:
            postHaltOperation = Operation.BOOT;
            break;
         case ADMIN:
            postHaltOperation = Operation.ADMIN;
            break;
         case RUNNING:
            postHaltOperation = Operation.START;
            break;
         default:
            throw new PartitionLifeCycleException("ForceRestart of partition " + partitionName + " attempted but desired state of partition is " + restartEndState + " which is not one of SHUTDOWN.HALTED, SHUTDOWN.BOOTED, ADMIN, RUNNING");
      }

      if (restartDelay > 0L) {
         try {
            Thread.sleep(restartDelay);
         } catch (InterruptedException var8) {
            throw new PartitionLifeCycleException(var8);
         }
      }

      if (postHaltOperation != null) {
         this.doPartitionOperation(partitionName, postHaltOperation);
      }

      return postHaltOperation;
   }

   private void doDomainRGOperation(String resourceGroupName, ResourceGroupLifecycleOperations.RGOperation operation, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ResourceGroupLifecycleException {
      try {
         if (this.rgOpInProgress.containsKey(resourceGroupName) && !((ResourceGroupLifecycleOperations.RGOperation)this.rgOpInProgress.get(resourceGroupName)).isAllowedOp(operation)) {
            PartitionLifecycleLogger.logResourceGroupOpInProgress(resourceGroupName, "DOMAIN", ((ResourceGroupLifecycleOperations.RGOperation)this.rgOpInProgress.get(resourceGroupName)).name());
            return;
         }

         if (operation.nextSuccessRGState != this.getInternalRgState(resourceGroupName)) {
            if (!operation.isValidForState(this.getInternalRgState(resourceGroupName))) {
               PartitionLifecycleLogger.logResourceGroupOpIncompatible(resourceGroupName, "DOMAIN", operation.name(), this.getInternalRgState(resourceGroupName).name());
               return;
            }

            if (this.getInternalRgState(resourceGroupName) == RGState.ADMIN && RGOperation.START == operation) {
               operation = RGOperation.RESUME;
               PartitionLifecycleLogger.logStartDomainResourceGroupConvertedToResume(resourceGroupName);
            }

            this.preOp(resourceGroupName, operation);
            switch (operation) {
               case START:
                  ((PartitionManagerService)this.partitionManagerService.getService()).startResourceGroup((String)null, resourceGroupName);
                  break;
               case ADMIN:
                  ((PartitionManagerService)this.partitionManagerService.getService()).startResourceGroupInAdmin((String)null, resourceGroupName);
                  break;
               case SUSPEND:
                  this.doGracefulRGOperation(operation, resourceGroupName, ignoreSessions, false, timeout);
                  break;
               case FORCE_SUSPEND:
                  ((PartitionManagerService)this.partitionManagerService.getService()).forceSuspendResourceGroup((String)null, resourceGroupName);
                  break;
               case RESUME:
                  ((PartitionManagerService)this.partitionManagerService.getService()).resumeResourceGroup((String)null, resourceGroupName);
                  break;
               case SHUTDOWN:
                  this.doGracefulRGOperation(operation, resourceGroupName, ignoreSessions, waitForAllSessions, timeout);
                  break;
               case FORCE_SHUTDOWN:
                  ((PartitionManagerService)this.partitionManagerService.getService()).forceShutdownResourceGroup((String)null, resourceGroupName);
            }

            this.postOp(resourceGroupName, operation);
            return;
         }

         PartitionLifecycleLogger.logResourceGroupAlreadyInState(resourceGroupName, "DOMAIN", operation.nextSuccessRGState.name());
      } catch (Throwable var11) {
         this.setRgState(resourceGroupName, operation.nextFailureRGState);
         ResourceGroupLifecycleException ex = new ResourceGroupLifecycleException(var11);
         PartitionLifecycleLogger.logResourceGroupOperationException(operation.name(), "DOMAIN", resourceGroupName, ex);
         throw ex;
      } finally {
         this.rgOpInProgress.remove(resourceGroupName);
      }

   }

   private void doDomainRGOperation(String resourceGroupName, ResourceGroupLifecycleOperations.RGOperation operation) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, operation, 0, true, false);
   }

   private void doDomainRGOperation(String resourceGroupName, ResourceGroupLifecycleOperations.RGOperation operation, int timeout, boolean ignoreSessions) throws ResourceGroupLifecycleException {
      this.doDomainRGOperation(resourceGroupName, operation, timeout, ignoreSessions, false);
   }

   private static void debug(String debugMessage) {
      PartitionLifecycleDebugger.debug("<ServerRuntime> " + debugMessage);
   }

   public AggregateProgressMBean getAggregateProgress() {
      return this.aggregateProgressMBean;
   }

   public void setAggregateProgress(AggregateProgressMBean aggregateMeter) {
      this.aggregateProgressMBean = aggregateMeter;
   }

   public boolean isInSitConfigState() {
      return this.sitConfigState;
   }

   public void setInSitConfigState(boolean setter) {
      this.sitConfigState = setter;
   }

   public String[] getPatchList() {
      if (this.mwHome == null) {
         this.mwHome = Home.getMiddlewareHomePath();
      }

      return OPatchUtil.getInstance().getPatchInfos(this.mwHome);
   }

   public String toString() {
      return "ServerRuntime(" + (this.runtimeAccess == null ? "null" : this.runtimeAccess.getServerName()) + "," + System.identityHashCode(this) + ")";
   }
}
