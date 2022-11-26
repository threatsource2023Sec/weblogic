package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SessionDescriptorBeanImpl extends AbstractDescriptorBean implements SessionDescriptorBean, Serializable {
   private int _AuthCookieIdLength;
   private int _CacheSize;
   private String _CookieComment;
   private String _CookieDomain;
   private boolean _CookieHttpOnly;
   private int _CookieMaxAgeSecs;
   private String _CookieName;
   private String _CookiePath;
   private boolean _CookieSecure;
   private boolean _CookiesEnabled;
   private boolean _DebugEnabled;
   private boolean _EncodeSessionIdInQueryParams;
   private boolean _HttpProxyCachingOfCookies;
   private String _Id;
   private int _IdLength;
   private boolean _InvalidateOnRelogin;
   private int _InvalidationIntervalSecs;
   private String _JdbcColumnNameMaxInactiveInterval;
   private int _MaxInMemorySessions;
   private int _MaxSavePostSize;
   private String _MonitoringAttributeName;
   private int _PersistentAsyncQueueTimeout;
   private String _PersistentDataSourceJNDIName;
   private int _PersistentSessionFlushInterval;
   private int _PersistentSessionFlushThreshold;
   private String _PersistentStoreCookieName;
   private String _PersistentStoreDir;
   private String _PersistentStorePool;
   private String _PersistentStoreTable;
   private String _PersistentStoreType;
   private int _SavePostTimeoutIntervalSecs;
   private int _SavePostTimeoutSecs;
   private boolean _SharingEnabled;
   private int _TimeoutSecs;
   private boolean _TrackingEnabled;
   private boolean _UrlRewritingEnabled;
   private static SchemaHelper2 _schemaHelper;

   public SessionDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public SessionDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SessionDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getTimeoutSecs() {
      return this._TimeoutSecs;
   }

   public boolean isTimeoutSecsInherited() {
      return false;
   }

   public boolean isTimeoutSecsSet() {
      return this._isSet(0);
   }

   public void setTimeoutSecs(int param0) {
      int _oldVal = this._TimeoutSecs;
      this._TimeoutSecs = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getInvalidationIntervalSecs() {
      return this._InvalidationIntervalSecs;
   }

   public boolean isInvalidationIntervalSecsInherited() {
      return false;
   }

   public boolean isInvalidationIntervalSecsSet() {
      return this._isSet(1);
   }

   public void setInvalidationIntervalSecs(int param0) {
      int _oldVal = this._InvalidationIntervalSecs;
      this._InvalidationIntervalSecs = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMaxSavePostSize() {
      return this._MaxSavePostSize;
   }

   public boolean isMaxSavePostSizeInherited() {
      return false;
   }

   public boolean isMaxSavePostSizeSet() {
      return this._isSet(2);
   }

   public void setMaxSavePostSize(int param0) {
      int _oldVal = this._MaxSavePostSize;
      this._MaxSavePostSize = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getSavePostTimeoutSecs() {
      return this._SavePostTimeoutSecs;
   }

   public boolean isSavePostTimeoutSecsInherited() {
      return false;
   }

   public boolean isSavePostTimeoutSecsSet() {
      return this._isSet(3);
   }

   public void setSavePostTimeoutSecs(int param0) {
      int _oldVal = this._SavePostTimeoutSecs;
      this._SavePostTimeoutSecs = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getSavePostTimeoutIntervalSecs() {
      return this._SavePostTimeoutIntervalSecs;
   }

   public boolean isSavePostTimeoutIntervalSecsInherited() {
      return false;
   }

   public boolean isSavePostTimeoutIntervalSecsSet() {
      return this._isSet(4);
   }

   public void setSavePostTimeoutIntervalSecs(int param0) {
      int _oldVal = this._SavePostTimeoutIntervalSecs;
      this._SavePostTimeoutIntervalSecs = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isDebugEnabled() {
      return this._DebugEnabled;
   }

   public boolean isDebugEnabledInherited() {
      return false;
   }

   public boolean isDebugEnabledSet() {
      return this._isSet(5);
   }

   public void setDebugEnabled(boolean param0) {
      boolean _oldVal = this._DebugEnabled;
      this._DebugEnabled = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getIdLength() {
      return this._IdLength;
   }

   public boolean isIdLengthInherited() {
      return false;
   }

   public boolean isIdLengthSet() {
      return this._isSet(6);
   }

   public void setIdLength(int param0) {
      LegalChecks.checkMin("IdLength", param0, 8);
      int _oldVal = this._IdLength;
      this._IdLength = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getAuthCookieIdLength() {
      return this._AuthCookieIdLength;
   }

   public boolean isAuthCookieIdLengthInherited() {
      return false;
   }

   public boolean isAuthCookieIdLengthSet() {
      return this._isSet(7);
   }

   public void setAuthCookieIdLength(int param0) {
      LegalChecks.checkMin("AuthCookieIdLength", param0, 8);
      int _oldVal = this._AuthCookieIdLength;
      this._AuthCookieIdLength = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean isTrackingEnabled() {
      return this._TrackingEnabled;
   }

   public boolean isTrackingEnabledInherited() {
      return false;
   }

   public boolean isTrackingEnabledSet() {
      return this._isSet(8);
   }

   public void setTrackingEnabled(boolean param0) {
      boolean _oldVal = this._TrackingEnabled;
      this._TrackingEnabled = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getCacheSize() {
      return this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return false;
   }

   public boolean isCacheSizeSet() {
      return this._isSet(9);
   }

   public void setCacheSize(int param0) {
      int _oldVal = this._CacheSize;
      this._CacheSize = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getMaxInMemorySessions() {
      return this._MaxInMemorySessions;
   }

   public boolean isMaxInMemorySessionsInherited() {
      return false;
   }

   public boolean isMaxInMemorySessionsSet() {
      return this._isSet(10);
   }

   public void setMaxInMemorySessions(int param0) {
      int _oldVal = this._MaxInMemorySessions;
      this._MaxInMemorySessions = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isCookiesEnabled() {
      return this._CookiesEnabled;
   }

   public boolean isCookiesEnabledInherited() {
      return false;
   }

   public boolean isCookiesEnabledSet() {
      return this._isSet(11);
   }

   public void setCookiesEnabled(boolean param0) {
      boolean _oldVal = this._CookiesEnabled;
      this._CookiesEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getCookieName() {
      return this._CookieName;
   }

   public boolean isCookieNameInherited() {
      return false;
   }

   public boolean isCookieNameSet() {
      return this._isSet(12);
   }

   public void setCookieName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookieName;
      this._CookieName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getCookiePath() {
      return this._CookiePath;
   }

   public boolean isCookiePathInherited() {
      return false;
   }

   public boolean isCookiePathSet() {
      return this._isSet(13);
   }

   public void setCookiePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookiePath;
      this._CookiePath = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getCookieDomain() {
      return this._CookieDomain;
   }

   public boolean isCookieDomainInherited() {
      return false;
   }

   public boolean isCookieDomainSet() {
      return this._isSet(14);
   }

   public void setCookieDomain(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookieDomain;
      this._CookieDomain = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getCookieComment() {
      return this._CookieComment;
   }

   public boolean isCookieCommentInherited() {
      return false;
   }

   public boolean isCookieCommentSet() {
      return this._isSet(15);
   }

   public void setCookieComment(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookieComment;
      this._CookieComment = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isCookieSecure() {
      return this._CookieSecure;
   }

   public boolean isCookieSecureInherited() {
      return false;
   }

   public boolean isCookieSecureSet() {
      return this._isSet(16);
   }

   public void setCookieSecure(boolean param0) {
      boolean _oldVal = this._CookieSecure;
      this._CookieSecure = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getCookieMaxAgeSecs() {
      return this._CookieMaxAgeSecs;
   }

   public boolean isCookieMaxAgeSecsInherited() {
      return false;
   }

   public boolean isCookieMaxAgeSecsSet() {
      return this._isSet(17);
   }

   public void setCookieMaxAgeSecs(int param0) {
      int _oldVal = this._CookieMaxAgeSecs;
      this._CookieMaxAgeSecs = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isCookieHttpOnly() {
      return this._CookieHttpOnly;
   }

   public boolean isCookieHttpOnlyInherited() {
      return false;
   }

   public boolean isCookieHttpOnlySet() {
      return this._isSet(18);
   }

   public void setCookieHttpOnly(boolean param0) {
      boolean _oldVal = this._CookieHttpOnly;
      this._CookieHttpOnly = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getPersistentStoreType() {
      return this._PersistentStoreType;
   }

   public boolean isPersistentStoreTypeInherited() {
      return false;
   }

   public boolean isPersistentStoreTypeSet() {
      return this._isSet(19);
   }

   public void setPersistentStoreType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"memory", "replicated", "replicated_if_clustered", "file", "jdbc", "cookie", "async-replicated", "async-replicated-if-clustered", "async-jdbc", "coherence-web"};
      param0 = LegalChecks.checkInEnum("PersistentStoreType", param0, _set);
      String _oldVal = this._PersistentStoreType;
      this._PersistentStoreType = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getPersistentStoreCookieName() {
      return this._PersistentStoreCookieName;
   }

   public boolean isPersistentStoreCookieNameInherited() {
      return false;
   }

   public boolean isPersistentStoreCookieNameSet() {
      return this._isSet(20);
   }

   public void setPersistentStoreCookieName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStoreCookieName;
      this._PersistentStoreCookieName = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getPersistentStoreDir() {
      return this._PersistentStoreDir;
   }

   public boolean isPersistentStoreDirInherited() {
      return false;
   }

   public boolean isPersistentStoreDirSet() {
      return this._isSet(21);
   }

   public void setPersistentStoreDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStoreDir;
      this._PersistentStoreDir = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getPersistentStorePool() {
      return this._PersistentStorePool;
   }

   public boolean isPersistentStorePoolInherited() {
      return false;
   }

   public boolean isPersistentStorePoolSet() {
      return this._isSet(22);
   }

   public void setPersistentStorePool(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStorePool;
      this._PersistentStorePool = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getPersistentDataSourceJNDIName() {
      return this._PersistentDataSourceJNDIName;
   }

   public boolean isPersistentDataSourceJNDINameInherited() {
      return false;
   }

   public boolean isPersistentDataSourceJNDINameSet() {
      return this._isSet(23);
   }

   public void setPersistentDataSourceJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentDataSourceJNDIName;
      this._PersistentDataSourceJNDIName = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getPersistentSessionFlushInterval() {
      return this._PersistentSessionFlushInterval;
   }

   public boolean isPersistentSessionFlushIntervalInherited() {
      return false;
   }

   public boolean isPersistentSessionFlushIntervalSet() {
      return this._isSet(24);
   }

   public void setPersistentSessionFlushInterval(int param0) {
      int _oldVal = this._PersistentSessionFlushInterval;
      this._PersistentSessionFlushInterval = param0;
      this._postSet(24, _oldVal, param0);
   }

   public int getPersistentSessionFlushThreshold() {
      return this._PersistentSessionFlushThreshold;
   }

   public boolean isPersistentSessionFlushThresholdInherited() {
      return false;
   }

   public boolean isPersistentSessionFlushThresholdSet() {
      return this._isSet(25);
   }

   public void setPersistentSessionFlushThreshold(int param0) {
      int _oldVal = this._PersistentSessionFlushThreshold;
      this._PersistentSessionFlushThreshold = param0;
      this._postSet(25, _oldVal, param0);
   }

   public int getPersistentAsyncQueueTimeout() {
      return this._PersistentAsyncQueueTimeout;
   }

   public boolean isPersistentAsyncQueueTimeoutInherited() {
      return false;
   }

   public boolean isPersistentAsyncQueueTimeoutSet() {
      return this._isSet(26);
   }

   public void setPersistentAsyncQueueTimeout(int param0) {
      int _oldVal = this._PersistentAsyncQueueTimeout;
      this._PersistentAsyncQueueTimeout = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getPersistentStoreTable() {
      return this._PersistentStoreTable;
   }

   public boolean isPersistentStoreTableInherited() {
      return false;
   }

   public boolean isPersistentStoreTableSet() {
      return this._isSet(27);
   }

   public void setPersistentStoreTable(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistentStoreTable;
      this._PersistentStoreTable = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getJdbcColumnNameMaxInactiveInterval() {
      return this._JdbcColumnNameMaxInactiveInterval;
   }

   public boolean isJdbcColumnNameMaxInactiveIntervalInherited() {
      return false;
   }

   public boolean isJdbcColumnNameMaxInactiveIntervalSet() {
      return this._isSet(28);
   }

   public void setJdbcColumnNameMaxInactiveInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JdbcColumnNameMaxInactiveInterval;
      this._JdbcColumnNameMaxInactiveInterval = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean isUrlRewritingEnabled() {
      return this._UrlRewritingEnabled;
   }

   public boolean isUrlRewritingEnabledInherited() {
      return false;
   }

   public boolean isUrlRewritingEnabledSet() {
      return this._isSet(29);
   }

   public void setUrlRewritingEnabled(boolean param0) {
      boolean _oldVal = this._UrlRewritingEnabled;
      this._UrlRewritingEnabled = param0;
      this._postSet(29, _oldVal, param0);
   }

   public boolean isHttpProxyCachingOfCookies() {
      return this._HttpProxyCachingOfCookies;
   }

   public boolean isHttpProxyCachingOfCookiesInherited() {
      return false;
   }

   public boolean isHttpProxyCachingOfCookiesSet() {
      return this._isSet(30);
   }

   public void setHttpProxyCachingOfCookies(boolean param0) {
      boolean _oldVal = this._HttpProxyCachingOfCookies;
      this._HttpProxyCachingOfCookies = param0;
      this._postSet(30, _oldVal, param0);
   }

   public boolean isEncodeSessionIdInQueryParams() {
      return this._EncodeSessionIdInQueryParams;
   }

   public boolean isEncodeSessionIdInQueryParamsInherited() {
      return false;
   }

   public boolean isEncodeSessionIdInQueryParamsSet() {
      return this._isSet(31);
   }

   public void setEncodeSessionIdInQueryParams(boolean param0) {
      boolean _oldVal = this._EncodeSessionIdInQueryParams;
      this._EncodeSessionIdInQueryParams = param0;
      this._postSet(31, _oldVal, param0);
   }

   public String getMonitoringAttributeName() {
      return !this._isSet(32) ? null : this._MonitoringAttributeName;
   }

   public boolean isMonitoringAttributeNameInherited() {
      return false;
   }

   public boolean isMonitoringAttributeNameSet() {
      return this._isSet(32);
   }

   public void setMonitoringAttributeName(String param0) {
      if (param0 == null) {
         this._unSet(32);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String _oldVal = this._MonitoringAttributeName;
         this._MonitoringAttributeName = param0;
         this._postSet(32, _oldVal, param0);
      }
   }

   public boolean isSharingEnabled() {
      return this._SharingEnabled;
   }

   public boolean isSharingEnabledInherited() {
      return false;
   }

   public boolean isSharingEnabledSet() {
      return this._isSet(33);
   }

   public void setSharingEnabled(boolean param0) {
      boolean _oldVal = this._SharingEnabled;
      this._SharingEnabled = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isInvalidateOnRelogin() {
      return this._InvalidateOnRelogin;
   }

   public boolean isInvalidateOnReloginInherited() {
      return false;
   }

   public boolean isInvalidateOnReloginSet() {
      return this._isSet(34);
   }

   public void setInvalidateOnRelogin(boolean param0) {
      boolean _oldVal = this._InvalidateOnRelogin;
      this._InvalidateOnRelogin = param0;
      this._postSet(34, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(35);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(35, _oldVal, param0);
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
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._AuthCookieIdLength = 20;
               if (initOne) {
                  break;
               }
            case 9:
               this._CacheSize = 1024;
               if (initOne) {
                  break;
               }
            case 15:
               this._CookieComment = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._CookieDomain = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._CookieMaxAgeSecs = -1;
               if (initOne) {
                  break;
               }
            case 12:
               this._CookieName = "JSESSIONID";
               if (initOne) {
                  break;
               }
            case 13:
               this._CookiePath = "/";
               if (initOne) {
                  break;
               }
            case 35:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._IdLength = 52;
               if (initOne) {
                  break;
               }
            case 1:
               this._InvalidationIntervalSecs = 60;
               if (initOne) {
                  break;
               }
            case 28:
               this._JdbcColumnNameMaxInactiveInterval = "wl_max_inactive_interval";
               if (initOne) {
                  break;
               }
            case 10:
               this._MaxInMemorySessions = -1;
               if (initOne) {
                  break;
               }
            case 2:
               this._MaxSavePostSize = 4096;
               if (initOne) {
                  break;
               }
            case 32:
               this._MonitoringAttributeName = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._PersistentAsyncQueueTimeout = 30;
               if (initOne) {
                  break;
               }
            case 23:
               this._PersistentDataSourceJNDIName = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._PersistentSessionFlushInterval = 180;
               if (initOne) {
                  break;
               }
            case 25:
               this._PersistentSessionFlushThreshold = 100;
               if (initOne) {
                  break;
               }
            case 20:
               this._PersistentStoreCookieName = "WLCOOKIE";
               if (initOne) {
                  break;
               }
            case 21:
               this._PersistentStoreDir = "session_db";
               if (initOne) {
                  break;
               }
            case 22:
               this._PersistentStorePool = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._PersistentStoreTable = "wl_servlet_sessions";
               if (initOne) {
                  break;
               }
            case 19:
               this._PersistentStoreType = "memory";
               if (initOne) {
                  break;
               }
            case 4:
               this._SavePostTimeoutIntervalSecs = 20;
               if (initOne) {
                  break;
               }
            case 3:
               this._SavePostTimeoutSecs = 40;
               if (initOne) {
                  break;
               }
            case 0:
               this._TimeoutSecs = 3600;
               if (initOne) {
                  break;
               }
            case 18:
               this._CookieHttpOnly = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._CookieSecure = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._CookiesEnabled = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._DebugEnabled = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._EncodeSessionIdInQueryParams = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._HttpProxyCachingOfCookies = true;
               if (initOne) {
                  break;
               }
            case 34:
               this._InvalidateOnRelogin = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._SharingEnabled = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._TrackingEnabled = true;
               if (initOne) {
                  break;
               }
            case 29:
               this._UrlRewritingEnabled = false;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 35;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 17:
            case 23:
            case 24:
            case 27:
            case 35:
            case 36:
            case 37:
            default:
               break;
            case 9:
               if (s.equals("id-length")) {
                  return 6;
               }
               break;
            case 10:
               if (s.equals("cache-size")) {
                  return 9;
               }
               break;
            case 11:
               if (s.equals("cookie-name")) {
                  return 12;
               }

               if (s.equals("cookie-path")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("timeout-secs")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("cookie-domain")) {
                  return 14;
               }

               if (s.equals("cookie-secure")) {
                  return 16;
               }

               if (s.equals("debug-enabled")) {
                  return 5;
               }
               break;
            case 14:
               if (s.equals("cookie-comment")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("cookies-enabled")) {
                  return 11;
               }

               if (s.equals("sharing-enabled")) {
                  return 33;
               }
               break;
            case 16:
               if (s.equals("cookie-http-only")) {
                  return 18;
               }

               if (s.equals("tracking-enabled")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("max-save-post-size")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("cookie-max-age-secs")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("persistent-store-dir")) {
                  return 21;
               }
               break;
            case 21:
               if (s.equals("auth-cookie-id-length")) {
                  return 7;
               }

               if (s.equals("persistent-store-pool")) {
                  return 22;
               }

               if (s.equals("persistent-store-type")) {
                  return 19;
               }

               if (s.equals("invalidate-on-relogin")) {
                  return 34;
               }

               if (s.equals("url-rewriting-enabled")) {
                  return 29;
               }
               break;
            case 22:
               if (s.equals("max-in-memory-sessions")) {
                  return 10;
               }

               if (s.equals("persistent-store-table")) {
                  return 27;
               }

               if (s.equals("save-post-timeout-secs")) {
                  return 3;
               }
               break;
            case 25:
               if (s.equals("monitoring-attribute-name")) {
                  return 32;
               }
               break;
            case 26:
               if (s.equals("invalidation-interval-secs")) {
                  return 1;
               }
               break;
            case 28:
               if (s.equals("persistent-store-cookie-name")) {
                  return 20;
               }
               break;
            case 29:
               if (s.equals("http-proxy-caching-of-cookies")) {
                  return 30;
               }
               break;
            case 30:
               if (s.equals("persistent-async-queue-timeout")) {
                  return 26;
               }
               break;
            case 31:
               if (s.equals("save-post-timeout-interval-secs")) {
                  return 4;
               }
               break;
            case 32:
               if (s.equals("persistent-data-source-jndi-name")) {
                  return 23;
               }
               break;
            case 33:
               if (s.equals("persistent-session-flush-interval")) {
                  return 24;
               }

               if (s.equals("encode-session-id-in-query-params")) {
                  return 31;
               }
               break;
            case 34:
               if (s.equals("persistent-session-flush-threshold")) {
                  return 25;
               }
               break;
            case 38:
               if (s.equals("jdbc-column-name-max-inactive-interval")) {
                  return 28;
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
               return "timeout-secs";
            case 1:
               return "invalidation-interval-secs";
            case 2:
               return "max-save-post-size";
            case 3:
               return "save-post-timeout-secs";
            case 4:
               return "save-post-timeout-interval-secs";
            case 5:
               return "debug-enabled";
            case 6:
               return "id-length";
            case 7:
               return "auth-cookie-id-length";
            case 8:
               return "tracking-enabled";
            case 9:
               return "cache-size";
            case 10:
               return "max-in-memory-sessions";
            case 11:
               return "cookies-enabled";
            case 12:
               return "cookie-name";
            case 13:
               return "cookie-path";
            case 14:
               return "cookie-domain";
            case 15:
               return "cookie-comment";
            case 16:
               return "cookie-secure";
            case 17:
               return "cookie-max-age-secs";
            case 18:
               return "cookie-http-only";
            case 19:
               return "persistent-store-type";
            case 20:
               return "persistent-store-cookie-name";
            case 21:
               return "persistent-store-dir";
            case 22:
               return "persistent-store-pool";
            case 23:
               return "persistent-data-source-jndi-name";
            case 24:
               return "persistent-session-flush-interval";
            case 25:
               return "persistent-session-flush-threshold";
            case 26:
               return "persistent-async-queue-timeout";
            case 27:
               return "persistent-store-table";
            case 28:
               return "jdbc-column-name-max-inactive-interval";
            case 29:
               return "url-rewriting-enabled";
            case 30:
               return "http-proxy-caching-of-cookies";
            case 31:
               return "encode-session-id-in-query-params";
            case 32:
               return "monitoring-attribute-name";
            case 33:
               return "sharing-enabled";
            case 34:
               return "invalidate-on-relogin";
            case 35:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 35:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
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
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               return super.isConfigurable(propIndex);
            case 10:
               return true;
            case 17:
               return true;
            case 32:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SessionDescriptorBeanImpl bean;

      protected Helper(SessionDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TimeoutSecs";
            case 1:
               return "InvalidationIntervalSecs";
            case 2:
               return "MaxSavePostSize";
            case 3:
               return "SavePostTimeoutSecs";
            case 4:
               return "SavePostTimeoutIntervalSecs";
            case 5:
               return "DebugEnabled";
            case 6:
               return "IdLength";
            case 7:
               return "AuthCookieIdLength";
            case 8:
               return "TrackingEnabled";
            case 9:
               return "CacheSize";
            case 10:
               return "MaxInMemorySessions";
            case 11:
               return "CookiesEnabled";
            case 12:
               return "CookieName";
            case 13:
               return "CookiePath";
            case 14:
               return "CookieDomain";
            case 15:
               return "CookieComment";
            case 16:
               return "CookieSecure";
            case 17:
               return "CookieMaxAgeSecs";
            case 18:
               return "CookieHttpOnly";
            case 19:
               return "PersistentStoreType";
            case 20:
               return "PersistentStoreCookieName";
            case 21:
               return "PersistentStoreDir";
            case 22:
               return "PersistentStorePool";
            case 23:
               return "PersistentDataSourceJNDIName";
            case 24:
               return "PersistentSessionFlushInterval";
            case 25:
               return "PersistentSessionFlushThreshold";
            case 26:
               return "PersistentAsyncQueueTimeout";
            case 27:
               return "PersistentStoreTable";
            case 28:
               return "JdbcColumnNameMaxInactiveInterval";
            case 29:
               return "UrlRewritingEnabled";
            case 30:
               return "HttpProxyCachingOfCookies";
            case 31:
               return "EncodeSessionIdInQueryParams";
            case 32:
               return "MonitoringAttributeName";
            case 33:
               return "SharingEnabled";
            case 34:
               return "InvalidateOnRelogin";
            case 35:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthCookieIdLength")) {
            return 7;
         } else if (propName.equals("CacheSize")) {
            return 9;
         } else if (propName.equals("CookieComment")) {
            return 15;
         } else if (propName.equals("CookieDomain")) {
            return 14;
         } else if (propName.equals("CookieMaxAgeSecs")) {
            return 17;
         } else if (propName.equals("CookieName")) {
            return 12;
         } else if (propName.equals("CookiePath")) {
            return 13;
         } else if (propName.equals("Id")) {
            return 35;
         } else if (propName.equals("IdLength")) {
            return 6;
         } else if (propName.equals("InvalidationIntervalSecs")) {
            return 1;
         } else if (propName.equals("JdbcColumnNameMaxInactiveInterval")) {
            return 28;
         } else if (propName.equals("MaxInMemorySessions")) {
            return 10;
         } else if (propName.equals("MaxSavePostSize")) {
            return 2;
         } else if (propName.equals("MonitoringAttributeName")) {
            return 32;
         } else if (propName.equals("PersistentAsyncQueueTimeout")) {
            return 26;
         } else if (propName.equals("PersistentDataSourceJNDIName")) {
            return 23;
         } else if (propName.equals("PersistentSessionFlushInterval")) {
            return 24;
         } else if (propName.equals("PersistentSessionFlushThreshold")) {
            return 25;
         } else if (propName.equals("PersistentStoreCookieName")) {
            return 20;
         } else if (propName.equals("PersistentStoreDir")) {
            return 21;
         } else if (propName.equals("PersistentStorePool")) {
            return 22;
         } else if (propName.equals("PersistentStoreTable")) {
            return 27;
         } else if (propName.equals("PersistentStoreType")) {
            return 19;
         } else if (propName.equals("SavePostTimeoutIntervalSecs")) {
            return 4;
         } else if (propName.equals("SavePostTimeoutSecs")) {
            return 3;
         } else if (propName.equals("TimeoutSecs")) {
            return 0;
         } else if (propName.equals("CookieHttpOnly")) {
            return 18;
         } else if (propName.equals("CookieSecure")) {
            return 16;
         } else if (propName.equals("CookiesEnabled")) {
            return 11;
         } else if (propName.equals("DebugEnabled")) {
            return 5;
         } else if (propName.equals("EncodeSessionIdInQueryParams")) {
            return 31;
         } else if (propName.equals("HttpProxyCachingOfCookies")) {
            return 30;
         } else if (propName.equals("InvalidateOnRelogin")) {
            return 34;
         } else if (propName.equals("SharingEnabled")) {
            return 33;
         } else if (propName.equals("TrackingEnabled")) {
            return 8;
         } else {
            return propName.equals("UrlRewritingEnabled") ? 29 : super.getPropertyIndex(propName);
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
            if (this.bean.isAuthCookieIdLengthSet()) {
               buf.append("AuthCookieIdLength");
               buf.append(String.valueOf(this.bean.getAuthCookieIdLength()));
            }

            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isCookieCommentSet()) {
               buf.append("CookieComment");
               buf.append(String.valueOf(this.bean.getCookieComment()));
            }

            if (this.bean.isCookieDomainSet()) {
               buf.append("CookieDomain");
               buf.append(String.valueOf(this.bean.getCookieDomain()));
            }

            if (this.bean.isCookieMaxAgeSecsSet()) {
               buf.append("CookieMaxAgeSecs");
               buf.append(String.valueOf(this.bean.getCookieMaxAgeSecs()));
            }

            if (this.bean.isCookieNameSet()) {
               buf.append("CookieName");
               buf.append(String.valueOf(this.bean.getCookieName()));
            }

            if (this.bean.isCookiePathSet()) {
               buf.append("CookiePath");
               buf.append(String.valueOf(this.bean.getCookiePath()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIdLengthSet()) {
               buf.append("IdLength");
               buf.append(String.valueOf(this.bean.getIdLength()));
            }

            if (this.bean.isInvalidationIntervalSecsSet()) {
               buf.append("InvalidationIntervalSecs");
               buf.append(String.valueOf(this.bean.getInvalidationIntervalSecs()));
            }

            if (this.bean.isJdbcColumnNameMaxInactiveIntervalSet()) {
               buf.append("JdbcColumnNameMaxInactiveInterval");
               buf.append(String.valueOf(this.bean.getJdbcColumnNameMaxInactiveInterval()));
            }

            if (this.bean.isMaxInMemorySessionsSet()) {
               buf.append("MaxInMemorySessions");
               buf.append(String.valueOf(this.bean.getMaxInMemorySessions()));
            }

            if (this.bean.isMaxSavePostSizeSet()) {
               buf.append("MaxSavePostSize");
               buf.append(String.valueOf(this.bean.getMaxSavePostSize()));
            }

            if (this.bean.isMonitoringAttributeNameSet()) {
               buf.append("MonitoringAttributeName");
               buf.append(String.valueOf(this.bean.getMonitoringAttributeName()));
            }

            if (this.bean.isPersistentAsyncQueueTimeoutSet()) {
               buf.append("PersistentAsyncQueueTimeout");
               buf.append(String.valueOf(this.bean.getPersistentAsyncQueueTimeout()));
            }

            if (this.bean.isPersistentDataSourceJNDINameSet()) {
               buf.append("PersistentDataSourceJNDIName");
               buf.append(String.valueOf(this.bean.getPersistentDataSourceJNDIName()));
            }

            if (this.bean.isPersistentSessionFlushIntervalSet()) {
               buf.append("PersistentSessionFlushInterval");
               buf.append(String.valueOf(this.bean.getPersistentSessionFlushInterval()));
            }

            if (this.bean.isPersistentSessionFlushThresholdSet()) {
               buf.append("PersistentSessionFlushThreshold");
               buf.append(String.valueOf(this.bean.getPersistentSessionFlushThreshold()));
            }

            if (this.bean.isPersistentStoreCookieNameSet()) {
               buf.append("PersistentStoreCookieName");
               buf.append(String.valueOf(this.bean.getPersistentStoreCookieName()));
            }

            if (this.bean.isPersistentStoreDirSet()) {
               buf.append("PersistentStoreDir");
               buf.append(String.valueOf(this.bean.getPersistentStoreDir()));
            }

            if (this.bean.isPersistentStorePoolSet()) {
               buf.append("PersistentStorePool");
               buf.append(String.valueOf(this.bean.getPersistentStorePool()));
            }

            if (this.bean.isPersistentStoreTableSet()) {
               buf.append("PersistentStoreTable");
               buf.append(String.valueOf(this.bean.getPersistentStoreTable()));
            }

            if (this.bean.isPersistentStoreTypeSet()) {
               buf.append("PersistentStoreType");
               buf.append(String.valueOf(this.bean.getPersistentStoreType()));
            }

            if (this.bean.isSavePostTimeoutIntervalSecsSet()) {
               buf.append("SavePostTimeoutIntervalSecs");
               buf.append(String.valueOf(this.bean.getSavePostTimeoutIntervalSecs()));
            }

            if (this.bean.isSavePostTimeoutSecsSet()) {
               buf.append("SavePostTimeoutSecs");
               buf.append(String.valueOf(this.bean.getSavePostTimeoutSecs()));
            }

            if (this.bean.isTimeoutSecsSet()) {
               buf.append("TimeoutSecs");
               buf.append(String.valueOf(this.bean.getTimeoutSecs()));
            }

            if (this.bean.isCookieHttpOnlySet()) {
               buf.append("CookieHttpOnly");
               buf.append(String.valueOf(this.bean.isCookieHttpOnly()));
            }

            if (this.bean.isCookieSecureSet()) {
               buf.append("CookieSecure");
               buf.append(String.valueOf(this.bean.isCookieSecure()));
            }

            if (this.bean.isCookiesEnabledSet()) {
               buf.append("CookiesEnabled");
               buf.append(String.valueOf(this.bean.isCookiesEnabled()));
            }

            if (this.bean.isDebugEnabledSet()) {
               buf.append("DebugEnabled");
               buf.append(String.valueOf(this.bean.isDebugEnabled()));
            }

            if (this.bean.isEncodeSessionIdInQueryParamsSet()) {
               buf.append("EncodeSessionIdInQueryParams");
               buf.append(String.valueOf(this.bean.isEncodeSessionIdInQueryParams()));
            }

            if (this.bean.isHttpProxyCachingOfCookiesSet()) {
               buf.append("HttpProxyCachingOfCookies");
               buf.append(String.valueOf(this.bean.isHttpProxyCachingOfCookies()));
            }

            if (this.bean.isInvalidateOnReloginSet()) {
               buf.append("InvalidateOnRelogin");
               buf.append(String.valueOf(this.bean.isInvalidateOnRelogin()));
            }

            if (this.bean.isSharingEnabledSet()) {
               buf.append("SharingEnabled");
               buf.append(String.valueOf(this.bean.isSharingEnabled()));
            }

            if (this.bean.isTrackingEnabledSet()) {
               buf.append("TrackingEnabled");
               buf.append(String.valueOf(this.bean.isTrackingEnabled()));
            }

            if (this.bean.isUrlRewritingEnabledSet()) {
               buf.append("UrlRewritingEnabled");
               buf.append(String.valueOf(this.bean.isUrlRewritingEnabled()));
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
            SessionDescriptorBeanImpl otherTyped = (SessionDescriptorBeanImpl)other;
            this.computeDiff("AuthCookieIdLength", this.bean.getAuthCookieIdLength(), otherTyped.getAuthCookieIdLength(), false);
            this.computeDiff("CacheSize", this.bean.getCacheSize(), otherTyped.getCacheSize(), false);
            this.computeDiff("CookieComment", this.bean.getCookieComment(), otherTyped.getCookieComment(), false);
            this.computeDiff("CookieDomain", this.bean.getCookieDomain(), otherTyped.getCookieDomain(), false);
            this.computeDiff("CookieMaxAgeSecs", this.bean.getCookieMaxAgeSecs(), otherTyped.getCookieMaxAgeSecs(), true);
            this.computeDiff("CookieName", this.bean.getCookieName(), otherTyped.getCookieName(), false);
            this.computeDiff("CookiePath", this.bean.getCookiePath(), otherTyped.getCookiePath(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IdLength", this.bean.getIdLength(), otherTyped.getIdLength(), false);
            this.computeDiff("InvalidationIntervalSecs", this.bean.getInvalidationIntervalSecs(), otherTyped.getInvalidationIntervalSecs(), true);
            this.computeDiff("JdbcColumnNameMaxInactiveInterval", this.bean.getJdbcColumnNameMaxInactiveInterval(), otherTyped.getJdbcColumnNameMaxInactiveInterval(), false);
            this.computeDiff("MaxInMemorySessions", this.bean.getMaxInMemorySessions(), otherTyped.getMaxInMemorySessions(), true);
            this.computeDiff("MaxSavePostSize", this.bean.getMaxSavePostSize(), otherTyped.getMaxSavePostSize(), true);
            this.computeDiff("MonitoringAttributeName", this.bean.getMonitoringAttributeName(), otherTyped.getMonitoringAttributeName(), true);
            this.computeDiff("PersistentAsyncQueueTimeout", this.bean.getPersistentAsyncQueueTimeout(), otherTyped.getPersistentAsyncQueueTimeout(), false);
            this.computeDiff("PersistentDataSourceJNDIName", this.bean.getPersistentDataSourceJNDIName(), otherTyped.getPersistentDataSourceJNDIName(), false);
            this.computeDiff("PersistentSessionFlushInterval", this.bean.getPersistentSessionFlushInterval(), otherTyped.getPersistentSessionFlushInterval(), false);
            this.computeDiff("PersistentSessionFlushThreshold", this.bean.getPersistentSessionFlushThreshold(), otherTyped.getPersistentSessionFlushThreshold(), false);
            this.computeDiff("PersistentStoreCookieName", this.bean.getPersistentStoreCookieName(), otherTyped.getPersistentStoreCookieName(), false);
            this.computeDiff("PersistentStoreDir", this.bean.getPersistentStoreDir(), otherTyped.getPersistentStoreDir(), false);
            this.computeDiff("PersistentStorePool", this.bean.getPersistentStorePool(), otherTyped.getPersistentStorePool(), false);
            this.computeDiff("PersistentStoreTable", this.bean.getPersistentStoreTable(), otherTyped.getPersistentStoreTable(), false);
            this.computeDiff("PersistentStoreType", this.bean.getPersistentStoreType(), otherTyped.getPersistentStoreType(), false);
            this.computeDiff("SavePostTimeoutIntervalSecs", this.bean.getSavePostTimeoutIntervalSecs(), otherTyped.getSavePostTimeoutIntervalSecs(), true);
            this.computeDiff("SavePostTimeoutSecs", this.bean.getSavePostTimeoutSecs(), otherTyped.getSavePostTimeoutSecs(), true);
            this.computeDiff("TimeoutSecs", this.bean.getTimeoutSecs(), otherTyped.getTimeoutSecs(), true);
            this.computeDiff("CookieHttpOnly", this.bean.isCookieHttpOnly(), otherTyped.isCookieHttpOnly(), false);
            this.computeDiff("CookieSecure", this.bean.isCookieSecure(), otherTyped.isCookieSecure(), false);
            this.computeDiff("CookiesEnabled", this.bean.isCookiesEnabled(), otherTyped.isCookiesEnabled(), false);
            this.computeDiff("DebugEnabled", this.bean.isDebugEnabled(), otherTyped.isDebugEnabled(), true);
            this.computeDiff("EncodeSessionIdInQueryParams", this.bean.isEncodeSessionIdInQueryParams(), otherTyped.isEncodeSessionIdInQueryParams(), false);
            this.computeDiff("HttpProxyCachingOfCookies", this.bean.isHttpProxyCachingOfCookies(), otherTyped.isHttpProxyCachingOfCookies(), false);
            this.computeDiff("InvalidateOnRelogin", this.bean.isInvalidateOnRelogin(), otherTyped.isInvalidateOnRelogin(), false);
            this.computeDiff("SharingEnabled", this.bean.isSharingEnabled(), otherTyped.isSharingEnabled(), false);
            this.computeDiff("TrackingEnabled", this.bean.isTrackingEnabled(), otherTyped.isTrackingEnabled(), false);
            this.computeDiff("UrlRewritingEnabled", this.bean.isUrlRewritingEnabled(), otherTyped.isUrlRewritingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SessionDescriptorBeanImpl original = (SessionDescriptorBeanImpl)event.getSourceBean();
            SessionDescriptorBeanImpl proposed = (SessionDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthCookieIdLength")) {
                  original.setAuthCookieIdLength(proposed.getAuthCookieIdLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("CacheSize")) {
                  original.setCacheSize(proposed.getCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("CookieComment")) {
                  original.setCookieComment(proposed.getCookieComment());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("CookieDomain")) {
                  original.setCookieDomain(proposed.getCookieDomain());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CookieMaxAgeSecs")) {
                  original.setCookieMaxAgeSecs(proposed.getCookieMaxAgeSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("CookieName")) {
                  original.setCookieName(proposed.getCookieName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CookiePath")) {
                  original.setCookiePath(proposed.getCookiePath());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("IdLength")) {
                  original.setIdLength(proposed.getIdLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("InvalidationIntervalSecs")) {
                  original.setInvalidationIntervalSecs(proposed.getInvalidationIntervalSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("JdbcColumnNameMaxInactiveInterval")) {
                  original.setJdbcColumnNameMaxInactiveInterval(proposed.getJdbcColumnNameMaxInactiveInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("MaxInMemorySessions")) {
                  original.setMaxInMemorySessions(proposed.getMaxInMemorySessions());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MaxSavePostSize")) {
                  original.setMaxSavePostSize(proposed.getMaxSavePostSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MonitoringAttributeName")) {
                  original.setMonitoringAttributeName(proposed.getMonitoringAttributeName());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("PersistentAsyncQueueTimeout")) {
                  original.setPersistentAsyncQueueTimeout(proposed.getPersistentAsyncQueueTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("PersistentDataSourceJNDIName")) {
                  original.setPersistentDataSourceJNDIName(proposed.getPersistentDataSourceJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("PersistentSessionFlushInterval")) {
                  original.setPersistentSessionFlushInterval(proposed.getPersistentSessionFlushInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("PersistentSessionFlushThreshold")) {
                  original.setPersistentSessionFlushThreshold(proposed.getPersistentSessionFlushThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("PersistentStoreCookieName")) {
                  original.setPersistentStoreCookieName(proposed.getPersistentStoreCookieName());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("PersistentStoreDir")) {
                  original.setPersistentStoreDir(proposed.getPersistentStoreDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("PersistentStorePool")) {
                  original.setPersistentStorePool(proposed.getPersistentStorePool());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("PersistentStoreTable")) {
                  original.setPersistentStoreTable(proposed.getPersistentStoreTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("PersistentStoreType")) {
                  original.setPersistentStoreType(proposed.getPersistentStoreType());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("SavePostTimeoutIntervalSecs")) {
                  original.setSavePostTimeoutIntervalSecs(proposed.getSavePostTimeoutIntervalSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SavePostTimeoutSecs")) {
                  original.setSavePostTimeoutSecs(proposed.getSavePostTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TimeoutSecs")) {
                  original.setTimeoutSecs(proposed.getTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("CookieHttpOnly")) {
                  original.setCookieHttpOnly(proposed.isCookieHttpOnly());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CookieSecure")) {
                  original.setCookieSecure(proposed.isCookieSecure());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("CookiesEnabled")) {
                  original.setCookiesEnabled(proposed.isCookiesEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DebugEnabled")) {
                  original.setDebugEnabled(proposed.isDebugEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("EncodeSessionIdInQueryParams")) {
                  original.setEncodeSessionIdInQueryParams(proposed.isEncodeSessionIdInQueryParams());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("HttpProxyCachingOfCookies")) {
                  original.setHttpProxyCachingOfCookies(proposed.isHttpProxyCachingOfCookies());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("InvalidateOnRelogin")) {
                  original.setInvalidateOnRelogin(proposed.isInvalidateOnRelogin());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SharingEnabled")) {
                  original.setSharingEnabled(proposed.isSharingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("TrackingEnabled")) {
                  original.setTrackingEnabled(proposed.isTrackingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("UrlRewritingEnabled")) {
                  original.setUrlRewritingEnabled(proposed.isUrlRewritingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
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
            SessionDescriptorBeanImpl copy = (SessionDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthCookieIdLength")) && this.bean.isAuthCookieIdLengthSet()) {
               copy.setAuthCookieIdLength(this.bean.getAuthCookieIdLength());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheSize")) && this.bean.isCacheSizeSet()) {
               copy.setCacheSize(this.bean.getCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieComment")) && this.bean.isCookieCommentSet()) {
               copy.setCookieComment(this.bean.getCookieComment());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieDomain")) && this.bean.isCookieDomainSet()) {
               copy.setCookieDomain(this.bean.getCookieDomain());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieMaxAgeSecs")) && this.bean.isCookieMaxAgeSecsSet()) {
               copy.setCookieMaxAgeSecs(this.bean.getCookieMaxAgeSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieName")) && this.bean.isCookieNameSet()) {
               copy.setCookieName(this.bean.getCookieName());
            }

            if ((excludeProps == null || !excludeProps.contains("CookiePath")) && this.bean.isCookiePathSet()) {
               copy.setCookiePath(this.bean.getCookiePath());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IdLength")) && this.bean.isIdLengthSet()) {
               copy.setIdLength(this.bean.getIdLength());
            }

            if ((excludeProps == null || !excludeProps.contains("InvalidationIntervalSecs")) && this.bean.isInvalidationIntervalSecsSet()) {
               copy.setInvalidationIntervalSecs(this.bean.getInvalidationIntervalSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("JdbcColumnNameMaxInactiveInterval")) && this.bean.isJdbcColumnNameMaxInactiveIntervalSet()) {
               copy.setJdbcColumnNameMaxInactiveInterval(this.bean.getJdbcColumnNameMaxInactiveInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxInMemorySessions")) && this.bean.isMaxInMemorySessionsSet()) {
               copy.setMaxInMemorySessions(this.bean.getMaxInMemorySessions());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxSavePostSize")) && this.bean.isMaxSavePostSizeSet()) {
               copy.setMaxSavePostSize(this.bean.getMaxSavePostSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MonitoringAttributeName")) && this.bean.isMonitoringAttributeNameSet()) {
               copy.setMonitoringAttributeName(this.bean.getMonitoringAttributeName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentAsyncQueueTimeout")) && this.bean.isPersistentAsyncQueueTimeoutSet()) {
               copy.setPersistentAsyncQueueTimeout(this.bean.getPersistentAsyncQueueTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentDataSourceJNDIName")) && this.bean.isPersistentDataSourceJNDINameSet()) {
               copy.setPersistentDataSourceJNDIName(this.bean.getPersistentDataSourceJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentSessionFlushInterval")) && this.bean.isPersistentSessionFlushIntervalSet()) {
               copy.setPersistentSessionFlushInterval(this.bean.getPersistentSessionFlushInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentSessionFlushThreshold")) && this.bean.isPersistentSessionFlushThresholdSet()) {
               copy.setPersistentSessionFlushThreshold(this.bean.getPersistentSessionFlushThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStoreCookieName")) && this.bean.isPersistentStoreCookieNameSet()) {
               copy.setPersistentStoreCookieName(this.bean.getPersistentStoreCookieName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStoreDir")) && this.bean.isPersistentStoreDirSet()) {
               copy.setPersistentStoreDir(this.bean.getPersistentStoreDir());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStorePool")) && this.bean.isPersistentStorePoolSet()) {
               copy.setPersistentStorePool(this.bean.getPersistentStorePool());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStoreTable")) && this.bean.isPersistentStoreTableSet()) {
               copy.setPersistentStoreTable(this.bean.getPersistentStoreTable());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentStoreType")) && this.bean.isPersistentStoreTypeSet()) {
               copy.setPersistentStoreType(this.bean.getPersistentStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("SavePostTimeoutIntervalSecs")) && this.bean.isSavePostTimeoutIntervalSecsSet()) {
               copy.setSavePostTimeoutIntervalSecs(this.bean.getSavePostTimeoutIntervalSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("SavePostTimeoutSecs")) && this.bean.isSavePostTimeoutSecsSet()) {
               copy.setSavePostTimeoutSecs(this.bean.getSavePostTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutSecs")) && this.bean.isTimeoutSecsSet()) {
               copy.setTimeoutSecs(this.bean.getTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieHttpOnly")) && this.bean.isCookieHttpOnlySet()) {
               copy.setCookieHttpOnly(this.bean.isCookieHttpOnly());
            }

            if ((excludeProps == null || !excludeProps.contains("CookieSecure")) && this.bean.isCookieSecureSet()) {
               copy.setCookieSecure(this.bean.isCookieSecure());
            }

            if ((excludeProps == null || !excludeProps.contains("CookiesEnabled")) && this.bean.isCookiesEnabledSet()) {
               copy.setCookiesEnabled(this.bean.isCookiesEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugEnabled")) && this.bean.isDebugEnabledSet()) {
               copy.setDebugEnabled(this.bean.isDebugEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("EncodeSessionIdInQueryParams")) && this.bean.isEncodeSessionIdInQueryParamsSet()) {
               copy.setEncodeSessionIdInQueryParams(this.bean.isEncodeSessionIdInQueryParams());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpProxyCachingOfCookies")) && this.bean.isHttpProxyCachingOfCookiesSet()) {
               copy.setHttpProxyCachingOfCookies(this.bean.isHttpProxyCachingOfCookies());
            }

            if ((excludeProps == null || !excludeProps.contains("InvalidateOnRelogin")) && this.bean.isInvalidateOnReloginSet()) {
               copy.setInvalidateOnRelogin(this.bean.isInvalidateOnRelogin());
            }

            if ((excludeProps == null || !excludeProps.contains("SharingEnabled")) && this.bean.isSharingEnabledSet()) {
               copy.setSharingEnabled(this.bean.isSharingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TrackingEnabled")) && this.bean.isTrackingEnabledSet()) {
               copy.setTrackingEnabled(this.bean.isTrackingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UrlRewritingEnabled")) && this.bean.isUrlRewritingEnabledSet()) {
               copy.setUrlRewritingEnabled(this.bean.isUrlRewritingEnabled());
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
