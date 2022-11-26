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

public class MaxThreadsConstraintBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MaxThreadsConstraintBean beanTreeNode;

   public MaxThreadsConstraintBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MaxThreadsConstraintBean)btn;
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
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getCount() {
      return this.beanTreeNode.getCount();
   }

   public void setCount(int value) {
      this.beanTreeNode.setCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Count", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getPoolName() {
      return this.beanTreeNode.getPoolName();
   }

   public void setPoolName(String value) {
      this.beanTreeNode.setPoolName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PoolName", (Object)null, (Object)null));
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

   public int getQueueSize() {
      return this.beanTreeNode.getQueueSize();
   }

   public void setQueueSize(int value) {
      this.beanTreeNode.setQueueSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "QueueSize", (Object)null, (Object)null));
      this.setModified(true);
   }
}
