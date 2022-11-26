package org.python.google.common.collect;

import java.util.concurrent.ConcurrentMap;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingConcurrentMap extends ForwardingMap implements ConcurrentMap {
   protected ForwardingConcurrentMap() {
   }

   protected abstract ConcurrentMap delegate();

   @CanIgnoreReturnValue
   public Object putIfAbsent(Object key, Object value) {
      return this.delegate().putIfAbsent(key, value);
   }

   @CanIgnoreReturnValue
   public boolean remove(Object key, Object value) {
      return this.delegate().remove(key, value);
   }

   @CanIgnoreReturnValue
   public Object replace(Object key, Object value) {
      return this.delegate().replace(key, value);
   }

   @CanIgnoreReturnValue
   public boolean replace(Object key, Object oldValue, Object newValue) {
      return this.delegate().replace(key, oldValue, newValue);
   }
}
