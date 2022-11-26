package org.python.apache.commons.compress.compressors.lzma;

import java.util.HashMap;
import java.util.Map;
import org.python.apache.commons.compress.compressors.FileNameUtil;

public class LZMAUtils {
   private static final FileNameUtil fileNameUtil;
   private static final byte[] HEADER_MAGIC = new byte[]{93, 0, 0};
   private static volatile CachedAvailability cachedLZMAAvailability;

   private LZMAUtils() {
   }

   public static boolean matches(byte[] signature, int length) {
      if (length < HEADER_MAGIC.length) {
         return false;
      } else {
         for(int i = 0; i < HEADER_MAGIC.length; ++i) {
            if (signature[i] != HEADER_MAGIC[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isLZMACompressionAvailable() {
      CachedAvailability cachedResult = cachedLZMAAvailability;
      if (cachedResult != LZMAUtils.CachedAvailability.DONT_CACHE) {
         return cachedResult == LZMAUtils.CachedAvailability.CACHED_AVAILABLE;
      } else {
         return internalIsLZMACompressionAvailable();
      }
   }

   private static boolean internalIsLZMACompressionAvailable() {
      try {
         LZMACompressorInputStream.matches((byte[])null, 0);
         return true;
      } catch (NoClassDefFoundError var1) {
         return false;
      }
   }

   public static boolean isCompressedFilename(String filename) {
      return fileNameUtil.isCompressedFilename(filename);
   }

   public static String getUncompressedFilename(String filename) {
      return fileNameUtil.getUncompressedFilename(filename);
   }

   public static String getCompressedFilename(String filename) {
      return fileNameUtil.getCompressedFilename(filename);
   }

   public static void setCacheLZMAAvailablity(boolean doCache) {
      if (!doCache) {
         cachedLZMAAvailability = LZMAUtils.CachedAvailability.DONT_CACHE;
      } else if (cachedLZMAAvailability == LZMAUtils.CachedAvailability.DONT_CACHE) {
         boolean hasLzma = internalIsLZMACompressionAvailable();
         cachedLZMAAvailability = hasLzma ? LZMAUtils.CachedAvailability.CACHED_AVAILABLE : LZMAUtils.CachedAvailability.CACHED_UNAVAILABLE;
      }

   }

   static CachedAvailability getCachedLZMAAvailability() {
      return cachedLZMAAvailability;
   }

   static {
      Map uncompressSuffix = new HashMap();
      uncompressSuffix.put(".lzma", "");
      uncompressSuffix.put("-lzma", "");
      fileNameUtil = new FileNameUtil(uncompressSuffix, ".lzma");
      cachedLZMAAvailability = LZMAUtils.CachedAvailability.DONT_CACHE;

      try {
         Class.forName("org.osgi.framework.BundleEvent");
      } catch (Exception var2) {
         setCacheLZMAAvailablity(true);
      }

   }

   static enum CachedAvailability {
      DONT_CACHE,
      CACHED_AVAILABLE,
      CACHED_UNAVAILABLE;
   }
}
