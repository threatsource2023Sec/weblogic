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

public class XAParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private XAParamsBean beanTreeNode;

   public XAParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (XAParamsBean)btn;
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

   public int getDebugLevel() {
      return this.beanTreeNode.getDebugLevel();
   }

   public void setDebugLevel(int value) {
      this.beanTreeNode.setDebugLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DebugLevel", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isKeepConnUntilTxCompleteEnabled() {
      return this.beanTreeNode.isKeepConnUntilTxCompleteEnabled();
   }

   public void setKeepConnUntilTxCompleteEnabled(boolean value) {
      this.beanTreeNode.setKeepConnUntilTxCompleteEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeepConnUntilTxCompleteEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEndOnlyOnceEnabled() {
      return this.beanTreeNode.isEndOnlyOnceEnabled();
   }

   public void setEndOnlyOnceEnabled(boolean value) {
      this.beanTreeNode.setEndOnlyOnceEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EndOnlyOnceEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRecoverOnlyOnceEnabled() {
      return this.beanTreeNode.isRecoverOnlyOnceEnabled();
   }

   public void setRecoverOnlyOnceEnabled(boolean value) {
      this.beanTreeNode.setRecoverOnlyOnceEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RecoverOnlyOnceEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTxContextOnCloseNeeded() {
      return this.beanTreeNode.isTxContextOnCloseNeeded();
   }

   public void setTxContextOnCloseNeeded(boolean value) {
      this.beanTreeNode.setTxContextOnCloseNeeded(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TxContextOnCloseNeeded", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isNewConnForCommitEnabled() {
      return this.beanTreeNode.isNewConnForCommitEnabled();
   }

   public void setNewConnForCommitEnabled(boolean value) {
      this.beanTreeNode.setNewConnForCommitEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NewConnForCommitEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getPreparedStatementCacheSize() {
      return this.beanTreeNode.getPreparedStatementCacheSize();
   }

   public void setPreparedStatementCacheSize(int value) {
      this.beanTreeNode.setPreparedStatementCacheSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PreparedStatementCacheSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isKeepLogicalConnOpenOnRelease() {
      return this.beanTreeNode.isKeepLogicalConnOpenOnRelease();
   }

   public void setKeepLogicalConnOpenOnRelease(boolean value) {
      this.beanTreeNode.setKeepLogicalConnOpenOnRelease(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeepLogicalConnOpenOnRelease", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isLocalTransactionSupported() {
      return this.beanTreeNode.isLocalTransactionSupported();
   }

   public void setLocalTransactionSupported(boolean value) {
      this.beanTreeNode.setLocalTransactionSupported(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LocalTransactionSupported", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isResourceHealthMonitoringEnabled() {
      return this.beanTreeNode.isResourceHealthMonitoringEnabled();
   }

   public void setResourceHealthMonitoringEnabled(boolean value) {
      this.beanTreeNode.setResourceHealthMonitoringEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceHealthMonitoringEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isXaSetTransactionTimeout() {
      return this.beanTreeNode.isXaSetTransactionTimeout();
   }

   public void setXaSetTransactionTimeout(boolean value) {
      this.beanTreeNode.setXaSetTransactionTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XaSetTransactionTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getXaTransactionTimeout() {
      return this.beanTreeNode.getXaTransactionTimeout();
   }

   public void setXaTransactionTimeout(int value) {
      this.beanTreeNode.setXaTransactionTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XaTransactionTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRollbackLocaltxUponConnclose() {
      return this.beanTreeNode.isRollbackLocaltxUponConnclose();
   }

   public void setRollbackLocaltxUponConnclose(boolean value) {
      this.beanTreeNode.setRollbackLocaltxUponConnclose(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RollbackLocaltxUponConnclose", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getXaRetryDurationSeconds() {
      return this.beanTreeNode.getXaRetryDurationSeconds();
   }

   public void setXaRetryDurationSeconds(int value) {
      this.beanTreeNode.setXaRetryDurationSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XaRetryDurationSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }
}
