package weblogic.ejb.container.deployer;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.CDIUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ConcurrentModule;
import weblogic.application.DeployableObjectInfo;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.ParentModule;
import weblogic.application.PojoAnnotationSupportingModule;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.Type;
import weblogic.application.UpdateListener;
import weblogic.application.internal.BaseJ2EEModule;
import weblogic.application.library.IllegalSpecVersionTypeException;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.library.LibraryConstants.AutoReferrer;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.internal.EJBComponentRuntimeMBeanImpl;
import weblogic.ejb.spi.EJBDeploymentException;
import weblogic.ejb.spi.EJBJar;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.EJBComponentMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.persistence.AbstractPersistenceUnitRegistry;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.ModulePersistenceUnitRegistry;
import weblogic.persistence.PersistenceUnitRegistryInitializer;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class EJBModule extends BaseJ2EEModule implements PojoAnnotationSupportingModule, ModuleLocationInfo, UpdateListener, PersistenceUnitRegistryProvider, DeployableObjectInfo, ConcurrentModule, ParentModule {
   private static final DebugLogger debugLogger;
   private EJBComponentRuntimeMBeanImpl compRTMBean;
   private VirtualJarFile jf;
   private EJBDeployer ejbDeployer;
   private boolean beanClassRedeployEnabled = false;
   private final Map implClassToNameMap = new HashMap();
   private final List updateImplClasses = new ArrayList();
   protected GenericClassLoader classLoader;
   private ClassFinder classFinder;
   private String altDD = null;
   protected ApplicationContextInternal appCtx;
   private final String uri;
   private ModuleExtensionContextImpl extCtx;
   private EJBMetadataHandler ejbMetadataHandler;
   private EjbJarArchive archive;
   private EJBJar ejbJar;
   private AbstractPersistenceUnitRegistry persistenceUnitRegistry;
   private AbstractPersistenceUnitRegistry proposedPersistenceUnitRegistry;
   private LibraryManager libManager = null;
   private VirtualJarFile[] libJars = null;
   private ClassFinder[] libFinders = null;
   private String ejbCompMBeanName;
   private CoherenceService coherenceService;
   private volatile boolean inAdminFromProduction = false;

   public EJBModule(String uri, CoherenceService coherenceService) {
      this.uri = uri;
      this.coherenceService = coherenceService;
      if (debugLogger.isDebugEnabled()) {
         debug("EJBModule() creates new module: " + uri + ".");
      }

      EJBLogger.logEJBModuleCreated("[" + uri + "]");
   }

   public String getId() {
      return this.uri;
   }

   public String getURI() {
      return this.uri;
   }

   public String getType() {
      return ModuleType.EJB.toString();
   }

   public GenericClassLoader getClassLoader() {
      return this.classLoader;
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[]{this.compRTMBean};
   }

   public DescriptorBean[] getDescriptors() {
      if (this.ejbMetadataHandler == null) {
         throw new IllegalStateException("ejbMetadataHandler not initialized");
      } else {
         return this.ejbMetadataHandler.getDescriptors();
      }
   }

   protected ModuleContext getModuleContext() {
      return this.appCtx.getModuleContext(this.getId());
   }

   protected ModuleRegistry getRegistry() {
      return this.getModuleContext().getRegistry();
   }

   protected void processAnnotations() throws ModuleException {
      this.ejbMetadataHandler.processAnnotations();
   }

   protected void setupPersistenceUnitRegistry() throws DeploymentException {
      try {
         if (this.persistenceUnitRegistry == null) {
            this.persistenceUnitRegistry = new ModulePersistenceUnitRegistry(this.classLoader, this.appCtx, this, true);
            this.persistenceUnitRegistry.setParentRuntimeMBean(this.compRTMBean, this.compRTMBean);
         }

      } catch (EnvironmentException var2) {
         throw new DeploymentException(var2);
      }
   }

   protected void initMBeans() throws ModuleException {
      EJBComponentMBean compMBean = (EJBComponentMBean)this.findComponentMBeanInternal(this.appCtx, this.uri, EJBComponentMBean.class);
      this.ejbCompMBeanName = compMBean != null && compMBean.getName() != null ? compMBean.getName() : this.getURI();

      try {
         this.compRTMBean = new EJBComponentRuntimeMBeanImpl(compMBean != null && compMBean.getName() != null ? compMBean.getName() : this.getId(), this.getId(), this.appCtx.getRuntime(), this.appCtx.getApplicationName());
      } catch (ManagementException var3) {
         EJBLogger.logStackTraceAndMessage("Error creating RuntimeMBean for EJBModule:" + this, var3);
         throw new ModuleException("Error creating RuntimeMBean for EJBModule '" + this + "': " + var3.getMessage(), var3);
      }
   }

   protected void unregisterMBeans() throws ModuleException {
      if (this.compRTMBean != null) {
         try {
            this.compRTMBean.unregister();
         } catch (ManagementException var5) {
            throw new ModuleException("Error unregistering RuntimeMBean: " + var5.getMessage(), var5);
         } finally {
            this.compRTMBean = null;
         }
      }

   }

   private void initJNDIContext() {
      Context envCtx = this.appCtx.getEnvContext();

      try {
         envCtx.lookup("/ejb");
      } catch (NameNotFoundException var5) {
         try {
            envCtx.createSubcontext("ejb");
         } catch (NamingException var4) {
            throw new AssertionError(var4);
         }
      } catch (NamingException var6) {
         throw new AssertionError(var6);
      }

   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, parent, reg);
   }

   public GenericClassLoader init(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.appCtx = (ApplicationContextInternal)ac;
      this.classLoader = parent;
      this.initJNDIContext();
      reg.addUpdateListener(this);

      try {
         if (debugLogger.isDebugEnabled()) {
            debug("init() on module : " + this + ".");
         }

         this.ejbJar = new EJBJar(this.uri, this.appCtx);
         this.jf = this.appCtx.getApplicationFileManager().getVirtualJarFile(this.uri);
         this.classFinder = this.ejbJar.getClassFinder();
         this.classLoader.addClassFinder(this.classFinder);
         PersistenceUnitRegistryInitializer puri = PersistenceUnitRegistryInitializer.getInstance(this.appCtx);
         puri.addPersistenceUnitRegistryPrepareTask(new PersistenceUnitRegistryInitializer.PersistenceUnitRegistryPrepareTask() {
            public void execute() throws ModuleException {
               try {
                  EJBModule.this.setupPersistenceUnitRegistry();
               } catch (DeploymentException var2) {
                  throw new ModuleException(var2);
               }
            }
         });
         this.initMBeans();
         this.initializeAutoRefLibraries();
         this.archive = new EjbJarArchive(this.getModuleContext(), this.appCtx, this.classFinder);
         this.ejbMetadataHandler = new EJBMetadataHandler(this.getModuleContext(), this.archive, this.libJars);
         this.ejbMetadataHandler.loadDescriptors();
         this.extCtx = new ModuleExtensionContextImpl(this.getModuleContext(), this.classFinder, this.archive);
         return this.classLoader;
      } catch (Exception var7) {
         EJBLogger.logExcepionInitializing(this.getURI(), var7);

         try {
            this.destroy(reg);
         } catch (ModuleException var6) {
            EJBLogger.logExcepionUninitializing(this.getURI(), var6);
         }

         throw new ModuleException(var7);
      }
   }

   public void remove() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("remove() on module : " + this + ".");
      }

      if (this.ejbJar != null) {
         this.ejbJar.remove();
      }

      if (this.ejbDeployer != null) {
         this.ejbDeployer.remove();
         this.ejbDeployer = null;
      }

   }

   public void adminToProduction() {
      if (debugLogger.isDebugEnabled()) {
         debug("adminToProduction() on module : " + this + ".");
      }

      if (this.inAdminFromProduction) {
         this.ejbDeployer.adminBackToProduction();
      } else {
         this.ejbDeployer.adminToProduction();
      }

      this.inAdminFromProduction = false;
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
      if (debugLogger.isDebugEnabled()) {
         debug("gracefulProductionToAdmin() on module : " + this + ".");
      }

      this.ejbDeployer.gracefulProductionToAdmin(barrier);
      this.inAdminFromProduction = true;
   }

   public void forceProductionToAdmin() {
      if (debugLogger.isDebugEnabled()) {
         debug("forceProductionToAdmin() on module : " + this + ".");
      }

      this.ejbDeployer.forceProductionToAdmin();
      this.inAdminFromProduction = true;
   }

   public ComponentRuntimeMBean getRuntimeMBean() {
      return this.compRTMBean;
   }

   public final void prepare() throws ModuleException {
      try {
         if (debugLogger.isDebugEnabled()) {
            debug("prepare() on module : " + this + ".");
         }

         PersistenceUnitRegistryInitializer puri = PersistenceUnitRegistryInitializer.getInstance(this.appCtx);
         puri.setupPersistenceUnitRegistries();
         ModuleRegistry mr = this.getRegistry();
         if (mr != null) {
            mr.put(PersistenceUnitRegistryProvider.class.getName(), this);
         }

         EnvironmentManager em = new EJBJarEnvironmentManager(this.appCtx, this.getName(), this.getURI(), this.getId());
         if (this.extCtx != null) {
            this.extCtx.setEnvironmentManager(em);
         }

         EJBLogger.logStartProcessAnnotaions(this.uri);
         this.processAnnotations();
         WeblogicEjbJarBean wlJar = this.getEJBDescriptor().getWeblogicEjbJarBean();
         this.beanClassRedeployEnabled = wlJar.isEnableBeanClassRedeploy();
         Iterator var5;
         if (this.beanClassRedeployEnabled) {
            this.setupEJBToImplClassDependencies(this.classLoader);
            if (debugLogger.isDebugEnabled()) {
               debug("Dumping beanImpl -> ejbName map");
               var5 = this.implClassToNameMap.entrySet().iterator();

               while(var5.hasNext()) {
                  Map.Entry e = (Map.Entry)var5.next();
                  debug((String)e.getKey() + " : " + e.getValue());
               }
            }
         }

         if (this.beanClassRedeployEnabled) {
            if (!this.getEJBDescriptor().verSupportsBeanRedeploy()) {
               wlJar.setEnableBeanClassRedeploy(false);
               EJBLogger.logEnableBeanClassRedeployIsNotSupportedForEJB3(this.getName(), ((DescriptorBean)this.getEJBDescriptor().getEjbJarBean()).getDescriptor().getOriginalVersionInfo());
            } else {
               var5 = this.implClassToNameMap.keySet().iterator();

               while(var5.hasNext()) {
                  String clsName = (String)var5.next();
                  this.classLoader.excludeClass(clsName);
               }
            }
         }

         this.ejbDeployer = new EJBDeployer(this.getName(), this.getModuleURI(), this.getRegistry(), this.getClassLoader(), this.compRTMBean, em, this.appCtx, this.getId(), this.ejbCompMBeanName, this.isCDIEnabled(), this.getBeanDiscoveryMode());
         this.ejbDeployer.prepare(this.jf, this.getEJBDescriptor());
         this.setupCoherenceCaches(wlJar);
      } catch (DeploymentException var8) {
         EJBLogger.logExcepionPreparing(this.getURI(), var8);

         try {
            this.unprepare();
         } catch (ModuleException var7) {
            EJBLogger.logExcepionUninitializing(this.getURI(), var7);
         }

         throw new ModuleException("Exception preparing module: " + this + "\n" + var8.getMessage(), var8);
      }
   }

   protected void setupCoherenceCaches(WeblogicEjbJarBean wlJar) throws DeploymentException {
      if (this.shouldProcessCoherence(this.getClassLoader()) && wlJar != null && wlJar.getCoherenceClusterRef() != null) {
         if (this.coherenceService == null) {
            throw new DeploymentException("Coherence-Cluster-Ref found but Coherence not available");
         }

         this.coherenceService.setupCoherenceCaches(this.getClassLoader(), this.compRTMBean, wlJar.getCoherenceClusterRef());
      }

   }

   public final void activate() throws IllegalStateException, ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("activate() on module : " + this + " : activating module");
      }

      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.classLoader);

      try {
         this.ejbDeployer.activate(this.getEJBDescriptor(), this.getModuleExtensionContext());
      } catch (DeploymentException var8) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error activating module : " + this + " :\n" + var8.getMessage());
         }

         this.doDeactivate();
         throw new ModuleException("Exception activating module: " + this + "\n" + var8.getMessage(), var8);
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

      try {
         this.registerBeanUpdateListeners();
         this.reconfigPersistenceUnits();
      } catch (Throwable var7) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error activating module : " + this + " :\n" + var7.getMessage());
         }

         this.deactivate();
         throw new ModuleException("Exception activating module: " + this + "\n" + var7.getMessage(), var7);
      }
   }

   protected void initializeAutoRefLibraries() throws DeploymentException {
      try {
         if (this.libManager == null) {
            this.libManager = new LibraryManager(new LibraryReferencer(this.uri, this.compRTMBean, "Unresolved library references for module " + this.uri), this.appCtx.getPartitionName());
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
               this.classLoader.addClassFinder(this.libFinders[i]);
            }

         }
      } catch (IOException var4) {
         throw new EJBDeploymentException(this.uri, this.uri, var4);
      }
   }

   private void reconfigPersistenceUnits() throws ModuleException {
      PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
      if (pur != null) {
         Iterator var2 = pur.getPersistenceUnitNames().iterator();

         while(var2.hasNext()) {
            String pun = (String)var2.next();
            BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);

            try {
               puii.activate(this.appCtx.getEnvContext());
            } catch (EnvironmentException var6) {
               throw new ModuleException("Error activating Persistence Units.", var6);
            }
         }

      }
   }

   private void resetPersistenceUnits() throws ModuleException {
      PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
      if (pur != null) {
         Iterator var2 = pur.getPersistenceUnitNames().iterator();

         while(var2.hasNext()) {
            String pun = (String)var2.next();
            BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);
            puii.deactivate();
         }

      }
   }

   public final void start() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("start() on module : " + this + " : starting module");
      }

      ClassLoader clSave = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.classLoader);

      try {
         this.ejbDeployer.start();
      } catch (EJBDeploymentException var6) {
         EJBLogger.logExcepionStarting(this.getURI(), var6);
         throw new ModuleException("Exception starting module: " + this + "\n" + var6.getMessage(), var6);
      } finally {
         Thread.currentThread().setContextClassLoader(clSave);
      }

   }

   public final void deactivate() throws IllegalStateException, ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("deactivate() on module : " + this + ".");
      }

      this.resetPersistenceUnits();
      this.unregisterBeanUpdateListeners();
      this.doDeactivate();
   }

   public boolean acceptURI(String u) {
      if (!this.acceptModuleUri(this.appCtx, this.uri, u)) {
         debug("acceptURI: does not acceptModuleURI: " + u);
         return false;
      } else if (this.ejbMetadataHandler != null && this.ejbMetadataHandler.acceptURI(u)) {
         return true;
      } else if (this.getPersistenceDDName(u) != null) {
         debug("getPersistenceDDName(u) != null: " + u);
         debug("acceptURI: does acceptModuleURI: " + u);
         return true;
      } else if (this.findImplClassName(u) != null) {
         debug("findImplClassName(u) != null: " + u);
         debug("acceptURI: does acceptModuleURI: " + u);
         return true;
      } else {
         debug("acceptURI: none of the cases applies, therefore no acceptance of uri: " + u);
         return false;
      }
   }

   private String findImplClassName(String updateUri) {
      if (!updateUri.endsWith(".class")) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         if (this.appCtx.isEar()) {
            sb.append(updateUri.substring(this.uri.length() + 1));
         } else {
            sb.append(updateUri);
         }

         sb.setLength(sb.length() - 6);
         if (sb.length() == 0) {
            return null;
         } else {
            for(int i = 0; i < sb.length(); ++i) {
               char c = sb.charAt(i);
               if (c == '\\' || c == '/') {
                  sb.setCharAt(i, '.');
               }
            }

            String candidate = sb.toString();
            if (this.implClassToNameMap.containsKey(candidate)) {
               return candidate;
            } else {
               return null;
            }
         }
      }
   }

   private String getPersistenceDDName(String updateUri) {
      if (!updateUri.endsWith("xml")) {
         return null;
      } else {
         String candidate = updateUri.replace('\\', '/');
         if (this.appCtx.isEar()) {
            candidate = candidate.substring(this.uri.length() + 1);
            debug("getPersistenceDDName: candidate is " + candidate);
         }

         if (candidate.length() == 0) {
            return null;
         } else if (candidate.equals("META-INF/persistence.xml")) {
            return candidate;
         } else {
            return candidate.equals("META-INF/persistence-configuration.xml") ? candidate : null;
         }
      }
   }

   public void prepareUpdate(String uri) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("prepareUpdate called for app: " + this.appCtx.getApplicationId() + " uri: " + this.uri);
      }

      if (uri.endsWith(".xml")) {
         this.prepareDescriptorUpdate(uri);
      } else {
         this.updateImplClasses.clear();
         String implClassName = this.findImplClassName(uri);
         if (implClassName != null) {
            this.updateImplClasses.add(implClassName);
         }

         if (!this.updateImplClasses.isEmpty()) {
            if (!this.beanClassRedeployEnabled) {
               EJBLogger.logEJBModuleRolledBackSinceImplCLDisabled(this.getDisplayName(), (String)this.updateImplClasses.get(0));
               throw new ModuleException("Unable to update bean implementation class for EJB " + uri + " since enable-bean-class-redeploy was not enabled in the weblogic-ejb-jar.xml");
            } else {
               GenericClassLoader tempCL = new GenericClassLoader(this.classFinder);

               try {
                  String className = this.ejbDeployer.needsRecompile(this.updateImplClasses, tempCL);
                  if (className != null) {
                     EJBLogger.logEJBModuleRolledBackSinceChangeIncompatible(this.getDisplayName(), className);
                     if (debugLogger.isDebugEnabled()) {
                        debug("needsRecompile returned: " + className);
                     }

                     throw new ModuleException("Attempt to update EJB implementation class in EJB " + uri + " failed because " + className + "requires weblogic.appc to be run.  You must redeploy the application.");
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debug("needsRecompile returned false");
                     }

                  }
               } catch (ClassNotFoundException var5) {
                  if (debugLogger.isDebugEnabled()) {
                     debug("ClassNotFoundException during needsRecompile: " + var5);
                     var5.printStackTrace();
                  }

                  throw new ModuleException(var5);
               }
            }
         }
      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("activateUpdate() on module : " + this + ".");
      }

      if (uri.endsWith(".xml")) {
         this.activateDescriptorUpdate(uri);
      } else if (!this.updateImplClasses.isEmpty()) {
         if (debugLogger.isDebugEnabled()) {
            debug("Updating beanImpl ClassLoader for EJBs:");
         }

         Set ejbs = new HashSet();
         Iterator var3 = this.updateImplClasses.iterator();

         String ejbName;
         while(var3.hasNext()) {
            ejbName = (String)var3.next();
            ejbs.addAll((Collection)this.implClassToNameMap.get(ejbName));
         }

         var3 = ejbs.iterator();

         while(var3.hasNext()) {
            ejbName = (String)var3.next();
            if (debugLogger.isDebugEnabled()) {
               debug(ejbName);
            }

            try {
               this.ejbDeployer.updateImplClassLoader(ejbName);
            } catch (WLDeploymentException var6) {
               throw new ModuleException("Module :" + uri + " cannot be redeployed: " + var6);
            }
         }

      }
   }

   public void rollbackUpdate(String uri) {
      if (debugLogger.isDebugEnabled()) {
         debug("rollbackUpdate() on module : " + this + ".");
      }

      if (uri.endsWith(".xml")) {
         this.rollbackDescriptorUpdate(uri);
      }

   }

   private void prepareDescriptorUpdate(String uri) throws ModuleException {
      if (this.ejbMetadataHandler == null || !this.ejbMetadataHandler.prepareDescriptorUpdate(uri)) {
         String descriptorName = this.getPersistenceDDName(uri);
         debug("prepareDescriptorUpdate: persistence descriptorName = " + descriptorName);

         assert descriptorName != null;

         if (this.proposedPersistenceUnitRegistry == null) {
            try {
               this.proposedPersistenceUnitRegistry = new ModulePersistenceUnitRegistry(this.classLoader, this.appCtx, this, false);
            } catch (EnvironmentException var6) {
               throw new ModuleException(var6);
            }
         }

         try {
            AbstractPersistenceUnitRegistry mpur = this.getPersistenceUnitRegistry();
            Descriptor d1 = mpur.getDescriptor(descriptorName);
            mpur = this.proposedPersistenceUnitRegistry;
            d1.prepareUpdate(mpur.getDescriptor(descriptorName));
         } catch (DescriptorUpdateRejectedException var5) {
            throw new ModuleException(var5);
         }
      }

   }

   private void activateDescriptorUpdate(String uri) throws ModuleException {
      if (this.ejbMetadataHandler == null || !this.ejbMetadataHandler.activateDescriptorUpdate(uri)) {
         String descriptorName = this.getPersistenceDDName(uri);

         assert descriptorName != null;

         this.proposedPersistenceUnitRegistry = null;
         AbstractPersistenceUnitRegistry mpur = this.getPersistenceUnitRegistry();
         Descriptor d1 = mpur.getDescriptor(descriptorName);

         try {
            d1.activateUpdate();
         } catch (DescriptorUpdateFailedException var6) {
            throw new ModuleException(var6);
         }
      }

   }

   private void rollbackDescriptorUpdate(String uri) {
      if (this.ejbMetadataHandler == null || !this.ejbMetadataHandler.rollbackDescriptorUpdate(uri)) {
         String descriptorName = this.getPersistenceDDName(uri);

         assert descriptorName != null;

         this.proposedPersistenceUnitRegistry = null;
         AbstractPersistenceUnitRegistry mpur = this.getPersistenceUnitRegistry();
         Descriptor d1 = mpur.getDescriptor(descriptorName);
         d1.rollbackUpdate();
      }

   }

   protected EjbDescriptorBean getEJBDescriptor() {
      return this.ejbMetadataHandler.getEjbDescriptorBean();
   }

   public void unprepare() throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("unprepare() on module : " + this + ".");
      }

      if (this.archive != null) {
         this.archive.reset();
      }

      if (this.ejbDeployer != null) {
         this.ejbDeployer.unprepare();
         this.releaseCoherenceCaches();
      }

      if (this.persistenceUnitRegistry != null) {
         this.persistenceUnitRegistry.close();
         this.persistenceUnitRegistry = null;
      }

      if (this.ifShutdownHappensOnPartition() && this.ejbDeployer != null) {
         this.ejbDeployer = null;
      }

   }

   private boolean ifShutdownHappensOnPartition() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String partitionName = cic.getPartitionName();
      return partitionName != null ? EJBPartitionManagerInterceptor.isPartitionShutdown() : false;
   }

   protected void releaseCoherenceCaches() {
      if (this.shouldProcessCoherence(this.getClassLoader()) && this.coherenceService != null) {
         EjbDescriptorBean ejbDesc = this.getEJBDescriptor();
         if (ejbDesc != null && ejbDesc.getWeblogicEjbJarBean() != null) {
            CoherenceClusterRefBean cohRefBean = ejbDesc.getWeblogicEjbJarBean().getCoherenceClusterRef();
            if (cohRefBean != null) {
               try {
                  this.coherenceService.releaseCoherenceCaches(this.getClassLoader(), this.compRTMBean, this.getModuleURI(), cohRefBean);
               } catch (Exception var4) {
                  EJBLogger.logErrorUndeploying(this.uri, var4);
               }
            }
         }
      }

   }

   private void doDeactivate() {
      this.ejbDeployer.deactivate(this.getEJBDescriptor());
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      if (debugLogger.isDebugEnabled()) {
         debug("destroy() on module : " + this + ".");
      }

      int var3;
      int var4;
      if (this.libFinders != null) {
         ClassFinder[] var2 = this.libFinders;
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            ClassFinder cf = var2[var4];
            cf.close();
         }

         this.libFinders = null;
      }

      if (this.libJars != null) {
         VirtualJarFile[] var9 = this.libJars;
         var3 = var9.length;

         for(var4 = 0; var4 < var3; ++var4) {
            VirtualJarFile vjf = var9[var4];

            try {
               vjf.close();
            } catch (Exception var8) {
               EJBLogger.logStackTrace(var8);
            }
         }

         this.libJars = null;
      }

      if (this.libManager != null) {
         this.libManager.removeReferences();
         this.libManager = null;
      }

      this.unregisterMBeans();
      this.archive = null;
      this.ejbMetadataHandler = null;
      reg.removeUpdateListener(this);
      if (this.classFinder != null) {
         this.classFinder.close();
      }

      try {
         if (this.jf != null) {
            this.jf.close();
         }
      } catch (Exception var7) {
         EJBLogger.logStackTrace(var7);
      }

      this.jf = null;
      this.classLoader = null;
      this.classFinder = null;
   }

   public String getName() {
      return this.getModuleContext().getName();
   }

   private String getDisplayName() {
      StringBuilder sb = new StringBuilder(this.getName());
      sb.append("(Application: ").append(this.appCtx.getApplicationId()).append(")");
      return sb.toString();
   }

   public void setAltDD(String newValue) {
      this.altDD = newValue;
   }

   public String getAltDD() {
      return this.altDD;
   }

   private void registerBeanUpdateListeners() {
      PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
      if (pur != null) {
         Iterator var2 = pur.getPersistenceUnitNames().iterator();

         while(var2.hasNext()) {
            String pun = (String)var2.next();
            BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);
            puii.registerUpdateListeners();
         }
      }

   }

   private void unregisterBeanUpdateListeners() {
      PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
      if (pur != null) {
         Iterator var2 = pur.getPersistenceUnitNames().iterator();

         while(var2.hasNext()) {
            String pun = (String)var2.next();
            BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);
            puii.unregisterUpdateListeners();
         }
      }

   }

   private void setupEJBToImplClassDependencies(GenericClassLoader moduleCL) {
      this.implClassToNameMap.clear();
      EnterpriseBeansBean beans = this.getEJBDescriptor().getEjbJarBean().getEnterpriseBeans();
      EntityBeanBean[] var3 = beans.getEntities();
      int var4 = var3.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         EnterpriseBeanBean ebb = var3[var5];
         this.addEJBToImplClassDependency(ebb.getEjbName(), ebb.getEjbClass());
      }

      SessionBeanBean[] var17 = beans.getSessions();
      var4 = var17.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EnterpriseBeanBean ebb = var17[var5];
         this.addEJBToImplClassDependency(ebb.getEjbName(), ebb.getEjbClass());
      }

      MessageDrivenBeanBean[] var18 = beans.getMessageDrivens();
      var4 = var18.length;

      for(var5 = 0; var5 < var4; ++var5) {
         EnterpriseBeanBean ebb = var18[var5];
         this.addEJBToImplClassDependency(ebb.getEjbName(), ebb.getEjbClass());
      }

      ClassFinder cf = moduleCL.getClassFinder();
      GenericClassLoader tempCL = new GenericClassLoader(cf, moduleCL.getParent());
      Set implSet = this.implClassToNameMap.keySet();
      String[] impls = (String[])implSet.toArray(new String[implSet.size()]);
      String[] var7 = impls;
      int var8 = impls.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String impl = var7[var9];

         try {
            Class child = tempCL.loadClass(impl);

            while(true) {
               Class parent = child.getSuperclass();
               if (null == parent || parent.getClassLoader() != tempCL) {
                  break;
               }

               Set ejbNames = (Set)this.implClassToNameMap.get(impl);
               impl = parent.getName();
               Iterator var14 = ejbNames.iterator();

               while(var14.hasNext()) {
                  String ejbName = (String)var14.next();
                  this.addEJBToImplClassDependency(ejbName, impl);
               }

               child = parent;
            }
         } catch (ClassNotFoundException var16) {
         }
      }

   }

   private void addEJBToImplClassDependency(String ejbName, String className) {
      Set li = (Set)this.implClassToNameMap.get(className);
      if (li == null) {
         Set li = new HashSet();
         li.add(ejbName);
         this.implClassToNameMap.put(className, li);
      } else if (!li.contains(ejbName)) {
         li.add(ejbName);
      }

   }

   public AbstractPersistenceUnitRegistry getPersistenceUnitRegistry() {
      return this.persistenceUnitRegistry;
   }

   public void populateViewFinders(File baseDir, String viewAppName, boolean isArchived, VirtualJarFile moduleViewVjf, SplitDirectoryInfo viewSplitDirInfo, MultiClassFinder finder, MultiClassFinder resourceFinder) throws IOException, IllegalSpecVersionTypeException {
   }

   public String getModuleURI() {
      return this.uri;
   }

   public boolean isParallelEnabled() {
      EjbDescriptorBean desc = this.getEJBDescriptor();
      if (desc != null) {
         EjbJarBean ejbJar = desc.getEjbJarBean();
         if (ejbJar != null) {
            EnterpriseBeansBean ebb = ejbJar.getEnterpriseBeans();
            if (ebb != null) {
               EntityBeanBean[] entities = ebb.getEntities();
               if (entities != null && entities.length > 0) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return this.extCtx;
   }

   public Descriptor getStandardDescriptor() {
      return this.getEJBDescriptor() != null && this.getEJBDescriptor().getEjbJarBean() != null ? ((DescriptorBean)this.getEJBDescriptor().getEjbJarBean()).getDescriptor() : null;
   }

   public String getWLExtensionDirectory() {
      return null;
   }

   private boolean shouldProcessCoherence(ClassLoader cl) {
      return this.appCtx != null && (!this.appCtx.isEar() || this.appCtx.getAppClassLoader() != cl);
   }

   protected boolean isCDIEnabled() throws EJBDeploymentException {
      try {
         return CDIUtils.isModuleCdiEnabled(this.getModuleContext(), this.getModuleExtensionContext(), this.appCtx);
      } catch (InjectionException var2) {
         throw new EJBDeploymentException(this.jf.getName(), this.getModuleURI(), var2);
      }
   }

   protected BeanDiscoveryMode getBeanDiscoveryMode() throws EJBDeploymentException {
      try {
         return CDIUtils.getBeanDiscoveryMode(this.getModuleContext(), this.getModuleExtensionContext(), this.appCtx);
      } catch (InjectionException var2) {
         throw new EJBDeploymentException(this.jf.getName(), this.getModuleURI(), var2);
      }
   }

   public CdiDescriptorBean getCdiDescriptorBean() {
      WeblogicEjbJarBean root = this.getEJBDescriptor().getWeblogicEjbJarBean();
      return root != null ? root.getCdiDescriptor() : null;
   }

   public boolean isMetadataComplete() {
      EjbJarBean ejbJarBean = this.getEJBDescriptor().getEjbJarBean();
      return ejbJarBean != null && ejbJarBean.isMetadataComplete();
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBModule] " + s);
   }

   public String toString() {
      return "EJBModule(" + this.getURI() + ")";
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }
}
