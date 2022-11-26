package weblogic.ejb.container.deployer;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.CDIUtils;
import java.io.File;
import java.io.IOException;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.Type;
import weblogic.application.UpdateListener;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.library.LibraryConstants.AutoReferrer;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.spi.EJBDeploymentException;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbInWarIndicator;
import weblogic.ejb.spi.EjbInWarSupportingModule;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.management.DeploymentException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class EjbModuleExtension extends ModuleExtension implements UpdateListener, EjbInWarIndicator {
   private static final DebugLogger debugLogger;
   private final String moduleId;
   private final String moduleURI;
   private LibraryManager libManager = null;
   private VirtualJarFile[] libJars = null;
   private ClassFinder[] libFinders = null;
   private EJBDeployer ejbDeployer;
   private EJBMetadataHandler metadataHandler;
   private EJBRuntimeHolder compRTMBean;
   private UpdateListener.Registration reg;

   EjbModuleExtension(ModuleExtensionContext modExtCtx, ApplicationContextInternal appCtx, Module extMod) {
      super(modExtCtx, appCtx, extMod);
      this.moduleId = this.extensibleModule.getId();
      this.moduleURI = appCtx.getModuleContext(extMod.getId()).getURI();
      EJBLogger.logEJBModuleCreated("[EjbModuleExtension:" + this.moduleURI + "]");
   }

   public void postPrepare(UpdateListener.Registration reg) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("postPrepare() called on : " + this);
      }

      try {
         if (null != reg) {
            reg.addUpdateListener(this);
            this.reg = reg;
         }

         this.compRTMBean = (EJBRuntimeHolder)this.extensibleModule.getComponentRuntimeMBeans()[0];
         Archive archive = new WarArchive(this.extensionCtx, this.modCtx, this.appCtx);
         this.metadataHandler = new EJBMetadataHandler(this.modCtx, archive, this.libJars);
         EjbDescriptorBean ejbDescriptor = this.metadataHandler.loadDescriptors();
         this.metadataHandler.processAnnotations();
         WeblogicEjbJarBean wlJar = ejbDescriptor.getWeblogicEjbJarBean();
         if (wlJar.isEnableBeanClassRedeploy() && !ejbDescriptor.verSupportsBeanRedeploy()) {
            wlJar.setEnableBeanClassRedeploy(false);
            EJBLogger.logEnableBeanClassRedeployIsNotSupportedForEJB3(this.modCtx.getName(), ((DescriptorBean)ejbDescriptor.getEjbJarBean()).getDescriptor().getOriginalVersionInfo());
         }

         boolean isCDIEnabled = false;
         BeanDiscoveryMode beanDiscoveryMode = null;

         try {
            isCDIEnabled = CDIUtils.isModuleCdiEnabled(this.modCtx, this.extensionCtx, this.appCtx);
            beanDiscoveryMode = CDIUtils.getBeanDiscoveryMode(this.modCtx, this.extensionCtx, this.appCtx);
         } catch (InjectionException var9) {
            throw new EJBDeploymentException(this.modCtx.getVirtualJarFile().getName(), this.modCtx.getURI(), var9);
         }

         this.ejbDeployer = new EJBDeployer(this.modCtx.getName(), this.modCtx.getURI(), this.modCtx.getRegistry(), this.modCtx.getClassLoader(), this.compRTMBean, new WarEnvironmentManager(this.moduleId, this.extensionCtx), this.appCtx, this.moduleId, this.modCtx.getName(), isCDIEnabled, beanDiscoveryMode);
         this.ejbDeployer.prepare(this.modCtx.getVirtualJarFile(), this.metadataHandler.getEjbDescriptorBean());
      } catch (DeploymentException var10) {
         try {
            this.preUnprepare(reg);
            this.postUnprepare();
         } catch (ModuleException var8) {
            EJBLogger.logStackTrace(var8);
         }

         throw new ModuleException("Exception preparing: " + this, var10);
      }
   }

   public void preUnprepare(UpdateListener.Registration reg) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("preprepare() called on : " + this);
      }

      if (null != reg) {
         reg.removeUpdateListener(this);
         this.reg = null;
      }

   }

   public void postUnprepare() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("postprepare() called on : " + this);
      }

      if (this.ejbDeployer != null) {
         this.ejbDeployer.unprepare();
      }

   }

   public void preActivate() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("preActivate() called on : " + this);
      }

   }

   public void postActivate() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("postActivate() called on : " + this);
      }

      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.modCtx.getClassLoader());

      try {
         this.ejbDeployer.activate(this.metadataHandler.getEjbDescriptorBean(), this.extensionCtx);
      } catch (DeploymentException var6) {
         this.postDeactivate();
         throw new ModuleException("Exception activating : " + this, var6);
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

   }

   public void postAdminToProduction() {
      if (debugLogger.isDebugEnabled()) {
         debug("postAdminToProduction() on module : " + this);
      }

      this.ejbDeployer.adminToProduction();
   }

   public void postDeactivate() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("postdeactivate() called on : " + this);
      }

      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.modCtx.getClassLoader());

      try {
         this.ejbDeployer.deactivate(this.metadataHandler.getEjbDescriptorBean());
         ((EjbInWarSupportingModule)this.extensibleModule).cleanEnvironmentEntries();
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

   }

   public void remove() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("remove() called on : " + this);
      }

      if (this.ejbDeployer != null) {
         this.ejbDeployer.remove();
      }

      this.ejbDeployer = null;
   }

   public void start() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("start() called on : " + this);
      }

      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.modCtx.getClassLoader());

      try {
         this.ejbDeployer.start();
      } catch (EJBDeploymentException var6) {
         throw new ModuleException("Exception starting " + this, var6);
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

   }

   public void postRefreshClassLoader() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("refreshClassLoader() called on : " + this);
      }

   }

   public boolean acceptURI(String uri) {
      return this.myExtModulesURI(uri) && this.metadataHandler.acceptURI(uri);
   }

   public void prepareUpdate(String uri) throws ModuleException {
      if (uri.endsWith(".xml")) {
         this.metadataHandler.prepareDescriptorUpdate(uri);
      }

   }

   public void activateUpdate(String uri) throws ModuleException {
      if (uri.endsWith(".xml")) {
         this.metadataHandler.activateDescriptorUpdate(uri);
      }

   }

   public void rollbackUpdate(String uri) {
      if (uri.endsWith(".xml")) {
         this.metadataHandler.rollbackDescriptorUpdate(uri);
      }

   }

   public String toString() {
      return "EjbModuleExtension for Module(" + this.moduleId + ")";
   }

   private void initAutoRefLibraries() throws DeploymentException {
      try {
         if (this.libManager == null) {
            this.libManager = new LibraryManager(new LibraryReferencer(this.moduleURI, this.compRTMBean, "Unresolved library references for module " + this.moduleURI), this.modCtx.getPartitionName());
         }

         this.libManager.lookupAndAddAutoReferences(Type.EJB, AutoReferrer.EJBApp);
         Library[] libs = this.libManager.getAutoReferencedLibraries();
         if (libs != null && libs.length != 0) {
            this.libJars = new VirtualJarFile[libs.length];
            this.libFinders = new ClassFinder[libs.length];

            for(int i = 0; i < libs.length; ++i) {
               File file = libs[i].getLocation();
               this.libJars[i] = VirtualJarFactory.createVirtualJar(file);
               this.libFinders[i] = new JarClassFinder(file);
               this.modCtx.getClassLoader().addClassFinder(this.libFinders[i]);
            }

         }
      } catch (IOException var4) {
         throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var4);
      }
   }

   private void cleanupAutoRefLibraries() {
      int var2;
      int var3;
      if (this.libFinders != null) {
         ClassFinder[] var1 = this.libFinders;
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            ClassFinder cf = var1[var3];
            cf.close();
         }

         this.libFinders = null;
      }

      if (this.libJars != null) {
         VirtualJarFile[] var7 = this.libJars;
         var2 = var7.length;

         for(var3 = 0; var3 < var2; ++var3) {
            VirtualJarFile vjf = var7[var3];

            try {
               vjf.close();
            } catch (Exception var6) {
               EJBLogger.logStackTrace(var6);
            }
         }

         this.libJars = null;
      }

      if (this.libManager != null) {
         this.libManager.removeReferences();
         this.libManager = null;
      }

   }

   private boolean myExtModulesURI(String uri) {
      return !this.appCtx.isEar() || uri.startsWith(this.moduleURI + "/");
   }

   private static void debug(String s) {
      debugLogger.debug("[EjbModuleExtension] " + s);
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }
}
