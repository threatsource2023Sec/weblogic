package weblogic.rjvm;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.ObjectIO;
import weblogic.rmi.internal.RMIDiagnosticUtil;
import weblogic.rmi.provider.BasicServiceContext;
import weblogic.rmi.provider.WorkServiceContext;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.utils.io.Immutable;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public final class BasicOutboundRequest implements OutboundRequest {
   private static final int NEVER = -1;
   private int timeOut = -1;
   private final RemoteReference ror;
   private final MsgAbbrevOutputStream request;
   private Object txContext;
   private final RuntimeMethodDescriptor md;
   private final ImmutableServiceContext mdContext;
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");

   public BasicOutboundRequest(RemoteReference ror, MsgAbbrevOutputStream req, RuntimeMethodDescriptor rmd) throws IOException {
      this.ror = ror;
      this.request = req;
      this.md = (RuntimeMethodDescriptor)((MethodDescriptor)rmd).interopWriteReplace(this.request.getPeerInfo());
      this.request.setRuntimeMethodDescriptor(this.md);
      this.mdContext = new ImmutableServiceContext(6, this.md);
      this.request.setContext(this.mdContext);
   }

   public MsgOutput getMsgOutput() {
      return this.request;
   }

   public EndPoint getEndPoint() {
      return this.request.getEndPoint();
   }

   public void marshalArgs(Object[] args) throws MarshalException {
      try {
         MsgOutput out = this.getMsgOutput();
         Class[] argTypes = this.md.getParameterTypes();
         if (argTypes.length != 0) {
            short[] paramTypeCodes = this.md.getParameterTypeAbbrevs();

            for(int i = 0; i < args.length; ++i) {
               ObjectIO.writeObject(out, args[i], argTypes[i], paramTypeCodes[i]);
            }

         }
      } catch (IOException var6) {
         throw new MarshalException("failed to marshal " + this.md.getSignature(), var6);
      }
   }

   public void sendOneWay() throws RemoteException {
      if (this.txContext != null && !this.md.isOnewayTransactionalRequest()) {
         receivedTxResponse(this.txContext);
         throw new MarshalException("One-way calls are prohibited when a transaction is associated with the calling thread");
      } else {
         this.request.sendOneWay(this.ror.getObjectID());
      }
   }

   public InboundResponse sendReceive() throws Throwable {
      if (KernelStatus.DEBUG && debugMessaging.isDebugEnabled()) {
         int rqSize = this.request.getLength();
         int maxSize = this.request.getServerChannel().getMaxMessageSize();
         if (rqSize > maxSize) {
            debugMessaging.debug("BasicOutboundRequest.sendReceive request message size: " + rqSize + " for method '" + this.md + "' to receiver: " + this.getEndPoint() + " max size " + maxSize, new Throwable());
         }
      }

      InboundResponse response = (InboundResponse)this.request.sendRecv(this.ror.getObjectID());
      receivedTxResponse(response.getTxContext());
      return response;
   }

   public void transferThreadLocalContext() throws IOException {
      RMIDiagnosticUtil.setTimeoutToWorkContext((long)this.timeOut);
      this.request.marshalCustomCallData();
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
      if (interceptor != null) {
         this.request.setContext(new WorkServiceContext(true));
      }

      this.request.marshalUserCustomCallData();
   }

   private static void receivedTxResponse(Object txContext) {
      Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
      if (ti != null) {
         try {
            ti.receiveResponse(txContext);
         } catch (RemoteException var3) {
         }
      }

   }

   public void sendAsync(AsyncCallback callback) throws RemoteException {
      if (callback == null) {
         this.sendOneWay();
      } else {
         this.request.sendAsync(this.ror.getObjectID(), new BasicResponseListener(callback));
      }
   }

   public void setTimeOut(int msecs) {
      this.timeOut = msecs;
      this.request.setTimeOut(this.timeOut);
   }

   public void setTxContext(Object txc) throws RemoteException {
      if (this.txContext != null) {
         throw new IllegalStateException("Attempt to send tx context twice");
      } else {
         this.txContext = txc;
         this.request.setTxContext(this.txContext);
      }
   }

   public void setReplicaInfo(Version replicaInfo) throws IOException {
      this.getMsgOutput().writeObject(replicaInfo, Object.class);
   }

   public void setActivationID(Object activationID) throws IOException {
      if (activationID instanceof Immutable) {
         this.request.setContext(new ImmutableServiceContext(2, activationID, true));
      } else {
         this.request.setContext(new BasicServiceContext(2, activationID, true));
      }

   }

   public void setContext(int id, Object data) throws IOException {
      this.request.setContext(id, data);
   }

   private final class BasicResponseListener implements ResponseListener {
      final AsyncCallback callback;

      BasicResponseListener(AsyncCallback callback) {
         this.callback = callback;
      }

      public synchronized void response(Response r) {
         InboundResponse response = (InboundResponse)r;

         try {
            synchronized(BasicOutboundRequest.this) {
               response.retrieveThreadLocalContext();
               Interceptor interceptor = InterceptorManager.getManager().getTransactionInterceptor();
               if (interceptor != null) {
                  interceptor.receiveAsyncResponse(response.getTxContext());
               }

               Throwable t = r.getThrowable();
               if (t != null) {
                  this.callback.setThrowable(t);
               } else {
                  this.callback.setInboundResponse(response);
               }

               BasicOutboundRequest.this.notifyAll();
            }
         } catch (Throwable var8) {
         }

      }
   }
}
