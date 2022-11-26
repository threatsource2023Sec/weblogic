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

public class EntityClusteringBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private EntityClusteringBean beanTreeNode;

   public EntityClusteringBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (EntityClusteringBean)btn;
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

   public boolean isHomeIsClusterable() {
      return this.beanTreeNode.isHomeIsClusterable();
   }

   public void setHomeIsClusterable(boolean value) {
      this.beanTreeNode.setHomeIsClusterable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HomeIsClusterable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getHomeLoadAlgorithm() {
      return this.beanTreeNode.getHomeLoadAlgorithm();
   }

   public void setHomeLoadAlgorithm(String value) {
      this.beanTreeNode.setHomeLoadAlgorithm(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HomeLoadAlgorithm", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getHomeCallRouterClassName() {
      return this.beanTreeNode.getHomeCallRouterClassName();
   }

   public void setHomeCallRouterClassName(String value) {
      this.beanTreeNode.setHomeCallRouterClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HomeCallRouterClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUseServersideStubs() {
      return this.beanTreeNode.isUseServersideStubs();
   }

   public void setUseServersideStubs(boolean value) {
      this.beanTreeNode.setUseServersideStubs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseServersideStubs", (Object)null, (Object)null));
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
