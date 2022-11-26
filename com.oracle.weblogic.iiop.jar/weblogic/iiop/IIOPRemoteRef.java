package weblogic.iiop;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.ConnectIOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.omg.CORBA.Object;
import weblogic.corba.cos.transactions.OTSHelper;
import weblogic.corba.orb.WlsIIOPInitialization;
import weblogic.corba.utils.RemoteInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.messages.RequestMessage;
import weblogic.kernel.Kernel;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.ForwardReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.trace.Trace;
import weblogic.transaction.ServerTransactionInterceptor;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public class IIOPRemoteRef implements ActivatableRemoteReference, ForwardReference, Externalizable, IORDelegate {
   static final long serialVersionUID = 7205760308016316442L;
   private static final int LOCATION_RETRIES = 5;
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final boolean tracingEnabled = Kernel.isTracingEnabled();
   private IOR ior;
   private transient IOR locatedIOR;
   private transient boolean rmiType;
   private HostID hostID;
   private Remote stub;
   private transient boolean timedOut;
   private transient long timeStamp;

   public IIOPRemoteRef(IOR ior) {
      this(ior, (RemoteInfo)null);
   }

   public IIOPRemoteRef(IOR ior, RemoteInfo rinfo) {
      this.rmiType = true;
      this.hostID = null;
      this.ior = ior;
      if (rinfo != null && rinfo.isIDLInterface() || ior.getTypeId().isIDLType()) {
         this.rmiType = false;
      }

      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("typeid: " + ior.getTypeId() + ", interface: " + (rinfo == null ? "<null>" : rinfo.getClassName()) + " is " + (this.rmiType ? "" : "not ") + "RMI-style");
      }

      ior.getProfile();
   }

   private IIOPOutboundRequest getOutboundRequestInternal(RuntimeMethodDescriptor md) throws IOException {
      try {
         boolean rmi = this.rmiType;
         String operation = md.getMangledName();
         if (rmi && Object.class.isAssignableFrom(md.getDeclaringClass())) {
            rmi = false;
         }

         if (this.locatedIOR == null) {
            synchronized(this) {
               if (this.locatedIOR == null) {
                  this.locatedIOR = this.locateRequest(this.ior);
               }
            }
         }

         EndPoint endPoint = this.findOrCreateEndPoint(this.locatedIOR);
         RequestMessage requestMessage = endPoint.createRequest(this.locatedIOR, operation, md.isOneway());
         return IIOPOutboundRequest.createOutboundRequest(this.stub, endPoint, requestMessage, rmi, md);
      } catch (IOException var7) {
         if (this.rmiType) {
            throw Utils.mapToRemoteException(var7);
         } else {
            throw Utils.mapToCORBAException(var7);
         }
      }
   }

   protected EndPoint findOrCreateEndPoint(IOR ior) throws IOException {
      return EndPointManager.findOrCreateEndPoint(ior);
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      return this.getOutboundRequest(md, partitionURL);
   }

   /** @deprecated */
   @Deprecated
   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      IIOPOutboundRequest req = this.getOutboundRequestInternal(md);
      associateTxContext(req);
      if (tracingEnabled) {
         req.setContext(4, Trace.currentTrace());
      }

      req.transferThreadLocalContext();
      return req;
   }

   public java.lang.Object invoke(Remote stub, RuntimeMethodDescriptor md, java.lang.Object[] args, Method m) throws Throwable {
      this.stub = stub;

      try {
         if (this.locatedIOR == null) {
            synchronized(this) {
               if (this.locatedIOR == null) {
                  this.locatedIOR = this.locateRequest(this.ior);
               }
            }
         }

         int i = 0;

         while(i < 5) {
            try {
               return this.invokeInternal(md, args);
            } catch (LocationForwardException var11) {
               LocationForwardException lfe = var11;
               synchronized(this) {
                  this.locatedIOR = lfe.getIor();
               }

               ++i;
            }
         }
      } catch (IOException var12) {
         if (this.rmiType) {
            throw Utils.mapToRemoteException(var12);
         }

         throw Utils.mapToCORBAException(var12);
      }

      ConnectIOException cioe = new ConnectIOException("Too many forwards");
      if (this.rmiType) {
         throw Utils.mapToRemoteException(cioe);
      } else {
         throw Utils.mapToCORBAException(cioe);
      }
   }

   private java.lang.Object invokeInternal(RuntimeMethodDescriptor md, java.lang.Object[] args) throws Throwable {
      IIOPOutboundRequest request = this.getOutboundRequestInternal(md);
      if (md.isTransactional()) {
         associateTxContext(request);
      }

      if (tracingEnabled) {
         request.setContext(4, Trace.currentTrace());
      }

      request.transferThreadLocalContext();

      java.lang.Object var4;
      try {
         request.marshalArgs(args);
         var4 = request.invoke();
      } finally {
         request.close();
      }

      return var4;
   }

   private static void associateTxContext(IIOPOutboundRequest req) throws RemoteException {
      if (Kernel.isServer()) {
         OTSHelper.forceLocalCoordinator();
      }

      TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      ServerTransactionInterceptor ti = (ServerTransactionInterceptor)tm.getInterceptor();
      req.setTxContext(ti.sendRequest(req.getEndPoint()));
   }

   public int getObjectID() {
      return IiopConfigurationFacade.getObjectId(this.ior);
   }

   public java.lang.Object getActivationID() {
      return IiopConfigurationFacade.getActivationID(this.ior);
   }

   public String getCodebase() {
      String cb = this.ior.getCodebase();
      return cb == null ? "" : cb;
   }

   public synchronized void setRequestTimedOut(boolean flag) {
      if (Kernel.getConfig().getTimedOutRefIsolationTime() > 0L) {
         this.timedOut = flag;
         this.timeStamp = System.currentTimeMillis();
      }
   }

   public synchronized boolean hasRequestTimedOut() {
      if (!this.timedOut) {
         return false;
      } else if (System.currentTimeMillis() - this.timeStamp > Kernel.getConfig().getTimedOutRefIsolationTime()) {
         this.setRequestTimedOut(false);
         return false;
      } else {
         return true;
      }
   }

   public HostID getHostID() {
      if (this.hostID == null) {
         synchronized(this) {
            if (this.hostID == null) {
               this.hostID = IiopConfigurationFacade.getHostID(this.ior);
            }
         }
      }

      return this.hostID;
   }

   public Channel getChannel() {
      return (Channel)this.getHostID();
   }

   public final IOR getIOR() {
      return this.ior;
   }

   public final int hashCode() {
      return this.ior.hashCode();
   }

   public final boolean equals(java.lang.Object o) {
      return o instanceof IIOPRemoteRef && this.ior.equals(((IIOPRemoteRef)o).getIOR());
   }

   public IIOPRemoteRef() {
      this.rmiType = true;
      this.hostID = null;
      WlsIIOPInitialization.initialize();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.ior);
   }

   public final void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.ior = (IOR)in.readObject();
   }

   protected IOR locateRequest(IOR ior) throws IOException {
      return locateIORForRequest(ior);
   }

   public static IOR locateIORForRequest(IOR ior) throws IOException {
      if (IIOPClientService.useLocateRequest) {
         ior = EndPointManager.findOrCreateEndPoint(ior).getCurrentIor(ior, 0L);
      }

      return ior;
   }

   protected synchronized void redirect(IOR ior) throws IOException {
      this.locatedIOR = ior;
   }

   static void p(String s) {
      System.err.println("<IIOPRemoteRef> " + s);
   }

   public void handleRedirect(InboundResponse response) throws Exception {
      IOR ior = null;
      if (response instanceof InboundResponseImpl && (ior = ((InboundResponseImpl)response).needsForwarding()) != null) {
         this.redirect(ior);
         throw new weblogic.rmi.extensions.server.LocationForwardException();
      }
   }
}
