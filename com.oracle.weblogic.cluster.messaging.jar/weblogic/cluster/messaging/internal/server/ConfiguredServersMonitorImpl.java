package weblogic.cluster.messaging.internal.server;

import java.net.InetAddress;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.cluster.messaging.internal.ConfiguredServersMonitor;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.GroupManagerImpl;
import weblogic.cluster.messaging.internal.ServerConfigurationInformation;
import weblogic.cluster.messaging.internal.ServerConfigurationInformationImpl;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfiguredServersMonitorImpl implements ConfiguredServersMonitor {
   private static final boolean DEBUG;
   private static final AuthenticatedSubject kernelId;
   private final ServerConfigurationInformation localInfo;
   private final TreeSet servers;
   private final String clusterName;
   private final HashMap serverBeanUpdateListeners;

   public static ConfiguredServersMonitor getInstance() {
      return ConfiguredServersMonitorImpl.Factory.THE_ONE;
   }

   private ConfiguredServersMonitorImpl() {
      this.servers = new TreeSet();
      this.serverBeanUpdateListeners = new HashMap();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerMBean server = runtimeAccess.getServer();
      this.localInfo = this.createConfiguration(server);
      if (DEBUG) {
         this.debug("localInfo=" + this.localInfo);
      }

      ClusterMBean cluster = server.getCluster();
      ServerMBean[] mbeans = cluster.getServers();

      for(int i = 0; i < mbeans.length; ++i) {
         this.addServer(mbeans[i]);
      }

      DomainMBean domainMBean = runtimeAccess.getDomain();
      this.registerBeanUpdateListener(domainMBean, new DomainBeanUpdateListener());
      this.clusterName = cluster.getName();
   }

   void addServer(ServerMBean mbean) {
      this.registerServerConfigUpdateListener(mbean);
      ServerConfigurationInformation info = this.createConfiguration(mbean);
      synchronized(this.servers) {
         this.servers.add(info);
      }

      if (DEBUG) {
         this.debug("added " + info + " configured servers: " + this.servers);
      }

   }

   private void removeServer(ServerMBean server, boolean unregisterListener) {
      if (unregisterListener) {
         this.unregisterServerConfigUpdateListener(server);
      }

      synchronized(this.servers) {
         Iterator itr = this.servers.iterator();

         while(itr.hasNext()) {
            ServerConfigurationInformation info = (ServerConfigurationInformation)itr.next();
            if (info.getServerName().equals(server.getName())) {
               itr.remove();
               break;
            }
         }
      }

      if (DEBUG) {
         this.debug("removed " + server.getName() + " configured servers: " + this.servers);
      }

   }

   private void registerBeanUpdateListener(DescriptorBean descriptorBean, BeanUpdateListener beanUpdateListener) {
      descriptorBean.addBeanUpdateListener(beanUpdateListener);
   }

   private void unregisterBeanUpdateListener(DescriptorBean descriptorBean, BeanUpdateListener beanUpdateListener) {
      descriptorBean.removeBeanUpdateListener(beanUpdateListener);
   }

   private void registerServerConfigUpdateListener(ServerMBean serverMBean) {
      ServerBeanUpdateListener serverBeanUpdateListener = (ServerBeanUpdateListener)this.serverBeanUpdateListeners.get(serverMBean.getName());
      if (serverBeanUpdateListener == null) {
         serverBeanUpdateListener = new ServerBeanUpdateListener(serverMBean.getName());
         this.registerBeanUpdateListener(serverMBean, serverBeanUpdateListener);
         this.serverBeanUpdateListeners.put(serverMBean.getName(), serverBeanUpdateListener);
         if (DEBUG) {
            this.debug("registerServerConfigUpdateListener registered beanUpdateListener: " + serverBeanUpdateListener + " to serverMBean: " + serverMBean);
         }
      }

   }

   private void unregisterServerConfigUpdateListener(ServerMBean serverMBean) {
      BeanUpdateListener beanUpdateListener = (BeanUpdateListener)this.serverBeanUpdateListeners.remove(serverMBean.getName());
      if (beanUpdateListener != null) {
         this.unregisterBeanUpdateListener(serverMBean, beanUpdateListener);
         if (DEBUG) {
            this.debug("unregisterServerConfigUpdateListener unregistered beanUpdateListener: " + beanUpdateListener + " from serverMBean: " + serverMBean);
         }
      }

   }

   private void debug(String s) {
      Environment.getLogService().debug("[ConfiguredServersMonitor] " + s);
   }

   private ServerConfigurationInformation createConfiguration(ServerMBean server) {
      String clusterName = server.getCluster().getName();
      NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();
      if (naps != null) {
         for(int i = 0; i < naps.length; ++i) {
            if (naps[i].getName().equals(server.getCluster().getClusterBroadcastChannel())) {
               String addressHostname = this.determineListenAddressHostname(naps[i].getListenAddress(), server);
               InetAddress address = Environment.getInetAddressFromHostname(addressHostname);
               if (DEBUG) {
                  this.debug("obtained NAP [" + address + ":" + naps[i].getListenPort() + "] for " + server.getName());
               }

               return new ServerConfigurationInformationImpl(address, naps[i].getListenPort(), server.getName(), 1L, ChannelHelper.isNAPSecure(naps[i]), addressHostname, clusterName);
            }
         }
      }

      String addressHostname = this.determineListenAddressHostname(server.getListenAddress(), server);
      InetAddress address = Environment.getInetAddressFromHostname(addressHostname);
      ServerMBean localserver = ManagementService.getRuntimeAccess(kernelId).getServer();
      if (server.isListenPortEnabled() && localserver.isListenPortEnabled()) {
         return new ServerConfigurationInformationImpl(address, server.getListenPort(), server.getName(), 1L, false, addressHostname, clusterName);
      } else {
         SSLMBean sslMBean = server.getSSL();
         if (sslMBean.isListenPortEnabled() && localserver.getSSL().isListenPortEnabled()) {
            return new ServerConfigurationInformationImpl(address, sslMBean.getListenPort(), server.getName(), 1L, true, addressHostname, clusterName);
         } else {
            throw new AssertionError("Servers do not have a common channel to communicate over");
         }
      }
   }

   private String determineListenAddressHostname(String hostname, ServerMBean server) {
      if (hostname != null && hostname.length() > 0) {
         return hostname;
      } else {
         MachineMBean machine = server.getMachine();
         if (machine != null) {
            NodeManagerMBean nm = machine.getNodeManager();
            if (nm != null) {
               hostname = nm.getListenAddress();
               if (DEBUG) {
                  this.debug("listen address can be obtained from machine configuration : " + hostname);
               }

               return hostname;
            }
         }

         if (DEBUG) {
            this.debug("both of listen address and machine address are null.");
         }

         return null;
      }
   }

   public ServerConfigurationInformation getLocalServerConfiguration() {
      return this.localInfo;
   }

   public SortedSet getConfiguredServers() {
      TreeSet result = null;
      synchronized(this.servers) {
         result = (TreeSet)this.servers.clone();
         return result;
      }
   }

   public ServerConfigurationInformation getConfiguration(String serverName) {
      synchronized(this.servers) {
         Iterator iter = this.servers.iterator();

         ServerConfigurationInformation info;
         do {
            if (!iter.hasNext()) {
               return null;
            }

            info = (ServerConfigurationInformation)iter.next();
         } while(!info.getServerName().equals(serverName));

         return info;
      }
   }

   // $FF: synthetic method
   ConfiguredServersMonitorImpl(Object x0) {
      this();
   }

   static {
      DEBUG = Environment.DEBUG;
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private class DomainBeanUpdateListener implements BeanUpdateListener {
      private DomainBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var3 = updates;
         int var4 = updates.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate update = var3[var5];
            if (ConfiguredServersMonitorImpl.DEBUG) {
               ConfiguredServersMonitorImpl.this.debug("DomainBeanUpdateListener.activateUpdate update: " + update);
            }

            Object o;
            ServerMBean server;
            switch (update.getUpdateType()) {
               case 2:
                  if (update.getPropertyName().equals("Servers")) {
                     o = update.getAddedObject();
                     if (o instanceof ServerMBean) {
                        server = (ServerMBean)o;
                        if (server.getCluster() != null && server.getCluster().getName().equals(ConfiguredServersMonitorImpl.this.clusterName)) {
                           ConfiguredServersMonitorImpl.this.addServer(server);
                           ServerConfigurationInformation info = ConfiguredServersMonitorImpl.this.createConfiguration(server);
                           GroupManagerImpl.getInstance().addToGroup(info);
                           if (ConfiguredServersMonitorImpl.DEBUG) {
                              ConfiguredServersMonitorImpl.this.debug("DomainBeanUpdateListener.activateUpdate ADD serverConfigurationInfo: " + ConfiguredServersMonitorImpl.this.createConfiguration(server));
                           }
                        }
                     }
                  }
                  break;
               case 3:
                  if (update.getPropertyName().equals("Servers")) {
                     o = update.getRemovedObject();
                     if (o instanceof ServerMBean) {
                        server = (ServerMBean)o;
                        ConfiguredServersMonitorImpl.this.removeServer(server, true);
                        GroupManagerImpl.getInstance().removeFromGroup(server.getName());
                        if (ConfiguredServersMonitorImpl.DEBUG) {
                           ConfiguredServersMonitorImpl.this.debug("DomainBeanUpdateListener.activateUpdate REMOVE server: " + server.getName());
                        }
                     }
                  }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      DomainBeanUpdateListener(Object x1) {
         this();
      }
   }

   private class ServerBeanUpdateListener implements BeanUpdateListener {
      String serverName;

      ServerBeanUpdateListener(String name) {
         this.serverName = name;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         boolean listenAddressOrPortChange = false;
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var4 = updates;
         int var5 = updates.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BeanUpdateEvent.PropertyUpdate update = var4[var6];
            if (ConfiguredServersMonitorImpl.DEBUG) {
               ConfiguredServersMonitorImpl.this.debug("ServerBeanUpdateListener.activateUpdate update: " + update);
            }

            switch (update.getUpdateType()) {
               case 1:
                  if (update.getPropertyName().equals("Cluster")) {
                     if (ConfiguredServersMonitorImpl.DEBUG) {
                        ConfiguredServersMonitorImpl.this.debug("ServerBeanUpdateListener.activateUpdate CHANGE in cluster property for server: " + this.serverName);
                     }

                     DomainMBean domain = ManagementService.getRuntimeAccess(ConfiguredServersMonitorImpl.kernelId).getDomain();
                     ServerMBean serverx = domain.lookupServer(this.serverName);
                     if (serverx.getCluster() == null) {
                        this.removeConfiguredServer(serverx, false);
                        if (ConfiguredServersMonitorImpl.DEBUG) {
                           ConfiguredServersMonitorImpl.this.debug("ServerBeanUpdateListener.activateUpdate " + this.serverName + " configured to STANDALONE from cluster");
                        }
                     } else if (serverx.getCluster().getName().equals(ConfiguredServersMonitorImpl.this.clusterName)) {
                        this.addConfiguredServer(serverx);
                        if (ConfiguredServersMonitorImpl.DEBUG) {
                           ConfiguredServersMonitorImpl.this.debug("ServerBeanUpdateListener.activateUpdate " + this.serverName + " configured for cluster: " + serverx.getCluster().getName());
                        }
                     }
                  } else if (update.getPropertyName().equals("ListenAddress") || update.getPropertyName().equals("ListenPort")) {
                     listenAddressOrPortChange = true;
                  }
            }
         }

         if (listenAddressOrPortChange) {
            DomainMBean domainx = ManagementService.getRuntimeAccess(ConfiguredServersMonitorImpl.kernelId).getDomain();
            ServerMBean server = domainx.lookupServer(this.serverName);
            if (server.getCluster() != null && server.getCluster().getName().equals(ConfiguredServersMonitorImpl.this.clusterName)) {
               this.removeConfiguredServer(server, true);
               this.addConfiguredServer(server);
            }
         }

      }

      private void addConfiguredServer(ServerMBean server) {
         ConfiguredServersMonitorImpl.this.addServer(server);
         ServerConfigurationInformation info = ConfiguredServersMonitorImpl.this.createConfiguration(server);
         GroupManagerImpl.getInstance().addToGroup(info);
      }

      private void removeConfiguredServer(ServerMBean server, boolean unregisterListener) {
         ConfiguredServersMonitorImpl.this.removeServer(server, unregisterListener);
         GroupManagerImpl.getInstance().removeFromGroup(server.getName());
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static final class Factory {
      static final ConfiguredServersMonitor THE_ONE = new ConfiguredServersMonitorImpl();
   }
}
