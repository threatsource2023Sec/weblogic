package weblogic.rmi.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.ConnectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import javax.naming.Context;
import javax.naming.NamingException;
import org.omg.CORBA.SystemException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.RMILogger;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.RemoteEJBPreInvokeException;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.RemoteSystemException;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.utils.Debug;

public class BasicReplicaHandler implements ReplicaHandler, PiggybackRequester, Externalizable {
   private static final long serialVersionUID = 5316697778118669758L;
   private static final DebugLogger debugFailoverLogger = DebugLogger.getDebugLogger("DebugFailOver");
   private static final DebugLogger debugLoadbalancingLogger = DebugLogger.getDebugLogger("DebugLoadBalancing");
   private static final boolean DEBUG = false;
   protected int current;
   protected ReplicaList replicaList;
   protected CallRouter callRouter;
   protected boolean stickToFirstServer;
   protected boolean propagateEnvironment;
   protected String jndiName;
   protected transient ReplicaAwareInfo info;
   protected transient RemoteReference primary;
   private transient boolean firstRequest;
   private transient Object env;
   private transient HostID lastPreferredHost;
   private transient boolean isAffinityRequired;

   protected int getCurrent() {
      return this.current;
   }

   protected void setCurrent(int current) {
      this.current = current;
   }

   protected void setAffinityRequired(boolean isAffinityRequired) {
      this.isAffinityRequired = isAffinityRequired;
   }

   protected boolean isAffinityRequired() {
      return this.isAffinityRequired;
   }

   protected void setStickToFirstServer(boolean stickToFirstServer) {
      this.stickToFirstServer = stickToFirstServer;
   }

   public BasicReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      this(info, newReplicaList(info, primary));
      this.primary = primary;
   }

   private static ReplicaList newReplicaList(ReplicaAwareInfo info, RemoteReference primary) {
      return (ReplicaList)(info.getCallRouter() == null ? new BasicReplicaList(primary) : new RichReplicaList(primary));
   }

   protected BasicReplicaHandler(ReplicaAwareInfo info, ReplicaList list) {
      this();
      this.callRouter = info.getCallRouter();
      this.stickToFirstServer = info.getStickToFirstServer();
      this.jndiName = info.getJNDIName();
      this.propagateEnvironment = info.getPropagateEnvironment();
      Debug.assertion(this.callRouter == null || list instanceof RichReplicaList, "list must support server name mapping");
      this.replicaList = list;
   }

   public Version getPiggybackRequest() {
      return this.replicaList.version();
   }

   public void setPiggybackResponse(Object response) {
      if (response != null) {
         this.resetReplicaList((ReplicaList)response);
      }

   }

   public ReplicaList getReplicaList() {
      return this.replicaList;
   }

   public void resetReplicaList(ReplicaList newList) {
      if (newList.getClass().equals(this.replicaList.getClass())) {
         this.replicaList.reset(newList);
      } else {
         this.replicaList = newList;
      }

      int count = this.replicaList.size();
      if (count == 0) {
         this.current = 0;
      } else {
         double dIdx = Math.random() * (double)count + 0.5;
         this.current = (int)Math.round(dIdx) - 1;
      }

   }

   protected boolean isRecoverableFailure(RuntimeMethodDescriptor md, RemoteException e) {
      if (e instanceof RemoteEJBPreInvokeException) {
         return true;
      } else {
         if (e instanceof RemoteEJBInvokeException) {
            Throwable t = unwrapRemoteEJBInvokeException((RemoteEJBInvokeException)e);
            if (!(t instanceof RemoteException)) {
               return false;
            }

            e = (RemoteException)t;
         }

         return md.isIdempotent() ? RemoteHelper.isRecoverableFailure(e) : RemoteHelper.isRecoverablePreInvokeFailure(e);
      }
   }

   public static Throwable unwrapRemoteEJBInvokeException(RemoteEJBInvokeException e) {
      Throwable t = e.getCause();
      if (t instanceof RemoteException) {
         return t;
      } else if (t instanceof IOException) {
         return new UnmarshalException(t.getMessage(), (Exception)t);
      } else if (t instanceof SystemException) {
         return new RemoteSystemException((SystemException)t);
      } else {
         if (t instanceof NamingException) {
            NamingException ne = (NamingException)t;
            if (RMIEnvironment.getEnvironment().isAdminModeAccessException(ne)) {
               return new RemoteException("", ne);
            }

            Throwable tt = ClusterableRemoteRef.getRootCauseForNamingException(ne);
            if (tt instanceof RemoteException) {
               return tt;
            }
         }

         return t;
      }
   }

   protected final String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String jndiName) {
      this.jndiName = jndiName;
   }

   public RemoteReference failOver(RemoteReference failedRef, RuntimeMethodDescriptor md, Method m, Object[] params, RemoteException re, RetryHandler retryHandler) throws RemoteException {
      int retryCount = retryHandler.getRetryCount();
      if (re == null) {
         return this.failOverToPreventTimeout(failedRef, md, m, params, re, retryCount);
      } else if (!this.isRecoverableFailure(md, re)) {
         if (debugFailoverLogger.isDebugEnabled()) {
            debugFailoverLogger.debug(this.getJNDIName() + " can't recover from unrecoverable exception " + re);
         }

         throw RemoteHelper.returnOrUnwrap(re);
      } else {
         if (debugFailoverLogger.isDebugEnabled()) {
            debugFailoverLogger.debug(this.getJNDIName() + " attempting failover due to: " + re);
         }

         this.replicaList.remove(failedRef);
         if (retryCount == 0) {
            retryHandler.setMaxRetryCount(this.replicaList.size());
         }

         boolean isAlreadyRefreshed = retryHandler.isListRefreshed();
         if (this.replicaList.size() != 0 || retryHandler.updateIsListRefreshed(this.refreshReplicaList()) && this.replicaList.size() != 0) {
            if (isAlreadyRefreshed && retryHandler.isListExhausted()) {
               if (debugFailoverLogger.isDebugEnabled()) {
                  debugFailoverLogger.debug(this.getJNDIName() + " unable to failover after " + retryCount + " tries");
               }

               throw RemoteHelper.returnOrUnwrap(re);
            } else {
               if (!isAlreadyRefreshed && retryHandler.isListRefreshed()) {
                  retryHandler.setMaxRetryCount(retryCount + this.replicaList.size() - 1);
               }

               RemoteReference ref = null;
               if (this.callRouter != null) {
                  ref = this.chooseReplicaUsingCallRouter(m, params);
               }

               if (ref == null && this.isAffinityRequired) {
                  ref = this.chooseReplicaAfterFailureUsingAffinity(failedRef, m, params, re);
               }

               if (ref == null) {
                  ref = this.chooseReplicaAfterFailure(failedRef, m, params, re);
               }

               if (ref == null) {
                  if (debugFailoverLogger.isDebugEnabled()) {
                     debugFailoverLogger.debug(this.getJNDIName() + " unable to failover after " + retryCount + " tries");
                  }

                  throw RemoteHelper.returnOrUnwrap(re);
               } else if (ref == failedRef) {
                  if (debugFailoverLogger.isDebugEnabled()) {
                     debugFailoverLogger.debug(this.getJNDIName() + " unable to failover");
                  }

                  throw RemoteHelper.returnOrUnwrap(re);
               } else {
                  if (debugFailoverLogger.isDebugEnabled()) {
                     debugFailoverLogger.debug(this.getJNDIName() + " failing over to " + ref.getHostID());
                  }

                  return ref;
               }
            }
         } else {
            if (debugFailoverLogger.isDebugEnabled()) {
               debugFailoverLogger.debug(this.getJNDIName() + " unable to failover");
            }

            throw RemoteHelper.returnOrUnwrap(re);
         }
      }
   }

   private RemoteReference failOverToPreventTimeout(RemoteReference failedRef, RuntimeMethodDescriptor md, Method m, Object[] params, RemoteException re, int retryCount) throws RemoteException {
      if (this.replicaList.size() != 0 || this.refreshReplicaList() && this.replicaList.size() != 0) {
         if (this.callRouter == null && this.isAffinityRequired) {
            return failedRef;
         } else {
            RemoteReference ref = null;

            for(int count = this.replicaList.size(); count > 0; --count) {
               if (this.callRouter != null) {
                  ref = this.chooseReplicaUsingCallRouter(m, params);
               }

               if (ref == null) {
                  ref = this.chooseReplicaAfterFailure(failedRef, m, params, re);
               }

               if (ref == null) {
                  return failedRef;
               }

               if (!ref.hasRequestTimedOut()) {
                  return ref;
               }
            }

            return failedRef;
         }
      } else {
         return failedRef;
      }
   }

   public RemoteReference loadBalance(RemoteReference currentRef, Method method, Object[] params, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor md) {
      RemoteReference newRef = null;
      if (this.callRouter != null) {
         try {
            newRef = this.chooseReplicaUsingCallRouter(method, params);
            if (newRef != null) {
               this.stickToFirstServer = false;
            }
         } catch (ConnectException var10) {
            return null;
         }
      }

      if (newRef == null) {
         if (!this.firstRequest && this.stickToFirstServer) {
            newRef = currentRef;
         } else if ((newRef = this.getPreferredRef(currentRef, txnAffinityHandler, md)) == null) {
            newRef = currentRef;

            for(int count = this.replicaList.size(); count > 0; --count) {
               RemoteReference nextReplica;
               if (this.isAffinityRequired) {
                  nextReplica = this.chooseReplicaUsingAffinity(currentRef, method, params);
               } else {
                  nextReplica = this.chooseReplica(currentRef, method, params);
               }

               try {
                  if (nextReplica != null && !isHostUnresponsive(nextReplica)) {
                     newRef = nextReplica;
                     break;
                  }
               } catch (PeerNotActiveException var11) {
                  this.replicaList.remove(nextReplica);
               }
            }
         }

         this.firstRequest = false;
      }

      if (debugLoadbalancingLogger.isDebugEnabled()) {
         if (newRef != null) {
            debugLoadbalancingLogger.debug(this.getJNDIName() + " request routing from " + (currentRef == null ? null : currentRef.getHostID()) + " to " + newRef.getHostID());
         } else {
            debugLoadbalancingLogger.debug(this.getJNDIName() + " from " + (currentRef == null ? null : currentRef.getHostID()) + " request can't find live host");
         }
      }

      return newRef;
   }

   private RemoteReference getPreferredRef(RemoteReference currentRef, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor md) {
      RemoteReference preferredRef = null;
      if (KernelStatus.isServer() && currentRef.getHostID().isLocal()) {
         return currentRef;
      } else {
         if (preferredRef == null) {
            if (txnAffinityHandler != null && txnAffinityHandler.requiresAffinityBasedHandling(md)) {
               preferredRef = txnAffinityHandler.findTxnAffinityBasedRef(currentRef, this.jndiName, this.replicaList);
               if (preferredRef != null) {
                  return preferredRef;
               }
            }

            HostID preferredHost = ThreadPreferredHost.get();
            if (preferredHost == null) {
               this.lastPreferredHost = null;
            } else if (preferredHost == this.lastPreferredHost) {
               preferredRef = currentRef;
            } else {
               preferredRef = this.replicaList.findReplicaHostedBy(preferredHost);
               if (preferredRef != null) {
                  this.lastPreferredHost = preferredHost;
               }
            }

            if (debugLoadbalancingLogger.isDebugEnabled() && preferredHost != null && preferredRef == null) {
               debugLoadbalancingLogger.debug("couldn't find replica for " + this.getJNDIName() + "hosted by " + preferredHost + " in\n: " + this.replicaList);
            }
         }

         return preferredRef;
      }
   }

   protected RemoteReference chooseReplica(RemoteReference currentRef, Method method, Object[] params) {
      synchronized(this.replicaList) {
         int count = this.replicaList.size();
         if (count == 0) {
            return currentRef;
         } else {
            this.current = (this.current + 1) % count;
            return this.replicaList.get(this.current);
         }
      }
   }

   protected RemoteReference chooseReplicaAfterFailure(RemoteReference currentRef, Method method, Object[] params, RemoteException exception) {
      return this.chooseReplica(currentRef, method, params);
   }

   protected RemoteReference chooseReplicaUsingCallRouter(Method method, Object[] params) throws ConnectException {
      RichReplicaList replicaList = (RichReplicaList)this.getReplicaList();
      String[] servers = this.callRouter.getServerList(method, params);
      if (servers == null) {
         if (debugLoadbalancingLogger.isDebugEnabled()) {
            debugLoadbalancingLogger.debug("CallRouter returned null list");
         }

         return null;
      } else {
         for(int j = 0; j < 2; ++j) {
            for(int i = 0; i < servers.length; ++i) {
               if (debugLoadbalancingLogger.isDebugEnabled()) {
                  debugLoadbalancingLogger.debug(this.getJNDIName() + " trying " + servers[i]);
               }

               RemoteReference choice = replicaList.findReplicaHostedBy(servers[i]);
               if (choice != null && !RemoteHelper.isHostDead(choice)) {
                  return choice;
               }
            }

            this.refreshReplicaList();
         }

         throw new ConnectException("Failed to reach any server hosting " + this.getJNDIName());
      }
   }

   protected boolean refreshReplicaList() {
      if (this.jndiName != null && !RMIEnvironment.getEnvironment().isIIOPVendorInfoCluster(this.replicaList)) {
         Context ctx = null;

         boolean var5;
         try {
            if (debugFailoverLogger.isDebugEnabled()) {
               debugFailoverLogger.debug(this.jndiName + " refreshing replica list");
            }

            ctx = RMIEnvironment.getEnvironment().getContext(this.env);
            Object obj = ctx.lookup(this.jndiName);
            Object ref;
            if (obj instanceof StubInfoIntf) {
               ref = ((StubInfoIntf)obj).getStubInfo().getRemoteRef();
            } else if (obj instanceof ClusterableRemoteRef) {
               ref = (ClusterableRemoteRef)obj;
            } else {
               Object obj1 = ((RemoteWrapper)obj).getRemoteDelegate();
               if (RemoteHelper.isCollocated(obj1)) {
                  if (debugFailoverLogger.isDebugEnabled()) {
                     debugFailoverLogger.debug("RemoteDelegate: " + obj1 + " is co-located. Generating stub");
                  }

                  obj1 = StubFactory.getStub((Remote)obj1);
               }

               ref = ((StubInfoIntf)obj1).getStubInfo().getRemoteRef();
            }

            if (!(ref instanceof ClusterableRemoteRef)) {
               if (debugFailoverLogger.isDebugEnabled()) {
                  debugFailoverLogger.debug(this.jndiName + " failed to refresh replica list");
               }

               return false;
            }

            ReplicaList list = ((ClusterableRemoteRef)ref).getReplicaList();
            this.resetReplicaList(list);
            var5 = true;
         } catch (NamingException var16) {
            if (debugFailoverLogger.isDebugEnabled()) {
               debugFailoverLogger.debug(this.getJNDIName() + " failed to refresh replica list");
            }

            return false;
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var15) {
                  if (debugFailoverLogger.isDebugEnabled()) {
                     log("failed to close context");
                  }
               }
            }

         }

         return var5;
      } else {
         return false;
      }
   }

   protected static final boolean isHostUnresponsive(RemoteReference ref) throws PeerNotActiveException {
      if (RMIEnvironment.getEnvironment().isIIOPHostID(ref.getHostID())) {
         return false;
      } else {
         EndPoint endPoint = RMIRuntime.findOrCreateEndPoint(ref.getHostID());
         if (endPoint != null && !endPoint.isDead()) {
            return endPoint.isUnresponsive();
         } else {
            throw new PeerNotActiveException();
         }
      }
   }

   private static boolean isConnectionEstablished(RemoteReference ref) {
      EndPoint endPoint = RMIRuntime.findEndPoint(ref.getHostID());
      return endPoint != null && !endPoint.isDead();
   }

   private RemoteReference getReplicaWithEndPoint(RemoteReference currentRef, Method method, Object[] params) {
      RemoteReference ref = null;
      ReplicaList replicaList = this.getReplicaList();

      for(int cnt = replicaList.size(); cnt > 0; --cnt) {
         RemoteReference nextReplica = this.chooseReplica(currentRef, method, params);
         if (isConnectionEstablished(nextReplica)) {
            ref = nextReplica;
            break;
         }
      }

      return ref;
   }

   private RemoteReference chooseReplicaUsingAffinity(RemoteReference currentRef, Method method, Object[] params) {
      RemoteReference ref;
      if (KernelStatus.isServer()) {
         ref = this.chooseReplica(currentRef, method, params);
      } else if (isConnectionEstablished(currentRef)) {
         ref = currentRef;
      } else {
         ref = this.getReplicaWithEndPoint(currentRef, method, params);
         if (ref == null) {
            ref = this.chooseReplica(currentRef, method, params);
         }
      }

      return ref;
   }

   private RemoteReference chooseReplicaAfterFailureUsingAffinity(RemoteReference currentRef, Method method, Object[] params, RemoteException exception) {
      RemoteReference ref;
      if (KernelStatus.isServer()) {
         ref = this.chooseReplicaAfterFailure(currentRef, method, params, exception);
      } else {
         ref = this.getReplicaWithEndPoint(currentRef, method, params);
         if (ref == null) {
            ref = this.chooseReplicaAfterFailure(currentRef, method, params, exception);
         }
      }

      return ref;
   }

   protected static final void log(String s) {
      RMILogger.logDebug(s);
   }

   public String toString() {
      return this.replicaList.toString();
   }

   public BasicReplicaHandler() {
      this.current = 0;
      this.firstRequest = true;
      this.lastPreferredHost = null;
      this.isAffinityRequired = false;
      this.env = RMIEnvironment.getEnvironment().threadEnvironmentGet();
      if (this.env == null) {
         this.env = RMIEnvironment.getEnvironment().newEnvironment();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.current = this.current + 1 & Integer.MAX_VALUE;
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeInt(this.current);
         wlOut.writeObjectWL(this.replicaList);
         wlOut.writeBoolean(this.stickToFirstServer || this.isAffinityRequired);
         wlOut.writeObject(this.jndiName);
         wlOut.writeObject(this.callRouter);
         if (wlOut instanceof ColocatedStream) {
            wlOut.writeImmutable(this.env);
         }
      } else {
         out.writeInt(this.current);
         out.writeObject(this.replicaList);
         out.writeBoolean(this.stickToFirstServer || this.isAffinityRequired);
         out.writeObject(this.jndiName);
         out.writeObject(this.callRouter);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.current = wlIn.readInt();
         this.replicaList = (ReplicaList)wlIn.readObjectWL();
         this.stickToFirstServer = wlIn.readBoolean();
         this.jndiName = (String)wlIn.readObject();
         this.callRouter = (CallRouter)wlIn.readObject();
         if (wlIn instanceof ColocatedStream) {
            this.env = wlIn.readImmutable();
         }
      } else {
         this.current = in.readInt();
         this.replicaList = (ReplicaList)in.readObject();
         this.stickToFirstServer = in.readBoolean();
         this.jndiName = (String)in.readObject();
         this.callRouter = (CallRouter)in.readObject();
      }

   }
}
