package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface SessionDescriptorMBean extends WebElementMBean {
   void setURLRewritingEnabled(boolean var1);

   boolean isURLRewritingEnabled();

   void setIDLength(int var1);

   int getIDLength();

   void setCacheSize(int var1);

   int getCacheSize();

   void setCookieComment(String var1);

   String getCookieComment();

   void setCookieDomain(String var1);

   String getCookieDomain();

   void setCookieMaxAgeSecs(int var1);

   int getCookieMaxAgeSecs();

   void setCookieName(String var1);

   String getCookieName();

   void setEncodeSessionIdInQueryParams(boolean var1);

   boolean isEncodeSessionIdInQueryParams();

   void setCacheSessionCookie(boolean var1);

   boolean isCacheSessionCookie();

   void setCookiePath(String var1);

   String getCookiePath();

   void setCookieSecure(boolean var1);

   boolean isCookieSecure();

   void setInvalidationIntervalSecs(int var1);

   int getInvalidationIntervalSecs();

   void setJDBCConnectionTimeoutSecs(int var1);

   int getJDBCConnectionTimeoutSecs();

   void setPersistentStoreCookieName(String var1);

   String getPersistentStoreCookieName();

   void setPersistentStoreDir(String var1);

   String getPersistentStoreDir();

   void setPersistentStorePool(String var1);

   String getPersistentStorePool();

   String getPersistentDataSourceJNDIName();

   void setPersistentDataSourceJNDIName(String var1);

   void setPersistentStoreTable(String var1);

   String getPersistentStoreTable();

   void setJDBCColumnName_MaxInactiveInterval(String var1);

   String getJDBCColumnName_MaxInactiveInterval();

   void setPersistentStoreType(String var1);

   String getPersistentStoreType();

   void setCookiesEnabled(boolean var1);

   boolean isCookiesEnabled();

   void setTrackingEnabled(boolean var1);

   boolean isTrackingEnabled();

   void setPersistentStoreShared(boolean var1);

   boolean isPersistentStoreShared();

   void setSwapIntervalSecs(int var1);

   int getSwapIntervalSecs();

   void setTimeoutSecs(int var1);

   int getTimeoutSecs();

   void setConsoleMainAttribute(String var1);

   String getConsoleMainAttribute();

   void setDebugEnabled(boolean var1);

   boolean isDebugEnabled();
}
