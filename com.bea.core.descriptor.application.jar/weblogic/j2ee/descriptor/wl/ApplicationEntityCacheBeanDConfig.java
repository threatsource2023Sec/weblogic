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

public class ApplicationEntityCacheBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ApplicationEntityCacheBean beanTreeNode;

   public ApplicationEntityCacheBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ApplicationEntityCacheBean)btn;
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
      return this.getEntityCacheName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEntityCacheName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EntityCacheName: ");
      sb.append(this.beanTreeNode.getEntityCacheName());
      sb.append("\n");
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

   public int getMaxBeansInCache() {
      return this.beanTreeNode.getMaxBeansInCache();
   }

   public void setMaxBeansInCache(int value) {
      this.beanTreeNode.setMaxBeansInCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxBeansInCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public MaxCacheSizeBean getMaxCacheSize() {
      return this.beanTreeNode.getMaxCacheSize();
   }

   public int getMaxQueriesInCache() {
      return this.beanTreeNode.getMaxQueriesInCache();
   }

   public void setMaxQueriesInCache(int value) {
      this.beanTreeNode.setMaxQueriesInCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxQueriesInCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCachingStrategy() {
      return this.beanTreeNode.getCachingStrategy();
   }

   public void setCachingStrategy(String value) {
      this.beanTreeNode.setCachingStrategy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CachingStrategy", (Object)null, (Object)null));
      this.setModified(true);
   }
}
