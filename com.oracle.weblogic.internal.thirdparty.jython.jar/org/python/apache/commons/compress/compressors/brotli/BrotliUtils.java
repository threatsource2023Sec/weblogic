package org.python.apache.commons.compress.compressors.brotli;

public class BrotliUtils {
   private static volatile CachedAvailability cachedBrotliAvailability;

   private BrotliUtils() {
   }

   public static boolean isBrotliCompressionAvailable() {
      CachedAvailability cachedResult = cachedBrotliAvailability;
      if (cachedResult != BrotliUtils.CachedAvailability.DONT_CACHE) {
         return cachedResult == BrotliUtils.CachedAvailability.CACHED_AVAILABLE;
      } else {
         return internalIsBrotliCompressionAvailable();
      }
   }

   private static boolean internalIsBrotliCompressionAvailable() {
      try {
         Class.forName("org.brotli.dec.BrotliInputStream");
         return true;
      } catch (Exception | NoClassDefFoundError var1) {
         return false;
      }
   }

   public static void setCacheBrotliAvailablity(boolean doCache) {
      if (!doCache) {
         cachedBrotliAvailability = BrotliUtils.CachedAvailability.DONT_CACHE;
      } else if (cachedBrotliAvailability == BrotliUtils.CachedAvailability.DONT_CACHE) {
         boolean hasBrotli = internalIsBrotliCompressionAvailable();
         cachedBrotliAvailability = hasBrotli ? BrotliUtils.CachedAvailability.CACHED_AVAILABLE : BrotliUtils.CachedAvailability.CACHED_UNAVAILABLE;
      }

   }

   static CachedAvailability getCachedBrotliAvailability() {
      return cachedBrotliAvailability;
   }

   static {
      cachedBrotliAvailability = BrotliUtils.CachedAvailability.DONT_CACHE;

      try {
         Class.forName("org.osgi.framework.BundleEvent");
      } catch (Exception var1) {
         setCacheBrotliAvailablity(true);
      }

   }

   static enum CachedAvailability {
      DONT_CACHE,
      CACHED_AVAILABLE,
      CACHED_UNAVAILABLE;
   }
}
