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

public class CustomModuleBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private CustomModuleBean beanTreeNode;

   public CustomModuleBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (CustomModuleBean)btn;
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
      return this.getUri();
   }

   public void initKeyPropertyValue(String value) {
      this.setUri(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Uri: ");
      sb.append(this.beanTreeNode.getUri());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getUri() {
      return this.beanTreeNode.getUri();
   }

   public void setUri(String value) {
      this.beanTreeNode.setUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Uri", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getProviderName() {
      return this.beanTreeNode.getProviderName();
   }

   public void setProviderName(String value) {
      this.beanTreeNode.setProviderName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ProviderName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConfigurationSupportBean getConfigurationSupport() {
      return this.beanTreeNode.getConfigurationSupport();
   }
}
