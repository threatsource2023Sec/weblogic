package org.python.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Objects;

@GwtCompatible
public abstract class ForwardingMapEntry extends ForwardingObject implements Map.Entry {
   protected ForwardingMapEntry() {
   }

   protected abstract Map.Entry delegate();

   public Object getKey() {
      return this.delegate().getKey();
   }

   public Object getValue() {
      return this.delegate().getValue();
   }

   public Object setValue(Object value) {
      return this.delegate().setValue(value);
   }

   public boolean equals(@Nullable Object object) {
      return this.delegate().equals(object);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   protected boolean standardEquals(@Nullable Object object) {
      if (!(object instanceof Map.Entry)) {
         return false;
      } else {
         Map.Entry that = (Map.Entry)object;
         return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
      }
   }

   protected int standardHashCode() {
      Object k = this.getKey();
      Object v = this.getValue();
      return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
   }

   @Beta
   protected String standardToString() {
      return this.getKey() + "=" + this.getValue();
   }
}
