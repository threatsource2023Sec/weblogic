package weblogic.xml.util.cache.entitycache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.XMLEntityCacheMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class EntityCache implements Serializable {
   static final long serialVersionUID = 1L;
   static ServerMBean serverConfigMBean = null;
   static final long DefaultCacheSize = 2000000L;
   static final long DefaultCacheDiskSize = 5000000L;
   static final int DefaultSizeBias = 5;
   static final String DefaultCachePath = "EntityCache";
   long maxMemory;
   long maxDisk;
   long memoryUsed;
   long fileNameCounter;
   double sizeBias;
   String name;
   transient StatisticsMonitor statisticsMonitor;
   transient Statistics stats;
   transient Statistics sessionStats;
   transient Statistics currentStats;
   private String rootPath;
   static transient Hashtable caches = new Hashtable();
   transient ConcurrentHashMap entries;
   transient long diskUsed;
   transient AccessList accessList;
   static transient Persister persister = null;
   transient CacheListener listener;
   transient volatile boolean statsCumulativeModification;
   transient volatile boolean statsCurrentModification;
   transient long memLossTotal;
   transient long diskLossTotal;

   static ServerMBean getServerConfigMBean() throws CX.EntityCacheException {
      if (serverConfigMBean == null) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         serverConfigMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         if (serverConfigMBean == null) {
            throw new CX.EntityCacheException("ServerConfigMBean can't be null!");
         }
      }

      return serverConfigMBean;
   }

   public static EntityCache getCache(CacheSpec spec, XMLEntityCacheMBean cacheConfig) throws CX.EntityCacheException {
      boolean wasNotOpen = false;
      String extendedPath = spec.path;
      if (!extendedPath.endsWith(String.valueOf(File.separatorChar))) {
         extendedPath = extendedPath + File.separatorChar;
      }

      EntityCache cache = (EntityCache)caches.get(extendedPath);
      if (cache == null) {
         wasNotOpen = true;
         if ((new File(getCacheFilePath(extendedPath))).exists()) {
            cache = open(extendedPath, spec);
         } else {
            cache = create(extendedPath, spec);
         }
      }

      if (cache == null) {
         return null;
      } else {
         cache.name = spec.name;
         cache.listener = spec.cacheListener;
         cache.setMemoryFootprint(spec.memSize);
         cache.setDiskFootprint(spec.diskSize);
         File dir = new File(extendedPath);
         if (!dir.exists()) {
            dir.mkdirs();
         }

         dir = new File(getIndexDirectory(extendedPath));
         if (!dir.exists()) {
            dir.mkdirs();
         }

         dir = new File(getDataDirectory(extendedPath));
         if (!dir.exists()) {
            dir.mkdirs();
         }

         try {
            saveFile(cache, getCacheFilePath(extendedPath));
         } catch (Exception var12) {
            if (spec.cacheListener != null) {
               spec.cacheListener.notify(new Event.FileAccessErrorForCacheEvent(cache, extendedPath, true));
            }
         }

         if (wasNotOpen) {
            caches.put(extendedPath, cache);

            try {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               ServerRuntimeMBean server = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
               cache.statisticsMonitor = new StatisticsMonitor(cache, 300000L);
               cache.stats = Statistics.initialize(cache, getStatsFilePath(cache.rootPath));
               EntityCacheCumulativeStats historicalMBean = cache.registerCumulativeStatsMBean("EntityCacheHistorical_", cache.stats);
               cache.statisticsMonitor.addSubject(cache.sessionStats, historicalMBean);
               server.setEntityCacheHistoricalRuntime(historicalMBean);
               cache.sessionStats = new Statistics(cache);
               EntityCacheCumulativeStats sessionMBean = cache.registerCumulativeStatsMBean("EntityCacheCumulative_", cache.sessionStats);
               server.setEntityCacheCumulativeRuntime(sessionMBean);
               cache.statisticsMonitor.addSubject(cache.sessionStats, sessionMBean);
               cache.currentStats = new Statistics(cache);
               EntityCacheCurrentStats currentMBean = new EntityCacheCurrentStats(cache);
               cache.statisticsMonitor.addSubject(cache.currentStats, currentMBean);
               server.setEntityCacheCurrentStateRuntime(currentMBean);
               cache.statisticsMonitor.start();
            } catch (ManagementException var11) {
               throw new CX.EntityCacheException(var11);
            }
         }

         return cache;
      }
   }

   public String getName() {
      return this.name;
   }

   public void addEntry(Serializable key, CacheEntry ce) throws CX.EntityCacheException {
      this.addEntry(key, ce, true);
   }

   public void addEntry(Serializable key, CacheEntry ce, boolean isPersistent) throws CX.EntityCacheException {
      synchronized(this) {
         this.removeEntry(key);
         ce.setPersistent(isPersistent);
         this.findSpace(ce);
         this.put(key, ce);
         this.accessList.addMostRecent(ce);
         this.memoryUsed += ce.getSize();
         ce.setFileName(this.getNewFileName());
      }

      if (isPersistent) {
         Persister.get().add(ce);
      }

      if (this.stats != null) {
         this.stats.addEntry(ce);
      }

      if (this.sessionStats != null) {
         this.sessionStats.addEntry(ce);
      }

      this.notifyListener(new Event.EntryAddEvent(this, ce));
      this.statsCurrentModification = true;
   }

   public Object getData(Serializable key) throws CX.EntityCacheException {
      Object data = null;
      CacheEntry ce = this.get(key);
      if (ce != null) {
         if (ce.isExpired()) {
            throw new CX.EntryExpired(ce);
         }

         data = ce.getData();
      }

      return data;
   }

   public synchronized CacheEntry renewLease(Serializable key) throws CX.EntityCacheException {
      CacheEntry ce = this.get(key);
      if (ce != null) {
         ce.renewLease();
      }

      return ce;
   }

   public synchronized CacheEntry putrify(Serializable key) throws CX.EntityCacheException {
      CacheEntry ce = this.get(key);
      if (ce != null) {
         ce.stopLease();
      }

      return ce;
   }

   public synchronized CacheEntry renewLease(Serializable key, long leaseInterval) throws CX.EntityCacheException {
      CacheEntry ce = this.get(key);
      if (ce != null) {
         ce.renewLease(leaseInterval);
         this.accessList.moveMostRecent(ce);
      }

      return ce;
   }

   public synchronized CacheEntry removeEntry(Serializable key) throws CX.EntityCacheException {
      CacheEntry ce = this.get(key);
      if (ce != null) {
         this.remove(key);
         this.accessList.remove(ce);
         this.reduceMemoryUsed(ce.getSize());
         ce.delete();
         this.notifyListener(new Event.EntryDeleteEvent(this, ce));
      }

      this.statsCurrentModification = true;
      return ce;
   }

   public synchronized long getMemoryFootprint() {
      return this.maxMemory;
   }

   public synchronized void setMemoryFootprint(long newMemSize) throws CX.EntityCacheException {
      long toPurge = this.memoryUsed - newMemSize;
      if (toPurge != 0L) {
         if (toPurge > 0L) {
            this.purge(toPurge);
         }

         this.maxMemory = newMemSize;
      }
   }

   public synchronized long getDiskFootprint() {
      return this.maxDisk;
   }

   public synchronized void setDiskFootprint(long newDiskSize) throws CX.EntityCacheException {
      long toPurge = this.diskUsed - newDiskSize;
      if (toPurge != 0L) {
         if (toPurge > 0L) {
            this.purgeDisk(toPurge);
         }

         this.maxDisk = newDiskSize;
      }
   }

   public synchronized void close() throws CX.EntityCacheException {
      caches.remove(this.rootPath);
      if (this.statisticsMonitor != null) {
         this.statisticsMonitor.finish();
      }

      this.notifyListener(new Event.CacheCloseEvent(this));
      this.listener = null;
   }

   synchronized void notifyListener(Event.CacheUtilityEvent e) {
      if (this.listener != null) {
         this.listener.notify(e);
      }

   }

   public void makeMostRecent(CacheEntry entry) {
      entry.updateAccessed();
      this.accessList.moveMostRecent(entry);
   }

   protected EntityCache(String name, String path) {
      this(name, 2000000L, 5000000L, 5, path);
   }

   protected EntityCache(String name, long size, long maxDisk, int sizeBias, String persistencePath) {
      this.maxMemory = 0L;
      this.maxDisk = 0L;
      this.memoryUsed = 0L;
      this.fileNameCounter = -1L;
      this.sizeBias = 0.0;
      this.name = null;
      this.statisticsMonitor = null;
      this.stats = null;
      this.sessionStats = null;
      this.currentStats = null;
      this.entries = null;
      this.diskUsed = 0L;
      this.accessList = null;
      this.listener = null;
      this.statsCumulativeModification = false;
      this.statsCurrentModification = false;
      this.memLossTotal = 0L;
      this.diskLossTotal = 0L;
      this.name = name;
      this.maxMemory = size;
      this.maxDisk = maxDisk;
      this.sizeBias = (double)sizeBias / 100.0;
      this.rootPath = persistencePath;
   }

   void put(Serializable key, CacheEntry ce) {
      this.entries.put(key, ce);
   }

   CacheEntry get(Serializable key) {
      CacheEntry ce = (CacheEntry)this.entries.get(key);
      return ce;
   }

   CacheEntry remove(Serializable key) {
      CacheEntry ce = (CacheEntry)this.entries.remove(key);
      return ce;
   }

   public String getIndexFilePath(String fileName) {
      return getIndexFilePath(this.rootPath, fileName);
   }

   public String getDataFilePath(String fileName) {
      return getDataFilePath(this.rootPath, fileName);
   }

   static String getIndexFilePath(String root, String fileName) {
      return getIndexDirectory(root) + fileName;
   }

   static String getIndexDirectory(String root) {
      return root + "index" + File.separatorChar;
   }

   static String getDataFilePath(String root, String fileName) {
      return getDataDirectory(root) + fileName;
   }

   static String getDataDirectory(String root) {
      return root + "data" + File.separatorChar;
   }

   static String getCacheFilePath(String root) {
      return root + File.separatorChar + "cache";
   }

   static String getStatsFilePath(String root) {
      return root + File.separatorChar + "stats";
   }

   protected static EntityCache open(String path, CacheSpec spec) throws CX.EntityCacheException {
      EntityCache cache = null;

      try {
         try {
            cache = (EntityCache)loadFile(getCacheFilePath(path));
            cache.initTransientInfo(path, spec);
            if (spec.cacheListener != null) {
               spec.cacheListener.notify(new Event.CacheLoadEvent(cache));
            }
         } catch (CX.FileLoadOutOfMemory var16) {
            Tools.log("Unable to load cache data record from disk (too big!): " + path + ". Rebuilding from index...");
            if (spec.cacheListener != null) {
               spec.cacheListener.notify(new Event.OutOfMemoryLoadingCacheEvent(path));
            }
         } catch (CX.FileLoad var17) {
            Tools.log("Unable to load cache data record from disk (corrupted?): " + path + ". Rebuilding from index...");
            if (canRead(getCacheFilePath(path))) {
               if (spec.cacheListener != null) {
                  spec.cacheListener.notify(new Event.CacheCorruptionEvent(path));
               }
            } else if (spec.cacheListener != null) {
               spec.cacheListener.notify(new Event.FileAccessErrorForCacheEvent((EntityCache)null, path, false));
            }
         }

         if (cache == null) {
            cache = create(path, spec);
         } else {
            cache.name = spec.name;
            cache.setMemoryFootprint(spec.memSize);
            cache.setDiskFootprint(spec.diskSize);
         }

         String[] files = (new File(getIndexDirectory(path))).list();

         for(int i = 0; i < files.length; ++i) {
            long fileNumber = 0L;

            try {
               Long.parseLong(files[i]);
            } catch (NumberFormatException var13) {
            }

            CacheEntry ce = null;
            String indexFilePath = getIndexFilePath(path, files[i]);
            String dataFilePath = getDataFilePath(path, files[i]);

            try {
               ce = (CacheEntry)loadFile(indexFilePath);
            } catch (CX.FileLoadOutOfMemory var14) {
               Tools.log("Unable to load cache index data record from disk (too big!). Deleting entry...");
               cache.notifyListener(new Event.OutOfMemoryLoadingEntryEvent(cache, (Serializable)null, path));
               continue;
            } catch (CX.FileLoad var15) {
               if (canRead(indexFilePath)) {
                  cache.notifyListener(new Event.EntryCorruptionEvent(cache, (Serializable)null, path));
               } else {
                  cache.notifyListener(new Event.FileAccessErrorForEntryEvent(cache, (CacheEntry)null, path, false));
               }

               Tools.log("Unable to load cache index data record from disk (corrupted?). Deleting entry...");
               cache.deleteEntryData(files[i], true);
               continue;
            }

            if (ce != null) {
               long diskSize = (new File(dataFilePath)).length();
               ce.reactivateFromDisk(cache, files[i], diskSize);
               cache.put(ce.getKey(), ce);
               cache.accessList.addSorted(ce);
               cache.diskUsed += diskSize;
               if (fileNumber > cache.fileNameCounter) {
                  cache.fileNameCounter = fileNumber;
               }
            }
         }

         try {
            saveFile(cache, getCacheFilePath(cache.rootPath));
         } catch (Exception var12) {
            cache.notifyListener(new Event.FileAccessErrorForCacheEvent(cache, path, true));
         }
      } catch (Exception var18) {
         if (spec.cacheListener != null) {
            spec.cacheListener.notify(new Event.CacheFailureEvent(cache, path, "Exception while opening cache."));
         }

         cache = null;
      }

      return cache;
   }

   protected void initTransientInfo(String path, CacheSpec spec) {
      this.entries = new ConcurrentHashMap();
      this.accessList = new AccessList();
      this.rootPath = path;
      this.memoryUsed = 0L;
      this.diskUsed = 0L;
      this.listener = spec.cacheListener;
   }

   protected static EntityCache create(String path, CacheSpec spec) throws CX.EntityCacheException {
      EntityCache cache = null;

      try {
         cache = new EntityCache(spec.name, spec.memSize, spec.diskSize, spec.sizeBias, path);
         cache.initTransientInfo(path, spec);
         if (spec.cacheListener != null) {
            spec.cacheListener.notify(new Event.CacheCreationEvent(cache));
         }
      } catch (Exception var4) {
         if (spec.cacheListener != null) {
            spec.cacheListener.notify(new Event.CacheFailureEvent(cache, path, "Exception while creating cache."));
         }

         cache = null;
      }

      return cache;
   }

   String getRootPath() {
      return this.rootPath;
   }

   String getNewFileName() throws CX.EntityCacheException {
      try {
         ++this.fileNameCounter;

         while((new File(getIndexFilePath(this.rootPath, "" + this.fileNameCounter))).exists()) {
            ++this.fileNameCounter;
         }

         try {
            saveFile(this, getCacheFilePath(this.rootPath));
         } catch (Exception var2) {
            this.notifyListener(new Event.FileAccessErrorForCacheEvent(this, getCacheFilePath(this.rootPath), true));
            throw var2;
         }
      } catch (Exception var3) {
         throw new CX.EntityCacheException(var3);
      }

      return "" + this.fileNameCounter;
   }

   protected synchronized void findSpace(CacheEntry ce) throws CX.EntityCacheException {
      if (ce.getSize() > this.maxMemory) {
         if (this.stats != null) {
            this.stats.rejection(ce);
         }

         if (this.sessionStats != null) {
            this.sessionStats.rejection(ce);
         }

         this.notifyListener(new Event.EntryRejectionEvent(this, ce));
         throw new CX.EntryTooLargeMemory(ce, this.maxMemory);
      } else {
         if (this.memoryUsed + ce.getSize() > this.maxMemory) {
            this.purge(ce.getSize() - (this.maxMemory - this.memoryUsed));
         }

      }
   }

   synchronized void purge(long sizeToPurge) throws CX.EntityCacheException {
      Vector toPurge = this.findEntitiesToPurge(sizeToPurge);
      long memoryUsedSave = this.memoryUsed;
      Enumeration e = toPurge.elements();

      while(e.hasMoreElements()) {
         CacheEntry ce = (CacheEntry)e.nextElement();
         ce.purge();
      }

      long sizeOfEntriesPurged = memoryUsedSave - this.memoryUsed;
      long memLossThisTime = sizeOfEntriesPurged - sizeToPurge;
      if (memLossThisTime < 0L) {
         memLossThisTime = 0L;
      }

      this.memLossTotal += memLossThisTime;
      if (this.stats != null) {
         this.stats.memoryPurge((long)toPurge.size(), sizeOfEntriesPurged);
      }

      if (this.sessionStats != null) {
         this.sessionStats.memoryPurge((long)toPurge.size(), sizeOfEntriesPurged);
      }

      this.statsCurrentModification = true;
      this.notifyListener(new Event.MemoryPurgeEvent(this, toPurge));
   }

   protected Vector findEntitiesToPurge(long size) {
      int totalSize = 0;
      Vector toPurge = new Vector();

      for(CacheEntry ce = this.accessList.oldestEntry; ce != null && (long)totalSize < size; ce = ce.prevAccess) {
         long entrySize = ce.getMemorySize();
         if (entrySize != 0L) {
            toPurge.addElement(ce);
            totalSize = (int)((long)totalSize + entrySize);
         }
      }

      return toPurge;
   }

   protected void purgeDisk(long size) throws CX.EntityCacheException {
      this.purgeDisk(size, (CacheEntry)null);
   }

   protected synchronized void purgeDisk(long size, CacheEntry dontPurge) throws CX.EntityCacheException {
      Vector toPurge = this.findDiskEntitiesToPurge(size, dontPurge);
      long diskUsedSave = this.diskUsed;
      Enumeration e = toPurge.elements();

      while(e.hasMoreElements()) {
         CacheEntry ce = (CacheEntry)e.nextElement();
         ce.makeTransient(false);
      }

      long diskLossThisTime = diskUsedSave - this.diskUsed - size;
      this.diskLossTotal += diskUsedSave - this.diskUsed - size;
      if (this.stats != null) {
         this.stats.diskPurge((long)toPurge.size(), diskUsedSave - this.diskUsed);
      }

      if (this.sessionStats != null) {
         this.sessionStats.diskPurge((long)toPurge.size(), diskUsedSave - this.diskUsed);
      }

      this.notifyListener(new Event.DiskPurgeEvent(this, toPurge));
      this.statsCurrentModification = true;
   }

   synchronized void reduceDiskUsed(long diskSize) {
      this.diskUsed -= diskSize;
   }

   synchronized void reduceMemoryUsed(long size) {
      this.memoryUsed -= size;
   }

   synchronized void loadCacheEntry(CacheEntry entry) throws CX.EntityCacheException {
      this.findSpace(entry);
      Serializable key = entry.getKey();
      String path = getDataFilePath(this.rootPath, entry.getFileName());

      try {
         entry.loadBytesCallback(path);
         this.memoryUsed += entry.getMemorySize();
      } catch (CX.FileLoadOutOfMemory var6) {
         Tools.log("Unable to load cache entry data record from disk (too big!): " + key + ". Deleting entry...");
         this.removeEntry(key);
         this.notifyListener(new Event.OutOfMemoryLoadingEntryEvent(this, key, path));
         throw var6;
      } catch (CX.FileLoad var7) {
         Tools.log("Unable to load cache entry data record from disk (corrupted?): " + key + ". Deleting entry...");
         if (canRead(path)) {
            this.notifyListener(new Event.EntryCorruptionEvent(this, key, path));
         } else {
            this.notifyListener(new Event.FileAccessErrorForEntryEvent(this, (CacheEntry)null, path, false));
         }

         this.removeEntry(key);
         throw var7;
      }

      this.notifyListener(new Event.EntryLoadEvent(this, entry));
   }

   protected synchronized Vector findDiskEntitiesToPurge(long size, CacheEntry dontPurge) {
      int totalSize = 0;
      Vector toPurge = new Vector();

      for(CacheEntry ce = this.accessList.oldestEntry; ce != null; ce = ce.prevAccess) {
         if (ce != dontPurge && ce.isPersisted()) {
            if ((long)totalSize >= size) {
               break;
            }

            toPurge.addElement(ce);
            totalSize = (int)((long)totalSize + ce.getDiskSize());
         }
      }

      return toPurge;
   }

   long getBestFitRetryCount() {
      double possibleRetries = this.factorial((long)this.entries.size()) - 1.0;
      long retries = (long)(possibleRetries * this.sizeBias);
      return retries;
   }

   double factorial(long number) {
      long factorial = 1L;

      for(long i = 2L; i < number + 1L; ++i) {
         factorial *= number;
      }

      return (double)factorial;
   }

   static boolean canRead(String path) {
      try {
         return (new File(path)).canRead();
      } catch (SecurityException var2) {
         return false;
      }
   }

   static void saveFile(Serializable obj, String fileName) throws IOException {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(obj);
      oos.flush();
      oos.close();
      fos.close();
   }

   synchronized void deleteEntryData(String fileName, boolean ignoreErrors) {
      String path = null;
      File f = null;
      path = this.getIndexFilePath(fileName);
      f = new File(path);
      if (ignoreErrors) {
         try {
            f.delete();
         } catch (Throwable var7) {
         }
      } else {
         f.delete();
      }

      path = this.getDataFilePath(fileName);
      f = new File(path);
      this.reduceDiskUsed(f.length());
      if (ignoreErrors) {
         try {
            f.delete();
         } catch (Throwable var6) {
         }
      } else {
         f.delete();
      }

   }

   static final Serializable loadFile(String path) throws CX.FileLoadOutOfMemory, CX.FileLoad {
      Serializable obj = null;

      try {
         FileInputStream fis = new FileInputStream(path);
         ObjectInputStream ois = new ObjectInputStream(fis);
         obj = (Serializable)ois.readObject();
         ois.close();
         fis.close();
         return obj;
      } catch (OutOfMemoryError var4) {
         throw new CX.FileLoadOutOfMemory(path, var4);
      } catch (Exception var5) {
         Tools.px(var5);
         throw new CX.FileLoad(path, var5);
      }
   }

   public EntityCacheCumulativeStats registerCumulativeStatsMBean(String name, Statistics stats) throws ManagementException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      String beanName = name + serverRuntime.getName();
      EntityCacheCumulativeStats cumulativeStatsMBean = new EntityCacheCumulativeStats(beanName, serverRuntime, this, stats);
      return cumulativeStatsMBean;
   }

   public void initializeStatisticsMBeans() {
   }

   class AccessList {
      transient AtomicReference mostRecentlyAccessed = new AtomicReference();
      CacheEntry oldestEntry = null;

      boolean isMostRecent(CacheEntry entry) {
         return this.mostRecentlyAccessed.get() == entry;
      }

      synchronized void addMostRecent(CacheEntry ce) {
         CacheEntry currentMostRecent = (CacheEntry)this.mostRecentlyAccessed.get();
         this.mostRecentlyAccessed.set(ce);
         ce.nextAccess = currentMostRecent;
         ce.prevAccess = null;
         if (currentMostRecent != null) {
            currentMostRecent.prevAccess = ce;
         }

         if (this.oldestEntry == null) {
            this.oldestEntry = ce;
         }

      }

      void moveMostRecent(CacheEntry ce) {
         if (this.mostRecentlyAccessed.get() != ce) {
            synchronized(this) {
               if (this.mostRecentlyAccessed.get() != ce) {
                  this.remove(ce);
                  this.addMostRecent(ce);
               }
            }
         }

      }

      synchronized void remove(CacheEntry ce) {
         if (ce.prevAccess != null) {
            ce.prevAccess.nextAccess = ce.nextAccess;
         } else {
            this.mostRecentlyAccessed.set(ce.nextAccess);
         }

         if (ce.nextAccess != null) {
            ce.nextAccess.prevAccess = ce.prevAccess;
         } else {
            this.oldestEntry = ce.prevAccess;
         }

      }

      synchronized void addSorted(CacheEntry newEntry) {
         for(CacheEntry ce = (CacheEntry)this.mostRecentlyAccessed.get(); ce != null; ce = ce.nextAccess) {
            if (newEntry.olderThan(ce)) {
               newEntry.prevAccess = ce.prevAccess;
               ce.prevAccess.nextAccess = newEntry;
               ce.prevAccess = newEntry;
               if (ce.nextAccess == null) {
                  this.oldestEntry = newEntry;
               }

               return;
            }
         }

         if (this.oldestEntry != null) {
            this.oldestEntry.nextAccess = newEntry;
         }

         newEntry.prevAccess = this.oldestEntry;
         this.oldestEntry = newEntry;
      }
   }

   public static class CacheSpec {
      public String name = null;
      public String path = "EntityCache";
      public long memSize = 2000000L;
      public long diskSize = 5000000L;
      int sizeBias = 5;
      public CacheListener cacheListener = null;
      Vector eventsOfInterest = null;
   }
}
