package weblogic.cluster.singleton;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.cluster.GroupMessage;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class ReplicatedSingletonServicesStateManager implements SingletonServicesStateManager, SingletonServicesStateManagerRemote, RecoverListener {
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private static final int DEFAULT_SIZE = 32;
   private static final String VERSION_STRING = "ReplicatedSingletonServicesStateManager.Version";
   private static final Integer VERSION_NONE = new Integer(Integer.MAX_VALUE);
   private static final int EXECUTE_MESSAGE = 1;
   private static final int GET_ALL_SERVICES_STATE = 2;
   private static final int STORE_MESSAGE_FOR_NEXT_STATE_MANAGER = 3;
   private static final int GET_PENDING_MESSAGES = 4;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ConcurrentHashMap vmVideMap = new ConcurrentHashMap();
   private static final ConcurrentHashMap vmVidePendingMessagesMap = new ConcurrentHashMap();
   private static String localServerName;
   private static ClusterMBean clusterMBean;
   private static int unicast_servers_to_send;
   private static int unicast_servers_to_recv;
   private final String instanceName;
   private final LeaseManager leaseManager;
   private final MulticastSession multicastSession;
   private boolean isActive = false;
   private Integer lastVersionSent = null;

   public ReplicatedSingletonServicesStateManager(String name, LeaseManager lm) {
      this.leaseManager = lm;
      this.instanceName = name;
      ConcurrentHashMap localServicesStateMap = new ConcurrentHashMap(32);
      vmVideMap.put(this.instanceName, localServicesStateMap);
      ArrayList pendingMessages = new ArrayList();
      vmVidePendingMessagesMap.put(this.instanceName, pendingMessages);
      ClusterService cs = ClusterService.getClusterServiceInternal();
      if (cs == null) {
         throw new RuntimeException("This server is not in a cluster.");
      } else {
         this.multicastSession = cs.createMulticastSession(this, 1, false, true);
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         localServerName = LocalServerIdentity.getIdentity().getServerName();
         clusterMBean = domain.lookupServer(localServerName).getCluster();
         int totalServersInCluster = clusterMBean.getServers().length;

         String numServers;
         try {
            numServers = System.getProperty("weblogic.cluster.singleton.SendPendingMessagesToServers", "3");
            unicast_servers_to_send = Integer.parseInt(numServers);
         } catch (Exception var10) {
            unicast_servers_to_send = 3;
         }

         unicast_servers_to_send = totalServersInCluster > unicast_servers_to_send ? unicast_servers_to_send : totalServersInCluster;

         try {
            numServers = System.getProperty("weblogic.cluster.singleton.ReceivePendingMessagesFromServers", String.valueOf(totalServersInCluster));
            unicast_servers_to_recv = Integer.parseInt(numServers);
         } catch (Exception var9) {
            unicast_servers_to_recv = totalServersInCluster;
         }

         unicast_servers_to_recv = totalServersInCluster > unicast_servers_to_recv ? unicast_servers_to_recv : totalServersInCluster;
         syncStateFromActiveStateManager(this.instanceName);
         if (getVersion(this.instanceName) == null) {
            incrementVersion(this.instanceName);
         }

      }
   }

   public Serializable invoke(int methodNum, Serializable arguments) {
      Serializable result = null;
      Message mesg;
      switch (methodNum) {
         case 1:
            mesg = (Message)arguments;
            mesg.executeOnActiveStateManager(this);
            result = new Boolean(true);
            break;
         case 2:
            result = (Serializable)this.getAllServicesState();
            break;
         case 3:
            mesg = (Message)arguments;
            if (DEBUG) {
               p("Adding pending message: " + mesg);
            }

            addLocalPendingMessages(this.instanceName, mesg);
            result = new Boolean(true);
            break;
         case 4:
            result = (Serializable)getLocalPendingMessages(this.instanceName);
            break;
         case 1001:
            Map argumentMap = (Map)arguments;
            String sender = (String)argumentMap.get("Sender");
            String svcName = (String)argumentMap.get("SvcName");
            SingletonServicesState state = (SingletonServicesState)argumentMap.get("SvcState");
            String owner = null;

            try {
               owner = this.leaseManager.findOwner(svcName);
               if (owner != null) {
                  owner = LeaseManager.getServerNameFromOwnerIdentity(owner);
               }
            } catch (LeasingException var10) {
            }

            boolean status = false;
            if (owner != null && (owner == null || !sender.equals(owner))) {
               if (DEBUG) {
                  p("Ignoring the call for update state - " + state + " for service " + svcName + "as the sender - " + sender + " does not match the current owner - " + owner);
               }
            } else {
               status = this.storeServiceState(svcName, state);
            }

            result = new Boolean(status);
      }

      return (Serializable)result;
   }

   public boolean checkServiceState(String svcName, int state) {
      Map localServicesStateMap = getLocalMap(this.instanceName);
      synchronized(localServicesStateMap) {
         SingletonServicesState storedState = (SingletonServicesState)localServicesStateMap.get(svcName);
         return storedState != null && storedState.getState() == state;
      }
   }

   public SingletonServicesState getServiceState(String svcName) {
      Map localServicesStateMap = getLocalMap(this.instanceName);
      synchronized(localServicesStateMap) {
         return (SingletonServicesState)localServicesStateMap.get(svcName);
      }
   }

   public Map getAllServicesState() {
      Map localServicesStateMap = getLocalMap(this.instanceName);
      synchronized(localServicesStateMap) {
         HashMap copy = new HashMap(localServicesStateMap);
         return copy;
      }
   }

   public boolean storeServiceState(String svcName, SingletonServicesState newState) {
      boolean result = false;
      if (this.isActive) {
         Map localServicesStateMap = getLocalMap(this.instanceName);
         Integer nextVersion = null;
         synchronized(localServicesStateMap) {
            SingletonServicesState oldState = (SingletonServicesState)localServicesStateMap.put(svcName, newState);
            nextVersion = incrementVersion(this.instanceName);
         }

         result = this.sendUpdateMessage(nextVersion, svcName, newState, false, true);
      } else {
         result = this.sendUpdateMessage(VERSION_NONE, svcName, newState, false, false);
      }

      return result;
   }

   public boolean removeServiceState(String svcName) {
      boolean result = false;

      try {
         Map localServicesStateMap = getLocalMap(this.instanceName);
         Integer nextVersion = null;
         synchronized(localServicesStateMap) {
            SingletonServicesState oldValue = (SingletonServicesState)localServicesStateMap.remove(svcName);
            nextVersion = incrementVersion(this.instanceName);
         }

         result = this.sendUpdateMessage(nextVersion, svcName, (SingletonServicesState)null, true, true);
      } catch (Exception var9) {
      }

      return result;
   }

   public synchronized void syncState() {
      Integer version = getVersion(this.instanceName);
      if (this.lastVersionSent == null || !this.lastVersionSent.equals(version)) {
         Message hbm = new HeartbeatMessage(this.instanceName, version);
         boolean useUnicast = false;
         if (!ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
            this.sendGroupMessage(hbm);
            this.lastVersionSent = version;
         }
      }
   }

   public void leaseAcquired() {
      this.isActive = true;
      long beginTime = System.currentTimeMillis();
      FetchPendingMessages fpMsg = new FetchPendingMessages();
      fpMsg.run();
      if (DEBUG) {
         p("time for fetching pending messages: " + (System.currentTimeMillis() - beginTime));
      }

   }

   private void sortPendingMessages(List pendingMessages, Set pendingMessagesFromPreviousStateMgr, List pendingMessagesForNextStateMgr) {
      Iterator itr = pendingMessages.iterator();

      while(itr.hasNext()) {
         Message mesg = (Message)itr.next();
         if (mesg.messageID.equals(VERSION_NONE)) {
            if (!pendingMessagesForNextStateMgr.contains(mesg)) {
               pendingMessagesForNextStateMgr.add(mesg);
               if (DEBUG) {
                  p("Added pending message for current state manager - " + mesg);
               }
            }
         } else if (pendingMessagesFromPreviousStateMgr.add(mesg) && DEBUG) {
            p("Added message from previous state manager - " + mesg);
         }
      }

   }

   public synchronized void lostLease() {
      this.isActive = false;
      this.lastVersionSent = null;
   }

   public GroupMessage createRecoverMessage() {
      return this.isActive ? new StateDumpMessage(this.instanceName, (Serializable)getLocalMap(this.instanceName), getVersion(this.instanceName)) : new StateDumpMessage(this.instanceName, (Serializable)null, getVersion(this.instanceName));
   }

   private static Integer getVersion(String name) {
      Integer version = null;
      Map localServicesStateMap = getLocalMap(name);
      synchronized(localServicesStateMap) {
         SingletonServicesState state = (SingletonServicesState)localServicesStateMap.get("ReplicatedSingletonServicesStateManager.Version");
         if (state != null) {
            version = (Integer)state.getStateData();
         }

         return version;
      }
   }

   private static void setVersion(String name, Integer newVersion) {
      SingletonServicesState state = new SingletonServicesState(1);
      state.setStateData(newVersion);
      Map localServicesStateMap = getLocalMap(name);
      synchronized(localServicesStateMap) {
         localServicesStateMap.put("ReplicatedSingletonServicesStateManager.Version", state);
      }
   }

   private static Integer incrementVersion(String name) {
      Map localServicesStateMap = getLocalMap(name);
      Integer newVersion = null;
      synchronized(localServicesStateMap) {
         Integer version = getVersion(name);
         if (version == null) {
            version = new Integer(0);
         }

         int nextVersion = version % 2147483646 + 1;
         newVersion = new Integer(nextVersion);
         setVersion(name, newVersion);
         return newVersion;
      }
   }

   private static List getLocalPendingMessages(String name) {
      ArrayList localPendingMessagesList = (ArrayList)vmVidePendingMessagesMap.get(name);
      if (localPendingMessagesList == null) {
         return null;
      } else {
         synchronized(localPendingMessagesList) {
            ArrayList list = (ArrayList)localPendingMessagesList.clone();
            localPendingMessagesList.clear();
            return list;
         }
      }
   }

   private static void addLocalPendingMessages(String name, Message gm) {
      ArrayList localPendingMessagesList = (ArrayList)vmVidePendingMessagesMap.get(name);
      if (localPendingMessagesList != null) {
         synchronized(localPendingMessagesList) {
            localPendingMessagesList.add(gm);
         }
      }
   }

   private boolean sendUpdateMessage(Integer version, Serializable key, SingletonServicesState newValue, boolean removeState, boolean sentFromActiveStateMgr) {
      boolean result = false;
      Message updateMesg = new UpdateMessage(this.instanceName, version, key, newValue, removeState);
      if (sentFromActiveStateMgr) {
         if (!ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown()) {
            result = this.sendGroupMessage(updateMesg);
         } else {
            this.sendPendingMessagesForNextStateManager(updateMesg, (long)clusterMBean.getHealthCheckIntervalMillis());
            result = true;
         }
      } else {
         SingletonServicesStateManagerRemote remote = null;

         try {
            String srvrName = MigratableServerService.theOne().findSingletonMaster();
            if (srvrName != null) {
               remote = getSingletonServicesStateManagerRemote(srvrName, (long)clusterMBean.getHealthCheckIntervalMillis());
            }
         } catch (LeasingException var11) {
         }

         if (remote != null) {
            try {
               Boolean status = (Boolean)remote.invoke(1, updateMesg);
               result = status;
            } catch (RemoteException var10) {
            }
         } else {
            this.sendPendingMessagesForNextStateManager(updateMesg, (long)clusterMBean.getHealthCheckIntervalMillis());
            result = true;
         }
      }

      return result;
   }

   private boolean sendGroupMessage(Message gm) {
      boolean result = false;
      if (!this.isActive) {
         if (DEBUG) {
            p("Inactive state manager cannot send group message: " + gm);
         }

         return false;
      } else {
         try {
            this.multicastSession.send(gm);
            result = true;
         } catch (IOException var4) {
            ClusterLogger.logMulticastSendErrorMsg("Error in sending message: " + var4);
         }

         return result;
      }
   }

   private void sendPendingMessagesForNextStateManager(Message mesg, long timeout) {
      if (!mesg.isPendingModeExecutionAllowed()) {
         if (DEBUG) {
            p("Not sendPendingMessagesForNextStateManager - " + mesg);
         }

      } else {
         Collection remMembers = ClusterService.getClusterServiceInternal().getAllRemoteMembers();
         int numServersToTry = remMembers.size() > unicast_servers_to_send ? unicast_servers_to_send : remMembers.size();
         if (DEBUG) {
            p("Total number of servers to push pending message = " + numServersToTry);
         }

         Set serverSet = this.sortServersBasedonSeniority(remMembers);
         int index = 0;
         Iterator itr = serverSet.iterator();

         while(itr.hasNext()) {
            ClusterMemberInfo info = (ClusterMemberInfo)itr.next();
            String srvrName = info.serverName();

            try {
               SingletonServicesStateManagerRemote remote = getSingletonServicesStateManagerRemote(srvrName, timeout);
               if (remote == null) {
                  continue;
               }

               remote.invoke(3, mesg);
               if (DEBUG) {
                  p("Sent pending message to " + srvrName);
               }
            } catch (Exception var12) {
               continue;
            }

            ++index;
            if (index == numServersToTry) {
               break;
            }
         }

      }
   }

   private Set sortServersBasedonSeniority(Collection servers) {
      Set serverSet = new TreeSet(new Comparator() {
         public int compare(Object o1, Object o2) {
            if (o1 instanceof ClusterMemberInfo && o2 instanceof ClusterMemberInfo) {
               Long joinTime1 = new Long(((ClusterMemberInfo)o1).joinTime());
               Long joinTime2 = new Long(((ClusterMemberInfo)o2).joinTime());
               return joinTime1.compareTo(joinTime2);
            } else {
               throw new IllegalArgumentException("Objects not instanceof ClusterMemberInfo");
            }
         }
      });
      serverSet.addAll(servers);
      return serverSet;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static SingletonServicesStateManagerRemote getSingletonServicesStateManagerRemote(String srvrName, long timeout) {
      SingletonServicesStateManagerRemote remote = null;

      try {
         String url = null;

         try {
            url = getURLManagerService().findAdministrationURL(srvrName);
         } catch (UnknownHostException var8) {
            MigratableServerService.theOne();
            url = MigratableServerService.findURLOfUnconnectedServer(srvrName);
         }

         if (url == null) {
            if (DEBUG) {
               p("Unable to find the admin url for active state manager - " + srvrName);
            }

            return null;
         }

         if (DEBUG) {
            p("Looking up SingletonServicesStateManagerRemote on " + srvrName + " with url " + url);
         }

         Hashtable table = new Hashtable();
         table.put("java.naming.provider.url", url);
         table.put("weblogic.jndi.requestTimeout", new Long(timeout));
         table.put("weblogic.jndi.responseReadTimeout", new Long(timeout));
         Environment env = new Environment(table);
         Context ctx = env.getInitialContext();
         remote = (SingletonServicesStateManagerRemote)ctx.lookup("weblogic.cluster.singleton.SingletonServicesStateManager");
      } catch (Exception var9) {
      }

      return remote;
   }

   private static void syncStateFromActiveStateManager(String name) {
      try {
         String srvrName = MigratableServerService.theOne().findSingletonMaster();
         if (srvrName == null || srvrName.equals(localServerName)) {
            if (DEBUG) {
               p("Not able to sync state as active state manager - " + srvrName + " not yet available or the local state manager is the active one.");
            }

            return;
         }

         SingletonServicesStateManagerRemote smr = getSingletonServicesStateManagerRemote(srvrName, (long)clusterMBean.getHealthCheckIntervalMillis());
         if (smr != null) {
            Map newState = (Map)smr.invoke(2, name);
            if (DEBUG) {
               p("New Services State Map:" + newState);
            }

            replaceLocalMap(name, newState);
         }
      } catch (Exception var4) {
         if (DEBUG) {
            p("Failed to get a state dump because of :" + var4);
         }
      }

   }

   private static Map getLocalMap(String name) {
      return (Map)vmVideMap.get(name);
   }

   private static void replaceLocalMap(String name, Map newMap) {
      Map m = getLocalMap(name);
      if (m == null) {
         if (DEBUG) {
            p("Local State Manager not yet initialized. Not replacing local map.");
         }

      } else {
         synchronized(m) {
            m.clear();
            m.putAll(newMap);
         }
      }
   }

   private static void p(Object o) {
      SingletonServicesDebugLogger.debug("ReplicatedSingletonServicesStateManager: " + o);
   }

   private class FetchPendingMessages implements Runnable {
      private FetchPendingMessages() {
      }

      public void run() {
         Set pendingMessagesSetFromPreviousStateMgr = new TreeSet();
         List pendingMessagesForNextStateMgr = new ArrayList();
         List pendingMessages = ReplicatedSingletonServicesStateManager.getLocalPendingMessages(ReplicatedSingletonServicesStateManager.this.instanceName);
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Got " + pendingMessages.size() + " local pending messages");
         }

         ReplicatedSingletonServicesStateManager.this.sortPendingMessages(pendingMessages, pendingMessagesSetFromPreviousStateMgr, pendingMessagesForNextStateMgr);
         Collection remMembers = ClusterService.getClusterServiceInternal().getAllRemoteMembers();
         int numServersToTry = remMembers.size() > ReplicatedSingletonServicesStateManager.unicast_servers_to_recv ? ReplicatedSingletonServicesStateManager.unicast_servers_to_recv : remMembers.size();
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Fetching pending messages from " + numServersToTry + " remote members");
         }

         Set serverSet = ReplicatedSingletonServicesStateManager.this.sortServersBasedonSeniority(remMembers);
         int index = 0;
         Iterator itr = serverSet.iterator();

         while(itr.hasNext()) {
            ClusterMemberInfo info = (ClusterMemberInfo)itr.next();
            String srvrName = info.serverName();
            SingletonServicesStateManagerRemote remote = ReplicatedSingletonServicesStateManager.getSingletonServicesStateManagerRemote(srvrName, (long)ReplicatedSingletonServicesStateManager.clusterMBean.getHealthCheckIntervalMillis());
            if (remote != null) {
               try {
                  List messageList = (List)remote.invoke(4, (Serializable)null);
                  if (ReplicatedSingletonServicesStateManager.DEBUG) {
                     ReplicatedSingletonServicesStateManager.p("Got " + messageList.size() + " pending messages from " + srvrName);
                  }

                  ReplicatedSingletonServicesStateManager.this.sortPendingMessages(messageList, pendingMessagesSetFromPreviousStateMgr, pendingMessagesForNextStateMgr);
               } catch (Exception var13) {
                  continue;
               }

               ++index;
               if (index == numServersToTry) {
                  break;
               }
            }
         }

         Iterator pendingMessagesItr = pendingMessagesSetFromPreviousStateMgr.iterator();

         while(pendingMessagesItr.hasNext()) {
            Message gm = (Message)pendingMessagesItr.next();
            if (gm.isPendingModeExecutionAllowed()) {
               gm.executeOnActiveStateManager(ReplicatedSingletonServicesStateManager.this);
            }
         }

         Iterator itr1 = pendingMessagesForNextStateMgr.iterator();

         while(itr1.hasNext()) {
            Message gmx = (Message)itr1.next();
            if (gmx.isPendingModeExecutionAllowed()) {
               gmx.executeOnActiveStateManager(ReplicatedSingletonServicesStateManager.this);
            }
         }

      }

      // $FF: synthetic method
      FetchPendingMessages(Object x1) {
         this();
      }
   }

   protected static class StateDumpMessage extends Message {
      private static final long serialVersionUID = -3290218502688112771L;
      private Serializable stateDump;

      StateDumpMessage(String cn, Serializable sd, Integer version) {
         super(cn, version);
         this.stateDump = sd;
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Sending :" + this);
         }

      }

      public void execute(HostID id) {
         if (this.stateDump != null) {
            if (ReplicatedSingletonServicesStateManager.DEBUG) {
               ReplicatedSingletonServicesStateManager.p("Executing: " + this);
            }

            ReplicatedSingletonServicesStateManager.replaceLocalMap(this.name, (Map)this.stateDump);
         }

      }

      public boolean isPendingModeExecutionAllowed() {
         return false;
      }

      public String toString() {
         return new String("StateDumpMessage for state manager:" + this.name + " MessageID:" + this.messageID);
      }
   }

   public static class HeartbeatMessage extends Message {
      private static final long serialVersionUID = -7435880529011603250L;
      private Integer version;

      public HeartbeatMessage(String cn, Integer version) {
         super(cn, version);
         this.version = version;
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Sending :" + this);
         }

      }

      public void execute(HostID id) {
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Executing :" + this);
         }

         if (ManagementService.getRuntimeAccess(ReplicatedSingletonServicesStateManager.kernelId).getServerRuntime().isShuttingDown()) {
            if (ReplicatedSingletonServicesStateManager.DEBUG) {
               ReplicatedSingletonServicesStateManager.p("Server is shutting down. Not executing " + this);
            }

         } else {
            Map m = ReplicatedSingletonServicesStateManager.getLocalMap(this.name);
            if (m == null) {
               if (ReplicatedSingletonServicesStateManager.DEBUG) {
                  ReplicatedSingletonServicesStateManager.p("Local State Manager not yet initialized. Not executing " + this);
               }

            } else {
               Integer curVersion = ReplicatedSingletonServicesStateManager.getVersion(this.name);
               if (curVersion == null) {
                  if (ReplicatedSingletonServicesStateManager.DEBUG) {
                     ReplicatedSingletonServicesStateManager.p("Local State Manager not yet initialized. Not executing " + this);
                  }

               } else {
                  if (!curVersion.equals(this.version)) {
                     if (ReplicatedSingletonServicesStateManager.DEBUG) {
                        ReplicatedSingletonServicesStateManager.p("Version mismatch for state manager:" + this.name + " Local Version#:" + curVersion + " Master Version#:" + this.version);
                     }

                     ReplicatedSingletonServicesStateManager.syncStateFromActiveStateManager(this.name);
                  }

                  List pendingMessages = ReplicatedSingletonServicesStateManager.getLocalPendingMessages(this.name);
                  if (pendingMessages != null && pendingMessages.size() > 0) {
                     if (ReplicatedSingletonServicesStateManager.DEBUG) {
                        ReplicatedSingletonServicesStateManager.p("Discarding pending messages that are still present even after starting to get heartbeat messages");
                     }

                     pendingMessages.clear();
                  }

               }
            }
         }
      }

      public boolean isPendingModeExecutionAllowed() {
         return false;
      }

      public String toString() {
         return new String("HeartbeatMessage for state manager:" + this.name + " MessageID:" + this.messageID + " Value:" + this.version);
      }
   }

   public static class UpdateMessage extends Message {
      private static final long serialVersionUID = 5942110178350613494L;
      private Serializable key;
      private SingletonServicesState newValue;
      private Integer version;
      private boolean removeState = false;

      public UpdateMessage(String cn, Integer version, Serializable key, SingletonServicesState newValue, boolean remove) {
         super(cn, version);
         this.key = key;
         this.version = version;
         this.newValue = newValue;
         this.removeState = remove;
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Sending :" + this);
         }

      }

      public boolean equals(Object obj) {
         if (this.isVersionValid()) {
            return super.equals(obj);
         } else {
            boolean result = false;
            if (obj instanceof UpdateMessage) {
               UpdateMessage other = (UpdateMessage)obj;
               if (other.key.equals(this.key) && other.newValue.equals(this.newValue) && other.removeState == this.removeState) {
                  result = true;
               }
            }

            return result;
         }
      }

      public void executeOnActiveStateManager(ReplicatedSingletonServicesStateManager localMgr) {
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Executing :" + this);
         }

         if (!this.removeState) {
            localMgr.storeServiceState((String)this.key, this.newValue);
         } else {
            localMgr.removeServiceState((String)this.key);
         }

      }

      public void execute(HostID id) {
         if (ReplicatedSingletonServicesStateManager.DEBUG) {
            ReplicatedSingletonServicesStateManager.p("Executing :" + this);
         }

         Map m = ReplicatedSingletonServicesStateManager.getLocalMap(this.name);
         if (m == null) {
            if (ReplicatedSingletonServicesStateManager.DEBUG) {
               ReplicatedSingletonServicesStateManager.p("Local State Manager not yet initialized. Not executing " + this);
            }

         } else {
            Integer curVersion = ReplicatedSingletonServicesStateManager.getVersion(this.name);
            int currentValue = curVersion == null ? 0 : curVersion;
            Integer expectedVersion = new Integer(currentValue + 1);
            if (!expectedVersion.equals(this.version)) {
               if (ReplicatedSingletonServicesStateManager.DEBUG) {
                  ReplicatedSingletonServicesStateManager.p("Missed Update Message for state manager:" + this.name + " Expected Version#:" + expectedVersion + " Master Version#:" + this.version);
               }

               ReplicatedSingletonServicesStateManager.syncStateFromActiveStateManager(this.name);
            } else {
               synchronized(m) {
                  if (this.removeState) {
                     m.remove(this.key);
                  } else {
                     m.put(this.key, this.newValue);
                  }
               }

               ReplicatedSingletonServicesStateManager.setVersion(this.name, this.version);
            }

         }
      }

      public boolean isPendingModeExecutionAllowed() {
         return true;
      }

      public String toString() {
         String printVersion = this.version.equals(ReplicatedSingletonServicesStateManager.VERSION_NONE) ? "None" : String.valueOf(this.version);
         return new String("UpdateMessage for state manager:" + this.name + " MessageID:" + printVersion + " Key:" + this.key + " New Value:" + this.newValue + " DeleteState:" + this.removeState);
      }

      private boolean isVersionValid() {
         return !this.version.equals(ReplicatedSingletonServicesStateManager.VERSION_NONE);
      }
   }

   protected abstract static class Message implements Serializable, Comparable, GroupMessage {
      protected final String name;
      protected final Integer messageID;

      protected Message(String cn, Integer messageId) {
         this.name = cn;
         this.messageID = messageId;
      }

      public boolean equals(Object obj) {
         if (!obj.getClass().getName().equals(this.getClass().getName())) {
            return false;
         } else {
            Message m = (Message)obj;
            return this.messageID == m.messageID;
         }
      }

      public int hashCode() {
         return this.messageID;
      }

      public int compareTo(Object other) {
         if (!other.getClass().getName().equals(this.getClass().getName())) {
            throw new ClassCastException();
         } else {
            Message m = (Message)other;
            return this.messageID.compareTo(m.messageID);
         }
      }

      public abstract boolean isPendingModeExecutionAllowed();

      public void executeOnActiveStateManager(ReplicatedSingletonServicesStateManager localMgr) {
      }

      public abstract void execute(HostID var1);
   }
}
