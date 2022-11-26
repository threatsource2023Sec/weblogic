package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class SessionDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SessionDescriptorBean beanTreeNode;

   public SessionDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SessionDescriptorBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public int getTimeoutSecs() {
      return this.beanTreeNode.getTimeoutSecs();
   }

   public void setTimeoutSecs(int value) {
      this.beanTreeNode.setTimeoutSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeoutSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTimeoutSecsSet() {
      return this.beanTreeNode.isTimeoutSecsSet();
   }

   public int getInvalidationIntervalSecs() {
      return this.beanTreeNode.getInvalidationIntervalSecs();
   }

   public void setInvalidationIntervalSecs(int value) {
      this.beanTreeNode.setInvalidationIntervalSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InvalidationIntervalSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isInvalidationIntervalSecsSet() {
      return this.beanTreeNode.isInvalidationIntervalSecsSet();
   }

   public int getMaxSavePostSize() {
      return this.beanTreeNode.getMaxSavePostSize();
   }

   public void setMaxSavePostSize(int value) {
      this.beanTreeNode.setMaxSavePostSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxSavePostSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMaxSavePostSizeSet() {
      return this.beanTreeNode.isMaxSavePostSizeSet();
   }

   public int getSavePostTimeoutSecs() {
      return this.beanTreeNode.getSavePostTimeoutSecs();
   }

   public void setSavePostTimeoutSecs(int value) {
      this.beanTreeNode.setSavePostTimeoutSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SavePostTimeoutSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSavePostTimeoutSecsSet() {
      return this.beanTreeNode.isSavePostTimeoutSecsSet();
   }

   public int getSavePostTimeoutIntervalSecs() {
      return this.beanTreeNode.getSavePostTimeoutIntervalSecs();
   }

   public void setSavePostTimeoutIntervalSecs(int value) {
      this.beanTreeNode.setSavePostTimeoutIntervalSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SavePostTimeoutIntervalSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSavePostTimeoutIntervalSecsSet() {
      return this.beanTreeNode.isSavePostTimeoutIntervalSecsSet();
   }

   public boolean isDebugEnabled() {
      return this.beanTreeNode.isDebugEnabled();
   }

   public void setDebugEnabled(boolean value) {
      this.beanTreeNode.setDebugEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DebugEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDebugEnabledSet() {
      return this.beanTreeNode.isDebugEnabledSet();
   }

   public int getIdLength() {
      return this.beanTreeNode.getIdLength();
   }

   public void setIdLength(int value) {
      this.beanTreeNode.setIdLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IdLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIdLengthSet() {
      return this.beanTreeNode.isIdLengthSet();
   }

   public int getAuthCookieIdLength() {
      return this.beanTreeNode.getAuthCookieIdLength();
   }

   public void setAuthCookieIdLength(int value) {
      this.beanTreeNode.setAuthCookieIdLength(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AuthCookieIdLength", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAuthCookieIdLengthSet() {
      return this.beanTreeNode.isAuthCookieIdLengthSet();
   }

   public boolean isTrackingEnabled() {
      return this.beanTreeNode.isTrackingEnabled();
   }

   public void setTrackingEnabled(boolean value) {
      this.beanTreeNode.setTrackingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TrackingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTrackingEnabledSet() {
      return this.beanTreeNode.isTrackingEnabledSet();
   }

   public int getCacheSize() {
      return this.beanTreeNode.getCacheSize();
   }

   public void setCacheSize(int value) {
      this.beanTreeNode.setCacheSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCacheSizeSet() {
      return this.beanTreeNode.isCacheSizeSet();
   }

   public int getMaxInMemorySessions() {
      return this.beanTreeNode.getMaxInMemorySessions();
   }

   public void setMaxInMemorySessions(int value) {
      this.beanTreeNode.setMaxInMemorySessions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxInMemorySessions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMaxInMemorySessionsSet() {
      return this.beanTreeNode.isMaxInMemorySessionsSet();
   }

   public boolean isCookiesEnabled() {
      return this.beanTreeNode.isCookiesEnabled();
   }

   public void setCookiesEnabled(boolean value) {
      this.beanTreeNode.setCookiesEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookiesEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookiesEnabledSet() {
      return this.beanTreeNode.isCookiesEnabledSet();
   }

   public String getCookieName() {
      return this.beanTreeNode.getCookieName();
   }

   public void setCookieName(String value) {
      this.beanTreeNode.setCookieName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieNameSet() {
      return this.beanTreeNode.isCookieNameSet();
   }

   public String getCookiePath() {
      return this.beanTreeNode.getCookiePath();
   }

   public void setCookiePath(String value) {
      this.beanTreeNode.setCookiePath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookiePath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookiePathSet() {
      return this.beanTreeNode.isCookiePathSet();
   }

   public String getCookieDomain() {
      return this.beanTreeNode.getCookieDomain();
   }

   public void setCookieDomain(String value) {
      this.beanTreeNode.setCookieDomain(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieDomain", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieDomainSet() {
      return this.beanTreeNode.isCookieDomainSet();
   }

   public String getCookieComment() {
      return this.beanTreeNode.getCookieComment();
   }

   public void setCookieComment(String value) {
      this.beanTreeNode.setCookieComment(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieComment", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieCommentSet() {
      return this.beanTreeNode.isCookieCommentSet();
   }

   public boolean isCookieSecure() {
      return this.beanTreeNode.isCookieSecure();
   }

   public void setCookieSecure(boolean value) {
      this.beanTreeNode.setCookieSecure(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieSecure", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieSecureSet() {
      return this.beanTreeNode.isCookieSecureSet();
   }

   public int getCookieMaxAgeSecs() {
      return this.beanTreeNode.getCookieMaxAgeSecs();
   }

   public void setCookieMaxAgeSecs(int value) {
      this.beanTreeNode.setCookieMaxAgeSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieMaxAgeSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieMaxAgeSecsSet() {
      return this.beanTreeNode.isCookieMaxAgeSecsSet();
   }

   public boolean isCookieHttpOnly() {
      return this.beanTreeNode.isCookieHttpOnly();
   }

   public void setCookieHttpOnly(boolean value) {
      this.beanTreeNode.setCookieHttpOnly(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CookieHttpOnly", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCookieHttpOnlySet() {
      return this.beanTreeNode.isCookieHttpOnlySet();
   }

   public String getPersistentStoreType() {
      return this.beanTreeNode.getPersistentStoreType();
   }

   public void setPersistentStoreType(String value) {
      this.beanTreeNode.setPersistentStoreType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStoreType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentStoreTypeSet() {
      return this.beanTreeNode.isPersistentStoreTypeSet();
   }

   public String getPersistentStoreCookieName() {
      return this.beanTreeNode.getPersistentStoreCookieName();
   }

   public void setPersistentStoreCookieName(String value) {
      this.beanTreeNode.setPersistentStoreCookieName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStoreCookieName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentStoreCookieNameSet() {
      return this.beanTreeNode.isPersistentStoreCookieNameSet();
   }

   public String getPersistentStoreDir() {
      return this.beanTreeNode.getPersistentStoreDir();
   }

   public void setPersistentStoreDir(String value) {
      this.beanTreeNode.setPersistentStoreDir(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStoreDir", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentStoreDirSet() {
      return this.beanTreeNode.isPersistentStoreDirSet();
   }

   public String getPersistentStorePool() {
      return this.beanTreeNode.getPersistentStorePool();
   }

   public void setPersistentStorePool(String value) {
      this.beanTreeNode.setPersistentStorePool(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStorePool", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentStorePoolSet() {
      return this.beanTreeNode.isPersistentStorePoolSet();
   }

   public String getPersistentDataSourceJNDIName() {
      return this.beanTreeNode.getPersistentDataSourceJNDIName();
   }

   public void setPersistentDataSourceJNDIName(String value) {
      this.beanTreeNode.setPersistentDataSourceJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentDataSourceJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentDataSourceJNDINameSet() {
      return this.beanTreeNode.isPersistentDataSourceJNDINameSet();
   }

   public int getPersistentSessionFlushInterval() {
      return this.beanTreeNode.getPersistentSessionFlushInterval();
   }

   public void setPersistentSessionFlushInterval(int value) {
      this.beanTreeNode.setPersistentSessionFlushInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentSessionFlushInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentSessionFlushIntervalSet() {
      return this.beanTreeNode.isPersistentSessionFlushIntervalSet();
   }

   public int getPersistentSessionFlushThreshold() {
      return this.beanTreeNode.getPersistentSessionFlushThreshold();
   }

   public void setPersistentSessionFlushThreshold(int value) {
      this.beanTreeNode.setPersistentSessionFlushThreshold(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentSessionFlushThreshold", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentSessionFlushThresholdSet() {
      return this.beanTreeNode.isPersistentSessionFlushThresholdSet();
   }

   public int getPersistentAsyncQueueTimeout() {
      return this.beanTreeNode.getPersistentAsyncQueueTimeout();
   }

   public void setPersistentAsyncQueueTimeout(int value) {
      this.beanTreeNode.setPersistentAsyncQueueTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentAsyncQueueTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentAsyncQueueTimeoutSet() {
      return this.beanTreeNode.isPersistentAsyncQueueTimeoutSet();
   }

   public String getPersistentStoreTable() {
      return this.beanTreeNode.getPersistentStoreTable();
   }

   public void setPersistentStoreTable(String value) {
      this.beanTreeNode.setPersistentStoreTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PersistentStoreTable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPersistentStoreTableSet() {
      return this.beanTreeNode.isPersistentStoreTableSet();
   }

   public String getJdbcColumnNameMaxInactiveInterval() {
      return this.beanTreeNode.getJdbcColumnNameMaxInactiveInterval();
   }

   public void setJdbcColumnNameMaxInactiveInterval(String value) {
      this.beanTreeNode.setJdbcColumnNameMaxInactiveInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JdbcColumnNameMaxInactiveInterval", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isJdbcColumnNameMaxInactiveIntervalSet() {
      return this.beanTreeNode.isJdbcColumnNameMaxInactiveIntervalSet();
   }

   public boolean isUrlRewritingEnabled() {
      return this.beanTreeNode.isUrlRewritingEnabled();
   }

   public void setUrlRewritingEnabled(boolean value) {
      this.beanTreeNode.setUrlRewritingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UrlRewritingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUrlRewritingEnabledSet() {
      return this.beanTreeNode.isUrlRewritingEnabledSet();
   }

   public boolean isHttpProxyCachingOfCookies() {
      return this.beanTreeNode.isHttpProxyCachingOfCookies();
   }

   public void setHttpProxyCachingOfCookies(boolean value) {
      this.beanTreeNode.setHttpProxyCachingOfCookies(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpProxyCachingOfCookies", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isHttpProxyCachingOfCookiesSet() {
      return this.beanTreeNode.isHttpProxyCachingOfCookiesSet();
   }

   public boolean isEncodeSessionIdInQueryParams() {
      return this.beanTreeNode.isEncodeSessionIdInQueryParams();
   }

   public void setEncodeSessionIdInQueryParams(boolean value) {
      this.beanTreeNode.setEncodeSessionIdInQueryParams(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EncodeSessionIdInQueryParams", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEncodeSessionIdInQueryParamsSet() {
      return this.beanTreeNode.isEncodeSessionIdInQueryParamsSet();
   }

   public String getMonitoringAttributeName() {
      return this.beanTreeNode.getMonitoringAttributeName();
   }

   public void setMonitoringAttributeName(String value) {
      this.beanTreeNode.setMonitoringAttributeName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MonitoringAttributeName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMonitoringAttributeNameSet() {
      return this.beanTreeNode.isMonitoringAttributeNameSet();
   }

   public boolean isSharingEnabled() {
      return this.beanTreeNode.isSharingEnabled();
   }

   public void setSharingEnabled(boolean value) {
      this.beanTreeNode.setSharingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SharingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSharingEnabledSet() {
      return this.beanTreeNode.isSharingEnabledSet();
   }

   public boolean isInvalidateOnRelogin() {
      return this.beanTreeNode.isInvalidateOnRelogin();
   }

   public void setInvalidateOnRelogin(boolean value) {
      this.beanTreeNode.setInvalidateOnRelogin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InvalidateOnRelogin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isInvalidateOnReloginSet() {
      return this.beanTreeNode.isInvalidateOnReloginSet();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIdSet() {
      return this.beanTreeNode.isIdSet();
   }
}
