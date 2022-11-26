package weblogic.transaction.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.Remote;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.PeerExchangeTransaction;
import weblogic.transaction.TransactionLogger;
import weblogic.utils.collections.ArraySet;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class ServerCoordinatorDescriptorManagerImpl implements ServerCoordinatorDescriptorManager {
   private static final int SERVER_REFRESH_INTERVAL_MILLIS = 5000;
   private static final CacheLock cacheLock = new CacheLock();
   private static final HashMap activeServers = new HashMap();
   private static final HashMap lostServers = new HashMap();
   private static final boolean isRefreshServerEnabledProp = Boolean.valueOf(System.getProperty("weblogic.transaction.isRefreshServerEnabled", "true"));
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int purgeFromCheckpointIntervalSeconds;
   private boolean serverCheckpointNeeded = false;
   private ServerCheckpoint latestServerCheckpoint = null;

   public ServerCoordinatorDescriptor getLocalCoordinatorDescriptor() {
      return ServerCoordinatorDescriptorManagerImpl.SCDMaker.svrCoDes;
   }

   public void setLocalCoordinatorDescriptor(CoordinatorDescriptor scd) {
   }

   public String getLocalCoordinatorURL() {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getLocalCoordinatorDescriptor=" + this.getLocalCoordinatorDescriptor());
      }

      return this.getLocalCoordinatorDescriptor() == null ? null : this.getLocalCoordinatorDescriptor().getCoordinatorURL();
   }

   public String getLocalCoordinatorSecureURL() {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getLocalCoordinatorSecureURL getLocalCoordinatorDescriptor=" + this.getLocalCoordinatorDescriptor());
      }

      return this.getLocalCoordinatorDescriptor() == null ? null : this.getLocalCoordinatorDescriptor().getSSLCoordinatorURL();
   }

   public String getLocalCoordinatorNonSecureURL() {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getLocalCoordinatorNonSecureURL getLocalCoordinatorDescriptor=" + this.getLocalCoordinatorDescriptor());
      }

      return this.getLocalCoordinatorDescriptor() == null ? null : this.getLocalCoordinatorDescriptor().getNonSSLCoordinatorURL();
   }

   public ServerCoordinatorDescriptor getOrCreate(String aCoURL) {
      return aCoURL == null ? null : this.getOrCreate(aCoURL, (ServerMBean)null);
   }

   public CoordinatorDescriptor getOrCreateForMigration(String serverName) {
      try {
         DomainMBean domain = this.getDomainMBean();
         ServerMBean serverMbean = domain.lookupServer(serverName);
         if (serverMbean == null) {
            serverMbean = this.getServerMBeanForServerNameIgnoreCase(serverName, domain, serverMbean);
         }

         String coURL = getCoordinatorURL(serverMbean);
         ServerCoordinatorDescriptor scd = this.getOrCreate(coURL);
         return new CoordinatorDescriptor(scd.getCoordinatorURL());
      } catch (Exception var6) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("ServerCoordinatorDescriptor.getOrCreateForMigration(server=" + serverName + ") failed", var6);
         }

         return null;
      }
   }

   ServerMBean getServerMBeanForServerNameIgnoreCase(String serverName, DomainMBean domain, ServerMBean serverMbean) {
      String[] serverNames = this.getAllServerNamesInDomain();
      String[] var5 = serverNames;
      int var6 = serverNames.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String serverName1 = var5[var7];
         if (serverName1.equalsIgnoreCase(serverName)) {
            serverMbean = domain.lookupServer(serverName1);
         }
      }

      return serverMbean;
   }

   private String[] getAllServerNamesInDomain() {
      return PlatformHelper.getPlatformHelper().getAllServerNamesInDomain();
   }

   DomainMBean getDomainMBean() {
      return ManagementService.getRuntimeAccess(kernelID).getDomain();
   }

   public List getAllCheckpointServers() {
      List list = new ArrayList();
      synchronized(cacheLock) {
         Collection actives = activeServers.values();
         if (actives != null) {
            Iterator ait = actives.iterator();

            while(ait.hasNext()) {
               ServerCoordinatorDescriptor cd = (ServerCoordinatorDescriptor)ait.next();
               if (cd.includeInCheckpoint()) {
                  list.add(cd.getCoordinatorURL());
               }
            }
         }

         Collection lost = lostServers.values();
         if (lost != null) {
            Iterator lit = lost.iterator();

            while(lit.hasNext()) {
               ServerCoordinatorDescriptor cd = (ServerCoordinatorDescriptor)lit.next();
               if (cd.includeInCheckpoint()) {
                  list.add(cd.getCoordinatorURL());
               }
            }
         }

         return list;
      }
   }

   public void setLatestServerCheckpoint(TransactionLogger tlog, ServerCheckpoint sc) {
      if (getTM().getTransactionLogger() == null || tlog == getTM().getTransactionLogger()) {
         long txTotalCount = getTM().getRuntime().getTransactionTotalCount();
         ServerCheckpoint old;
         synchronized(cacheLock) {
            old = this.latestServerCheckpoint;
            this.latestServerCheckpoint = sc;
         }

         if (old != null) {
            tlog.release(old);
         }

      }
   }

   public void setLatestServerCheckpoint(ServerCheckpoint sc) {
      synchronized(cacheLock) {
         this.latestServerCheckpoint = sc;
      }
   }

   public void checkpointIfNecessary() {
      synchronized(cacheLock) {
         if (!this.serverCheckpointNeeded) {
            return;
         }

         this.serverCheckpointNeeded = false;
      }

      this.checkpointServers();
   }

   public ServerCoordinatorDescriptor[] getServers(String resourceName) {
      ResourceDescriptor rd = ResourceDescriptor.get(resourceName);
      if (rd != null) {
         List scList = rd.getSCUrlList();
         if (scList != null) {
            return (ServerCoordinatorDescriptor[])scList.toArray(new ServerCoordinatorDescriptor[scList.size()]);
         }
      }

      return null;
   }

   public void checkpointServers() {
      ServerCheckpoint cp = new ServerCheckpoint();
      cp.blockingStore(getTM().getTransactionLogger());
   }

   public void setServerCheckpointNeeded(boolean needed) {
      this.serverCheckpointNeeded = needed;
   }

   public void setPurgeFromCheckpointIntervalSeconds(int max) {
      this.purgeFromCheckpointIntervalSeconds = max;
      if (TxDebug.JTARecovery.isDebugEnabled()) {
         TxDebug.JTARecovery.debug("ServerCoordinatorDescriptor.setPurgeFromCheckpointIntervalSecs:" + max);
      }

   }

   public int getPurgeFromCheckpointIntervalSeconds() {
      return this.purgeFromCheckpointIntervalSeconds;
   }

   public void handleNotification(PropertyChangeNotification pcn, String coURL) {
      ServerCoordinatorDescriptor scd = this.getOrCreate(coURL);
      if (scd == null) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManager.handleNotification(); null scd for " + coURL);
         }

      } else {
         Set newResourceDescriptors;
         Set staleResourceDescriptors;
         if ("XAResources".equals(pcn.getName())) {
            newResourceDescriptors = nameArray2XAResourceDescriptorSet((String[])((String[])pcn.getNewValue()));
            staleResourceDescriptors = scd.updateXAResources(newResourceDescriptors);
            updateCache(staleResourceDescriptors, newResourceDescriptors, scd);
         } else if ("NonXAResources".equals(pcn.getName())) {
            newResourceDescriptors = nameArray2NonXAResourceDescriptorSet((String[])((String[])pcn.getNewValue()));
            staleResourceDescriptors = scd.updateNonXAResources(newResourceDescriptors);
            updateCache(staleResourceDescriptors, newResourceDescriptors, scd);
         }

      }
   }

   private static void updateSCDXAResources(ServerCoordinatorDescriptor scd, Set aNewResourceDescriptors) {
      if (aNewResourceDescriptors != null) {
         Set staleResourceDescriptors = scd.updateXAResources(aNewResourceDescriptors);
         updateCache(staleResourceDescriptors, aNewResourceDescriptors, scd);
      }
   }

   private static void updateSCDNonXAResources(ServerCoordinatorDescriptor scd, Set aNewResourceDescriptors) {
      if (aNewResourceDescriptors != null) {
         Set staleResourceDescriptors = scd.updateNonXAResources(aNewResourceDescriptors);
         updateCache(staleResourceDescriptors, aNewResourceDescriptors, scd);
      }
   }

   private static final void updateCache(Set aStaleResourceDescriptors, Set aNewResourceDescriptors, ServerCoordinatorDescriptor scd) {
      Set addResourceNames = complementOfXInY(aStaleResourceDescriptors, aNewResourceDescriptors);
      Iterator addIt = addResourceNames.iterator();

      while(addIt.hasNext()) {
         ResourceDescriptor rd = (ResourceDescriptor)addIt.next();
         rd.addSC(scd);
         String resourceName = rd.getName();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.updateCache() resource " + resourceName + " now registered with " + scd);
         }
      }

      Set removeResourceNames = complementOfXInY(aNewResourceDescriptors, aStaleResourceDescriptors);
      Iterator removeIt = removeResourceNames.iterator();

      while(removeIt.hasNext()) {
         ResourceDescriptor rd = (ResourceDescriptor)removeIt.next();
         rd.removeSC(scd);
         String resourceName = rd.getName();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.updateCache() resource " + resourceName + " no longer registered with " + scd);
         }
      }

   }

   private static final Set complementOfXInY(Set x, Set y) {
      Set ret = new ArraySet();
      if (y == null) {
         return ret;
      } else if (x == null) {
         return y;
      } else {
         Iterator yIt = y.iterator();

         while(yIt.hasNext()) {
            Object o = yIt.next();
            if (!x.contains(o)) {
               ret.add(o);
            }
         }

         return ret;
      }
   }

   private final ServerCoordinatorDescriptor getOrCreate(String aCoURL, ServerMBean aServerMBean) {
      if (aCoURL == null) {
         return null;
      } else {
         String serverID = CoordinatorDescriptor.getServerID(aCoURL);
         if (serverID == null) {
            return null;
         } else if (this.isLocalServer(serverID)) {
            return this.getLocalCoordinatorDescriptor();
         } else {
            synchronized(cacheLock) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getOrCreate(" + aCoURL + ") serverID:" + serverID);
               }

               ServerCoordinatorDescriptor scd;
               if (!this.isKnownServer(serverID)) {
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getOrCreate(" + aCoURL + ")");
                  }

                  if (TxDebug.JTANamingStackTrace.isDebugEnabled()) {
                     TxDebug.debugStack(TxDebug.JTANaming, "ServerCoordinatorDescriptor.getOrCreate(" + aCoURL + ")");
                  }

                  scd = new ServerCoordinatorDescriptor(aCoURL);
                  synchronized(cacheLock) {
                     lostServers.put(serverID, scd);
                  }

                  this.scheduleRefresh(scd);
                  return scd;
               }

               scd = (ServerCoordinatorDescriptor)activeServers.get(serverID);
               if (scd != null) {
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("DEBUG setSSL URL ServerCoordinatorDescriptor.getOrCreate(" + aCoURL + ") serverID:" + serverID);
                  }

                  scd.setOnlySSLCoordinatorURL(aCoURL);
                  return scd;
               }

               scd = (ServerCoordinatorDescriptor)lostServers.get(serverID);
               if (scd != null) {
                  this.scheduleRefresh(scd);
                  return scd;
               }
            }

            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getOrCreate(): known server but not active or lost");
            }

            return null;
         }
      }
   }

   private boolean isKnownServer(String aServerID) {
      synchronized(cacheLock) {
         if (activeServers.get(aServerID) != null) {
            return true;
         } else {
            return lostServers.get(aServerID) != null;
         }
      }
   }

   private static final String getHost(ServerMBean aServerMBean) throws Exception {
      String host = aServerMBean.getListenAddress();
      if (host != null && !host.equalsIgnoreCase("localhost") && !host.equals("127.0.0.1")) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordiatorDescripto.getHost host:" + host);
         }

         return host;
      } else {
         throw new Exception("Unable to get runtime listen address.Server " + aServerMBean.getName() + " configured with localhost");
      }
   }

   private static String getCoordinatorURL(String domainName, String serverName) throws URISyntaxException {
      String url = null;

      try {
         ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
         ServerTransactionManagerImpl var10000 = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
         if (ServerTransactionManagerImpl.getInteropMode() != 1 && !PlatformHelper.getPlatformHelper().isCDSEnabled()) {
            url = protocolService.findAdministrationURL(serverName);
         } else {
            url = protocolService.findURL(serverName, protocolService.getDefaultProtocol());
         }

         if (url == null) {
            url = protocolService.findURL(serverName, protocolService.getDefaultSecureProtocol());
         }
      } catch (UnknownHostException var7) {
         return null;
      }

      URI uri = new URI(url);
      String protocol = uri.getScheme();
      int port = uri.getPort();
      String host = uri.getHost();
      return CoordinatorDescriptor.getCoordinatorURL(host + ":" + port, domainName, serverName, protocol);
   }

   private static URI createURI(String protocol, String host, int port) throws URISyntaxException {
      return new URI(protocol, (String)null, host, port, (String)null, (String)null, (String)null);
   }

   private static URI createURI(String channelName) throws URISyntaxException {
      if (channelName == null) {
         return null;
      } else {
         ServerMBean servermbean = ManagementService.getRuntimeAccess(kernelID).getServer();
         NetworkAccessPointMBean napmbean = servermbean.lookupNetworkAccessPoint(channelName);
         return napmbean == null ? null : createURI(napmbean.getProtocol(), napmbean.getListenAddress(), napmbean.getListenPort());
      }
   }

   private static URI createPublicURI(String channelName) throws URISyntaxException {
      if (channelName == null) {
         return null;
      } else {
         ServerMBean servermbean = ManagementService.getRuntimeAccess(kernelID).getServer();
         NetworkAccessPointMBean napmbean = servermbean.lookupNetworkAccessPoint(channelName);
         return napmbean == null ? null : createURI(napmbean.getProtocol(), napmbean.getPublicAddress(), napmbean.getPublicPort());
      }
   }

   private static ServerCoordinatorDescriptor createLocalCoordinatorDescriptorFromChannelConfiguration(ServerCoordinatorDescriptor defaultCoDesc) throws URISyntaxException {
      ServerMBean servermbean = ManagementService.getRuntimeAccess(kernelID).getServer();
      ServerCoordinatorDescriptor svrCoDes = null;
      String channelName = PlatformHelper.getPlatformHelper().getPrimaryChannelName();
      String publicChannelName = PlatformHelper.getPlatformHelper().getPublicChannelName();
      String secureChannelName = PlatformHelper.getPlatformHelper().getSecureChannelName();
      String publicSecureChannelName = PlatformHelper.getPlatformHelper().getPublicSecureChannelName();
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("createLocalCoordinatorDescriptorFromChannelConfiguration() primaryChannel=" + channelName + ", publicChannelName=" + publicChannelName + ", secureChannelName=" + secureChannelName + ", publicSecureChannelName=" + publicSecureChannelName);
      }

      if (channelName == null && publicChannelName == null && secureChannelName == null && publicSecureChannelName == null) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("createLocalCoordinatorDescriptorFromChannelConfiguration() returning default descriptor " + defaultCoDesc);
         }

         return defaultCoDesc;
      } else {
         URI uriPrimary = null;
         URI uriPublic = null;
         if (channelName != null) {
            if (publicChannelName != null && channelName.equals(publicChannelName)) {
               uriPrimary = createURI(channelName);
               uriPublic = createPublicURI(channelName);
            } else {
               uriPrimary = createPublicURI(channelName);
            }
         } else {
            uriPrimary = new URI(defaultCoDesc.getPrimaryURL());
         }

         if (uriPrimary == null) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("createLocalCoordinatorDescriptorFromChannelConfiguration() no primary URL configured, using default descriptor " + defaultCoDesc);
            }

            return defaultCoDesc;
         } else {
            if (uriPublic == null && publicChannelName != null) {
               uriPublic = createPublicURI(publicChannelName);
            }

            URI uriSecure = null;
            URI uriPublicSecure = null;
            if (secureChannelName != null) {
               if (publicSecureChannelName != null && secureChannelName.equals(publicSecureChannelName)) {
                  uriSecure = createURI(secureChannelName);
                  uriPublicSecure = createPublicURI(secureChannelName);
               } else {
                  uriSecure = createPublicURI(secureChannelName);
               }
            }

            if (uriPublicSecure == null && publicSecureChannelName != null) {
               uriPublicSecure = createPublicURI(publicSecureChannelName);
            }

            ServerCoordinatorDescriptor scd = new ServerCoordinatorDescriptor(ManagementService.getRuntimeAccess(kernelID).getDomainName(), servermbean.getName(), uriPrimary, uriPublic, uriSecure, uriPublicSecure);
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("createLocalCoordinatorDescriptorFromChannelConfiguration(): local server " + scd);
            }

            return scd;
         }
      }
   }

   private static String getCoordinatorURL(ServerMBean aServerMBean) throws Exception {
      if (aServerMBean == null) {
         return null;
      } else {
         String serverName = aServerMBean.getName();
         if (serverName == null) {
            throw new Exception("Unable to obtain the server name");
         } else {
            int port;
            String protocol;
            String host;
            try {
               ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL serverName:" + serverName + " proto:" + protocolService.getDefaultSecureProtocol());
               }

               ServerTransactionManagerImpl var10000 = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
               String url;
               if (ServerTransactionManagerImpl.getInteropMode() != 1 && !PlatformHelper.getPlatformHelper().isCDSEnabled()) {
                  url = protocolService.findAdministrationURL(serverName);
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL 2:" + url);
                  }
               } else {
                  url = protocolService.findURL(serverName, protocolService.getDefaultProtocol());
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL:" + url);
                  }

                  if (url == null) {
                     url = protocolService.findAdministrationURL(serverName);
                     if (TxDebug.JTANaming.isDebugEnabled()) {
                        TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL ?:" + url);
                     }
                  }
               }

               if (url == null) {
                  url = protocolService.findURL(serverName, protocolService.getDefaultSecureProtocol());
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL 3:" + url);
                  }
               }

               URI uri = new URI(url);
               protocol = uri.getScheme();
               port = uri.getPort();
               host = uri.getHost();
            } catch (UnknownHostException var8) {
               host = getHost(aServerMBean);
               if (aServerMBean.isListenPortEnabled()) {
                  port = aServerMBean.getListenPort();
                  protocol = aServerMBean.getDefaultProtocol();
               } else {
                  SSLMBean sslMBean = aServerMBean.getSSL();
                  if (sslMBean == null) {
                     throw new Exception("Unable to obtain the SSL listen port of the server: no sslMBean");
                  }

                  if (!sslMBean.isEnabled()) {
                     throw new Exception("SSL listen port is not configured on the server");
                  }

                  port = sslMBean.getListenPort();
                  protocol = aServerMBean.getDefaultSecureProtocol();
               }
            }

            String domain = ManagementService.getRuntimeAccess(kernelID).getDomainName();
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getCoordinatorURL() " + host + ":" + port + "+" + domain + "+" + serverName + "+" + protocol);
            }

            return CoordinatorDescriptor.getCoordinatorURL(host + ":" + port, domain, serverName, protocol);
         }
      }
   }

   private static String getSecureCoordinatorURL(ServerMBean aServerMBean) throws Exception {
      if (aServerMBean == null) {
         return null;
      } else {
         String serverName = aServerMBean.getName();
         if (serverName == null) {
            throw new Exception("Unable to obtain the server name");
         } else {
            int port;
            String protocol;
            String host;
            try {
               ProtocolService protocolService = (new ProtocolServiceImpl()).getProtocolService();
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getSecureCoordinatorURL serverName:" + serverName + " proto:" + protocolService.getDefaultSecureProtocol());
               }

               String url = protocolService.findURL(serverName, protocolService.getDefaultSecureProtocol());
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptorManagerImpl.getSecureCoordinatorURL:" + url);
               }

               if (url == null) {
                  return null;
               }

               URI uri = new URI(url);
               protocol = uri.getScheme();
               port = uri.getPort();
               host = uri.getHost();
            } catch (UnknownHostException var8) {
               host = getHost(aServerMBean);
               if (aServerMBean.isListenPortEnabled()) {
                  port = aServerMBean.getListenPort();
                  protocol = aServerMBean.getDefaultProtocol();
               } else {
                  SSLMBean sslMBean = aServerMBean.getSSL();
                  if (sslMBean == null) {
                     throw new Exception("Unable to obtain the SSL listen port of the server: no sslMBean");
                  }

                  if (!sslMBean.isEnabled()) {
                     throw new Exception("SSL listen port is not configured on the server");
                  }

                  port = sslMBean.getListenPort();
                  protocol = aServerMBean.getDefaultSecureProtocol();
               }
            }

            String domain = ManagementService.getRuntimeAccess(kernelID).getDomainName();
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getSecureCoordinatorURL() " + host + ":" + port + "+" + domain + "+" + serverName + "+" + protocol);
            }

            return CoordinatorDescriptor.getCoordinatorURL(host + ":" + port, domain, serverName, protocol);
         }
      }
   }

   private final boolean isLocalServer(String aServerID) {
      String localURL;
      try {
         localURL = this.getLocalCoordinatorURL();
      } catch (Exception var4) {
         return false;
      }

      String localServerID = CoordinatorDescriptor.getServerID(localURL);
      return localServerID != null && localServerID.equals(aServerID);
   }

   private final void scheduleRefresh(final ServerCoordinatorDescriptor cd) {
      if (!isRefreshServerEnabledProp) {
         TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.scheduleRefresh(" + cd.getCoordinatorURL() + ") disabled - will not refresh.");
      } else {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.scheduleRefresh(" + cd.getCoordinatorURL() + ")");
         }

         synchronized(this) {
            label54: {
               if (!cd.isRefreshScheduled() && !cd.isRefreshInProgress()) {
                  long timeSinceLastRefresh = System.currentTimeMillis() - cd.getLastRefreshTime();
                  if (timeSinceLastRefresh >= 5000L) {
                     cd.setRefreshScheduled(true);
                     break label54;
                  }

                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.scheduleRefresh(): timeSinceLastRefresh = " + timeSinceLastRefresh + " is less than server refresh interval of " + 5000);
                  }

                  return;
               }

               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.scheduleRefresh(" + cd.getCoordinatorURL() + "): lookup in progress");
               }

               return;
            }
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
            public void run() {
               try {
                  String coURL = PlatformHelper.getPlatformHelper().getTargetChannelURL(cd);
                  if (coURL == null) {
                     cd.getNonSSLCoordinatorURL();
                  }

                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.scheduleRefresh(" + coURL + "): refreshServer");
                  }

                  AuthenticatedSubject var10000 = ServerCoordinatorDescriptorManagerImpl.kernelID;
                  RefreshServerAction var10001 = ServerCoordinatorDescriptorManagerImpl.this.new RefreshServerAction(cd);
                  ServerCoordinatorDescriptor var10002 = cd;
                  SecureAction.runAction(var10000, var10001, ServerCoordinatorDescriptor.getServerURL(coURL), "refreshServer");
               } catch (Exception var6) {
                  String str = var6.toString();
                  if ("java.io.IOException: Trouble finding the subject in the CredentialMap".equals(str.substring(0, 69))) {
                     synchronized(this) {
                        cd.setRefreshScheduled(false);
                     }
                  }

                  if (ServerCoordinatorDescriptorManagerImpl.getTM().getRecoverySiteName() == null) {
                     throw new RuntimeException(var6);
                  }
               }

            }
         });
      }
   }

   private static final Set nameArray2XAResourceDescriptorSet(Object[] aArray) {
      ArraySet ret = new ArraySet();

      for(int i = 0; i < aArray.length; ++i) {
         ret.add(XAResourceDescriptor.getOrCreate((String)aArray[i]));
      }

      return ret;
   }

   private static final Set nameArray2NonXAResourceDescriptorSet(Object[] aArray) {
      ArraySet ret = new ArraySet();

      for(int i = 0; i < aArray.length; ++i) {
         ret.add(NonXAResourceDescriptor.getOrCreate((String)aArray[i]));
      }

      return ret;
   }

   private final boolean inLocalDomain(CoordinatorDescriptor cd) {
      return getLocalDomain().equals(cd.getDomainName());
   }

   private static final String getLocalDomain() {
      return ManagementService.getRuntimeAccess(kernelID).getDomain().getName();
   }

   private static final void lostServer(String aServerID) {
      try {
         synchronized(cacheLock) {
            ServerCoordinatorDescriptor cd = (ServerCoordinatorDescriptor)activeServers.remove(aServerID);
            if (cd == null) {
               return;
            }

            lostServers.put(aServerID, cd);
            updateSCDXAResources(cd, new ArraySet());
            updateSCDNonXAResources(cd, new ArraySet());
         }
      } catch (Exception var5) {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.lostServer() ", var5);
         }
      }

   }

   public void setOnlySSLCoordinatorURL() {
      try {
         String coSSLUrl = getSecureCoordinatorURL(ManagementService.getRuntimeAccess(kernelID).getServer());
         this.getLocalCoordinatorDescriptor().setOnlySSLCoordinatorURL(coSSLUrl);
      } catch (Exception var2) {
         TXLogger.logLocalCoordinatorDescriptorError(var2);
      }

   }

   private static final ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   public ServerCoordinatorDescriptor[] getActiveServers() {
      synchronized(cacheLock) {
         return (ServerCoordinatorDescriptor[])((ServerCoordinatorDescriptor[])activeServers.values().toArray(new ServerCoordinatorDescriptor[activeServers.size()]));
      }
   }

   private class GetSubCoordinatorInfoAction implements PrivilegedExceptionAction {
      private String coUrl;
      private Map infoMap;
      private SubCoordinator3 sc;

      GetSubCoordinatorInfoAction(SubCoordinator3 asc, String acoUrl) {
         this.sc = asc;
         this.infoMap = null;
         this.coUrl = acoUrl;
      }

      public Object run() throws Exception {
         this.infoMap = this.sc.getSubCoordinatorInfo(this.coUrl);
         return null;
      }

      public Map getMap() {
         return this.infoMap;
      }
   }

   private final class RefreshServerAction implements PrivilegedExceptionAction {
      private final ServerCoordinatorDescriptor coordinatorDescriptor;

      RefreshServerAction(ServerCoordinatorDescriptor aCoordinatorDescriptor) {
         this.coordinatorDescriptor = aCoordinatorDescriptor;
      }

      public Object run() throws Exception {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ")");
         }

         TransactionImpl tx = null;
         synchronized(this) {
            if (this.coordinatorDescriptor.isRefreshInProgress()) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") refresh in progress");
               }

               return null;
            }

            this.coordinatorDescriptor.setRefreshInProgress(true);
         }

         boolean var21 = false;

         Object var4;
         label343: {
            label344: {
               try {
                  label308: {
                     try {
                        var21 = true;
                        tx = ServerCoordinatorDescriptorManagerImpl.getTM().internalSuspend();
                        SubCoordinator3 sc3 = this.getSubCoordinator3();
                        if (sc3 == null) {
                           break label308;
                        }

                        this.doPeerExchange(sc3);
                        var4 = null;
                     } finally {
                        ServerCoordinatorDescriptorManagerImpl.getTM().internalResume(tx);
                     }

                     var21 = false;
                     break label343;
                  }

                  var21 = false;
               } catch (Exception var35) {
                  if (TxDebug.JTANaming.isDebugEnabled()) {
                     TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") Exception: ", var35);
                     var21 = false;
                  } else {
                     var21 = false;
                  }
                  break label344;
               } finally {
                  if (var21) {
                     synchronized(this) {
                        this.coordinatorDescriptor.setRefreshInProgress(false);
                        this.coordinatorDescriptor.setRefreshScheduled(false);
                        this.coordinatorDescriptor.setLastRefreshTime(System.currentTimeMillis());
                        this.notifyAll();
                     }

                     if (TxDebug.JTANaming.isDebugEnabled()) {
                        TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") completed lookup");
                     }

                  }
               }

               synchronized(this) {
                  this.coordinatorDescriptor.setRefreshInProgress(false);
                  this.coordinatorDescriptor.setRefreshScheduled(false);
                  this.coordinatorDescriptor.setLastRefreshTime(System.currentTimeMillis());
                  this.notifyAll();
               }

               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") completed lookup");
               }

               return null;
            }

            synchronized(this) {
               this.coordinatorDescriptor.setRefreshInProgress(false);
               this.coordinatorDescriptor.setRefreshScheduled(false);
               this.coordinatorDescriptor.setLastRefreshTime(System.currentTimeMillis());
               this.notifyAll();
            }

            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") completed lookup");
            }

            return null;
         }

         synchronized(this) {
            this.coordinatorDescriptor.setRefreshInProgress(false);
            this.coordinatorDescriptor.setRefreshScheduled(false);
            this.coordinatorDescriptor.setLastRefreshTime(System.currentTimeMillis());
            this.notifyAll();
         }

         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.refreshServer(" + this.coordinatorDescriptor.getCoordinatorURL() + ") completed lookup");
         }

         return var4;
      }

      private SubCoordinator3 getSubCoordinator3() {
         PeerExchangeTransaction refTx = new PeerExchangeTransactionImpl();
         Object co = JNDIAdvertiser.getCachedCoordinator(this.coordinatorDescriptor, refTx);
         if (co == null && ServerCoordinatorDescriptorManagerImpl.this.inLocalDomain(this.coordinatorDescriptor)) {
            String newCoordinatorURL;
            try {
               newCoordinatorURL = ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL(this.coordinatorDescriptor.getDomainName(), this.coordinatorDescriptor.getServerName());
            } catch (URISyntaxException var5) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getSubCoordinator3() Invalid URI: " + var5);
               }

               return null;
            }

            if (newCoordinatorURL == null) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getSubCoordinator3() no URL found for " + this.coordinatorDescriptor.getServerID());
               }

               return null;
            }

            if (!CoordinatorDescriptor.getServerURL(newCoordinatorURL).equals(this.coordinatorDescriptor.serverURL)) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.getSubCoordinator3() server " + this.coordinatorDescriptor.getServerID() + " has moved to " + newCoordinatorURL);
               }

               this.coordinatorDescriptor.init(newCoordinatorURL);
            }
         }

         co = JNDIAdvertiser.getCachedCoordinator(this.coordinatorDescriptor, refTx);
         return co != null && co instanceof SubCoordinator3 && co instanceof NotificationBroadcaster ? (SubCoordinator3)co : null;
      }

      private void doPeerExchange(SubCoordinator3 sc3) throws Exception {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()");
         }

         String coUrl = ServerCoordinatorDescriptorManagerImpl.this.getLocalCoordinatorNonSecureURL();
         String scURL = this.coordinatorDescriptor.getNonSSLCoordinatorURL();
         ServerCoordinatorDescriptor var10000 = this.coordinatorDescriptor;
         String serverURL = ServerCoordinatorDescriptor.getServerURL(scURL);
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()serverURL= " + serverURL + " coUrl=" + coUrl + " scURL=" + scURL);
         }

         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()serverURL= " + serverURL + " coUrl=" + coUrl + " scURL=" + scURL + "coDesc:" + this.coordinatorDescriptor);
         }

         GetSubCoordinatorInfoAction action = ServerCoordinatorDescriptorManagerImpl.this.new GetSubCoordinatorInfoAction(sc3, coUrl);
         SecureAction.runAction(ServerCoordinatorDescriptorManagerImpl.kernelID, action, serverURL, "sc3.getSubCoordinatorInfo");
         Map infoMap = action.getMap();
         String coSSLUrl = ServerCoordinatorDescriptorManagerImpl.this.getLocalCoordinatorSecureURL();
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()serverURL= " + serverURL + " coSSLUrl=" + coSSLUrl + " scURL=" + scURL + "coDesc:" + this.coordinatorDescriptor);
         }

         if (coSSLUrl != null) {
            GetSubCoordinatorInfoAction actionSsl = ServerCoordinatorDescriptorManagerImpl.this.new GetSubCoordinatorInfoAction(sc3, coSSLUrl);
            SecureAction.runAction(ServerCoordinatorDescriptorManagerImpl.kernelID, actionSsl, serverURL, "sc3.getSubCoordinatorInfo");
         }

         if (infoMap == null) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()  peer info null");
            }

         } else {
            SubCoordinatorInfo info = new SubCoordinatorInfo(infoMap);
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()serverURL= " + serverURL + " coSSLUrl=" + coSSLUrl + " scURL=" + scURL + "coDesc:" + this.coordinatorDescriptor + " init coordinatorURL=" + (coSSLUrl == null ? info.getCoordinatorNonSecureURL() : info.getCoordinatorURL()));
            }

            if (coSSLUrl == null) {
               this.coordinatorDescriptor.init(info.getCoordinatorNonSecureURL());
            } else {
               this.coordinatorDescriptor.init(info.getCoordinatorURL());
            }

            this.coordinatorDescriptor.setSSLOnly(info.isSslOnly());
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()Notification Listener " + this.coordinatorDescriptor.getNonSSLCoordinatorURL());
            }

            NotificationBroadcaster nb = (NotificationBroadcaster)sc3;
            nb.addNotificationListener((CoordinatorImpl)ServerCoordinatorDescriptorManagerImpl.getTM().getLocalCoordinator(), this.coordinatorDescriptor.getNonSSLCoordinatorURL());
            if (sc3 instanceof Remote) {
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange()DisconnectionMonitor " + this.coordinatorDescriptor.getNonSSLCoordinatorURL());
               }

               DisconnectMonitor dm = DisconnectMonitorListImpl.getDisconnectMonitor();

               try {
                  dm.addDisconnectListener(sc3, new SubCoordinatorDisconnectListener(this.coordinatorDescriptor.getNonSSLCoordinatorURL(), this.coordinatorDescriptor.getServerID()));
               } catch (DisconnectMonitorUnavailableException var14) {
               }
            }

            synchronized(ServerCoordinatorDescriptorManagerImpl.cacheLock) {
               ServerCoordinatorDescriptorManagerImpl.updateSCDXAResources(this.coordinatorDescriptor, ServerCoordinatorDescriptorManagerImpl.nameArray2XAResourceDescriptorSet(info.getRegisteredXAResources()));
               ServerCoordinatorDescriptorManagerImpl.updateSCDNonXAResources(this.coordinatorDescriptor, ServerCoordinatorDescriptorManagerImpl.nameArray2NonXAResourceDescriptorSet(info.getRegisteredNonXAResources()));
               ServerCoordinatorDescriptorManagerImpl.activeServers.put(this.coordinatorDescriptor.getServerID(), this.coordinatorDescriptor);
               ServerCoordinatorDescriptorManagerImpl.lostServers.remove(this.coordinatorDescriptor.getServerID());
            }

            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.process90Exchange() completed lookup for " + this.coordinatorDescriptor.getCoordinatorURL());
            }

            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.doPeerExchange() initialized");
            }

         }
      }

      class SubCoordinatorDisconnectListener implements DisconnectListener {
         String url;
         String id;

         SubCoordinatorDisconnectListener(String url, String id) {
            this.url = url;
            this.id = id;
         }

         public void onDisconnect(DisconnectEvent reason) {
            if (TxDebug.JTANaming.isDebugEnabled()) {
               TxDebug.JTANaming.debug("ServerCoordinatorDescriptor.RefreshServerAction.DisconnectListenerImpl.onDisconnect(" + this.url + ")");
            }

            ServerCoordinatorDescriptorManagerImpl.lostServer(this.id);
         }
      }
   }

   private static final class SCDMaker {
      private static ServerCoordinatorDescriptor svrCoDes;

      static {
         try {
            if (Kernel.isServer()) {
               String coUrl = ServerCoordinatorDescriptorManagerImpl.getCoordinatorURL(ManagementService.getRuntimeAccess(ServerCoordinatorDescriptorManagerImpl.kernelID).getServer());
               String coSSLUrl = ServerCoordinatorDescriptorManagerImpl.getSecureCoordinatorURL(ManagementService.getRuntimeAccess(ServerCoordinatorDescriptorManagerImpl.kernelID).getServer());
               if (TxDebug.JTANaming.isDebugEnabled()) {
                  TxDebug.JTANaming.debug("ServerCoordinatorDescriptor(): local server " + coUrl + " secureURL=" + coSSLUrl);
               }

               if (PlatformHelper.getPlatformHelper().extendCoordinatorURL(coUrl) && !PlatformHelper.getPlatformHelper().isCDSEnabled()) {
                  svrCoDes = new ServerCoordinatorDescriptor(coUrl, CoordinatorDescriptor.getPort(coUrl));
               } else {
                  ServerTransactionManagerImpl var10000 = (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
                  ServerTransactionManagerImpl.setInteropModeToVal(1);
                  svrCoDes = new ServerCoordinatorDescriptor(coUrl);
                  svrCoDes.setOnlySSLCoordinatorURL(coSSLUrl);
               }

               svrCoDes = ServerCoordinatorDescriptorManagerImpl.createLocalCoordinatorDescriptorFromChannelConfiguration(svrCoDes);
            }
         } catch (Exception var2) {
            TXLogger.logLocalCoordinatorDescriptorError(var2);
         }

      }
   }

   private static final class CacheLock {
      private CacheLock() {
      }

      // $FF: synthetic method
      CacheLock(Object x0) {
         this();
      }
   }
}
