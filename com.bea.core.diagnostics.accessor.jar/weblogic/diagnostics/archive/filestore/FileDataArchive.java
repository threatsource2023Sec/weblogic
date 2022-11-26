package weblogic.diagnostics.archive.filestore;

import com.bea.logging.LogFileRotator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.archive.CustomObjectHandler;
import weblogic.diagnostics.archive.DataArchive;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.utils.PropertyHelper;

public abstract class FileDataArchive extends DataArchive {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final long FIRST_VALID_RECORDID = 1L;
   private File archiveDir;
   private File archiveFile;
   private String archiveFilePattern;
   private RecordParser recordParser;
   private byte[] recordMarker;
   private PersistentStore indexStore;
   private final PersistentStoreConnection metaConnection;
   private final PersistentStoreConnection dataConnection;
   private File[] rotatedFiles;
   private FileIndexMetaInfo[] rotatedIndexArr;
   private FileIndexMetaInfo currentMetaInfo;
   private Object indexMutex = new Object();
   private int rotationCount;
   private int indexedRotationCount = -1;
   private Object rotationMutex = new Object();
   private Object storeMutex = new Object();
   private List accessIterators = new ArrayList();
   private boolean hasTimestamps;
   private long lastKnownRecordId = 1L;
   private String internalName;
   private int indexCycleCount;
   private long indexTime;
   private int incrementalIndexCycleCount;
   private long incrementalIndexTime;
   private static final ObjectHandler CUSTOM_OBJECT_HANDLER = new CustomObjectHandler();
   private Object idLock = new Object();

   public FileDataArchive(String name, ColumnInfo[] columns, File archiveFile, File archiveDir, File indexStoreDir, RecordParser recordParser, byte[] recordMarker, boolean hasTimestamps, boolean readOnly) throws IOException, ManagementException {
      super(name, columns, readOnly);
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      this.internalName = this.getName() + "-" + cic.getPartitionId();
      archiveFile = archiveFile.getCanonicalFile();
      if (archiveDir == null) {
         archiveDir = archiveFile.getParentFile();
      }

      archiveDir = archiveDir.getCanonicalFile();
      indexStoreDir = indexStoreDir.getCanonicalFile();
      this.archiveDir = archiveDir;
      this.archiveFile = archiveFile;
      this.archiveFilePattern = archiveFile.getName();
      this.recordParser = recordParser;
      this.recordMarker = recordMarker;
      this.hasTimestamps = hasTimestamps;
      File parent = archiveFile.getParentFile();
      String fname = this.archiveFilePattern.replaceAll("%", "");
      if (parent == null) {
         this.archiveFile = new File(fname);
      } else {
         this.archiveFile = new File(parent, fname);
      }

      this.archiveFile = this.archiveFile.getCanonicalFile();
      if (indexStoreDir != null) {
         try {
            this.indexStore = DiagnosticStoreRepository.getInstance().getStore(indexStoreDir.getPath());
            this.metaConnection = this.createConnection("meta." + this.getInternalName());
            this.dataConnection = this.createConnection("data." + this.getInternalName());
         } catch (PersistentStoreException var16) {
            IOException ioe = new IOException(var16.getMessage());
            ioe.initCause(var16);
            throw ioe;
         }

         this.initializeIndexArray();
         if (!readOnly && !PropertyHelper.getBoolean("_Offline_FileDataArchive")) {
            Logger logger = LogFileRotator.getLogRotationLogger();
            logger.addHandler(new LogRotationHandler(this));
         }

         this.registerRuntimeMBean();
      } else {
         throw new IOException("Missing indexStoreDir directory");
      }
   }

   private String getInternalName() {
      return this.internalName;
   }

   private PersistentStoreConnection createConnection(String name) throws PersistentStoreException {
      if (this.indexStore == null) {
         return null;
      } else {
         name = "weblogic.diagnostics." + name;
         PersistentStoreConnection conn = this.indexStore.createConnection(name, CUSTOM_OBJECT_HANDLER);
         return conn;
      }
   }

   Object getStoreLock() {
      return this.storeMutex;
   }

   RecordParser getRecordParser() {
      return this.recordParser;
   }

   byte[] getRecordMarker() {
      return this.recordMarker;
   }

   File getArchiveDirectory() {
      return this.archiveDir;
   }

   File getArchiveFile() {
      return this.archiveFile;
   }

   boolean isIndexed() {
      return this.indexStore != null;
   }

   private File[] getRotatedFiles() {
      File[] files = this.archiveDir.listFiles(new LogFileFilter(this.archiveFilePattern));
      if (files == null) {
         files = new File[0];
      }

      for(int i = 0; i < files.length; ++i) {
         try {
            files[i] = files[i].getCanonicalFile();
         } catch (IOException var4) {
         }
      }

      Arrays.sort(files, new FileModtimeComparator());
      return files;
   }

   private void initializeIndexArray() {
      if (this.indexStore != null) {
         Map indexMap = this.identifySavedIndices();
         indexMap.remove(this.archiveFile);
         int size = indexMap.size();
         FileIndexMetaInfo[] indexArr = null;
         if (size > 0) {
            indexArr = new FileIndexMetaInfo[size];
            indexArr = (FileIndexMetaInfo[])((FileIndexMetaInfo[])indexMap.values().toArray(indexArr));
            Arrays.sort(indexArr);
         }

         this.activateIndexInfo(indexArr, (FileIndexMetaInfo)null);
      }
   }

   public void printIndex() {
      int size = this.rotatedIndexArr != null ? this.rotatedIndexArr.length : 0;

      for(int i = 0; i < size; ++i) {
         this.rotatedIndexArr[i].printIndex(this);
      }

   }

   private Map identifySavedIndices() {
      ArrayList indexList = new ArrayList();
      ArrayList staleIndexList = new ArrayList();
      boolean foundInvalidIndex = false;
      Map indexMap = new HashMap();
      synchronized(this.getStoreLock()) {
         try {
            PersistentStoreConnection conn = this.getMetaConnection();
            PersistentStoreConnection.Cursor cursor = conn.createCursor(0);
            PersistentStoreRecord record = null;

            FileIndexMetaInfo metaInfo;
            while((record = cursor.next()) != null) {
               metaInfo = (FileIndexMetaInfo)record.getData();
               metaInfo.setIndexHandle(record.getHandle());
               this.setLastKnownRecordId(metaInfo.getBaseRecordId() + metaInfo.getRecordCount());

               try {
                  if (!this.archiveFile.equals(metaInfo.getArchiveFile())) {
                     foundInvalidIndex = true;
                     if (DEBUG.isDebugEnabled()) {
                        DebugLogger.println("identifySavedIndices: found invalid or stale index: " + metaInfo);
                     }
                  }
               } catch (Exception var14) {
                  foundInvalidIndex = true;
                  var14.printStackTrace();
               }

               File dataFile = metaInfo.getDataFile();
               File rotationDir = dataFile.getParentFile();
               boolean isStale = dataFile == null || !dataFile.exists();
               if (!rotationDir.equals(this.archiveDir)) {
                  isStale = true;
               }

               if (DEBUG.isDebugEnabled()) {
                  DebugLogger.println("identifySavedIndices: index-meta " + metaInfo + " isStale=" + isStale);
               }

               if (isStale) {
                  staleIndexList.add(metaInfo);
               } else {
                  indexList.add(metaInfo);
                  indexMap.put(metaInfo.getDataFile(), metaInfo);
               }
            }

            metaInfo = (FileIndexMetaInfo)indexMap.get(this.archiveFile);
            indexMap.remove(this.archiveFile);
            this.setCurrentMetaInfo((FileIndexMetaInfo)null);
            if (metaInfo != null) {
               metaInfo.delete(this);
            }
         } catch (PersistentStoreException var15) {
            DiagnosticsLogger.logCreateLogIndexError(this.archiveFile.toString(), var15);
         }
      }

      Iterator it = staleIndexList.iterator();

      FileIndexMetaInfo metaInfo;
      while(it.hasNext()) {
         metaInfo = (FileIndexMetaInfo)it.next();
         this.deleteMetaInfo(metaInfo);
      }

      if (foundInvalidIndex) {
         it = indexList.iterator();

         while(it.hasNext()) {
            metaInfo = (FileIndexMetaInfo)it.next();
            this.deleteMetaInfo(metaInfo);
         }

         indexMap = new HashMap();
      }

      return indexMap;
   }

   private void deleteMetaInfo(FileIndexMetaInfo metaInfo) {
      synchronized(this.getStoreLock()) {
         try {
            if (metaInfo != null && !this.archiveFile.equals(metaInfo.getDataFile())) {
               metaInfo.delete(this);
            }
         } catch (PersistentStoreException var5) {
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("Failed to delete stale index entry: " + metaInfo);
               var5.printStackTrace();
            }
         }

      }
   }

   private void computeFullIndices() {
      if (this.indexStore != null && !this.isReadOnly()) {
         FileIndexMetaInfo[] indexArr = null;
         this.rotatedFiles = this.getRotatedFiles();
         Map indexMap = this.identifySavedIndices();
         File[] files = this.rotatedFiles;
         int size = files != null ? files.length : 0;
         long baseRecordId = this.currentMetaInfo != null ? this.currentMetaInfo.getBaseRecordId() : this.lastKnownRecordId;
         boolean first = true;

         for(int i = 0; i < size; ++i) {
            FileIndexMetaInfo mInfo = (FileIndexMetaInfo)indexMap.get(files[i]);
            if (mInfo == null) {
               try {
                  if (files[i].exists()) {
                     FileIndexMetaInfo metaInfo = new FileIndexMetaInfo(this.archiveFile, files[i], baseRecordId);
                     metaInfo.buildIndex(this);
                     indexMap.put(metaInfo.getDataFile(), metaInfo);
                     mInfo = metaInfo;
                     first = false;
                  }
               } catch (Exception var15) {
                  if (files[i].exists()) {
                     DiagnosticsLogger.logCreateLogIndexError(files[i].toString(), var15);
                  }
               }
            } else {
               if (first) {
                  baseRecordId = mInfo.getBaseRecordId();
               }

               first = false;

               try {
                  if (baseRecordId != mInfo.getBaseRecordId()) {
                     synchronized(this.getStoreLock()) {
                        mInfo.setBaseRecordId(baseRecordId);
                        mInfo.writeMetaInfo(this);
                     }
                  }

                  if (mInfo.isReindexingNeeded()) {
                     mInfo.buildIndex(this);
                  }
               } catch (Exception var14) {
                  DiagnosticsLogger.logCreateLogIndexError(files[i].toString(), var14);
               }
            }

            if (mInfo != null) {
               baseRecordId += mInfo.getRecordCount();
               this.setLastKnownRecordId(baseRecordId);
            }
         }

         FileIndexMetaInfo metaInfo = null;

         try {
            metaInfo = this.buildCurrentIndex(baseRecordId);
         } catch (Exception var12) {
            DiagnosticsLogger.logCreateLogIndexError(this.archiveFile.toString(), var12);
         }

         size = indexMap.size();
         indexArr = new FileIndexMetaInfo[size];
         indexArr = (FileIndexMetaInfo[])((FileIndexMetaInfo[])indexMap.values().toArray(indexArr));
         Arrays.sort(indexArr);
         this.activateIndexInfo(indexArr, metaInfo);
         this.captureIndexedRotationCount();
      }
   }

   private void activateIndexInfo(FileIndexMetaInfo[] indexArr, FileIndexMetaInfo metaInfo) {
      synchronized(this.getStoreLock()) {
         this.rotatedIndexArr = indexArr;
         this.setCurrentMetaInfo(metaInfo);
      }
   }

   private void setCurrentMetaInfo(FileIndexMetaInfo metaInfo) {
      synchronized(this.getStoreLock()) {
         this.currentMetaInfo = metaInfo;
      }
   }

   private FileIndexMetaInfo buildCurrentIndex(long baseRecordId) throws Exception {
      FileIndexMetaInfo metaInfo = this.currentMetaInfo;
      if (!this.archiveFile.exists()) {
         return null;
      } else {
         boolean isDeleted = false;

         try {
            if (metaInfo == null) {
               metaInfo = new FileIndexMetaInfo(this.archiveFile, this.archiveFile, baseRecordId);
            }

            if (metaInfo.isReindexingNeeded()) {
               metaInfo.buildIndex(this);
            }

            this.setLastKnownRecordId(metaInfo.getBaseRecordId() + metaInfo.getRecordCount());
         } catch (FileNotFoundException var6) {
         } catch (IOException var7) {
            isDeleted = metaInfo.isDeleted();
            if (!isDeleted) {
               throw var7;
            }
         }

         isDeleted |= metaInfo.isDeleted();
         if (isDeleted) {
            metaInfo.delete(this);
            return null;
         } else {
            return metaInfo;
         }
      }
   }

   PersistentStoreConnection getMetaConnection() {
      return this.metaConnection;
   }

   PersistentStoreConnection getDataConnection() {
      return this.dataConnection;
   }

   PersistentStore getIndexStore() {
      return this.indexStore;
   }

   private FileOffset earliestOffset() {
      File f = this.archiveFile;
      long recId = this.currentMetaInfo != null ? this.currentMetaInfo.getBaseRecordId() : (this.indexStore != null ? this.lastKnownRecordId : 1L);
      boolean found = false;
      if (this.rotatedFiles != null && this.rotatedFiles.length > 0) {
         f = this.rotatedFiles[0];
         int indexCount = this.rotatedIndexArr != null ? this.rotatedIndexArr.length : 0;

         for(int i = 0; !found && i < indexCount; ++i) {
            File dataFile = this.rotatedIndexArr[i].getDataFile();
            if (f.equals(dataFile)) {
               recId = this.rotatedIndexArr[i].getBaseRecordId();
               found = true;
            }
         }
      }

      return new FileOffset(f, 0L, recId);
   }

   FileOffset findOffset(long value, boolean useTimestamp) throws IOException {
      synchronized(this.indexMutex) {
         this.ensureRotatedIndexArray();
         if (useTimestamp && !this.hasTimestamps) {
            return this.earliestOffset();
         } else {
            FileIndexMetaInfo index = this.findIndex(value, useTimestamp);
            if (index == null) {
               return this.earliestOffset();
            } else {
               long high = index.getHighTimestamp();
               long low = index.getLowTimestamp();
               if (!useTimestamp) {
                  high = index.getRecordCount();
                  low = 0L;
                  value -= index.getBaseRecordId();
               }

               if (value >= high) {
                  value = high;
               }

               long offset = 0L;
               FileOffset fileOffset = null;
               if (value > low) {
                  if (useTimestamp) {
                     fileOffset = index.findOffsetByTimestamp(this, value);
                  } else {
                     fileOffset = index.findOffsetByRecordId(this, value);
                  }
               }

               if (fileOffset == null) {
                  fileOffset = new FileOffset(index.getDataFile(), 0L, index.getBaseRecordId());
               }

               return fileOffset;
            }
         }
      }
   }

   private void ensureRotatedIndexArray() {
      if (this.rotatedFiles == null || this.rotatedFiles.length == 0 || this.isRotationHappened()) {
         this.rotatedFiles = this.getRotatedFiles();
      }

      if (this.indexStore != null) {
         if (this.isRotationHappened()) {
            this.computeFullIndices();
         }

      }
   }

   FileOffset getNextOffset(FileOffset offset) {
      if (offset == null) {
         return null;
      } else {
         File file = offset.getFile();
         if (file.equals(this.archiveFile)) {
            return null;
         } else {
            long recordId = offset.getRecordId();
            File[] files = this.getRotatedFiles();
            int size = files != null ? files.length : 0;

            for(int i = 0; i < size; ++i) {
               if (file.equals(files[i])) {
                  if (i < size - 1) {
                     return new FileOffset(files[i + 1], 0L, recordId);
                  }

                  return new FileOffset(this.archiveFile, 0L, recordId);
               }
            }

            return null;
         }
      }
   }

   private FileIndexMetaInfo findIndex(long value, boolean useTimestamp) {
      synchronized(this.indexMutex) {
         if (this.indexStore == null) {
            return null;
         } else if (!this.isRotationHappened() && this.currentMetaInfo != null && !this.currentMetaInfo.isDeleted() && this.currentMetaInfo.getTuples() > 0 && value >= this.getIndexLowValue(this.currentMetaInfo, useTimestamp)) {
            if (DEBUG.isDebugEnabled()) {
               DebugLogger.println("findIndex: thread=<" + Thread.currentThread().getName() + ">  returning currentMetaInfo=" + this.currentMetaInfo);
            }

            return this.currentMetaInfo;
         } else {
            int size = this.rotatedIndexArr != null ? this.rotatedIndexArr.length : 0;

            int i;
            FileIndexMetaInfo metaInfo;
            for(i = size - 1; i >= 0; --i) {
               metaInfo = this.rotatedIndexArr[i];
               if (!metaInfo.isDeleted() && metaInfo.getTuples() > 0 && value >= this.getIndexLowValue(metaInfo, useTimestamp)) {
                  return metaInfo;
               }
            }

            for(i = 0; i < size; ++i) {
               metaInfo = this.rotatedIndexArr[i];
               if (!metaInfo.isDeleted()) {
                  return metaInfo;
               }
            }

            return this.isRotationHappened() ? null : this.currentMetaInfo;
         }
      }
   }

   private long getIndexLowValue(FileIndexMetaInfo metaInfo, boolean useTimestamp) {
      return useTimestamp ? metaInfo.getLowTimestamp() : metaInfo.getBaseRecordId();
   }

   public long getEarliestAvailableTimestamp() {
      if (!this.hasTimestamps) {
         return -1L;
      } else {
         long ts = -1L;

         try {
            Iterator it = new FileRecordIterator(this, 0L, Long.MAX_VALUE, "");
            if (it.hasNext()) {
               DataRecord rec = (DataRecord)it.next();
               long t0 = this.recordParser.getTimestamp(rec);
               if (t0 > 0L) {
                  ts = t0;
               }
            }
         } catch (QueryException var7) {
            UnexpectedExceptionHandler.handle("Unexcpected exception in getEarliestAvailableTimestamp", var7);
         }

         return ts;
      }
   }

   public long getLatestAvailableTimestamp() {
      if (!this.hasTimestamps) {
         return -1L;
      } else {
         FileIndexMetaInfo index = this.findIndex(Long.MAX_VALUE, true);
         if (index == null) {
            return -1L;
         } else {
            long ts = -1L;

            try {
               long startTime = index.getHighTimestamp();
               Iterator it = new FileRecordIterator(this, startTime, Long.MAX_VALUE, "");

               while(it.hasNext()) {
                  DataRecord rec = (DataRecord)it.next();
                  long t0 = this.recordParser.getTimestamp(rec);
                  if (t0 > 0L && t0 > ts) {
                     ts = t0;
                  }
               }
            } catch (QueryException var10) {
               UnexpectedExceptionHandler.handle("Unexcpected exception in getLatestAvailableTimestamp", var10);
            }

            return ts;
         }
      }
   }

   private void incrementRotationCount() {
      synchronized(this.rotationMutex) {
         ++this.rotationCount;
      }
   }

   private boolean isRotationHappened() {
      synchronized(this.rotationMutex) {
         return this.rotationCount != this.indexedRotationCount;
      }
   }

   private void captureIndexedRotationCount() {
      synchronized(this.rotationMutex) {
         this.indexedRotationCount = this.rotationCount;
      }
   }

   void preRotate() {
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("Before file rotation: " + this.getInternalName());
      }

   }

   void postRotate(File fromFile, File toFile) {
      if (DEBUG.isDebugEnabled()) {
         DebugLogger.println("After file rotation: " + this.getInternalName() + " " + fromFile + " to " + toFile);
      }

      this.realignAccessIterators(fromFile, toFile);
      synchronized(this.getStoreLock()) {
         try {
            if (this.currentMetaInfo != null) {
               this.currentMetaInfo.setDataFile(toFile);
               this.currentMetaInfo.buildIndex(this);
               this.currentMetaInfo.writeMetaInfo(this);
               this.setCurrentMetaInfo((FileIndexMetaInfo)null);
            }
         } catch (Exception var6) {
            UnexpectedExceptionHandler.handle("Failed to delete current info for " + this.archiveFile.toString(), var6);
         }
      }

      this.incrementRotationCount();
   }

   private void realignAccessIterators(File fromFile, File toFile) {
      synchronized(this.accessIterators) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("Realigning " + this.accessIterators.size() + " iterators for: " + this.getInternalName());
         }

         Iterator it = this.accessIterators.iterator();

         while(it.hasNext()) {
            WeakReference ref = (WeakReference)it.next();
            FileRecordIterator recordIterator = (FileRecordIterator)ref.get();
            if (recordIterator != null) {
               recordIterator.realign(fromFile, toFile);
            }
         }

      }
   }

   void addAccessIterator(FileRecordIterator recordIterator) {
      synchronized(this.accessIterators) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("Adding access iterator " + recordIterator + " to " + this.getInternalName());
         }

         WeakReference ref = this.findAccessIterator(recordIterator);
         if (ref == null) {
            this.accessIterators.add(new WeakReference(recordIterator));
         }

      }
   }

   void removeAccessIterator(FileRecordIterator recordIterator) {
      synchronized(this.accessIterators) {
         if (DEBUG.isDebugEnabled()) {
            DebugLogger.println("Removing access iterator " + recordIterator + " from " + this.getInternalName());
         }

         WeakReference ref = this.findAccessIterator(recordIterator);
         if (ref != null) {
            this.accessIterators.remove(ref);
         }

      }
   }

   private WeakReference findAccessIterator(FileRecordIterator recordIterator) {
      Iterator it = this.accessIterators.iterator();

      WeakReference ref;
      do {
         if (!it.hasNext()) {
            return null;
         }

         ref = (WeakReference)it.next();
      } while(ref.get() != recordIterator);

      return ref;
   }

   public Iterator getDataRecords(String query) throws QueryException, DiagnosticDataAccessException {
      return this.getDataRecords(0L, Long.MAX_VALUE, query);
   }

   public Iterator getDataRecords(long startTime, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      if (!this.hasTimestamps && (startTime != 0L || endTime != Long.MAX_VALUE)) {
         throw new UnsupportedOperationException("Can not specify time interval with archive: " + this.getInternalName());
      } else {
         return new FileRecordIterator(this, startTime, endTime, query);
      }
   }

   public Iterator getDataRecords(long startRecordId, long endrecordId, long endTime, String query) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      if (!this.hasTimestamps) {
         endTime = Long.MAX_VALUE;
      }

      return new FileRecordIterator(this, startRecordId, endrecordId, endTime, query);
   }

   public int getRotatedFilesCount() {
      return this.rotatedFiles != null ? this.rotatedFiles.length : 0;
   }

   public int getIndexCycleCount() {
      return this.indexCycleCount;
   }

   public long getIndexTime() {
      return this.indexTime;
   }

   public int getIncrementalIndexCycleCount() {
      return this.incrementalIndexCycleCount;
   }

   public long getIncrementalIndexTime() {
      return this.incrementalIndexTime;
   }

   public void close() throws DiagnosticDataAccessException, ManagementException {
      if (this.archiveRuntime != null) {
         this.unregisterRuntimeMBean();
      } else {
         try {
            DiagnosticStoreRepository.getInstance().close();
         } catch (Exception var2) {
            throw new DiagnosticDataAccessException(var2);
         }
      }

      this.isClosed = true;
   }

   public long getLatestKnownRecordID() throws DiagnosticDataAccessException, UnsupportedOperationException {
      synchronized(this.indexMutex) {
         FileIndexMetaInfo lastMetaInfo = this.getLastIndexMetaInfo();
         if (lastMetaInfo == null) {
            return 0L;
         } else {
            try {
               long id = lastMetaInfo.getLatestKnownRecordID(this);
               Iterator it = new FileRecordIterator(this, id, Long.MAX_VALUE, Long.MAX_VALUE, "");

               while(it.hasNext()) {
                  DataRecord rec = (DataRecord)it.next();
                  Object o = rec.get(0);
                  if (o instanceof Long) {
                     id = (Long)o;
                  }
               }

               long var10000 = id;
               return var10000;
            } catch (Exception var9) {
               throw new DiagnosticDataAccessException(var9);
            }
         }
      }
   }

   private FileIndexMetaInfo getLastIndexMetaInfo() {
      synchronized(this.indexMutex) {
         if (this.currentMetaInfo != null) {
            return this.currentMetaInfo;
         } else {
            int size = this.rotatedIndexArr != null ? this.rotatedIndexArr.length : 0;
            return size > 0 ? this.rotatedIndexArr[size - 1] : null;
         }
      }
   }

   public boolean isTimestampAvailable() {
      return this.hasTimestamps;
   }

   long getLastKnownRecordId() {
      return this.lastKnownRecordId;
   }

   void setLastKnownRecordId(long value) {
      synchronized(this.idLock) {
         if (value > this.lastKnownRecordId) {
            this.lastKnownRecordId = value;
         }

      }
   }
}
