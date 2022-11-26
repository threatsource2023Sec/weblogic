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

public class TableMapBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TableMapBean beanTreeNode;

   public TableMapBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TableMapBean)btn;
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
      return this.getTableName();
   }

   public void initKeyPropertyValue(String value) {
      this.setTableName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("TableName: ");
      sb.append(this.beanTreeNode.getTableName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getTableName() {
      return this.beanTreeNode.getTableName();
   }

   public void setTableName(String value) {
      this.beanTreeNode.setTableName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TableName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public FieldMapBean[] getFieldMaps() {
      return this.beanTreeNode.getFieldMaps();
   }

   public String getVerifyRows() {
      return this.beanTreeNode.getVerifyRows();
   }

   public void setVerifyRows(String value) {
      this.beanTreeNode.setVerifyRows(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VerifyRows", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getVerifyColumns() {
      return this.beanTreeNode.getVerifyColumns();
   }

   public void setVerifyColumns(String value) {
      this.beanTreeNode.setVerifyColumns(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VerifyColumns", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getOptimisticColumn() {
      return this.beanTreeNode.getOptimisticColumn();
   }

   public void setOptimisticColumn(String value) {
      this.beanTreeNode.setOptimisticColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "OptimisticColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isTriggerUpdatesOptimisticColumn() {
      return this.beanTreeNode.isTriggerUpdatesOptimisticColumn();
   }

   public void setTriggerUpdatesOptimisticColumn(boolean value) {
      this.beanTreeNode.setTriggerUpdatesOptimisticColumn(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TriggerUpdatesOptimisticColumn", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getVersionColumnInitialValue() {
      return this.beanTreeNode.getVersionColumnInitialValue();
   }

   public void setVersionColumnInitialValue(int value) {
      this.beanTreeNode.setVersionColumnInitialValue(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "VersionColumnInitialValue", (Object)null, (Object)null));
      this.setModified(true);
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
