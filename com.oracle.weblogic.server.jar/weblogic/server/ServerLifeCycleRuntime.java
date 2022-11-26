package weblogic.server;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.naming.NamingException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerStartMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.ServerMigrationRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.ServerStates;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.nodemanager.mbean.NodeManagerTask;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.protocol.URLManager;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.UnknownProtocolException;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.t3.srvr.T3Srvr;
import weblogic.t3.srvr.WebLogicServer;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.client.AggregateProgressBean;
import weblogic.work.ContextWrap;
import weblogic.work.ExecuteThread;
import weblogic.work.WorkManagerFactory;

public final class ServerLifeCycleRuntime extends DomainRuntimeMBeanDelegate implements ServerLifeCycleRuntimeMBean, ServerStates {
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   private static final String SERVER_LIFECYCLE_STORE_NAME = "weblogic.server.lifecycle.store";
   static final String SERVER_STARTUP_MODE_OVERRIDE_PROP = "-Dweblogic.management.startupMode=";
   private static final DebugCategory debug = Debug.getCategory("weblogic.slcruntime");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long DEFAULT_NM_TIMEOUT;
   private final Set tasks = Collections.synchronizedSet(new HashSet());
   private final String serverName;
   private final ServerTemplateMBean serverMBean;
   private String oldState;
   private String currentState;
   private int startCount;
   private boolean stateShouldBeAvailable;
   private transient String cachedBulkQueryState;
   private static final PersistentMap pMap = initStore();
   private ServerProperties mCachedValue;
   private MachineMBean lastKnownMachine;
   private final ServiceHandle runtimeAccessHandle = GlobalServiceLocator.getServiceLocator().getServiceHandle(RuntimeAccess.class, new Annotation[0]);

   ServerLifeCycleRuntime(ServerTemplateMBean serverMBean) throws ManagementException {
      super(serverMBean.getName());
      this.serverMBean = serverMBean;
      this.serverName = serverMBean.getName();
      MachineMBean mmb = serverMBean.getMachine();
      if (mmb != null) {
         try {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            generator.getInstance(mmb).initState(serverMBean);
         } catch (IOException var4) {
         }

      }
   }

   public ServerTemplateMBean getServerMBean() {
      return this.serverMBean;
   }

   static void cleanupStore(ServerTemplateMBean[] servers) {
      if (pMap != null) {
         try {
            Set keySet = pMap.keySet();
            ArrayList obsoleteKeys = new ArrayList();
            obsoleteKeys.addAll(keySet);
            if (servers != null && servers.length > 0) {
               for(int i = 0; i < servers.length; ++i) {
                  if (servers[i] != null) {
                     obsoleteKeys.remove(servers[i].getName());
                  }
               }
            }

            String server;
            for(Iterator iter = obsoleteKeys.iterator(); iter.hasNext(); pMap.remove(server)) {
               server = (String)iter.next();
               if (debug.isEnabled()) {
                  Debug.say("ServerLifeCycleRuntime.cleanupStore() removing entry for " + server);
               }
            }
         } catch (PersistentStoreException var5) {
            if (debug.isEnabled()) {
               Debug.say(var5.getMessage());
            }
         }

      }
   }

   private static PersistentMap initStore() {
      PersistentMap map = null;
      PersistentStore pStore = PersistentStoreManager.getManager().getDefaultStore();
      if (pStore == null) {
         if (debug.isEnabled()) {
            Debug.say("The default persistent store cannot be found.");
         }
      } else {
         try {
            map = pStore.createPersistentMap("weblogic.server.lifecycle.store");
            if (debug.isEnabled()) {
               Set keySet = map.keySet();
               Iterator iter = keySet.iterator();

               while(iter.hasNext()) {
                  Object key = iter.next();
                  Debug.say("--  pMap contains (" + key + ", " + map.get(key) + ")");
               }
            }
         } catch (PersistentStoreException var5) {
            if (debug.isEnabled()) {
               Debug.say(var5.getMessage());
            }
         }
      }

      return map;
   }

   void cleanup() {
      if (pMap != null) {
         try {
            pMap.remove(this.getName());
         } catch (PersistentStoreException var4) {
            if (debug.isEnabled()) {
               Debug.say(var4.getMessage());
            }
         }
      }

      MachineMBean mmb = this.serverMBean.getMachine();
      if (mmb != null) {
         try {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            generator.getInstance(mmb).remove(this.serverMBean);
         } catch (IOException var3) {
         }

      }
   }

   private void saveStore(ServerProperties valueToCache) {
      if (pMap != null) {
         try {
            pMap.put(this.getName(), valueToCache);
            if (debug.isEnabled()) {
               Debug.say("saveStore() adding to pMap(" + this.getName() + ", " + valueToCache + ") pMap size is " + pMap.size());
               Set keySet = pMap.keySet();
               Iterator iter = keySet.iterator();

               while(iter.hasNext()) {
                  Object key = iter.next();
                  Debug.say("--  pMap contains (" + key + ", " + pMap.get(key) + ")");
               }
            }

            this.mCachedValue = valueToCache;
         } catch (PersistentStoreException var5) {
            if (debug.isEnabled()) {
               Debug.say(var5.getMessage());
            }
         }
      }

   }

   private ServerProperties getFromStore() {
      if (this.mCachedValue == null && pMap != null) {
         try {
            this.mCachedValue = (ServerProperties)pMap.get(this.getName());
            if (debug.isEnabled()) {
               Debug.say("getFromStore() got from pMap(" + this.getName() + ", " + this.mCachedValue + ")");
            }
         } catch (PersistentStoreException var2) {
            if (debug.isEnabled()) {
               Debug.say(var2.getMessage());
            }
         }
      }

      return this.mCachedValue;
   }

   public ServerLifeCycleTaskRuntimeMBean start() throws ServerLifecycleException {
      return this.start(false);
   }

   public ServerLifeCycleTaskRuntimeMBean start(boolean disableMsiMode) throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var4;
      try {
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server ...", "start");
         this.tasks.add(taskMBean);
         this.currentState = null;
         if (disableMsiMode) {
            this.startServer(taskMBean, (String)null, "-Dweblogic.msi.disabled=true");
         } else {
            this.startServer(taskMBean, (String)null);
         }

         updateTaskMBeanOnCompletion(taskMBean);
         boolean isServer = Kernel.isServer();
         if (isServer) {
            try {
               if (debug.isEnabled()) {
                  Debug.say("ServerLifecycleRuntime: Calling LifecycleManager start operation for : " + this.serverName);
               }

               LCMHelper.performManagedServerOperation(this.serverName, LCMHelper.Operation.START);
            } catch (Exception var9) {
            }
         }

         var4 = taskMBean;
      } catch (ManagementException var10) {
         throw new ServerLifecycleException(var10);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var4;
   }

   public ServerLifeCycleTaskRuntimeMBean start(String machine) throws ServerLifecycleException {
      return this.start(machine, "RUNNING");
   }

   public ServerLifeCycleTaskRuntimeMBean startInAdmin(String machine) throws ServerLifecycleException {
      return this.start(machine, "ADMIN");
   }

   public ServerLifeCycleTaskRuntimeMBean startInStandby(String machine) throws ServerLifecycleException {
      return this.start(machine, "STANDBY");
   }

   private ServerLifeCycleTaskRuntimeMBean start(String machine, String startingState) throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var14;
      try {
         boolean validMachine = false;
         MachineMBean configuredMachine = this.serverMBean.getMachine();
         if (configuredMachine != null && configuredMachine.getName().equals(machine)) {
            validMachine = true;
         }

         if (!validMachine) {
            MachineMBean[] candidateMachines = this.serverMBean.getCandidateMachines();
            if (candidateMachines != null && candidateMachines.length > 0) {
               validMachine = this.isMachineListed(machine, candidateMachines);
            } else {
               ClusterMBean clusterMbean = this.serverMBean.getCluster();
               if (clusterMbean != null) {
                  MachineMBean[] clusterMachines = clusterMbean.getCandidateMachinesForMigratableServers();
                  if (clusterMachines != null && clusterMachines.length > 0) {
                     validMachine = this.isMachineListed(machine, clusterMachines);
                  }
               }
            }
         }

         if (!validMachine) {
            throw new ServerLifecycleException("Invalid machine name '" + machine + "' or server not configured to run on this machine");
         }

         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server ...", "start");
         this.tasks.add(taskMBean);
         this.currentState = null;
         this.startInMode(taskMBean, machine, startingState);
         updateTaskMBeanOnCompletion(taskMBean);
         var14 = taskMBean;
      } catch (ManagementException var12) {
         throw new ServerLifecycleException(var12);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var14;
   }

   private boolean isMachineListed(String machineName, MachineMBean[] machineMbeans) {
      for(int i = 0; i < machineMbeans.length; ++i) {
         if (machineMbeans[i].getName().equals(machineName)) {
            return true;
         }
      }

      return false;
   }

   public ServerLifeCycleTaskRuntimeMBean startInStandby() throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var2;
      try {
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server in standby mode ...", "startInStandby");
         this.tasks.add(taskMBean);
         this.currentState = null;
         this.startInMode(taskMBean, "STANDBY");
         updateTaskMBeanOnCompletion(taskMBean);
         var2 = taskMBean;
      } catch (ManagementException var6) {
         throw new ServerLifecycleException(var6);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var2;
   }

   public ServerLifeCycleTaskRuntimeMBean startInAdmin() throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var2;
      try {
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server in admin mode ...", "startInAdmin");
         this.tasks.add(taskMBean);
         this.currentState = null;
         this.startInMode(taskMBean, "ADMIN");
         updateTaskMBeanOnCompletion(taskMBean);
         var2 = taskMBean;
      } catch (ManagementException var6) {
         throw new ServerLifecycleException(var6);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var2;
   }

   public ServerLifeCycleTaskRuntimeMBean resume() throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var3;
      try {
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Resuming " + this.serverName + " server ...", "resume");
         this.tasks.add(taskMBean);
         ResumeRequest request = new ResumeRequest(taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var3 = taskMBean;
      } catch (ManagementException var7) {
         throw new ServerLifecycleException(var7);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var3;
   }

   public ServerLifeCycleTaskRuntimeMBean shutdown() throws ServerLifecycleException {
      return this.shutdown(0, false);
   }

   public ServerLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      return this.shutdown(timeout, ignoreSessions, false);
   }

   public ServerLifeCycleTaskRuntimeMBean shutdown(int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var6;
      try {
         this.logAdministratorAddress("Graceful shutdown");
         if (this.getState() == "SHUTDOWN") {
            throw new ServerLifecycleException("Can not get to the relevant ServerRuntimeMBean for server " + this.serverName + ". Server is in SHUTDOWN state and cannot be reached.");
         }

         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Shutting down " + this.serverName + " server ...", "shutdown");
         this.tasks.add(taskMBean);
         ShutdownRequest request = new ShutdownRequest(timeout, ignoreSessions, waitForAllSessions, taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var6 = taskMBean;
      } catch (ManagementException var10) {
         throw new ServerLifecycleException(var10);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var6;
   }

   public ServerLifeCycleTaskRuntimeMBean forceShutdown() throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var3;
      try {
         this.logAdministratorAddress("Force shutdown");
         if (this.getState() == "SHUTDOWN") {
            throw new ServerLifecycleException("Can not get to the relevant ServerRuntimeMBean for server " + this.serverName + ". Server is in SHUTDOWN state and cannot be reached.");
         }

         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Forcefully shutting down " + this.serverName + " server ...", "forceShutdown");
         this.tasks.add(taskMBean);
         ShutdownRequest request = new ShutdownRequest(taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var3 = taskMBean;
      } catch (ManagementException var7) {
         throw new ServerLifecycleException(var7);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var3;
   }

   public ServerLifeCycleTaskRuntimeMBean[] getTasks() {
      return (ServerLifeCycleTaskRuntimeMBean[])((ServerLifeCycleTaskRuntimeMBean[])this.tasks.toArray(new ServerLifeCycleTaskRuntimeMBean[this.tasks.size()]));
   }

   public ServerLifeCycleTaskRuntimeMBean lookupTask(String taskName) {
      Iterator tasksItr = this.tasks.iterator();

      ServerLifeCycleTaskRuntimeMBean task;
      do {
         if (!tasksItr.hasNext()) {
            return null;
         }

         task = (ServerLifeCycleTaskRuntimeMBean)tasksItr.next();
      } while(!task.getName().equals(taskName));

      return task;
   }

   public ServerLifeCycleTaskRuntimeMBean suspend() throws ServerLifecycleException {
      return this.suspend(0, false);
   }

   public ServerLifeCycleTaskRuntimeMBean suspend(int timeout, boolean ignoreSessions) throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var5;
      try {
         this.logAdministratorAddress("Graceful suspend");
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "suspending " + this.serverName + " server ...", "suspendWithTimeout");
         this.tasks.add(taskMBean);
         SuspendRequest request = new SuspendRequest(timeout, ignoreSessions, taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var5 = taskMBean;
      } catch (ManagementException var9) {
         throw new ServerLifecycleException(var9);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var5;
   }

   public ServerLifeCycleTaskRuntimeMBean forceSuspend() throws ServerLifecycleException {
      ServerLifeCycleTaskRuntime var3;
      try {
         this.logAdministratorAddress("Force suspend");
         ServerLifeCycleTaskRuntime taskMBean = new ServerLifeCycleTaskRuntime(this, "Forcefully suspending " + this.serverName + " server ...", "forceSuspend");
         this.tasks.add(taskMBean);
         SuspendRequest request = new SuspendRequest(taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var3 = taskMBean;
      } catch (ManagementException var7) {
         throw new ServerLifecycleException(var7);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var3;
   }

   public void setState(String newState) {
      synchronized(this) {
         if (newState == null || newState.equalsIgnoreCase(this.currentState)) {
            return;
         }

         this.oldState = this.currentState;
         this.currentState = newState;
         if ("RUNNING".equals(newState)) {
            this.saveStore(new ServerProperties(this));
         }

         if (!"SHUTTING_DOWN".equals(newState) && !"FORCE_SHUTTING_DOWN".equals(newState)) {
            if ("STARTING".equals(newState) || "RESUMING".equals(newState) || "RUNNING".equals(newState)) {
               this.stateShouldBeAvailable = true;
               this.saveLastKnownMachine();
            }
         } else {
            this.stateShouldBeAvailable = false;
         }
      }

      this._postSet("State", this.oldState, this.currentState);
   }

   public String getState() {
      String state = this.getStateRemote();
      if (state == null) {
         state = this.getStateNodeManager();
         if (state == null || "UNKNOWN".equalsIgnoreCase(state)) {
            state = this.stateShouldBeAvailable ? "UNKNOWN" : "SHUTDOWN";
         }
      }

      this.clearOldServerLifeCycleTaskRuntimes();
      return state;
   }

   public int getNodeManagerRestartCount() {
      return this.startCount > 0 ? this.startCount - 1 : 0;
   }

   public String getWeblogicHome() {
      String var3;
      try {
         ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + this.serverName + " ]");
         final RemoteLifeCycleOperations remote = this.getLifeCycleOperationsRemote();
         if (remote != null) {
            String var7 = this.executePrivilegedAction(new PrivilegedExceptionAction() {
               public String run() throws RemoteException {
                  return remote.getWeblogicHome();
               }
            }, false);
            return var7;
         }

         ServerProperties cachedValue = this.getFromStore();
         if (cachedValue != null) {
            var3 = cachedValue.getWeblogicHome();
            return var3;
         }

         var3 = null;
      } finally {
         ExecuteThread.updateWorkDescription((String)null);
      }

      return var3;
   }

   public String getMiddlewareHome() {
      String var3;
      try {
         ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + this.serverName + " ]");
         final RemoteLifeCycleOperations remote = this.getLifeCycleOperationsRemote();
         if (remote != null) {
            String var7 = this.executePrivilegedAction(new PrivilegedExceptionAction() {
               public String run() throws RemoteException {
                  return remote.getMiddlewareHome();
               }
            }, false);
            return var7;
         }

         ServerProperties cachedValue = this.getFromStore();
         if (cachedValue == null) {
            var3 = null;
            return var3;
         }

         var3 = cachedValue.getMiddlewareHome();
      } finally {
         ExecuteThread.updateWorkDescription((String)null);
      }

      return var3;
   }

   private String executePrivilegedAction(PrivilegedExceptionAction action, boolean handleExceptions) {
      Throwable thrNested = null;

      try {
         return (String)SecurityServiceManager.runAs(kernelId, kernelId, action);
      } catch (RuntimeException var6) {
         if (handleExceptions) {
            if (var6 instanceof ManagementRuntimeException) {
               thrNested = ((ManagementRuntimeException)var6).getCause();
            } else if (var6 instanceof RemoteRuntimeException) {
               thrNested = ((RemoteRuntimeException)var6).getNested();
            } else if (var6 instanceof RuntimeException) {
               throw new AssertionError(var6);
            }
         }
      } catch (PrivilegedActionException var7) {
         if (handleExceptions) {
            Throwable thr = var7.getCause();
            if (thr instanceof RemoteException) {
               thrNested = ((RemoteException)thr).getCause();
            }
         }
      }

      if (thrNested != null && !isNestedDueToShutdown(thrNested)) {
         throw new AssertionError(thrNested);
      } else {
         return null;
      }
   }

   public String getIPv4URL(String protocol) {
      ServerIdentity remoteId = ServerIdentityManager.findServerIdentity(((RuntimeAccess)this.runtimeAccessHandle.getService()).getDomainName(), this.getName());
      String url = null;

      try {
         if (remoteId != null) {
            url = URLManager.findIPv4URL(remoteId, ProtocolManager.findProtocol(protocol));
         }

         if (url == null) {
            ServerProperties cachedValue = this.getFromStore();
            if (cachedValue != null) {
               url = cachedValue.getIPv4URL(protocol);
            }
         }
      } catch (UnknownProtocolException var5) {
      }

      return url;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public String getIPv6URL(String protocol) {
      ServerIdentity remoteId = ServerIdentityManager.findServerIdentity(((RuntimeAccess)this.runtimeAccessHandle.getService()).getDomainName(), this.getName());
      String url = null;

      try {
         if (remoteId != null) {
            url = URLManager.findIPv6URL(remoteId, ProtocolManager.findProtocol(protocol));
         }

         if (url == null) {
            ServerProperties cachedValue = this.getFromStore();
            if (cachedValue != null) {
               url = cachedValue.getIPv6URL(protocol);
            }
         }
      } catch (UnknownProtocolException var5) {
      }

      return url;
   }

   private String getStateRemote() {
      String var2;
      try {
         ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + this.serverName + " ]");
         final RemoteLifeCycleOperations remote = this.getLifeCycleOperationsRemote();
         if (remote != null) {
            var2 = this.executePrivilegedAction(new PrivilegedExceptionAction() {
               public String run() throws RemoteException {
                  return remote.getState();
               }
            }, true);
            return var2;
         }

         var2 = null;
      } finally {
         ExecuteThread.updateWorkDescription((String)null);
      }

      return var2;
   }

   private String getStateNodeManager() {
      MachineMBean mmb = this.getLastKnownMachine();
      if (mmb == null) {
         return null;
      } else {
         try {
            NodeManagerMBean nmb = mmb.getNodeManager();
            if (nmb != null) {
               ExecuteThread.updateWorkDescription("[ Getting state of " + this.serverName + ", via NodeManager of " + nmb.getName() + " ]");
            }

            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nmr = generator.getInstance(mmb);
            String tmpState = nmr.getState(this.serverMBean);
            if (tmpState.equals("UNKNOWN")) {
               try {
                  nmr.initState(this.serverMBean);
                  tmpState = nmr.getState(this.serverMBean);
               } catch (IOException var11) {
               }
            }

            String var6 = tmpState;
            return var6;
         } catch (IOException var12) {
         } finally {
            ExecuteThread.updateWorkDescription((String)null);
         }

         return null;
      }
   }

   private static boolean isNestedDueToShutdown(Throwable nested) {
      return nested instanceof IOException;
   }

   public int getStateVal() {
      String state = this.getState().intern();
      if (state != "STARTING" && state != "FAILED_RESTARTING") {
         if (state == "SHUTTING_DOWN") {
            return 7;
         } else if (state == "FORCE_SHUTTING_DOWN") {
            return 18;
         } else if (state == "STANDBY") {
            return 3;
         } else if (state == "ADMIN") {
            return 17;
         } else if (state == "SUSPENDING") {
            return 4;
         } else if (state == "RESUMING") {
            return 6;
         } else if (state == "RUNNING") {
            return 2;
         } else if (state == "SHUTDOWN") {
            return 0;
         } else if (state == "FAILED") {
            return 8;
         } else if (state == "ACTIVATE_LATER") {
            return 13;
         } else if (state == "FAILED_NOT_RESTARTABLE") {
            return 14;
         } else {
            return state == "FAILED_MIGRATABLE" ? 15 : 9;
         }
      } else {
         return 1;
      }
   }

   private void startServer(ServerLifeCycleTaskRuntime taskMBean, String machine) {
      this.startServer(taskMBean, machine, (String)null);
   }

   private void startServer(ServerLifeCycleTaskRuntime taskMBean, String machine, String transientServerArgs) {
      try {
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         generator.checkStartPrivileges(this.serverName, SecurityServiceManager.getCurrentSubject(kernelId));
         NodeManagerLifecycleService nm = null;
         if (machine != null) {
            MachineMBean machineMBean = this.getMachine(machine);
            if (machineMBean == null) {
               taskMBean.setError(new IOException("Unknown machine name"));
               return;
            }

            nm = generator.getInstance(machineMBean);
         } else {
            nm = generator.getInstance(this.serverMBean);
         }

         NodeManagerTask nmTask = nm.start(this.serverMBean, transientServerArgs);
         ++this.startCount;
         taskMBean.setNMTask(nmTask);
      } catch (SecurityException var7) {
         taskMBean.setError(var7);
      } catch (IOException var8) {
         taskMBean.setError(var8);
      }

   }

   private MachineMBean getMachine(String machineName) {
      MachineMBean[] machines = ((RuntimeAccess)this.runtimeAccessHandle.getService()).getDomain().getMachines();

      for(int i = 0; i < machines.length; ++i) {
         if (machines[i].getName().equals(machineName)) {
            return machines[i];
         }
      }

      return null;
   }

   private void startInMode(ServerLifeCycleTaskRuntime taskMBean, String startupMode) {
      this.startServer(taskMBean, (String)null, "-Dweblogic.management.startupMode=" + startupMode);
   }

   private void startInMode(ServerLifeCycleTaskRuntime taskMBean, String machine, String startupMode) {
      this.startServer(taskMBean, machine, "-Dweblogic.management.startupMode=" + startupMode);
   }

   private static boolean isSecurityException(Exception e) {
      Object rootCause;
      for(rootCause = e; ((Throwable)rootCause).getCause() != null; rootCause = ((Throwable)rootCause).getCause()) {
      }

      return rootCause instanceof SecurityException;
   }

   private boolean useNodeManagerToShutdown() throws IOException {
      ServerStartMBean targetServerStartMBean = this.serverMBean.getServerStart();
      if (targetServerStartMBean == null) {
         return false;
      } else {
         MachineMBean mc = this.serverMBean.getMachine();
         if (mc == null) {
            return false;
         } else {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nm = generator.getInstance(mc);
            if (nm == null) {
               return false;
            } else {
               nm.kill(this.serverMBean);
               return true;
            }
         }
      }
   }

   private static void updateTaskMBeanOnCompletion(ServerLifeCycleTaskRuntime slcTaskRuntime) {
      if (slcTaskRuntime.getError() != null) {
         slcTaskRuntime.setStatus("FAILED");
      } else {
         slcTaskRuntime.setStatus("TASK COMPLETED");
      }

      slcTaskRuntime.setEndTime(System.currentTimeMillis());
      slcTaskRuntime.setIsRunning(false);
   }

   public String getBulkQueryState() {
      return this.cachedBulkQueryState;
   }

   public void setBulkQueryState(String state) {
      this.cachedBulkQueryState = state;
   }

   public void clearOldServerLifeCycleTaskRuntimes() {
      synchronized(this.tasks) {
         Iterator iter = this.tasks.iterator();

         while(iter.hasNext()) {
            ServerLifeCycleTaskRuntime task = (ServerLifeCycleTaskRuntime)iter.next();
            if (task.getEndTime() > 0L && System.currentTimeMillis() - task.getEndTime() > 1800000L) {
               try {
                  task.unregister();
               } catch (ManagementException var6) {
               }

               iter.remove();
            }
         }

      }
   }

   private RemoteLifeCycleOperations getLifeCycleOperationsRemote() {
      return getLifeCycleOperationsRemote(this.serverName);
   }

   protected static RemoteLifeCycleOperations getLifeCycleOperationsRemote(String serverName, long responseReadTimeout) {
      RuntimeAccess runtimeAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);

      try {
         int timeout = runtimeAccess.getDomain().getJMX().getInvocationTimeoutSeconds();
         ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
         if (responseReadTimeout > 0L) {
            env.setResponseReadTimeout(responseReadTimeout);
         } else if (timeout > 0) {
            env.setResponseReadTimeout((long)(timeout * 1000));
         }

         if (timeout > 0) {
            env.setConnectionTimeout((long)(timeout * 1000));
         }

         String adminstrationURL = getURLManagerService().findAdministrationURL(serverName);
         env.setProviderUrl(adminstrationURL);
         if (debug.isEnabled()) {
            Debug.say("Looking up RemoteLifeCycleOperations with URL " + adminstrationURL);
         }

         Class operationsClass = LocatorUtilities.getServiceImplementationClass(RemoteLifeCycleOperations.class);
         RemoteLifeCycleOperations remote = (RemoteLifeCycleOperations)PortableRemoteObject.narrow(env.getInitialReference(operationsClass), RemoteLifeCycleOperations.class);
         return remote;
      } catch (UnknownHostException var9) {
         ServerLogger.logLookupSLCOperations(serverName, var9);
         return null;
      } catch (NamingException var10) {
         ServerLogger.logLookupSLCOperations(serverName, var10);
         return null;
      }
   }

   public static RemoteLifeCycleOperations getLifeCycleOperationsRemote(String serverName) {
      return getLifeCycleOperationsRemote(serverName, 0L);
   }

   private void logAdministratorAddress(String operation) {
      EndPoint endPoint = ServerHelper.getClientEndPointInternal();
      if (endPoint != null) {
         Channel channel = endPoint.getRemoteChannel();
         if (channel != null) {
            ServerLogger.logAdminAddress(operation + " of " + this.serverName, channel.getInetAddress().getHostAddress());
         }
      }
   }

   private ServerRuntimeMBean getServerRuntimeMBean() {
      DomainRuntimeServiceMBean domainRuntimeServiceMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();
      return domainRuntimeServiceMBean.lookupServerRuntime(this.serverMBean.getName());
   }

   private void saveLastKnownMachine() {
      ServerRuntimeMBean serverRuntimeMBean = this.getServerRuntimeMBean();
      if (serverRuntimeMBean != null) {
         this.lastKnownMachine = this.getMachine(serverRuntimeMBean.getCurrentMachine());
      } else {
         this.lastKnownMachine = null;
      }

   }

   public MachineMBean getLastKnownMachine() {
      if (this.lastKnownMachine == null) {
         ServerRuntimeMBean server = this.getServerRuntimeMBean();
         if (server != null) {
            String currentMachine = server.getCurrentMachine();
            if (currentMachine != null && !currentMachine.isEmpty()) {
               this.lastKnownMachine = this.getMachine(currentMachine);
            }
         }

         if (this.lastKnownMachine == null) {
            this.lastKnownMachine = this.findLastKnownMachineFromMigrationHistory();
         }
      }

      return this.lastKnownMachine != null ? this.lastKnownMachine : this.serverMBean.getMachine();
   }

   private MachineMBean findLastKnownMachineFromMigrationHistory() {
      MigrationDataRuntimeMBean[] history = ManagementService.getDomainAccess(kernelId).getMigrationDataRuntimes();
      if (history == null) {
         history = this.getMigrationHistoryFromClusterMaster();
      }

      if (history != null) {
         for(int i = history.length - 1; i >= 0; --i) {
            MigrationDataRuntimeMBean migrationData = history[i];
            if (this.serverName.equals(migrationData.getServerName())) {
               String currentMachine = migrationData.getMachineMigratedTo();
               return this.getMachine(currentMachine);
            }
         }
      }

      return null;
   }

   private MigrationDataRuntimeMBean[] getMigrationHistoryFromClusterMaster() {
      MigrationDataRuntimeMBean[] history = null;
      ServerRuntimeMBean[] serverRuntimeMBeans = this.getServerRuntimeMBeansOfCluster();
      if (serverRuntimeMBeans != null) {
         for(int i = 0; i < serverRuntimeMBeans.length; ++i) {
            try {
               ServerRuntimeMBean serverRuntimeMBean = serverRuntimeMBeans[i];
               ClusterRuntimeMBean clusterRuntimeMBean = serverRuntimeMBean.getClusterRuntime();
               if (clusterRuntimeMBean != null) {
                  ServerMigrationRuntimeMBean serverMigrationRuntimeMBean = clusterRuntimeMBean.getServerMigrationRuntime();
                  if (serverMigrationRuntimeMBean != null) {
                     try {
                        if (serverMigrationRuntimeMBean.isClusterMaster()) {
                           history = serverMigrationRuntimeMBean.getMigrationData();
                        }
                     } catch (ManagementException var8) {
                        if (debug.isEnabled()) {
                           Debug.say(var8.getMessage());
                        }
                     }
                  }
               }
            } catch (Exception var9) {
               if (debug.isEnabled()) {
                  Debug.say(var9.getMessage());
               }
            }
         }
      }

      return history;
   }

   private ServerRuntimeMBean[] getServerRuntimeMBeansOfCluster() {
      ServerTemplateMBean myServerMBean = this.getServerMBean();
      if (myServerMBean != null) {
         ClusterMBean myClusterMBean = myServerMBean.getCluster();
         if (myClusterMBean != null) {
            ServerMBean[] servers = myClusterMBean.getServers();
            if (servers != null) {
               ArrayList serverRuntimeMBeansList = new ArrayList();
               DomainRuntimeServiceMBean domainRuntimeServiceMBean = ManagementService.getDomainAccess(kernelId).getDomainRuntimeService();

               for(int i = 0; i < servers.length; ++i) {
                  ServerRuntimeMBean serverRuntimeMBean = null;

                  try {
                     serverRuntimeMBean = domainRuntimeServiceMBean.lookupServerRuntime(servers[i].getName());
                  } catch (Exception var9) {
                     continue;
                  }

                  if (serverRuntimeMBean != null) {
                     serverRuntimeMBeansList.add(serverRuntimeMBean);
                  }
               }

               return (ServerRuntimeMBean[])((ServerRuntimeMBean[])serverRuntimeMBeansList.toArray(new ServerRuntimeMBean[serverRuntimeMBeansList.size()]));
            }
         }
      }

      return null;
   }

   protected static long computeTimeOut(boolean isShutdown, long timeOut, long configuredTimeoutVal, long gracefulShutdownTimeout) {
      long responseReadTimeout = 0L;
      if (timeOut == 0L) {
         return responseReadTimeout;
      } else {
         if (isShutdown) {
            responseReadTimeout = computeShutdownTimeOut(configuredTimeoutVal, timeOut, gracefulShutdownTimeout);
         } else if (timeOut > 0L) {
            responseReadTimeout = timeOut * 1000L + 5000L;
         }

         return responseReadTimeout;
      }
   }

   private static long computeShutdownTimeOut(long responseReadTimeout, long timeOut, long gracefulShutdownTimeout) {
      if (timeOut > 0L) {
         responseReadTimeout += timeOut;
      } else {
         responseReadTimeout += gracefulShutdownTimeout;
      }

      if (responseReadTimeout > 0L) {
         responseReadTimeout = responseReadTimeout * 1000L + 5000L;
      }

      return responseReadTimeout;
   }

   public String getProgressAsXml() throws ServerLifecycleException {
      RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessHandle.getService();
      String localServerName = runtimeAccess.getServerName();
      if (GeneralUtilities.safeEquals(this.serverName, localServerName)) {
         String domainName = runtimeAccess.getDomainName();
         WebLogicServer srvr = T3Srvr.getT3Srvr();
         String state = srvr.getState();
         AggregateProgressBean apb = ((ProgressTrackerRegistrar)LocatorUtilities.getService(ProgressTrackerRegistrar.class)).getAggregateProgress();
         apb.setDomainName(domainName);
         apb.setServerName(this.serverName);
         apb.setServerDisposition(state);
         return apb.getXmlVersionOfAggregateState(new String[0]);
      } else {
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)LocatorUtilities.getService(NodeManagerLifecycleServiceGenerator.class);
         NodeManagerLifecycleService nm = generator.getInstance(this.serverMBean);

         try {
            return nm.getProgress(this.serverMBean);
         } catch (IOException var7) {
            throw new ServerLifecycleException(var7);
         }
      }
   }

   static long getDefault_NM_Timeout() {
      return DEFAULT_NM_TIMEOUT;
   }

   public String toString() {
      return "ServerLifeCycleRuntime(" + this.serverName + "," + System.identityHashCode(this) + ")";
   }

   static {
      boolean useOldNMTimeout = Boolean.getBoolean("useOldShutdownNMTimeout");
      if (useOldNMTimeout) {
         DEFAULT_NM_TIMEOUT = 5000L;
      } else {
         DEFAULT_NM_TIMEOUT = 120000L;
      }

   }

   static class ServerProperties implements Serializable {
      private String mWeblogicHome;
      private String mMiddlewareHome;
      private HashMap mIPv4URLs;
      private HashMap mIPv6URLs;

      ServerProperties(ServerLifeCycleRuntime slc) {
         this.mWeblogicHome = slc.getWeblogicHome();
         this.mMiddlewareHome = slc.getMiddlewareHome();
         String[] protocols = ProtocolManager.getProtocols();
         if (protocols != null && protocols.length > 0) {
            this.mIPv4URLs = new HashMap();
            this.mIPv6URLs = new HashMap();

            for(int i = 0; i < protocols.length; ++i) {
               String protocol = protocols[i];
               String v4url = slc.getIPv4URL(protocol);
               String v6url = slc.getIPv6URL(protocol);
               if (v4url != null) {
                  this.mIPv4URLs.put(protocol, v4url);
               }

               if (v6url != null) {
                  this.mIPv6URLs.put(protocol, v6url);
               }
            }
         }

      }

      public String getWeblogicHome() {
         return this.mWeblogicHome;
      }

      public String getMiddlewareHome() {
         return this.mMiddlewareHome;
      }

      public String getIPv4URL(String protocol) {
         String url = null;
         if (this.mIPv4URLs != null) {
            url = (String)this.mIPv4URLs.get(protocol);
         }

         return url;
      }

      public String getIPv6URL(String protocol) {
         String url = null;
         if (this.mIPv6URLs != null) {
            url = (String)this.mIPv6URLs.get(protocol);
         }

         return url;
      }

      public String toString() {
         return "ServerProperties(weblogicHome: " + this.mWeblogicHome + ", middlewareHome: " + this.mMiddlewareHome + ")";
      }
   }

   private final class ResumeRequest implements Runnable {
      private final ServerLifeCycleTaskRuntime taskMBean;

      ResumeRequest(ServerLifeCycleTaskRuntime taskMBean) {
         this.taskMBean = taskMBean;
      }

      public void run() {
         try {
            ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + ServerLifeCycleRuntime.this.serverName + " ]");
            RemoteLifeCycleOperations srvr = ServerLifeCycleRuntime.this.getLifeCycleOperationsRemote();
            if (srvr == null) {
               throw new ServerLifecycleException("Can not get to the relevant ServerRuntimeMBean");
            }

            srvr.resume();
         } catch (Exception var5) {
            this.taskMBean.setError(var5);
         } finally {
            ServerLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
            ExecuteThread.updateWorkDescription((String)null);
         }

      }
   }

   private final class SuspendRequest implements Runnable {
      private int timeout;
      private final boolean forceSuspend;
      private boolean ignoreSessions;
      private final ServerLifeCycleTaskRuntime taskMBean;

      SuspendRequest(int timeout, boolean ignoreSessions, ServerLifeCycleTaskRuntime taskMBean) {
         this.timeout = timeout;
         this.ignoreSessions = ignoreSessions;
         this.forceSuspend = false;
         this.taskMBean = taskMBean;
      }

      SuspendRequest(ServerLifeCycleTaskRuntime taskMBean) {
         this.forceSuspend = true;
         this.taskMBean = taskMBean;
      }

      public void run() {
         try {
            ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + ServerLifeCycleRuntime.this.serverName + " ]");
            long responseReadTimeout = 0L;
            if (this.timeout > 0) {
               responseReadTimeout = (long)(this.timeout * 1000 + 5000);
            }

            RemoteLifeCycleOperations srvr = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(ServerLifeCycleRuntime.this.serverName, responseReadTimeout);
            if (srvr == null) {
               throw new ServerLifecycleException("Can not get to the relevant ServerRuntimeMBean");
            }

            if (this.forceSuspend) {
               srvr.forceSuspend();
            } else {
               srvr.suspend(this.timeout, this.ignoreSessions);
            }
         } catch (Exception var7) {
            this.taskMBean.setError(var7);
         } finally {
            ServerLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
            ExecuteThread.updateWorkDescription((String)null);
         }

      }
   }

   private final class ShutdownRequest implements Runnable {
      private int timeout;
      private final boolean forceShutdown;
      private boolean ignoreSessions;
      private boolean waitForAllSessions;
      private final ServerLifeCycleTaskRuntime taskMBean;

      ShutdownRequest(int timeout, boolean ignoreSessions, ServerLifeCycleTaskRuntime taskMBean) {
         this(timeout, ignoreSessions, false, taskMBean);
      }

      ShutdownRequest(int timeout, boolean ignoreSessions, boolean waitForAllSessions, ServerLifeCycleTaskRuntime taskMBean) {
         this.timeout = timeout;
         this.ignoreSessions = ignoreSessions;
         this.waitForAllSessions = waitForAllSessions;
         this.forceShutdown = false;
         this.taskMBean = taskMBean;
      }

      ShutdownRequest(ServerLifeCycleTaskRuntime taskMBean) {
         this.forceShutdown = true;
         this.taskMBean = taskMBean;
      }

      public void run() {
         long begin = System.currentTimeMillis();

         try {
            Exception e;
            try {
               ExecuteThread.updateWorkDescription("[ Executing/Getting RemoteLifeCycleOperations for server : " + ServerLifeCycleRuntime.this.serverName + " ]");
               long responseReadTimeout = ServerLifeCycleRuntime.computeTimeOut(!this.forceShutdown, (long)this.timeout, (long)ServerLifeCycleRuntime.this.serverMBean.getServerLifeCycleTimeoutVal(), (long)ServerLifeCycleRuntime.this.serverMBean.getGracefulShutdownTimeout());
               RemoteLifeCycleOperations srvr = ServerLifeCycleRuntime.getLifeCycleOperationsRemote(ServerLifeCycleRuntime.this.serverName, responseReadTimeout);
               if (srvr == null) {
                  Loggable loggable = ServerLogger.logRemoteServerLifeCycleRuntimeNotFoundLoggable(this.taskMBean.getServerName());
                  throw new ServerLifecycleException(loggable.getMessageText());
               }

               if (this.forceShutdown) {
                  srvr.forceShutdown();
               } else {
                  srvr.shutdown(this.timeout, this.ignoreSessions, this.waitForAllSessions);
               }

               this.confirmNMStatus(begin);
               return;
            } catch (RemoteRuntimeException var13) {
               Throwable t = var13.getNestedException();
               if (ServerLifeCycleRuntime.debug.isEnabled()) {
                  Debug.say("Got a RemoteRuntimeException shutting down with nested exception:" + t);
               }

               if (!(t instanceof PeerGoneException)) {
                  this.taskMBean.setError(var13);
               } else {
                  this.confirmNMStatus(begin);
               }

               return;
            } catch (Exception var14) {
               e = var14;
               if (ServerLifeCycleRuntime.debug.isEnabled()) {
                  Debug.say("Got an Exception shutting down with exeption " + var14);
               }
            }

            if (!(var14 instanceof PeerGoneException)) {
               try {
                  boolean nmShutdown = !ServerLifeCycleRuntime.isSecurityException(e);
                  if (!nmShutdown) {
                     AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(ServerLifeCycleRuntime.kernelId);
                     nmShutdown = SubjectUtils.doesUserHaveAnyAdminRoles(subject);
                  }

                  if (!this.forceShutdown || !nmShutdown || !ServerLifeCycleRuntime.this.useNodeManagerToShutdown()) {
                     this.taskMBean.setError(e);
                     return;
                  }

                  return;
               } catch (IOException var12) {
                  var12.initCause(var14);
                  this.taskMBean.setError(var12);
                  return;
               }
            }

            this.confirmNMStatus(begin);
         } finally {
            ServerLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
            ExecuteThread.updateWorkDescription((String)null);
         }

      }

      private void confirmNMStatus(long begin) {
         long to = 0L;
         long start = System.currentTimeMillis();
         if (this.timeout == 0) {
            to = ServerLifeCycleRuntime.getDefault_NM_Timeout() + start - begin;
         } else {
            to = (long)this.timeout * 1000L;
         }

         long end = 0L;

         String state;
         for(long interval = 500L; to > 0L; begin = end) {
            state = ServerLifeCycleRuntime.this.getStateNodeManager();
            if (state == null || "SHUTDOWN".equals(state) || "FAILED_NOT_RESTARTABLE".equals(state)) {
               return;
            }

            try {
               Thread.sleep(interval);
            } catch (InterruptedException var13) {
            }

            end = System.currentTimeMillis();
            if (end - start > 5000L) {
               interval = 1000L;
            }

            to -= end - begin;
         }

         state = ServerLogger.getServerProcessKillMessageLoggable(this.taskMBean.getServerName()).getMessageText();
         this.taskMBean.setError(new Exception(state));
      }
   }
}
