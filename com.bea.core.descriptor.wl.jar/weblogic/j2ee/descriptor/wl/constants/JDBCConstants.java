package weblogic.j2ee.descriptor.wl.constants;

public final class JDBCConstants {
   public static final String JNDI_NAME_SEPARATOR_DEFAULT = ";";
   public static final int SHRINK_FREQUENCY_SECONDS_DEFAULT = 900;
   public static final int INACTIVE_CONN_TIMEOUT_SECONDS = 120;
   public static final String TEST_CONNS_ON_CREATE_DEFAULT = "true";
   public static final int COUNT_TILL_FLUSH_DEFAULT = 2;
   public static final int COUNT_TILL_DISABLE_DEFAULT = 2;
   public static final String SECURITY_CACHE_TIMEOUT_SECONDS_DEFAULT = "30";
   public static final String ALGORITHM_HIGH_AVAILABILITY = "High-Availability";
   public static final String ALGORITHM_FAILOVER = "Failover";
   public static final String ALGORITHM_LOAD_BALANCING = "Load-Balancing";
   public static final String STATEMENT_CACHE_TYPE_LRU = "LRU";
   public static final String STATEMENT_CACHE_TYPE_FIXED = "FIXED";
   public static final String STATEMENT_CACHE_TYPE_DEFAULT = "LRU";
   public static final int STATEMENT_CACHE_TYPE_LRU_VAL = 0;
   public static final int STATEMENT_CACHE_TYPE_FIXED_VAL = 1;
   public static final int STATEMENT_CACHE_TYPE_DEFAULT_VAL = 0;
   public static final int MIN_CACHE_STATEMENTS_SIZE = 0;
   public static final int MAX_CACHE_STATEMENTS_SIZE = 1024;
   public static final int DEFAULT_CACHE_STATEMENTS_SIZE = 10;
   public static final int UNSPECIFIED_CACHE_STATEMENTS_SIZE = -1;
   public static final int BEAN_TYPE_NONE = 0;
   public static final int BEAN_TYPE_CONNECTIONPOOL = 1;
   public static final int BEAN_TYPE_MULTIPOOL = 2;
   public static final int BEAN_TYPE_DATASOURCE = 3;
   public static final int BEAN_TYPE_TXDATASOURCE = 4;
   public static final String TX_PROTO_TWO_PHASE_COMMIT = "TwoPhaseCommit";
   public static final String TX_PROTO_LOGGING_LAST_RESOURCE = "LoggingLastResource";
   public static final String TX_PROTO_EMULATE_TWO_PHASE_COMMIT = "EmulateTwoPhaseCommit";
   public static final String TX_PROTO_ONE_PHASE_COMMIT = "OnePhaseCommit";
   public static final String TX_PROTO_NONE = "None";
   public static final int PROFILE_TYPE_NONE = 0;
   public static final String PROFILE_TYPE_NONE_STR = "WEBLOGIC.JDBC.NONE";
   public static final int PROFILE_TYPE_ALL = 255;
   public static final String PROFILE_TYPE_ALL_STR = "WEBLOGIC.JDBC.ALL";
   public static final int PROFILE_TYPE_CONN_USAGE = 1;
   public static final String PROFILE_TYPE_CONN_USAGE_STR = "WEBLOGIC.JDBC.CONN.USAGE";
   public static final int PROFILE_TYPE_CONN_RESV_WAIT = 2;
   public static final String PROFILE_TYPE_CONN_RESV_WAIT_STR = "WEBLOGIC.JDBC.CONN.RESV.WAIT";
   public static final int PROFILE_TYPE_CONN_LEAK = 4;
   public static final String PROFILE_TYPE_CONN_LEAK_STR = "WEBLOGIC.JDBC.CONN.LEAK";
   public static final int PROFILE_TYPE_CONN_RESV_FAIL = 8;
   public static final String PROFILE_TYPE_CONN_RESV_FAIL_STR = "WEBLOGIC.JDBC.CONN.RESV.FAIL";
   public static final int PROFILE_TYPE_STMT_CACHE_ENTRY = 16;
   public static final String PROFILE_TYPE_STMT_CACHE_ENTRY_STR = "WEBLOGIC.JDBC.STMT_CACHE.ENTRY";
   public static final int PROFILE_TYPE_STMT_USAGE = 32;
   public static final String PROFILE_TYPE_STMT_USAGE_STR = "WEBLOGIC.JDBC.STMT.USAGE";
   public static final int PROFILE_TYPE_CONN_LAST_USAGE = 64;
   public static final String PROFILE_TYPE_CONN_LAST_USAGE_STR = "WEBLOGIC.JDBC.CONN.LAST_USAGE";
   public static final int PROFILE_TYPE_CONN_MT_USAGE = 128;
   public static final String PROFILE_TYPE_CONN_MT_USAGE_STR = "WEBLOGIC.JDBC.CONN.MT_USAGE";
   public static final String SCOPE_GLOBAL = "Global";
   public static final String SCOPE_APPLICATION = "Application";
   public static final String MAX_CAPACITY = "MaxCapacity";
   public static final String INITIAL_CAPACITY = "InitialCapacity";
   public static final String CAPACITY_INCREMENT = "CapacityIncrement";
   public static final String HIGHEST_NUM_WAITERS = "HighestNumWaiters";
   public static final String HIGHEST_NUM_UNAVL = "HighestNumUnavailable";
   public static final String INACTIVE_CONN_TIMEOUT_SECS = "InactiveConnectionTimeoutSeconds";
   public static final String CONN_RESERVE_TIMEOUT_SECS = "ConnectionReserveTimeoutSeconds";
   public static final String CONN_CREATION_RETRY_SECS = "ConnectionCreationRetryFrequencySeconds";
   public static final String SHRINK_FREQ_SECS = "ShrinkFrequencySeconds";
   public static final String TEST_FREQ_SECS = "TestFrequencySeconds";
   public static final String TEST_ON_RESERVE = "TestConnectionsOnReserve";
   public static final String TEST_ON_RELEASE = "TestConnectionsOnRelease";
   public static final String TEST_ON_CREATE = "TestConnectionsOnCreate";
   public static final String STMT_CACHE_SIZE = "StatementCacheSize";
   public static final String TEST_TABLE_NAME = "TestTableName";
   public static final String FAILOVER_IF_BUSY = "FailoverIfBusy";
   public static final String PROFILE_TYPE = "ProfileType";
   public static final String PROFILE_HARVEST_FREQ_SECS = "ProfileHarvestFrequencySeconds";
   public static final String IGNORE_IN_USE_CONNS_ENABLED = "IgnoreInUseConnsEnabled";
   public static final String SECS_TO_TRUST_IDLE_CONN = "SecondsToTrustAnIdlePoolConnection";
   public static final String COUNT_TILL_FLUSH = "CountOfTestFailuresTillFlush";
   public static final String COUNT_TILL_DISABLE = "CountOfRefreshFailuresTillDisable";
   public static final String JNDI_NAME_SEPARATOR = "JNDINameSeparator";
   public static final String CONN_LEAK_PROFILING = "ConnLeakProfilingEnabled";
   public static final String STMT_PROFILING = "SqlStmtProfilingEnabled";
   public static final String LEGACY_TYPE = "LegacyType";
   public static final String LEGACY_POOL_NAME = "LegacyPoolName";
   public static final String DATA_SOURCE_LIST = "DataSourceList";

   private JDBCConstants() {
   }

   public static int computeCountTillFlush(int testSecs, int maxCap) {
      int retVal = 2;
      if (testSecs == 0 && maxCap > 8) {
         retVal = maxCap / 4;
      }

      return retVal;
   }

   public static String[] getDefaultJNDINames(String dsName) {
      return dsName == null ? new String[0] : new String[]{dsName};
   }
}
