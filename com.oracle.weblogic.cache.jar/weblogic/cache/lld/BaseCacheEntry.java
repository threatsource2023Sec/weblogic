package weblogic.cache.lld;

public class BaseCacheEntry implements CacheEntry {
   protected final Object key;
   protected Object value;
   protected final long createTime;
   protected long lastAccessTime;
   protected long lastUpdateTime;
   protected long ttl;
   protected boolean discarded;

   public BaseCacheEntry(Object key, Object value) {
      this(key, value, -1L);
   }

   public BaseCacheEntry(Object key, Object value, long ttl) {
      this.key = key;
      this.value = value;
      this.createTime = this.lastUpdateTime = this.lastAccessTime = System.currentTimeMillis();
      this.ttl = ttl;
   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      this.lastAccessTime = System.currentTimeMillis();
      return this.value;
   }

   public Object setValue(Object o) {
      this.lastUpdateTime = this.lastAccessTime = System.currentTimeMillis();
      Object old = this.value;
      this.value = o;
      return old;
   }

   public long expiration() {
      return this.lastUpdateTime + this.ttl * 1000L;
   }

   public boolean expired() {
      if (this.ttl < 0L) {
         return false;
      } else {
         return System.currentTimeMillis() > this.expiration();
      }
   }

   public long getCreationTime() {
      return this.createTime;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public long getLastUpdateTime() {
      return this.lastUpdateTime;
   }

   public boolean isDiscarded() {
      return this.discarded;
   }

   public void touch() {
      this.lastAccessTime = System.currentTimeMillis();
   }

   public void discard() {
      this.discarded = true;
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof BaseCacheEntry) {
         boolean var10000;
         label38: {
            label27: {
               BaseCacheEntry other = (BaseCacheEntry)o;
               if (this.key == null) {
                  if (other.getKey() != null) {
                     break label27;
                  }
               } else if (!this.key.equals(other.getKey())) {
                  break label27;
               }

               if (this.value == null) {
                  if (other.getValue() == null) {
                     break label38;
                  }
               } else if (this.value.equals(other.getValue())) {
                  break label38;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
   }

   public String toString() {
      return super.toString() + "|key:" + this.key.toString() + "|value:" + this.value.toString();
   }
}
