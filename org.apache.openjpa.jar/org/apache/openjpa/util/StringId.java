package org.apache.openjpa.util;

public final class StringId extends OpenJPAId {
   private final String key;

   public StringId(Class cls, String key) {
      super(cls);
      this.key = key == null ? "" : key;
   }

   public StringId(Class cls, String key, boolean subs) {
      super(cls, subs);
      this.key = key == null ? "" : key;
   }

   public String getId() {
      return this.key;
   }

   public Object getIdObject() {
      return this.key;
   }

   public String toString() {
      return this.key;
   }

   protected int idHash() {
      return this.key.hashCode();
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key.equals(((StringId)o).key);
   }
}
