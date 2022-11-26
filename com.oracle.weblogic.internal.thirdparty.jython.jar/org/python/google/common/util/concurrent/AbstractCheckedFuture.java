package org.python.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

/** @deprecated */
@Deprecated
@Beta
@GwtIncompatible
public abstract class AbstractCheckedFuture extends ForwardingListenableFuture.SimpleForwardingListenableFuture implements CheckedFuture {
   protected AbstractCheckedFuture(ListenableFuture delegate) {
      super(delegate);
   }

   protected abstract Exception mapException(Exception var1);

   @CanIgnoreReturnValue
   public Object checkedGet() throws Exception {
      try {
         return this.get();
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         throw this.mapException(var2);
      } catch (CancellationException var3) {
         throw this.mapException(var3);
      } catch (ExecutionException var4) {
         throw this.mapException(var4);
      }
   }

   @CanIgnoreReturnValue
   public Object checkedGet(long timeout, TimeUnit unit) throws TimeoutException, Exception {
      try {
         return this.get(timeout, unit);
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         throw this.mapException(var5);
      } catch (CancellationException var6) {
         throw this.mapException(var6);
      } catch (ExecutionException var7) {
         throw this.mapException(var7);
      }
   }
}
