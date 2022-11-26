package weblogic.server.channels;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.util.ReplicationPorts;
import weblogic.management.internal.ChannelImportExportService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.net.http.HttpURLConnection;
import weblogic.protocol.AdminServerIdentity;
import weblogic.protocol.ChannelList;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManager;
import weblogic.protocol.URLManagerService;
import weblogic.protocol.UnknownProtocolException;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.protocol.configuration.NetworkAccessPointDefaultMBean;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.ServerDisconnectEvent;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityManager;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.SSLContextManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerLogger;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.socket.ChannelSocketFactory;
import weblogic.socket.ServerThrottle;
import weblogic.t3.srvr.AdminPortLifeCycleService;
import weblogic.t3.srvr.ServerRuntime;
import weblogic.transaction.internal.PlatformHelper;
import weblogic.transaction.internal.ServerCoordinatorDescriptorManager;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;
import weblogic.utils.net.AddressUtils;
import weblogic.utils.net.InetAddressHelper;

@Service
@Named
@RunLevel(5)
public class ChannelService extends ServerChannelManager implements ServerService, BeanUpdateListener {
   @Inject
   @Named("ProtocolService")
   private ServerService dependencyOnProtocolService;
   @Inject
   @Named("ProtocolRegistrationService")
   private ServerService dependencyOnProtocolRegistrationService;
   @Inject
   @Named("RJVMService")
   private ServerService dependencyOnRJVMService;
   private static final int EXPECTED_SERVERS = 63;
   private static final int EXPECTED_CHANNELS = 31;
   private static final int EXPECTED_PROTOCOLS = 16;
   private static final int EXPECTED_PARTITIONS = 4;
   private static final int EXPECTED_GLOBAL_VTS = 4;
   private static Map channelMap = new ConcurrentHashMap(63);
   private static Map channelsByName = new ConcurrentHashMap(63);
   private static Map localChannels = new ConcurrentHashMap(31);
   private static Map localChannelsByProtocol = new ConcurrentHashMap(16);
   private static Map localChannelsByName = new ConcurrentHashMap(31);
   private static Map EMPTY_MAP = new ConcurrentHashMap(1);
   private static ConcurrentHashMap preferredChannelCache = new ConcurrentHashMap(31);
   private static boolean initialized = false;
   private static String outboundCheckIgnoredChannel = System.getProperty("weblogic.IgnoreOutboundEnabledCheckFor", "");
   private static boolean DEBUG_MAP = false;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static RuntimeAccess runtimeAccess;
   private long adminChannelCreationTime;
   private static List replicationChannelNames = new ArrayList();
   private static MyChannelsComparator channelsComparator = new MyChannelsComparator();
   private static Map localChannelsByPartitionName = new ConcurrentHashMap(4);
   private static Map localChannelsByGlobalVTName = new ConcurrentHashMap(4);
   private boolean inCreateChannels = false;
   private static final String[] protocols = new String[]{"t3", "iiop", "http"};
   private String defaultChannelName = null;

   public String getName() {
      return "ChannelService";
   }

   public String getVersion() {
      return null;
   }

   @PostConstruct
   public void start() throws ServiceFailureException {
      try {
         runtimeAccess = ManagementService.getRuntimeAccess(kernelId);

         try {
            ChannelHelper.checkConsistency(runtimeAccess.getServer(), runtimeAccess.getServer().getName());
         } catch (ConfigurationException var3) {
            throw new ServiceFailureException(var3.getMessage(), var3);
         }

         ChannelHelper.logChannelConfiguration(runtimeAccess.getServer());
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Admin protocol is: " + ProtocolManager.getRealProtocol(ChannelService.PROTOCOL.ADMIN).getAsURLPrefix());
         }

         ServerURL.setIsServer(true);
         this.initializeServerChannels();
         initialized = true;
         ServerChannelManager.setServerChannelManager(this);
         if (AdminServerIdentity.getBootstrapIdentity() != null) {
            boolean localHasAdmin = ServerChannelManager.findServerChannel(LocalServerIdentity.getIdentity(), ChannelService.PROTOCOL.ADMIN) != null;
            boolean remoteHasAdmin = ServerChannelManager.findServerChannel(AdminServerIdentity.getBootstrapIdentity(), ChannelService.PROTOCOL.ADMIN) != null;
            if (localHasAdmin && !remoteHasAdmin || remoteHasAdmin && !localHasAdmin) {
               ServerLogger.logAdminChannelConflict();
               throw new ServiceFailureException(ServerLogger.logAdminChannelConflictLoggable().getMessage());
            }
         }

         if (getURLManagerService().findAdministrationURL(LocalServerIdentity.getIdentity()) == null) {
            throw new ServiceFailureException(ServerLogger.getServerNoConfiguredChannelLoggable().getMessage());
         } else if (AdminServerIdentity.getBootstrapIdentity() != null && !AdminServerIdentity.getBootstrapIdentity().isLocal() && isLocalChannel(getURLManagerService().findAdministrationURL(AdminServerIdentity.getBootstrapIdentity()))) {
            throw new ServiceFailureException(ServerLogger.getServerAdministrationChannelConflictWithAdminserverLoggable().getMessage());
         } else {
            this.resetQOS();
            RemoteChannelServiceImpl.getInstance().addConnectDisconnectListener(new ConnectListener(), new DisconnectListener());
            this.injectChannelBasedSocketFactories();
            ServerLogger.logChannelsEnabled();
         }
      } catch (UnknownHostException var4) {
         throw new ServiceFailureException(ServerLogger.getServerInitializationFailedLoggable(var4.toString()).getMessage(), var4);
      }
   }

   private void injectChannelBasedSocketFactories() {
      ServerChannel sc = ChannelService.PROTOCOL.HTTP.getHandler().getDefaultServerChannel();
      if (sc != null) {
         HttpURLConnection.setDefaultSocketFactory(new ChannelSocketFactory(sc));
      }

   }

   private void resetQOS() {
      byte qosToSet;
      if (findLocalServerChannel(ChannelService.PROTOCOL.ADMIN) == null) {
         qosToSet = 101;
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Kernel and Server Identity is now QOS_ANY");
         }
      } else {
         qosToSet = 103;
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Kernel and Server Identity is now QOS_ADMIN");
         }
      }

      kernelId.setQOS(qosToSet);
      AuthenticatedSubject serverIdentity = RmiSecurityFacade.sendASToWire(kernelId);
      if (serverIdentity != null) {
         serverIdentity.setQOS(qosToSet);
      }

   }

   private void initializeServerChannels() throws UnknownHostException {
      Map myserverMap = this.createServerChannels(ManagementService.getRuntimeAccess(kernelId).getServer(), LocalServerIdentity.getIdentity(), localChannelsByProtocol, localChannelsByName);
      Iterator var2 = myserverMap.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry channel = (Map.Entry)var2.next();
         localChannels.put(channel.getKey(), channel.getValue());
      }

      channelMap.put(LocalServerIdentity.getIdentity(), localChannelsByProtocol);
      channelsByName.put(LocalServerIdentity.getIdentity(), localChannelsByName);
   }

   void createPartitionServerChannels(String partitionName) throws IOException {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Creating server channels for partition: " + partitionName);
      }

      Map myServerMap = this.createVirtualTargetChannels(partitionName, ManagementService.getRuntimeAccess(kernelId).getServer());
      this.putChannelListIntoLocalMap(myServerMap);
      localChannelsByPartitionName.put(partitionName, myServerMap);
   }

   private void putChannelListIntoLocalMap(Map myServerMap) throws IOException {
      Map.Entry channels;
      for(Iterator var2 = myServerMap.entrySet().iterator(); var2.hasNext(); localChannels.put(channels.getKey(), channels.getValue())) {
         channels = (Map.Entry)var2.next();
         ServerChannel channel = (ServerChannel)((List)channels.getValue()).get(0);
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Binding ServerSocket using channel: " + channel);
         }

         if (channel.getProtocol() == ChannelService.PROTOCOL.ADMIN) {
            this.getAdminPortLifeCycleService().addServerSocket((List)channels.getValue());
         } else {
            ChannelListenerManager.getInstance().addServerSocket((List)channels.getValue());
         }

         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Adding to localChannels; Key: " + (String)channels.getKey() + " Value: " + channels.getValue());
         }
      }

   }

   private AdminPortLifeCycleService getAdminPortLifeCycleService() {
      return (AdminPortLifeCycleService)GlobalServiceLocator.getServiceLocator().getService(AdminPortLifeCycleService.class, new Annotation[0]);
   }

   void removePartitionServerChannels(String partitionName, boolean force) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Stopping server channels for partition: " + partitionName + "; by force: " + force);
      }

      Map myServerMap = (Map)localChannelsByPartitionName.remove(partitionName);
      if (myServerMap != null) {
         Iterator var4 = myServerMap.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            if (ChannelHelper.DEBUG) {
               ChannelHelper.p("Name: " + (String)entry.getKey() + " ServerChannels: " + entry.getValue());
            }

            List channels = (List)entry.getValue();

            try {
               if (((ServerChannel)channels.get(0)).getProtocol() == ChannelService.PROTOCOL.ADMIN) {
                  this.getAdminPortLifeCycleService().closeServerSocket((ServerChannel)channels.get(0));
               } else {
                  ChannelListenerManager.getInstance().closeServerSocket((ServerChannel)channels.get(0));
               }
            } catch (IOException var9) {
            }

            Iterator var7 = channels.iterator();

            while(var7.hasNext()) {
               ServerChannel c = (ServerChannel)var7.next();
               this.removeLocalServerChannelMappings((ServerChannelImpl)c);
            }

            channels.clear();
         }

      }
   }

   void registerRuntimeService() throws ManagementException, RemoteException {
      ServerMBean serverMBean = runtimeAccess.getServer();
      ChannelValidationListener.registerChannelValidationLister(runtimeAccess, serverMBean.getName(), runtimeAccess.isAdminServer());
      SSLMBean ssl = serverMBean.getSSL();
      ssl.addBeanUpdateListener(this);
      serverMBean.addBeanUpdateListener(this);
      DomainMBean domain = runtimeAccess.getDomain();
      domain.addBeanUpdateListener(this);
      Iterator var4 = localChannelsByProtocol.values().iterator();

      while(var4.hasNext()) {
         List l = (List)var4.next();
         Iterator var6 = l.iterator();

         while(var6.hasNext()) {
            ServerChannel j = (ServerChannel)var6.next();
            ServerChannelImpl ch = (ServerChannelImpl)j;
            ((ServerRuntime)runtimeAccess.getServerRuntime()).addServerChannelRuntime(ch.createRuntime());
            if (ChannelHelper.DEBUG) {
               ChannelHelper.p("added runtime for: " + ch);
            }
         }
      }

      ServerHelper.exportObject((Remote)RemoteChannelServiceImpl.getInstance());
   }

   private Map findOrCreateChannels(ServerIdentity id) {
      if (id.isClient()) {
         return null;
      } else {
         Map map = (Map)channelMap.get(id);
         if (map == null && id.getDomainName().equals(LocalServerIdentity.getIdentity().getDomainName())) {
            this.createChannels(id);
            map = (Map)channelMap.get(id);
         }

         return map;
      }
   }

   private Map findOrCreateNamedChannels(ServerIdentity id) {
      if (id.isClient()) {
         return null;
      } else {
         Map map = (Map)channelsByName.get(id);
         if (map == null && id.getDomainName().equals(LocalServerIdentity.getIdentity().getDomainName())) {
            this.createChannels(id);
            map = (Map)channelsByName.get(id);
         }

         return map;
      }
   }

   private synchronized void createChannels(final ServerIdentity id) {
      if (!this.inCreateChannels) {
         try {
            this.inCreateChannels = true;
            SecurityManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
               public Object run() throws Exception {
                  RemoteChannelServiceImpl.retrieveRemoteChannels(id);
                  if (ChannelService.channelMap.get(id) != null) {
                     return null;
                  } else {
                     Map map = new ConcurrentHashMap(31);
                     Map nameMap = new ConcurrentHashMap(31);
                     ServerMBean server = ChannelService.this.getServer(id.getServerName());
                     if (server != null) {
                        ChannelService.this.createServerChannels(server, id, map, nameMap);
                        ChannelService.channelMap.put(id, map);
                        ChannelService.channelsByName.put(id, nameMap);
                     } else {
                        ChannelService.channelMap.put(id, ChannelService.EMPTY_MAP);
                        ChannelService.channelsByName.put(id, ChannelService.EMPTY_MAP);
                     }

                     return null;
                  }
               }
            });
         } catch (PrivilegedActionException var6) {
         } finally {
            this.inCreateChannels = false;
         }

      }
   }

   private ServerMBean getServer(String name) {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return domain.lookupServer(name);
   }

   private synchronized Map createServerChannels(ServerMBean server, ServerIdentity id, Map protocolMap, Map nameMap) throws UnknownHostException {
      NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();
      Map serverMap = new HashMap();

      for(int i = 0; i < naps.length; ++i) {
         NetworkAccessPointMBean napMBean = naps[i];
         if (id != null && id.isLocal()) {
            napMBean.addBeanUpdateListener(this);
         }

         if (napMBean.isEnabled()) {
            try {
               ServerChannelImpl channel = new ServerChannelImpl(napMBean, id);
               addServerChannel(channel, serverMap, protocolMap, nameMap);
            } catch (Exception var18) {
               ServerLogger.logChannelConfigurationFailed(napMBean.getName(), var18);
            }
         }
      }

      if (ChannelHelper.isMultipleReplicationChannelsConfigured(server)) {
         this.createAdditionalReplicationChannels(server, serverMap, protocolMap, nameMap);
      }

      Iterator i = ProtocolManager.iterator();

      while(true) {
         while(true) {
            Protocol p;
            do {
               do {
                  if (!i.hasNext()) {
                     List channels;
                     for(i = serverMap.entrySet().iterator(); i.hasNext(); Collections.sort(channels)) {
                        Map.Entry channel = (Map.Entry)i.next();
                        channels = (List)channel.getValue();
                        Collections.sort(channels);
                        boolean needsHttp = false;
                        boolean foundHttp = false;
                        boolean needsLdap = false;
                        boolean foundLdap = false;
                        boolean foundCluster = false;
                        Iterator var15 = channels.iterator();

                        while(true) {
                           while(var15.hasNext()) {
                              ServerChannel a = (ServerChannel)var15.next();
                              ServerChannelImpl sc = (ServerChannelImpl)a;
                              switch (sc.getProtocol().toByte()) {
                                 case 1:
                                 case 3:
                                    foundHttp = true;
                                    break;
                                 case 6:
                                    needsLdap = true;
                                    if (ProtocolManager.getRealProtocol(sc.getProtocol()) == ChannelService.PROTOCOL.HTTP || ProtocolManager.getRealProtocol(sc.getProtocol()) == ChannelService.PROTOCOL.HTTPS) {
                                       foundHttp = true;
                                    }
                                 case 2:
                                 case 4:
                                 case 5:
                                 case 7:
                                 case 8:
                                 case 9:
                                 default:
                                    if (sc.supportsHttp()) {
                                       needsHttp = true;
                                    }
                                    break;
                                 case 10:
                                 case 11:
                                    foundLdap = true;
                                    break;
                                 case 12:
                                 case 13:
                                    foundCluster = true;
                                    if (sc.supportsHttp()) {
                                       needsHttp = true;
                                    }
                              }
                           }

                           ServerChannelImpl sc;
                           if (needsLdap && !foundLdap) {
                              sc = (ServerChannelImpl)channels.get(0);
                              if (sc.supportsTLS()) {
                                 addServerChannel(sc.createImplicitChannel(ChannelService.PROTOCOL.LDAPS), serverMap, protocolMap, nameMap);
                              } else {
                                 addServerChannel(sc.createImplicitChannel(ChannelService.PROTOCOL.LDAP), serverMap, protocolMap, nameMap);
                              }
                           }

                           if (needsHttp && !foundHttp) {
                              sc = (ServerChannelImpl)channels.get(0);
                              if (sc.supportsTLS()) {
                                 addServerChannel(sc.createImplicitChannel(ChannelService.PROTOCOL.HTTPS), serverMap, protocolMap, nameMap);
                              } else {
                                 addServerChannel(sc.createImplicitChannel(ChannelService.PROTOCOL.HTTP), serverMap, protocolMap, nameMap);
                              }
                           }
                           break;
                        }
                     }

                     i = protocolMap.values().iterator();

                     List channels;
                     while(i.hasNext()) {
                        channels = (List)i.next();
                        Collections.sort(channels, channelsComparator);
                     }

                     i = nameMap.values().iterator();

                     while(i.hasNext()) {
                        channels = (List)i.next();
                        Collections.sort(channels);
                     }

                     return serverMap;
                  }

                  p = (Protocol)i.next();
               } while(!this.isClusterChannelsNeeded(p, server.getCluster()));
            } while(!p.isEnabled());

            if (id != null && id.isLocal()) {
               ServerChannel sc = p.getHandler().getDefaultServerChannel();
               if (sc != null && sc instanceof ServerChannelImpl) {
                  ServerChannelImpl sci = (ServerChannelImpl)sc;
                  if (sci.getConfig().isEnabled() && p.getHandler().getPriority() >= 0) {
                     addServerChannel(sci, serverMap, protocolMap, nameMap);
                  }
               }
            } else {
               NetworkAccessPointMBean nap = new NetworkAccessPointDefaultMBean(p, server);
               if (nap.isEnabled()) {
                  addServerChannel(new ServerChannelImpl(nap, p, id), serverMap, protocolMap, nameMap);
               }
            }
         }
      }
   }

   private Map createVirtualTargetChannels(String partitionName, ServerMBean server) throws UnknownHostException {
      Map serverMap = new HashMap();
      return serverMap;
   }

   public void createChannelsForGlobalVirtualTarget(VirtualTargetMBean target) throws IOException {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Creating server channels for Global Virtual Target: " + target.getName());
      }

      HashMap serverMap = new HashMap();
      this.addChannelsForVirtualTarget("domain", serverMap, target);
      this.putChannelListIntoLocalMap(serverMap);
      localChannelsByGlobalVTName.put(target.getName(), serverMap);
   }

   public void removeGlobalVirtualTargetServerChannels(String vtName, boolean force) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Stopping server channels for Global Virtual Target: " + vtName + "; by force: " + force);
      }

      Map myServerMap = (Map)localChannelsByGlobalVTName.remove(vtName);
      if (myServerMap != null) {
         Iterator var4 = myServerMap.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            if (ChannelHelper.DEBUG) {
               ChannelHelper.p("Name: " + (String)entry.getKey() + " ServerChannels: " + entry.getValue());
            }

            List channels = (List)entry.getValue();

            try {
               if (((ServerChannel)channels.get(0)).getProtocol() == ChannelService.PROTOCOL.ADMIN) {
                  this.getAdminPortLifeCycleService().closeServerSocket((ServerChannel)channels.get(0));
               } else {
                  ChannelListenerManager.getInstance().closeServerSocket((ServerChannel)channels.get(0));
               }
            } catch (IOException var9) {
            }

            Iterator var7 = channels.iterator();

            while(var7.hasNext()) {
               ServerChannel c = (ServerChannel)var7.next();
               this.removeLocalServerChannelMappings((ServerChannelImpl)c);
            }

            channels.clear();
         }

      }
   }

   private void addChannelsForVirtualTarget(String partitionName, Map serverMap, VirtualTargetMBean target) throws UnknownHostException {
      int portOffset = target.getPortOffset();
      int explicitPort = target.getExplicitPort();
      if (explicitPort == 0 && portOffset == 0) {
         if (target.getUriPrefix() == null) {
            ServerLogger.warnServerResourceUsedAsPartitionResource(partitionName, target.getPartitionChannel());
         }
      } else {
         ServerChannelImpl[] channels = this.createChannelsForVirtualTarget(partitionName, target, localChannelsByName);
         ServerChannelImpl[] var8 = channels;
         int var9 = channels.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ServerChannelImpl sc = var8[var10];
            addServerChannel(sc, serverMap, localChannelsByProtocol, localChannelsByName);

            try {
               ((ServerRuntime)runtimeAccess.getServerRuntime()).addServerChannelRuntime(sc.createRuntime());
               if (ChannelHelper.DEBUG) {
                  ChannelHelper.p("added runtime for: " + sc);
               }
            } catch (ManagementException var13) {
            }
         }
      }

   }

   private ServerChannelImpl[] createChannelsForVirtualTarget(String pName, VirtualTargetMBean vt, Map nameMap) {
      String partitionChName = vt.getPartitionChannel();
      int portOffset = vt.getPortOffset();
      int explicitPort = vt.getExplicitPort();
      ServerChannelImpl channel = this.findRequestedChannelOtherwiseReturnDefaultChannel(nameMap, partitionChName);
      int port = explicitPort > 0 ? explicitPort : channel.getPort() + portOffset;
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Creating ServerChannel for Virtual Target: " + vt.getName() + " with Port: " + port + "; {Reference ServerChannel: " + channel + "; PortOffset: " + portOffset + "; ExplicitPort: " + explicitPort + '}');
      }

      ServerChannelImpl[] channels = new ServerChannelImpl[protocols.length];

      for(int i = 0; i < protocols.length; ++i) {
         ServerChannelImpl sc = channel.createImplicitChannel(ProtocolManager.getProtocolByName(protocols[i]));
         sc.implicitChannel = false;
         sc.displayName = pName + '-' + vt.getName();
         sc.associatedVirtualTargetName = vt.getName();
         sc.channelName = pName + '-' + vt.getName() + '[' + protocols[i] + ']';
         sc.listenPort = port;
         if (sc.publicPort > -1) {
            sc.publicPort = sc.listenPort;
         }

         channels[i] = sc;
      }

      return channels;
   }

   private ServerChannelImpl findRequestedChannelOtherwiseReturnDefaultChannel(Map nameMap, String channelName) {
      List channelList = (List)nameMap.get(channelName);
      ServerChannelImpl channel = null;
      if (channelList != null) {
         channel = (ServerChannelImpl)channelList.get(0);
      }

      if (channel == null) {
         String defChannelName = this.getDefaultChannelName();
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Channel: '" + channelName + "' not found; Using default channel: '" + defChannelName + '\'');
         }

         channelList = (List)nameMap.get(defChannelName);
         channel = (ServerChannelImpl)channelList.get(0);
      }

      return channel;
   }

   private String getDefaultChannelName() {
      if (this.defaultChannelName == null) {
         Protocol p = ProtocolManager.getDefaultProtocol();
         if (!p.isEnabled()) {
            p = ProtocolManager.getDefaultSecureProtocol();
            if (!p.isEnabled()) {
               p = ProtocolManager.getDefaultAdminProtocol();
            }
         }

         this.defaultChannelName = "Default[" + p.getProtocolName() + ']';
      }

      return this.defaultChannelName;
   }

   private void createAdditionalReplicationChannels(ServerMBean srvr, Map serverMap, Map protocolMap, Map nameMap) throws UnknownHostException {
      ClusterMBean cluster = srvr.getCluster();
      String replicationChannel = cluster.getReplicationChannel();
      List channelList = (List)nameMap.get(replicationChannel);
      ServerChannelImpl repChannel = (ServerChannelImpl)channelList.get(0);
      boolean oneWayRMIEnabled = cluster.isOneWayRmiForReplicationEnabled();

      try {
         int[] replicationPorts = parseReplicationPorts(srvr.getReplicationPorts());
         int numReplicationChannels = replicationPorts.length;

         for(int i = 0; i < numReplicationChannels; ++i) {
            ServerChannelImpl sc = repChannel.cloneChannel(String.valueOf(i + 1));
            sc.listenPort = replicationPorts[i];
            if (sc.publicPort > -1) {
               sc.publicPort = sc.listenPort;
            }

            if (oneWayRMIEnabled) {
               sc.setT3SenderQueueDisabled(true);
            }

            replicationChannelNames.add(sc.getChannelName());
            addServerChannel(sc, serverMap, protocolMap, nameMap);
         }
      } catch (NumberFormatException var14) {
         ServerLogger.logChannelConfigurationFailed(replicationChannel, new Exception(ServerLogger.getFailedToParseReplicationPortsLoggable(srvr.getName(), srvr.getReplicationPorts()).getMessage()));
      }

   }

   public static int[] parseReplicationPorts(String ports) throws NumberFormatException {
      return ReplicationPorts.parseReplicationPorts(ports);
   }

   public static List getReplicationChannelNames() {
      return replicationChannelNames;
   }

   private static void addServerChannel(ServerChannelImpl channel, Map serverMap, Map protocolMap, Map nameMap) throws UnknownHostException {
      if (channel.getConfig() != null && !channel.getConfig().isEnabled()) {
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("channel " + channel.toString() + " is not enabled");
         }

      } else if (channel.isLocal() && channel.getAddress() == null) {
         addServerChannel(new ServerChannelImpl(channel, AddressUtils.getIPAny(0, resolveDNS()), ""), serverMap, protocolMap, nameMap);

         for(int i = 1; i < AddressUtils.getIPAny().length; ++i) {
            addServerChannel(new ServerChannelImpl(channel, AddressUtils.getIPAny(i, resolveDNS()), "[" + i + "]"), serverMap, protocolMap, nameMap);
         }

      } else {
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("adding " + channel.toString());
         }

         String key = channel.getListenerKey();
         Object channels;
         if (serverMap != null) {
            channels = (List)serverMap.get(key);
            if (channels == null) {
               channels = new ArrayList();
               serverMap.put(key, channels);
            }

            ((List)channels).add(channel);
         }

         channels = (List)protocolMap.get(channel.getProtocol());
         if (channels == null) {
            channels = new ArrayList();
            protocolMap.put(channel.getProtocol(), channels);
         }

         ((List)channels).add(channel);
         channels = (List)nameMap.get(channel.getChannelName());
         if (channels == null) {
            channels = new ArrayList();
            nameMap.put(channel.getChannelName(), channels);
         }

         ((List)channels).add(channel);
      }
   }

   private ServerChannelImpl[] expandServerChannels(ServerIdentity id, ServerChannelImpl channel) throws IOException {
      if (id.isLocal() && channel.getAddress() == null) {
         ServerChannelImpl[] ret = new ServerChannelImpl[AddressUtils.getIPAny().length];
         ret[0] = new ServerChannelImpl(channel, AddressUtils.getIPAny(0, resolveDNS()), "");

         for(int i = 1; i < AddressUtils.getIPAny().length; ++i) {
            ret[i] = new ServerChannelImpl(channel, AddressUtils.getIPAny(i, resolveDNS()), "[" + i + "]");
         }

         return ret;
      } else {
         return new ServerChannelImpl[]{channel};
      }
   }

   private ServerChannelImpl[] expandServerChannels(ServerIdentity id, NetworkAccessPointMBean nap) throws IOException, UnknownProtocolException {
      return this.expandServerChannels(id, new ServerChannelImpl(nap, id));
   }

   private static boolean resolveDNS() {
      return runtimeAccess.getServer().isReverseDNSAllowed();
   }

   private void addLocalServerChannels(ServerChannelImpl channel) throws IOException, ManagementException {
      ServerChannelImpl[] channels = this.expandServerChannels(LocalServerIdentity.getIdentity(), channel);

      for(int i = 0; i < channels.length; ++i) {
         this.addLocalServerChannel(channels[i]);
      }

   }

   private List addLocalServerChannel(ServerChannelImpl channel) throws IOException, ManagementException {
      ServerIdentity id = LocalServerIdentity.getIdentity();
      channel.update(id);
      List channels = getDiscriminantSet(channel.getListenerKey());
      NetworkAccessPointMBean nap = channel.getConfig();
      boolean needsLdap = channel.getProtocol() == ChannelService.PROTOCOL.ADMIN;
      boolean needsHttp = (nap.isHttpEnabledForThisProtocol() || nap.isTunnelingEnabled()) && (needsLdap || !(nap instanceof NetworkAccessPointDefaultMBean)) && ProtocolManager.getRealProtocol(channel.getProtocol()) != ChannelService.PROTOCOL.HTTP && ProtocolManager.getRealProtocol(channel.getProtocol()) != ChannelService.PROTOCOL.HTTPS;
      boolean needsChannel = nap.isEnabled();
      if (channels != null) {
         Iterator i = channels.iterator();

         label67:
         while(true) {
            while(true) {
               if (!i.hasNext()) {
                  break label67;
               }

               ServerChannelImpl sc = (ServerChannelImpl)i.next();
               if (sc.getProtocol() == channel.getProtocol()) {
                  sc.update(id);
                  needsChannel = false;
               } else if (sc.getProtocol() != ChannelService.PROTOCOL.HTTP && sc.getProtocol() != ChannelService.PROTOCOL.HTTPS) {
                  if (sc.getProtocol() == ChannelService.PROTOCOL.LDAP || sc.getProtocol() == ChannelService.PROTOCOL.LDAPS) {
                     needsLdap = false;
                  }
               } else if (!channel.supportsHttp() && sc.isImplicitChannel()) {
                  i.remove();
                  getServerRuntime().removeServerChannelRuntime(sc.deleteRuntime());
               } else {
                  needsHttp = false;
               }
            }
         }
      }

      if (needsChannel) {
         addServerChannel(channel, localChannels, localChannelsByProtocol, localChannelsByName);
         getServerRuntime().addServerChannelRuntime(channel.createRuntime());
      }

      ServerChannelImpl sc;
      if (needsLdap) {
         if (channel.supportsTLS()) {
            sc = channel.createImplicitChannel(ChannelService.PROTOCOL.LDAPS);
            addServerChannel(sc, localChannels, localChannelsByProtocol, localChannelsByName);
            getServerRuntime().addServerChannelRuntime(sc.createRuntime());
         } else {
            sc = channel.createImplicitChannel(ChannelService.PROTOCOL.LDAP);
            addServerChannel(sc, localChannels, localChannelsByProtocol, localChannelsByName);
            getServerRuntime().addServerChannelRuntime(sc.createRuntime());
         }
      }

      if (needsHttp) {
         if (channel.supportsTLS()) {
            sc = channel.createImplicitChannel(ChannelService.PROTOCOL.HTTPS);
            addServerChannel(sc, localChannels, localChannelsByProtocol, localChannelsByName);
            getServerRuntime().addServerChannelRuntime(sc.createRuntime());
         } else {
            sc = channel.createImplicitChannel(ChannelService.PROTOCOL.HTTP);
            addServerChannel(sc, localChannels, localChannelsByProtocol, localChannelsByName);
            getServerRuntime().addServerChannelRuntime(sc.createRuntime());
         }
      }

      return getDiscriminantSet(channel.getListenerKey());
   }

   private synchronized void updateLocalServerChannel(NetworkAccessPointMBean nap) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("updateLocalServerChannel(" + nap + ")");
      }

      try {
         ServerChannelImpl[] channel = this.expandServerChannels(LocalServerIdentity.getIdentity(), nap);

         for(int i = 0; i < channel.length; ++i) {
            this.addLocalServerChannel(channel[i]);
            if (nap.getProtocol().equalsIgnoreCase("ADMIN")) {
               this.adminChannelCreationTime = System.currentTimeMillis();
               this.getAdminPortLifeCycleService().updateServerSocket(channel[i]);
            } else {
               ChannelListenerManager.getInstance().updateServerSocket(channel[i]);
            }
         }
      } catch (Exception var4) {
         ServerLogger.logChannelConfigurationFailed(nap.getName(), var4);
      }

   }

   private synchronized void removeLocalServerChannel(NetworkAccessPointMBean nap) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("removeLocalServerChannel(" + nap + ")");
      }

      try {
         ServerIdentity id = LocalServerIdentity.getIdentity();
         ServerChannelImpl[] channel = this.expandServerChannels(id, nap);

         label82:
         for(int i = 0; i < channel.length; ++i) {
            List channels = getDiscriminantSet(channel[i].getListenerKey());
            if (channels != null) {
               if (channel[i].getProtocol() == ChannelService.PROTOCOL.ADMIN) {
                  Iterator var12 = channels.iterator();

                  while(var12.hasNext()) {
                     ServerChannel c = (ServerChannel)var12.next();
                     this.removeLocalServerChannelMappings((ServerChannelImpl)c);
                  }

                  channels.clear();
                  this.getAdminPortLifeCycleService().closeServerSocket(channel[i]);
               } else {
                  boolean hasHttp = false;
                  ServerChannelImpl httpChannel = null;
                  Iterator iter = channels.iterator();

                  while(true) {
                     while(true) {
                        while(iter.hasNext()) {
                           ServerChannelImpl sc = (ServerChannelImpl)iter.next();
                           if (!sc.equals(channel[i]) && !sc.isImplicitChannel()) {
                              if (sc.getProtocol() != ChannelService.PROTOCOL.HTTP && sc.getProtocol() != ChannelService.PROTOCOL.HTTPS) {
                                 if (sc.supportsHttp()) {
                                    httpChannel = sc;
                                 }
                              } else {
                                 hasHttp = true;
                              }
                           } else {
                              iter.remove();
                              this.removeLocalServerChannelMappings(sc);
                           }
                        }

                        if (httpChannel != null && !hasHttp) {
                           ServerChannelImpl httpch;
                           if (channel[i].supportsTLS()) {
                              httpch = httpChannel.createImplicitChannel(ChannelService.PROTOCOL.HTTPS);
                           } else {
                              httpch = httpChannel.createImplicitChannel(ChannelService.PROTOCOL.HTTP);
                           }

                           channels.add(httpch);

                           try {
                              getServerRuntime().addServerChannelRuntime(httpch.createRuntime());
                           } catch (ManagementException var10) {
                              ServerLogger.logChannelConfigurationFailed(httpch.getChannelName(), var10);
                           }
                        }

                        ChannelListenerManager.getInstance().updateServerSocket(channel[i]);
                        continue label82;
                     }
                  }
               }
            }
         }
      } catch (Exception var11) {
         ServerLogger.logChannelConfigurationFailed(nap.getName(), var11);
      }

   }

   private synchronized void removeDefaultServerChannel(ServerChannelImpl channel) {
      List channels = (List)localChannels.remove(channel.getListenerKey());
      if (ChannelHelper.DEBUG && channels == null) {
         ChannelHelper.p("Could not remove: " + channel);
      }

      if (channels != null) {
         Iterator var3 = channels.iterator();

         while(var3.hasNext()) {
            ServerChannel sc = (ServerChannel)var3.next();
            this.removeLocalServerChannelMappings((ServerChannelImpl)sc);
         }
      }

   }

   private void removeLocalServerChannelMappings(ServerChannelImpl channel) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("removing " + channel.toString());
      }

      List o = (List)localChannelsByName.remove(channel.getChannelName());
      if (ChannelHelper.DEBUG && o == null) {
         ChannelHelper.p("Could not remove: " + channel);
      }

      if (channel.getRuntime() != null) {
         getServerRuntime().removeServerChannelRuntime(channel.deleteRuntime());
      }

      List channels = (List)localChannelsByProtocol.get(channel.getProtocol());
      if (channels != null) {
         channels.remove(channel);
      }

      this.removePreferredChannel(channel);
   }

   private void restartChannelSet(ServerChannelImpl[] channels) throws IOException {
      for(int i = 0; i < channels.length; ++i) {
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("restartChannelSet(" + channels[i] + ")");
         }

         ChannelListenerManager.getInstance().updateServerSocket(channels[i]);
      }

   }

   private synchronized void updateDefaultChannels(boolean secure, ClusterMBean cluster) {
      try {
         this.stopDefaultChannels(secure);
         ArrayList localServerChannels = new ArrayList();
         ServerChannelImpl[] channels = this.updateAndGetDefaultChannels(secure, localServerChannels, cluster);
         if (channels != null && channels.length > 0) {
            if (this.isChannelListenKeyInUse(channels[0], !secure)) {
               this.stopDefaultChannels(!secure);
               this.updateAndGetDefaultChannels(!secure, (ArrayList)null, cluster);
            }

            this.addLocalServerChannels(localServerChannels);
            this.restartChannelSet(channels);
         }
      } catch (IOException var5) {
         ServerLogger.logProtocolNotConfigured(var5.getMessage());
      }

   }

   private boolean isChannelListenKeyInUse(ServerChannel channel, boolean secure) {
      if (channel != null) {
         List channelsArray = getDiscriminantSet(channel.getListenerKey());
         if (channelsArray != null) {
            ServerChannel[] channels = (ServerChannel[])channelsArray.toArray(new ServerChannel[0]);
            ServerChannel[] var5 = channels;
            int var6 = channels.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ServerChannel ch = var5[var7];
               if (ch != null && ch.supportsTLS() == secure) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private ServerChannelImpl[] updateAndGetDefaultChannels(boolean secure, ArrayList localServerChannels, ClusterMBean cluster) throws IOException {
      ServerChannelImpl[] channels = null;
      Iterator iter = ProtocolManager.iterator();

      while(true) {
         do {
            Protocol p;
            ServerChannelImpl sc;
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              do {
                                 if (!iter.hasNext()) {
                                    return channels;
                                 }

                                 p = (Protocol)iter.next();
                              } while(!this.isClusterChannelsNeeded(p, cluster));
                           } while(!p.isEnabled());
                        } while(p.isSecure() != secure);
                     } while(p.getQOS() == 103);

                     sc = (ServerChannelImpl)p.getHandler().getDefaultServerChannel();
                     if (ChannelHelper.DEBUG) {
                        ChannelHelper.p("updateAndGetDefaultChannels(" + p + ")");
                     }
                  } while(sc == null);
               } while(!sc.getConfig().isEnabled());
            } while(p.getHandler().getPriority() < 0);

            sc.update(LocalServerIdentity.getIdentity());
            channels = this.expandServerChannels(LocalServerIdentity.getIdentity(), sc);
         } while(localServerChannels == null);

         ServerChannelImpl[] var8 = channels;
         int var9 = channels.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ServerChannel ch = var8[var10];
            localServerChannels.add(ch);
         }
      }
   }

   private void addLocalServerChannels(ArrayList channels) {
      Iterator iter = channels.iterator();

      while(iter.hasNext()) {
         ServerChannelImpl channel = (ServerChannelImpl)iter.next();

         try {
            this.addLocalServerChannel(channel);
         } catch (Exception var5) {
            ServerLogger.logChannelConfigurationFailed(channel.getChannelName(), var5);
         }
      }

   }

   private synchronized void stopDefaultChannels(boolean secure) {
      try {
         Iterator i = ProtocolManager.iterator();

         while(true) {
            Protocol p;
            ServerChannel sc;
            do {
               do {
                  do {
                     do {
                        do {
                           if (!i.hasNext()) {
                              return;
                           }

                           p = (Protocol)i.next();
                        } while(!p.isEnabled());
                     } while(p.isSecure() != secure);
                  } while(p.getQOS() == 103);

                  sc = p.getHandler().getDefaultServerChannel();
                  if (ChannelHelper.DEBUG) {
                     ChannelHelper.p("stopDefaultChannels(" + p + ")");
                  }
               } while(p.getHandler().getPriority() < 0);
            } while(sc == null);

            ServerChannelImpl[] channels = this.expandServerChannels(LocalServerIdentity.getIdentity(), (ServerChannelImpl)sc);
            ServerChannelImpl[] var6 = channels;
            int var7 = channels.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ServerChannelImpl ch = var6[var8];
               ChannelListenerManager.getInstance().closeServerSocket(ch);
               this.removeDefaultServerChannel(ch);
            }
         }
      } catch (UnknownHostException var10) {
         ServerLogger.logProtocolNotConfigured(var10.getMessage());
      } catch (IOException var11) {
         ServerLogger.logProtocolNotConfigured(var11.getMessage());
      }

   }

   private synchronized void startDefaultAdminChannel() {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("startDefaultAdminChannel()");
      }

      ServerChannelImpl sc = (ServerChannelImpl)ProtocolHandlerAdmin.getProtocolHandler().getDefaultServerChannel();

      try {
         sc.update(LocalServerIdentity.getIdentity());
         this.addLocalServerChannels(sc);
         EnableAdminListenersService.getInstance().start();
      } catch (Exception var3) {
         ServerLogger.logChannelConfigurationFailed(sc.getChannelName(), var3);
      }

      this.resetQOS();
      this.adminChannelCreationTime = System.currentTimeMillis();
   }

   private synchronized void stopDefaultAdminChannel() {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("stopDefaultAdminChannel()");
      }

      this.getAdminPortLifeCycleService().halt();
      ServerChannelImpl sc = (ServerChannelImpl)ProtocolHandlerAdmin.getProtocolHandler().getDefaultServerChannel();

      try {
         ServerChannelImpl[] channels = this.expandServerChannels(LocalServerIdentity.getIdentity(), sc);

         for(int i = 0; i < channels.length; ++i) {
            this.removeDefaultServerChannel(channels[i]);
         }

         sc.update(LocalServerIdentity.getIdentity());
      } catch (Exception var4) {
         ServerLogger.logChannelConfigurationFailed(sc.getChannelName(), var4);
      }

      this.resetQOS();
      this.adminChannelCreationTime = 0L;
   }

   public static Map getLocalServerChannels() {
      return localChannels;
   }

   public static void exportServerChannels(ServerIdentity id, ObjectOutput out) throws IOException {
      boolean isLocalServer = LocalServerIdentity.getIdentity().equals(id);
      List exportList = new ArrayList();
      Map map = (Map)channelMap.get(id);
      Iterator var5;
      if (map != null) {
         var5 = map.values().iterator();

         label41:
         while(true) {
            while(true) {
               if (!var5.hasNext()) {
                  break label41;
               }

               List arr = (List)var5.next();
               if (isLocalServer) {
                  Iterator var7 = arr.iterator();

                  while(var7.hasNext()) {
                     ServerChannel channel = (ServerChannel)var7.next();
                     ServerChannelImpl ch = (ServerChannelImpl)channel;
                     if (ch.isValid()) {
                        exportList.add(ch);
                     }
                  }
               } else {
                  exportList.addAll(arr);
               }
            }
         }
      }

      out.writeInt(exportList.size());
      var5 = exportList.iterator();

      while(var5.hasNext()) {
         ServerChannel sc = (ServerChannel)var5.next();
         out.writeObject(sc);
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("Exported: " + sc);
         }
      }

   }

   public static boolean hasChannels(ServerIdentity id) {
      Map map = (Map)channelMap.get(id);
      return map != null && !map.isEmpty();
   }

   public static void importServerChannels(ServerIdentity id, ObjectInput in) throws IOException {
      if (!id.isLocal() && !LocalServerIdentity.getIdentity().getPersistentIdentity().equals(id.getPersistentIdentity())) {
         importNonLocalServerChannels(id, in);
      } else {
         int n = in.readInt();

         while(n-- > 0) {
            try {
               in.readObject();
            } catch (ClassNotFoundException var4) {
            }
         }
      }

   }

   public static void importNonLocalServerChannels(ServerIdentity id, ObjectInput in) throws IOException {
      int n = in.readInt();
      Map cbp = new ConcurrentHashMap(31);
      Map cbn = new ConcurrentHashMap(31);

      for(int i = 0; i < n; ++i) {
         try {
            addServerChannel((ServerChannelImpl)in.readObject(), (Map)null, cbp, cbn);
         } catch (ClassNotFoundException var7) {
         }
      }

      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("Imported: " + id + ":\n" + cbp.toString());
         ChannelHelper.p("Imported: " + id + ":\n" + cbn.toString());
      }

      channelMap.put(id, cbp);
      channelsByName.put(id, cbn);
      ServerIdentityManager.recordIdentity(id);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static void updateConnectedServers() {
      ServiceLocator gsl = GlobalServiceLocator.getServiceLocator();
      String[] servers = URLManager.getConnectedServers();

      for(int i = 0; i < servers.length; ++i) {
         if (!servers[i].equals(AdminServerIdentity.getIdentity().getServerName()) && !servers[i].equals(LocalServerIdentity.getIdentity().getServerName())) {
            try {
               String url = getURLManagerService().findAdministrationURL(servers[i]);
               if (url != null) {
                  ServerEnvironment env = (ServerEnvironment)gsl.getService(ServerEnvironment.class, new Annotation[0]);
                  env.setProviderUrl(url);
                  RemoteChannelService remote = (RemoteChannelService)PortableRemoteObject.narrow(env.getInitialReference(RemoteChannelServiceImpl.class), RemoteChannelService.class);
                  remote.updateServer(LocalServerIdentity.getIdentity().getServerName(), ServerChannelManager.getLocalChannelsForExport());
               }
            } catch (RemoteException var6) {
               ServerLogger.logServerUpdateFailed(servers[i]);
            } catch (NamingException var7) {
               ServerLogger.logServerUpdateFailed(servers[i]);
            }
         }
      }

   }

   protected ChannelList getLocalChannelList() {
      return new ChannelListImpl();
   }

   public static List getDiscriminantSet(String key) {
      return (List)localChannels.get(key);
   }

   public static boolean isLocalChannel(InetAddress address, int port) {
      String hostAddress = address.getHostAddress();
      if (InetAddressHelper.isIPV6Address(hostAddress)) {
         hostAddress = "[" + hostAddress + "]";
      }

      boolean ret = localChannels.get(address.getHostName().toLowerCase() + port) != null || localChannels.get(hostAddress + port) != null;
      if (!ret && address.isLoopbackAddress() && !AddressUtils.getIPForLocalHost().isLoopbackAddress()) {
         return isLocalChannel(AddressUtils.getIPForLocalHost(), port);
      } else {
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("isLocalChannel(" + address + ", " + port + "): " + ret);
         }

         return ret;
      }
   }

   public static boolean isLocalChannel(String url) {
      try {
         URI uri = new URI(url);
         return isLocalChannel(InetAddress.getByName(uri.getHost()), uri.getPort());
      } catch (URISyntaxException var2) {
         return false;
      } catch (UnknownHostException var3) {
         return false;
      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      Set updateSet = new HashSet();
      BeanUpdateEvent.PropertyUpdate[] changes;
      BeanUpdateEvent.PropertyUpdate[] changes;
      int var5;
      int var6;
      BeanUpdateEvent.PropertyUpdate change;
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("activateUpdate() " + event.getSourceBean() + ", event dump:");
         changes = event.getUpdateList();
         changes = changes;
         var5 = changes.length;

         for(var6 = 0; var6 < var5; ++var6) {
            change = changes[var6];
            ChannelHelper.p(" " + change.getPropertyName());
         }
      }

      String attName;
      if (event.getSourceBean() instanceof ServerMBean) {
         changes = event.getUpdateList();
         changes = changes;
         var5 = changes.length;

         for(var6 = 0; var6 < var5; ++var6) {
            change = changes[var6];
            NetworkAccessPointMBean nap;
            if (change.getAddedObject() instanceof NetworkAccessPointMBean) {
               nap = (NetworkAccessPointMBean)change.getAddedObject();
               if (ChannelHelper.DEBUG) {
                  ChannelHelper.p("adding: " + nap);
               }

               nap.addBeanUpdateListener(this);
               if (nap.isEnabled()) {
                  updateSet.add(nap);
               }
            } else if (change.getRemovedObject() instanceof NetworkAccessPointMBean) {
               nap = (NetworkAccessPointMBean)change.getRemovedObject();
               if (ChannelHelper.DEBUG) {
                  ChannelHelper.p("remove: " + nap);
               }

               this.removeLocalServerChannel(nap);
               updateSet.add(new Object());
            } else {
               attName = change.getPropertyName();
               if (!attName.equals("KeyStores") && !attName.equals("CustomIdentityKeyStoreFileName") && !attName.equals("CustomIdentityKeyStoreType") && !attName.equals("CustomIdentityKeyStorePassPhrase") && !attName.equals("CustomTrustKeyStoreFileName") && !attName.equals("CustomTrustKeyStoreType") && !attName.equals("CustomTrustKeyStorePassPhrase") && !attName.equals("JavaStandardTrustKeyStorePassPhrase")) {
                  if (attName.equals("ListenPortEnabled") && !((ServerMBean)event.getSourceBean()).isListenPortEnabled()) {
                     this.stopDefaultChannels(false);
                     updateSet.add(new Object());
                  } else if (!attName.equals("ListenPortEnabled") && !attName.equals("ListenPort")) {
                     if (attName.equals("ListenAddress")) {
                        updateSet.add(event.getSourceBean());
                        updateSet.add(((ServerMBean)event.getSourceBean()).getSSL());
                     } else if (attName.equals("AdministrationPort")) {
                        updateSet.add(event.getSourceBean());
                     } else if (attName.equals("MaxOpenSockCount")) {
                        ServerThrottle.getServerThrottle().changeMaxOpenSockets(((ServerMBean)event.getSourceBean()).getMaxOpenSockCount());
                     }
                  } else {
                     updateSet.add(event.getSourceBean());
                  }
               } else {
                  updateSet.add(((ServerMBean)event.getSourceBean()).getSSL());
               }
            }
         }
      } else if (event.getSourceBean() instanceof NetworkAccessPointMBean) {
         NetworkAccessPointMBean nap = (NetworkAccessPointMBean)event.getSourceBean();
         if (ChannelHelper.DEBUG) {
            ChannelHelper.p("updating: " + nap);
         }

         changes = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var13 = changes;
         var6 = changes.length;

         for(int var15 = 0; var15 < var6; ++var15) {
            BeanUpdateEvent.PropertyUpdate change = var13[var15];
            String attName = change.getPropertyName();
            if (attName.equals("Enabled")) {
               if (!nap.isEnabled()) {
                  this.removeLocalServerChannel(nap);
                  updateSet.remove(nap);
               } else {
                  updateSet.add(nap);
               }
               break;
            }

            if (attName.equals("AcceptBacklog") || attName.equals("HttpEnabledForThisProtocol") || attName.equals("TunnelingEnabled") || attName.equals("OutboundEnabled") || attName.equals("TwoWaySSLEnabled") || attName.equals("PrivateKeyAlias") || attName.equals("PrivateKeyPassPhrase") || attName.equals("ClientCertificateEnforced") || attName.equals("CustomPrivateKeyPassPhraseEncrypted") || attName.equals("CustomIdentityKeyStorePassPhraseEncrypted") || attName.equals("CustomIdentityKeyStoreFileName") || attName.equals("CustomIdentityKeyStoreType") || attName.equals("CustomPrivateKeyAlias") || attName.equals("LoginTimeoutMillisSSL")) {
               updateSet.add(nap);
            }
         }
      } else if (event.getSourceBean() instanceof SSLMBean) {
         if (!((SSLMBean)event.getSourceBean()).isEnabled()) {
            this.stopDefaultChannels(true);
         } else {
            updateSet.add(event.getSourceBean());
         }
      } else if (event.getSourceBean() instanceof DomainMBean) {
         changes = event.getUpdateList();
         changes = changes;
         var5 = changes.length;

         for(var6 = 0; var6 < var5; ++var6) {
            change = changes[var6];
            attName = change.getPropertyName();
            if (attName.equals("AdministrationPortEnabled") || attName.equals("AdministrationPort")) {
               updateSet.add(event.getSourceBean());
            }
         }
      }

      if (updateSet.size() > 0) {
         SSLContextManager.clearSSLContextCache();
         Iterator var11 = updateSet.iterator();

         while(var11.hasNext()) {
            Object o = var11.next();
            if (o instanceof NetworkAccessPointMBean) {
               this.updateLocalServerChannel((NetworkAccessPointMBean)o);
            } else if (o instanceof ServerMBean) {
               this.updateDefaultChannels(false, ((ServerMBean)o).getCluster());
            } else if (o instanceof SSLMBean) {
               this.updateDefaultChannels(true, ((ServerMBean)((SSLMBean)o).getParent()).getCluster());
               ServerCoordinatorDescriptorManager svrCoDesMgr = (ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager();
               if (svrCoDesMgr != null) {
                  svrCoDesMgr.setOnlySSLCoordinatorURL();
               }
            } else if (o instanceof DomainMBean) {
               this.stopDefaultAdminChannel();
               if (((DomainMBean)o).isAdministrationPortEnabled()) {
                  this.startDefaultAdminChannel();
               }
            }
         }

         updateOthers();
      }

   }

   static void updateOthers() {
      try {
         if (!AdminServerIdentity.getIdentity().isLocal()) {
            RemoteChannelServiceImpl.getDomainGateway().updateServer(LocalServerIdentity.getIdentity().getServerName(), ServerChannelManager.getLocalChannelsForExport());
         }
      } catch (RemoteException var1) {
         ServerLogger.logServerUpdateFailed(AdminServerIdentity.getIdentity().getServerName());
      }

      ClusterServices clusterServices = Locator.locateClusterServices();
      if (clusterServices != null) {
         clusterServices.resendLocalAttributes();
      } else {
         updateConnectedServers();
      }

   }

   public void restartSSLChannels() {
      SSLContextManager.clearSSLContextCache();
      ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      if (ChannelHelper.isAdminChannelEnabled(serverMBean)) {
         this.stopDefaultAdminChannel();
      }

      this.updateDefaultChannels(true, serverMBean.getCluster());
      if (ChannelHelper.isAdminChannelEnabled(serverMBean)) {
         this.startDefaultAdminChannel();
      }

      NetworkAccessPointMBean[] naps = serverMBean.getNetworkAccessPoints();

      for(int i = 0; i < naps.length; ++i) {
         NetworkAccessPointMBean nap = naps[i];
         byte qos = ProtocolManager.getProtocolByName(nap.getProtocol()).getQOS();
         if (nap.isEnabled() && (qos == 102 || qos == 103)) {
            this.updateLocalServerChannel(nap);
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      if (ChannelHelper.DEBUG) {
         ChannelHelper.p("rollbackUpdate() " + event.getSourceBean());
      }

   }

   public static void removeChannelEntries(String serverName) {
      removeMapEntriesForServer(channelMap, serverName);
      removeMapEntriesForServer(channelsByName, serverName);
   }

   static void clearLocalChannels() {
      localChannels.clear();
      localChannelsByName.clear();
      localChannelsByProtocol.clear();
   }

   private static void removeMapEntriesForServer(Map map, String serverName) {
      if (map != null) {
         Iterator entries = map.entrySet().iterator();
         String domainName = LocalServerIdentity.getIdentity().getDomainName();

         while(entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry)entries.next();
            Object key = thisEntry.getKey();
            if (key instanceof ServerIdentity) {
               ServerIdentity keyServerId = (ServerIdentity)key;
               if (StringUtils.strcmp(keyServerId.getDomainName(), domainName) && StringUtils.strcmp(keyServerId.getServerName(), serverName)) {
                  entries.remove();
               }
            }
         }

      }
   }

   public static void removeChannelEntries(ServerIdentity id) {
      channelMap.remove(id);
      channelsByName.remove(id);
   }

   private static void invalidateChannelEntriesInDomain() {
   }

   private static ServerChannel findOutboundServerChannelInternal(Protocol protocol, String channelName) throws IOException {
      Debug.assertion(initialized && (protocol != null || channelName != null));
      List arr;
      Iterator i;
      if (channelName != null) {
         arr = (List)localChannelsByName.get(channelName);
         if (arr != null) {
            label98: {
               i = arr.iterator();

               ServerChannelImpl sc;
               do {
                  do {
                     if (!i.hasNext()) {
                        break label98;
                     }

                     sc = (ServerChannelImpl)i.next();
                  } while(!outboundCheckIgnoredChannel.equalsIgnoreCase(sc.getChannelName()) && !sc.getConfig().isOutboundEnabled());
               } while(protocol != null && sc.getProtocol() != protocol);

               return sc;
            }
         }
      }

      if (protocol == null) {
         return null;
      } else {
         arr = (List)localChannelsByProtocol.get(protocol);
         if (arr != null) {
            i = arr.iterator();

            ServerChannelImpl sc;
            ServerChannel i;
            while(i.hasNext()) {
               i = (ServerChannel)i.next();
               sc = (ServerChannelImpl)i;
               if (sc.getConfig().isOutboundEnabled() && !sc.isSDPEnabled() && !sc.isT3SenderQueueDisabled()) {
                  return sc;
               }
            }

            if (protocol == ChannelService.PROTOCOL.ADMIN) {
               i = arr.iterator();

               while(i.hasNext()) {
                  i = (ServerChannel)i.next();
                  sc = (ServerChannelImpl)i;
                  if (sc.getConfig().isOutboundEnabled()) {
                     return sc;
                  }
               }
            }
         }

         if (!protocol.isEnabled()) {
            throw new IOException(ServerLogger.getNoConfiguredOutboundChannelLoggable(protocol.getProtocolName()).getMessage());
         } else {
            return protocol.getHandler().getDefaultServerChannel();
         }
      }
   }

   static ServerChannel findInboundServerChannel(Protocol protocol, String name) {
      if (!initialized) {
         return null;
      } else {
         List arr = (List)localChannelsByProtocol.get(protocol);
         if (arr != null) {
            Iterator var3 = arr.iterator();

            while(var3.hasNext()) {
               ServerChannel sc = (ServerChannel)var3.next();
               if (name == null || sc.getChannelName().equals(name)) {
                  return sc;
               }
            }
         }

         return null;
      }
   }

   static ServerChannel findInboundServerChannel(Protocol protocol) {
      return findInboundServerChannel(protocol, (String)null);
   }

   public static List findInboundServerChannels(Protocol protocol) {
      Debug.assertion(initialized);
      return (List)localChannelsByProtocol.get(protocol);
   }

   public static List getInboundServerChannels() {
      Debug.assertion(initialized);
      List list = new ArrayList();
      Iterator var1 = localChannelsByProtocol.values().iterator();

      while(var1.hasNext()) {
         List i = (List)var1.next();
         list.addAll(i);
      }

      return list;
   }

   public static InetSocketAddress findServerAddress(Protocol protocol) {
      ServerChannel sc = findInboundServerChannel(protocol);
      return sc == null ? null : sc.getPublicInetAddress();
   }

   public static InetSocketAddress findServerAddress(String protocol) {
      Protocol p = ProtocolManager.getProtocolByName(protocol);
      return findServerAddress(p);
   }

   private static final ServerRuntime getServerRuntime() {
      return (ServerRuntime)ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   public long getAdminChannelCreationTime() {
      return this.adminChannelCreationTime;
   }

   public ServerChannel getServerChannel(HostID identity) {
      if (identity != LocalServerIdentity.getIdentity()) {
         throw new AssertionError(ServerLogger.getOnlyLocalHostIDIsSupportedLoggable("" + identity).getMessage());
      } else {
         return ServerChannelManager.findDefaultLocalServerChannel();
      }
   }

   protected ServerChannel getServerChannel(HostID identity, Protocol protocol) {
      return this.getServerChannel(identity, protocol, (String)null);
   }

   protected ServerChannel getIPv4ServerChannel(HostID identity, Protocol protocol) {
      return this.getServerChannel(identity, protocol, false);
   }

   protected ServerChannel getIPv6ServerChannel(HostID identity, Protocol protocol) {
      return this.getServerChannel(identity, protocol, true);
   }

   private ServerChannel getServerChannel(HostID identity, Protocol protocol, boolean ipv6Only) {
      Debug.assertion(identity != null);
      Map map;
      if (DEBUG_MAP) {
         map = (Map)channelMap.get(identity);
         ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ") =>\n" + map.toString());
      }

      map = this.findOrCreateChannels((ServerIdentity)identity);
      if (map != null) {
         List arr = (List)map.get(protocol);
         if (arr != null) {
            Iterator var6 = arr.iterator();

            while(var6.hasNext()) {
               ServerChannel sc = (ServerChannel)var6.next();

               try {
                  InetAddress ia = InetAddress.getByName(sc.getPublicAddress());
                  if (ia instanceof Inet6Address) {
                     if (ipv6Only) {
                        if (DEBUG_MAP) {
                           ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ", ipv6Only) returned " + sc);
                        }

                        return sc;
                     }
                  } else if (!ipv6Only) {
                     if (DEBUG_MAP) {
                        ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ", ipv4Only) returned " + sc);
                     }

                     return sc;
                  }
               } catch (UnknownHostException var9) {
               }
            }
         }
      }

      if (DEBUG_MAP) {
         ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ") returned NULL");
      }

      return null;
   }

   protected ServerChannel getServerChannel(HostID identity, Protocol protocol, String channelName) {
      Debug.assertion(identity != null);
      Map map;
      if (DEBUG_MAP) {
         map = (Map)channelMap.get(identity);
         ChannelHelper.p("getServerChannel(" + Thread.currentThread() + "\n" + identity + ", " + protocol + ") =>\n" + map.toString());
      }

      map = this.findOrCreateChannels((ServerIdentity)identity);
      if (map != null) {
         List arr = (List)map.get(protocol);
         if (arr != null) {
            String nameDefault = (protocol.isSecure() ? "DefaultSecure" : "Default") + "[" + protocol.getAsURLPrefix() + "]";
            ServerChannel defaultChannel = null;
            Iterator var8 = arr.iterator();

            while(true) {
               if (!var8.hasNext()) {
                  if (defaultChannel != null) {
                     if (DEBUG_MAP) {
                        ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ") returned " + defaultChannel);
                     }

                     return defaultChannel;
                  }
                  break;
               }

               ServerChannel sc = (ServerChannel)var8.next();
               if (channelName == null && !sc.isSDPEnabled() && !sc.isT3SenderQueueDisabled() || sc.getChannelName().equals(channelName)) {
                  if (DEBUG_MAP) {
                     ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ") returned " + sc);
                  }

                  return sc;
               }

               if (nameDefault.equals(sc.getChannelName())) {
                  defaultChannel = sc;
               }
            }
         }
      }

      if (DEBUG_MAP) {
         ChannelHelper.p("getServerChannel(" + identity + ", " + protocol + ") returned NULL");
      }

      return null;
   }

   protected ServerChannel getServerChannel(HostID identity, String partitionName, String vtName, Protocol protocol) {
      if (identity != LocalServerIdentity.getIdentity()) {
         throw new IllegalArgumentException("Can't retrieve partition channels for " + (identity == null ? "NULL" : identity.getServerName()));
      } else if (partitionName != null && vtName != null) {
         Map serverChannelMap = (Map)localChannelsByPartitionName.get(partitionName);
         if (serverChannelMap != null) {
            Iterator var6 = serverChannelMap.values().iterator();

            label60:
            while(true) {
               List channels;
               do {
                  do {
                     if (!var6.hasNext()) {
                        break label60;
                     }

                     channels = (List)var6.next();
                  } while(channels.isEmpty());
               } while(!vtName.equals(((ServerChannel)channels.get(0)).getAssociatedVirtualTargetName()));

               Iterator var8 = channels.iterator();

               while(var8.hasNext()) {
                  ServerChannel sc = (ServerChannel)var8.next();
                  if (protocol == null || sc.getProtocol() == protocol) {
                     if (DEBUG_MAP) {
                        ChannelHelper.p("getServerChannel(" + identity + ", " + partitionName + ", " + vtName + ", " + protocol + ") returned " + sc);
                     }

                     return sc;
                  }
               }
            }
         }

         if (DEBUG_MAP) {
            ChannelHelper.p("getServerChannel(" + identity + ", " + partitionName + ", " + vtName + ", " + protocol + ") returned NULL");
         }

         return null;
      } else {
         throw new IllegalArgumentException("PartitionName or VirtualTargetName can't be null");
      }
   }

   protected ServerChannel getServerChannel(HostID identity, String channelName) {
      Debug.assertion(identity != null);
      Map map = this.findOrCreateNamedChannels((ServerIdentity)identity);
      if (map != null) {
         List arr = (List)map.get(channelName);
         if (DEBUG_MAP) {
            ChannelHelper.p("getServerChannel(" + identity + ", " + channelName + ") => " + arr);
         }

         if (arr != null) {
            Iterator var5 = arr.iterator();
            if (var5.hasNext()) {
               ServerChannel sc = (ServerChannel)var5.next();
               return sc;
            }
         }
      }

      return null;
   }

   protected ServerChannel getAvailableServerChannel(HostID identity, String channelName) {
      Debug.assertion(identity != null);
      Map map = this.findNamedChannels((ServerIdentity)identity);
      if (map != null) {
         List arr = (List)map.get(channelName);
         if (DEBUG_MAP) {
            ChannelHelper.p("getServerChannel(" + identity + ", " + channelName + ") => " + arr);
         }

         if (arr != null) {
            Iterator var5 = arr.iterator();
            if (var5.hasNext()) {
               ServerChannel sc = (ServerChannel)var5.next();
               return sc;
            }
         }
      }

      return null;
   }

   private Map findNamedChannels(ServerIdentity id) {
      if (id.isClient()) {
         return null;
      } else {
         Map map = (Map)channelsByName.get(id);
         return map;
      }
   }

   protected ServerChannel getRelatedServerChannel(HostID identity, Protocol protocol, String publicAddress) {
      Debug.assertion(initialized);
      Map map = this.findOrCreateChannels((ServerIdentity)identity);
      if (map != null) {
         List arr = (List)map.get(protocol);
         if (arr != null) {
            Iterator var6 = arr.iterator();

            while(var6.hasNext()) {
               ServerChannel sc = (ServerChannel)var6.next();
               if (publicAddress.equals(sc.getPublicAddress())) {
                  return sc;
               }
            }
         }
      }

      return null;
   }

   protected ServerChannel getOutboundServerChannel(Protocol protocol, String channelName) {
      try {
         return findOutboundServerChannelInternal(protocol, channelName);
      } catch (IOException var4) {
         return null;
      }
   }

   public static void dumpTables() {
      System.out.println("Local channels:");
      System.out.println(localChannels.toString());
      System.out.println("Domain channels by protocol:");
      System.out.println(channelMap.toString());
      System.out.println("Domain channels by name:");
      System.out.println(channelsByName.toString());
   }

   private void removePreferredChannel(ServerChannel channel) {
      Set entries = preferredChannelCache.entrySet();
      Iterator var3 = entries.iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (channel.getChannelName().equals(((ServerChannel)entry.getValue()).getChannelName())) {
            preferredChannelCache.remove(entry.getKey());
         }
      }

   }

   public String findPreferredChannelName(SocketAddress sa) {
      ServerChannel sc = findPreferredChannelForPeer(sa);
      return sc != null ? sc.getChannelName() : null;
   }

   public static ServerChannel findPreferredChannelForPeer(SocketAddress sa) {
      return sa == null ? null : (ServerChannel)preferredChannelCache.get(sa);
   }

   public static ServerChannel findPreferredChannelFromSocket(Socket s, Protocol p, ServerChannel channel) {
      if (initialized) {
         List list = findInboundServerChannels(p);
         SocketAddress socketRemoteSocketAddr = s.getRemoteSocketAddress();
         if (list != null) {
            Iterator iter = list.iterator();
            ArrayList match = null;

            ServerChannel sc;
            while(iter.hasNext()) {
               sc = (ServerChannel)iter.next();
               if (!sc.isSDPEnabled() != channel.isSDPEnabled() && Arrays.equals(sc.getInetAddress().getAddress(), s.getLocalAddress().getAddress())) {
                  if (sc.getChannelName().equals(channel.getChannelName())) {
                     preferredChannelCache.put(socketRemoteSocketAddr, sc);
                     return sc;
                  }

                  if (match == null) {
                     match = new ArrayList();
                  }

                  match.add(sc);
               }
            }

            if (match != null && match.size() > 0) {
               sc = (ServerChannel)match.get(0);
               preferredChannelCache.put(socketRemoteSocketAddr, sc);
               return sc;
            }
         }

         preferredChannelCache.put(socketRemoteSocketAddr, channel);
      }

      return channel;
   }

   private boolean isClusterChannelsNeeded(Protocol protocol, ClusterMBean cluster) {
      return protocol.toByte() != 12 && protocol.toByte() != 13 || cluster != null && "unicast".equals(cluster.getClusterMessagingMode());
   }

   @Service
   private static class ChannelImportExportServiceImpl implements ChannelImportExportService {
      public void exportServerChannels(ServerIdentity id, ObjectOutput out) throws IOException {
         ChannelService.exportServerChannels(id, out);
      }

      public void importNonLocalServerChannels(ServerIdentity id, ObjectInput in) throws IOException {
         ChannelService.importNonLocalServerChannels(id, in);
      }

      public InetSocketAddress findServerAddress(Protocol protocol) {
         return ChannelService.findServerAddress(protocol);
      }

      public InetSocketAddress findServerAddress(String protocol) {
         return ChannelService.findServerAddress(protocol);
      }
   }

   private static class MyChannelsComparator implements Comparator {
      private MyChannelsComparator() {
      }

      public int compare(Object o1, Object o2) {
         ServerChannelImpl sc1 = (ServerChannelImpl)o1;
         ServerChannelImpl sc2 = (ServerChannelImpl)o2;
         if (sc1.getChannelName().equals(sc2.getChannelName())) {
            if (sc1.isOutboundEnabled() == sc2.isOutboundEnabled()) {
               return 0;
            } else {
               return sc1.isOutboundEnabled() ? 1 : -1;
            }
         } else {
            String nameDefault = "Default[" + sc1.getProtocol().getAsURLPrefix() + "]";
            String nameDefaultSecure = "DefaultSecure[" + sc1.getProtocol().getAsURLPrefix() + "]";
            if (nameDefault.equals(sc1.getChannelName())) {
               return -1;
            } else if (nameDefault.equals(sc2.getChannelName())) {
               return 1;
            } else if (nameDefaultSecure.equals(sc1.getChannelName())) {
               return -1;
            } else {
               return nameDefaultSecure.equals(sc2.getChannelName()) ? 1 : sc1.getChannelName().compareTo(sc2.getChannelName());
            }
         }
      }

      // $FF: synthetic method
      MyChannelsComparator(Object x0) {
         this();
      }
   }

   private static class DisconnectListener implements weblogic.rmi.extensions.DisconnectListener {
      private DisconnectListener() {
      }

      public void onDisconnect(DisconnectEvent event) {
         if (event instanceof ServerDisconnectEvent) {
            String serverName = ((ServerDisconnectEvent)event).getServerName();
            ChannelService.removeChannelEntries(serverName);
         }

      }

      // $FF: synthetic method
      DisconnectListener(Object x0) {
         this();
      }
   }

   private static class ConnectListener implements weblogic.rmi.extensions.ConnectListener {
      private ConnectListener() {
      }

      public void onConnect(ConnectEvent event) {
      }

      // $FF: synthetic method
      ConnectListener(Object x0) {
         this();
      }
   }

   private static final class PROTOCOL {
      private static final Protocol LDAP = ProtocolManager.getProtocolByIndex(10);
      private static final Protocol LDAPS = ProtocolManager.getProtocolByIndex(11);
      private static final Protocol HTTP = ProtocolManager.getProtocolByIndex(1);
      private static final Protocol HTTPS = ProtocolManager.getProtocolByIndex(3);
      private static final Protocol ADMIN = ProtocolManager.getProtocolByIndex(6);
      private static final Protocol CLUSTER = ProtocolManager.getProtocolByIndex(12);
      private static final Protocol CLUSTERS = ProtocolManager.getProtocolByIndex(13);
      private static final Protocol SNMP = ProtocolManager.getProtocolByIndex(14);
   }
}
