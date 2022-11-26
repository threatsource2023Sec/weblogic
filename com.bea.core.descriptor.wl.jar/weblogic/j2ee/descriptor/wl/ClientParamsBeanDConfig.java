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

public class ClientParamsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ClientParamsBean beanTreeNode;

   public ClientParamsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ClientParamsBean)btn;
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

   public String getClientId() {
      return this.beanTreeNode.getClientId();
   }

   public void setClientId(String value) {
      this.beanTreeNode.setClientId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClientId", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getClientIdPolicy() {
      return this.beanTreeNode.getClientIdPolicy();
   }

   public void setClientIdPolicy(String value) {
      this.beanTreeNode.setClientIdPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClientIdPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSubscriptionSharingPolicy() {
      return this.beanTreeNode.getSubscriptionSharingPolicy();
   }

   public void setSubscriptionSharingPolicy(String value) {
      this.beanTreeNode.setSubscriptionSharingPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SubscriptionSharingPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAcknowledgePolicy() {
      return this.beanTreeNode.getAcknowledgePolicy();
   }

   public void setAcknowledgePolicy(String value) {
      this.beanTreeNode.setAcknowledgePolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AcknowledgePolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isAllowCloseInOnMessage() {
      return this.beanTreeNode.isAllowCloseInOnMessage();
   }

   public void setAllowCloseInOnMessage(boolean value) {
      this.beanTreeNode.setAllowCloseInOnMessage(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AllowCloseInOnMessage", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getMessagesMaximum() {
      return this.beanTreeNode.getMessagesMaximum();
   }

   public void setMessagesMaximum(int value) {
      this.beanTreeNode.setMessagesMaximum(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessagesMaximum", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMulticastOverrunPolicy() {
      return this.beanTreeNode.getMulticastOverrunPolicy();
   }

   public void setMulticastOverrunPolicy(String value) {
      this.beanTreeNode.setMulticastOverrunPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MulticastOverrunPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getSynchronousPrefetchMode() {
      return this.beanTreeNode.getSynchronousPrefetchMode();
   }

   public void setSynchronousPrefetchMode(String value) {
      this.beanTreeNode.setSynchronousPrefetchMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "SynchronousPrefetchMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getReconnectPolicy() {
      return this.beanTreeNode.getReconnectPolicy();
   }

   public void setReconnectPolicy(String value) {
      this.beanTreeNode.setReconnectPolicy(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReconnectPolicy", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getReconnectBlockingMillis() {
      return this.beanTreeNode.getReconnectBlockingMillis();
   }

   public void setReconnectBlockingMillis(long value) {
      this.beanTreeNode.setReconnectBlockingMillis(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReconnectBlockingMillis", (Object)null, (Object)null));
      this.setModified(true);
   }

   public long getTotalReconnectPeriodMillis() {
      return this.beanTreeNode.getTotalReconnectPeriodMillis();
   }

   public void setTotalReconnectPeriodMillis(long value) {
      this.beanTreeNode.setTotalReconnectPeriodMillis(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TotalReconnectPeriodMillis", (Object)null, (Object)null));
      this.setModified(true);
   }
}
