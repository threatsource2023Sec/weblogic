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

public class ForeignServerBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ForeignServerBean beanTreeNode;

   public ForeignServerBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ForeignServerBean)btn;
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

   public ForeignDestinationBean[] getForeignDestinations() {
      return this.beanTreeNode.getForeignDestinations();
   }

   public ForeignConnectionFactoryBean[] getForeignConnectionFactories() {
      return this.beanTreeNode.getForeignConnectionFactories();
   }

   public String getInitialContextFactory() {
      return this.beanTreeNode.getInitialContextFactory();
   }

   public void setInitialContextFactory(String value) {
      this.beanTreeNode.setInitialContextFactory(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "InitialContextFactory", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionURL() {
      return this.beanTreeNode.getConnectionURL();
   }

   public void setConnectionURL(String value) {
      this.beanTreeNode.setConnectionURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionURL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getJNDIPropertiesCredentialEncrypted() {
      return this.beanTreeNode.getJNDIPropertiesCredentialEncrypted();
   }

   public void setJNDIPropertiesCredentialEncrypted(byte[] value) {
      this.beanTreeNode.setJNDIPropertiesCredentialEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIPropertiesCredentialEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getJNDIPropertiesCredential() {
      return this.beanTreeNode.getJNDIPropertiesCredential();
   }

   public void setJNDIPropertiesCredential(String value) {
      this.beanTreeNode.setJNDIPropertiesCredential(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "JNDIPropertiesCredential", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertyBean[] getJNDIProperties() {
      return this.beanTreeNode.getJNDIProperties();
   }
}
