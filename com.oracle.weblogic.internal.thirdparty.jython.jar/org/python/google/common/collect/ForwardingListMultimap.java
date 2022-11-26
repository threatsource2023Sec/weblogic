package org.python.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingListMultimap extends ForwardingMultimap implements ListMultimap {
   protected ForwardingListMultimap() {
   }

   protected abstract ListMultimap delegate();

   public List get(@Nullable Object key) {
      return this.delegate().get(key);
   }

   @CanIgnoreReturnValue
   public List removeAll(@Nullable Object key) {
      return this.delegate().removeAll(key);
   }

   @CanIgnoreReturnValue
   public List replaceValues(Object key, Iterable values) {
      return this.delegate().replaceValues(key, values);
   }
}
