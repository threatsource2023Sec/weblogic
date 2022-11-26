package weblogic.diagnostics.archive.wlstore;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.common.CompletionRequest;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticDataAccessException;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.archive.CustomObjectHandler;
import weblogic.diagnostics.archive.DataRetirementTaskImpl;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.archive.EditableDataArchive;
import weblogic.diagnostics.archive.QueryEvaluator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.management.ManagementException;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.StoreWritePolicy;
import weblogic.utils.collections.SecondChanceCacheMap;

public abstract class PersistentStoreDataArchive extends EditableDataArchive implements DataWriter {
   private static final int PAGE_SIZE = 512;
   private static final int CACHE_SIZE = 8;
   private static final int CURRENT_PAGE_INDEX = -1;
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private static final DebugLogger DEBUG_RETIREMENT = DebugLogger.getDebugLogger("DebugDiagnosticArchiveRetirement");
   private static final String SCHEMA_CONNECTION_NAME = "weblogic.diagnostics.archiveschemas";
   public static final int RECORDID_INDEX = 0;
   public static final int TIMESTAMP_INDEX = 1;
   private static final int RETIREMENT_MIN_LIMIT = 10000;
   private String storeName;
   private String storeDirectory;
   private PersistentStore persistentStore;
   private final PersistentStoreConnection dataConnection;
   private final PersistentStoreConnection objectConnection;
   private final PersistentStoreConnection indexConnection;
   private final PersistentStoreConnection metaConnection;
   private PersistentHandle lastRecordHandle;
   private RecordWrapper lastRecord;
   private long lastRecordId;
   private IndexEntry[] currentIndexPage;
   private int currentEntryCount;
   private IndexMetaInfo[] indexMetaInfoArray;
   private StoreWritePolicy storeWritePolicy;
   private static int connectionWritePolicy = 0;
   private boolean allowUpdates;
   private long earliestKnownTimestamp;
   private long latestKnownTimestamp;
   private long insertionCount;
   private long insertionTime;
   private long deletionCount;
   private long deletionTime;
   private HashMap persistedAttributeMap;
   private static final String ATTR_RECORDCOUNT = "ATTR_RECORD_COUNT";
   private static final String ATTR_RECORDCOUNT_AT_LAST_RETIREMENT = "ATTR_RECORD_COUNT_AT_LAST_RETIREMENT";
   private static final String[] PERSISTED_ATTRIBUTE_NAMES = new String[]{"ATTR_RECORD_COUNT", "ATTR_RECORD_COUNT_AT_LAST_RETIREMENT"};
   private SecondChanceCacheMap indexPageCache;
   private static final ObjectHandler CUSTOM_OBJECT_HANDLER = new CustomObjectHandler();
   private static DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();

   public PersistentStoreDataArchive(String name, ColumnInfo[] columns, String storeName, String storeDirectory, boolean readOnly) throws PersistentStoreException, ManagementException {
      this(name, columns, storeName, storeDirectory, false, readOnly);
   }

   public PersistentStoreDataArchive(String name, ColumnInfo[] columns, String storeName, String storeDirectory, boolean allowUpdates, boolean readOnly) throws PersistentStoreException, ManagementException {
      super(name, getArchiveSchema(name, columns, storeDirectory), readOnly);
      this.storeWritePolicy = StoreWritePolicy.DISABLED;
      this.earliestKnownTimestamp = -1L;
      this.latestKnownTimestamp = Long.MAX_VALUE;
      this.persistedAttributeMap = new HashMap();
      this.storeName = storeName;
      this.storeDirectory = storeDirectory;
      this.allowUpdates = allowUpdates;
      this.enableIndexPageCache(true);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Attempting to get diagnostic store from " + storeDirectory);
      }

      this.persistentStore = DiagnosticStoreRepository.getInstance().getStore(storeDirectory);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Using diagnostic store: " + this.persistentStore);
      }

      this.dataConnection = this.createConnection("data." + name);
      this.objectConnection = this.createConnection("object." + name);
      this.indexConnection = this.createConnection("index." + name);
      this.metaConnection = this.createConnection("meta." + name);
      if (this.persistentStore != null) {
         this.readIndexInfo();
         this.registerRuntimeMBean();
      }
   }

   private PersistentStoreConnection createConnection(String name) throws PersistentStoreException {
      if (this.persistentStore == null) {
         return null;
      } else {
         name = "weblogic.diagnostics." + name;
         PersistentStoreConnection conn = this.persistentStore.createConnection(name, CUSTOM_OBJECT_HANDLER);
         return conn;
      }
   }

   private static synchronized ColumnInfo[] getArchiveSchema(String name, ColumnInfo[] columns, String storeDirectory) throws PersistentStoreException {
      ArchiveSchema archiveSchema = null;

      try {
         PersistentStore store = DiagnosticStoreRepository.getInstance().getStore(storeDirectory);
         if (store == null) {
            return columns;
         }

         PersistentStoreConnection conn = store.createConnection("weblogic.diagnostics.archiveschemas", CUSTOM_OBJECT_HANDLER);
         PersistentStoreConnection.Cursor cursor = conn.createCursor(0);
         PersistentStoreRecord record = null;
         PersistentHandle schemaHandle = null;

         while(archiveSchema == null && (record = cursor.next()) != null) {
            ArchiveSchema schema = (ArchiveSchema)record.getData();
            if (name.equals(schema.name)) {
               archiveSchema = schema;
               schemaHandle = record.getHandle();
            }
         }

         if (archiveSchema != null && schemaHandle != null) {
            if (columns != null) {
               ColumnInfo[] columns_old = archiveSchema.columns;
               int columns_count_old = columns_old != null ? columns_old.length : 0;
               int columns_count_new = columns.length;
               if (columns_count_new < columns_count_old) {
                  throw new PersistentStoreException("Schema size mismatch for archive " + name + " previous-size=" + columns_count_old + " new-size=" + columns_count_new);
               }

               for(int i = 0; i < columns_count_old; ++i) {
                  ColumnInfo c_new = columns[i];
                  ColumnInfo c_old = columns_old[i];
                  if (!c_old.equals(c_new)) {
                     throw new PersistentStoreException("Schema column " + i + " mismatch " + c_old + " != " + c_new + " for archive " + name);
                  }
               }

               if (columns_count_new > columns_count_old) {
                  archiveSchema.columns = columns;
                  PersistentStoreTransaction tx = store.begin();
                  conn.update(tx, schemaHandle, archiveSchema, connectionWritePolicy);
                  tx.commit();
               }
            }
         } else {
            if (columns == null) {
               throw new PersistentStoreException("Schema not found for archive " + name);
            }

            archiveSchema = new ArchiveSchema();
            archiveSchema.name = name;
            archiveSchema.columns = columns;
            PersistentStoreTransaction tx = store.begin();
            conn.create(tx, archiveSchema, connectionWritePolicy);
            tx.commit();
         }
      } catch (Exception var15) {
         if (var15 instanceof PersistentStoreException) {
            throw (PersistentStoreException)var15;
         }

         throw new PersistentStoreException(var15);
      }

      return archiveSchema.columns;
   }

   private void readIndexInfo() throws PersistentStoreException {
      PersistentStore store = this.getPersistentStore();
      PersistentStoreConnection conn = this.getMetaConnection();
      PersistentStoreConnection.Cursor cursor = conn.createCursor(0);
      PersistentStoreRecord record = null;
      List list = new ArrayList();
      List handlesList = new ArrayList();

      while((record = cursor.next()) != null) {
         Object rec = record.getData();
         PersistentHandle metaHandle = record.getHandle();
         if (rec instanceof PersistedAttribute) {
            PersistedAttribute persistedAttr = (PersistedAttribute)rec;
            persistedAttr.setHandle(metaHandle);
            this.persistedAttributeMap.put(persistedAttr.key, persistedAttr);
         } else {
            IndexMetaInfo metaInfo = (IndexMetaInfo)rec;
            metaInfo.setMetaHandle(metaHandle);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("readIndexInfo: index-meta " + metaInfo);
            }

            list.add(metaInfo);
            handlesList.add(metaHandle);
         }
      }

      this.initializePersistedAttributes(conn);

      for(int i = 0; i < list.size(); ++i) {
         IndexMetaInfo metaInfo = (IndexMetaInfo)list.get(i);
         PersistentHandle metaHandle = (PersistentHandle)handlesList.get(i);
         if (!metaInfo.hasBoundsInfo()) {
            PersistentHandle indexPageHandle = metaInfo.getIndexPageHandle();
            IndexEntry[] page = (IndexEntry[])((IndexEntry[])this.readRecord(this.indexConnection, indexPageHandle));
            IndexEntry firstEntry = page[0];
            IndexEntry lastEntry = page[page.length - 1];
            metaInfo.setLowTimestamp(firstEntry.timestamp);
            metaInfo.setHighTimestamp(lastEntry.timestamp);
            metaInfo.setLowRecordId(firstEntry.recordId);
            metaInfo.setHighRecordId(lastEntry.recordId);
            PersistentStoreTransaction tx = store.begin();
            conn.update(tx, metaHandle, metaInfo, connectionWritePolicy);
            tx.commit();
         }
      }

      this.indexMetaInfoArray = new IndexMetaInfo[list.size()];
      this.indexMetaInfoArray = (IndexMetaInfo[])((IndexMetaInfo[])list.toArray(this.indexMetaInfoArray));
      Arrays.sort(this.indexMetaInfoArray);
      if (this.indexMetaInfoArray.length > 0) {
         this.earliestKnownTimestamp = this.indexMetaInfoArray[0].getLowTimestamp();
         this.latestKnownTimestamp = this.indexMetaInfoArray[this.indexMetaInfoArray.length - 1].getHighTimestamp();
      }

      this.createNewIndexPage();
      this.recreateUnfinishedIndexPage();
   }

   private void initializePersistedAttributes(PersistentStoreConnection conn) throws PersistentStoreException {
      PersistentStore store = this.getPersistentStore();

      for(int i = 0; i < PERSISTED_ATTRIBUTE_NAMES.length; ++i) {
         String key = PERSISTED_ATTRIBUTE_NAMES[i];
         PersistedAttribute pAttr = (PersistedAttribute)this.persistedAttributeMap.get(key);
         if (pAttr == null) {
            pAttr = new PersistedAttribute();
            pAttr.setKey(key);
            PersistentStoreTransaction tx = store.begin();
            PersistentHandle handle = conn.create(tx, pAttr, connectionWritePolicy);
            tx.commit();
            pAttr.setHandle(handle);
            this.persistedAttributeMap.put(key, pAttr);
         }
      }

      long lastRecAtRetirement = this.getRecordCountAtLastRetirement();
      if (lastRecAtRetirement <= 0L) {
         this.setRecordCountAtLastRetirement(this.getRecordCount());
      }

   }

   private void recreateUnfinishedIndexPage() {
      PersistentHandle firstRecordHandle = null;
      this.lastRecordHandle = null;
      this.lastRecord = null;

      try {
         RecordWrapper record;
         if (this.indexMetaInfoArray.length > 0) {
            PersistentHandle indexPageHandle = this.indexMetaInfoArray[this.indexMetaInfoArray.length - 1].getIndexPageHandle();
            IndexEntry[] page = (IndexEntry[])((IndexEntry[])this.readRecord(this.indexConnection, indexPageHandle));
            IndexEntry entry = page[page.length - 1];
            this.lastRecordHandle = entry.recordHandle;
            this.lastRecord = (RecordWrapper)this.readRecord(this.dataConnection, this.lastRecordHandle);
            firstRecordHandle = this.lastRecord.next;
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("recreateUnfinishedIndexPage: lastRecord: " + this.lastRecord);
            }
         } else {
            firstRecordHandle = this.getRandomRecordHandle(this.dataConnection);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("recreateUnfinishedIndexPage random firstRecordHandle: " + firstRecordHandle);
            }

            if (firstRecordHandle != null) {
               for(record = (RecordWrapper)this.readRecord(this.dataConnection, firstRecordHandle); record != null && record.prev != null; record = (RecordWrapper)this.readRecord(this.dataConnection, firstRecordHandle)) {
                  firstRecordHandle = record.prev;
               }
            }
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("recreateUnfinishedIndexPage: firstRecordHandle=" + firstRecordHandle);
         }

         while(firstRecordHandle != null) {
            record = (RecordWrapper)this.readRecord(this.dataConnection, firstRecordHandle);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("recreateUnfinishedIndexPage: record=" + record);
            }

            this.insertIntoIndex(record.startId, record.timestamp, firstRecordHandle);
            if (this.earliestKnownTimestamp < 0L) {
               this.earliestKnownTimestamp = record.timestamp;
            }

            this.latestKnownTimestamp = record.timestamp;
            this.lastRecord = record;
            this.lastRecordHandle = firstRecordHandle;
            firstRecordHandle = record.next;
         }

         if (this.lastRecord != null) {
            this.lastRecordId = this.lastRecord.endId;
         }
      } catch (Exception var5) {
         DiagnosticsLogger.logIndexInitializationError(var5);
      }

   }

   private synchronized void enableIndexPageCache(boolean enable) {
      this.indexPageCache = enable ? new SecondChanceCacheMap(8) : null;
   }

   RecordWrapper readRecord(PersistentHandle recordHandle) throws PersistentStoreException {
      return (RecordWrapper)this.readRecord(this.dataConnection, recordHandle, true);
   }

   Object readRecordObject(PersistentHandle recordHandle) throws PersistentStoreException {
      return this.readRecord(this.objectConnection, recordHandle, false);
   }

   private synchronized Object readRecord(PersistentStoreConnection conn, PersistentHandle recordHandle) throws PersistentStoreException {
      return this.readRecord(conn, recordHandle, true);
   }

   private synchronized Object readRecord(PersistentStoreConnection conn, PersistentHandle recordHandle, boolean readFailureFatal) throws PersistentStoreException {
      if (recordHandle == null) {
         return null;
      } else {
         PersistentStore store = this.getPersistentStore();
         PersistentStoreTransaction tx = store.begin();
         CompletionRequest cr = new CompletionRequest();
         conn.read(tx, recordHandle, cr, readFailureFatal);

         try {
            PersistentStoreRecord record = (PersistentStoreRecord)cr.getResult();
            return record.getData();
         } catch (Throwable var8) {
            throw new PersistentStoreException(var8);
         }
      }
   }

   private PersistentHandle getRandomRecordHandle(PersistentStoreConnection conn) throws PersistentStoreException {
      PersistentStore store = this.getPersistentStore();
      PersistentStoreConnection.Cursor cursor = conn.createCursor(0);
      PersistentStoreRecord record = cursor.next();
      return record == null ? null : record.getHandle();
   }

   private void createNewIndexPage() {
      this.currentIndexPage = new IndexEntry[512];
      this.currentEntryCount = 0;
   }

   protected PersistentStore getPersistentStore() {
      return this.persistentStore;
   }

   protected PersistentStoreConnection getDataConnection() {
      return this.dataConnection;
   }

   protected PersistentStoreConnection getObjectConnection() {
      return this.objectConnection;
   }

   protected PersistentStoreConnection getMetaConnection() {
      return this.metaConnection;
   }

   protected PersistentStoreConnection getIndexConnection() {
      return this.indexConnection;
   }

   protected long createRecordId() {
      return ++this.lastRecordId;
   }

   private void insertIntoIndex(long recordId, long timestamp, PersistentHandle recordHandle) throws PersistentStoreException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("insertIntoIndex: " + this.getName() + " recordId=" + recordId + ", timestamp=" + timestamp + ", handle=" + recordHandle);
      }

      IndexEntry entry = new IndexEntry();
      entry.recordId = recordId;
      entry.timestamp = timestamp;
      entry.recordHandle = recordHandle;
      this.currentIndexPage[this.currentEntryCount++] = entry;
      if (this.currentEntryCount >= 512) {
         PersistentStore store = this.getPersistentStore();
         PersistentStoreConnection conn = this.getIndexConnection();
         PersistentStoreTransaction tx = store.begin();
         PersistentHandle indexPageHandle = conn.create(tx, this.currentIndexPage, connectionWritePolicy);
         tx.commit();
         IndexMetaInfo metaInfo = new IndexMetaInfo();
         metaInfo.setLowTimestamp(this.currentIndexPage[0].timestamp);
         metaInfo.setLowRecordId(this.currentIndexPage[0].recordId);
         metaInfo.setHighTimestamp(this.currentIndexPage[511].timestamp);
         metaInfo.setHighRecordId(this.currentIndexPage[511].recordId);
         metaInfo.setIndexPageHandle(indexPageHandle);
         conn = this.getMetaConnection();
         tx = store.begin();
         PersistentHandle metaHandle = conn.create(tx, metaInfo, connectionWritePolicy);
         metaInfo.setMetaHandle(metaHandle);
         tx.commit();
         int size = this.indexMetaInfoArray != null ? this.indexMetaInfoArray.length : 0;
         IndexMetaInfo[] newInfos = new IndexMetaInfo[size + 1];
         if (size > 0) {
            System.arraycopy(this.indexMetaInfoArray, 0, newInfos, 0, size);
         }

         newInfos[size] = metaInfo;
         this.indexMetaInfoArray = newInfos;
         this.createNewIndexPage();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Inserted index page " + indexPageHandle + " and " + metaHandle);
         }
      }

   }

   protected synchronized void insertRecord(long startId, long endId, long timestamp, long endTimestamp, Object record) throws PersistentStoreException {
      if (this.persistentStore == null) {
         throw new PersistentStoreException(DTF.getStoreNotAvailable(this.getName()));
      } else {
         long t0 = this.elapsedTimer.timestamp();
         this.latestKnownTimestamp = endTimestamp;
         if (this.earliestKnownTimestamp < 0L) {
            this.earliestKnownTimestamp = timestamp;
         }

         PersistentStore store = this.getPersistentStore();
         PersistentStoreConnection objectConn = this.getObjectConnection();
         PersistentStoreTransaction tx = store.begin();
         PersistentHandle recordHandle = objectConn.create(tx, record, connectionWritePolicy);
         RecordWrapper wrapper = new RecordWrapper();
         wrapper.prev = this.lastRecordHandle;
         wrapper.next = null;
         wrapper.startId = startId;
         wrapper.endId = endId;
         wrapper.timestamp = timestamp;
         wrapper.endTimestamp = endTimestamp;
         wrapper.record = recordHandle;
         PersistentStoreConnection dataConn = this.getDataConnection();
         PersistentHandle handle = dataConn.create(tx, wrapper, connectionWritePolicy);
         if (this.lastRecord != null) {
            this.lastRecord.next = handle;
            dataConn.update(tx, this.lastRecordHandle, this.lastRecord, connectionWritePolicy);
         }

         tx.commit();
         this.insertIntoIndex(startId, timestamp, handle);
         this.lastRecordHandle = handle;
         this.lastRecord = wrapper;
         long delta = this.elapsedTimer.timestamp() - t0;
         int incr = 1;
         if (record instanceof Snapshot) {
            Collection collection = ((Snapshot)record).getData();
            incr = collection != null ? collection.size() : 0;
         }

         this.insertionCount += (long)incr;
         this.insertionTime += delta;
         this.updateRecordCount((long)incr, true);
      }
   }

   private void updateRecordCount(long val, boolean isIncrement) throws PersistentStoreException {
      PersistentStore store = this.getPersistentStore();
      PersistedAttribute pAttr = (PersistedAttribute)this.persistedAttributeMap.get("ATTR_RECORD_COUNT");
      if (pAttr != null) {
         synchronized(pAttr) {
            PersistentHandle handle = pAttr.getHandle();
            if (handle != null) {
               PersistentStoreConnection metaConn = this.getMetaConnection();
               long count = pAttr.getLongValue();
               if (isIncrement) {
                  count += val;
               } else {
                  count = val;
               }

               if (count < 0L) {
                  count = 0L;
               }

               pAttr.setLongValue(count);
               PersistentStoreTransaction tx = store.begin();
               metaConn.update(tx, handle, pAttr, connectionWritePolicy);
               tx.commit();
            }
         }
      }

   }

   public long getRecordCount() {
      return this.getPersistedAttributeLong("ATTR_RECORD_COUNT");
   }

   private long getRecordCountAtLastRetirement() {
      return this.getPersistedAttributeLong("ATTR_RECORD_COUNT_AT_LAST_RETIREMENT");
   }

   private void setRecordCountAtLastRetirement(long val) throws PersistentStoreException {
      if (DEBUG_RETIREMENT.isDebugEnabled()) {
         DEBUG_RETIREMENT.debug("Setting RecordCountAtLastRetirement=" + val + " for " + this.getName());
      }

      this.setPersistedAttributeLong("ATTR_RECORD_COUNT_AT_LAST_RETIREMENT", val);
   }

   private void setPersistedAttributeLong(String attrName, long val) throws PersistentStoreException {
      PersistentStore store = this.getPersistentStore();
      PersistedAttribute pAttr = (PersistedAttribute)this.persistedAttributeMap.get(attrName);
      if (pAttr != null) {
         synchronized(pAttr) {
            pAttr.setLongValue(val);
            PersistentHandle handle = pAttr.getHandle();
            if (handle != null) {
               PersistentStoreTransaction tx = store.begin();
               this.metaConnection.update(tx, handle, pAttr, connectionWritePolicy);
               tx.commit();
            }
         }
      }

   }

   private long getPersistedAttributeLong(String attrName) {
      if (this.persistentStore == null) {
         return 0L;
      } else {
         PersistedAttribute pAttr = (PersistedAttribute)this.persistedAttributeMap.get(attrName);
         if (pAttr != null) {
            synchronized(pAttr) {
               return pAttr.getLongValue();
            }
         } else {
            return 0L;
         }
      }
   }

   public synchronized long recomputeRecordCount() throws PersistentStoreException {
      if (this.persistentStore == null) {
         throw new PersistentStoreException(DTF.getStoreNotAvailable(this.getName()));
      } else {
         long count = 0L;

         RecordWrapper wrapper;
         for(PersistentHandle handle = this.findRecordHandle(0L, false); handle != null; handle = wrapper.next) {
            wrapper = this.readRecord(handle);
            Object rec = wrapper.getDataObject(this);
            if (rec != null) {
               if (rec instanceof Snapshot) {
                  Collection data = ((Snapshot)rec).getData();
                  if (data != null) {
                     count += (long)data.size();
                  }
               } else {
                  ++count;
               }
            }
         }

         this.updateRecordCount(count, false);
         return count;
      }
   }

   synchronized PersistentHandle findRecordHandle(long value, boolean useTimestamp) throws PersistentStoreException {
      if (this.persistentStore == null) {
         throw new PersistentStoreException(DTF.getStoreNotAvailable(this.getName()));
      } else {
         int pageIndex = this.findIndex(value, useTimestamp);
         if (pageIndex != -1) {
            while(pageIndex < this.indexMetaInfoArray.length) {
               IndexEntry[] page = this.findIndexPage(pageIndex);
               PersistentHandle recordHandle = this.findRecordHandle(value, useTimestamp, page, page.length);
               if (recordHandle != null) {
                  return recordHandle;
               }

               ++pageIndex;
            }
         }

         return this.findRecordHandle(value, useTimestamp, this.currentIndexPage, this.currentEntryCount);
      }
   }

   private synchronized int findIndex(long value, boolean useTimestamp) {
      if (this.indexMetaInfoArray != null && this.indexMetaInfoArray.length != 0 && (this.currentEntryCount <= 0 || this.getIndexEntryValue(this.currentIndexPage[0], useTimestamp) > value)) {
         int lo = 0;
         int hi = this.indexMetaInfoArray.length - 1;
         int answer = 0;

         while(lo <= hi) {
            answer = lo + (hi - lo) / 2;
            int val = this.indexMetaInfoArray[answer].checkInterval(value, useTimestamp);
            if (val == 0) {
               break;
            }

            if (val < 0) {
               hi = answer - 1;
            }

            if (val > 0) {
               lo = answer + 1;
            }
         }

         while(answer > 0 && this.indexMetaInfoArray[answer].checkInterval(value, useTimestamp) < 0) {
            --answer;
         }

         return answer;
      } else {
         return -1;
      }
   }

   private long getIndexEntryValue(IndexEntry entry, boolean useTimestamp) {
      return useTimestamp ? entry.timestamp : entry.recordId;
   }

   private synchronized IndexEntry[] findIndexPage(int pageIndex) throws PersistentStoreException {
      if (pageIndex == -1) {
         return null;
      } else {
         IndexMetaInfo metaInfo = this.indexMetaInfoArray[pageIndex];
         SecondChanceCacheMap cache = this.indexPageCache;
         IndexEntry[] page = (IndexEntry[])((IndexEntry[])(cache != null ? cache.get(metaInfo) : null));
         if (page == null) {
            PersistentHandle indexPageHandle = metaInfo.getIndexPageHandle();
            page = (IndexEntry[])((IndexEntry[])this.readRecord(this.indexConnection, indexPageHandle));
            if (page != null && cache != null) {
               cache.put(metaInfo, page);
            }
         }

         return page;
      }
   }

   private PersistentHandle findRecordHandle(long value, boolean useTimestamp, IndexEntry[] indexPage, int limit) throws PersistentStoreException {
      if (indexPage != null && limit != 0) {
         int answer = 0;
         if (value >= this.getIndexEntryValue(indexPage[answer], useTimestamp)) {
            int lo = 0;
            int hi = limit - 1;

            while(lo <= hi) {
               answer = lo + (hi - lo) / 2;
               long val = this.getIndexEntryValue(indexPage[answer], useTimestamp);
               if (value == val) {
                  break;
               }

               if (value < val) {
                  hi = answer - 1;
               }

               if (value > val) {
                  lo = answer + 1;
               }
            }

            while(answer > 0 && value <= this.getIndexEntryValue(indexPage[answer], useTimestamp)) {
               --answer;
            }
         }

         while(answer < limit) {
            PersistentHandle recordHandle = indexPage[answer].recordHandle;
            if (recordHandle != null) {
               return recordHandle;
            }

            ++answer;
         }

         return null;
      } else {
         return null;
      }
   }

   public int retireOldestRecords(DataRetirementTaskRuntimeMBean task) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      if (this.persistentStore == null) {
         return 0;
      } else {
         int cnt = 0;
         long t0 = System.currentTimeMillis();
         long currentRecordCount = this.getRecordCount();
         long recCountAtLastretirement = this.getRecordCountAtLastRetirement();
         if (DEBUG_RETIREMENT.isDebugEnabled()) {
            DEBUG_RETIREMENT.debug("archive=" + this.getName() + " currentRecordCount=" + currentRecordCount + " recCountAtLastretirement=" + recCountAtLastretirement);
         }

         long maxDelete;
         if (currentRecordCount > 10000L) {
            maxDelete = currentRecordCount - 10000L;
            long deleteCount = currentRecordCount - recCountAtLastretirement;
            if (deleteCount > maxDelete) {
               deleteCount = maxDelete;
            }

            if (DEBUG_RETIREMENT.isDebugEnabled()) {
               DEBUG_RETIREMENT.debug("Retiring " + deleteCount + " records out of " + currentRecordCount + " from " + this.getName());
            }

            if (deleteCount > 0L) {
               cnt = this.deleteDataRecords(0L, Long.MAX_VALUE, (String)null, task, deleteCount);
            }

            if (DEBUG_RETIREMENT.isDebugEnabled()) {
               DEBUG_RETIREMENT.debug("Deleted " + cnt + " records out of " + currentRecordCount + " from " + this.getName());
            }
         } else if (DEBUG_RETIREMENT.isDebugEnabled()) {
            DEBUG_RETIREMENT.debug("Skipping data retirement for " + this.getName() + " due to small record count: " + currentRecordCount);
         }

         maxDelete = System.currentTimeMillis();
         this.updateRetirementStatistics(t0, maxDelete, (long)cnt);
         return cnt;
      }
   }

   public int deleteDataRecords(long startTime, long endTime, String queryString, DataRetirementTaskRuntimeMBean task) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      return this.deleteDataRecords(startTime, endTime, queryString, task, Long.MAX_VALUE);
   }

   private int deleteDataRecords(long startTime, long endTime, String queryString, DataRetirementTaskRuntimeMBean task, long maxRecords) throws QueryException, DiagnosticDataAccessException, UnsupportedOperationException {
      if (this.persistentStore == null) {
         throw new DiagnosticDataAccessException(DTF.getStoreNotAvailable(this.getName()));
      } else if (this.isReadOnly()) {
         throw new UnsupportedOperationException("Delete operation is not supported with read-only archive " + this.getName());
      } else {
         int count = 0;
         if (startTime >= endTime) {
            return count;
         } else {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("deleteDataRecords from " + this.getName() + " startTime=" + startTime + " endTime=" + endTime + " query=" + queryString);
               DEBUG.debug("deleteDataRecords START: " + this.getConnectionStats());
            }

            try {
               QueryEvaluator queryEvaluator = new QueryEvaluator(this, queryString);
               PersistentHandle handle = this.findRecordHandle(startTime, true);
               boolean endReached = false;

               while(handle != null && this.continueDeletion(task, maxRecords) && !endReached) {
                  synchronized(this) {
                     RecordWrapper wrapper = this.readRecord(handle);
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("deleteDataRecords: readRecord(" + handle + ") returned wrapper " + wrapper);
                     }

                     Object sObj = wrapper.getDataObject(this);
                     if (wrapper.isTimestampWithinRange(startTime, endTime) && sObj instanceof Snapshot) {
                        Snapshot snapshot = (Snapshot)sObj;
                        Collection data = snapshot.getData();
                        if (data != null && data.size() > 0) {
                           ArrayList newData = new ArrayList();
                           int delCount = 0;
                           Iterator it = data.iterator();

                           while(true) {
                              while(it.hasNext()) {
                                 DataRecord dataRecord = (DataRecord)it.next();
                                 Long tsL = (Long)dataRecord.get(1);
                                 long timestamp = tsL != null ? tsL : 0L;
                                 if (timestamp >= startTime && timestamp < endTime && this.continueDeletion(task, maxRecords) && queryEvaluator.evaluate(dataRecord)) {
                                    ++delCount;
                                    --maxRecords;
                                 } else {
                                    newData.add(dataRecord);
                                 }
                              }

                              if (delCount > 0) {
                                 PersistentHandle recHandle = (PersistentHandle)wrapper.record;
                                 PersistentStoreTransaction tx = this.getPersistentStore().begin();
                                 boolean doUpdate = newData.size() > 0 || handle.equals(this.lastRecordHandle);
                                 if (doUpdate) {
                                    if (DEBUG.isDebugEnabled()) {
                                       DEBUG.debug("Updating snapshot wrapperHandle=" + handle + " recHandle=" + recHandle);
                                    }

                                    snapshot = new Snapshot(snapshot.getTimestamp(), newData);
                                    this.getObjectConnection().update(tx, recHandle, snapshot, connectionWritePolicy);
                                 } else {
                                    if (DEBUG.isDebugEnabled()) {
                                       DEBUG.debug("Deleting snapshot wrapperHandle=" + handle + " recHandle=" + recHandle);
                                    }

                                    wrapper.record = null;
                                    this.getObjectConnection().delete(tx, recHandle, 0);
                                    this.getDataConnection().update(tx, handle, wrapper, connectionWritePolicy);
                                 }

                                 tx.commit();
                                 if (task instanceof DataRetirementTaskImpl) {
                                    ((DataRetirementTaskImpl)task).incrementRetiredRecordsCount((long)delCount);
                                 }

                                 count += delCount;
                              }
                              break;
                           }
                        }
                     }

                     handle = wrapper.next;
                     if (wrapper.timestamp > endTime) {
                        endReached = true;
                     }
                  }
               }

               if (count > 0) {
                  this.updateRecordCount((long)(0 - count), true);
               }

               if (queryString == null || queryString.trim().length() == 0) {
                  this.cleanup(startTime, endTime);
               }

               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("deleteDataRecords END: " + this.getConnectionStats());
               }

               return count;
            } catch (PersistentStoreException var27) {
               throw new DiagnosticDataAccessException(var27);
            } catch (ClassCastException var28) {
               throw new DiagnosticDataAccessException(var28);
            } catch (UnknownVariableException var29) {
               throw new DiagnosticDataAccessException(var29);
            }
         }
      }
   }

   private boolean continueDeletion(DataRetirementTaskRuntimeMBean task, long maxRecords) {
      return maxRecords > 0L && (task == null || task.isRunning());
   }

   public int getIndexPageCount() {
      return this.indexMetaInfoArray != null ? this.indexMetaInfoArray.length : 0;
   }

   public synchronized long getInsertionCount() {
      return this.insertionCount;
   }

   public synchronized long getInsertionTime() {
      return this.insertionTime;
   }

   public long getDeletionCount() {
      return this.deletionCount;
   }

   public long getDeletionTime() {
      return this.deletionTime;
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
      return this.lastRecordId;
   }

   public void writeData(Collection data) throws IOException {
      if (this.persistentStore == null) {
         throw new IOException(DTF.getStoreNotAvailable(this.getName()));
      } else {
         long timestamp = 0L;
         long endTimestamp = 0L;
         long startId = 0L;
         long endId = 0L;

         Object[] values;
         for(Iterator it = data.iterator(); it.hasNext(); values[0] = endId) {
            DataRecord rec = (DataRecord)it.next();
            values = rec.getValues();
            Long timestampL = (Long)values[1];
            endTimestamp = timestampL != null ? timestampL : 0L;
            if (timestamp == 0L) {
               timestamp = endTimestamp;
            }

            endId = this.createRecordId();
            if (startId == 0L) {
               startId = endId;
            }
         }

         try {
            Snapshot snapshot = new Snapshot(timestamp, data);
            this.insertRecord(startId, endId, timestamp, endTimestamp, snapshot);
         } catch (PersistentStoreException var14) {
            throw new IOException(var14.getMessage());
         }
      }
   }

   public void updateRecord(long recordId, Map changeMap) throws DiagnosticDataAccessException, UnsupportedOperationException {
      if (this.persistentStore == null) {
         throw new DiagnosticDataAccessException(DTF.getStoreNotAvailable(this.getName()));
      } else if (!this.isReadOnly() && this.allowUpdates) {
         if (changeMap != null && changeMap.size() != 0) {
            try {
               Iterator it = changeMap.keySet().iterator();

               while(it.hasNext()) {
                  String attrName = (String)it.next();
                  int index = this.getVariableIndex(attrName);
                  if (index < 2) {
                     throw new DiagnosticDataAccessException("Cannot update attribute " + attrName);
                  }
               }
            } catch (UnknownVariableException var26) {
               throw new DiagnosticDataAccessException(var26);
            }

            try {
               PersistentHandle handle = this.findRecordHandle(recordId, false);
               if (handle == null) {
                  throw new DiagnosticDataAccessException("Record " + recordId + " cannot be found");
               } else {
                  RecordWrapper wrapper = null;
                  Snapshot snapshot = null;
                  synchronized(this) {
                     for(; handle != null && snapshot == null; handle = wrapper.next) {
                        wrapper = this.readRecord(handle);
                        if (wrapper.startId <= recordId && recordId <= wrapper.endId) {
                           Object sObj = wrapper.getDataObject(this);
                           if (!(sObj instanceof Snapshot)) {
                              throw new DiagnosticDataAccessException("Cannot update record which is not part of a valid Snapshot");
                           }

                           snapshot = (Snapshot)sObj;
                        } else if (wrapper.startId > recordId) {
                           break;
                        }
                     }

                     if (snapshot == null) {
                        throw new DiagnosticDataAccessException("Cannot locate snapshot containing record " + recordId);
                     } else {
                        long sTimestamp = snapshot.getTimestamp();
                        ArrayList outList = new ArrayList();
                        boolean changed = false;

                        DataRecord dataRecord;
                        for(Iterator it = snapshot.getData().iterator(); it.hasNext(); outList.add(dataRecord)) {
                           dataRecord = (DataRecord)it.next();
                           if (!changed) {
                              Object[] data = dataRecord.getValues();
                              long id = (Long)data[0];
                              if (id == recordId) {
                                 String attrName;
                                 int index;
                                 for(Iterator it2 = changeMap.keySet().iterator(); it2.hasNext(); data[index] = changeMap.get(attrName)) {
                                    attrName = (String)it2.next();
                                    index = this.getVariableIndex(attrName);
                                 }

                                 dataRecord = new DataRecord(data);
                                 changed = true;
                              }
                           }
                        }

                        if (!changed) {
                           throw new DiagnosticDataAccessException("Record " + recordId + " cannot be found in the snapshot");
                        } else {
                           snapshot = new Snapshot(sTimestamp, outList);
                           PersistentHandle recHandle = (PersistentHandle)wrapper.record;
                           PersistentStoreTransaction tx = this.getPersistentStore().begin();
                           this.getObjectConnection().update(tx, recHandle, snapshot, connectionWritePolicy);
                           tx.commit();
                        }
                     }
                  }
               }
            } catch (PersistentStoreException var22) {
               throw new DiagnosticDataAccessException(var22);
            } catch (DiagnosticDataAccessException var23) {
               throw var23;
            } catch (ClassCastException var24) {
               throw new DiagnosticDataAccessException(var24);
            } catch (UnknownVariableException var25) {
               throw new DiagnosticDataAccessException(var25);
            }
         }
      } else {
         throw new UnsupportedOperationException(DTF.getArchiveDoesNotSupportUpdate(this.getName()));
      }
   }

   public long getEarliestAvailableTimestamp() {
      return this.earliestKnownTimestamp;
   }

   public long getLatestAvailableTimestamp() {
      return this.latestKnownTimestamp < Long.MAX_VALUE ? this.latestKnownTimestamp : -1L;
   }

   private void cleanup(long startTime, long endTime) throws PersistentStoreException {
      int startPageIndex = this.findIndex(startTime, true);
      if (startPageIndex != -1) {
         int endPageIndex = this.findIndex(endTime, true);
         int indexMetaInfoArrayLength = this.indexMetaInfoArray != null ? this.indexMetaInfoArray.length : 0;
         if (endPageIndex == -1) {
            endPageIndex = indexMetaInfoArrayLength - 1;
         } else if (endPageIndex < indexMetaInfoArrayLength - 1) {
            ++endPageIndex;
         }

         this.cleanupPages(startPageIndex, endPageIndex);
      }
   }

   private int[] cleanupPages(int startPageIndex, int endPageIndex) throws PersistentStoreException {
      int[] stats = new int[2];
      if (startPageIndex > endPageIndex) {
         return stats;
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("cleanupPages: " + startPageIndex + " to " + endPageIndex);
         }

         this.enableIndexPageCache(false);

         try {
            int pageIndex = endPageIndex;

            int lastEntryIndex;
            for(lastEntryIndex = -1; pageIndex >= startPageIndex; --pageIndex) {
               IndexEntry[] page = this.findIndexPage(pageIndex);

               for(int ind = page.length - 1; ind >= 0; --ind) {
                  if (page[ind].recordHandle != null) {
                     lastEntryIndex = ind;
                     break;
                  }
               }

               if (lastEntryIndex >= 0) {
                  break;
               }
            }

            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Last entry in pageIndex=" + pageIndex + " lastEntryIndex=" + lastEntryIndex);
            }

            if (lastEntryIndex >= 0) {
               for(boolean retainLastEntry = true; pageIndex >= startPageIndex; retainLastEntry = false) {
                  int[] counts = this.removeGarbageInPage(pageIndex, lastEntryIndex, retainLastEntry);
                  stats[0] += counts[0];
                  stats[1] += counts[1];
                  --pageIndex;
               }
            }
         } finally {
            this.enableIndexPageCache(true);
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("PersistentStoreDataArchive.cleanupPages on " + this.getName() + " deleted=" + stats[0] + " updated=" + stats[1]);
         }

         return stats;
      }
   }

   public synchronized int[] compact() throws PersistentStoreException {
      if (this.persistentStore == null) {
         throw new PersistentStoreException(DTF.getStoreNotAvailable(this.getName()));
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Compacting archive " + this.getName());
         }

         int[] stats = new int[2];
         int metaInfoCount = this.indexMetaInfoArray != null ? this.indexMetaInfoArray.length : 0;
         return metaInfoCount == 0 ? stats : this.cleanupPages(0, metaInfoCount - 1);
      }
   }

   private RecordWrapper getWrapperFromCache(PersistentStoreConnection dataConn, Map wrapperMap, Map deletedMap, Map updateMap, PersistentHandle handle) throws PersistentStoreException {
      RecordWrapper wrapper = (RecordWrapper)wrapperMap.get(handle);
      if (wrapper == null) {
         wrapper = (RecordWrapper)deletedMap.get(handle);
      }

      if (wrapper == null) {
         wrapper = (RecordWrapper)updateMap.get(handle);
      }

      return wrapper;
   }

   private RecordWrapper getWrapper(PersistentStoreConnection dataConn, Map wrapperMap, Map deletedMap, Map updateMap, PersistentHandle handle) throws PersistentStoreException {
      RecordWrapper wrapper = this.getWrapperFromCache(dataConn, wrapperMap, deletedMap, updateMap, handle);
      if (wrapper == null) {
         RecordWrapper tmp = this.readRecord(handle);
         wrapper = this.getWrapperFromCache(dataConn, wrapperMap, deletedMap, updateMap, handle);
         if (wrapper == null) {
            wrapper = tmp;
         }

         wrapper.getDataObject(this);
         wrapperMap.put(handle, wrapper);
      }

      return wrapper;
   }

   private synchronized int[] removeGarbageInPage(int pageIndex, int lastEntryIndex, boolean retainLastEntry) throws PersistentStoreException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("removeGarbageInPage pageIndex=" + pageIndex + " lastEntryIndex=" + lastEntryIndex + " retainLastEntry=" + retainLastEntry);
      }

      IndexMetaInfo[] updatedIndexMetaInfoArray = null;
      int[] stats = new int[2];
      IndexEntry[] page = this.findIndexPage(pageIndex);
      PersistentStoreConnection objConn = this.getObjectConnection();
      PersistentStoreConnection dataConn = this.getDataConnection();
      PersistentStoreTransaction tx = this.getPersistentStore().begin();
      if (!retainLastEntry) {
         lastEntryIndex = page.length;
      }

      HashMap wrapperMap = new HashMap();
      HashMap deletedMap = new HashMap();
      HashMap updateMap = new HashMap();

      int delCount;
      IndexEntry entry;
      PersistentHandle wrapperHandle;
      for(delCount = 0; delCount < lastEntryIndex; ++delCount) {
         entry = page[delCount];
         wrapperHandle = entry.recordHandle;
         if (wrapperHandle != null) {
            this.getWrapper(dataConn, wrapperMap, deletedMap, updateMap, wrapperHandle);
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("removeGarbageInPage: priming wrapper at ind=" + delCount + " wrapperHandle=" + wrapperHandle);
         }
      }

      for(delCount = 0; delCount < lastEntryIndex; ++delCount) {
         entry = page[delCount];
         wrapperHandle = entry.recordHandle;
         if (wrapperHandle != null) {
            RecordWrapper wrapper = this.getWrapper(dataConn, wrapperMap, deletedMap, updateMap, wrapperHandle);
            if (wrapper != null) {
               PersistentHandle objHandle = (PersistentHandle)wrapper.record;
               Object sObj = wrapper.getDataObject(this);
               if (sObj instanceof Snapshot) {
                  Snapshot snapshot = (Snapshot)sObj;
                  Collection data = snapshot.getData();
                  if (data == null || data.size() == 0) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("removeGarbageInPage: deleting snapshot objHandle=" + objHandle);
                     }

                     objConn.delete(tx, objHandle, 0);
                     sObj = null;
                  }
               }

               if (sObj == null) {
                  PersistentHandle nextHandle = wrapper.next;
                  PersistentHandle prevHandle = wrapper.prev;
                  RecordWrapper prevWrapper;
                  if (nextHandle != null) {
                     prevWrapper = this.getWrapper(dataConn, wrapperMap, deletedMap, updateMap, nextHandle);
                     prevWrapper.prev = prevHandle;
                     updateMap.put(nextHandle, prevWrapper);
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("removeGarbageInPage: PREV(" + nextHandle + ") = " + prevHandle);
                     }
                  }

                  if (prevHandle != null) {
                     prevWrapper = this.getWrapper(dataConn, wrapperMap, deletedMap, updateMap, prevHandle);
                     prevWrapper.next = nextHandle;
                     updateMap.put(prevHandle, prevWrapper);
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("removeGarbageInPage: NEXT(" + prevHandle + ") = " + nextHandle);
                     }
                  }

                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("removeGarbageInPage: scheduling for delete: " + wrapperHandle);
                  }

                  deletedMap.put(wrapperHandle, wrapper);
                  entry.recordHandle = null;
               }
            }
         }
      }

      delCount = 0;
      int updateCnt = 0;
      if (deletedMap.size() > 0) {
         Iterator it = deletedMap.keySet().iterator();

         while(it.hasNext()) {
            PersistentHandle wrapperHandle = (PersistentHandle)it.next();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("removeGarbageInPage: deleting wrapper wrapperHandle=" + wrapperHandle);
            }

            ++delCount;
            dataConn.delete(tx, wrapperHandle, 0);
         }

         it = updateMap.entrySet().iterator();

         while(it.hasNext()) {
            Object e = it.next();
            Map.Entry entry = (Map.Entry)e;
            PersistentHandle wrapperHandle = (PersistentHandle)entry.getKey();
            if (deletedMap.get(wrapperHandle) == null) {
               RecordWrapper wrapper = (RecordWrapper)entry.getValue();
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("removeGarbageInPage: updating wrapper wrapperHandle=" + wrapperHandle);
               }

               ++updateCnt;
               dataConn.update(tx, wrapperHandle, wrapper, connectionWritePolicy);
            }
         }

         updatedIndexMetaInfoArray = this.updateOrDeleteIndexPage(tx, pageIndex, page);
         tx.commit();
      }

      if (updatedIndexMetaInfoArray != null) {
         this.indexMetaInfoArray = updatedIndexMetaInfoArray;
      }

      stats[0] = delCount;
      stats[1] = updateCnt;
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("removeGarbageInPage: page=" + pageIndex + " deleted=" + delCount + " updated=" + updateCnt);
      }

      return stats;
   }

   private IndexMetaInfo[] updateOrDeleteIndexPage(PersistentStoreTransaction tx, int pageIndex, IndexEntry[] page) throws PersistentStoreException {
      IndexMetaInfo[] retArr = null;
      IndexMetaInfo metaInfo = this.indexMetaInfoArray[pageIndex];
      boolean hasEntry = false;

      for(int i = 0; !hasEntry && i < page.length; ++i) {
         IndexEntry entry = page[i];
         if (entry.recordHandle != null) {
            hasEntry = true;
         }
      }

      PersistentStoreConnection indexConn = this.getIndexConnection();
      PersistentStoreConnection metaConn = this.getMetaConnection();
      if (hasEntry) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("updateOrDeleteIndexPage: Updating index page " + pageIndex);
         }

         indexConn.update(tx, metaInfo.getIndexPageHandle(), page, connectionWritePolicy);
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("updateOrDeleteIndexPage: Deleting index page and metaInfo " + pageIndex);
         }

         indexConn.delete(tx, metaInfo.getIndexPageHandle(), 0);
         metaConn.delete(tx, metaInfo.getMetaHandle(), 0);
         retArr = new IndexMetaInfo[this.indexMetaInfoArray.length - 1];
         if (pageIndex > 0) {
            System.arraycopy(this.indexMetaInfoArray, 0, retArr, 0, pageIndex);
         }

         if (pageIndex < this.indexMetaInfoArray.length - 1) {
            System.arraycopy(this.indexMetaInfoArray, pageIndex + 1, retArr, pageIndex, this.indexMetaInfoArray.length - 1 - pageIndex);
         }
      }

      return retArr;
   }

   public long captureCurrentRecordCount() throws PersistentStoreException {
      long curCount = this.getRecordCount();
      long recCountAtLastretirement = this.getRecordCountAtLastRetirement();
      if (curCount > recCountAtLastretirement) {
         recCountAtLastretirement = curCount;
         this.setRecordCountAtLastRetirement(curCount);
      }

      return recCountAtLastretirement;
   }

   private String getConnectionStats() {
      try {
         long metaObjCnt = this.getMetaConnection().getStatistics().getObjectCount();
         long indexObjCnt = this.getIndexConnection().getStatistics().getObjectCount();
         long dataObjCnt = this.getDataConnection().getStatistics().getObjectCount();
         long objCnt = this.getObjectConnection().getStatistics().getObjectCount();
         return "getConnectionStats on " + this.getName() + " metaObjCnt=" + metaObjCnt + " indexObjCnt=" + indexObjCnt + " dataObjCnt=" + dataObjCnt + " objCnt=" + objCnt;
      } catch (Exception var9) {
         return "getConnectionStats: " + this.getName() + " failed: " + var9.getMessage();
      }
   }

   private static class ArchiveSchema implements Serializable {
      static final long serialVersionUID = -665469141712332428L;
      String name;
      ColumnInfo[] columns;

      private ArchiveSchema() {
      }

      // $FF: synthetic method
      ArchiveSchema(Object x0) {
         this();
      }
   }

   private static class PersistedAttribute implements Externalizable {
      static final long serialVersionUID = 12345L;
      private String key;
      private Object value;
      private transient PersistentHandle handle;

      public PersistedAttribute() {
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeObject(this.key);
         out.writeObject(this.value);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.key = (String)in.readObject();
         this.value = in.readObject();
      }

      public String getKey() {
         return this.key;
      }

      public void setKey(String key) {
         this.key = key;
      }

      public Object getValue() {
         return this.value;
      }

      public void setValue(Object value) {
         this.value = value;
      }

      public PersistentHandle getHandle() {
         return this.handle;
      }

      public void setHandle(PersistentHandle handle) {
         this.handle = handle;
      }

      public long getLongValue() {
         return this.value instanceof Long ? (Long)this.value : 0L;
      }

      public void setLongValue(long value) {
         this.value = value;
      }
   }

   private static class IndexEntry implements Externalizable {
      static final long serialVersionUID = 12345L;
      long timestamp;
      long recordId;
      PersistentHandle recordHandle;

      public IndexEntry() {
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeLong(this.recordId);
         out.writeLong(this.timestamp);
         out.writeObject(this.recordHandle);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.recordId = in.readLong();
         this.timestamp = in.readLong();
         this.recordHandle = (PersistentHandle)in.readObject();
      }
   }
}
