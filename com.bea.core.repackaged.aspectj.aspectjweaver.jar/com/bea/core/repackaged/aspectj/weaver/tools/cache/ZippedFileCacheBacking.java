package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZippedFileCacheBacking extends AsynchronousFileCacheBacking {
   public static final String ZIP_FILE = "cache.zip";
   private static final AsynchronousFileCacheBacking.AsynchronousFileCacheBackingCreator defaultCreator = new AsynchronousFileCacheBacking.AsynchronousFileCacheBackingCreator() {
      public ZippedFileCacheBacking create(File cacheDir) {
         return new ZippedFileCacheBacking(cacheDir);
      }
   };
   private final File zipFile;

   public ZippedFileCacheBacking(File cacheDir) {
      super(cacheDir);
      this.zipFile = new File(cacheDir, "cache.zip");
   }

   public File getZipFile() {
      return this.zipFile;
   }

   public static final ZippedFileCacheBacking createBacking(File cacheDir) {
      return (ZippedFileCacheBacking)createBacking(cacheDir, defaultCreator);
   }

   protected void writeClassBytes(String key, byte[] bytes) throws Exception {
      File outFile = this.getZipFile();

      Map entriesMap;
      try {
         entriesMap = readZipClassBytes(outFile);
      } catch (Exception var6) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.warn("writeClassBytes(" + outFile + ")[" + key + "]" + " failed (" + var6.getClass().getSimpleName() + ")" + " to read current data: " + var6.getMessage(), var6);
         }

         FileUtil.deleteContents(outFile);
         return;
      }

      if (entriesMap.isEmpty()) {
         entriesMap = Collections.singletonMap(key, bytes);
      } else {
         entriesMap.put(key, bytes);
      }

      try {
         writeZipClassBytes(outFile, entriesMap);
      } catch (Exception var7) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.warn("writeClassBytes(" + outFile + ")[" + key + "]" + " failed (" + var7.getClass().getSimpleName() + ")" + " to write updated data: " + var7.getMessage(), var7);
         }

         FileUtil.deleteContents(outFile);
      }

   }

   protected void removeClassBytes(String key) throws Exception {
      File outFile = this.getZipFile();

      Map entriesMap;
      try {
         entriesMap = readZipClassBytes(outFile);
      } catch (Exception var5) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.warn("removeClassBytes(" + outFile + ")[" + key + "]" + " failed (" + var5.getClass().getSimpleName() + ")" + " to read current data: " + var5.getMessage(), var5);
         }

         FileUtil.deleteContents(outFile);
         return;
      }

      if (entriesMap.isEmpty() || entriesMap.remove(key) != null) {
         try {
            writeZipClassBytes(outFile, entriesMap);
         } catch (Exception var6) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.warn("removeClassBytes(" + outFile + ")[" + key + "]" + " failed (" + var6.getClass().getSimpleName() + ")" + " to write updated data: " + var6.getMessage(), var6);
            }

            FileUtil.deleteContents(outFile);
         }

      }
   }

   protected Map readClassBytes(Map indexMap, File cacheDir) {
      File dataFile = new File(cacheDir, "cache.zip");
      boolean okEntries = true;

      Object entriesMap;
      try {
         entriesMap = readZipClassBytes(dataFile);
      } catch (Exception var7) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.warn("Failed (" + var7.getClass().getSimpleName() + ")" + " to read zip entries from " + dataFile + ": " + var7.getMessage(), var7);
         }

         entriesMap = new TreeMap();
         okEntries = false;
      }

      if (!this.syncClassBytesEntries(dataFile, indexMap, (Map)entriesMap)) {
         okEntries = false;
      }

      if (!okEntries) {
         FileUtil.deleteContents(dataFile);
         if (!((Map)entriesMap).isEmpty()) {
            ((Map)entriesMap).clear();
         }
      }

      this.syncIndexEntries(dataFile, indexMap, (Map)entriesMap);
      return (Map)entriesMap;
   }

   protected Collection syncIndexEntries(File dataFile, Map indexMap, Map entriesMap) {
      Collection toDelete = null;
      Iterator i$ = indexMap.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry ie = (Map.Entry)i$.next();
         String key = (String)ie.getKey();
         AbstractIndexedFileCacheBacking.IndexEntry indexEntry = (AbstractIndexedFileCacheBacking.IndexEntry)ie.getValue();
         if (!indexEntry.ignored && !entriesMap.containsKey(key)) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.debug("syncIndexEntries(" + dataFile + ")[" + key + "] no class bytes");
            }

            if (toDelete == null) {
               toDelete = new TreeSet();
            }

            toDelete.add(key);
         }
      }

      if (toDelete == null) {
         return Collections.emptySet();
      } else {
         i$ = toDelete.iterator();

         while(i$.hasNext()) {
            String key = (String)i$.next();
            indexMap.remove(key);
         }

         return toDelete;
      }
   }

   protected boolean syncClassBytesEntries(File dataFile, Map indexMap, Map entriesMap) {
      boolean okEntries = true;
      Iterator i$ = entriesMap.entrySet().iterator();

      while(true) {
         while(i$.hasNext()) {
            Map.Entry bytesEntry = (Map.Entry)i$.next();
            String key = (String)bytesEntry.getKey();
            AbstractIndexedFileCacheBacking.IndexEntry indexEntry = (AbstractIndexedFileCacheBacking.IndexEntry)indexMap.get(key);
            if (indexEntry != null && !indexEntry.ignored) {
               long crc = crc((byte[])bytesEntry.getValue());
               if (crc != indexEntry.crcWeaved) {
                  if (this.logger != null && this.logger.isTraceEnabled()) {
                     this.logger.debug("syncClassBytesEntries(" + dataFile + ")[" + key + "]" + " mismatched CRC - expected=" + indexEntry.crcWeaved + "/got=" + crc);
                  }

                  indexMap.remove(key);
                  okEntries = false;
               }
            } else {
               if (this.logger != null && this.logger.isTraceEnabled()) {
                  this.logger.debug("syncClassBytesEntries(" + dataFile + ")[" + key + "] bad index entry");
               }

               okEntries = false;
            }
         }

         return okEntries;
      }
   }

   protected AbstractIndexedFileCacheBacking.IndexEntry resolveIndexMapEntry(File cacheDir, AbstractIndexedFileCacheBacking.IndexEntry ie) {
      return cacheDir.exists() ? ie : null;
   }

   public static final Map readZipClassBytes(File file) throws IOException {
      if (!file.canRead()) {
         return Collections.emptyMap();
      } else {
         Map result = new TreeMap();
         byte[] copyBuf = new byte[4096];
         ByteArrayOutputStream out = new ByteArrayOutputStream(copyBuf.length);
         ZipFile zipFile = new ZipFile(file);

         try {
            Enumeration entries = zipFile.entries();

            while(entries != null && entries.hasMoreElements()) {
               ZipEntry e = (ZipEntry)entries.nextElement();
               String name = e.getName();
               if (!LangUtil.isEmpty(name)) {
                  out.reset();
                  InputStream zipStream = zipFile.getInputStream(e);

                  try {
                     for(int nRead = zipStream.read(copyBuf); nRead != -1; nRead = zipStream.read(copyBuf)) {
                        out.write(copyBuf, 0, nRead);
                     }
                  } finally {
                     zipStream.close();
                  }

                  byte[] data = out.toByteArray();
                  byte[] var10 = (byte[])result.put(name, data);
                  if (var10 != null) {
                     throw new StreamCorruptedException("Multiple entries for " + name);
                  }
               }
            }
         } finally {
            zipFile.close();
         }

         return result;
      }
   }

   public static final void writeZipClassBytes(File file, Map entriesMap) throws IOException {
      if (entriesMap.isEmpty()) {
         FileUtil.deleteContents(file);
      } else {
         File zipDir = file.getParentFile();
         if (!zipDir.exists() && !zipDir.mkdirs()) {
            throw new IOException("Failed to create path to " + zipDir.getAbsolutePath());
         } else {
            ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file), 4096));

            try {
               Iterator i$ = entriesMap.entrySet().iterator();

               while(i$.hasNext()) {
                  Map.Entry bytesEntry = (Map.Entry)i$.next();
                  String key = (String)bytesEntry.getKey();
                  byte[] bytes = (byte[])bytesEntry.getValue();
                  zipOut.putNextEntry(new ZipEntry(key));
                  zipOut.write(bytes);
                  zipOut.closeEntry();
               }
            } finally {
               zipOut.close();
            }

         }
      }
   }
}
