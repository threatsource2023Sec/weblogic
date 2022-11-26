package weblogic.diagnostics.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class WLDFRESTNotificationBeanDConfigBeanInfo extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private WLDFRESTNotificationBean beanTreeNode;

   public WLDFRESTNotificationBeanDConfigBeanInfo(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (WLDFRESTNotificationBean)btn;
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

   public String getEndpointURL() {
      return this.beanTreeNode.getEndpointURL();
   }

   public void setEndpointURL(String value) {
      this.beanTreeNode.setEndpointURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "EndpointURL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getRestInvocationMethodType() {
      return this.beanTreeNode.getRestInvocationMethodType();
   }

   public void setRestInvocationMethodType(String value) {
      this.beanTreeNode.setRestInvocationMethodType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RestInvocationMethodType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getAcceptedResponseType() {
      return this.beanTreeNode.getAcceptedResponseType();
   }

   public void setAcceptedResponseType(String value) {
      this.beanTreeNode.setAcceptedResponseType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AcceptedResponseType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getHttpAuthenticationMode() {
      return this.beanTreeNode.getHttpAuthenticationMode();
   }

   public void setHttpAuthenticationMode(String value) {
      this.beanTreeNode.setHttpAuthenticationMode(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpAuthenticationMode", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getHttpAuthenticationUserName() {
      return this.beanTreeNode.getHttpAuthenticationUserName();
   }

   public void setHttpAuthenticationUserName(String value) {
      this.beanTreeNode.setHttpAuthenticationUserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpAuthenticationUserName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getHttpAuthenticationPassword() {
      return this.beanTreeNode.getHttpAuthenticationPassword();
   }

   public void setHttpAuthenticationPassword(String value) {
      this.beanTreeNode.setHttpAuthenticationPassword(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpAuthenticationPassword", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getHttpAuthenticationPasswordEncrypted() {
      return this.beanTreeNode.getHttpAuthenticationPasswordEncrypted();
   }

   public void setHttpAuthenticationPasswordEncrypted(byte[] value) {
      this.beanTreeNode.setHttpAuthenticationPasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpAuthenticationPasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public Properties getCustomNotificationProperties() {
      return this.beanTreeNode.getCustomNotificationProperties();
   }

   public void setCustomNotificationProperties(Properties value) {
      this.beanTreeNode.setCustomNotificationProperties(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CustomNotificationProperties", (Object)null, (Object)null));
      this.setModified(true);
   }
}
