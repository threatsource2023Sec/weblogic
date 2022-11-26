package weblogic.servlet.internal;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.PersistenceUnitParent;
import weblogic.application.UpdateListener;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.persistence.AbstractPersistenceUnitRegistry;
import weblogic.persistence.BasePersistenceUnitInfo;
import weblogic.persistence.ModulePersistenceUnitRegistry;
import weblogic.persistence.PersistenceUnitRegistryInitializer;
import weblogic.server.GlobalServiceLocator;
import weblogic.spring.monitoring.instrumentation.SpringInstrumentationUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public class WebAppInternalModuleExtension extends ModuleExtension {
   private PersistenceExtension persistenceExtension;
   private SpringInstrumentationExtension springExtension;
   private CacheExtension coherenceExtension;

   public WebAppInternalModuleExtension(ModuleExtensionContext modCtx, ApplicationContextInternal appCtx, Module extensibleModule) {
      super(modCtx, appCtx, extensibleModule);
      WebAppModule module = (WebAppModule)extensibleModule;
      this.persistenceExtension = new PersistenceExtension(module);
      this.springExtension = new SpringInstrumentationExtension(module);
      ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
      CacheExtensionFactory cef = (CacheExtensionFactory)serviceLocator.getService(CacheExtensionFactory.class, new Annotation[0]);
      if (cef != null) {
         this.coherenceExtension = cef.create(module, module.getClassLoader(), module.getWlWebAppBean());
      }

   }

   public void prePrepare() throws ModuleException {
      this.persistenceExtension.initPersistenceUnitRegistry();
      this.persistenceExtension.setupPersistenceUnitRegistry();
      this.springExtension.doInstrumentation();
   }

   public void postUnprepare() {
      try {
         this.persistenceExtension.closePersistenceUnitRegistry();
      } catch (Throwable var2) {
      }

   }

   public void postPrepare(UpdateListener.Registration reg) throws ModuleException {
      this.persistenceExtension.setupPersistenceUnitMBean();
      if (this.coherenceExtension != null) {
         this.coherenceExtension.setupCaches();
      }

   }

   public void preUnprepare(UpdateListener.Registration reg) throws ModuleException {
      this.persistenceExtension.closePersistenceUnitRegistry();
      if (this.coherenceExtension != null) {
         this.coherenceExtension.releaseCaches();
      }

   }

   public void postActivate() throws ModuleException {
      this.persistenceExtension.activatePersistenceUnit();
   }

   public void preDeactivate() throws ModuleException {
      this.persistenceExtension.resetPersistenceUnitRegistry();
      if (this.coherenceExtension != null) {
         this.coherenceExtension.releaseCaches();
      }

   }

   public void preRefreshClassLoader() throws ModuleException {
      this.persistenceExtension.closePersistenceUnitRegistry();
      if (this.coherenceExtension != null) {
         this.coherenceExtension.releaseCaches();
      }

   }

   public void postRefreshClassLoader() throws ModuleException {
      this.persistenceExtension.setupPersistenceUnitRegistry();
      if (this.coherenceExtension != null) {
         this.coherenceExtension.setupCaches();
      }

   }

   private static class PersistenceExtension implements PersistenceUnitRegistryProvider, PersistenceUnitParent {
      private Map runtimePersistenceUnits;
      private PersistenceUnitRegistry persistenceUnitRegistry;
      private WebAppModule module;
      private ApplicationContextInternal appCtx;
      private ModuleContext mc;

      private PersistenceExtension(WebAppModule module) {
         this.module = module;
         this.appCtx = module.getApplicationContext();
         this.mc = this.appCtx.getModuleContext(module.getId());
      }

      public PersistenceUnitRegistry getPersistenceUnitRegistry() {
         return this.persistenceUnitRegistry;
      }

      public void addPersistenceUnit(PersistenceUnitRuntimeMBean childMBean) {
         this.runtimePersistenceUnits.put(childMBean.getPersistenceUnitName(), childMBean);
      }

      private void activatePersistenceUnit() throws ModuleException {
         PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
         if (pur != null) {
            Collection purNames = pur.getPersistenceUnitNames();
            Iterator purni = purNames.iterator();

            while(purni.hasNext()) {
               String pun = (String)purni.next();
               BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);

               try {
                  puii.activate(this.appCtx.getEnvContext());
               } catch (EnvironmentException var7) {
                  throw new ModuleException("Error activating JPA deployment:", var7);
               }
            }

         }
      }

      private void setupPersistenceUnitMBean() throws ModuleException {
         this.runtimePersistenceUnits = new HashMap();
         AbstractPersistenceUnitRegistry unitRegistry = (AbstractPersistenceUnitRegistry)this.getPersistenceUnitRegistry();
         ComponentRuntimeMBean[] mBeans = this.module.getComponentRuntimeMBeans();
         ComponentRuntimeMBean[] var3 = mBeans;
         int var4 = mBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ComponentRuntimeMBean mBean = var3[var5];

            try {
               unitRegistry.setParentRuntimeMBean(this, mBean);
            } catch (EnvironmentException var8) {
               throw new ModuleException(var8);
            }

            WebAppRuntimeMBeanImpl runtimeMBean = (WebAppRuntimeMBeanImpl)mBean;
            runtimeMBean.setKodoPersistenceUnitRuntimeMBeans(this.runtimePersistenceUnits);
         }

      }

      private void initPersistenceUnitRegistry() throws ModuleException {
         PersistenceUnitRegistryInitializer puri = PersistenceUnitRegistryInitializer.getInstance(this.appCtx);
         puri.setupPersistenceUnitRegistries();
      }

      private void setupPersistenceUnitRegistry() throws ModuleException {
         if (this.persistenceUnitRegistry == null) {
            GenericClassLoader cl = this.module.getClassLoader();

            try {
               this.persistenceUnitRegistry = new ModulePersistenceUnitRegistry(cl, this.appCtx, this.module, true);
            } catch (Exception var3) {
               throw new ModuleException(var3);
            }
         }

         if (this.mc != null) {
            ModuleRegistry mr = this.mc.getRegistry();
            mr.put(PersistenceUnitRegistryProvider.class.getName(), this);
            this.module.setPersistenceUnitRegistryProvider(this);
         }

      }

      private void closePersistenceUnitRegistry() {
         if (this.persistenceUnitRegistry != null) {
            if (this.mc != null) {
               ModuleRegistry mr = this.mc.getRegistry();
               mr.remove(PersistenceUnitRegistryProvider.class.getName());
            }

            this.persistenceUnitRegistry.close();
            this.persistenceUnitRegistry = null;
         }

      }

      private void resetPersistenceUnitRegistry() {
         PersistenceUnitRegistry pur = this.getPersistenceUnitRegistry();
         if (pur != null) {
            Collection purNames = pur.getPersistenceUnitNames();
            Iterator purni = purNames.iterator();

            while(purni.hasNext()) {
               String pun = (String)purni.next();
               BasePersistenceUnitInfo puii = (BasePersistenceUnitInfo)pur.getPersistenceUnit(pun);
               puii.deactivate();
            }

         }
      }

      // $FF: synthetic method
      PersistenceExtension(WebAppModule x0, Object x1) {
         this(x0);
      }
   }

   private static class SpringInstrumentationExtension {
      private WebAppModule module;

      private SpringInstrumentationExtension(WebAppModule module) {
         this.module = module;
      }

      private void doInstrumentation() {
         ClassLoader classLoader = this.module.getClassLoader();
         if (classLoader instanceof GenericClassLoader) {
            SpringInstrumentationUtils.addSpringInstrumentor((GenericClassLoader)classLoader);
         }

      }

      // $FF: synthetic method
      SpringInstrumentationExtension(WebAppModule x0, Object x1) {
         this(x0);
      }
   }
}
