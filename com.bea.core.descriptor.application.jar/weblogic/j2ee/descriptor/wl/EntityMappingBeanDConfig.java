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

public class EntityMappingBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EntityMappingBean beanTreeNode;

   public EntityMappingBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EntityMappingBean)btn;
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
      return this.getEntityMappingName();
   }

   public void initKeyPropertyValue(String value) {
      this.setEntityMappingName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("EntityMappingName: ");
      sb.append(this.beanTreeNode.getEntityMappingName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getEntityMappingName() {
      return this.beanTreeNode.getEntityMappingName();
   }

   public void setEntityMappingName(String value) {
      this.beanTreeNode.setEntityMappingName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EntityMappingName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPublicId() {
      return this.beanTreeNode.getPublicId();
   }

   public void setPublicId(String value) {
      this.beanTreeNode.setPublicId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PublicId", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSystemId() {
      return this.beanTreeNode.getSystemId();
   }

   public void setSystemId(String value) {
      this.beanTreeNode.setSystemId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SystemId", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getEntityUri() {
      return this.beanTreeNode.getEntityUri();
   }

   public void setEntityUri(String value) {
      this.beanTreeNode.setEntityUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EntityUri", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getWhenToCache() {
      return this.beanTreeNode.getWhenToCache();
   }

   public void setWhenToCache(String value) {
      this.beanTreeNode.setWhenToCache(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "WhenToCache", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCacheTimeoutInterval() {
      return this.beanTreeNode.getCacheTimeoutInterval();
   }

   public void setCacheTimeoutInterval(int value) {
      this.beanTreeNode.setCacheTimeoutInterval(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheTimeoutInterval", (Object)null, (Object)null));
      this.setModified(true);
   }
}
