package org.python.google.common.cache;

import java.util.AbstractMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
public final class RemovalNotification extends AbstractMap.SimpleImmutableEntry {
   private final RemovalCause cause;
   private static final long serialVersionUID = 0L;

   public static RemovalNotification create(@Nullable Object key, @Nullable Object value, RemovalCause cause) {
      return new RemovalNotification(key, value, cause);
   }

   private RemovalNotification(@Nullable Object key, @Nullable Object value, RemovalCause cause) {
      super(key, value);
      this.cause = (RemovalCause)Preconditions.checkNotNull(cause);
   }

   public RemovalCause getCause() {
      return this.cause;
   }

   public boolean wasEvicted() {
      return this.cause.wasEvicted();
   }
}
