package weblogic.j2ee.descriptor.wl60;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WeblogicRdbmsBeanBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WeblogicRdbmsBeanBean beanTreeNode;

   public WeblogicRdbmsBeanBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WeblogicRdbmsBeanBean)btn;
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
      return this.getEjbName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEjbName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EjbName: ");
      sb.append(this.beanTreeNode.getEjbName());
      sb.append("\n");
      sb.append("PoolName: ");
      sb.append(this.beanTreeNode.getPoolName());
      sb.append("\n");
      sb.append("DataSourceJndiName: ");
      sb.append(this.beanTreeNode.getDataSourceJndiName());
      sb.append("\n");
      sb.append("TableName: ");
      sb.append(this.beanTreeNode.getTableName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEjbName() {
      return this.beanTreeNode.getEjbName();
   }

   public void setEjbName(String value) {
      this.beanTreeNode.setEjbName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EjbName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPoolName() {
      return this.beanTreeNode.getPoolName();
   }

   public void setPoolName(String value) {
      this.beanTreeNode.setPoolName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PoolName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDataSourceJndiName() {
      return this.beanTreeNode.getDataSourceJndiName();
   }

   public void setDataSourceJndiName(String value) {
      this.beanTreeNode.setDataSourceJndiName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataSourceJndiName", (Object)null, (Object)null));
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

   public FieldMapBean[] getFieldMaps() {
      return this.beanTreeNode.getFieldMaps();
   }

   public FinderBean[] getFinders() {
      return this.beanTreeNode.getFinders();
   }

   public boolean isEnableTunedUpdates() {
      return this.beanTreeNode.isEnableTunedUpdates();
   }

   public void setEnableTunedUpdates(boolean value) {
      this.beanTreeNode.setEnableTunedUpdates(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableTunedUpdates", (Object)null, (Object)null));
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
