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

public class PoolBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PoolBean beanTreeNode;

   public PoolBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PoolBean)btn;
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

   public int getMaxBeansInFreePool() {
      return this.beanTreeNode.getMaxBeansInFreePool();
   }

   public void setMaxBeansInFreePool(int value) {
      this.beanTreeNode.setMaxBeansInFreePool(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxBeansInFreePool", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getInitialBeansInFreePool() {
      return this.beanTreeNode.getInitialBeansInFreePool();
   }

   public void setInitialBeansInFreePool(int value) {
      this.beanTreeNode.setInitialBeansInFreePool(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialBeansInFreePool", (Object)null, (Object)null));
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

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
