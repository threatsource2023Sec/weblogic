package weblogic.store;

import java.util.Locale;
import java.util.Set;

public final class SystemProperties {
   public static final String JDBC_REFRESH_MILLIS = "weblogic.store.jdbc.RefreshMillis";
   public static final String JDBC_FAILBACK_CHECK_MILLIS = "weblogic.store.jdbc.FailbackCheckMillis";
   public static final String JDBC_RETRY_DELAY_SECS_PROP_OLD = "weblogic.jms.store.JMSJDBCIORetryDelay";
   public static final String JDBC_RETRY_DELAY_MILLIS_PROP_NEW = "weblogic.store.jdbc.IORetryDelayMillis";
   public static final String JDBC_RETRY_PERIOD_MILLIS = "weblogic.store.jdbc.ReconnectRetryPeriodMillis";
   public static final String JDBC_RETRY_INTERVAL_MILLIS = "weblogic.store.jdbc.ReconnectRetryIntervalMillis";
   public static final String JDBC_MAX_IDLE_CURSOR_MILLIS = "weblogic.store.jdbc.MaxIdleCursorMillis";
   public static final String FILE_STORE_INITIAL_EXTENT_PROP = "weblogic.store.InitialExtentSize";
   public static final String STORE_BOOT_ON_ERROR = "weblogic.store.StoreBootOnError";
   public static final String STORE_RESTORE_METADATA = "weblogic.store.RestoreMetaData";
   public static final String DELETE_BAD_PMAPKEYS = "weblogic.store.DeleteBadPMapKeys";
   public static final String DELETE_BAD_INSTRTEST = "weblogic.store.DeleteBadInstrTest";
   public static final String PREFIX = "weblogic.store";
   public static final String PREFIX_DOT = "weblogic.store.";
   public static final String FILE_STORE_LOCAL_BLOCK_SIZE = ".BlockSize";
   public static final String FILE_STORE_GLOBAL_BLOCK_SIZE = "weblogic.store.BlockSize";
   public static final String FILE_STORE_LOCAL_AVOID_DIRECT_IO = ".AvoidDirectIO";
   public static final String FILE_STORE_GLOBAL_AVOID_DIRECT_IO = "weblogic.store.AvoidDirectIO";
   public static final String FILE_STORE_LOCAL_DIRECT_IO_MODE = ".DirectIOMode";
   public static final String FILE_STORE_GLOBAL_DIRECT_IO_MODE = "weblogic.store.DirectIOMode";
   public static final String FILE_STORE_VERY_VERBOSE_DEBUG = "weblogic.store.DebugVerboseFileIO";
   public static final String FILE_STORE_MAX_FILE_SIZE = "weblogic.store.MaxFileSize";
   public static final String SKIP_SPACE_UPDATES = "weblogic.store.SkipSpaceUpdates";
   public static final String DEBUG_SPACE_UPDATES = "weblogic.store.DebugSpaceUpdates";
   public static final String FILE_STORE_SYNC_DESERIALIZERS = "weblogic.store.SynchronousDeserializationSet";
   public static final String FILE_STORE_LARGE_DEFAULTS = "weblogic.store.LargeDefaults";
   public static final String FILE_STORE_CURSOR_BATCH_SIZE = "weblogic.store.CursorBatchSize";
   public static final String ENABLE_FILESTORE_CLEAN_ON_BOOT = "weblogic.store.EnableFileStoreCleanOnBoot";
   public static final String AUTO_CREATE_DIRECTORY = "weblogic.store.file.AutoCreateDirectory";
   public static final String REPLICATED_STORE_PREFIX = "weblogic.store.replicated";
   public static final String REPLICATED_STORE_PREFIX_DOT = "weblogic.store.replicated.";
   public static final String LOCAL_INDEX_PROP = ".LocalIndex";
   public static final int DEFAULT_LOCAL_INDEX = 0;
   public static final String SPACE_USAGE_WARNING_PERCENT_PROP = ".SpaceOverloadYellowPercent";
   public static final int DEFAULT_SPACE_USAGE_WARNING_PERCENT = 80;
   public static final String SPACE_USAGE_ERROR_PERCENT_PROP = ".SpaceOverloadRedPercent";
   public static final int DEFAULT_SPACE_USAGE_ERROR_PERCENT = 90;
   public static final String SPACE_USAGE_LOGGING_START_PERCENT_PROP = ".SpaceLoggingStartPercent";
   public static final int DEFAULT_SPACE_USAGE_LOGGING_START_PERCENT = 70;
   public static final String SPACE_USAGE_LOGGING_DELTA_PERCENT_PROP = ".SpaceLoggingDeltaPercent";
   public static final int DEFAULT_SPACE_USAGE_LOGGING_DELTA_PERCENT = 10;
   public static final String SPACE_USAGE_BATCH_FACTOR_PROP = ".SpaceOverloadBatchFactor";
   public static final int DEFAULT_SPACE_USAGE_BATCH_FACTOR = 1;
   public static final String MAXIMUM_MESSAGE_SIZE_PERCENT_PROP = ".MaximumMessageSizePercent";
   public static final int DEFAULT_MAXIMUM_MESSAGE_SIZE_PERCENT = 1;
   public static final String DEVICE_POLL_INTERVAL_PROP = ".DevicePollIntervalMillis";
   public static final int DEFAULT_DEVICE_POLL_INTERVAL = 1000;
   public static final String MAX_JDBC_TABLE_DUMP_SIZE = "weblogic.store.jdbc.MaxTableDumpSize";

   public static void register(Set specialProperties) {
      specialProperties.add("weblogic.store.jdbc.RefreshMillis");
      specialProperties.add("weblogic.jms.store.JMSJDBCIORetryDelay");
      specialProperties.add("weblogic.store.jdbc.IORetryDelayMillis");
      specialProperties.add("weblogic.store.InitialExtentSize");
      specialProperties.add("weblogic.store.SynchronousDeserializationSet");
      specialProperties.add("weblogic.store.DebugVerboseFileIO");
      specialProperties.add("weblogic.store.LargeDefaults");
      specialProperties.add("weblogic.store.CursorBatchSize");
   }

   public static final boolean isSpecialFileStoreProperty(String lowerCaseProp) {
      return lowerCaseProp.startsWith("weblogic.store.") && (lowerCaseProp.endsWith(".DirectIOMode".toLowerCase(Locale.US)) || lowerCaseProp.endsWith(".AvoidDirectIO".toLowerCase(Locale.US)) || lowerCaseProp.endsWith(".BlockSize".toLowerCase(Locale.US)));
   }
}
