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

public class JDBCDataSourceParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCDataSourceParamsBean beanTreeNode;

   public JDBCDataSourceParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCDataSourceParamsBean)btn;
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

   public String[] getJNDINames() {
      return this.beanTreeNode.getJNDINames();
   }

   public void setJNDINames(String[] value) {
      this.beanTreeNode.setJNDINames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDINames", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getScope() {
      return this.beanTreeNode.getScope();
   }

   public void setScope(String value) {
      this.beanTreeNode.setScope(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Scope", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isRowPrefetch() {
      return this.beanTreeNode.isRowPrefetch();
   }

   public void setRowPrefetch(boolean value) {
      this.beanTreeNode.setRowPrefetch(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RowPrefetch", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRowPrefetchSize() {
      return this.beanTreeNode.getRowPrefetchSize();
   }

   public void setRowPrefetchSize(int value) {
      this.beanTreeNode.setRowPrefetchSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RowPrefetchSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStreamChunkSize() {
      return this.beanTreeNode.getStreamChunkSize();
   }

   public void setStreamChunkSize(int value) {
      this.beanTreeNode.setStreamChunkSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StreamChunkSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAlgorithmType() {
      return this.beanTreeNode.getAlgorithmType();
   }

   public void setAlgorithmType(String value) {
      this.beanTreeNode.setAlgorithmType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AlgorithmType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDataSourceList() {
      return this.beanTreeNode.getDataSourceList();
   }

   public void setDataSourceList(String value) {
      this.beanTreeNode.setDataSourceList(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataSourceList", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionPoolFailoverCallbackHandler() {
      return this.beanTreeNode.getConnectionPoolFailoverCallbackHandler();
   }

   public void setConnectionPoolFailoverCallbackHandler(String value) {
      this.beanTreeNode.setConnectionPoolFailoverCallbackHandler(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionPoolFailoverCallbackHandler", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isFailoverRequestIfBusy() {
      return this.beanTreeNode.isFailoverRequestIfBusy();
   }

   public void setFailoverRequestIfBusy(boolean value) {
      this.beanTreeNode.setFailoverRequestIfBusy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FailoverRequestIfBusy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getGlobalTransactionsProtocol() {
      return this.beanTreeNode.getGlobalTransactionsProtocol();
   }

   public void setGlobalTransactionsProtocol(String value) {
      this.beanTreeNode.setGlobalTransactionsProtocol(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GlobalTransactionsProtocol", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isKeepConnAfterLocalTx() {
      return this.beanTreeNode.isKeepConnAfterLocalTx();
   }

   public void setKeepConnAfterLocalTx(boolean value) {
      this.beanTreeNode.setKeepConnAfterLocalTx(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeepConnAfterLocalTx", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isKeepConnAfterGlobalTx() {
      return this.beanTreeNode.isKeepConnAfterGlobalTx();
   }

   public void setKeepConnAfterGlobalTx(boolean value) {
      this.beanTreeNode.setKeepConnAfterGlobalTx(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeepConnAfterGlobalTx", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProxySwitchingCallback() {
      return this.beanTreeNode.getProxySwitchingCallback();
   }

   public void setProxySwitchingCallback(String value) {
      this.beanTreeNode.setProxySwitchingCallback(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProxySwitchingCallback", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProxySwitchingProperties() {
      return this.beanTreeNode.getProxySwitchingProperties();
   }

   public void setProxySwitchingProperties(String value) {
      this.beanTreeNode.setProxySwitchingProperties(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProxySwitchingProperties", (Object)null, (Object)null));
      this.setModified(true);
   }
}
