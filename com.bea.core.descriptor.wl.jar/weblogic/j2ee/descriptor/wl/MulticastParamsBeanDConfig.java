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

public class MulticastParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MulticastParamsBean beanTreeNode;

   public MulticastParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MulticastParamsBean)btn;
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

   public String getMulticastAddress() {
      return this.beanTreeNode.getMulticastAddress();
   }

   public void setMulticastAddress(String value) {
      this.beanTreeNode.setMulticastAddress(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MulticastAddress", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMulticastPort() {
      return this.beanTreeNode.getMulticastPort();
   }

   public void setMulticastPort(int value) {
      this.beanTreeNode.setMulticastPort(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MulticastPort", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMulticastTimeToLive() {
      return this.beanTreeNode.getMulticastTimeToLive();
   }

   public void setMulticastTimeToLive(int value) {
      this.beanTreeNode.setMulticastTimeToLive(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MulticastTimeToLive", (Object)null, (Object)null));
      this.setModified(true);
   }

   public TemplateBean getTemplateBean() {
      return this.beanTreeNode.getTemplateBean();
   }
}
