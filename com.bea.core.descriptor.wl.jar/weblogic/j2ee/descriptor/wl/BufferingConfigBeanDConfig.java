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

public class BufferingConfigBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private BufferingConfigBean beanTreeNode;

   public BufferingConfigBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (BufferingConfigBean)btn;
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

   public boolean isCustomized() {
      return this.beanTreeNode.isCustomized();
   }

   public void setCustomized(boolean value) {
      this.beanTreeNode.setCustomized(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Customized", (Object)null, (Object)null));
      this.setModified(true);
   }

   public BufferingQueueBean getRequestQueue() {
      return this.beanTreeNode.getRequestQueue();
   }

   public BufferingQueueBean getResponseQueue() {
      return this.beanTreeNode.getResponseQueue();
   }

   public int getRetryCount() {
      return this.beanTreeNode.getRetryCount();
   }

   public void setRetryCount(int value) {
      this.beanTreeNode.setRetryCount(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetryCount", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRetryDelay() {
      return this.beanTreeNode.getRetryDelay();
   }

   public void setRetryDelay(String value) {
      this.beanTreeNode.setRetryDelay(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetryDelay", (Object)null, (Object)null));
      this.setModified(true);
   }
}
