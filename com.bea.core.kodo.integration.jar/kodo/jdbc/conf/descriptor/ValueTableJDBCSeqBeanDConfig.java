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

public class ValueTableJDBCSeqBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ValueTableJDBCSeqBean beanTreeNode;

   public ValueTableJDBCSeqBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ValueTableJDBCSeqBean)btn;
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

   public int getType() {
      return this.beanTreeNode.getType();
   }

   public void setType(int value) {
      this.beanTreeNode.setType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Type", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getAllocate() {
      return this.beanTreeNode.getAllocate();
   }

   public void setAllocate(int value) {
      this.beanTreeNode.setAllocate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Allocate", (Object)null, (Object)null));
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

   public String getPrimaryKeyValue() {
      return this.beanTreeNode.getPrimaryKeyValue();
   }

   public void setPrimaryKeyValue(String value) {
      this.beanTreeNode.setPrimaryKeyValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PrimaryKeyValue", (Object)null, (Object)null));
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

   public String getSequenceColumn() {
      return this.beanTreeNode.getSequenceColumn();
   }

   public void setSequenceColumn(String value) {
      this.beanTreeNode.setSequenceColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SequenceColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getIncrement() {
      return this.beanTreeNode.getIncrement();
   }

   public void setIncrement(int value) {
      this.beanTreeNode.setIncrement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Increment", (Object)null, (Object)null));
      this.setModified(true);
   }
}
