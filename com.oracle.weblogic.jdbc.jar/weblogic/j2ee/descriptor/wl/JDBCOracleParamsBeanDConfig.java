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

public class JDBCOracleParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCOracleParamsBean beanTreeNode;

   public JDBCOracleParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCOracleParamsBean)btn;
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

   public boolean isFanEnabled() {
      return this.beanTreeNode.isFanEnabled();
   }

   public void setFanEnabled(boolean value) {
      this.beanTreeNode.setFanEnabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FanEnabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOnsNodeList() {
      return this.beanTreeNode.getOnsNodeList();
   }

   public void setOnsNodeList(String value) {
      this.beanTreeNode.setOnsNodeList(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OnsNodeList", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOnsWalletFile() {
      return this.beanTreeNode.getOnsWalletFile();
   }

   public void setOnsWalletFile(String value) {
      this.beanTreeNode.setOnsWalletFile(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OnsWalletFile", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getOnsWalletPasswordEncrypted() {
      return this.beanTreeNode.getOnsWalletPasswordEncrypted();
   }

   public void setOnsWalletPasswordEncrypted(byte[] value) {
      this.beanTreeNode.setOnsWalletPasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OnsWalletPasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOnsWalletPassword() {
      return this.beanTreeNode.getOnsWalletPassword();
   }

   public void setOnsWalletPassword(String value) {
      this.beanTreeNode.setOnsWalletPassword(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OnsWalletPassword", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOracleEnableJavaNetFastPath() {
      return this.beanTreeNode.isOracleEnableJavaNetFastPath();
   }

   public void setOracleEnableJavaNetFastPath(boolean value) {
      this.beanTreeNode.setOracleEnableJavaNetFastPath(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OracleEnableJavaNetFastPath", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOracleOptimizeUtf8Conversion() {
      return this.beanTreeNode.isOracleOptimizeUtf8Conversion();
   }

   public void setOracleOptimizeUtf8Conversion(boolean value) {
      this.beanTreeNode.setOracleOptimizeUtf8Conversion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OracleOptimizeUtf8Conversion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionInitializationCallback() {
      return this.beanTreeNode.getConnectionInitializationCallback();
   }

   public void setConnectionInitializationCallback(String value) {
      this.beanTreeNode.setConnectionInitializationCallback(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionInitializationCallback", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAffinityPolicy() {
      return this.beanTreeNode.getAffinityPolicy();
   }

   public void setAffinityPolicy(String value) {
      this.beanTreeNode.setAffinityPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AffinityPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isOracleProxySession() {
      return this.beanTreeNode.isOracleProxySession();
   }

   public void setOracleProxySession(boolean value) {
      this.beanTreeNode.setOracleProxySession(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OracleProxySession", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseDatabaseCredentials() {
      return this.beanTreeNode.isUseDatabaseCredentials();
   }

   public void setUseDatabaseCredentials(boolean value) {
      this.beanTreeNode.setUseDatabaseCredentials(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseDatabaseCredentials", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getReplayInitiationTimeout() {
      return this.beanTreeNode.getReplayInitiationTimeout();
   }

   public void setReplayInitiationTimeout(int value) {
      this.beanTreeNode.setReplayInitiationTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReplayInitiationTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isActiveGridlink() {
      return this.beanTreeNode.isActiveGridlink();
   }

   public void setActiveGridlink(boolean value) {
      this.beanTreeNode.setActiveGridlink(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ActiveGridlink", (Object)null, (Object)null));
      this.setModified(true);
   }
}
