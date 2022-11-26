package weblogic.security.utils;

import java.sql.Timestamp;

public class CacheStatistics {
   private long cacheHits = 0L;
   private long cacheEntries = 0L;
   private long cacheQueries = 0L;
   private long statStartTimeStamp = System.currentTimeMillis();

   public long getCacheEntries() {
      return this.cacheEntries;
   }

   public void setCacheEntries(long cacheEntries) {
      this.cacheEntries = cacheEntries;
   }

   public long getCacheHits() {
      return this.cacheHits;
   }

   public void setCacheHits(long cacheHits) {
      this.cacheHits = cacheHits;
   }

   public long getCacheQueries() {
      return this.cacheQueries;
   }

   public void setCacheQueries(long cacheQueries) {
      this.cacheQueries = cacheQueries;
   }

   public float getCacheHitRatio() {
      return this.cacheQueries != 0L ? (float)this.cacheHits / (float)this.cacheQueries : 0.0F;
   }

   public Timestamp getStatStartTimeStamp() {
      return new Timestamp(this.statStartTimeStamp);
   }

   public static enum CacheCaller {
      Authenticator,
      MBean,
      Authenticator_User,
      Authenticator_Group,
      MBean_User,
      MBean_Group;
   }
}
