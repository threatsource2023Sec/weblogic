package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.jdbc.common.internal.JDBCHelperImpl;
import weblogic.utils.collections.CombinedIterator;

public class JDBCConnectionPoolParamsBeanImpl extends SettableBeanImpl implements JDBCConnectionPoolParamsBean, Serializable {
   private int _CapacityIncrement;
   private int _ConnectionCreationRetryFrequencySeconds;
   private int _ConnectionHarvestMaxCount;
   private int _ConnectionHarvestTriggerCount;
   private String _ConnectionLabelingCallback;
   private int _ConnectionReserveTimeoutSeconds;
   private int _CountOfRefreshFailuresTillDisable;
   private int _CountOfTestFailuresTillFlush;
   private boolean _CredentialMappingEnabled;
   private String _DriverInterceptor;
   private String _FatalErrorCodes;
   private int _HighestNumWaiters;
   private boolean _IdentityBasedConnectionPoolingEnabled;
   private boolean _IgnoreInUseConnectionsEnabled;
   private int _InactiveConnectionTimeoutSeconds;
   private String _InitSql;
   private int _InitialCapacity;
   private boolean _InvokeBeginEndRequest;
   private int _JDBCXADebugLevel;
   private int _LoginDelaySeconds;
   private int _MaxCapacity;
   private int _MinCapacity;
   private boolean _PinnedToThread;
   private int _ProfileConnectionLeakTimeoutSeconds;
   private int _ProfileHarvestFrequencySeconds;
   private int _ProfileType;
   private boolean _RemoveInfectedConnections;
   private int _SecondsToTrustAnIdlePoolConnection;
   private int _ShrinkFrequencySeconds;
   private int _StatementCacheSize;
   private String _StatementCacheType;
   private int _StatementTimeout;
   private boolean _TestConnectionsOnReserve;
   private int _TestFrequencySeconds;
   private String _TestTableName;
   private boolean _WrapJdbc;
   private boolean _WrapTypes;
   private static SchemaHelper2 _schemaHelper;

   public JDBCConnectionPoolParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCConnectionPoolParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCConnectionPoolParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getInitialCapacity() {
      return this._InitialCapacity;
   }

   public boolean isInitialCapacityInherited() {
      return false;
   }

   public boolean isInitialCapacitySet() {
      return this._isSet(0);
   }

   public void setInitialCapacity(int param0) {
      LegalChecks.checkInRange("InitialCapacity", (long)param0, 0L, 2147483647L);
      int _oldVal = this._InitialCapacity;
      this._InitialCapacity = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMaxCapacity() {
      return this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return false;
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(1);
   }

   public void setMaxCapacity(int param0) {
      LegalChecks.checkInRange("MaxCapacity", (long)param0, 1L, 2147483647L);
      int _oldVal = this._MaxCapacity;
      this._MaxCapacity = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMinCapacity() {
      if (!this._isSet(2)) {
         try {
            return this.getInitialCapacity();
         } catch (NullPointerException var2) {
         }
      }

      return this._MinCapacity;
   }

   public boolean isMinCapacityInherited() {
      return false;
   }

   public boolean isMinCapacitySet() {
      return this._isSet(2);
   }

   public void setMinCapacity(int param0) {
      LegalChecks.checkInRange("MinCapacity", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MinCapacity;
      this._MinCapacity = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getCapacityIncrement() {
      return this._CapacityIncrement;
   }

   public boolean isCapacityIncrementInherited() {
      return false;
   }

   public boolean isCapacityIncrementSet() {
      return this._isSet(3);
   }

   public void setCapacityIncrement(int param0) {
      LegalChecks.checkInRange("CapacityIncrement", (long)param0, 1L, 2147483647L);
      int _oldVal = this._CapacityIncrement;
      this._CapacityIncrement = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getShrinkFrequencySeconds() {
      return this._ShrinkFrequencySeconds;
   }

   public boolean isShrinkFrequencySecondsInherited() {
      return false;
   }

   public boolean isShrinkFrequencySecondsSet() {
      return this._isSet(4);
   }

   public void setShrinkFrequencySeconds(int param0) {
      LegalChecks.checkInRange("ShrinkFrequencySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ShrinkFrequencySeconds;
      this._ShrinkFrequencySeconds = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getHighestNumWaiters() {
      return this._HighestNumWaiters;
   }

   public boolean isHighestNumWaitersInherited() {
      return false;
   }

   public boolean isHighestNumWaitersSet() {
      return this._isSet(5);
   }

   public void setHighestNumWaiters(int param0) {
      LegalChecks.checkInRange("HighestNumWaiters", (long)param0, 0L, 2147483647L);
      int _oldVal = this._HighestNumWaiters;
      this._HighestNumWaiters = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this._ConnectionCreationRetryFrequencySeconds;
   }

   public boolean isConnectionCreationRetryFrequencySecondsInherited() {
      return false;
   }

   public boolean isConnectionCreationRetryFrequencySecondsSet() {
      return this._isSet(6);
   }

   public void setConnectionCreationRetryFrequencySeconds(int param0) {
      LegalChecks.checkInRange("ConnectionCreationRetryFrequencySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ConnectionCreationRetryFrequencySeconds;
      this._ConnectionCreationRetryFrequencySeconds = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this._ConnectionReserveTimeoutSeconds;
   }

   public boolean isConnectionReserveTimeoutSecondsInherited() {
      return false;
   }

   public boolean isConnectionReserveTimeoutSecondsSet() {
      return this._isSet(7);
   }

   public void setConnectionReserveTimeoutSeconds(int param0) {
      LegalChecks.checkInRange("ConnectionReserveTimeoutSeconds", (long)param0, -1L, 2147483647L);
      int _oldVal = this._ConnectionReserveTimeoutSeconds;
      this._ConnectionReserveTimeoutSeconds = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getTestFrequencySeconds() {
      return this._TestFrequencySeconds;
   }

   public boolean isTestFrequencySecondsInherited() {
      return false;
   }

   public boolean isTestFrequencySecondsSet() {
      return this._isSet(8);
   }

   public void setTestFrequencySeconds(int param0) {
      LegalChecks.checkInRange("TestFrequencySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._TestFrequencySeconds;
      this._TestFrequencySeconds = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isTestConnectionsOnReserve() {
      return this._TestConnectionsOnReserve;
   }

   public boolean isTestConnectionsOnReserveInherited() {
      return false;
   }

   public boolean isTestConnectionsOnReserveSet() {
      return this._isSet(9);
   }

   public void setTestConnectionsOnReserve(boolean param0) {
      boolean _oldVal = this._TestConnectionsOnReserve;
      this._TestConnectionsOnReserve = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getProfileHarvestFrequencySeconds() {
      return this._ProfileHarvestFrequencySeconds;
   }

   public boolean isProfileHarvestFrequencySecondsInherited() {
      return false;
   }

   public boolean isProfileHarvestFrequencySecondsSet() {
      return this._isSet(10);
   }

   public void setProfileHarvestFrequencySeconds(int param0) {
      LegalChecks.checkInRange("ProfileHarvestFrequencySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ProfileHarvestFrequencySeconds;
      this._ProfileHarvestFrequencySeconds = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      return this._IgnoreInUseConnectionsEnabled;
   }

   public boolean isIgnoreInUseConnectionsEnabledInherited() {
      return false;
   }

   public boolean isIgnoreInUseConnectionsEnabledSet() {
      return this._isSet(11);
   }

   public void setIgnoreInUseConnectionsEnabled(boolean param0) {
      boolean _oldVal = this._IgnoreInUseConnectionsEnabled;
      this._IgnoreInUseConnectionsEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this._InactiveConnectionTimeoutSeconds;
   }

   public boolean isInactiveConnectionTimeoutSecondsInherited() {
      return false;
   }

   public boolean isInactiveConnectionTimeoutSecondsSet() {
      return this._isSet(12);
   }

   public void setInactiveConnectionTimeoutSeconds(int param0) {
      LegalChecks.checkInRange("InactiveConnectionTimeoutSeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._InactiveConnectionTimeoutSeconds;
      this._InactiveConnectionTimeoutSeconds = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getTestTableName() {
      return this._TestTableName;
   }

   public boolean isTestTableNameInherited() {
      return false;
   }

   public boolean isTestTableNameSet() {
      return this._isSet(13);
   }

   public void setTestTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TestTableName;
      this._TestTableName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getLoginDelaySeconds() {
      return this._LoginDelaySeconds;
   }

   public boolean isLoginDelaySecondsInherited() {
      return false;
   }

   public boolean isLoginDelaySecondsSet() {
      return this._isSet(14);
   }

   public void setLoginDelaySeconds(int param0) {
      LegalChecks.checkInRange("LoginDelaySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._LoginDelaySeconds;
      this._LoginDelaySeconds = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getInitSql() {
      return this._InitSql;
   }

   public boolean isInitSqlInherited() {
      return false;
   }

   public boolean isInitSqlSet() {
      return this._isSet(15);
   }

   public void setInitSql(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitSql;
      this._InitSql = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getStatementCacheSize() {
      return this._StatementCacheSize;
   }

   public boolean isStatementCacheSizeInherited() {
      return false;
   }

   public boolean isStatementCacheSizeSet() {
      return this._isSet(16);
   }

   public void setStatementCacheSize(int param0) {
      LegalChecks.checkInRange("StatementCacheSize", (long)param0, 0L, 1024L);
      int _oldVal = this._StatementCacheSize;
      this._StatementCacheSize = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getStatementCacheType() {
      return this._StatementCacheType;
   }

   public boolean isStatementCacheTypeInherited() {
      return false;
   }

   public boolean isStatementCacheTypeSet() {
      return this._isSet(17);
   }

   public void setStatementCacheType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"LRU", "FIXED"};
      param0 = LegalChecks.checkInEnum("StatementCacheType", param0, _set);
      String _oldVal = this._StatementCacheType;
      this._StatementCacheType = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isRemoveInfectedConnections() {
      return this._RemoveInfectedConnections;
   }

   public boolean isRemoveInfectedConnectionsInherited() {
      return false;
   }

   public boolean isRemoveInfectedConnectionsSet() {
      return this._isSet(18);
   }

   public void setRemoveInfectedConnections(boolean param0) {
      boolean _oldVal = this._RemoveInfectedConnections;
      this._RemoveInfectedConnections = param0;
      this._postSet(18, _oldVal, param0);
   }

   public int getSecondsToTrustAnIdlePoolConnection() {
      return this._SecondsToTrustAnIdlePoolConnection;
   }

   public boolean isSecondsToTrustAnIdlePoolConnectionInherited() {
      return false;
   }

   public boolean isSecondsToTrustAnIdlePoolConnectionSet() {
      return this._isSet(19);
   }

   public void setSecondsToTrustAnIdlePoolConnection(int param0) {
      LegalChecks.checkInRange("SecondsToTrustAnIdlePoolConnection", (long)param0, 0L, 2147483647L);
      int _oldVal = this._SecondsToTrustAnIdlePoolConnection;
      this._SecondsToTrustAnIdlePoolConnection = param0;
      this._postSet(19, _oldVal, param0);
   }

   public int getStatementTimeout() {
      return this._StatementTimeout;
   }

   public boolean isStatementTimeoutInherited() {
      return false;
   }

   public boolean isStatementTimeoutSet() {
      return this._isSet(20);
   }

   public void setStatementTimeout(int param0) {
      LegalChecks.checkInRange("StatementTimeout", (long)param0, -1L, 2147483647L);
      int _oldVal = this._StatementTimeout;
      this._StatementTimeout = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getProfileType() {
      return this._ProfileType;
   }

   public boolean isProfileTypeInherited() {
      return false;
   }

   public boolean isProfileTypeSet() {
      return this._isSet(21);
   }

   public void setProfileType(int param0) {
      int _oldVal = this._ProfileType;
      this._ProfileType = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getJDBCXADebugLevel() {
      return this._JDBCXADebugLevel;
   }

   public boolean isJDBCXADebugLevelInherited() {
      return false;
   }

   public boolean isJDBCXADebugLevelSet() {
      return this._isSet(22);
   }

   public void setJDBCXADebugLevel(int param0) {
      LegalChecks.checkInRange("JDBCXADebugLevel", (long)param0, 0L, 100L);
      int _oldVal = this._JDBCXADebugLevel;
      this._JDBCXADebugLevel = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isCredentialMappingEnabled() {
      return this._CredentialMappingEnabled;
   }

   public boolean isCredentialMappingEnabledInherited() {
      return false;
   }

   public boolean isCredentialMappingEnabledSet() {
      return this._isSet(23);
   }

   public void setCredentialMappingEnabled(boolean param0) {
      boolean _oldVal = this._CredentialMappingEnabled;
      this._CredentialMappingEnabled = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getDriverInterceptor() {
      return this._DriverInterceptor;
   }

   public boolean isDriverInterceptorInherited() {
      return false;
   }

   public boolean isDriverInterceptorSet() {
      return this._isSet(24);
   }

   public void setDriverInterceptor(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DriverInterceptor;
      this._DriverInterceptor = param0;
      this._postSet(24, _oldVal, param0);
   }

   public boolean isPinnedToThread() {
      return this._PinnedToThread;
   }

   public boolean isPinnedToThreadInherited() {
      return false;
   }

   public boolean isPinnedToThreadSet() {
      return this._isSet(25);
   }

   public void setPinnedToThread(boolean param0) {
      boolean _oldVal = this._PinnedToThread;
      this._PinnedToThread = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean isIdentityBasedConnectionPoolingEnabled() {
      return this._IdentityBasedConnectionPoolingEnabled;
   }

   public boolean isIdentityBasedConnectionPoolingEnabledInherited() {
      return false;
   }

   public boolean isIdentityBasedConnectionPoolingEnabledSet() {
      return this._isSet(26);
   }

   public void setIdentityBasedConnectionPoolingEnabled(boolean param0) {
      boolean _oldVal = this._IdentityBasedConnectionPoolingEnabled;
      this._IdentityBasedConnectionPoolingEnabled = param0;
      this._postSet(26, _oldVal, param0);
   }

   public boolean isWrapTypes() {
      return this._WrapTypes;
   }

   public boolean isWrapTypesInherited() {
      return false;
   }

   public boolean isWrapTypesSet() {
      return this._isSet(27);
   }

   public void setWrapTypes(boolean param0) {
      boolean _oldVal = this._WrapTypes;
      this._WrapTypes = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getFatalErrorCodes() {
      return this._FatalErrorCodes;
   }

   public boolean isFatalErrorCodesInherited() {
      return false;
   }

   public boolean isFatalErrorCodesSet() {
      return this._isSet(28);
   }

   public void setFatalErrorCodes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FatalErrorCodes;
      this._FatalErrorCodes = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getConnectionLabelingCallback() {
      return this._ConnectionLabelingCallback;
   }

   public boolean isConnectionLabelingCallbackInherited() {
      return false;
   }

   public boolean isConnectionLabelingCallbackSet() {
      return this._isSet(29);
   }

   public void setConnectionLabelingCallback(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionLabelingCallback;
      this._ConnectionLabelingCallback = param0;
      this._postSet(29, _oldVal, param0);
   }

   public int getConnectionHarvestMaxCount() {
      return this._ConnectionHarvestMaxCount;
   }

   public boolean isConnectionHarvestMaxCountInherited() {
      return false;
   }

   public boolean isConnectionHarvestMaxCountSet() {
      return this._isSet(30);
   }

   public void setConnectionHarvestMaxCount(int param0) {
      LegalChecks.checkMin("ConnectionHarvestMaxCount", param0, 1);
      JDBCValidator.validateConnectionHarvestMaxCount(this, param0);
      int _oldVal = this._ConnectionHarvestMaxCount;
      this._ConnectionHarvestMaxCount = param0;
      this._postSet(30, _oldVal, param0);
   }

   public int getConnectionHarvestTriggerCount() {
      return this._ConnectionHarvestTriggerCount;
   }

   public boolean isConnectionHarvestTriggerCountInherited() {
      return false;
   }

   public boolean isConnectionHarvestTriggerCountSet() {
      return this._isSet(31);
   }

   public void setConnectionHarvestTriggerCount(int param0) {
      LegalChecks.checkMin("ConnectionHarvestTriggerCount", param0, -1);
      JDBCValidator.validateConnectionHarvestTriggerCount(this, param0);
      int _oldVal = this._ConnectionHarvestTriggerCount;
      this._ConnectionHarvestTriggerCount = param0;
      this._postSet(31, _oldVal, param0);
   }

   public int getCountOfTestFailuresTillFlush() {
      return this._CountOfTestFailuresTillFlush;
   }

   public boolean isCountOfTestFailuresTillFlushInherited() {
      return false;
   }

   public boolean isCountOfTestFailuresTillFlushSet() {
      return this._isSet(32);
   }

   public void setCountOfTestFailuresTillFlush(int param0) {
      LegalChecks.checkMin("CountOfTestFailuresTillFlush", param0, 0);
      int _oldVal = this._CountOfTestFailuresTillFlush;
      this._CountOfTestFailuresTillFlush = param0;
      this._postSet(32, _oldVal, param0);
   }

   public int getCountOfRefreshFailuresTillDisable() {
      return this._CountOfRefreshFailuresTillDisable;
   }

   public boolean isCountOfRefreshFailuresTillDisableInherited() {
      return false;
   }

   public boolean isCountOfRefreshFailuresTillDisableSet() {
      return this._isSet(33);
   }

   public void setCountOfRefreshFailuresTillDisable(int param0) {
      LegalChecks.checkMin("CountOfRefreshFailuresTillDisable", param0, 0);
      int _oldVal = this._CountOfRefreshFailuresTillDisable;
      this._CountOfRefreshFailuresTillDisable = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isWrapJdbc() {
      return this._WrapJdbc;
   }

   public boolean isWrapJdbcInherited() {
      return false;
   }

   public boolean isWrapJdbcSet() {
      return this._isSet(34);
   }

   public void setWrapJdbc(boolean param0) {
      boolean _oldVal = this._WrapJdbc;
      this._WrapJdbc = param0;
      this._postSet(34, _oldVal, param0);
   }

   public int getProfileConnectionLeakTimeoutSeconds() {
      return this._ProfileConnectionLeakTimeoutSeconds;
   }

   public boolean isProfileConnectionLeakTimeoutSecondsInherited() {
      return false;
   }

   public boolean isProfileConnectionLeakTimeoutSecondsSet() {
      return this._isSet(35);
   }

   public void setProfileConnectionLeakTimeoutSeconds(int param0) {
      LegalChecks.checkInRange("ProfileConnectionLeakTimeoutSeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._ProfileConnectionLeakTimeoutSeconds;
      this._ProfileConnectionLeakTimeoutSeconds = param0;
      this._postSet(35, _oldVal, param0);
   }

   public boolean isInvokeBeginEndRequest() {
      if (!this._isSet(36)) {
         try {
            return JDBCHelperImpl.getInvokeBeginEndRequestDefault((JDBCDataSourceBean)this.getParentBean());
         } catch (NullPointerException var2) {
         }
      }

      return this._InvokeBeginEndRequest;
   }

   public boolean isInvokeBeginEndRequestInherited() {
      return false;
   }

   public boolean isInvokeBeginEndRequestSet() {
      return this._isSet(36);
   }

   public void setInvokeBeginEndRequest(boolean param0) {
      boolean _oldVal = this._InvokeBeginEndRequest;
      this._InvokeBeginEndRequest = param0;
      this._postSet(36, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._CapacityIncrement = 1;
               if (initOne) {
                  break;
               }
            case 6:
               this._ConnectionCreationRetryFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 30:
               this._ConnectionHarvestMaxCount = 1;
               if (initOne) {
                  break;
               }
            case 31:
               this._ConnectionHarvestTriggerCount = -1;
               if (initOne) {
                  break;
               }
            case 29:
               this._ConnectionLabelingCallback = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._ConnectionReserveTimeoutSeconds = 10;
               if (initOne) {
                  break;
               }
            case 33:
               this._CountOfRefreshFailuresTillDisable = 2;
               if (initOne) {
                  break;
               }
            case 32:
               this._CountOfTestFailuresTillFlush = 2;
               if (initOne) {
                  break;
               }
            case 24:
               this._DriverInterceptor = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._FatalErrorCodes = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._HighestNumWaiters = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 12:
               this._InactiveConnectionTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 15:
               this._InitSql = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._InitialCapacity = 1;
               if (initOne) {
                  break;
               }
            case 22:
               this._JDBCXADebugLevel = 10;
               if (initOne) {
                  break;
               }
            case 14:
               this._LoginDelaySeconds = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxCapacity = 15;
               if (initOne) {
                  break;
               }
            case 2:
               this._MinCapacity = 1;
               if (initOne) {
                  break;
               }
            case 35:
               this._ProfileConnectionLeakTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 10:
               this._ProfileHarvestFrequencySeconds = 300;
               if (initOne) {
                  break;
               }
            case 21:
               this._ProfileType = 0;
               if (initOne) {
                  break;
               }
            case 19:
               this._SecondsToTrustAnIdlePoolConnection = 10;
               if (initOne) {
                  break;
               }
            case 4:
               this._ShrinkFrequencySeconds = 900;
               if (initOne) {
                  break;
               }
            case 16:
               this._StatementCacheSize = 10;
               if (initOne) {
                  break;
               }
            case 17:
               this._StatementCacheType = "LRU";
               if (initOne) {
                  break;
               }
            case 20:
               this._StatementTimeout = -1;
               if (initOne) {
                  break;
               }
            case 8:
               this._TestFrequencySeconds = 120;
               if (initOne) {
                  break;
               }
            case 13:
               this._TestTableName = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._CredentialMappingEnabled = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._IdentityBasedConnectionPoolingEnabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._IgnoreInUseConnectionsEnabled = true;
               if (initOne) {
                  break;
               }
            case 36:
               this._InvokeBeginEndRequest = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._PinnedToThread = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._RemoveInfectedConnections = true;
               if (initOne) {
                  break;
               }
            case 9:
               this._TestConnectionsOnReserve = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._WrapJdbc = true;
               if (initOne) {
                  break;
               }
            case 27:
               this._WrapTypes = true;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("init-sql")) {
                  return 15;
               }
               break;
            case 9:
               if (s.equals("wrap-jdbc")) {
                  return 34;
               }
               break;
            case 10:
               if (s.equals("wrap-types")) {
                  return 27;
               }
            case 11:
            case 13:
            case 14:
            case 21:
            case 23:
            case 25:
            case 29:
            case 30:
            case 31:
            case 36:
            case 37:
            case 42:
            default:
               break;
            case 12:
               if (s.equals("max-capacity")) {
                  return 1;
               }

               if (s.equals("min-capacity")) {
                  return 2;
               }

               if (s.equals("profile-type")) {
                  return 21;
               }
               break;
            case 15:
               if (s.equals("test-table-name")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("initial-capacity")) {
                  return 0;
               }

               if (s.equals("pinned-to-thread")) {
                  return 25;
               }
               break;
            case 17:
               if (s.equals("fatal-error-codes")) {
                  return 28;
               }

               if (s.equals("statement-timeout")) {
                  return 20;
               }
               break;
            case 18:
               if (s.equals("capacity-increment")) {
                  return 3;
               }

               if (s.equals("driver-interceptor")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("highest-num-waiters")) {
                  return 5;
               }

               if (s.equals("jdbc-xa-debug-level")) {
                  return 22;
               }

               if (s.equals("login-delay-seconds")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("statement-cache-size")) {
                  return 16;
               }

               if (s.equals("statement-cache-type")) {
                  return 17;
               }
               break;
            case 22:
               if (s.equals("test-frequency-seconds")) {
                  return 8;
               }
               break;
            case 24:
               if (s.equals("shrink-frequency-seconds")) {
                  return 4;
               }

               if (s.equals("invoke-begin-end-request")) {
                  return 36;
               }
               break;
            case 26:
               if (s.equals("credential-mapping-enabled")) {
                  return 23;
               }
               break;
            case 27:
               if (s.equals("remove-infected-connections")) {
                  return 18;
               }

               if (s.equals("test-connections-on-reserve")) {
                  return 9;
               }
               break;
            case 28:
               if (s.equals("connection-harvest-max-count")) {
                  return 30;
               }

               if (s.equals("connection-labeling-callback")) {
                  return 29;
               }
               break;
            case 32:
               if (s.equals("connection-harvest-trigger-count")) {
                  return 31;
               }
               break;
            case 33:
               if (s.equals("count-of-test-failures-till-flush")) {
                  return 32;
               }

               if (s.equals("profile-harvest-frequency-seconds")) {
                  return 10;
               }

               if (s.equals("ignore-in-use-connections-enabled")) {
                  return 11;
               }
               break;
            case 34:
               if (s.equals("connection-reserve-timeout-seconds")) {
                  return 7;
               }
               break;
            case 35:
               if (s.equals("inactive-connection-timeout-seconds")) {
                  return 12;
               }
               break;
            case 38:
               if (s.equals("count-of-refresh-failures-till-disable")) {
                  return 33;
               }
               break;
            case 39:
               if (s.equals("profile-connection-leak-timeout-seconds")) {
                  return 35;
               }
               break;
            case 40:
               if (s.equals("seconds-to-trust-an-idle-pool-connection")) {
                  return 19;
               }
               break;
            case 41:
               if (s.equals("identity-based-connection-pooling-enabled")) {
                  return 26;
               }
               break;
            case 43:
               if (s.equals("connection-creation-retry-frequency-seconds")) {
                  return 6;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "initial-capacity";
            case 1:
               return "max-capacity";
            case 2:
               return "min-capacity";
            case 3:
               return "capacity-increment";
            case 4:
               return "shrink-frequency-seconds";
            case 5:
               return "highest-num-waiters";
            case 6:
               return "connection-creation-retry-frequency-seconds";
            case 7:
               return "connection-reserve-timeout-seconds";
            case 8:
               return "test-frequency-seconds";
            case 9:
               return "test-connections-on-reserve";
            case 10:
               return "profile-harvest-frequency-seconds";
            case 11:
               return "ignore-in-use-connections-enabled";
            case 12:
               return "inactive-connection-timeout-seconds";
            case 13:
               return "test-table-name";
            case 14:
               return "login-delay-seconds";
            case 15:
               return "init-sql";
            case 16:
               return "statement-cache-size";
            case 17:
               return "statement-cache-type";
            case 18:
               return "remove-infected-connections";
            case 19:
               return "seconds-to-trust-an-idle-pool-connection";
            case 20:
               return "statement-timeout";
            case 21:
               return "profile-type";
            case 22:
               return "jdbc-xa-debug-level";
            case 23:
               return "credential-mapping-enabled";
            case 24:
               return "driver-interceptor";
            case 25:
               return "pinned-to-thread";
            case 26:
               return "identity-based-connection-pooling-enabled";
            case 27:
               return "wrap-types";
            case 28:
               return "fatal-error-codes";
            case 29:
               return "connection-labeling-callback";
            case 30:
               return "connection-harvest-max-count";
            case 31:
               return "connection-harvest-trigger-count";
            case 32:
               return "count-of-test-failures-till-flush";
            case 33:
               return "count-of-refresh-failures-till-disable";
            case 34:
               return "wrap-jdbc";
            case 35:
               return "profile-connection-leak-timeout-seconds";
            case 36:
               return "invoke-begin-end-request";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
            case 28:
            case 29:
            case 34:
            default:
               return super.isConfigurable(propIndex);
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 35:
               return true;
            case 36:
               return true;
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCConnectionPoolParamsBeanImpl bean;

      protected Helper(JDBCConnectionPoolParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InitialCapacity";
            case 1:
               return "MaxCapacity";
            case 2:
               return "MinCapacity";
            case 3:
               return "CapacityIncrement";
            case 4:
               return "ShrinkFrequencySeconds";
            case 5:
               return "HighestNumWaiters";
            case 6:
               return "ConnectionCreationRetryFrequencySeconds";
            case 7:
               return "ConnectionReserveTimeoutSeconds";
            case 8:
               return "TestFrequencySeconds";
            case 9:
               return "TestConnectionsOnReserve";
            case 10:
               return "ProfileHarvestFrequencySeconds";
            case 11:
               return "IgnoreInUseConnectionsEnabled";
            case 12:
               return "InactiveConnectionTimeoutSeconds";
            case 13:
               return "TestTableName";
            case 14:
               return "LoginDelaySeconds";
            case 15:
               return "InitSql";
            case 16:
               return "StatementCacheSize";
            case 17:
               return "StatementCacheType";
            case 18:
               return "RemoveInfectedConnections";
            case 19:
               return "SecondsToTrustAnIdlePoolConnection";
            case 20:
               return "StatementTimeout";
            case 21:
               return "ProfileType";
            case 22:
               return "JDBCXADebugLevel";
            case 23:
               return "CredentialMappingEnabled";
            case 24:
               return "DriverInterceptor";
            case 25:
               return "PinnedToThread";
            case 26:
               return "IdentityBasedConnectionPoolingEnabled";
            case 27:
               return "WrapTypes";
            case 28:
               return "FatalErrorCodes";
            case 29:
               return "ConnectionLabelingCallback";
            case 30:
               return "ConnectionHarvestMaxCount";
            case 31:
               return "ConnectionHarvestTriggerCount";
            case 32:
               return "CountOfTestFailuresTillFlush";
            case 33:
               return "CountOfRefreshFailuresTillDisable";
            case 34:
               return "WrapJdbc";
            case 35:
               return "ProfileConnectionLeakTimeoutSeconds";
            case 36:
               return "InvokeBeginEndRequest";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CapacityIncrement")) {
            return 3;
         } else if (propName.equals("ConnectionCreationRetryFrequencySeconds")) {
            return 6;
         } else if (propName.equals("ConnectionHarvestMaxCount")) {
            return 30;
         } else if (propName.equals("ConnectionHarvestTriggerCount")) {
            return 31;
         } else if (propName.equals("ConnectionLabelingCallback")) {
            return 29;
         } else if (propName.equals("ConnectionReserveTimeoutSeconds")) {
            return 7;
         } else if (propName.equals("CountOfRefreshFailuresTillDisable")) {
            return 33;
         } else if (propName.equals("CountOfTestFailuresTillFlush")) {
            return 32;
         } else if (propName.equals("DriverInterceptor")) {
            return 24;
         } else if (propName.equals("FatalErrorCodes")) {
            return 28;
         } else if (propName.equals("HighestNumWaiters")) {
            return 5;
         } else if (propName.equals("InactiveConnectionTimeoutSeconds")) {
            return 12;
         } else if (propName.equals("InitSql")) {
            return 15;
         } else if (propName.equals("InitialCapacity")) {
            return 0;
         } else if (propName.equals("JDBCXADebugLevel")) {
            return 22;
         } else if (propName.equals("LoginDelaySeconds")) {
            return 14;
         } else if (propName.equals("MaxCapacity")) {
            return 1;
         } else if (propName.equals("MinCapacity")) {
            return 2;
         } else if (propName.equals("ProfileConnectionLeakTimeoutSeconds")) {
            return 35;
         } else if (propName.equals("ProfileHarvestFrequencySeconds")) {
            return 10;
         } else if (propName.equals("ProfileType")) {
            return 21;
         } else if (propName.equals("SecondsToTrustAnIdlePoolConnection")) {
            return 19;
         } else if (propName.equals("ShrinkFrequencySeconds")) {
            return 4;
         } else if (propName.equals("StatementCacheSize")) {
            return 16;
         } else if (propName.equals("StatementCacheType")) {
            return 17;
         } else if (propName.equals("StatementTimeout")) {
            return 20;
         } else if (propName.equals("TestFrequencySeconds")) {
            return 8;
         } else if (propName.equals("TestTableName")) {
            return 13;
         } else if (propName.equals("CredentialMappingEnabled")) {
            return 23;
         } else if (propName.equals("IdentityBasedConnectionPoolingEnabled")) {
            return 26;
         } else if (propName.equals("IgnoreInUseConnectionsEnabled")) {
            return 11;
         } else if (propName.equals("InvokeBeginEndRequest")) {
            return 36;
         } else if (propName.equals("PinnedToThread")) {
            return 25;
         } else if (propName.equals("RemoveInfectedConnections")) {
            return 18;
         } else if (propName.equals("TestConnectionsOnReserve")) {
            return 9;
         } else if (propName.equals("WrapJdbc")) {
            return 34;
         } else {
            return propName.equals("WrapTypes") ? 27 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isCapacityIncrementSet()) {
               buf.append("CapacityIncrement");
               buf.append(String.valueOf(this.bean.getCapacityIncrement()));
            }

            if (this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               buf.append("ConnectionCreationRetryFrequencySeconds");
               buf.append(String.valueOf(this.bean.getConnectionCreationRetryFrequencySeconds()));
            }

            if (this.bean.isConnectionHarvestMaxCountSet()) {
               buf.append("ConnectionHarvestMaxCount");
               buf.append(String.valueOf(this.bean.getConnectionHarvestMaxCount()));
            }

            if (this.bean.isConnectionHarvestTriggerCountSet()) {
               buf.append("ConnectionHarvestTriggerCount");
               buf.append(String.valueOf(this.bean.getConnectionHarvestTriggerCount()));
            }

            if (this.bean.isConnectionLabelingCallbackSet()) {
               buf.append("ConnectionLabelingCallback");
               buf.append(String.valueOf(this.bean.getConnectionLabelingCallback()));
            }

            if (this.bean.isConnectionReserveTimeoutSecondsSet()) {
               buf.append("ConnectionReserveTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getConnectionReserveTimeoutSeconds()));
            }

            if (this.bean.isCountOfRefreshFailuresTillDisableSet()) {
               buf.append("CountOfRefreshFailuresTillDisable");
               buf.append(String.valueOf(this.bean.getCountOfRefreshFailuresTillDisable()));
            }

            if (this.bean.isCountOfTestFailuresTillFlushSet()) {
               buf.append("CountOfTestFailuresTillFlush");
               buf.append(String.valueOf(this.bean.getCountOfTestFailuresTillFlush()));
            }

            if (this.bean.isDriverInterceptorSet()) {
               buf.append("DriverInterceptor");
               buf.append(String.valueOf(this.bean.getDriverInterceptor()));
            }

            if (this.bean.isFatalErrorCodesSet()) {
               buf.append("FatalErrorCodes");
               buf.append(String.valueOf(this.bean.getFatalErrorCodes()));
            }

            if (this.bean.isHighestNumWaitersSet()) {
               buf.append("HighestNumWaiters");
               buf.append(String.valueOf(this.bean.getHighestNumWaiters()));
            }

            if (this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               buf.append("InactiveConnectionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getInactiveConnectionTimeoutSeconds()));
            }

            if (this.bean.isInitSqlSet()) {
               buf.append("InitSql");
               buf.append(String.valueOf(this.bean.getInitSql()));
            }

            if (this.bean.isInitialCapacitySet()) {
               buf.append("InitialCapacity");
               buf.append(String.valueOf(this.bean.getInitialCapacity()));
            }

            if (this.bean.isJDBCXADebugLevelSet()) {
               buf.append("JDBCXADebugLevel");
               buf.append(String.valueOf(this.bean.getJDBCXADebugLevel()));
            }

            if (this.bean.isLoginDelaySecondsSet()) {
               buf.append("LoginDelaySeconds");
               buf.append(String.valueOf(this.bean.getLoginDelaySeconds()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isMinCapacitySet()) {
               buf.append("MinCapacity");
               buf.append(String.valueOf(this.bean.getMinCapacity()));
            }

            if (this.bean.isProfileConnectionLeakTimeoutSecondsSet()) {
               buf.append("ProfileConnectionLeakTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getProfileConnectionLeakTimeoutSeconds()));
            }

            if (this.bean.isProfileHarvestFrequencySecondsSet()) {
               buf.append("ProfileHarvestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getProfileHarvestFrequencySeconds()));
            }

            if (this.bean.isProfileTypeSet()) {
               buf.append("ProfileType");
               buf.append(String.valueOf(this.bean.getProfileType()));
            }

            if (this.bean.isSecondsToTrustAnIdlePoolConnectionSet()) {
               buf.append("SecondsToTrustAnIdlePoolConnection");
               buf.append(String.valueOf(this.bean.getSecondsToTrustAnIdlePoolConnection()));
            }

            if (this.bean.isShrinkFrequencySecondsSet()) {
               buf.append("ShrinkFrequencySeconds");
               buf.append(String.valueOf(this.bean.getShrinkFrequencySeconds()));
            }

            if (this.bean.isStatementCacheSizeSet()) {
               buf.append("StatementCacheSize");
               buf.append(String.valueOf(this.bean.getStatementCacheSize()));
            }

            if (this.bean.isStatementCacheTypeSet()) {
               buf.append("StatementCacheType");
               buf.append(String.valueOf(this.bean.getStatementCacheType()));
            }

            if (this.bean.isStatementTimeoutSet()) {
               buf.append("StatementTimeout");
               buf.append(String.valueOf(this.bean.getStatementTimeout()));
            }

            if (this.bean.isTestFrequencySecondsSet()) {
               buf.append("TestFrequencySeconds");
               buf.append(String.valueOf(this.bean.getTestFrequencySeconds()));
            }

            if (this.bean.isTestTableNameSet()) {
               buf.append("TestTableName");
               buf.append(String.valueOf(this.bean.getTestTableName()));
            }

            if (this.bean.isCredentialMappingEnabledSet()) {
               buf.append("CredentialMappingEnabled");
               buf.append(String.valueOf(this.bean.isCredentialMappingEnabled()));
            }

            if (this.bean.isIdentityBasedConnectionPoolingEnabledSet()) {
               buf.append("IdentityBasedConnectionPoolingEnabled");
               buf.append(String.valueOf(this.bean.isIdentityBasedConnectionPoolingEnabled()));
            }

            if (this.bean.isIgnoreInUseConnectionsEnabledSet()) {
               buf.append("IgnoreInUseConnectionsEnabled");
               buf.append(String.valueOf(this.bean.isIgnoreInUseConnectionsEnabled()));
            }

            if (this.bean.isInvokeBeginEndRequestSet()) {
               buf.append("InvokeBeginEndRequest");
               buf.append(String.valueOf(this.bean.isInvokeBeginEndRequest()));
            }

            if (this.bean.isPinnedToThreadSet()) {
               buf.append("PinnedToThread");
               buf.append(String.valueOf(this.bean.isPinnedToThread()));
            }

            if (this.bean.isRemoveInfectedConnectionsSet()) {
               buf.append("RemoveInfectedConnections");
               buf.append(String.valueOf(this.bean.isRemoveInfectedConnections()));
            }

            if (this.bean.isTestConnectionsOnReserveSet()) {
               buf.append("TestConnectionsOnReserve");
               buf.append(String.valueOf(this.bean.isTestConnectionsOnReserve()));
            }

            if (this.bean.isWrapJdbcSet()) {
               buf.append("WrapJdbc");
               buf.append(String.valueOf(this.bean.isWrapJdbc()));
            }

            if (this.bean.isWrapTypesSet()) {
               buf.append("WrapTypes");
               buf.append(String.valueOf(this.bean.isWrapTypes()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            JDBCConnectionPoolParamsBeanImpl otherTyped = (JDBCConnectionPoolParamsBeanImpl)other;
            this.computeDiff("CapacityIncrement", this.bean.getCapacityIncrement(), otherTyped.getCapacityIncrement(), true);
            this.computeDiff("ConnectionCreationRetryFrequencySeconds", this.bean.getConnectionCreationRetryFrequencySeconds(), otherTyped.getConnectionCreationRetryFrequencySeconds(), true);
            this.computeDiff("ConnectionHarvestMaxCount", this.bean.getConnectionHarvestMaxCount(), otherTyped.getConnectionHarvestMaxCount(), true);
            this.computeDiff("ConnectionHarvestTriggerCount", this.bean.getConnectionHarvestTriggerCount(), otherTyped.getConnectionHarvestTriggerCount(), true);
            this.computeDiff("ConnectionLabelingCallback", this.bean.getConnectionLabelingCallback(), otherTyped.getConnectionLabelingCallback(), false);
            this.computeDiff("ConnectionReserveTimeoutSeconds", this.bean.getConnectionReserveTimeoutSeconds(), otherTyped.getConnectionReserveTimeoutSeconds(), true);
            this.computeDiff("CountOfRefreshFailuresTillDisable", this.bean.getCountOfRefreshFailuresTillDisable(), otherTyped.getCountOfRefreshFailuresTillDisable(), true);
            this.computeDiff("CountOfTestFailuresTillFlush", this.bean.getCountOfTestFailuresTillFlush(), otherTyped.getCountOfTestFailuresTillFlush(), true);
            this.computeDiff("DriverInterceptor", this.bean.getDriverInterceptor(), otherTyped.getDriverInterceptor(), false);
            this.computeDiff("FatalErrorCodes", this.bean.getFatalErrorCodes(), otherTyped.getFatalErrorCodes(), false);
            this.computeDiff("HighestNumWaiters", this.bean.getHighestNumWaiters(), otherTyped.getHighestNumWaiters(), true);
            this.computeDiff("InactiveConnectionTimeoutSeconds", this.bean.getInactiveConnectionTimeoutSeconds(), otherTyped.getInactiveConnectionTimeoutSeconds(), true);
            this.computeDiff("InitSql", this.bean.getInitSql(), otherTyped.getInitSql(), false);
            this.computeDiff("InitialCapacity", this.bean.getInitialCapacity(), otherTyped.getInitialCapacity(), true);
            this.computeDiff("JDBCXADebugLevel", this.bean.getJDBCXADebugLevel(), otherTyped.getJDBCXADebugLevel(), false);
            this.computeDiff("LoginDelaySeconds", this.bean.getLoginDelaySeconds(), otherTyped.getLoginDelaySeconds(), false);
            this.computeDiff("MaxCapacity", this.bean.getMaxCapacity(), otherTyped.getMaxCapacity(), true);
            this.computeDiff("MinCapacity", this.bean.getMinCapacity(), otherTyped.getMinCapacity(), true);
            this.computeDiff("ProfileConnectionLeakTimeoutSeconds", this.bean.getProfileConnectionLeakTimeoutSeconds(), otherTyped.getProfileConnectionLeakTimeoutSeconds(), true);
            this.computeDiff("ProfileHarvestFrequencySeconds", this.bean.getProfileHarvestFrequencySeconds(), otherTyped.getProfileHarvestFrequencySeconds(), true);
            this.computeDiff("ProfileType", this.bean.getProfileType(), otherTyped.getProfileType(), true);
            this.computeDiff("SecondsToTrustAnIdlePoolConnection", this.bean.getSecondsToTrustAnIdlePoolConnection(), otherTyped.getSecondsToTrustAnIdlePoolConnection(), true);
            this.computeDiff("ShrinkFrequencySeconds", this.bean.getShrinkFrequencySeconds(), otherTyped.getShrinkFrequencySeconds(), true);
            this.computeDiff("StatementCacheSize", this.bean.getStatementCacheSize(), otherTyped.getStatementCacheSize(), true);
            this.computeDiff("StatementCacheType", this.bean.getStatementCacheType(), otherTyped.getStatementCacheType(), false);
            this.computeDiff("StatementTimeout", this.bean.getStatementTimeout(), otherTyped.getStatementTimeout(), false);
            this.computeDiff("TestFrequencySeconds", this.bean.getTestFrequencySeconds(), otherTyped.getTestFrequencySeconds(), true);
            this.computeDiff("TestTableName", this.bean.getTestTableName(), otherTyped.getTestTableName(), true);
            this.computeDiff("CredentialMappingEnabled", this.bean.isCredentialMappingEnabled(), otherTyped.isCredentialMappingEnabled(), false);
            this.computeDiff("IdentityBasedConnectionPoolingEnabled", this.bean.isIdentityBasedConnectionPoolingEnabled(), otherTyped.isIdentityBasedConnectionPoolingEnabled(), false);
            this.computeDiff("IgnoreInUseConnectionsEnabled", this.bean.isIgnoreInUseConnectionsEnabled(), otherTyped.isIgnoreInUseConnectionsEnabled(), false);
            this.computeDiff("InvokeBeginEndRequest", this.bean.isInvokeBeginEndRequest(), otherTyped.isInvokeBeginEndRequest(), false);
            this.computeDiff("PinnedToThread", this.bean.isPinnedToThread(), otherTyped.isPinnedToThread(), false);
            this.computeDiff("RemoveInfectedConnections", this.bean.isRemoveInfectedConnections(), otherTyped.isRemoveInfectedConnections(), false);
            this.computeDiff("TestConnectionsOnReserve", this.bean.isTestConnectionsOnReserve(), otherTyped.isTestConnectionsOnReserve(), true);
            this.computeDiff("WrapJdbc", this.bean.isWrapJdbc(), otherTyped.isWrapJdbc(), false);
            this.computeDiff("WrapTypes", this.bean.isWrapTypes(), otherTyped.isWrapTypes(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCConnectionPoolParamsBeanImpl original = (JDBCConnectionPoolParamsBeanImpl)event.getSourceBean();
            JDBCConnectionPoolParamsBeanImpl proposed = (JDBCConnectionPoolParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CapacityIncrement")) {
                  original.setCapacityIncrement(proposed.getCapacityIncrement());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ConnectionCreationRetryFrequencySeconds")) {
                  original.setConnectionCreationRetryFrequencySeconds(proposed.getConnectionCreationRetryFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ConnectionHarvestMaxCount")) {
                  original.setConnectionHarvestMaxCount(proposed.getConnectionHarvestMaxCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("ConnectionHarvestTriggerCount")) {
                  original.setConnectionHarvestTriggerCount(proposed.getConnectionHarvestTriggerCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("ConnectionLabelingCallback")) {
                  original.setConnectionLabelingCallback(proposed.getConnectionLabelingCallback());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("ConnectionReserveTimeoutSeconds")) {
                  original.setConnectionReserveTimeoutSeconds(proposed.getConnectionReserveTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("CountOfRefreshFailuresTillDisable")) {
                  original.setCountOfRefreshFailuresTillDisable(proposed.getCountOfRefreshFailuresTillDisable());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("CountOfTestFailuresTillFlush")) {
                  original.setCountOfTestFailuresTillFlush(proposed.getCountOfTestFailuresTillFlush());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("DriverInterceptor")) {
                  original.setDriverInterceptor(proposed.getDriverInterceptor());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("FatalErrorCodes")) {
                  original.setFatalErrorCodes(proposed.getFatalErrorCodes());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("HighestNumWaiters")) {
                  original.setHighestNumWaiters(proposed.getHighestNumWaiters());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InactiveConnectionTimeoutSeconds")) {
                  original.setInactiveConnectionTimeoutSeconds(proposed.getInactiveConnectionTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("InitSql")) {
                  original.setInitSql(proposed.getInitSql());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("InitialCapacity")) {
                  original.setInitialCapacity(proposed.getInitialCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("JDBCXADebugLevel")) {
                  original.setJDBCXADebugLevel(proposed.getJDBCXADebugLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("LoginDelaySeconds")) {
                  original.setLoginDelaySeconds(proposed.getLoginDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MaxCapacity")) {
                  original.setMaxCapacity(proposed.getMaxCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MinCapacity")) {
                  original.setMinCapacity(proposed.getMinCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ProfileConnectionLeakTimeoutSeconds")) {
                  original.setProfileConnectionLeakTimeoutSeconds(proposed.getProfileConnectionLeakTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("ProfileHarvestFrequencySeconds")) {
                  original.setProfileHarvestFrequencySeconds(proposed.getProfileHarvestFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ProfileType")) {
                  original.setProfileType(proposed.getProfileType());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("SecondsToTrustAnIdlePoolConnection")) {
                  original.setSecondsToTrustAnIdlePoolConnection(proposed.getSecondsToTrustAnIdlePoolConnection());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ShrinkFrequencySeconds")) {
                  original.setShrinkFrequencySeconds(proposed.getShrinkFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("StatementCacheSize")) {
                  original.setStatementCacheSize(proposed.getStatementCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("StatementCacheType")) {
                  original.setStatementCacheType(proposed.getStatementCacheType());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("StatementTimeout")) {
                  original.setStatementTimeout(proposed.getStatementTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("TestFrequencySeconds")) {
                  original.setTestFrequencySeconds(proposed.getTestFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("TestTableName")) {
                  original.setTestTableName(proposed.getTestTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CredentialMappingEnabled")) {
                  original.setCredentialMappingEnabled(proposed.isCredentialMappingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("IdentityBasedConnectionPoolingEnabled")) {
                  original.setIdentityBasedConnectionPoolingEnabled(proposed.isIdentityBasedConnectionPoolingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("IgnoreInUseConnectionsEnabled")) {
                  original.setIgnoreInUseConnectionsEnabled(proposed.isIgnoreInUseConnectionsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("InvokeBeginEndRequest")) {
                  original.setInvokeBeginEndRequest(proposed.isInvokeBeginEndRequest());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("PinnedToThread")) {
                  original.setPinnedToThread(proposed.isPinnedToThread());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("RemoveInfectedConnections")) {
                  original.setRemoveInfectedConnections(proposed.isRemoveInfectedConnections());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("TestConnectionsOnReserve")) {
                  original.setTestConnectionsOnReserve(proposed.isTestConnectionsOnReserve());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("WrapJdbc")) {
                  original.setWrapJdbc(proposed.isWrapJdbc());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("WrapTypes")) {
                  original.setWrapTypes(proposed.isWrapTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            JDBCConnectionPoolParamsBeanImpl copy = (JDBCConnectionPoolParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CapacityIncrement")) && this.bean.isCapacityIncrementSet()) {
               copy.setCapacityIncrement(this.bean.getCapacityIncrement());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionCreationRetryFrequencySeconds")) && this.bean.isConnectionCreationRetryFrequencySecondsSet()) {
               copy.setConnectionCreationRetryFrequencySeconds(this.bean.getConnectionCreationRetryFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionHarvestMaxCount")) && this.bean.isConnectionHarvestMaxCountSet()) {
               copy.setConnectionHarvestMaxCount(this.bean.getConnectionHarvestMaxCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionHarvestTriggerCount")) && this.bean.isConnectionHarvestTriggerCountSet()) {
               copy.setConnectionHarvestTriggerCount(this.bean.getConnectionHarvestTriggerCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionLabelingCallback")) && this.bean.isConnectionLabelingCallbackSet()) {
               copy.setConnectionLabelingCallback(this.bean.getConnectionLabelingCallback());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionReserveTimeoutSeconds")) && this.bean.isConnectionReserveTimeoutSecondsSet()) {
               copy.setConnectionReserveTimeoutSeconds(this.bean.getConnectionReserveTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CountOfRefreshFailuresTillDisable")) && this.bean.isCountOfRefreshFailuresTillDisableSet()) {
               copy.setCountOfRefreshFailuresTillDisable(this.bean.getCountOfRefreshFailuresTillDisable());
            }

            if ((excludeProps == null || !excludeProps.contains("CountOfTestFailuresTillFlush")) && this.bean.isCountOfTestFailuresTillFlushSet()) {
               copy.setCountOfTestFailuresTillFlush(this.bean.getCountOfTestFailuresTillFlush());
            }

            if ((excludeProps == null || !excludeProps.contains("DriverInterceptor")) && this.bean.isDriverInterceptorSet()) {
               copy.setDriverInterceptor(this.bean.getDriverInterceptor());
            }

            if ((excludeProps == null || !excludeProps.contains("FatalErrorCodes")) && this.bean.isFatalErrorCodesSet()) {
               copy.setFatalErrorCodes(this.bean.getFatalErrorCodes());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumWaiters")) && this.bean.isHighestNumWaitersSet()) {
               copy.setHighestNumWaiters(this.bean.getHighestNumWaiters());
            }

            if ((excludeProps == null || !excludeProps.contains("InactiveConnectionTimeoutSeconds")) && this.bean.isInactiveConnectionTimeoutSecondsSet()) {
               copy.setInactiveConnectionTimeoutSeconds(this.bean.getInactiveConnectionTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InitSql")) && this.bean.isInitSqlSet()) {
               copy.setInitSql(this.bean.getInitSql());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialCapacity")) && this.bean.isInitialCapacitySet()) {
               copy.setInitialCapacity(this.bean.getInitialCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCXADebugLevel")) && this.bean.isJDBCXADebugLevelSet()) {
               copy.setJDBCXADebugLevel(this.bean.getJDBCXADebugLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginDelaySeconds")) && this.bean.isLoginDelaySecondsSet()) {
               copy.setLoginDelaySeconds(this.bean.getLoginDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCapacity")) && this.bean.isMaxCapacitySet()) {
               copy.setMaxCapacity(this.bean.getMaxCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("MinCapacity")) && this.bean.isMinCapacitySet()) {
               copy.setMinCapacity(this.bean.getMinCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("ProfileConnectionLeakTimeoutSeconds")) && this.bean.isProfileConnectionLeakTimeoutSecondsSet()) {
               copy.setProfileConnectionLeakTimeoutSeconds(this.bean.getProfileConnectionLeakTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ProfileHarvestFrequencySeconds")) && this.bean.isProfileHarvestFrequencySecondsSet()) {
               copy.setProfileHarvestFrequencySeconds(this.bean.getProfileHarvestFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ProfileType")) && this.bean.isProfileTypeSet()) {
               copy.setProfileType(this.bean.getProfileType());
            }

            if ((excludeProps == null || !excludeProps.contains("SecondsToTrustAnIdlePoolConnection")) && this.bean.isSecondsToTrustAnIdlePoolConnectionSet()) {
               copy.setSecondsToTrustAnIdlePoolConnection(this.bean.getSecondsToTrustAnIdlePoolConnection());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkFrequencySeconds")) && this.bean.isShrinkFrequencySecondsSet()) {
               copy.setShrinkFrequencySeconds(this.bean.getShrinkFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("StatementCacheSize")) && this.bean.isStatementCacheSizeSet()) {
               copy.setStatementCacheSize(this.bean.getStatementCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("StatementCacheType")) && this.bean.isStatementCacheTypeSet()) {
               copy.setStatementCacheType(this.bean.getStatementCacheType());
            }

            if ((excludeProps == null || !excludeProps.contains("StatementTimeout")) && this.bean.isStatementTimeoutSet()) {
               copy.setStatementTimeout(this.bean.getStatementTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("TestFrequencySeconds")) && this.bean.isTestFrequencySecondsSet()) {
               copy.setTestFrequencySeconds(this.bean.getTestFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("TestTableName")) && this.bean.isTestTableNameSet()) {
               copy.setTestTableName(this.bean.getTestTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialMappingEnabled")) && this.bean.isCredentialMappingEnabledSet()) {
               copy.setCredentialMappingEnabled(this.bean.isCredentialMappingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityBasedConnectionPoolingEnabled")) && this.bean.isIdentityBasedConnectionPoolingEnabledSet()) {
               copy.setIdentityBasedConnectionPoolingEnabled(this.bean.isIdentityBasedConnectionPoolingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreInUseConnectionsEnabled")) && this.bean.isIgnoreInUseConnectionsEnabledSet()) {
               copy.setIgnoreInUseConnectionsEnabled(this.bean.isIgnoreInUseConnectionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("InvokeBeginEndRequest")) && this.bean.isInvokeBeginEndRequestSet()) {
               copy.setInvokeBeginEndRequest(this.bean.isInvokeBeginEndRequest());
            }

            if ((excludeProps == null || !excludeProps.contains("PinnedToThread")) && this.bean.isPinnedToThreadSet()) {
               copy.setPinnedToThread(this.bean.isPinnedToThread());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoveInfectedConnections")) && this.bean.isRemoveInfectedConnectionsSet()) {
               copy.setRemoveInfectedConnections(this.bean.isRemoveInfectedConnections());
            }

            if ((excludeProps == null || !excludeProps.contains("TestConnectionsOnReserve")) && this.bean.isTestConnectionsOnReserveSet()) {
               copy.setTestConnectionsOnReserve(this.bean.isTestConnectionsOnReserve());
            }

            if ((excludeProps == null || !excludeProps.contains("WrapJdbc")) && this.bean.isWrapJdbcSet()) {
               copy.setWrapJdbc(this.bean.isWrapJdbc());
            }

            if ((excludeProps == null || !excludeProps.contains("WrapTypes")) && this.bean.isWrapTypesSet()) {
               copy.setWrapTypes(this.bean.isWrapTypes());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
