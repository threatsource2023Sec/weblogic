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

public class ConfigurationSupportBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ConfigurationSupportBean beanTreeNode;

   public ConfigurationSupportBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ConfigurationSupportBean)btn;
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

   public String getBaseRootElement() {
      return this.beanTreeNode.getBaseRootElement();
   }

   public void setBaseRootElement(String value) {
      this.beanTreeNode.setBaseRootElement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseRootElement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConfigRootElement() {
      return this.beanTreeNode.getConfigRootElement();
   }

   public void setConfigRootElement(String value) {
      this.beanTreeNode.setConfigRootElement(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConfigRootElement", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBaseNamespace() {
      return this.beanTreeNode.getBaseNamespace();
   }

   public void setBaseNamespace(String value) {
      this.beanTreeNode.setBaseNamespace(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseNamespace", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConfigNamespace() {
      return this.beanTreeNode.getConfigNamespace();
   }

   public void setConfigNamespace(String value) {
      this.beanTreeNode.setConfigNamespace(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConfigNamespace", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBaseUri() {
      return this.beanTreeNode.getBaseUri();
   }

   public void setBaseUri(String value) {
      this.beanTreeNode.setBaseUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BaseUri", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConfigUri() {
      return this.beanTreeNode.getConfigUri();
   }

   public void setConfigUri(String value) {
      this.beanTreeNode.setConfigUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConfigUri", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBasePackageName() {
      return this.beanTreeNode.getBasePackageName();
   }

   public void setBasePackageName(String value) {
      this.beanTreeNode.setBasePackageName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BasePackageName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConfigPackageName() {
      return this.beanTreeNode.getConfigPackageName();
   }

   public void setConfigPackageName(String value) {
      this.beanTreeNode.setConfigPackageName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConfigPackageName", (Object)null, (Object)null));
      this.setModified(true);
   }
}
