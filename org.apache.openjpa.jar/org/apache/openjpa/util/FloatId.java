package org.apache.openjpa.util;

public final class FloatId extends OpenJPAId {
   private final float key;

   public FloatId(Class cls, Float key) {
      this(cls, key == null ? 0.0F : key);
   }

   public FloatId(Class cls, String key) {
      this(cls, key == null ? 0.0F : Float.parseFloat(key));
   }

   public FloatId(Class cls, float key) {
      super(cls);
      this.key = key;
   }

   public FloatId(Class cls, float key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public float getId() {
      return this.key;
   }

   public Object getIdObject() {
      return new Float(this.key);
   }

   public String toString() {
      return Float.toString(this.key);
   }

   protected int idHash() {
      return Float.floatToIntBits(this.key);
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((FloatId)o).key;
   }
}
