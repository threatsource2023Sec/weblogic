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
      sb.append("DataSourceJNDIName: ");
      sb.append(this.beanTreeNode.getDataSourceJNDIName());
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

   public String getDataSourceJNDIName() {
      return this.beanTreeNode.getDataSourceJNDIName();
   }

   public void setDataSourceJNDIName(String value) {
      this.beanTreeNode.setDataSourceJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataSourceJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public UnknownPrimaryKeyFieldBean getUnknownPrimaryKeyField() {
      return this.beanTreeNode.getUnknownPrimaryKeyField();
   }

   public TableMapBean[] getTableMaps() {
      return this.beanTreeNode.getTableMaps();
   }

   public FieldGroupBean[] getFieldGroups() {
      return this.beanTreeNode.getFieldGroups();
   }

   public RelationshipCachingBean[] getRelationshipCachings() {
      return this.beanTreeNode.getRelationshipCachings();
   }

   public SqlShapeBean[] getSqlShapes() {
      return this.beanTreeNode.getSqlShapes();
   }

   public WeblogicQueryBean[] getWeblogicQueries() {
      return this.beanTreeNode.getWeblogicQueries();
   }

   public String getDelayDatabaseInsertUntil() {
      return this.beanTreeNode.getDelayDatabaseInsertUntil();
   }

   public void setDelayDatabaseInsertUntil(String value) {
      this.beanTreeNode.setDelayDatabaseInsertUntil(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DelayDatabaseInsertUntil", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseSelectForUpdate() {
      return this.beanTreeNode.isUseSelectForUpdate();
   }

   public void setUseSelectForUpdate(boolean value) {
      this.beanTreeNode.setUseSelectForUpdate(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseSelectForUpdate", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getLockOrder() {
      return this.beanTreeNode.getLockOrder();
   }

   public void setLockOrder(int value) {
      this.beanTreeNode.setLockOrder(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LockOrder", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInstanceLockOrder() {
      return this.beanTreeNode.getInstanceLockOrder();
   }

   public void setInstanceLockOrder(String value) {
      this.beanTreeNode.setInstanceLockOrder(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InstanceLockOrder", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AutomaticKeyGenerationBean getAutomaticKeyGeneration() {
      return this.beanTreeNode.getAutomaticKeyGeneration();
   }

   public boolean isCheckExistsOnMethod() {
      return this.beanTreeNode.isCheckExistsOnMethod();
   }

   public void setCheckExistsOnMethod(boolean value) {
      this.beanTreeNode.setCheckExistsOnMethod(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CheckExistsOnMethod", (Object)null, (Object)null));
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

   public boolean isClusterInvalidationDisabled() {
      return this.beanTreeNode.isClusterInvalidationDisabled();
   }

   public void setClusterInvalidationDisabled(boolean value) {
      this.beanTreeNode.setClusterInvalidationDisabled(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClusterInvalidationDisabled", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseInnerJoin() {
      return this.beanTreeNode.isUseInnerJoin();
   }

   public void setUseInnerJoin(boolean value) {
      this.beanTreeNode.setUseInnerJoin(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseInnerJoin", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCategoryCmpField() {
      return this.beanTreeNode.getCategoryCmpField();
   }

   public void setCategoryCmpField(String value) {
      this.beanTreeNode.setCategoryCmpField(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CategoryCmpField", (Object)null, (Object)null));
      this.setModified(true);
   }
}
