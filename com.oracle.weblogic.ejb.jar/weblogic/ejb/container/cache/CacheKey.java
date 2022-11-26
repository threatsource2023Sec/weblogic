package weblogic.ejb.container.cache;

import weblogic.ejb.container.interfaces.CachingManager;

public final class CacheKey {
   private final Object pk;
   private final CachingManager callback;
   private final int hashCode;

   public CacheKey(Object pk, CachingManager callback) {
      assert pk != null;

      assert callback != null;

      this.pk = pk;
      this.callback = callback;
      this.hashCode = pk.hashCode() ^ callback.hashCode();
   }

   private static boolean eq(Object a, Object b) {
      return a == b || a.equals(b);
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CacheKey)) {
         return false;
      } else {
         CacheKey other = (CacheKey)o;
         return this.hashCode == other.hashCode && eq(this.pk, other.pk) && eq(this.callback, other.callback);
      }
   }

   public Object getPrimaryKey() {
      return this.pk;
   }

   public CachingManager getCallback() {
      return this.callback;
   }

   public String toString() {
      return "(" + this.pk + ", " + this.callback + ")";
   }
}
