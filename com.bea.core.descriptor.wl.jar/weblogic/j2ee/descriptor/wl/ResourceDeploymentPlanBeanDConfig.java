package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;

public class ResourceDeploymentPlanBeanDConfig extends BasicDConfigBeanRoot {
   private static final boolean debug = Debug.isDebug("config");
   private ResourceDeploymentPlanBean beanTreeNode;

   public ResourceDeploymentPlanBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ResourceDeploymentPlanBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   public ResourceDeploymentPlanBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, DescriptorBean beanTree, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);
      this.beanTree = beanTree;
      this.beanTreeNode = (ResourceDeploymentPlanBean)beanTree;

      try {
         this.initXpaths();
         this.customInit();
      } catch (ConfigurationException var7) {
         throw new InvalidModuleException(var7.toString());
      }
   }

   public ResourceDeploymentPlanBeanDConfig(DDBeanRoot root, WebLogicDeploymentConfiguration dc, String name, DescriptorSupport ds) throws InvalidModuleException, IOException {
      super(root, dc, name, ds);

      try {
         this.initXpaths();
         if (debug) {
            Debug.say("Creating new root object");
         }

         this.beanTree = DescriptorParser.getWLSEditableDescriptorBean(ResourceDeploymentPlanBean.class);
         this.beanTreeNode = (ResourceDeploymentPlanBean)this.beanTree;
         this.customInit();
      } catch (ConfigurationException var6) {
         throw new InvalidModuleException(var6.toString());
      }
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

   public String getDescription() {
      return this.beanTreeNode.getDescription();
   }

   public void setDescription(String value) {
      this.beanTreeNode.setDescription(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Description", (Object)null, (Object)null));
      this.setModified(true);
   }

   public VariableDefinitionBean getVariableDefinition() {
      return this.beanTreeNode.getVariableDefinition();
   }

   public boolean isGlobalVariables() {
      return this.beanTreeNode.isGlobalVariables();
   }

   public void setGlobalVariables(boolean value) {
      this.beanTreeNode.setGlobalVariables(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "GlobalVariables", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ExternalResourceOverrideBean[] getExternalResourceOverrides() {
      return this.beanTreeNode.getExternalResourceOverrides();
   }

   public ConfigResourceOverrideBean[] getConfigResourceOverrides() {
      return this.beanTreeNode.getConfigResourceOverrides();
   }

   public DConfigBean[] getSecondaryDescriptors() {
      return super.getSecondaryDescriptors();
   }
}
