package weblogic.management.internal;

import java.io.Serializable;

public final class Pair implements Serializable {
   static final long serialVersionUID = 1L;
   Object key;
   Object value;
   private int hashCode;

   public Pair(Object k, Object v) {
      this.key = k;
      this.value = v;
   }

   public final String getName() {
      return this.key.toString();
   }

   public final Object getKey() {
      return this.key;
   }

   public final Object getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.hashCode != 0 ? this.hashCode : (this.hashCode = (this.value != null ? this.value.hashCode() : 0) ^ (this.key != null ? this.key.hashCode() : 0));
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof Pair)) {
         return false;
      } else {
         Pair pair = (Pair)obj;
         if (this.value != null && this.key != null) {
            return this.value.equals(pair.value) && this.key.equals(pair.key);
         } else if (this.value == null && this.key != null) {
            return pair.value == null && this.key.equals(pair.key);
         } else if (this.value != null && this.key == null) {
            return pair.key == null && this.value.equals(pair.value);
         } else if (this.value == null && this.key == null) {
            return pair.key == null && pair.value == null;
         } else {
            return false;
         }
      }
   }
}
