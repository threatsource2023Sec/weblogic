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

public class EntityDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EntityDescriptorBean beanTreeNode;

   public EntityDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EntityDescriptorBean)btn;
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

   public PoolBean getPool() {
      return this.beanTreeNode.getPool();
   }

   public boolean isPoolSet() {
      return this.beanTreeNode.isPoolSet();
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this.beanTreeNode.getTimerDescriptor();
   }

   public boolean isTimerDescriptorSet() {
      return this.beanTreeNode.isTimerDescriptorSet();
   }

   public EntityCacheBean getEntityCache() {
      return this.beanTreeNode.getEntityCache();
   }

   public boolean isEntityCacheSet() {
      return this.beanTreeNode.isEntityCacheSet();
   }

   public EntityCacheRefBean getEntityCacheRef() {
      return this.beanTreeNode.getEntityCacheRef();
   }

   public boolean isEntityCacheRefSet() {
      return this.beanTreeNode.isEntityCacheRefSet();
   }

   public PersistenceBean getPersistence() {
      return this.beanTreeNode.getPersistence();
   }

   public boolean isPersistenceSet() {
      return this.beanTreeNode.isPersistenceSet();
   }

   public EntityClusteringBean getEntityClustering() {
      return this.beanTreeNode.getEntityClustering();
   }

   public boolean isEntityClusteringSet() {
      return this.beanTreeNode.isEntityClusteringSet();
   }

   public InvalidationTargetBean getInvalidationTarget() {
      return this.beanTreeNode.getInvalidationTarget();
   }

   public boolean isInvalidationTargetSet() {
      return this.beanTreeNode.isInvalidationTargetSet();
   }

   public boolean isEnableDynamicQueries() {
      return this.beanTreeNode.isEnableDynamicQueries();
   }

   public void setEnableDynamicQueries(boolean value) {
      this.beanTreeNode.setEnableDynamicQueries(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableDynamicQueries", (Object)null, (Object)null));
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
