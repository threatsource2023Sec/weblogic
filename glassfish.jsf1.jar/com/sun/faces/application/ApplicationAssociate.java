package com.sun.faces.application;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.el.DemuxCompositeELResolver;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.el.VariableResolverChainWrapper;
import com.sun.faces.lifecycle.ELResolverInitPhaseListener;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.scripting.GroovyHelper;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderFactory;
import com.sun.faces.util.MessageUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javax.el.CompositeELResolver;
import javax.el.ExpressionFactory;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.servlet.ServletContext;

public class ApplicationAssociate {
   private static final String APPLICATION_IMPL_ATTR_NAME = "com.sun.faces.ApplicationImpl";
   private ApplicationImpl app = null;
   private Map caseListMap = null;
   private TreeSet wildcardMatchList = null;
   private boolean responseRendered = false;
   private static final String ASSOCIATE_KEY = "com.sun.faces.ApplicationAssociate";
   private static ThreadLocal instance = new ThreadLocal() {
      protected ApplicationAssociate initialValue() {
         return null;
      }
   };
   private List elResolversFromFacesConfig = null;
   private VariableResolver legacyVRChainHead = null;
   private VariableResolverChainWrapper legacyVRChainHeadWrapperForJsp = null;
   private VariableResolverChainWrapper legacyVRChainHeadWrapperForFaces = null;
   private PropertyResolver legacyPRChainHead = null;
   private ExpressionFactory expressionFactory = null;
   private PropertyResolver legacyPropertyResolver = null;
   private VariableResolver legacyVariableResolver = null;
   private FacesCompositeELResolver facesELResolverForJsp = null;
   private InjectionProvider injectionProvider;
   private String contextName;
   private boolean requestServiced;
   private BeanManager beanManager;
   private GroovyHelper groovyHelper;
   private boolean devModeEnabled;
   private PropertyEditorHelper propertyEditorHelper;
   Map resourceBundles = new HashMap();

   public ApplicationAssociate(ApplicationImpl appImpl) {
      this.app = appImpl;
      this.propertyEditorHelper = new PropertyEditorHelper(appImpl);
      FacesContext ctx = FacesContext.getCurrentInstance();
      if (ctx == null) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_ASSOCIATE_CTOR_WRONG_CALLSTACK"));
      } else {
         ExternalContext externalContext = ctx.getExternalContext();
         if (null != externalContext.getApplicationMap().get("com.sun.faces.ApplicationAssociate")) {
            throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.APPLICATION_ASSOCIATE_EXISTS"));
         } else {
            externalContext.getApplicationMap().put("com.sun.faces.ApplicationImpl", appImpl);
            externalContext.getApplicationMap().put("com.sun.faces.ApplicationAssociate", this);
            this.caseListMap = new HashMap();
            this.wildcardMatchList = new TreeSet(new SortIt());
            this.injectionProvider = InjectionProviderFactory.createInstance(externalContext);
            WebConfiguration webConfig = WebConfiguration.getInstance(externalContext);
            this.beanManager = new BeanManager(this.injectionProvider, webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableLazyBeanValidation));
            this.groovyHelper = GroovyHelper.getCurrentInstance();
            this.devModeEnabled = webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DevelopmentMode);
         }
      }
   }

   public static ApplicationAssociate getInstance(ExternalContext externalContext) {
      if (externalContext == null) {
         return null;
      } else {
         Map applicationMap = externalContext.getApplicationMap();
         return (ApplicationAssociate)applicationMap.get("com.sun.faces.ApplicationAssociate");
      }
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
         FacesContext fc = FacesContext.getCurrentInstance();
         if (fc != null) {
            ExternalContext extContext = fc.getExternalContext();
            if (extContext != null) {
               return getInstance(extContext);
            }
         }
      }

      return associate;
   }

   public static void clearInstance(ExternalContext externalContext) {
      Map applicationMap = externalContext.getApplicationMap();
      ApplicationAssociate me = (ApplicationAssociate)applicationMap.get("com.sun.faces.ApplicationAssociate");
      if (null != me && null != me.resourceBundles) {
         me.resourceBundles.clear();
      }

      applicationMap.remove("com.sun.faces.ApplicationAssociate");
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public GroovyHelper getGroovyHelper() {
      return this.groovyHelper;
   }

   public boolean isDevModeEnabled() {
      return this.devModeEnabled;
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
      return this.app.getApplicationELResolvers();
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

   public void addNavigationCase(ConfigNavigationCase navigationCase) {
      String fromViewId = navigationCase.getFromViewId();
      List caseList = (List)this.caseListMap.get(fromViewId);
      if (caseList == null) {
         List caseList = new ArrayList();
         caseList.add(navigationCase);
         this.caseListMap.put(fromViewId, caseList);
      } else {
         String key = navigationCase.getKey();
         boolean foundIt = false;

         for(int i = 0; i < caseList.size(); ++i) {
            ConfigNavigationCase navCase = (ConfigNavigationCase)caseList.get(i);
            if (key.equals(navCase.getKey())) {
               caseList.set(i, navigationCase);
               foundIt = true;
               break;
            }
         }

         if (!foundIt) {
            caseList.add(navigationCase);
         }
      }

      if (fromViewId.endsWith("*")) {
         fromViewId = fromViewId.substring(0, fromViewId.lastIndexOf(42));
         this.wildcardMatchList.add(fromViewId);
      }

   }

   public Map getNavigationCaseListMappings() {
      return this.caseListMap == null ? Collections.emptyMap() : this.caseListMap;
   }

   public TreeSet getNavigationWildCardList() {
      return this.wildcardMatchList;
   }

   public ResourceBundle getResourceBundle(FacesContext context, String var) {
      ApplicationResourceBundle bundle = (ApplicationResourceBundle)this.resourceBundles.get(var);
      if (bundle == null) {
         return null;
      } else {
         Locale defaultLocale = Locale.getDefault();
         Locale locale = defaultLocale;
         UIViewRoot root;
         if (null != (root = context.getViewRoot()) && null == (locale = root.getLocale())) {
            locale = defaultLocale;
         }

         assert null != locale;

         return bundle.getResourceBundle(locale);
      }
   }

   public void initializeELResolverChains() {
      if (null == this.app.compositeELResolver) {
         this.app.compositeELResolver = new DemuxCompositeELResolver(FacesCompositeELResolver.ELResolverChainType.Faces);
         ELUtils.buildFacesResolver(this.app.compositeELResolver, this);
         ELResolverInitPhaseListener.populateFacesELResolverForJsp(this.app, this);
      }

   }

   public void installProgrammaticallyAddedResolvers() {
      VariableResolver vr = this.getLegacyVariableResolver();
      if (null != vr) {
         assert null != this.getLegacyVRChainHeadWrapperForJsp();

         this.getLegacyVRChainHeadWrapperForJsp().setWrapped(vr);

         assert null != this.getLegacyVRChainHeadWrapperForFaces();

         this.getLegacyVRChainHeadWrapperForFaces().setWrapped(vr);
      }

   }

   public void addResourceBundle(String var, ApplicationResourceBundle bundle) {
      this.resourceBundles.put(var, bundle);
   }

   public Map getResourceBundles() {
      return this.resourceBundles;
   }

   void responseRendered() {
      this.responseRendered = true;
   }

   boolean isResponseRendered() {
      return this.responseRendered;
   }

   static class SortIt implements Comparator {
      public int compare(String fromViewId1, String fromViewId2) {
         return -fromViewId1.compareTo(fromViewId2);
      }
   }
}
