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

public class JDBCXAParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCXAParamsBean beanTreeNode;

   public JDBCXAParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCXAParamsBean)btn;
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

   public boolean isKeepXaConnTillTxComplete() {
      return this.beanTreeNode.isKeepXaConnTillTxComplete();
   }

   public void setKeepXaConnTillTxComplete(boolean value) {
      this.beanTreeNode.setKeepXaConnTillTxComplete(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeepXaConnTillTxComplete", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isNeedTxCtxOnClose() {
      return this.beanTreeNode.isNeedTxCtxOnClose();
   }

   public void setNeedTxCtxOnClose(boolean value) {
      this.beanTreeNode.setNeedTxCtxOnClose(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NeedTxCtxOnClose", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isXaEndOnlyOnce() {
      return this.beanTreeNode.isXaEndOnlyOnce();
   }

   public void setXaEndOnlyOnce(boolean value) {
      this.beanTreeNode.setXaEndOnlyOnce(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XaEndOnlyOnce", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isNewXaConnForCommit() {
      return this.beanTreeNode.isNewXaConnForCommit();
   }

   public void setNewXaConnForCommit(boolean value) {
      this.beanTreeNode.setNewXaConnForCommit(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NewXaConnForCommit", (Object)null, (Object)null));
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

   public boolean isResourceHealthMonitoring() {
      return this.beanTreeNode.isResourceHealthMonitoring();
   }

   public void setResourceHealthMonitoring(boolean value) {
      this.beanTreeNode.setResourceHealthMonitoring(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceHealthMonitoring", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRecoverOnlyOnce() {
      return this.beanTreeNode.isRecoverOnlyOnce();
   }

   public void setRecoverOnlyOnce(boolean value) {
      this.beanTreeNode.setRecoverOnlyOnce(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RecoverOnlyOnce", (Object)null, (Object)null));
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

   public boolean isRollbackLocalTxUponConnClose() {
      return this.beanTreeNode.isRollbackLocalTxUponConnClose();
   }

   public void setRollbackLocalTxUponConnClose(boolean value) {
      this.beanTreeNode.setRollbackLocalTxUponConnClose(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RollbackLocalTxUponConnClose", (Object)null, (Object)null));
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

   public int getXaRetryIntervalSeconds() {
      return this.beanTreeNode.getXaRetryIntervalSeconds();
   }

   public void setXaRetryIntervalSeconds(int value) {
      this.beanTreeNode.setXaRetryIntervalSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "XaRetryIntervalSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }
}
