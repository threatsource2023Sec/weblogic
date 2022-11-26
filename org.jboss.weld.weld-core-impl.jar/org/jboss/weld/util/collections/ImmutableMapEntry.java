package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.util.Preconditions;

class ImmutableMapEntry extends ImmutableMap implements Map.Entry, Serializable {
   private static final long serialVersionUID = 1L;
   private final Object key;
   private final Object value;
   private transient volatile Set entrySet;
   private transient volatile Set keySet;
   private transient volatile Collection values;

   ImmutableMapEntry(Map.Entry entry) {
      this(entry.getKey(), entry.getValue());
   }

   ImmutableMapEntry(Object key, Object value) {
      Preconditions.checkNotNull(key);
      Preconditions.checkNotNull(value);
      this.key = key;
      this.value = value;
   }

   public int size() {
      return 1;
   }

   public Set entrySet() {
      if (this.entrySet == null) {
         this.entrySet = new ImmutableTinySet.Singleton(this);
      }

      return this.entrySet;
   }

   public Set keySet() {
      if (this.keySet == null) {
         this.keySet = new ImmutableTinySet.Singleton(this.key);
      }

      return this.keySet;
   }

   public Collection values() {
      if (this.values == null) {
         this.values = new ImmutableTinySet.Singleton(this.value);
      }

      return this.values;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      return this.value;
   }

   public Object setValue(Object value) {
      throw new UnsupportedOperationException();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Map.Entry)) {
         if (o instanceof Map) {
            Map map = (Map)o;
            return map.size() != 1 ? false : this.value.equals(map.get(this.key));
         } else {
            return false;
         }
      } else {
         Map.Entry entry = (Map.Entry)o;
         return this.key.equals(entry.getKey()) && this.value.equals(entry.getValue());
      }
   }

   public int hashCode() {
      return this.key.hashCode() ^ this.value.hashCode();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.key == this ? "(this Map)" : this.key);
      sb.append('=');
      sb.append(this.value == this ? "(this Map)" : this.value);
      sb.append('}');
      return sb.toString();
   }
}
