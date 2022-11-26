package weblogic.j2ee.descriptor.wl;

public interface SessionDescriptorBean {
   int getTimeoutSecs();

   void setTimeoutSecs(int var1);

   boolean isTimeoutSecsSet();

   int getInvalidationIntervalSecs();

   void setInvalidationIntervalSecs(int var1);

   boolean isInvalidationIntervalSecsSet();

   int getMaxSavePostSize();

   void setMaxSavePostSize(int var1);

   boolean isMaxSavePostSizeSet();

   int getSavePostTimeoutSecs();

   void setSavePostTimeoutSecs(int var1);

   boolean isSavePostTimeoutSecsSet();

   int getSavePostTimeoutIntervalSecs();

   void setSavePostTimeoutIntervalSecs(int var1);

   boolean isSavePostTimeoutIntervalSecsSet();

   boolean isDebugEnabled();

   void setDebugEnabled(boolean var1);

   boolean isDebugEnabledSet();

   int getIdLength();

   void setIdLength(int var1);

   boolean isIdLengthSet();

   int getAuthCookieIdLength();

   void setAuthCookieIdLength(int var1);

   boolean isAuthCookieIdLengthSet();

   boolean isTrackingEnabled();

   void setTrackingEnabled(boolean var1);

   boolean isTrackingEnabledSet();

   int getCacheSize();

   void setCacheSize(int var1);

   boolean isCacheSizeSet();

   int getMaxInMemorySessions();

   void setMaxInMemorySessions(int var1);

   boolean isMaxInMemorySessionsSet();

   boolean isCookiesEnabled();

   void setCookiesEnabled(boolean var1);

   boolean isCookiesEnabledSet();

   String getCookieName();

   void setCookieName(String var1);

   boolean isCookieNameSet();

   String getCookiePath();

   void setCookiePath(String var1);

   boolean isCookiePathSet();

   String getCookieDomain();

   void setCookieDomain(String var1);

   boolean isCookieDomainSet();

   String getCookieComment();

   void setCookieComment(String var1);

   boolean isCookieCommentSet();

   boolean isCookieSecure();

   void setCookieSecure(boolean var1);

   boolean isCookieSecureSet();

   int getCookieMaxAgeSecs();

   void setCookieMaxAgeSecs(int var1);

   boolean isCookieMaxAgeSecsSet();

   boolean isCookieHttpOnly();

   void setCookieHttpOnly(boolean var1);

   boolean isCookieHttpOnlySet();

   String getPersistentStoreType();

   void setPersistentStoreType(String var1);

   boolean isPersistentStoreTypeSet();

   String getPersistentStoreCookieName();

   void setPersistentStoreCookieName(String var1);

   boolean isPersistentStoreCookieNameSet();

   String getPersistentStoreDir();

   void setPersistentStoreDir(String var1);

   boolean isPersistentStoreDirSet();

   String getPersistentStorePool();

   void setPersistentStorePool(String var1);

   boolean isPersistentStorePoolSet();

   String getPersistentDataSourceJNDIName();

   void setPersistentDataSourceJNDIName(String var1);

   boolean isPersistentDataSourceJNDINameSet();

   int getPersistentSessionFlushInterval();

   void setPersistentSessionFlushInterval(int var1);

   boolean isPersistentSessionFlushIntervalSet();

   int getPersistentSessionFlushThreshold();

   void setPersistentSessionFlushThreshold(int var1);

   boolean isPersistentSessionFlushThresholdSet();

   int getPersistentAsyncQueueTimeout();

   void setPersistentAsyncQueueTimeout(int var1);

   boolean isPersistentAsyncQueueTimeoutSet();

   String getPersistentStoreTable();

   void setPersistentStoreTable(String var1);

   boolean isPersistentStoreTableSet();

   String getJdbcColumnNameMaxInactiveInterval();

   void setJdbcColumnNameMaxInactiveInterval(String var1);

   boolean isJdbcColumnNameMaxInactiveIntervalSet();

   boolean isUrlRewritingEnabled();

   void setUrlRewritingEnabled(boolean var1);

   boolean isUrlRewritingEnabledSet();

   boolean isHttpProxyCachingOfCookies();

   void setHttpProxyCachingOfCookies(boolean var1);

   boolean isHttpProxyCachingOfCookiesSet();

   boolean isEncodeSessionIdInQueryParams();

   void setEncodeSessionIdInQueryParams(boolean var1);

   boolean isEncodeSessionIdInQueryParamsSet();

   String getMonitoringAttributeName();

   void setMonitoringAttributeName(String var1);

   boolean isMonitoringAttributeNameSet();

   boolean isSharingEnabled();

   void setSharingEnabled(boolean var1);

   boolean isSharingEnabledSet();

   boolean isInvalidateOnRelogin();

   void setInvalidateOnRelogin(boolean var1);

   boolean isInvalidateOnReloginSet();

   String getId();

   void setId(String var1);

   boolean isIdSet();
}
