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

public class DeliveryParamsOverridesBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DeliveryParamsOverridesBean beanTreeNode;

   public DeliveryParamsOverridesBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DeliveryParamsOverridesBean)btn;
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

   public String getDeliveryMode() {
      return this.beanTreeNode.getDeliveryMode();
   }

   public void setDeliveryMode(String value) {
      this.beanTreeNode.setDeliveryMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DeliveryMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTimeToDeliver() {
      return this.beanTreeNode.getTimeToDeliver();
   }

   public void setTimeToDeliver(String value) {
      this.beanTreeNode.setTimeToDeliver(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeToDeliver", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getTimeToLive() {
      return this.beanTreeNode.getTimeToLive();
   }

   public void setTimeToLive(long value) {
      this.beanTreeNode.setTimeToLive(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TimeToLive", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getPriority() {
      return this.beanTreeNode.getPriority();
   }

   public void setPriority(int value) {
      this.beanTreeNode.setPriority(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Priority", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getRedeliveryDelay() {
      return this.beanTreeNode.getRedeliveryDelay();
   }

   public void setRedeliveryDelay(long value) {
      this.beanTreeNode.setRedeliveryDelay(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RedeliveryDelay", (Object)null, (Object)null));
      this.setModified(true);
   }

   public TemplateBean getTemplateBean() {
      return this.beanTreeNode.getTemplateBean();
   }
}
