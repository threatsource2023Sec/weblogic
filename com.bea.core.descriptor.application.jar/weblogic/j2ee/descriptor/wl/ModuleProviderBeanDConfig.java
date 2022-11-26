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

public class ModuleProviderBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ModuleProviderBean beanTreeNode;

   public ModuleProviderBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ModuleProviderBean)btn;
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
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getModuleFactoryClassName() {
      return this.beanTreeNode.getModuleFactoryClassName();
   }

   public void setModuleFactoryClassName(String value) {
      this.beanTreeNode.setModuleFactoryClassName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ModuleFactoryClassName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getBindingJarUri() {
      return this.beanTreeNode.getBindingJarUri();
   }

   public void setBindingJarUri(String value) {
      this.beanTreeNode.setBindingJarUri(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "BindingJarUri", (Object)null, (Object)null));
      this.setModified(true);
   }
}
