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

public class JDBCConnectionPoolParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCConnectionPoolParamsBean beanTreeNode;

   public JDBCConnectionPoolParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCConnectionPoolParamsBean)btn;
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

   public int getInitialCapacity() {
      return this.beanTreeNode.getInitialCapacity();
   }

   public void setInitialCapacity(int value) {
      this.beanTreeNode.setInitialCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxCapacity() {
      return this.beanTreeNode.getMaxCapacity();
   }

   public void setMaxCapacity(int value) {
      this.beanTreeNode.setMaxCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMinCapacity() {
      return this.beanTreeNode.getMinCapacity();
   }

   public void setMinCapacity(int value) {
      this.beanTreeNode.setMinCapacity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MinCapacity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCapacityIncrement() {
      return this.beanTreeNode.getCapacityIncrement();
   }

   public void setCapacityIncrement(int value) {
      this.beanTreeNode.setCapacityIncrement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CapacityIncrement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getShrinkFrequencySeconds() {
      return this.beanTreeNode.getShrinkFrequencySeconds();
   }

   public void setShrinkFrequencySeconds(int value) {
      this.beanTreeNode.setShrinkFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ShrinkFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getHighestNumWaiters() {
      return this.beanTreeNode.getHighestNumWaiters();
   }

   public void setHighestNumWaiters(int value) {
      this.beanTreeNode.setHighestNumWaiters(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HighestNumWaiters", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.beanTreeNode.getConnectionCreationRetryFrequencySeconds();
   }

   public void setConnectionCreationRetryFrequencySeconds(int value) {
      this.beanTreeNode.setConnectionCreationRetryFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionCreationRetryFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.beanTreeNode.getConnectionReserveTimeoutSeconds();
   }

   public void setConnectionReserveTimeoutSeconds(int value) {
      this.beanTreeNode.setConnectionReserveTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionReserveTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getTestFrequencySeconds() {
      return this.beanTreeNode.getTestFrequencySeconds();
   }

   public void setTestFrequencySeconds(int value) {
      this.beanTreeNode.setTestFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTestConnectionsOnReserve() {
      return this.beanTreeNode.isTestConnectionsOnReserve();
   }

   public void setTestConnectionsOnReserve(boolean value) {
      this.beanTreeNode.setTestConnectionsOnReserve(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestConnectionsOnReserve", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getProfileHarvestFrequencySeconds() {
      return this.beanTreeNode.getProfileHarvestFrequencySeconds();
   }

   public void setProfileHarvestFrequencySeconds(int value) {
      this.beanTreeNode.setProfileHarvestFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProfileHarvestFrequencySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      return this.beanTreeNode.isIgnoreInUseConnectionsEnabled();
   }

   public void setIgnoreInUseConnectionsEnabled(boolean value) {
      this.beanTreeNode.setIgnoreInUseConnectionsEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IgnoreInUseConnectionsEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this.beanTreeNode.getInactiveConnectionTimeoutSeconds();
   }

   public void setInactiveConnectionTimeoutSeconds(int value) {
      this.beanTreeNode.setInactiveConnectionTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InactiveConnectionTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTestTableName() {
      return this.beanTreeNode.getTestTableName();
   }

   public void setTestTableName(String value) {
      this.beanTreeNode.setTestTableName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestTableName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getLoginDelaySeconds() {
      return this.beanTreeNode.getLoginDelaySeconds();
   }

   public void setLoginDelaySeconds(int value) {
      this.beanTreeNode.setLoginDelaySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoginDelaySeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInitSql() {
      return this.beanTreeNode.getInitSql();
   }

   public void setInitSql(String value) {
      this.beanTreeNode.setInitSql(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitSql", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStatementCacheSize() {
      return this.beanTreeNode.getStatementCacheSize();
   }

   public void setStatementCacheSize(int value) {
      this.beanTreeNode.setStatementCacheSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StatementCacheSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getStatementCacheType() {
      return this.beanTreeNode.getStatementCacheType();
   }

   public void setStatementCacheType(String value) {
      this.beanTreeNode.setStatementCacheType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StatementCacheType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRemoveInfectedConnections() {
      return this.beanTreeNode.isRemoveInfectedConnections();
   }

   public void setRemoveInfectedConnections(boolean value) {
      this.beanTreeNode.setRemoveInfectedConnections(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RemoveInfectedConnections", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getSecondsToTrustAnIdlePoolConnection() {
      return this.beanTreeNode.getSecondsToTrustAnIdlePoolConnection();
   }

   public void setSecondsToTrustAnIdlePoolConnection(int value) {
      this.beanTreeNode.setSecondsToTrustAnIdlePoolConnection(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SecondsToTrustAnIdlePoolConnection", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStatementTimeout() {
      return this.beanTreeNode.getStatementTimeout();
   }

   public void setStatementTimeout(int value) {
      this.beanTreeNode.setStatementTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StatementTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getProfileType() {
      return this.beanTreeNode.getProfileType();
   }

   public void setProfileType(int value) {
      this.beanTreeNode.setProfileType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProfileType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getJDBCXADebugLevel() {
      return this.beanTreeNode.getJDBCXADebugLevel();
   }

   public void setJDBCXADebugLevel(int value) {
      this.beanTreeNode.setJDBCXADebugLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JDBCXADebugLevel", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCredentialMappingEnabled() {
      return this.beanTreeNode.isCredentialMappingEnabled();
   }

   public void setCredentialMappingEnabled(boolean value) {
      this.beanTreeNode.setCredentialMappingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CredentialMappingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDriverInterceptor() {
      return this.beanTreeNode.getDriverInterceptor();
   }

   public void setDriverInterceptor(String value) {
      this.beanTreeNode.setDriverInterceptor(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DriverInterceptor", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isPinnedToThread() {
      return this.beanTreeNode.isPinnedToThread();
   }

   public void setPinnedToThread(boolean value) {
      this.beanTreeNode.setPinnedToThread(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PinnedToThread", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isIdentityBasedConnectionPoolingEnabled() {
      return this.beanTreeNode.isIdentityBasedConnectionPoolingEnabled();
   }

   public void setIdentityBasedConnectionPoolingEnabled(boolean value) {
      this.beanTreeNode.setIdentityBasedConnectionPoolingEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IdentityBasedConnectionPoolingEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isWrapTypes() {
      return this.beanTreeNode.isWrapTypes();
   }

   public void setWrapTypes(boolean value) {
      this.beanTreeNode.setWrapTypes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WrapTypes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFatalErrorCodes() {
      return this.beanTreeNode.getFatalErrorCodes();
   }

   public void setFatalErrorCodes(String value) {
      this.beanTreeNode.setFatalErrorCodes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FatalErrorCodes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionLabelingCallback() {
      return this.beanTreeNode.getConnectionLabelingCallback();
   }

   public void setConnectionLabelingCallback(String value) {
      this.beanTreeNode.setConnectionLabelingCallback(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionLabelingCallback", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionHarvestMaxCount() {
      return this.beanTreeNode.getConnectionHarvestMaxCount();
   }

   public void setConnectionHarvestMaxCount(int value) {
      this.beanTreeNode.setConnectionHarvestMaxCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionHarvestMaxCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getConnectionHarvestTriggerCount() {
      return this.beanTreeNode.getConnectionHarvestTriggerCount();
   }

   public void setConnectionHarvestTriggerCount(int value) {
      this.beanTreeNode.setConnectionHarvestTriggerCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionHarvestTriggerCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCountOfTestFailuresTillFlush() {
      return this.beanTreeNode.getCountOfTestFailuresTillFlush();
   }

   public void setCountOfTestFailuresTillFlush(int value) {
      this.beanTreeNode.setCountOfTestFailuresTillFlush(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CountOfTestFailuresTillFlush", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCountOfRefreshFailuresTillDisable() {
      return this.beanTreeNode.getCountOfRefreshFailuresTillDisable();
   }

   public void setCountOfRefreshFailuresTillDisable(int value) {
      this.beanTreeNode.setCountOfRefreshFailuresTillDisable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CountOfRefreshFailuresTillDisable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isWrapJdbc() {
      return this.beanTreeNode.isWrapJdbc();
   }

   public void setWrapJdbc(boolean value) {
      this.beanTreeNode.setWrapJdbc(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WrapJdbc", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getProfileConnectionLeakTimeoutSeconds() {
      return this.beanTreeNode.getProfileConnectionLeakTimeoutSeconds();
   }

   public void setProfileConnectionLeakTimeoutSeconds(int value) {
      this.beanTreeNode.setProfileConnectionLeakTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProfileConnectionLeakTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isInvokeBeginEndRequest() {
      return this.beanTreeNode.isInvokeBeginEndRequest();
   }

   public void setInvokeBeginEndRequest(boolean value) {
      this.beanTreeNode.setInvokeBeginEndRequest(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InvokeBeginEndRequest", (Object)null, (Object)null));
      this.setModified(true);
   }
}
