package weblogic.application.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.xml.stream.XMLStreamException;
import weblogic.application.CustomModuleContext;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.utils.IOUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class DefaultEARModule implements ToolsModule {
   private ConfigDescriptorManager configDescManager;
   private DescriptorBean moduleDescriptorBean;
   private final String bindingJarUri;
   final String descriptorUri;
   String parentModuleUri;
   String parentModuleId;
   String appModuleName;
   private final ModuleType parentModuleType;
   private GenericClassLoader parentClassLoader;
   private boolean useBindingCache;
   private final boolean ignoreMissingDescriptors;
   private ModuleContext state;
   private final String moduleUri;
   private ToolsContext ctx;

   public DefaultEARModule(CustomModuleContext customModuleContext, CustomModuleBean cmb) throws ToolFailureException {
      this(customModuleContext, cmb, false);
   }

   public DefaultEARModule(CustomModuleContext customModuleContext, CustomModuleBean cmb, boolean ignoreMissingDescriptors) throws ToolFailureException {
      this.useBindingCache = true;
      this.moduleUri = cmb.getUri();
      this.bindingJarUri = customModuleContext.getModuleProviderBean().getBindingJarUri();
      this.parentModuleUri = customModuleContext.getParentModuleUri();
      this.parentModuleId = customModuleContext.getParentModuleId();
      if (this.parentModuleUri != null) {
         this.parentModuleType = ModuleType.WAR;
         this.appModuleName = this.parentModuleUri;
      } else {
         this.parentModuleType = ModuleType.EAR;
      }

      if (cmb.getConfigurationSupport() != null && cmb.getConfigurationSupport().getBaseUri() != null) {
         this.descriptorUri = cmb.getConfigurationSupport().getBaseUri();
         this.ignoreMissingDescriptors = ignoreMissingDescriptors;
      } else {
         throw new ToolFailureException("Unable to find base descriptor URI for config module " + this.appModuleName);
      }
   }

   public DefaultEARModule(String parentModuleUri, String parentModuleId, ModuleType parentModuleType, String moduleUri, String descriptorUri) {
      this.useBindingCache = true;
      this.moduleUri = moduleUri;
      this.parentModuleUri = parentModuleUri;
      if (parentModuleUri != null) {
         this.appModuleName = parentModuleUri;
      }

      this.parentModuleId = parentModuleId;
      this.parentModuleType = parentModuleType;
      this.descriptorUri = descriptorUri;
      this.ignoreMissingDescriptors = true;
      this.bindingJarUri = null;
   }

   public String getAltDD() {
      return null;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      this.ctx = ctx;

      try {
         this.configDescManager = new ConfigDescriptorManager(this.appModuleName);
         this.configDescManager.initBindingInfo(parentClassLoader, this.bindingJarUri, this.useBindingCache);
         this.parentClassLoader = parentClassLoader;
         return NullClassFinder.NULL_FINDER;
      } catch (ModuleException var5) {
         throw new ToolFailureException("Unable to init module classloader", var5);
      }
   }

   public boolean needsClassLoader() {
      return false;
   }

   public void write() throws ToolFailureException {
      try {
         if (this.moduleDescriptorBean != null && this.descriptorUri != null && this.parentClassLoader != null) {
            FileOutputStream fos = new FileOutputStream(IOUtils.checkCreateParent(new File(this.state.getOutputDir(), this.descriptorUri)));
            (new EditableDescriptorManager(this.parentClassLoader)).writeDescriptorAsXML(this.moduleDescriptorBean.getDescriptor(), fos);
            fos.close();
         }

      } catch (IOException var2) {
         throw new ToolFailureException("Unable to write custom module descriptors", var2);
      }
   }

   public Map merge() throws ToolFailureException {
      Map descriptors = new HashMap();

      try {
         String applicationFileName = this.ctx.getSourceFile() == null ? null : this.ctx.getSourceFile().getName();
         this.moduleDescriptorBean = this.configDescManager.parseMergedDescriptorBean(this.ctx.getApplicationFileManager(), applicationFileName, this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.descriptorUri, this.ctx.getLibraryProvider(this.parentModuleId), this.parentModuleType, this.parentModuleUri, true, this.ignoreMissingDescriptors);
         if (this.descriptorUri != null && this.moduleDescriptorBean != null) {
            descriptors.put(this.descriptorUri, this.moduleDescriptorBean);
         }

         return descriptors;
      } catch (IOException var3) {
         throw new ToolFailureException("Error reading descriptor: " + this.descriptorUri + " for app module " + this.appModuleName, var3);
      } catch (XMLStreamException var4) {
         throw new ToolFailureException("Error reading descriptor: " + this.descriptorUri + " for app module " + this.appModuleName, var4);
      } catch (ModuleException var5) {
         throw new ToolFailureException("Error reading descriptor: " + this.descriptorUri + " for app module " + this.appModuleName, var5);
      }
   }

   public void cleanup() {
      this.configDescManager.destroy();
   }

   public ModuleType getModuleType() {
      return WebLogicModuleType.CONFIG;
   }

   public String toString() {
      return this.getURI();
   }

   public String getStandardDescriptorURI() {
      return this.descriptorUri;
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      return Collections.emptyMap();
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
   }

   public boolean isDeployableObject() {
      return false;
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
   }

   public String[] getApplicationNameXPath() {
      return null;
   }
}
