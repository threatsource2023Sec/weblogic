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

public class MessageDrivenDescriptorBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private MessageDrivenDescriptorBean beanTreeNode;

   public MessageDrivenDescriptorBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (MessageDrivenDescriptorBean)btn;
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
      sb.append("ResourceAdapterJNDIName: ");
      sb.append(this.beanTreeNode.getResourceAdapterJNDIName());
      sb.append("\n");
      sb.append("DestinationJNDIName: ");
      sb.append(this.beanTreeNode.getDestinationJNDIName());
      sb.append("\n");
      sb.append("ConnectionFactoryJNDIName: ");
      sb.append(this.beanTreeNode.getConnectionFactoryJNDIName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public PoolBean getPool() {
      return this.beanTreeNode.getPool();
   }

   public boolean isPoolSet() {
      return this.beanTreeNode.isPoolSet();
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this.beanTreeNode.getTimerDescriptor();
   }

   public boolean isTimerDescriptorSet() {
      return this.beanTreeNode.isTimerDescriptorSet();
   }

   public String getResourceAdapterJNDIName() {
      return this.beanTreeNode.getResourceAdapterJNDIName();
   }

   public void setResourceAdapterJNDIName(String value) {
      this.beanTreeNode.setResourceAdapterJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResourceAdapterJNDIName", (Object)null, (Object)null));
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

   public String getConnectionFactoryJNDIName() {
      return this.beanTreeNode.getConnectionFactoryJNDIName();
   }

   public void setConnectionFactoryJNDIName(String value) {
      this.beanTreeNode.setConnectionFactoryJNDIName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryJNDIName", (Object)null, (Object)null));
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

   public String getConnectionFactoryResourceLink() {
      return this.beanTreeNode.getConnectionFactoryResourceLink();
   }

   public void setConnectionFactoryResourceLink(String value) {
      this.beanTreeNode.setConnectionFactoryResourceLink(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryResourceLink", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getJmsPollingIntervalSeconds() {
      return this.beanTreeNode.getJmsPollingIntervalSeconds();
   }

   public void setJmsPollingIntervalSeconds(int value) {
      this.beanTreeNode.setJmsPollingIntervalSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JmsPollingIntervalSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJmsClientId() {
      return this.beanTreeNode.getJmsClientId();
   }

   public void setJmsClientId(String value) {
      this.beanTreeNode.setJmsClientId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JmsClientId", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isGenerateUniqueJmsClientId() {
      return this.beanTreeNode.isGenerateUniqueJmsClientId();
   }

   public void setGenerateUniqueJmsClientId(boolean value) {
      this.beanTreeNode.setGenerateUniqueJmsClientId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GenerateUniqueJmsClientId", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isDurableSubscriptionDeletion() {
      return this.beanTreeNode.isDurableSubscriptionDeletion();
   }

   public void setDurableSubscriptionDeletion(boolean value) {
      this.beanTreeNode.setDurableSubscriptionDeletion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DurableSubscriptionDeletion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxMessagesInTransaction() {
      return this.beanTreeNode.getMaxMessagesInTransaction();
   }

   public void setMaxMessagesInTransaction(int value) {
      this.beanTreeNode.setMaxMessagesInTransaction(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxMessagesInTransaction", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDistributedDestinationConnection() {
      return this.beanTreeNode.getDistributedDestinationConnection();
   }

   public void setDistributedDestinationConnection(String value) {
      this.beanTreeNode.setDistributedDestinationConnection(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DistributedDestinationConnection", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isUse81StylePolling() {
      return this.beanTreeNode.isUse81StylePolling();
   }

   public void setUse81StylePolling(boolean value) {
      this.beanTreeNode.setUse81StylePolling(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Use81StylePolling", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getInitSuspendSeconds() {
      return this.beanTreeNode.getInitSuspendSeconds();
   }

   public void setInitSuspendSeconds(int value) {
      this.beanTreeNode.setInitSuspendSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitSuspendSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMaxSuspendSeconds() {
      return this.beanTreeNode.getMaxSuspendSeconds();
   }

   public void setMaxSuspendSeconds(int value) {
      this.beanTreeNode.setMaxSuspendSeconds(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MaxSuspendSeconds", (Object)null, (Object)null));
      this.setModified(true);
   }

   public SecurityPluginBean getSecurityPlugin() {
      return this.beanTreeNode.getSecurityPlugin();
   }

   public boolean isSecurityPluginSet() {
      return this.beanTreeNode.isSecurityPluginSet();
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
