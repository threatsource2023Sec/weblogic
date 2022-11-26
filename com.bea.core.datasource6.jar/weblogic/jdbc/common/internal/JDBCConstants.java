package weblogic.jdbc.common.internal;

import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public class JDBCConstants {
   public static final String JNDI_NAME_SEPARATOR_DEFAULT = ";";
   public static final int SHRINK_FREQUENCY_SECONDS_DEFAULT = 900;
   public static final int INACTIVE_CONN_TIMEOUT_SECONDS = 120;
   public static final String TEST_CONNS_ON_CREATE_DEFAULT = "true";
   public static final int COUNT_TILL_FLUSH_DEFAULT = 2;
   public static final int COUNT_TILL_DISABLE_DEFAULT = 2;
   public static final String SECURITY_CACHE_TIMEOUT_SECONDS_DEFAULT = "30";
   public static final int SECURITY_CACHE_TIMEOUT_SECONDS_MAX = 600;
   public static final String HARVESTING_TIMEOUT_SECONDS_DEFAULT = "30";
   public static final String MAINTENANCE_FREQUENCY_SECONDS_DEFAULT = "5";
   public static final String GRIDLINK_GRAVITATION_SHRINK_FREQUENCY_SECONDS_DEFAULT = "30";
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
   public static final int BEAN_TYPE_ORACLE = 5;
   public static final String TX_PROTO_TWO_PHASE_COMMIT = "TwoPhaseCommit";
   public static final String TX_PROTO_LOGGING_LAST_RESOURCE = "LoggingLastResource";
   public static final String TX_PROTO_EMULATE_TWO_PHASE_COMMIT = "EmulateTwoPhaseCommit";
   public static final String TX_PROTO_ONE_PHASE_COMMIT = "OnePhaseCommit";
   public static final String TX_PROTO_NONE = "None";
   public static final String TX_FIRST_RESOURCE_COMMIT = "FirstResourceCommit";
   public static final String AFFINITY_NONE = "None";
   public static final String AFFINITY_XA = "Transaction";
   public static final String AFFINITY_SESSION = "Session";
   public static final String AFFINITY_DATA = "Data";
   public static final int PROFILE_TYPE_NONE = 0;
   public static final String PROFILE_TYPE_NONE_STR = "WEBLOGIC.JDBC.NONE";
   public static final int PROFILE_TYPE_ALL = 4095;
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
   public static final int PROFILE_TYPE_CONN_UNWRAP_USAGE = 256;
   public static final String PROFILE_TYPE_CONN_UNWRAP_USAGE_STR = "WEBLOGIC.JDBC.CONN.UNWRAP_USAGE";
   public static final int PROFILE_TYPE_CONN_LOCALTX_LEAK = 512;
   public static final String PROFILE_TYPE_CONN_LOCALTX_LEAK_STR = "WEBLOGIC.JDBC.CONN.LOCALTX_LEAK";
   public static final int PROFILE_TYPE_CLOSED_USAGE = 1024;
   public static final String PROFILE_TYPE_CLOSED_USAGE_STR = "WEBLOGIC.JDBC.CLOSED_USAGE";
   public static final String SCOPE_GLOBAL = "Global";
   public static final String SCOPE_APPLICATION = "Application";
   public static final String MAX_CAPACITY = "MaxCapacity";
   public static final String MIN_CAPACITY = "MinCapacity";
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
   public static final String LEGACY_TYPE = "LegacyType";
   public static final String LEGACY_POOL_NAME = "LegacyPoolName";
   public static final String DATA_SOURCE_LIST = "DataSourceList";
   public static final String ORACLE_OPTIMIZE_UTF8_CONVERSION = "OracleOptimizeUtf8Conversion";
   public static final String FAN_ENABLED = "FanEnabled";
   public static final String REPLAY_ENABLED = "ReplayEnabled";
   public static final String CONNECTION_HARVEST_MAX_COUNT = "ConnectionHarvestMaxCount";
   public static final String CONNECTION_HARVEST_TRIGGER_COUNT = "ConnectionHarvestTriggerCount";
   public static final String REPLAY_INITIATION_TIMEOUT = "ReplayInitiationTimeout";
   public static final String COUNT_OF_REFRESH_FAILURES_TILL_DISABLE = "CountOfRefreshFailuresTillDisable";
   public static final String COUNT_OF_TEST_FAILURES_TILL_FLUSH = "CountOfTestFailuresTillFlush";
   public static final String PROXY_SWITCHING_PROPERTIES = "ProxySwitchingProperties";
   public static final String PROXY_SWITCHING_CALLBACK = "ProxySwitchingCallback";
   public static final String PROFILE_CONNECTION_LEAK_TIMEOUT_SECONDS = "ProfileConnectionLeakTimeoutSeconds";
   public static final String PASSWORD_ENCRYPTED = "PasswordEncrypted";
   public static final String BI_IMPERSONATE = "IMPERSONATE";
   public static final String PROP_FIRST_RESOURCE_COMMIT = "weblogic.jdbc.firstResourceCommit";
   public static final String PROP_COMMIT_OUTCOME_ENABLED = "weblogic.jdbc.commitOutcomeEnabled";
   public static final String PROP_WEBLOGIC_JDBC_COMMIT_OUTCOME_RETRY_MAX_SECONDS = "weblogic.jdbc.commitOutcomeRetryMaxSeconds";
   public static final int PROP_COMMIT_OUTCOME_RETRY_MAX_SECONDS_DEFAULT_VALUE = 120;
   public static final String PROP_ATTACH_NETWORK_TIMEOUT = "weblogic.jdbc.attachNetworkTimeout";
   public static final String PROP_CROSS_PARTITION_ENABLED = "weblogic.jdbc.crossPartitionEnabled";
   public static final String PROP_LOCAL_RESOURCE_ASSIGNMENT_ENABLED = "weblogic.jdbc.localResourceAssignmentEnabled";
   public static final String PROP_SHARED_POOL_JNDI_NAME = "weblogic.jdbc.sharedPoolJNDIName";
   public static final String PROP_SHARED_POOL = "weblogic.jdbc.sharedPool";
   public static final String PROP_PDB_NAME = "weblogic.jdbc.pdbName";
   public static final String PROP_PDB_SERVICE_NAME = "weblogic.jdbc.pdbServiceName";
   public static final String PROP_PDB_ROLE_PREFIX = "weblogic.jdbc.pdbRole.";
   public static final String PROP_PDB_PROXY_PREFIX = "weblogic.jdbc.pdbProxy.";
   public static final String PROP_DATASOURCE_TYPE = "weblogic.jdbc.type";
   public static final String PROP_CRITICAL = "weblogic.jdbc.critical";
   public static final String PROP_STARTUP_RETRY_COUNT = "weblogic.jdbc.startupRetryCount";
   public static final String PROP_STARTUP_RETRY_DELAY_SECONDS = "weblogic.jdbc.startupRetryDelaySeconds";
   public static final String PROP_CONTINUE_MAKE_RESOURCE_ATTEMPTS_AFTER_FAILURE = "weblogic.jdbc.continueMakeResourceAttemptsAfterFailure";
   public static final String PROP_MAX_CONCURRENT_CREATE_REQUESTS = "weblogic.jdbc.maxConcurrentCreateRequests";
   public static final String PROP_CONCURRENT_CREATE_REQUESTS_TIMEOUT_SECONDS = "weblogic.jdbc.concurrentCreateRequestsTimeoutSeconds";
   public static final String GET_CONNECTION_TO_INSTANCE_KEY = "_weblogic.jdbc.instanceName";
   public static final String CREATE_CONNECTION_KEY = "_weblogic.jdbc.properties";
   public static final String INTERNAL_PARTITION_NAME = "PartitionName";
   public static final String INTERNAL_UNQUALIFIED_NAME = "UnqualifiedName";
   public static final String TYPE_GENERIC = "GENERIC";
   public static final String TYPE_MDS = "MDS";
   public static final String TYPE_AGL = "AGL";
   public static final String TYPE_UCP = "UCP";
   public static final String TYPE_PROXY = "PROXY";
   public static final String AFFINITY_CONTEXT_KEY_PREFIX = "weblogic.jdbc.affinity.";
   public static final String GROUP_INSTANCE = "instance";
   public static final String GROUP_SERVICE_PDBNAME = "service_pdbname";
   public static final String GROUP_SERVICE_PDBNAME_INSTANCE = "service_pdbname_instance";
   public static final String PROP_PROXY_AU = "weblogic.jdbc.ProxyUseAuthUser";
   public static final String PROP_DRAIN_TIMEOUT = "weblogic.jdbc.drainTimeout";
   public static final String PROP_PROFILE_CONNECTION_LEAK_TIMEOUT_SECONDS = "weblogic.jdbc.profileConnectionLeakTimeoutSeconds";

   public static int computeCountTillFlush(int testSecs, int maxCap) {
      int retVal = 2;
      if (testSecs == 0 && maxCap > 8) {
         retVal = maxCap / 4;
      }

      return retVal;
   }

   public static String[] getDefaultJNDINames(JDBCDataSourceBean dsBean) {
      if (dsBean == null) {
         return new String[0];
      } else {
         String dsName = JDBCUtil.getUnqualifiedName(dsBean);
         if (dsName == null) {
            dsName = dsBean.getName();
         }

         return dsName == null ? new String[0] : new String[]{dsName};
      }
   }
}
