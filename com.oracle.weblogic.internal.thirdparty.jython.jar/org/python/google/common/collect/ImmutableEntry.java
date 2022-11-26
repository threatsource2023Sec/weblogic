package org.python.google.common.collect;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true
)
class ImmutableEntry extends AbstractMapEntry implements Serializable {
   final Object key;
   final Object value;
   private static final long serialVersionUID = 0L;

   ImmutableEntry(@Nullable Object key, @Nullable Object value) {
      this.key = key;
      this.value = value;
   }

   @Nullable
   public final Object getKey() {
      return this.key;
   }

   @Nullable
   public final Object getValue() {
      return this.value;
   }

   public final Object setValue(Object value) {
      throw new UnsupportedOperationException();
   }
}
