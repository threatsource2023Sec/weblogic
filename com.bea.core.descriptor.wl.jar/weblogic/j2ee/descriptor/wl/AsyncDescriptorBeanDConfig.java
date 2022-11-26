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

public class AsyncDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private AsyncDescriptorBean beanTreeNode;

   public AsyncDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (AsyncDescriptorBean)btn;
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

   public int getTimeoutSecs() {
      return this.beanTreeNode.getTimeoutSecs();
   }

   public void setTimeoutSecs(int value) {
      this.beanTreeNode.setTimeoutSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeoutSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getTimeoutCheckIntervalSecs() {
      return this.beanTreeNode.getTimeoutCheckIntervalSecs();
   }

   public void setTimeoutCheckIntervalSecs(int value) {
      this.beanTreeNode.setTimeoutCheckIntervalSecs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeoutCheckIntervalSecs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAsyncWorkManager() {
      return this.beanTreeNode.getAsyncWorkManager();
   }

   public void setAsyncWorkManager(String value) {
      this.beanTreeNode.setAsyncWorkManager(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AsyncWorkManager", (Object)null, (Object)null));
      this.setModified(true);
   }
}
