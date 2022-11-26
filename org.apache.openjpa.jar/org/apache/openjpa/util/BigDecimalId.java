package org.apache.openjpa.util;

import java.math.BigDecimal;

public class BigDecimalId extends OpenJPAId {
   private final BigDecimal key;

   public BigDecimalId(Class cls, String key) {
      this(cls, key == null ? null : new BigDecimal(key));
   }

   public BigDecimalId(Class cls, BigDecimal key) {
      super(cls);
      this.key = key;
   }

   public BigDecimalId(Class cls, BigDecimal key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public BigDecimal getId() {
      return this.key;
   }

   public Object getIdObject() {
      return this.key;
   }

   public String toString() {
      return this.key == null ? "NULL" : this.key.toString();
   }

   protected int idHash() {
      return this.key != null ? this.key.hashCode() : 0;
   }

   protected boolean idEquals(OpenJPAId other) {
      return this.key == null ? false : this.key.equals(other);
   }
}
