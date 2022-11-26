package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.SNMPAgentDeploymentMBean;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.domainruntime.MBeanServerConnectionManagerMBean;
import weblogic.management.mbeanservers.internal.DomainServiceImpl;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.DomainConfiguration;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManager;
import weblogic.protocol.URLManagerService;
import weblogic.rjvm.ConnectionManager;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.rmi.extensions.ConnectMonitor;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.WorkManagerFactory;

public class MBeanServerConnectionManager extends DomainServiceImpl implements MBeanServerConnectionManagerMBean {
   private static final String JNDI = "/jndi/";
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   private final Map locationToMBeanServerMap = new ConcurrentHashMap();
   private final Map connectionToServerNameMap = new ConcurrentHashMap();
   private final Map connectorsByConnectionMap = new ConcurrentHashMap();
   private ConnectDisconnectListenerLock listenerLock = new ConnectDisconnectListenerLock();
   private final CopyOnWriteArrayList callbacks = new CopyOnWriteArrayList();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Map msconnectors = new ConcurrentHashMap();
   private final long RETRIAL_INTERVAL;

   public MBeanServerConnection lookupMBeanServerConnection(String serverName) {
      return (MBeanServerConnection)this.locationToMBeanServerMap.get(serverName);
   }

   MBeanServerConnectionManager() {
      super("MBeanServerConnectionManager", MBeanServerConnectionManagerMBean.class.getName(), (Service)null);
      this.RETRIAL_INTERVAL = ConnectionManager.RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS;
   }

   void initializeConnectivity() {
      this.registerLocalMBeanServer();
      this.startListening();
      this.pollAllManagedServers();
   }

   private void registerLocalMBeanServer() {
      if (debug.isDebugEnabled()) {
         debug.debug("Registering Local Mbean Server");
      }

      String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      MBeanServerConnection mbs = ManagementService.getRuntimeMBeanServer(kernelId);
      MBeanServerConnection connection = new ManagedMBeanServerConnection(mbs, serverName, (JMXExecutor)null, this.isManagedServerNotificationListenersDisabled());
      this.locationToMBeanServerMap.put(serverName, connection);
      this.connectionToServerNameMap.put(connection, serverName);
      this.invokeConnectCallbacks(serverName, connection);
   }

   void shutdown() {
      if (debug.isDebugEnabled()) {
         debug.debug("Shutting down Local Mbean Server");
      }

      Set connectors = this.connectorsByConnectionMap.keySet();
      Iterator iterator = connectors.iterator();

      while(iterator.hasNext()) {
         JMXConnector jmxConnector = (JMXConnector)iterator.next();

         try {
            jmxConnector.close();
         } catch (IOException var5) {
         }
      }

      this.locationToMBeanServerMap.clear();
      this.connectionToServerNameMap.clear();
      this.connectorsByConnectionMap.clear();
   }

   void addCallback(MBeanServerConnectionListener listener) {
      this.callbacks.add(listener);
   }

   void removeCallback(MBeanServerConnectionListener listener) {
      this.callbacks.remove(listener);
   }

   private void startListening() {
      ConnectMonitor connectionMonitor = ConnectMonitorFactory.getConnectMonitor();
      connectionMonitor.addConnectDisconnectListener(this.createConnectListener(), this.createDisconnectListener());
   }

   private void pollAllManagedServers() {
      if (debug.isDebugEnabled()) {
         debug.debug("Polling managed servers ");
      }

      ServerMBean[] servers = ManagementService.getRuntimeAccess(kernelId).getDomain().getServers();

      for(int i = 0; i < servers.length; ++i) {
         String serverName = servers[i].getName();
         MBeanServerConnection existing = this.lookupMBeanServerConnection(serverName);
         if (existing == null) {
            JMXLogger.logJMXResiliencyWarning(serverName, "Attempting to connect to the server during initialization ");
            this.connectToManagedServerWithLock(serverName, false);
            String managedServerURL = null;

            try {
               managedServerURL = getURLManagerService().findAdministrationURL(serverName);
            } catch (UnknownHostException var7) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Exception occurred during URL look for server " + serverName, var7);
               }
            }

            if (managedServerURL != null) {
               this.retryIfServerisnotConnected(serverName);
            } else {
               JMXLogger.logJMXResiliencyWarning(serverName, "No URL found for the server during the initialization");
            }
         } else {
            JMXLogger.logJMXResiliencyWarning(serverName, "Connection already exists for the server. Did not attempt to connect to the server");
         }

         if (debug.isDebugEnabled()) {
            debug.debug("pollAllManagedServers: connect to server " + serverName + " is already in progress");
         }
      }

   }

   private ConnectListener createConnectListener() {
      return new ConnectListener() {
         public void onConnect(ConnectEvent event) {
            String serverName = event.getServerName();
            boolean isFound = false;

            try {
               JMXLogger.logJMXResiliencyWarning(serverName, "\nReceived a CONNECT EVENT \n");
               isFound = MBeanServerConnectionManager.this.findServerInConfiguration(serverName);
               if (isFound) {
                  MBeanServerConnectionManager.this.connectToManagedServerWithLock(serverName, false);
                  return;
               }

               JMXLogger.logNoSuchServerInConfiguration(serverName);
            } catch (Exception var8) {
               JMXLogger.logExceptionDuringJMXConnectivity(var8);
               return;
            } finally {
               if (isFound) {
                  MBeanServerConnectionManager.this.retryIfServerisnotConnected(serverName);
               }

            }

         }
      };
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private void retryIfServerisnotConnected(String serverName) {
      if (this.isExistingConnectionGood(serverName)) {
         JMXLogger.logJMXResiliencyWarning(serverName, "Server is reachable, after a single attempt. Will return from the retry.");
      } else {
         JMXLogger.logJMXResiliencyWarning(serverName, "Server is  not reachable, after a single attempt. Will attempt continuously");
         this.connectionToMSContinuously(serverName, this.RETRIAL_INTERVAL);
      }
   }

   private void connectionToMSContinuously(String serverName, long retrialgap) {
      JMXLogger.logJMXResiliencyWarning(serverName, "There will now be a continuous attempt to connect to to the server at " + retrialgap + " ms interval");
      synchronized(this.msconnectors) {
         ContinuousMSConnector msconnector;
         if (!this.msconnectors.containsKey(serverName)) {
            JMXLogger.logJMXResiliencyWarning(serverName, "There is no continuous thread trying to connect. Creating one.");
            msconnector = new ContinuousMSConnector(serverName, retrialgap);
            WorkManagerFactory.getInstance().getSystem().schedule(msconnector);
            this.msconnectors.put(serverName, msconnector);
         } else {
            msconnector = (ContinuousMSConnector)this.msconnectors.get(serverName);
            if (msconnector.isStopped()) {
               JMXLogger.logJMXResiliencyWarning(serverName, "There is a continuous thread but it is already stopped. Creating one.");
               ContinuousMSConnector newmsconnector = new ContinuousMSConnector(serverName, retrialgap);
               WorkManagerFactory.getInstance().getSystem().schedule(newmsconnector);
               this.msconnectors.put(serverName, newmsconnector);
            } else {
               JMXLogger.logJMXResiliencyWarning(serverName, "There is a continuous connecting thread that is still running. Skipping creating a new thread.");
            }
         }

      }
   }

   private void connectToManagedServerWithLock(String serverName, boolean forceReconnect) {
      Object lock = this.listenerLock.acquireLock(serverName);

      try {
         synchronized(lock) {
            this.connectToManagedServer(serverName, forceReconnect);
         }
      } finally {
         this.listenerLock.releaseLock(serverName);
      }

   }

   private void connectToManagedServer(String serverName, boolean forceReconnect) {
      JMXLogger.logJMXResiliencyWarning(serverName, "Starting JMX connection. forceReconnect value: " + forceReconnect);
      if (!forceReconnect && this.isExistingConnectionGood(serverName)) {
         JMXLogger.logJMXResiliencyWarning(serverName, "No force reconnect but there is already a connection exists for the server. Returning from the method");
      } else {
         JMXServiceURL serviceURL = null;
         String managedServerURL = null;

         String serviceURLString;
         try {
            managedServerURL = getURLManagerService().findAdministrationURL(serverName);
            if (managedServerURL == null) {
               JMXLogger.logJMXResiliencyWarning(serverName, "Can not connect, ManagedServerURL is NULL");
               return;
            }

            ServerURL managedURL = new ServerURL(managedServerURL);
            serviceURL = new JMXServiceURL(managedURL.getProtocol(), managedURL.getHost(), managedURL.getPort(), "/jndi/weblogic.management.mbeanservers.runtime");
         } catch (MalformedURLException var20) {
            serviceURLString = serviceURL != null ? serviceURL.toString() : "<none>";
            JMXLogger.logManagedServerURLMalformed(serverName, serviceURLString, var20);
            throw new AssertionError(" Malformed URL" + var20);
         } catch (UnknownHostException var21) {
            serviceURLString = serviceURL != null ? serviceURL.toString() : "<none>";
            JMXLogger.logManagedServerNotAvailable(serverName, serviceURLString);
            return;
         }

         ManagedMBeanServerConnection connection;
         JMXConnector connector;
         label136: {
            try {
               JMXContext jmxContext = JMXContextHelper.getJMXContext(true);
               jmxContext.setSubject(SecurityServiceManager.sendASToWire(kernelId).getSubject());
               jmxContext.setPartitionName(this.getPartitionName());
               JMXContextHelper.putJMXContext(jmxContext);
               Map m = new HashMap(3);
               m.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
               JMXExecutor executor = new JMXExecutor();
               m.put("jmx.remote.x.fetch.notifications.executor", executor);
               m.put("jmx.remote.x.notification.buffer.size", 50000);
               int timeout = ManagementService.getRuntimeAccess(kernelId).getDomain().getJMX().getInvocationTimeoutSeconds();
               if (timeout > 0) {
                  m.put("jmx.remote.x.request.waiting.timeout", new Long((long)(timeout * 1000)));
               }

               if (managedServerURL != null && managedServerURL.length() > 0) {
                  m.put("java.naming.provider.url", managedServerURL);
               } else {
                  JMXLogger.logJMXResiliencyWarning(serverName, "No URL to Managed Server");
               }

               ServerIdentity serverId = URLManager.findServerIdentity(serverName);
               if (!serverId.getHostURI().contains("-1")) {
                  m.put("weblogic.jndi.provider.rjvm", serverId);
               }

               connector = JMXConnectorFactory.connect(serviceURL, m);
               MBeanServerConnection direct = connector.getMBeanServerConnection();
               connection = new ManagedMBeanServerConnection(direct, serverName, executor, this.isManagedServerNotificationListenersDisabled());
               break label136;
            } catch (Exception var18) {
               JMXLogger.logUnableToContactManagedServer(serverName, serviceURL.toString(), var18);
            } finally {
               JMXContextHelper.removeJMXContext();
            }

            return;
         }

         this.registerConnection(serverName, connection, connector);
         JMXLogger.logEstablishedJMXConnectionWithManagedServer(serverName, serviceURL.toString());
      }
   }

   private boolean isExistingConnectionGood(String serverName) {
      return this.locationToMBeanServerMap.containsKey(serverName) && this.isMBeanServerReachable(serverName);
   }

   private String getPartitionName() {
      String pname = null;

      try {
         pname = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      } catch (Exception var3) {
         pname = "DOMAIN";
      }

      if (!ok(pname)) {
         pname = "DOMAIN";
      }

      return pname;
   }

   private static boolean ok(String s) {
      return s != null && s.length() > 0;
   }

   private void registerConnection(String serverName, ManagedMBeanServerConnection connection, JMXConnector connector) {
      JMXLogger.logJMXResiliencyWarning(serverName, "Registering call backs for server");
      synchronized(this) {
         this.locationToMBeanServerMap.put(serverName, connection);
         this.connectionToServerNameMap.put(connection, serverName);
         this.connectorsByConnectionMap.put(connection, connector);
      }

      this.invokeConnectCallbacks(serverName, connection);
   }

   private DisconnectListener createDisconnectListener() {
      return new DisconnectListener() {
         public void onDisconnect(DisconnectEvent event) {
            String serverName = ((ServerDisconnectEvent)event).getServerName();
            Object lock = MBeanServerConnectionManager.this.listenerLock.acquireLock(serverName);
            ContinuousMSConnector connector = null;

            try {
               synchronized(lock) {
                  JMXLogger.logJMXResiliencyWarning(serverName, "\n Received a DISCONNECT EVENT \n");
                  int retryAttemps = 3;
                  boolean isRetrySucceed = false;

                  for(int attempt = 1; attempt <= retryAttemps; ++attempt) {
                     if (MBeanServerConnectionManager.this.isMBeanServerReachable(serverName)) {
                        isRetrySucceed = true;
                        JMXLogger.logJMXResiliencyWarning(serverName, "MBean Server is reachable. Aborting retry");
                        break;
                     }

                     if (!isRetrySucceed && attempt != retryAttemps) {
                        try {
                           Thread.sleep(2000L);
                        } catch (InterruptedException var18) {
                        }
                     }
                  }

                  if (isRetrySucceed) {
                     MBeanServerConnectionManager.this.attemptReconnection(serverName);
                     return;
                  }

                  JMXLogger.logJMXResiliencyWarning(serverName, "Failed to reconnect after " + retryAttemps + " attempts, disconnecting ");
                  synchronized(MBeanServerConnectionManager.this.msconnectors) {
                     if (MBeanServerConnectionManager.this.msconnectors.containsKey(serverName)) {
                        JMXLogger.logJMXResiliencyWarning(serverName, "Stopping the existing thread that is connecting to the server");
                        connector = (ContinuousMSConnector)MBeanServerConnectionManager.this.msconnectors.get(serverName);
                        connector.stop();
                        MBeanServerConnectionManager.this.msconnectors.remove(serverName);
                        JMXLogger.logJMXResiliencyWarning(serverName, "stopped the existing thread that is connecting to the server");
                     }
                  }

                  MBeanServerConnectionManager.this.cleanupDisconnectedServer(serverName);
               }

               if (connector != null) {
                  while(!connector.isStopped()) {
                     JMXLogger.logJMXResiliencyWarning(serverName, "Still Stopping the existing thread that is connecting to the server");
                     Thread.sleep(1000L);
                  }

                  JMXLogger.logJMXResiliencyWarning(serverName, "stopped the existing thread that is connecting to the server");
               }

               JMXLogger.logDisconnectedJMXConnectionWithManagedServer(serverName);
            } catch (Exception var21) {
               JMXLogger.logExceptionDuringJMXConnectivity(var21);
            } finally {
               MBeanServerConnectionManager.this.listenerLock.releaseLock(serverName);
            }

         }
      };
   }

   private void cleanupDisconnectedServer(String serverName) {
      JMXLogger.logJMXResiliencyWarning(serverName, "Cleaning up disconnected server");
      synchronized(this) {
         ManagedMBeanServerConnection removed = (ManagedMBeanServerConnection)this.locationToMBeanServerMap.remove(serverName);
         if (removed == null) {
            if (debug.isDebugEnabled()) {
               debug.debug("RCXN, Local MBean Server " + serverName + " already Removed ");
            }

            return;
         }

         removed.disconnected();
         this.connectionToServerNameMap.remove(removed);
         this.connectorsByConnectionMap.remove(removed);
         MBeanServerInvocationHandler.clearCache(removed);
      }

      this.invokeDisconnectCallbacks(serverName);
   }

   private void invokeDisconnectCallbacks(String serverName) {
      if (debug.isDebugEnabled()) {
         debug.debug("RCXN, invokeDisconnectCallbacks,for serverName " + serverName);
      }

      Iterator iterator = this.callbacks.iterator();

      while(iterator.hasNext()) {
         MBeanServerConnectionListener mBeanServerConnectionListener = (MBeanServerConnectionListener)iterator.next();

         try {
            mBeanServerConnectionListener.disconnect(serverName);
         } catch (Exception var5) {
            JMXLogger.logExceptionDuringJMXConnectivity(var5);
         }
      }

   }

   private void invokeConnectCallbacks(String serverName, MBeanServerConnection connection) {
      JMXLogger.logJMXResiliencyWarning(serverName, "Initializing callbacks");
      int threadCount = 2;
      ExecutorService executor = Executors.newFixedThreadPool(threadCount);
      Collection tasks = new ArrayList();

      MBeanServerConnectionListener mBeanServerConnectionListener;
      for(Iterator iterator = this.callbacks.iterator(); iterator.hasNext(); tasks.add(new Task(mBeanServerConnectionListener, serverName, connection))) {
         mBeanServerConnectionListener = (MBeanServerConnectionListener)iterator.next();
         if (debug.isDebugEnabled()) {
            debug.debug("RCXN, the call back listener mBeanServerConnectionListener = " + mBeanServerConnectionListener + " is added to a Collection of tasks.");
         }
      }

      try {
         List results;
         try {
            if (debug.isDebugEnabled()) {
               debug.debug("RCXN: start invoking all call back listeners in parallel using ExecutorService.invokeAll()...");
            }

            results = executor.invokeAll(tasks);
         } catch (InterruptedException var18) {
            JMXLogger.logExceptionDuringJMXConnectivity(var18);
            return;
         }

         Iterator var8 = results.iterator();

         while(var8.hasNext()) {
            Future future = (Future)var8.next();

            try {
               if (debug.isDebugEnabled()) {
                  debug.debug("RCXN: get the results returned from the connect() of the call back listeners.");
               }

               future.get();
            } catch (ExecutionException var16) {
               JMXLogger.logExceptionDuringJMXConnectivity(var16.getCause());
            } catch (InterruptedException var17) {
               JMXLogger.logExceptionDuringJMXConnectivity(var17);
            }
         }

      } finally {
         JMXLogger.logJMXResiliencyWarning(serverName, "Callback is done");
         executor.shutdown();
      }
   }

   public void iterateConnections(ConnectionCallback callback, boolean failOnException) throws IOException {
      Iterator it = this.locationToMBeanServerMap.values().iterator();

      while(it.hasNext()) {
         MBeanServerConnection connection = (MBeanServerConnection)it.next();

         try {
            callback.connection(connection);
         } catch (IOException var6) {
            if (debug.isDebugEnabled()) {
               debug.debug("Failed while iterating remote connections ", var6);
            }

            if (failOnException) {
               throw var6;
            }
         }
      }

   }

   public void notifyNewMBeanServer(String serverName) {
      this.connectToManagedServerWithLock(serverName, false);
   }

   public String lookupServerName(MBeanServerConnection mbeanServerConnection) {
      return (String)this.connectionToServerNameMap.get(mbeanServerConnection);
   }

   public synchronized void stop() {
      JMXConnector[] connections = (JMXConnector[])((JMXConnector[])this.connectorsByConnectionMap.values().toArray(new JMXConnector[this.connectorsByConnectionMap.size()]));

      for(int i = 0; i < connections.length; ++i) {
         try {
            connections[i].close();
         } catch (IOException var4) {
         }
      }

   }

   public boolean isManagedServerNotificationsDisabled() {
      boolean isDisabled = Boolean.getBoolean("weblogic.management.disableManagedServerNotifications");
      if (isDisabled) {
         return isDisabled;
      } else {
         return !ManagementService.getRuntimeAccess(kernelId).getDomain().getJMX().isManagedServerNotificationsEnabled();
      }
   }

   public boolean isManagedServerNotificationListenersDisabled() {
      boolean isDisabled = this.isManagedServerNotificationsDisabled();
      if (!isDisabled) {
         return isDisabled;
      } else {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         SNMPAgentMBean agent = domain.getSNMPAgent();
         if (agent != null && agent.isEnabled()) {
            return false;
         } else {
            SNMPAgentDeploymentMBean[] agents = domain.getSNMPAgentDeployments();
            if (agents != null && agents.length > 0) {
               SNMPAgentDeploymentMBean[] var5 = agents;
               int var6 = agents.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  SNMPAgentDeploymentMBean agentDeployment = var5[var7];
                  if (agentDeployment.isEnabled()) {
                     TargetMBean[] targets = agentDeployment.getTargets();
                     if (targets != null && targets.length > 0) {
                        TargetMBean[] var10 = targets;
                        int var11 = targets.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                           TargetMBean target = var10[var12];
                           if (target.getServerNames().contains(domain.getAdminServerName())) {
                              return false;
                           }
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   public void attemptReconnection(String serverName) {
      JMXLogger.logJMXResiliencyWarning(serverName, "Attempting reconnection");
      if (this.isMBeanServerReachable(serverName)) {
         JMXLogger.logJMXResiliencyWarning(serverName, " The server is reachable. Returning from the attempt");
      } else {
         Object lock = this.listenerLock.acquireLock(serverName);

         try {
            synchronized(lock) {
               if (this.isExistingConnectionGood(serverName)) {
                  JMXLogger.logJMXResiliencyWarning(serverName, " The server is reachable. Returning from the attempt");
                  return;
               }

               if (this.lookupMBeanServerConnection(serverName) == null) {
                  JMXLogger.logJMXResiliencyWarning(serverName, " The server is disconnected. Returning from the attempt");
                  return;
               }

               try {
                  this.connectToManagedServer(serverName, true);
               } catch (Throwable var10) {
                  JMXLogger.logExceptionDuringJMXConnectivity(var10);
               }
            }
         } finally {
            this.listenerLock.releaseLock(serverName);
         }

      }
   }

   private boolean isMBeanServerReachable(String serverName) {
      try {
         MBeanServerConnection conn = this.lookupMBeanServerConnection(serverName);
         if (conn != null) {
            conn.getDefaultDomain();
            return true;
         }
      } catch (Throwable var3) {
      }

      return false;
   }

   private boolean findServerInConfiguration(String serverName) {
      DomainMBean domainMBean = null;

      try {
         domainMBean = DomainConfiguration.getInstance().getDomainMBean();
      } catch (IOException var11) {
         throw new AssertionError("IO Exception retrieving Domain Configuration", var11);
      }

      ServerMBean serverMBean = domainMBean.lookupServer(serverName);
      if (serverMBean != null) {
         return true;
      } else {
         ClusterMBean[] clusters = domainMBean.getClusters();
         if (clusters != null && clusters.length != 0) {
            ClusterMBean[] var5 = clusters;
            int var6 = clusters.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ClusterMBean clusterMBean = var5[var7];
               DynamicServersMBean dynamicServersMBean = clusterMBean.getDynamicServers();
               if (dynamicServersMBean != null) {
                  String[] dynamicServerNames = dynamicServersMBean.getDynamicServerNames();
                  if (dynamicServerNames != null && dynamicServerNames.length != 0 && Arrays.asList(dynamicServerNames).contains(serverName)) {
                     return true;
                  }
               }
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private class ConnectDisconnectListenerLock {
      private final Map listenerLocks;

      private ConnectDisconnectListenerLock() {
         this.listenerLocks = new ConcurrentHashMap();
      }

      public synchronized Object acquireLock(String serverName) {
         AtomicInteger lock = (AtomicInteger)this.listenerLocks.get(serverName);
         if (lock == null) {
            lock = new AtomicInteger(1);
            this.listenerLocks.put(serverName, lock);
         } else {
            lock.incrementAndGet();
         }

         return lock;
      }

      public synchronized void releaseLock(String serverName) {
         AtomicInteger lock = (AtomicInteger)this.listenerLocks.get(serverName);
         if (lock != null) {
            int value = lock.decrementAndGet();
            if (value == 0) {
               this.listenerLocks.remove(serverName);
            }
         }

      }

      // $FF: synthetic method
      ConnectDisconnectListenerLock(Object x1) {
         this();
      }
   }

   public interface ConnectionCallback {
      void connection(MBeanServerConnection var1) throws IOException;
   }

   private final class Task implements Callable {
      private final MBeanServerConnectionListener mbsConListener;
      private final String serverName;
      private final MBeanServerConnection connection;

      Task(MBeanServerConnectionListener mbsConnListener, String serverName, MBeanServerConnection connection) {
         this.mbsConListener = mbsConnListener;
         this.serverName = serverName;
         this.connection = connection;
      }

      public Void call() {
         this.mbsConListener.connect(this.serverName, this.connection);
         return null;
      }
   }

   private class ContinuousMSConnector implements Runnable {
      String serverName;
      volatile boolean stopRequested = false;
      boolean stopped = false;
      long INTERVAL = 1000L;

      public ContinuousMSConnector(String aServerName, long interval) {
         this.serverName = aServerName;
         this.INTERVAL = interval;
      }

      public void run() {
         while(true) {
            label107: {
               if (!this.stopRequested) {
                  try {
                     if (ManagementService.getRuntimeAccess(MBeanServerConnectionManager.kernelId).getDomain().lookupServer(this.serverName) == null) {
                        JMXLogger.logJMXResiliencyWarning(this.serverName, "The server does not exist in the configuration. Stopping the continuous connection thread");
                     } else {
                        label106: {
                           Object lock = MBeanServerConnectionManager.this.listenerLock.acquireLock(this.serverName);

                           try {
                              synchronized(lock) {
                                 if (this.stopRequested) {
                                    JMXLogger.logJMXResiliencyWarning(this.serverName, "Stopping the continuous connection thread requested");
                                    break label106;
                                 }

                                 MBeanServerConnectionManager.this.connectToManagedServer(this.serverName, false);
                              }
                           } finally {
                              MBeanServerConnectionManager.this.listenerLock.releaseLock(this.serverName);
                           }

                           if (!MBeanServerConnectionManager.this.isExistingConnectionGood(this.serverName)) {
                              break label107;
                           }

                           JMXLogger.logJMXResiliencyWarning(this.serverName, "The server is reachable. Stopping the continuous connection thread");
                        }
                     }
                  } catch (Exception var12) {
                     JMXLogger.logExceptionDuringJMXConnectivity(new Exception("Exception occurred in continuous thread", var12));
                     break label107;
                  }
               }

               this.stopped = true;
               return;
            }

            try {
               Thread.sleep(this.INTERVAL);
            } catch (InterruptedException var9) {
               JMXLogger.logExceptionDuringJMXConnectivity(new Exception("Exception occurred in continuous thread sleep"));
            }
         }
      }

      public void stop() {
         this.stopRequested = true;
      }

      public boolean isStopped() {
         return this.stopped;
      }
   }

   public interface MBeanServerConnectionListener {
      void connect(String var1, MBeanServerConnection var2);

      void disconnect(String var1);
   }
}
