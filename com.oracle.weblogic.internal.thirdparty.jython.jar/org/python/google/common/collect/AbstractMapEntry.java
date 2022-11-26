package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;

@GwtCompatible
abstract class AbstractMapEntry implements Map.Entry {
   public abstract Object getKey();

   public abstract Object getValue();

   public Object setValue(Object value) {
      throw new UnsupportedOperationException();
   }

   public boolean equals(@Nullable Object object) {
      if (!(object instanceof Map.Entry)) {
         return false;
      } else {
         Map.Entry that = (Map.Entry)object;
         return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
      }
   }

   public int hashCode() {
      Object k = this.getKey();
      Object v = this.getValue();
      return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
   }

   public String toString() {
      return this.getKey() + "=" + this.getValue();
   }
}
