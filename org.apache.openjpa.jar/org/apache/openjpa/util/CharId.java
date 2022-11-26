package org.apache.openjpa.util;

public final class CharId extends OpenJPAId {
   private final char key;

   public CharId(Class cls, Character key) {
      this(cls, key == null ? '\u0000' : key);
   }

   public CharId(Class cls, String key) {
      this(cls, key == null ? '\u0000' : key.charAt(0));
   }

   public CharId(Class cls, char key) {
      super(cls);
      this.key = key;
   }

   public CharId(Class cls, char key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public char getId() {
      return this.key;
   }

   public Object getIdObject() {
      return new Character(this.key);
   }

   public String toString() {
      return String.valueOf(this.key);
   }

   protected int idHash() {
      return this.key;
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((CharId)o).key;
   }
}
