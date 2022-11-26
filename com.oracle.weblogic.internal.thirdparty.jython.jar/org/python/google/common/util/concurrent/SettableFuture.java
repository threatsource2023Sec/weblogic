package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public final class SettableFuture extends AbstractFuture.TrustedFuture {
   public static SettableFuture create() {
      return new SettableFuture();
   }

   @CanIgnoreReturnValue
   public boolean set(@Nullable Object value) {
      return super.set(value);
   }

   @CanIgnoreReturnValue
   public boolean setException(Throwable throwable) {
      return super.setException(throwable);
   }

   @Beta
   @CanIgnoreReturnValue
   public boolean setFuture(ListenableFuture future) {
      return super.setFuture(future);
   }

   private SettableFuture() {
   }
}
