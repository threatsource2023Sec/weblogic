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

public class ModuleOverrideBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ModuleOverrideBean beanTreeNode;

   public ModuleOverrideBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ModuleOverrideBean)btn;
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
      return this.getModuleName();
   }

   public void initKeyPropertyValue(String value) {
      this.setModuleName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModuleName: ");
      sb.append(this.beanTreeNode.getModuleName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getModuleName() {
      return this.beanTreeNode.getModuleName();
   }

   public void setModuleName(String value) {
      this.beanTreeNode.setModuleName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ModuleName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getModuleType() {
      return this.beanTreeNode.getModuleType();
   }

   public void setModuleType(String value) {
      this.beanTreeNode.setModuleType(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ModuleType", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ModuleDescriptorBean[] getModuleDescriptors() {
      return this.beanTreeNode.getModuleDescriptors();
   }
}
