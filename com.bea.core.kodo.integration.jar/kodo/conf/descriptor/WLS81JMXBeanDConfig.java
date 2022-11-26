package kodo.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLS81JMXBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLS81JMXBean beanTreeNode;

   public WLS81JMXBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLS81JMXBean)btn;
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

   public String getURL() {
      return this.beanTreeNode.getURL();
   }

   public void setURL(String value) {
      this.beanTreeNode.setURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "URL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getUserName() {
      return this.beanTreeNode.getUserName();
   }

   public void setUserName(String value) {
      this.beanTreeNode.setUserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UserName", (Object)null, (Object)null));
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
}
