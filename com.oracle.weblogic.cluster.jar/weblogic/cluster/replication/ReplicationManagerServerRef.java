package weblogic.cluster.replication;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.channels.ChannelService;
import weblogic.utils.collections.LRUCacheHashMap;
import weblogic.work.ExecuteQueueFactory;
import weblogic.work.ServerWorkManagerFactory;
import weblogic.work.WorkManager;

public class ReplicationManagerServerRef extends BasicServerRef {
   private static final String REP_CHANNEL_NAME;
   private static ServerChannel replicationChannel;
   private static Map dispatchPolicyMapping;
   private static boolean USE_SSL;
   private static final String SERVER_NAME;
   private static final String CLUSTER_NAME;
   private static final int MAX_CACHE = 512;
   private static final Object PRIVILEGED = new Object();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static Map cache = Collections.synchronizedMap(new LRUCacheHashMap(512));

   public ReplicationManagerServerRef(int oid, Object o) throws RemoteException {
      super(oid, o);
   }

   private static void initialize(ServerMBean server) {
      List replicationNames = ChannelService.getReplicationChannelNames();
      if (replicationNames.size() > 0) {
         String[] names = new String[replicationNames.size()];
         names = (String[])replicationNames.toArray(names);
         dispatchPolicyMapping = new HashMap();
         String[] var3 = names;
         int var4 = names.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String chName = var3[var5];
            ServerChannel sc = ServerChannelManager.findLocalServerChannel(chName);
            if (sc == null) {
               ClusterExtensionLogger.logNoChannelForReplicationCalls(CLUSTER_NAME);
               throw new AssertionError("Can't find replication server channel '" + chName + "' for " + SERVER_NAME);
            }

            WorkManager wm;
            if (server.getUse81StyleExecuteQueues()) {
               wm = ExecuteQueueFactory.createExecuteQueue(chName, 1);
            } else {
               wm = ServerWorkManagerFactory.createExecuteQueue(chName, 1);
            }

            dispatchPolicyMapping.put(sc, wm);
         }
      }

      replicationChannel = ServerChannelManager.findLocalServerChannel(REP_CHANNEL_NAME);
      if (replicationChannel != null) {
         USE_SSL = replicationChannel.getProtocol().isSecure();
      } else {
         replicationChannel = ServerChannelManager.findLocalServerChannel(USE_SSL ? ProtocolManager.getDefaultSecureProtocol() : ProtocolManager.getDefaultProtocol());
      }

      if (replicationChannel == null && !USE_SSL && !server.isListenPortEnabled()) {
         replicationChannel = ServerChannelManager.findLocalServerChannel(ProtocolManager.getDefaultSecureProtocol());
      }

      if (replicationChannel == null) {
         ClusterExtensionLogger.logNoChannelForReplicationCalls(CLUSTER_NAME);
         throw new AssertionError("No replication server channel for " + SERVER_NAME);
      }
   }

   private void reqReceivedOnReplicationChannel(ServerChannel channel) throws RemoteException {
      if (dispatchPolicyMapping == null || !dispatchPolicyMapping.containsKey(channel)) {
         if (!replicationChannel.equals(channel)) {
            ClusterLogger.logWrongChannelForReplicationCalls(CLUSTER_NAME, channel.getChannelName());
            SecurityException se = new SecurityException("Incorrect channel used for replication " + channel);
            throw new RemoteException(se.getMessage(), se);
         }
      }
   }

   protected WorkManager getWorkManager(ServerChannel sc, RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      WorkManager wm;
      if (replicationChannel.equals(sc)) {
         wm = super.getWorkManager(md, subject);
      } else {
         wm = (WorkManager)dispatchPolicyMapping.get(sc);
      }

      return wm;
   }

   protected void checkPriviledges(AuthenticatedSubject subject, ServerChannel channel, EndPoint endPoint) throws RemoteException {
      SecurityException se;
      if (channel == null) {
         se = new SecurityException("ServerChannel is null");
         throw new RemoteException(se.getMessage(), se);
      } else {
         this.reqReceivedOnReplicationChannel(channel);
         if (subject == null) {
            se = new SecurityException("AuthenticatedSubject is null");
            throw new RemoteException(se.getMessage(), se);
         } else if (doesCacheContains(subject)) {
            getFromCache(subject);
         } else {
            verifyAndCachePriviledgesFor(subject);
         }
      }
   }

   private static boolean doesCacheContains(AuthenticatedSubject subject) {
      return cache.containsKey(subject);
   }

   private static void getFromCache(AuthenticatedSubject subject) throws RemoteException {
      Object obj = cache.get(subject);
      if (obj instanceof RemoteException) {
         throw (RemoteException)obj;
      }
   }

   private static void verifyAndCachePriviledgesFor(AuthenticatedSubject sub) throws RemoteException {
      try {
         SecureReplicationInvocationHandler.checkPriviledges(sub, USE_SSL);
      } catch (SecurityException var3) {
         RemoteException re = new RemoteException(var3.getMessage(), var3);
         cache.put(sub, re);
         throw re;
      }

      cache.put(sub, PRIVILEGED);
   }

   public void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_REPLICATION_MANAGER_SERVER_REF_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
      Throwable var7 = null;

      try {
         super.invoke(md, request, response);
      } catch (Throwable var16) {
         var7 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var7.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   static {
      ServerMBean server = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
      SERVER_NAME = server.getName();
      ClusterMBean clusterMBean = server.getCluster();
      if (clusterMBean == null) {
         throw new AssertionError("Can't have replication without a cluster");
      } else {
         CLUSTER_NAME = clusterMBean.getName();
         REP_CHANNEL_NAME = clusterMBean.getReplicationChannel();
         USE_SSL = clusterMBean.isSecureReplicationEnabled();
         initialize(server);
      }
   }
}
