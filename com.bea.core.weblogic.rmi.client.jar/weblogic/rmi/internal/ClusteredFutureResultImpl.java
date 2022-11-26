package weblogic.rmi.internal;

import java.lang.reflect.Constructor;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.FutureResult;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;

public class ClusteredFutureResultImpl extends ClusteredAsyncResultImpl implements Future, FutureResult {
   private Remote invoker;
   private final FutureResultID id;
   private HostID hostID;
   private ClusterableRemoteRef clusteredRef;
   private boolean cancelled;
   private ClusteredFutureResultRetryHelper helper;
   private Class remoteExceptionWrapperClass;
   private Constructor constructor;

   public ClusteredFutureResultImpl(Remote stub, HostID hostID, ClusterableRemoteRef clusteredRef) throws RemoteException {
      this.cancelled = false;
      this.invoker = stub;
      this.hostID = hostID;
      this.clusteredRef = clusteredRef;
      this.id = new FutureResultID((long)this.hashCode());
   }

   public ClusteredFutureResultImpl(Remote stub, HostID hostID, ClusterableRemoteRef clusteredRef, RuntimeMethodDescriptor md, ClusteredFutureResultRetryHelper helper) throws RemoteException {
      this.cancelled = false;
      this.invoker = stub;
      this.hostID = hostID;
      this.clusteredRef = clusteredRef;
      this.id = new FutureResultID((long)this.hashCode());
      this.helper = helper;
      String remoteExceptionWrapperName = md.getRemoteExceptionWrapperClassName();
      if (remoteExceptionWrapperName != null && !remoteExceptionWrapperName.equals("")) {
         try {
            this.remoteExceptionWrapperClass = Class.forName(remoteExceptionWrapperName);
            this.constructor = this.remoteExceptionWrapperClass.getConstructor(Throwable.class);
         } catch (ClassNotFoundException var8) {
         } catch (NoSuchMethodException var9) {
         }
      }

   }

   public ClusteredFutureResultRetryHelper getHelper() {
      return this.helper;
   }

   public ClusteredFutureResultImpl(Remote stub, HostID hostID, ClusterableRemoteRef clusteredRef, ClusteredFutureResultRetryHelper helper) throws RemoteException {
      this(stub, hostID, clusteredRef, helper.getMethodDescriptor(), helper);
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      if (!this.cancelled && !this.isDone()) {
         HostID prevHostID = weblogic.rmi.cluster.ThreadPreferredHost.get();
         weblogic.rmi.cluster.ThreadPreferredHost.set(this.hostID);

         try {
            this.cancelled = ((FutureResultHandle)this.invoker).__WL_cancel(this.id, mayInterruptIfRunning);
         } finally {
            weblogic.rmi.cluster.ThreadPreferredHost.set(prevHostID);
         }

         return this.cancelled;
      } else {
         return false;
      }
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public boolean isDone() {
      return this.cancelled || this.hasResults();
   }

   public synchronized boolean hasResults() {
      if (this.inboundResponse != null) {
         return true;
      } else if (this.throwable != null) {
         try {
            if (this.shouldRetry(this.throwable)) {
               this.retryRequest();
               return false;
            } else {
               return true;
            }
         } catch (Throwable var2) {
            return true;
         }
      } else {
         return false;
      }
   }

   private void retryRequest() throws Throwable {
      this.clusteredRef.invoke(this, this.invoker);
   }

   private boolean shouldRetry(Throwable t) throws Exception {
      return this.helper != null ? this.clusteredRef.handleException((Exception)t, this.helper) : false;
   }

   public Object get() throws InterruptedException, ExecutionException, CancellationException {
      try {
         return this.unWrappedFutureResult();
      } catch (Throwable var2) {
         if (var2 instanceof CancellationException) {
            throw (CancellationException)var2;
         } else if (var2 instanceof RemoteException) {
            throw (RuntimeException)this.throwUserDefinedException(var2, this.remoteExceptionWrapperClass, this.constructor);
         } else {
            throw new ExecutionException(var2);
         }
      }
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException, CancellationException {
      long timeoutVal = timeout;
      if (timeout > 0L) {
         timeoutVal = TimeUnit.MILLISECONDS.convert(timeout, unit);
      }

      this.setTimeOut(timeoutVal);

      try {
         return this.unWrappedFutureResult();
      } catch (Throwable var8) {
         if (var8 instanceof TimeoutException) {
            throw new TimeoutException("Timed out. Unable to retrieve object after " + timeoutVal + unit);
         } else if (var8 instanceof CancellationException) {
            throw (CancellationException)var8;
         } else if (var8 instanceof RemoteException) {
            Exception exception = (Exception)this.throwUserDefinedException(var8, this.remoteExceptionWrapperClass, this.constructor);
            if (exception instanceof TimeoutException) {
               throw (TimeoutException)exception;
            } else {
               throw (RuntimeException)exception;
            }
         } else {
            throw new ExecutionException(var8);
         }
      }
   }

   private Object unWrappedFutureResult() throws Throwable {
      if (this.cancelled) {
         throw new CancellationException("Results cannot be retrieved from a cancelled future!");
      } else {
         try {
            Future future = (Future)this.getObject();
            this.clusteredRef.setPiggybackResponse(this.getReplicaInfo());
            return future.get();
         } catch (Throwable var2) {
            if (this.shouldRetry(var2)) {
               this.retryRequest();
            }

            throw var2;
         }
      }
   }

   public FutureResultID getId() {
      return this.id;
   }

   public synchronized void setInboundResponse(InboundResponse inboundResponse) {
      super.setInboundResponse(inboundResponse);
      if (this.cancelled) {
         try {
            this.clusteredRef.setPiggybackResponse(this.getReplicaInfo());
         } catch (Throwable var3) {
         }
      }

   }

   public synchronized void setThrowable(Throwable throwable) {
      this.throwable = throwable;
   }

   public synchronized void updateClusterableRemoteRef(ClusterableRemoteRef crr) {
      this.clusteredRef = crr;
   }
}
