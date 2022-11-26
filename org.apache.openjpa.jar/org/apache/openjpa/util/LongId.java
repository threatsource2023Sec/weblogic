package org.apache.openjpa.util;

import serp.util.Numbers;

public final class LongId extends OpenJPAId {
   private final long key;

   public LongId(Class cls, Long key) {
      this(cls, key == null ? 0L : key);
   }

   public LongId(Class cls, String key) {
      this(cls, key == null ? 0L : Long.parseLong(key));
   }

   public LongId(Class cls, long key) {
      super(cls);
      this.key = key;
   }

   public LongId(Class cls, long key, boolean subs) {
      super(cls, subs);
      this.key = key;
   }

   public long getId() {
      return this.key;
   }

   public Object getIdObject() {
      return Numbers.valueOf(this.key);
   }

   protected int idHash() {
      return (int)(this.key ^ this.key >>> 32);
   }

   protected boolean idEquals(OpenJPAId o) {
      return this.key == ((LongId)o).key;
   }
}
