package weblogic.xml.util.cache.entitycache;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class Event {
   public static class CacheFailureEvent extends CacheEvent {
      public String path;
      public String message;

      CacheFailureEvent(EntityCache cache, String extendedPath, String message) throws CX.EntityCacheException {
         super(cache);
         this.path = extendedPath;
         this.message = message;
      }
   }

   public static class OutOfMemoryLoadingStatisticsEvent extends OutOfMemoryOnLoadEvent {
      OutOfMemoryLoadingStatisticsEvent(EntityCache cache, String path) throws CX.EntityCacheException {
         super(cache, path);
      }
   }

   public static class OutOfMemoryLoadingCacheEvent extends OutOfMemoryOnLoadEvent {
      OutOfMemoryLoadingCacheEvent(String path) throws CX.EntityCacheException {
         super((EntityCache)null, path);
      }
   }

   public static class OutOfMemoryLoadingEntryEvent extends OutOfMemoryOnLoadEvent {
      public Serializable key;

      OutOfMemoryLoadingEntryEvent(EntityCache cache, Serializable key, String path) throws CX.EntityCacheException {
         super(cache, path);
         this.key = key;
      }
   }

   public abstract static class OutOfMemoryOnLoadEvent extends OutOfMemoryEvent {
      public String path;

      OutOfMemoryOnLoadEvent(EntityCache cache, String path) throws CX.EntityCacheException {
         super(cache);
         this.path = path;
      }
   }

   public abstract static class OutOfMemoryEvent extends CacheEvent {
      OutOfMemoryEvent(EntityCache cache) throws CX.EntityCacheException {
         super(cache);
      }
   }

   public static class FileAccessErrorForStatisticsEvent extends FileAccessErrorEvent {
      FileAccessErrorForStatisticsEvent(EntityCache cache, String path, boolean onWrite) throws CX.EntityCacheException {
         super(cache, path, onWrite);
      }
   }

   public static class FileAccessErrorForCacheEvent extends FileAccessErrorEvent {
      FileAccessErrorForCacheEvent(EntityCache cache, String path, boolean onWrite) throws CX.EntityCacheException {
         super(cache, path, onWrite);
      }
   }

   public static class FileAccessErrorForEntryEvent extends FileAccessErrorEvent {
      public CacheEntry cacheEntry;

      FileAccessErrorForEntryEvent(EntityCache cache, CacheEntry entry, String path, boolean onWrite) throws CX.EntityCacheException {
         super(cache, path, onWrite);
         this.cacheEntry = entry;
      }
   }

   public abstract static class FileAccessErrorEvent extends FileErrorEvent {
      public boolean onRead;
      public boolean onWrite;

      FileAccessErrorEvent(EntityCache cache, String path, boolean onWrite) throws CX.EntityCacheException {
         super(cache, path);
         this.onWrite = onWrite;
         this.onRead = !onWrite;
      }
   }

   public abstract static class FileErrorEvent extends CacheEvent {
      public String path;

      FileErrorEvent(EntityCache cache, String path) throws CX.EntityCacheException {
         super(cache);
         this.path = path;
      }
   }

   public static class StatisticsCorruptionEvent extends CorruptionEvent {
      StatisticsCorruptionEvent(EntityCache c, String path) throws CX.EntityCacheException {
         super(c, path);
         this.cache = c;
      }
   }

   public static class EntryCorruptionEvent extends CorruptionEvent {
      public Serializable key;

      EntryCorruptionEvent(EntityCache c, Serializable key, String path) throws CX.EntityCacheException {
         super(c, path);
         this.key = key;
         this.cache = c;
      }
   }

   public static class CacheCorruptionEvent extends CorruptionEvent {
      CacheCorruptionEvent(String path) throws CX.EntityCacheException {
         super((EntityCache)null, path);
      }
   }

   public abstract static class CorruptionEvent extends CacheEvent {
      public String path;

      CorruptionEvent(EntityCache c, String path) throws CX.EntityCacheException {
         super(c);
         this.path = path;
      }
   }

   public static class CacheCloseEvent extends CacheEvent {
      CacheCloseEvent(EntityCache c) throws CX.EntityCacheException {
         super(c);
      }
   }

   public static class CacheLoadEvent extends CacheEvent {
      CacheLoadEvent(EntityCache c) throws CX.EntityCacheException {
         super(c);
      }
   }

   public static class CacheCreationEvent extends CacheEvent {
      CacheCreationEvent(EntityCache c) throws CX.EntityCacheException {
         super(c);
      }
   }

   public static class StatCheckpointEvent extends CacheEvent {
      StatCheckpointEvent(EntityCache c) throws CX.EntityCacheException {
         super(c);
      }
   }

   public static class EntryLoadEvent extends SingleEntryEvent {
      EntryLoadEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public static class EntryPersistEvent extends SingleEntryEvent {
      EntryPersistEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public static class EntryDeleteEvent extends SingleEntryEvent {
      EntryDeleteEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public static class EntryAddEvent extends SingleEntryEvent {
      EntryAddEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public static class EntryDiskRejectionEvent extends SingleEntryEvent {
      EntryDiskRejectionEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public static class EntryRejectionEvent extends SingleEntryEvent {
      EntryRejectionEvent(EntityCache c, CacheEntry ce) throws CX.EntityCacheException {
         super(c, ce);
      }
   }

   public abstract static class SingleEntryEvent extends CacheEvent {
      public CacheEntry cacheEntry = null;
      public long memorySize = -1L;
      public long diskSize = -1L;
      public long secondsUntilExpiration = -1L;

      SingleEntryEvent(EntityCache c, CacheEntry cacheEntry) throws CX.EntityCacheException {
         super(c);
         this.cacheEntry = cacheEntry;
         this.memorySize = cacheEntry.getMemorySize();
         this.diskSize = cacheEntry.getDiskSize();
         this.secondsUntilExpiration = cacheEntry.getSecondsUntilExpiration();
      }
   }

   public static class DiskPurgeEvent extends MultipleEntryEvent {
      DiskPurgeEvent(EntityCache c, Vector toPurge) throws CX.EntityCacheException {
         super(c, toPurge);
      }
   }

   public static class MemoryPurgeEvent extends MultipleEntryEvent {
      MemoryPurgeEvent(EntityCache c, Vector toPurge) throws CX.EntityCacheException {
         super(c, toPurge);
      }
   }

   public abstract static class MultipleEntryEvent extends CacheEvent {
      public Vector cacheEntries;
      public long combinedMemorySize = 0L;
      public long combinedDiskSize = 0L;

      MultipleEntryEvent(EntityCache c, Vector cacheEntries) throws CX.EntityCacheException {
         super(c);
         this.cacheEntries = cacheEntries;

         CacheEntry ce;
         for(Enumeration e = cacheEntries.elements(); e.hasMoreElements(); this.combinedDiskSize += ce.getDiskSize()) {
            ce = (CacheEntry)e.nextElement();
            this.combinedMemorySize += ce.getMemorySize();
         }

      }
   }

   public abstract static class CacheEvent extends CacheUtilityEvent {
      public EntityCache cache = null;
      public long currentMemorySize;
      public long currentDiskSize;

      CacheEvent(EntityCache cache) throws CX.EntityCacheException {
         this.cache = cache;
         if (cache != null) {
            this.currentMemorySize = cache.memoryUsed;
            this.currentDiskSize = cache.diskUsed;
         }

      }
   }

   public abstract static class CacheUtilityEvent {
      public Date timeStamp = new Date();

      CacheUtilityEvent() throws CX.EntityCacheException {
      }
   }
}
