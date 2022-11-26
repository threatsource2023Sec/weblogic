package org.apache.openjpa.util;

import serp.util.Numbers;

public final class IntId extends OpenJPAId {
   private final int key;

   public IntId(Class cls, Integer key) {
      this(cls, key == null ? 0 : key);
   }

   public IntId(Class cls, String key) {
      this(cls, key == null ? 0 : Integer.parseInt(key));
   }

   public IntId(Class cls, int key) {
      super(cls);
      this.key = key;
   }

   public IntId(Class cls, int key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public int getId() {
      return this.key;
   }

   public Object getIdObject() {
      return Numbers.valueOf(this.key);
   }

   public String toString() {
      return String.valueOf(this.key);
   }

   protected int idHash() {
      return this.key;
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((IntId)o).key;
   }
}
