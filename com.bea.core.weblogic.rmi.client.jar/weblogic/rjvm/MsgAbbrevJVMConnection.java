package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.AccessException;
import java.rmi.UnmarshalException;
import java.security.cert.X509Certificate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.AsyncMessageSender;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.ContextHandler;
import weblogic.utils.BlockingCircularQueue;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.io.Chunk;

public abstract class MsgAbbrevJVMConnection implements MessageSenderStatistics, MessageReceiverStatistics {
   public static final String CONNECT_PARAM_ABBREV_SIZE = "AS";
   public static final String CONNECT_PARAM_HEADER_LEN = "HL";
   public static final String CHANNEL_MAX_MESSAGE_SIZE = "MS";
   public static final String PARTITION_URL = "PU";
   public static final String PARTITION_NAME = "PN";
   public static final String LOCAL_PARTITION_URL = "LU";
   public static final String PROXIED = "PX";
   public static final String UPGRADE = "UP";
   public static final String LOCAL_PARTITION_NAME = "LP";
   private static final boolean ASSERT = false;
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private static final DebugLogger debugAbbrevs = DebugLogger.getDebugLogger("DebugAbbrevs");
   public static final int ABBREV_TABLE_SIZE = RJVMEnvironment.getEnvironment().getAbbrevTableSize();
   private ClusterInfo clusterInfo;
   private BubblingAbbrever abbrevTableOutbound;
   private BubblingAbbrever abbrevTableInbound;
   private boolean needDownGrade;
   private boolean isAdminQOS;
   private int remoteHeaderLength;
   private int peerChannelMaxMessageSize = -1;
   protected boolean proxied = false;
   private boolean authenticated = false;
   private String partitionURL;
   private String partitionName;
   private String localPartitionName;
   protected boolean phantomConnection = false;
   private final Object peerGoneLock = new Object() {
   };
   private final long connectTime = System.currentTimeMillis();
   private volatile CountDownLatch latch = null;
   private final WritingState writingState = new WritingState();
   private long messagesSent = 0L;
   private long messagesReceived = 0L;
   private long bytesSent = 0L;
   private long bytesReceived = 0L;
   private ConnectionManager dispatcher;
   private boolean waitForPeergone = false;

   final int getRemoteHeaderLength() {
      return this.remoteHeaderLength;
   }

   final ClusterInfo getClusterInfo() {
      return this.clusterInfo;
   }

   final void setClusterInfo(ClusterInfo ci) {
      this.clusterInfo = ci;
   }

   public final void setAdminQOS() {
      this.isAdminQOS = true;
   }

   public abstract ServerChannel getChannel();

   public abstract InetAddress getInetAddress();

   public final void doDownGrade() {
      this.needDownGrade = true;
   }

   final boolean isDownGrade() {
      return this.needDownGrade;
   }

   void markPhantom() {
      this.phantomConnection = true;
   }

   boolean isPhantom() {
      return this.phantomConnection;
   }

   public X509Certificate[] getJavaCertChain() {
      return null;
   }

   public void init(int abbrevSize, int headerLen, int peerChannelMaxMessageSize, String localPartitionName, String remotePartitionURL, String remotePartitionName) {
      this.abbrevTableOutbound = new BubblingAbbrever(abbrevSize);
      this.abbrevTableInbound = new BubblingAbbrever(abbrevSize);
      this.remoteHeaderLength = headerLen;
      this.peerChannelMaxMessageSize = peerChannelMaxMessageSize;
      this.partitionName = remotePartitionName;
      this.partitionURL = remotePartitionURL;
      this.localPartitionName = localPartitionName;
   }

   final void sendMsg(MsgAbbrevOutputStream outputStream) {
      if (this.getChannel().isT3SenderQueueDisabled()) {
         this.sendMsgNow(outputStream);
      } else {
         this.sendMsg(outputStream, false);
      }

   }

   final void sendMsg(MsgAbbrevOutputStream outputStream, boolean shouldSend) {
      if (outputStream.getServerChannel() == null) {
         outputStream.setServerChannel(this.getChannel());
      }

      boolean canSend;
      synchronized(this.writingState) {
         JVMMessage header = outputStream.getMessageHeader();
         header.abbrevOffset = outputStream.getSize();
         boolean isCSharpClient = outputStream.getEndPoint() != null && ((RJVMImpl)outputStream.getEndPoint()).isCSharpClient();
         if (header.cmd != JVMMessage.Command.CMD_IDENTIFY_RESPONSE_CSHARP && !isCSharpClient) {
            this.writeMsgAbbrevs(outputStream);
         }

         canSend = this.canSendMsg(outputStream);
      }

      if (canSend || shouldSend) {
         this.sendOutMsg(outputStream);
      }

   }

   private void sendMsgNow(MsgAbbrevOutputStream outputStream) {
      if (outputStream.getServerChannel() == null) {
         outputStream.setServerChannel(this.getChannel());
      }

      synchronized(this.writingState) {
         JVMMessage header = outputStream.getMessageHeader();
         header.abbrevOffset = outputStream.getSize();
         boolean isCSharpClient = outputStream.getEndPoint() != null && ((RJVMImpl)outputStream.getEndPoint()).isCSharpClient();
         if (header.cmd != JVMMessage.Command.CMD_IDENTIFY_RESPONSE_CSHARP && !isCSharpClient) {
            this.writeMsgAbbrevs(outputStream);
         }

         try {
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               debugMessaging.debug("Sending " + outputStream.getMessageHeader() + '\n' + outputStream.dumpBuf());
            }

            int bytesToWrite = outputStream.getLength();
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               int maxMessageSize = this.getChannel().getMaxMessageSize();
               if (bytesToWrite > maxMessageSize) {
                  debugMessaging.debug("MsgAbbrevJVMConnection.sendMsgNow bytesToWrite: " + bytesToWrite + ", maxMessageSize: " + maxMessageSize);
               }
            }

            this.sendMsg((OutgoingMessage)outputStream);
            ++this.messagesSent;
            this.bytesSent += (long)bytesToWrite;
         } catch (IOException var8) {
            RJVMLogger.logFailedSendMsgWarning(header.toString(), var8);
            if (this.dispatcher != null) {
               this.dispatcher.gotExceptionSending(header, var8);
            }
         }

      }
   }

   private void writeMsgAbbrevs(MsgAbbrevOutputStream outputStream) {
      JVMMessage header = outputStream.getMessageHeader();
      OutboundMsgAbbrev abbrevs = outputStream.getAbbrevs();

      try {
         if (header.hasJVMIDs) {
            abbrevs.addAbbrev(header.src, true);
            abbrevs.addAbbrev(header.dest, true);
         }

         Object wireUser = outputStream.getUser();
         if (wireUser instanceof AuthenticatedSubject && RJVMEnvironment.getEnvironment().isUserAnonymous((AuthenticatedSubject)wireUser)) {
            wireUser = null;
         }

         abbrevs.addAbbrev(wireUser, true);
         abbrevs.write(this.abbrevTableOutbound);
         header.hasTX |= outputStream.getTxContext() != null;
         header.hasTrace |= outputStream.getTrace() != null;
      } catch (IOException var5) {
         if (debugAbbrevs.isDebugEnabled()) {
            debugAbbrevs.debug("Local abbrevs:\n" + this.abbrevTableOutbound.dump());
         }

         throw (Error)(new AssertionError("Error writing message header and abbrevs")).initCause(var5);
      }
   }

   final void readMsgAbbrevs(MsgAbbrevInputStream res) throws IOException {
      JVMMessage header = res.getMessageHeader();
      InboundMsgAbbrev abbrevs = res.getAbbrevs();

      try {
         abbrevs.read(res, this.abbrevTableInbound);
         if (header.hasJVMIDs) {
            header.src = (JVMID)abbrevs.getAbbrev();
            header.dest = (JVMID)abbrevs.getAbbrev();
         }

         Object user = abbrevs.getAbbrev();
         this.validateRemoteAnonymousRMIT3Access((AuthenticatedUser)user, header, res);
         res.setAuthenticatedUser((AuthenticatedUser)user);
      } catch (ClassNotFoundException var5) {
         if (debugAbbrevs.isDebugEnabled()) {
            debugAbbrevs.debug("RemoteAbbrevs:\n" + this.abbrevTableInbound.dump());
         }

         throw (Error)(new AssertionError("Exception creating response stream")).initCause(var5);
      }
   }

   private boolean canSendMsg(MsgAbbrevOutputStream msg) {
      try {
         return this.writingState.sendNow(msg);
      } catch (IOException var3) {
         if (this.dispatcher != null) {
            this.dispatcher.gotExceptionSending(msg.getMessageHeader(), var3);
         }

         return false;
      }
   }

   private void sendOutMsg(MsgAbbrevOutputStream msg) {
      MsgAbbrevOutputStream towrite = null;

      try {
         long bytesNow = 0L;

         for(boolean sentMine = false; (towrite = this.writingState.continueSending(bytesNow >= (long)AsyncMessageSender.MAX_QUEUED_SEND_SIZE && sentMine)) != null; this.sendMsg((OutgoingMessage)towrite)) {
            if (towrite == msg) {
               sentMine = true;
            }

            ++this.messagesSent;
            bytesNow += (long)towrite.getLength();
            if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
               debugMessaging.debug("Sending " + towrite.getMessageHeader() + '\n' + towrite.dumpBuf());
            }
         }

         this.bytesSent += bytesNow;
      } catch (IOException var6) {
         RJVMLogger.logFailedSendMsgWarning(towrite.getMessageHeader().toString(), var6);
         this.writingState.gotIOException(var6);
         JVMMessage[] outstanding = this.writingState.dequeue(towrite);
         if (this.dispatcher != null) {
            this.dispatcher.gotExceptionSending(this, outstanding, var6);
         }
      }

   }

   final void cancelIO(JVMID rjvm) {
      this.writingState.cancelIO(rjvm);
   }

   public final long getMessagesSentCount() {
      return this.messagesSent;
   }

   public final long getBytesSentCount() {
      return this.bytesSent;
   }

   public final long getConnectTime() {
      return this.connectTime;
   }

   public final long getMessagesReceivedCount() {
      return this.messagesReceived;
   }

   public final long getBytesReceivedCount() {
      return this.bytesReceived;
   }

   public final MsgAbbrevJVMConnection setDispatcher(ConnectionManager dispatcher, boolean close) {
      MsgAbbrevJVMConnection connection = null;
      synchronized(this.peerGoneLock) {
         this.dispatcher = dispatcher;
         if (this.partitionName != null) {
            connection = dispatcher.addPartitionConnection(this, close);
         }

         if (this.waitForPeergone) {
            this.waitForPeergone = false;
            this.peerGoneLock.notify();
         }

         return connection;
      }
   }

   public final ConnectionManager getDispatcher() {
      return this.dispatcher;
   }

   void setWaitForPeergone(boolean flag) {
      synchronized(this.peerGoneLock) {
         this.waitForPeergone = flag;
      }
   }

   private void waitForPeergone() {
      synchronized(this.peerGoneLock) {
         while(this.waitForPeergone) {
            try {
               this.peerGoneLock.wait();
            } catch (InterruptedException var4) {
               if (KernelStatus.DEBUG && debugConnection.isDebugEnabled()) {
                  debugConnection.debug(" wait was interrupted on thread : [" + Thread.currentThread().getName() + "] : " + StackTraceUtils.throwable2StackTrace(var4));
               }
            }
         }

      }
   }

   public Protocol getProtocol() {
      return this.getChannel().getProtocol();
   }

   public final byte getQOS() {
      return this.isAdminQOS ? 103 : this.getProtocol().getQOS();
   }

   public abstract InetAddress getLocalAddress();

   public abstract int getLocalPort();

   public abstract void connect(String var1, InetAddress var2, int var3, int var4) throws IOException;

   protected abstract void sendMsg(OutgoingMessage var1) throws IOException;

   public abstract void close();

   public final void dispatch(Chunk data) {
      this.waitForPeergone();
      ++this.messagesReceived;
      this.bytesReceived += (long)Chunk.size(data);
      this.bytesReceived += 4L;
      ConnectionManager disp = this.getDispatcher();
      if (disp != null) {
         MsgAbbrevInputStream incomingMessage;
         try {
            incomingMessage = disp.getInputStream();
            incomingMessage.init(data, this);
         } catch (Exception var6) {
            RJVMLogger.logUnmarshal(var6);
            Exception ume = new UnmarshalException("Incoming message header or abbreviation processing failed ", var6);
            this.gotExceptionReceiving(ume);
            return;
         }

         disp.dispatch(this, incomingMessage);
      }

   }

   public final void gotExceptionReceiving(Throwable t) {
      if (debugAbbrevs.isDebugEnabled()) {
         debugAbbrevs.debug("ExceptionReceiving:" + StackTraceUtils.throwable2StackTrace(t));
         debugAbbrevs.debug("LocalAbbrevs:\n" + this.abbrevTableOutbound.dump());
         debugAbbrevs.debug("RemoteAbbrevs:\n" + this.abbrevTableInbound.dump());
      }

      ConnectionManager disp = this.getDispatcher();
      if (disp != null) {
         disp.gotExceptionReceiving(this, t);
      }

   }

   public ContextHandler getContextHandler() {
      return null;
   }

   protected void ensureForceClose() {
   }

   int getPeerChannelMaxMessageSize() {
      return this.peerChannelMaxMessageSize;
   }

   public String getPartitionUrl() {
      return this.partitionURL;
   }

   public String getRemotePartitionName() {
      return this.partitionName;
   }

   public String getLocalPartitionName() {
      return this.localPartitionName;
   }

   public void setProxied(boolean proxied) {
      this.proxied = proxied;
   }

   public void waitIdentify(int timeout) {
      if (this.latch != null) {
         try {
            if (timeout <= 0) {
               this.latch.await();
            } else {
               this.latch.await((long)timeout, TimeUnit.MILLISECONDS);
            }
         } catch (InterruptedException var3) {
         }

      }
   }

   public void beginIdentify() {
      this.latch = new CountDownLatch(1);
   }

   public void endIdentify() {
      this.latch.countDown();
   }

   private void validateRemoteAnonymousRMIT3Access(AuthenticatedUser user, JVMMessage header, MsgAbbrevInputStream res) throws IOException {
      if (KernelStatus.isServer() && !RMIEnvironment.getEnvironment().isRemoteAnonymousRMIT3Enabled()) {
         if (user == null) {
            if (this.requiresUser(header, res)) {
               InboundMsgAbbrev abbrevs = res.getAbbrevs();
               Object method = abbrevs.getAbbrev();
               OIDManager mgr = OIDManager.getInstance();
               ServerReference sref = mgr.findServerReference(header.invokableId);
               String className = sref == null ? "" : sref.getDescriptor().getRemoteClassName();
               String mthdName = method == null ? className : className + " " + method.toString();
               String jvmid = header.src == null ? "" : header.src.toString();
               RJVMLogger.logRemoteAnonymousRMIT3AccessNotAllowed(header.cmd.getValue(), header.invokableId, mthdName, jvmid);
               throw new AccessException("Anonymous RMI access not allowed for cmd " + header.cmd + " id " + header.invokableId + " mthd " + mthdName + " JVMID " + jvmid);
            }

            if (header.cmd == JVMMessage.Command.CMD_REQUEST && (header.invokableId == 1 || header.invokableId == 27)) {
               res.setValidatingClass(true);
            }
         } else {
            if (!this.isFromServer(header, res) && this.requiresUser(header, res)) {
               RMIEnvironment.getEnvironment().validateAuthenticatedUser(user);
            }

            this.authenticated = true;
         }

      }
   }

   private boolean requiresUser(JVMMessage header, MsgAbbrevInputStream res) {
      if (header.cmd == JVMMessage.Command.CMD_REQUEST && !this.isFromServer(header, res)) {
         if (header.invokableId > 256) {
            OIDManager mgr = OIDManager.getInstance();
            ServerReference sref = mgr.findServerReference(header.invokableId);
            if (sref != null) {
               String clsName = sref.getDescriptor().getRemoteClassName();
               if ("weblogic.management.remote.iiop.IIOPServerImpl".equals(clsName)) {
                  return false;
               }
            }
         }

         return header.invokableId != 1 && header.invokableId != 27 && !this.authenticated;
      } else {
         return false;
      }
   }

   private boolean isFromServer(JVMMessage header, MsgAbbrevInputStream res) {
      return res.isCollocated() || header.src != null && header.src.hasServerName();
   }

   static final class WritingState {
      private final BlockingCircularQueue sendQueue = new BlockingCircularQueue(32);
      private static final int WS_IDLE = 0;
      private static final int WS_WRITING = 1;
      private static final int WS_NEED_A_BREAK = 2;
      private static final int WS_THREAD_WAITING = 3;
      private static final int WS_GOT_IOEXCEPTION = 4;
      private int state = 0;
      private IOException exception;

      final int getQLength() {
         return this.sendQueue.size();
      }

      final synchronized boolean sendNow(MsgAbbrevOutputStream mbuf) throws IOException {
         switch (this.state) {
            case 0:
               this.state = 1;
               this.sendQueue.put(mbuf);
               return true;
            case 1:
            case 3:
               this.sendQueue.put(mbuf);
               return false;
            case 2:
               this.state = 3;
               this.sendQueue.put(mbuf);

               while(this.state == 3) {
                  try {
                     this.wait();
                  } catch (InterruptedException var3) {
                  }
               }

               return this.state != 4;
            case 4:
               throw this.exception;
            default:
               throw new AssertionError("Invalid writing state: " + this.state);
         }
      }

      final synchronized MsgAbbrevOutputStream continueSending(boolean relieveMe) {
         if (relieveMe) {
            switch (this.state) {
               case 1:
                  this.state = 2;
               case 2:
                  break;
               case 3:
                  this.state = 1;
                  this.notify();
                  return null;
               default:
                  throw new AssertionError("Invalid writing state: " + this.state);
            }
         }

         return this.getNextMessage();
      }

      private MsgAbbrevOutputStream getNextMessage() {
         MsgAbbrevOutputStream msg = (MsgAbbrevOutputStream)this.sendQueue.get();
         if (msg == null) {
            this.state = 0;
            return null;
         } else {
            return msg;
         }
      }

      final synchronized void cancelIO(JVMID rjvm) {
         for(int i = this.sendQueue.size(); i > 0; --i) {
            MsgAbbrevOutputStream msg = (MsgAbbrevOutputStream)this.sendQueue.get();
            JVMMessage header = msg.getMessageHeader();
            if (!rjvm.equals(header.dest)) {
               this.sendQueue.put(msg);
            }
         }

      }

      final synchronized JVMMessage[] dequeue(MsgAbbrevOutputStream msg) {
         JVMMessage[] outstanding = new JVMMessage[this.getQLength() + 1];
         outstanding[0] = msg.getMessageHeader();

         for(int i = 1; i < outstanding.length; ++i) {
            MsgAbbrevOutputStream message = this.getNextMessage();
            if (message != null) {
               outstanding[i] = message.getMessageHeader();
            }
         }

         return outstanding;
      }

      final synchronized void gotIOException(IOException exp) {
         this.exception = exp;
         this.state = 4;
         this.notify();
      }
   }
}
