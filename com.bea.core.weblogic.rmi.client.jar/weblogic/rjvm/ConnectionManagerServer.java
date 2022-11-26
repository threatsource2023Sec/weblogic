package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.MarshalException;
import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import weblogic.common.internal.PeerInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PropertyHelper;

public final class ConnectionManagerServer extends ConnectionManager implements PeerGoneListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final Set connectionManagers = Collections.newSetFromMap(new WeakHashMap());
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final DebugLogger debugRouting = DebugLogger.getDebugLogger("DebugRouting");
   private PartitionEventInterceptor partitionEventInterceptor;
   private static boolean enableProtocolSwitch;
   private static boolean reuseDomainConnections;
   private static InetAddress localhost;
   private boolean isMissedPeergone = false;
   private final HashMap table = new HashMap();

   public ConnectionManagerServer(RJVMImpl rjvm) {
      super(rjvm);
      if (rjvm != null) {
         this.partitionEventInterceptor = new PartitionEventInterceptor(this);
      }

      synchronized(connectionManagers) {
         connectionManagers.add(this);
      }
   }

   private boolean isLocal(JVMID id, MsgAbbrevJVMConnection connection) {
      if (localhost == null) {
         try {
            localhost = InetAddress.getByName("127.0.0.1");
         } catch (UnknownHostException var9) {
         }
      }

      boolean addressMatches = id.address().equals(connection.getLocalAddress()) || id.address().equals(JVMID.localID().address()) || id.address().equals(localhost) || this.thisRJVM == null && id.isBootstrapping();
      ServerChannel channel = connection.getChannel();
      if (!addressMatches && id.getPublicAddress() != null && channel != null) {
         addressMatches = id.getPublicAddress().equals(channel.getPublicAddress()) || id.getAddress().equals(channel.getPublicAddress());
      }

      Protocol adminProtocol = RJVMManager.getRJVMManager().getProtocol((byte)6);
      Protocol protocol = connection.getQOS() == 103 ? adminProtocol : connection.getProtocol();
      int idPort;
      if (protocol != null && protocol.equals(adminProtocol)) {
         if ((idPort = id.getPort(protocol)) == -1 && (idPort = id.getPort(RJVMManager.getRJVMManager().getProtocol((byte)2))) == -1) {
            idPort = id.getPort(RJVMManager.getRJVMManager().getProtocol((byte)3));
         }
      } else {
         idPort = id.getPort(protocol);
      }

      boolean portMatches = idPort != -1 && (idPort == connection.getLocalPort() || idPort == JVMID.localID().getPort(protocol) || channel != null && idPort == channel.getPublicPort());
      if (enableProtocolSwitch || connection.proxied) {
         portMatches = this.thisRJVM == null && id.isBootstrapping() || portMatches;
      }

      return addressMatches && portMatches;
   }

   private static ManagedInvocationContext setPartitionInvocationContext(String partitionName) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      ComponentInvocationContext cic = manager.createComponentInvocationContext(partitionName);
      return manager.setCurrentComponentInvocationContext(cic);
   }

   protected static String getCurrentPartitionName() {
      return ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext().getPartitionName();
   }

   final void handleRJVM(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      if (!JVMID.localID().equals(header.dest)) {
         if (this.isLocal(header.dest, connection)) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("Message for this server has the wrong destination address: '" + header.dest + '\'');
            }

            this.sendPeerGoneMsg(header.dest, header.src, header.QOS, connection);
         } else if (this.thisRJVM == null) {
            this.shouldNeverHappen(connection, "Server expected to route a message received over an uninitialized connection: '" + header + '\'');
         } else {
            this.connectionAppearsValid(connection);
            this.routeMsg(inputStream);
         }
      } else {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            debugConnection.debug(String.format("ConnectionManagerServer Connection [%s] Current partition name [%s], pushing partition name [%s] in CIC", connection, getCurrentPartitionName(), connection.getLocalPartitionName()));
         }

         try {
            ManagedInvocationContext mic = setPartitionInvocationContext(connection.getLocalPartitionName());
            Throwable var5 = null;

            try {
               if (this.thisRJVM != null) {
                  if (this.missedPeerGone(connection, inputStream, header)) {
                     return;
                  }

                  this.connectionAppearsValid(connection);
                  if (this.thisRJVM.getID().equals(header.src)) {
                     this.thisRJVM.dispatch(inputStream);
                     return;
                  } else {
                     RJVMImpl theRJVM = RJVMManager.getRJVMManager().findRemote(header.src);
                     if (theRJVM == null) {
                        this.shouldNeverHappen(connection, "Server received a routed message from an unknown JVM: '" + header.src + '\'');
                     } else {
                        theRJVM.dispatch(inputStream);
                     }

                     return;
                  }
               }

               this.shouldNeverHappen(connection, "Server received a message over an uninitialized connection: '" + header + '\'');
            } catch (Throwable var25) {
               var5 = var25;
               throw var25;
            } finally {
               if (mic != null) {
                  if (var5 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var24) {
                        var5.addSuppressed(var24);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } finally {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug(String.format("ConnectionManagerServer Popped Local partition Name [%s] Connection[%s], current CIC partition name is [%s]", connection.getLocalPartitionName(), connection, getCurrentPartitionName()));
            }

         }

         return;
      }

   }

   private synchronized void connectionAppearsValid(MsgAbbrevJVMConnection connection) {
      if (this.getConnectionInPairedConnTable(connection) != connection) {
      }

   }

   private boolean missedPeerGone(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream, JVMMessage header) {
      if (this.isMissedPeergone) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            debugConnection.debug("Another message came in before we swapped the connection manager: Received  " + header);
         }

         RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(header.src);
         ConnectionManager connMgr = theRJVM.findOrCreateConMan();
         if (((ConnectionManagerServer)connMgr).isMissedPeergone) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("swapped connection manager '" + connMgr + "' is already in missedPeerGone mode !");
            }

            this.shouldNeverHappen(connection, "Unable to swap connection manager in missedPeerGone handling");
         }

         inputStream.setConnectionManager(connMgr);
         connMgr.handleRJVM(connection, inputStream);
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            debugConnection.debug("A response for this message must have been sent  :" + header);
         }

         return true;
      } else {
         MsgAbbrevJVMConnection existingConnection = this.getConnectionInPairedConnTable(connection);
         if (existingConnection != null && connection != existingConnection) {
            if (existingConnection.getMessagesSentCount() < 2L && existingConnection.getMessagesReceivedCount() < 2L) {
               return false;
            } else if (header.src.isServer() && header.dest.isServer() && !this.possibleMissedPeergone) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  debugConnection.debug("Duplicate Connection between servers detected.");
               }

               return false;
            } else {
               this.isMissedPeergone = true;
               this.possibleMissedPeergone = false;
               connection.setWaitForPeergone(true);
               PeerInfo pi = this.thisRJVM.getPeerInfo();
               int periodLengthMillis = this.thisRJVM.getPeriodLengthMillis();
               byte[] sharedSecret = this.thisRJVM.getSharedSecret();
               this.thisRJVM.peerGone(new PeerGoneException("Duplicate Connection [" + connection.getChannel() + "]. RJVM Being Shutdown in favor of [" + existingConnection.getChannel() + ']'));
               RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(header.src);
               theRJVM.setSharedSecret(sharedSecret);
               theRJVM.completeConnectionSetup(periodLengthMillis, (byte[])null, pi, connection, header.QOS);
               ConnectionManager theConMan = ConnectionManager.create(theRJVM);
               theConMan = theRJVM.findOrSetConMan(theConMan);
               inputStream.setConnectionManager(theConMan);
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  debugConnection.debug("ConnectionManager in MsgAbbrevInputStream was replaced due to Missed peergone/Duplicate Connection.\n Old connection manager was '" + this + "'. New connection manager is '" + theConMan + '\'');
               }

               connection.setDispatcher(theConMan, false);
               theConMan.handleRJVM(connection, inputStream);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   final void handleIdentifyRequest(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      JVMID me = JVMID.localID();
      JVMID src = header.src;
      JVMID dest = header.dest;
      boolean proxied = false;
      if (src.isClient() && connection.proxied && !dest.equals(JVMID.localID())) {
         debugConnection.debug("Received a proxied connection" + src.toString());
         if (connection.getInetAddress() != null && dest.getInetAddress().equals(connection.getInetAddress())) {
            proxied = true;
         }
      }

      if (!src.isServer() && (src.getRouter() == null || !src.getRouter().equals(me) || proxied)) {
         src.setRouter(me);
      }

      if (header.cmd != JVMMessage.Command.CMD_NO_ROUTE_IDENTIFY_REQUEST && !me.equals(dest) && !this.isLocal(dest, connection)) {
         if (this.thisRJVM == null) {
            if (!dest.isBootstrapping()) {
               this.shouldNeverHappen(connection, "Server expected to route a message received over an uninitialized connection: '" + header + '\'');
            } else {
               RJVMLogger.logBadNAT(header.toString());
               this.gotExceptionReceiving(connection, new IOException("Bad NAT request"));
            }
         } else {
            this.connectionAppearsValid(connection);
            this.routeMsg(inputStream);
         }

      } else if (!me.equals(dest) && this.isLocal(dest, connection) && !dest.isBootstrapping()) {
         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            debugConnection.debug("CMD_IDENTIFY_REQUEST for this JVM has the wrong destination address: '" + dest + '\'');
         }

         this.sendPeerGoneMsg(dest, src, header.QOS, connection);
      } else {
         int remotePeriodLength = readRemotePeriodLength(inputStream);
         byte[] sharedSecret = readPublickey(inputStream);
         PeerInfo peerInfo;
         if (header.cmd == JVMMessage.Command.CMD_IDENTIFY_REQUEST_CSHARP) {
            peerInfo = readDotNetClientPeerInfo(inputStream);
         } else {
            peerInfo = readPeerInfo(inputStream);
         }

         ServerChannel networkChannel = inputStream.getServerChannel();
         JVMMessage.Command responseCode = header.cmd == JVMMessage.Command.CMD_IDENTIFY_REQUEST_CSHARP ? JVMMessage.Command.CMD_IDENTIFY_RESPONSE_CSHARP : JVMMessage.Command.CMD_IDENTIFY_RESPONSE;
         MsgAbbrevOutputStream outputStream;
         if (this.thisRJVM == null && peerInfo.getMajor() > 6) {
            outputStream = this.createIdentifyMsg(src, header.QOS, responseCode, networkChannel, peerInfo);
         } else {
            outputStream = this.createIdentifyMsg(src, header.QOS, responseCode, (ServerChannel)null, (PeerInfo)null);
         }

         if (connection.getQOS() == 103) {
            outputStream.header.invokableId = 7938;
         }

         connection.sendMsg(outputStream);
         if (me.equals(src) || src.isBootstrapping() && this.isLocal(src, connection)) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("Received CMD_IDENTIFY_REQUEST from self");
            }

         } else {
            RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(src);
            if (header.cmd == JVMMessage.Command.CMD_IDENTIFY_REQUEST_CSHARP) {
               theRJVM.setCSharpClient();
            }

            peerInfo.setIsServer(theRJVM.getID().isServer());
            theRJVM.completeConnectionSetup(remotePeriodLength, sharedSecret, peerInfo, connection, header.QOS);
            if (this.thisRJVM == null) {
               this.thisRJVM = theRJVM;
               ConnectionManager theConMan = this.thisRJVM.findOrSetConMan(this);
               connection.setDispatcher(theConMan, false);
               setAppletRouter(theConMan);
               MsgAbbrevJVMConnection existingConnection = this.getConnectionInPairedConnTable(connection);
               if (existingConnection != null && existingConnection != connection) {
                  theConMan.possibleMissedPeergone = existingConnection.getMessagesReceivedCount() >>> 1 > 0L;
               }
            } else if (this.thisRJVM.getID().equals(src)) {
               this.shouldNeverHappen(connection, "Server received an unrouted CMD_IDENTIFY_REQUEST through an established connection");
            } else {
               theRJVM.findOrCreateConManRouter(this);
               if (KernelStatus.DEBUG && debugRouting.isDebugEnabled()) {
                  debugRouting.debug("Saving second stop server " + this + " as the router to " + src);
               }
            }

         }
      }
   }

   final void handleIdentifyResponse(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      JVMID me = JVMID.localID();
      JVMID src = header.src;
      JVMID dest = header.dest;
      if (!me.equals(dest)) {
         if (this.isLocal(dest, connection)) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("CMD_IDENTIFY_RESPONSE for this JVM has the wrong destination address: '" + dest + '\'');
            }
         } else {
            this.connectionAppearsValid(connection);
            this.routeMsg(inputStream);
         }
      } else {
         if (me.equals(src)) {
            if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("Received CMD_IDENTIFY_RESPONSE from self");
            }

            ConnectionManager bootstrapConMan;
            if (this.thisRJVM == null) {
               this.bootstrapRJVM = LocalRJVM.getLocalRJVM();
               bootstrapConMan = connection.getDispatcher();
            } else {
               bootstrapConMan = this.thisRJVM.findOrCreateConMan();
            }

            if (bootstrapConMan != null) {
               bootstrapConMan.bootstrapResponseReceived = true;
               synchronized(bootstrapConMan.bootstrapResult) {
                  bootstrapConMan.bootstrapResult.notify();
               }
            }

            this.cleanShutdown(connection);
            return;
         }

         int remotePeriodLength = readRemotePeriodLength(inputStream);
         byte[] sharedSecret = readPublickey(inputStream);
         PeerInfo peerInfo = readPeerInfo(inputStream);
         if (peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0) {
            src.cleanupPorts();
         }

         RJVMImpl theRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(src);
         if (peerInfo != null) {
            peerInfo.setIsServer(theRJVM.getID().isServer());
         }

         theRJVM.findOrSetConMan(this);
         this.bootstrapRJVM = this.thisRJVM;
         theRJVM.completeConnectionSetup(remotePeriodLength, sharedSecret, peerInfo, connection, header.QOS);
         ConnectionManager theConMan = null;
         ConnectionManager bootstrapConMan;
         if (this.thisRJVM == null) {
            this.thisRJVM = theRJVM;
            theConMan = this.thisRJVM.findOrSetConMan(this);
            if (theConMan != this && KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
               debugConnection.debug("Another bootstrapping RJVM beat us to creating a connection");
            }

            bootstrapConMan = connection.getDispatcher();
            if (header.invokableId == 7938) {
               this.thisRJVM.convertedToAdminQOS = true;
            }

            theConMan.possibleMissedPeergone = false;
            connection.setDispatcher(theConMan, true);
            setAppletRouter(theConMan);
         } else if (this.thisRJVM.getID().equals(src)) {
            theConMan = this.thisRJVM.findOrCreateConMan();
            bootstrapConMan = theConMan;
            setAppletRouter(theConMan);
         } else {
            bootstrapConMan = theRJVM.findOrCreateConMan();
         }

         if (theConMan != null) {
            connection.setClusterInfo(readClusterInfo(inputStream, peerInfo, theRJVM.getID()));
         }

         if (bootstrapConMan != null) {
            bootstrapConMan.bootstrapResponseReceived = true;
            bootstrapConMan.bootstrapRJVM = this.thisRJVM;
            synchronized(bootstrapConMan.bootstrapResult) {
               bootstrapConMan.bootstrapResult.notify();
            }
         }
      }

   }

   final void handlePeerGone(MsgAbbrevJVMConnection connection, MsgAbbrevInputStream inputStream) {
      if (this.thisRJVM == null) {
         ConnectionManager bootstrapConMan = connection.getDispatcher();
         if (bootstrapConMan != null) {
            bootstrapConMan.bootstrapResponseReceived = true;
            this.bootstrapRJVM = null;
            synchronized(bootstrapConMan.bootstrapResult) {
               bootstrapConMan.bootstrapResult.notify();
            }
         }
      } else {
         JVMMessage header = inputStream.getMessageHeader();
         if (!JVMID.localID().equals(header.dest)) {
            if (this.isLocal(header.dest, connection)) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  debugConnection.debug("PeerGone message for this JVM has the wrong destination address: '" + header.dest + '\'');
               }
            } else {
               this.connectionAppearsValid(connection);
               this.routeMsg(inputStream);
            }
         } else {
            String message = "Peer requested connection shutdown";
            if (this.thisRJVM.getID().equals(header.src)) {
               this.thisRJVM.findOrCreateConMan();
               this.thisRJVM.peerGone(new PeerGoneException(message));
            } else {
               RJVMImpl theRJVM = RJVMManager.getRJVMManager().findRemote(header.src);
               if (theRJVM != null) {
                  theRJVM.findOrCreateConMan();
                  theRJVM.peerGone(new PeerGoneException(message));
               }
            }
         }
      }

   }

   protected final synchronized void shutdown() {
      if (this.partitionEventInterceptor != null) {
         this.partitionEventInterceptor.close();
      }

      super.shutdown();
      synchronized(this.table) {
         Iterator routers = this.table.values().iterator();

         while(routers.hasNext()) {
            RJVMImpl rjvm = (RJVMImpl)routers.next();
            JVMID thisRJVMID = this.thisRJVM.getID();
            rjvm.findOrCreateConMan().cancelIO(thisRJVMID);
            rjvm.removePeerGoneListener(this);
            routers.remove();
         }

      }
   }

   private static void copyMessageContext(MsgAbbrevInputStream inputStream, MsgAbbrevOutputStream outputStream) throws IOException {
      InboundMsgAbbrev abbrevs = inputStream.getAbbrevs();
      abbrevs.writeTo(outputStream);
      outputStream.setUser(inputStream.getAuthenticatedUser());
   }

   private void updateRoutersTable(RJVMImpl destRJVM) {
      synchronized(this.table) {
         RJVMImpl res = (RJVMImpl)this.table.get(destRJVM.getID());
         if (res == null) {
            this.table.put(destRJVM.getID(), destRJVM);
            destRJVM.addPeerGoneListener(this);
         }

      }
   }

   private void routeMsg(MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      RJVMImpl destRJVM = RJVMManager.getRJVMManager().findOrCreateRemote(header.dest);
      String remotePN = inputStream.getConnection().getRemotePartitionName();

      MsgAbbrevOutputStream outputStream;
      try {
         outputStream = destRJVM.findOrCreateConMan().getOutputStreamByName((ServerChannel)null, remotePN);
         outputStream.header.init(header);
         copyMessageContext(inputStream, outputStream);
         int toCopy = header.abbrevOffset - inputStream.pos();

         int n;
         for(byte[] tmp = new byte[1024]; toCopy > 0; toCopy -= n) {
            n = inputStream.read(tmp, 0, Math.min(tmp.length, toCopy));
            outputStream.write(tmp, 0, n);
         }
      } catch (IOException var10) {
         this.gotExceptionSending(header, new MarshalException("Error creating routed message", var10));
         return;
      }

      MsgAbbrevJVMConnection outConnection;
      try {
         outConnection = destRJVM.findOrCreateConMan().getOrMakeConnection(header.QOS, remotePN);
      } catch (IOException var9) {
         this.gotExceptionSending(header, new ConnectException("Error creating connection to: '" + destRJVM.getID() + "' while routing message", var9));
         return;
      }

      if (outConnection == null) {
         this.gotExceptionSending(header, new ConnectException("Unable to get a direct connection to: '" + destRJVM + '\''));
      } else {
         this.updateRoutersTable(destRJVM);
         outConnection.sendMsg(outputStream);
      }
   }

   public void peerGone(PeerGoneEvent e) {
      RJVMImpl rjvm = (RJVMImpl)e.getSource();
      JVMID rjvmID = rjvm.getID();
      synchronized(this.table) {
         RJVMImpl res = (RJVMImpl)this.table.get(rjvmID);
         if (res == null) {
            return;
         }

         this.table.remove(rjvmID);
      }

      this.sendPeerGoneMsg(rjvmID, this.thisRJVM.getID(), (byte)101, (MsgAbbrevJVMConnection)null);
   }

   String getPartitionNameByURL(String partitionURL) {
      String pName = super.getPartitionNameByURL(partitionURL);
      if (pName == null && reuseDomainConnections) {
         try {
            pName = PartitionTable.getInstance().lookup(partitionURL).getPartitionName();
         } catch (URISyntaxException var4) {
            return PartitionTable.getInstance().getGlobalPartitionName();
         }
      }

      return pName;
   }

   static {
      String s = System.getProperty("weblogic.rjvm.enableprotocolswitch");
      if (s != null) {
         enableProtocolSwitch = true;
      }

      reuseDomainConnections = PropertyHelper.getBoolean("weblogic.rjvm.reuseDomainConnections");
      localhost = null;
   }
}
