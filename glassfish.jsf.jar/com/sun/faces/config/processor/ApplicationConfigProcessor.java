package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationResourceBundle;
import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.el.ChainAwareVariableResolver;
import com.sun.faces.el.DummyPropertyResolverImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ResourceHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.context.FacesContext;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.event.NamedEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ApplicationConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String APPLICATION = "application";
   private static final String ACTION_LISTENER = "action-listener";
   private static final String DEFAULT_RENDERKIT_ID = "default-render-kit-id";
   private static final String DEFAULT_VALIDATORS = "default-validators";
   private static final String VALIDATOR_ID = "validator-id";
   private static final String MESSAGE_BUNDLE = "message-bundle";
   private static final String NAVIGATION_HANDLER = "navigation-handler";
   private static final String VIEW_HANDLER = "view-handler";
   private static final String STATE_MANAGER = "state-manager";
   private static final String RESOURCE_HANDLER = "resource-handler";
   private static final String EL_RESOLVER = "el-resolver";
   private static final String SEARCH_EXPRESSION_HANDLER = "search-expression-handler";
   private static final String SEARCH_KEYWORD_RESOLVER = "search-keyword-resolver";
   private static final String PROPERTY_RESOLVER = "property-resolver";
   private static final String VARIABLE_RESOLVER = "variable-resolver";
   private static final String DEFAULT_LOCALE = "default-locale";
   private static final String SUPPORTED_LOCALE = "supported-locale";
   private static final String RESOURCE_BUNDLE = "resource-bundle";
   private static final String BASE_NAME = "base-name";
   private static final String VAR = "var";
   private static final String RES_DESCRIPTIONS = "description";
   private static final String RES_DISPLAY_NAMES = "display-name";
   private static final String SYSTEM_EVENT_LISTENER = "system-event-listener";
   private static final String SYSTEM_EVENT_LISTENER_CLASS = "system-event-listener-class";
   private static final String SYSTEM_EVENT_CLASS = "system-event-class";
   private static final String SOURCE_CLASS = "source-class";
   private List actionListeners = new CopyOnWriteArrayList();
   private List navigationHandlers = new CopyOnWriteArrayList();
   private List viewHandlers = new CopyOnWriteArrayList();
   private List stateManagers = new CopyOnWriteArrayList();
   private List resourceHandlers = new CopyOnWriteArrayList();
   private List elResolvers = new CopyOnWriteArrayList();
   private List systemEventListeners = new CopyOnWriteArrayList();
   private List searchExpressionHandlers = new CopyOnWriteArrayList();
   private List searchKeywordResolvers = new CopyOnWriteArrayList();

   public void process(ServletContext servletContext, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      Application application = this.getApplication();
      ApplicationAssociate associate = ApplicationAssociate.getInstance(facesContext.getExternalContext());
      LinkedHashMap viewHandlers = new LinkedHashMap();
      LinkedHashSet defaultValidatorIds = null;

      for(int i = 0; i < documentInfos.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing application elements for document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         NodeList applicationElements = document.getDocumentElement().getElementsByTagNameNS(namespace, "application");
         if (applicationElements != null && applicationElements.getLength() > 0) {
            int a = 0;

            for(int asize = applicationElements.getLength(); a < asize; ++a) {
               Node appElement = applicationElements.item(a);
               NodeList children = ((Element)appElement).getElementsByTagNameNS(namespace, "*");
               if (children != null && children.getLength() > 0) {
                  int c = 0;

                  for(int csize = children.getLength(); c < csize; ++c) {
                     Node n = children.item(c);
                     switch (n.getLocalName()) {
                        case "message-bundle":
                           this.setMessageBundle(application, n);
                           break;
                        case "default-render-kit-id":
                           this.setDefaultRenderKitId(application, n);
                           break;
                        case "action-listener":
                           this.addActionListener(servletContext, facesContext, application, n);
                           break;
                        case "navigation-handler":
                           this.setNavigationHandler(servletContext, facesContext, application, n);
                           break;
                        case "view-handler":
                           String viewHandler = this.getNodeText(n);
                           if (viewHandler != null) {
                              viewHandlers.put(viewHandler, n);
                           }
                           break;
                        case "state-manager":
                           this.setStateManager(servletContext, facesContext, application, n);
                           break;
                        case "el-resolver":
                           this.addELResolver(servletContext, facesContext, associate, n);
                           break;
                        case "property-resolver":
                           this.addPropertyResolver(servletContext, facesContext, associate, n);
                           break;
                        case "variable-resolver":
                           this.addVariableResolver(servletContext, facesContext, associate, n);
                           break;
                        case "default-locale":
                           this.setDefaultLocale(application, n);
                           break;
                        case "supported-locale":
                           this.addSupportedLocale(application, n);
                           break;
                        case "resource-bundle":
                           this.addResouceBundle(associate, n);
                           break;
                        case "resource-handler":
                           this.setResourceHandler(servletContext, facesContext, application, n);
                           break;
                        case "system-event-listener":
                           this.addSystemEventListener(servletContext, facesContext, application, n);
                           break;
                        case "default-validators":
                           if (defaultValidatorIds == null) {
                              defaultValidatorIds = new LinkedHashSet();
                           } else {
                              defaultValidatorIds.clear();
                           }
                           break;
                        case "validator-id":
                           defaultValidatorIds.add(this.getNodeText(n));
                           break;
                        case "search-expression-handler":
                           this.setSearchExpressionHandler(servletContext, facesContext, application, n);
                           break;
                        case "search-keyword-resolver":
                           this.addSearchKeywordResolver(servletContext, facesContext, application, n);
                     }
                  }
               }
            }
         }
      }

      this.registerDefaultValidatorIds(facesContext, application, defaultValidatorIds);
      this.processViewHandlers(servletContext, facesContext, application, viewHandlers);
      this.processAnnotations(facesContext, NamedEvent.class);
   }

   public void destroy(ServletContext sc, FacesContext facesContext) {
      this.destroyInstances(sc, facesContext, this.actionListeners);
      this.destroyInstances(sc, facesContext, this.navigationHandlers);
      this.destroyInstances(sc, facesContext, this.stateManagers);
      this.destroyInstances(sc, facesContext, this.viewHandlers);
      this.destroyInstances(sc, facesContext, this.elResolvers);
      this.destroyInstances(sc, facesContext, this.resourceHandlers);
      this.destroyInstances(sc, facesContext, this.systemEventListeners);
      this.destroyInstances(sc, facesContext, this.searchExpressionHandlers);
      this.destroyInstances(sc, facesContext, this.searchKeywordResolvers);
   }

   private void destroyInstances(ServletContext sc, FacesContext facesContext, List instances) {
      Iterator var4 = instances.iterator();

      while(var4.hasNext()) {
         Object instance = var4.next();
         this.destroyInstance(sc, facesContext, instance.getClass().getName(), instance);
      }

      instances.clear();
   }

   private void registerDefaultValidatorIds(FacesContext facesContext, Application application, LinkedHashSet defaultValidatorIds) {
      if (defaultValidatorIds == null) {
         defaultValidatorIds = new LinkedHashSet();
         if (isBeanValidatorAvailable(facesContext)) {
            WebConfiguration webConfig = WebConfiguration.getInstance();
            if (!webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableDefaultBeanValidator)) {
               defaultValidatorIds.add("javax.faces.Bean");
            }
         }
      }

      String validatorId;
      for(Iterator var6 = defaultValidatorIds.iterator(); var6.hasNext(); application.addDefaultValidatorId(validatorId)) {
         validatorId = (String)var6.next();
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.addDefaultValidatorId({0})", validatorId));
         }
      }

   }

   static boolean isBeanValidatorAvailable(FacesContext facesContext) {
      boolean result = false;
      String beansValidationAvailabilityCacheKey = "javax.faces.BEANS_VALIDATION_AVAILABLE";
      Map appMap = facesContext.getExternalContext().getApplicationMap();
      if (appMap.containsKey("javax.faces.BEANS_VALIDATION_AVAILABLE")) {
         result = (Boolean)appMap.get("javax.faces.BEANS_VALIDATION_AVAILABLE");
      } else {
         try {
            Thread.currentThread().getContextClassLoader().loadClass("javax.validation.MessageInterpolator");
            Object cachedObject = appMap.get("javax.faces.validator.beanValidator.ValidatorFactory");
            if (cachedObject instanceof ValidatorFactory) {
               result = true;
            } else {
               Context initialContext = null;

               try {
                  initialContext = new InitialContext();
               } catch (NoClassDefFoundError var10) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.log(Level.FINE, var10.toString(), var10);
                  }
               } catch (NamingException var11) {
                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, var11.toString(), var11);
                  }
               }

               try {
                  Object validatorFactory = initialContext.lookup("java:comp/ValidatorFactory");
                  if (null != validatorFactory) {
                     appMap.put("javax.faces.validator.beanValidator.ValidatorFactory", validatorFactory);
                     result = true;
                  }
               } catch (NamingException var9) {
                  if (LOGGER.isLoggable(Level.FINE)) {
                     LOGGER.fine("Could not build a default Bean Validator factory: " + var9.getMessage());
                  }
               }

               if (!result) {
                  try {
                     ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                     Validator validator = factory.getValidator();
                     appMap.put("javax.faces.validator.beanValidator.ValidatorFactory", factory);
                     result = true;
                  } catch (Throwable var8) {
                  }
               }
            }
         } catch (Throwable var12) {
            LOGGER.fine("Unable to load Beans Validation");
         }

         appMap.put("javax.faces.BEANS_VALIDATION_AVAILABLE", result);
      }

      return result;
   }

   private void setMessageBundle(Application application, Node messageBundle) {
      if (messageBundle != null) {
         String bundle = this.getNodeText(messageBundle);
         if (bundle != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setMessageBundle({0})", bundle));
            }

            application.setMessageBundle(bundle);
         }
      }

   }

   private void setDefaultRenderKitId(Application application, Node defaultId) {
      if (defaultId != null) {
         String id = this.getNodeText(defaultId);
         if (id != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setDefaultRenderKitId({0})", id));
            }

            application.setDefaultRenderKitId(id);
         }
      }

   }

   private void addActionListener(ServletContext sc, FacesContext facesContext, Application application, Node actionListener) {
      if (actionListener != null) {
         String listener = this.getNodeText(actionListener);
         if (listener != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            ActionListener instance = (ActionListener)this.createInstance(sc, facesContext, listener, ActionListener.class, application.getActionListener(), actionListener, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.actionListeners.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setActionListeners({0})", listener));
               }

               application.setActionListener(instance);
            }
         }
      }

   }

   private void setNavigationHandler(ServletContext sc, FacesContext facesContext, Application application, Node navigationHandler) {
      if (navigationHandler != null) {
         String handler = this.getNodeText(navigationHandler);
         if (handler != null) {
            Class rootType = this.findRootType(sc, facesContext, handler, navigationHandler, new Class[]{ConfigurableNavigationHandler.class, NavigationHandler.class});
            boolean[] didPerformInjection = new boolean[]{false};
            NavigationHandler instance = (NavigationHandler)this.createInstance(sc, facesContext, handler, rootType != null ? rootType : NavigationHandler.class, application.getNavigationHandler(), navigationHandler, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.navigationHandlers.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setNavigationHandlers({0})", handler));
               }

               application.setNavigationHandler(instance);
            }
         }
      }

   }

   private void setStateManager(ServletContext sc, FacesContext facesContext, Application application, Node stateManager) {
      if (stateManager != null) {
         String manager = this.getNodeText(stateManager);
         if (manager != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            StateManager instance = (StateManager)this.createInstance(sc, facesContext, manager, StateManager.class, application.getStateManager(), stateManager, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.stateManagers.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setStateManagers({0})", manager));
               }

               application.setStateManager(instance);
            }
         }
      }

   }

   private void setViewHandler(ServletContext sc, FacesContext facesContext, Application application, Node viewHandler) {
      if (viewHandler != null) {
         String handler = this.getNodeText(viewHandler);
         if (handler != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            ViewHandler instance = (ViewHandler)this.createInstance(sc, facesContext, handler, ViewHandler.class, application.getViewHandler(), viewHandler, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.viewHandlers.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setViewHandler({0})", handler));
               }

               application.setViewHandler(instance);
            }
         }
      }

   }

   private void addELResolver(ServletContext sc, FacesContext facesContext, ApplicationAssociate associate, Node elResolver) {
      if (elResolver != null && associate != null) {
         List resolvers = associate.getELResolversFromFacesConfig();
         if (resolvers == null) {
            resolvers = new ArrayList();
            associate.setELResolversFromFacesConfig((List)resolvers);
         }

         String elResolverClass = this.getNodeText(elResolver);
         if (elResolverClass != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            ELResolver elRes = (ELResolver)this.createInstance(sc, facesContext, elResolverClass, ELResolver.class, (Object)null, elResolver, true, didPerformInjection);
            if (elRes != null) {
               if (didPerformInjection[0]) {
                  this.elResolvers.add(elRes);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to ELResolver chain", elResolverClass));
               }

               ((List)resolvers).add(elRes);
            }
         }
      }

   }

   private void setSearchExpressionHandler(ServletContext sc, FacesContext facesContext, Application application, Node searchExpressionHandler) {
      if (searchExpressionHandler != null) {
         String handler = this.getNodeText(searchExpressionHandler);
         if (handler != null) {
            Class rootType = this.findRootType(sc, facesContext, handler, searchExpressionHandler, new Class[]{SearchExpressionHandler.class});
            boolean[] didPerformInjection = new boolean[]{false};
            SearchExpressionHandler instance = (SearchExpressionHandler)this.createInstance(sc, facesContext, handler, rootType != null ? rootType : SearchExpressionHandler.class, application.getSearchExpressionHandler(), searchExpressionHandler, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.searchExpressionHandlers.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setSearchExpressionHandler({0})", handler));
               }

               application.setSearchExpressionHandler(instance);
            }
         }
      }

   }

   private void addSearchKeywordResolver(ServletContext sc, FacesContext facesContext, Application application, Node searchKeywordResolver) {
      if (searchKeywordResolver != null) {
         String searchKeywordResolverClass = this.getNodeText(searchKeywordResolver);
         if (searchKeywordResolverClass != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            SearchKeywordResolver keywordResolver = (SearchKeywordResolver)this.createInstance(sc, facesContext, searchKeywordResolverClass, SearchKeywordResolver.class, (Object)null, searchKeywordResolver, true, didPerformInjection);
            if (keywordResolver != null) {
               if (didPerformInjection[0]) {
                  this.searchKeywordResolvers.add(keywordResolver);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to SearchKeywordResolver chain", searchKeywordResolverClass));
               }

               application.addSearchKeywordResolver(keywordResolver);
            }
         }
      }

   }

   private void addPropertyResolver(ServletContext sc, FacesContext facesContext, ApplicationAssociate associate, Node propertyResolver) {
      if (propertyResolver != null && associate != null) {
         Object resolverImpl = associate.getLegacyPRChainHead();
         if (resolverImpl == null) {
            resolverImpl = new DummyPropertyResolverImpl();
         }

         String resolver = this.getNodeText(propertyResolver);
         if (resolver != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            resolverImpl = this.createInstance(sc, facesContext, resolver, PropertyResolver.class, resolverImpl, propertyResolver, false, didPerformInjection);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to PropertyResolver chain", resolverImpl));
            }
         }

         if (resolverImpl != null) {
            associate.setLegacyPRChainHead((PropertyResolver)resolverImpl);
         }
      }

   }

   private void addVariableResolver(ServletContext sc, FacesContext facesContext, ApplicationAssociate associate, Node variableResolver) {
      if (variableResolver != null && associate != null) {
         Object resolverImpl = associate.getLegacyVRChainHead();
         if (resolverImpl == null) {
            resolverImpl = new ChainAwareVariableResolver();
         }

         String resolver = this.getNodeText(variableResolver);
         if (resolver != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            resolverImpl = this.createInstance(sc, facesContext, resolver, VariableResolver.class, resolverImpl, variableResolver, false, didPerformInjection);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to VariableResolver chain", resolverImpl));
            }
         }

         if (resolverImpl != null) {
            associate.setLegacyVRChainHead((VariableResolver)resolverImpl);
         }
      }

   }

   private void setDefaultLocale(Application application, Node defaultLocale) {
      if (defaultLocale != null) {
         String defLocale = this.getNodeText(defaultLocale);
         if (defLocale != null) {
            Locale def = Util.getLocaleFromString(defLocale);
            if (def != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Setting default Locale to ''{0}''", defLocale));
               }

               application.setDefaultLocale(def);
            }
         }
      }

   }

   private void addSupportedLocale(Application application, Node supportedLocale) {
      if (supportedLocale != null) {
         Set sLocales = this.getCurrentLocales(application);
         String locString = this.getNodeText(supportedLocale);
         if (locString != null) {
            Locale loc = Util.getLocaleFromString(locString);
            if (loc != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Adding supported Locale ''{0}''", locString));
               }

               sLocales.add(loc);
            }

            application.setSupportedLocales(sLocales);
         }
      }

   }

   private void addResouceBundle(ApplicationAssociate associate, Node resourceBundle) {
      if (resourceBundle != null) {
         NodeList children = resourceBundle.getChildNodes();
         if (children != null) {
            String baseName = null;
            String var = null;
            List descriptions = null;
            List displayNames = null;
            int i = 0;

            for(int size = children.getLength(); i < size; ++i) {
               Node n = children.item(i);
               if (n.getNodeType() == 1) {
                  switch (n.getLocalName()) {
                     case "base-name":
                        baseName = this.getNodeText(n);
                        break;
                     case "var":
                        var = this.getNodeText(n);
                        break;
                     case "description":
                        if (descriptions == null) {
                           descriptions = new ArrayList(2);
                        }

                        descriptions.add(n);
                        break;
                     case "display-name":
                        if (displayNames == null) {
                           displayNames = new ArrayList(2);
                        }

                        displayNames.add(n);
                  }
               }
            }

            if (baseName != null && var != null) {
               associate.addResourceBundle(var, new ApplicationResourceBundle(baseName, this.getTextMap(displayNames), this.getTextMap(descriptions)));
            }
         }
      }

   }

   private Set getCurrentLocales(Application application) {
      Set supportedLocales = new HashSet();
      Iterator i = application.getSupportedLocales();

      while(i.hasNext()) {
         supportedLocales.add(i.next());
      }

      return supportedLocales;
   }

   private void setResourceHandler(ServletContext sc, FacesContext facesContext, Application application, Node resourceHandler) {
      if (resourceHandler != null) {
         String handler = this.getNodeText(resourceHandler);
         if (handler != null) {
            boolean[] didPerformInjection = new boolean[]{false};
            ResourceHandler instance = (ResourceHandler)this.createInstance(sc, facesContext, handler, ResourceHandler.class, application.getResourceHandler(), resourceHandler, true, didPerformInjection);
            if (instance != null) {
               if (didPerformInjection[0]) {
                  this.resourceHandlers.add(instance);
               }

               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setResourceHandler({0})", handler));
               }

               application.setResourceHandler(instance);
            }
         }
      }

   }

   private void addSystemEventListener(ServletContext sc, FacesContext facesContext, Application application, Node systemEventListener) {
      NodeList children = systemEventListener.getChildNodes();
      String listenerClass = null;
      String eventClass = null;
      String sourceClass = null;
      int j = 0;

      for(int len = children.getLength(); j < len; ++j) {
         Node n = children.item(j);
         if (n.getNodeType() == 1) {
            switch (n.getLocalName()) {
               case "system-event-listener-class":
                  listenerClass = this.getNodeText(n);
                  break;
               case "system-event-class":
                  eventClass = this.getNodeText(n);
                  break;
               case "source-class":
                  sourceClass = this.getNodeText(n);
            }
         }
      }

      if (listenerClass != null) {
         SystemEventListener selInstance = (SystemEventListener)this.createInstance(sc, facesContext, listenerClass, SystemEventListener.class, (Object)null, systemEventListener);
         if (selInstance != null) {
            this.systemEventListeners.add(selInstance);

            try {
               Class eventClazz;
               if (eventClass != null) {
                  eventClazz = this.loadClass(sc, facesContext, eventClass, this, (Class)null);
               } else {
                  eventClazz = SystemEvent.class;
               }

               Class sourceClazz = sourceClass != null && sourceClass.length() != 0 ? Util.loadClass(sourceClass, this.getClass()) : null;
               application.subscribeToEvent(eventClazz, sourceClazz, selInstance);
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Subscribing for event {0} and source {1} using listener {2}", new Object[]{eventClazz.getName(), sourceClazz != null ? sourceClazz.getName() : "ANY", selInstance.getClass().getName()});
               }
            } catch (ClassNotFoundException var14) {
               throw new ConfigurationException(var14);
            }
         }
      }

   }

   private void processViewHandlers(ServletContext servletContext, FacesContext facesContext, Application application, LinkedHashMap viewHandlers) {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      if (!webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler) && !webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandlerDeprecated) && viewHandlers.containsKey("com.sun.facelets.FaceletViewHandler")) {
         LOGGER.log(Level.WARNING, "jsf.application.legacy_facelet_viewhandler_detected", "com.sun.facelets.FaceletViewHandler");
         webConfig.overrideContextInitParameter(WebConfiguration.BooleanWebContextInitParameter.DisableFaceletJSFViewHandler, true);
      }

      Iterator var6 = viewHandlers.values().iterator();

      while(var6.hasNext()) {
         Node viewHandlerNode = (Node)var6.next();
         this.setViewHandler(servletContext, facesContext, application, viewHandlerNode);
      }

   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
