package weblogic.iiop;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.rmi.MarshalException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.transaction.xa.XAException;
import javax.validation.constraints.NotNull;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.UNKNOWN;
import org.omg.SendingContext.RunTime;
import weblogic.common.internal.PeerInfo;
import weblogic.corba.cos.transactions.OTSHelper;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.contexts.BiDirIIOPContextImpl;
import weblogic.iiop.contexts.CodeSetServiceContext;
import weblogic.iiop.contexts.ContextOutputImpl;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.iiop.contexts.RequestUrlServiceContext;
import weblogic.iiop.contexts.SASServiceContext;
import weblogic.iiop.contexts.SendingContextRunTime;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.contexts.VendorInfo;
import weblogic.iiop.contexts.VendorInfoTx;
import weblogic.iiop.csi.ClientSecurity;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.CancelRequestMessage;
import weblogic.iiop.messages.CloseConnectionMessage;
import weblogic.iiop.messages.FragmentMessage;
import weblogic.iiop.messages.LocateReplyMessage;
import weblogic.iiop.messages.LocateRequestMessage;
import weblogic.iiop.messages.Message;
import weblogic.iiop.messages.MessageErrorMessage;
import weblogic.iiop.messages.MessageHeader;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.messages.SequencedMessage;
import weblogic.iiop.messages.SequencedRequestMessage;
import weblogic.iiop.messages.ServiceContextMessage;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.kernel.Kernel;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.DisconnectEventImpl;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.NotImplementedException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.ServerDisconnectEventImpl;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.internal.PropagationContext;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.Hex;
import weblogic.utils.SyncKeyTable;
import weblogic.utils.collections.NumericKeyHashtable;
import weblogic.utils.io.Chunk;
import weblogic.workarea.WorkContextOutput;

public abstract class EndPointImpl implements EndPoint {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final boolean DEBUG = false;
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugCategory debugMarshal = Debug.getCategory("weblogic.iiop.marshal");
   private static final DebugCategory debugSecurity = Debug.getCategory("weblogic.iiop.security");
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");
   private static final DebugLogger debugIIOPMarshal = DebugLogger.getDebugLogger("DebugIIOPMarshal");
   private static final DebugLogger debugIIOPSecurity = DebugLogger.getDebugLogger("DebugIIOPSecurity");
   private static ClientContextInterceptor[] clientContextInterceptors = new ClientContextInterceptor[]{new BiDirClientInterceptor(), new CodeBaseClientInterceptor(), new CodeSetClientInterceptor(), new VendorInfoClientInterceptor()};
   protected final boolean secure;
   protected final Connection c;
   private int contextsNeeded;
   private int nextRequestID;
   private int pendingCount;
   private final NumericKeyHashtable fragmentTable;
   private ClientSecurity clientSecurity;
   private final SyncKeyTable pendingResponses;
   private final long creationTime;
   private RunTime codebase;
   private HostID hostID;
   private final HashMap disconnectListeners;

   public IOR getCurrentIor(IOR ior, long timeout) throws RemoteException {
      SequencedRequestMessage request = null;
      Message reply = null;

      try {
         request = new LocateRequestMessage(ior, this.getMinorVersion(), this.getNextRequestID());
         request.setTimeout(timeout);
         request.marshalTo(this.createOutputStream());
         reply = this.sendReceive(request);
         IOR locatedIOR = ((LocateReplyMessage)reply).needsForwarding();
         if (locatedIOR != null) {
            ior = locatedIOR;
         }
      } finally {
         if (request != null) {
            request.getOutputStream().close();
         }

         if (reply != null) {
            reply.getInputStream().close();
         }

      }

      return ior;
   }

   public WorkContextOutput createWorkContextOutput() {
      CorbaOutputStream output = IiopProtocolFacade.createOutputStream();
      output.setCodeSets(this.getCharCodeSet(), this.getWcharCodeSet());
      return new ContextOutputImpl(output);
   }

   private void p(String s) {
      System.err.println("<EndPointImpl(" + Integer.toHexString(this.hashCode()) + ")>: " + s);
   }

   private void pfull(String s) {
      this.p("@" + System.currentTimeMillis() + ", connected to: " + this.c.getListenPoint().toString() + ": " + s);
   }

   protected boolean isSecure(Connection c) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         this.p(" +++ connection isSecure : " + c.isSecure());
      }

      return c.isSecure();
   }

   public final Connection getConnection() {
      return this.c;
   }

   public final void setCodeSets(int cs, int ws) {
      this.c.setCodeSets(cs, ws);
   }

   public final int getWcharCodeSet() {
      return this.c.getWcharCodeSet();
   }

   public final int getCharCodeSet() {
      return this.c.getCharCodeSet();
   }

   public final boolean getFlag(int f) {
      return this.c.getFlag(f);
   }

   public final void setFlag(int f) {
      this.c.setFlag(f);
   }

   public final RunTime getRemoteCodeBase() {
      if (this.codebase == null && this.isUsableRemoteCodebase(this.c.getRemoteCodeBase())) {
         try {
            this.codebase = (RunTime)PortableRemoteObject.narrow(IIOPReplacer.resolveObject(this.c.getRemoteCodeBase()), RunTime.class);
         } catch (IOException | ClassCastException var2) {
            IIOPLogger.logBadRuntime(var2);
         }
      }

      return this.codebase;
   }

   private boolean isUsableRemoteCodebase(IOR codeBaseIor) {
      return codeBaseIor != null && !IiopConfigurationFacade.isLocal(codeBaseIor);
   }

   public final void setRemoteCodeBaseIOR(IOR ior) {
      if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
         IIOPLogger.logDebugTransport(this + " setting remote codebase to " + ior);
      }

      this.c.setRemoteCodeBase(ior);
   }

   public PeerInfo getPeerInfo() {
      return this.c.getPeerInfo();
   }

   public void setPeerInfo(PeerInfo peerinfo) {
      if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
         IIOPLogger.logDebugTransport("peer is " + peerinfo);
      }

      this.c.setPeerInfo(peerinfo);
   }

   protected EndPointImpl(Connection c) {
      this(c, new ClientSecurity(c));
   }

   protected EndPointImpl(Connection connection, ClientSecurity clientSecurity) {
      this.nextRequestID = 1;
      this.pendingCount = 0;
      this.fragmentTable = new NumericKeyHashtable();
      this.pendingResponses = new SyncKeyTable();
      this.codebase = null;
      this.disconnectListeners = new HashMap();
      this.c = connection;
      this.clientSecurity = clientSecurity;
      this.secure = this.isSecure(this.c);
      this.creationTime = System.currentTimeMillis();
      ClientContextInterceptor[] var3 = clientContextInterceptors;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ClientContextInterceptor interceptor = var3[var5];
         this.contextsNeeded |= interceptor.getFlag();
      }

   }

   public ClientSecurity getClientSecurity() {
      return this.clientSecurity;
   }

   public SequencedRequestMessage getPendingResponse(int requestid) {
      return (SequencedRequestMessage)this.pendingResponses.get(requestid);
   }

   public SequencedRequestMessage removePendingResponse(int requestid) {
      return (SequencedRequestMessage)this.pendingResponses.remove(requestid);
   }

   public void registerPendingResponse(SequencedRequestMessage req) {
      this.pendingResponses.put(req);
   }

   Message createMsgFromStream(Chunk msgChunks) throws IOException {
      IIOPInputStream cdr = new IIOPInputStream(msgChunks, this.isSecure(), this);
      if (IIOPLoggerFacade.isTransportDebugEnabled()) {
         IIOPLogger.logDebugMarshal(this.getInputMessageDump(cdr));
      }

      MessageHeader messageHeader = new MessageHeader(cdr);
      if (IIOPLoggerFacade.isTransportDebugEnabled()) {
         IIOPLoggerFacade.logDebugTransport("received " + messageHeader.getMsgTypeAsString() + " message", new Object[0]);
      }

      if (this.needsHigherConnectionMinorVersion(messageHeader)) {
         this.c.setMinorVersion(messageHeader.getMinorVersion());
      }

      cdr.setSupportsJDK13Chunking(messageHeader.getMinorVersion() < 2);
      switch (messageHeader.getMsgType()) {
         case 0:
            return new RequestMessage(messageHeader, cdr);
         case 1:
            return ReplyMessage.readFromInputStream(this, messageHeader, cdr);
         case 2:
            return new CancelRequestMessage(messageHeader, cdr);
         case 3:
            return new LocateRequestMessage(messageHeader, cdr);
         case 4:
            return new LocateReplyMessage(messageHeader, cdr);
         case 5:
            return new CloseConnectionMessage(messageHeader, cdr);
         case 6:
            return new MessageErrorMessage(messageHeader, cdr);
         case 7:
            return new FragmentMessage(messageHeader, cdr);
         default:
            IIOPLogger.logGarbageMessage();
            throw new UnmarshalException("Received unknown message type: " + messageHeader.getMsgType());
      }
   }

   private String getInputMessageDump(IIOPInputStream cdr) {
      return String.format("received [%s] from %s on %s%n%s", this.getServerChannel().getProtocol(), this.getConnection().getListenPoint(), this.getServerChannel(), cdr.dumpBuf());
   }

   private boolean needsHigherConnectionMinorVersion(MessageHeader m) {
      return m.getMinorVersion() > this.c.getMinorVersion();
   }

   public final void dispatch(Chunk msgChunks) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         this.pfull("dispatch()");
      }

      Message m = null;

      try {
         m = this.createMsgFromStream(msgChunks);
         this.handleMessage(m);
      } catch (RuntimeException | IOException var5) {
         this.handleProtocolException(m, var5);
      } catch (Throwable var6) {
         UNKNOWN un = new UNKNOWN("Unhandled error: " + var6.getMessage());
         un.initCause(var6);
         this.handleProtocolException(m, un);
      }

   }

   public CorbaOutputStream createOutputStream() {
      return new IIOPOutputStream(false, this);
   }

   public void handleProtocolException(Message m, Throwable e) {
      if (m == null) {
         this.gotExceptionReceiving(e);
      } else {
         if (m.isFragmented() || m.getMsgType() == 7) {
            if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
               this.p("message fragment error");
            }

            m = this.purgeFragment((SequencedMessage)m);
         }

         this.handleErrorOnMessage(m, e);
      }
   }

   protected void handleRequestMessage(RequestMessage req) throws IOException {
      this.addOutboundContexts(req);
      this.handleIncomingRequest(req);
   }

   private void addOutboundContexts(RequestMessage req) {
      if (!this.getFlag(2)) {
         req.addOutboundServiceContext(new SendingContextRunTime(this.getCodeBaseIor()));
         this.setFlag(2);
      }

      if (!this.getFlag(4)) {
         req.addOutboundServiceContext(VendorInfo.VENDOR_INFO);
         this.setFlag(4);
      }

   }

   protected void handleIncomingRequest(RequestMessage req) throws IOException {
   }

   protected void handleErrorOnMessage(Message m, Throwable e) {
      switch (m.getMsgType()) {
         case 1:
            this.notifySender((ReplyMessage)m, e);
            return;
         case 2:
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            this.gotExceptionReceiving(e);
            return;
         case 4:
            new ConnectionShutdownHandler(this.c, e);
      }
   }

   protected OBJECT_NOT_EXIST replyNoSuchObject(String msg) {
      return new OBJECT_NOT_EXIST(msg, 1330446337, CompletionStatus.COMPLETED_NO);
   }

   private void notifySender(ReplyMessage reply, Throwable e) {
      int requestID = reply.getRequestID();
      SequencedRequestMessage request = this.removePendingResponse(requestID);
      if (request != null) {
         UnmarshalException replyEx = new UnmarshalException("GIOP protocol failure");
         replyEx.detail = e;
         reply.setThrowable(replyEx);
         request.notify((SequencedMessage)reply);
      }
   }

   private void handleMessage(Message m) throws IOException {
      if (m.isFragmented()) {
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            this.p("message is fragmented");
         }

         this.collectFragment((SequencedMessage)m);
      } else {
         this.processMessage(m);
      }
   }

   protected void processMessage(Message m) throws IOException {
      switch (m.getMsgType()) {
         case 0:
            this.handleRequestMessage((RequestMessage)m);
            return;
         case 1:
            this.handleIncomingResponse((ReplyMessage)m);
            return;
         case 2:
            return;
         case 3:
         default:
            this.gotExceptionReceiving(Utils.mapToCORBAException(new UnmarshalException("Unkown message type: " + m.getMsgType())));
            return;
         case 4:
            this.handleLocateReply((LocateReplyMessage)m);
            return;
         case 5:
            this.handleCloseConnection(m);
            return;
         case 6:
            this.handleMessageError(m);
            return;
         case 7:
            this.processFragment((FragmentMessage)m);
      }
   }

   private void gotExceptionReceiving(Throwable t) {
      ConnectionManager.handleExceptionReceiving(this.c, t);
   }

   private void handleMessageError(Message m) {
      new ConnectionShutdownHandler(this.c, new EOFException("GIOP protocol error"));
   }

   private void handleCloseConnection(Message m) {
      new ConnectionShutdownHandler(this.c, new EOFException("Connection closed by peer"), false);
   }

   private void collectFragment(SequencedMessage m) throws IOException {
      int index = m.getRequestID();
      ArrayList aList;
      synchronized(this.fragmentTable) {
         aList = (ArrayList)this.fragmentTable.get((long)index);
         if (aList == null) {
            if (m.getMsgType() == 7) {
               throw new UnmarshalException("Message Fragment out of order");
            }

            aList = new ArrayList();
            this.fragmentTable.put((long)index, aList);
         } else if (m.getMsgType() != 7) {
            throw new UnmarshalException("Message Fragment out of order");
         }
      }

      aList.add(m);
      if (debugMarshal.isEnabled() || debugIIOPMarshal.isDebugEnabled()) {
         IIOPLogger.logDebugMarshal("collected fragment " + aList.size() + " for request " + m.getRequestID());
      }

   }

   private void processFragment(FragmentMessage fragmentMessage) throws IOException {
      ArrayList aList = (ArrayList)this.fragmentTable.remove((long)fragmentMessage.getRequestID());
      aList.add(fragmentMessage);
      SequencedMessage m = (SequencedMessage)aList.remove(0);
      CorbaInputStream is = m.getInputStream();

      while(!aList.isEmpty()) {
         Message fragment = (Message)aList.remove(0);
         is.addChunks(fragment.getInputStream());
      }

      m.unmarshal();
      this.processMessage(m);
   }

   private Message purgeFragment(SequencedMessage m) {
      ArrayList aList = (ArrayList)this.fragmentTable.remove((long)m.getRequestID());
      if (aList == null) {
         return m;
      } else {
         m = (SequencedMessage)aList.remove(0);
         CorbaInputStream is = m.getInputStream();

         try {
            while(!aList.isEmpty()) {
               Message fragment = (Message)aList.remove(0);
               is.addChunks(fragment.getInputStream());
            }
         } catch (Throwable var5) {
         }

         return m;
      }
   }

   public boolean supportsForwarding() {
      return true;
   }

   void handleIncomingResponse(ReplyMessage reply) {
      this.handleReplyContexts(reply.getServiceContexts());
      if (reply.getMinorVersion() < this.getMinorVersion()) {
         this.c.setMinorVersion(reply.getMinorVersion());
      }

      int requestID = reply.getRequestID();
      if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
         IIOPLogger.logDebugTransport("REPLY(" + requestID + "): " + reply.getStatusAsString());
      }

      RequestMessage request = (RequestMessage)this.getPendingResponse(requestID);
      if (this.contextsNeeded != 0) {
         this.updateContextsNeeded(request);
      }

      PropagationContextImpl ctx;
      if (reply.getServiceContext(0) == null && request != null && (ctx = (PropagationContextImpl)request.getServiceContext(0)) != null) {
         reply.addServiceContext(ctx);
      }

      request.notify(reply);
   }

   private synchronized void updateContextsNeeded(RequestMessage request) {
      ClientContextInterceptor[] var2 = clientContextInterceptors;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ClientContextInterceptor interceptor = var2[var4];
         int flag = interceptor.getServiceContextFlagToClear(request);
         if ((flag & this.contextsNeeded) != 0) {
            this.markContextSent(flag);
         }
      }

   }

   void markContextSent(int flag) {
      this.contextsNeeded ^= flag;
   }

   private void handleReplyContexts(ServiceContextList serviceContexts) {
      SASServiceContext sas = (SASServiceContext)serviceContexts.getServiceContext(15);
      if (sas != null) {
         this.getClientSecurity().handleSASReply(sas);
      }

      VendorInfo vendorInfo = (VendorInfo)serviceContexts.getServiceContext(1111834880);
      if (vendorInfo != null && this.getPeerInfo() == null) {
         this.setPeerInfo(vendorInfo.getPeerInfo());
      }

      SendingContextRunTime scrt = (SendingContextRunTime)serviceContexts.getServiceContext(6);
      if (scrt != null) {
         this.setRemoteCodeBaseIOR(scrt.getCodeBase());
      }

   }

   private void handleLocateReply(LocateReplyMessage reply) {
      int requestID = reply.getRequestID();
      this.removePendingResponse(requestID).notify((SequencedMessage)reply);
   }

   public void send(CorbaOutputStream outboundData) throws RemoteException {
      try {
         if (IIOPLoggerFacade.isTransportDebugEnabled()) {
            IIOPLogger.logDebugMarshal(this.getOutputMessageDump(outboundData));
         }

         this.c.send(outboundData);
      } catch (IOException var3) {
         ConnectionManager.handleExceptionSending(this.c, var3);
         throw new MarshalException("IOException while sending", var3);
      }
   }

   private String getOutputMessageDump(CorbaOutputStream outboundData) {
      byte[] buf = outboundData.getBuffer();
      return String.format("sent %n%s", Hex.dump(buf, 0, buf.length));
   }

   public final Message sendReceive(SequencedRequestMessage request, int flags) throws RemoteException {
      this.sendRequest(request, flags);

      try {
         request.waitForData();
      } catch (RequestTimeoutException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new UnmarshalException("Exception waiting for response", var10);
      } catch (Throwable var11) {
         throw new UnmarshalException("Throwable waiting for response (" + var11.getClass().getName() + ") " + var11.getMessage());
      } finally {
         this.removePendingResponse(request.getRequestID());
      }

      return request.getReply();
   }

   public void sendRequest(SequencedRequestMessage request, int flags) throws RemoteException {
      this.registerPendingResponse(request);
      this.send(request.getOutputStream());
   }

   public Message sendReceive(SequencedRequestMessage request) throws RemoteException {
      return this.sendReceive(request, 0);
   }

   public RequestMessage createRequest(IOR ior, String operation, boolean oneWay) {
      RequestMessage requestMessage = new RequestMessage(ior, this.getMinorVersion(), this.getNextRequestID(), operation, oneWay);
      if (this.contextsNeeded != 0) {
         this.addServiceContexts(ior, requestMessage);
      }

      this.addRequestUrlServiceContextIfNeeded(requestMessage);
      return requestMessage;
   }

   private void addRequestUrlServiceContextIfNeeded(RequestMessage requestMessage) {
      String requestUrl = RequestUrl.get();
      if (requestUrl != null) {
         requestMessage.addServiceContext(new RequestUrlServiceContext(requestUrl));
      }

   }

   private void addServiceContexts(IOR ior, RequestMessage requestMessage) {
      ClientContextInterceptor[] var3 = clientContextInterceptors;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ClientContextInterceptor interceptor = var3[var5];
         interceptor.addServiceContexts(requestMessage, this, ior, this.contextsNeeded);
      }

   }

   public boolean hasPendingResponses() {
      return this.pendingResponses.size() > 0 || this.pendingCount > 0;
   }

   public final synchronized void cleanupPendingResponses(Throwable t) {
      Enumeration e = this.pendingResponses.elements();

      while(e.hasMoreElements()) {
         ((SequencedRequestMessage)e.nextElement()).notify(t);
      }

      this.deliverHeartbeatMonitorListenerException(t instanceof Exception ? (Exception)t : new Exception(t));
   }

   public final boolean isSecure() {
      return this.secure;
   }

   public final int getMinorVersion() {
      return this.c.getMinorVersion();
   }

   public void setMessageServiceContext(ServiceContextMessage msg, ServiceContext ctx) {
      if (ctx != null && this.areServiceContextsSupported()) {
         msg.addServiceContext(ctx);
      }

   }

   private boolean areServiceContextsSupported() {
      return this.getMinorVersion() > 0;
   }

   public ServiceContext getMessageServiceContext(ServiceContextMessage msg, int ctxId) {
      return msg.getServiceContext(ctxId);
   }

   public void setCredentials(RequestMessage request, AuthenticatedSubject subject) {
      subject = this.getEffectiveSubject(subject);
      if (!this.clientSecurity.mayIgnoreCredentials(subject)) {
         this.setMessageServiceContext(request, this.clientSecurity.getServiceContext(subject, request.getMechanismListForRequest()));
      }
   }

   private AuthenticatedSubject getEffectiveSubject(AuthenticatedSubject subject) {
      if (subject == null) {
         return RmiSecurityFacade.getAnonymousSubject();
      } else {
         return RmiSecurityFacade.isKernelIdentity(subject) ? this.getTranslatedKernelIdentity(subject) : subject;
      }
   }

   private AuthenticatedSubject getTranslatedKernelIdentity(AuthenticatedSubject subject) {
      return this.clientSecurity.isProprietarySecuritySupported() ? RmiSecurityFacade.sendASToWire(subject) : RmiSecurityFacade.getAnonymousSubject();
   }

   public static void logDebugSecurity(String debugMessage) {
      if (debugSecurity.isEnabled() || debugIIOPSecurity.isDebugEnabled()) {
         IIOPLogger.logDebugSecurity(debugMessage);
      }

   }

   public void setOutboundRequestTxContext(RequestMessage request, Object txContext) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         this.p("setOutboundRequestTxContext(" + (txContext == null ? null : txContext.getClass().getName()) + ")");
      }

      if (txContext != null) {
         if (txContext instanceof PropagationContext) {
            try {
               if (IIOPService.txMechanism == 2) {
                  PropagationContext ctx = (PropagationContext)txContext;
                  ctx.getTransaction().setProperty("weblogic.transaction.protocol", "iiop");
                  this.setMessageServiceContext(request, new VendorInfoTx(ctx));
               } else {
                  this.setMessageServiceContext(request, OTSHelper.exportTransaction((PropagationContext)((PropagationContext)txContext), 0));
               }
            } catch (Throwable var4) {
               IIOPLogger.logOTSError("JTA error while exporting transaction", var4);
               throw new TRANSACTION_ROLLEDBACK(var4.getMessage());
            }
         } else if (txContext instanceof org.omg.CosTransactions.PropagationContext) {
            this.setMessageServiceContext(request, new PropagationContextImpl((org.omg.CosTransactions.PropagationContext)txContext));
         }
      }

      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         this.p("setOutboundRequestTxContext(): " + txContext);
      }

   }

   public Object getInboundResponseTxContext(ReplyMessage reply) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         this.p("getInboundResponseTxContext()");
      }

      ServiceContext sc;
      if ((sc = reply.getServiceContext(1111834881)) != null) {
         return ((VendorInfoTx)sc).getTxContext();
      } else if ((sc = reply.getServiceContext(0)) != null) {
         if (!Kernel.isServer()) {
            return sc;
         } else {
            if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
               this.p("getInboundResponseTxContext(" + sc + ")");
            }

            try {
               Object txContext = OTSHelper.importTransaction((PropagationContextImpl)sc, 1);
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  this.p("getInboundResponseTxContext(): " + txContext);
               }

               return txContext;
            } catch (XAException var4) {
               IIOPLogger.logOTSError("JTA error while importing transaction", var4);
               throw new TRANSACTION_ROLLEDBACK(var4.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public synchronized int getNextRequestID() {
      return ++this.nextRequestID;
   }

   public synchronized void incrementPendingRequests() {
      ++this.pendingCount;
   }

   public synchronized void decrementPendingRequests() {
      --this.pendingCount;
   }

   public HostID getHostID() {
      if (this.hostID == null) {
         ListenPoint listenPoint = this.getConnection().getListenPoint();
         this.hostID = new HostIDImpl(listenPoint.getAddress(), listenPoint.getPort());
      }

      return this.hostID;
   }

   public Channel getRemoteChannel() {
      return this.getConnection().getRemoteChannel();
   }

   public ServerChannel getServerChannel() {
      return this.getConnection().getChannel();
   }

   public boolean isDead() {
      return this.c == null || this.c.isDead();
   }

   public boolean isUnresponsive() {
      return this.isDead();
   }

   public OutboundRequest getOutboundRequest(RemoteReference rr, RuntimeMethodDescriptor md, String serverChannel, String partitionURL) throws IOException {
      return null;
   }

   public OutboundRequest getOutboundRequest(RemoteReference rr, RuntimeMethodDescriptor md, String serverChannel, Protocol protocol, String partitionURL) throws IOException {
      return null;
   }

   public String getClusterURL(ObjectInput notUsed) {
      return null;
   }

   public boolean addDisconnectListener(Remote stub, DisconnectListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("DisocnnectListener cannot be null.");
      } else {
         synchronized(this.disconnectListeners) {
            HeartbeatKey key = new HeartbeatKey(stub, listener);
            this.disconnectListeners.put(key, listener);
            this.getConnection().setHeartbeatStub(stub);
            return true;
         }
      }
   }

   public boolean removeDisconnectListener(Remote stub, DisconnectListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("DisconnectListener cannot be null.");
      } else {
         synchronized(this.disconnectListeners) {
            HeartbeatKey key = new HeartbeatKey(stub, listener);
            this.disconnectListeners.remove(key);
            if (this.disconnectListeners.isEmpty()) {
               this.getConnection().setHeartbeatStub((Remote)null);
            }

            return true;
         }
      }
   }

   Collection getDisconnectListeners() {
      return Collections.unmodifiableCollection(this.disconnectListeners.values());
   }

   public void disconnect() {
      throw new NotImplementedException("disconnect not implemented in IIOP");
   }

   public void disconnect(String msg, boolean suppressEventPublish) {
      throw new NotImplementedException("disconnect not implemented in IIOP");
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   private void deliverHeartbeatMonitorListenerException(Exception e) {
      Iterator var2 = this.getDisconnectListenerMapCopy().values().iterator();

      while(var2.hasNext()) {
         DisconnectListener l = (DisconnectListener)var2.next();
         IiopConfigurationFacade.runAsynchronously(new HeartbeatMonitorExceptionHandler(l, e));
      }

      this.disconnectListeners.clear();
   }

   private HashMap getDisconnectListenerMapCopy() {
      synchronized(this.disconnectListeners) {
         return new HashMap(this.disconnectListeners);
      }
   }

   private static class HeartbeatKey {
      Remote stub;
      DisconnectListener l;
      int hashCode;

      HeartbeatKey(Remote s, DisconnectListener l) {
         if (l == null) {
            throw new IllegalArgumentException("DisocnnectListener cannot be null.");
         } else {
            this.stub = s;
            this.l = l;
            if (this.stub != null) {
               this.hashCode = this.stub.hashCode() ^ this.l.hashCode();
            } else {
               this.hashCode = this.l.hashCode();
            }

         }
      }

      public boolean equals(Object o) {
         if (!(o instanceof HeartbeatKey)) {
            return false;
         } else {
            HeartbeatKey other = (HeartbeatKey)o;
            return this.stub == other.stub && this.l == other.l;
         }
      }

      public int hashCode() {
         return this.hashCode;
      }
   }

   private class HeartbeatMonitorExceptionHandler implements Runnable {
      private DisconnectListener l;
      private Exception e;

      HeartbeatMonitorExceptionHandler(DisconnectListener l, Exception e) {
         this.l = l;
         this.e = e;
      }

      public final void run() {
         if (EndPointImpl.this.getRemoteCodeBase() != null) {
            ServerIdentity id = ObjectKey.getObjectKey(EndPointImpl.this.getConnection().getRemoteCodeBase()).getTarget();
            if (id != null && !id.isClient()) {
               this.l.onDisconnect(new ServerDisconnectEventImpl(this.e, id.getServerName()));
               return;
            }
         }

         this.l.onDisconnect(new DisconnectEventImpl(this.e));
      }
   }

   private static class CodeBaseClientInterceptor extends ClientContextInterceptor {
      CodeBaseClientInterceptor() {
         super(2, 6);
      }

      protected ServiceContext getContext(@NotNull EndPoint endPoint) {
         return new SendingContextRunTime(endPoint.getCodeBaseIor());
      }
   }

   private static class CodeSetClientInterceptor extends ClientContextInterceptor {
      CodeSetClientInterceptor() {
         super(1, 1);
      }

      protected boolean mayAddContext(@NotNull IOR ior) {
         return ior.isRemote();
      }

      protected ServiceContext getContext(@NotNull EndPoint endPoint) {
         return new CodeSetServiceContext(endPoint.getCharCodeSet(), endPoint.getWcharCodeSet());
      }
   }

   private static class VendorInfoClientInterceptor extends ClientContextInterceptor {
      VendorInfoClientInterceptor() {
         super(4, 1111834880);
      }

      protected ServiceContext getContext(@NotNull EndPoint endPoint) {
         return VendorInfo.VENDOR_INFO;
      }
   }

   private static class BiDirClientInterceptor extends ClientContextInterceptor {
      BiDirClientInterceptor() {
         super(8, 5);
      }

      protected boolean mayAddContext(@NotNull IOR ior) {
         return ior.getProfile().getMinorVersion() >= 2;
      }

      protected ServiceContext getContext(@NotNull EndPoint endPoint) {
         return BiDirIIOPContextImpl.getContext();
      }
   }
}
