package weblogic.store;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public interface PersistentStore {
   String CACHE_DIR_KEY = "CacheDirectory";
   String INIT_SIZE_KEY = "InitialSize";
   String MAX_FILE_SIZE_KEY = "MaxFileSize";
   String MAX_WIN_SIZE_KEY = "MaxWindowBufferSize";
   String MIN_WIN_SIZE_KEY = "MinWindowBufferSize";
   String WRITE_POLICY_KEY = "SynchronousWritePolicy";
   String FILE_LOCKING_KEY = "FileLockingEnabled";
   String DOMAIN_KEY = "DomainName";
   String DAEMON_KEY = "DaemonThreadInClientJVM";
   String OPEN_FAILURES_ARE_FATAL = "OpenFailuresAreFatal";
   String MAX_RETRY_SECONDS = "MaxRetrySeconds";
   String WORKER_COUNT = "WorkerCount";
   String WORKER_PREFERRED_BATCH_SIZE = "WorkerPreferredBatchSize";
   String THREE_STEP_THRESHOLD = "ThreeStepThreshold";
   String LB_STRATEGY = "LBStrategy";
   String ORACLE_PIGGYBACK_COMMIT_ENABLED = "OraclePiggybackCommitEnabled";
   String CONNECTION_CACHING_POLICY = "ConnectionCachingPolicy";
   String ADDRESS_KEY = "Address";
   String BLOCK_SIZE_KEY = "BlockSize";
   String BUSY_WAIT_MICRO_SECONDS_KEY = "BusyWaitMicroSeconds";
   String CANDIDATE_HANDLE_KEY = "CandidateHandle";
   String STORE_CONFIG_NAME_KEY = "StoreConfigName";
   String CONFIG_FILE_NAME_KEY = "ConfigFileName";
   String IO_BUFFER_SIZE_KEY = "IoBufferSize";
   String LOCAL_INDEX_KEY = "LocalIndex";
   String MAX_REPLICA_COUNT_KEY = "MaxReplicaCount";
   String MAXIMUM_MESSAGE_SIZE_PERCENT_KEY = "MaximumMessageSizePercent";
   String MIN_REPLICA_COUNT_KEY = "MinReplicaCount";
   String PORT_KEY = "Port";
   String REGION_SIZE_KEY = "RegionSize";
   String SLEEP_WAIT_MILLI_SECONDS_KEY = "SleepWaitMilliSeconds";
   String SPACE_USAGE_LOGGING_START_PERCENT_KEY = "SpaceLoggingStartPercent";
   String SPACE_USAGE_LOGGING_DELTA_PERCENT_KEY = "SpaceLoggingDeltaPercent";
   String SPACE_USAGE_WARNING_PERCENT_KEY = "SpaceOverloadYellowPercent";
   String SPACE_USAGE_ERROR_PERCENT_KEY = "SpaceOverloadRedPercent";
   String SPACE_USAGE_BATCH_FACTOR_KEY = "SpaceOverloadBatchFactor";
   String DEVICE_POLL_INTERVAL_KEY = "DevicePollIntervalMillis";
   String IS_LOG_ERROR_FOR_MUST_BE_ME = "IslogErrorForMustBeMe";
   String IS_MIGRATABLE = "IsMigratable";
   String IS_RP_ENABLED = "IsRPEnabled";
   String IS_DEFAULT_STORE = "IsDefaultStore";
   List VALID_REPLICATED_IO_KEYS = Collections.unmodifiableList(Arrays.asList("CandidateHandle", "Address", "BlockSize", "BusyWaitMicroSeconds", "StoreConfigName", "ConfigFileName", "IoBufferSize", "LocalIndex", "MaxReplicaCount", "MinReplicaCount", "Port", "RegionSize", "SleepWaitMilliSeconds"));

   void open(HashMap var1) throws PersistentStoreException;

   /** @deprecated */
   @Deprecated
   void open(StoreWritePolicy var1) throws PersistentStoreException;

   void unregisterStoreMBean() throws PersistentStoreException;

   void close() throws PersistentStoreException;

   PersistentStoreConnection createConnection(String var1) throws PersistentStoreException;

   PersistentStoreConnection createConnection(String var1, ObjectHandler var2) throws PersistentStoreException;

   PersistentStoreConnection getConnection(String var1);

   PersistentMapAsyncTX createPersistentMap(String var1) throws PersistentStoreException;

   PersistentMapAsyncTX createPersistentMap(String var1, ObjectHandler var2) throws PersistentStoreException;

   PersistentStoreTransaction begin();

   StoreStatistics getStatistics();

   String getName();

   String getShortName();

   Iterator getConnectionNames();

   Iterator getMapConnectionNames();

   boolean supportsFastReads();

   Object getConfigValue(Object var1) throws PersistentStoreException;

   void setConfigValue(Object var1, Object var2) throws PersistentStoreException;

   PersistentStoreException getFatalException();
}
