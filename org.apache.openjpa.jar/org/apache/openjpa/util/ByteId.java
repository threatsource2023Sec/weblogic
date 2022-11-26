package org.apache.openjpa.util;

public final class ByteId extends OpenJPAId {
   private final byte key;

   public ByteId(Class cls, Byte key) {
      this(cls, key == null ? 0 : key);
   }

   public ByteId(Class cls, String key) {
      this(cls, key == null ? 0 : Byte.parseByte(key));
   }

   public ByteId(Class cls, byte key) {
      super(cls);
      this.key = key;
   }

   public ByteId(Class cls, byte key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public byte getId() {
      return this.key;
   }

   public Object getIdObject() {
      return new Byte(this.key);
   }

   public String toString() {
      return Byte.toString(this.key);
   }

   protected int idHash() {
      return this.key;
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((ByteId)o).key;
   }
}
