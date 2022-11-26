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

public class AdminObjectGroupBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AdminObjectGroupBean beanTreeNode;

   public AdminObjectGroupBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AdminObjectGroupBean)btn;
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

   public String getAdminObjectInterface() {
      return this.beanTreeNode.getAdminObjectInterface();
   }

   public void setAdminObjectInterface(String value) {
      this.beanTreeNode.setAdminObjectInterface(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AdminObjectInterface", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAdminObjectClass() {
      return this.beanTreeNode.getAdminObjectClass();
   }

   public void setAdminObjectClass(String value) {
      this.beanTreeNode.setAdminObjectClass(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AdminObjectClass", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AdminObjectInstanceBean[] getAdminObjectInstances() {
      return this.beanTreeNode.getAdminObjectInstances();
   }

   public ConfigPropertiesBean getDefaultProperties() {
      return this.beanTreeNode.getDefaultProperties();
   }

   public boolean isDefaultPropertiesSet() {
      return this.beanTreeNode.isDefaultPropertiesSet();
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
