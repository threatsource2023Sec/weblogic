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

public class QueueBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private QueueBean beanTreeNode;

   public QueueBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (QueueBean)btn;
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

   public int getForwardDelay() {
      return this.beanTreeNode.getForwardDelay();
   }

   public void setForwardDelay(int value) {
      this.beanTreeNode.setForwardDelay(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ForwardDelay", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getResetDeliveryCountOnForward() {
      return this.beanTreeNode.getResetDeliveryCountOnForward();
   }

   public void setResetDeliveryCountOnForward(boolean value) {
      this.beanTreeNode.setResetDeliveryCountOnForward(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResetDeliveryCountOnForward", (Object)null, (Object)null));
      this.setModified(true);
   }
}
