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

public class JDBCDriverParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private JDBCDriverParamsBean beanTreeNode;

   public JDBCDriverParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (JDBCDriverParamsBean)btn;
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

   public String getUrl() {
      return this.beanTreeNode.getUrl();
   }

   public void setUrl(String value) {
      this.beanTreeNode.setUrl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Url", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDriverName() {
      return this.beanTreeNode.getDriverName();
   }

   public void setDriverName(String value) {
      this.beanTreeNode.setDriverName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DriverName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public JDBCPropertiesBean getProperties() {
      return this.beanTreeNode.getProperties();
   }

   public byte[] getPasswordEncrypted() {
      return this.beanTreeNode.getPasswordEncrypted();
   }

   public void setPasswordEncrypted(byte[] value) {
      this.beanTreeNode.setPasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseXaDataSourceInterface() {
      return this.beanTreeNode.isUseXaDataSourceInterface();
   }

   public void setUseXaDataSourceInterface(boolean value) {
      this.beanTreeNode.setUseXaDataSourceInterface(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseXaDataSourceInterface", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPassword() {
      return this.beanTreeNode.getPassword();
   }

   public void setPassword(String value) {
      this.beanTreeNode.setPassword(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Password", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUsePasswordIndirection() {
      return this.beanTreeNode.isUsePasswordIndirection();
   }

   public void setUsePasswordIndirection(boolean value) {
      this.beanTreeNode.setUsePasswordIndirection(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UsePasswordIndirection", (Object)null, (Object)null));
      this.setModified(true);
   }
}
