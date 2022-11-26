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

public class SingletonClusteringBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SingletonClusteringBean beanTreeNode;

   public SingletonClusteringBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SingletonClusteringBean)btn;
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

   public boolean isUseServersideStubs() {
      return this.beanTreeNode.isUseServersideStubs();
   }

   public void setUseServersideStubs(boolean value) {
      this.beanTreeNode.setUseServersideStubs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "UseServersideStubs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isSingletonBeanIsClusterable() {
      return this.beanTreeNode.isSingletonBeanIsClusterable();
   }

   public void setSingletonBeanIsClusterable(boolean value) {
      this.beanTreeNode.setSingletonBeanIsClusterable(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SingletonBeanIsClusterable", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSingletonBeanLoadAlgorithm() {
      return this.beanTreeNode.getSingletonBeanLoadAlgorithm();
   }

   public void setSingletonBeanLoadAlgorithm(String value) {
      this.beanTreeNode.setSingletonBeanLoadAlgorithm(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SingletonBeanLoadAlgorithm", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSingletonBeanCallRouterClassName() {
      return this.beanTreeNode.getSingletonBeanCallRouterClassName();
   }

   public void setSingletonBeanCallRouterClassName(String value) {
      this.beanTreeNode.setSingletonBeanCallRouterClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SingletonBeanCallRouterClassName", (Object)null, (Object)null));
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
