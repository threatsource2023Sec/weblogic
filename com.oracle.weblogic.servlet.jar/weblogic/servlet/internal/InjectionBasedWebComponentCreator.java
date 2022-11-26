package weblogic.servlet.internal;

import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.utils.InjectionBeanCreator;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.management.DeploymentException;

class InjectionBasedWebComponentCreator extends WebComponentContributor {
   private static final DebugLogger m_diLogger = DebugLogger.getDebugLogger("DebugWebAppDI");
   private final InjectionContainer m_injectionContainer;
   private final WebAppModule m_webAppModule;
   private InjectionBeanCreator injectionBeanCreator;
   private ModuleContext moduleContext;
   private ModuleExtensionContext moduleExtensionContext;
   private Set pojoClasses;
   private Set injectClasses = new HashSet();

   public InjectionBasedWebComponentCreator(InjectionContainer injectionContainer, WebAppModule webAppModule, PitchforkContext pitchforkContext, ModuleContext moduleContext) throws DeploymentException {
      super(pitchforkContext);
      if (injectionContainer == null) {
         throw new DeploymentException("InjectionContainer instance should not be null");
      } else if (webAppModule == null) {
         throw new DeploymentException("WebAppModule instance should not be null");
      } else {
         this.m_injectionContainer = injectionContainer;
         this.m_webAppModule = webAppModule;
         this.moduleExtensionContext = this.m_webAppModule.getModuleExtensionContext();
         this.setInjectionBeanCreator(new InjectionBeanCreator(injectionContainer, this.m_webAppModule.getId()));
         this.moduleContext = moduleContext;
      }
   }

   private void initInjectClasses() {
      if (this.m_webAppModule != null) {
         War war = this.m_webAppModule.getWarInstance();
         if (war != null) {
            ClassInfoFinder classInfo = war.getClassInfoFinder();
            if (classInfo == null) {
               return;
            }

            String[] injectAnnotation = new String[]{"javax.inject.Inject"};
            Map annotated = classInfo.getAnnotatedClassesByTargetsAndSources(injectAnnotation, (ClassInfoFinder.Filter)null, false, (ClassLoader)null);
            if (annotated == null || annotated.isEmpty()) {
               return;
            }

            Iterator var5 = annotated.values().iterator();

            while(var5.hasNext()) {
               Map mapElement = (Map)var5.next();
               Iterator var7 = mapElement.values().iterator();

               while(var7.hasNext()) {
                  Set setElement = (Set)var7.next();
                  this.injectClasses.addAll(setElement);
               }
            }
         }
      }

   }

   public void initialize(WebAppServletContext ctx) throws DeploymentException {
      super.initialize(ctx);
      this.initInjectClasses();
   }

   public boolean needDependencyInjection(Object obj) {
      if (obj == null) {
         return false;
      } else {
         for(Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (this.injectClasses.contains(clazz.getName())) {
               return true;
            }
         }

         return false;
      }
   }

   public void contribute(EnricherI enricher) {
      super.contribute(enricher);
      this.pojoClasses = this.moduleContext.getRegistry().getAnnotatedPojoClasses();
      if (this.pojoClasses != null) {
         PojoEnvironmentBean pojoEnvironmentBean = this.moduleExtensionContext.getPojoEnvironmentBean();
         if (pojoEnvironmentBean != null) {
            Iterator var3 = this.pojoClasses.iterator();

            while(var3.hasNext()) {
               Class oneClass = (Class)var3.next();
               String className = oneClass.getName();
               this.contribute(enricher, className, className, pojoEnvironmentBean);
            }
         }
      }

      this.pojoClasses = null;
      this.moduleContext.getRegistry().clearAnnotatedPojoClasses();
   }

   protected void contribute(Jsr250MetadataI jsr250Metadata, J2eeEnvironmentBean environmentGroupBean) {
      super.contribute(jsr250Metadata, environmentGroupBean);
      this.injectClasses.add(jsr250Metadata.getComponentName());
      this.m_injectionContainer.getIntegrationService().addInjectionMetaData(jsr250Metadata.getComponentClass(), jsr250Metadata);
   }

   public Servlet createServletInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      return (Servlet)this.injectionBeanCreator.newBeanInstance(className, true);
   }

   public Filter createFilterInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      return (Filter)this.injectionBeanCreator.newBeanInstance(className, true);
   }

   public EventListener createListenerInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      return (EventListener)this.injectionBeanCreator.newBeanInstance(className, true);
   }

   public Object createInstance(Class clazz) throws IllegalAccessException, ClassNotFoundException, InstantiationException, ClassCastException {
      return this.injectionBeanCreator.createInstance(clazz);
   }

   public void inject(Object obj) {
      try {
         this.injectionBeanCreator.injectOnExternalInstance(obj);
      } catch (InjectionException var3) {
         m_diLogger.debug("Exception occurred while trying to inject on an external instance", var3);
      }

   }

   public void notifyPreDestroy(Object obj) {
      try {
         this.injectionBeanCreator.invokePreDestroy(obj);
         this.injectionBeanCreator.destroyBean(obj);
      } catch (InjectionException var3) {
         m_diLogger.debug("Exception occurred while trying to invoke @PreDestroy on a bean", var3);
      }

   }

   public void notifyPostConstruct(Object obj) {
      try {
         this.injectionBeanCreator.invokePostConstruct(obj);
      } catch (InjectionException var3) {
         m_diLogger.debug("Exception occurred while trying to invoke @PostConstruct on a bean", var3);
      }

   }

   public InjectionBeanCreator getInjectionBeanCreator() {
      return this.injectionBeanCreator;
   }

   void setInjectionBeanCreator(InjectionBeanCreator injectionBeanCreator) {
      this.injectionBeanCreator = injectionBeanCreator;
   }

   protected boolean createInjectionWhenNoLookupValueFound(EnvEntryBean envEntry, Jsr250MetadataI jsr250Metadata) {
      if (this.pojoClasses != null && this.pojoClasses.contains(jsr250Metadata.getComponentClass())) {
         if (m_diLogger.isDebugEnabled()) {
            String msg = "[InjectionBasedWebComponentCreator] Environment entry: " + envEntry.getEnvEntryName() + " on the POJO class " + jsr250Metadata.getComponentClass().getName() + " does not have a Value or LookupName specified. Processing the injection metadata for the POJO, with the expectation that there is another definition of the environment entry with the pertinent information, contributing to the same component environment";
            m_diLogger.debug(msg);
         }

         return true;
      } else {
         return false;
      }
   }
}
