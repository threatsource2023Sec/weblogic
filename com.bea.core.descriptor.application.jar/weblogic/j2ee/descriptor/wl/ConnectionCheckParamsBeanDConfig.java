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

public class ConnectionCheckParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ConnectionCheckParamsBean beanTreeNode;

   public ConnectionCheckParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ConnectionCheckParamsBean)btn;
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

   public String getTableName() {
      return this.beanTreeNode.getTableName();
   }

   public void setTableName(String value) {
      this.beanTreeNode.setTableName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCheckOnReserveEnabled() {
      return this.beanTreeNode.isCheckOnReserveEnabled();
   }

   public void setCheckOnReserveEnabled(boolean value) {
      this.beanTreeNode.setCheckOnReserveEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CheckOnReserveEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCheckOnReleaseEnabled() {
      return this.beanTreeNode.isCheckOnReleaseEnabled();
   }

   public void setCheckOnReleaseEnabled(boolean value) {
      this.beanTreeNode.setCheckOnReleaseEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CheckOnReleaseEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getRefreshMinutes() {
      return this.beanTreeNode.getRefreshMinutes();
   }

   public void setRefreshMinutes(int value) {
      this.beanTreeNode.setRefreshMinutes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RefreshMinutes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCheckOnCreateEnabled() {
      return this.beanTreeNode.isCheckOnCreateEnabled();
   }

   public void setCheckOnCreateEnabled(boolean value) {
      this.beanTreeNode.setCheckOnCreateEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CheckOnCreateEnabled", (Object)null, (Object)null));
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

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.beanTreeNode.getConnectionCreationRetryFrequencySeconds();
   }

   public void setConnectionCreationRetryFrequencySeconds(int value) {
      this.beanTreeNode.setConnectionCreationRetryFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionCreationRetryFrequencySeconds", (Object)null, (Object)null));
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

   public int getTestFrequencySeconds() {
      return this.beanTreeNode.getTestFrequencySeconds();
   }

   public void setTestFrequencySeconds(int value) {
      this.beanTreeNode.setTestFrequencySeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TestFrequencySeconds", (Object)null, (Object)null));
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
}
