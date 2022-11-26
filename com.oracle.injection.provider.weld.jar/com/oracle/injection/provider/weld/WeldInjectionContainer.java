package com.oracle.injection.provider.weld;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import com.oracle.injection.ejb.InterceptorMapping;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.weld.bootstrap.WeldBootstrap;
import org.jboss.weld.bootstrap.api.Environments;
import org.jboss.weld.bootstrap.api.SingletonProvider;
import org.jboss.weld.bootstrap.api.helpers.TCCLSingletonProvider;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.ejb.spi.InterceptorBindings;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.module.ejb.SessionBeanInterceptor;
import org.jboss.weld.module.web.el.WeldELContextListener;
import org.jboss.weld.module.web.servlet.ConversationFilter;
import org.jboss.weld.module.web.servlet.WeldInitialListener;
import org.jboss.weld.module.web.servlet.WeldTerminalListener;

public class WeldInjectionContainer implements InjectionContainer {
   private static Boolean SINGLETON_INITIALIZED = false;
   private WeldBootstrap m_weldBootstrap;
   private boolean m_initialized;
   private List m_injectionArchives;
   private ContainerIntegrationService m_containerIntegrationService;
   private InjectionDeployment m_injectionDeployment;
   private BasicDeployment basicDeployment;

   public WeldInjectionContainer() {
      this(new DefaultSingletonProviderFactory());
   }

   public WeldInjectionContainer(SingletonProviderFactory singletonProviderFactory) {
      this.m_weldBootstrap = null;
      this.m_initialized = false;
      this.m_injectionArchives = new ArrayList();
      this.m_injectionDeployment = null;
      Class var2 = WeldInjectionContainer.class;
      synchronized(WeldInjectionContainer.class) {
         if (!SINGLETON_INITIALIZED) {
            SingletonProvider provider = singletonProviderFactory.createSingletonProvider();
            SingletonProvider.reset();
            SingletonProvider.initialize(provider);
            SINGLETON_INITIALIZED = true;
         }

      }
   }

   public void setIntegrationService(ContainerIntegrationService containerIntegrationService) throws InjectionException {
      if (this.m_containerIntegrationService != null) {
         throw new InjectionException("ContainerIntegrationService already set.");
      } else {
         this.m_containerIntegrationService = containerIntegrationService;
      }
   }

   public ContainerIntegrationService getIntegrationService() {
      return this.m_containerIntegrationService;
   }

   public void addInjectionArchive(InjectionArchive injectionArchive) throws InjectionException {
      if (injectionArchive != null) {
         this.m_injectionArchives.add(injectionArchive);
      }

   }

   public void initialize() throws InjectionException {
      this.m_weldBootstrap = new WeldBootstrap();
      this.basicDeployment = new BasicDeployment(this.m_weldBootstrap, this.m_injectionArchives, this.m_containerIntegrationService);
      this.m_weldBootstrap.startExtensions(this.basicDeployment.getExtensions());
      this.m_weldBootstrap.startContainer(Environments.EE, this.basicDeployment);
      this.m_weldBootstrap.startInitialization();
      this.fireProcessInjectionTargetEvents();
      this.m_injectionDeployment = new WeldInjectionDeployment(this.m_containerIntegrationService, this.m_injectionArchives, this.basicDeployment, this.m_weldBootstrap);
      this.m_initialized = true;
   }

   private void fireProcessInjectionTargetEvents() {
      Collection bdaList = this.basicDeployment.getBeanDeploymentArchives();
      Iterator var2 = bdaList.iterator();

      while(true) {
         BeanDeploymentArchive bda;
         ClassLoader bdaClassLoader;
         Collection bdaClassNames;
         do {
            if (!var2.hasNext()) {
               return;
            }

            bda = (BeanDeploymentArchive)var2.next();
            WlsBeanDeploymentArchive wlsBda = (WlsBeanDeploymentArchive)bda;
            bdaClassLoader = wlsBda.getBdaClassLoader();
            bdaClassNames = ((WlsBeanDeploymentArchive)bda).getComponentClassesForProcessInjectionTarget();
         } while(bdaClassNames == null);

         Iterator var7 = bdaClassNames.iterator();

         while(var7.hasNext()) {
            String oneClassName = (String)var7.next();

            try {
               Class bdaClass = Class.forName(oneClassName, false, bdaClassLoader);
               this.firePITEvent(bda, bdaClass);
            } catch (ClassNotFoundException var10) {
            }
         }
      }
   }

   private void firePITEvent(BeanDeploymentArchive bda, Class bdaClazz) {
      if (!bdaClazz.isInterface()) {
         AnnotatedType at = this.m_weldBootstrap.getManager(bda).createAnnotatedType(bdaClazz);
         this.m_weldBootstrap.getManager(bda).fireProcessInjectionTarget(at);
      }
   }

   public void deploy() throws InjectionException {
      this.checkIfContainerInitialized();
      this.m_weldBootstrap.deployBeans();
   }

   public void start() throws InjectionException {
      this.checkIfContainerInitialized();
      this.m_weldBootstrap.validateBeans();
      this.m_weldBootstrap.endInitialization();
   }

   public void stop() throws InjectionException {
      this.checkIfContainerInitialized();
      this.m_weldBootstrap.shutdown();
   }

   public InjectionDeployment getDeployment() {
      return this.m_injectionDeployment;
   }

   public Collection getServletListenerNames() {
      ArrayList servletListenerNames = new ArrayList();
      servletListenerNames.add(WeldInitialListener.class.getName());
      servletListenerNames.add(WeldTerminalListener.class.getName());
      return servletListenerNames;
   }

   public Collection getServletFilterNames() {
      ArrayList filterNames = new ArrayList();
      filterNames.add(ConversationFilter.class.getName());
      return filterNames;
   }

   public Collection getPhaseListenerNames() {
      return Collections.EMPTY_LIST;
   }

   public Collection getELContextListenerNames() {
      return Collections.singletonList(WeldELContextListener.class.getName());
   }

   public Collection getEjbInterceptorMappings() {
      return Collections.singletonList(new DefaultInterceptorMapping());
   }

   public Collection getInjectionArchives() {
      return this.m_injectionArchives;
   }

   private void checkIfContainerInitialized() throws InjectionException {
      if (this.m_weldBootstrap == null || !this.m_initialized) {
         throw new InjectionException("Weld InjectionContainer not initialized.");
      }
   }

   public InterceptorBindings getInterceptorBindings(String ejbName) {
      WeldEjbServicesAdapter ejbServices = (WeldEjbServicesAdapter)this.basicDeployment.getServices().get(EjbServices.class);
      return ejbServices.getInterceptorBindings(ejbName);
   }

   public Class getBindingInterceptorClass(Class ejbClass) {
      Class interceptorClass = null;
      BeanDeploymentArchive archive = this.basicDeployment.getBeanDeploymentArchive(ejbClass);
      if (archive == null) {
         return null;
      } else {
         String rootId = archive.getId();
         String id = rootId.substring(5);
         WeldBeanManager weldBeanManager = (WeldBeanManager)this.m_injectionDeployment.getBeanManager(id);
         if (weldBeanManager != null) {
            BeanManagerImpl bm = (BeanManagerImpl)((BeanManagerImpl)weldBeanManager.getCDIBeanManager());
            if (bm != null) {
               Iterator var8 = bm.getInterceptors().iterator();

               while(var8.hasNext()) {
                  Interceptor i = (Interceptor)var8.next();
                  if (i.intercepts(InterceptionType.AROUND_CONSTRUCT)) {
                     interceptorClass = i.getBeanClass();
                  }
               }
            }
         }

         return interceptorClass;
      }
   }

   private static class DefaultInterceptorMapping implements InterceptorMapping {
      private final Method m_aroundInvokeMethod;

      DefaultInterceptorMapping() {
         try {
            this.m_aroundInvokeMethod = SessionBeanInterceptor.class.getMethod("aroundInvoke", InvocationContext.class);
         } catch (NoSuchMethodException var2) {
            throw new IllegalStateException("Weld interceptor's aroundInvoke method not found", var2);
         }
      }

      public String getInterceptorClass() {
         return SessionBeanInterceptor.class.getName();
      }

      public List getAroundInvokeMethods() {
         return Collections.singletonList(this.m_aroundInvokeMethod);
      }

      public List getAroundTimeoutMethods() {
         return Collections.singletonList(this.m_aroundInvokeMethod);
      }

      public List getPostConstructMethods() {
         return Collections.singletonList(this.m_aroundInvokeMethod);
      }

      public Method getTargetMethod() {
         return null;
      }
   }

   private static class DefaultSingletonProviderFactory implements SingletonProviderFactory {
      private DefaultSingletonProviderFactory() {
      }

      public SingletonProvider createSingletonProvider() {
         return new TCCLSingletonProvider();
      }

      // $FF: synthetic method
      DefaultSingletonProviderFactory(Object x0) {
         this();
      }
   }

   private static class WeldInjectionDeployment implements InjectionDeployment {
      private final ContainerIntegrationService m_containerIntegrationService;
      private final Collection m_injectionArchives;
      private final BasicDeployment m_basicDeployment;
      private final WeldBootstrap m_weldBootstrap;
      private final Map m_mapOfArchiveClassLoaders = new HashMap();
      private final Map m_mapOfArchiveIDsToBeanManagers = new HashMap();

      WeldInjectionDeployment(ContainerIntegrationService containerIntegrationService, Collection injectionArchives, BasicDeployment basicDeployment, WeldBootstrap weldBootStrap) {
         this.m_containerIntegrationService = containerIntegrationService;
         this.m_injectionArchives = injectionArchives;
         this.m_basicDeployment = basicDeployment;
         this.m_weldBootstrap = weldBootStrap;
         Iterator var5 = this.m_injectionArchives.iterator();

         while(var5.hasNext()) {
            InjectionArchive archive = (InjectionArchive)var5.next();
            this.m_mapOfArchiveClassLoaders.put(archive.getArchiveName(), archive.getClassLoader());
         }

      }

      public Collection getArchives() {
         return this.m_injectionArchives;
      }

      public BeanManager getBeanManager(String archiveID) {
         if (this.m_mapOfArchiveIDsToBeanManagers.containsKey(archiveID)) {
            return (BeanManager)this.m_mapOfArchiveIDsToBeanManagers.get(archiveID);
         } else {
            BeanDeploymentArchive deploymentArchive = this.m_basicDeployment.getBeanDeploymentArchive(archiveID);
            if (deploymentArchive != null) {
               WeldManager weldManager = this.m_weldBootstrap.getManager(deploymentArchive);
               ClassLoader archiveClassLoader = (ClassLoader)this.m_mapOfArchiveClassLoaders.get(archiveID);
               WeldBeanManager weldBeanManager = new WeldBeanManager(this.m_containerIntegrationService, weldManager, archiveClassLoader);
               this.m_mapOfArchiveIDsToBeanManagers.put(archiveID, weldBeanManager);
               return weldBeanManager;
            } else {
               return null;
            }
         }
      }
   }
}
