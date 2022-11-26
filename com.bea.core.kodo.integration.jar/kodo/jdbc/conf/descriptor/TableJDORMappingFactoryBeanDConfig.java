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

public class TableJDORMappingFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TableJDORMappingFactoryBean beanTreeNode;

   public TableJDORMappingFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TableJDORMappingFactoryBean)btn;
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

   public boolean getUseSchemaValidation() {
      return this.beanTreeNode.getUseSchemaValidation();
   }

   public void setUseSchemaValidation(boolean value) {
      this.beanTreeNode.setUseSchemaValidation(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSchemaValidation", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTypeColumn() {
      return this.beanTreeNode.getTypeColumn();
   }

   public void setTypeColumn(String value) {
      this.beanTreeNode.setTypeColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TypeColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getConstraintNames() {
      return this.beanTreeNode.getConstraintNames();
   }

   public void setConstraintNames(boolean value) {
      this.beanTreeNode.setConstraintNames(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConstraintNames", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTable() {
      return this.beanTreeNode.getTable();
   }

   public void setTable(String value) {
      this.beanTreeNode.setTable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Table", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTypes() {
      return this.beanTreeNode.getTypes();
   }

   public void setTypes(String value) {
      this.beanTreeNode.setTypes(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Types", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStoreMode() {
      return this.beanTreeNode.getStoreMode();
   }

   public void setStoreMode(int value) {
      this.beanTreeNode.setStoreMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StoreMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMappingColumn() {
      return this.beanTreeNode.getMappingColumn();
   }

   public void setMappingColumn(String value) {
      this.beanTreeNode.setMappingColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MappingColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStrict() {
      return this.beanTreeNode.getStrict();
   }

   public void setStrict(boolean value) {
      this.beanTreeNode.setStrict(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Strict", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getNameColumn() {
      return this.beanTreeNode.getNameColumn();
   }

   public void setNameColumn(String value) {
      this.beanTreeNode.setNameColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NameColumn", (Object)null, (Object)null));
      this.setModified(true);
   }
}
