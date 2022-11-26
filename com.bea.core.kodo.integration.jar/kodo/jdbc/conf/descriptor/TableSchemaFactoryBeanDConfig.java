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

public class TableSchemaFactoryBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TableSchemaFactoryBean beanTreeNode;

   public TableSchemaFactoryBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TableSchemaFactoryBean)btn;
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

   public String getSchemaColumn() {
      return this.beanTreeNode.getSchemaColumn();
   }

   public void setSchemaColumn(String value) {
      this.beanTreeNode.setSchemaColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SchemaColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTableName() {
      return this.beanTreeNode.getTableName();
   }

   public void setTableName(String value) {
      this.beanTreeNode.setTableName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableName", (Object)null, (Object)null));
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

   public String getPrimaryKeyColumn() {
      return this.beanTreeNode.getPrimaryKeyColumn();
   }

   public void setPrimaryKeyColumn(String value) {
      this.beanTreeNode.setPrimaryKeyColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrimaryKeyColumn", (Object)null, (Object)null));
      this.setModified(true);
   }
}
