package com.sun.faces.application;

import com.sun.faces.application.annotation.AnnotationManager;
import com.sun.faces.application.annotation.FacesComponentUsage;
import com.sun.faces.application.resource.ResourceCache;
import com.sun.faces.application.resource.ResourceManager;
import com.sun.faces.component.search.SearchExpressionHandlerImpl;
import com.sun.faces.config.ConfigManager;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.el.DemuxCompositeELResolver;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.el.VariableResolverChainWrapper;
import com.sun.faces.facelets.PrivateApiFaceletCacheAdapter;
import com.sun.faces.facelets.compiler.Compiler;
import com.sun.faces.facelets.compiler.SAXCompiler;
import com.sun.faces.facelets.impl.DefaultFaceletFactory;
import com.sun.faces.facelets.impl.DefaultResourceResolver;
import com.sun.faces.facelets.tag.composite.CompositeLibrary;
import com.sun.faces.facelets.tag.jsf.PassThroughAttributeLibrary;
import com.sun.faces.facelets.tag.jsf.PassThroughElementLibrary;
import com.sun.faces.facelets.tag.jsf.core.CoreLibrary;
import com.sun.faces.facelets.tag.jsf.html.HtmlLibrary;
import com.sun.faces.facelets.tag.jstl.core.JstlCoreLibrary;
import com.sun.faces.facelets.tag.jstl.fn.JstlFunction;
import com.sun.faces.facelets.tag.ui.UILibrary;
import com.sun.faces.facelets.util.DevTools;
import com.sun.faces.facelets.util.FunctionLibrary;
import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.lifecycle.ELResolverInitPhaseListener;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.CompositeELResolver;
import javax.el.ExpressionFactory;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.NavigationCase;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyCustomScopeEvent;
import javax.faces.event.ScopeContext;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowHandlerFactory;
import javax.faces.view.facelets.FaceletCache;
import javax.faces.view.facelets.FaceletCacheFactory;
import javax.faces.view.facelets.FaceletsResourceResolver;
import javax.faces.view.facelets.ResourceResolver;
import javax.faces.view.facelets.TagDecorator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ApplicationAssociate {
   private static final Logger LOGGER;
   private ApplicationImpl applicationImpl;
   private Map navigationMap;
   private Map facesComponentsByNamespace;
   private boolean responseRendered;
   private static final String ASSOCIATE_KEY = "com.sun.faces.ApplicationAssociate";
   private static ThreadLocal instance;
   private List elResolversFromFacesConfig;
   private VariableResolver legacyVRChainHead;
   private VariableResolverChainWrapper legacyVRChainHeadWrapperForJsp;
   private VariableResolverChainWrapper legacyVRChainHeadWrapperForFaces;
   private PropertyResolver legacyPRChainHead;
   private ExpressionFactory expressionFactory;
   private PropertyResolver legacyPropertyResolver;
   private VariableResolver legacyVariableResolver;
   private FacesCompositeELResolver facesELResolverForJsp;
   private InjectionProvider injectionProvider;
   private ResourceCache resourceCache;
   private String contextName;
   private boolean requestServiced;
   private boolean errorPagePresent;
   private BeanManager beanManager;
   private AnnotationManager annotationManager;
   private boolean devModeEnabled;
   private boolean hasPushBuilder;
   private Compiler compiler;
   private DefaultFaceletFactory faceletFactory;
   private ResourceManager resourceManager;
   private ApplicationStateInfo applicationStateInfo;
   private PropertyEditorHelper propertyEditorHelper;
   private NamedEventManager namedEventManager;
   private WebConfiguration webConfig;
   private FlowHandler flowHandler;
   private SearchExpressionHandler searchExpressionHandler;
   private Map definingDocumentIdsToTruncatedJarUrls;
   private long timeOfInstantiation;
   private Map resourceLibraryContracts;
   Map resourceBundles = new HashMap();

   public ApplicationAssociate(ApplicationImpl appImpl) {
      this.applicationImpl = appImpl;
      this.propertyEditorHelper = new PropertyEditorHelper(appImpl);
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext == null) {
         throw new IllegalStateException("ApplicationAssociate ctor not called in same callstack as ConfigureListener.contextInitialized()");
      } else {
         ExternalContext externalContext = facesContext.getExternalContext();
         if (externalContext.getApplicationMap().get("com.sun.faces.ApplicationAssociate") != null) {
            throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_ASSOCIATE_EXISTS"));
         } else {
            Map applicationMap = externalContext.getApplicationMap();
            applicationMap.put("com.sun.faces.ApplicationAssociate", this);
            this.navigationMap = new ConcurrentHashMap();
            this.injectionProvider = (InjectionProvider)facesContext.getAttributes().get(ConfigManager.INJECTION_PROVIDER_KEY);
            this.webConfig = WebConfiguration.getInstance(externalContext);
            this.beanManager = new BeanManager(this.injectionProvider, this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableLazyBeanValidation));
            this.applicationImpl.subscribeToEvent(PreDestroyCustomScopeEvent.class, ScopeContext.class, this.beanManager);
            this.annotationManager = new AnnotationManager();
            this.devModeEnabled = appImpl.getProjectStage() == ProjectStage.Development;
            this.hasPushBuilder = this.checkForPushBuilder();
            if (!this.devModeEnabled) {
               this.resourceCache = new ResourceCache();
            }

            this.resourceManager = new ResourceManager(applicationMap, this.resourceCache);
            this.namedEventManager = new NamedEventManager();
            this.applicationStateInfo = new ApplicationStateInfo();
            appImpl.subscribeToEvent(PostConstructApplicationEvent.class, Application.class, new PostConstructApplicationListener());
            this.definingDocumentIdsToTruncatedJarUrls = new ConcurrentHashMap();
            this.timeOfInstantiation = System.currentTimeMillis();
         }
      }
   }

   private boolean checkForPushBuilder() {
      try {
         return HttpServletRequest.class.getMethod("newPushBuilder", (Class[])null) != null;
      } catch (SecurityException | NoSuchMethodException var2) {
         return false;
      }
   }

   public Application getApplication() {
      return this.applicationImpl;
   }

   public void setResourceLibraryContracts(Map map) {
      this.resourceLibraryContracts = map;
   }

   public void initializeFacelets() {
      if (this.compiler == null) {
         FacesContext ctx = FacesContext.getCurrentInstance();
         if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) && !this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated)) {
            Map appMap = ctx.getExternalContext().getApplicationMap();
            this.compiler = this.createCompiler(appMap, this.webConfig);
            this.faceletFactory = this.createFaceletFactory(ctx, this.compiler, this.webConfig);
         }

      }
   }

   public static ApplicationAssociate getInstance(ExternalContext externalContext) {
      return externalContext == null ? null : (ApplicationAssociate)externalContext.getApplicationMap().get("com.sun.faces.ApplicationAssociate");
   }

   public long getTimeOfInstantiation() {
      return this.timeOfInstantiation;
   }

   public static ApplicationAssociate getInstance(ServletContext context) {
      return context == null ? null : (ApplicationAssociate)context.getAttribute("com.sun.faces.ApplicationAssociate");
   }

   public static void setCurrentInstance(ApplicationAssociate associate) {
      if (associate == null) {
         instance.remove();
      } else {
         instance.set(associate);
      }

   }

   public static ApplicationAssociate getCurrentInstance() {
      ApplicationAssociate associate = (ApplicationAssociate)instance.get();
      if (associate == null) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         if (facesContext != null) {
            ExternalContext extContext = facesContext.getExternalContext();
            if (extContext != null) {
               return getInstance(extContext);
            }
         }
      }

      return associate;
   }

   public ApplicationStateInfo getApplicationStateInfo() {
      return this.applicationStateInfo;
   }

   public ResourceManager getResourceManager() {
      return this.resourceManager;
   }

   public Map getResourceLibraryContracts() {
      return this.resourceLibraryContracts;
   }

   public void setResourceManager(ResourceManager resourceManager) {
      this.resourceManager = resourceManager;
   }

   public ResourceCache getResourceCache() {
      return this.resourceCache;
   }

   public AnnotationManager getAnnotationManager() {
      return this.annotationManager;
   }

   public Compiler getCompiler() {
      if (this.compiler == null) {
         this.initializeFacelets();
      }

      return this.compiler;
   }

   public boolean isErrorPagePresent() {
      return this.errorPagePresent;
   }

   public void setErrorPagePresent(boolean errorPagePresent) {
      this.errorPagePresent = errorPagePresent;
   }

   public DefaultFaceletFactory getFaceletFactory() {
      return this.faceletFactory;
   }

   public static void clearInstance(ExternalContext externalContext) {
      Map applicationMap = externalContext.getApplicationMap();
      ApplicationAssociate me = (ApplicationAssociate)applicationMap.get("com.sun.faces.ApplicationAssociate");
      if (me != null && me.resourceBundles != null) {
         me.resourceBundles.clear();
      }

      applicationMap.remove("com.sun.faces.ApplicationAssociate");
   }

   public static void clearInstance(ServletContext servletContext) {
      ApplicationAssociate me = (ApplicationAssociate)servletContext.getAttribute("com.sun.faces.ApplicationAssociate");
      if (me != null && me.resourceBundles != null) {
         me.resourceBundles.clear();
      }

      servletContext.removeAttribute("com.sun.faces.ApplicationAssociate");
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public void initializeELResolverChains() {
      if (this.applicationImpl.getCompositeELResolver() == null) {
         this.applicationImpl.setCompositeELResolver(new DemuxCompositeELResolver(FacesCompositeELResolver.ELResolverChainType.Faces));
         ELUtils.buildFacesResolver(this.applicationImpl.getCompositeELResolver(), this);
         ELResolverInitPhaseListener.populateFacesELResolverForJsp(this.applicationImpl, this);
      }

   }

   public void installProgrammaticallyAddedResolvers() {
      VariableResolver variableResolver = this.getLegacyVariableResolver();
      if (variableResolver != null) {
         this.getLegacyVRChainHeadWrapperForJsp().setWrapped(variableResolver);
         this.getLegacyVRChainHeadWrapperForFaces().setWrapped(variableResolver);
      }

   }

   public boolean isDevModeEnabled() {
      return this.devModeEnabled;
   }

   public boolean isPushBuilderSupported() {
      return this.hasPushBuilder;
   }

   public PropertyEditorHelper getPropertyEditorHelper() {
      return this.propertyEditorHelper;
   }

   public void setLegacyVRChainHead(VariableResolver resolver) {
      this.legacyVRChainHead = resolver;
   }

   public VariableResolver getLegacyVRChainHead() {
      return this.legacyVRChainHead;
   }

   public VariableResolverChainWrapper getLegacyVRChainHeadWrapperForJsp() {
      return this.legacyVRChainHeadWrapperForJsp;
   }

   public void setLegacyVRChainHeadWrapperForJsp(VariableResolverChainWrapper legacyVRChainHeadWrapper) {
      this.legacyVRChainHeadWrapperForJsp = legacyVRChainHeadWrapper;
   }

   public VariableResolverChainWrapper getLegacyVRChainHeadWrapperForFaces() {
      return this.legacyVRChainHeadWrapperForFaces;
   }

   public void setLegacyVRChainHeadWrapperForFaces(VariableResolverChainWrapper legacyVRChainHeadWrapperForFaces) {
      this.legacyVRChainHeadWrapperForFaces = legacyVRChainHeadWrapperForFaces;
   }

   public void setLegacyPRChainHead(PropertyResolver resolver) {
      this.legacyPRChainHead = resolver;
   }

   public PropertyResolver getLegacyPRChainHead() {
      return this.legacyPRChainHead;
   }

   public FacesCompositeELResolver getFacesELResolverForJsp() {
      return this.facesELResolverForJsp;
   }

   public FlowHandler getFlowHandler() {
      return this.flowHandler;
   }

   public void setFlowHandler(FlowHandler flowHandler) {
      this.flowHandler = flowHandler;
   }

   public SearchExpressionHandler getSearchExpressionHandler() {
      return this.searchExpressionHandler;
   }

   public void setSearchExpressionHandler(SearchExpressionHandler searchExpressionHandler) {
      this.searchExpressionHandler = searchExpressionHandler;
   }

   public void setFacesELResolverForJsp(FacesCompositeELResolver celr) {
      this.facesELResolverForJsp = celr;
   }

   public void setELResolversFromFacesConfig(List resolvers) {
      this.elResolversFromFacesConfig = resolvers;
   }

   public List getELResolversFromFacesConfig() {
      return this.elResolversFromFacesConfig;
   }

   public void setExpressionFactory(ExpressionFactory expressionFactory) {
      this.expressionFactory = expressionFactory;
   }

   public ExpressionFactory getExpressionFactory() {
      return this.expressionFactory;
   }

   public CompositeELResolver getApplicationELResolvers() {
      return this.applicationImpl.getApplicationELResolvers();
   }

   public InjectionProvider getInjectionProvider() {
      return this.injectionProvider;
   }

   public void setContextName(String contextName) {
      this.contextName = contextName;
   }

   public String getContextName() {
      return this.contextName;
   }

   public void setLegacyPropertyResolver(PropertyResolver resolver) {
      this.legacyPropertyResolver = resolver;
   }

   public PropertyResolver getLegacyPropertyResolver() {
      return this.legacyPropertyResolver;
   }

   public void setLegacyVariableResolver(VariableResolver resolver) {
      this.legacyVariableResolver = resolver;
   }

   public VariableResolver getLegacyVariableResolver() {
      return this.legacyVariableResolver;
   }

   public void setRequestServiced() {
      this.requestServiced = true;
   }

   public boolean hasRequestBeenServiced() {
      return this.requestServiced;
   }

   public void addFacesComponent(FacesComponentUsage facesComponentUsage) {
      if (this.facesComponentsByNamespace == null) {
         this.facesComponentsByNamespace = new HashMap();
      }

      ((List)this.facesComponentsByNamespace.computeIfAbsent(facesComponentUsage.getAnnotation().namespace(), (k) -> {
         return new ArrayList();
      })).add(facesComponentUsage);
   }

   public List getComponentsForNamespace(String namespace) {
      return this.facesComponentsByNamespace != null && this.facesComponentsByNamespace.containsKey(namespace) ? (List)this.facesComponentsByNamespace.get(namespace) : Collections.emptyList();
   }

   public void addNavigationCase(NavigationCase navigationCase) {
      ((Set)this.navigationMap.computeIfAbsent(navigationCase.getFromViewId(), (k) -> {
         return new LinkedHashSet();
      })).add(navigationCase);
   }

   public NamedEventManager getNamedEventManager() {
      return this.namedEventManager;
   }

   public Map getNavigationCaseListMappings() {
      return this.navigationMap == null ? Collections.emptyMap() : this.navigationMap;
   }

   public ResourceBundle getResourceBundle(FacesContext context, String var) {
      ApplicationResourceBundle bundle = (ApplicationResourceBundle)this.resourceBundles.get(var);
      if (bundle == null) {
         return null;
      } else {
         Locale defaultLocale = Locale.getDefault();
         Locale locale = defaultLocale;
         UIViewRoot root = context.getViewRoot();
         if (root != null) {
            locale = root.getLocale();
            if (locale == null) {
               locale = defaultLocale;
            }
         }

         return bundle.getResourceBundle(locale);
      }
   }

   public void addResourceBundle(String var, ApplicationResourceBundle bundle) {
      this.resourceBundles.put(var, bundle);
   }

   public Map getResourceBundles() {
      return this.resourceBundles;
   }

   public void responseRendered() {
      this.responseRendered = true;
   }

   public boolean isResponseRendered() {
      return this.responseRendered;
   }

   public boolean urlIsRelatedToDefiningDocumentInJar(URL candidateUrl, String definingDocumentId) {
      boolean result = false;
      String match = (String)this.definingDocumentIdsToTruncatedJarUrls.get(definingDocumentId);
      if (match != null) {
         String candidate = candidateUrl.toExternalForm();
         if (candidate != null) {
            int i = candidate.lastIndexOf("/META-INF");
            if (i == -1) {
               throw new FacesException("Invalid url for application configuration resources file with respect to faces flows");
            }

            candidate = candidate.substring(0, i);
            result = candidate.equals(match);
         }
      }

      return result;
   }

   public void relateUrlToDefiningDocumentInJar(URL url, String definingDocumentId) {
      String candidate = url.toExternalForm();
      int i = candidate.lastIndexOf("/META-INF");
      if (i != -1) {
         candidate = candidate.substring(0, i);
         this.definingDocumentIdsToTruncatedJarUrls.put(definingDocumentId, candidate);
      }
   }

   protected DefaultFaceletFactory createFaceletFactory(FacesContext ctx, Compiler compiler, WebConfiguration webConfig) {
      boolean isProduction = this.applicationImpl.getProjectStage() == ProjectStage.Production;
      String refreshPeriod;
      if (!webConfig.isSet(WebConfiguration.WebContextInitParameter.FaceletsDefaultRefreshPeriod) && !webConfig.isSet(WebConfiguration.WebContextInitParameter.FaceletsDefaultRefreshPeriodDeprecated)) {
         if (isProduction) {
            refreshPeriod = "-1";
         } else {
            refreshPeriod = WebConfiguration.WebContextInitParameter.FaceletsDefaultRefreshPeriod.getDefaultValue();
         }
      } else {
         refreshPeriod = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsDefaultRefreshPeriod);
      }

      long period = Long.parseLong(refreshPeriod);
      ResourceResolver defaultResourceResolver = new DefaultResourceResolver(this.applicationImpl.getResourceHandler());
      ResourceResolver resolver = defaultResourceResolver;
      String resolverName = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsResourceResolver);
      if (resolverName != null && resolverName.length() > 0) {
         resolver = (ResourceResolver)ReflectionUtil.decorateInstance((String)resolverName, ResourceResolver.class, defaultResourceResolver);
      } else {
         Set resourceResolvers = (Set)ConfigManager.getAnnotatedClasses(ctx).get(FaceletsResourceResolver.class);
         if (null != resourceResolvers && !resourceResolvers.isEmpty()) {
            Class resolverClass = (Class)resourceResolvers.iterator().next();
            if (resourceResolvers.size() > 1 && LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Found more than one class annotated with FaceletsResourceResolver.  Will use {0} and ignore the others", resolverClass);
            }

            resolver = (ResourceResolver)ReflectionUtil.decorateInstance((Class)resolverClass, ResourceResolver.class, defaultResourceResolver);
         }
      }

      if (resolver != defaultResourceResolver && webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableFaceletsResourceResolverResolveCompositeComponents)) {
         ctx.getExternalContext().getApplicationMap().put("com.sun.faces.NDRRPN", resolver);
      }

      FaceletCache cache = null;
      String faceletCacheName = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletCache);
      if (faceletCacheName != null && faceletCacheName.length() > 0) {
         try {
            com.sun.faces.facelets.FaceletCache privateApiCache = (com.sun.faces.facelets.FaceletCache)ReflectionUtil.forName(faceletCacheName).newInstance();
            cache = new PrivateApiFaceletCacheAdapter(privateApiCache);
         } catch (ClassCastException var14) {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "Please remove context-param when using javax.faces.view.facelets.FaceletCache class with name:" + faceletCacheName + "and use the new FaceletCacheFactory API", var14);
            }
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var15) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Error Loading Facelet cache: " + faceletCacheName, var15);
            }
         }
      }

      if (cache == null) {
         FaceletCacheFactory cacheFactory = (FaceletCacheFactory)FactoryFinder.getFactory("javax.faces.view.facelets.FaceletCacheFactory");
         cache = cacheFactory.getFaceletCache();
      }

      DefaultFaceletFactory toReturn = new DefaultFaceletFactory();
      toReturn.init(compiler, (ResourceResolver)resolver, period, (FaceletCache)cache);
      return toReturn;
   }

   protected Compiler createCompiler(Map appMap, WebConfiguration webConfig) {
      Compiler newCompiler = new SAXCompiler();
      this.loadDecorators(appMap, newCompiler);
      newCompiler.setTrimmingComments(webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.FaceletsSkipComments));
      this.addTagLibraries(newCompiler);
      return newCompiler;
   }

   protected void loadDecorators(Map appMap, Compiler newCompiler) {
      String decoratorsParamValue = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsDecorators);
      if (decoratorsParamValue != null) {
         String[] var4 = Util.split(appMap, decoratorsParamValue.trim(), ";");
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String decorator = var4[var6];

            try {
               newCompiler.addTagDecorator((TagDecorator)ReflectionUtil.forName(decorator).newInstance());
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Successfully Loaded Decorator: {0}", decorator);
               }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var9) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Error Loading Decorator: " + decorator, var9);
               }
            }
         }
      }

   }

   protected void addTagLibraries(Compiler newCompiler) {
      newCompiler.addTagLibrary(new CoreLibrary());
      newCompiler.addTagLibrary(new CoreLibrary("http://xmlns.jcp.org/jsf/core"));
      newCompiler.addTagLibrary(new HtmlLibrary());
      newCompiler.addTagLibrary(new HtmlLibrary("http://xmlns.jcp.org/jsf/html"));
      newCompiler.addTagLibrary(new UILibrary());
      newCompiler.addTagLibrary(new UILibrary("http://xmlns.jcp.org/jsf/facelets"));
      newCompiler.addTagLibrary(new JstlCoreLibrary());
      newCompiler.addTagLibrary(new JstlCoreLibrary("http://java.sun.com/jstl/core"));
      newCompiler.addTagLibrary(new JstlCoreLibrary("http://xmlns.jcp.org/jsp/jstl/core"));
      newCompiler.addTagLibrary(new PassThroughAttributeLibrary());
      newCompiler.addTagLibrary(new PassThroughElementLibrary());
      newCompiler.addTagLibrary(new FunctionLibrary(JstlFunction.class, "http://java.sun.com/jsp/jstl/functions"));
      newCompiler.addTagLibrary(new FunctionLibrary(JstlFunction.class, "http://xmlns.jcp.org/jsp/jstl/functions"));
      if (this.isDevModeEnabled()) {
         newCompiler.addTagLibrary(new FunctionLibrary(DevTools.class, "http://java.sun.com/mojarra/private/functions"));
         newCompiler.addTagLibrary(new FunctionLibrary(DevTools.class, "http://xmlns.jcp.org/mojarra/private/functions"));
      }

      newCompiler.addTagLibrary(new CompositeLibrary());
      newCompiler.addTagLibrary(new CompositeLibrary("http://xmlns.jcp.org/jsf/composite"));
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      instance = new ThreadLocal() {
         protected ApplicationAssociate initialValue() {
            return null;
         }
      };
   }

   private class PostConstructApplicationListener implements SystemEventListener {
      private PostConstructApplicationListener() {
      }

      public boolean isListenerForSource(Object source) {
         return source instanceof Application;
      }

      public void processEvent(SystemEvent event) {
         ApplicationAssociate.this.initializeFacelets();
         if (ApplicationAssociate.this.flowHandler == null) {
            FlowHandlerFactory flowHandlerFactory = (FlowHandlerFactory)FactoryFinder.getFactory("javax.faces.flow.FlowHandlerFactory");
            ApplicationAssociate.this.flowHandler = flowHandlerFactory.createFlowHandler(FacesContext.getCurrentInstance());
         }

         if (ApplicationAssociate.this.searchExpressionHandler == null) {
            ApplicationAssociate.this.searchExpressionHandler = new SearchExpressionHandlerImpl();
         }

         FacesContext context = FacesContext.getCurrentInstance();
         if (Util.isCdiAvailable(context)) {
            try {
               (new JavaFlowLoaderHelper()).loadFlows(context, ApplicationAssociate.this.flowHandler);
            } catch (IOException var5) {
               ApplicationAssociate.LOGGER.log(Level.SEVERE, (String)null, var5);
            }
         }

         ViewHandler viewHandler = context.getApplication().getViewHandler();
         viewHandler.getViewDeclarationLanguage(context, "com.sun.faces.xhtml");
         String facesConfigVersion = Util.getFacesConfigXmlVersion(context);
         context.getExternalContext().getApplicationMap().put("com.sun.faces.facesConfigVersion", facesConfigVersion);
      }

      // $FF: synthetic method
      PostConstructApplicationListener(Object x1) {
         this();
      }
   }
}
