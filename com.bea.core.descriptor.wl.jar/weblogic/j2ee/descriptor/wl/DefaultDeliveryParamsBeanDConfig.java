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

public class DefaultDeliveryParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private DefaultDeliveryParamsBean beanTreeNode;

   public DefaultDeliveryParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (DefaultDeliveryParamsBean)btn;
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

   public String getDefaultDeliveryMode() {
      return this.beanTreeNode.getDefaultDeliveryMode();
   }

   public void setDefaultDeliveryMode(String value) {
      this.beanTreeNode.setDefaultDeliveryMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultDeliveryMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDefaultTimeToDeliver() {
      return this.beanTreeNode.getDefaultTimeToDeliver();
   }

   public void setDefaultTimeToDeliver(String value) {
      this.beanTreeNode.setDefaultTimeToDeliver(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultTimeToDeliver", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getDefaultTimeToLive() {
      return this.beanTreeNode.getDefaultTimeToLive();
   }

   public void setDefaultTimeToLive(long value) {
      this.beanTreeNode.setDefaultTimeToLive(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultTimeToLive", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getDefaultPriority() {
      return this.beanTreeNode.getDefaultPriority();
   }

   public void setDefaultPriority(int value) {
      this.beanTreeNode.setDefaultPriority(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultPriority", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getDefaultRedeliveryDelay() {
      return this.beanTreeNode.getDefaultRedeliveryDelay();
   }

   public void setDefaultRedeliveryDelay(long value) {
      this.beanTreeNode.setDefaultRedeliveryDelay(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultRedeliveryDelay", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getSendTimeout() {
      return this.beanTreeNode.getSendTimeout();
   }

   public void setSendTimeout(long value) {
      this.beanTreeNode.setSendTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SendTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getDefaultCompressionThreshold() {
      return this.beanTreeNode.getDefaultCompressionThreshold();
   }

   public void setDefaultCompressionThreshold(int value) {
      this.beanTreeNode.setDefaultCompressionThreshold(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultCompressionThreshold", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDefaultUnitOfOrder() {
      return this.beanTreeNode.getDefaultUnitOfOrder();
   }

   public void setDefaultUnitOfOrder(String value) {
      this.beanTreeNode.setDefaultUnitOfOrder(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DefaultUnitOfOrder", (Object)null, (Object)null));
      this.setModified(true);
   }
}
