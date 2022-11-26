package weblogic.rjvm;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownServiceException;
import java.rmi.ConnectException;
import java.rmi.UnmarshalException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.socket.UnrecoverableConnectException;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;

public abstract class ConnectionManager {
   private static final RJVMTextTextFormatter formatter = RJVMTextTextFormatter.getInstance();
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final DebugLogger debugRouting = DebugLogger.getDebugLogger("DebugRouting");
   private static final int DEFAULT_STREAM_POOL_SIZE = 5;
   protected static final int CONNECT_TO_ADMIN_PORT = 7938;
   protected static boolean isApplet = false;
   private static ConnectionManager appletRouter = null;
   public static final int DEFAULT_CONNECTION_TIMEOUT = 0;
   private static final String WEBLOGIC_JNDI_CONNECT_TIMEOUT = "weblogic.jndi.connectTimeout";
   private static final String WEBLOGIC_JNDI_CONNECT_TIMEOUT_DEPRECATED = "weblogic.jndi.requestTimeout";
   static final String PARTITION_QUERY = "/?partitionName=";
   private static final int bootstrapWaitPeriod = HeartbeatMonitor.periodLengthMillisNoDisable();
   private static boolean ignoreIncomingProtocol = false;
   private static int streamPoolSize = 5;
   protected final Object bootstrapResult = new Object();
   protected RJVMImpl bootstrapRJVM;
   private JVMID bootstrapJVMID;
   protected boolean bootstrapResponseReceived = false;
   protected RJVMImpl thisRJVM;
   private final ConcurrentHashMap pairedConn = new ConcurrentHashMap();
   private final ConcurrentHashMap dupConnections = new ConcurrentHashMap();
   private final ConcurrentHashMap partitionURLNameMapper = new ConcurrentHashMap();
   private final Map connectImpossible = new HashMap(9);
   public static final long RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS = initExpireTime();
   private ServerChannel lastChannelUsed;
   private boolean wasShutdown;
   protected ConnectionManager router;
   private boolean inUse = true;
   protected volatile boolean possibleMissedPeergone;
   private final Pool inStreamPool;
   private final Pool outStreamPool;

   protected static void setAppletRouter(ConnectionManager conMan) {
      if (conMan.thisRJVM != null && !conMan.thisRJVM.getID().equals(JVMID.localID())) {
         appletRouter = conMan;
      }

   }

   private static long initExpireTime() {
      long t = Long.getLong("weblogic.rjvm.reconnect.after.millis", 5000L);
      if (t < 0L) {
         t = 0L;
      }

      return t;
   }

   final synchronized void setRouter(ConnectionManager inRouter) {
      this.router = inRouter;
   }

   private void setInUse(boolean usage) {
      this.inUse = usage;
   }

   final boolean isInUse() {
      boolean b = this.inUse;
      this.setInUse(false);
      return b;
   }

   public static ConnectionManager create(RJVMImpl rjvm) {
      String className = RJVMEnvironment.getEnvironment().isServer() ? "weblogic.rjvm.ConnectionManagerServer" : "weblogic.rjvm.ConnectionManagerClient";
      if (RJVMEnvironment.getEnvironment().isServer()) {
         ignoreIncomingProtocol = Boolean.getBoolean("weblogic.system.IgnoreIncomingProtocol");
         streamPoolSize = Integer.getInteger("weblogic.system.StreamPoolSize", 5);
      }

      Class[] signature = new Class[]{RJVMImpl.class};
      Object[] params = new Object[]{rjvm};
      return (ConnectionManager)getInstanceDynamically(className, signature, params);
   }

   ConnectionManager(RJVMImpl rjvm) {
      this.inStreamPool = new StackPool(streamPoolSize);
      this.outStreamPool = new StackPool(streamPoolSize);
      this.thisRJVM = rjvm;
      this.router = null;
      this.wasShutdown = false;
   }

   final RJVMImpl bootstrap(String host, InetAddress address, int port, ServerChannel channel, int connectionTimeout, String partitionName, String partitionUrl) throws IOException {
      int[] ports = new int[9];
      int i = ports.length;

      while(true) {
         --i;
         if (i < 0) {
            ports[ProtocolManager.getRealProtocol(channel.getProtocol()).toByte()] = port;
            this.bootstrapJVMID = new JVMID(address, ports);
            this.bootstrapRJVM = null;
            this.bootstrap(host, this.bootstrapJVMID, channel, connectionTimeout, partitionName, partitionUrl);
            if (!this.bootstrapResponseReceived) {
               synchronized(this.bootstrapResult) {
                  if (!this.bootstrapResponseReceived) {
                     try {
                        this.bootstrapResult.wait((long)bootstrapWaitPeriod);
                        if (this.bootstrapRJVM == null) {
                           throw new IOException("Timed out while attempting to establish connection to :" + partitionUrl);
                        }
                     } catch (InterruptedException var13) {
                     }
                  }
               }
            }

            if (this.bootstrapResponseReceived) {
               if (this.bootstrapRJVM == null) {
                  RJVMLogger.logBootstrapException(partitionUrl);
               }

               this.convertConnectionToAdminQOS();
               return this.bootstrapRJVM;
            } else {
               StringBuilder message = new StringBuilder();
               message.append("Bootstrap to: ");
               message.append(address);
               message.append(':');
               message.append(port);
               message.append("' over: '");
               message.append(channel.getProtocol());
               if (this.wasShutdown) {
                  message.append("' failed due to the connection being shut down");
               } else {
                  this.shutdown();
                  message.append("' got an error or timed out while trying to connect to ").append(address).append(':').append(port);
               }

               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug(message.toString());
               }

               throw new ConnectException(message.toString());
            }
         }

         ports[i] = -1;
      }
   }

   private void convertConnectionToAdminQOS() {
      if (this.bootstrapRJVM != null && this.bootstrapRJVM.convertedToAdminQOS) {
         this.bootstrapRJVM.convertedToAdminQOS = false;
         ConnectionManager cm = this.bootstrapRJVM.findOrSetConMan((ConnectionManager)null);
         if (cm != null) {
            synchronized(cm) {
               if (!this.isConnectedInPairedConnTable() && !cm.wasShutdown) {
                  throw new AssertionError("No connections in ConnectionManager. Unable to set Admin QoS");
               }

               Iterator var3 = this.pairedConn.values().iterator();

               while(var3.hasNext()) {
                  MsgAbbrevJVMConnection connection = (MsgAbbrevJVMConnection)var3.next();
                  connection.setAdminQOS();
               }
            }
         }
      }

   }

   final MsgAbbrevJVMConnection bootstrap(String host, RJVM bootstrapJVM, ServerChannel channel, String partitionName, String partitionUrl) throws IOException {
      JVMID bootstrapJVMID = bootstrapJVM.getID().withPortFor(channel.getProtocol());
      int connectionTimeout = this.getConnectionTimeout(bootstrapJVMID, channel);
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug("timeout used for bootstrapping connection = " + connectionTimeout);
      }

      MsgAbbrevJVMConnection conn = this.bootstrap(host, bootstrapJVMID, channel, connectionTimeout, partitionName, partitionUrl);
      if (!this.bootstrapResponseReceived) {
         synchronized(this.bootstrapResult) {
            if (!this.bootstrapResponseReceived) {
               try {
                  this.bootstrapResult.wait((long)bootstrapWaitPeriod);
               } catch (InterruptedException var12) {
               }
            }
         }
      }

      if (!this.bootstrapResponseReceived) {
         if (this.wasShutdown) {
            throw new ConnectException("Bootstrap request to JVMID " + bootstrapJVMID + " failed due to the connection being shut down");
         } else {
            throw new ConnectException("Bootstrap request to JVMID " + bootstrapJVMID + " got an error or timed out");
         }
      } else {
         return conn;
      }
   }

   private MsgAbbrevJVMConnection bootstrap(String host, JVMID bootstrapID, ServerChannel channel, int connectionTimeout, String partitionName, String partitionUrl) throws IOException {
      this.bootstrapResponseReceived = false;
      Protocol protocol = channel.getProtocol();

      try {
         MsgAbbrevJVMConnection conn = partitionName != null ? this.findOrCreateConnection(host, channel, bootstrapID, connectionTimeout, partitionName) : this.findOrCreateConnection(host, channel, bootstrapID, connectionTimeout, partitionUrl, true);
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug(String.format("ConnectionManager Bootstrapping '%s', connection to: '%s' on port: '%d Partition URL :%s'", protocol, bootstrapID.address(), bootstrapID.getPort(protocol), partitionUrl));
         }

         return conn;
      } catch (ConnectException var13) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug2("Bootstrap unable to get a direct: '" + protocol + "'connection to: '" + bootstrapID + "' on port: '" + bootstrapID.getPort(protocol) + '\'', var13);
         }

         try {
            ConnectionManager router = this.findOrCreateRouter(channel, bootstrapID, partitionUrl);
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug("Bootstrapping using: '" + router + "' as the router to: '" + bootstrapID.address() + '\'');
            }

            return null;
         } catch (ConnectException var12) {
            String routedFailure = var12.detail != null ? var12.detail.getMessage() : var12.getMessage();
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug2("Bootstrap unable to get a routed: '" + protocol + "' connection to: '" + bootstrapID + "' on port: '" + bootstrapID.getPort(protocol) + '\'', var12);
            }

            throw new ConnectException(var13.getMessage() + "; " + routedFailure, var12);
         }
      }
   }

   final void sendMsg(MsgAbbrevOutputStream outputStream) {
      JVMMessage header = outputStream.getMessageHeader();
      if (this.wasShutdown) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Attempt to sendMsg using a closed connection");
         }

         this.gotExceptionSending(header, new ConnectException("Attempt to sendMsg using a closed connection"));
      } else {
         JVMID destJVMID = this.thisRJVM.getID();
         ServerChannel channel = outputStream.getServerChannel();
         if (channel == null) {
            channel = this.qosToChannel(header.QOS);
         }

         JVMMessage.Command cmd = header.cmd;
         if (cmd != JVMMessage.Command.CMD_INTERNAL) {
            this.setInUse(true);
         }

         String partitionName = outputStream.getPartitionName();
         int timeout = this.getConnectionTimeout(destJVMID, channel);
         MsgAbbrevJVMConnection connection = outputStream.getPhantomConnection();

         try {
            if (connection == null) {
               connection = this.getConnectionInPairedConnTable(partitionName, channel.getProtocol().toByte());
            }

            if (connection == null) {
               connection = this.findOrCreateConnection(channel, destJVMID, partitionName);
            } else {
               connection.waitIdentify(timeout);
            }
         } catch (UnrecoverableConnectException var15) {
            this.gotExceptionSending(header, var15);
            return;
         } catch (ConnectException var16) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug3("Unable to get a direct: '" + channel.getProtocol() + "' connection to: '" + destJVMID + "' while sending out a message", var16);
            }
         }

         if (connection == null) {
            byte qos = channel.getProtocol().getQOS();
            ServerChannel newChannel = this.qosToChannel(qos);
            if (!channel.equals(newChannel)) {
               try {
                  connection = this.findOrCreateConnection(newChannel, destJVMID, partitionName);
               } catch (UnrecoverableConnectException var13) {
                  this.gotExceptionSending(header, var13);
                  return;
               } catch (ConnectException var14) {
                  if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                     RJVMLogger.logDebug3("Retry failed. Unable to get a direct: '" + channel.getProtocol() + "' connection to: '" + destJVMID + "' while sending out a message", var14);
                  }
               }
            }
         }

         if (connection == null) {
            try {
               ConnectionManager router = this.findOrCreateRouter(channel, destJVMID);
               outputStream.setServerChannel(router.qosToChannel(header.QOS));
               header.hasJVMIDs = true;
               router.sendMsg(outputStream);
            } catch (ConnectException var12) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug2("Unable to get a routed: '" + channel.getProtocol() + "', connection to '" + destJVMID + "' on port: '" + destJVMID.getPort(channel.getProtocol()) + "' while sending out a message", var12);
               }

               this.gotExceptionSending(header, new ConnectException("Unable to get direct or routed connection to: '" + destJVMID + "'", var12));
            }
         } else {
            connection.sendMsg(outputStream);
         }
      }

   }

   final void sendHeartbeatMsg() throws IOException {
      byte useQOS = 101;
      byte protocolNum = 9;
      Channel remoteChannel = this.thisRJVM.getRemoteChannel();
      int port = remoteChannel.getPublicPort();
      if (port > -1) {
         int idx = this.thisRJVM.getID().getConfiguredProtocolIndex();
         if (idx > -1) {
            Protocol p = ProtocolManager.getProtocolByIndex(idx);
            protocolNum = p.toByte();
            useQOS = p.getQOS();
         }
      } else {
         Protocol p = ProtocolManager.getProtocolByName(remoteChannel.getProtocolPrefix());
         protocolNum = p.toByte();
         useQOS = p.getQOS();
      }

      MsgAbbrevJVMConnection conn = this.getHeartBeatConnectionInPairedConnTable("DOMAIN", protocolNum);
      MsgAbbrevOutputStream outputStream = this.createHeartbeatMsg(conn.getRemotePartitionName(), useQOS);
      if (this.wasShutdown) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Attempt to send HBMsg using a closed connection");
         }

         JVMMessage header = outputStream.getMessageHeader();
         this.gotExceptionSending(header, new ConnectException("Attempt to send HBMsg using a closed connection"));
      } else {
         conn.sendMsg(outputStream);
      }

   }

   private void sendPeerGoneMsgToSrc(JVMMessage msg) {
      RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(msg.src);
      theRJVM.findOrCreateConMan().sendPeerGoneMsg(msg.dest, msg.src, msg.QOS, (MsgAbbrevJVMConnection)null);
   }

   protected final void sendPeerGoneMsg(JVMID from, JVMID to, byte QOS, MsgAbbrevJVMConnection con) {
      if (!this.wasShutdown) {
         ServerChannel channel = null;
         MsgAbbrevJVMConnection connection = con;

         try {
            if (connection == null) {
               channel = this.qosToChannel(QOS);
               connection = this.findOrCreateConnection(channel, to, "DOMAIN");
            } else {
               channel = con.getChannel();
            }
         } catch (UnrecoverableConnectException var10) {
            RJVMLogger.logTargetUnreach();
            return;
         } catch (ConnectException var11) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug2("Unable to get a direct: '" + channel.getProtocol() + "' connection to: '" + to + "' on port: '" + to.getPort(channel.getProtocol()) + "' while attempting to send a peer gone message", var11);
            }
         }

         MsgAbbrevOutputStream outputStream = this.createPeerGoneMsg(from, to, channel, QOS);
         if (connection == null) {
            try {
               ConnectionManager router = this.findOrCreateRouter(channel, to);
               router.sendMsg(outputStream);
            } catch (ConnectException var9) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug2("Unable to get a routed: '" + channel.getProtocol() + "', connection to '" + to + "' on port: '" + to.getPort(channel.getProtocol()) + "' while attempting to send a peer gone message", var9);
               }

               RJVMLogger.logTargetGone();
            }
         } else {
            connection.sendMsg(outputStream);
         }

      }
   }

   final void cancelIO(JVMID rjvm) {
      Iterator var2 = this.pairedConn.values().iterator();

      while(var2.hasNext()) {
         MsgAbbrevJVMConnection connection = (MsgAbbrevJVMConnection)var2.next();
         connection.cancelIO(rjvm);
      }

   }

   protected synchronized void shutdown() {
      this.wasShutdown = true;
      JVMID destination = this.thisRJVM == null ? this.bootstrapJVMID : this.thisRJVM.getID();
      this.cancelIO(destination);
      Iterator var2 = this.pairedConn.values().iterator();

      while(var2.hasNext()) {
         MsgAbbrevJVMConnection connection = (MsgAbbrevJVMConnection)var2.next();
         connection.ensureForceClose();
         this.removeConnection(connection);
         this.removeDuplicateConnection(connection);
      }

      if (this.router != null) {
         this.router.cancelIO(destination);
      }

      this.bootstrapResponseReceived = true;
      synchronized(this.bootstrapResult) {
         this.bootstrapResult.notify();
      }
   }

   ServerChannel protocolToChannel(Protocol protocol) {
      Protocol realProtocol = ProtocolManager.getRealProtocol(protocol);
      if (this.lastChannelUsed != null && ProtocolManager.getRealProtocol(this.lastChannelUsed.getProtocol()).equals(realProtocol)) {
         return this.lastChannelUsed;
      } else {
         Iterator var3 = this.pairedConn.values().iterator();

         ServerChannel c;
         do {
            if (!var3.hasNext()) {
               return ServerChannelManager.findOutboundServerChannel(realProtocol);
            }

            MsgAbbrevJVMConnection connection = (MsgAbbrevJVMConnection)var3.next();
            c = connection.getChannel();
         } while(!ProtocolManager.getRealProtocol(c.getProtocol()).equals(realProtocol));

         return c;
      }
   }

   final ServerChannel qosToChannel(byte QOS) {
      ServerChannel channel = this.findExistingChannel(QOS);
      if (channel != null) {
         return channel;
      } else {
         return QOS == 103 ? ServerChannelManager.findOutboundServerChannel(RJVMManager.getRJVMManager().getProtocol((byte)6)) : ServerChannelManager.findOutboundServerChannel(ProtocolManager.getProtocol(QOS));
      }
   }

   private ServerChannel findExistingChannel(byte QOS) {
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug(this.toString() + " looking for channel for QOS: " + QOS);
      }

      Iterator var2;
      MsgAbbrevJVMConnection connection;
      ServerChannel c;
      switch (QOS) {
         case 101:
         case 102:
            if (this.lastChannelUsed != null && this.lastChannelUsed.getProtocol().isSatisfactoryQOS(QOS)) {
               return this.lastChannelUsed;
            } else {
               var2 = this.pairedConn.values().iterator();

               do {
                  if (!var2.hasNext()) {
                     return null;
                  }

                  connection = (MsgAbbrevJVMConnection)var2.next();
                  c = connection.getChannel();
               } while(connection.getQOS() == 103 || !c.getProtocol().isSatisfactoryQOS(QOS));

               return c;
            }
         case 103:
            var2 = this.pairedConn.values().iterator();

            do {
               if (!var2.hasNext()) {
                  return null;
               }

               connection = (MsgAbbrevJVMConnection)var2.next();
               c = connection.getChannel();
            } while(connection.getQOS() != 103);

            return c;
         default:
            throw new AssertionError("Unknown QOS: '" + QOS + '\'');
      }
   }

   final void dispatch(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream incomingMessage) {
      if (this.wasShutdown) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("We may have dropped JVMMessages since this ConnectionManager is already shutdown. ConnectionManager: [" + this + "] connection : [" + connection + ']');
         }

         if (connection.getMessagesSentCount() < 2L && connection.getMessagesReceivedCount() < 3L) {
            synchronized(this) {
               connection.close();
            }
         }

      } else {
         JVMMessage header = incomingMessage.getMessageHeader();
         if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug("Received: '" + header + "' on '" + connection.getChannel() + '\'');
         }

         if (ignoreIncomingProtocol) {
            header.QOS = 101;
         }

         if (header.cmd != JVMMessage.Command.CMD_INTERNAL) {
            this.setInUse(true);
         }

         switch (header.cmd) {
            case CMD_NO_ROUTE_IDENTIFY_REQUEST:
            case CMD_IDENTIFY_REQUEST:
            case CMD_IDENTIFY_REQUEST_CSHARP:
               this.handleIdentifyRequest(connection, incomingMessage);
               break;
            case CMD_TRANSLATED_IDENTIFY_RESPONSE:
            case CMD_IDENTIFY_RESPONSE:
               this.handleIdentifyResponse(connection, incomingMessage);
               break;
            case CMD_PEER_GONE:
               this.handlePeerGone(connection, incomingMessage);
               break;
            case CMD_REQUEST_CLOSE:
               this.removeConnection(connection);
               break;
            case CMD_INTERNAL:
            case CMD_ONE_WAY:
            case CMD_REQUEST:
            case CMD_RESPONSE:
            case CMD_ERROR_RESPONSE:
               this.handleRJVM(connection, incomingMessage);
               break;
            default:
               Exception e = new UnmarshalException("Illegal command code: '" + header.cmd + '\'');
               RJVMLogger.logUnmarshal2(e);
               this.gotExceptionReceiving(connection, e);
         }

         JVMID srcId = header.src;
         ServerIdentity id = ServerIdentityManager.findServerIdentity(srcId.getDomainName(), srcId.getServerName());
         if (id == null) {
            if (srcId.isServer() && header.cmd != JVMMessage.Command.CMD_PEER_GONE) {
               ServerIdentityManager.recordIdentity(srcId);
            }
         } else {
            ServerIdentity transitId = ServerIdentityManager.findServerIdentityFromTransient(id.getTransientIdentity());
            if (transitId == null && header.cmd != JVMMessage.Command.CMD_PEER_GONE) {
               ServerIdentityManager.recordIdentity(srcId);
            }
         }

      }
   }

   public void messageReceived() {
      if (this.thisRJVM != null) {
         this.thisRJVM.messageReceived();
      }

   }

   protected static int readRemotePeriodLength(MsgAbbrevInputStream in) {
      int remotePeriodLength;
      try {
         remotePeriodLength = in.readInt();
      } catch (IOException var3) {
         RJVMLogger.logBadInterval();
         remotePeriodLength = HeartbeatMonitor.periodLengthMillis();
      }

      return remotePeriodLength;
   }

   protected static byte[] readPublickey(MsgAbbrevInputStream in) {
      try {
         int length = in.readInt();
         if (length == 0) {
            return null;
         } else {
            byte[] publicKey = new byte[length];
            in.readFully(publicKey);
            return publicKey;
         }
      } catch (IOException var3) {
         throw new AssertionError(var3);
      }
   }

   protected static PeerInfo readDotNetClientPeerInfo(MsgAbbrevInputStream in) {
      try {
         int major = in.readInt();
         int minor = in.readInt();
         int servicePack = in.readInt();
         int rollingPatch = in.readInt();
         boolean temporaryPatch = in.readBoolean();
         if (major >= 12 && (major != 12 || minor >= 1 && (minor != 1 || servicePack > 1))) {
            try {
               in.readObject();
            } catch (ClassNotFoundException var7) {
            }

            return new PeerInfo(major, minor, servicePack, rollingPatch, in.readInt(), temporaryPatch, (PackageInfo[])null);
         } else {
            return new PeerInfo(major, minor, servicePack, rollingPatch, 0, temporaryPatch, (PackageInfo[])null);
         }
      } catch (IOException var8) {
         throw new AssertionError(var8);
      }
   }

   protected static PeerInfo readPeerInfo(MsgAbbrevInputStream in) {
      try {
         return (PeerInfo)in.readObjectFromPreDiabloPeer();
      } catch (ClassNotFoundException | IOException var2) {
         throw new AssertionError(var2);
      }
   }

   protected static ClusterInfo readClusterInfo(MsgAbbrevInputStream in, PeerInfo pi, JVMID id) {
      return ClusterInfoHelper.readClusterInfo(in, pi, id);
   }

   abstract void handleRJVM(MsgAbbrevJVMConnection var1, MsgAbbrevInputStream var2);

   abstract void handleIdentifyRequest(MsgAbbrevJVMConnection var1, MsgAbbrevInputStream var2);

   abstract void handleIdentifyResponse(MsgAbbrevJVMConnection var1, MsgAbbrevInputStream var2);

   abstract void handlePeerGone(MsgAbbrevJVMConnection var1, MsgAbbrevInputStream var2);

   protected final void shouldNeverHappen(MsgAbbrevJVMConnection connection, String problem) {
      RJVMLogger.logClose(connection.toString(), problem);
      this.gotExceptionReceiving(connection, new IOException(problem));
   }

   final void gotExceptionReceiving(MsgAbbrevJVMConnection errConnection, Throwable e) {
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug2("Error on connection: '" + errConnection + '\'', e);
      }

      if (this.thisRJVM != null) {
         MsgAbbrevJVMConnection existingConnection = this.getConnectionInPairedConnTable(errConnection);
         if (existingConnection == errConnection) {
            boolean isLastConnection = false;
            synchronized(this) {
               this.removeConnection(existingConnection);
               if (!this.isConnectedInPairedConnTable()) {
                  isLastConnection = true;
               }
            }

            if (isLastConnection) {
               this.thisRJVM.gotExceptionReceiving(e, errConnection.getProtocol().getProtocolName());
            }
         }
      } else {
         this.shutdown();
      }

   }

   public final void gotExceptionSending(MsgAbbrevJVMConnection connection, JVMMessage[] outstanding, IOException e) {
      boolean sendExceptionToThisRjvm = false;
      HashMap alreadySent = new HashMap();
      JVMMessage[] var6 = outstanding;
      int var7 = outstanding.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         JVMMessage msg = var6[var8];
         if (msg != null) {
            if (!alreadySent.containsKey(msg.dest)) {
               if (msg.src.equals(JVMID.localID())) {
                  sendExceptionToThisRjvm = true;
                  if (!msg.dest.equals(this.thisRJVM == null ? this.bootstrapJVMID : this.thisRJVM.getID())) {
                     RJVMManager.getRJVMManager().findOrCreateRemote(msg.dest).gotExceptionSending(outstanding, connection.getRemotePartitionName(), e);
                     alreadySent.put(msg.dest, Boolean.TRUE);
                  }
               } else {
                  this.sendPeerGoneMsgToSrc(msg);
                  alreadySent.put(msg.dest, Boolean.TRUE);
               }
            }

            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               if (msg.src.equals(JVMID.localID())) {
                  RJVMLogger.logDebug2("Error sending JVMMessage from: '" + JVMID.localID() + "' to: '" + msg.dest + "' on connection: '" + connection + '\'', e);
               } else {
                  RJVMLogger.logDebug2("Error routing JVMMessage from: '" + msg.src + "' to: '" + msg.dest + "' on connection: '" + connection + '\'', e);
               }
            }
         }
      }

      if (sendExceptionToThisRjvm) {
         String pn = "DOMAIN";
         if (connection != null) {
            pn = connection.getRemotePartitionName();
         }

         if (this.thisRJVM != null) {
            this.thisRJVM.gotExceptionSending(outstanding, pn, e);
         } else if (this.bootstrapRJVM != null) {
            this.bootstrapRJVM.gotExceptionSending(outstanding, pn, e);
         }
      }

   }

   final void gotExceptionSending(JVMMessage msg, IOException e) {
      JVMMessage[] outstanding = new JVMMessage[]{msg};
      this.gotExceptionSending((MsgAbbrevJVMConnection)null, outstanding, e);
   }

   private synchronized void removeConnection(MsgAbbrevJVMConnection connection) {
      connection.close();
      this.possibleMissedPeergone = false;
      ServerChannel channel = connection.getChannel();
      if (this.lastChannelUsed != null && this.lastChannelUsed.equals(channel)) {
         this.lastChannelUsed = null;
      }

      this.rmConnectionInPairedConnTable(connection);
      JVMID destination = this.thisRJVM == null ? this.bootstrapJVMID : this.thisRJVM.getID();
      JVMID me = JVMID.localID();
      if (!me.isServer() && me.equals(destination)) {
         me.setRouter((JVMID)null);
      }

   }

   private void removeDuplicateConnection(MsgAbbrevJVMConnection connection) {
      MsgAbbrevJVMConnection duplicate = (MsgAbbrevJVMConnection)this.dupConnections.remove(connection.getLocalPartitionName() + connection.getRemotePartitionName());
      if (duplicate != null && connection != duplicate) {
         JVMID destination = this.thisRJVM == null ? this.bootstrapJVMID : this.thisRJVM.getID();
         duplicate.cancelIO(destination);
         duplicate.ensureForceClose();
         duplicate.close();
      }
   }

   protected final MsgAbbrevJVMConnection getOrMakeConnection(byte QOS, String partitionName) throws UnrecoverableConnectException {
      MsgAbbrevJVMConnection connection = null;
      ServerChannel channel = this.qosToChannel(QOS);

      try {
         connection = this.findOrCreateConnection(channel, this.thisRJVM.getID(), partitionName == null ? "DOMAIN" : partitionName);
      } catch (ConnectException var6) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug2("Unable to get a: '" + channel.getProtocol() + "' connection to: '" + this.thisRJVM.getID() + "' on port: '" + this.thisRJVM.getID().getPort(channel.getProtocol()) + "' while getting at  the routing connection", var6);
         }
      }

      return connection;
   }

   boolean hasConnection(MsgAbbrevJVMConnection conn) {
      return this.pairedConn.containsValue(conn);
   }

   protected final void cleanShutdown(MsgAbbrevJVMConnection connection) {
      this.cleanShutdown(connection, false);
   }

   protected final void cleanShutdown(MsgAbbrevJVMConnection connection, boolean shouldSendCloseMsg) {
      try {
         if (this.thisRJVM != null && shouldSendCloseMsg) {
            MsgAbbrevOutputStream outputStream = this.createCloseMsg(connection.getProtocol().getQOS(), connection.getRemotePartitionName());
            connection.sendMsg(outputStream, true);
         }
      } catch (Exception var7) {
         RJVMLogger.logCloseError(var7);
      } finally {
         connection.close();
      }

   }

   private synchronized MsgAbbrevJVMConnection findOrCreateConnection(ServerChannel channel, JVMID destJVMID, String partitionName) throws UnrecoverableConnectException, ConnectException {
      int timeout = this.getConnectionTimeout(destJVMID, channel);
      JVMID connectingTo = destJVMID.isClient() ? destJVMID.getRouter() : destJVMID;
      return this.findOrCreateConnection(connectingTo.getAddress(), channel, destJVMID, timeout, partitionName);
   }

   private int getCachedConnectionTimeout(JVMID destJVMID, ServerChannel channel) {
      int timeout = channel.getConnectTimeout() * 1000;
      if (destJVMID != null) {
         Long obj = RJVMManager.getRJVMManager().getCachedJNDIConnectionTimeout(destJVMID);
         if (obj != null) {
            timeout = obj.intValue();
         }
      }

      return timeout;
   }

   private MsgAbbrevJVMConnection getJVMConnectionByURL(String partitionURL, byte protocolNum) {
      String partitionName = this.getPartitionNameByURL(partitionURL);
      return partitionName == null ? null : this.getJVMConnectionByName(partitionName, protocolNum);
   }

   private MsgAbbrevJVMConnection getJVMConnectionByName(String partitionName, byte protocolNum) {
      return this.getConnectionInPairedConnTable(partitionName, protocolNum);
   }

   private synchronized MsgAbbrevJVMConnection findOrCreateConnection(String host, ServerChannel channel, JVMID destJVMID, int connectionTimeout, String partitionUrl, boolean ignore) throws UnrecoverableConnectException, ConnectException {
      if (channel == null) {
         throw new ConnectException("No configured outbound channel on this server");
      } else {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Looking for a new connection for channel " + channel + " to remote server " + destJVMID + " active connections: " + this.pairedConn + " URLToName Mapping: " + this.partitionURLNameMapper + " partitionUrl: " + partitionUrl);
         }

         synchronized(this) {
            byte protocolNum = channel.getProtocol().toByte();
            MsgAbbrevJVMConnection connection = this.getJVMConnectionByURL(partitionUrl, protocolNum);
            if (connection != null) {
               return connection;
            } else {
               if (this.isPreDiabloPeer()) {
                  connection = this.getJVMConnectionByURL(partitionUrl, protocolNum);
                  if (connection != null) {
                     return connection;
                  }
               }

               if (!destJVMID.isServer()) {
                  throw new ConnectException("Destination " + destJVMID.getAddress() + ", " + destJVMID.getPort() + " is not a server");
               } else {
                  Long keyAddedAt = (Long)this.connectImpossible.get(channel);
                  if (keyAddedAt != null) {
                     if (RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS <= 0L || keyAddedAt + RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS > System.currentTimeMillis()) {
                        throw new ConnectException("Destination not reachable using: '" + channel + '\'');
                     }

                     if (KernelStatus.DEBUG && debugRouting.isDebugEnabled()) {
                        RJVMLogger.logDebug("Removing channel '" + channel + "' as key expiration is set to " + RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS + '\'');
                     }

                     this.connectImpossible.remove(channel);
                  }

                  InetAddress address = destJVMID.address();
                  Protocol protocol = channel.getProtocol();
                  int port = destJVMID.getPort(protocol);
                  if (port == -1) {
                     this.connectImpossible.put(channel, System.currentTimeMillis());
                     throw new ConnectException("No known valid port for: " + channel);
                  } else {
                     label234: {
                        MsgAbbrevJVMConnection var10000;
                        try {
                           connection = createConnection(protocol, host, address, port, channel, destJVMID, connectionTimeout, partitionUrl);
                           MsgAbbrevJVMConnection c = this.getConnectionInPairedConnTable(connection);
                           if (c == null) {
                              if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                                 RJVMLogger.logDebug("New connection for protocol " + protocol + ", remote address " + address + ", remote port " + port + ", over channel " + connection.getChannel().getChannelName());
                              }
                              break label234;
                           }

                           this.savePartitionMappings(connection);
                           connection.close();
                           this.bootstrapResponseReceived = true;
                           var10000 = c;
                        } catch (UnrecoverableConnectException var27) {
                           if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                              RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var27);
                           }

                           this.connectImpossible.put(channel, System.currentTimeMillis());
                           throw var27;
                        } catch (ConnectException var28) {
                           if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                              RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var28);
                           }

                           this.connectImpossible.put(channel, System.currentTimeMillis());
                           throw var28;
                        } catch (IOException var29) {
                           if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                              RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var29);
                           }

                           this.connectImpossible.put(channel, System.currentTimeMillis());
                           throw new ConnectException(formatter.msgDestinationUnreachable(address.getHostAddress(), port), var29);
                        }

                        return var10000;
                     }

                     connection.beginIdentify();
                     connection.setDispatcher(this, false);
                     JVMMessage.Command cmd = JVMMessage.Command.CMD_IDENTIFY_REQUEST;
                     if (connection.isDownGrade()) {
                        cmd = JVMMessage.Command.CMD_NO_ROUTE_IDENTIFY_REQUEST;
                     }

                     MsgAbbrevOutputStream msg = this.createIdentifyMsg(destJVMID, protocol.getQOS(), cmd, connection.getChannel(), (PeerInfo)null);

                     MsgAbbrevJVMConnection var16;
                     try {
                        connection.sendMsg(msg);
                        var16 = connection;
                     } catch (SecurityException var25) {
                        isApplet = true;
                        this.connectImpossible.put(connection.getChannel(), System.currentTimeMillis());
                        connection.close();
                        throw new ConnectException(formatter.msgDestinationUnreachable(address.getHostAddress(), port), var25);
                     } finally {
                        connection.endIdentify();
                     }

                     return var16;
                  }
               }
            }
         }
      }
   }

   private synchronized MsgAbbrevJVMConnection findOrCreateConnection(String host, ServerChannel channel, JVMID destJVMID, int connectionTimeout, String partitionName) throws UnrecoverableConnectException, ConnectException {
      if (channel == null) {
         throw new ConnectException("No configured outbound channel on this server");
      } else {
         synchronized(this) {
            byte protocolNum = channel.getProtocol().toByte();
            MsgAbbrevJVMConnection connection = this.getJVMConnectionByName(partitionName, protocolNum);
            if (connection != null) {
               return connection;
            } else if (!destJVMID.isServer()) {
               throw new ConnectException("Destination " + destJVMID.getAddress() + ", " + destJVMID.getPort() + " is not a server");
            } else {
               Long keyAddedAt = (Long)this.connectImpossible.get(channel);
               if (keyAddedAt != null) {
                  if (RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS <= 0L || keyAddedAt + RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS > System.currentTimeMillis()) {
                     throw new ConnectException("Destination not reachable using: '" + channel + '\'');
                  }

                  if (KernelStatus.DEBUG && debugRouting.isDebugEnabled()) {
                     RJVMLogger.logDebug("Removing channel '" + channel + "' as key expiration is set to " + RJVM_RECONNECT_COOL_OFF_PERIOD_MILLIS + '\'');
                  }

                  this.connectImpossible.remove(channel);
               }

               InetAddress address = destJVMID.address();
               Protocol protocol = channel.getProtocol();
               int port = destJVMID.getPort(protocol);
               if (port == -1) {
                  this.connectImpossible.put(channel, System.currentTimeMillis());
                  throw new ConnectException("No known valid port for: " + channel);
               } else {
                  try {
                     connection = createConnection(protocol, host, address, port, channel, destJVMID, connectionTimeout, getPartitionURLByName(partitionName));
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logDebug("New connection for protocol " + protocol + ", remote address " + address + ", remote port " + port + ", over channel " + connection.getChannel().getChannelName());
                     }
                  } catch (UnrecoverableConnectException var26) {
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var26);
                     }

                     this.connectImpossible.put(channel, System.currentTimeMillis());
                     throw var26;
                  } catch (ConnectException var27) {
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var27);
                     }

                     this.connectImpossible.put(channel, System.currentTimeMillis());
                     throw var27;
                  } catch (IOException var28) {
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logConnectingFailureWarning(protocol.toString(), address.getHostAddress(), port, var28);
                     }

                     this.connectImpossible.put(channel, System.currentTimeMillis());
                     throw new ConnectException(formatter.msgDestinationUnreachable(address.getHostAddress(), port), var28);
                  }

                  connection.beginIdentify();
                  connection.setDispatcher(this, false);
                  JVMMessage.Command cmd = JVMMessage.Command.CMD_IDENTIFY_REQUEST;
                  if (connection.isDownGrade()) {
                     cmd = JVMMessage.Command.CMD_NO_ROUTE_IDENTIFY_REQUEST;
                  }

                  MsgAbbrevOutputStream msg = this.createIdentifyMsg(destJVMID, protocol.getQOS(), cmd, connection.getChannel(), (PeerInfo)null);

                  MsgAbbrevJVMConnection var15;
                  try {
                     connection.sendMsg(msg);
                     var15 = connection;
                  } catch (SecurityException var24) {
                     isApplet = true;
                     this.connectImpossible.put(connection.getChannel(), System.currentTimeMillis());
                     connection.close();
                     throw new ConnectException(formatter.msgDestinationUnreachable(address.getHostAddress(), port), var24);
                  } finally {
                     connection.endIdentify();
                  }

                  return var15;
               }
            }
         }
      }
   }

   private synchronized ConnectionManager findOrCreateRouter(ServerChannel channel, JVMID destJVMID) throws ConnectException {
      return this.findOrCreateRouter(channel, destJVMID, (String)null);
   }

   private synchronized ConnectionManager findOrCreateRouter(ServerChannel channel, JVMID destJVMID, String partitionUrl) throws ConnectException {
      if (this.router != null && this.router.isShutdownDown()) {
         this.router = null;
      }

      if (this.router == null) {
         if (isApplet) {
            if (appletRouter.getJVMID().equals(destJVMID)) {
               throw new ConnectException(formatter.msgNoRouter());
            }

            this.router = appletRouter;
            if (KernelStatus.DEBUG && debugRouting.isDebugEnabled()) {
               RJVMLogger.logDebug("Electing applet default: '" + this.router + "' as the router to: '" + destJVMID + '\'');
            }
         } else if (destJVMID.getRouter() != null && !destJVMID.getRouter().equals(JVMID.localID())) {
            this.router = RJVMManager.getRJVMManager().findOrCreateRemote(destJVMID.getRouter()).findOrCreateConMan();
            if (this.router == this) {
               this.router = null;
               throw new ConnectException(formatter.msgNoRouter());
            }
         }

         if (this.router == null) {
            throw new ConnectException(formatter.msgNoRouter());
         }

         byte QOS = channel.getProtocol().getQOS();
         MsgAbbrevOutputStream outputStream = this.createIdentifyMsg(destJVMID, QOS, JVMMessage.Command.CMD_IDENTIFY_REQUEST, this.router.qosToChannel(QOS), (PeerInfo)null);
         if (partitionUrl != null) {
            outputStream.setPartitionName(this.router.getPartitionNameByURL(partitionUrl));
         }

         this.router.sendMsg(outputStream);
      }

      return this.router;
   }

   protected final MsgAbbrevOutputStream createIdentifyMsg(JVMID dest, byte QOS, JVMMessage.Command cmd, ServerChannel nc, PeerInfo pi) {
      MsgAbbrevOutputStream outputStream;
      try {
         outputStream = new MsgAbbrevOutputStream(this, nc);
         outputStream.setReplacer(RemoteObjectReplacer.getReplacer());
      } catch (IOException var9) {
         throw new AssertionError("cannot create identify message", var9);
      }

      outputStream.header.init(dest, QOS, cmd);
      if (cmd != JVMMessage.Command.CMD_IDENTIFY_RESPONSE_CSHARP) {
         outputStream.header.hasJVMIDs = true;
      }

      try {
         outputStream.writeInt(HeartbeatMonitor.periodLengthMillis());
         if (cmd != JVMMessage.Command.CMD_IDENTIFY_RESPONSE_CSHARP) {
            byte[] publicKey = LocalRJVM.getLocalRJVM().getPublicKey();
            outputStream.writeInt(publicKey.length);
            outputStream.write(publicKey);
            outputStream.writeObject(LocalRJVM.getLocalRJVM().getPeerInfo());
            if (nc != null && RJVMEnvironment.getEnvironment().isServer() && RJVMEnvironment.getEnvironment().isServerClusteringSupported()) {
               ClusterInfoHelper.writeClusterInfo(outputStream, nc, JVMID.localID(), pi);
            }
         }

         outputStream.flush();
         return outputStream;
      } catch (IOException var8) {
         throw new AssertionError(var8);
      }
   }

   final MsgAbbrevOutputStream createPeerGoneMsg(JVMID src, JVMID dest, ServerChannel channel, byte QOS) {
      MsgAbbrevOutputStream outputStream;
      try {
         outputStream = new MsgAbbrevOutputStream(this, channel);
         outputStream.setReplacer(RemoteObjectReplacer.getReplacer());
      } catch (IOException var7) {
         throw new AssertionError("cannot create peerGone message", var7);
      }

      outputStream.header.init(dest, QOS, JVMMessage.Command.CMD_PEER_GONE);
      outputStream.header.responseId = -1;
      outputStream.header.invokableId = -1;
      outputStream.header.src = src;
      outputStream.header.hasJVMIDs = true;
      return outputStream;
   }

   private MsgAbbrevOutputStream createCloseMsg(byte QOS, String partitionName) throws IOException {
      MsgAbbrevOutputStream outputStream = this.getOutputStreamByName((ServerChannel)null, partitionName);
      outputStream.header.init(this.thisRJVM.getID(), QOS, JVMMessage.Command.CMD_REQUEST_CLOSE);
      outputStream.header.hasJVMIDs = true;
      return outputStream;
   }

   private MsgAbbrevJVMConnection getHeartBeatConnectionInPairedConnTable(String preferredPartitionName, byte protocolNum) throws ConnectException {
      if (this.pairedConn.isEmpty()) {
         throw new ConnectException("Should Never Happen: No heartbeat before the connection is ready!");
      } else {
         SearchKey key = new SearchKey(preferredPartitionName, preferredPartitionName, protocolNum);
         return this.pairedConn.containsKey(key) ? (MsgAbbrevJVMConnection)this.pairedConn.get(key) : (MsgAbbrevJVMConnection)this.pairedConn.elements().nextElement();
      }
   }

   private MsgAbbrevOutputStream createHeartbeatMsg(String remotePartitionName, byte useQOS) throws IOException {
      MsgAbbrevOutputStream outputStream = this.getOutputStreamByName((ServerChannel)null, remotePartitionName);
      outputStream.header.init(this.thisRJVM.getID(), useQOS, JVMMessage.Command.CMD_INTERNAL);
      outputStream.header.hasJVMIDs = true;
      return outputStream;
   }

   private PeerInfo getPeerInfo() {
      return this.thisRJVM == null ? null : this.thisRJVM.getPeerInfo();
   }

   final MsgAbbrevInputStream getInputStream() {
      MsgAbbrevInputStream mais = (MsgAbbrevInputStream)this.inStreamPool.remove();
      if (mais == null) {
         try {
            return new MsgAbbrevInputStream(this);
         } catch (IOException var3) {
            throw new AssertionError("Failed to create input stream", var3);
         }
      } else {
         return mais;
      }
   }

   private boolean isPreDiabloPeer() {
      PeerInfo info = this.getPeerInfo();
      return info == null || info.compareTo(PeerInfo.VERSION_DIABLO) < 0;
   }

   final void releaseInputStream(MsgAbbrevInputStream is) {
      this.inStreamPool.add(is);
   }

   final MsgAbbrevOutputStream getOutputStreamByURL(ServerChannel channel, String partitionURL) throws IOException {
      String partitionName = this.getPartitionNameByURL(partitionURL);
      MsgAbbrevOutputStream maos = this.getOutputStreamByName(channel, partitionName);
      maos.setPartitionURL(partitionURL);
      return maos;
   }

   final MsgAbbrevOutputStream getOutputStreamByName(ServerChannel channel, String partitionName) throws IOException {
      if (partitionName == null) {
         throw new IllegalArgumentException("Can't proceed without partitionName");
      } else if (this.wasShutdown) {
         throw new IOException("The connection manager to " + this + " has already been shut down");
      } else {
         MsgAbbrevOutputStream maos = (MsgAbbrevOutputStream)this.outStreamPool.remove();
         if (maos == null) {
            PeerInfo peerInfo = this.getPeerInfo();
            if (peerInfo == null && this.thisRJVM != null) {
               this.thisRJVM.waitBootstrapDone();
               peerInfo = this.getPeerInfo();
            }

            if (peerInfo == null) {
               throw new ConnectException("Couldn't connect to " + partitionName + '@' + this.thisRJVM + " - it is likely that the connection has already been shut down");
            }

            maos = new MsgAbbrevOutputStream(this, peerInfo, channel, partitionName);
            maos.setReplacer(RemoteObjectReplacer.getReplacer(peerInfo));
         } else {
            maos.setServerChannel(channel);
            maos.setPartitionName(partitionName);
         }

         maos.setPartitionURL((String)null);
         maos.setUser(this.thisRJVM.getUser(partitionName));
         return maos;
      }
   }

   private void savePartitionMappings(MsgAbbrevJVMConnection connection) {
      String pName = connection.getRemotePartitionName();
      if (pName == null) {
         throw new IllegalStateException("Can't save connection mapping without a partitionName");
      } else {
         String url = connection.getPartitionUrl();
         if (url != null) {
            this.partitionURLNameMapper.putIfAbsent(url, pName);
         }

         this.partitionURLNameMapper.putIfAbsent(getPartitionURLByName(pName), pName);
      }
   }

   String getPartitionNameByURL(String partitionURL) {
      if (partitionURL == null) {
         throw new IllegalArgumentException("PartitionURL is null");
      } else {
         String pName = (String)this.partitionURLNameMapper.get(partitionURL);
         if (pName == null && this.router != null) {
            pName = this.router.getPartitionNameByURL(partitionURL);
         }

         return pName;
      }
   }

   private static MsgAbbrevJVMConnection createConnection(Protocol protocol, String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      byte protocolNum = protocol.toByte();
      if (protocolNum == 6) {
         MsgAbbrevJVMConnection adminConnection = createConnection(ProtocolManager.getDefaultAdminProtocol(), host, address, port, networkChannel, destinationJVMID, connectionTimeout, partitionUrl);
         adminConnection.setAdminQOS();
         return adminConnection;
      } else {
         RJVMConnectionFactory factory = RJVMManager.getRJVMManager().getConnectionFactory(protocolNum);
         if (factory == null) {
            throw new UnknownServiceException("Unknown protocol: '" + protocol + '\'');
         } else {
            return factory.createConnection(host, address, port, networkChannel, destinationJVMID, connectionTimeout, partitionUrl);
         }
      }
   }

   final void releaseOutputStream(MsgAbbrevOutputStream os) {
      os.reset();
      this.outStreamPool.add(os);
   }

   private static Object getInstanceDynamically(String className, Class[] signature, Object[] params) {
      try {
         ClassLoader loader = RJVMEnvironment.getEnvironment().getConnectionManagerClassLoader();
         Class propClass;
         if (loader != null) {
            propClass = loader.loadClass(className);
         } else {
            propClass = Class.forName(className);
         }

         Constructor constructor = propClass.getConstructor(signature);
         return constructor.newInstance(params);
      } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | ClassNotFoundException var6) {
         throw new AssertionError(var6);
      } catch (InvocationTargetException var7) {
         throw new AssertionError(var7.getTargetException());
      }
   }

   public final String toString() {
      return super.toString() + " for: '" + this.thisRJVM + '\'' + (this.router == null ? "" : " routed via: " + this.router);
   }

   final JVMID getJVMID() {
      return this.thisRJVM == null ? this.bootstrapJVMID : this.thisRJVM.getID();
   }

   final boolean isShutdownDown() {
      return this.wasShutdown;
   }

   private int getConnectionTimeout(JVMID destJVMID, ServerChannel channel) {
      Hashtable ht = RMIEnvironment.getEnvironment().getFromThreadLocalMap();
      if (ht == null) {
         return this.getCachedConnectionTimeout(destJVMID, channel);
      } else {
         Object o = ht.get("weblogic.jndi.connectTimeout");
         if (o == null) {
            o = ht.get("weblogic.jndi.requestTimeout");
         }

         long result;
         if (o == null) {
            result = 0L;
         } else if (o instanceof String) {
            result = Long.parseLong((String)o);
         } else {
            result = (Long)o;
         }

         return (int)result;
      }
   }

   synchronized MsgAbbrevJVMConnection addPartitionConnection(MsgAbbrevJVMConnection connection, boolean closeDuplicate) {
      if (this.wasShutdown) {
         connection.close();
         return null;
      } else {
         this.savePartitionMappings(connection);
         MsgAbbrevJVMConnection existingConnection = this.getConnectionInPairedConnTable(connection);
         boolean hasDuplicate = existingConnection != null && existingConnection != connection;
         if (hasDuplicate) {
            if (closeDuplicate) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  RJVMLogger.logDebug("Closing duplicate connection: '" + connection + "'\nLocal partition name: " + connection.getLocalPartitionName() + "; Remote partition name: " + connection.getRemotePartitionName() + "\nExisting connection: '" + existingConnection + "'\nLocal partition name: " + existingConnection.getLocalPartitionName() + "; Remote partition name: " + existingConnection.getRemotePartitionName());
               }

               this.cleanShutdown(connection);
            } else {
               connection.markPhantom();
               RJVMLogger.logDebug("Adding to duplicate table; connection: " + connection);
               MsgAbbrevJVMConnection conn = (MsgAbbrevJVMConnection)this.dupConnections.put(connection.getLocalPartitionName() + connection.getRemotePartitionName(), connection);
               if (conn != null) {
                  RJVMLogger.logDebug("Found multiple duplicates in duplicate table; dropping connection: " + conn);
               }
            }

            return existingConnection;
         } else {
            this.lastChannelUsed = connection.getChannel();
            this.putConnectionInPairedConnTable(connection);
            return connection;
         }
      }
   }

   int getConnectionCount() {
      return this.pairedConn.size();
   }

   boolean isConnectedInPairedConnTable() {
      return !this.pairedConn.isEmpty();
   }

   boolean isConnectedByNameInPairedConnTable(String remotePartitionName, byte protocolNum) {
      return this.getConnectionInPairedConnTable(remotePartitionName, protocolNum) != null;
   }

   boolean isConnectedByNameInPairedConnTable(String localPartitionName, String remotePartitionName) {
      return this.isConnectedByNameInPairedConnTable(localPartitionName, remotePartitionName, (byte)9);
   }

   boolean isConnectedByNameInPairedConnTable(String localPartitionName, String remotePartitionName, byte protocolNum) {
      return this.getConnectionInPairedConnTable(localPartitionName, remotePartitionName, protocolNum) != null;
   }

   static String getPartitionURLByName(String partitionName) {
      return "/?partitionName=" + partitionName;
   }

   protected MsgAbbrevJVMConnection getConnectionInPairedConnTable(String remotePartitionName, byte protocolNum) {
      String localPartitionName = "DOMAIN";
      if (KernelStatus.isServer()) {
         localPartitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      }

      return this.getConnectionInPairedConnTable(localPartitionName, remotePartitionName, protocolNum);
   }

   MsgAbbrevJVMConnection getConnectionInPairedConnTable(MsgAbbrevJVMConnection connection) {
      return this.getConnectionInPairedConnTable(connection.getLocalPartitionName(), connection.getRemotePartitionName(), connection.getProtocol().toByte());
   }

   MsgAbbrevJVMConnection getConnectionInPairedConnTable(String localPartitionName, String remotePartitionName, byte protocolNum) {
      return (MsgAbbrevJVMConnection)this.pairedConn.get(new SearchKey(localPartitionName, remotePartitionName, protocolNum));
   }

   private SearchKey getKeyInPairedConnTable(MsgAbbrevJVMConnection connection) {
      return new SearchKey(connection.getLocalPartitionName(), connection.getRemotePartitionName(), connection.getProtocol().toByte());
   }

   private MsgAbbrevJVMConnection putConnectionInPairedConnTable(MsgAbbrevJVMConnection connection) {
      SearchKey key = this.getKeyInPairedConnTable(connection);
      return (MsgAbbrevJVMConnection)this.pairedConn.putIfAbsent(key, connection);
   }

   boolean rmConnectionInPairedConnTable(MsgAbbrevJVMConnection connection) {
      SearchKey key = this.getKeyInPairedConnTable(connection);
      return this.pairedConn.remove(key, connection);
   }

   void mergeConnections(ConnectionManager otherCM) {
      Iterator var2 = otherCM.pairedConn.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry partitionEntry = (Map.Entry)var2.next();
         this.addPartitionConnection((MsgAbbrevJVMConnection)partitionEntry.getValue(), true);
      }

   }

   void closeConnectionsForPartition(String localPartitionName) {
      Iterator var2 = this.pairedConn.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         SearchKey key = (SearchKey)entry.getKey();
         if (key.localPartitionName.equals(localPartitionName)) {
            MsgAbbrevJVMConnection connection = (MsgAbbrevJVMConnection)entry.getValue();
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug("Removing connection: " + connection.toString() + "; for LocalPartition: " + localPartitionName);
            }

            this.cleanShutdown(connection, true);
            this.pairedConn.remove(key);
         }
      }

   }

   boolean hasAdminConnection() {
      Iterator var1 = this.pairedConn.values().iterator();

      MsgAbbrevJVMConnection connection;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         connection = (MsgAbbrevJVMConnection)var1.next();
      } while(connection.getQOS() != 103);

      return true;
   }

   static class SearchKey {
      private String localPartitionName;
      private String remotePartitionName;
      private byte protocol;
      private int hashcode = -1;

      SearchKey(String localPName, String remotePName, byte protocol) {
         if (localPName != null && remotePName != null) {
            this.localPartitionName = localPName;
            this.remotePartitionName = remotePName;
            this.protocol = protocol;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public int hashCode() {
         if (this.hashcode == -1) {
            this.hashcode = this.localPartitionName.hashCode() ^ this.remotePartitionName.hashCode() ^ this.protocol;
         }

         return this.hashcode;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof SearchKey)) {
            return false;
         } else {
            SearchKey key = (SearchKey)obj;
            return key.protocol == this.protocol && key.localPartitionName.equals(this.localPartitionName) && key.remotePartitionName.equals(this.remotePartitionName);
         }
      }

      public String toString() {
         return '(' + this.localPartitionName + ", " + this.remotePartitionName + ")[" + ProtocolManager.getProtocolByIndex(this.protocol).getAsURLPrefix() + ']';
      }
   }
}
