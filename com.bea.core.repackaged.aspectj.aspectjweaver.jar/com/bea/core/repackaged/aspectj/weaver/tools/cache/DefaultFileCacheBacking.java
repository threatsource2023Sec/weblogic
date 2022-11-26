package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class DefaultFileCacheBacking extends AbstractIndexedFileCacheBacking {
   private final Map index = this.readIndex();
   private static final Object LOCK = new Object();

   protected DefaultFileCacheBacking(File cacheDir) {
      super(cacheDir);
   }

   public static final DefaultFileCacheBacking createBacking(File cacheDir) {
      if (!cacheDir.exists()) {
         if (!cacheDir.mkdirs()) {
            MessageUtil.error("Unable to create cache directory at " + cacheDir.getName());
            return null;
         }
      } else if (!cacheDir.isDirectory()) {
         MessageUtil.error("Not a cache directory at " + cacheDir.getName());
         return null;
      }

      if (!cacheDir.canWrite()) {
         MessageUtil.error("Cache directory is not writable at " + cacheDir.getName());
         return null;
      } else {
         return new DefaultFileCacheBacking(cacheDir);
      }
   }

   protected Map getIndex() {
      return this.index;
   }

   protected AbstractIndexedFileCacheBacking.IndexEntry resolveIndexMapEntry(File cacheDir, AbstractIndexedFileCacheBacking.IndexEntry ie) {
      File cacheEntry = new File(cacheDir, ie.key);
      return !ie.ignored && !cacheEntry.canRead() ? null : ie;
   }

   private void removeIndexEntry(String key) {
      synchronized(LOCK) {
         this.index.remove(key);
         this.writeIndex();
      }
   }

   private void addIndexEntry(AbstractIndexedFileCacheBacking.IndexEntry ie) {
      synchronized(LOCK) {
         this.index.put(ie.key, ie);
         this.writeIndex();
      }
   }

   protected Map readIndex() {
      synchronized(LOCK) {
         return super.readIndex();
      }
   }

   protected void writeIndex() {
      synchronized(LOCK) {
         super.writeIndex();
      }
   }

   public void clear() {
      File cacheDir = this.getCacheDirectory();
      int numDeleted = false;
      int numDeleted;
      synchronized(LOCK) {
         numDeleted = FileUtil.deleteContents(cacheDir);
      }

      if (numDeleted > 0 && this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.info("clear(" + cacheDir + ") deleted");
      }

   }

   public static CacheBacking createBacking(String scope) {
      String cache = System.getProperty("aj.weaving.cache.dir");
      if (cache == null) {
         return null;
      } else {
         File cacheDir = new File(cache, scope);
         return createBacking(cacheDir);
      }
   }

   public String[] getKeys(final String regex) {
      File cacheDirectory = this.getCacheDirectory();
      File[] files = cacheDirectory.listFiles(new FilenameFilter() {
         public boolean accept(File file, String s) {
            return s.matches(regex);
         }
      });
      if (LangUtil.isEmpty((Object[])files)) {
         return EMPTY_KEYS;
      } else {
         String[] keys = new String[files.length];

         for(int i = 0; i < files.length; ++i) {
            keys[i] = files[i].getName();
         }

         return keys;
      }
   }

   public CachedClassEntry get(CachedClassReference ref, byte[] originalBytes) {
      File cacheDirectory = this.getCacheDirectory();
      String refKey = ref.getKey();
      File cacheFile = new File(cacheDirectory, refKey);
      AbstractIndexedFileCacheBacking.IndexEntry ie = (AbstractIndexedFileCacheBacking.IndexEntry)this.index.get(refKey);
      if (ie == null) {
         this.delete(cacheFile);
         return null;
      } else if (crc(originalBytes) != ie.crcClass) {
         this.delete(cacheFile);
         return null;
      } else if (ie.ignored) {
         return new CachedClassEntry(ref, WeavedClassCache.ZERO_BYTES, CachedClassEntry.EntryType.IGNORED);
      } else {
         if (cacheFile.canRead()) {
            byte[] bytes = this.read(cacheFile, ie.crcWeaved);
            if (bytes != null) {
               if (!ie.generated) {
                  return new CachedClassEntry(ref, bytes, CachedClassEntry.EntryType.WEAVED);
               }

               return new CachedClassEntry(ref, bytes, CachedClassEntry.EntryType.GENERATED);
            }
         }

         return null;
      }
   }

   public void put(CachedClassEntry entry, byte[] originalBytes) {
      File cacheDirectory = this.getCacheDirectory();
      String refKey = entry.getKey();
      AbstractIndexedFileCacheBacking.IndexEntry ie = (AbstractIndexedFileCacheBacking.IndexEntry)this.index.get(refKey);
      File cacheFile = new File(cacheDirectory, refKey);
      boolean writeEntryBytes;
      if (ie == null || ie.ignored == entry.isIgnored() && ie.generated == entry.isGenerated() && crc(originalBytes) == ie.crcClass) {
         writeEntryBytes = !cacheFile.exists();
      } else {
         this.delete(cacheFile);
         writeEntryBytes = true;
      }

      if (writeEntryBytes) {
         ie = createIndexEntry(entry, originalBytes);
         if (!entry.isIgnored()) {
            ie.crcWeaved = this.write(cacheFile, entry.getBytes());
         }

         this.addIndexEntry(ie);
      }

   }

   public void remove(CachedClassReference ref) {
      File cacheDirectory = this.getCacheDirectory();
      String refKey = ref.getKey();
      File cacheFile = new File(cacheDirectory, refKey);
      synchronized(LOCK) {
         this.removeIndexEntry(refKey);
         this.delete(cacheFile);
      }
   }

   protected void delete(File file) {
      synchronized(LOCK) {
         super.delete(file);
      }
   }

   protected byte[] read(File file, long expectedCRC) {
      byte[] bytes = null;
      synchronized(LOCK) {
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(file);
            bytes = FileUtil.readAsByteArray((InputStream)fis);
         } catch (Exception var13) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.warn("read(" + file.getAbsolutePath() + ")" + " failed (" + var13.getClass().getSimpleName() + ")" + " to read contents: " + var13.getMessage(), var13);
            }
         } finally {
            this.close(fis, file);
         }

         if (bytes != null && crc(bytes) == expectedCRC) {
            return bytes;
         } else {
            this.delete(file);
            return null;
         }
      }
   }

   protected long write(File file, byte[] bytes) {
      synchronized(LOCK) {
         if (file.exists()) {
            return -1L;
         } else {
            OutputStream out = null;

            long var6;
            try {
               out = new FileOutputStream(file);
               out.write(bytes);
               return crc(bytes);
            } catch (Exception var13) {
               if (this.logger != null && this.logger.isTraceEnabled()) {
                  this.logger.warn("write(" + file.getAbsolutePath() + ")" + " failed (" + var13.getClass().getSimpleName() + ")" + " to write contents: " + var13.getMessage(), var13);
               }

               this.delete(file);
               var6 = -1L;
            } finally {
               this.close(out, file);
            }

            return var6;
         }
      }
   }
}
