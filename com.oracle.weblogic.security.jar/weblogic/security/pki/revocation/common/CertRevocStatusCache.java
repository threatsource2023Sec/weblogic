package weblogic.security.pki.revocation.common;

import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

final class CertRevocStatusCache {
   private static final int INITIAL_CAPACITY = 1024;
   private final CacheImpl cache = new CacheImpl();
   private final Object cacheSync = new Object();

   public static CertRevocStatusCache getInstance() {
      return new CertRevocStatusCache();
   }

   private CertRevocStatusCache() {
   }

   public CertRevocStatus getStatus(X509Certificate x509Cert, int timeTolerance, int refreshPeriodPercent, LogListener log) {
      Util.checkNotNull("X509Certificate", x509Cert);
      Util.checkTimeTolerance(timeTolerance);
      Util.checkRefreshPeriodPercent(refreshPeriodPercent);
      Object key = CertRevocStatus.createKey(x509Cert);
      synchronized(this.cacheSync) {
         CertRevocStatus status = (CertRevocStatus)this.cache.get(key);
         if (null == status) {
            return null;
         } else if (!status.isValid(timeTolerance, refreshPeriodPercent, log)) {
            this.cache.remove(status.getKey());
            return null;
         } else {
            return status;
         }
      }
   }

   public boolean putStatus(X509Certificate x509Cert, CertRevocStatus status, int timeTolerance, int refreshPeriodPercent, int capacity, LogListener log) {
      Util.checkNotNull("X509Certificate", x509Cert);
      Util.checkTimeTolerance(timeTolerance);
      Util.checkRefreshPeriodPercent(refreshPeriodPercent);
      checkCapacity(capacity);
      Object key = CertRevocStatus.createKey(x509Cert);
      synchronized(this.cacheSync) {
         if (null == status) {
            return null != this.cache.remove(key);
         } else if (!status.isValid(timeTolerance, refreshPeriodPercent, log)) {
            return false;
         } else {
            this.adjustCapacity(capacity);
            this.cache.put(key, status);
            return true;
         }
      }
   }

   public int getSize() {
      synchronized(this.cacheSync) {
         return this.cache.size();
      }
   }

   private static void checkCapacity(int capacity) {
      Util.checkRange("capacity", (long)capacity, 1L, (Long)null);
   }

   private void adjustCapacity(int capacity) {
      synchronized(this.cacheSync) {
         this.cache.setMaxEntries(capacity);
         int excessEntryCount = this.cache.size() - capacity;
         if (excessEntryCount > 0) {
            for(Iterator iter = this.cache.entrySet().iterator(); excessEntryCount > 0 && iter.hasNext(); --excessEntryCount) {
               Map.Entry entry = (Map.Entry)iter.next();
               iter.remove();
            }
         }

      }
   }

   private static class CacheImpl extends LinkedHashMap {
      private static final float LOAD_FACTOR = 0.75F;
      private static final boolean ACCESS_ORDER = true;
      private volatile int maxEntries;

      private CacheImpl() {
         super(1024, 0.75F, true);
         this.setMaxEntries(1024);
      }

      protected boolean removeEldestEntry(Map.Entry eldest) {
         return this.size() > this.maxEntries;
      }

      public void setMaxEntries(int maxEntries) {
         CertRevocStatusCache.checkCapacity(maxEntries);
         this.maxEntries = maxEntries;
      }

      // $FF: synthetic method
      CacheImpl(Object x0) {
         this();
      }
   }
}
