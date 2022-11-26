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

public class SoapjmsServiceEndpointAddressBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private SoapjmsServiceEndpointAddressBean beanTreeNode;

   public SoapjmsServiceEndpointAddressBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (SoapjmsServiceEndpointAddressBean)btn;
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

   public String getLookupVariant() {
      return this.beanTreeNode.getLookupVariant();
   }

   public void setLookupVariant(String value) {
      this.beanTreeNode.setLookupVariant(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LookupVariant", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDestinationName() {
      return this.beanTreeNode.getDestinationName();
   }

   public void setDestinationName(String value) {
      this.beanTreeNode.setDestinationName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getDestinationType() {
      return this.beanTreeNode.getDestinationType();
   }

   public void setDestinationType(String value) {
      this.beanTreeNode.setDestinationType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DestinationType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJndiConnectionFactoryName() {
      return this.beanTreeNode.getJndiConnectionFactoryName();
   }

   public void setJndiConnectionFactoryName(String value) {
      this.beanTreeNode.setJndiConnectionFactoryName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JndiConnectionFactoryName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJndiInitialContextFactory() {
      return this.beanTreeNode.getJndiInitialContextFactory();
   }

   public void setJndiInitialContextFactory(String value) {
      this.beanTreeNode.setJndiInitialContextFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JndiInitialContextFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJndiUrl() {
      return this.beanTreeNode.getJndiUrl();
   }

   public void setJndiUrl(String value) {
      this.beanTreeNode.setJndiUrl(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JndiUrl", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJndiContextParameter() {
      return this.beanTreeNode.getJndiContextParameter();
   }

   public void setJndiContextParameter(String value) {
      this.beanTreeNode.setJndiContextParameter(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JndiContextParameter", (Object)null, (Object)null));
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

   public String getDeliveryMode() {
      return this.beanTreeNode.getDeliveryMode();
   }

   public void setDeliveryMode(String value) {
      this.beanTreeNode.setDeliveryMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DeliveryMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getReplyToName() {
      return this.beanTreeNode.getReplyToName();
   }

   public void setReplyToName(String value) {
      this.beanTreeNode.setReplyToName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReplyToName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getTargetService() {
      return this.beanTreeNode.getTargetService();
   }

   public void setTargetService(String value) {
      this.beanTreeNode.setTargetService(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TargetService", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBindingVersion() {
      return this.beanTreeNode.getBindingVersion();
   }

   public void setBindingVersion(String value) {
      this.beanTreeNode.setBindingVersion(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BindingVersion", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getMessageType() {
      return this.beanTreeNode.getMessageType();
   }

   public void setMessageType(String value) {
      this.beanTreeNode.setMessageType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MessageType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isEnableHttpWsdlAccess() {
      return this.beanTreeNode.isEnableHttpWsdlAccess();
   }

   public void setEnableHttpWsdlAccess(boolean value) {
      this.beanTreeNode.setEnableHttpWsdlAccess(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EnableHttpWsdlAccess", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRunAsPrincipal() {
      return this.beanTreeNode.getRunAsPrincipal();
   }

   public void setRunAsPrincipal(String value) {
      this.beanTreeNode.setRunAsPrincipal(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RunAsPrincipal", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRunAsRole() {
      return this.beanTreeNode.getRunAsRole();
   }

   public void setRunAsRole(String value) {
      this.beanTreeNode.setRunAsRole(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RunAsRole", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isMdbPerDestination() {
      return this.beanTreeNode.isMdbPerDestination();
   }

   public void setMdbPerDestination(boolean value) {
      this.beanTreeNode.setMdbPerDestination(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "MdbPerDestination", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getActivationConfig() {
      return this.beanTreeNode.getActivationConfig();
   }

   public void setActivationConfig(String value) {
      this.beanTreeNode.setActivationConfig(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ActivationConfig", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJmsMessageHeader() {
      return this.beanTreeNode.getJmsMessageHeader();
   }

   public void setJmsMessageHeader(String value) {
      this.beanTreeNode.setJmsMessageHeader(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JmsMessageHeader", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJmsMessageProperty() {
      return this.beanTreeNode.getJmsMessageProperty();
   }

   public void setJmsMessageProperty(String value) {
      this.beanTreeNode.setJmsMessageProperty(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JmsMessageProperty", (Object)null, (Object)null));
      this.setModified(true);
   }
}
