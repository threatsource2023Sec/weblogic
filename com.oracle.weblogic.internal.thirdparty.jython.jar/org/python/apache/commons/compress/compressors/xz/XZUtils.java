package org.python.apache.commons.compress.compressors.xz;

import java.util.HashMap;
import java.util.Map;
import org.python.apache.commons.compress.compressors.FileNameUtil;

public class XZUtils {
   private static final FileNameUtil fileNameUtil;
   private static final byte[] HEADER_MAGIC = new byte[]{-3, 55, 122, 88, 90, 0};
   private static volatile CachedAvailability cachedXZAvailability;

   private XZUtils() {
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

   public static boolean isXZCompressionAvailable() {
      CachedAvailability cachedResult = cachedXZAvailability;
      if (cachedResult != XZUtils.CachedAvailability.DONT_CACHE) {
         return cachedResult == XZUtils.CachedAvailability.CACHED_AVAILABLE;
      } else {
         return internalIsXZCompressionAvailable();
      }
   }

   private static boolean internalIsXZCompressionAvailable() {
      try {
         XZCompressorInputStream.matches((byte[])null, 0);
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

   public static void setCacheXZAvailablity(boolean doCache) {
      if (!doCache) {
         cachedXZAvailability = XZUtils.CachedAvailability.DONT_CACHE;
      } else if (cachedXZAvailability == XZUtils.CachedAvailability.DONT_CACHE) {
         boolean hasXz = internalIsXZCompressionAvailable();
         cachedXZAvailability = hasXz ? XZUtils.CachedAvailability.CACHED_AVAILABLE : XZUtils.CachedAvailability.CACHED_UNAVAILABLE;
      }

   }

   static CachedAvailability getCachedXZAvailability() {
      return cachedXZAvailability;
   }

   static {
      Map uncompressSuffix = new HashMap();
      uncompressSuffix.put(".txz", ".tar");
      uncompressSuffix.put(".xz", "");
      uncompressSuffix.put("-xz", "");
      fileNameUtil = new FileNameUtil(uncompressSuffix, ".xz");
      cachedXZAvailability = XZUtils.CachedAvailability.DONT_CACHE;

      try {
         Class.forName("org.osgi.framework.BundleEvent");
      } catch (Exception var2) {
         setCacheXZAvailablity(true);
      }

   }

   static enum CachedAvailability {
      DONT_CACHE,
      CACHED_AVAILABLE,
      CACHED_UNAVAILABLE;
   }
}
