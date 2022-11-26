package com.sun.faces.config;

import com.sun.faces.config.configpopulator.JsfRIRuntimePopulator;
import com.sun.faces.config.configprovider.MetaInfFaceletTaglibraryConfigProvider;
import com.sun.faces.config.configprovider.MetaInfFacesConfigResourceProvider;
import com.sun.faces.config.configprovider.WebAppFlowConfigResourceProvider;
import com.sun.faces.config.configprovider.WebFaceletTaglibResourceProvider;
import com.sun.faces.config.configprovider.WebFacesConfigResourceProvider;
import com.sun.faces.config.manager.DbfFactory;
import com.sun.faces.config.manager.Documents;
import com.sun.faces.config.manager.FacesConfigInfo;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.config.manager.tasks.FindAnnotatedConfigClasses;
import com.sun.faces.config.manager.tasks.ProvideMetadataToAnnotationScanTask;
import com.sun.faces.config.processor.ApplicationConfigProcessor;
import com.sun.faces.config.processor.BehaviorConfigProcessor;
import com.sun.faces.config.processor.ComponentConfigProcessor;
import com.sun.faces.config.processor.ConfigProcessor;
import com.sun.faces.config.processor.ConverterConfigProcessor;
import com.sun.faces.config.processor.FaceletTaglibConfigProcessor;
import com.sun.faces.config.processor.FacesConfigExtensionProcessor;
import com.sun.faces.config.processor.FacesFlowDefinitionConfigProcessor;
import com.sun.faces.config.processor.FactoryConfigProcessor;
import com.sun.faces.config.processor.LifecycleConfigProcessor;
import com.sun.faces.config.processor.ManagedBeanConfigProcessor;
import com.sun.faces.config.processor.NavigationConfigProcessor;
import com.sun.faces.config.processor.ProtectedViewsConfigProcessor;
import com.sun.faces.config.processor.RenderKitConfigProcessor;
import com.sun.faces.config.processor.ResourceLibraryContractsConfigProcessor;
import com.sun.faces.config.processor.ValidatorConfigProcessor;
import com.sun.faces.el.ELContextImpl;
import com.sun.faces.el.ELUtils;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.spi.ConfigurationResourceProviderFactory;
import com.sun.faces.spi.HighAvailabilityEnabler;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderFactory;
import com.sun.faces.spi.ThreadContext;
import com.sun.faces.util.FacesLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.el.ExpressionFactory;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationConfigurationPopulator;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PostConstructApplicationEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

public class ConfigManager {
   private static final Logger LOGGER;
   public static final String INJECTION_PROVIDER_KEY;
   private static final int NUMBER_OF_TASK_THREADS = 5;
   private static final String CONFIG_MANAGER_INSTANCE_KEY = "com.sun.faces.CONFIG_MANAGER_KEY";
   private static final String ANNOTATIONS_SCAN_TASK_KEY;
   private List initializedContexts = new CopyOnWriteArrayList();
   private final List configProcessors = Collections.unmodifiableList(Arrays.asList(new FactoryConfigProcessor(), new LifecycleConfigProcessor(), new ApplicationConfigProcessor(), new ComponentConfigProcessor(), new ConverterConfigProcessor(), new ValidatorConfigProcessor(), new ManagedBeanConfigProcessor(), new RenderKitConfigProcessor(), new NavigationConfigProcessor(), new BehaviorConfigProcessor(), new FacesConfigExtensionProcessor(), new ProtectedViewsConfigProcessor(), new FacesFlowDefinitionConfigProcessor(), new ResourceLibraryContractsConfigProcessor()));
   private final List facesConfigProviders = Collections.unmodifiableList(Arrays.asList(new MetaInfFacesConfigResourceProvider(), new WebAppFlowConfigResourceProvider(), new WebFacesConfigResourceProvider()));
   private final List facesletsTagLibConfigProviders = Collections.unmodifiableList(Arrays.asList(new MetaInfFaceletTaglibraryConfigProvider(), new WebFaceletTaglibResourceProvider()));
   private final ConfigProcessor faceletTaglibConfigProcessor = new FaceletTaglibConfigProcessor();

   public static ConfigManager createInstance(ServletContext servletContext) {
      ConfigManager result = new ConfigManager();
      servletContext.setAttribute("com.sun.faces.CONFIG_MANAGER_KEY", result);
      return result;
   }

   public static ConfigManager getInstance(ServletContext servletContext) {
      return (ConfigManager)servletContext.getAttribute("com.sun.faces.CONFIG_MANAGER_KEY");
   }

   public static Map getAnnotatedClasses(FacesContext ctx) {
      Map appMap = ctx.getExternalContext().getApplicationMap();
      Future scanTask = (Future)appMap.get(ANNOTATIONS_SCAN_TASK_KEY);

      try {
         return scanTask != null ? (Map)scanTask.get() : Collections.emptyMap();
      } catch (ExecutionException | InterruptedException var4) {
         throw new FacesException(var4);
      }
   }

   public static void removeInstance(ServletContext servletContext) {
      servletContext.removeAttribute("com.sun.faces.CONFIG_MANAGER_KEY");
   }

   public void initialize(ServletContext servletContext, InitFacesContext facesContext) {
      if (!this.hasBeenInitialized(servletContext)) {
         this.initializedContexts.add(servletContext);
         this.initializeConfigProcessers(servletContext, facesContext);
         ExecutorService executor = null;

         try {
            WebConfiguration webConfig = WebConfiguration.getInstance(servletContext);
            boolean validating = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ValidateFacesConfigFiles);
            if (this.useThreads(servletContext)) {
               executor = createExecutorService();
            }

            DocumentInfo[] facesDocuments = Documents.mergeDocuments(Documents.getXMLDocuments(servletContext, this.getFacesConfigResourceProviders(), executor, validating), Documents.getProgrammaticDocuments(this.getConfigPopulators()));
            FacesConfigInfo lastFacesConfigInfo = new FacesConfigInfo(facesDocuments[facesDocuments.length - 1]);
            facesDocuments = Documents.sortDocuments(facesDocuments, lastFacesConfigInfo);
            InjectionProvider containerConnector = InjectionProviderFactory.createInstance(facesContext.getExternalContext());
            facesContext.getAttributes().put(INJECTION_PROVIDER_KEY, containerConnector);
            boolean isFaceletsDisabled = this.isFaceletsDisabled(webConfig, lastFacesConfigInfo);
            if (!lastFacesConfigInfo.isWebInfFacesConfig() || !lastFacesConfigInfo.isMetadataComplete()) {
               this.findAnnotations(facesDocuments, containerConnector, servletContext, facesContext, executor);
            }

            if (containerConnector instanceof HighAvailabilityEnabler) {
               ((HighAvailabilityEnabler)containerConnector).enableHighAvailability(servletContext);
            }

            this.configProcessors.subList(0, 3).stream().forEach((e) -> {
               try {
                  e.process(servletContext, facesContext, facesDocuments);
               } catch (Exception var5) {
                  var5.printStackTrace();
               }

            });
            long parentThreadId = Thread.currentThread().getId();
            ClassLoader parentContextClassLoader = Thread.currentThread().getContextClassLoader();
            ThreadContext threadContext = this.getThreadContext(containerConnector);
            Object parentWebContext = threadContext != null ? threadContext.getParentWebContext() : null;
            this.configProcessors.subList(3, this.configProcessors.size()).stream().forEach((e) -> {
               long currentThreadId = Thread.currentThread().getId();
               InitFacesContext initFacesContext = null;
               if (currentThreadId != parentThreadId) {
                  Thread.currentThread().setContextClassLoader(parentContextClassLoader);
                  initFacesContext = InitFacesContext.getInstance(servletContext);
                  if (parentWebContext != null) {
                     threadContext.propagateWebContextToChild(parentWebContext);
                  }
               } else {
                  initFacesContext = facesContext;
               }

               try {
                  e.process(servletContext, initFacesContext, facesDocuments);
               } catch (Exception var16) {
                  var16.printStackTrace();
               } finally {
                  if (currentThreadId != parentThreadId) {
                     Thread.currentThread().setContextClassLoader((ClassLoader)null);
                     if (parentWebContext != null) {
                        threadContext.clearChildContext();
                     }
                  }

               }

            });
            if (!isFaceletsDisabled) {
               this.faceletTaglibConfigProcessor.process(servletContext, facesContext, Documents.getXMLDocuments(servletContext, this.getFaceletConfigResourceProviders(), executor, validating));
            }
         } catch (Exception var19) {
            this.releaseFactories();
            Throwable t = var19;
            if (!(var19 instanceof ConfigurationException)) {
               t = new ConfigurationException("CONFIGURATION FAILED! " + var19.getMessage(), var19);
            }

            throw (ConfigurationException)t;
         } finally {
            if (executor != null) {
               executor.shutdown();
            }

            servletContext.removeAttribute(ANNOTATIONS_SCAN_TASK_KEY);
         }
      }

      DbfFactory.removeSchemaMap(servletContext);
   }

   public boolean hasBeenInitialized(ServletContext servletContext) {
      return this.initializedContexts.contains(servletContext);
   }

   private void findAnnotations(DocumentInfo[] facesDocuments, InjectionProvider containerConnector, ServletContext servletContext, InitFacesContext context, ExecutorService executor) {
      ProvideMetadataToAnnotationScanTask taskMetadata = new ProvideMetadataToAnnotationScanTask(facesDocuments, containerConnector);
      Object annotationScan;
      if (executor != null) {
         annotationScan = executor.submit(new FindAnnotatedConfigClasses(servletContext, context, taskMetadata));
      } else {
         annotationScan = new FutureTask(new FindAnnotatedConfigClasses(servletContext, context, taskMetadata));
         ((FutureTask)annotationScan).run();
      }

      this.pushTaskToContext(servletContext, (Future)annotationScan);
   }

   private void pushTaskToContext(ServletContext sc, Future scanTask) {
      sc.setAttribute(ANNOTATIONS_SCAN_TASK_KEY, scanTask);
   }

   private boolean useThreads(ServletContext ctx) {
      return WebConfiguration.getInstance(ctx).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableThreading);
   }

   private List getFacesConfigResourceProviders() {
      return this.getConfigurationResourceProviders(this.facesConfigProviders, ConfigurationResourceProviderFactory.ProviderType.FacesConfig);
   }

   private List getFaceletConfigResourceProviders() {
      return this.getConfigurationResourceProviders(this.facesletsTagLibConfigProviders, ConfigurationResourceProviderFactory.ProviderType.FaceletConfig);
   }

   private List getConfigurationResourceProviders(List defaultProviders, ConfigurationResourceProviderFactory.ProviderType providerType) {
      ConfigurationResourceProvider[] customProviders = ConfigurationResourceProviderFactory.createProviders(providerType);
      if (customProviders.length == 0) {
         return defaultProviders;
      } else {
         List providers = new ArrayList(defaultProviders);
         providers.addAll(defaultProviders.size() - 1, Arrays.asList(customProviders));
         return Collections.unmodifiableList(providers);
      }
   }

   private void initializeConfigProcessers(ServletContext servletContext, FacesContext facesContext) {
      ((Stream)this.configProcessors.stream().parallel()).forEach((e) -> {
         e.initializeClassMetadataMap(servletContext, facesContext);
      });
   }

   private List getConfigPopulators() {
      List configPopulators = new ArrayList();
      configPopulators.add(new JsfRIRuntimePopulator());
      ServiceLoader.load(ApplicationConfigurationPopulator.class).forEach((e) -> {
         configPopulators.add(e);
      });
      return configPopulators;
   }

   private boolean isFaceletsDisabled(WebConfiguration webConfig, FacesConfigInfo lastFacesConfigInfo) {
      if (lastFacesConfigInfo.isWebInfFacesConfig()) {
         return this._isFaceletsDisabled(webConfig, lastFacesConfigInfo);
      } else {
         return webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) || webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated);
      }
   }

   private boolean _isFaceletsDisabled(WebConfiguration webconfig, FacesConfigInfo facesConfigInfo) {
      boolean isFaceletsDisabled = webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) || webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated);
      if (!isFaceletsDisabled) {
         isFaceletsDisabled = !facesConfigInfo.isVersionGreaterOrEqual(2.0);
         webconfig.overrideContextInitParameter(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler, isFaceletsDisabled);
      }

      return isFaceletsDisabled;
   }

   void publishPostConfigEvent() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      Application app = ctx.getApplication();
      if (null == ((InitFacesContext)ctx).getELContext()) {
         ELContext elContext = new ELContextImpl(app.getELResolver());
         elContext.putContext(FacesContext.class, ctx);
         ExpressionFactory exFactory = ELUtils.getDefaultExpressionFactory(ctx);
         if (null != exFactory) {
            elContext.putContext(ExpressionFactory.class, exFactory);
         }

         UIViewRoot root = ctx.getViewRoot();
         if (null != root) {
            elContext.setLocale(root.getLocale());
         }

         ELContextListener[] listeners = app.getELContextListeners();
         if (listeners.length > 0) {
            ELContextEvent event = new ELContextEvent(elContext);
            ELContextListener[] var8 = listeners;
            int var9 = listeners.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               ELContextListener listener = var8[var10];
               listener.contextCreated(event);
            }
         }

         ((InitFacesContext)ctx).setELContext(elContext);
      }

      app.publishEvent(ctx, PostConstructApplicationEvent.class, Application.class, app);
   }

   private static ExecutorService createExecutorService() {
      int tc = Runtime.getRuntime().availableProcessors();
      if (tc > 5) {
         tc = 5;
      }

      try {
         return (ExecutorService)(new InitialContext()).lookup("java:comp/env/concurrent/ThreadPool");
      } catch (NamingException var2) {
         return Executors.newFixedThreadPool(tc);
      }
   }

   private ThreadContext getThreadContext(InjectionProvider containerConnector) {
      return containerConnector instanceof ThreadContext ? (ThreadContext)containerConnector : null;
   }

   private void releaseFactories() {
      try {
         FactoryFinder.releaseFactories();
      } catch (FacesException var2) {
         LOGGER.log(Level.FINE, "Exception thrown from FactoryFinder.releaseFactories()", var2);
      }

   }

   public void destroy(ServletContext servletContext, FacesContext facesContext) {
      this.configProcessors.stream().forEach((e) -> {
         e.destroy(servletContext, facesContext);
      });
      this.initializedContexts.remove(servletContext);
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      INJECTION_PROVIDER_KEY = ConfigManager.class.getName() + "_INJECTION_PROVIDER_TASK";
      ANNOTATIONS_SCAN_TASK_KEY = ConfigManager.class.getName() + "_ANNOTATION_SCAN_TASK";
   }
}
