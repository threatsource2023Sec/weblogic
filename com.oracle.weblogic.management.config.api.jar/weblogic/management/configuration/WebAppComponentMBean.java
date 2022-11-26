package weblogic.management.configuration;

import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface WebAppComponentMBean extends ComponentMBean, WebDeploymentMBean {
   String[] INDEX_FILES = new String[]{"index.html", "index.htm", "index.jsp"};

   /** @deprecated */
   @Deprecated
   int getSessionCookieMaxAgeSecs();

   /** @deprecated */
   @Deprecated
   void setSessionCookieMaxAgeSecs(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSessionInvalidationIntervalSecs();

   /** @deprecated */
   @Deprecated
   void setSessionInvalidationIntervalSecs(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSessionJDBCConnectionTimeoutSecs();

   /** @deprecated */
   @Deprecated
   void setSessionJDBCConnectionTimeoutSecs(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSessionTimeoutSecs();

   /** @deprecated */
   @Deprecated
   void setSessionTimeoutSecs(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getMimeTypeDefault();

   /** @deprecated */
   @Deprecated
   void setMimeTypeDefault(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   Map getMimeTypes();

   /** @deprecated */
   @Deprecated
   void setMimeTypes(Map var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getDocumentRoot();

   /** @deprecated */
   @Deprecated
   void setDocumentRoot(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getDefaultServlet();

   /** @deprecated */
   @Deprecated
   void setDefaultServlet(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isIndexDirectoryEnabled();

   /** @deprecated */
   @Deprecated
   void setIndexDirectoryEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   String[] getIndexFiles();

   /** @deprecated */
   @Deprecated
   void setIndexFiles(String[] var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean addIndexFile(String var1);

   /** @deprecated */
   @Deprecated
   boolean removeIndexFile(String var1);

   /** @deprecated */
   @Deprecated
   String getServletClasspath();

   /** @deprecated */
   @Deprecated
   void setServletClasspath(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isServletExtensionCaseSensitive();

   /** @deprecated */
   @Deprecated
   void setServletExtensionCaseSensitive(boolean var1);

   /** @deprecated */
   @Deprecated
   int getServletReloadCheckSecs();

   /** @deprecated */
   @Deprecated
   void setServletReloadCheckSecs(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSingleThreadedServletPoolSize();

   /** @deprecated */
   @Deprecated
   void setSingleThreadedServletPoolSize(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String[] getServlets();

   /** @deprecated */
   @Deprecated
   String getAuthRealmName();

   /** @deprecated */
   @Deprecated
   void setAuthRealmName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getAuthFilter();

   /** @deprecated */
   @Deprecated
   void setAuthFilter(String var1);

   /** @deprecated */
   @Deprecated
   boolean isDebugEnabled();

   /** @deprecated */
   @Deprecated
   void setDebugEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isSessionURLRewritingEnabled();

   /** @deprecated */
   @Deprecated
   boolean isSessionURLRewritingEnabledSet();

   /** @deprecated */
   @Deprecated
   void setSessionURLRewritingEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   int getSessionIDLength();

   /** @deprecated */
   @Deprecated
   void setSessionIDLength(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSessionCacheSize();

   /** @deprecated */
   @Deprecated
   void setSessionCacheSize(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isSessionCookiesEnabled();

   /** @deprecated */
   @Deprecated
   void setSessionCookiesEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isSessionTrackingEnabled();

   /** @deprecated */
   @Deprecated
   void setSessionTrackingEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   String getSessionCookieComment();

   /** @deprecated */
   @Deprecated
   void setSessionCookieComment(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionCookieDomain();

   /** @deprecated */
   @Deprecated
   void setSessionCookieDomain(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionCookieName();

   /** @deprecated */
   @Deprecated
   void setSessionCookieName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionCookiePath();

   /** @deprecated */
   @Deprecated
   void setSessionCookiePath(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionPersistentStoreDir();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStoreDir(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionPersistentStorePool();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStorePool(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionPersistentStoreTable();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStoreTable(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isSessionPersistentStoreShared();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStoreShared(boolean var1);

   /** @deprecated */
   @Deprecated
   String getSessionPersistentStoreType();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStoreType(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getSessionPersistentStoreCookieName();

   /** @deprecated */
   @Deprecated
   void setSessionPersistentStoreCookieName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getSessionSwapIntervalSecs();

   /** @deprecated */
   @Deprecated
   void setSessionSwapIntervalSecs(int var1) throws InvalidAttributeValueException;

   void setSessionDebuggable(boolean var1);

   boolean isSessionDebuggable();

   /** @deprecated */
   @Deprecated
   void setCleanupSessionFilesEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isCleanupSessionFilesEnabled();

   /** @deprecated */
   @Deprecated
   String getContextPath();

   /** @deprecated */
   @Deprecated
   void setContextPath(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   /** @deprecated */
   @Deprecated
   String getSessionMainAttribute();

   /** @deprecated */
   @Deprecated
   void setSessionMainAttribute(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isSessionMonitoringEnabled();

   /** @deprecated */
   @Deprecated
   void setSessionMonitoringEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isPreferWebInfClasses();

   /** @deprecated */
   @Deprecated
   void setPreferWebInfClasses(boolean var1);

   void setJaxRsMonitoringDefaultBehavior(Boolean var1);

   Boolean isJaxRsMonitoringDefaultBehavior();
}
