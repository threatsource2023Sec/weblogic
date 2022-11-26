package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.utils.InjectionBeanCreator;
import com.oracle.injection.spi.WebModuleIntegrationService;
import java.io.IOException;
import java.util.EventListener;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.enterprise.deploy.shared.ModuleType;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtension;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.UpdateListener;
import weblogic.application.naming.ModuleRegistry;
import weblogic.management.DeploymentException;
import weblogic.transaction.TransactionHelper;

public class CDIModuleExtension extends ModuleExtension {
   private static Logger m_logger = Logger.getLogger(CDIModuleExtension.class.getName());
   public static final String META_INF_BEANS_XML = "META-INF/beans.xml";
   public static final String WEB_INF_BEANS_XML = "WEB-INF/beans.xml";
   public static final String SLASH_WEB_INF_BEANS_XML = "/WEB-INF/beans.xml";
   public static final String WEB_INF_CLASSES = "WEB-INF/classes/";
   public static final String SLASH_WEB_INF_CLASSES = "/WEB-INF/classes/";
   public static final String WEB_INF_CLASSES_NO_END_SLASH = "WEB-INF/classes";
   public static final String WEB_INF_LIB = "WEB-INF/lib/";
   public static final String SLASH_WEB_INF_LIB = "/WEB-INF/lib/";
   public static final String WEB_INF_CLASSES_META_INF_BEANS_XML = "WEB-INF/classes/META-INF/beans.xml";
   public static final String SLASH_WEB_INF_CLASSES_META_INF_BEANS_XML = "/WEB-INF/classes/META-INF/beans.xml";
   static final String CDI_CONVERSATION_FILTER_NAME = "CDI Conversation Filter";
   public static final String WEB_INF_CLASSES_GEN_JAR = "WEB-INF/lib/_wl_cls_gen.jar";
   public static final String META_INF_SERVICES_EXTENSION = "META-INF/services/javax.enterprise.inject.spi.Extension";
   public static final String WEB_INF_CLASSES_META_INF_SERVICES_EXTENSION = "WEB-INF/classes/META-INF/services/javax.enterprise.inject.spi.Extension";
   public static final String EJB_CACHE_DIR = "cache/EJBCompilerCache";
   private final InjectionContainerFactory m_injectionContainerFactory;
   private InjectionArchive moduleInjectionArchive = null;

   CDIModuleExtension(ModuleExtensionContext extensionContext, ApplicationContextInternal applicationContextInternal, Module extensibleModule, InjectionContainerFactory injectionContainerFactory) {
      super(extensionContext, applicationContextInternal, extensibleModule);
      this.m_injectionContainerFactory = injectionContainerFactory;
   }

   public void postUnprepare() throws ModuleException {
      ModuleRegistry moduleRegistry = this.modCtx.getRegistry();
      if (moduleRegistry != null) {
         moduleRegistry.remove(InjectionContainer.class.getName());
      }

   }

   public void preActivate() throws ModuleException {
      try {
         this.registerServletObjects(this.getInjectionContainer());
      } catch (Exception var2) {
         throw new ModuleException("Error getting CDI Container", var2);
      }
   }

   public void postPrepare(UpdateListener.Registration reg) throws ModuleException {
      super.postPrepare(reg);
      synchronized(this.appCtx) {
         try {
            InjectionContainer injectionContainer = this.getInjectionContainer();
            if (this.moduleInjectionArchive != null && this.moduleInjectionArchive.getArchiveType() == InjectionArchiveType.WAR) {
               if (this.moduleInjectionArchive.getArchiveType() == InjectionArchiveType.WAR) {
                  try {
                     this.registerServletObjects(injectionContainer);
                  } catch (Exception var6) {
                     throw new ModuleException("Error getting CDI Container", var6);
                  }
               }
            } else {
               this.moduleInjectionArchive = this.createModuleInjectionArchive();
               m_logger.finer("Created module injection archive: " + this.appCtx.getApplicationFileName() + ":" + this.moduleInjectionArchive.getClassPathArchiveName());
               injectionContainer.addInjectionArchive(this.moduleInjectionArchive);
               InjectionDeploymentHelper injectionHelper = this.getInjectionDeploymentHelper();
               this.registerArchiveHolder(this.moduleInjectionArchive, injectionHelper);
               if (!this.isInjectionDeploymentHelperAvailable()) {
                  this.appCtx.putUserObject(InjectionDeploymentHelper.class.getName(), injectionHelper);
               }

               if (!this.isInjectionContainerAvailable()) {
                  this.appCtx.putUserObject(InjectionContainer.class.getName(), injectionContainer);
               }
            }

            this.bindBeanManager(injectionContainer);
         } catch (DeploymentException | NamingException | IOException | InjectionException var7) {
            throw new ModuleException("Error in initializing CDI Container", var7);
         }

      }
   }

   public void postRefreshClassLoader() throws ModuleException {
      this.moduleInjectionArchive = null;
   }

   protected void bindBeanManager(InjectionContainer injectionContainer) throws DeploymentException, NamingException {
      CDIUtils.bindBeanManager(this.getComponentContext(), this.moduleInjectionArchive, injectionContainer.getDeployment());
   }

   protected void registerServletObjects(InjectionContainer injectionContainer) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      if (this.moduleInjectionArchive.getArchiveType() == InjectionArchiveType.WAR) {
         InjectionBeanCreator injectionBeanCreator = new InjectionBeanCreator(injectionContainer, this.modCtx.getId());
         this.registerServletListeners(injectionContainer, injectionBeanCreator);
         this.registerServletFilters(injectionContainer, injectionBeanCreator);
      }

   }

   private Context getComponentContext() {
      Context context = null;
      if (!this.extensibleModule.getType().equals(ModuleType.RAR.toString()) && this.extensionCtx.getEnvironment(this.extensibleModule.getId()) != null) {
         context = this.extensionCtx.getEnvironment(this.extensibleModule.getId()).getCompContext();
      }

      return context;
   }

   private void registerArchiveHolder(InjectionArchive moduleInjectionArchive, InjectionDeploymentHelper injectionHelper) {
      if (!this.extensibleModule.getType().equals(ModuleType.RAR.toString())) {
         if (this.extensionCtx.getEnvironment(this.extensibleModule.getId()) != null) {
            injectionHelper.addArchiveHolder(moduleInjectionArchive, this.extensionCtx.getEnvironment(this.extensibleModule.getId()).getCompContext(), this.modCtx, this.extensionCtx);
         } else if (this.extensibleModule.getType().equals(ModuleType.EJB.toString())) {
            injectionHelper.addArchiveHolder(moduleInjectionArchive, (Context)null, this.modCtx, this.extensionCtx);
         }
      } else {
         injectionHelper.addArchiveHolder(moduleInjectionArchive, (Context)null, this.modCtx, this.extensionCtx);
      }

   }

   private InjectionArchive createModuleInjectionArchive() throws IOException, InjectionException {
      String moduleType = this.modCtx.getType();
      Object injectionArchive;
      if (ModuleType.EJB.toString().equals(moduleType)) {
         injectionArchive = new EjbModuleInjectionArchive(this.modCtx, this.extensionCtx);
      } else if (ModuleType.WAR.toString().equals(moduleType)) {
         injectionArchive = this.createWebModuleInjectionArchive();
      } else {
         if (!ModuleType.RAR.toString().equals(moduleType)) {
            throw new IllegalArgumentException("Unsupported module type: " + moduleType);
         }

         injectionArchive = new RarModuleInjectionArchive(this.modCtx, this.extensionCtx);
      }

      return (InjectionArchive)injectionArchive;
   }

   private InjectionArchive createWebModuleInjectionArchive() throws IOException, InjectionException {
      WebModuleInjectionArchive webInjectionArchive = new WebModuleInjectionArchive(this.modCtx, this.extensionCtx, this.extensibleModule, this.appCtx);
      if (this.extensionCtx instanceof WebModuleIntegrationService) {
         WebModuleIntegrationService webModuleIntegration = (WebModuleIntegrationService)this.extensionCtx;
         WeblogicContainerIntegrationService integrationService = (WeblogicContainerIntegrationService)this.appCtx.getUserObject(WeblogicContainerIntegrationService.class.getName());
         integrationService.addInjectionEnabledWebApp(webModuleIntegration.getServletContext());
      }

      return webInjectionArchive;
   }

   private InjectionDeploymentHelper getInjectionDeploymentHelper() {
      InjectionDeploymentHelper deploymentHelper;
      if (this.isInjectionDeploymentHelperAvailable()) {
         deploymentHelper = (InjectionDeploymentHelper)this.appCtx.getUserObject(InjectionDeploymentHelper.class.getName());
      } else {
         deploymentHelper = new InjectionDeploymentHelper();
      }

      return deploymentHelper;
   }

   private InjectionContainer getInjectionContainer() throws InjectionException {
      InjectionContainer injectionContainer;
      if (this.isInjectionContainerAvailable()) {
         injectionContainer = (InjectionContainer)this.appCtx.getUserObject(InjectionContainer.class.getName());
      } else {
         injectionContainer = this.m_injectionContainerFactory.createInjectionContainer();
         ModuleContainerIntegrationService containerIntegrationService = new ModuleContainerIntegrationService(TransactionHelper.getTransactionHelper());
         this.appCtx.putUserObject(WeblogicContainerIntegrationService.class.getName(), containerIntegrationService);
         injectionContainer.setIntegrationService(containerIntegrationService);
      }

      return injectionContainer;
   }

   private void registerServletListeners(InjectionContainer injectionContainer, InjectionBeanCreator injectionBeanCreator) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      if (this.extensionCtx instanceof WebModuleIntegrationService) {
         WebModuleIntegrationService webIntegrationService = (WebModuleIntegrationService)this.extensionCtx;
         Iterator var4 = injectionContainer.getServletListenerNames().iterator();

         while(var4.hasNext()) {
            String servletListenerName = (String)var4.next();
            EventListener eventListener = (EventListener)injectionBeanCreator.createInstance(servletListenerName);
            webIntegrationService.registerServletListener(eventListener);
         }
      }

   }

   private void registerServletFilters(InjectionContainer injectionContainer, InjectionBeanCreator injectionBeanCreator) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      if (this.extensionCtx instanceof WebModuleIntegrationService) {
         WebModuleIntegrationService webIntegrationService = (WebModuleIntegrationService)this.extensionCtx;
         if (this.isCdiConverstaionFilterMappingEnabled(webIntegrationService)) {
            ServletContext servletContext = webIntegrationService.getServletContext();
            Iterator var5 = injectionContainer.getServletFilterNames().iterator();

            while(var5.hasNext()) {
               String servletListenerName = (String)var5.next();
               Filter servletFilter = (Filter)injectionBeanCreator.createInstance(servletListenerName);
               servletContext.addFilter("CDI Conversation Filter", servletFilter);
            }
         }
      }

   }

   private boolean isCdiConverstaionFilterMappingEnabled(WebModuleIntegrationService webIntegrationService) {
      String[] filterMappingNames = webIntegrationService.getFilterMappingNames();
      if (filterMappingNames != null && filterMappingNames.length > 0) {
         String[] var3 = filterMappingNames;
         int var4 = filterMappingNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String oneFilterMappingName = var3[var5];
            if (oneFilterMappingName.equals("CDI Conversation Filter")) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isInjectionContainerAvailable() {
      return this.appCtx.getUserObject(InjectionContainer.class.getName()) != null;
   }

   private boolean isInjectionDeploymentHelperAvailable() {
      return this.appCtx.getUserObject(InjectionDeploymentHelper.class.getName()) != null;
   }
}
