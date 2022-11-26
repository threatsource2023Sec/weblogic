package weblogic.cache.util;

import weblogic.cache.CacheEntry;
import weblogic.cache.locks.RWLockImpl;

public class BaseCacheEntry extends RWLockImpl implements CacheEntry {
   protected final Object key;
   protected Object value;
   protected final Version version;
   protected final long createTime;
   protected long lastAccessTime;
   protected long lastUpdateTime;
   protected long idleTime;
   protected long hardExpiration;
   protected boolean discarded;

   public BaseCacheEntry(Object key, Object value, long idleTime, long ttl) {
      this.key = key;
      this.value = value;
      this.createTime = this.lastUpdateTime = this.lastAccessTime = System.currentTimeMillis();
      this.setIdleTime(idleTime);
      this.setTTL(ttl);
      this.version = new Version();
   }

   public BaseCacheEntry(CacheEntry entry, long idleTime, long ttl) {
      this.key = entry.getKey();
      this.value = entry.getValue();
      this.createTime = entry.getCreationTime();
      this.lastUpdateTime = entry.getLastUpdateTime();
      this.lastAccessTime = entry.getLastAccessTime();
      this.setIdleTime(idleTime);
      this.setTTL(ttl);
      this.version = new Version();
      this.version.set(entry.getVersion());
   }

   public void setIdleTime(long idleTime) {
      this.idleTime = idleTime;
   }

   public void setTTL(long ttl) {
      if (ttl == 0L) {
         this.hardExpiration = Long.MAX_VALUE;
      } else {
         long proposedHardExpiration = this.createTime + ttl;
         if (ttl > 0L && proposedHardExpiration < this.createTime) {
            this.hardExpiration = Long.MAX_VALUE;
         } else {
            this.hardExpiration = proposedHardExpiration;
         }
      }

   }

   public Object getKey() {
      return this.key;
   }

   public Object getValue() {
      if (!this.discarded) {
         this.touch();
      }

      return this.value;
   }

   public Object setValue(Object o) {
      if (!this.discarded) {
         this.touch();
         this.lastUpdateTime = this.lastAccessTime;
         this.version.increment();
      }

      Object old = this.value;
      this.value = o;
      return old;
   }

   public long getExpirationTime() {
      return Math.min(this.idleTime == 0L ? Long.MAX_VALUE : this.lastAccessTime + this.idleTime, this.hardExpiration);
   }

   public void setVersion(long ver) {
      this.version.set(ver);
   }

   public long getVersion() {
      return this.version.get();
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

   public void restore() {
      this.discarded = false;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o != null && o instanceof BaseCacheEntry) {
         boolean var10000;
         label47: {
            BaseCacheEntry other = (BaseCacheEntry)o;
            if (this.key == null) {
               if (other.getKey() != null) {
                  break label47;
               }
            } else if (!this.key.equals(other.getKey())) {
               break label47;
            }

            if (this.value == null) {
               if (other.getValue() != null) {
                  break label47;
               }
            } else if (!this.value.equals(other.getValue()) || this.version.get() != other.version.get()) {
               break label47;
            }

            var10000 = true;
            return var10000;
         }

         var10000 = false;
         return var10000;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode()) ^ this.version.hashCode();
   }

   public String toString() {
      return super.toString() + "|key:" + this.key + "|value:" + this.value + "|version:" + this.version.get();
   }
}
