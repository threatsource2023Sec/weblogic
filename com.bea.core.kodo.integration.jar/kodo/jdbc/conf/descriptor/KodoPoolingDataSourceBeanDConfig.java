package kodo.jdbc.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class KodoPoolingDataSourceBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private KodoPoolingDataSourceBean beanTreeNode;

   public KodoPoolingDataSourceBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (KodoPoolingDataSourceBean)btn;
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

   public String getConnectionUserName() {
      return this.beanTreeNode.getConnectionUserName();
   }

   public void setConnectionUserName(String value) {
      this.beanTreeNode.setConnectionUserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionUserName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getLoginTimeout() {
      return this.beanTreeNode.getLoginTimeout();
   }

   public void setLoginTimeout(int value) {
      this.beanTreeNode.setLoginTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoginTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionPassword() {
      return this.beanTreeNode.getConnectionPassword();
   }

   public void setConnectionPassword(String value) {
      this.beanTreeNode.setConnectionPassword(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionPassword", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getConnectionPasswordEncrypted() {
      return this.beanTreeNode.getConnectionPasswordEncrypted();
   }

   public void setConnectionPasswordEncrypted(byte[] value) {
      this.beanTreeNode.setConnectionPasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionPasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionURL() {
      return this.beanTreeNode.getConnectionURL();
   }

   public void setConnectionURL(String value) {
      this.beanTreeNode.setConnectionURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionURL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionDriverName() {
      return this.beanTreeNode.getConnectionDriverName();
   }

   public void setConnectionDriverName(String value) {
      this.beanTreeNode.setConnectionDriverName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionDriverName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
