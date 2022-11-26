package org.python.google.common.collect;

import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible
public abstract class ForwardingSet extends ForwardingCollection implements Set {
   protected ForwardingSet() {
   }

   protected abstract Set delegate();

   public boolean equals(@Nullable Object object) {
      return object == this || this.delegate().equals(object);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   protected boolean standardRemoveAll(Collection collection) {
      return Sets.removeAllImpl(this, (Collection)((Collection)Preconditions.checkNotNull(collection)));
   }

   protected boolean standardEquals(@Nullable Object object) {
      return Sets.equalsImpl(this, object);
   }

   protected int standardHashCode() {
      return Sets.hashCodeImpl(this);
   }
}
