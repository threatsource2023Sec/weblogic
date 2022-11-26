package weblogic.iiop.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Objects;
import javax.management.remote.rmi.RMIServer;
import javax.transaction.Transaction;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import weblogic.corba.cos.codebase.CodeBaseImpl;
import weblogic.corba.cos.naming.NamingContextImpl;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.iiop.ClusterServices;
import weblogic.iiop.Connection;
import weblogic.iiop.ConnectionShutdownHandler;
import weblogic.iiop.EndPointImpl;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPService;
import weblogic.iiop.IiopConfigurationFacade;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ReplyHandler;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.iiop.contexts.RequestUrlServiceContext;
import weblogic.iiop.contexts.SASServiceContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.contexts.VendorInfoSecurity;
import weblogic.iiop.contexts.VendorInfoTx;
import weblogic.iiop.interceptors.BiDirIIOPContextInterceptor;
import weblogic.iiop.interceptors.CodeSetContextInterceptor;
import weblogic.iiop.interceptors.SendingContextRuntimeInterceptor;
import weblogic.iiop.interceptors.ServerContextInterceptor;
import weblogic.iiop.interceptors.VendorInfoContextInterceptor;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.LocateReplyMessage;
import weblogic.iiop.messages.LocateRequestMessage;
import weblogic.iiop.messages.Message;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.Kernel;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.internal.ReplyOnError;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.internal.PropagationContext;
import weblogic.utils.collections.NumericKeyHashtable;

public class ServerEndPointImpl extends EndPointImpl implements ServerEndPoint {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final ServerContextInterceptor[] COMMON_INTERCEPTORS = new ServerContextInterceptor[]{new DiscardSecurityContextInterceptor(), new CodeSetContextInterceptor(), new SendingContextRuntimeInterceptor(), new VendorInfoContextInterceptor()};
   private static OTSSupport otsSupport = new OTSSupportImpl();
   private final NumericKeyHashtable securityContextTable;
   private ServerSASServiceContextHandler serverHandler;
   private ServerContextInterceptor[] interceptors;

   public ServerEndPointImpl(Connection connection) {
      this(connection, new BiDirIIOPContextInterceptor());
   }

   protected ServerEndPointImpl(Connection connection, ServerContextInterceptor... interceptors) {
      super(connection, new FullClientSecurity(connection));
      this.securityContextTable = new NumericKeyHashtable();
      this.serverHandler = new ServerSASServiceContextHandler(this);
      this.interceptors = new ServerContextInterceptor[COMMON_INTERCEPTORS.length + interceptors.length];
      System.arraycopy(COMMON_INTERCEPTORS, 0, this.interceptors, 0, COMMON_INTERCEPTORS.length);
      System.arraycopy(interceptors, 0, this.interceptors, COMMON_INTERCEPTORS.length, interceptors.length);
   }

   protected void processMessage(Message m) throws IOException {
      switch (m.getMsgType()) {
         case 3:
            this.handleLocateRequest(m);
            return;
         default:
            super.processMessage(m);
      }
   }

   private void handleLocateRequest(Message m) throws IOException {
      LocateRequestMessage req = (LocateRequestMessage)m;
      ObjectKey key = ObjectKey.create(req.getObjectKey());
      LocateReplyMessage reply;
      if (key.isWLSKey()) {
         if (!key.isLocalKey()) {
            throw new AssertionError("LocateRequest for non-local object");
         }

         reply = LocateReplyMessage.createReply(req, 1);
      } else {
         IOR ior;
         if (key.isBootstrapKey()) {
            ior = RootNamingContextImpl.getInitialReference().getIOR();
            reply = LocateReplyMessage.createForwardReply(req, ior);
         } else {
            ior = key.getInitialReference();
            if (ior != null) {
               reply = LocateReplyMessage.createForwardReply(req, ior);
            } else {
               reply = LocateReplyMessage.createReply(req, 0);
            }
         }
      }

      reply.marshalTo(this.createOutputStream());
      new ReplyHandler(this, reply);
   }

   protected void handleIncomingRequest(RequestMessage req) throws IOException {
      ServerContextInterceptor[] var2 = this.interceptors;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServerContextInterceptor interceptor = var2[var4];
         interceptor.handleReceivedRequest(req.getServiceContexts(), this, req.getInputStream());
      }

      try {
         ObjectKey objectKey = ObjectKey.create(req.getObjectKey());
         if (objectKey.isWLSKey()) {
            this.handleWlsObjectRequest(req, objectKey);
         } else if (objectKey.isNamingKey()) {
            this.handleNamingRequest(req);
         } else if (objectKey.getInitialReference() != null) {
            this.sendLocationForwardReply(req, objectKey.getInitialReference());
         } else {
            if (!objectKey.isBootstrapKey()) {
               throw this.replyNoSuchObject("Object not exported");
            }

            this.handleBootstrapRequest(req);
         }

      } catch (RemoteException var6) {
         throw this.replyNoSuchObject("Object not exported");
      }
   }

   private void handleWlsObjectRequest(RequestMessage requestMessage, ObjectKey objectKey) throws IOException {
      if (this.isUnknownRemoteKey(objectKey)) {
         throw this.replyNoSuchObject("Transient reference expired.");
      } else {
         if (RootNamingContextImpl.isInitialReferenceObjectKey(objectKey)) {
            this.redirectToPartitionRootContext(requestMessage);
         } else {
            this.dispatchRequest(requestMessage, objectKey);
         }

      }
   }

   private boolean isUnknownRemoteKey(ObjectKey object_key) {
      return !object_key.isLocalKey() && object_key.getObjectID() > 256;
   }

   private void redirectToPartitionRootContext(RequestMessage requestMessage) throws IOException {
      ManagedInvocationContext ignored = RmiInvocationFacade.setPartitionName(KERNEL_ID, this.getPartitionNameForRequest(requestMessage));
      Throwable var3 = null;

      try {
         NamingContextImpl redirectedRootContext = RootNamingContextImpl.getInitialReference().getRootContextForCurrentPartition();
         boolean aborted = this.processSASServiceContext(requestMessage);
         if (!aborted) {
            this.sendLocationForwardReply(requestMessage, redirectedRootContext.getIOR());
         }
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (ignored != null) {
            if (var3 != null) {
               try {
                  ignored.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               ignored.close();
            }
         }

      }

   }

   private String getPartitionNameForRequest(RequestMessage requestMessage) {
      return this.hasRequestUrl(requestMessage) ? this.getPartitionNameForRequestUrl(requestMessage) : this.getPartitionNameForConnection();
   }

   private boolean hasRequestUrl(RequestMessage requestMessage) {
      return requestMessage.getServiceContext(1111834894) != null;
   }

   private String getPartitionNameForRequestUrl(RequestMessage requestMessage) {
      try {
         return RmiInvocationFacade.getPartitionNameForUrl(this.getRequestUrl(requestMessage));
      } catch (URISyntaxException var3) {
         return RmiInvocationFacade.getGlobalPartitionName();
      }
   }

   private String getRequestUrl(RequestMessage requestMessage) {
      return ((RequestUrlServiceContext)requestMessage.getServiceContext(1111834894)).getRequestUrl();
   }

   private String getPartitionNameForConnection() {
      ServerChannel sc = this.getConnection().getChannel();
      String partitionName = RmiInvocationFacade.getPartitionNameForAddress(getConnectionSocketAddress(sc));
      if (partitionName == null || partitionName.equals(RmiInvocationFacade.getGlobalPartitionName())) {
         InetSocketAddress address = getConnectionSocketAddressWithDNSName(sc);
         if (address != null) {
            partitionName = RmiInvocationFacade.getPartitionNameForAddress(address);
         }
      }

      return partitionName != null ? partitionName : RmiInvocationFacade.getGlobalPartitionName();
   }

   private static InetSocketAddress getConnectionSocketAddress(ServerChannel channel) {
      return new InetSocketAddress(channel.getPublicAddress(), channel.getPort());
   }

   private static InetSocketAddress getConnectionSocketAddressWithDNSName(ServerChannel channel) {
      try {
         return new InetSocketAddress(InetAddress.getByName(channel.getPublicAddress()).getCanonicalHostName(), channel.getPort());
      } catch (UnknownHostException var2) {
         return null;
      }
   }

   protected void dispatchRequest(RequestMessage request, int objectId) throws RemoteException {
      InboundRequestImpl incoming = new InboundRequestImpl(this, request, objectId);
      this.dispatch(request, incoming);
   }

   private void dispatch(RequestMessage request, InboundRequestImpl incoming) {
      try {
         this.dispatchInPartition(request, incoming, incoming.getServerReference());
      } catch (NoSuchObjectException var4) {
         if (incoming.isResponseExpected()) {
            this.sendNoSuchObjectResponse(incoming);
         }
      } catch (RemoteException var5) {
         if (incoming.isResponseExpected()) {
            this.sendUnknownFailureResponse(incoming);
         }
      }

   }

   private void dispatchRequest(RequestMessage request, ObjectKey objectKey) throws RemoteException {
      InboundRequestImpl incoming = new InboundRequestImpl(this, request, objectKey);
      this.dispatch(request, incoming);
   }

   void dispatchInPartition(RequestMessage request, InboundIiopRequest incoming, ServerReference sref) {
      ManagedInvocationContext ignored = RmiInvocationFacade.setInvocationContext(KERNEL_ID, this.getComponentInvocationContext(incoming, sref));
      Throwable var5 = null;

      try {
         boolean aborted = this.processSASServiceContext(request);
         if (aborted) {
            return;
         }

         boolean forbiddenRequest = this.isForbiddenAnonymousRequest(request, sref);
         if (forbiddenRequest) {
            IIOPLogger.logRemoteAnonymousRMIAccesssNotAllowed(request.getOperationName(), sref.getDescriptor().getRemoteReferenceClassName());
            this.sendAuthenticationRequiredResponse(incoming);
            return;
         }

         this.dispatchMethod(request, incoming, sref);
      } catch (Throwable var18) {
         var5 = var18;
         throw var18;
      } finally {
         if (ignored != null) {
            if (var5 != null) {
               try {
                  ignored.close();
               } catch (Throwable var17) {
                  var5.addSuppressed(var17);
               }
            } else {
               ignored.close();
            }
         }

      }

   }

   private ComponentInvocationContext getComponentInvocationContext(InboundIiopRequest incoming, ServerReference sref) {
      return this.isNotPartitionAware(sref) ? getInvocationContextForRequest(KERNEL_ID, incoming) : sref.getInvocationContext();
   }

   private boolean isNotPartitionAware(ServerReference sref) {
      return sref.getImplementation() instanceof RMIServer;
   }

   private static ComponentInvocationContext getInvocationContextForRequest(AuthenticatedSubject kernelId, weblogic.rmi.spi.InboundRequest request) {
      return RmiInvocationFacade.createInvocationContext(kernelId, getPartitionNameForRequest(request));
   }

   private static String getPartitionNameForRequest(weblogic.rmi.spi.InboundRequest request) {
      try {
         String requestUrl = request.getRequestUrl();
         return Objects.equals(requestUrl, "") ? RmiInvocationFacade.getPartitionNameForAddress(getConnectionSocketAddress(request)) : RmiInvocationFacade.getPartitionNameForUrl(requestUrl);
      } catch (URISyntaxException var2) {
         return RmiInvocationFacade.getGlobalPartitionName();
      }
   }

   private static InetSocketAddress getConnectionSocketAddress(weblogic.rmi.spi.InboundRequest request) {
      return getConnectionSocketAddress(request.getServerChannel());
   }

   private boolean processSASServiceContext(RequestMessage req) {
      ServiceContext sc = req.getServiceContext(15);
      return sc != null && this.isContextEstablished(req, (SASServiceContext)sc);
   }

   private boolean isContextEstablished(RequestMessage req, SASServiceContext sc) {
      return this.serverHandler.handleSASRequest(req, sc);
   }

   private void dispatchMethod(RequestMessage request, InboundIiopRequest incoming, ServerReference sref) {
      this.renewLease(sref);
      RuntimeMethodDescriptor md = incoming.getRuntimeMethodDescriptor(sref.getDescriptor());
      if (md == null) {
         IIOPLogger.logMethodParseFailure(request.getOperationName());
         if (incoming.isResponseExpected()) {
            this.sendNoSuchOperationResponse(request, incoming);
         }
      } else if (md.requiresTransaction() && this.clientCannotPropagateTransaction(request)) {
         if (incoming.isResponseExpected()) {
            this.sendTransactionRequiredResponse(incoming);
         }
      } else {
         incoming.registerAsPending();
         sref.dispatch(incoming);
      }

   }

   private void renewLease(ServerReference sref) {
      if (sref instanceof Collectable) {
         ((Collectable)sref).renewLease();
      }

   }

   private void sendNoSuchOperationResponse(RequestMessage request, InboundIiopRequest incoming) {
      this.sendErrorResponseAsynchronously(incoming, new BAD_OPERATION("Could not dispatch to method name: " + request.getOperationName(), 0, CompletionStatus.COMPLETED_NO));
   }

   private void sendErrorResponseAsynchronously(InboundIiopRequest incoming, Exception cause) {
      new ReplyOnError(incoming.getOutboundResponse(), cause);
   }

   private boolean clientCannotPropagateTransaction(RequestMessage request) {
      PropagationContextImpl pctx = (PropagationContextImpl)request.getServiceContext(0);
      return pctx != null && (pctx.isNull() || IIOPService.txMechanism == 0);
   }

   private void sendTransactionRequiredResponse(InboundIiopRequest incoming) {
      this.sendErrorResponseAsynchronously(incoming, new UnmarshalException("A transaction is required or not allowed"));
   }

   private void sendNoSuchObjectResponse(InboundRequestImpl incoming) {
      this.sendErrorResponseAsynchronously(incoming, this.replyNoSuchObject("No such oid: " + incoming.getObjectID()));
   }

   private void sendUnknownFailureResponse(InboundRequestImpl incoming) {
      this.sendErrorResponseAsynchronously(incoming, new INV_OBJREF("Could not dispatch to oid: " + incoming.getObjectID(), 0, CompletionStatus.COMPLETED_NO));
   }

   private void sendAuthenticationRequiredResponse(InboundIiopRequest incoming) {
      this.sendErrorResponseAsynchronously(incoming, new UnmarshalException("Remote Anonymous RMI is not allowed"));
   }

   protected void handleNamingRequest(RequestMessage request) throws IOException {
      this.sendLocationForwardReply(request, this.getNamingContextRootIor());
   }

   private void sendLocationForwardReply(RequestMessage request, IOR ior) {
      ReplyMessage reply = ReplyMessage.createLocationForwardReply(request, request.getOutboundServiceContexts(), ior);
      reply.marshalTo(this.createOutputStream());
      new ReplyHandler(this, reply);
   }

   private IOR getNamingContextRootIor() throws IOException {
      ClusterServices cs = ClusterServices.getServices();
      return this.supportsForwarding() && cs != null && cs.getLocationForwardPolicy() != 0 ? NamingContextImpl.getBootstrapIOR() : RootNamingContextImpl.getInitialReference().getIOR();
   }

   private void handleBootstrapRequest(RequestMessage request) throws IOException {
      ReplyMessage reply = new ReplyMessage(request, request.getOutboundServiceContexts(), 0);
      CorbaOutputStream os = reply.marshalTo(this.createOutputStream());
      this.writeBootstrapReplyBody(request, os);
      new ReplyHandler(this, reply);
   }

   private void writeBootstrapReplyBody(RequestMessage request, CorbaOutputStream os) throws IOException {
      switch (request.getOperationName()) {
         case "get":
            this.getInitialReferenceIor(this.getInitialReferenceName(request)).write(os);
            break;
         case "list":
            this.writeServiceList(os);
      }

   }

   private String getInitialReferenceName(RequestMessage request) {
      return request.getInputStream().read_string();
   }

   private IOR getInitialReferenceIor(String serviceName) throws IOException {
      return serviceName.equals("NameService") ? this.getNamingContextRootIor() : this.getNamedServiceIor(serviceName);
   }

   private IOR getNamedServiceIor(String serviceName) {
      IOR ior = InitialReferences.getInitialReference(serviceName);
      if (ior == null) {
         throw this.replyNoSuchObject("Object not exported");
      } else {
         return ior;
      }
   }

   private void writeServiceList(CorbaOutputStream os) {
      String[] services = InitialReferences.getServiceList();
      os.write_long(services.length);
      String[] var3 = services;
      int var4 = services.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String service = var3[var5];
         os.write_string(service);
      }

   }

   public final void putSecurityContext(long clientContextId, SecurityContext secCtx) {
      this.securityContextTable.put(clientContextId, secCtx);
   }

   public final SecurityContext getSecurityContext(long clientContextId) {
      return (SecurityContext)this.securityContextTable.get(clientContextId);
   }

   public final void removeSecurityContext(long clientContextId) {
      this.securityContextTable.remove(clientContextId);
   }

   protected void handleErrorOnMessage(Message m, Throwable e) {
      switch (m.getMsgType()) {
         case 0:
            this.replyErrorToOutstandingRequest((RequestMessage)m, e);
            return;
         case 3:
            new ConnectionShutdownHandler(this.c, e);
            return;
         default:
            super.handleErrorOnMessage(m, e);
      }
   }

   private void replyErrorToOutstandingRequest(RequestMessage request, Throwable e) {
      ServiceContextList contexts = null;

      try {
         contexts = request.getOutboundServiceContexts();
      } catch (Throwable var6) {
      }

      ReplyMessage reply = new ReplyMessage(request, contexts, 0);
      weblogic.rmi.spi.OutboundResponse outbound = new OutboundResponseImpl(this, reply);
      if (!(e instanceof SystemException) && !(e instanceof RemoteException)) {
         if (e instanceof Exception) {
            new ReplyOnError(outbound, new MarshalException("GIOP protocol error", (Exception)e));
         } else {
            new ReplyOnError(outbound, new MarshalException("GIOP protocol error: " + e.getMessage()));
         }
      } else {
         new ReplyOnError(outbound, e);
      }

   }

   public Object getInboundRequestTxContext(RequestMessage request) {
      ServiceContext sc;
      if ((sc = request.getServiceContext(1111834881)) != null) {
         return ((VendorInfoTx)sc).getTxContext();
      } else {
         return (sc = request.getServiceContext(0)) != null ? this.getImportedTransaction(request, (PropagationContextImpl)sc) : this.c.getTxContext();
      }
   }

   private Object getImportedTransaction(RequestMessage request, PropagationContextImpl otsContext) {
      if (!Kernel.isServer()) {
         return otsContext;
      } else {
         try {
            return this.importOTSTransaction(this.getSubject(request), otsContext);
         } catch (PrivilegedActionException var4) {
            IIOPLogger.logOTSError("JTA error while importing transaction", var4.getException());
            throw new TRANSACTION_ROLLEDBACK(var4.getException().getMessage());
         }
      }
   }

   private Object importOTSTransaction(AuthenticatedSubject subject, final PropagationContextImpl otsTransactionContext) throws PrivilegedActionException {
      return RmiSecurityFacade.runAs(KERNEL_ID, subject, new PrivilegedExceptionAction() {
         public Transaction run() throws Exception {
            return ServerEndPointImpl.otsSupport.importOTSTransaction(otsTransactionContext);
         }
      });
   }

   public void setOutboundResponseTxContext(ReplyMessage reply, Object txContext) {
      if (txContext != null && this.c.getTxContext() == null) {
         if (txContext instanceof PropagationContext) {
            try {
               if (IIOPService.txMechanism == 2) {
                  PropagationContext ctx = (PropagationContext)txContext;
                  ctx.getTransaction().setProperty("weblogic.transaction.protocol", "iiop");
                  this.setMessageServiceContext(reply, new VendorInfoTx(ctx));
               } else {
                  this.setMessageServiceContext(reply, otsSupport.exportOTSTransaction((PropagationContext)txContext));
               }
            } catch (Throwable var4) {
               IIOPLogger.logOTSError("JTA error while exporting transaction", var4);
               throw new TRANSACTION_ROLLEDBACK(var4.getMessage());
            }
         } else if (txContext instanceof org.omg.CosTransactions.PropagationContext) {
            this.setMessageServiceContext(reply, new PropagationContextImpl((org.omg.CosTransactions.PropagationContext)txContext));
         }
      }

   }

   public IOR getCodeBaseIor() {
      return CodeBaseImpl.getCodeBase().getIOR();
   }

   public AuthenticatedSubject getSubject(RequestMessage request) {
      if (request.getSubject() == null) {
         request.setSubject(this.computeSubject(request));
      }

      return (AuthenticatedSubject)request.getSubject();
   }

   private AuthenticatedSubject computeSubject(RequestMessage request) {
      ServiceContext sc;
      if ((sc = request.getServiceContext(1111834882)) != null) {
         return this.getSubjectFromVendorInfoSecurity((VendorInfoSecurity)sc);
      } else {
         return (sc = request.getServiceContext(15)) != null ? this.getCSIv2AuthenticatedSubject((SASServiceContext)sc) : this.getSubjectFromConnection();
      }
   }

   private AuthenticatedSubject getSubjectFromVendorInfoSecurity(VendorInfoSecurity sc) {
      AuthenticatedUser au = sc.getUser();
      return au == null ? RmiSecurityFacade.getAnonymousSubject() : RmiSecurityFacade.getASFromAUInServerOrClient(au);
   }

   private AuthenticatedSubject getCSIv2AuthenticatedSubject(SASServiceContext sc) {
      AuthenticatedSubject subject = sc.getSubject();
      return subject == null ? RmiSecurityFacade.getAnonymousSubject() : subject;
   }

   private AuthenticatedSubject getSubjectFromConnection() {
      AuthenticatedSubject as = this.c.getUser();
      return as == null ? RmiSecurityFacade.getAnonymousSubject() : as;
   }

   private boolean isForbiddenAnonymousRequest(RequestMessage request, ServerReference sref) {
      SASServiceContext sc = (SASServiceContext)request.getServiceContext(15);
      if (!IiopConfigurationFacade.isRemoteAnonymousRMIIIOPEnabled() && (sc == null || sc.getSubject() == null) && !"_non_existent".equals(request.getOperationName())) {
         boolean noMechList = request.getMechanismListForRequest() == null || !request.getMechanismListForRequest().useSAS();
         boolean primitive = false;
         if (noMechList) {
            primitive = true;
            Method md = sref.getDescriptor().getMethod(request.getOperationName());
            Class[] ptypes = md != null ? md.getParameterTypes() : null;

            for(int i = 0; ptypes != null && i < ptypes.length && primitive; ++i) {
               if (!ptypes[i].isPrimitive() && !String.class.equals(ptypes[i])) {
                  primitive = false;
               }
            }
         }

         if (!primitive) {
            return true;
         }
      }

      return false;
   }
}
