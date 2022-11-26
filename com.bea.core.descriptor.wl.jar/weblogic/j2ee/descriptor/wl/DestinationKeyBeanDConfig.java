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

public class DestinationKeyBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DestinationKeyBean beanTreeNode;

   public DestinationKeyBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DestinationKeyBean)btn;
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

   public String getProperty() {
      return this.beanTreeNode.getProperty();
   }

   public void setProperty(String value) {
      this.beanTreeNode.setProperty(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Property", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getKeyType() {
      return this.beanTreeNode.getKeyType();
   }

   public void setKeyType(String value) {
      this.beanTreeNode.setKeyType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "KeyType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSortOrder() {
      return this.beanTreeNode.getSortOrder();
   }

   public void setSortOrder(String value) {
      this.beanTreeNode.setSortOrder(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SortOrder", (Object)null, (Object)null));
      this.setModified(true);
   }
}
