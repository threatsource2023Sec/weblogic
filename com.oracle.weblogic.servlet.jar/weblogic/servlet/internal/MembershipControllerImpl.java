package weblogic.servlet.internal;

import java.lang.annotation.Annotation;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.application.AppClassLoaderManager;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.cluster.RemoteClusterMemberManager;
import weblogic.cluster.RemoteClusterMembersChangeListener;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.ClusterMBean;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.encoders.BASE64Encoder;

public class MembershipControllerImpl implements ClusterMembersChangeListener, RemoteClusterMembersChangeListener, ClusterProvider {
   private final Map localClusterChannelMap = new ConcurrentHashMap();
   private final Map localClusterMap = new ConcurrentHashMap();
   private final Map clusterMap = new ConcurrentHashMap();
   private final BASE64Encoder base64 = new BASE64Encoder();
   private MessageDigest sha;
   private String currentHash;
   private ClusterServices clusterServices;
   private AppClassLoaderManager appClassLoaderManager;
   private ClusterServicesInvocationContext clusterServicesInvocationContext;

   public MembershipControllerImpl() {
      try {
         this.sha = MessageDigest.getInstance("SHA");
      } catch (NoSuchAlgorithmException var2) {
         throw new Error("JVM does not support SHA");
      }
   }

   public void start() {
      this.clusterServices = Locator.locate();
      this.clusterServices.addClusterMembersListener(this);
      ServerIdentity local = LocalServerIdentity.getIdentity();
      this.localClusterMap.put(new Integer(local.hashCode()), local);
      this.clusterMap.put(new Integer(local.hashCode()), local);
      Iterator servers = this.clusterServices.getRemoteMembers().iterator();

      while(servers.hasNext()) {
         ClusterMemberInfo meminfo = (ClusterMemberInfo)servers.next();
         ServerIdentity id = meminfo.identity();
         this.localClusterMap.put(new Integer(id.hashCode()), id);
         this.clusterMap.put(new Integer(id.hashCode()), id);
      }

      this.getHash();
      if (this.isClusterManType()) {
         RemoteClusterMemberManager manager = weblogic.cluster.RemoteClusterMemberManager.Locator.locateRemoteSiteManager();
         manager.start();
         manager.addRemoteClusterMemberListener(this);
      }

   }

   public void stop() {
      this.clusterServices.removeClusterMembersListener(this);
      if (this.isClusterManType()) {
         RemoteClusterMemberManager manager = weblogic.cluster.RemoteClusterMemberManager.Locator.locateRemoteSiteManager();
         manager.removeRemoteClusterMemberListener(this);
         manager.stop();
      }

      this.clusterServices = null;
   }

   public boolean shouldDetectSessionCompatiblity() {
      List failoverGroups = this.getFailoverServerGroups();
      return failoverGroups != null && !failoverGroups.isEmpty();
   }

   private List getFailoverServerGroups() {
      List failoverGroups = null;
      if (this.clusterServices != null) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String partitionName = null;
         if (cic != null) {
            partitionName = cic.getPartitionName();
         }

         failoverGroups = partitionName != null ? this.clusterServices.getFailoverServerGroups(partitionName) : this.clusterServices.getFailoverServerGroups();
      }

      return failoverGroups;
   }

   public String getCompatibleFailoverServerList(ServerChannel channel, String serverName) {
      List failoverServerGroups = this.getFailoverServerGroups();
      if (failoverServerGroups != null && !failoverServerGroups.isEmpty()) {
         List compatibleFailoverServers = new ArrayList();
         Iterator var5 = failoverServerGroups.iterator();

         while(var5.hasNext()) {
            List serverGroup = (List)var5.next();
            if (serverGroup != null && !serverGroup.contains(serverName)) {
               compatibleFailoverServers.addAll(serverGroup);
            }
         }

         StringBuilder result = null;
         String channelName = channel.getChannelName();
         Iterator var7 = compatibleFailoverServers.iterator();

         while(var7.hasNext()) {
            String server = (String)var7.next();
            if (server != null && !server.isEmpty()) {
               ServerIdentity identity = this.findServerIdentity(server);
               if (identity != null) {
                  String serverEntry = ServerHelper.createServerEntry(channelName, identity, "!");
                  if (serverEntry != null) {
                     if (result == null) {
                        result = new StringBuilder();
                        result.append(serverEntry);
                     } else {
                        result.append("|").append(serverEntry);
                     }
                  }
               }
            }
         }

         return result == null ? null : result.toString();
      } else {
         return null;
      }
   }

   private ServerIdentity findServerIdentity(String serverName) {
      Iterator var2 = this.localClusterMap.values().iterator();

      ServerIdentity serverIdentity;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         serverIdentity = (ServerIdentity)var2.next();
      } while(serverIdentity == null || !serverName.equals(serverIdentity.getServerName()));

      return serverIdentity;
   }

   private boolean isClusterManType() {
      ClusterMBean cluster = WebServerRegistry.getInstance().getClusterMBean();
      return cluster != null ? "man".equals(cluster.getClusterType()) : false;
   }

   private Map fabricateList(String channelName) {
      Map map = new ConcurrentHashMap();
      ClusterServices services = weblogic.cluster.ClusterServicesActivator.Locator.locateClusterServices();
      if (services == null) {
         throw new AssertionError("ClusterService not initialized");
      } else {
         Iterator i = this.localClusterMap.values().iterator();

         while(i.hasNext()) {
            ServerIdentity identity = (ServerIdentity)i.next();
            String serverEntry = ServerHelper.createServerEntry(channelName, identity, "!");
            if (serverEntry != null) {
               map.put(new Integer(identity.hashCode()), serverEntry);
            }
         }

         return map;
      }
   }

   private String calcHash() {
      this.sha.reset();
      List l = new ArrayList(this.localClusterMap.keySet());
      Collections.sort(l);
      Iterator iterator = l.iterator();

      while(iterator.hasNext()) {
         this.sha.update(((Integer)iterator.next()).toString().getBytes());
      }

      String encoded = this.base64.encodeBuffer(this.sha.digest());
      return encoded.substring(0, encoded.length() - 1);
   }

   public synchronized void clusterMembersChanged(ClusterMembersChangeEvent cmce) {
      this.addClusterMember(cmce.getClusterMemberInfo(), cmce.getAction(), true);
   }

   private void addClusterMember(ClusterMemberInfo meminfo, int action, boolean isLocal) {
      ServerIdentity identity = meminfo.identity();
      Integer key = new Integer(identity.hashCode());
      Iterator iter;
      switch (action) {
         case 0:
         case 2:
            if (isLocal) {
               if (!this.localClusterChannelMap.isEmpty()) {
                  iter = this.localClusterChannelMap.keySet().iterator();

                  while(iter.hasNext()) {
                     String channelName = (String)iter.next();
                     String serverEntry = ServerHelper.createServerEntry(channelName, identity, "!");
                     if (serverEntry != null) {
                        Map map = (Map)this.localClusterChannelMap.get(channelName);
                        map.put(key, serverEntry);
                     }
                  }
               }

               this.localClusterMap.put(key, identity);
            }

            this.clusterMap.put(key, identity);
            this.currentHash = this.calcHash();
            break;
         case 1:
            if (isLocal) {
               this.localClusterMap.remove(key);
               if (!this.localClusterChannelMap.isEmpty()) {
                  iter = this.localClusterChannelMap.values().iterator();

                  while(iter.hasNext()) {
                     Map map = (Map)iter.next();
                     map.remove(key);
                  }
               }
            }

            this.clusterMap.remove(key);
            this.currentHash = this.calcHash();
      }

   }

   public String getHash() {
      if (this.currentHash == null) {
         synchronized(this) {
            if (this.currentHash == null) {
               this.currentHash = this.calcHash();
            }
         }
      }

      return this.currentHash;
   }

   public String[] getClusterList(ServerChannel channel) {
      String channelName = channel.getChannelName();
      Map currentMap = (Map)this.localClusterChannelMap.get(channelName);
      if (currentMap == null) {
         synchronized(this) {
            currentMap = (Map)this.localClusterChannelMap.get(channelName);
            if (currentMap == null) {
               currentMap = this.fabricateList(channelName);
               this.localClusterChannelMap.put(channelName, currentMap);
            }
         }
      }

      String[] result = null;
      synchronized(currentMap) {
         int size = currentMap.size();
         if (size < 1) {
            return null;
         } else {
            result = new String[size];
            currentMap.values().toArray(result);
            return result;
         }
      }
   }

   public Map getClusterMembers() {
      return this.clusterMap;
   }

   public void remoteClusterMembersChanged(ArrayList list) {
      int size = list.size();
      if (size == 0) {
         this.clusterMap.clear();
         this.clusterMap.putAll(this.localClusterMap);
      } else {
         for(int i = 0; i < size; ++i) {
            ClusterMemberInfo info = (ClusterMemberInfo)list.get(i);
            this.addClusterMember(info, 2, false);
         }
      }

   }

   public ClusterServices getClusterService() {
      return this.clusterServices;
   }

   public AppClassLoaderManager getApplicationClassLoaderManager() {
      if (this.appClassLoaderManager == null) {
         this.appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
      }

      return this.appClassLoaderManager;
   }

   public ClusterServicesInvocationContext getClusterServicesInvocationContext() {
      if (this.clusterServicesInvocationContext == null) {
         this.clusterServicesInvocationContext = (ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0]);
      }

      return this.clusterServicesInvocationContext;
   }
}
