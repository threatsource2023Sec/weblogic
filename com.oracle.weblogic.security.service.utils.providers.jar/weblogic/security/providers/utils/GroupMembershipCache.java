package weblogic.security.providers.utils;

import com.bea.common.security.utils.TTLLRUCache;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.security.utils.CacheStatistics;

public class GroupMembershipCache {
   private boolean keysAreCaseSensitive = true;
   private TTLLRUCache cache;
   private final ReentrantLock lock = new ReentrantLock();
   private final ReentrantLock statLock = new ReentrantLock();
   private CacheStatistics _groupStatistics;

   private GroupMembershipCache() {
      this.cache = new TTLLRUCache(100, 600);
   }

   public GroupMembershipCache(int maxGroupMembershipsInCache, int timeToLive, boolean keysAreCaseSensitive) {
      this.keysAreCaseSensitive = keysAreCaseSensitive;
      this.cache = new TTLLRUCache(maxGroupMembershipsInCache, timeToLive);
   }

   public Vector getGroupMembership(String groupName) {
      Vector var2;
      try {
         this.lock.lock();
         if (this.keysAreCaseSensitive) {
            var2 = (Vector)this.cache.get(groupName);
            return var2;
         }

         var2 = (Vector)this.cache.get(groupName.toUpperCase());
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public void putGroupMembership(String groupName, Vector memberGroups) {
      try {
         this.lock.lock();
         if (this.keysAreCaseSensitive) {
            this.cache.put(groupName, memberGroups);
         } else {
            this.cache.put(groupName.toUpperCase(), memberGroups);
         }

         if (this._groupStatistics != null) {
            this._groupStatistics.setCacheEntries((long)this.cache.size());
         }
      } finally {
         this.lock.unlock();
      }

   }

   public void clear() {
      try {
         this.lock.lock();
         this.cache.clear();
      } finally {
         this.lock.unlock();
      }

   }

   public Vector removeEntry(String groupName) {
      Vector var2;
      try {
         this.lock.lock();
         if (this.keysAreCaseSensitive) {
            var2 = (Vector)this.cache.remove(groupName);
            return var2;
         }

         var2 = (Vector)this.cache.remove(groupName.toUpperCase());
      } finally {
         if (this._groupStatistics != null) {
            this._groupStatistics.setCacheEntries((long)this.cache.size());
         }

         this.lock.unlock();
      }

      return var2;
   }

   public void createStatistics() {
      this._groupStatistics = new CacheStatistics();
   }

   public CacheStatistics getStatistics() {
      return this._groupStatistics;
   }

   public void incGrpCacheHits() {
      if (this._groupStatistics != null) {
         try {
            this.statLock.lock();
            this._groupStatistics.setCacheHits(this._groupStatistics.getCacheHits() + 1L);
         } finally {
            this.statLock.unlock();
         }
      }

   }

   public void incGrpCacheQueries() {
      if (this._groupStatistics != null) {
         try {
            this.statLock.lock();
            this._groupStatistics.setCacheQueries(this._groupStatistics.getCacheQueries() + 1L);
         } finally {
            this.statLock.unlock();
         }
      }

   }
}
