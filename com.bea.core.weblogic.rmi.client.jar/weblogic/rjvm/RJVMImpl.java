package weblogic.rjvm;

import java.io.IOException;
import java.io.ObjectInput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.rmi.StubNotFoundException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.common.T3Exception;
import weblogic.common.T3ExecuteException;
import weblogic.common.WLObjectInput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ChannelImpl;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ProtocolStack;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentityManager;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectEventImpl;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.ServerDisconnectEventImpl;
import weblogic.rmi.extensions.UnrecoverableConnectionException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.KeyTable;
import weblogic.utils.NestedException;
import weblogic.utils.collections.ArraySet;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

class RJVMImpl implements RJVM, PeerInfoable {
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private final JVMID id;
   private InvokableFinder finder;
   private volatile ConnectionManager connectionManager;
   private byte[] sharedSecret;
   private Object services;
   private volatile boolean isDead;
   boolean convertedToAdminQOS;
   private int peerChannelMaxMessageSize = -1;
   private ConcurrentHashMap userTable = new ConcurrentHashMap();
   private Timer monitorTrigger;
   private int nextResponseId;
   private final KeyTable pendingResponses = new KeyTable();
   private final ArraySet peerGoneListeners = new ArraySet();
   private final ArraySet partitionGoneListeners = new ArraySet();
   private final Date connectTime = new Date();
   private final ConcurrentHashMap responseContexts = new ConcurrentHashMap();
   private int periodLengthMillis;
   private volatile long timeOfLastMessage = -1L;
   private volatile boolean sentNoMessageRecently = true;
   private volatile boolean hbMessageReceivedThisPeriod;
   private volatile PeerInfo peerInfo;
   protected Channel remoteChannel;
   private final Object bootstrapLock = new Object();
   protected static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int hbIdlePeriods;
   private boolean interopMode = true;
   protected boolean preDiabloPeer = true;
   private boolean bootstrapping;
   private String bootstrapErrorMessage = null;
   private volatile boolean peerRequestConnectionShutdown = false;
   private boolean cSharpClient = false;
   private final Object connectionManagerCreateLock = new Object();
   private int notInUseCounter = 0;
   private final Set disconnectListeners = new ArraySet();

   boolean isCSharpClient() {
      return this.cSharpClient;
   }

   void setCSharpClient() {
      this.cSharpClient = true;
   }

   RJVMImpl(JVMID i, InvokableFinder f) {
      this.id = i;
      this.finder = f;
      if (this.finder == null) {
         RJVMLogger.logFinderInit();
      }

      this.nextResponseId = 1;
   }

   RJVMImpl(JVMID i, ServerChannel c, PeerInfo info) {
      this.id = i;
      this.remoteChannel = this.createRemoteChannel(i, c);
      this.peerInfo = info;
      this.interopMode = !LocalRJVM.getLocalRJVM().getPeerInfo().equals(this.peerInfo);
      this.preDiabloPeer = this.isPreDiabloPeer();
   }

   private boolean isPreDiabloPeer() {
      return this.peerInfo == null || this.peerInfo.compareTo(PeerInfo.VERSION_DIABLO) < 0;
   }

   private Channel createRemoteChannel(JVMID rid, Protocol p) {
      if (p != null && rid != null) {
         return rid.isClient() ? new ChannelImpl(rid.getAddress(), -1, p.getProtocolName()) : new ChannelImpl(rid.getAddress(), rid.getPort(p), p.getProtocolName());
      } else {
         return null;
      }
   }

   private Channel createRemoteChannel(JVMID rid, ServerChannel c) {
      return c != null ? this.createRemoteChannel(rid, c.getProtocol()) : null;
   }

   private ServerChannel ensureConnectionEstablishedByName(ServerChannel channel, String partitionName) throws Throwable {
      byte protocolNum = channel.getProtocol().toByte();
      if (this.isConnectedByName(partitionName, protocolNum)) {
         return channel;
      } else {
         while(true) {
            boolean needsToBootstrap;
            synchronized(this.bootstrapLock) {
               if (!this.bootstrapping) {
                  needsToBootstrap = true;
                  this.bootstrapping = true;
               } else {
                  needsToBootstrap = false;
               }
            }

            if (needsToBootstrap) {
               boolean var23 = false;

               ServerChannel var7;
               label197: {
                  try {
                     var23 = true;
                     this.bootstrapErrorMessage = null;
                     ConnectionManager bootstrapConMan = this.findOrCreateConMan();
                     this.remoteChannel = this.createRemoteChannel(this.getID(), channel.getProtocol());
                     MsgAbbrevJVMConnection conn = bootstrapConMan.bootstrap(this.getID().getHostAddress(), this, channel, partitionName, (String)null);
                     if (conn != null) {
                        var7 = conn.getChannel();
                        var23 = false;
                        break label197;
                     }

                     var7 = channel;
                     var23 = false;
                  } catch (Throwable var29) {
                     this.bootstrapErrorMessage = var29.getMessage();
                     throw var29;
                  } finally {
                     if (var23) {
                        synchronized(this.bootstrapLock) {
                           this.bootstrapping = false;
                           this.bootstrapLock.notifyAll();
                        }
                     }
                  }

                  synchronized(this.bootstrapLock) {
                     this.bootstrapping = false;
                     this.bootstrapLock.notifyAll();
                     return var7;
                  }
               }

               synchronized(this.bootstrapLock) {
                  this.bootstrapping = false;
                  this.bootstrapLock.notifyAll();
                  return var7;
               }
            }

            synchronized(this.bootstrapLock) {
               while(this.bootstrapping) {
                  try {
                     this.bootstrapLock.wait();
                  } catch (InterruptedException var27) {
                  }
               }

               if (this.bootstrapErrorMessage != null) {
                  throw new IOException(this.bootstrapErrorMessage);
               }

               if (this.isConnectedByName(partitionName, protocolNum)) {
                  return channel;
               }
            }
         }
      }
   }

   private ServerChannel ensureConnectionEstablishedByURL(ServerChannel channel, String partitionURL) throws Throwable {
      byte protocolNum = channel.getProtocol().toByte();
      if (this.isConnectedByURL(partitionURL, protocolNum)) {
         return channel;
      } else {
         while(true) {
            boolean needsToBootstrap;
            synchronized(this.bootstrapLock) {
               if (!this.bootstrapping) {
                  needsToBootstrap = true;
                  this.bootstrapping = true;
               } else {
                  needsToBootstrap = false;
               }
            }

            if (needsToBootstrap) {
               boolean var23 = false;

               ServerChannel var7;
               label197: {
                  try {
                     var23 = true;
                     this.bootstrapErrorMessage = null;
                     ConnectionManager bootstrapConMan = this.findOrCreateConMan();
                     this.remoteChannel = this.createRemoteChannel(this.getID(), channel.getProtocol());
                     MsgAbbrevJVMConnection conn = bootstrapConMan.bootstrap(this.getID().getHostAddress(), this, channel, (String)null, partitionURL);
                     if (conn != null) {
                        var7 = conn.getChannel();
                        var23 = false;
                        break label197;
                     }

                     var7 = channel;
                     var23 = false;
                  } catch (Throwable var29) {
                     this.bootstrapErrorMessage = var29.getMessage();
                     throw var29;
                  } finally {
                     if (var23) {
                        synchronized(this.bootstrapLock) {
                           this.bootstrapping = false;
                           this.bootstrapLock.notifyAll();
                        }
                     }
                  }

                  synchronized(this.bootstrapLock) {
                     this.bootstrapping = false;
                     this.bootstrapLock.notifyAll();
                     return var7;
                  }
               }

               synchronized(this.bootstrapLock) {
                  this.bootstrapping = false;
                  this.bootstrapLock.notifyAll();
                  return var7;
               }
            }

            synchronized(this.bootstrapLock) {
               while(this.bootstrapping) {
                  try {
                     this.bootstrapLock.wait();
                  } catch (InterruptedException var27) {
                  }
               }

               if (this.bootstrapErrorMessage != null) {
                  throw new IOException(this.bootstrapErrorMessage);
               }

               if (this.isConnectedByURL(partitionURL, protocolNum)) {
                  return channel;
               }
            }
         }
      }
   }

   private boolean isConnectedByName(String partitionName, byte protocolNum) {
      if (this.connectionManager == null) {
         return false;
      } else {
         boolean b = this.connectionManager.isConnectedByNameInPairedConnTable(partitionName, protocolNum);
         if (!b && this.connectionManager.router != null) {
            b = this.connectionManager.router.isConnectedByNameInPairedConnTable(partitionName, protocolNum);
         }

         return b;
      }
   }

   private boolean isConnectedByURL(String partitionURL, byte protocolNum) {
      if (this.connectionManager == null) {
         return false;
      } else {
         String pn = this.connectionManager.getPartitionNameByURL(partitionURL);
         return pn != null && this.isConnectedByName(pn, protocolNum);
      }
   }

   private MsgAbbrevOutputStream getOutputStreamByURL(ServerChannel channel, String partitionURL) throws IOException {
      if (partitionURL == null) {
         throw new IllegalArgumentException("Can't proceed further without partitionURL");
      } else {
         this.connectionCheck();

         ServerChannel ch;
         try {
            ch = this.ensureConnectionEstablishedByURL(channel, partitionURL);
         } catch (Exception var6) {
            throw new ConnectException("Could not establish a connectionManager with " + this.getID() + ", " + partitionURL + ' ' + var6.toString(), var6);
         } catch (Throwable var7) {
            throw new ConnectException("Could not establish a connectionManager with " + this.getID() + ", " + var7.toString(), new Exception(var7));
         }

         synchronized(this.peerGoneListeners) {
            if (this.peerRequestConnectionShutdown) {
               throw new UnrecoverableConnectionException("This RJVM has already been shutdown " + this.getID());
            }

            if (this.isDead) {
               throw new ConnectException("This RJVM has already been shutdown " + this.getID());
            }
         }

         ConnectionManager manager = this.findOrCreateConMan();
         return manager.getOutputStreamByURL(ch, partitionURL);
      }
   }

   private MsgAbbrevOutputStream getOutputStreamByName(ServerChannel channel, String partitionName) throws IOException {
      if (partitionName == null) {
         throw new IllegalArgumentException("Can't proceed further without partitionName");
      } else {
         this.connectionCheck();

         ServerChannel ch;
         try {
            ch = this.ensureConnectionEstablishedByName(channel, partitionName);
         } catch (Exception var6) {
            throw new ConnectException("Could not establish a connectionManager with " + this.getID() + ", " + partitionName + ' ' + var6.toString(), var6);
         } catch (Throwable var7) {
            throw new ConnectException("Could not establish a connectionManager with " + this.getID() + ", " + var7.toString(), new Exception(var7));
         }

         synchronized(this.peerGoneListeners) {
            if (this.peerRequestConnectionShutdown) {
               throw new UnrecoverableConnectionException("This RJVM has already been shutdown " + this.getID());
            }

            if (this.isDead) {
               throw new ConnectException("This RJVM has already been shutdown " + this.getID());
            }
         }

         ConnectionManager manager = this.findOrCreateConMan();
         return manager.getOutputStreamByName(ch, partitionName);
      }
   }

   public JVMID getID() {
      return this.id;
   }

   public final AuthenticatedUser getUser(String partitionName) {
      if (partitionName == null) {
         throw new IllegalArgumentException("getUser called with null partitionName");
      } else {
         return (AuthenticatedUser)this.userTable.get(partitionName);
      }
   }

   public final void setUser(String partitionURL, AuthenticatedUser au) {
      if (au == null) {
         throw new IllegalArgumentException("setUser called with null AuthenticatedUser");
      } else if (partitionURL == null) {
         throw new IllegalArgumentException("setUser called with null partitionURL");
      } else {
         String pName = this.findOrCreateConMan().getPartitionNameByURL(partitionURL);
         if (pName == null) {
            throw new IllegalStateException("Can't find partitionName for URL: " + partitionURL);
         } else {
            this.userTable.putIfAbsent(pName, au);
         }
      }
   }

   final ConnectionManager findOrCreateConMan() {
      if (this.connectionManager == null) {
         synchronized(this.connectionManagerCreateLock) {
            if (this.connectionManager == null) {
               this.connectionManager = ConnectionManager.create(this);
               if (this.isDead) {
                  this.connectionManager.shutdown();
               }
            }
         }
      }

      return this.connectionManager;
   }

   final ConnectionManager findOrSetConMan(ConnectionManager useThisIfNull) {
      if (this.connectionManager == null) {
         synchronized(this.connectionManagerCreateLock) {
            if (this.connectionManager == null) {
               this.connectionManager = useThisIfNull;
               if (this.isDead) {
                  this.connectionManager.shutdown();
               }
            } else if (this.connectionManager != useThisIfNull) {
               this.connectionManager.mergeConnections(useThisIfNull);
            }
         }
      }

      return this.connectionManager;
   }

   final void findOrCreateConManRouter(ConnectionManager inRouter) {
      ConnectionManager myConMan = this.findOrCreateConMan();
      myConMan.setRouter(inRouter);
   }

   public final void addPeerGoneListener(PeerGoneListener peerGoneListener) {
      synchronized(this.peerGoneListeners) {
         if (this.isDead) {
            PeerGoneEvent ev = new PeerGoneEvent(this, new IOException("RJVM has already been shutdown"));
            peerGoneListener.peerGone(ev);
         } else {
            this.peerGoneListeners.add(peerGoneListener);
            if (peerGoneListener instanceof PartitionGoneListener) {
               this.addPartitionGoneListener((PartitionGoneListener)peerGoneListener);
            }
         }

      }
   }

   public final void removePeerGoneListener(PeerGoneListener peerGoneListener) {
      synchronized(this.peerGoneListeners) {
         this.peerGoneListeners.remove(peerGoneListener);
         if (peerGoneListener instanceof PartitionGoneListener) {
            this.removePartitionGoneListener((PartitionGoneListener)peerGoneListener);
         }

      }
   }

   public final void addPartitionGoneListener(PartitionGoneListener partitionGoneListener) {
      synchronized(this.partitionGoneListeners) {
         this.partitionGoneListeners.add(partitionGoneListener);
      }
   }

   public final void removePartitionGoneListener(PartitionGoneListener partitionGoneListener) {
      synchronized(this.partitionGoneListeners) {
         this.partitionGoneListeners.remove(partitionGoneListener);
      }
   }

   private int addPendingResponse(ResponseImpl resp, Object txContext) {
      if (Thread.currentThread().getName().contains("weblogic.kernel.Non-Blocking") && KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug2("Sending an outbound request on a non-blocking thread", new Exception());
      }

      synchronized(this.pendingResponses) {
         while(true) {
            int responseId = this.nextResponseId++;
            ResponseImpl oldResp = (ResponseImpl)this.pendingResponses.remove(responseId);
            if (oldResp == null) {
               resp.setId(responseId);
               if (resp.getTimeout() > 0 && txContext != null) {
                  int txTimeout = this.getTxTimeout(txContext);
                  if (txTimeout > resp.getTimeout()) {
                     resp.setTimeout(txTimeout);
                  }
               }

               if (this.isDead) {
                  PeerGoneEvent ev = new PeerGoneEvent(this, new IOException("RJVM has already been shutdown"));
                  if (!resp.hasTxContext()) {
                     resp.setTxContext(txContext);
                  }

                  resp.peerGone(ev);
               } else {
                  if (txContext != null) {
                     this.responseContexts.put(resp, txContext);
                  }

                  this.pendingResponses.put(resp);
               }

               return responseId;
            }

            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               RJVMLogger.logDebug("Interrupting an old request with responseId: " + responseId);
            }

            oldResp.notify((Throwable)(new IOException("Interrupting waiting for response")));
         }
      }
   }

   private int getTxTimeout(Object txContext) {
      Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
      return ti != null ? ti.getTransactionTimeout(txContext) : 0;
   }

   public ResponseImpl removePendingResponse(int responseId) {
      synchronized(this.pendingResponses) {
         ResponseImpl returnable = (ResponseImpl)this.pendingResponses.remove(responseId);
         if (returnable != null) {
            this.responseContexts.remove(returnable);
         }

         return returnable;
      }
   }

   public final MsgAbbrevOutputStream getRequestStream(String partitionName) throws IOException {
      return this.getRequestStream((ServerChannel)null, partitionName, (String)null);
   }

   public final MsgAbbrevOutputStream getRequestStream(ServerChannel channel) throws IOException {
      return this.getRequestStream(channel, "DOMAIN", (String)null);
   }

   public final MsgAbbrevOutputStream getRequestStream(ServerChannel channel, String partitionName, String partitionURL) throws IOException {
      if (partitionName == null && partitionURL == null) {
         throw new IllegalArgumentException("Both partitionName and partitionURL are NULL");
      } else {
         AuthenticatedSubject subject = RMIEnvironment.getEnvironment().getCurrentSubjectForWire(kernelId);
         byte QOS = subject != null ? subject.getQOS() : 101;
         channel = this.ensureChannel(channel, QOS);
         if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug("getRequestStream() for user: " + subject + ", QOS: " + QOS + ", channel: " + channel + ", partitionName: " + partitionName + ", partitionURL: " + partitionURL);
         }

         return this.getMsgAbbrevOutputStream(channel, partitionName, partitionURL, subject);
      }
   }

   MsgAbbrevOutputStream getMsgAbbrevOutputStream(ServerChannel channel, String partitionName, String partitionURL, AuthenticatedSubject subject) throws IOException {
      try {
         MsgAbbrevOutputStream out = partitionName != null ? this.getOutputStreamByName(channel, partitionName) : this.getOutputStreamByURL(channel, partitionURL);
         out.setUser(subject);
         return out;
      } catch (IOException var6) {
         if (this.connectionManager != null && !this.connectionManager.isConnectedInPairedConnTable()) {
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               RJVMLogger.logDebug("Throwing PeerGone; partitionName: " + partitionName + " partitionURL: " + partitionURL);
            }

            this.peerGone(var6);
         }

         throw var6;
      }
   }

   protected ServerChannel ensureChannel(ServerChannel channel, byte QOS) {
      Protocol protocol = this.getProtocolToUse(QOS);
      if (protocol != null) {
         if (channel == null || !ProtocolManager.getRealProtocol(channel.getProtocol()).equals(protocol)) {
            channel = this.findOrCreateConMan().protocolToChannel(protocol);
         }
      } else if (channel == null || !channel.getProtocol().isSatisfactoryQOS(QOS)) {
         channel = this.findOrCreateConMan().qosToChannel(QOS);
      }

      return channel;
   }

   private void connectionCheck() throws IOException {
      if (this.peerRequestConnectionShutdown) {
         throw new UnrecoverableConnectionException("This RJVM has already been shutdown " + this.getID());
      } else if (this.isDead) {
         throw new ConnectException("This RJVM has already been shutdown " + this.getID());
      }
   }

   private Protocol getProtocolToUse(byte QOS) {
      Protocol protocol = ProtocolStack.get();
      if (protocol != null) {
         protocol = ProtocolManager.getRealProtocol(protocol);
         if (protocol.isSatisfactoryQOS(QOS) && this.isProtocolSupported(protocol)) {
            return protocol;
         }
      }

      return null;
   }

   private boolean isProtocolSupported(Protocol protocol) {
      if (!this.getID().isClient() && !this.getID().isBootstrapping()) {
         return this.getID().getPort(protocol) != -1;
      } else {
         return true;
      }
   }

   public final MsgAbbrevOutputStream getRequestStreamForDefaultUser(Protocol protocol, String partitionName, String partitionURL) throws IOException {
      AuthenticatedSubject subject = RMIEnvironment.getEnvironment().getCurrentSubjectForWire(kernelId);
      byte QOS = subject == null ? 101 : subject.getQOS();
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug("getRequestStreamForDefaultUser() Using QOS of the current subject on the wire: " + subject + ", QOS: " + QOS + ", partitionName: " + partitionName + ", partitionURL: " + partitionURL);
      }

      MsgAbbrevOutputStream out;
      if (QOS == 103) {
         try {
            if (!protocol.isSatisfactoryQOS(QOS)) {
               protocol = protocol.upgrade();
            }

            out = partitionName != null ? this.getOutputStreamByName(ServerChannelManager.findOutboundServerChannel(protocol), partitionName) : this.getOutputStreamByURL(ServerChannelManager.findOutboundServerChannel(protocol), partitionURL);
            out.setUser(subject);
            return out;
         } catch (IOException var7) {
            this.peerGone(var7);
            throw var7;
         }
      } else {
         QOS = 101;

         try {
            if (!protocol.isSatisfactoryQOS(QOS)) {
               protocol = protocol.upgrade();
            }

            out = partitionName != null ? this.getOutputStreamByName(ServerChannelManager.findOutboundServerChannel(protocol), partitionName) : this.getOutputStreamByURL(ServerChannelManager.findOutboundServerChannel(protocol), partitionURL);
            out.setUser((AuthenticatedUser)null);
            return out;
         } catch (IOException var8) {
            this.peerGone(var8);
            throw var8;
         }
      }
   }

   public final MsgAbbrevOutputStream getResponseStream(ServerChannel channel, byte qos, String partitionName) throws IOException {
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug("getResponseStream() ServerChannel: " + channel + ", QOS: " + qos + ", partitionName: " + partitionName);
      }

      try {
         MsgAbbrevOutputStream out = this.getOutputStreamByName(channel, partitionName);
         out.header.QOS = qos;
         return out;
      } catch (IOException var5) {
         if (!this.findOrCreateConMan().isConnectedInPairedConnTable()) {
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               RJVMLogger.logDebug("Throwing PeerGone");
            }

            this.peerGone(var5);
         }

         throw var5;
      }
   }

   public final InvokableFinder getFinder() {
      return this.finder;
   }

   public final boolean isDead() {
      return this.isDead;
   }

   final boolean isScavengeable(int scavengeInterval) {
      boolean scavengeable = false;
      if (this.peerInfo != null) {
         if (!this.findOrCreateConMan().isInUse()) {
            ++this.notInUseCounter;
            if (this.notInUseCounter == scavengeInterval) {
               scavengeable = true;
            }
         } else {
            this.notInUseCounter = 0;
         }
      }

      return scavengeable;
   }

   public final HostID getHostID() {
      return this.getID();
   }

   public final Channel getRemoteChannel() {
      Debug.assertion(this.remoteChannel != null);
      return this.remoteChannel;
   }

   public final ServerChannel getServerChannel() {
      throw new AssertionError("getServerChannel() not supported");
   }

   public final OutboundRequest getOutboundRequest(RemoteReference rr, RuntimeMethodDescriptor md, String serverChannel, String partitionURL) throws IOException {
      ServerChannel channel = serverChannel == null ? null : ServerChannelManager.findLocalServerChannel(serverChannel);
      return new BasicOutboundRequest(rr, this.getRequestStream(channel, (String)null, partitionURL), md);
   }

   public final OutboundRequest getOutboundRequest(RemoteReference remoteReference, RuntimeMethodDescriptor methodDescriptor, String serverChannelName, Protocol protocol, String partitionURL) throws IOException {
      ServerChannel channel = serverChannelName == null ? ServerChannelManager.findOutboundServerChannel(protocol) : ServerChannelManager.findLocalServerChannel(serverChannelName);
      return new BasicOutboundRequest(remoteReference, this.getRequestStream(channel, (String)null, partitionURL), methodDescriptor);
   }

   public final String getServerName() {
      return this.getID().getServerName();
   }

   public final String getClusterURL(ObjectInput in) {
      return this.getID().getClusterURL(in);
   }

   public final boolean addDisconnectListener(Remote stub, DisconnectListener listener) {
      synchronized(this.disconnectListeners) {
         return this.disconnectListeners.add(listener);
      }
   }

   public final boolean removeDisconnectListener(Remote stub, DisconnectListener listener) {
      synchronized(this.disconnectListeners) {
         return this.disconnectListeners.remove(listener);
      }
   }

   private Date getConnectTime() {
      return this.connectTime;
   }

   public final boolean isUnresponsive() {
      return this.hbIdlePeriods > 1;
   }

   private void setPublicKey(byte[] key) {
      this.sharedSecret = LocalRJVM.getLocalRJVM().getSharedKey(key);
   }

   final void send(int id, JVMMessage.Command cmd, ResponseImpl resp, MsgAbbrevOutputStream msgStream, byte QOS) {
      ConnectionManager cm = this.findOrCreateConMan();
      Protocol protocol = ProtocolManager.getProtocol(QOS);
      AuthenticatedUser user = msgStream.getUser();
      byte qos = QOS;
      if (user != null) {
         qos = user.getQOS();
      }

      if (!protocol.isSatisfactoryQOS(qos)) {
         QOS = qos;
      }

      msgStream.header.init(this.getID(), QOS, cmd);
      if (resp == null) {
         msgStream.header.responseId = msgStream.getReplyID();
      } else {
         msgStream.header.responseId = this.addPendingResponse(resp, msgStream.getTxContext());
      }

      msgStream.header.invokableId = id;
      cm.sendMsg(msgStream);
      this.sentNoMessageRecently = false;
   }

   final void gotExceptionSending(JVMMessage[] outstanding, String partitionName, IOException exception) {
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug2("Exception sending on " + this.getID() + " for partition " + partitionName, exception);
      }

      JVMMessage[] var4 = outstanding;
      int var5 = outstanding.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         JVMMessage msg = var4[var6];
         if (msg != null && JVMID.localID().equals(msg.src) && this.getID().equals(msg.dest)) {
            ResponseImpl resp = this.removePendingResponse(msg.responseId);
            if (resp != null) {
               resp.notify((Throwable)exception);
            }
         }
      }

      this.peerGone(exception);
   }

   final void gotExceptionReceiving(Throwable exception, String protocol) {
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         RJVMLogger.logDebug2("Exception receiving " + protocol + " message on " + this.getID(), exception);
      }

      PeerGoneException e;
      if (exception instanceof PeerGoneException) {
         e = (PeerGoneException)exception;
      } else {
         Exception nested = exception instanceof Exception ? (Exception)exception : new NestedException(exception);
         e = new PeerGoneException("", (Exception)nested);
      }

      this.peerGone(e);
   }

   public final void messageReceived() {
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         long currentTime = System.currentTimeMillis();
         long difference = currentTime - this.timeOfLastMessage;
         if (this.periodLengthMillis != 0 && this.timeOfLastMessage != -1L && difference > (long)(this.periodLengthMillis + 200)) {
            RJVMLogger.logDebug("PeerGone danger - no message received over a: '" + difference + "' millisecond period");
         }

         this.timeOfLastMessage = currentTime;
      }

      if (this.monitorTrigger == null) {
         this.startHeartbeatTimer(this.periodLengthMillis);
      }

      this.hbMessageReceivedThisPeriod = true;
   }

   final void dispatch(MsgAbbrevInputStream is) {
      this.messageReceived();
      JVMMessage header = is.getMessageHeader();
      switch (header.cmd) {
         case CMD_INTERNAL:
            break;
         case CMD_ONE_WAY:
         case CMD_REQUEST:
            this.dispatchRequest(is);
            break;
         case CMD_RESPONSE:
            this.dispatchResponse(is);
            break;
         case CMD_ERROR_RESPONSE:
            this.dispatchErrorResponse(is);
            break;
         default:
            throw new AssertionError("Received unknown CMD: '" + header.cmd + '\'');
      }

   }

   private void dispatchRequest(MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();

      try {
         inputStream.setResponseId(header.responseId);
         int rid = header.invokableId;
         if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug("dispatchRequest with " + inputStream.getSubject());
         }

         if (rid == 1) {
            RemoteInvokable ri = this.finder.lookupRemoteInvokable(rid);
            if (ri == null) {
               throw new NoSuchObjectException("Unable to dispatch request for boot time services: the object has been garbage collected.");
            }

            RJVMEnvironment.getEnvironment().invokeBootService(ri, inputStream);
            return;
         }

         SecurityException se = null;
         if (RJVMEnvironment.getEnvironment().isServer() && rid != 27 && inputStream.getConnection().getQOS() == 103 && rid != 2) {
            AuthenticatedSubject subject = (AuthenticatedSubject)inputStream.getSubject();
            if (subject == null) {
               se = new SecurityException("Authentication Denied");
            } else if (!SubjectUtils.doesUserHaveAnyAdminRoles(subject)) {
               if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
                  RJVMLogger.logDebug("Subject " + subject + " does not have system administrator privilege");
               }

               se = new SecurityException("User " + SubjectUtils.getPrincipalNames(subject) + " does not have access to the administrator port.");
            }
         }

         ServerReference sRef = OIDManager.getInstance().findServerReference(rid);
         if (sRef == null) {
            RemoteInvokable ri = this.finder.lookupRemoteInvokable(rid);
            if (ri != null) {
               ri.invoke(inputStream);
               return;
            }

            try {
               sRef = OIDManager.getInstance().getServerReference(rid);
            } catch (NoSuchObjectException var10) {
               if (inputStream.getTxContext() != null) {
                  Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
                  if (ti != null) {
                     ti.receiveRequest(inputStream.getTxContext());
                  }

                  WorkManagerFactory.getInstance().getSystem().schedule(new TxErrorReporter(inputStream, var10));
                  return;
               }

               throw var10;
            }
         }

         if (se == null) {
            sRef.dispatch(inputStream);
         } else {
            sRef.dispatchError(inputStream, se);
         }
      } catch (Throwable var11) {
         Throwable t = var11;
         if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
            RJVMLogger.logDebug2("Dispatch problem", var11);
         }

         try {
            WorkManagerFactory.getInstance().getSystem().schedule(new ErrorReporter(inputStream.getResponseStream(), t));
         } catch (IOException var9) {
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               RJVMLogger.logDebug2("Failed to deliever error response to client", var9);
            }
         }

         if (var11 instanceof Error) {
            throw (Error)var11;
         }
      }

   }

   private void dispatchResponse(MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      int responseNumber = header.responseId;
      ResponseImpl resp = this.removePendingResponse(responseNumber);
      if (resp == null) {
         RJVMLogger.logUnsolResponse(responseNumber);
      } else {
         resp.setTxContext(inputStream.getTxContext());
         resp.notify((WLObjectInput)inputStream);
      }
   }

   private void dispatchErrorResponse(MsgAbbrevInputStream inputStream) {
      JVMMessage header = inputStream.getMessageHeader();
      int rid = header.invokableId;
      ResponseImpl resp = this.removePendingResponse(rid);
      if (resp == null) {
         RJVMLogger.logUnsolResponseError(rid);
      } else {
         resp.setTxContext(inputStream.getTxContext());
         resp.notifyError(inputStream);
      }
   }

   final void completeConnectionSetup(int remotePeriodLength, byte[] sharedSecret, PeerInfo peerInfo, MsgAbbrevJVMConnection conn, byte QOS) {
      if (!this.isDead) {
         ServerChannel channel = conn.getChannel();
         if (this.peerInfo != null) {
            if (remotePeriodLength != this.periodLengthMillis) {
               RJVMLogger.logHBPeriod((long)remotePeriodLength, (long)this.periodLengthMillis);
            }

         } else {
            synchronized(this) {
               if (this.peerInfo != null) {
                  if (remotePeriodLength != this.periodLengthMillis) {
                     RJVMLogger.logHBPeriod((long)remotePeriodLength, (long)this.periodLengthMillis);
                  }

               } else {
                  if (sharedSecret != null) {
                     this.setPublicKey(sharedSecret);
                  }

                  if (this.id != null && this.id != JVMID.localID()) {
                     int localPeriodLength = HeartbeatMonitor.periodLengthMillis();
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logDebug("Remote heartbeat: '" + remotePeriodLength + "', local heartbeat: '" + localPeriodLength + '\'');
                     }

                     if (localPeriodLength != 0 && remotePeriodLength != 0) {
                        this.periodLengthMillis = Math.max(remotePeriodLength, localPeriodLength);
                        if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                           RJVMLogger.logDebug("Setting heartbeat for RJVM: '" + this.getID() + "' to: '" + this.periodLengthMillis + "' milliseconds");
                        }

                        if (this.bootstrapping) {
                           this.startHeartbeatTimer(this.periodLengthMillis);
                        }
                     } else {
                        if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                           RJVMLogger.logDebug("Disabling heartbeats for RJVM: '" + this.getID() + '\'');
                        }

                        this.periodLengthMillis = 0;
                     }
                  }

                  Protocol prot = channel != null ? channel.getProtocol() : null;
                  if (prot != null) {
                     this.remoteChannel = this.createRemoteChannel(this.id, this.findOrCreateConMan().protocolToChannel(prot));
                  } else {
                     this.remoteChannel = this.createRemoteChannel(this.id, this.findOrCreateConMan().qosToChannel(QOS));
                  }

                  this.interopMode = !LocalRJVM.getLocalRJVM().getPeerInfo().equals(peerInfo);
                  this.peerChannelMaxMessageSize = conn.getPeerChannelMaxMessageSize();
                  this.peerInfo = peerInfo;
                  this.preDiabloPeer = this.isPreDiabloPeer();
                  if (this.id != null && this.id.isServer()) {
                     ServerIdentityManager.recordIdentity(this.id);
                  }

               }
            }
         }
      }
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }

   final byte[] getSharedSecret() {
      return this.sharedSecret;
   }

   final void setSharedSecret(byte[] key) {
      this.sharedSecret = key;
   }

   int getPeriodLengthMillis() {
      return this.periodLengthMillis;
   }

   final boolean getInteropMode() {
      return this.interopMode;
   }

   final boolean isPreDiablo() {
      return this.preDiabloPeer;
   }

   private void signalPeerGoneOnPendingResponses(PeerGoneEvent ev) {
      synchronized(this.pendingResponses) {
         ResponseImpl response;
         for(Enumeration e = this.pendingResponses.elements(); e.hasMoreElements(); response.peerGone(ev)) {
            response = (ResponseImpl)e.nextElement();
            if (!response.hasTxContext()) {
               response.setTxContext(this.responseContexts.remove(response));
            }
         }

      }
   }

   final void peerGone(IOException reason) {
      this.peerGone(reason, false);
   }

   final void peerGone(IOException reason, boolean suppressPeerGone) {
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug2("!!!!!! Firing PeerGone to " + this.getID(), (Throwable)reason);
      }

      try {
         if (reason instanceof UnrecoverableConnectionException) {
            this.peerRequestConnectionShutdown = true;
         }

         if (this.peerInfo == null) {
            reason = new ConnectException("PeerGone thrown while in non-connected state", (Exception)reason);
         }

         final PeerGoneEvent ev = new PeerGoneEvent(this, (IOException)reason, suppressPeerGone);
         synchronized(this) {
            if (this.isDead) {
               return;
            }

            synchronized(this.pendingResponses) {
               synchronized(this.connectionManagerCreateLock) {
                  synchronized(this.peerGoneListeners) {
                     this.isDead = true;
                     if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                        RJVMLogger.logDebug2("RJVM [" + this + "] is set to be dead!", new Throwable());
                     }
                  }
               }
            }

            this.peerChannelMaxMessageSize = -1;
            this.peerInfo = null;
            this.interopMode = true;
            this.notifyAll();
         }

         if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug2("Signaling peer: '" + this.getID() + "' gone - " + reason, new Throwable((Throwable)reason));
         }

         this.findOrCreateConMan().shutdown();
         this.signalPeerGoneOnPendingResponses(ev);
         final Set peerGoneListenersCopy = new ArraySet();
         synchronized(this.peerGoneListeners) {
            peerGoneListenersCopy.addAll(this.peerGoneListeners);
         }

         final Set disconnectListenersCopy = new ArraySet();
         synchronized(this.disconnectListeners) {
            disconnectListenersCopy.addAll(this.disconnectListeners);
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               Iterator var1 = peerGoneListenersCopy.iterator();

               StringBuilder sb;
               while(var1.hasNext()) {
                  PeerGoneListener nextElemx = (PeerGoneListener)var1.next();

                  try {
                     (new PeerGoneDeliverer(ev, nextElemx)).run();
                  } catch (Throwable var6) {
                     if (RJVMImpl.debugConnection.isDebugEnabled()) {
                        sb = new StringBuilder();
                        sb.append("Thrown while delivering PeerGoneEvent ").append(ev);
                        sb.append(" to ").append(nextElemx);
                        RJVMImpl.debugConnection.debug(sb.toString(), var6);
                     }
                  }
               }

               var1 = disconnectListenersCopy.iterator();

               while(var1.hasNext()) {
                  DisconnectListener nextElem = (DisconnectListener)var1.next();

                  try {
                     (new DisconnectEventDeliverer(RJVMImpl.this.getServerName(), ev.getReason(), nextElem)).run();
                  } catch (Throwable var5) {
                     if (RJVMImpl.debugConnection.isDebugEnabled()) {
                        sb = new StringBuilder();
                        sb.append("Thrown while delivering PeerGoneEvent ").append(ev);
                        sb.append(" to ").append(nextElem);
                        RJVMImpl.debugConnection.debug(sb.toString(), var5);
                     }
                  }
               }

               RJVMManager.getRJVMManager().peerGone(ev);
            }
         });
      } finally {
         this.close();
      }

   }

   final void partitionGone(String partitionName, Throwable throwable) {
      try {
         PeerGoneEvent ev = new PeerGoneEvent(this, new IOException(throwable), true, partitionName);
         Iterator var4 = this.partitionGoneListeners.iterator();

         while(var4.hasNext()) {
            PartitionGoneListener partitionGoneListener = (PartitionGoneListener)var4.next();

            try {
               (new PartitionGoneDeliverer(partitionGoneListener, ev)).run();
            } catch (Throwable var8) {
               if (debugConnection.isDebugEnabled()) {
                  StringBuilder sb = new StringBuilder();
                  sb.append("Thrown while delivering Partition Gone Event ").append(ev);
                  sb.append(" to ").append(partitionGoneListener);
                  debugConnection.debug(sb.toString(), var8);
               }
            }
         }
      } catch (Throwable var9) {
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("error occured notifying listener", var9);
         }
      }

   }

   public final void disconnect() {
      this.disconnect("User requested disconnect", false);
   }

   public final void disconnect(String msg, boolean suppressPeerGone) {
      this.peerGone(new PeerGoneException(msg), suppressPeerGone);
   }

   public long getCreationTime() {
      return this.getConnectTime().getTime();
   }

   public final Object getColocatedServices() throws RemoteException {
      if (this.services != null) {
         return this.services;
      } else if (!this.getID().isServer()) {
         throw new StubNotFoundException("RJVM: '" + this.getID() + "' not a server");
      } else {
         AuthenticatedSubject subject = RMIEnvironment.getEnvironment().getCurrentSubjectForWire(kernelId);
         byte QOS = subject == null ? 101 : subject.getQOS();
         Protocol protocol = this.findOrCreateConMan().qosToChannel(QOS).getProtocol();
         int port = this.getID().getPort(protocol);
         if (port == -1) {
            throw new ConnectIOException("No valid port for protocol: '" + protocol + '\'');
         } else {
            this.services = this.getT3ServicesUsingClient(protocol.getProtocolName() + "://" + this.getID().address().getHostName() + ':' + port, subject);
            return this.services;
         }
      }
   }

   private Object getT3ServicesUsingClient(String url, UserInfo user) throws ConnectException, ConnectIOException, ServerException {
      try {
         Class c = Class.forName("weblogic.common.T3Client");
         Class[] argTypes = new Class[]{String.class, UserInfo.class};
         Constructor constructor = c.getConstructor(argTypes);
         Object[] args = new Object[]{url, user};
         Object t3Client = constructor.newInstance(args);
         argTypes = new Class[0];
         Method connect = c.getMethod("connect", argTypes);
         args = new Object[0];
         connect.invoke(t3Client, args);
         Method getT3Services = c.getMethod("getT3Services", argTypes);
         return getT3Services.invoke(t3Client, args);
      } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | NoSuchMethodException var10) {
         throw new AssertionError(var10);
      } catch (InvocationTargetException var11) {
         Throwable actual = var11.getTargetException();
         if (actual instanceof SecurityException) {
            throw (SecurityException)actual;
         } else if (actual instanceof T3Exception) {
            throw new ConnectException("Problem getting services", (Exception)actual);
         } else if (actual instanceof T3ExecuteException) {
            throw new ServerException("Problem getting services", (Exception)actual);
         } else if (actual instanceof UnknownHostException) {
            throw (Error)(new AssertionError("Previously known host: '" + Arrays.toString(this.getID().address().getAddress()) + "' now unknown")).initCause(actual);
         } else if (actual instanceof IOException) {
            throw new ConnectIOException("Problem getting services", (Exception)actual);
         } else {
            throw (Error)(new AssertionError("Unexpected exception")).initCause(actual);
         }
      }
   }

   public final String toString() {
      return super.toString() + " - JVMID: '" + this.getID() + "' connect time: '" + this.getConnectTime() + '\'';
   }

   public final String getCodebase(Protocol protocol) {
      String prot = protocol.isSecure() ? "https" : "http";
      InetAddress addr = this.getID().getInetAddress();
      int port = this.getID().getPort(protocol);
      StringBuilder cb = new StringBuilder(prot);
      cb.append("://");
      if (addr instanceof Inet6Address) {
         cb.append('[').append(addr.getHostAddress()).append(']');
      } else {
         cb.append(addr.getHostAddress());
      }

      cb.append(':').append(port);
      if (KernelStatus.getTunellingURLPrefix() != null) {
         cb.append(KernelStatus.getTunellingURLPrefix());
      }

      cb.append(RJVMEnvironment.getEnvironment().getInternalWebAppContextPath()).append("/classes/");
      return cb.toString();
   }

   private void close() {
      if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
         RJVMLogger.logDebug("Closing: '" + this.getID() + '\'');
      }

      this.cancelHeartbeatTimer();
   }

   private void startHeartbeatTimer(int periodLength) {
      if (periodLength > 0) {
         HeartbeatChecker trigger = new HeartbeatChecker(periodLength);
         this.monitorTrigger = TimerManagerFactory.getTimerManagerFactory().getTimerManager("RJVMHeartbeats", "weblogic.kernel.System").schedule(trigger, (long)this.periodLengthMillis, (long)this.periodLengthMillis);
      }
   }

   public AuthenticatedSubject getCurrentSubjectForWire() {
      return RMIEnvironment.getEnvironment().getCurrentSubjectForWire(kernelId);
   }

   private void cancelHeartbeatTimer() {
      if (this.monitorTrigger != null) {
         this.monitorTrigger.cancel();
      }

   }

   public int getPeerChannelMaxMessageSize() {
      return this.peerChannelMaxMessageSize;
   }

   public void waitBootstrapDone() {
      synchronized(this.bootstrapLock) {
         while(this.bootstrapping) {
            try {
               this.bootstrapLock.wait();
            } catch (InterruptedException var4) {
            }
         }

      }
   }

   private static final class TxErrorReporter extends WorkAdapter {
      final InboundRequest request;
      final Throwable problem;

      TxErrorReporter(InboundRequest request, Throwable problem) {
         this.request = request;
         this.problem = problem;
      }

      public final void run() {
         try {
            Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
            if (ti != null) {
               ti.dispatchRequest(this.request.getTxContext());
            }

            OutboundResponse response = this.request.getOutboundResponse();
            response.transferThreadLocalContext(this.request);
            response.sendThrowable(this.problem);
         } catch (IOException var3) {
            RJVMLogger.logDebug2("Unable to send error response to client", var3);
         }

      }

      public final String toString() {
         return super.toString() + " - problem: '" + this.problem + '\'';
      }
   }

   private static final class ErrorReporter extends WorkAdapter {
      final ReplyStream resp;
      final Throwable problem;

      ErrorReporter(ReplyStream resp, Throwable problem) {
         this.resp = resp;
         this.problem = problem;
      }

      public final void run() {
         this.resp.sendThrowable(this.problem);
      }

      public final String toString() {
         return super.toString() + " - problem: '" + this.problem + '\'';
      }
   }

   private static final class DisconnectEventDeliverer implements Runnable {
      final Exception e;
      final DisconnectListener l;
      private String serverName;

      DisconnectEventDeliverer(String serverName, Exception e, DisconnectListener l) {
         this.serverName = serverName;
         this.e = e;
         this.l = l;
      }

      public final void run() {
         Object event;
         if (this.serverName != null) {
            event = new ServerDisconnectEventImpl(this.e, this.serverName);
         } else {
            event = new DisconnectEventImpl(this.e);
         }

         this.l.onDisconnect((DisconnectEvent)event);
      }

      public final String toString() {
         return super.toString() + " - reason: '" + this.e + '\'';
      }
   }

   private static final class PeerGoneDeliverer implements Runnable {
      final PeerGoneEvent ev;
      final PeerGoneListener l;

      PeerGoneDeliverer(PeerGoneEvent ev, PeerGoneListener l) {
         this.ev = ev;
         this.l = l;
      }

      public final void run() {
         if (!this.ev.suppressPeerGoneEvent()) {
            this.l.peerGone(this.ev);
         }

      }

      public final String toString() {
         return super.toString() + " - event: '" + this.ev + '\'';
      }
   }

   private final class HeartbeatChecker implements NakedTimerListener {
      private final int period;

      HeartbeatChecker(int period) {
         this.period = period;
      }

      public final void timerExpired(Timer timer) {
         if (!RJVMImpl.this.connectionManager.isConnectedInPairedConnTable()) {
            if (KernelStatus.DEBUG && RJVMImpl.debugConnection.isDebugEnabled()) {
               RJVMLogger.logDebug("Heartbeat is ignored since current RJVM [" + this + "] is NOT connected yet!");
            }

         } else {
            try {
               if (this.hbCheckTimeout()) {
                  RJVMImpl.this.peerGone(new PeerGoneException("No message was received for: '" + this.period * HeartbeatMonitor.idlePeriodsUntilTimeout() / 1000 + "' seconds"));
               }

               if (!RJVMImpl.this.isDead && RJVMImpl.this.sentNoMessageRecently) {
                  RJVMImpl.this.findOrCreateConMan().sendHeartbeatMsg();
               }

               RJVMImpl.this.sentNoMessageRecently = true;
            } catch (Throwable var5) {
               Throwable t = var5;

               try {
                  RJVMLogger.logHBTrigger(RJVMImpl.this.getID().toString(), t);
               } catch (Throwable var4) {
               }
            }

         }
      }

      private boolean hbCheckTimeout() {
         RJVMImpl.this.hbIdlePeriods = RJVMImpl.this.hbMessageReceivedThisPeriod ? 0 : RJVMImpl.this.hbIdlePeriods + 1;
         RJVMImpl.this.hbMessageReceivedThisPeriod = false;
         return RJVMImpl.this.hbIdlePeriods >= HeartbeatMonitor.idlePeriodsUntilTimeout();
      }
   }

   private static final class PartitionGoneDeliverer implements Runnable {
      final PeerGoneEvent peerGoneEvent;
      final PartitionGoneListener partitionGoneListener;

      PartitionGoneDeliverer(PartitionGoneListener partitionGoneListener, PeerGoneEvent e) {
         this.peerGoneEvent = e;
         this.partitionGoneListener = partitionGoneListener;
      }

      public final void run() {
         this.partitionGoneListener.partitionGone(this.peerGoneEvent);
      }
   }
}
