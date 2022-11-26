package weblogic.store.io.jdbc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreFatalException;
import weblogic.store.PersistentStoreTestException;
import weblogic.store.StoreLogger;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.IOListener;
import weblogic.store.io.IORecord;
import weblogic.store.io.PersistentStoreIO;
import weblogic.utils.collections.NumericKeyHashMap;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.xml.stax.util.XMLPrettyPrinter;

public class JDBCStoreIO implements PersistentStoreIO {
   private static final String RESTORE_METADATA_DEFAULT = "true";
   private static String CONNECTION_CACHING_POLICY_PROP = ".ConnectionCachingPolicy";
   private static final int DEFAULT_DUMP_SIZE = 2000;
   private static boolean tableAlreadyDumped = false;
   public static final String TABLE_NAME = "WLStore";
   public static final int RETRY_PERIOD_DEFAULT = 1000;
   public static final int RETRY_PERIOD_MIN = 200;
   public static final int RETRY_PERIOD_MAX = 300000;
   public static final int RETRY_INTERVAL_DEFAULT = 200;
   public static final int RETRY_INTERVAL_MIN = 100;
   public static final int RETRY_INTERVAL_MAX = 10000;
   private static final int JDBCSTORE_VERSION_2 = 2;
   private static final int JDBCSTORE_VERSION_3 = 3;
   private static final int CURRENT_VERSION = 3;
   private static final boolean BOOT_ON_ERROR;
   private static final String INDEX_SUFFIX = "X";
   private static final String DDL_DIRECTORY = "/weblogic/store/io/jdbc/ddl/";
   private static final int NUM_LOCK_PARTITIONS = 16;
   static final String ROWID_COL_NAME = "id";
   static final String TYPE_COL_NAME = "type";
   static final String HANDLE_COL_NAME = "handle";
   static final String RECORD_COL_NAME = "record";
   static final String ALLCOLUMNS = "id,type,handle,record";
   static final int ROWID_COL = 1;
   static final int TYPE_COL = 2;
   static final int HANDLE_COL = 3;
   static final int RECORD_COL = 4;
   private static final int VERSION_TYPE = -1;
   private static final int TEST_EXCEPTION_TYPE = -2;
   private static final int LOCK_PARTITION_TYPE = -3;
   static final int ALL_TYPES = -4;
   static final int USER_TYPES = -5;
   static final int DEFAULT_FLUSH_RETRY_COUNT = 0;
   private static final int MIN_READ_ATTEMPTS = 2;
   private static final int VERSION_ROW = -1;
   private static final int TEST_EXCEPTION_ROW = -2;
   private DataSource dataSource;
   private String serverName;
   private String storeName;
   private int dbmsType;
   private String tableRef;
   private String tableDMLIdentifier;
   private String tableDDLIdentifier;
   private String indexDDLIdentifier;
   private Boolean isOracleBlobRecord;
   private boolean enableBatchInserts;
   private boolean enableBatchDeletes;
   private int maxDeleteCount;
   private int maxDeleteStatementsPerBatch;
   private int maxInsertStatementsPerBatch;
   private int retryPeriodMilliseconds;
   private int retryIntervalMilliseconds;
   private ReservedConnection mainConnAC;
   private ReservedConnection mainConnLC;
   private volatile boolean isOpen;
   private volatile boolean isBeingOpened;
   private volatile boolean isBeingClosed;
   private volatile boolean isPrepareToClose;
   private AtomicBoolean tableExists;
   private volatile boolean fatalError;
   private HandleTracking handleTracking;
   private PendingDeleteHashMap pDeletes;
   private PendingInsertHashMap pInserts;
   private static final int TABLE_LOCK_REFRESH_INTERVAL_DEFAULT = 10000;
   private static final int TABLE_LOCK_MAX_TRIES_DEFAULT = 2;
   private static final int TABLE_LOCK_REFRESH_WAIT_FACTOR = 3;
   private static final int TABLE_LOCK_REFRESH_INTERVAL_FUDGE = 50;
   private static final int TABLE_LOCK_STALE_FACTOR = 2;
   private static final String PROPNAME_TABLE_LOCK_INTERVAL = "weblogic.store.jdbc.TableLockingInterval";
   private static final String PROPNAME_TABLE_LOCK_DEBUG = "weblogic.store.jdbc.TableLockingDebug";
   private static final String PROPNAME_TABLE_LOCK_DISABLE = "weblogic.store.jdbc.TableLockingDisabled";
   public static final boolean FLUSH_FAILURE_FATAL_DEFAULT = true;
   private boolean flushFailureFatal;
   private int tableLockRefreshInterval;
   private int tableLockMaxTries;
   private final TableLockRecord tableLockRecord;
   private long tableLockRecordCandidateTimestamp;
   private TableLockRecord dbTableLockRecord;
   private int tableOwnerState;
   private boolean tableLockStampingEnabled;
   private boolean tableLockDebugTrace;
   private int flushRetryTimeoutMillis;
   private PersistentStoreException testStoreException;
   private Set lockedHandles;
   private Map handleLocks;
   private Map flushToWorkerMap;
   private static final int WORKER_OVERRIDE_NONE = -2;
   private int workerOverride;
   private static final String JDBC_STORE_PREFIX = "weblogic.store.jdbc";
   private static final String JDBC_STORE_PREFIX_DOT = "weblogic.store.jdbc.";
   private static final String WORKER_COUNT_PROP = ".WorkerCount";
   private static final int DEFAULT_WORKER_COUNT = 1;
   private int workerCount;
   private int effectiveWorkerCount;
   private static final String WORKER_PREFERRED_BATCH_SIZE_PROP = ".WorkerPreferredBatchSize";
   private static final int DEFAULT_WORKER_PREFERRED_BATCH_SIZE = 10;
   private int workerPreferredBatchSize;
   public static final int LB_FAIR = 0;
   public static final int LB_EFFICIENCY = 1;
   public static final int LB_ROUND_ROBIN = 2;
   private static final String LB_STRATEGY_PROP = ".LBStrategy";
   private static final int DEFAULT_LB_STRATEGY = 1;
   private int lbStrategy;
   private static final String THREE_STEP_THRESHOLD_PROP = ".ThreeStepThreshold";
   private static final int DEFAULT_THREE_STEP_THRESHOLD = 200000;
   private int threeStepThreshold;
   private static final String ORACLE_PIGGYBACK_COMMIT_ENABLED = ".OraclePiggybackCommitEnabled";
   private static final boolean DEFAULT_ORACLE_PIGGYBACK_COMMIT_ENABLED = false;
   boolean isOraclePiggybackCommitEnabled;
   private String createTableDDLFile;
   private volatile JDBCStoreWorker[] workers;
   private Object workersLock;
   private CountDownLatch workerShutdownLatch;
   private long flushIndex;
   private RACState racState;
   private ConnectionCachingPolicy connectionPolicy;
   private boolean enableAutoCommit;
   private boolean getConnectionOnDemand;
   private String storeConfiguredName;
   private boolean RESTORE_METADATA;
   private static final long CLOSE_MAX_WAIT_FOR_WORKERS_MILLIS = 10000L;
   private AtomicInteger activeWorkerCount;
   private int roundRobinIndex;

   public JDBCStoreIO(String _storeName, DataSource _dataSource, String _tableRef, String _createTableDDLFile, int _maxDeleteStatementsPerBatch, int _maxInsertStatementsPerBatch, int _maxDeletesPerStatement) {
      this(_storeName, _dataSource, _tableRef, _createTableDDLFile, _maxDeleteStatementsPerBatch, _maxInsertStatementsPerBatch, _maxDeletesPerStatement, 1000, 200, true);
   }

   public JDBCStoreIO(String _storeName, DataSource _dataSource, String _tableRef, String _createTableDDLFile, int _maxDeleteStatementsPerBatch, int _maxInsertStatementsPerBatch, int _maxDeletesPerStatement, boolean _flushFailureFatal) {
      this(_storeName, _dataSource, _tableRef, _createTableDDLFile, _maxDeleteStatementsPerBatch, _maxInsertStatementsPerBatch, _maxDeletesPerStatement, 1000, 200, _flushFailureFatal);
   }

   public JDBCStoreIO(String _storeName, DataSource _dataSource, String _tableRef, String _createTableDDLFile, int _maxDeleteStatementsPerBatch, int _maxInsertStatementsPerBatch, int _maxDeletesPerStatement, int _retryPeriodMilliseconds, int _retryIntervalMilliseconds, boolean _flushFailureFatal) {
      this.dbmsType = 0;
      this.maxDeleteCount = 20;
      this.isOpen = false;
      this.isBeingOpened = false;
      this.isBeingClosed = false;
      this.isPrepareToClose = false;
      this.tableExists = new AtomicBoolean(false);
      this.flushFailureFatal = true;
      this.tableLockRecordCandidateTimestamp = -1L;
      this.tableOwnerState = 1;
      this.handleLocks = new HashMap();
      this.flushToWorkerMap = new HashMap();
      this.workerOverride = -2;
      this.workersLock = new Object();
      this.flushIndex = 0L;
      this.connectionPolicy = JDBCStoreIO.ConnectionCachingPolicy.DEFAULT;
      this.enableAutoCommit = this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.DEFAULT;
      this.getConnectionOnDemand = this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.NONE;
      this.storeConfiguredName = null;
      this.RESTORE_METADATA = Boolean.parseBoolean(System.getProperty("weblogic.store.RestoreMetaData", "true"));
      this.activeWorkerCount = new AtomicInteger(0);
      this.roundRobinIndex = 0;
      RjvmInfo rjvmInfo = RjvmInfo.getRjvmInfo();
      this.serverName = rjvmInfo != null ? rjvmInfo.getServerName() : "client";
      this.storeName = _storeName;
      this.dataSource = _dataSource;
      this.tableRef = _tableRef;
      this.createTableDDLFile = _createTableDDLFile;
      this.maxDeleteStatementsPerBatch = _maxDeleteStatementsPerBatch;
      this.maxInsertStatementsPerBatch = _maxInsertStatementsPerBatch;
      this.maxDeleteCount = _maxDeletesPerStatement;
      this.retryPeriodMilliseconds = Math.max(_retryPeriodMilliseconds, 200);
      this.retryPeriodMilliseconds = Math.min(this.retryPeriodMilliseconds, 300000);
      this.retryIntervalMilliseconds = Math.max(_retryIntervalMilliseconds, 100);
      this.retryIntervalMilliseconds = Math.min(this.retryIntervalMilliseconds, 10000);
      this.flushFailureFatal = _flushFailureFatal;
      this.initTableLocking();
      this.tableLockRecord = new TableLockRecord(this);
      if (this.tableLockDebugTrace) {
         this.tableLockDebug("JDBCStoreIO", "<init>: table locking enabled: this.tlr=" + this.tableLockRecord);
      }

   }

   String getServerName() {
      return this.serverName;
   }

   String getStoreName() {
      return this.storeName;
   }

   DataSource getDataSource() {
      return this.dataSource;
   }

   String getTableRef() {
      return this.tableRef;
   }

   String getTableDMLIdentifier() {
      return this.tableDMLIdentifier;
   }

   String getTableDDLIdentifier() {
      return this.tableDDLIdentifier;
   }

   String getIndexDDLIdentifier() {
      return this.indexDDLIdentifier;
   }

   String getStoreShortName() {
      return this.storeConfiguredName;
   }

   /** @deprecated */
   @Deprecated
   public int open(StoreWritePolicy wp, int ignored) throws PersistentStoreException {
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", wp);
      return this.open(config);
   }

   private static int getIntConfiguration(HashMap config, String configName, String myStoreName, String localPropName, int defaultValue) {
      Object cp = config.get(configName);
      if (cp instanceof Integer) {
         defaultValue = (Integer)cp;
      }

      return Integer.getInteger("weblogic.store.jdbc." + myStoreName + localPropName, Integer.getInteger("weblogic.store.jdbc" + localPropName, defaultValue));
   }

   private static boolean getBooleanConfiguration(HashMap config, String configName, String myStoreName, String localPropName, boolean defaultValue) {
      Object cp = config.get(configName);
      if (cp instanceof Boolean) {
         defaultValue = (Boolean)cp;
      }

      return new Boolean(System.getProperty("weblogic.store.jdbc." + myStoreName + localPropName, System.getProperty("weblogic.store.jdbc" + localPropName, (new Boolean(defaultValue)).toString())));
   }

   private static Enum getEnumConfiguration(HashMap config, String configName, String myStoreName, String localPropName, Enum defaultEnumValue) {
      Enum enumValue = null;
      String defaultValStr = defaultEnumValue.toString();
      Object cp = config.get(configName);
      if (cp instanceof String) {
         defaultValStr = (String)cp;
      } else if (cp instanceof Enum && defaultEnumValue.getDeclaringClass().isAssignableFrom(((Enum)cp).getDeclaringClass())) {
         defaultEnumValue = (Enum)cp;
         defaultValStr = defaultEnumValue.toString();
      }

      defaultValStr = System.getProperty("weblogic.store.jdbc" + localPropName, defaultValStr);
      String enumValueStr = System.getProperty("weblogic.store.jdbc." + myStoreName + localPropName, defaultValStr);

      try {
         enumValue = Enum.valueOf(defaultEnumValue.getDeclaringClass(), enumValueStr);
         return enumValue;
      } catch (IllegalArgumentException var16) {
         StringBuilder sb = new StringBuilder();
         sb.append(localPropName).append(": invalid value: ").append(enumValueStr);
         sb.append("; valid values: ");
         int i = 0;
         Enum[] var12 = (Enum[])defaultEnumValue.getDeclaringClass().getEnumConstants();
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            Enum p = var12[var14];
            if (i++ > 0) {
               sb.append(", ");
            }

            sb.append(p);
         }

         throw new IllegalArgumentException(sb.toString());
      }
   }

   public boolean exists(Map config) throws PersistentStoreException {
      Connection conn = null;
      boolean itExists = this.isOpen;
      if (!itExists) {
         try {
            conn = this.dataSource.getConnection();
            DatabaseMetaData mainMetaData = conn.getMetaData();
            itExists = JDBCHelper.tableExists(conn, mainMetaData, this.tableRef);
         } catch (SQLException var8) {
            throw new PersistentStoreException(var8);
         } finally {
            JDBCHelper.close(conn);
         }
      }

      return itExists;
   }

   public int open(HashMap config) throws JDBCStoreException {
      synchronized(this) {
         if (this.isBeingOpened) {
            throw new AssertionError((new JDBCStoreException(this, "store is being opened")).toString());
         }

         if (this.isPrepareToClose || this.isBeingClosed) {
            throw new AssertionError((new JDBCStoreException(this, "store is being closed")).toString());
         }

         if (this.isOpen) {
            throw new AssertionError((new JDBCStoreException(this, "store already open")).toString());
         }

         this.isBeingOpened = true;
      }

      if (config == null) {
         config = new HashMap();
      }

      this.storeConfiguredName = config.containsKey("StoreConfigName") ? (String)config.get("StoreConfigName") : PartitionNameUtils.stripDecoratedPartitionName(this.storeName);
      this.workerCount = getIntConfiguration(config, "WorkerCount", this.storeName, ".WorkerCount", 1);
      if (this.workerCount < 1) {
         StoreLogger.logInvalidIntegerProperty("WorkerCount", String.valueOf(this.workerCount), 1);
         this.workerCount = 1;
      }

      try {
         this.connectionPolicy = getConnectionPolicy(config, this.getStoreName());
      } catch (IllegalArgumentException var43) {
         throw new JDBCStoreException(this, var43.getMessage());
      }

      this.enableAutoCommit = this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.DEFAULT;
      this.getConnectionOnDemand = this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.NONE;
      if (this.getConnectionOnDemand && this.workerCount > 1) {
         StoreLogger.logJDBCStoreConnectionPolicyIncompatibile(this.storeConfiguredName, JDBCStoreIO.ConnectionCachingPolicy.NONE.toString(), "WorkerCount", Integer.toString(this.workerCount));
         throw new JDBCStoreException(this, StoreLogger.logJDBCStoreConnectionPolicyIncompatibileLoggable(this.storeConfiguredName, JDBCStoreIO.ConnectionCachingPolicy.NONE.toString(), "WorkerCount", Integer.toString(this.workerCount)));
      } else {
         long recordCount;
         if (this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.NONE) {
            StoreLogger.logJDBCStoreUseUncachedConnections(this.storeConfiguredName, JDBCStoreIO.ConnectionCachingPolicy.NONE.toString());
         } else {
            recordCount = (long)((this.enableAutoCommit ? 2 : 1) + (this.workerCount > 1 ? this.workerCount : 0));
            StoreLogger.logJDBCStoreConnectionPolicy(this.storeConfiguredName, this.connectionPolicy.toString(), recordCount);
         }

         this.lbStrategy = getIntConfiguration(config, "LBStrategy", this.storeName, ".LBStrategy", 1);
         if (this.lbStrategy != 0 && this.lbStrategy != 1 && this.lbStrategy != 2) {
            StoreLogger.logInvalidIntegerProperty("LBStrategy", String.valueOf(this.lbStrategy), 1);
            this.lbStrategy = 1;
         }

         this.workerPreferredBatchSize = getIntConfiguration(config, "WorkerPreferredBatchSize", this.storeName, ".WorkerPreferredBatchSize", 10);
         if (this.workerCount > 1) {
            if (this.workerPreferredBatchSize < 1) {
               StoreLogger.logInvalidIntegerProperty("WorkerPreferredBatchSize", String.valueOf(this.workerPreferredBatchSize), 10);
               this.workerPreferredBatchSize = 10;
            }
         } else {
            this.workerPreferredBatchSize = Integer.MAX_VALUE;
         }

         this.threeStepThreshold = getIntConfiguration(config, "ThreeStepThreshold", this.storeName, ".ThreeStepThreshold", 200000);
         if (this.threeStepThreshold < 4000) {
            StoreLogger.logInvalidIntegerProperty("ThreeStepThreshold", String.valueOf(this.threeStepThreshold), 200000);
            this.threeStepThreshold = 200000;
         }

         this.isOraclePiggybackCommitEnabled = getBooleanConfiguration(config, "OraclePiggybackCommitEnabled", this.storeName, ".OraclePiggybackCommitEnabled", false);
         if (this.isOraclePiggybackCommitEnabled && this.connectionPolicy == JDBCStoreIO.ConnectionCachingPolicy.NONE) {
            this.isOraclePiggybackCommitEnabled = false;
            StoreLogger.logJDBCStorePiggyBackCommitDisabled(this.storeConfiguredName, JDBCStoreIO.ConnectionCachingPolicy.NONE.toString());
         }

         this.handleTracking = new HandleTracking(16);
         this.pDeletes = new PendingDeleteHashMap();
         this.pInserts = new PendingInsertHashMap();
         this.lockedHandles = new HashSet();
         recordCount = 0L;
         Exception openException = null;
         Object mainConnACLock = null;
         Object mainConnLCLock = null;
         boolean islogErrorForMustBeMe = true;
         boolean isTableOwnershipSet = false;
         if (config.containsKey("IslogErrorForMustBeMe") && !(Boolean)config.get("IslogErrorForMustBeMe")) {
            islogErrorForMustBeMe = false;
         }

         try {
            Object[] locks = this.initialize(false, true);
            mainConnLCLock = locks[0];
            if (locks.length > 1) {
               mainConnACLock = locks[1];
            }

            Object maxRetrySeconds = config == null ? null : config.get("MaxRetrySeconds");
            int defaultRetryPeriodMillis = this.mainConnLC.getRetryPeriodMillis();
            this.flushRetryTimeoutMillis = maxRetrySeconds == null ? defaultRetryPeriodMillis : (Integer)maxRetrySeconds * 1000;
            DatabaseMetaData mainMetaData = this.mainConnLC.getMetaData();
            if (!JDBCHelper.tableExists(this.mainConnLC.getConnection(), mainMetaData, this.tableRef)) {
               this.createTable(this.mainConnLC, mainMetaData);
               this.mainConnLC.commit();
               if (!JDBCHelper.tableExists(this.mainConnLC.getConnection(), mainMetaData, this.tableRef)) {
                  throw new JDBCStoreException(this, "failed to find tables after successful create, check connection pool's database permissions, consider specifying schema in table name prefix");
               }
            }

            IORecord ior = this.internalRead(this.mainConnLC, -1);
            int rowCount;
            if (ior == null) {
               rowCount = this.mainConnLC.getRowCount();
               if (rowCount == 0) {
                  this.initializeEmptyTable();
               } else {
                  if (!this.RESTORE_METADATA) {
                     StoreLogger.logJDBCStoreVersionRecMissing(this.storeName, this.tableRef);
                     throw new JDBCStoreException(this, "Expected empty table; version record missing; found " + rowCount + " rows");
                  }

                  StoreLogger.logJDBCStoreRestoreMissingVersionRec(this.storeName, this.tableRef);
                  this.restoreVersionRow();
               }
            } else {
               rowCount = ior.getHandle();
               if (rowCount < 2 || rowCount > 3) {
                  throw new JDBCStoreException(this, "Incompatible JDBC store table version " + rowCount + ", expected " + 2 + " or " + 3 + ".");
               }

               if (this.tableLockStampingEnabled || rowCount < 3) {
                  if (this.tableLockDebugTrace) {
                     this.tableLockDebug("open", "getting table lock ownership for this.tlr=" + this.tableLockRecord);
                  }

                  synchronized(this.tableLockRecord) {
                     this.getTableOwnershipLogical(this.mainConnLC, islogErrorForMustBeMe);
                  }

                  isTableOwnershipSet = true;
               }
            }

            this.checkPartitions();
            JDBCStoreCursor cursor = new JDBCStoreCursor(this, -4, false, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds);

            JDBCStoreCursor.LocalIORecord rec;
            try {
               while(null != (rec = (JDBCStoreCursor.LocalIORecord)cursor.next())) {
                  if (rec.getRowId() > 0) {
                     this.handleTracking.bootRowId(rec.getRowId());
                  }

                  if (rec.getTypeCode() > 0) {
                     try {
                        this.handleTracking.bootHandle(rec.getTypeCode(), rec.getHandle(), rec.getRowId());
                        ++recordCount;
                     } catch (AssertionError var45) {
                        if (!BOOT_ON_ERROR) {
                           if (!tableAlreadyDumped) {
                              try {
                                 this.dumpTable("- BOOT FAILURE");
                              } catch (Throwable var44) {
                                 if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                                    StoreDebug.storeIOPhysical.debug("JDBCStoreIO.open() dumpTable failed " + var44);
                                 }
                              }
                           }

                           throw var45;
                        }

                        StoreLogger.logStoreJDBCIgnoreAssertionError(this.storeName, rec.getTypeCode(), rec.getHandle(), rec.getRowId());
                     }
                  } else if (rec.getTypeCode() == -2) {
                     this.checkSavedStoreException();
                  }
               }
            } finally {
               cursor.close(false);
            }

            this.handleTracking.bootFreeLists();
         } catch (PersistentStoreException var47) {
            openException = var47;
         } catch (SQLException var48) {
            openException = var48;
         } catch (RuntimeException var49) {
            openException = var49;
         } catch (Throwable var50) {
            openException = new Exception(var50);
         } finally {
            if (mainConnLCLock != null) {
               this.mainConnLC.unlock(mainConnLCLock, openException != null);
            }

            if (mainConnACLock != null) {
               this.mainConnAC.unlock(mainConnACLock, openException != null);
            }

            if (openException != null) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("JDBCStoreIO.open() failed, isTableOwnershipSet " + isTableOwnershipSet);
               }

               if (isTableOwnershipSet) {
                  this.updateTableOwnershipFromClose();
               }

               this.closedb();
               this.debugDump();
               if (islogErrorForMustBeMe) {
                  StoreLogger.logJDBCStoreOpenFailed(this.storeName, this.tableRef, (Throwable)openException);
               }

               this.isBeingOpened = false;
               throw new JDBCStoreException(this, "open failed", (Throwable)openException);
            }

         }

         if (this.tableLockStampingEnabled) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("open", "starting timer with refreshInterval=" + this.tableLockRefreshInterval);
            }

            this.mainConnLC.startPingTimer(this.tableLockStampingEnabled, this.tableLockRefreshInterval);
         } else {
            this.mainConnLC.startPingTimer();
         }

         if (this.mainConnAC != null) {
            this.mainConnAC.startPingTimer();
         }

         this.isOpen = true;
         this.isBeingOpened = false;
         this.debugDump();
         StoreLogger.logJDBCStoreOpened(this.storeName, this.tableRef, recordCount);
         return (int)recordCount;
      }
   }

   private void checkSavedStoreException() throws PersistentStoreException {
      PersistentStoreTestException testException = this.readStoreTestException();
      if (testException.shouldFailOnBoot()) {
         PersistentStoreTestException testExceptionToBeSaved = null;

         try {
            Date failUntilDate = testException.getBootFailureUntil();
            int failCount = testException.getBootFailureCount();
            if (failUntilDate != null) {
               Date now = new Date();
               if (now.before(failUntilDate)) {
                  testExceptionToBeSaved = new PersistentStoreTestException(testException.getMessage());
                  testExceptionToBeSaved.clearFailOnFlush();
                  testExceptionToBeSaved.setBootFailureUntil(failUntilDate);
                  throw testException;
               }
            }

            if (failCount > 0) {
               --failCount;
               if (failCount > 0) {
                  testExceptionToBeSaved = new PersistentStoreTestException(testException.getMessage());
                  testExceptionToBeSaved.clearFailOnFlush();
                  testExceptionToBeSaved.setBootFailureCount(failCount);
               }

               throw testException;
            }
         } finally {
            if (testExceptionToBeSaved != null) {
               this.saveStoreTestException(testExceptionToBeSaved, false);
            } else {
               this.deleteStoreTestException();
            }

         }
      }

   }

   private Object[] initialize(boolean ignoreIfActive, boolean skipPing) throws PersistentStoreException, SQLException {
      boolean cleanUpLocks = true;
      Object[] locks = new Object[this.enableAutoCommit ? 2 : 1];

      try {
         this.mainConnLC = new ReservedConnection(this, false, (String)null, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds, this.getConnectionOnDemand);
         String affinityInstance = this.mainConnLC.getInstance();
         locks[0] = this.mainConnLC.lock(ignoreIfActive, skipPing);
         if (this.enableAutoCommit) {
            try {
               this.mainConnAC = new ReservedConnection(this, true, affinityInstance, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds, this.getConnectionOnDemand);
               locks[1] = this.mainConnAC.lock(ignoreIfActive, skipPing);
            } catch (SQLException var18) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  StoreDebug.storeIOPhysical.debug("JDBC " + this.storeName + ": error when creating the auto-commit connection", var18);
               }

               throw var18;
            }
         }

         DatabaseMetaData mainMetaData = this.mainConnLC.getMetaData();
         this.enableBatchInserts = this.maxInsertStatementsPerBatch > 1 && JDBCHelper.isBatchCapable(mainMetaData);
         this.enableBatchDeletes = this.maxDeleteStatementsPerBatch > 1 && JDBCHelper.isBatchCapable(mainMetaData);
         if (this.enableBatchDeletes) {
            this.maxDeleteCount = 1;
         } else {
            this.maxDeleteCount = Math.min(this.maxDeleteCount, JDBCHelper.getMaxORDeleteCount(mainMetaData));
         }

         if (this.mainConnAC != null) {
            this.mainConnAC.setMaxDeleteCount(this.maxDeleteCount);
         }

         this.mainConnLC.setMaxDeleteCount(this.maxDeleteCount);
         String[] trace = null;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            trace = new String[1];
         }

         this.dbmsType = JDBCHelper.getDBMSType(mainMetaData, trace);
         if (trace != null && trace[0] != null) {
            this.mainConnLC.debug(trace[0]);
         }

         this.tableDMLIdentifier = JDBCHelper.getDMLIdentifier(mainMetaData, this.tableRef, this.dbmsType);
         this.tableDDLIdentifier = JDBCHelper.getDDLIdentifier(mainMetaData, this.tableRef, this.dbmsType);
         this.indexDDLIdentifier = JDBCHelper.getIndexIdentifier(mainMetaData, this.tableRef + "X", this.dbmsType);
         int i;
         synchronized(this.workersLock) {
            this.workerShutdownLatch = new CountDownLatch(this.workerCount > 1 ? this.workerCount : 0);
            this.workers = new JDBCStoreWorker[this.workerCount > 1 ? this.workerCount : 0];
            if (this.workerCount > 1) {
               for(i = 0; i < this.workerCount; ++i) {
                  this.workers[i] = new JDBCStoreWorker(i, new ReservedConnection(this, false, affinityInstance, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds, this.getConnectionOnDemand));
               }
            }
         }

         if (this.mainConnAC != null && this.mainConnAC.isUsingGridLinkDS() || this.workers.length > 0 && this.workers[0].getConnection().isUsingGridLinkDS()) {
            this.mainConnLC.setUsingGridLinkDS(true);
            StoreLogger.logJDBCStoreInitialRACInstance(this.storeName, affinityInstance);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.mainConnLC.debug("mainConnLC is on RAC instance=" + affinityInstance);
            }

            ArrayList rcConnList = new ArrayList();
            rcConnList.add(this.mainConnLC);
            if (this.enableAutoCommit) {
               rcConnList.add(this.mainConnAC);
            }

            for(i = 0; i < this.workers.length; ++i) {
               rcConnList.add(this.workers[i].getConnection());
            }

            ReservedConnection[] rcConns = new ReservedConnection[rcConnList.size()];
            rcConns = (ReservedConnection[])rcConnList.toArray(rcConns);
            this.racState = new RACState(this.storeName, affinityInstance, this.dataSource, rcConns);
         }

         cleanUpLocks = false;
      } catch (SQLException var20) {
         throw var20;
      } catch (PersistentStoreException var21) {
         throw var21;
      } catch (Throwable var22) {
         throw new PersistentStoreException(var22);
      } finally {
         if (cleanUpLocks) {
            if (locks[0] != null) {
               this.mainConnLC.unlock(locks[0]);
               locks[0] = null;
            }

            if (this.enableAutoCommit && locks[1] != null) {
               this.mainConnAC.unlock(locks[1]);
               locks[1] = null;
            }
         }

      }

      return locks;
   }

   RACState getRACState() {
      return this.racState;
   }

   void createTable(ReservedConnection rconn, DatabaseMetaData metaData) throws PersistentStoreException {
      if (this.isOpen) {
         throw new JDBCStoreException(this, "store is already open, can not create table");
      } else {
         String fileName = this.createTableDDLFile;
         InputStream is = null;
         if (fileName == null) {
            String pn;
            String dn;
            if (this.dbmsType == 0) {
               pn = "";
               dn = "";

               try {
                  pn = metaData.getDatabaseProductName();
                  dn = metaData.getDriverName();
               } catch (SQLException var20) {
               }

               throw new JDBCStoreException(this, StoreLogger.logJDBCStoreCreateUnknownDatabaseLoggable(pn, dn));
            }

            pn = JDBCHelper.getDBMSTypeString(this.dbmsType);
            dn = "/weblogic/store/io/jdbc/ddl/" + pn;
            fileName = dn + ".ddl";
         } else {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               rconn.debug("loading create table ddl in " + fileName + " from file path");
            }

            try {
               is = new FileInputStream(fileName);
            } catch (FileNotFoundException var24) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  rconn.debug("could not load create table ddl in " + fileName + " from file path, trying classpath");
               }
            }
         }

         if (is == null) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               rconn.debug("loading create table ddl in " + fileName + " from classpath");
            }

            is = this.getClass().getResourceAsStream(fileName);
            if (is == null) {
               if (!fileName.startsWith("/")) {
                  is = this.getClass().getResourceAsStream("/" + fileName);
               }

               if (is == null) {
                  throw new JDBCStoreException(this, StoreLogger.logJDBCStoreCreateDDLFileNotFoundLoggable(fileName));
               }
            }
         }

         Exception exc = null;

         try {
            rconn.executeDDLStream((InputStream)is);
            StoreLogger.logJDBCStoreTableCreateSuccess(this.storeName, this.tableRef, fileName);
         } catch (SQLException var21) {
            exc = var21;
         } catch (IOException var22) {
            exc = var22;
         } finally {
            try {
               ((InputStream)is).close();
            } catch (IOException var19) {
            }

         }

         if (exc != null) {
            throw new JDBCStoreException(this, StoreLogger.logJDBCStoreTableCreateFailedLoggable(fileName), (Throwable)exc);
         }
      }
   }

   public void destroy() throws PersistentStoreException {
      synchronized(this) {
         if (this.isOpen) {
            throw new JDBCStoreException(this, "store is open, can not drop table");
         }
      }

      Object mainConnLCLock = null;
      Object mainConnACLock = null;

      try {
         Object[] locks = this.initialize(false, true);
         mainConnLCLock = locks[0];
         if (locks.length > 1) {
            mainConnACLock = locks[1];
         }

         if (!JDBCHelper.tableExists(this.mainConnLC.getConnection(), this.mainConnLC.getMetaData(), this.tableRef)) {
            return;
         }

         this.mainConnLC.executeDDLString("DROP TABLE $TABLE");
         this.mainConnLC.commit();
      } catch (SQLException var8) {
         throw new JDBCStoreException(this, var8.toString(), var8);
      } finally {
         if (mainConnLCLock != null) {
            this.mainConnLC.unlock(mainConnLCLock);
         }

         if (mainConnACLock != null) {
            this.mainConnAC.unlock(mainConnACLock);
         }

         this.closedb();
      }

   }

   private void initializeEmptyTable() throws PersistentStoreException, SQLException {
      this.insertVersionRow();
      int[] lockPartitions = this.handleTracking.getLockPartitionRowIds();

      for(int i = 0; i < lockPartitions.length; ++i) {
         this.mainConnLC.fillInsertStatement(lockPartitions[i], -3, 0, (ByteBuffer[])null, true, false);
      }

      this.mainConnLC.commit();
      IORecord ior = this.internalRead(this.mainConnLC, -1);
      if (ior == null) {
         throw new JDBCStoreException(this, "Failed to find version record even though it inserted correctly.");
      }
   }

   private void restoreVersionRow() throws PersistentStoreException, SQLException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("The version record is missing; repairing the table.");
      }

      try {
         this.insertVersionRow();
         this.mainConnLC.commit();
      } catch (PersistentStoreException var2) {
         throw var2;
      } catch (SQLException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new PersistentStoreException(var4);
      }
   }

   private void insertVersionRow() throws PersistentStoreException, SQLException {
      ByteBuffer[] bba = null;
      if (this.tableLockStampingEnabled) {
         this.tableLockRecord.setTimeStamp(System.currentTimeMillis());
         bba = new ByteBuffer[]{this.tableLockRecord.toBB()};
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("insertVersionRow", "initalizing table lock with this.tlr=" + this.tableLockRecord);
         }
      }

      this.mainConnLC.fillInsertStatement(-1, -1, 3, bba, true, false);
   }

   private void checkPartitions() throws PersistentStoreException, SQLException {
      int[] lockPartitions = this.handleTracking.getLockPartitionRowIds();
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.mainConnLC.debug("checking " + lockPartitions.length + " partitions");
      }

      for(int i = 0; i < lockPartitions.length; ++i) {
         IORecord part = this.internalRead(this.mainConnLC, lockPartitions[i]);
         if (part == null) {
            throw new JDBCStoreException(this, "Failed to find partition record.");
         }

         if (part.getTypeCode() != -3) {
            throw new JDBCStoreException(this, "Incorrect partition record type = " + part.getTypeCode());
         }
      }

   }

   private IORecord internalRead(ReservedConnection rConnection, int row) throws PersistentStoreException, SQLException {
      ResultSet results = null;
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         rConnection.debug("internalRead row=" + row);
      }

      try {
         PreparedStatement readOneRowStatement = rConnection.getReadOneRowStatement();
         readOneRowStatement.setInt(1, row);
         results = readOneRowStatement.executeQuery();
         if (this.isOracleBlobRecord == null && results != null) {
            this.isOracleBlobRecord = JDBCHelper.isOracleBlobColumn(this.dbmsType, results, 4) ? Boolean.TRUE : Boolean.FALSE;
         }

         if (results != null && results.next()) {
            int typeCode = results.getInt(2);
            int handle = results.getInt(3);
            byte[] b;
            if (this.isOracleBlobRecord()) {
               Blob blob = results.getBlob(4);
               b = blob.getBytes(1L, (int)blob.length());
            } else {
               b = results.getBytes(4);
            }

            ByteBuffer data = ByteBuffer.wrap(b);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               rConnection.debug("internalRead found row=" + row + " t=" + typeCode + " h=" + handle + " b=" + b.length);
            }

            IORecord var9 = new IORecord(handle, typeCode, data);
            return var9;
         }
      } catch (RuntimeException var14) {
         throw new SQLExceptionWrapper(var14);
      } catch (Error var15) {
         throw new JDBCStoreException(this, var15.toString(), var15);
      } finally {
         JDBCHelper.close(results);
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         rConnection.debug("internalRead row=" + row + " not found");
      }

      return null;
   }

   private boolean rowExists(int row) throws PersistentStoreException {
      IORecord rec = null;
      PersistentStoreException err = null;
      Object connLock = this.mainConnLC.lock();

      try {
         rec = this.internalRead(this.mainConnLC, row);
         boolean var5 = rec != null;
         return var5;
      } catch (SQLException var10) {
         err = new JDBCStoreException(this, var10.toString(), var10);
      } catch (PersistentStoreException var11) {
         err = var11;
      } finally {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.mainConnLC.debug("row exists row=" + row + (err != null ? " throw " + err : " return " + (rec != null)));
         }

         this.mainConnLC.unlock(connLock, err != null);
      }

      throw err;
   }

   private void dumpTable(String title) throws SQLException, PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         int tableDumpSize = 2000;
         String propVal = System.getProperty("weblogic.store.jdbc.MaxTableDumpSize");
         if (propVal != null) {
            tableDumpSize = Integer.parseInt(propVal);
         }

         JDBCStoreCursor cursor = new JDBCStoreCursor(this, -4, true, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds);

         try {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               cursor.debug("\nTABLE DUMP " + title);
            }

            StringBuffer sb = new StringBuffer();
            int count = 0;

            JDBCStoreCursor.LocalIORecord rec;
            while(null != (rec = (JDBCStoreCursor.LocalIORecord)cursor.next()) && count++ < tableDumpSize) {
               sb.setLength(0);
               sb.append(count + ":DUMP:");
               sb.append(rec.getRowId()).append(": ");
               switch (rec.getTypeCode()) {
                  case -3:
                     sb.append("partition");
                     break;
                  case -1:
                     sb.append("version");
                     break;
                  default:
                     sb.append("data" + rec.getTypeCode());
               }

               sb.append(' ');
               sb.append(rec.getHandle());
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  cursor.debug(sb.toString());
               }
            }
         } finally {
            cursor.close(false);
            tableAlreadyDumped = true;
         }

      }
   }

   private synchronized void closedb() {
      if (this.isOpen || this.isBeingOpened) {
         this.isOpen = false;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("JDBC SHUTTING DOWN  store='" + this.storeName + "' table='" + this.tableDMLIdentifier + "'");
         }

         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("JDBC SHUTTING DOWN", new Exception("STACK TRACE"));
         }

         if (this.mainConnAC != null) {
            this.mainConnAC.close(false);
         }

         if (this.mainConnLC != null) {
            this.mainConnLC.close(false);
         }

         this.stopAllWorkers();
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("JDBC STORE SHUTDOWN  store='" + this.storeName + "' table='" + this.tableDMLIdentifier + "'");
         }

      }
   }

   private void stopAllWorkers() {
      JDBCStoreWorker[] workerList = null;
      synchronized(this.workersLock) {
         if (this.workers == null) {
            return;
         }

         workerList = new JDBCStoreWorker[this.workers.length];
         System.arraycopy(this.workers, 0, workerList, 0, this.workers.length);
      }

      JDBCStoreWorker[] var2 = workerList;
      int var3 = workerList.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JDBCStoreWorker worker = var2[var4];
         if (worker != null) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("Stopping JDBC store worker " + worker.getID());
            }

            worker.stop();
         }
      }

   }

   int getDBMSType() {
      return this.dbmsType;
   }

   public boolean isHandleReadable(int typeCode, int handle) {
      return this.handleTracking.isValid(typeCode, handle);
   }

   private void checkHandle(int typeCode, int handle) throws JDBCStoreException {
      if (!this.handleTracking.isValid(typeCode, handle)) {
         throw new JDBCStoreException(this, "invalid handle " + handle);
      }
   }

   private static void assertTypeCode(int typeCode) {
      if (typeCode < 0) {
         throw new AssertionError("bad typeCode " + typeCode);
      }
   }

   private void checkOpen() throws PersistentStoreException {
      if (this.fatalError) {
         throw new PersistentStoreFatalException(StoreLogger.logStoreFatalErrorLoggable());
      } else if (!this.isOpen) {
         throw new JDBCStoreException(this, "store is closed");
      } else if (this.isPrepareToClose || this.isBeingClosed) {
         throw new JDBCStoreException(this, "store is shutting down");
      }
   }

   boolean isOracleBlobRecord() {
      return this.isOracleBlobRecord;
   }

   private void doDeletesPass1(ReservedConnection rConnection, PendingDelete first, boolean doBatchDeletes, int toDeleteCount, boolean doAutoCommit) throws JDBCStoreException, SQLException {
      StringBuffer sbtrace = null;
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         sbtrace = new StringBuffer();
      }

      try {
         PreparedStatement deleteStatement = rConnection.getDeleteStatement();
         int count = 0;
         if (doBatchDeletes) {
            if (this.maxDeleteCount != 1) {
               throw new AssertionError();
            }

            for(; first != null; first = first.getNext()) {
               ++count;
               deleteStatement.setInt(1, first.getRowId());
               deleteStatement.addBatch();
               if (sbtrace != null) {
                  sbtrace.append(' ').append(first.getRowId());
               }

               if (count == this.maxDeleteStatementsPerBatch) {
                  rConnection.executeBatchForStatement(deleteStatement, count == toDeleteCount);
                  toDeleteCount -= count;
                  count = 0;
                  if (sbtrace != null) {
                     sbtrace.append(" E");
                  }
               }
            }

            if (count != 0) {
               rConnection.executeBatchForStatement(deleteStatement, true);
               if (sbtrace != null) {
                  sbtrace.append(" E");
               }
            }
         } else {
            int lastRow;
            for(lastRow = 0; first != null; first = first.getNext()) {
               ++count;
               lastRow = first.getRowId();
               deleteStatement.setInt(count, lastRow);
               if (sbtrace != null) {
                  sbtrace.append(' ').append(lastRow);
               }

               if (count == this.maxDeleteCount) {
                  rConnection.executeUpdateForStatement(deleteStatement, !doAutoCommit && count == toDeleteCount);
                  count = 0;
                  if (sbtrace != null) {
                     sbtrace.append(" U");
                  }
               }
            }

            if (count != 0) {
               while(true) {
                  ++count;
                  if (count > this.maxDeleteCount) {
                     rConnection.executeUpdateForStatement(deleteStatement, !doAutoCommit);
                     if (sbtrace != null) {
                        sbtrace.append(" U");
                     }
                     break;
                  }

                  deleteStatement.setInt(count, lastRow);
                  if (sbtrace != null) {
                     sbtrace.append(' ').append(lastRow);
                  }
               }
            }
         }
      } catch (RuntimeException var13) {
         if (sbtrace != null) {
            sbtrace.append(" " + var13);
         }

         throw new SQLExceptionWrapper(var13);
      } finally {
         if (sbtrace != null) {
            rConnection.debug("delete " + sbtrace);
         }

      }

   }

   private void doDeletesPass2AndDestroyList(PendingDelete first) {
      for(PendingDelete nextPD = null; first != null; first = nextPD) {
         nextPD = first.getNext();
         first.setNext((PendingDelete)null);
         int handle = first.getHandleToFree();
         if (handle > 0) {
            this.handleTracking.freeHandle(first.getTypeCode(), handle);
         }

         this.handleTracking.freeRowId(first.getRowId());
      }

   }

   private static ByteBuffer BBGLOMMER(ByteBuffer[] bb) {
      int size = 0;

      for(int i = 0; i < bb.length; ++i) {
         size += bb[i].remaining();
      }

      ByteBuffer ret = ByteBuffer.allocate(size);

      for(int i = 0; i < bb.length; ++i) {
         int hold = bb[i].position();
         ret.put(bb[i]);
         bb[i].position(hold);
      }

      ret.flip();
      return ret;
   }

   public void close() {
      synchronized(this) {
         if (!this.isOpen) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("The store \"" + this.getStoreName() + "\" is not open.");
            }

            return;
         }

         if (this.isBeingClosed) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("The store \"" + this.getStoreName() + "\" is being closed.");
            }

            return;
         }

         this.isBeingClosed = true;
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Closing store \"" + this.getStoreName() + "\".");
      }

      this.stopAllWorkers();
      if (this.workerCount > 1) {
         boolean countedDown = false;

         try {
            countedDown = this.workerShutdownLatch.await(10000L, TimeUnit.MILLISECONDS);
         } catch (InterruptedException var3) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               StoreDebug.storeIOPhysical.debug("InterruptedException while waiting for all threads to terminate.");
            }
         }

         if (!countedDown && StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Maximum wait time for worker thread termination has been exceeded; proceeding with shutdown of store \"" + this.getStoreName() + "\".");
         }
      }

      this.updateTableOwnershipFromClose();
      this.isOpen = false;
      if (this.mainConnAC != null) {
         this.mainConnAC.close(false);
      }

      if (this.mainConnLC != null) {
         this.mainConnLC.close(false);
      }

      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         StoreDebug.storeIOPhysical.debug("Store \"" + this.getStoreName() + "\" has been closed.");
      }

      this.isBeingClosed = false;
   }

   public boolean supportsFastReads() {
      return false;
   }

   public boolean supportsAsyncIO() {
      return this.effectiveWorkerCount > 1;
   }

   public boolean isIdle() {
      return this.activeWorkerCount.get() == 0;
   }

   public int getPreferredFlushLoadSize() {
      return this.workerPreferredBatchSize;
   }

   public int getWorkerCount() {
      return this.effectiveWorkerCount;
   }

   public int allocateHandle(int typeCode) {
      return this.handleTracking.allocHandle(typeCode);
   }

   public void ensureHandleAllocated(int typeCode, int handle) {
      this.handleTracking.ensureHandleAllocated(typeCode, handle);
   }

   public void releaseHandle(int typeCode, int handle) {
      if (this.handleTracking.getRowId(typeCode, handle) != 0) {
         throw new AssertionError();
      } else {
         this.handleTracking.freeHandle(typeCode, handle);
      }
   }

   public void create(int handle, int typeCode, ByteBuffer[] data, int flags) throws PersistentStoreException {
      this.internalUpdate(handle, typeCode, data, flags, true);
   }

   public void update(int handle, int typeCode, ByteBuffer[] data, int flags) throws PersistentStoreException {
      this.internalUpdate(handle, typeCode, data, flags, false);
   }

   private long lockHandle(int typeCode, int handle, long id) throws InterruptedException {
      long key = (long)typeCode << 32 | (long)handle;
      synchronized(this.handleLocks) {
         while(true) {
            Long currentId = (Long)this.handleLocks.get(key);
            if (currentId == null) {
               this.handleLocks.put(key, id);
               return key;
            }

            if (currentId.equals(id)) {
               return key;
            }

            if (this.workerOverride == -2) {
               this.workerOverride = (Integer)this.flushToWorkerMap.get(currentId);
               this.handleLocks.put(key, id);
               return key;
            }

            if (this.workerOverride == (Integer)this.flushToWorkerMap.get(currentId)) {
               this.handleLocks.put(key, id);
               return key;
            }

            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.mainConnLC.debug("JDBC StoreIO flush " + id + " is waiting for the lock of (" + typeCode + "," + handle + ")");
            }

            this.handleLocks.wait();
         }
      }
   }

   private void unlockHandle(long key, long id) {
      Long lockedId = (Long)this.handleLocks.get(key);
      if (lockedId != null && lockedId == id) {
         this.handleLocks.remove(key);
         this.handleLocks.notifyAll();
      }

   }

   private void internalUpdate(int handle, int typeCode, ByteBuffer[] data, int flags, boolean isCreate) throws PersistentStoreException {
      this.checkOpen();
      assertTypeCode(typeCode);
      if (this.supportsAsyncIO() && !isCreate) {
         try {
            this.lockedHandles.add(this.lockHandle(typeCode, handle, this.flushIndex));
         } catch (InterruptedException var8) {
            throw new JDBCStoreException(this, "handle locking is interrupted");
         }
      }

      this.checkHandle(typeCode, handle);
      if (data != null && data.length != 0) {
         PendingInsert pi = new PendingInsert(typeCode, handle, data);
         if (this.pInserts.put(pi) == null && !isCreate) {
            int oldRow = this.handleTracking.getRowId(typeCode, handle);
            if (oldRow != 0) {
               this.pDeletes.put(new PendingDelete(typeCode, handle, oldRow, false));
            }
         }

      } else {
         throw new AssertionError();
      }
   }

   public IORecord read(int handle, int typeCode) throws PersistentStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.mainConnLC.debug("read t=" + typeCode + " h=" + handle);
      }

      assertTypeCode(typeCode);
      this.checkHandle(typeCode, handle);
      int minReadAttempts = 2;
      long retryPeriodEndMillis = this.flushRetryTimeoutMillis == 0 ? 0L : this.getCurrentTimeMillisAtStartOfRecovery() + (long)this.flushRetryTimeoutMillis;
      PersistentStoreException pse = null;

      while(true) {
         this.checkOpen();

         try {
            this.updateTableOwnershipFromIO(this.mainConnLC);
            return this.readInner(handle, typeCode);
         } catch (OwnershipException var8) {
            throw var8;
         } catch (PersistentStoreException var9) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.mainConnLC.debug("retry read failed, t=" + typeCode + " h=" + handle, var9);
            }

            --minReadAttempts;
            if (minReadAttempts <= 0 && (this.flushRetryTimeoutMillis == 0 || this.getCurrentTimeMillis() >= retryPeriodEndMillis)) {
               throw var9;
            }
         }
      }
   }

   IORecord readInner(int handle, int typeCode) throws JDBCStoreException {
      if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
         this.mainConnLC.debug("readInner t=" + typeCode + " h=" + handle);
      }

      PendingInsert pi = this.pInserts.get(typeCode, handle);
      if (pi != null) {
         ByteBuffer[] bba = pi.getBB();
         ByteBuffer bb = bba == null ? null : BBGLOMMER(bba);
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            this.mainConnLC.debug("readInner return pending insert");
         }

         return new IORecord(handle, typeCode, bb);
      } else {
         int row = this.handleTracking.getRowId(typeCode, handle);
         if (row <= 0) {
            throw new JDBCStoreException(this, "invalid handle " + handle);
         } else {
            Object connLock = this.mainConnLC.lock();
            JDBCStoreException readException = null;

            try {
               IORecord rec = this.internalRead(this.mainConnLC, row);
               if (rec == null) {
                  throw new SQLException("record not found, handle=" + handle + " row=" + row);
               }

               IORecord var8 = rec;
               return var8;
            } catch (SQLException var14) {
               readException = new JDBCStoreException(this, var14.toString(), var14);
            } catch (JDBCStoreException var15) {
               readException = var15;
            } catch (PersistentStoreException var16) {
               readException = new JDBCStoreException(this, var16.toString(), var16);
            } finally {
               this.mainConnLC.unlock(connLock, readException != null);
            }

            throw readException;
         }
      }
   }

   public void delete(int handle, int typeCode, int flags) throws PersistentStoreException {
      this.checkOpen();
      assertTypeCode(typeCode);
      if (this.supportsAsyncIO()) {
         try {
            this.lockedHandles.add(this.lockHandle(typeCode, handle, this.flushIndex));
         } catch (InterruptedException var5) {
            throw new JDBCStoreException(this, "handle locking is interrupted");
         }
      }

      this.checkHandle(typeCode, handle);
      if (this.pInserts.remove(typeCode, handle) != null) {
         PendingDelete pd = this.pDeletes.get(typeCode, handle);
         if (pd == null) {
            this.handleTracking.freeHandle(typeCode, handle);
         } else {
            pd.enableHandleFree();
         }

      } else if (this.pDeletes.get(typeCode, handle) == null) {
         int currentRow = this.handleTracking.getRowId(typeCode, handle);
         if (currentRow == 0) {
            this.handleTracking.freeHandle(typeCode, handle);
         } else {
            this.pDeletes.put(new PendingDelete(typeCode, handle, currentRow, true));
         }

      }
   }

   public int drop(int typeCode) throws PersistentStoreException {
      return this._drop(typeCode);
   }

   public int _drop(int typeCode) throws PersistentStoreException {
      int dropCount = 0;

      for(int handle = this.handleTracking.getFirstHandle(typeCode); handle != -1; handle = this.handleTracking.getNextHandle()) {
         if (0 != this.handleTracking.getRowId(typeCode, handle) || null != this.pInserts.get(typeCode, handle)) {
            this.delete(handle, typeCode, 0);
            ++dropCount;
            if (dropCount > 50) {
               break;
            }
         }
      }

      return dropCount;
   }

   public int getNumObjects(int typeCode) {
      int count = 0;

      for(int handle = this.handleTracking.getFirstHandle(typeCode); handle != -1; handle = this.handleTracking.getNextHandle()) {
         if (this.handleTracking.getRowId(typeCode, handle) != 0) {
            ++count;
         }
      }

      return count;
   }

   public void flush() throws PersistentStoreException {
      this.flush((IOListener)null);
   }

   public void flush(IOListener listener) throws PersistentStoreException {
      this.checkOpen();
      boolean var22 = false;

      Iterator var32;
      long lh;
      label313: {
         try {
            var22 = true;
            if (this.testStoreException != null) {
               boolean isFatalFlushException = false;
               PersistentStoreException flushException = this.testStoreException;
               this.testStoreException = null;
               if (flushException instanceof PersistentStoreTestException) {
                  PersistentStoreTestException testException = (PersistentStoreTestException)flushException;
                  if (testException.shouldFailOnBoot()) {
                     this.saveStoreTestException(testException, true);
                  }

                  if (!testException.shouldFailOnFlush()) {
                     flushException = null;
                  } else {
                     isFatalFlushException = testException.isFatalFailure();
                  }
               } else {
                  isFatalFlushException = flushException instanceof PersistentStoreFatalException;
               }

               if (flushException != null) {
                  String logMessage;
                  if (isFatalFlushException) {
                     this.fatalError = true;
                     logMessage = "test fatal exception";
                  } else {
                     logMessage = "test non-fatal exception";
                  }

                  if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                     StoreDebug.storeIOPhysical.debug(logMessage, flushException);
                  }

                  throw flushException;
               }
            }

            if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
               this.mainConnLC.debugVerbose("flush delCnt=" + this.pDeletes.size() + " insCnt=" + this.pInserts.size());
            }

            if (this.pDeletes.size() != 0 || this.pInserts.size() != 0) {
               PendingInsert[] newInserts = this.pInserts.getAllPendingInsert();
               PendingDelete[] newDeletes = this.pDeletes.getAllPendingDelete();
               this.allocateRowId(newInserts);
               if (listener == null) {
                  try {
                     this.flushWithRetry((ReservedConnection)null, this.pInserts.getAllPendingInsert(), this.pDeletes.getAllPendingDelete());
                     var22 = false;
                     break label313;
                  } catch (Error var24) {
                     throw new PersistentStoreException(var24);
                  }
               } else {
                  if (this.workerCount == 1) {
                     throw new AssertionError("Async I/O requested when unsupported by store configuration.");
                  }

                  int target;
                  if (this.workerOverride != -2) {
                     target = this.workerOverride;
                  } else {
                     target = this.assignWorker();
                  }

                  synchronized(this.handleLocks) {
                     this.flushToWorkerMap.put(this.flushIndex, target);
                  }

                  this.workers[target].putLoad(new WorkLoad(this.flushIndex, newInserts, newDeletes, (Long[])((Long[])this.lockedHandles.toArray(new Long[0])), listener));
                  this.lockedHandles.clear();
                  var22 = false;
                  break label313;
               }
            }

            var22 = false;
         } catch (PersistentStoreException var28) {
            if (!this.flushFailureFatal) {
               throw new PersistentStoreException(var28);
            }

            this.fatalError = true;
            this.prepareToClose();
            this.stopAllWorkers();
            throw new PersistentStoreFatalException(var28);
         } finally {
            if (var22) {
               this.workerOverride = -2;
               if (this.lockedHandles.size() > 0) {
                  synchronized(this.handleLocks) {
                     Iterator var11 = this.lockedHandles.iterator();

                     while(true) {
                        if (!var11.hasNext()) {
                           break;
                        }

                        long lh = (Long)var11.next();
                        this.unlockHandle(lh, this.flushIndex);
                     }
                  }

                  this.lockedHandles.clear();
               }

               ++this.flushIndex;
               this.pInserts.clear();
               this.pDeletes.clear();
            }
         }

         this.workerOverride = -2;
         if (this.lockedHandles.size() > 0) {
            synchronized(this.handleLocks) {
               var32 = this.lockedHandles.iterator();

               while(true) {
                  if (!var32.hasNext()) {
                     break;
                  }

                  lh = (Long)var32.next();
                  this.unlockHandle(lh, this.flushIndex);
               }
            }

            this.lockedHandles.clear();
         }

         ++this.flushIndex;
         this.pInserts.clear();
         this.pDeletes.clear();
         return;
      }

      this.workerOverride = -2;
      if (this.lockedHandles.size() > 0) {
         synchronized(this.handleLocks) {
            var32 = this.lockedHandles.iterator();

            while(true) {
               if (!var32.hasNext()) {
                  break;
               }

               lh = (Long)var32.next();
               this.unlockHandle(lh, this.flushIndex);
            }
         }

         this.lockedHandles.clear();
      }

      ++this.flushIndex;
      this.pInserts.clear();
      this.pDeletes.clear();
   }

   private void saveStoreTestException(PersistentStoreTestException testException, boolean shouldLock) throws PersistentStoreException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PreparedStatement pStatement = null;
      if (shouldLock) {
         try {
            this.updateTableOwnershipFromIO(this.mainConnLC);
         } catch (OwnershipException var18) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.mainConnLC.debug("save test exception update Ownership failed with OwnershipException:" + var18);
            }

            throw var18;
         } catch (JDBCStoreException var19) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               this.mainConnLC.debug("save test exception update Ownership failed with JDBCStoreException:" + var19);
            }
         }
      }

      Object lockLC = null;
      Object lockAC = null;

      try {
         if (shouldLock) {
            lockLC = this.mainConnLC.lock();
            if (this.mainConnAC != null) {
               lockAC = this.mainConnAC.lock();
            }
         }

         ObjectOutputStream oos = new ObjectOutputStream(baos);
         oos.writeObject(testException);
         oos.flush();
         byte[] baosBytes = baos.toByteArray();
         oos.close();
         ByteBuffer[] bba = new ByteBuffer[]{ByteBuffer.allocate(baosBytes.length)};
         bba[0].put(baosBytes);
         bba[0].flip();
         IORecord rec = this.internalRead(this.mainConnLC, -2);
         if (rec != null) {
            pStatement = this.mainConnLC.getUpdateStatement();
            this.mainConnLC.fillUpdateStatement(-2, -2, -2, bba);
         } else {
            pStatement = this.mainConnLC.getInsertRowStatement();
            this.mainConnLC.fillInsertStatement(-2, -2, -2, bba, false, false);
         }

         pStatement.executeUpdate();
         this.mainConnLC.commit();
      } catch (Throwable var16) {
         throw new JDBCStoreException(this, "saveStoreTestException failed", var16);
      } finally {
         if (lockLC != null) {
            this.mainConnLC.unlock(lockLC);
         }

         if (lockAC != null) {
            this.mainConnAC.unlock(lockAC);
         }

      }

   }

   private PersistentStoreTestException readStoreTestException() throws PersistentStoreException {
      PersistentStoreTestException testException = null;

      try {
         IORecord rec = this.internalRead(this.mainConnLC, -2);
         ByteArrayInputStream bais = new ByteArrayInputStream(rec.getData().array());
         ObjectInputStream ois = new FilteringObjectInputStream(bais);
         testException = (PersistentStoreTestException)ois.readObject();
         return testException;
      } catch (Throwable var5) {
         throw new JDBCStoreException(this, "readStoreTestException failed", var5);
      }
   }

   private void deleteStoreTestException() throws PersistentStoreException {
      PreparedStatement pStatement = null;

      try {
         String sql = "DELETE FROM " + this.getTableDMLIdentifier() + " WHERE " + "id" + "= ?";
         pStatement = this.mainConnLC.prepareStatement(sql);
         pStatement.setInt(1, -2);
         this.mainConnLC.executeUpdateForStatement(pStatement, false);
         this.mainConnLC.commit();
      } catch (Throwable var3) {
         throw new JDBCStoreException(this, "deleteStoreTestException failed", var3);
      }
   }

   private void allocateRowId(PendingInsert[] ins) {
      if (ins != null) {
         PendingInsert[] var2 = ins;
         int var3 = ins.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PendingInsert pi = var2[var4];
            int myRowid = this.handleTracking.allocRowId();
            pi.setRowid(myRowid);
            this.handleTracking.setRowId(pi.getTypeCode(), pi.getHandle(), myRowid);
         }
      }

   }

   private int assignWorker() throws PersistentStoreException {
      int minAboveIndex;
      int minAboveLoad;
      switch (this.lbStrategy) {
         case 0:
            int target = 0;
            int minLoad = this.workers[0].getOperationCount();
            minAboveIndex = 1;

            for(int thisLoad = false; minAboveIndex < this.workers.length; ++minAboveIndex) {
               if ((minAboveLoad = this.workers[minAboveIndex].getOperationCount()) < minLoad) {
                  target = minAboveIndex;
                  minLoad = minAboveLoad;
               }
            }

            return target;
         case 1:
            minAboveIndex = -1;
            minAboveLoad = Integer.MAX_VALUE;
            int minUnderIndex = -1;
            int minUnderLoad = this.workerPreferredBatchSize;
            int zeroIndex = -1;

            for(int i = 0; i < this.workers.length; ++i) {
               int thisLoad = this.workers[i].getOperationCount();
               if (thisLoad >= this.workerPreferredBatchSize) {
                  if (thisLoad < minAboveLoad) {
                     minAboveLoad = thisLoad;
                     minAboveIndex = i;
                  }
               } else if (thisLoad == 0) {
                  if (zeroIndex == -1) {
                     zeroIndex = i;
                  }
               } else if (thisLoad < minUnderLoad) {
                  minUnderLoad = thisLoad;
                  minUnderIndex = i;
               }
            }

            return minUnderIndex != -1 ? minUnderIndex : (zeroIndex != -1 ? zeroIndex : minAboveIndex);
         case 2:
            return this.roundRobinIndex = (this.roundRobinIndex + 1) % this.workers.length;
         default:
            throw new JDBCStoreException(this, "Wrong load balancing strategy:" + this.lbStrategy);
      }
   }

   long getCurrentTimeMillisAtStartOfRecovery() {
      return System.currentTimeMillis();
   }

   long getCurrentTimeMillis() {
      return System.currentTimeMillis();
   }

   void sleepThread(long sleepTime) throws InterruptedException {
      Thread.sleep(sleepTime);
   }

   private void flushWithRetry(ReservedConnection conn, PendingInsert[] insertArray, PendingDelete[] deleteArray) throws PersistentStoreException {
      PersistentStoreException pse1 = null;

      ReservedConnection debugConn;
      try {
         this.updateTableOwnershipFromIO(conn == null ? this.mainConnLC : conn);
      } catch (OwnershipException var30) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            debugConn = conn == null ? this.mainConnLC : conn;
            debugConn.debug("retry update Ownership failed with OwnershipException:" + var30);
         }

         throw var30;
      } catch (JDBCStoreException var31) {
         pse1 = var31;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            debugConn = conn == null ? this.mainConnLC : conn;
            debugConn.debug("retry update Ownership failed with JDBCStoreException:" + var31);
         }
      }

      int[] newRow = new int[]{-1};
      if (pse1 == null) {
         try {
            this.flushInner(conn, insertArray, deleteArray, newRow);
            return;
         } catch (JDBCStoreException var29) {
            pse1 = var29;
         }
      }

      long retryPeriodEndMillis = this.flushRetryTimeoutMillis == 0 ? 0L : this.getCurrentTimeMillisAtStartOfRecovery() + (long)this.flushRetryTimeoutMillis;
      int retryAttempt = 0;

      do {
         ReservedConnection debugConn;
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            debugConn = conn == null ? this.mainConnLC : conn;
            StringBuilder var10001 = (new StringBuilder()).append("flush may have failed: ").append(pse1.toString()).append(", about to retry attempt:");
            ++retryAttempt;
            debugConn.debug(var10001.append(retryAttempt).append(" flushRetryTimeout ").append(this.flushRetryTimeoutMillis).append(" flushFailureFatal ").append(this.flushFailureFatal).toString());
         }

         PendingInsert[] var33 = insertArray;
         int var10 = insertArray.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            PendingInsert ins = var33[var11];
            ins.rewindBuffers();
         }

         ReservedConnection debugConn;
         try {
            if (newRow[0] != -1 && this.rowExists(newRow[0])) {
               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  debugConn = conn == null ? this.mainConnLC : conn;
                  debugConn.debug("flush ok");
               }

               return;
            }
         } catch (PersistentStoreException var28) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               debugConn = conn == null ? this.mainConnLC : conn;
               debugConn.debug("flush failed, could not reconnect, ignoring exception " + var28);
            }
            continue;
         }

         PersistentStoreException pse2 = null;

         ReservedConnection debugConn;
         try {
            this.updateTableOwnershipFromIO(conn == null ? this.mainConnLC : conn);
         } catch (OwnershipException var26) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               debugConn = conn == null ? this.mainConnLC : conn;
               debugConn.debug("retry update Ownership failed with OwnershipException:" + var26);
            }

            throw var26;
         } catch (JDBCStoreException var27) {
            pse2 = var27;
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               debugConn = conn == null ? this.mainConnLC : conn;
               debugConn.debug("retry update Ownership failed with JDBCStoreException:" + var27);
            }
         }

         if (pse2 == null) {
            boolean var23 = false;

            label405: {
               try {
                  var23 = true;
                  newRow[0] = -1;
                  this.flushInner(conn, insertArray, deleteArray, newRow);
                  var23 = false;
                  break label405;
               } catch (JDBCStoreException var24) {
                  pse2 = var24;
                  var23 = false;
               } finally {
                  if (var23) {
                     if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                        ReservedConnection debugConn = conn == null ? this.mainConnLC : conn;
                        debugConn.debug("retry flush result=" + (pse2 == null ? "ok" : pse2.toString()));
                     }

                  }
               }

               if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
                  debugConn = conn == null ? this.mainConnLC : conn;
                  debugConn.debug("retry flush result=" + (pse2 == null ? "ok" : pse2.toString()));
               }
               continue;
            }

            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               debugConn = conn == null ? this.mainConnLC : conn;
               debugConn.debug("retry flush result=" + (pse2 == null ? "ok" : pse2.toString()));
            }

            return;
         }
      } while(this.flushRetryTimeoutMillis != 0 && this.isOpen && !this.isPrepareToClose && !this.isBeingClosed && this.getCurrentTimeMillis() < retryPeriodEndMillis);

      throw pse1;
   }

   void flushInner(ReservedConnection conn, PendingInsert[] insertArray, PendingDelete[] deleteArray, int[] newRow) throws JDBCStoreException {
      List largeBlobs = new ArrayList();
      List regularInserts = new ArrayList();
      if (this.isOracleBlobRecord()) {
         PendingInsert[] var7 = insertArray;
         int var8 = insertArray.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            PendingInsert pi = var7[var9];
            if (pi.getSize() > this.threeStepThreshold) {
               largeBlobs.add(pi);
            } else {
               regularInserts.add(pi);
            }
         }
      }

      boolean doLargeBlobs = largeBlobs.size() > 0;
      if (doLargeBlobs) {
         insertArray = (PendingInsert[])regularInserts.toArray(new PendingInsert[0]);
      }

      boolean doAutoCommit;
      if (conn != null) {
         doAutoCommit = conn.isAutoCommit();
      } else {
         doAutoCommit = this.mainConnAC != null && (this.pInserts.size() == 0 && this.pDeletes.size() <= this.maxDeleteCount || this.pDeletes.size() == 0 && this.pInserts.size() <= 1 && largeBlobs.size() == 0);
         if (doAutoCommit) {
            conn = this.mainConnAC;
         } else {
            conn = this.mainConnLC;
         }
      }

      boolean invalidateConn = false;
      Object connLock = null;
      boolean doDeletes = deleteArray.length > 0;

      try {
         connLock = conn.lock();
         boolean doBatchDeletes;
         int batchCount;
         if (insertArray.length > 0) {
            boolean doBatchInserts = this.enableBatchInserts && !doAutoCommit && insertArray.length > 1;
            doBatchDeletes = !doAutoCommit && !doLargeBlobs && !doDeletes;
            batchCount = 0;
            PendingInsert[] var15 = insertArray;
            int var16 = insertArray.length;
            int var17 = 0;

            while(true) {
               if (var17 >= var16) {
                  if (doBatchInserts) {
                     conn.executeBatchForStatement(conn.getInsertRowStatement(), doBatchDeletes);
                  }
                  break;
               }

               PendingInsert pi = var15[var17];
               ++batchCount;
               if (doBatchInserts && batchCount > this.maxInsertStatementsPerBatch) {
                  conn.executeBatchForStatement(conn.getInsertRowStatement(), false);
                  batchCount = 1;
               }

               newRow[0] = pi.getRowid();
               conn.fillInsertStatement(newRow[0], pi.getTypeCode(), pi.getHandle(), pi.getBB(), !doBatchInserts, doBatchDeletes && !doBatchInserts && batchCount == insertArray.length);
               if (doBatchInserts) {
                  conn.getInsertRowStatement().addBatch();
               }

               ++var17;
            }
         }

         if (largeBlobs.size() > 0) {
            Iterator var34 = largeBlobs.iterator();

            while(var34.hasNext()) {
               PendingInsert pi = (PendingInsert)var34.next();
               newRow[0] = pi.getRowid();
               conn.threeStepInsert(newRow[0], pi.getTypeCode(), pi.getHandle(), pi.getBB());
            }
         }

         PendingDelete deleteList = null;
         PendingDelete[] var37 = deleteArray;
         batchCount = deleteArray.length;
         int var38 = 0;

         while(true) {
            if (var38 >= batchCount) {
               if (doDeletes) {
                  doBatchDeletes = this.enableBatchDeletes && deleteArray.length > this.maxDeleteCount;
                  this.doDeletesPass1(conn, deleteList, doBatchDeletes, deleteArray.length, doAutoCommit);
               }

               if (!doAutoCommit) {
                  conn.commit();
               }

               if (doDeletes) {
                  this.doDeletesPass2AndDestroyList(deleteList);
               }
               break;
            }

            PendingDelete pd = var37[var38];
            pd.setNext(deleteList);
            deleteList = pd;
            ++var38;
         }
      } catch (RuntimeException var25) {
         invalidateConn = true;
         throw new JDBCStoreException(this, var25.toString(), var25);
      } catch (SQLException var26) {
         invalidateConn = true;
         throw new JDBCStoreException(this, var26.toString(), var26);
      } catch (JDBCStoreException var27) {
         invalidateConn = true;
         throw var27;
      } catch (Error var28) {
         invalidateConn = true;
         throw var28;
      } finally {
         if (connLock != null) {
            conn.unlock(connLock, invalidateConn);
         }

      }

   }

   public PersistentStoreIO.Cursor createCursor(int typeCode, int flags) throws PersistentStoreException {
      this.checkOpen();
      if (typeCode < 0) {
         typeCode = -5;
      }

      try {
         return new JDBCStoreCursor(this, typeCode, true, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds);
      } catch (SQLException var4) {
         throw new JDBCStoreException(this, var4.toString(), var4);
      } catch (JDBCStoreException var5) {
         throw var5;
      } catch (PersistentStoreException var6) {
         throw new JDBCStoreException(this, var6.toString(), var6);
      }
   }

   private static ConnectionCachingPolicy getConnectionPolicy(HashMap config, String storeName) {
      ConnectionCachingPolicy connPolicy = (ConnectionCachingPolicy)getEnumConfiguration(config, "ConnectionCachingPolicy", storeName, CONNECTION_CACHING_POLICY_PROP, JDBCStoreIO.ConnectionCachingPolicy.DEFAULT);
      if (StoreDebug.storeConnectionCaching.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Store \"").append(storeName);
         sb.append("\" will use a connection caching policy of ");
         sb.append(connPolicy);
         StoreDebug.storeConnectionCaching.debug(sb.toString());
      }

      return connPolicy;
   }

   private static void dump(XMLStreamWriter xsw, Object obj, String methodName) throws XMLStreamException {
      Class cls = obj.getClass();
      Object ret = null;

      try {
         Method method = cls.getMethod(methodName);
         ret = method.invoke(obj);
         xsw.writeStartElement("Field");
         xsw.writeAttribute(methodName, "" + ret);
         xsw.writeEndElement();
         return;
      } catch (NoSuchMethodException var6) {
         ret = var6;
      } catch (SecurityException var7) {
         ret = var7;
      } catch (IllegalAccessException var8) {
         ret = var8;
      } catch (IllegalArgumentException var9) {
         ret = var9;
      } catch (ExceptionInInitializerError var10) {
         ret = var10;
      } catch (InvocationTargetException var11) {
         if (var11.getTargetException() != null) {
            ret = var11.getTargetException();
         } else {
            ret = var11;
         }

         if (ret instanceof RuntimeException) {
            throw (RuntimeException)ret;
         }

         if (ret instanceof Error) {
            throw (Error)ret;
         }
      }

      xsw.writeStartElement("Field");
      xsw.writeAttribute("ExceptionCalling" + methodName, "" + ret);
      xsw.writeEndElement();
   }

   public void dump(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeStartElement("JDBCStore");
      xsw.writeAttribute("isOpen", "" + this.isOpen);
      xsw.writeStartElement("ConfiguredValues");
      xsw.writeAttribute("storeName", "" + this.storeName);
      xsw.writeAttribute("serverName", "" + this.serverName);
      xsw.writeAttribute("dataSource", "" + this.dataSource);
      xsw.writeAttribute("tableRef", "" + this.tableRef);
      xsw.writeAttribute("createTableDDLFile", "" + this.createTableDDLFile);
      xsw.writeEndElement();
      if (this.isOpen) {
         xsw.writeStartElement("DerivedValues");
         xsw.writeAttribute("tableDMLIdentifier", "" + this.tableDMLIdentifier);
         xsw.writeAttribute("tableDDLIdentifier", "" + this.tableDDLIdentifier);
         xsw.writeAttribute("indexDDLIdentifier", "" + this.indexDDLIdentifier);
         xsw.writeAttribute("isOracleBlobRecord", "" + this.isOracleBlobRecord);
         xsw.writeAttribute("enableBatchInserts", "" + this.enableBatchInserts);
         xsw.writeAttribute("enableBatchDeletes", "" + this.enableBatchDeletes);
         xsw.writeAttribute("maxDeleteCount", "" + this.maxDeleteCount);
         xsw.writeAttribute("maxDeleteStatementsPerBatch", "" + this.maxDeleteStatementsPerBatch);
         xsw.writeAttribute("maxInsertStatementsPerBatch", "" + this.maxInsertStatementsPerBatch);
         xsw.writeEndElement();
      }

      xsw.writeStartElement("DatabaseMetaData");
      Connection conn = null;

      try {
         conn = this.dataSource.getConnection();
         DatabaseMetaData md = conn.getMetaData();
         dump(xsw, md, "getURL");
         dump(xsw, md, "getUserName");
         dump(xsw, md, "getDatabaseProductName");
         dump(xsw, md, "getDatabaseProductVersion");
         dump(xsw, md, "getDatabaseMajorVersion");
         dump(xsw, md, "getDatabaseMinorVersion");
         dump(xsw, md, "getJDBCMajorVersion");
         dump(xsw, md, "getJDBCMinorVersion");
         dump(xsw, md, "getDriverName");
         dump(xsw, md, "getDriverVersion");
         dump(xsw, md, "getDriverMajorVersion");
         dump(xsw, md, "getDriverMinorVersion");
         dump(xsw, md, "supportsBatchUpdates");
         dump(xsw, md, "getSchemaTerm");
         dump(xsw, md, "getCatalogTerm");
         dump(xsw, md, "isCatalogAtStart");
         dump(xsw, md, "getCatalogSeparator");
         dump(xsw, md, "supportsMixedCaseIdentifiers");
         dump(xsw, md, "storesUpperCaseIdentifiers");
         dump(xsw, md, "storesLowerCaseIdentifiers");
         dump(xsw, md, "storesMixedCaseIdentifiers");
         dump(xsw, md, "supportsSchemasInDataManipulation");
         dump(xsw, md, "supportsSchemasInProcedureCalls");
         dump(xsw, md, "supportsSchemasInTableDefinitions");
         dump(xsw, md, "supportsSchemasInIndexDefinitions");
         dump(xsw, md, "supportsCatalogsInDataManipulation");
         dump(xsw, md, "supportsCatalogsInProcedureCalls");
         dump(xsw, md, "supportsCatalogsInTableDefinitions");
         dump(xsw, md, "supportsCatalogsInIndexDefinitions");
         dump(xsw, md, "getMaxBinaryLiteralLength");
         dump(xsw, md, "getMaxCharLiteralLength");
         dump(xsw, md, "getMaxColumnNameLength");
         dump(xsw, md, "getMaxSchemaNameLength");
         dump(xsw, md, "getMaxProcedureNameLength");
         dump(xsw, md, "getMaxCatalogNameLength");
         dump(xsw, md, "getMaxStatementLength");
         dump(xsw, md, "getMaxTableNameLength");
         dump(xsw, md, "getMaxUserNameLength");
      } catch (SQLException var14) {
         xsw.writeAttribute("ExceptionGettingMetaData", "" + var14);
      } catch (RuntimeException var15) {
         XMLStreamException x = new XMLStreamException(var15.toString());
         x.initCause(var15);
         throw x;
      } finally {
         if (conn != null) {
            try {
               conn.close();
            } catch (SQLException var13) {
            }
         }

      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   public void dump(XMLStreamWriter xsw, int typeCode) throws XMLStreamException {
      try {
         JDBCStoreCursor cursor = new JDBCStoreCursor(this, typeCode, false, this.retryPeriodMilliseconds, this.retryIntervalMilliseconds);

         JDBCStoreCursor.LocalIORecord rec;
         try {
            while(null != (rec = (JDBCStoreCursor.LocalIORecord)cursor.next())) {
               xsw.writeStartElement("Row");
               xsw.writeAttribute("Handle", "" + rec.getHandle());
               xsw.writeAttribute("RowId", "" + rec.getRowId());
               xsw.writeEndElement();
            }
         } finally {
            cursor.close(false);
         }
      } catch (SQLException var10) {
         xsw.writeAttribute("ExceptionWhileProcessing", "" + var10);
      } catch (PersistentStoreException var11) {
         xsw.writeAttribute("ExceptionWhileProcessing", "" + var11);
      }

   }

   private final void debugDump() {
      if (StoreDebug.storeIOLogical.isDebugEnabled()) {
         StringWriter sw = new StringWriter();

         try {
            XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(sw), 2);
            xsw.writeStartDocument();
            this.dump(xsw);
            xsw.writeEndDocument();
            xsw.flush();
            xsw.close();
            StoreDebug.storeIOLogical.debug(sw.toString());
         } catch (Throwable var3) {
            StoreDebug.storeIOLogical.debug(sw.toString(), var3);
         }
      }

   }

   private final void debugDump(int typeCode) {
      try {
         XMLStreamWriter xsw = new XMLPrettyPrinter(new PrintWriter(System.out), 2);
         xsw.writeStartDocument();
         this.dump(xsw, typeCode);
         xsw.writeEndDocument();
         xsw.flush();
         xsw.close();
      } catch (XMLStreamException var3) {
         var3.printStackTrace();
      }

   }

   private void initTableLocking() {
      this.tableLockDebugTrace = false;
      this.tableLockRefreshInterval = 10000;
      this.tableLockMaxTries = 2;
      this.tableLockStampingEnabled = !Boolean.getBoolean("weblogic.store.jdbc.TableLockingDisabled");
      if (this.tableLockStampingEnabled) {
         Integer i = Integer.getInteger("weblogic.store.jdbc.TableLockingInterval");
         if (i != null && i > 0) {
            this.tableLockRefreshInterval = i;
         }

         this.tableLockDebugTrace = Boolean.getBoolean("weblogic.store.jdbc.TableLockingDebug");
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("initTableLocking", " tableLockStampingEnabled=" + this.tableLockStampingEnabled + " tableLockRefreshInterval=" + this.tableLockRefreshInterval + " tableLockMaxTries=" + this.tableLockMaxTries + " tableLockDebugTrace=" + this.tableLockDebugTrace);
         }

      }
   }

   private int checkTableOwnership(TableLockRecord tlr, boolean mustBeMe) throws JDBCStoreException {
      return this.checkTableOwnership(tlr, mustBeMe, true);
   }

   private int checkTableOwnership(TableLockRecord tlr, boolean mustBeMe, boolean islogErrorForMustBeMe) throws JDBCStoreException {
      int ret = true;
      if (this.tableLockDebugTrace) {
         this.tableLockDebug("checkTableOwnership", "> tlr=" + tlr + " mustBeMe=" + mustBeMe + " this.tlr=" + this.tableLockRecord);
      }

      byte ret;
      if (!this.tableLockStampingEnabled) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "state=disabled stamping");
         }

         ret = 3;
      } else if (tlr == null) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "state=null tlr");
         }

         ret = 2;
      } else if (!tlr.getName().equals(this.tableLockRecord.getName())) {
         if (this.isDeadTableLock(tlr)) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("checkTableOwnership", "state=dead different name lock");
            }

            ret = 2;
         } else {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("checkTableOwnership", "state=non-dead different name lock");
            }

            ret = 3;
         }
      } else if (tlr.getRandom() != this.tableLockRecord.getRandom()) {
         if (this.isDeadTableLock(tlr)) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("checkTableOwnership", "state=dead different random lock");
            }

            ret = 2;
         } else {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("checkTableOwnership", "state=non-dead different random lock");
            }

            ret = 3;
         }
      } else if (tlr.getTimeStamp() != this.tableLockRecord.getTimeStamp() && tlr.getTimeStamp() != this.tableLockRecordCandidateTimestamp) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "state=different timestamp current lock");
         }

         ret = 2;
      } else {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "state=same as current lock");
         }

         ret = 1;
      }

      if (mustBeMe && ret != 1) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "mustBeMe but not so=" + ret);
         }

         if (islogErrorForMustBeMe) {
            throw new OwnershipException(this, StoreLogger.logJDBCStoreTableUnexpectedOwner(this.storeName, this.tableRef, tlr + "", this.tableLockRecord + ""));
         } else {
            throw new JDBCStoreException(this, "JDBC store " + this.storeName + " failed to open table " + this.tableRef + " tableLockRecord " + tlr + "this.tableLockRecord " + this.tableLockRecord);
         }
      } else {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("checkTableOwnership", "<" + ret);
         }

         return ret;
      }
   }

   private void getTableOwnershipPhysical(ReservedConnection conn, long inTime) throws JDBCStoreException {
      if (this.tableLockStampingEnabled) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("getTableOwnershipPhysical", ">inTime=" + inTime);
         }

         long holdTime = this.tableLockRecord.getTimeStamp();
         this.tableLockRecord.setTimeStamp(inTime);
         ByteBuffer[] bba = new ByteBuffer[]{this.tableLockRecord.toBB()};
         this.tableLockRecord.setTimeStamp(holdTime);
         this.tableLockRecordCandidateTimestamp = inTime;
         PreparedStatement updateStatement = null;

         try {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               conn.debug("getTableOwnershipPhysical; begin update");
            }

            updateStatement = conn.getUpdateStatement();
            conn.fillUpdateStatement(-1, -1, 3, bba);
            updateStatement.executeUpdate();
            conn.commit();
            this.tableLockRecord.setTimeStamp(inTime);
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               conn.debug("getTableOwnershipPhysical; update complete");
            }
         } catch (SQLException var9) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               conn.debug("getTableOwnershipPhysical: SQLException", var9);
            }

            if (this.tableLockDebugTrace) {
               this.tableLockDebug("getTableOwnershipPhysical", var9.toString());
            }

            throw new JDBCStoreException(this, var9.toString(), var9);
         } catch (RuntimeException var10) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               conn.debug("getTableOwnershipPhysical: RuntimeException", var10);
            }

            if (this.tableLockDebugTrace) {
               this.tableLockDebug("getTableOwnershipPhysical", var10.toString());
            }

            throw new JDBCStoreException(this, var10.toString(), var10);
         } catch (Throwable var11) {
            if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
               conn.debug("getTableOwnershipPhysical: Error: ", var11);
            }

            if (this.tableLockDebugTrace) {
               this.tableLockDebug("getTableOwnershipPhysical", var11.toString());
            }

            throw new JDBCStoreException(this, var11.toString(), var11);
         }

         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            conn.debug("getTableOwnershipPhysical; exit");
         }

         if (this.tableLockDebugTrace) {
            this.tableLockDebug("getTableOwnershipPhysical", "<");
         }

      }
   }

   private void getTableOwnershipLogical(ReservedConnection conn, boolean islogErrorForMustBeMe) throws JDBCStoreException {
      if (this.tableLockStampingEnabled) {
         TableLockRecord tlr = null;
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("getTableOwnershipLogical", ">");
         }

         int maxtries = this.tableLockMaxTries * 3;
         int naptime = this.tableLockRefreshInterval / 3 + 50;
         int tries = 0;

         while(tries < maxtries) {
            tlr = this.readTableLockRecord(conn);
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("getTableOwnershipLogical", "iter=" + tries + "/" + maxtries + " tlr=" + tlr + " this.tlr=" + this.tableLockRecord);
            }

            switch (this.checkTableOwnership(tlr, false)) {
               case 1:
                  if (this.tableLockDebugTrace) {
                     this.tableLockDebug("getTableOwnershipLogical", tlr + "ALREADY");
                  }

                  return;
               case 2:
                  if (this.tableLockDebugTrace) {
                     this.tableLockDebug("getTableOwnershipLogical", tlr + "CAN_BE: pre-getTableOwnershipPhysical");
                  }

                  this.getTableOwnershipPhysical(conn, System.currentTimeMillis());
                  if (this.tableLockDebugTrace) {
                     this.tableLockDebug("getTableOwnershipLogical", tlr + "CAN_BE: post-getTableOwnershipPhysical");
                  }

                  return;
               case 3:
                  if (this.tableLockDebugTrace) {
                     this.tableLockDebug("getTableOwnershipLogical", tlr + "CANNOT_BE");
                  }
               default:
                  if (tries == 0 && islogErrorForMustBeMe) {
                     StoreLogger.logJDBCStoreTableOwnershipWait(this.storeName, this.tableRef, tlr == null ? "null" : tlr.toString());
                  }

                  try {
                     if (this.tableLockDebugTrace) {
                        this.tableLockDebug("getTableOwnershipLogical", tlr + "pre-nap naptime=" + naptime);
                     }

                     this.sleepThread((long)naptime);
                     if (this.tableLockDebugTrace) {
                        this.tableLockDebug("getTableOwnershipLogical", tlr + "post-nap");
                     }
                  } catch (InterruptedException var8) {
                  }

                  ++tries;
            }
         }

         if (this.tableLockDebugTrace) {
            this.tableLockDebug("getTableOwnershipLogical", "pre-mustBeMe check");
         }

         this.checkTableOwnership(tlr, true, islogErrorForMustBeMe);
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("getTableOwnershipLogical", "<" + tlr);
         }

      }
   }

   private TableLockRecord readTableLockRecord(ReservedConnection conn) throws JDBCStoreException {
      if (!this.tableLockStampingEnabled) {
         return null;
      } else {
         IORecord ior = null;
         TableLockRecord tlr = null;
         JDBCStoreException jEx = null;
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("readTableLockRecord", ">");
         }

         try {
            ior = this.internalRead(conn, -1);
            if (ior == null) {
               if (this.tableLockDebugTrace) {
                  this.tableLockDebug("readTableLockRecord", "null record");
               }

               throw new JDBCStoreException(this, "Null table lock record");
            }

            ByteBuffer bb = ior.getData();
            if (bb != null) {
               tlr = TableLockRecord.fromBB(bb);
            }
         } catch (SQLException var6) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("readTableLockRecord", var6.toString());
            }

            throw new JDBCStoreException(this, var6.toString(), var6);
         } catch (PersistentStoreException var7) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("readTableLockRecord", var7.toString());
            }

            throw new JDBCStoreException(this, var7.toString(), var7);
         } catch (RuntimeException var8) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("readTableLockRecord", var8.toString());
            }

            throw new JDBCStoreException(this, var8.toString(), var8);
         } catch (Throwable var9) {
            if (this.tableLockDebugTrace) {
               this.tableLockDebug("readTableLockRecord", var9.toString());
            }

            throw new JDBCStoreException(this, var9.toString(), var9);
         }

         if (this.tableLockDebugTrace) {
            this.tableLockDebug("readTableLockRecord", "<" + tlr);
         }

         return tlr;
      }
   }

   private void updateTableOwnershipFromClose() {
      synchronized(this.tableLockRecord) {
         try {
            if (this.tableOwnerState == 1) {
               this.updateTableOwnership(this.mainConnLC, 0L, "Close", false);
            }
         } catch (JDBCStoreException var8) {
         } finally {
            this.tableOwnerState = 2;
         }

      }
   }

   void updateTableOwnershipFromTimer() {
      synchronized(this.tableLockRecord) {
         try {
            this.updateTableOwnership(this.mainConnLC, System.currentTimeMillis(), "Timer", true);
         } catch (JDBCStoreException var4) {
         }

      }
   }

   private void updateTableOwnershipFromIO(ReservedConnection conn) throws JDBCStoreException {
      synchronized(this.tableLockRecord) {
         if (this.tableOwnerState != 1) {
            try {
               this.updateTableOwnership(conn, System.currentTimeMillis(), "Flush-A", false);
            } catch (JDBCStoreException var5) {
               this.updateTableOwnership(conn, System.currentTimeMillis(), "Flush-B", false);
            }

         }
      }
   }

   void updateTableOwnership(ReservedConnection conn, long inTime, String callingMethod, boolean ignoreIfActive) throws JDBCStoreException {
      if (this.tableLockStampingEnabled) {
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("updateTableOwnership", ">inTime=" + inTime + "calledfrom=" + callingMethod);
         }

         Object lockA = null;
         Object lockL = null;
         boolean var14 = false;

         boolean connBad;
         label246: {
            try {
               var14 = true;
               if (this.tableOwnerState == 3) {
                  throw new OwnershipException(this, StoreLogger.logJDBCStoreTableUnexpectedOwner(this.storeName, this.tableRef, this.dbTableLockRecord + "", this.tableLockRecord + ""));
               }

               if (this.mainConnLC == conn && this.mainConnAC != null) {
                  lockA = this.mainConnAC.lock(ignoreIfActive, false);
               }

               lockL = conn.lock(ignoreIfActive, false);
               if (lockL == null) {
                  var14 = false;
                  break label246;
               }

               this.tableOwnerState = 2;
               this.dbTableLockRecord = this.readTableLockRecord(conn);

               try {
                  if (this.dbTableLockRecord != null) {
                     this.checkTableOwnership(this.dbTableLockRecord, true);
                  }
               } catch (OwnershipException var15) {
                  this.tableOwnerState = 3;
                  StoreLogger.logJDBCStoreTableOwnershipRefreshFailed(this.storeName, this.tableRef, var15);
                  throw var15;
               }

               this.getTableOwnershipPhysical(conn, inTime);
               this.tableOwnerState = 1;
               var14 = false;
            } catch (JDBCStoreException var16) {
               if (this.tableLockDebugTrace) {
                  this.tableLockDebug("updateTableOwnership", "Exception=" + var16);
               }

               throw var16;
            } finally {
               if (var14) {
                  boolean connBad = this.tableOwnerState == 2;
                  if (lockL != null) {
                     conn.unlock(lockL, connBad);
                  }

                  if (lockA != null) {
                     this.mainConnAC.unlock(lockA, connBad);
                  }

                  if (this.tableOwnerState == 3) {
                     this.prepareToClose();
                     this.stopAllWorkers();
                  }

               }
            }

            connBad = this.tableOwnerState == 2;
            if (lockL != null) {
               conn.unlock(lockL, connBad);
            }

            if (lockA != null) {
               this.mainConnAC.unlock(lockA, connBad);
            }

            if (this.tableOwnerState == 3) {
               this.prepareToClose();
               this.stopAllWorkers();
            }

            if (this.tableLockDebugTrace) {
               this.tableLockDebug("updateTableOwnership", "<");
            }

            return;
         }

         connBad = this.tableOwnerState == 2;
         if (lockL != null) {
            conn.unlock(lockL, connBad);
         }

         if (lockA != null) {
            this.mainConnAC.unlock(lockA, connBad);
         }

         if (this.tableOwnerState == 3) {
            this.prepareToClose();
            this.stopAllWorkers();
         }

      }
   }

   private boolean isDeadTableLock(TableLockRecord tlr) {
      if (tlr == null) {
         return true;
      } else {
         long diff = System.currentTimeMillis() - tlr.getTimeStamp();
         long range = (long)(this.tableLockRefreshInterval * 2 + this.tableLockRefreshInterval / 2);
         boolean ret = diff < (long)(-this.tableLockRefreshInterval) || diff > range;
         if (this.tableLockDebugTrace) {
            this.tableLockDebug("isDeadTableLock", "diff=" + diff + " range=" + range + " tlr=" + tlr + " ret=" + ret);
         }

         return ret;
      }
   }

   private void tableLockDebug(String method, String msg) {
      if (this.tableLockDebugTrace) {
         StringBuffer sb = (new StringBuffer("TABLE_LOCK_DEBUG")).append("||");
         sb.append(this).append(".").append(method).append("()||");
         sb.append("msg=").append(msg).append("||");
         sb.append("time=" + System.currentTimeMillis()).append("||");
         sb.append("thr=").append(Thread.currentThread()).append("||");
         DebugLogger.println(sb.toString());
      }
   }

   public void setTestException(PersistentStoreException exception) {
      this.testStoreException = exception;
   }

   public void prepareToClose() {
      this.isPrepareToClose = true;
   }

   static {
      String storeBootOnError = System.getProperty("weblogic.store.StoreBootOnError");
      BOOT_ON_ERROR = storeBootOnError != null && storeBootOnError.equalsIgnoreCase("true");
   }

   private static class PendingInsertHashMap {
      NumericKeyHashMap map;
      int newRow;

      private PendingInsertHashMap() {
         this.map = new NumericKeyHashMap();
         this.newRow = -1;
      }

      PendingInsert put(PendingInsert pi) {
         long lkey = (long)pi.getTypeCode() << 32 | (long)pi.getHandle();
         return (PendingInsert)this.map.put(lkey, pi);
      }

      PendingInsert get(int typeCode, int handle) {
         long lkey = (long)typeCode << 32 | (long)handle;
         return (PendingInsert)this.map.get(lkey);
      }

      PendingInsert remove(int typeCode, int handle) {
         long lkey = (long)typeCode << 32 | (long)handle;
         return (PendingInsert)this.map.remove(lkey);
      }

      void setNewRow(int row) {
         this.newRow = row;
      }

      int getNewRow() {
         return this.newRow;
      }

      Iterator iterator() {
         return this.map.values().iterator();
      }

      void clear() {
         this.map.clear();
         this.newRow = -1;
      }

      int size() {
         return this.map.size();
      }

      PendingInsert[] getAllPendingInsert() {
         PendingInsert[] ins = new PendingInsert[this.map.size()];
         int index = 0;

         for(Iterator iter = this.iterator(); iter.hasNext(); ins[index++] = (PendingInsert)iter.next()) {
         }

         return ins;
      }

      // $FF: synthetic method
      PendingInsertHashMap(Object x0) {
         this();
      }
   }

   private static class PendingDeleteHashMap {
      NumericKeyHashMap map;

      private PendingDeleteHashMap() {
         this.map = new NumericKeyHashMap();
      }

      PendingDelete put(PendingDelete pd) {
         long key = (long)pd.getTypeCode() << 32 | (long)pd.getHandle();
         return (PendingDelete)this.map.put(key, pd);
      }

      PendingDelete get(int typeCode, int handle) {
         long key = (long)typeCode << 32 | (long)handle;
         return (PendingDelete)this.map.get(key);
      }

      Iterator iterator() {
         return this.map.values().iterator();
      }

      void clear() {
         this.map.clear();
      }

      int size() {
         return this.map.size();
      }

      PendingDelete[] getAllPendingDelete() {
         PendingDelete[] dels = new PendingDelete[this.map.size()];
         int index = 0;

         for(Iterator iter = this.iterator(); iter.hasNext(); dels[index++] = (PendingDelete)iter.next()) {
         }

         return dels;
      }

      // $FF: synthetic method
      PendingDeleteHashMap(Object x0) {
         this();
      }
   }

   static final class PendingInsert {
      private ByteBuffer[] bb;
      private int handle;
      private int typeCode;
      private int size = 0;
      private int rowid = 0;

      PendingInsert(int _typeCode, int _handle, ByteBuffer[] _bb) {
         this.handle = _handle;
         this.typeCode = _typeCode;
         this.bb = _bb;
         if (this.bb != null) {
            ByteBuffer[] var4 = _bb;
            int var5 = _bb.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ByteBuffer buf = var4[var6];
               if (buf != null) {
                  this.size += buf.remaining();
               }
            }
         }

      }

      void rewindBuffers() {
         for(int inc = 0; this.bb != null && inc < this.bb.length; ++inc) {
            this.bb[inc].rewind();
         }

      }

      ByteBuffer[] getBB() {
         return this.bb;
      }

      int getHandle() {
         return this.handle;
      }

      int getTypeCode() {
         return this.typeCode;
      }

      int getSize() {
         return this.size;
      }

      int getRowid() {
         return this.rowid;
      }

      void setRowid(int rowid) {
         this.rowid = rowid;
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("[ handle = ");
         buf.append(this.handle);
         buf.append(" typeCode = ");
         buf.append(this.typeCode);
         buf.append(" buffers = ");
         buf.append(this.bb.length);
         buf.append(": ");

         for(int inc = 0; inc < this.bb.length; ++inc) {
            if (inc > 0) {
               buf.append(", ");
            }

            buf.append(this.bb[inc].remaining());
         }

         buf.append(" ]");
         return buf.toString();
      }
   }

   static final class PendingDelete {
      private boolean freeTheHandle;
      private int typeCode;
      private int handle;
      private int rowId;
      private PendingDelete next;

      PendingDelete(int _typeCode, int _handle, int _rowId, boolean _freeTheHandle) {
         if (_freeTheHandle && _handle < 0) {
            throw new AssertionError();
         } else {
            this.freeTheHandle = _freeTheHandle;
            this.rowId = _rowId;
            this.typeCode = _typeCode;
            this.handle = _handle;
         }
      }

      synchronized PendingDelete getNext() {
         return this.next;
      }

      synchronized void setNext(PendingDelete pd) {
         this.next = pd;
      }

      synchronized int getRowId() {
         return this.rowId;
      }

      synchronized int getHandle() {
         return this.handle;
      }

      synchronized int getHandleToFree() {
         return this.freeTheHandle ? this.handle : 0;
      }

      synchronized int getTypeCode() {
         return this.typeCode;
      }

      synchronized void enableHandleFree() {
         this.freeTheHandle = true;
      }
   }

   class JDBCStoreWorker implements Runnable {
      private final int id;
      private final ReservedConnection conn;
      private final LinkedList loads;
      private final Thread workerThread;
      private int newOps;
      private volatile boolean stopped;
      private boolean active;

      private JDBCStoreWorker(int id, ReservedConnection conn) {
         this.stopped = false;
         this.active = false;
         this.id = id;

         assert conn != null;

         this.conn = conn;
         this.loads = new LinkedList();
         this.workerThread = new Thread(this);
         this.workerThread.setDaemon(false);
         this.workerThread.start();
      }

      int getID() {
         return this.id;
      }

      Thread getThread() {
         return this.workerThread;
      }

      ReservedConnection getConnection() {
         return this.conn;
      }

      private synchronized int getOperationCount() {
         return this.newOps;
      }

      private synchronized void putLoad(WorkLoad load) throws JDBCStoreException {
         if (this.stopped) {
            throw new JDBCStoreException(JDBCStoreIO.this, "cannot put more load on the stopped worker");
         } else {
            this.loads.offer(load);
            this.newOps += load.getOperationCount();
            this.notifyAll();
         }
      }

      private synchronized void stop() {
         if (!this.stopped) {
            this.stopped = true;
            this.notifyAll();
            this.conn.close(false);
         }
      }

      private synchronized WorkLoad[] getRemainingLoads() {
         this.newOps = 0;
         return (WorkLoad[])this.loads.toArray(new WorkLoad[0]);
      }

      private synchronized WorkLoad[] getAllLoads() {
         while(!this.stopped && this.loads.size() == 0) {
            try {
               if (this.active) {
                  this.active = false;
                  JDBCStoreIO.this.activeWorkerCount.decrementAndGet();
               }

               this.wait();
            } catch (InterruptedException var2) {
            }
         }

         if (!this.active) {
            this.active = true;
            JDBCStoreIO.this.activeWorkerCount.incrementAndGet();
         }

         WorkLoad[] all = (WorkLoad[])this.loads.toArray(new WorkLoad[0]);
         this.loads.clear();
         this.newOps = 0;
         return all;
      }

      private void notifyListeners(WorkLoad[] completedLoads, Object result) {
         int var5;
         synchronized(JDBCStoreIO.this.handleLocks) {
            WorkLoad[] var4 = completedLoads;
            var5 = completedLoads.length;
            int var6 = 0;

            while(true) {
               if (var6 >= var5) {
                  break;
               }

               WorkLoad loadx = var4[var6];
               JDBCStoreIO.this.flushToWorkerMap.remove(loadx.loadId);
               Long[] var8 = loadx.wlHandles;
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Long lh = var8[var10];
                  JDBCStoreIO.this.unlockHandle(lh, loadx.loadId);
               }

               ++var6;
            }
         }

         WorkLoad[] var3 = completedLoads;
         int var15 = completedLoads.length;

         for(var5 = 0; var5 < var15; ++var5) {
            WorkLoad load = var3[var5];

            try {
               load.listener.ioCompleted(result);
            } catch (Throwable var13) {
               var13.printStackTrace();
            }
         }

      }

      public void run() {
         this.active = true;
         JDBCStoreIO.this.activeWorkerCount.incrementAndGet();

         while(true) {
            boolean var40 = false;

            try {
               var40 = true;
               WorkLoad[] nextLoads = this.getAllLoads();
               if (this.stopped) {
                  JDBCStoreException exp = new JDBCStoreException(JDBCStoreIO.this, "JDBC store I/O is stopped");
                  this.notifyListeners(nextLoads, exp);
                  var40 = false;
                  break;
               }

               assert nextLoads != null;

               PendingInsert[] allIns;
               PendingDelete[] allDels;
               if (nextLoads.length == 1) {
                  allIns = nextLoads[0].insertArray;
                  allDels = nextLoads[0].deleteArray;
               } else {
                  int iSize = 0;
                  int dSize = 0;
                  WorkLoad[] var6 = nextLoads;
                  int dIndex = nextLoads.length;

                  for(int var8 = 0; var8 < dIndex; ++var8) {
                     WorkLoad wl = var6[var8];
                     iSize += wl.insertArray.length;
                     dSize += wl.deleteArray.length;
                  }

                  int iIndex = 0;
                  dIndex = 0;
                  allIns = new PendingInsert[iSize];
                  allDels = new PendingDelete[dSize];
                  WorkLoad[] var51 = nextLoads;
                  int var52 = nextLoads.length;

                  for(int var10 = 0; var10 < var52; ++var10) {
                     WorkLoad wlx = var51[var10];
                     PendingInsert[] var12 = wlx.insertArray;
                     int var13 = var12.length;

                     int var14;
                     for(var14 = 0; var14 < var13; ++var14) {
                        PendingInsert pi = var12[var14];
                        allIns[iIndex++] = pi;
                     }

                     PendingDelete[] var53 = wlx.deleteArray;
                     var13 = var53.length;

                     for(var14 = 0; var14 < var13; ++var14) {
                        PendingDelete pd = var53[var14];
                        allDels[dIndex++] = pd;
                     }
                  }
               }

               Object pse;
               try {
                  JDBCStoreIO.this.flushWithRetry(this.conn, allIns, allDels);
                  this.notifyListeners(nextLoads, (Object)null);
                  continue;
               } catch (Throwable var44) {
                  JDBCStoreIO.this.prepareToClose();
                  JDBCStoreIO.this.stopAllWorkers();
                  if (var44 instanceof PersistentStoreException) {
                     if (JDBCStoreIO.this.flushFailureFatal) {
                        pse = new PersistentStoreFatalException((PersistentStoreException)var44);
                        JDBCStoreIO.this.fatalError = true;
                     } else {
                        pse = (PersistentStoreException)var44;
                     }
                  } else if (JDBCStoreIO.this.flushFailureFatal) {
                     pse = new PersistentStoreFatalException(new JDBCStoreException(JDBCStoreIO.this, "JDBCStoreWorker fails", var44));
                     JDBCStoreIO.this.fatalError = true;
                  } else {
                     pse = new PersistentStoreException(new JDBCStoreException(JDBCStoreIO.this, "JDBCStoreWorker fails", var44));
                  }
               }

               this.notifyListeners(nextLoads, pse);
               var40 = false;
            } finally {
               if (var40) {
                  try {
                     JDBCStoreException expxxx = new JDBCStoreException(JDBCStoreIO.this, "JDBC store I/O is stopped");
                     this.notifyListeners(this.getRemainingLoads(), expxxx);
                     if (this.conn != null) {
                        this.conn.close(false);
                     }

                     if (this.active) {
                        this.active = false;
                        JDBCStoreIO.this.activeWorkerCount.decrementAndGet();
                     }
                  } finally {
                     JDBCStoreIO.this.workerShutdownLatch.countDown();
                  }

               }
            }

            try {
               JDBCStoreException expxx = new JDBCStoreException(JDBCStoreIO.this, "JDBC store I/O is stopped");
               this.notifyListeners(this.getRemainingLoads(), expxx);
               if (this.conn != null) {
                  this.conn.close(false);
               }

               if (this.active) {
                  this.active = false;
                  JDBCStoreIO.this.activeWorkerCount.decrementAndGet();
               }
            } finally {
               JDBCStoreIO.this.workerShutdownLatch.countDown();
            }

            return;
         }

         try {
            JDBCStoreException expx = new JDBCStoreException(JDBCStoreIO.this, "JDBC store I/O is stopped");
            this.notifyListeners(this.getRemainingLoads(), expx);
            if (this.conn != null) {
               this.conn.close(false);
            }

            if (this.active) {
               this.active = false;
               JDBCStoreIO.this.activeWorkerCount.decrementAndGet();
            }
         } finally {
            JDBCStoreIO.this.workerShutdownLatch.countDown();
         }

      }

      // $FF: synthetic method
      JDBCStoreWorker(int x1, ReservedConnection x2, Object x3) {
         this(x1, x2);
      }
   }

   private static class WorkLoad {
      private long loadId;
      private PendingInsert[] insertArray;
      private PendingDelete[] deleteArray;
      private IOListener listener;
      private Long[] wlHandles;

      private WorkLoad(long flushIndex, PendingInsert[] insertArray, PendingDelete[] deleteArray, Long[] lockedHandles, IOListener listener) {
         this.loadId = flushIndex;
         this.insertArray = insertArray;
         this.deleteArray = deleteArray;
         this.wlHandles = lockedHandles;
         this.listener = listener;
      }

      private int getOperationCount() {
         return (this.insertArray == null ? 0 : this.insertArray.length) + (this.deleteArray == null ? 0 : this.deleteArray.length);
      }

      // $FF: synthetic method
      WorkLoad(long x0, PendingInsert[] x1, PendingDelete[] x2, Long[] x3, IOListener x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   private static enum ConnectionCachingPolicy {
      DEFAULT,
      MINIMAL,
      NONE;
   }
}
