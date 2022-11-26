package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import javax.naming.NamingException;
import org.omg.CORBA.SystemException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rjvm.JVMID;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.extensions.RemoteSystemException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.server.ClusterAwareRemoteReference;
import weblogic.rmi.extensions.server.ForwardReference;
import weblogic.rmi.extensions.server.LocationForwardException;
import weblogic.rmi.extensions.server.OutboundRequestBuilder;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.ClusteredFutureResultImpl;
import weblogic.rmi.internal.ClusteredFutureResultRetryHelper;
import weblogic.rmi.internal.LeasedRemoteRef;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

public class ClusterableRemoteRef implements ClusterAwareRemoteReference, Externalizable, Cloneable {
   private static final DebugLogger debugFailoverLogger = DebugLogger.getDebugLogger("DebugFailOver");
   private ReplicaHandler replicaHandler;
   private RemoteReference curRef;
   private boolean propagateEnvironment;
   private boolean isInitialized;
   private transient Object environment;
   private transient PiggybackRequester piggybackRequester;
   private static final long serialVersionUID = -4613906356180778170L;
   private TransactionalAffinityHandler txnAffinityHandler = null;

   public ClusterableRemoteRef(RemoteReference primary) {
      this.curRef = primary;
      this.isInitialized = false;
      this.environment = RMIEnvironment.getEnvironment().threadEnvironmentGet();
   }

   public final Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError("impossible exception", var2);
      }
   }

   public ClusterableRemoteRef initialize(ReplicaAwareInfo info) {
      if (!this.isInitialized) {
         if (RMIEnvironment.getEnvironment().txnAffinityEnabled() && this.txnAffinityHandler == null) {
            this.txnAffinityHandler = new TransactionalAffinityHandler();
         }

         this.replicaHandler = info.getReplicaHandler(this.curRef);
         if (this.replicaHandler instanceof PiggybackRequester) {
            this.piggybackRequester = (PiggybackRequester)this.replicaHandler;
         }

         this.propagateEnvironment = info.getPropagateEnvironment();
         if (this.getHostID().isLocal()) {
            try {
               ServerReference serverRef = OIDManager.getInstance().getServerReference(this.getObjectID());
               Debug.assertion(serverRef instanceof ClusterableServerRef, "serverRef must be ReplicaAwareServerRef");
               ((ClusterableServerRef)serverRef).initialize(this, info);
            } catch (NoSuchObjectException var3) {
               throw new RuntimeException("attempting to initialize a replica-aware ref using a remote object that isn't exported", var3);
            }
         }

         this.isInitialized = true;
      }

      return this;
   }

   public void initialize(ServerReference ref, String jndiName) {
      ClusterableServerRef sRef = (ClusterableServerRef)ref;
      ReplicaAwareInfo info = sRef.getInfo();
      info.setJNDIName(jndiName);
      this.initialize(info);
   }

   public final boolean isInitialized() {
      return this.isInitialized;
   }

   public final ReplicaList getReplicaList() {
      return this.replicaHandler.getReplicaList();
   }

   public final int getReplicaCount() {
      return this.replicaHandler.getReplicaList().size();
   }

   public final void resetReplicaList(ReplicaList newList) {
      this.getReplicaHandler().resetReplicaList(newList);
      if (this.getHostID().isLocal()) {
         Object o = null;

         try {
            o = OIDManager.getInstance().getServerReference(this.getObjectID());
            ClusterableServerRef sRef = (ClusterableServerRef)o;
            sRef.reset(this);
         } catch (NoSuchObjectException var4) {
            throw new AssertionError("remote not initialized", var4);
         } catch (ClassCastException var5) {
            throw new AssertionError("server reference is not ClusterableServerRef. reference:" + (o != null ? o : "[this is not initialized]") + " ,reference.getClass():" + (o != null ? o.getClass() : "[this is not initialized]") + " ,ClusterableRemoteRef:" + this, var5);
         }
      }

   }

   public final RemoteReference getPrimaryRef() {
      return !this.isInitialized ? this.curRef : this.getReplicaList().getPrimary();
   }

   public final HostID getHostID() {
      RemoteReference current = this.getCurrentReplica();
      return current != null ? current.getHostID() : null;
   }

   public final Channel getChannel() {
      RemoteReference current = this.getCurrentReplica();
      return current != null ? current.getChannel() : null;
   }

   public final String getCodebase() {
      RemoteReference current = this.getCurrentReplica();
      return current != null ? current.getCodebase() : null;
   }

   public void setRequestTimedOut(boolean flag) {
   }

   public boolean hasRequestTimedOut() {
      return false;
   }

   public final int getObjectID() {
      RemoteReference current = this.getCurrentReplica();
      return current != null ? current.getObjectID() : -1;
   }

   public final RemoteReference getCurrentReplica() {
      if (this.replicaHandler instanceof PrimarySecondaryReplicaHandler) {
         return this.curRef;
      } else {
         if (KernelStatus.isServer() && this.curRef != null && !this.curRef.getHostID().isLocal() && this.isInitialized) {
            ReplicaList replicaList = this.replicaHandler.getReplicaList();
            if (replicaList != null) {
               RemoteReference localRef = replicaList.findReplicaHostedBy(LocalServerIdentity.getIdentity());
               this.curRef = localRef != null ? localRef : this.curRef;
            }
         }

         return this.curRef;
      }
   }

   public final ReplicaHandler getReplicaHandler() {
      return this.replicaHandler;
   }

   public final Object invoke(Remote stub, RuntimeMethodDescriptor md, Object[] params, Method method) throws Throwable {
      Debug.assertion(this.isInitialized, "must initialize before invoke");
      if (this.environment != null) {
         RMIEnvironment.getEnvironment().threadEnvironmentPush(this.environment);
      }

      if (this.replicaHandler instanceof PrimarySecondaryReplicaHandler && stub instanceof StubInfoIntf) {
         StubInfoIntf stubInfoIntf = (StubInfoIntf)stub;
         StubInfo stubInfo = stubInfoIntf.getStubInfo();
         ((PrimarySecondaryReplicaHandler)this.replicaHandler).setPartitionName(stubInfo.getPartitionName());
      }

      try {
         this.curRef = this.replicaHandler.loadBalance(this.curRef, method, params, this.txnAffinityHandler, md);
         if (this.curRef == null) {
            throw new ConnectException("No server can be reached");
         } else {
            RetryHandler retryHandler = new RetryHandler();
            if (this.hasTimedOut(this.curRef, md)) {
               this.curRef = this.replicaHandler.failOver(this.curRef, md, method, params, (RemoteException)null, retryHandler);
            }

            Integer forwardCount = 0;
            int retryCount = 0;

            while(forwardCount < 2) {
               try {
                  retryHandler.setRetryCount(retryCount);
                  Object var21;
                  if (md.hasAsyncResponse()) {
                     ClusteredFutureResultRetryHelper cfRetryHelper = new ClusteredFutureResultRetryHelper(md, method, params, retryHandler, 0);
                     var21 = this.asyncInvoke(stub, cfRetryHelper);
                     return var21;
                  }

                  int timeout = md.getTimeOut();
                  if (stub instanceof StubInfoIntf) {
                     StubInfoIntf stubInfoIntf = (StubInfoIntf)stub;
                     StubInfo stubInfo = stubInfoIntf.getStubInfo();
                     timeout = stubInfo.getTimeOut(md.getSignature());
                  }

                  var21 = this.invoke(this.curRef, md, params, this.getPartitionURL(stub), timeout);
                  return var21;
               } catch (Exception var16) {
                  Exception e = var16;

                  try {
                     if (!this.handleException(e, md, method, params, retryHandler, forwardCount)) {
                        throw e;
                     }
                  } catch (Exception var15) {
                     if (var15 instanceof RemoteEJBInvokeException) {
                        throw var15.getCause();
                     }

                     throw var15;
                  }

                  ++retryCount;
               }
            }

            throw new ConnectException("Unable to handle LocationForward");
         }
      } finally {
         if (this.environment != null) {
            RMIEnvironment.getEnvironment().threadEnvironmentPop();
         }

      }
   }

   private String getPartitionName(Remote stub) {
      if (stub instanceof StubInfoIntf) {
         StubInfo si = ((StubInfoIntf)stub).getStubInfo();
         return si.getPartitionName();
      } else {
         throw new IllegalArgumentException("Remote does not implement StubInfoIntf interface: " + stub);
      }
   }

   /** @deprecated */
   @Deprecated
   private String getPartitionURL(Remote stub) {
      if (stub instanceof StubInfoIntf) {
         StubInfo si = ((StubInfoIntf)stub).getStubInfo();
         return si.getPartitionURL();
      } else {
         throw new IllegalArgumentException("Remote does not implement StubInfoIntf interface: " + stub);
      }
   }

   public boolean handleException(Exception e, ClusteredFutureResultRetryHelper helper) throws Exception {
      return this.handleException(e, helper.getMethodDescriptor(), helper.getMethod(), helper.getParams(), helper.getRetryHandler(), helper.getForwardCount());
   }

   private boolean handleException(Exception e, RuntimeMethodDescriptor md, Method method, Object[] params, RetryHandler retryHandler, Integer forwardCount) throws Exception {
      boolean retry = false;
      if (e instanceof RequestTimeoutException) {
         this.curRef.setRequestTimedOut(true);
         throw e;
      } else {
         if (e instanceof RemoteException) {
            if (debugFailoverLogger.isDebugEnabled()) {
               debugFailoverLogger.debug("Attempt to failover because of exception", e);
            }

            this.curRef = this.replicaHandler.failOver(this.curRef, md, method, params, (RemoteException)e, retryHandler);
            retry = true;
         } else if (e instanceof SystemException) {
            if (debugFailoverLogger.isDebugEnabled()) {
               debugFailoverLogger.debug("Attempt to failover because of exception", e);
            }

            this.curRef = this.replicaHandler.failOver(this.curRef, md, method, params, new RemoteSystemException((SystemException)e), retryHandler);
            retry = true;
         }

         if (e instanceof LocationForwardException) {
            forwardCount + 1;
         }

         if (e instanceof NamingException) {
            if (RMIEnvironment.getEnvironment().isAdminModeAccessException((NamingException)e)) {
               if (debugFailoverLogger.isDebugEnabled()) {
                  debugFailoverLogger.debug("Attempt to failover because of exception", e);
               }

               this.curRef = this.replicaHandler.failOver(this.curRef, md, method, params, new RemoteException("", e), retryHandler);
               retry = true;
            } else {
               Throwable rootCause = getRootCauseForNamingException((NamingException)e);
               if (!(rootCause instanceof RemoteException)) {
                  throw e;
               }

               if (debugFailoverLogger.isDebugEnabled()) {
                  debugFailoverLogger.debug("Attempt to failover because of exception", e);
               }

               this.curRef = this.replicaHandler.failOver(this.curRef, md, method, params, (RemoteException)rootCause, retryHandler);
               retry = true;
            }
         }

         return retry;
      }
   }

   private Object asyncInvoke(Remote stub, ClusteredFutureResultRetryHelper helper) throws Exception {
      RuntimeMethodDescriptor md = helper.getMethodDescriptor();
      ClusteredFutureResultImpl cfri;
      if (md.getRemoteExceptionWrapperClassName() != null && !md.getRemoteExceptionWrapperClassName().isEmpty()) {
         cfri = new ClusteredFutureResultImpl(stub, this.curRef.getHostID(), this, md, helper);
      } else {
         cfri = new ClusteredFutureResultImpl(stub, this.curRef.getHostID(), this, helper);
      }

      OutboundRequest request;
      if (this.curRef instanceof OutboundRequestBuilder) {
         request = ((OutboundRequestBuilder)this.curRef).getOutboundRequest(md, 25, cfri.getId(), this.getPartitionURL(stub));
      } else {
         request = this.curRef.getOutboundRequest(md, this.getPartitionURL(stub));
      }

      request.marshalArgs(helper.getParams());
      request.setReplicaInfo(this.getPiggybackRequest());
      request.sendAsync(cfri);
      return md.hasAsyncResponse() ? cfri : null;
   }

   public void invoke(ClusteredFutureResultImpl cfri, Remote stub) throws Throwable {
      Debug.assertion(this.isInitialized, "must initialize before invoke");
      if (this.environment != null) {
         RMIEnvironment.getEnvironment().threadEnvironmentPush(this.environment);
      }

      if (this.replicaHandler instanceof PrimarySecondaryReplicaHandler && stub instanceof StubInfoIntf) {
         StubInfoIntf stubInfoIntf = (StubInfoIntf)stub;
         StubInfo stubInfo = stubInfoIntf.getStubInfo();
         ((PrimarySecondaryReplicaHandler)this.replicaHandler).setPartitionName(stubInfo.getPartitionName());
      }

      try {
         ClusteredFutureResultRetryHelper helper = cfri.getHelper();
         if (this.hasTimedOut(this.curRef, helper.getMethodDescriptor())) {
            this.curRef = this.replicaHandler.failOver(this.curRef, helper.getMethodDescriptor(), helper.getMethod(), helper.getParams(), (RemoteException)null, helper.getRetryHandler());
         }

         int count = helper.getRetryHandler().getRetryCount();
         helper.getRetryHandler().setRetryCount(count++);
         OutboundRequest request;
         if (this.curRef instanceof OutboundRequestBuilder) {
            request = ((OutboundRequestBuilder)this.curRef).getOutboundRequest(helper.getMethodDescriptor(), 25, cfri.getId(), this.getPartitionURL(stub));
         } else {
            request = this.curRef.getOutboundRequest(helper.getMethodDescriptor(), this.getPartitionURL(stub));
         }

         cfri.updateClusterableRemoteRef(this);
         cfri.setThrowable((Throwable)null);
         request.marshalArgs(helper.getParams());
         request.setReplicaInfo(this.getPiggybackRequest());
         request.sendAsync(cfri);
      } finally {
         if (this.environment != null) {
            RMIEnvironment.getEnvironment().threadEnvironmentPop();
         }

      }

   }

   static Throwable getRootCauseForNamingException(NamingException ne) {
      Throwable th;
      for(th = ne.getRootCause(); th instanceof NamingException; th = ((NamingException)th).getRootCause()) {
      }

      return th;
   }

   private boolean hasTimedOut(RemoteReference curRef, RuntimeMethodDescriptor md) {
      if (md.getTimeOut() <= 0) {
         return false;
      } else {
         return curRef.hasRequestTimedOut();
      }
   }

   private Object invoke(RemoteReference ref, RuntimeMethodDescriptor md, Object[] args, String partitionURL, int timeout) throws Throwable {
      InboundResponse response = null;

      Object o;
      try {
         OutboundRequest request = ref.getOutboundRequest(md, partitionURL);
         request.setTimeOut(timeout);
         request.marshalArgs(args);
         request.setReplicaInfo(this.getPiggybackRequest());
         if (!md.isOneway()) {
            response = request.sendReceive();
            if (ref instanceof ForwardReference) {
               ((ForwardReference)ref).handleRedirect(response);
            }

            o = response.unmarshalReturn();
            this.setPiggybackResponse(response.getReplicaInfo());
            Object var9 = o;
            return var9;
         }

         request.sendOneWay();
         o = null;
      } catch (RemoteException var20) {
         throw var20;
      } catch (IOException var21) {
         throw new UnmarshalException("invoking method " + md + " caused " + var21.toString() + ", please check method's implementation for the cause ", var21);
      } finally {
         try {
            if (response != null) {
               response.close();
            }
         } catch (IOException var19) {
            throw new UnmarshalException("failed to close response stream", var19);
         }

      }

      return o;
   }

   protected final Version getPiggybackRequest() {
      return this.piggybackRequester != null ? this.piggybackRequester.getPiggybackRequest() : null;
   }

   public final void setPiggybackResponse(Object o) {
      if (this.piggybackRequester != null && o != null) {
         this.piggybackRequester.setPiggybackResponse(o);
      }

   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public final OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      String className = this.getClass().getName();
      int lastDot = className.lastIndexOf(46);
      if (lastDot > -1) {
         className = className.substring(lastDot + 1);
      }

      return className + " Initialized?=" + this.isInitialized + '(' + (this.curRef != null ? this.curRef.getHostID() : "NullRef") + ' ' + (this.replicaHandler != null ? this.replicaHandler : "NullReplicaHandler") + ")/" + this.getObjectID();
   }

   public int hashCode() {
      return this.curRef == null ? 0 : this.curRef.hashCode();
   }

   public boolean equals(Object other) {
      return other instanceof ClusterableRemoteRef ? ((ClusterableRemoteRef)other).curRef.equals(this.curRef) : false;
   }

   public synchronized void setCurRef(RemoteReference ref) {
      this.curRef = ref;
   }

   public ClusterableRemoteRef() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeBoolean(this.isInitialized);
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlout = (WLObjectOutput)out;
         wlout.writeObjectWL(this.replicaHandler);
         wlout.writeObjectWL(this.curRef);
      } else {
         out.writeObject(this.replicaHandler);
         out.writeObject(this.curRef);
      }

      out.writeBoolean(this.propagateEnvironment);
      if (out instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)out).getPeerInfo();
         if (pi.is1213Peer()) {
            out.writeBoolean(this.txnAffinityHandler != null);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.isInitialized = in.readBoolean();
      if (in instanceof WLObjectInput) {
         WLObjectInput wlin = (WLObjectInput)in;
         this.replicaHandler = (ReplicaHandler)wlin.readObjectWL();
         this.curRef = (RemoteReference)wlin.readObjectWL();
      } else {
         this.replicaHandler = (ReplicaHandler)in.readObject();
         this.curRef = (RemoteReference)in.readObject();
      }

      this.propagateEnvironment = in.readBoolean();
      if (this.replicaHandler instanceof PiggybackRequester) {
         this.piggybackRequester = (PiggybackRequester)this.replicaHandler;
      }

      if (this.propagateEnvironment) {
         this.environment = RMIEnvironment.getEnvironment().threadEnvironmentGet();
         if (this.environment == null && !KernelStatus.isServer()) {
            this.environment = RMIEnvironment.getEnvironment().newEnvironment();
         }
      }

      if (in instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)in).getPeerInfo();
         if (pi.is1213Peer()) {
            boolean needTxnAffinityHandler = in.readBoolean();
            if (needTxnAffinityHandler) {
               this.txnAffinityHandler = new TransactionalAffinityHandler();
            }
         }
      }

      StringBuffer msg = new StringBuffer();
      msg.append("ClusterableRemoteRef.readExternal Initialized:" + this.isInitialized).append(" CurRef is " + this.curRef == null ? "Null" : "NonNull");
      debugFailoverLogger.debug(msg.toString());
   }

   public void add(ClusterableRemoteRef other) {
      RemoteReference otherPrimary = other.getPrimaryRef();
      this.getReplicaList().add(otherPrimary);
      if (otherPrimary.getHostID().isLocal()) {
         try {
            other.resetReplicaList(this.getReplicaList());
            this.setCurRef(otherPrimary);
         } catch (AssertionError var7) {
            this.getReplicaList().remove(otherPrimary);
            Object o = null;

            try {
               o = OIDManager.getInstance().getServerReference(this.getObjectID());
            } catch (NoSuchObjectException var6) {
            }

            throw new AssertionError("ClusterableServerRef.add faced AssertionError.  ,serverReference:" + (o != null ? o : "[this is not initialized]") + " ,serverReference.getClass():" + (o != null ? o.getClass() : "[this is not initialized]") + " ,this:" + this, var7);
         }
      }

   }

   public void remove(ClusterableRemoteRef other) {
      this.removeOne(other.getPrimaryRef().getHostID());
   }

   public void removeOne(HostID host) {
      this.getReplicaList().removeOne(host);
      if (this.getHostID() != null && this.getHostID().equals(host)) {
         if (this.getReplicaList().size() == 0) {
            this.setCurRef((RemoteReference)null);
         } else {
            this.setCurRef(this.getReplicaList().getPrimary());
         }
      }

   }

   public void replace(ClusterableRemoteRef replacement) {
      RemoteReference replacementPrimary = replacement.getPrimaryRef();
      ReplicaList replicaList = this.getReplicaList();
      if (replacementPrimary.getHostID().isLocal()) {
         replacement.resetReplicaList(replicaList);
         if (this.getReplicaHandler() instanceof BasicReplicaHandler && ((BasicReplicaHandler)this.getReplicaHandler()).isAffinityRequired()) {
            this.setCurRef(replacementPrimary);
         }
      }

      synchronized(replicaList) {
         replicaList.removeOne(replacementPrimary.getHostID());
         replicaList.add(replacementPrimary);
      }
   }

   public boolean pin(JVMID id) {
      ReplicaList list = this.getReplicaList();
      if (list == null) {
         return false;
      } else {
         RemoteReference r = list.findReplicaHostedBy(id);
         if (r == null) {
            return false;
         } else if (!(r instanceof BasicRemoteRef)) {
            return false;
         } else {
            JVMID refID = (JVMID)r.getHostID();
            Object newRef;
            if (id.matchAddressPortAndProtocolIndex(refID.getInetAddress(), refID.getPublicPort(), refID.getConfiguredProtocolIndex())) {
               newRef = r;
            } else {
               newRef = r instanceof LeasedRemoteRef ? new LeasedRemoteRef(r.getObjectID(), id) : new BasicRemoteRef(r.getObjectID(), id);
               synchronized(list) {
                  list.clear();
                  list.add((RemoteReference)newRef);
               }
            }

            this.isInitialized = false;
            this.setCurRef((RemoteReference)newRef);

            try {
               this.initialize(((ClusterableServerRef)OIDManager.getInstance().getServerReference(((RemoteReference)newRef).getObjectID())).getInfo());
               return true;
            } catch (Exception var8) {
               return false;
            }
         }
      }
   }

   boolean isAffinityHandlerSet() {
      return this.txnAffinityHandler != null;
   }
}
