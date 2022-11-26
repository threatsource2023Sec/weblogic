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

public class MessageDestinationDescriptorBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MessageDestinationDescriptorBean beanTreeNode;

   public MessageDestinationDescriptorBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MessageDestinationDescriptorBean)btn;
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
      return this.getMessageDestinationName();
   }

   public void initKeyPropertyValue(String value) {
      this.setMessageDestinationName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("MessageDestinationName: ");
      sb.append(this.beanTreeNode.getMessageDestinationName());
      sb.append("\n");
      sb.append("DestinationJNDIName: ");
      sb.append(this.beanTreeNode.getDestinationJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getMessageDestinationName() {
      return this.beanTreeNode.getMessageDestinationName();
   }

   public void setMessageDestinationName(String value) {
      this.beanTreeNode.setMessageDestinationName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessageDestinationName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDestinationJNDIName() {
      return this.beanTreeNode.getDestinationJNDIName();
   }

   public void setDestinationJNDIName(String value) {
      this.beanTreeNode.setDestinationJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationJNDIName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getInitialContextFactory() {
      return this.beanTreeNode.getInitialContextFactory();
   }

   public void setInitialContextFactory(String value) {
      this.beanTreeNode.setInitialContextFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialContextFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProviderUrl() {
      return this.beanTreeNode.getProviderUrl();
   }

   public void setProviderUrl(String value) {
      this.beanTreeNode.setProviderUrl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProviderUrl", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDestinationResourceLink() {
      return this.beanTreeNode.getDestinationResourceLink();
   }

   public void setDestinationResourceLink(String value) {
      this.beanTreeNode.setDestinationResourceLink(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationResourceLink", (Object)null, (Object)null));
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
