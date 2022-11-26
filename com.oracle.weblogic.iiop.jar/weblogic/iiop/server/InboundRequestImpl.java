package weblogic.iiop.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.PortableServer.POA;
import weblogic.corba.idl.ObjectImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IDLMsgInput;
import weblogic.iiop.IIOPInputStream;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ProtocolHandlerIIOPS;
import weblogic.iiop.contexts.FutureObjectIdServiceContext;
import weblogic.iiop.contexts.PropagationContextImpl;
import weblogic.iiop.contexts.RequestUrlServiceContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.contexts.VendorInfoTrace;
import weblogic.iiop.contexts.WorkAreaContext;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.kernel.Kernel;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.MsgInput;
import weblogic.security.service.ContextHandler;
import weblogic.security.subject.AbstractSubject;
import weblogic.utils.Debug;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.spi.WorkContextMapInterceptor;

class InboundRequestImpl implements InboundIiopRequest {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final String INTERFACE = "_interface";
   private static final String GET_INTERFACE = "_get_interface_def";
   private static final OIDManager oidManager = OIDManager.getInstance();
   private final ServerEndPoint endPoint;
   private final RequestMessage request;
   private MsgInput msgInput;
   private int oid;
   private ObjectKey objectKey;
   private RuntimeMethodDescriptor md;
   private Method method;
   private ServerReference serverReference;
   private static final Object NULL = new Object();
   private Object cachedTx;
   private boolean pending;
   private boolean rmiType;
   private static final MethodDescriptor NULL_METHOD = new MethodDescriptor();

   private static void p(String s) {
      System.err.println("<InboundRequestImpl> " + s);
   }

   InboundRequestImpl(ServerEndPoint endPoint, RequestMessage request, int objectID) throws RemoteException {
      this(endPoint, request, (ObjectKey)null, objectID);
   }

   InboundRequestImpl(ServerEndPoint endPoint, RequestMessage request, ObjectKey objectKey) throws RemoteException {
      this(endPoint, request, objectKey, objectKey.getObjectID());
   }

   private InboundRequestImpl(ServerEndPoint endPoint, RequestMessage request, ObjectKey objectKey, int objectID) throws RemoteException {
      this.cachedTx = NULL;
      this.pending = false;
      this.rmiType = true;
      this.endPoint = endPoint;
      this.request = request;
      this.oid = objectID;
      this.objectKey = objectKey;
      String op = request.getOperationName();
      if (op.charAt(0) == '_' && op.equals("_interface")) {
         op = "_get_interface_def";
      }

      try {
         RuntimeDescriptor rd = this.getServerReference().getDescriptor();
         this.md = rd.getMethodDescriptor(op);
         boolean idl = false;
         Class[] remotes = rd.getRemoteInterfaces();

         int i;
         for(i = 0; i < remotes.length; ++i) {
            if (IDLEntity.class.isAssignableFrom(remotes[i])) {
               idl = true;
               break;
            }
         }

         if (this.md == null) {
            if (!idl) {
               Object impl = this.getServerReference().getImplementation();
               ClassLoader clSave = Thread.currentThread().getContextClassLoader();

               try {
                  if (impl == null) {
                     ClassLoader cl = this.getServerReference().getApplicationClassLoader();
                     Thread.currentThread().setContextClassLoader(cl);
                     impl = ((ActivatableServerReference)this.getServerReference()).getImplementation(this.getActivationID());
                  }
               } catch (IOException var17) {
                  throw new NoSuchObjectException("Failed to unmarshal activation id");
               } finally {
                  Thread.currentThread().setContextClassLoader(clSave);
               }

               Debug.assertion(impl != null);
               ObjectImpl oimpl = new ObjectImpl((Remote)impl);
               oidManager.getReplacement(oimpl);
               this.serverReference = this.withInvocationContext(oimpl);
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  p("Object server ref is : " + this.serverReference);
               }

               this.oid = this.serverReference.getObjectID();
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  p("Object id is : " + this.oid);
               }

               idl = true;
               this.md = this.serverReference.getDescriptor().getMethodDescriptor(op);
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  p("Object md is : " + this.md);
               }

               impl = this.serverReference.getImplementation();
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
                  p("Object impl is : " + impl);
               }
            } else {
               for(i = 0; i < remotes.length; ++i) {
                  if (POA.class.isAssignableFrom(remotes[i])) {
                     this.md = NULL_METHOD;
                     break;
                  }
               }
            }
         }

         this.method = this.getServerReference().getDescriptor().getMethod(op);
         if (this.md == null || idl) {
            this.rmiType = false;
            this.msgInput = new IDLMsgInput((IIOPInputStream)request.getInputStream());
         }
      } catch (NoSuchObjectException var19) {
         this.msgInput = request.getInputStream();
         this.oid = objectID;
      }

   }

   private ServerReference withInvocationContext(ObjectImpl oimpl) {
      ServerReference newServerReference = oidManager.getServerReference(oimpl);
      newServerReference.setInvocationContext(this.serverReference.getInvocationContext());
      return newServerReference;
   }

   public final ServerReference getServerReference() throws RemoteException {
      if (this.serverReference == null) {
         this.serverReference = oidManager.getServerReference(this.getObjectID());
      }

      return this.serverReference;
   }

   public void registerAsPending() {
      if (!this.isOneWay()) {
         this.pending = true;
         this.endPoint.incrementPendingRequests();
      }

   }

   public final InputStream getInputStream() {
      this.request.unmarshal();
      return this.request.getInputStream();
   }

   public final String getMethod() {
      return this.request.getOperationName();
   }

   public synchronized MsgInput getMsgInput() {
      if (this.msgInput == null) {
         this.msgInput = this.request.getInputStream();
      }

      return this.msgInput;
   }

   public boolean isCollocated() {
      return false;
   }

   public EndPoint getEndPoint() {
      return this.endPoint;
   }

   public ServerChannel getServerChannel() {
      return this.endPoint.getServerChannel();
   }

   public Protocol getProtocol() {
      return this.endPoint.isSecure() ? ProtocolHandlerIIOPS.PROTOCOL_IIOPS : ProtocolHandlerIIOP.PROTOCOL_IIOP;
   }

   public AbstractSubject getSubject() {
      return this.endPoint.getSubject(this.request);
   }

   public int getObjectID() {
      if (this.oid != -1) {
         return this.oid;
      } else {
         this.oid = this.getObjectKey().getObjectID();
         return this.oid;
      }
   }

   private ObjectKey getObjectKey() {
      return this.objectKey;
   }

   public RuntimeMethodDescriptor getRuntimeMethodDescriptor(RuntimeDescriptor rd) {
      return this.md;
   }

   public Object getTxContext() {
      if (this.cachedTx == NULL) {
         if (this.md != null && !this.md.isTransactional()) {
            this.cachedTx = null;
         } else {
            this.cachedTx = this.endPoint.getInboundRequestTxContext(this.request);
         }
      }

      return this.cachedTx;
   }

   public void retrieveThreadLocalContext() throws IOException {
      WorkAreaContext wac = (WorkAreaContext)this.endPoint.getMessageServiceContext(this.request, 1111834891);
      if (wac != null) {
         WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();
         interceptor.receiveRequest(wac.getInputStream());
      }

   }

   public Object getReplicaInfo() throws IOException {
      VendorInfoCluster vic = (VendorInfoCluster)this.endPoint.getMessageServiceContext(this.request, 1111834883);
      return vic != null ? vic.version() : null;
   }

   public Object getActivationID() throws IOException {
      return this.getObjectKey().getActivationID();
   }

   public weblogic.rmi.spi.OutboundResponse getOutboundResponse() {
      if (this.isOneWay()) {
         return null;
      } else {
         ReplyMessage reply = new ReplyMessage(this.request, this.request.getOutboundServiceContexts(), 0);
         ServiceContext txsc;
         if (this.md != null && !this.md.isTransactional() && (txsc = this.request.getServiceContext(0)) != null) {
            reply.addServiceContext(((PropagationContextImpl)txsc).getNullContext());
         }

         return new OutboundResponseImpl(this.endPoint, reply, this.method, this.rmiType, this.pending);
      }
   }

   public void close() throws IOException {
      if (this.msgInput != null) {
         this.msgInput.close();
      }

   }

   public boolean isResponseExpected() {
      return !this.request.isOneWay();
   }

   private boolean isOneWay() {
      return this.request.isOneWay();
   }

   public Object getContext(int id) throws IOException {
      switch (id) {
         case 4:
            ServiceContext tsc = this.request.getServiceContext(1111834890);
            if (tsc != null) {
               return ((VendorInfoTrace)tsc).getTrace();
            }
            break;
         case 25:
            ServiceContext foi = this.request.getServiceContext(1111834892);
            if (foi != null) {
               return ((FutureObjectIdServiceContext)foi).getResultID();
            }
      }

      return null;
   }

   public X509Certificate[] getCertificateChain() {
      return null;
   }

   public ContextHandler getContextHandler() {
      return null;
   }

   public String getRequestUrl() {
      ServiceContext serviceContext = this.request.getServiceContext(1111834894);
      return serviceContext == null ? "" : ((RequestUrlServiceContext)serviceContext).getRequestUrl();
   }
}
