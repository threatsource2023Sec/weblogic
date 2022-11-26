package weblogic.application.internal.flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.MergedDescriptorModule;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleWrapper;
import weblogic.application.UpdateListener;
import weblogic.application.internal.FlowContext;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.ExtensionDescriptorParser;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.management.DeploymentException;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class ScopedModuleDriver extends ModuleWrapper implements Module, MergedDescriptorModule {
   private final Module module;
   private Module[] scopedModules = new Module[0];
   private Module[] allModules;
   private final ModuleStateDriver driver;
   private final String moduleUri;
   private DescriptorBean[] descriptors = null;
   private FlowContext appCtx;
   private final ExtensionDescriptorParser extLoader;
   private WeblogicExtensionBean extDescriptor;

   public ScopedModuleDriver(Module module, FlowContext appCtx, String moduleUri, VirtualJarFile moduleJar, String extensionLocationUri) throws ModuleException {
      if (module == null) {
         throw new IllegalArgumentException("module cannot be null");
      } else if (this.scopedModules == null) {
         throw new IllegalArgumentException("scopedModules cannot be null");
      } else {
         this.module = module;
         this.appCtx = appCtx;
         this.driver = new ModuleStateDriver(appCtx);
         this.moduleUri = moduleUri;
         this.setScopedModules(new Module[0]);
         if (extensionLocationUri != null) {
            this.extLoader = new ExtensionDescriptorParser(moduleJar, EarUtils.getConfigDir(appCtx), appCtx.getAppDeploymentMBean().getDeploymentPlanDescriptor(), module.getId(), extensionLocationUri + "/" + "weblogic-extension.xml");

            try {
               this.extLoader.getWlExtensionDescriptor();
            } catch (IOException var7) {
               throw new ModuleException(var7);
            } catch (XMLStreamException var8) {
               throw new ModuleException(var8);
            }
         } else {
            this.extLoader = null;
         }

      }
   }

   private void setScopedModules(Module[] scopedModules) {
      this.scopedModules = scopedModules;
      this.allModules = new Module[scopedModules.length + 1];
      this.allModules[0] = this.module;
      if (scopedModules.length > 0) {
         System.arraycopy(scopedModules, 0, this.allModules, 1, scopedModules.length);
      }

   }

   public Module getDelegate() {
      return this.module;
   }

   public String getId() {
      return this.module.getId();
   }

   public String getType() {
      return this.module.getType();
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return this.module.getComponentRuntimeMBeans();
   }

   public synchronized DescriptorBean[] getDescriptors() {
      if (this.descriptors != null) {
         return this.descriptors;
      } else {
         List l = new ArrayList();
         l.addAll(Arrays.asList(this.module.getDescriptors()));
         if (this.extDescriptor != null) {
            l.add((DescriptorBean)this.extDescriptor);
         }

         for(int i = 0; i < this.scopedModules.length; ++i) {
            l.addAll(Arrays.asList(this.scopedModules[i].getDescriptors()));
         }

         this.descriptors = (DescriptorBean[])((DescriptorBean[])l.toArray(new DescriptorBean[l.size()]));
         return this.descriptors;
      }
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      GenericClassLoader gcl = this.module.init(appCtx, parent, reg);
      this.initScopedModules(appCtx, gcl, reg);
      return gcl;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.module.initUsingLoader(appCtx, gcl, reg);
      this.initScopedModules(appCtx, gcl, reg);
   }

   private void initScopedModules(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      for(int i = 0; i < this.scopedModules.length; ++i) {
         this.scopedModules[i].init(appCtx, gcl, reg);
      }

   }

   public void prepare() throws ModuleException {
      this.module.prepare();
      this.findAndInitScopedCustomModules();
      this.prepareScopedModules();
   }

   private void prepareScopedModules() throws ModuleException {
      this.driver.prepare(this.scopedModules);
   }

   public void activate() throws ModuleException {
      this.driver.activate(this.allModules);
   }

   public void start() throws ModuleException {
      this.driver.start(this.allModules);
   }

   public void deactivate() throws ModuleException {
      this.driver.deactivate(this.allModules);
   }

   public void unprepare() throws ModuleException {
      this.driver.unprepare(this.allModules);
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      this.driver.destroy(this.allModules);
   }

   public void remove() throws ModuleException {
      this.driver.remove(this.allModules);
   }

   public void adminToProduction() {
      try {
         this.driver.adminToProduction(this.allModules);
      } catch (ModuleException var2) {
         if (var2.getCause() != null && var2.getCause() instanceof RuntimeException) {
            throw (RuntimeException)var2.getCause();
         } else {
            throw new RuntimeException(var2);
         }
      }
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
      this.driver.gracefulProductionToAdmin(barrier, this.allModules);
   }

   public void forceProductionToAdmin() throws ModuleException {
      this.driver.forceProductionToAdmin(this.allModules);
   }

   private void findAndInitScopedCustomModules() throws ModuleException {
      GenericClassLoader moduleClassLoader = ApplicationAccess.getApplicationAccess().findModuleLoader(this.appCtx.getApplicationId(), this.module.getId());
      VirtualJarFile[] libVjfs = null;

      try {
         if (this.extLoader != null) {
            libVjfs = LibraryUtils.getLibraryVjarsWithDescriptor(this.appCtx.getLibraryProvider(this.module.getId()), this.extLoader.getDocumentURI());
            this.extLoader.mergeWlExtensionDescriptorsFromLibraries(libVjfs);
            this.extDescriptor = this.extLoader.getWlExtensionDescriptor();
         }

         try {
            Module[] scopedCustomModules = CustomModuleHelper.createScopedCustomModules(this.appCtx, this.module, this.moduleUri, this.extDescriptor, moduleClassLoader);
            this.setScopedModules(scopedCustomModules);
            this.initScopedModules(this.appCtx, moduleClassLoader, this.appCtx);
         } catch (DeploymentException var9) {
            throw new ModuleException(var9);
         }
      } catch (IOException var10) {
         throw new ModuleException(var10);
      } catch (XMLStreamException var11) {
         throw new ModuleException(var11);
      } finally {
         IOUtils.forceClose(libVjfs);
      }

   }

   public Map getDescriptorMappings() {
      Map map = null;

      for(int i = 0; i < this.allModules.length; ++i) {
         if (this.allModules[i] != this && this.allModules[i] instanceof MergedDescriptorModule) {
            MergedDescriptorModule m = (MergedDescriptorModule)this.allModules[i];
            if (m.getDescriptorMappings() != null) {
               if (map == null) {
                  map = new HashMap();
               }

               map.putAll(m.getDescriptorMappings());
            }
         }
      }

      return map;
   }

   public void handleMergedFinder(ClassFinder finder) {
      for(int i = 0; i < this.allModules.length; ++i) {
         if (this.allModules[i] != this && this.allModules[i] instanceof MergedDescriptorModule) {
            ((MergedDescriptorModule)this.allModules[i]).handleMergedFinder(finder);
         }
      }

   }
}
