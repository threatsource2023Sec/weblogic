package org.apache.openjpa.util;

public final class DoubleId extends OpenJPAId {
   private final double key;

   public DoubleId(Class cls, Double key) {
      this(cls, key == null ? 0.0 : key);
   }

   public DoubleId(Class cls, String key) {
      this(cls, key == null ? 0.0 : Double.parseDouble(key));
   }

   public DoubleId(Class cls, double key) {
      super(cls);
      this.key = key;
   }

   public DoubleId(Class cls, double key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public double getId() {
      return this.key;
   }

   public Object getIdObject() {
      return new Double(this.key);
   }

   public String toString() {
      return Double.toString(this.key);
   }

   protected int idHash() {
      return (int)(Double.doubleToLongBits(this.key) ^ Double.doubleToLongBits(this.key) >>> 32);
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((DoubleId)o).key;
   }
}
