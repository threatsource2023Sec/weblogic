package weblogic.deploy.api.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class ModuleInfo {
   protected String name = null;
   protected Set subModules = new HashSet();
   protected ModuleType type;
   protected boolean archived;
   protected ModuleInfo parent;
   protected String[] roots;
   protected String[] webservices;
   protected String[] restservices;
   protected String[] datasources;
   protected String[] beans;
   protected String[] subDeployments;

   protected ModuleInfo() {
      this.type = WebLogicModuleType.UNKNOWN;
      this.archived = false;
      this.parent = null;
      this.roots = null;
   }

   protected static ModuleInfo createModuleInfo(WebLogicDeployableObject dobj) throws IOException, ConfigurationException {
      return createModuleInfo(dobj, (WebLogicDeploymentConfiguration)null, (String)null);
   }

   protected static ModuleInfo createModuleInfo(WebLogicDeployableObject dobj, WebLogicDeploymentConfiguration dc, String name) throws IOException, ConfigurationException {
      ConfigHelper.checkParam("WebLogicDeployableObject", dobj);
      return new DeployableObjectInfo(dobj, dc, name);
   }

   private ModuleInfo createModuleInfo(WeblogicModuleBean mod, DescriptorBean beanTree) {
      if ("JMS".equals(mod.getType())) {
         return new ModuleBeanInfo(mod, WebLogicModuleType.JMS, beanTree);
      } else if ("JDBC".equals(mod.getType())) {
         return new ModuleBeanInfo(mod, WebLogicModuleType.JDBC, beanTree);
      } else if ("Interception".equals(mod.getType())) {
         return new ModuleBeanInfo(mod, WebLogicModuleType.INTERCEPT, beanTree);
      } else {
         return "GAR".equals(mod.getType()) ? new ModuleBeanInfo(mod, WebLogicModuleType.GAR, beanTree) : null;
      }
   }

   public String getName() {
      return this.name;
   }

   public ModuleInfo[] getSubModules() {
      return (ModuleInfo[])((ModuleInfo[])this.subModules.toArray(new ModuleInfo[0]));
   }

   public ModuleType getType() {
      return this.type;
   }

   public boolean isArchived() {
      return this.archived;
   }

   public boolean isWebService() {
      return this.webservices != null && this.webservices.length > 0;
   }

   public String[] getWebServices() {
      return this.webservices;
   }

   public boolean isRestService() {
      return this.restservices != null && this.restservices.length > 0;
   }

   public String[] getRestServices() {
      return this.restservices;
   }

   public boolean hasDataSource() {
      return this.datasources != null && this.datasources.length > 0;
   }

   public String[] getDataSources() {
      return this.datasources;
   }

   public String[] getContextRoots() {
      return this.roots;
   }

   public String[] getBeans() {
      return this.beans;
   }

   public String[] getSubDeployments() {
      return this.subDeployments;
   }

   protected boolean addModuleInfo(ModuleInfo info) {
      boolean b = this.subModules.add(info);
      if (b) {
         info.setParent(this);
      }

      return b;
   }

   protected boolean addModuleInfo(WeblogicModuleBean mod, DescriptorBean beantree) {
      ModuleInfo minfo = this.createModuleInfo(mod, beantree);
      return minfo != null ? this.addModuleInfo(minfo) : false;
   }

   protected ModuleInfo getParent() {
      return this.parent;
   }

   private void setParent(ModuleInfo info) {
      this.parent = info;
   }

   protected boolean checkIfArchived(WebLogicDeployableObject dobj) {
      File f = dobj.getArchive();
      if (f.isDirectory()) {
         return false;
      } else {
         return !f.getName().endsWith(".xml");
      }
   }
}
