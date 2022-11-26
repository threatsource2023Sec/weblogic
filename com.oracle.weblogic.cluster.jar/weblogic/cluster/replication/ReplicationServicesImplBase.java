package weblogic.cluster.replication;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterHelper;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMembersPartitionChangeEvent;
import weblogic.cluster.ClusterMembersPartitionChangeListener;
import weblogic.cluster.ClusterService;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.replication.SecondarySelector.Locator;
import weblogic.cluster.replication.SecondarySelector.Locator.SelectorPolicy;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.ServerShuttingDownException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.server.channels.ChannelService;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.collections.ConcurrentWeakHashMap;
import weblogic.utils.collections.NumericValueHashtable;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public abstract class ReplicationServicesImplBase implements ReplicationServices, ClusterMembersPartitionChangeListener {
   static final String GLOBAL_PARTITION_NAME = "DOMAIN";
   static final String GLOBAL_RESOURCE_GROUP_NAME = "GLOBAL_RESOURCE_GROUP";
   static final String GLOBAL_PARTITION_ID = "0";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final HostID LOCAL_HOSTID = LocalServerIdentity.getIdentity();
   private static final int NOREPCHANNEL_INDEX = -1;
   private static String[] replicationChannels;
   private static HashMap appNameToResourceGroupMap = new HashMap();
   private static Map querySessionMap = new ConcurrentHashMap();
   private final Map cache = new ConcurrentWeakHashMap(11);
   private final Map cache2 = new ConcurrentWeakHashMap(11);
   private final RemoteReplicationServicesInternalImpl replicationServiceRemoteAdapter = new RemoteReplicationServicesInternalImpl(this);
   private final AuditableThreadLocal serializingReplicatable = AuditableThreadLocalFactory.createThreadLocal();
   protected HashMap secondarySelectorHashMap = new HashMap();
   private HashMap partitionToSecondarySelectorMap = new HashMap();
   @Inject
   private ClusterServicesInvocationContext clusterServicesInvocationContext;
   @Inject
   ReplicationServiceLocator svcLocator;
   @Inject
   private WroManager wroMan;
   private boolean replicatedOnShutdown = false;
   private Set replicatedOnShutdownSet = new HashSet();
   private MulticastSession multicastSessionSender;

   public ReplicationServicesImplBase() {
      this.initReplicationChannelNames();
      this.initializeSecondarySelectorsForPartitions();
   }

   public void startService() throws ServiceFailureException {
      this.exportRemoteAdapter();
   }

   public void stopService() {
      this.unExportRemoteAdapter();
      this.unregisterClusterChangeEventListener();
   }

   public void clusterMembersPartitionChanged(ClusterMembersPartitionChangeEvent cmpce) {
      HostID host = cmpce.getClusterMemberInfo().identity();
      String partitionId = cmpce.getPartitionId();
      SecondarySelector selector;
      switch (cmpce.getAction()) {
         case 0:
            selector = this.getLocalSecondarySelectorByPartition(partitionId);
            if (selector != null) {
               selector.addNewServer(cmpce.getClusterMemberInfo());
            }
            break;
         case 1:
            selector = this.getLocalSecondarySelectorByPartition(partitionId);
            if (selector != null) {
               HostID id = cmpce.getClusterMemberInfo().identity();
               selector.removeDeadSecondarySrvr(id);
               this.changeSecondary(id);
            }
            break;
         case 2:
            selector = this.getLocalSecondarySelectorByPartition(partitionId);
            if (selector != null) {
               selector.addNewServer(cmpce.getClusterMemberInfo());
            }
      }

   }

   public synchronized void replicateOnShutdown() {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("ReplicationManager.replicateOnShutdown");
      }

      String partitionName = getPartitionName();
      if (partitionName != null && !partitionName.equals("DOMAIN")) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ReplicationManager.replicateOnShutdown checking replicatedOnShutdownSet for " + partitionName);
         }

         if (this.replicatedOnShutdownSet.contains(partitionName)) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("ReplicationManager.replicateOnShutdown replicatedOnShutdownSet contains " + partitionName + " indicating it has already replicated OnShutdown");
            }

            return;
         }

         ReplicationMap repMap = (ReplicationMap)this.getWroManager().resourceGroupMap.get(getResourceGroupKey());
         if (repMap == null) {
            return;
         }

         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ReplicationManager.replicateOnShutdown  replicatedOnShutdownSet adding " + partitionName);
         }

         this.replicatedOnShutdownSet.add(partitionName);
         this.replicateOnShutdown(repMap);
         if (repMap.primaries.isEmpty() && repMap.secondaries.isEmpty()) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("ReplicationManager.replicateOnShutdown primaries and secondaries are empty - will remove " + partitionName);
            }

            this.replicatedOnShutdownSet.remove(partitionName);
         }
      } else {
         if (this.replicatedOnShutdown) {
            return;
         }

         this.replicatedOnShutdown = true;
         Iterator var2 = this.getWroManager().resourceGroupMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry groupMap = (Map.Entry)var2.next();
            ReplicationMap repMap = (ReplicationMap)groupMap.getValue();
            this.replicateOnShutdown(repMap);
         }
      }

   }

   private void replicateOnShutdown(ReplicationMap repMap) {
      if (repMap != null) {
         Iterator var2 = repMap.primaries.values().iterator();

         WrappedRO wrappedRO;
         while(var2.hasNext()) {
            wrappedRO = (WrappedRO)var2.next();

            try {
               if (wrappedRO.getMap().keySet().contains(Replicatable.DEFAULT_KEY)) {
                  if (ReplicationDebugLogger.isDebugEnabled()) {
                     ReplicationDebugLogger.debug(wrappedRO.getID(), "replicateOnShutdown(): skip DEFAULT_KEY primary wro");
                  }
               } else {
                  this.replicateOnShutdownForPrimaryIfNoSecondary(wrappedRO);
               }
            } catch (Throwable var6) {
               ClusterExtensionLogger.logEnsureReplicatedException(wrappedRO.getID().toString(), StackTraceUtilsClient.throwable2StackTrace(var6));
            }
         }

         var2 = repMap.secondaries.values().iterator();

         while(var2.hasNext()) {
            wrappedRO = (WrappedRO)var2.next();

            try {
               if (wrappedRO.getMap().keySet().contains(Replicatable.DEFAULT_KEY)) {
                  if (ReplicationDebugLogger.isDebugEnabled()) {
                     ReplicationDebugLogger.debug(wrappedRO.getID(), "replicateOnShutdown(): skip DEFAULT_KEY secondary wro");
                  }
               } else {
                  this.replicateOnShutdownForSecondaryIfNoPrimary(wrappedRO);
               }
            } catch (Throwable var5) {
               ClusterExtensionLogger.logEnsureReplicatedException(wrappedRO.getID().toString(), StackTraceUtilsClient.throwable2StackTrace(var5));
            }
         }

      }
   }

   protected void replicateOnShutdownForPrimaryIfNoSecondary(WrappedRO wrappedRO) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("do we need to replicate this primary session? " + wrappedRO.getID() + " with otherHost: " + wrappedRO.getOtherHost() + " with secondary candidates: " + this.getSecondarySelector(wrappedRO.getResourceGroupKey()).getSecondaryCandidates());
      }

      if (wrappedRO.getOtherHost() == null || !this.getSecondarySelector(wrappedRO.getResourceGroupKey()).getSecondaryCandidates().contains(wrappedRO.getOtherHost())) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ReplicationManager.flushing primary without secondary " + wrappedRO.getID() + " with version: " + wrappedRO.getVersion());
         }

         wrappedRO.setOtherHost((HostID)null);
         wrappedRO.setOtherHostInfo((Object)null);
         this.createSecondaryWithFullState(wrappedRO);
      }

   }

   private void replicateOnShutdownForSecondaryIfNoPrimary(WrappedRO wrappedRO) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("do we need to replicate this session? " + wrappedRO.getID() + " with otherHost: " + wrappedRO.getOtherHost() + " with secondary candidates: " + this.getSecondarySelector(wrappedRO.getResourceGroupKey()).getSecondaryCandidates());
      }

      if (wrappedRO.getOtherHost() != null && this.getSecondarySelector(wrappedRO.getResourceGroupKey()).getSecondaryCandidates().contains(wrappedRO.getOtherHost())) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ReplicationManager not sending session because primary is still available " + wrappedRO.getID());
         }
      } else {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ReplicationManager.flushing backup copy: " + wrappedRO.getID() + " with version: " + wrappedRO.getVersion());
         }

         this.getWroManager().ensureStatus((byte)0, wrappedRO);
         this.createSecondaryWithFullState(wrappedRO);
      }

   }

   void createSecondaryWithFullState(WrappedRO wrappedRO) {
      this.createSecondary(wrappedRO, (Object)null);
      Iterator iter = wrappedRO.getMap().keySet().iterator();

      while(iter.hasNext()) {
         Object key = iter.next();
         Replicatable ro = wrappedRO.getRO(key);
         if (ro instanceof ReplicatableExt) {
            ((ReplicatableExt)ro).secondaryCreatedForFullState(wrappedRO.getID(), wrappedRO.getResourceGroupKey());
         }
      }

   }

   private void clearRecursive() {
      this.serializingReplicatable.set((Object)null);
   }

   private boolean checkAndSetRecursive() {
      if (Boolean.TRUE.equals(this.serializingReplicatable.get())) {
         return true;
      } else {
         this.serializingReplicatable.set(Boolean.TRUE);
         return false;
      }
   }

   protected void createSecondary(WrappedRO wro, Object key) {
      this.createSecondary(wro, key, (SecondaryFilter)null);
   }

   private void createSecondary(WrappedRO wro, Object key, SecondaryFilter filter) {
      HostID secondary = wro.getOtherHost();
      if (secondary == null) {
         SecondarySelector selector = this.getSecondarySelector(wro.getResourceGroupKey());
         if (selector != null) {
            secondary = selector.getSecondarySrvr();
            if (filter != null) {
               secondary = filter.select(secondary, selector.getSecondaryCandidates());
            }
         }
      }

      Object keyToUse = key;

      try {
         while(secondary != null) {
            if (this.trySecondary(wro, secondary, keyToUse)) {
               return;
            }

            keyToUse = null;
            SecondarySelector selector = this.getSecondarySelector(wro.getResourceGroupKey());
            secondary = selector.getSecondarySrvr();
            if (filter != null) {
               secondary = filter.select(secondary, selector.getSecondaryCandidates());
            }
         }
      } catch (ApplicationUnavailableException var7) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(wro.getID(), "Failed to create secondary as secondary " + secondary + " doesn't have the application ready; Will try other hosts", var7);
         }

         if (this.trySecondaryOnOtherServers(wro)) {
            return;
         }
      }

      wro.setOtherHost((HostID)null);
      wro.setOtherHostInfo((Object)null);
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "Unable to create secondary on " + secondary);
      }

   }

   boolean trySecondary(WrappedRO wro, HostID secondaryHost, Object key) {
      if (this.checkAndSetRecursive()) {
         ClusterExtensionLogger.logReentrantThread("'" + Thread.currentThread().getName() + "'");
         return true;
      } else {
         Replicatable replicatable = null;

         boolean var6;
         try {
            ReplicationServicesInternal secondaryRepMan = this.getReplicationServicesInternal(wro, secondaryHost, key);
            if (key == null) {
               Iterator iter = wro.getMap().keySet().iterator();

               while(iter.hasNext()) {
                  Object iterKey = iter.next();
                  replicatable = wro.getRO(iterKey);
                  this.replicateWroToServer(wro, iterKey, replicatable, secondaryRepMan);
                  if (replicatable == null && ReplicationDetailsDebugLogger.isDebugEnabled()) {
                     ReplicationDetailsDebugLogger.debug(wro.getID(), "ReplicationManager.trySecondary replicatable is null for iterKey: " + iterKey + ", id: " + wro.getID() + ", wro.Map: " + wro.getMap());
                  }
               }
            } else {
               replicatable = wro.getRO(key);
               this.replicateWroToServer(wro, key, replicatable, secondaryRepMan);
               if (replicatable == null) {
                  if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                     ReplicationDetailsDebugLogger.debug(wro.getID(), "ReplicationManager.trySecondary replicatable is null for key: " + key + ", id: " + wro.getID() + ", wro.Map: " + wro.getMap());
                  }

                  var6 = true;
                  return var6;
               }
            }

            wro.setOtherHost(secondaryHost);
            resetTimeOut(secondaryHost);
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(wro.getID(), "Created secondary on " + secondaryHost);
            }

            var6 = true;
            return var6;
         } catch (ConnectException var20) {
            var6 = this.handleConnectExceptionDuringCreate(wro, secondaryHost, var20);
            return var6;
         } catch (ServerShuttingDownException var21) {
            var6 = this.handleServerShuttingDownDuringCreate(wro, secondaryHost, var21);
            return var6;
         } catch (ConnectIOException var22) {
            var6 = this.handleConnectIOExceptionDuringCreate(wro, secondaryHost, var22);
            return var6;
         } catch (MarshalException var23) {
            var6 = this.handleMarshalExceptionDuringCreate(replicatable, var23);
         } catch (PeerGoneException var24) {
            var6 = this.handlePeerGoneExcceptionDuringCreate(wro, secondaryHost, replicatable, var24);
            return var6;
         } catch (UnmarshalException var25) {
            var6 = this.handleUnmarshalExceptionDuringCreate(replicatable, var25);
            return var6;
         } catch (RemoteRuntimeException var26) {
            var6 = this.handleRemoteRuntimeExceptionDuringCreate(var26);
            return var6;
         } catch (RequestTimeoutException var27) {
            var6 = this.handleReqestTimeoutExceptionDuringCreate(wro, secondaryHost, var27);
            return var6;
         } catch (ApplicationUnavailableRemoteException var28) {
            throw var28.getApplicationUnavailableException();
         } catch (RemoteException var29) {
            var6 = this.handleRemoteExceptionDuringCreate(wro, secondaryHost, var29);
            return var6;
         } finally {
            this.clearRecursive();
         }

         return var6;
      }
   }

   private void replicateWroToServer(WrappedRO wro, Object key, Replicatable replicatable, ReplicationServicesInternal secondaryRepMan) throws RemoteException {
      if (replicatable != null) {
         wro.setOtherHostInfo(secondaryRepMan.create(LOCAL_HOSTID, wro.getVersion(key), wro.getID(), new WrappedSerializable(replicatable, wro.getResourceGroupKey())));
      }

   }

   private boolean handleRemoteExceptionDuringCreate(WrappedRO wro, HostID secondaryHost, RemoteException e) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "Error creating secondary on " + secondaryHost, e);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "RemoteException while creating secondary on " + secondaryHost);
      }

      return false;
   }

   private boolean handleReqestTimeoutExceptionDuringCreate(WrappedRO wro, HostID secondaryHost, RequestTimeoutException rte) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "Error creating secondary on " + secondaryHost, rte);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "RequestTimeoutException while creating secondary on " + secondaryHost);
      }

      return false;
   }

   private boolean handleRemoteRuntimeExceptionDuringCreate(RemoteRuntimeException rre) {
      ClusterExtensionLogger.logUnexpectedExceptionDuringReplication(rre.getCause());
      return true;
   }

   private boolean handleUnmarshalExceptionDuringCreate(Replicatable replicatable, UnmarshalException ume) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug((String)"Unmarshalling error", (Throwable)ume);
      }

      if (replicatable != null) {
         ClusterExtensionLogger.logUnableToUpdateNonSerializableObject(replicatable.getKey(), ume);
      } else {
         ClusterLogger.logUnableToUpdateNonSerializableObject(ume);
      }

      return true;
   }

   private boolean handlePeerGoneExcceptionDuringCreate(WrappedRO wro, HostID secondaryHost, Replicatable replicatable, PeerGoneException pge) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "Error creating secondary on " + secondaryHost, pge);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "PeerGoneException while creating secondary on " + secondaryHost);
      }

      if (ReplicationSizeDebugLogger.isDebugEnabled() && replicatable != null) {
         ReplicationSizeDebugLogger.debug(wro.getID(), "Error creating secondary on " + secondaryHost + ", Replicated Data is " + replicatable.toString(), pge);
      }

      return false;
   }

   private boolean handleMarshalExceptionDuringCreate(Replicatable replicatable, MarshalException me) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug((String)"Marshalling error", (Throwable)me);
      }

      if (replicatable != null) {
         ClusterExtensionLogger.logUnableToUpdateNonSerializableObject(replicatable.getKey(), me);
      } else {
         ClusterLogger.logUnableToUpdateNonSerializableObject(me);
      }

      return true;
   }

   private boolean handleConnectIOExceptionDuringCreate(WrappedRO wro, HostID secondaryHost, ConnectIOException cioe) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "ConnectIOException while trying to connect to secondary server " + secondaryHost + " for creating secondary", cioe);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "ConnectIOException while trying to connect to secondary server " + secondaryHost + " for creating secondary");
      }

      this.cleanupDeadServer(secondaryHost);
      return false;
   }

   private boolean handleServerShuttingDownDuringCreate(WrappedRO wro, HostID secondaryHost, ServerShuttingDownException ssde) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "Secondary server " + secondaryHost + " is shutting down. Failed to create secondary.", ssde);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "Secondary server " + secondaryHost + " is shutting down. Failed to create secondary");
      }

      this.cleanupDeadServer(secondaryHost);
      return false;
   }

   private boolean handleConnectExceptionDuringCreate(WrappedRO wro, HostID secondaryHost, ConnectException ce) {
      if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
         ReplicationDetailsDebugLogger.debug(wro.getID(), "Failed to reach secondary server " + secondaryHost + " trying to create secondary", ce);
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(wro.getID(), "Failed to reach secondary server " + secondaryHost + " trying to create secondary");
      }

      this.cleanupDeadServer(secondaryHost);
      return false;
   }

   private ReplicationServicesInternal getReplicationServicesInternal(WrappedRO wro, HostID secondaryHost, Object key) throws RemoteException {
      try {
         ReplicationServicesInternal secondaryRepMan;
         if (replicationChannels != null) {
            int index = wro.channelIndex;

            assert index != -1;

            secondaryRepMan = this.getRepMan(secondaryHost, replicationChannels[index]);
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug(wro.getID(), "Using Multiple-Channels for 2-way create() on channel" + replicationChannels[index] + ", key: " + key + ", version: " + (key == null ? wro.getVersion() : wro.getVersion(key)));
            }
         } else {
            secondaryRepMan = this.getRepMan(secondaryHost);
         }

         return secondaryRepMan;
      } catch (RemoteException var6) {
         this.cleanupDeadServer(secondaryHost);
         throw var6;
      }
   }

   private boolean trySecondaryOnOtherServers(WrappedRO wro) {
      Iterator iterator = this.getSecondarySelector(wro.getResourceGroupKey()).getSecondaryCandidates().iterator();

      while(iterator.hasNext()) {
         HostID secondary = (HostID)iterator.next();

         try {
            if (this.trySecondary(wro, secondary, (Object)null)) {
               return true;
            }
         } catch (ApplicationUnavailableException var5) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(wro.getID(), "Failed to create secondary as secondary " + secondary + " doesn't have the application ready ", var5);
            }
         }
      }

      return false;
   }

   public final void unregister(ROID id, Object key) {
      this.unregister(new ROID[]{id}, key);
   }

   public void unregister(ROID[] ids, Object key) {
      if (replicationChannels != null) {
         this.unregisterWithMultipleChannels(ids, key);
      } else {
         this.unregisterWithoutMultipleChannels(ids, key);
      }

   }

   private void unregisterWithMultipleChannels(ROID[] ids, Object key) {
      this.unregisterWithMultipleChannels(ids, key, (ResourceGroupKey)null);
   }

   protected void unregisterWithMultipleChannels(ROID[] ids, Object key, ResourceGroupKey rgkey) {
      HashMap map = new HashMap();
      ResourceGroupKey resourceGroupKey = rgkey == null ? getResourceGroupKey() : rgkey;
      ROID[] var6 = ids;
      int var7 = ids.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ROID id = var6[var8];
         WroManager wroManager = this.getWroManager();
         WrappedRO wro = wroManager.remove(id, key, resourceGroupKey);
         if (wroManager.getPrimaryResourceGroupMap(resourceGroupKey).isEmpty()) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("ReplicationManager.unregisterWithMultipleChannels removing  " + getPartitionName());
            }

            this.getReplicatedOnShutdownSet().remove(getPartitionName());
         }

         if (wro != null && !wro.isMigrated()) {
            HostID otherHost = wro.getOtherHost();
            if (otherHost != null) {
               CompositeKey hostAndChannelKey = new CompositeKey(otherHost, replicationChannels[wro.channelIndex]);
               ArrayList list = (ArrayList)map.get(hostAndChannelKey);
               if (list == null) {
                  list = new ArrayList();
                  map.put(hostAndChannelKey, list);
               }

               list.add(id);
            }
         }
      }

      if (!ClusterHelper.isPartitionShuttingDown(getPartitionName())) {
         this.removeSecondaryWithMultipleChannels(key, map);
      }
   }

   protected void removeSecondaryWithMultipleChannels(Object key, HashMap map) {
      Iterator var4 = map.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry e = (Map.Entry)var4.next();
         ArrayList list = (ArrayList)e.getValue();
         ROID[] idList = new ROID[list.size()];

         try {
            ReplicationServicesInternal secondaryRepMan = this.getRepMan(((CompositeKey)e.getKey()).hostID, ((CompositeKey)e.getKey()).channelName);
            if (ClusterService.getClusterServiceInternal().useOneWayRMI()) {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug("Using Multi-Channels for 1-way removeOneWay() on channel " + ((CompositeKey)e.getKey()).channelName);
               }

               secondaryRepMan.removeOneWay((ROID[])list.toArray(idList), key);
            } else {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug("Using Multiple-Channels for 2-way remove() on channel " + ((CompositeKey)e.getKey()).channelName);
               }

               secondaryRepMan.remove((ROID[])list.toArray(idList), key);
            }

            resetTimeOut(((CompositeKey)e.getKey()).hostID);
         } catch (RemoteException var8) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Unable to reach " + ((CompositeKey)e.getKey()).hostID + " to remove roids: " + Arrays.asList((Object[])idList));
            }
         }
      }

   }

   private void unregisterWithoutMultipleChannels(ROID[] ids, Object key) {
      this.unregisterWithoutMultipleChannels(ids, key, (ResourceGroupKey)null);
   }

   protected void unregisterWithoutMultipleChannels(ROID[] ids, Object key, ResourceGroupKey rgkey) {
      HashMap map = new HashMap();
      ResourceGroupKey resourceGroupKey = rgkey == null ? getResourceGroupKey() : rgkey;
      WroManager wroManager = this.getWroManager();
      ROID[] var7 = ids;
      int var8 = ids.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         ROID id = var7[var9];
         WrappedRO wro = wroManager.remove(id, key, resourceGroupKey);
         if (wroManager.getPrimaryResourceGroupMap(resourceGroupKey).isEmpty()) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("ReplicationManager.unregisterWithoutMultipleChannels removing  " + getPartitionName());
            }

            this.getReplicatedOnShutdownSet().remove(getPartitionName());
         }

         if (wro != null && !wro.isMigrated()) {
            HostID otherHost = wro.getOtherHost();
            if (otherHost != null) {
               ArrayList list = (ArrayList)map.get(otherHost);
               if (list == null) {
                  list = new ArrayList();
                  map.put(otherHost, list);
               }

               list.add(id);
            }
         }
      }

      if (!ClusterHelper.isPartitionShuttingDown(getPartitionName())) {
         this.removeSecondaryWithoutMultipleChannels(key, map);
      }
   }

   protected void removeSecondaryWithoutMultipleChannels(Object key, HashMap map) {
      Iterator var4 = map.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry e = (Map.Entry)var4.next();
         ArrayList list = (ArrayList)e.getValue();
         ROID[] idList = new ROID[list.size()];

         try {
            ReplicationServicesInternal secondaryRepMan = this.getRepMan((HostID)e.getKey());
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug("Using Single-Channel for 2-way remove()");
            }

            secondaryRepMan.remove((ROID[])list.toArray(idList), key);
            resetTimeOut((HostID)e.getKey());
         } catch (RemoteException var8) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Unable to reach " + e.getKey() + " to remove roids: " + Arrays.asList((Object[])idList));
            }
         }
      }

   }

   public void ensureFullStateReplicated(List ids) {
      if (ids != null) {
         ResourceGroupKey resourceGroupKey = getResourceGroupKey();
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("ensureFullStateReplicated() for " + ids.size() + " roids on resourceGroupKey=" + resourceGroupKey);
         }

         Iterator var3 = ids.iterator();

         while(var3.hasNext()) {
            ROID id = (ROID)var3.next();

            try {
               if (id == null) {
                  if (ReplicationDebugLogger.isDebugEnabled()) {
                     ReplicationDebugLogger.debug("ensureFullStateReplicated() null roid, resourceGroupKey=" + resourceGroupKey);
                  }
               } else {
                  this.ensureFullStateReplicated(id, resourceGroupKey);
               }
            } catch (Throwable var6) {
               ClusterExtensionLogger.logEnsureReplicatedException(id.toString(), StackTraceUtilsClient.throwable2StackTrace(var6));
            }
         }

      }
   }

   private void ensureFullStateReplicated(ROID id, ResourceGroupKey resourceGroupKey) throws NotFoundException {
      WrappedRO wro = this.getWroManager().find(id, resourceGroupKey);
      if (wro == null) {
         String msg = "ensureFullStateReplicated(): Unable to find object for roid:" + id + ", wro=" + wro;
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, msg);
         }

         throw new NotFoundException(msg);
      } else {
         if (wro.getStatus() == 0) {
            synchronized(this) {
               this.replicateOnShutdownForPrimaryIfNoSecondary(wro);
            }
         } else {
            synchronized(this) {
               this.replicateOnShutdownForSecondaryIfNoPrimary(wro);
            }
         }

      }
   }

   public void localCleanupOnPartitionShutdown(List ids, Object key) {
      if (ids != null) {
         ResourceGroupKey resourceGroupKey = getResourceGroupKey();
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("localCleanupOnPartitionShutdown() for " + ids.size() + " roids, key=" + key + " on resourceGroupKey=" + resourceGroupKey);
         }

         Iterator var4 = ids.iterator();

         while(var4.hasNext()) {
            ROID id = (ROID)var4.next();

            try {
               this.localCleanupOnPartitionShutdown(id, key, resourceGroupKey);
            } catch (Throwable var7) {
               ClusterExtensionLogger.logLocalCleanupException(id.toString(), key == null ? "" : key.toString(), StackTraceUtilsClient.throwable2StackTrace(var7));
            }
         }

      }
   }

   private void localCleanupOnPartitionShutdown(ROID id, Object key, ResourceGroupKey resourceGroupKey) throws NotFoundException {
      WroManager wroManager = this.getWroManager();
      WrappedRO wro = wroManager.findPrimary(id, resourceGroupKey);
      String msg;
      if (wro != null) {
         if (key != null && wro.getRO(key) == null) {
            msg = "localCleanupOnPartitionShutdown(): Unable to find object for roid:" + id + "[wro=" + wro + ", key=" + key + "]primary";
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, msg);
            }

            throw new NotFoundException(msg);
         } else {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               msg = "localCleanupOnPartitionShutdown(): on ResourceGroupKey " + resourceGroupKey + ", primary wro=" + wro;
               ReplicationDebugLogger.debug(id, msg);
            }

            this.unregisterLocally(wro.getID(), key, resourceGroupKey);
         }
      } else {
         wro = wroManager.findSecondary(id, resourceGroupKey);
         if (wro != null && (key == null || wro.getRO(key) != null)) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               msg = "localCleanupOnPartitionShutdown():  on ResourceGroupKey " + resourceGroupKey + ", secondary wro=" + wro;
               ReplicationDebugLogger.debug(id, msg);
            }

            wroManager.remove(wro.getID(), key, resourceGroupKey);
         } else {
            msg = "localCleanupOnPartitionShutdown(): Unable to find object for roid:" + id + "[wro=" + wro + ", key=" + key + "]secondary";
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, msg);
            }

            throw new NotFoundException(msg);
         }
      }
   }

   private void unregisterLocally(ROID id, Object key, ResourceGroupKey resourceGroupKey) {
      if (replicationChannels != null) {
         this.unregisterWithMultipleChannels(new ROID[]{id}, key, resourceGroupKey);
      } else {
         this.unregisterWithoutMultipleChannels(new ROID[]{id}, key, resourceGroupKey);
      }

   }

   public final ROInfo register(Replicatable ro) {
      return this.add(ROID.create(), ro);
   }

   public ROInfo add(ROID id, Replicatable ro) {
      WrappedRO wro = this.getWroManager().create(ro, id, (byte)0, 0, getResourceGroupKey());
      this.createSecondary(wro, ro.getKey());
      ROInfo info = wro.getROInfo();
      info.setSecondaryROVersion(0);
      return info;
   }

   public final RemoteReference lookupReplicaRemoteRef(ROID id, Object key, int version, boolean primary) throws NotFoundException, RemoteException {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(id, "lookupReplicaRemoteRef(roid=" + id + ", key=" + key + ", primary=" + primary + ")");
      }

      WrappedRO wro = this.lookupReplica(id, key, version);
      if (primary && wro.getStatus() != 0) {
         String msg = "Found replica for id " + id + " is not primary";
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, "lookupReplicaRemoteRef: " + msg);
         }

         throw new NotFoundException(msg);
      } else {
         Replicatable ro = wro.getRO(key);
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, "lookupReplicaRemoteRef found wro " + wro + ", isMigrated=" + wro.isMigrated() + ", status=" + wro.getStatus() + ", ro=" + ro);
         }

         return primary ? ((ReplicatableExt)ro).getPrimaryRemoteRef(id) : ((ReplicatableExt)ro).getSecondaryRemoteRef(id, false);
      }
   }

   private WrappedRO lookupReplica(ROID id, Object key, int version) throws NotFoundException {
      WrappedRO wro = this.getWroManager().find(id);
      if (wro == null || key != null && wro.getRO(key) == null) {
         String msg = "Unable to find object for roid:" + id + "[wro=" + wro + ", key=" + key + "]";
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, msg);
         }

         throw new NotFoundException(msg);
      } else {
         int wroversion = wro.getVersion(key);
         if (wroversion != version) {
            String msg = "Found object for roid:" + id + "[wro=" + wro + ", key=" + key + "]version=" + wroversion + ", not expected version " + version;
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, msg);
            }

            throw new NotFoundException(msg);
         } else {
            return wro;
         }
      }
   }

   static void validateResourceGroup(String partitionName, String resourceGroupName) throws NameNotFoundException {
      if (resourceGroupName != null && !"GLOBAL_RESOURCE_GROUP".equals(resourceGroupName)) {
         if (partitionName != null && !"DOMAIN".equals(partitionName)) {
            DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
            PartitionMBean partitionMBean = domain.lookupPartition(partitionName);
            if (partitionMBean == null) {
               throw new NameNotFoundException(partitionName + " does not exist.");
            } else {
               ResourceGroupMBean resourceGroupMBean = partitionMBean.lookupResourceGroup(resourceGroupName);
               if (resourceGroupMBean == null) {
                  throw new NameNotFoundException(resourceGroupName + " does not exist in partition: " + partitionName);
               }
            }
         }
      }
   }

   static int getChannelIndex(ROID id) {
      return replicationChannels != null ? (id.getValueAsInt() & Integer.MAX_VALUE) % replicationChannels.length : -1;
   }

   public final Replicatable lookup(ROID id, Object key) throws NotFoundException {
      WrappedRO wro = this.getPrimary(id, true, key, getResourceGroupKey());
      return wro.getRO(key);
   }

   public final Replicatable invalidationLookup(ROID id, Object key) throws NotFoundException {
      WrappedRO wro = this.getWroManager().find(id, getResourceGroupKey());
      if (wro != null) {
         return wro.getRO(key);
      } else {
         throw new NotFoundException("Failed to located " + id);
      }
   }

   public final Replicatable registerLocally(final HostID remoteServer, final ROID id, final Object key) throws RemoteException {
      if (id == null) {
         return null;
      } else {
         final ReplicationServicesInternal repMan;
         if (replicationChannels != null) {
            int index = getChannelIndex(id);

            assert index != -1;

            repMan = this.getRepMan(remoteServer, replicationChannels[index]);
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, "Fetching replicatable object from remote server " + remoteServer + " on channel: " + replicationChannels[index]);
            }
         } else {
            repMan = this.getRepMan(remoteServer);
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug(id, "Fetching replicatable object from remote server " + remoteServer);
            }
         }

         ResourceGroupKey resourceGroupKey = getResourceGroupKey();

         final ROObject roObject;
         try {
            roObject = repMan.fetch(id);
         } catch (NotFoundException var14) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("NotFoundException caught when fetching " + id + " from " + remoteServer), (Throwable)var14);
            }

            return null;
         } catch (ConnectException var15) {
            if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
               ReplicationDetailsDebugLogger.debug((String)("ConnectException caught when fetching " + id + " from " + remoteServer), (Throwable)var15);
            }

            return null;
         }

         resetTimeOut(remoteServer);
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Successfully fetched roObject for " + id + " from " + remoteServer + ": " + roObject + " with key: " + key);
         }

         Map roMap = roObject.getROS();
         if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
            ReplicationDetailsDebugLogger.debug("Successfully fetched roObject for " + id + " from " + remoteServer + ": " + roObject + " with roMap: " + roMap + " and ro: " + roMap.get(key));
         }

         if (roMap.get(key) == null) {
            return null;
         } else {
            WrappedRO wro = this.getWroManager().create((Replicatable)roMap.get(key), id, (byte)0, roObject.getVersion(key), roObject.getResourceGroupKey());
            Iterator var9 = roMap.entrySet().iterator();

            while(var9.hasNext()) {
               Object o = var9.next();
               Map.Entry e = (Map.Entry)o;
               Object iterKey = e.getKey();
               if (!iterKey.equals(key)) {
                  Replicatable value = (Replicatable)e.getValue();
                  wro.addRO(value, roObject.getVersion(iterKey));
               }
            }

            this.createSecondary(wro, (Object)null);
            boolean cleanupOrphanedSession = false;
            if (resourceGroupKey.getPartitionName() != null && !resourceGroupKey.getPartitionName().equals("DOMAIN")) {
               cleanupOrphanedSession = ClusterService.getClusterServiceInternal().isCleanupOrphanedSessionEnabled(resourceGroupKey.getPartitionName());
            } else {
               cleanupOrphanedSession = ClusterService.getClusterServiceInternal().isCleanupOrphanedSessionEnabled();
            }

            if (cleanupOrphanedSession) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug("cleanup orphanedSession on " + remoteServer + " and we replicated it to " + wro.getOtherHost());
               }

               if (wro.getOtherHost() == null || !wro.getOtherHost().equals(remoteServer)) {
                  WorkManager sysWorkManager = WorkManagerFactory.getInstance().getSystem();
                  sysWorkManager.schedule(new Runnable() {
                     public void run() {
                        try {
                           repMan.removeOrphanedSessionOnCondition(id, roObject.getVersion(key), key);
                        } catch (RemoteException var2) {
                           if (ReplicationDebugLogger.isDebugEnabled()) {
                              ReplicationDebugLogger.debug("Unable to reach " + remoteServer + " to remove orphanedSecondary  " + id);
                           }
                        }

                     }
                  });
               }
            }

            resetTimeOut(remoteServer);
            return wro.getRO(key);
         }
      }
   }

   public final void removeOrphanedSecondary(ROID id, Object key) {
      WrappedRO wro = this.getWroManager().remove(id, key, getResourceGroupKey());
      if (wro != null) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, "Removed orphaned secondary");
         }
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(id, "Attempt to remove non-existent object");
      }

   }

   public final Object getSecondaryInfo(ROID id) throws NotFoundException {
      WrappedRO wro = this.getPrimary(id, false, (Object)null, getResourceGroupKey());
      return wro.getSecondaryROInfo();
   }

   public boolean hasSecondaryServer(ROID id) throws NotFoundException {
      boolean hasSecondary = false;
      WrappedRO wro = this.getWroManager().find(id, getResourceGroupKey());
      if (wro == null) {
         throw new NotFoundException("Unable to find " + id + " with resourceGroupKey: " + getResourceGroupKey());
      } else {
         if (wro.getStatus() == 0) {
            if (wro.getSecondaryROInfo() != null) {
               hasSecondary = true;
            }
         } else {
            hasSecondary = true;
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationMap repMap = (ReplicationMap)this.getWroManager().resourceGroupMap.get(getResourceGroupKey());
               ReplicationDebugLogger.debug("wro " + wro.getID() + " has no secondary.  OtherHost: " + wro.getOtherHost() + " and is secondary copy? " + repMap.secondaries.containsKey(id) + " or " + repMap.primaries.containsKey(id));
               if (this.getSecondarySelector(wro.getResourceGroupKey()).getSecondaryCandidates().contains(wro.getOtherHost())) {
                  ReplicationDebugLogger.debug("ReplicationManager not sending session because primary is still available " + wro.getID());
               }
            }
         }

         return hasSecondary;
      }
   }

   public void removeSecondary(WrappedRO wro) {
      HostID otherHost = wro.getOtherHost();
      wro.setOtherHost((HostID)null);
      wro.setOtherHostInfo((Object)null);
      HashMap map;
      if (replicationChannels != null) {
         map = new HashMap();
         CompositeKey hostAndChannelKey = new CompositeKey(otherHost, replicationChannels[wro.channelIndex]);
         ArrayList lst = new ArrayList();
         lst.add(wro.getID());
         map.put(hostAndChannelKey, lst);
         this.removeSecondaryWithMultipleChannels((Object)null, map);
      } else {
         map = new HashMap();
         ArrayList lst = new ArrayList();
         lst.add(wro.getID());
         map.put(otherHost, lst);
         this.removeSecondaryWithoutMultipleChannels((Object)null, map);
      }

   }

   public Object copyUpdateSecondaryForZDT(ROID id, Serializable change, Object key) throws NotFoundException {
      ResourceGroupKey resourceGroupKey = getResourceGroupKey();
      WrappedRO wro = this.getWroManager().find(id, resourceGroupKey);
      if (wro == null || key != null && wro.getRO(key) == null) {
         String msg = "updateNewSecondaryForZDT: Unable to find object for roid:" + id + ", ResourceGroupKey=" + resourceGroupKey + "[wro=" + wro + ", key=" + key + "]";
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, msg);
         }

         throw new NotFoundException(msg);
      } else {
         this.getWroManager().ensureStatus((byte)0, wro);
         wro.setOtherHost((HostID)null);
         wro.setOtherHostInfo((Object)null);
         this.createSecondary(wro, (Object)null, new ZDTSecondaryFilter());
         return this.copyUpdateSecondary(id, change, key, resourceGroupKey);
      }
   }

   public Object updateSecondary(ROID id, Serializable change, Object key) throws NotFoundException {
      return this.updateSecondary(id, change, key, (ResourceGroupKey)null);
   }

   public Object updateSecondary(ROID id, Serializable change, Object key, ResourceGroupKey resourceGroupKey) throws NotFoundException {
      return this.updateSecondaryInternal(id, change, key, resourceGroupKey, true);
   }

   public Object copyUpdateSecondary(ROID id, Serializable change, Object key, ResourceGroupKey resourceGroupKey) throws NotFoundException {
      return this.updateSecondaryInternal(id, change, key, resourceGroupKey, false);
   }

   private Object updateSecondaryInternal(ROID id, Serializable change, Object key, ResourceGroupKey resourceGroupKey, boolean incrementVersion) throws NotFoundException {
      WrappedRO wro;
      if (change == null) {
         wro = this.getPrimary(id, true, key, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey);
         return wro.getROInfoForSecondary(key);
      } else {
         wro = this.getPrimary(id, false, key, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey);
         HostID otherHost = wro.getOtherHost();
         if (ResourceGroupMigrationHandler.inMigrationMode.get() && otherHost != null && ResourceGroupMigrationHandler.getInstance().isServerInLocalCluster(otherHost)) {
            otherHost = null;
            this.removeSecondary(wro);
         }

         if (otherHost != null) {
            try {
               this.sendUpdateRequestToSecondary(wro, id, change, key, resourceGroupKey, incrementVersion);
               resetTimeOut(otherHost);
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug(id, "Secondary server " + otherHost);
               }

               return wro.getROInfoForSecondary(key);
            } catch (MarshalException var9) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug((String)(id + "MarshalException updating secondary for " + id + ", key " + key + ", on " + otherHost), (Throwable)var9);
               }

               ClusterExtensionLogger.logUnableToUpdateNonSerializableObject(key, var9);
               if (var9.detail instanceof NotSerializableException) {
                  if (incrementVersion) {
                     wro.decrementVersion(key);
                  }

                  return wro.getROInfoForSecondary(key);
               }
            } catch (RemoteException var10) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug(id, "Error updating secondary for " + id + ", key " + key + ", on " + otherHost, var10);
               }

               if (ReplicationSizeDebugLogger.isDebugEnabled() && var10 instanceof PeerGoneException) {
                  ReplicationSizeDebugLogger.debug(id, "Error updating secondary for " + id + ", key " + key + ", on " + otherHost + ", ReplicatedSessionChange is " + change.toString(), var10);
               }
            } catch (NotFoundException var11) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug(id, "Error updating secondary for " + id + ", key " + key + ", on " + otherHost + ". Re-creating secondary.", var11);
               }
            } catch (Exception var12) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug(id, "Error updating secondary for " + id + ", key " + key + ", on " + otherHost, var12);
               }
            }

            wro.setOtherHost((HostID)null);
            wro.setOtherHostInfo((Object)null);
         }

         this.createSecondary(wro, (Object)null);
         if (ResourceGroupMigrationHandler.inMigrationMode.get() && wro.getOtherHost() != null && !ResourceGroupMigrationHandler.getInstance().isServerInLocalCluster(wro.getOtherHost())) {
            wro.setIsMigrated(true);
         }

         return wro.getROInfoForSecondary(key);
      }
   }

   private void sendUpdateRequestToSecondary(WrappedRO wro, ROID id, Serializable change, Object key, ResourceGroupKey resourceGroupKey, boolean incrementVersion) throws RemoteException, NotFoundException {
      if (this.checkAndSetRecursive()) {
         ClusterExtensionLogger.logReentrantThread("'" + Thread.currentThread().getName() + "'");
      } else {
         try {
            HostID otherHost = wro.getOtherHost();
            int newVersion;
            if (incrementVersion) {
               newVersion = wro.incrementVersion(key);
            } else {
               newVersion = wro.getVersion(key) + 1;
            }

            if (replicationChannels != null) {
               int index = wro.channelIndex;

               assert index != -1;

               ReplicationServicesInternal secondaryRepMan = this.getRepMan(otherHost, replicationChannels[index]);
               if (ClusterService.getClusterServiceInternal().useOneWayRMI()) {
                  if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                     ReplicationDetailsDebugLogger.debug(id, "Using Multi-Channels for 1-way updateOneWay() on channel " + replicationChannels[index] + ", key: " + key + ", new version: " + newVersion);
                  }

                  if (incrementVersion) {
                     secondaryRepMan.updateOneWay(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
                  } else {
                     secondaryRepMan.copyUpdateOneWay(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
                  }
               } else {
                  if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                     ReplicationDetailsDebugLogger.debug(id, "Using Multiple-Channels for 2-way update() on channel" + replicationChannels[index] + ", key: " + key + ", new version: " + newVersion);
                  }

                  if (incrementVersion) {
                     secondaryRepMan.update(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
                  } else {
                     secondaryRepMan.copyUpdate(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
                  }
               }
            } else {
               if (ReplicationDetailsDebugLogger.isDebugEnabled()) {
                  ReplicationDetailsDebugLogger.debug(id, "Using Single-Channel for 2-way update()");
               }

               ReplicationServicesInternal secondaryRepMan = this.getRepMan(otherHost);
               if (incrementVersion) {
                  secondaryRepMan.update(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
               } else {
                  secondaryRepMan.copyUpdate(id, newVersion, new WrappedSerializable(change, resourceGroupKey == null ? getResourceGroupKey() : resourceGroupKey), key);
               }
            }
         } finally {
            this.clearRecursive();
         }

      }
   }

   protected static final String getPartitionIdFromName(String partitionName) throws NotFoundException {
      if (partitionName != null && !"DOMAIN".equals(partitionName)) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         PartitionMBean partitionMBean = domain.lookupPartition(partitionName);
         if (partitionMBean == null) {
            throw new NotFoundException("Unable to find partition: " + partitionName + " in domain: " + domain.getName());
         } else {
            return partitionMBean.getPartitionID();
         }
      } else {
         return "0";
      }
   }

   protected WrappedRO getPrimary(ROID id, boolean forceCreate, Object key, ResourceGroupKey resourceGroupKey) throws NotFoundException {
      WroManager wroManager = this.getWroManager();
      WrappedRO wro = wroManager.find(id, resourceGroupKey);
      if (wro == null || key != null && wro.getRO(key) == null) {
         String message = "Unable to find object for roid:" + id + ", ResourceGroupKey=" + resourceGroupKey + "[wro=" + wro + ", key=" + key + "]";
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, message);
         }

         throw new NotFoundException(message);
      } else {
         wroManager.ensureStatus((byte)0, wro);
         if (forceCreate && wro.getOtherHost() == null) {
            this.createSecondary(wro, (Object)null);
         }

         return wro;
      }
   }

   private void initReplicationChannelNames() {
      List names = ChannelService.getReplicationChannelNames();

      assert names != null;

      synchronized(names) {
         if (names.size() > 0 && replicationChannels == null) {
            replicationChannels = new String[names.size()];
            replicationChannels = (String[])names.toArray(replicationChannels);
            ClusterExtensionLogger.logUsingMultipleChannelsForReplication(Arrays.toString(replicationChannels));
            if (ClusterService.getClusterServiceInternal().useOneWayRMI()) {
               ClusterExtensionLogger.logUsingOneWayRMIForReplication();
            }
         } else if (ClusterService.getClusterServiceInternal().useOneWayRMI()) {
            ClusterExtensionLogger.logIgnoringOneWayRMIWithoutMultipleChannels();
         }

      }
   }

   private void initializeSecondarySelectorsForPartitions() {
      this.partitionToSecondarySelectorMap.put("0", Locator.locate(SelectorPolicy.LOCAL));
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (cluster != null) {
         this.registerClusterChangeEventListener();
      }

   }

   private void registerClusterChangeEventListener() {
      ClusterService.getClusterServiceInternal().addClusterMembersPartitionListener(this);
   }

   private void unregisterClusterChangeEventListener() {
      ClusterService.getClusterServiceInternal().removeClusterMembersPartitionListener(this);
   }

   static final String getPartitionId() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getPartitionId cic: " + cic);
      if (cic != null) {
         String partitionID = cic.getPartitionId();
         ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getPartitionId partitionID: " + partitionID);
         if (partitionID != null) {
            return partitionID;
         }
      }

      return "0";
   }

   protected static void resetTimeOut(HostID serverID) {
   }

   protected void exportRemoteAdapter() throws ServiceFailureException {
      try {
         this.replicationServiceRemoteAdapter.export();
      } catch (RemoteException var2) {
         throw new ServiceFailureException(var2.getMessage());
      }
   }

   protected void unExportRemoteAdapter() throws AssertionError {
      try {
         this.replicationServiceRemoteAdapter.unExport();
      } catch (RemoteException var2) {
         throw new AssertionError("Failed to unexport replication system" + var2);
      }
   }

   public final void removeOrphanedSessionOnCondition(ROID id, int version, Object key) {
      WrappedRO wro = this.wroMan.remove(id, key, getResourceGroupKey(), version);
      if (wro != null) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug(id, "Removed orphaned secondary with matching version");
         }
      } else if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug(id, "Did not remove orphaned secondary either because it does not exist or the version has been updated");
      }

   }

   final void ensureStatus(byte status, WrappedRO wro) {
      this.wroMan.ensureStatus(status, wro);
   }

   ClusterServicesInvocationContext getClusterServicesInvocationContext() {
      return this.clusterServicesInvocationContext;
   }

   static final String getPartitionName() {
      boolean debugEnabled = ResourceGroupMigrationDebugLogger.isDebugEnabled();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (debugEnabled) {
         ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getPartitionName cic: " + cic);
      }

      if (cic != null) {
         String partitionName = cic.getPartitionName();
         if (debugEnabled) {
            ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getPartitionName partitionName: " + partitionName);
         }

         if (partitionName != null) {
            return partitionName;
         }
      }

      return "DOMAIN";
   }

   static String getResourceGroupName() {
      boolean debugEnabled = ResourceGroupMigrationDebugLogger.isDebugEnabled();
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (debugEnabled) {
         ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getResourceGroupName cic: " + cic);
      }

      if (cic != null) {
         String applicationId = cic.getApplicationId();
         String applicationName = cic.getApplicationName();
         if (debugEnabled) {
            ResourceGroupMigrationDebugLogger.debug("ReplicationManager.getResourceGroupName applicationName: " + applicationId);
         }

         if (applicationId != null) {
            if (appNameToResourceGroupMap.containsKey(applicationId)) {
               return (String)appNameToResourceGroupMap.get(applicationId);
            }

            synchronized(appNameToResourceGroupMap) {
               if (appNameToResourceGroupMap.containsKey(applicationId)) {
                  return (String)appNameToResourceGroupMap.get(applicationId);
               }
            }
         }
      }

      return "GLOBAL_RESOURCE_GROUP";
   }

   static ResourceGroupKey getResourceGroupKey() {
      return getResourceGroupKey(getPartitionName(), getResourceGroupName());
   }

   static ResourceGroupKey getResourceGroupKey(String partitionName, String resourceGroup) {
      return ResourceGroupKeyImpl.createKey(partitionName, resourceGroup);
   }

   protected ReplicationServicesInternal lookupRemoteReplicationServiceOnHost(ServerIdentity hostID) throws RemoteException {
      try {
         return this.svcLocator.replicationServicesLookup(hostID, RemoteReplicationServicesInternalImpl.class);
      } catch (NamingException var3) {
         throw new RemoteException(var3.getMessage(), var3);
      }
   }

   protected boolean supportsAsyncBatchUpdates() {
      return false;
   }

   protected final ReplicationServicesInternal getRepMan(HostID hostID) throws RemoteException {
      if (hostID.isLocal()) {
         return this.replicationServiceRemoteAdapter;
      } else {
         ReplicationServicesInternal repMan = (ReplicationServicesInternal)this.cache.get(hostID);
         if (repMan == null) {
            repMan = this.lookupRemoteReplicationServiceOnHost((ServerIdentity)hostID);
            this.cache.put(hostID, repMan);
         }

         return repMan;
      }
   }

   protected ReplicationServicesInternal getRepMan(HostID hostID, String channelName) throws RemoteException {
      if (channelName != null && channelName.length() != 0) {
         CompositeKey key = new CompositeKey(hostID, channelName);
         ReplicationServicesInternal repMan = (ReplicationServicesInternal)this.cache2.get(key);
         if (repMan == null) {
            repMan = this.getRepManWithChannelName(hostID, channelName);
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Created new stub for hostID " + hostID + " using channel " + channelName);
            }

            this.cache2.put(key, repMan);
         }

         return repMan;
      } else {
         return this.getRepMan(hostID);
      }
   }

   protected ReplicationServicesInternal getRepManWithChannelName(HostID hostID, String channelName) throws RemoteException {
      try {
         ReplicationServicesInternal repMan = this.svcLocator.replicationServicesLookup((ServerIdentity)hostID, channelName, RemoteReplicationServicesInternalImpl.class);
         return repMan;
      } catch (NamingException var5) {
         throw new RemoteException(var5.getMessage(), var5);
      }
   }

   protected void cleanupDeadServer(HostID hostID) {
      this.cache.remove(hostID);
      Collection selectors;
      synchronized(this.secondarySelectorHashMap) {
         HashMap map = (HashMap)this.secondarySelectorHashMap.clone();
         selectors = map.values();
      }

      Iterator var3 = selectors.iterator();

      while(var3.hasNext()) {
         SecondarySelector selector = (SecondarySelector)var3.next();
         selector.removeDeadSecondarySrvr(hostID);
      }

   }

   public final String[] getSecondaryDistributionNames() {
      NumericValueHashtable map = new NumericValueHashtable();
      Collection replicationMaps = this.wroMan.resourceGroupMap.values();
      Iterator var3 = replicationMaps.iterator();

      while(var3.hasNext()) {
         ReplicationMap replicationMap = (ReplicationMap)var3.next();
         Collection wros = replicationMap.secondaries.values();
         Iterator var6 = wros.iterator();

         while(var6.hasNext()) {
            WrappedRO wro = (WrappedRO)var6.next();
            if (wro.getStatus() == 1) {
               HostID key = wro.getOtherHost();
               if (key != null) {
                  if (!map.containsKey(key)) {
                     map.put(key, 1L);
                  } else {
                     map.put(key, map.get(key) + 1L);
                  }
               }
            }
         }
      }

      ArrayList seclist = new ArrayList();
      Collection selectors;
      synchronized(this.secondarySelectorHashMap) {
         HashMap mapOfSelectors = (HashMap)this.secondarySelectorHashMap.clone();
         selectors = mapOfSelectors.values();
      }

      Iterator var14 = selectors.iterator();

      while(true) {
         SecondarySelector selector;
         do {
            if (!var14.hasNext()) {
               String[] result = new String[seclist.size()];

               for(int i = 0; i < seclist.size(); ++i) {
                  result[i] = (String)seclist.get(i);
               }

               return result;
            }

            selector = (SecondarySelector)var14.next();
         } while(!(selector instanceof LocalSecondarySelector));

         Iterator memIter = selector.getSecondaryCandidates().iterator();

         while(memIter.hasNext()) {
            ServerIdentity identity = (ServerIdentity)memIter.next();
            long numSecondaries = map.get(identity);
            if (numSecondaries > 0L) {
               seclist.add(identity.getServerName() + " : " + numSecondaries);
            }
         }
      }
   }

   protected SecondarySelector getLocalSecondarySelectorByPartition(String partitionId) {
      if (!this.partitionToSecondarySelectorMap.containsKey(partitionId)) {
         this.partitionToSecondarySelectorMap.put(partitionId, new PartitionAwareLocalSecondarySelector(partitionId));
      }

      return (SecondarySelector)this.partitionToSecondarySelectorMap.get(partitionId);
   }

   protected SecondarySelector getSecondarySelector() {
      ResourceGroupKey key = getResourceGroupKey();
      return this.getSecondarySelector(key);
   }

   protected SecondarySelector getSecondarySelector(ResourceGroupKey key) {
      SecondarySelector selector = null;
      synchronized(this.secondarySelectorHashMap) {
         selector = (SecondarySelector)this.secondarySelectorHashMap.get(key);
         if (selector == null) {
            try {
               String partitionId = getPartitionIdFromName(key.getPartitionName());
               selector = this.getLocalSecondarySelectorByPartition(partitionId);
               this.setSecondarySelector(key, selector);
            } catch (NotFoundException var6) {
               if (ReplicationDebugLogger.isDebugEnabled()) {
                  ReplicationDebugLogger.debug("Failed to find partition id with partition name: " + key.getPartitionName());
               }
            }
         }

         return selector;
      }
   }

   protected void setSecondarySelector(ResourceGroupKey key, SecondarySelector secondarySelector) {
      synchronized(this.secondarySelectorHashMap) {
         this.secondarySelectorHashMap.put(key, secondarySelector);
      }
   }

   protected boolean removeSecondarySelector(SecondarySelector selector) {
      synchronized(this.secondarySelectorHashMap) {
         Collection selectors = this.secondarySelectorHashMap.values();
         Iterator itr = selectors.iterator();

         SecondarySelector value;
         do {
            if (!itr.hasNext()) {
               return false;
            }

            value = (SecondarySelector)itr.next();
         } while(!selector.equals(value));

         itr.remove();
         return true;
      }
   }

   public void removeSecondarySelector(String partitionName, String resourceGroupName) {
      if (partitionName != null && resourceGroupName != null) {
         synchronized(this.secondarySelectorHashMap) {
            this.secondarySelectorHashMap.remove(new ResourceGroupKeyImpl(partitionName, resourceGroupName));
         }
      }

   }

   final WroManager getWroManager() {
      return this.wroMan;
   }

   Set getReplicatedOnShutdownSet() {
      return this.replicatedOnShutdownSet;
   }

   MulticastSession getClusterMessageSender() {
      if (this.multicastSessionSender == null) {
         this.multicastSessionSender = ClusterService.getClusterServiceInternal().createMulticastSession((RecoverListener)null, -1);
      }

      return this.multicastSessionSender;
   }

   public QuerySessionResponseMessage sendQuerySessionRequest(QuerySessionRequestMessage request, int timeout) {
      Collection rmembers = ClusterService.getServices().getRemoteMembers();
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("ReplicationManager.sendQuerySessionRequest(" + request + "): cluster remote members: " + rmembers);
      }

      if (rmembers.isEmpty()) {
         return null;
      } else {
         QuerySessionRequestMessageWrapper msg = new QuerySessionRequestMessageWrapper(request);
         QuerySessionResponse response = new QuerySessionResponse(request.getID());
         querySessionMap.put(request.getID(), response);
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("Querying cluster for session with requestID: " + request.getID());
         }

         try {
            this.getClusterMessageSender().send(msg);
         } catch (IOException var14) {
            var14.printStackTrace();
            querySessionMap.remove(msg.request.getID());
            return null;
         }

         synchronized(response) {
            long timeToWait = (long)(timeout * 1000);
            long endTime = System.currentTimeMillis() + timeToWait;

            while(true) {
               if (response.getResponse() != null || System.currentTimeMillis() >= endTime) {
                  if (querySessionMap.containsKey(response.id)) {
                     querySessionMap.remove(response.id);
                  }
                  break;
               }

               try {
                  if (ReplicationDebugLogger.isDebugEnabled()) {
                     ReplicationDebugLogger.debug("ReplicationManager.sendQuerySessionRequest: Wait for QuerySessionResponse with requestID: " + request.getID());
                  }

                  response.wait(timeToWait);
               } catch (InterruptedException var13) {
               }
            }
         }

         QuerySessionResponseMessage result = response.getResponse();
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("result: " + result + " for response.id: " + response.id);
            if (result != null) {
               ReplicationDebugLogger.debug("Received response to session query for requestID: " + result.getID());
            }
         }

         return result;
      }
   }

   public void handleQuerySessionRequest(HostID sender, QuerySessionRequestMessage requestMessage) {
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("Received session query request for requestID: " + requestMessage.getID() + " from server: " + sender);
      }

      QuerySessionResponseMessage response = requestMessage.execute(sender);
      if (response != null) {
         if (ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("Sending session query response " + response + " for requestID: " + requestMessage.getID() + " to server: " + sender);
         }

         QuerySessionResponseMessageWrapper wrapper = new QuerySessionResponseMessageWrapper(response, sender);

         try {
            this.getClusterMessageSender().send(wrapper);
         } catch (IOException var6) {
            var6.printStackTrace();
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug("Failed to send response for requestID: " + requestMessage.getID() + " to server: " + sender);
            }
         }
      }

   }

   public void handleQuerySessionResponse(HostID sender, QuerySessionResponseMessage responseMessage) {
      assert responseMessage != null;

      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("Received session query response for requestID: " + responseMessage.getID() + " from server: " + sender + ", responseMessage: " + responseMessage.getClass().getName() + " querySessionMap: " + querySessionMap);
      }

      QuerySessionResponse response = (QuerySessionResponse)querySessionMap.remove(responseMessage.getID());
      if (ReplicationDebugLogger.isDebugEnabled()) {
         ReplicationDebugLogger.debug("Response object response: " + response + " for requestID: " + responseMessage.getID());
      }

      if (response != null) {
         synchronized(response) {
            response.setResponse(responseMessage);
            response.notify();
         }
      }

   }

   public void sync() {
   }

   public final long getPrimaryCount() {
      long count = 0L;
      Collection replicationMaps = this.getWroManager().resourceGroupMap.values();

      ReplicationMap map;
      for(Iterator var4 = replicationMaps.iterator(); var4.hasNext(); count += (long)map.primaries.size()) {
         map = (ReplicationMap)var4.next();
      }

      return count;
   }

   public final long getSecondaryCount() {
      long count = 0L;
      Collection replicationMaps = this.getWroManager().resourceGroupMap.values();

      ReplicationMap map;
      for(Iterator var4 = replicationMaps.iterator(); var4.hasNext(); count += (long)map.secondaries.size()) {
         map = (ReplicationMap)var4.next();
      }

      return count;
   }

   public void changeSecondary(HostID host) {
      Collection maps = this.getWroManager().resourceGroupMap.values();
      Iterator var3 = maps.iterator();

      while(var3.hasNext()) {
         ReplicationMap map = (ReplicationMap)var3.next();
         Map combined = map.getCombinedMap();
         if (combined.size() > 0) {
            Iterator iter = combined.values().iterator();
            WorkManagerFactory.getInstance().getSystem().schedule(new ChangeSecondaryInfo(iter, host));
         }
      }

   }

   public HostID[] getPrimarySecondaryHosts(ROID roid) {
      HostID[] ids = new HostID[2];
      WrappedRO wro = this.getWroManager().find(roid);
      if (wro != null) {
         if (wro.getStatus() == 0) {
            ids[0] = LOCAL_HOSTID;
            ids[1] = wro.getOtherHost();
         } else {
            ids[0] = wro.getOtherHost();
            ids[1] = LOCAL_HOSTID;
         }
      }

      return ids;
   }

   private static class ChangeSecondaryInfo implements Runnable {
      private final Iterator iterator;
      private final HostID hostID;

      private ChangeSecondaryInfo(Iterator iterator, HostID hostID) {
         this.iterator = iterator;
         this.hostID = hostID;
      }

      public void run() {
         int i = 0;
         long start = System.currentTimeMillis();
         ReplicationManager replicationManager = (ReplicationManager)GlobalServiceLocator.getServiceLocator().getService(ReplicationManager.class, new Annotation[0]);

         while(this.iterator.hasNext()) {
            WrappedRO wro = (WrappedRO)this.iterator.next();
            if (this.hostID.equals(wro.getOtherHost())) {
               wro.setOtherHost((HostID)null);
               wro.setOtherHostInfo((Object)null);
               ++i;
            }
         }

         long end = System.currentTimeMillis();
         if (i > 0 && ReplicationDebugLogger.isDebugEnabled()) {
            ReplicationDebugLogger.debug("Changed the status of " + i + " objects and it took " + (end - start) + " ms");
         }

      }

      // $FF: synthetic method
      ChangeSecondaryInfo(Iterator x0, HostID x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class ZDTSecondaryFilter implements SecondaryFilter {
      private ZDTSecondaryFilter() {
      }

      public HostID select(HostID secondary, List candidates) {
         ClusterService cs = ClusterService.getClusterServiceInternal();
         List failoverServerGroups = cs.getFailoverServerGroups();
         if (failoverServerGroups != null && !failoverServerGroups.isEmpty()) {
            List compatibleFailoverServers = new ArrayList();
            Iterator var6 = failoverServerGroups.iterator();

            while(var6.hasNext()) {
               List serverGroup = (List)var6.next();
               if (serverGroup != null && serverGroup.contains(ReplicationServicesImplBase.LOCAL_HOSTID.getServerName())) {
                  compatibleFailoverServers.addAll(serverGroup);
               }
            }

            if (!compatibleFailoverServers.contains(secondary.getServerName())) {
               return secondary;
            } else if (candidates == null) {
               return null;
            } else {
               var6 = candidates.iterator();

               HostID candidate;
               do {
                  if (!var6.hasNext()) {
                     return null;
                  }

                  candidate = (HostID)var6.next();
               } while(compatibleFailoverServers.contains(candidate.getServerName()));

               return candidate;
            }
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      ZDTSecondaryFilter(Object x0) {
         this();
      }
   }

   private static class CompositeKey {
      protected final HostID hostID;
      protected final String channelName;
      private final int hashcode;

      CompositeKey(HostID hostID, String channelName) {
         this.hostID = hostID;
         this.channelName = channelName;
         this.hashcode = hostID.hashCode() ^ channelName.hashCode();
      }

      public int hashCode() {
         return this.hashcode;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof CompositeKey)) {
            return false;
         } else {
            CompositeKey key = (CompositeKey)other;
            return (this.channelName == key.channelName || this.channelName != null && this.channelName.equals(key.channelName)) && (this.hostID == key.hostID || this.hostID != null && this.hostID.equals(key.hostID));
         }
      }
   }

   protected interface SecondaryFilter {
      HostID select(HostID var1, List var2);
   }
}
