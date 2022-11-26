package org.apache.openjpa.util;

import java.math.BigInteger;

public class BigIntegerId extends OpenJPAId {
   private final BigInteger key;

   public BigIntegerId(Class cls, String key) {
      this(cls, key == null ? null : new BigInteger(key));
   }

   public BigIntegerId(Class cls, BigInteger key) {
      super(cls);
      this.key = key;
   }

   public BigIntegerId(Class cls, BigInteger key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public BigInteger getId() {
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
