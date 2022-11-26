package weblogic.xml.util.cache.entitycache;

public class CX {
   public static class EntityCacheInternalException extends EntityCacheException {
      private static final long serialVersionUID = -2418110382655656920L;

      public EntityCacheInternalException() {
      }

      public EntityCacheInternalException(String message) {
         super("Property Exception: " + message);
      }

      public EntityCacheInternalException(Throwable prev) {
         this.previous = prev;
      }

      public EntityCacheInternalException(String message, Throwable prev) {
         super(message);
         this.previous = prev;
      }

      public Throwable getPrevious() {
         return this.previous;
      }
   }

   public static class FileLoadOutOfMemory extends FileLoad {
      private static final long serialVersionUID = 4798316024393810013L;

      public FileLoadOutOfMemory(String pathName, Throwable prev) {
         super("Error loading file: " + pathName, prev);
      }
   }

   public static class FileLoad extends EntityCacheException {
      private static final long serialVersionUID = -6088499391731515245L;

      public FileLoad(String pathName, Throwable prev) {
         super("Error loading file: " + pathName, prev);
      }
   }

   public static class UnnamedCache extends EntityCacheException {
      private static final long serialVersionUID = -7722783160384285313L;

      public UnnamedCache() {
         this((Throwable)null);
      }

      public UnnamedCache(Throwable prev) {
         super("New cache must be provided with a name.", prev);
      }
   }

   public static class EntryTooLargeDisk extends EntryTooLarge {
      private static final long serialVersionUID = 8758558572896661571L;

      public EntryTooLargeDisk(CacheEntry ce, long max) {
         this(ce, max, (Throwable)null);
      }

      public EntryTooLargeDisk(CacheEntry ce, long max, Throwable prev) {
         super(ce, ce.getDiskSize(), max, "disk", prev);
      }
   }

   public static class EntryTooLargeMemory extends EntryTooLarge {
      private static final long serialVersionUID = 1895054273049911015L;

      public EntryTooLargeMemory(CacheEntry ce, long max) {
         this(ce, max, (Throwable)null);
      }

      public EntryTooLargeMemory(CacheEntry ce, long max, Throwable prev) {
         super(ce, ce.getSize(), max, "memory", prev);
      }
   }

   public abstract static class EntryTooLarge extends EntityCacheException {
      private static final long serialVersionUID = -1455918258626459255L;
      CacheEntry cacheEntry;

      EntryTooLarge(CacheEntry ce, long size, long max, String type, Throwable prev) {
         super("The " + type + " size (" + size + ") of CacheEntry " + ce.getKey() + " is larger than the maximum cache size: " + max, prev);
      }
   }

   public static class EntryExpired extends EntityCacheException {
      private static final long serialVersionUID = 6195032839751825607L;
      public CacheEntry cacheEntry;

      public void renewLease() throws EntityCacheException {
         this.cacheEntry.renewLease();
      }

      public void renewLease(long leaseInterval) throws EntityCacheException {
         this.cacheEntry.renewLease(leaseInterval);
      }

      public EntryExpired(CacheEntry ce) {
         this(ce, (Throwable)null);
      }

      public EntryExpired(CacheEntry ce, Throwable prev) {
         super("CacheEntry " + ce.getKey() + " has expired.", prev);
         this.cacheEntry = ce;
      }
   }

   public static class EntityCacheException extends Exception implements Tools.ILinkableException {
      private static final long serialVersionUID = -2146832826767767621L;
      Throwable previous = null;

      public EntityCacheException() {
      }

      public EntityCacheException(String message) {
         super("Property Exception: " + message);
      }

      public EntityCacheException(Throwable prev) {
         this.previous = prev;
      }

      public EntityCacheException(String message, Throwable prev) {
         super(message);
         this.previous = prev;
      }

      public Throwable getPrevious() {
         return this.previous;
      }
   }
}
