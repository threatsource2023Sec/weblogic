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
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;

public class ConnectionDefinitionPropertiesBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ConnectionDefinitionPropertiesBean beanTreeNode;

   public ConnectionDefinitionPropertiesBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ConnectionDefinitionPropertiesBean)btn;
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

   public PoolParamsBean getPoolParams() {
      return this.beanTreeNode.getPoolParams();
   }

   public boolean isPoolParamsSet() {
      return this.beanTreeNode.isPoolParamsSet();
   }

   public LoggingBean getLogging() {
      return this.beanTreeNode.getLogging();
   }

   public boolean isLoggingSet() {
      return this.beanTreeNode.isLoggingSet();
   }

   public String getTransactionSupport() {
      return this.beanTreeNode.getTransactionSupport();
   }

   public void setTransactionSupport(String value) {
      this.beanTreeNode.setTransactionSupport(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TransactionSupport", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AuthenticationMechanismBean[] getAuthenticationMechanisms() {
      return this.beanTreeNode.getAuthenticationMechanisms();
   }

   public boolean isReauthenticationSupport() {
      return this.beanTreeNode.isReauthenticationSupport();
   }

   public void setReauthenticationSupport(boolean value) {
      this.beanTreeNode.setReauthenticationSupport(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ReauthenticationSupport", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConfigPropertiesBean getProperties() {
      return this.beanTreeNode.getProperties();
   }

   public boolean isPropertiesSet() {
      return this.beanTreeNode.isPropertiesSet();
   }

   public String getResAuth() {
      return this.beanTreeNode.getResAuth();
   }

   public void setResAuth(String value) {
      this.beanTreeNode.setResAuth(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ResAuth", (Object)null, (Object)null));
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
