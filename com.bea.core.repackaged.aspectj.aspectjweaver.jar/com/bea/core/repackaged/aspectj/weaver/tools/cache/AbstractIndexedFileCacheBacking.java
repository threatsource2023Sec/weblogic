package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractIndexedFileCacheBacking extends AbstractFileCacheBacking {
   public static final String INDEX_FILE = "cache.idx";
   protected static final IndexEntry[] EMPTY_INDEX = new IndexEntry[0];
   protected static final String[] EMPTY_KEYS = new String[0];
   private final File indexFile;

   protected AbstractIndexedFileCacheBacking(File cacheDir) {
      super(cacheDir);
      this.indexFile = new File(cacheDir, "cache.idx");
   }

   public File getIndexFile() {
      return this.indexFile;
   }

   public String[] getKeys(String regex) {
      Map index = this.getIndex();
      if (index != null && !index.isEmpty()) {
         Collection matches = new LinkedList();
         synchronized(index) {
            Iterator i$ = index.keySet().iterator();

            while(i$.hasNext()) {
               String key = (String)i$.next();
               if (key.matches(regex)) {
                  matches.add(key);
               }
            }

            return matches.isEmpty() ? EMPTY_KEYS : (String[])matches.toArray(new String[matches.size()]);
         }
      } else {
         return EMPTY_KEYS;
      }
   }

   protected Map readIndex() {
      return this.readIndex(this.getCacheDirectory(), this.getIndexFile());
   }

   protected void writeIndex() {
      this.writeIndex(this.getIndexFile());
   }

   protected void writeIndex(File file) {
      try {
         this.writeIndex(file, this.getIndex());
      } catch (Exception var3) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.warn("writeIndex(" + file + ") " + var3.getClass().getSimpleName() + ": " + var3.getMessage(), var3);
         }
      }

   }

   protected abstract Map getIndex();

   protected Map readIndex(File cacheDir, File cacheFile) {
      Map indexMap = new TreeMap();
      IndexEntry[] idxValues = this.readIndex(cacheFile);
      if (LangUtil.isEmpty((Object[])idxValues)) {
         if (this.logger != null && this.logger.isTraceEnabled()) {
            this.logger.debug("readIndex(" + cacheFile + ") no index entries");
         }

         return indexMap;
      } else {
         IndexEntry[] arr$ = idxValues;
         int len$ = idxValues.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            IndexEntry ie = arr$[i$];
            IndexEntry resEntry = this.resolveIndexMapEntry(cacheDir, ie);
            if (resEntry != null) {
               indexMap.put(resEntry.key, resEntry);
            } else if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.debug("readIndex(" + cacheFile + ") skip " + ie.key);
            }
         }

         return indexMap;
      }
   }

   protected IndexEntry resolveIndexMapEntry(File cacheDir, IndexEntry ie) {
      return ie;
   }

   public IndexEntry[] readIndex(File indexFile) {
      if (!indexFile.canRead()) {
         return EMPTY_INDEX;
      } else {
         ObjectInputStream ois = null;

         try {
            ois = new ObjectInputStream(new FileInputStream(indexFile));
            IndexEntry[] var3 = (IndexEntry[])((IndexEntry[])ois.readObject());
            return var3;
         } catch (Exception var7) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.error("Failed (" + var7.getClass().getSimpleName() + ")" + " to read index from " + indexFile.getAbsolutePath() + " : " + var7.getMessage(), var7);
            }

            this.delete(indexFile);
         } finally {
            this.close(ois, indexFile);
         }

         return EMPTY_INDEX;
      }
   }

   protected void writeIndex(File indexFile, Map index) throws IOException {
      this.writeIndex(indexFile, (Collection)(LangUtil.isEmpty(index) ? Collections.emptyList() : index.values()));
   }

   protected void writeIndex(File indexFile, IndexEntry... entries) throws IOException {
      this.writeIndex(indexFile, (Collection)(LangUtil.isEmpty((Object[])entries) ? Collections.emptyList() : Arrays.asList(entries)));
   }

   protected void writeIndex(File indexFile, Collection entries) throws IOException {
      File indexDir = indexFile.getParentFile();
      if (!indexDir.exists() && !indexDir.mkdirs()) {
         throw new IOException("Failed to create path to " + indexFile.getAbsolutePath());
      } else {
         int numEntries = LangUtil.isEmpty(entries) ? 0 : entries.size();
         IndexEntry[] entryValues = numEntries <= 0 ? null : (IndexEntry[])entries.toArray(new IndexEntry[numEntries]);
         if (LangUtil.isEmpty((Object[])entryValues)) {
            if (indexFile.exists() && !indexFile.delete()) {
               throw new StreamCorruptedException("Failed to clean up index file at " + indexFile.getAbsolutePath());
            }
         } else {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(indexFile), 4096));

            try {
               oos.writeObject(entryValues);
            } finally {
               this.close(oos, indexFile);
            }

         }
      }
   }

   public static final IndexEntry createIndexEntry(CachedClassEntry classEntry, byte[] originalBytes) {
      if (classEntry == null) {
         return null;
      } else {
         IndexEntry indexEntry = new IndexEntry();
         indexEntry.key = classEntry.getKey();
         indexEntry.generated = classEntry.isGenerated();
         indexEntry.ignored = classEntry.isIgnored();
         indexEntry.crcClass = crc(originalBytes);
         if (!classEntry.isIgnored()) {
            indexEntry.crcWeaved = crc(classEntry.getBytes());
         }

         return indexEntry;
      }
   }

   public static class IndexEntry implements Serializable, Cloneable {
      private static final long serialVersionUID = 756391290557029363L;
      public String key;
      public boolean generated;
      public boolean ignored;
      public long crcClass;
      public long crcWeaved;

      public IndexEntry clone() {
         try {
            return (IndexEntry)this.getClass().cast(super.clone());
         } catch (CloneNotSupportedException var2) {
            throw new RuntimeException("Failed to clone: " + this.toString() + ": " + var2.getMessage(), var2);
         }
      }

      public int hashCode() {
         return (int)((long)(this.key.hashCode() + (this.generated ? 1 : 0) + (this.ignored ? 1 : 0)) + this.crcClass + this.crcWeaved);
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            IndexEntry other = (IndexEntry)obj;
            return this.key.equals(other.key) && this.ignored == other.ignored && this.generated == other.generated && this.crcClass == other.crcClass && this.crcWeaved == other.crcWeaved;
         }
      }

      public String toString() {
         return this.key + "[" + (this.generated ? "generated" : "ignored") + "]" + ";crcClass=0x" + Long.toHexString(this.crcClass) + ";crcWeaved=0x" + Long.toHexString(this.crcWeaved);
      }
   }
}
