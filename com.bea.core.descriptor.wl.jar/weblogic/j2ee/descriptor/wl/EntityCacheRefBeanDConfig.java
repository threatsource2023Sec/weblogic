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

public class EntityCacheRefBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EntityCacheRefBean beanTreeNode;

   public EntityCacheRefBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EntityCacheRefBean)btn;
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

   public String getEntityCacheName() {
      return this.beanTreeNode.getEntityCacheName();
   }

   public void setEntityCacheName(String value) {
      this.beanTreeNode.setEntityCacheName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EntityCacheName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getIdleTimeoutSeconds() {
      return this.beanTreeNode.getIdleTimeoutSeconds();
   }

   public void setIdleTimeoutSeconds(int value) {
      this.beanTreeNode.setIdleTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IdleTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getReadTimeoutSeconds() {
      return this.beanTreeNode.getReadTimeoutSeconds();
   }

   public void setReadTimeoutSeconds(int value) {
      this.beanTreeNode.setReadTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReadTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConcurrencyStrategy() {
      return this.beanTreeNode.getConcurrencyStrategy();
   }

   public void setConcurrencyStrategy(String value) {
      this.beanTreeNode.setConcurrencyStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConcurrencyStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isCacheBetweenTransactions() {
      return this.beanTreeNode.isCacheBetweenTransactions();
   }

   public void setCacheBetweenTransactions(boolean value) {
      this.beanTreeNode.setCacheBetweenTransactions(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheBetweenTransactions", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getEstimatedBeanSize() {
      return this.beanTreeNode.getEstimatedBeanSize();
   }

   public void setEstimatedBeanSize(int value) {
      this.beanTreeNode.setEstimatedBeanSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EstimatedBeanSize", (Object)null, (Object)null));
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
