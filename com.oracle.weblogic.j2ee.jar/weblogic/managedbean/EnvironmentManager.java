package weblogic.managedbean;

import com.oracle.injection.InjectionContainer;
import com.oracle.pitchfork.spi.ManagedBeanProxyMetadata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.naming.BindingsFactory;
import weblogic.application.naming.Environment;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.wl.ManagedBeanBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.jndi.SimpleContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.classloaders.GenericClassLoader;

public class EnvironmentManager {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugManagedBean");
   private final ModuleContext modCtx;
   private final ManagedBeansBean managedBeans;
   private final String appId;
   private final String moduleName;
   private final Context appNamingRootCtx;
   private final Context moduleNamingRootCtx;
   private final GenericClassLoader moduleClassLoader;
   private Map nameToEnvironment;
   private ManagedBeanCreator creator;
   private List copiedResources = new ArrayList();
   private String moduleType;

   public EnvironmentManager(ApplicationContextInternal appCtx, String moduleName, ModuleContext modCtx, ManagedBeansBean bean, String moduleType) {
      this.modCtx = modCtx;
      this.appId = appCtx.getApplicationId();
      this.moduleName = moduleName;
      this.appNamingRootCtx = appCtx.getRootContext();
      this.moduleNamingRootCtx = (Context)modCtx.getRegistry().get(Context.class.getName());
      this.managedBeans = bean;
      this.moduleClassLoader = modCtx.getClassLoader();
      this.moduleType = moduleType;
   }

   public EnvironmentManager(ManagedBeansBean bean, String appId, String moduleName, Context appNamingRootCtx, Context moduleNamingRootCtx, GenericClassLoader gcl) {
      this.moduleClassLoader = gcl;
      this.appId = appId;
      this.managedBeans = bean;
      this.moduleName = moduleName;
      this.appNamingRootCtx = appNamingRootCtx;
      this.moduleNamingRootCtx = moduleNamingRootCtx;
      this.modCtx = null;
   }

   public void activate() throws ModuleException {
      if (this.managedBeans != null && !emptyArray(this.managedBeans.getManagedBeans())) {
         Thread thread = Thread.currentThread();
         ClassLoader oldLoader = thread.getContextClassLoader();
         thread.setContextClassLoader(this.moduleClassLoader);
         InjectionContainer injectionContainer = this.getInjectionContainer();
         Object contributor;
         if (injectionContainer != null) {
            contributor = new InjectionBasedManagedBeanContributor(injectionContainer, this.managedBeans, this.moduleClassLoader, new PitchforkContext((String)null));
         } else {
            contributor = new ManagedBeanContributor(this.managedBeans, this.moduleClassLoader, new PitchforkContext((String)null));
         }

         ((ManagedBeanContributor)contributor).initialize();
         this.initManagedBeanCreator((ManagedBeanContributor)contributor);
         PersistenceUnitRegistryProvider provider = null;
         if (this.modCtx != null) {
            ModuleRegistry mr = this.modCtx.getRegistry();
            provider = (PersistenceUnitRegistryProvider)mr.get(PersistenceUnitRegistryProvider.class.getName());
         }

         try {
            ManagedBeanBean[] var19 = this.managedBeans.getManagedBeans();
            int var7 = var19.length;

            int var8;
            Environment eBuilder;
            for(var8 = 0; var8 < var7; ++var8) {
               ManagedBeanBean bean = var19[var8];
               eBuilder = this.createEnvironment(bean.getManagedBeanName());
               this.mapBeanNameToEnvironment(bean.getManagedBeanName(), eBuilder);
               eBuilder.contributeEnvEntries(bean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
               eBuilder.validateEnvEntries(this.moduleClassLoader);
               eBuilder.bindEnvEntriesFromDDs(this.moduleClassLoader, provider);
               this.bindManagedBean(bean, this.creator);
               if (WebLogicModuleType.MODULETYPE_WAR.equalsIgnoreCase(this.moduleType)) {
                  try {
                     this.copyResources(eBuilder.getCompEnvContext(), (Context)this.getJavaModuleCtx().lookup("env"));
                  } catch (Exception var16) {
                  }
               }

               ManagedBeanProxyMetadata metadata = (ManagedBeanProxyMetadata)((ManagedBeanContributor)contributor).getMetadata(bean.getManagedBeanClass());
               if (metadata != null) {
                  metadata.setNamingContext(eBuilder.getRootContext());
               }
            }

            if (this.managedBeans.getInterceptors() != null && this.managedBeans.getInterceptors().getInterceptors() != null) {
               InterceptorBean[] var20 = this.managedBeans.getInterceptors().getInterceptors();
               var7 = var20.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  InterceptorBean bean = var20[var8];
                  eBuilder = this.createEnvironment(bean.getInterceptorClass());
                  eBuilder.contributeEnvEntries(bean, (WeblogicEnvironmentBean)null, (AuthenticatedSubject)null);
                  eBuilder.validateEnvEntries(this.moduleClassLoader);
                  eBuilder.bindEnvEntriesFromDDs(this.moduleClassLoader, provider);
               }

               return;
            }
         } catch (Exception var17) {
            this.cleanup();
            throw new ModuleException("Could not setup environment", var17);
         } finally {
            thread.setContextClassLoader(oldLoader);
         }

      }
   }

   public void destroy() {
      if (this.creator != null) {
         this.creator.destroy();
      }

      this.cleanup();
      Iterator var1 = this.nameToEnvironment.values().iterator();

      while(var1.hasNext()) {
         Environment env = (Environment)var1.next();
         env.destroy();
      }

      this.nameToEnvironment.clear();
      this.copiedResources.clear();
   }

   private void copyResources(Context src, Context des) {
      if (src != null && des != null) {
         try {
            NamingEnumeration list = src.listBindings("");

            while(list.hasMore()) {
               Binding nc = (Binding)list.next();

               try {
                  des.bind(nc.getName(), nc.getObject());
                  this.copiedResources.add(nc.getName());
               } catch (Exception var6) {
               }
            }
         } catch (Exception var7) {
         }

      }
   }

   private void initManagedBeanCreator(ManagedBeanContributor contributor) {
      InjectionContainer injectionContainer = this.getInjectionContainer();
      if (injectionContainer != null) {
         this.creator = new InjectionBasedManagedBeanCreator(this.modCtx, injectionContainer);
      } else {
         this.creator = new ManagedBeanCreatorImpl(contributor, this.moduleClassLoader);
      }

      if (this.modCtx != null) {
         ModuleRegistry registry = this.modCtx.getRegistry();
         if (registry == null) {
            return;
         }

         registry.put(ManagedBeanCreator.class.getName(), this.creator);
      }

   }

   private InjectionContainer getInjectionContainer() {
      return this.modCtx != null && this.modCtx.getRegistry() != null ? (InjectionContainer)this.modCtx.getRegistry().get(InjectionContainer.class.getName()) : null;
   }

   private boolean isAppClient() {
      return this.modCtx == null;
   }

   private Context getJavaModuleCtx() throws NamingException {
      return (Context)((Context)(this.isAppClient() ? this.moduleNamingRootCtx : this.moduleNamingRootCtx.lookup("module")));
   }

   private Context getJavaAppCtx() throws NamingException {
      return (Context)((Context)(this.isAppClient() ? this.appNamingRootCtx : this.appNamingRootCtx.lookup("app")));
   }

   private void bindManagedBean(final ManagedBeanBean bean, final ManagedBeanCreator creator) throws NamingException {
      Context appCtx = this.getJavaAppCtx();
      Context moduleCtx = this.getJavaModuleCtx();
      if (this.isAppClient()) {
         SimpleContext.SimpleReference reference = new SimpleContext.SimpleReference() {
            public Object get() throws NamingException {
               try {
                  Object instance = creator.createInstance(bean.getManagedBeanClass());
                  creator.notifyPostConstruct(bean.getManagedBeanClass(), instance);
                  return instance;
               } catch (Exception var2) {
                  throw new NamingException("Cannot create instance of managed bean " + bean.getManagedBeanClass() + "for app client");
               }
            }
         };
         moduleCtx.bind(bean.getManagedBeanName(), reference);
         appCtx.bind(normalizeModuleName(this.moduleName) + "/" + bean.getManagedBeanName(), reference);
      } else {
         ManagedBeanReference reference = new ManagedBeanReference(bean.getManagedBeanClass(), creator, this.moduleClassLoader);
         moduleCtx.bind(bean.getManagedBeanName(), reference);
         appCtx.bind(normalizeModuleName(this.moduleName) + "/" + bean.getManagedBeanName(), reference);
      }

   }

   private static String normalizeModuleName(String modName) {
      int dotIndex = modName.lastIndexOf(46);
      if (dotIndex <= 0) {
         return modName;
      } else {
         String extName = modName.substring(dotIndex);
         return extName == null || !extName.equalsIgnoreCase(".jar") && !extName.equalsIgnoreCase(".war") ? modName : modName.substring(0, dotIndex);
      }
   }

   private void unbindManagedBeans() {
      Context appCtx = null;
      Context moduleCtx = null;

      try {
         appCtx = this.getJavaAppCtx();
         moduleCtx = this.getJavaModuleCtx();
      } catch (Exception var7) {
         var7.printStackTrace();
         return;
      }

      Iterator var3 = this.nameToEnvironment.keySet().iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();

         try {
            moduleCtx.unbind(name);
            appCtx.unbind(normalizeModuleName(this.moduleName) + "/" + name);
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

   }

   private void removeCopiedResources(Context ctx) {
      if (ctx != null) {
         Iterator var2 = this.copiedResources.iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();

            try {
               ctx.unbind(key);
            } catch (Exception var5) {
            }
         }

      }
   }

   private Environment createEnvironment(String managedBeanName) throws NamingException {
      return BindingsFactory.getInstance().createManagedBeanEnvironment(new SimpleContext(), this.appId, this.moduleName, this.isAppClient() ? this.moduleName : this.modCtx.getId(), managedBeanName, logger, this.getJavaAppCtx(), this.getJavaModuleCtx(), this.isAppClient());
   }

   private void mapBeanNameToEnvironment(String managedBeanName, Environment eBuilder) {
      if (this.nameToEnvironment == null) {
         this.nameToEnvironment = new HashMap();
      }

      this.nameToEnvironment.put(managedBeanName, eBuilder);
   }

   private void cleanup() {
      if (this.nameToEnvironment != null) {
         Iterator var1 = this.nameToEnvironment.values().iterator();

         while(var1.hasNext()) {
            Environment env = (Environment)var1.next();
            env.unbindEnvEntries();
         }

         this.unbindManagedBeans();
         if (WebLogicModuleType.MODULETYPE_WAR.equalsIgnoreCase(this.moduleType)) {
            try {
               this.removeCopiedResources((Context)this.getJavaModuleCtx().lookup("env"));
            } catch (Exception var3) {
            }
         }

      }
   }

   private static boolean emptyArray(Object[] objs) {
      return objs == null || objs.length == 0;
   }
}
