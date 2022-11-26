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

public class StatefulSessionCacheBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private StatefulSessionCacheBean beanTreeNode;

   public StatefulSessionCacheBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (StatefulSessionCacheBean)btn;
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

   public int getIdleTimeoutSeconds() {
      return this.beanTreeNode.getIdleTimeoutSeconds();
   }

   public void setIdleTimeoutSeconds(int value) {
      this.beanTreeNode.setIdleTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IdleTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getSessionTimeoutSeconds() {
      return this.beanTreeNode.getSessionTimeoutSeconds();
   }

   public void setSessionTimeoutSeconds(int value) {
      this.beanTreeNode.setSessionTimeoutSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SessionTimeoutSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCacheType() {
      return this.beanTreeNode.getCacheType();
   }

   public void setCacheType(String value) {
      this.beanTreeNode.setCacheType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CacheType", (Object)null, (Object)null));
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
