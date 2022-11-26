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

public class ApplicationAdminModeTriggerBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ApplicationAdminModeTriggerBean beanTreeNode;

   public ApplicationAdminModeTriggerBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ApplicationAdminModeTriggerBean)btn;
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
      return this.getId();
   }

   public void initKeyPropertyValue(String value) {
      this.setId(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Id: ");
      sb.append(this.beanTreeNode.getId());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public int getMaxStuckThreadTime() {
      return this.beanTreeNode.getMaxStuckThreadTime();
   }

   public void setMaxStuckThreadTime(int value) {
      this.beanTreeNode.setMaxStuckThreadTime(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxStuckThreadTime", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getStuckThreadCount() {
      return this.beanTreeNode.getStuckThreadCount();
   }

   public void setStuckThreadCount(int value) {
      this.beanTreeNode.setStuckThreadCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StuckThreadCount", (Object)null, (Object)null));
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
