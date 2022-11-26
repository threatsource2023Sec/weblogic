package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Map;
import java.util.TreeMap;

public class FlatFileCacheBacking extends AsynchronousFileCacheBacking {
   private static final AsynchronousFileCacheBacking.AsynchronousFileCacheBackingCreator defaultCreator = new AsynchronousFileCacheBacking.AsynchronousFileCacheBackingCreator() {
      public FlatFileCacheBacking create(File cacheDir) {
         return new FlatFileCacheBacking(cacheDir);
      }
   };

   public FlatFileCacheBacking(File cacheDir) {
      super(cacheDir);
   }

   public static final FlatFileCacheBacking createBacking(File cacheDir) {
      return (FlatFileCacheBacking)createBacking(cacheDir, defaultCreator);
   }

   protected Map readClassBytes(Map indexMap, File cacheDir) {
      return this.readClassBytes(indexMap, cacheDir.listFiles());
   }

   protected Map readClassBytes(Map indexMap, File[] files) {
      Map result = new TreeMap();
      if (LangUtil.isEmpty((Object[])files)) {
         return result;
      } else {
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File file = arr$[i$];
            if (file.isFile()) {
               String key = file.getName();
               if (!"cache.idx".equalsIgnoreCase(key)) {
                  AbstractIndexedFileCacheBacking.IndexEntry entry = (AbstractIndexedFileCacheBacking.IndexEntry)indexMap.get(key);
                  if (entry != null && !entry.ignored) {
                     try {
                        byte[] bytes = FileUtil.readAsByteArray(file);
                        long crc = crc(bytes);
                        if (crc != entry.crcWeaved) {
                           throw new StreamCorruptedException("Mismatched CRC - expected=" + entry.crcWeaved + "/got=" + crc);
                        }

                        result.put(key, bytes);
                        if (this.logger != null && this.logger.isTraceEnabled()) {
                           this.logger.debug("readClassBytes(" + key + ") cached from " + file.getAbsolutePath());
                        }
                     } catch (IOException var13) {
                        if (this.logger != null && this.logger.isTraceEnabled()) {
                           this.logger.error("Failed (" + var13.getClass().getSimpleName() + ")" + " to read bytes from " + file.getAbsolutePath() + ": " + var13.getMessage());
                        }

                        indexMap.remove(key);
                        FileUtil.deleteContents(file);
                     }
                  } else {
                     if (this.logger != null && this.logger.isTraceEnabled()) {
                        this.logger.info("readClassBytes(" + key + ") remove orphan/ignored: " + file.getAbsolutePath());
                     }

                     FileUtil.deleteContents(file);
                  }
               }
            }
         }

         return result;
      }
   }

   protected AbstractIndexedFileCacheBacking.IndexEntry resolveIndexMapEntry(File cacheDir, AbstractIndexedFileCacheBacking.IndexEntry ie) {
      File cacheEntry = new File(cacheDir, ie.key);
      return !ie.ignored && !cacheEntry.canRead() ? null : ie;
   }

   protected void writeClassBytes(String key, byte[] bytes) throws Exception {
      File dir = this.getCacheDirectory();
      File file = new File(dir, key);
      FileOutputStream out = new FileOutputStream(file);

      try {
         out.write(bytes);
      } finally {
         out.close();
      }

   }

   protected void removeClassBytes(String key) throws Exception {
      File dir = this.getCacheDirectory();
      File file = new File(dir, key);
      if (file.exists() && !file.delete()) {
         throw new StreamCorruptedException("Failed to delete " + file.getAbsolutePath());
      }
   }
}
