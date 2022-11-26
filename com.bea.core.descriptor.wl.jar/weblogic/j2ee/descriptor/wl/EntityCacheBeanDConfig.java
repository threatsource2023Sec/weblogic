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

public class EntityCacheBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EntityCacheBean beanTreeNode;

   public EntityCacheBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EntityCacheBean)btn;
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

   public int getMaxBeansInCache() {
      return this.beanTreeNode.getMaxBeansInCache();
   }

   public void setMaxBeansInCache(int value) {
      this.beanTreeNode.setMaxBeansInCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxBeansInCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxQueriesInCache() {
      return this.beanTreeNode.getMaxQueriesInCache();
   }

   public void setMaxQueriesInCache(int value) {
      this.beanTreeNode.setMaxQueriesInCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxQueriesInCache", (Object)null, (Object)null));
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

   public boolean isDisableReadyInstances() {
      return this.beanTreeNode.isDisableReadyInstances();
   }

   public void setDisableReadyInstances(boolean value) {
      this.beanTreeNode.setDisableReadyInstances(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DisableReadyInstances", (Object)null, (Object)null));
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
