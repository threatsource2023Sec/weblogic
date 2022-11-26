package weblogic.protocol.configuration;

import java.io.ObjectOutput;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Provider;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ChannelHelperBase;
import weblogic.protocol.ClusterAddressCollaborator;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerChannelStream;
import weblogic.rjvm.RJVMLogger;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLogger;
import weblogic.utils.net.AddressUtils;

@Service
public final class ChannelHelper extends ChannelHelperBase implements ChannelHelperService {
   @Inject
   private Provider runtimeAccess;
   private static DebugLogger logger = DebugLogger.getDebugLogger("DebugChannel");
   public static final boolean DEBUG;
   private static final AuthenticatedSubject kernelId;

   public static void checkConsistency(ServerMBean server, String localServerName) throws ConfigurationException {
      checkConsistencyOfSSLPortConfiguration(server);
      String[] serverAddr = parseServerListenAddress(server, localServerName);
      HashSet addressAndPortMapOfDefaultChannels = populateAddressAndPortMapOfDefaultChannels(server, serverAddr);
      NetworkAccessPointMBean[] naps = populateNAPsOfServer(server);
      HashMap napFullAddresses = new HashMap();

      for(int i = 0; i < naps.length; ++i) {
         NetworkAccessPointMBean nap = naps[i];
         if (nap.isEnabled()) {
            String napListenAddress = populateNAPListenAddress(serverAddr, nap.getListenAddress());
            String[] napListenAddresses = populateNAPListenAddresses(serverAddr, nap.getListenAddress(), localServerName.equals(server.getName()));
            String[] var10 = napListenAddresses;
            int var11 = napListenAddresses.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String napAddress = var10[var12];
               String napAddressAndPort = napAddress + nap.getListenPort();
               checkConsistencyOfNapAndDefaultChannel(server, addressAndPortMapOfDefaultChannels, nap, napListenAddress, napAddressAndPort);
               checkConsistencyOfNapAndSDP(server, napFullAddresses, nap, napAddress, napAddressAndPort);
               checkConsistencyOfNaps(server, napFullAddresses, nap, napAddress);
            }
         }
      }

      if (DEBUG) {
         p(addressAndPortMapOfDefaultChannels.toString());
      }

      if (DEBUG) {
         p(napFullAddresses.toString());
      }

   }

   private static String[] populateNAPListenAddresses(String[] serverAddr, String napListenAddress, boolean runningOnLocalServer) throws ConfigurationException {
      if (DEBUG) {
         p("populateNAPListenAddresses: serverAddr:" + Arrays.toString(serverAddr) + "; napListenAddress:" + napListenAddress + " ; runningOnLocalServer:" + runningOnLocalServer);
      }

      String[] listenAddress;
      if (napListenAddress != null) {
         listenAddress = new String[]{parseInetAddress(napListenAddress, runningOnLocalServer)};
      } else {
         listenAddress = serverAddr;
      }

      if (DEBUG) {
         p("populateNAPListenAddresses: parsed to: " + Arrays.toString(listenAddress));
      }

      return listenAddress;
   }

   private static String parseInetAddress(String listenAddress, boolean runningOnLocalServer) throws ConfigurationException {
      try {
         return InetAddress.getByName(listenAddress).getHostAddress();
      } catch (UnknownHostException var3) {
         if (runningOnLocalServer) {
            throw new ConfigurationException(var3.getMessage());
         } else {
            if (DEBUG) {
               p("Ignore invalid listen address " + listenAddress + " since we are not sure whether it is valid or not on its own machine");
            }

            return listenAddress;
         }
      }
   }

   private static String populateNAPListenAddress(String[] serverAddr, String napListenAddress) {
      return napListenAddress == null ? serverAddr[0] : napListenAddress;
   }

   private static NetworkAccessPointMBean[] populateNAPsOfServer(ServerMBean server) {
      NetworkAccessPointMBean[] naps = (NetworkAccessPointMBean[])((NetworkAccessPointMBean[])server.getNetworkAccessPoints().clone());
      Arrays.sort(naps, new Comparator() {
         public int compare(Object a1, Object a2) {
            return ((NetworkAccessPointMBean)a1).getProtocol().compareTo(((NetworkAccessPointMBean)a2).getProtocol());
         }
      });
      return naps;
   }

   private static void checkConsistencyOfNaps(ServerMBean server, HashMap napFullAddresses, NetworkAccessPointMBean nap, String napAddress) throws ConfigurationException {
      String napFullAddress = nap.getProtocol() + napAddress + nap.getListenPort();
      NetworkAccessPointMBean possDupl = (NetworkAccessPointMBean)napFullAddresses.get(napFullAddress);
      if (possDupl == null && !nap.getProtocol().equalsIgnoreCase("ADMIN")) {
         possDupl = (NetworkAccessPointMBean)napFullAddresses.get("ADMIN".toLowerCase(Locale.ENGLISH) + napAddress + nap.getListenPort());
      }

      if (possDupl != null) {
         throw new ConfigurationException(ServerLogger.logPortConflictLoggable(nap.getName(), nap.getProtocol() + "://" + napAddress + ":" + nap.getListenPort(), possDupl.getName(), possDupl.getProtocol() + "://" + napAddress + ":" + possDupl.getListenPort(), server.getName()).getMessage());
      } else {
         napFullAddresses.put(napFullAddress, nap);
      }
   }

   private static void checkConsistencyOfNapAndSDP(ServerMBean server, HashMap napFullAddresses, NetworkAccessPointMBean nap, String napAddress, String napAddressAndPort) throws ConfigurationException {
      NetworkAccessPointMBean otherNap = (NetworkAccessPointMBean)napFullAddresses.get(napAddressAndPort);
      if (otherNap == null) {
         napFullAddresses.put(napAddressAndPort, nap);
      } else if (otherNap.isSDPEnabled() != nap.isSDPEnabled()) {
         throw new ConfigurationException(ServerLogger.logPortSDPConflictLoggable(nap.getName(), otherNap.getName(), napAddress + ":" + nap.getListenPort(), server.getName()).getMessage());
      }

   }

   private static void checkConsistencyOfNapAndDefaultChannel(ServerMBean server, HashSet addressAndPortMapOfDefaultChannels, NetworkAccessPointMBean nap, String napListenAddress, String napAddressAndPort) throws ConfigurationException {
      String napAddressWithEmptyListenAddress = "" + nap.getListenPort();
      if (addressAndPortMapOfDefaultChannels.contains(napAddressAndPort) || addressAndPortMapOfDefaultChannels.contains(napAddressWithEmptyListenAddress)) {
         throw new ConfigurationException(ServerLogger.logPortConflictLoggable(nap.getName(), nap.getProtocol() + "://" + napListenAddress + ":" + nap.getListenPort(), "Default Channel", server.getListenAddress() + ":" + server.getListenPort(), server.getName()).getMessage());
      }
   }

   private static HashSet populateAddressAndPortMapOfDefaultChannels(ServerMBean server, String[] serverAddr) {
      HashSet addressAndPortMap = new HashSet();

      for(int i = 0; i < serverAddr.length; ++i) {
         if (server.isListenPortEnabled()) {
            addressAndPortMap.add(serverAddr[i] + server.getListenPort());
         }

         if (server.getSSL().isListenPortEnabled()) {
            addressAndPortMap.add(serverAddr[i] + server.getSSL().getListenPort());
         }

         if (server.isAdministrationPortEnabled()) {
            addressAndPortMap.add(serverAddr[i] + server.getAdministrationPort());
         }
      }

      return addressAndPortMap;
   }

   private static String[] parseServerListenAddress(ServerMBean server, String localServerName) throws ConfigurationException {
      if (DEBUG) {
         p("parseServerListenAddress: " + server.getListenAddress());
      }

      String[] serverAddr;
      if (server.getListenAddress() != null && server.getListenAddress().length() > 0) {
         serverAddr = new String[]{parseInetAddress(server.getListenAddress(), localServerName.equals(server.getName()))};
      } else if (server instanceof ServerMBean && localServerName.equals(server.getName())) {
         InetAddress[] inets = AddressUtils.getIPAny();
         serverAddr = new String[inets.length];

         for(int i = 0; i < inets.length; ++i) {
            serverAddr[i] = inets[i].getHostAddress();
         }
      } else {
         serverAddr = new String[]{""};
      }

      if (DEBUG) {
         p("parseServerListenAddress: parsed to: " + Arrays.toString(serverAddr));
      }

      return serverAddr;
   }

   private static void checkConsistencyOfSSLPortConfiguration(ServerMBean server) throws ConfigurationException {
      if (server.getSSL().isEnabled() && server.isListenPortEnabled() && server.getListenPort() == server.getSSL().getListenPort()) {
         throw new ConfigurationException(ServerLogger.logPortConflictLoggable("default SSL Channel", server.getListenAddress() + ":" + server.getSSL().getListenPort(), "Default Channel", server.getListenAddress() + ":" + server.getListenPort(), server.getName()).getMessage());
      }
   }

   public static void logChannelConfiguration(ServerMBean server) {
      boolean admin = isAdminChannelEnabled(server);
      if (server.isListenPortEnabled()) {
         RJVMLogger.logChannelConfiguration(server.getName(), server.getListenAddress() == null ? "*" : server.getListenAddress() + ":" + server.getListenPort(), server.getExternalDNSName() == null ? "N/A" : "<all>://" + server.getExternalDNSName() + ":" + server.getListenPort(), server.isHttpdEnabled(), server.isTunnelingEnabled(), false, !admin, server.getResolveDNSName());
      }

      if (server.getSSL().isEnabled()) {
         RJVMLogger.logChannelConfiguration(server.getName(), server.getListenAddress() + ":" + server.getSSL().getListenPort() + " (SSL)", server.getExternalDNSName() == null ? "N/A" : server.getExternalDNSName() + " (SSL):" + server.getSSL().getListenPort(), server.isHttpdEnabled(), server.isTunnelingEnabled(), false, !admin, server.getResolveDNSName());
      }

      RJVMLogger.logChannelSettings(server.getName(), 50, server.getAcceptBacklog(), server.getLoginTimeoutMillis(), server.getMaxMessageSize(), server.getCompleteMessageTimeout(), server.getIdleConnectionTimeout(), server.getTunnelingClientTimeoutSecs(), server.getTunnelingClientPingSecs());
      NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();

      for(int i = 0; i < naps.length; ++i) {
         NetworkAccessPointMBean nap = naps[i];
         if (nap.isEnabled()) {
            RJVMLogger.logChannelConfiguration(nap.getName(), nap.getProtocol() + "://" + nap.getListenAddress() + ":" + nap.getListenPort(), nap.getProtocol() + "://" + nap.getPublicAddress() + ":" + nap.getPublicPort(), nap.isHttpEnabledForThisProtocol(), nap.isTunnelingEnabled(), nap.isOutboundEnabled(), !admin || nap.getProtocol().equalsIgnoreCase("ADMIN"), nap.getResolveDNSName());
            RJVMLogger.logChannelSettings(nap.getName(), nap.getChannelWeight(), nap.getAcceptBacklog(), nap.getLoginTimeoutMillis(), nap.getMaxMessageSize(), nap.getCompleteMessageTimeout(), nap.getIdleConnectionTimeout(), nap.getTunnelingClientTimeoutSecs(), nap.getTunnelingClientPingSecs());
         }
      }

   }

   public boolean isAdminServerAdminChannelEnabled() {
      RuntimeAccess rt = (RuntimeAccess)this.runtimeAccess.get();
      ServerMBean server = rt.getDomain().lookupServer(rt.getAdminServerName());
      return isAdminChannelEnabled(server);
   }

   public static boolean isAdminChannelEnabled(ServerMBean server) {
      if (ManagementService.getRuntimeAccess(kernelId).getDomain().isAdministrationPortEnabled()) {
         return true;
      } else {
         NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();

         for(int i = 0; i < naps.length; ++i) {
            if (naps[i].isEnabled() && naps[i].getProtocol().equalsIgnoreCase("ADMIN")) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isSSLChannelEnabled(ServerMBean server) {
      if (server.getSSL().isListenPortEnabled()) {
         return true;
      } else {
         NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();

         for(int i = 0; i < naps.length; ++i) {
            if (isNAPSecure(naps[i])) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isNAPSecure(NetworkAccessPointMBean nap) {
      byte qos = ProtocolManager.getProtocolByName(nap.getProtocol()).getQOS();
      return qos == 102 || qos == 103;
   }

   public static String getLocalAdministrationURL() {
      String url = getURLForProtocol(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      if (url == null) {
         url = getURLForProtocol(ProtocolManager.getDefaultProtocol());
         if (url == null) {
            url = getURLForProtocol(ProtocolManager.getDefaultSecureProtocol());
         }
      }

      return url;
   }

   public String getURL(Protocol protocol) {
      return getURLForProtocol(protocol);
   }

   private static String getURLForProtocol(Protocol protocol) {
      return createURL(ServerChannelManager.findLocalServerChannel(protocol));
   }

   public static String getIPv4URL(Protocol protocol) {
      return createURL(ServerChannelManager.findIPv4LocalServerChannel(protocol));
   }

   public static String getIPv6URL(Protocol protocol) {
      return createURL(ServerChannelManager.findIPv6LocalServerChannel(protocol));
   }

   public static String createCodebaseURL(Channel ch) {
      return ProtocolHelper.getCodebase(ch.supportsTLS(), ch.getPublicAddress(), ch.getPublicPort(), (String)null);
   }

   public String createCodeBaseURL(Channel ch) {
      return createCodebaseURL(ch);
   }

   public static String createClusterURL(ServerChannel ch) {
      return ClusterAddressCollaborator.Locator.locate().getClusterAddressURL(ch);
   }

   public static String createDefaultClusterURL() {
      return createClusterURL(ServerChannelManager.findDefaultLocalServerChannel());
   }

   public static String getClusterURL(ObjectOutput out) {
      if (out instanceof ServerChannelStream) {
         ServerChannel sc = ((ServerChannelStream)out).getServerChannel();
         if (sc != null) {
            return createClusterURL(sc);
         }
      }

      return createDefaultClusterURL();
   }

   public static boolean isMultipleReplicationChannelsConfigured(ServerMBean server) {
      String ports = server.getReplicationPorts();
      if (ports != null && ports.length() > 0) {
         ClusterMBean cluster = server.getCluster();
         if (cluster != null) {
            String repChannelName = cluster.getReplicationChannel();
            NetworkAccessPointMBean[] naps = server.getNetworkAccessPoints();
            NetworkAccessPointMBean[] var5 = naps;
            int var6 = naps.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               NetworkAccessPointMBean nap = var5[var7];
               if (nap.isEnabled() && nap.getName().equals(repChannelName)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isListenPortEnabled(String serverName) {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
      return server == null ? true : server.isListenPortEnabled();
   }

   public static void p(String s) {
      logger.debug("<Channel>: " + s);
   }

   static {
      DEBUG = logger.isDebugEnabled();
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
