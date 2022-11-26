package weblogic.xml.util.cache.entitycache;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class CacheEntry implements Serializable {
   static final long serialVersionUID = 1L;
   Object description = null;
   Object sourceIdentificationData = null;
   private long whenCreated;
   private volatile long whenLastAccessed;
   private long size;
   private long leaseInterval = 0L;
   private Serializable key;
   private boolean hasReader;
   transient volatile long expirationTime;
   transient String fileName;
   transient EntityCache cache;
   private transient AtomicReference theData = new AtomicReference();
   private transient long diskSize = 0L;
   transient CacheEntry prevAccess = null;
   transient CacheEntry nextAccess = null;
   private transient volatile boolean isPersistent = false;
   private transient volatile boolean isPersisted = false;

   public CacheEntry(EntityCache cache, Serializable key, Serializable obj, long size, Object sourceIdentificationData, long leaseInterval) throws CX.EntityCacheException {
      this.hasReader = false;
      this.cache = cache;
      this.key = key;
      this.sourceIdentificationData = sourceIdentificationData;
      long currentTime = System.currentTimeMillis();
      this.whenLastAccessed = this.whenCreated = currentTime;
      this.expirationTime = currentTime + leaseInterval;
      this.size = size;
      this.leaseInterval = leaseInterval;
      this.theData.set(obj);
   }

   public CacheEntry(EntityCache cache, Serializable key, Reader rdr, Object sourceIdentificationData, long leaseInterval) throws CX.EntityCacheException {
      this.hasReader = true;
      this.cache = cache;
      this.key = key;
      this.sourceIdentificationData = sourceIdentificationData;
      long currentTime = System.currentTimeMillis();
      this.whenLastAccessed = this.whenCreated = currentTime;
      this.expirationTime = currentTime + leaseInterval;
      this.leaseInterval = leaseInterval;
      char[] readData = this.readData(rdr);
      this.theData.set(readData);
      this.size = (long)readData.length;
   }

   protected char[] readData(Reader charsIn) throws CX.EntityCacheException {
      char[] ca = null;
      CharArrayWriter w = null;

      try {
         w = new CharArrayWriter();
         char[] cbuf = new char[1024];
         int charsRead = false;

         int charsRead;
         while((charsRead = charsIn.read(cbuf)) != -1) {
            w.write(cbuf, 0, charsRead);
         }

         char[] ca = w.toCharArray();
         return ca;
      } catch (OutOfMemoryError var10) {
         throw var10;
      } catch (Exception var11) {
         throw new CX.EntityCacheException(var11);
      } finally {
         if (w != null) {
            w.flush();
            w.close();
         }

      }
   }

   void renewLease() throws CX.EntityCacheException {
      this.renewLease(this.leaseInterval);
   }

   void renewLease(long leaseInterval) throws CX.EntityCacheException {
      synchronized(this.cache) {
         long currentTime = System.currentTimeMillis();
         this.leaseInterval = leaseInterval;
         this.expirationTime = currentTime + leaseInterval;
         this.whenCreated = currentTime;
         this.cache.makeMostRecent(this);
         if (this.isPersistent) {
            this.saveIndex();
         }
      }

      if (this.cache.stats != null) {
         this.cache.stats.renewal(this);
      }

      if (this.cache.sessionStats != null) {
         this.cache.sessionStats.renewal(this);
      }

   }

   void stopLease() throws CX.EntityCacheException {
      synchronized(this.cache) {
         long currentTime = System.currentTimeMillis();
         this.leaseInterval = 0L;
         this.whenCreated = this.expirationTime = currentTime;
         if (this.isPersistent) {
            this.saveIndex();
         }
      }

      if (this.cache.stats != null) {
         this.cache.stats.renewal(this);
      }

      if (this.cache.sessionStats != null) {
         this.cache.sessionStats.renewal(this);
      }

   }

   void updateAccessed() {
      this.whenLastAccessed = System.currentTimeMillis();
   }

   public Date getWhenLastAccessed() {
      return new Date(this.whenLastAccessed);
   }

   static char[] loadFilePerReader(String path) throws CX.EntityCacheException {
      FileReader r = null;
      CharArrayWriter w = null;
      char[] ca = null;

      try {
         r = new FileReader(path);
         w = new CharArrayWriter();
         char[] cbuf = new char[1024];
         int charsRead = false;

         int charsRead;
         while((charsRead = r.read(cbuf)) != -1) {
            w.write(cbuf, 0, charsRead);
         }

         char[] ca = w.toCharArray();
         return ca;
      } catch (OutOfMemoryError var14) {
         throw new CX.FileLoadOutOfMemory(path, var14);
      } catch (Exception var15) {
         Tools.px(var15);
         throw new CX.FileLoad(path, var15);
      } finally {
         try {
            if (r != null) {
               r.close();
            }

            if (w != null) {
               w.close();
            }
         } catch (Exception var13) {
         }

      }
   }

   private Serializable loadData() throws CX.EntityCacheException {
      synchronized(this.cache) {
         Serializable obj = (Serializable)this.theData.get();
         if (obj != null) {
            return obj;
         } else {
            this.cache.loadCacheEntry(this);
            return (Serializable)this.theData.get();
         }
      }
   }

   synchronized void loadBytesCallback(String path) throws CX.EntityCacheException {
      if (this.theData.get() == null) {
         Object obj;
         if (!this.hasReader) {
            obj = EntityCache.loadFile(path);
         } else {
            obj = loadFilePerReader(path);
         }

         this.theData.set(obj);
      }
   }

   protected void saveEntry() throws CX.EntityCacheException {
      if (this.isPersistent) {
         this.saveData();
         if (this.isPersistent) {
            this.saveIndex();
         }
      }
   }

   protected void saveIndex() throws CX.EntityCacheException {
      try {
         if (this.isPersistent) {
            String path = EntityCache.getIndexFilePath(this.cache.getRootPath(), this.fileName);

            try {
               EntityCache.saveFile(this, path);
            } catch (Exception var3) {
               this.isPersistent = false;
               this.cache.notifyListener(new Event.FileAccessErrorForEntryEvent(this.cache, this, path, true));
            }

         }
      } catch (Exception var4) {
         throw new CX.EntityCacheException(var4);
      }
   }

   void saveFilePerReader(char[] data, String path) throws CX.EntityCacheException {
      FileWriter w = null;

      try {
         w = new FileWriter(path);
         w.write(data);
      } catch (Exception var12) {
         throw new CX.EntityCacheException(var12);
      } finally {
         if (w != null) {
            try {
               w.flush();
               w.close();
            } catch (Exception var11) {
            }
         }

      }

   }

   protected synchronized void saveData() throws CX.EntityCacheException {
      try {
         if (this.isPersistent) {
            if (!this.isPersisted) {
               this.isPersisted = true;
               long oldDiskSize = 0L;
               if (this.theData.get() != null) {
                  oldDiskSize = this.diskSize;
               }

               String path = EntityCache.getDataFilePath(this.cache.getRootPath(), this.fileName);

               try {
                  if (!this.hasReader) {
                     EntityCache.saveFile((Serializable)this.theData.get(), path);
                  } else {
                     this.saveFilePerReader((char[])((char[])this.theData.get()), path);
                  }
               } catch (Exception var8) {
                  this.isPersistent = false;
                  this.cache.notifyListener(new Event.FileAccessErrorForEntryEvent(this.cache, this, path, true));
                  return;
               }

               long newDiskSize = (new File(path)).length();
               long newDiskUsage = this.cache.diskUsed - oldDiskSize + newDiskSize;
               if (newDiskUsage > this.cache.maxDisk) {
                  if (newDiskSize > this.cache.maxDisk) {
                     throw new CX.EntryTooLargeDisk(this, this.cache.maxDisk);
                  }

                  this.cache.purgeDisk(newDiskUsage - this.cache.maxDisk, this);
               }

               this.cache.diskUsed = newDiskUsage;
               this.diskSize = newDiskSize;
               if (this.cache.stats != null) {
                  this.cache.stats.writeEntry(this);
               }

               if (this.cache.stats != null) {
                  this.cache.sessionStats.writeEntry(this);
               }

            }
         }
      } catch (CX.EntityCacheException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new CX.EntityCacheException(var10);
      }
   }

   protected void purge() throws CX.EntityCacheException {
      if (this.theData.get() != null) {
         if (this.isPersistent) {
            try {
               this.saveEntry();
            } catch (Exception var2) {
               this.cache.removeEntry(this.key);
               return;
            }

            this.theData.set((Object)null);
            EntityCache var10000 = this.cache;
            var10000.memoryUsed -= this.size;
         } else {
            this.cache.removeEntry(this.key);
         }
      }

   }

   protected boolean isLoaded() {
      return this.theData.get() != null;
   }

   protected boolean isPersisted() {
      return this.isPersisted;
   }

   Object getData() throws CX.EntityCacheException {
      Object results = this.theData.get();
      if (results == null) {
         results = this.loadData();
      }

      this.cache.makeMostRecent(this);
      return !this.hasReader ? results : new CharArrayReader((char[])((char[])results));
   }

   protected void delete() {
      if (this.isPersistent) {
         this.deleteEntryData(this.fileName, false);
      }
   }

   void makeTransient(boolean ignoreErrors) {
      if (this.isPersistent) {
         try {
            this.deleteEntryData(this.fileName, ignoreErrors);
         } catch (Exception var3) {
         }

      }
   }

   synchronized void deleteEntryData(String fileName, boolean ignoreErrors) {
      this.cache.deleteEntryData(fileName, ignoreErrors);
      this.isPersistent = false;
      this.isPersisted = false;
   }

   public boolean isPersistent() {
      return this.isPersistent;
   }

   public void setPersistent(boolean persistent) {
      this.isPersistent = persistent;
   }

   public boolean isPersistentNoStupidException() {
      return this.isPersistent;
   }

   public boolean isExpired() throws CX.EntityCacheException {
      return System.currentTimeMillis() > this.expirationTime;
   }

   public boolean olderThan(CacheEntry other) {
      return this.whenLastAccessed > other.whenLastAccessed;
   }

   public long getSecondsUntilExpiration() throws CX.EntityCacheException {
      return (System.currentTimeMillis() - this.expirationTime) / 1000L;
   }

   public long getMemorySize() {
      return this.theData.get() != null ? this.size : 0L;
   }

   synchronized void reactivateFromDisk(EntityCache cache, String fileName, long diskSize) {
      this.cache = cache;
      this.fileName = fileName;
      this.diskSize = diskSize;
      this.isPersistent = true;
      this.isPersisted = true;
   }

   public long getDiskSize() {
      return this.diskSize;
   }

   public long getDiskSizeNoStupidException() {
      return this.diskSize;
   }

   public Serializable getCacheKey() throws CX.EntityCacheException {
      return this.key;
   }

   public Object getDescription() throws CX.EntityCacheException {
      return this.description;
   }

   public void setDescription(Object description) throws CX.EntityCacheException {
      this.description = description;
   }

   public Object getSourceIdentificationData() throws CX.EntityCacheException {
      return this.sourceIdentificationData;
   }

   public void setSourceIdentificationData(Object sourceIdentificationData) throws CX.EntityCacheException {
      this.sourceIdentificationData = sourceIdentificationData;
   }

   public String toString() {
      return this.key.toString();
   }

   public long getSize() {
      return this.size;
   }

   public long getLeaseInterval() {
      return this.leaseInterval;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeObject(this.description);
      out.writeObject(this.sourceIdentificationData);
      out.writeObject(new Date(this.whenCreated));
      out.writeObject(new Date(this.whenLastAccessed));
      out.writeLong(this.size);
      out.writeLong(this.leaseInterval);
      out.writeObject(this.key);
      out.writeBoolean(this.hasReader);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.description = in.readObject();
      this.sourceIdentificationData = in.readObject();
      Date created = (Date)in.readObject();
      if (created != null) {
         this.whenCreated = created.getTime();
      }

      Date accessed = (Date)in.readObject();
      if (accessed != null) {
         this.whenLastAccessed = accessed.getTime();
      }

      this.size = in.readLong();
      this.leaseInterval = in.readLong();
      this.key = (Serializable)in.readObject();
      this.hasReader = in.readBoolean();
   }

   public Serializable getKey() {
      return this.key;
   }
}
