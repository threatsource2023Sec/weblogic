package weblogic.rmi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.rmi.ConnectException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.concurrent.Future;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolStack;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.extensions.UnrecoverableConnectionException;
import weblogic.rmi.extensions.server.OutboundRequestBuilder;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.trace.Trace;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;

public class BasicRemoteRef implements RemoteReference, ReferenceConstants, OperationConstants, Externalizable, OutboundRequestBuilder {
   private static final boolean ASSERT = true;
   static final long serialVersionUID = 215700904493420587L;
   protected int oid;
   private EndPoint endPoint;
   private PeerInfo peerInfo;
   protected HostID hostID;
   private String channelName;
   private Protocol protocol;
   private static boolean enableQOSOnStub = true;
   private static final boolean tracingEnabled = RMIEnvironment.getEnvironment().isTracingEnabled();
   private transient boolean hostReachable;
   private int staleStubInvocationCount;
   private transient boolean timedOut;
   private transient long timeStamp;

   public BasicRemoteRef(int oid, HostID hostID) {
      this(oid, hostID, (String)null);
   }

   public BasicRemoteRef(int oid, HostID hostID, String channelName) {
      this.peerInfo = null;
      this.hostReachable = true;
      this.oid = oid;
      this.hostID = hostID;
      this.channelName = channelName;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BasicRemoteRef)) {
         return false;
      } else {
         BasicRemoteRef other = (BasicRemoteRef)obj;
         return other.oid != this.oid ? false : this.hostID.equals(other.hostID);
      }
   }

   public int hashCode() {
      return this.oid;
   }

   public String toString() {
      return this.getClass().getName() + " - hostID: '" + this.hostID + " channel: '" + this.channelName + '\'';
   }

   private boolean couldBeAStaleStub(RuntimeMethodDescriptor md) {
      if (KernelStatus.isServer()) {
         return false;
      } else {
         String className = md.getDeclaringClass().getName();
         if (className == null) {
            return false;
         } else {
            return className.indexOf("weblogic.jms.frontend.FEConnectionFactory") >= 0;
         }
      }
   }

   private synchronized void incrementStaleStubInvocationCount() {
      ++this.staleStubInvocationCount;
   }

   private synchronized void decrementStaleStubInvocationCount() {
      if (this.staleStubInvocationCount > 0) {
         --this.staleStubInvocationCount;
      }

   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, int contextID, Object context, String partitionURL) throws IOException {
      if (!this.hostReachable) {
         throw new ConnectException("Unable to reach host");
      } else {
         boolean couldBeAStaleStub = this.ensureStubIsUsable(md);
         EndPoint curEndPoint = this.getEndPoint();
         OutboundRequest req = this.getRequest(md, couldBeAStaleStub, curEndPoint, partitionURL);
         this.setServiceContexts(md, curEndPoint, req);
         this.setCustomContexts(req, contextID, context);
         req.transferThreadLocalContext();
         return req;
      }
   }

   private void setCustomContexts(OutboundRequest req, int contextID, Object context) throws IOException {
      req.setContext(contextID, context);
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      return this.getOutboundRequest(md, partitionURL);
   }

   /** @deprecated */
   @Deprecated
   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      if (!this.hostReachable) {
         throw new ConnectException("Unable to reach host");
      } else {
         boolean couldBeAStaleStub = this.ensureStubIsUsable(md);
         EndPoint curEndPoint = this.getEndPoint();
         OutboundRequest req = this.getRequest(md, couldBeAStaleStub, curEndPoint, partitionURL);
         this.setServiceContexts(md, curEndPoint, req);
         req.transferThreadLocalContext();
         return req;
      }
   }

   private void setServiceContexts(RuntimeMethodDescriptor md, EndPoint curEndPoint, OutboundRequest req) throws IOException {
      if (md.isTransactional()) {
         Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
         if (ti != null) {
            Object txContext = ti.sendRequest(curEndPoint);
            req.setTxContext(txContext);
         }
      }

      if (tracingEnabled) {
         req.setContext(4, Trace.currentTrace());
      }

      this.transferContext(req);
   }

   private OutboundRequest getRequest(RuntimeMethodDescriptor md, boolean couldBeAStaleStub, EndPoint curEndPoint, String partitionURL) throws IOException {
      OutboundRequest req;
      try {
         if (enableQOSOnStub && this.protocol != null) {
            req = curEndPoint.getOutboundRequest(this, md, this.channelName, this.protocol, partitionURL);
         } else {
            req = curEndPoint.getOutboundRequest(this, md, this.channelName, partitionURL);
         }

         if (couldBeAStaleStub) {
            this.decrementStaleStubInvocationCount();
         }
      } catch (UnrecoverableConnectionException var10) {
         synchronized(this) {
            this.hostReachable = false;
         }

         String host = curEndPoint != null ? curEndPoint.getHostID().toString() : "";
         throw new ConnectException("Unable to reach host " + host + "\ncaused by: " + var10.getMessage() + '\n' + StackTraceUtils.throwable2StackTrace(var10));
      } catch (IOException var11) {
         if (couldBeAStaleStub) {
            this.incrementStaleStubInvocationCount();
         }

         throw var11;
      }

      req.setTimeOut(md.getTimeOut());
      return req;
   }

   private boolean ensureStubIsUsable(RuntimeMethodDescriptor md) throws ConnectException {
      boolean couldBeAStaleStub = this.couldBeAStaleStub(md);
      if (couldBeAStaleStub && this.staleStubInvocationCount >= 3) {
         throw new ConnectException("Repeated invocation from a stale stub. Preventing further bootstraps from this remote reference");
      } else {
         return couldBeAStaleStub;
      }
   }

   protected void transferContext(OutboundRequest req) throws IOException {
   }

   public Object invoke(Remote stub, RuntimeMethodDescriptor md, Object[] args, Method m) throws Throwable {
      if (!md.hasAsyncResponse() && !md.hasAsyncParameter()) {
         String purl = this.getPartitionURL(stub);
         OutboundRequest request = this.getOutboundRequest(md, purl);
         InboundResponse response = null;

         Object var8;
         try {
            if (md.isOneway()) {
               sendOneway(request, args);
               var8 = null;
               return var8;
            }

            request.marshalArgs(args);
            this.setOverrideMethodTimeout(stub, md, request);
            response = request.sendReceive();

            try {
               var8 = response.unmarshalReturn();
            } catch (Throwable var18) {
               if (var18 instanceof RemoteEJBInvokeException) {
                  throw var18.getCause();
               }

               throw var18;
            }
         } finally {
            try {
               if (response != null) {
                  response.close();
               }
            } catch (IOException var17) {
               throw new UnmarshalException("failed to close response stream", var17);
            }

         }

         return var8;
      } else {
         return this.sendAsync(stub, md, args);
      }
   }

   protected String getPartitionURL(Remote stub) {
      if (stub == null) {
         return null;
      } else if (stub instanceof StubInfoIntf) {
         return ((StubInfoIntf)stub).getStubInfo().getPartitionURL();
      } else {
         throw new IllegalArgumentException("Unknown Remote Object: " + stub);
      }
   }

   private void setOverrideMethodTimeout(Remote stub, RuntimeMethodDescriptor md, OutboundRequest request) {
      if (stub instanceof StubInfoIntf) {
         StubInfo si = ((StubInfoIntf)stub).getStubInfo();
         int timeout = si.getTimeOut(md.getSignature());
         if (timeout > 0) {
            request.setTimeOut(timeout);
         }
      }

   }

   private AsyncCallback sendAsync(Remote stub, RuntimeMethodDescriptor md, Object[] args) throws IOException {
      AsyncCallback result = null;
      OutboundRequest req = null;
      if (md.hasAsyncParameter()) {
         req = this.getOutboundRequest(md, this.getPartitionURL(stub));
         result = this.handleAsyncMarshalArgs(req, md, args);
      } else if (md.hasAsyncResponse()) {
         Debug.assertion(md.hasAsyncResponse());
         FutureResultImpl fri;
         if (md.getReturnType().isAssignableFrom(Future.class)) {
            fri = this.createFutureResultObject(stub, md);
            req = this.getOutboundRequest(md, 25, fri.getId(), this.getPartitionURL(stub));
            result = fri;
         } else if (md.getReturnType().isAssignableFrom(Void.class)) {
            fri = this.createFutureResultObject(stub, md);
            req = this.getOutboundRequest(md, 25, fri.getId(), this.getPartitionURL(stub));
            result = null;
         } else {
            req = this.getOutboundRequest(md, this.getPartitionURL(stub));
            result = new AsyncResultImpl();
         }

         req.marshalArgs(args);
      }

      if (req == null) {
         throw new IOException("Failed to send request");
      } else {
         req.sendAsync((AsyncCallback)result);
         return (AsyncCallback)(md.hasAsyncResponse() ? result : null);
      }
   }

   private FutureResultImpl createFutureResultObject(Remote stub, RuntimeMethodDescriptor md) throws RemoteException {
      FutureResultImpl fri;
      if (md.getRemoteExceptionWrapperClassName() != null && !md.getRemoteExceptionWrapperClassName().isEmpty()) {
         fri = new FutureResultImpl(stub, md);
      } else {
         fri = new FutureResultImpl(stub);
      }

      return fri;
   }

   public final String getCodebase() {
      return null;
   }

   public synchronized void setRequestTimedOut(boolean flag) {
      if (RMIEnvironment.getEnvironment().getTimedOutRefIsolationTime() > 0L) {
         this.timedOut = flag;
         this.timeStamp = System.currentTimeMillis();
      }
   }

   public synchronized boolean hasRequestTimedOut() {
      if (!this.timedOut) {
         return false;
      } else if (System.currentTimeMillis() - this.timeStamp > RMIEnvironment.getEnvironment().getTimedOutRefIsolationTime()) {
         this.setRequestTimedOut(false);
         return false;
      } else {
         return true;
      }
   }

   protected static void sendOneway(OutboundRequest request, Object[] args) throws Exception {
      request.marshalArgs(args);
      request.sendOneWay();
   }

   private AsyncCallback handleAsyncMarshalArgs(OutboundRequest req, RuntimeMethodDescriptor md, Object[] args) throws MarshalException {
      try {
         MsgOutput out = req.getMsgOutput();
         int index = md.getAsyncParameterIndex();
         Class[] argTypes = md.getParameterTypes();
         short[] argTypeCodes = md.getParameterTypeAbbrevs();
         Debug.assertion(argTypes.length > 0);

         for(int i = 0; i < args.length; ++i) {
            if (index != i) {
               ObjectIO.writeObject(out, args[i], argTypes[i], argTypeCodes[i]);
            }
         }

         if (args[index] == null) {
            return new AsyncResultImpl();
         } else {
            return (AsyncCallback)args[index];
         }
      } catch (IOException var9) {
         throw new MarshalException("failed to marshal " + md.getSignature(), var9);
      }
   }

   public final int getObjectID() {
      return this.oid;
   }

   public final HostID getHostID() {
      return this.hostID;
   }

   public final Channel getChannel() {
      return this.getEndPoint().getRemoteChannel();
   }

   public final EndPoint getEndPoint() {
      if (this.endPoint == null || this.endPoint.isDead()) {
         this.endPoint = RMIRuntime.findOrCreateEndPoint(this.hostID);
         if (this.endPoint instanceof PeerInfoable) {
            PeerInfo pi = ((PeerInfoable)this.endPoint).getPeerInfo();
            if (pi != null) {
               this.peerInfo = pi;
            }
         }
      }

      if (this.channelName == null && !this.hostID.isLocal()) {
         Channel ch = (Channel)this.hostID;
         this.channelName = ServerChannelManager.findServerChannelNameForPeer(ch.getPublicInetAddress());
      }

      return this.endPoint;
   }

   public EndPoint getCurrentEndPoint() {
      return this.endPoint;
   }

   public PeerInfo getCurrentPeerInfo() {
      return this.peerInfo;
   }

   public BasicRemoteRef() {
      this.peerInfo = null;
      this.hostReachable = true;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.oid);
      out.writeObject(this.hostID);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.oid = in.readInt();
      this.hostID = (HostID)in.readObject();
      if (enableQOSOnStub) {
         this.protocol = ProtocolStack.get();
      }

   }

   public Object readResolve() throws ObjectStreamException {
      if (!this.getHostID().isLocal()) {
         return this;
      } else {
         try {
            ServerReference serverRef = OIDManager.getInstance().getServerReference(this.getObjectID());
            return serverRef.getLocalRef();
         } catch (NoSuchObjectException var2) {
            if (this.couldBeNonLocalOnNoSuchObjectException()) {
               return this;
            } else {
               throw (InvalidObjectException)(new InvalidObjectException("Local reference could not be found")).initCause(var2);
            }
         }
      }
   }

   protected boolean couldBeNonLocalOnNoSuchObjectException() {
      return false;
   }

   static {
      if (!KernelStatus.isApplet()) {
         String setQOSOnStub = System.getProperty("weblogic.t3.setQOSOnStub");
         if (setQOSOnStub != null) {
            enableQOSOnStub = !setQOSOnStub.equalsIgnoreCase("false");
         }
      }

   }
}
