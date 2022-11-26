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

public class LazySchemaFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private LazySchemaFactoryBean beanTreeNode;

   public LazySchemaFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (LazySchemaFactoryBean)btn;
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

   public boolean getForeignKeys() {
      return this.beanTreeNode.getForeignKeys();
   }

   public void setForeignKeys(boolean value) {
      this.beanTreeNode.setForeignKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ForeignKeys", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getIndexes() {
      return this.beanTreeNode.getIndexes();
   }

   public void setIndexes(boolean value) {
      this.beanTreeNode.setIndexes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Indexes", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getPrimaryKeys() {
      return this.beanTreeNode.getPrimaryKeys();
   }

   public void setPrimaryKeys(boolean value) {
      this.beanTreeNode.setPrimaryKeys(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrimaryKeys", (Object)null, (Object)null));
      this.setModified(true);
   }
}
