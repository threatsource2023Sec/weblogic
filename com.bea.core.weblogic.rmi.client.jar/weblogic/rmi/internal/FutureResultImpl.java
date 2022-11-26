package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.FutureResult;
import weblogic.rmi.spi.InboundResponse;

public final class FutureResultImpl extends AsyncResultImpl implements Future, FutureResult {
   private Remote invoker;
   private final FutureResultID id;
   private boolean cancelled;
   private Class remoteExceptionWrapperClass;
   private Constructor constructor;
   private static final int NUM_BITS = 32;

   public FutureResultImpl(Remote stub) throws RemoteException {
      this.invoker = stub;
      this.id = new FutureResultID(this.getUniqueID());
   }

   public FutureResultImpl(Remote stub, RuntimeMethodDescriptor md) throws RemoteException {
      this.invoker = stub;
      this.id = new FutureResultID((long)this.hashCode());
      String remoteExceptionWrapperName = md.getRemoteExceptionWrapperClassName();

      try {
         this.remoteExceptionWrapperClass = Class.forName(remoteExceptionWrapperName);
         this.constructor = this.remoteExceptionWrapperClass.getConstructor(Exception.class);
      } catch (NoSuchMethodException | ClassNotFoundException var5) {
         throw new RemoteException("Creation of FutureResult failed.", var5);
      }
   }

   public synchronized boolean cancel(boolean mayInterruptIfRunning) {
      if (!this.cancelled && !this.isDone()) {
         this.cancelled = ((FutureResultHandle)this.invoker).__WL_cancel(this.id, mayInterruptIfRunning);
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
      if (unit != TimeUnit.MILLISECONDS) {
         timeoutVal = TimeUnit.SECONDS.convert(timeout, TimeUnit.MILLISECONDS);
      }

      this.setTimeOut(timeoutVal);

      try {
         return this.unWrappedFutureResult();
      } catch (Throwable var7) {
         if (!(var7 instanceof TimeoutException) && !(var7 instanceof RequestTimeoutException)) {
            if (var7 instanceof CancellationException) {
               throw (CancellationException)var7;
            } else if (var7 instanceof RemoteException) {
               throw (RuntimeException)this.throwUserDefinedException(var7, this.remoteExceptionWrapperClass, this.constructor);
            } else {
               throw new ExecutionException(var7);
            }
         } else {
            throw new TimeoutException("Timed out. Unable to retrieve object after " + timeoutVal + " milliseconds.");
         }
      }
   }

   private Object unWrappedFutureResult() throws Throwable {
      if (this.cancelled) {
         throw new CancellationException("This task has been cancelled already!");
      } else {
         Future future = (Future)this.getObject();
         return future.get();
      }
   }

   public FutureResultID getId() {
      return this.id;
   }

   public synchronized void setInboundResponse(InboundResponse inboundResponse) {
      super.setInboundResponse(inboundResponse);
      if (this.cancelled) {
         try {
            this.closeResponse();
         } catch (IOException var3) {
         }
      }

   }

   private long getUniqueID() {
      return System.nanoTime() << 32 | (long)(this.hashCode() & 2 ^ 31);
   }
}
