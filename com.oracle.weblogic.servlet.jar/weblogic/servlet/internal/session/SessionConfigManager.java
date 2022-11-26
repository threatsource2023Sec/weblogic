package weblogic.servlet.internal.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.SessionTrackingMode;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.CookieConfigBean;
import weblogic.j2ee.descriptor.SessionConfigBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.SessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.servlet.internal.WebAppModule;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.WebComponentBeanUpdateListener;
import weblogic.servlet.spi.WebServerRegistry;

public final class SessionConfigManager implements SessionConstants {
   private final WebAppModule module;
   private final SessionDescriptorBean sdBean;
   private String persistentStoreType = "memory";
   private String sessionPersistentStoreDir = "session_db";
   private String sessionPersistentStoreTable = "wl_servlet_sessions";
   private String maxInactiveIntervalColumnName = "wl_max_inactive_interval";
   private String cookieName = "JSESSIONID";
   private String cookiePath = "/";
   private String cookiePersistentStoreCookieName = "WLCOOKIE";
   private int idLength = 52;
   private int authCookieIdLength = 20;
   private String partitionUriPath;
   private String sessionPersistentStorePool;
   private String sessionPersistentDataSourceJNDIName;
   private int sessionPersistentFlushInterval;
   private int sessionPersistentFlushThreshold;
   private int sessionPersistentQueueTimeout;
   private String cookieDomain;
   private String cookieComment;
   private boolean sessionTrackingEnabled = true;
   private boolean hasEffectiveSessionTrackingModes = false;
   private boolean sessionCookiesEnabled = true;
   private boolean urlRewritingEnabled;
   private boolean cacheSessionCookieEnabled = true;
   private boolean sessionSharingEnabled;
   private boolean invalidateOnRelogin = false;
   private boolean encodeSessionIdInQueryParamsEnabled;
   private boolean cookieSecure;
   private boolean cookieHttpOnly = true;
   private boolean cleanupSessionFilesEnabled;
   private boolean saveSessionsOnRedeploy;
   private boolean debugEnabled = false;
   private int cookieMaxAgeSecs = -1;
   private int sessionTimeoutSecs = 3600;
   private int savePostTimeoutSecs = 40;
   private boolean isTimeoutSecsSet = false;
   private int maxInMemorySessions = -1;
   private int cacheSize = 1024;
   private String monitoringAttributeName = null;
   private int invalidationIntervalSecs = 60;
   private int savePostTimeoutIntervalSecs = 20;
   private int maxSavePostSize = 4096;
   private final BeanUpdateListener sessionBeanListener;
   private String wlsAuthCookieName = "_WL_AUTHCOOKIE_JSESSIONID";
   private Set defaultTrackingModes = Collections.emptySet();
   private boolean ssl;
   public static final Map SESSION_ELEMENTS_MAP = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("PersistentStoreType".toLowerCase(), "persistent-store-type");
         this.put("CookiesEnabled".toLowerCase(), "cookies-enabled");
         this.put("URLRewritingEnabled".toLowerCase(), "url-rewriting-enabled");
         this.put("EncodeSessionIdInQueryParams".toLowerCase(), "encode-session-id-in-query-params");
         this.put("TrackingEnabled".toLowerCase(), "tracking-enabled");
         this.put("CacheSessionCookie".toLowerCase(), "http-proxy-caching-of-cookies");
         this.put("IDLength".toLowerCase(), "id-length");
         this.put("CookieComment".toLowerCase(), "cookie-comment");
         this.put("CookieDomain".toLowerCase(), "cookie-domain");
         this.put("CookieMaxAgeSecs".toLowerCase(), "cookie-max-age-secs");
         this.put("CookieName".toLowerCase(), "cookie-name");
         this.put("CookiePath".toLowerCase(), "cookie-path");
         this.put("InvalidationIntervalSecs".toLowerCase(), "invalidation-interval-secs");
         this.put("PersistentStoreCookieName".toLowerCase(), "persistent-store-cookie-name");
         this.put("PersistentStoreDir".toLowerCase(), "persistent-store-dir");
         this.put("PersistentStorePool".toLowerCase(), "persistent-store-pool");
         this.put("PersistentStoreTable".toLowerCase(), "persistent-store-table");
         this.put("SessionDebuggable".toLowerCase(), "debug-enabled");
         this.put("ConsoleMainAttribute".toLowerCase(), "monitoring-attribute-name");
         this.put("TimeoutSecs".toLowerCase(), "timeout-secs");
         this.put("CookieSecure".toLowerCase(), "cookie-secure");
         this.put("JDBCColumnName_MaxInactiveInterval".toLowerCase(), "jdbc-column-name-max-inactive-interval");
         this.put("CacheSize".toLowerCase(), "cache-size");
         this.put("JDBCConnectionTimeoutSecs".toLowerCase(), "jdbc-connection-timeout-secs");
         this.put("SharingEnabled".toLowerCase(), "sharing-enabled");
         this.put("PersistentDataSourceJNDIName".toLowerCase(), "persistent-data-source-jndi-name");
         this.put("PersistentSessionFlushInterval".toLowerCase(), "persistent-session-flush-interval");
         this.put("PersistentSessionFlushThreshold".toLowerCase(), "persistent-session-flush-threshold");
         this.put("PersistentAsyncQueueTimeout".toLowerCase(), "persistent-async-queue-timeout");
      }
   });

   public SessionConfigManager(WebAppModule module, SessionDescriptorBean sd) {
      this.module = module;
      this.sdBean = sd;
      this.initWithSessionConfigBean();
      this.initWithSessionDescriptorBean(this.sdBean);
      this.initDefaultSessionTrackingMode();
      this.sessionBeanListener = this.createBeanUpdateListener();
   }

   private void initDefaultSessionTrackingMode() {
      if (this.isSessionTrackingEnabled()) {
         this.defaultTrackingModes = new HashSet();
         if (this.isSessionCookiesEnabled()) {
            this.defaultTrackingModes.add(SessionTrackingMode.COOKIE);
         }

         if (this.isUrlRewritingEnabled()) {
            this.defaultTrackingModes.add(SessionTrackingMode.URL);
         }

         ServerMBean server = WebServerRegistry.getInstance().getServerMBean();
         boolean sslEnabled = server.getSSL().isEnabled();
         if (sslEnabled) {
            this.defaultTrackingModes.add(SessionTrackingMode.SSL);
         }

      }
   }

   public Set getDefaultSessionTrackingModes() {
      return this.defaultTrackingModes;
   }

   public Set getEffectiveSessionTrackingModes() {
      if (!this.isSessionTrackingEnabled()) {
         return Collections.emptySet();
      } else if (!this.hasEffectiveSessionTrackingModes) {
         return this.defaultTrackingModes;
      } else {
         Set sessionTrackingModeSet = new HashSet();
         if (this.isSessionCookiesEnabled()) {
            sessionTrackingModeSet.add(SessionTrackingMode.COOKIE);
         }

         if (this.isUrlRewritingEnabled()) {
            sessionTrackingModeSet.add(SessionTrackingMode.URL);
         }

         if (this.isSSLTrackingEnabled()) {
            sessionTrackingModeSet.add(SessionTrackingMode.SSL);
         }

         return sessionTrackingModeSet;
      }
   }

   public String getPersistentStoreDir() {
      return this.sessionPersistentStoreDir;
   }

   public void setPersistentStoreDir(String d) {
      this.sessionPersistentStoreDir = d;
   }

   public String getPersistentStorePool() {
      return this.sessionPersistentStorePool;
   }

   public void setPersistentStorePool(String p) {
      this.sessionPersistentStorePool = p;
   }

   public String getPersistentDataSourceJNDIName() {
      return this.sessionPersistentDataSourceJNDIName;
   }

   public void setPersistentDataSourceJNDIName(String p) {
      this.sessionPersistentDataSourceJNDIName = p;
   }

   public int getPersistentSessionFlushInterval() {
      return this.sessionPersistentFlushInterval;
   }

   public void setPersistentSessionFlushInterval(int p) {
      this.sessionPersistentFlushInterval = p;
   }

   public int getPersistentAsyncQueueTimeout() {
      return this.sessionPersistentQueueTimeout;
   }

   public void setPersistentAsyncQueueTimeout(int persistentQueueTimeout) {
      if (this.sdBean == null || !this.sdBean.isPersistentAsyncQueueTimeoutSet()) {
         this.sessionPersistentQueueTimeout = persistentQueueTimeout;
      }
   }

   public int getPersistentSessionFlushThreshold() {
      return this.sessionPersistentFlushThreshold;
   }

   public void setPersistentSessionFlushThreshold(int persistentFlushThreshold) {
      if (this.sdBean == null || !this.sdBean.isPersistentSessionFlushThresholdSet()) {
         this.sessionPersistentFlushThreshold = persistentFlushThreshold;
      }
   }

   public String getPersistentStoreTable() {
      return this.sessionPersistentStoreTable;
   }

   public void setPersistentStoreTable(String p) {
      if (this.sdBean == null || !this.sdBean.isPersistentStoreTableSet()) {
         this.sessionPersistentStoreTable = p;
      }
   }

   public String getMaxInactiveIntervalColumnName() {
      return this.maxInactiveIntervalColumnName;
   }

   public void setMaxInactiveIntervalColumnName(String name) {
      if (this.sdBean == null || !this.sdBean.isJdbcColumnNameMaxInactiveIntervalSet()) {
         this.maxInactiveIntervalColumnName = name;
      }
   }

   public boolean isCleanupSessionFilesEnabled() {
      return this.cleanupSessionFilesEnabled;
   }

   public void setCleanupSessionFilesEnabled(boolean b) {
      this.cleanupSessionFilesEnabled = b;
   }

   public boolean isSaveSessionsOnRedeployEnabled() {
      return this.saveSessionsOnRedeploy;
   }

   public void setSaveSessionsOnRedeployEnabled(boolean b) {
      this.saveSessionsOnRedeploy = b;
   }

   boolean isDebugEnabled() {
      return this.debugEnabled;
   }

   public boolean isCookieSecure() {
      return this.cookieSecure;
   }

   public void setCookieSecure(boolean secure) {
      if (this.sdBean == null || !this.sdBean.isCookieCommentSet()) {
         this.cookieSecure = secure;
      }
   }

   public boolean isCookieHttpOnly() {
      return this.cookieHttpOnly;
   }

   public void setCookieHttpOnly(boolean httpOnly) {
      if (this.sdBean == null || !this.sdBean.isCookieHttpOnlySet()) {
         this.cookieHttpOnly = httpOnly;
      }
   }

   public String getCookieComment() {
      return this.cookieComment;
   }

   public void setCookieComment(String c) {
      if (this.sdBean == null || !this.sdBean.isCookieCommentSet()) {
         this.cookieComment = c;
      }
   }

   public String getCookieName() {
      return this.cookieName;
   }

   public void setCookieName(String name) {
      if (this.sdBean == null || !this.sdBean.isCookieNameSet()) {
         if (name == null) {
            name = "JSESSIONID";
         }

         this.cookieName = name;
      }
   }

   public String getWLSAuthCookieName() {
      return this.wlsAuthCookieName;
   }

   public String getCookiePersistentStoreCookieName() {
      return this.cookiePersistentStoreCookieName;
   }

   public void setCookiePersistentStoreCookieName(String name) {
      if (this.sdBean == null || !this.sdBean.isPersistentStoreCookieNameSet()) {
         if (name == null) {
            name = "WLCOOKIE";
         }

         this.cookiePersistentStoreCookieName = name;
      }
   }

   public int getCookieMaxAgeSecs() {
      return this.cookieMaxAgeSecs;
   }

   public void setCookieMaxAgeSecs(int cookieMaxAgeSecs) {
      if (this.sdBean == null || !this.sdBean.isCookieMaxAgeSecsSet()) {
         this.cookieMaxAgeSecs = cookieMaxAgeSecs;
      }
   }

   public String getCookieDomain() {
      return this.cookieDomain;
   }

   public void setCookieDomain(String d) {
      if (this.sdBean == null || !this.sdBean.isCookieDomainSet()) {
         this.cookieDomain = d;
      }
   }

   public String getCookiePath() {
      return this.cookiePath;
   }

   public void setCookiePath(String path) {
      if (this.sdBean == null || !this.sdBean.isCookiePathSet()) {
         if (path == null) {
            path = "/";
         }

         this.cookiePath = path;
         if (this.partitionUriPath != null && !"/".equals(this.partitionUriPath)) {
            if (this.cookiePath == "/") {
               this.cookiePath = this.partitionUriPath;
            } else {
               this.cookiePath = this.partitionUriPath + this.cookiePath;
            }
         }

      }
   }

   public boolean isDefaultCookiePath() {
      return this.partitionUriPath == null ? "/".equals(this.cookiePath) : this.partitionUriPath.equals(this.cookiePath);
   }

   public void setPartitionUriPath(String uriPath) {
      if (uriPath != null && !"/".equals(uriPath)) {
         this.partitionUriPath = uriPath;
         if (this.cookiePath != null && this.cookiePath != "/") {
            this.cookiePath = this.partitionUriPath + this.cookiePath;
         } else {
            this.cookiePath = this.partitionUriPath;
         }

      }
   }

   public int getSessionTimeoutSecs() {
      return this.sessionTimeoutSecs;
   }

   public void setSessionTimeoutSecs(int sessionTimeout) {
      this.sessionTimeoutSecs = sessionTimeout;
   }

   public int getSavePostTimeoutSecs() {
      return this.savePostTimeoutSecs;
   }

   public int getMaxInMemorySessions() {
      return this.maxInMemorySessions;
   }

   public int getCacheSize() {
      return this.cacheSize;
   }

   public int getIDLength() {
      return this.idLength;
   }

   public void setIDLength(int len) {
      this.idLength = len;
   }

   public int getAuthCookieIDLength() {
      return this.authCookieIdLength;
   }

   public void setAuthCookieIDLength(int len) {
      this.authCookieIdLength = len;
   }

   String getMonitoringAttributeName() {
      return this.monitoringAttributeName;
   }

   public boolean isMonitoringEnabled() {
      if (this.module != null && this.module.getWlWebAppBean() != null) {
         WeblogicWebAppBean wab = this.module.getWlWebAppBean();
         ContainerDescriptorBean cb = (ContainerDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(wab, wab.getContainerDescriptors(), "ContainerDescriptor");
         return cb.isSessionMonitoringEnabled();
      } else {
         return false;
      }
   }

   public boolean isUrlRewritingEnabled() {
      return this.urlRewritingEnabled;
   }

   public void setUrlRewritingEnabled(boolean b) {
      if (this.sdBean == null || !this.sdBean.isUrlRewritingEnabledSet()) {
         this.urlRewritingEnabled = b;
      }
   }

   public boolean isSSLTrackingEnabled() {
      return this.ssl;
   }

   public void setSSLTrackingEnabled(boolean b) {
      this.ssl = b;
   }

   public boolean isEncodeSessionIdInQueryParamsEnabled() {
      return this.encodeSessionIdInQueryParamsEnabled;
   }

   public boolean isCacheSessionCookieEnabled() {
      return this.cacheSessionCookieEnabled;
   }

   public boolean isSessionTrackingEnabled() {
      return this.sessionTrackingEnabled;
   }

   public boolean isSessionCookiesEnabled() {
      return this.sessionCookiesEnabled;
   }

   public void setSessionCookiesEnabled(boolean b) {
      if (this.sdBean == null || !this.sdBean.isCookiesEnabledSet()) {
         this.sessionCookiesEnabled = b;
      }
   }

   public void hasEffectiveSessionTrackingModes(boolean b) {
      this.hasEffectiveSessionTrackingModes = b;
   }

   public boolean isSessionSharingEnabled() {
      return this.sessionSharingEnabled;
   }

   public boolean isInvalidateOnRelogin() {
      return this.invalidateOnRelogin;
   }

   public int getInvalidationIntervalSecs() {
      return this.invalidationIntervalSecs;
   }

   public int getSavePostTimeoutIntervalSecs() {
      return this.savePostTimeoutIntervalSecs;
   }

   public int getMaxSavePostSize() {
      return this.maxSavePostSize;
   }

   public String getPersistentStoreType() {
      return this.persistentStoreType;
   }

   public boolean isSamePersistentStore(SessionConfigManager mgr) {
      if (mgr == null) {
         return false;
      } else if (!this.persistentStoreType.equals(mgr.getPersistentStoreType())) {
         return false;
      } else if (this.persistentStoreType.equals("file")) {
         return this.sessionPersistentStoreDir.equals(mgr.sessionPersistentStoreDir);
      } else if (!this.persistentStoreType.equals("jdbc")) {
         return true;
      } else {
         return this.sessionPersistentStorePool.equals(mgr.sessionPersistentStorePool) && this.sessionPersistentStoreTable.equals(mgr.sessionPersistentStoreTable);
      }
   }

   public BeanUpdateListener getBeanUpdateListener() {
      return this.sessionBeanListener;
   }

   void validateSessionIdLength() {
      if (this.idLength < 32) {
         this.idLength = 32;
         HTTPSessionLogger.logSessionIDlengthTooShort(this.idLength, 32);
      }

   }

   private void initWithSessionConfigBean() {
      SessionConfigBean[] sc = null;
      if (this.module != null && this.module.getWebAppBean() != null) {
         sc = this.module.getWebAppBean().getSessionConfigs();
      }

      if (sc != null && sc.length > 0) {
         int timeout = sc[0].getSessionTimeout();
         if (timeout == 0) {
            timeout = -1;
         }

         if (timeout != 60) {
            this.sessionTimeoutSecs = timeout * 60;
            this.isTimeoutSecsSet = true;
         }

         this.initWithCookieConfigBean(sc[0].getCookieConfig());
      }

   }

   private void initWithCookieConfigBean(CookieConfigBean cookieConfig) {
      if (cookieConfig != null) {
         if (cookieConfig.getName() != null) {
            this.cookieName = cookieConfig.getName();
         }

         this.cookieDomain = cookieConfig.getDomain();
         this.cookieComment = cookieConfig.getComment();
         this.cookieHttpOnly = cookieConfig.isHttpOnly();
         if (cookieConfig.getPath() != null) {
            this.cookiePath = cookieConfig.getPath();
         }

         this.cookieMaxAgeSecs = cookieConfig.getMaxAge();
         this.cookieSecure = cookieConfig.isSecure();
      }
   }

   private void initWithSessionDescriptorBean(SessionDescriptorBean sd) {
      this.urlRewritingEnabled = this.getUrlRewritingEnabledDefaultValue();
      if (sd != null) {
         this.sessionCookiesEnabled = sd.isCookiesEnabled();
         if (sd.isUrlRewritingEnabledSet()) {
            this.urlRewritingEnabled = sd.isUrlRewritingEnabled();
         }

         this.encodeSessionIdInQueryParamsEnabled = sd.isEncodeSessionIdInQueryParams();
         this.sessionTrackingEnabled = sd.isTrackingEnabled();
         this.cacheSessionCookieEnabled = sd.isHttpProxyCachingOfCookies();
         this.idLength = sd.getIdLength();
         if (sd.isAuthCookieIdLengthSet()) {
            this.authCookieIdLength = sd.getAuthCookieIdLength();
         }

         this.cookieComment = sd.getCookieComment();
         this.cookieDomain = sd.getCookieDomain();
         this.cookieName = sd.getCookieName();
         this.cookiePath = sd.getCookiePath();
         this.cookiePersistentStoreCookieName = sd.getPersistentStoreCookieName();
         if ("async-jdbc".equals(sd.getPersistentStoreType()) && !sd.isPersistentDataSourceJNDINameSet()) {
            throw new IllegalArgumentException(HTTPSessionLogger.logAsyncJDBCSessionDatabaseConfigErrorLoggable().getMessage());
         } else {
            if ("async-jdbc".equals(sd.getPersistentStoreType()) && sd.isPersistentDataSourceJNDINameSet() && sd.isPersistentStorePoolSet()) {
               HTTPSessionLogger.logAsyncJDBCSessionIgnorePersistentStorePool(this.module.getApplicationName());
            }

            if ("jdbc".equals(sd.getPersistentStoreType()) && !sd.isPersistentStorePoolSet() && !sd.isPersistentDataSourceJNDINameSet()) {
               throw new IllegalArgumentException(HTTPSessionLogger.logJDBCSessionDatabaseConfigErrorLoggable().getMessage());
            } else {
               this.sessionPersistentDataSourceJNDIName = sd.getPersistentDataSourceJNDIName();
               this.sessionPersistentFlushInterval = sd.getPersistentSessionFlushInterval();
               this.sessionPersistentFlushThreshold = sd.getPersistentSessionFlushThreshold();
               this.sessionPersistentQueueTimeout = sd.getPersistentAsyncQueueTimeout();
               this.sessionPersistentStoreDir = sd.getPersistentStoreDir();
               this.sessionPersistentStorePool = sd.getPersistentStorePool();
               this.sessionPersistentStoreTable = sd.getPersistentStoreTable();
               this.maxInactiveIntervalColumnName = sd.getJdbcColumnNameMaxInactiveInterval();
               if (sd.isCookieSecureSet()) {
                  this.cookieSecure = sd.isCookieSecure();
               }

               if (sd.isCookieHttpOnlySet()) {
                  this.cookieHttpOnly = sd.isCookieHttpOnly();
               }

               this.persistentStoreType = sd.getPersistentStoreType();
               this.sessionSharingEnabled = sd.isSharingEnabled();
               this.invalidateOnRelogin = sd.isInvalidateOnRelogin();
               if (this.module != null && this.module.getWlWebAppBean() != null) {
                  WeblogicWebAppBean wab = this.module.getWlWebAppBean();
                  SessionDescriptorBean[] sdBeans = wab.getSessionDescriptors();
                  if (sdBeans != null && sdBeans.length > 0) {
                     sd = sdBeans[0];
                  }
               }

               this.savePostTimeoutSecs = sd.getSavePostTimeoutSecs();
               this.savePostTimeoutIntervalSecs = sd.getSavePostTimeoutIntervalSecs();
               this.maxSavePostSize = sd.getMaxSavePostSize();
               this.debugEnabled = sd.isDebugEnabled();
               this.cookieMaxAgeSecs = sd.getCookieMaxAgeSecs();
               if (!this.isTimeoutSecsSet) {
                  this.sessionTimeoutSecs = sd.getTimeoutSecs();
               }

               this.maxInMemorySessions = sd.getMaxInMemorySessions();
               this.cacheSize = sd.getCacheSize();
               this.monitoringAttributeName = sd.getMonitoringAttributeName();
               this.invalidationIntervalSecs = sd.getInvalidationIntervalSecs();
               if (sd.getCookiePath() != null && !"/".equals(sd.getCookiePath())) {
                  this.cookiePath = sd.getCookiePath();
               }

               if (this.cookieName != null) {
                  this.wlsAuthCookieName = "_WL_AUTHCOOKIE_" + this.cookieName;
               }

            }
         }
      }
   }

   private boolean getUrlRewritingEnabledDefaultValue() {
      try {
         WebAppBean webappBean = this.module.getWebAppBean();
         if (webappBean == null) {
            return false;
         }

         String schemaVersion = ((DescriptorBean)webappBean).getDescriptor().getOriginalVersionInfo();
         if (schemaVersion == null || schemaVersion.trim().isEmpty()) {
            return false;
         }

         if ((double)Float.parseFloat(schemaVersion) >= 3.1 && webappBean.getVersion() != null) {
            return false;
         }
      } catch (Exception var3) {
      }

      return true;
   }

   private BeanUpdateListener createBeanUpdateListener() {
      return new WebComponentBeanUpdateListener() {
         private void resetSessionContextInvalidationIntervalSecs() {
            Iterator var1 = SessionConfigManager.this.module.getAllContexts().iterator();

            while(var1.hasNext()) {
               WebAppServletContext context = (WebAppServletContext)var1.next();
               context.getSessionContext().setInvalidationIntervalSecs(SessionConfigManager.this.invalidationIntervalSecs);
            }

         }

         private void resetSessionContextSavePostInvalidationIntervalSecs() {
            Iterator var1 = SessionConfigManager.this.module.getAllContexts().iterator();

            while(var1.hasNext()) {
               WebAppServletContext context = (WebAppServletContext)var1.next();
               context.getSessionContext().setSavePostTimeoutIntervalSecs(SessionConfigManager.this.savePostTimeoutIntervalSecs);
            }

         }

         protected void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate prop) {
            String propertyName = prop.getPropertyName();
            if ("InvalidationIntervalSecs".equals(propertyName)) {
               SessionConfigManager.this.invalidationIntervalSecs = 60;
               this.resetSessionContextInvalidationIntervalSecs();
            } else if ("DebugEnabled".equals(propertyName)) {
               SessionConfigManager.this.debugEnabled = false;
            } else if ("CookieMaxAgeSecs".equals(propertyName)) {
               SessionConfigManager.this.cookieMaxAgeSecs = -1;
            } else if ("TimeoutSecs".equals(propertyName) && !SessionConfigManager.this.isTimeoutSecsSet) {
               SessionConfigManager.this.sessionTimeoutSecs = 3600;
            } else if ("MaxInMemorySessions".equals(propertyName)) {
               SessionConfigManager.this.maxInMemorySessions = -1;
            } else if ("CacheSize".equals(propertyName)) {
               SessionConfigManager.this.cacheSize = 1024;
            } else if ("MonitoringAttributeName".equals(propertyName)) {
               SessionConfigManager.this.monitoringAttributeName = null;
            } else if ("SavePostTimeoutSecs".equals(propertyName)) {
               SessionConfigManager.this.savePostTimeoutSecs = 40;
            } else if ("SavePostTimeoutIntervalSecs".equals(propertyName)) {
               SessionConfigManager.this.savePostTimeoutIntervalSecs = 20;
               this.resetSessionContextSavePostInvalidationIntervalSecs();
            }

         }

         protected void handlePropertyChange(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            SessionDescriptorBean sdb = (SessionDescriptorBean)newBean;
            String propertyName = prop.getPropertyName();
            if ("InvalidationIntervalSecs".equals(propertyName)) {
               SessionConfigManager.this.invalidationIntervalSecs = sdb.getInvalidationIntervalSecs();
               this.resetSessionContextInvalidationIntervalSecs();
            } else if ("DebugEnabled".equals(propertyName)) {
               SessionConfigManager.this.debugEnabled = sdb.isDebugEnabled();
            } else if ("CookieMaxAgeSecs".equals(propertyName)) {
               SessionConfigManager.this.cookieMaxAgeSecs = sdb.getCookieMaxAgeSecs();
            } else if ("TimeoutSecs".equals(propertyName) && !SessionConfigManager.this.isTimeoutSecsSet) {
               SessionConfigManager.this.sessionTimeoutSecs = sdb.getTimeoutSecs();
            } else if ("MaxInMemorySessions".equals(propertyName)) {
               SessionConfigManager.this.maxInMemorySessions = sdb.getMaxInMemorySessions();
            } else if ("CacheSize".equals(propertyName)) {
               SessionConfigManager.this.cacheSize = sdb.getCacheSize();
            } else if ("MonitoringAttributeName".equals(propertyName)) {
               SessionConfigManager.this.monitoringAttributeName = sdb.getMonitoringAttributeName();
            } else if ("SavePostTimeoutSecs".equals(propertyName)) {
               SessionConfigManager.this.savePostTimeoutSecs = sdb.getSavePostTimeoutSecs();
            } else if ("SavePostTimeoutIntervalSecs".equals(propertyName)) {
               SessionConfigManager.this.savePostTimeoutIntervalSecs = sdb.getSavePostTimeoutIntervalSecs();
               this.resetSessionContextSavePostInvalidationIntervalSecs();
            }

         }

         protected void handleBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
            if (newBean instanceof SessionDescriptorBean && prop != null && "SessionDescriptors".equals(prop.getPropertyName())) {
               SessionDescriptorBean sdb = (SessionDescriptorBean)newBean;
               ((DescriptorBean)sdb).addBeanUpdateListener(this);
               if (!SessionConfigManager.this.isTimeoutSecsSet) {
                  SessionConfigManager.this.sessionTimeoutSecs = sdb.getTimeoutSecs();
                  SessionConfigManager.this.isTimeoutSecsSet = true;
               }

               SessionConfigManager.this.invalidationIntervalSecs = sdb.getInvalidationIntervalSecs();
               SessionConfigManager.this.savePostTimeoutSecs = sdb.getSavePostTimeoutSecs();
               SessionConfigManager.this.savePostTimeoutIntervalSecs = sdb.getSavePostTimeoutIntervalSecs();
               this.resetSessionContextInvalidationIntervalSecs();
               this.resetSessionContextSavePostInvalidationIntervalSecs();
               SessionConfigManager.this.debugEnabled = sdb.isDebugEnabled();
               SessionConfigManager.this.maxInMemorySessions = sdb.getMaxInMemorySessions();
               SessionConfigManager.this.cookieMaxAgeSecs = sdb.getCookieMaxAgeSecs();
               SessionConfigManager.this.monitoringAttributeName = sdb.getMonitoringAttributeName();
            }
         }

         protected void handleBeanRemove(BeanUpdateEvent.PropertyUpdate prop) {
            if (prop != null && "SessionDescriptors".equals(prop.getPropertyName()) && prop.getRemovedObject() instanceof SessionDescriptorBean) {
               SessionDescriptorBean sdb = (SessionDescriptorBean)prop.getRemovedObject();
               ((DescriptorBean)sdb).removeBeanUpdateListener(this);
               if (!SessionConfigManager.this.isTimeoutSecsSet) {
                  SessionConfigManager.this.sessionTimeoutSecs = 3600;
                  SessionConfigManager.this.isTimeoutSecsSet = true;
               }

               SessionConfigManager.this.invalidationIntervalSecs = 60;
               this.resetSessionContextInvalidationIntervalSecs();
               SessionConfigManager.this.savePostTimeoutSecs = 40;
               SessionConfigManager.this.savePostTimeoutIntervalSecs = 20;
               this.resetSessionContextSavePostInvalidationIntervalSecs();
               SessionConfigManager.this.debugEnabled = false;
               SessionConfigManager.this.maxInMemorySessions = -1;
               SessionConfigManager.this.cookieMaxAgeSecs = -1;
               SessionConfigManager.this.monitoringAttributeName = null;
            }
         }

         protected void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) throws BeanUpdateRejectedException {
            if (newBean instanceof SessionDescriptorBean) {
               SessionDescriptorBean cur = (SessionDescriptorBean)newBean;
               SessionDescriptorBean prev = (SessionDescriptorBean)DescriptorUtils.getFirstChildOrDefaultBean(SessionConfigManager.this.module.getWlWebAppBean(), SessionConfigManager.this.module.getWlWebAppBean().getSessionDescriptors(), "SessionDescriptor");
               List changedNames = new ArrayList();
               computeChange("id-length", cur.getIdLength(), prev.getIdLength(), changedNames);
               computeChange("tracking-enabled", cur.isTrackingEnabled(), prev.isTrackingEnabled(), changedNames);
               computeChange("cache-size", cur.getCacheSize(), prev.getCacheSize(), changedNames);
               computeChange("max-in-memory-sessions", cur.getMaxInMemorySessions(), prev.getMaxInMemorySessions(), changedNames);
               computeChange("cookies-enabled", cur.isCookiesEnabled(), prev.isCookiesEnabled(), changedNames);
               computeChange("cookie-name", cur.getCookieName(), prev.getCookieName(), changedNames);
               computeChange("cookie-path", cur.getCookiePath(), prev.getCookiePath(), changedNames);
               computeChange("cookie-domain", cur.getCookieDomain(), prev.getCookieDomain(), changedNames);
               computeChange("cookie-comment", cur.getCookieComment(), prev.getCookieComment(), changedNames);
               computeChange("cookie-secure", cur.isCookieSecure(), prev.isCookieSecure(), changedNames);
               computeChange("persistent-store-type", cur.getPersistentStoreType(), prev.getPersistentStoreType(), changedNames);
               computeChange("persistent-store-cookie-name", cur.getPersistentStoreCookieName(), prev.getPersistentStoreCookieName(), changedNames);
               computeChange("persistent-store-dir", cur.getPersistentStoreDir(), prev.getPersistentStoreDir(), changedNames);
               computeChange("persistent-store-pool", cur.getPersistentStorePool(), prev.getPersistentStorePool(), changedNames);
               computeChange("persistent-data-source-jndi-name", cur.getPersistentDataSourceJNDIName(), prev.getPersistentDataSourceJNDIName(), changedNames);
               computeChange("persistent-session-flush-interval", cur.getPersistentSessionFlushInterval(), prev.getPersistentSessionFlushInterval(), changedNames);
               computeChange("persistent-session-flush-threshold", cur.getPersistentSessionFlushThreshold(), prev.getPersistentSessionFlushThreshold(), changedNames);
               computeChange("persistent-async-queue-timeout", cur.getPersistentAsyncQueueTimeout(), prev.getPersistentAsyncQueueTimeout(), changedNames);
               computeChange("persistent-store-table", cur.getPersistentStoreTable(), prev.getPersistentStoreTable(), changedNames);
               computeChange("jdbc-column-name-max-inactive-interval", cur.getJdbcColumnNameMaxInactiveInterval(), prev.getJdbcColumnNameMaxInactiveInterval(), changedNames);
               computeChange("url-rewriting-enabled", cur.isUrlRewritingEnabled(), prev.isUrlRewritingEnabled(), changedNames);
               computeChange("http-proxy-caching-of-cookies", cur.isHttpProxyCachingOfCookies(), prev.isHttpProxyCachingOfCookies(), changedNames);
               computeChange("encode-session-id-in-query-params", cur.isEncodeSessionIdInQueryParams(), prev.isEncodeSessionIdInQueryParams(), changedNames);
               computeChange("sharing-enabled", cur.isSharingEnabled(), prev.isSharingEnabled(), changedNames);
               computeChange("cookie-http-only", cur.isCookieHttpOnly(), prev.isCookieHttpOnly(), changedNames);
               if (!changedNames.isEmpty()) {
                  throw new BeanUpdateRejectedException("Non-Dynamic property in \"session-descriptor\" is/are specified in deployment plan: '" + getChangedPropertyNames(changedNames) + "'");
               }
            }
         }
      };
   }
}
