package org.apache.openjpa.util;

public final class ShortId extends OpenJPAId {
   private final short key;

   public ShortId(Class cls, Short key) {
      this(cls, key == null ? 0 : key);
   }

   public ShortId(Class cls, String key) {
      this(cls, key == null ? 0 : Short.parseShort(key));
   }

   public ShortId(Class cls, short key) {
      super(cls);
      this.key = key;
   }

   public ShortId(Class cls, short key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public short getId() {
      return this.key;
   }

   public Object getIdObject() {
      return new Short(this.key);
   }

   public String toString() {
      return Short.toString(this.key);
   }

   protected int idHash() {
      return this.key;
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((ShortId)o).key;
   }
}
