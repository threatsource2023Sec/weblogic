package weblogic.application.internal.flow;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ClassLoadingConfiguration;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.NonFatalDeploymentException;
import weblogic.application.internal.ClassLoaders;
import weblogic.application.internal.classloading.ShareabilityException;
import weblogic.application.internal.classloading.ShareabilityManager;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.ClassLoadingBean;
import weblogic.j2ee.descriptor.wl.ClassloaderStructureBean;
import weblogic.j2ee.descriptor.wl.ModuleRefBean;
import weblogic.j2ee.descriptor.wl.ShareableBean;
import weblogic.management.DeploymentException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;

public final class InitModulesFlow extends BaseFlow {
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
   private Set clstructLoaders;
   private List missingModuleRefs;
   private final String appIdSansPartitionName;
   private GenericClassLoader sharedAppClassLoader;

   public InitModulesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
      this.clstructLoaders = Collections.EMPTY_SET;
      this.missingModuleRefs = new ArrayList();
      this.sharedAppClassLoader = null;
      this.appIdSansPartitionName = appCtx.getPartialApplicationId(false);
   }

   private Module findModuleInArray(Module[] modules, String id) {
      Module[] var3 = modules;
      int var4 = modules.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Module module = var3[var5];
         if (id.equals(module.getId())) {
            return module;
         }
      }

      return null;
   }

   private Module findModule(Module[] modules, String uri) {
      Module m = this.findModuleInArray(modules, uri);
      if (m != null) {
         return m;
      } else {
         String contextRoot = EarUtils.findContextRoot(this.appCtx, uri);
         return contextRoot != null ? this.findModuleInArray(modules, contextRoot) : null;
      }
   }

   private void initClassLoaderStructure(ClassloaderStructureBean clStruct, GenericClassLoader cl, GenericClassLoader sharedCL, Map completedInit, Module[] modules) throws DeploymentException {
      ModuleRefBean[] refs = clStruct.getModuleRefs();
      List missRefAtThisLevel = new ArrayList();

      for(int i = 0; i < refs.length; ++i) {
         String uri = refs[i].getModuleUri();
         Module m = this.findModule(modules, uri);
         if (this.isDebugEnabled()) {
            this.debug("** Module for uri : " + uri + " id : " + (m == null ? "null" : m.getId()));
         }

         if (m != null) {
            if (this.missingModuleRefs.size() > 0 || missRefAtThisLevel.size() > 0) {
               throw new NonFatalDeploymentException(" The following modules: " + (this.missingModuleRefs.size() > 0 ? this.missingModuleRefs.toString() : "") + (missRefAtThisLevel.size() > 0 ? missRefAtThisLevel.toString() : "") + " which module uri " + uri + " depends on are not initiated in this application.");
            }

            String anString = cl.getAnnotation().getAnnotationString();
            if (anString == null || "".equals(anString)) {
               cl.setAnnotation(new weblogic.utils.classloaders.Annotation(this.appCtx.getAppDeploymentMBean().getApplicationIdentifier(), uri));
            }

            if (completedInit.get(uri) != null) {
               throw new DeploymentException("The module-uri " + uri + " is declared more than once in the classloader-structure.  Check your weblogic-application.xml");
            }

            m.initUsingLoader(this.appCtx, cl, this.appCtx);
            if (this.appCtx.isDeployedThroughResourceGroupTemplate()) {
               this.initSharedModuleClassLoading(m, cl, sharedCL, true);
            }

            this.appClassLoaderManager.addModuleLoader(cl, m.getId());
            completedInit.put(uri, m);
            if (!uri.equals(m.getId())) {
               completedInit.put(m.getId(), m);
            }
         } else {
            Module orig = null;
            if (this.appCtx.getPartialRedeployURIs() != null) {
               orig = this.findModule(this.appCtx.getApplicationModules(), uri);
            }

            if (orig == null) {
               if (!EarUtils.isValidModule(this.appCtx, uri)) {
                  throw new DeploymentException("classloader-structure element in weblogic-application.xml is referencing the module-uri " + uri + " which does not exist in this application.");
               }

               missRefAtThisLevel.add(uri);
               if (this.isDebugEnabled()) {
                  this.debug("** Module for uri : " + uri + " is missing.");
               }
            } else {
               if (this.isDebugEnabled()) {
                  Module[] ms = this.appCtx.getApplicationModules();
                  this.debug("** Modules in appCtx:");

                  for(int j = 0; j < ms.length; ++j) {
                     this.debug("** Module :" + ms[j].getId());
                  }
               }

               GenericClassLoader gcl = this.appClassLoaderManager.findModuleLoader(this.appCtx.getAppDeploymentMBean().getApplicationIdentifier(), orig.getId());
               if (this.isDebugEnabled()) {
                  this.debug("** Looking up Classloader for uri : " + uri + " id: " + orig.getId() + " ClassLoader: " + gcl);
               }

               if (gcl != null) {
                  this.clstructLoaders.remove(cl);
                  cl = gcl;
               }
            }
         }
      }

      ClassloaderStructureBean[] subCL = clStruct.getClassloaderStructures();
      if (subCL != null && subCL.length != 0) {
         if (missRefAtThisLevel.size() > 0) {
            this.missingModuleRefs.add(missRefAtThisLevel);
         }

         for(int i = 0; i < subCL.length; ++i) {
            GenericClassLoader subLoader = new GenericClassLoader(new MultiClassFinder(), cl);
            GenericClassLoader sharedSubLoader = new GenericClassLoader(new MultiClassFinder(), sharedCL);
            if (this.clstructLoaders.size() == 0) {
               this.clstructLoaders = new LinkedHashSet();
            }

            this.clstructLoaders.add(subLoader);
            this.initClassLoaderStructure(subCL[i], subLoader, sharedSubLoader, completedInit, modules);
         }

         this.missingModuleRefs.remove(missRefAtThisLevel);
      }
   }

   private void initRemainingModules(Map completedInit, Module[] modules) throws DeploymentException {
      for(int i = 0; i < modules.length; ++i) {
         String uri = modules[i].getId();
         if (!completedInit.containsKey(uri)) {
            this.initModule(modules[i]);
            completedInit.put(uri, modules[i]);
         }
      }

   }

   private void initModulesCLStructure(Module[] modules) throws DeploymentException {
      Map completedInit = new HashMap(modules.length);
      GenericClassLoader cl = this.appCtx.getAppClassLoader();
      ClassloaderStructureBean clStruct = this.appCtx.getWLApplicationDD().getClassloaderStructure();

      try {
         this.initClassLoaderStructure(clStruct, cl, this.getSharedAppClassLoader(), completedInit, modules);
         this.initRemainingModules(completedInit, modules);
      } catch (Throwable var9) {
         this.destroy(completedInit);
         this.throwAppException(var9);
      } finally {
         this.missingModuleRefs.clear();
      }

   }

   private void destroy(Map moduleMap) throws DeploymentException {
      Module[] m = new Module[moduleMap.size()];
      this.destroy((Module[])((Module[])moduleMap.values().toArray(m)), m.length);
   }

   private ClassLoadingConfiguration getConfiguration(Module m) {
      Module unwrappedModule = this.appCtx.getModuleAttributes(m.getId()).getUnwrappedModule();
      return unwrappedModule instanceof ClassLoadingConfiguration ? (ClassLoadingConfiguration)unwrappedModule : null;
   }

   private void initModule(Module m) throws ModuleException {
      GenericClassLoader appCL = this.appCtx.getAppClassLoader();
      GenericClassLoader moduleLoader = m.init(this.appCtx, appCL, this.appCtx);
      if (this.appCtx.isDeployedThroughResourceGroupTemplate()) {
         GenericClassLoader sharedModuleCL;
         if (moduleLoader != appCL) {
            sharedModuleCL = this.createSharedModuleCL(m);
         } else {
            sharedModuleCL = this.getSharedAppClassLoader();
         }

         this.initSharedModuleClassLoading(m, moduleLoader, sharedModuleCL, false);
      }

      this.appClassLoaderManager.addModuleLoader(moduleLoader, m.getId());
   }

   private void initSharedModuleClassLoading(Module module, GenericClassLoader instanceModuleCL, GenericClassLoader sharedModuleCL, boolean classLoaderStructureScenario) throws ModuleException {
      if (instanceModuleCL == null) {
         throw new IllegalStateException("Instance module class loader must not be null");
      } else if (sharedModuleCL == null) {
         throw new IllegalStateException("Shared module class loader must not be null");
      } else {
         ClassLoadingConfiguration classLoadingConfiguration = this.getConfiguration(module);
         if (classLoadingConfiguration != null) {
            if (this.isDebugEnabled()) {
               this.debug("Module found to be of type that supports Class Loading configuration: " + module.getId());
            }

            ClassLoadingBean bean = classLoadingConfiguration.getClassLoading();
            ModuleContext modCtx = this.appCtx.getModuleContext(module.getId());
            if (classLoaderStructureScenario) {
               GenericClassLoader existingSharedModuleCL = ClassLoaders.instance.registerCustomSharedAppClassLoader(this.appIdSansPartitionName, module.getId(), classLoadingConfiguration, sharedModuleCL);
               if (existingSharedModuleCL == sharedModuleCL) {
                  modCtx.createdSharedModuleClassLoader();
               } else {
                  sharedModuleCL = existingSharedModuleCL;
               }
            }

            ShareableBean[] sBeans = null;
            if (bean != null) {
               sBeans = bean.getShareables();
            }

            ShareabilityManager sManager;
            try {
               sManager = new ShareabilityManager(sBeans);
            } catch (ShareabilityException var15) {
               throw new ModuleException(var15);
            }

            Object lock = ClassLoaders.instance.getSharedAppClassLoaderName(this.appIdSansPartitionName, module.getId()).intern();
            synchronized(lock) {
               if (this.isDebugEnabled()) {
                  this.debug("Checking shareability for module " + module.getId());
               }

               if (ClassLoaders.instance.isShareable(this.appIdSansPartitionName, module.getId(), classLoadingConfiguration)) {
                  if (this.isDebugEnabled()) {
                     this.debug("Shared application class loader is found to be shareable");
                  }

                  modCtx.markShareability();
                  if (modCtx.wasSharedModuleClassLoaderCreated()) {
                     MultiClassFinder sharedAppFindersFromApp = new MultiClassFinder();
                     sManager.extractShareableFinders((MultiClassFinder)instanceModuleCL.getClassFinder(), sharedAppFindersFromApp);
                     if (this.isDebugEnabled()) {
                        this.debug("Number of finders found and added to the shared application class loader: " + sharedAppFindersFromApp.size());
                     }

                     sharedModuleCL.addClassFinder(sharedAppFindersFromApp);
                     ClassLoaderUtils.initFilterPatterns(classLoadingConfiguration.getPreferApplicationPackages(), classLoadingConfiguration.getPreferApplicationResources(), sharedModuleCL);
                  } else {
                     sManager.extractShareableFinders((MultiClassFinder)instanceModuleCL.getClassFinder(), new MultiClassFinder());
                     if (this.isDebugEnabled()) {
                        this.debug("Extracted out class finders for the module since they are already added to shared module class loader: " + module.getId());
                     }
                  }

                  if (classLoaderStructureScenario) {
                     if (instanceModuleCL.getAltParent() == null) {
                        instanceModuleCL.setAltParent(sharedModuleCL);
                     }
                  } else {
                     instanceModuleCL.setAltParent(sharedModuleCL);
                  }

                  ClassLoaders.instance.addReferenceToSharedAppClassLoader(this.appIdSansPartitionName, module.getId(), this.appCtx.getApplicationId());
               }
            }
         }

      }
   }

   private GenericClassLoader createSharedModuleCL(Module module) throws ModuleException {
      ClassLoadingConfiguration classLoadingConfiguration = this.getConfiguration(module);
      if (this.isDebugEnabled()) {
         this.debug("Module found to be of type that supports Class Loading configuration: " + module.getId());
      }

      Object lock = ClassLoaders.instance.getSharedAppClassLoaderName(this.appIdSansPartitionName, module.getId()).intern();
      synchronized(lock) {
         if (this.isDebugEnabled()) {
            this.debug("Creating shared module class loader for module " + module.getId());
         }

         GenericClassLoader sharedModuleCL = ClassLoaders.instance.getSharedAppClassLoader(this.appIdSansPartitionName, module.getId());
         if (sharedModuleCL == null) {
            sharedModuleCL = ClassLoaders.instance.createSharedAppClassLoader(this.appIdSansPartitionName, module.getId(), classLoadingConfiguration, this.getSharedAppClassLoader());
            this.appCtx.getModuleContext(module.getId()).createdSharedModuleClassLoader();
         }

         return sharedModuleCL;
      }
   }

   private void initModules(Module[] modules) throws DeploymentException {
      for(int i = 0; i < modules.length; ++i) {
         try {
            this.initModule(modules[i]);
         } catch (Throwable var6) {
            try {
               this.destroy(i);
            } catch (DeploymentException var5) {
               J2EELogger.logIgnoringUndeploymentError(var5);
            }

            this.throwAppException(var6);
         }
      }

   }

   private boolean hasClassLoaderStructure() {
      return this.appCtx.getWLApplicationDD() != null && this.appCtx.getWLApplicationDD().getClassloaderStructure() != null;
   }

   public void prepare() throws DeploymentException {
      if (this.hasClassLoaderStructure()) {
         this.initModulesCLStructure(this.appCtx.getApplicationModules());
      } else {
         this.initModules(this.appCtx.getApplicationModules());
      }

   }

   public void unprepare() throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();
      boolean var8 = false;

      try {
         var8 = true;
         this.destroy(modules, modules.length);
         var8 = false;
      } finally {
         if (var8) {
            if (this.clstructLoaders.size() > 0) {
               Iterator it = this.clstructLoaders.iterator();

               while(it.hasNext()) {
                  GenericClassLoader cl = (GenericClassLoader)it.next();
                  cl.close();
               }

               this.clstructLoaders = Collections.EMPTY_SET;
            }

         }
      }

      if (this.clstructLoaders.size() > 0) {
         Iterator it = this.clstructLoaders.iterator();

         while(it.hasNext()) {
            GenericClassLoader cl = (GenericClassLoader)it.next();
            cl.close();
         }

         this.clstructLoaders = Collections.EMPTY_SET;
      }

   }

   private void destroy(int N) throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();
      this.destroy(modules, N);
   }

   private void destroy(Module[] modules, int N) throws DeploymentException {
      ErrorCollectionException e = null;

      for(int i = N - 1; i >= 0; --i) {
         try {
            Module m = modules[i];
            m.destroy(this.appCtx);
            ModuleContext modCtx = this.appCtx.getModuleContext(m.getId());
            if (modCtx.checkShareability()) {
               Object lock = ClassLoaders.instance.getSharedAppClassLoaderName(this.appIdSansPartitionName, m.getId()).intern();
               synchronized(lock) {
                  ClassLoaders.instance.removeReferenceOrDestroyOnLastReference(this.appIdSansPartitionName, m.getId(), this.appCtx.getApplicationId());
                  ClassLoaders.instance.removeReferenceOrDestroyOnLastReference(this.appIdSansPartitionName, (String)null, this.appCtx.getApplicationId());
               }
            }
         } catch (Throwable var11) {
            if (this.isDebugEnabled()) {
               this.debug("** Module " + modules[i] + " threw " + StackTraceUtils.throwable2StackTrace(var11));
            }

            if (e == null) {
               e = new ErrorCollectionException();
            }

            e.addError(var11);
         }
      }

      if (e != null) {
         this.throwAppException(e);
      }

   }

   public void start(String[] uris) throws DeploymentException {
      Module[] startingModules = this.appCtx.getStartingModules();
      if (this.hasClassLoaderStructure()) {
         this.initModulesCLStructure(startingModules);
      } else {
         this.initModules(startingModules);
      }

   }

   public void stop(String[] uris) throws DeploymentException {
      Module[] stoppingModules = this.appCtx.getStoppingModules();

      try {
         this.destroy(stoppingModules, stoppingModules.length);
      } catch (DeploymentException var4) {
         J2EELogger.logIgnoringUndeploymentError(var4);
      }

   }

   private GenericClassLoader getSharedAppClassLoader() {
      if (this.sharedAppClassLoader == null) {
         this.sharedAppClassLoader = ClassLoaders.instance.getSharedAppClassLoader(this.appIdSansPartitionName, (String)null);
         if (this.sharedAppClassLoader == null) {
            this.sharedAppClassLoader = ClassLoaders.instance.createSharedAppClassLoader(this.appIdSansPartitionName, (String)null, (ClassLoadingConfiguration)null, (GenericClassLoader)null);
         }

         ClassLoaders.instance.addReferenceToSharedAppClassLoader(this.appIdSansPartitionName, (String)null, this.appCtx.getApplicationId());
      }

      return this.sharedAppClassLoader;
   }
}
