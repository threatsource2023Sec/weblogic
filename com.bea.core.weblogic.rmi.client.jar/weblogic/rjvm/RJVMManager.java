package weblogic.rjvm;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandler;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.rmi.extensions.DisconnectMonitorList;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.EndPointFinder;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;

public final class RJVMManager implements PeerGoneListener, EndPointFinder {
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static boolean protocolRegistry = ensureInitialized();
   private static final int MAX_PROTOCOLS = 32;
   private static ProtocolHolder[] rjvmProtocols;
   private static final int MILLISECOND = 1000;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ConcurrentHashMap rjvmConnectionTimeoutMapping;
   private final ConcurrentHashMap lockTable;
   private static boolean cacheOnConnectAddress = Boolean.getBoolean("weblogic.rjvm.cacheonconnectaddress");
   private static boolean allowUnknownHost = Boolean.getBoolean("weblogic.rjvm.allowUnknownHost");
   private RJVMScavenger rjvmScav;
   private final ConcurrentHashMap table;
   private final ConcurrentHashMap synonymCache;
   private final ConcurrentHashMap rjvmCache;
   private String adminID;

   public static RJVMManager getRJVMManager() {
      return RJVMManager.SingletonMaker.rjvmManager;
   }

   public final void initialize() {
      RJVMEnvironment.getEnvironment().ensureInitialized();
      int idleTimeoutPeriod = RJVMEnvironment.getEnvironment().getRjvmIdleTimeout();
      if (idleTimeoutPeriod > 0) {
         this.rjvmScav = new RJVMScavenger(idleTimeoutPeriod);
         TimerManagerFactory.getTimerManagerFactory().getTimerManager("RJVMHeartbeats", "weblogic.kernel.System").schedule(this.rjvmScav, 1000L, 1000L);
      }

   }

   static boolean ensureInitialized() {
      rjvmProtocols = new ProtocolHolder[32];
      RJVMEnvironment.getEnvironment().registerRJVMProtocols();
      return true;
   }

   public static RJVM getLocalRJVM() {
      return LocalRJVM.getLocalRJVM();
   }

   private RJVMManager() {
      this.rjvmConnectionTimeoutMapping = new ConcurrentHashMap();
      this.lockTable = new ConcurrentHashMap();
      this.table = new ConcurrentHashMap();
      this.synonymCache = new ConcurrentHashMap();
      this.rjvmCache = new ConcurrentHashMap();
      RMIRuntime.getRMIRuntime().addEndPointFinder(this);
      DisconnectMonitorList dml = DisconnectMonitorListImpl.getDisconnectMonitorList();
      dml.addDisconnectMonitor(new DisconnectMonitorImpl());
   }

   public RJVM findOrCreate(JVMID id) {
      return this.findOrCreateInternal(id, true);
   }

   public RJVM find(JVMID id) {
      return this.findOrCreateInternal(id, false);
   }

   RJVMImpl findRemote(JVMID id) {
      return (RJVMImpl)this.findOrCreateInternal(id, false);
   }

   RJVMImpl findOrCreateRemote(JVMID id) {
      return (RJVMImpl)this.findOrCreateInternal(id, true);
   }

   public RJVM findOrCreate(InetAddress address, int port, String protocol, String channelName, int connectionTimeout, String partitionUrl) throws IOException {
      return this.findOrCreate(address.getCanonicalHostName(), address, port, protocol, channelName, connectionTimeout, partitionUrl);
   }

   public RJVM findOrCreate(String host, InetAddress address, int port, String protocol, String channelName, int connectionTimeout, String partitionUrl) throws IOException {
      return this.findOrCreateRemoteInternal(host, address, port, protocol, channelName, connectionTimeout, partitionUrl);
   }

   private RJVM record(RJVM rjvm, InetAddress address, int port, ServerChannel channel) {
      JVMID id = rjvm.getID();
      InetAddress canonicalAddress = id.address(allowUnknownHost);
      if (JVMID.DUMMY_IP.equals(canonicalAddress.getHostName())) {
         return rjvm;
      } else {
         int remotePort = channel == null ? port : id.getPort(channel.getProtocol());
         if (cacheOnConnectAddress) {
            if (address != null) {
               this.rjvmCache.put(new SynonymCacheKey(address, port), rjvm);
            }
         } else if (address != null && !address.equals(canonicalAddress) || address != null && port != remotePort) {
            this.synonymCache.put(new SynonymCacheKey(address, port), new SynonymCacheValue(canonicalAddress, port != remotePort ? remotePort : port));
         }

         synchronized(this.table) {
            if (!rjvm.isDead()) {
               this.table.put(id.identityWithChannel(), rjvm);
            }
         }

         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("RJVMs: [" + this.table.size() + ']');
         }

         return rjvm;
      }
   }

   public RJVM findRemoteRJVM(InetAddress address, int port, String protocolName) throws IOException {
      Protocol protocol = ProtocolManager.getProtocolByName(protocolName);
      if (protocol.isUnknown()) {
         throw new UnknownHostException("Unknown protocol: '" + protocolName + '\'');
      } else {
         ServerChannel channel = ServerChannelManager.findOutboundServerChannel(protocol, (String)null);
         if (channel == null) {
            throw new AssertionError("Could not find outbound channel for: " + protocol);
         } else {
            return this.findExisting(address, port, channel);
         }
      }
   }

   private RJVM findOrCreateRemoteInternal(String host, InetAddress address, int port, String protocolName, String channelName, int connectionTimeout, String partitionURL) throws IOException {
      Protocol protocol = ProtocolManager.getProtocolByName(protocolName);
      if (protocol.isUnknown()) {
         throw new UnknownHostException("Unknown protocol: '" + protocolName + '\'');
      } else {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("[RJVMManager.findOrCreateRemoteInternal] address = " + address + ", port = " + port + ", protocolName = " + protocolName + ", partitionURL = " + partitionURL);
         }

         if (RJVMEnvironment.getEnvironment().isServer()) {
            AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
            if (subject != null && !protocol.isSatisfactoryQOS(subject.getQOS())) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug("[RJVMManager.findOrCreateRemoteInternal] subject = " + subject + ", subject.getQOS = " + subject.getQOS() + ", protocol.isSatisfactoryQOS(subject.getQOS()) = " + protocol.isSatisfactoryQOS(subject.getQOS()));
               }

               if (!protocol.equals(ProtocolManager.getDefaultAdminProtocol())) {
                  throw new ConnectException("Cannot use outbound protocol \"" + protocol + "\", it does not have administrator privileges");
               }

               protocol = ProtocolHandlerAdmin.PROTOCOL_ADMIN;
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug("[RJVMManager.findOrCreateRemoteInternal] - setting protocol to admin");
               }
            }
         }

         ServerChannel channel = ServerChannelManager.findOutboundServerChannel(protocol, channelName);
         if (channel == null) {
            throw new AssertionError("Could not find outbound channel for: " + protocol + ", " + channelName + ", partitionURL = " + partitionURL);
         } else {
            RJVM res = this.findExisting(address, port, channel);
            if (res != null) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug("[RJVMManager.findOrCreateRemoteInternal] - Found an existing RJVM for URL : " + partitionURL);
               }

               return res;
            } else {
               Object lock;
               synchronized(this) {
                  String lockKey = channel.getChannelName() + address.toString() + port;
                  lock = this.lockTable.putIfAbsent(lockKey, new Object());
                  if (lock == null) {
                     lock = this.lockTable.get(lockKey);
                  }
               }

               synchronized(lock) {
                  res = this.findExisting(address, port, channel);
                  if (res != null) {
                     return res;
                  } else {
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logDebug("[RJVMManager.findOrCreateRemoteInternal] - Bootstrapping connection to: '" + address + ':' + port + "' using: '" + protocol + '\'' + " partitionURL: " + partitionURL);
                     }

                     ConnectionManager bootstrapConMan = ConnectionManager.create((RJVMImpl)null);
                     RJVM rjvm = bootstrapConMan.bootstrap(host, address, port, channel, connectionTimeout, (String)null, partitionURL);
                     return this.record(rjvm, address, port, channel);
                  }
               }
            }
         }
      }
   }

   private RJVM findOrCreateInternal(JVMID id, boolean create) {
      if (id.equals(JVMID.localID())) {
         return LocalRJVM.getLocalRJVM();
      } else if (id.equals(JVMID.localRemoteID())) {
         return LocalRemoteJVM.getLocalRemoteJVM();
      } else {
         RJVM res = (RJVM)this.table.get(id.identityWithChannel());
         if (res == null && create) {
            RJVMImpl res;
            synchronized(this.table) {
               res = (RJVM)this.table.get(id.identityWithChannel());
               if (res != null) {
                  return res;
               }

               res = new RJVMImpl(id, LocalRJVM.getLocalRJVM().getFinder());
               this.record(res, (InetAddress)null, -1, (ServerChannel)null);
            }

            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug("Created RJVM for: '" + id.identityWithChannel().toString() + '\'' + ", " + this.toString());
            }

            return res;
         } else {
            return res;
         }
      }
   }

   private RJVM findExisting(InetAddress address, int port, ServerChannel channel) {
      if (cacheOnConnectAddress) {
         return (RJVM)this.rjvmCache.get(new SynonymCacheKey(address, port));
      } else {
         SynonymCacheValue synonym = (SynonymCacheValue)this.synonymCache.get(new SynonymCacheKey(address, port));
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled() && synonym != null) {
            debugConnection.debug("Using Synonym :" + synonym + " to find the RJVM for address: " + address);
         }

         address = synonym == null ? address : synonym.address;
         port = synonym == null ? port : (synonym.port != -1 ? synonym.port : port);
         RJVM rjvm = null;
         Iterator var6 = this.table.values().iterator();

         while(var6.hasNext()) {
            RJVM r = (RJVM)var6.next();
            JVMID id = r.getID();
            if (id.matchAddressAndPort(address, port, channel.getProtocol())) {
               if (rjvm == null) {
                  rjvm = r;
               } else if (!r.isDead() && r.getCreationTime() > rjvm.getCreationTime()) {
                  if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                     RJVMLogger.logDebug("Take the newer one [" + r + "] and leave the older one [" + rjvm + "] without peer gone");
                  }

                  rjvm = r;
               }
            }
         }

         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Found RJVM " + rjvm);
         }

         return rjvm;
      }
   }

   public void peerGone(PeerGoneEvent ev) {
      RJVM rjvm = (RJVM)ev.getSource();
      synchronized(this.table) {
         this.table.remove(rjvm.getID().identityWithChannel());
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("RJVMs: [" + this.table.size() + ']');
         }

         if (this.adminID != null && this.adminID.equals(rjvm.getID().getDomainName() + rjvm.getID().getServerName())) {
            ServerIdentityManager.removeTransientAndPersistentIdentity(rjvm.getID());
         } else {
            ServerIdentityManager.removeIdentity(rjvm.getID());
         }

      }
   }

   private Collection getRJVMs() {
      return this.table.values();
   }

   public void setAdminID(String id) {
      this.adminID = id;
   }

   public String getAdminID() {
      return this.adminID;
   }

   public void suspend() {
      IOException ioe = new IOException("Server has been suspended forcefully");
      Iterator var3 = this.getRJVMs().iterator();

      while(true) {
         RJVMImpl rjvm;
         JVMID id;
         do {
            if (!var3.hasNext()) {
               return;
            }

            RJVM r = (RJVM)var3.next();
            rjvm = (RJVMImpl)r;
            id = rjvm.getID();
         } while(id != null && id.equals(JVMID.localID()));

         ConnectionManager cm = rjvm.findOrSetConMan((ConnectionManager)null);
         if (cm == null) {
            rjvm.peerGone(new IOException("Server has been suspended forcefully"));
         } else if (!cm.hasAdminConnection()) {
            rjvm.peerGone(ioe);
         }
      }
   }

   int getScavengeInterval() {
      return this.rjvmScav.getScavengeInterval();
   }

   public boolean claimHostID(HostID hostID) {
      return hostID instanceof JVMID;
   }

   public boolean claimServerURL(String url) {
      return url != null && (url.startsWith("t3") || url.startsWith("t3s"));
   }

   public EndPoint findOrCreateEndPoint(HostID hostID) {
      return hostID instanceof JVMID ? this.findOrCreateInternal((JVMID)hostID, true) : null;
   }

   public EndPoint findEndPoint(HostID hostID) {
      return hostID instanceof JVMID ? this.findOrCreateInternal((JVMID)hostID, false) : null;
   }

   public EndPoint findOrCreateEndPoint(String url) throws IOException {
      ClientServerURL serverURL = new ClientServerURL(url);
      return serverURL.findOrCreateRJVM();
   }

   public EndPoint findEndPoint(String url) throws IOException {
      ClientServerURL serverURL = new ClientServerURL(url);
      return serverURL.findRJVM();
   }

   public static void registerRJVMProtocol(byte protocolNum, ProtocolHandler handler, RJVMConnectionFactory factory) {
      if (protocolNum >= 32) {
         throw new AssertionError("Invalid protocol number " + protocolNum);
      } else {
         ProtocolHolder holder = new ProtocolHolder(handler, factory);
         rjvmProtocols[protocolNum] = holder;
      }
   }

   public RJVMConnectionFactory getConnectionFactory(byte protocolNum) {
      if (protocolNum >= 32) {
         return null;
      } else {
         return rjvmProtocols[protocolNum] == null ? null : rjvmProtocols[protocolNum].getConnectionFactory();
      }
   }

   public ProtocolHandler getProtocolHandler(byte protocolNum) {
      if (protocolNum >= 32) {
         return null;
      } else {
         return rjvmProtocols[protocolNum] == null ? null : rjvmProtocols[protocolNum].getProtocolHandler();
      }
   }

   public Protocol getProtocol(byte protocolNum) {
      ProtocolHandler handler = this.getProtocolHandler(protocolNum);
      return handler == null ? null : handler.getProtocol();
   }

   public String toString() {
      StringBuilder builder = new StringBuilder("Active RJVMs:\n");
      Iterator var2 = this.table.entrySet().iterator();

      Map.Entry entry;
      while(var2.hasNext()) {
         entry = (Map.Entry)var2.next();
         builder.append("Key : ").append(entry.getKey()).append("\tRJVM : ").append(entry.getValue()).append('\n');
      }

      builder.append("\nSynonyms:\n");
      var2 = this.synonymCache.entrySet().iterator();

      while(var2.hasNext()) {
         entry = (Map.Entry)var2.next();
         builder.append("Key : ").append(entry.getKey()).append('\t').append(entry.getValue()).append('\n');
      }

      return builder.toString();
   }

   protected static void p(String msg) {
      RJVMLogger.logDebug("<RJVMManager>: " + msg);
   }

   public void storeConnectionTimeout(ServerIdentity hostVM, long connectionTimeout) {
      if (hostVM != null) {
         this.rjvmConnectionTimeoutMapping.put(hostVM, connectionTimeout);
      }

   }

   public void removeConnectionTimeout(ServerIdentity hostVM) {
      if (hostVM != null) {
         this.rjvmConnectionTimeoutMapping.remove(hostVM);
      }

   }

   public Long getCachedJNDIConnectionTimeout(ServerIdentity hostVM) {
      return hostVM != null ? (Long)this.rjvmConnectionTimeoutMapping.get(hostVM) : null;
   }

   // $FF: synthetic method
   RJVMManager(Object x0) {
      this();
   }

   private static final class ProtocolHolder {
      private final ProtocolHandler handler;
      private final RJVMConnectionFactory connectionFactory;

      ProtocolHolder(ProtocolHandler handler, RJVMConnectionFactory factory) {
         this.handler = handler;
         this.connectionFactory = factory;
      }

      public ProtocolHandler getProtocolHandler() {
         return this.handler;
      }

      public RJVMConnectionFactory getConnectionFactory() {
         return this.connectionFactory;
      }
   }

   private static final class SynonymCacheValue extends InetAddressAndPort {
      SynonymCacheValue(InetAddress address, int port) {
         super(address, port);
      }

      public boolean equals(Object key) {
         return key instanceof SynonymCacheValue && super.equals(key);
      }
   }

   private static final class SynonymCacheKey extends InetAddressAndPort {
      SynonymCacheKey(InetAddress address, int port) {
         super(address, port);
      }

      public boolean equals(Object key) {
         return key instanceof SynonymCacheKey && super.equals(key);
      }
   }

   private abstract static class InetAddressAndPort {
      protected final InetAddress address;
      protected final int port;

      public InetAddressAndPort(InetAddress address, int port) {
         this.address = address;
         this.port = port;
      }

      public boolean equals(Object o) {
         InetAddressAndPort that = (InetAddressAndPort)o;
         return this.port == that.port && this.address.equals(that.address);
      }

      public int hashCode() {
         int result = this.address.hashCode();
         result = 31 * result + this.port;
         return result;
      }

      public String toString() {
         return "[address=" + this.address + ", port=" + this.port + ']';
      }
   }

   private static final class RJVMScavenger implements NakedTimerListener {
      private final int scavengeInterval;

      RJVMScavenger(int scavInterval) {
         int newScavInterval = 1;
         if (scavInterval >= 1000) {
            newScavInterval = scavInterval / 1000;
            if (scavInterval % 1000 != 0) {
               ++newScavInterval;
            }
         }

         this.scavengeInterval = newScavInterval;
      }

      public void timerExpired(Timer timer) {
         PeerGoneException pge = new PeerGoneException("Idle RJVM being shut down.");
         Iterator var3 = RJVMManager.getRJVMManager().getRJVMs().iterator();

         while(var3.hasNext()) {
            RJVM r = (RJVM)var3.next();
            RJVMImpl rjvm = (RJVMImpl)r;
            ConnectionManager cm = rjvm.findOrCreateConMan();
            if (rjvm.isScavengeable(this.scavengeInterval)) {
               MsgAbbrevOutputStream peerGoneMessage = cm.createPeerGoneMsg(JVMID.localID(), rjvm.getID(), cm.qosToChannel((byte)101), (byte)101);
               cm.sendMsg(peerGoneMessage);
               rjvm.peerGone(pge);
            }
         }

      }

      int getScavengeInterval() {
         return this.scavengeInterval;
      }
   }

   private static class SingletonMaker {
      private static RJVMManager rjvmManager = new RJVMManager();
   }
}
