package org.python.google.common.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

/** @deprecated */
@Deprecated
@Beta
@GwtIncompatible
public abstract class ForwardingCheckedFuture extends ForwardingListenableFuture implements CheckedFuture {
   @CanIgnoreReturnValue
   public Object checkedGet() throws Exception {
      return this.delegate().checkedGet();
   }

   @CanIgnoreReturnValue
   public Object checkedGet(long timeout, TimeUnit unit) throws TimeoutException, Exception {
      return this.delegate().checkedGet(timeout, unit);
   }

   protected abstract CheckedFuture delegate();

   /** @deprecated */
   @Deprecated
   @Beta
   public abstract static class SimpleForwardingCheckedFuture extends ForwardingCheckedFuture {
      private final CheckedFuture delegate;

      protected SimpleForwardingCheckedFuture(CheckedFuture delegate) {
         this.delegate = (CheckedFuture)Preconditions.checkNotNull(delegate);
      }

      protected final CheckedFuture delegate() {
         return this.delegate;
      }
   }
}
