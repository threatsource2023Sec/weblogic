package org.python.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public abstract class ForwardingSetMultimap extends ForwardingMultimap implements SetMultimap {
   protected abstract SetMultimap delegate();

   public Set entries() {
      return this.delegate().entries();
   }

   public Set get(@Nullable Object key) {
      return this.delegate().get(key);
   }

   @CanIgnoreReturnValue
   public Set removeAll(@Nullable Object key) {
      return this.delegate().removeAll(key);
   }

   @CanIgnoreReturnValue
   public Set replaceValues(Object key, Iterable values) {
      return this.delegate().replaceValues(key, values);
   }
}
