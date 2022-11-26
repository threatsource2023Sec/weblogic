package weblogic.iiop;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.contexts.FutureObjectIdServiceContext;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.contexts.VendorInfoTrace;
import weblogic.iiop.contexts.WorkAreaContext;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.ReplyNotification;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.messages.SequencedMessage;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.ObjectIO;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.FutureResult;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionInterceptor;
import weblogic.transaction.TransactionManager;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextOutput;
import weblogic.workarea.spi.WorkContextMapInterceptor;

class OutboundRequestImpl implements OutboundRequest, Closeable {
   private MsgOutput msgOutput;
   private final EndPoint endPoint;
   private final RequestMessage request;
   private RuntimeMethodDescriptor md;
   private Object[] args;
   private boolean rmiType;
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   OutboundRequestImpl(EndPoint endPoint, RequestMessage request, boolean rmiType, RuntimeMethodDescriptor md) {
      this.rmiType = rmiType;
      this.endPoint = endPoint;
      this.request = request;
      endPoint.setCredentials(request, SecurityServiceManager.getCurrentSubject(kernelId));
      this.md = md;
   }

   public MsgOutput getMsgOutput() {
      if (this.msgOutput == null) {
         this.msgOutput = this.createMsgOutput();
      }

      return this.msgOutput;
   }

   private MsgOutput createMsgOutput() {
      this.request.marshalTo(this.endPoint.createOutputStream());
      return (MsgOutput)(this.rmiType ? this.request.getOutputStream() : new IDLMsgOutput((IIOPOutputStream)this.request.getOutputStream()));
   }

   public weblogic.rmi.spi.EndPoint getEndPoint() {
      return this.endPoint;
   }

   public void marshalArgs(Object[] args) throws MarshalException {
      this.args = args;
   }

   private void flush() throws MarshalException {
      try {
         Class[] argTypes = this.md.getParameterTypes();
         short[] argTypesAbbrevs = this.md.getParameterTypeAbbrevs();
         MsgOutput out = this.getMsgOutput();
         if (argTypes.length != 0) {
            for(int i = 0; i < this.args.length; ++i) {
               ObjectIO.writeObject(out, this.args[i], argTypes[i], argTypesAbbrevs[i]);
            }

         }
      } catch (IOException var5) {
         throw new MarshalException("failed to marshal " + this.md.getSignature(), var5);
      }
   }

   public void sendOneWay() throws RemoteException {
      if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
         IIOPLogger.logDebugTransport("REQUEST(" + this.request.getRequestID() + "): oneway invoke " + this.request.getOperationName() + "()");
      }

      this.flush();
      this.endPoint.send(this.request.getOutputStream());
   }

   public InboundResponse sendReceive() throws Throwable {
      this.request.setTimeout((long)this.md.getTimeOut());
      if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
         IIOPLogger.logDebugTransport("REQUEST(" + this.request.getRequestID() + "): timeout " + this.md.getTimeOut() + "ms : remote invoke " + this.request.getOperationName() + "()");
      }

      this.flush();
      ReplyMessage reply = (ReplyMessage)this.endPoint.sendReceive(this.request, this.request.getFlags());
      return this.createInboundResponse(reply);
   }

   InboundResponse createInboundResponse(ReplyMessage reply) throws RemoteException {
      InboundResponse response = new InboundResponseImpl(this.endPoint, reply, this.rmiType, this.md, this.getIorCodebase());
      receivedTxResponse(response.getTxContext());
      return response;
   }

   private String getIorCodebase() {
      return this.getCodeBaseOrNull(this.request.getIOR());
   }

   private String getCodeBaseOrNull(IOR ior) {
      return ior == null ? null : ior.getCodebase();
   }

   public void sendAsync(AsyncCallback futureResult) throws RemoteException {
      if (futureResult instanceof FutureResult) {
         this.getRequest().addServiceContext(new FutureObjectIdServiceContext(((FutureResult)futureResult).getId()));
      }

      this.request.setTimeout((long)this.md.getTimeOut());
      this.flush();
      this.endPoint.sendRequest(this.request, this.request.getFlags());
      this.getRequest().setNotification(new AsynchronousNotification(futureResult));
   }

   public void transferThreadLocalContext() throws IOException {
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
      if (interceptor != null) {
         WorkContextOutput out = this.endPoint.createWorkContextOutput();
         interceptor.sendRequest(out, 4);
         this.endPoint.setMessageServiceContext(this.request, new WorkAreaContext(out));
      }

   }

   public void setTimeOut(int msecs) {
      this.request.setTimeout((long)msecs);
   }

   private static void receivedTxResponse(Object txContext) throws RemoteException {
      TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      TransactionInterceptor ti = tm.getInterceptor();
      ti.receiveResponse(txContext);
   }

   public void setTxContext(Object txContext) {
      this.endPoint.setOutboundRequestTxContext(this.request, txContext);
   }

   public void setReplicaInfo(Version replicaInfo) throws IOException {
      if (replicaInfo != null) {
         this.endPoint.setMessageServiceContext(this.request, new VendorInfoCluster(replicaInfo));
      }

   }

   public void setActivationID(Object activationID) throws IOException {
   }

   public void setContext(int id, Object data) throws IOException {
      if (data != null) {
         switch (id) {
            case 4:
               this.endPoint.setMessageServiceContext(this.request, new VendorInfoTrace((byte[])((byte[])data)));
            default:
         }
      }
   }

   public RequestMessage getRequest() {
      return this.request;
   }

   public void close() throws IOException {
      if (this.msgOutput != null) {
         this.msgOutput.close();
      }

   }

   private class AsynchronousNotification implements ReplyNotification {
      AsyncCallback asyncCallback;

      public AsynchronousNotification(AsyncCallback asyncCallback) {
         this.asyncCallback = asyncCallback;
      }

      public SequencedMessage getReply() {
         return null;
      }

      public void notify(SequencedMessage reply) {
         try {
            this.asyncCallback.setInboundResponse(OutboundRequestImpl.this.createInboundResponse((ReplyMessage)reply));
         } catch (RemoteException var3) {
            this.asyncCallback.setThrowable(var3);
         }

      }
   }
}
