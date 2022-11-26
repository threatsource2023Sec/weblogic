package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationResourceBundle;
import com.sun.faces.el.ChainAwareVariableResolver;
import com.sun.faces.el.DummyPropertyResolverImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ApplicationConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String APPLICATION = "application";
   private static final String ACTION_LISTENER = "action-listener";
   private static final String DEFAULT_RENDERKIT_ID = "default-render-kit-id";
   private static final String MESSAGE_BUNDLE = "message-bundle";
   private static final String NAVIGATION_HANDLER = "navigation-handler";
   private static final String VIEW_HANDLER = "view-handler";
   private static final String STATE_MANAGER = "state-manager";
   private static final String EL_RESOLVER = "el-resolver";
   private static final String PROPERTY_RESOLVER = "property-resolver";
   private static final String VARIABLE_RESOLVER = "variable-resolver";
   private static final String DEFAULT_LOCALE = "default-locale";
   private static final String SUPPORTED_LOCALE = "supported-locale";
   private static final String RESOURCE_BUNDLE = "resource-bundle";
   private static final String BASE_NAME = "base-name";
   private static final String VAR = "var";
   private static final String RES_DESCRIPTIONS = "description";
   private static final String RES_DISPLAY_NAMES = "display-name";

   public void process(ServletContext sc, Document[] documents) throws Exception {
      Application app = this.getApplication();
      ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);

      for(int i = 0; i < documents.length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing application elements for document: ''{0}''", documents[i].getDocumentURI()));
         }

         String namespace = documents[i].getDocumentElement().getNamespaceURI();
         NodeList applicationElements = documents[i].getDocumentElement().getElementsByTagNameNS(namespace, "application");
         if (applicationElements != null && applicationElements.getLength() > 0) {
            int a = 0;

            for(int asize = applicationElements.getLength(); a < asize; ++a) {
               Node appElement = applicationElements.item(a);
               NodeList children = ((Element)appElement).getElementsByTagNameNS(namespace, "*");
               if (children != null && children.getLength() > 0) {
                  int c = 0;

                  for(int csize = children.getLength(); c < csize; ++c) {
                     Node n = children.item(c);
                     if ("message-bundle".equals(n.getLocalName())) {
                        this.setMessageBundle(app, n);
                     } else if ("default-render-kit-id".equals(n.getLocalName())) {
                        this.setDefaultRenderKitId(app, n);
                     } else if ("action-listener".equals(n.getLocalName())) {
                        this.addActionListener(app, n);
                     } else if ("navigation-handler".equals(n.getLocalName())) {
                        this.setNavigationHandler(app, n);
                     } else if ("view-handler".equals(n.getLocalName())) {
                        this.setViewHandler(app, n);
                     } else if ("state-manager".equals(n.getLocalName())) {
                        this.setStateManager(app, n);
                     } else if ("el-resolver".equals(n.getLocalName())) {
                        this.addELResolver(associate, n);
                     } else if ("property-resolver".equals(n.getLocalName())) {
                        this.addPropertyResolver(associate, n);
                     } else if ("variable-resolver".equals(n.getLocalName())) {
                        this.addVariableResolver(associate, n);
                     } else if ("default-locale".equals(n.getLocalName())) {
                        this.setDefaultLocale(app, n);
                     } else if ("supported-locale".equals(n.getLocalName())) {
                        this.addSupportedLocale(app, n);
                     } else if ("resource-bundle".equals(n.getLocalName())) {
                        this.addResouceBundle(associate, n);
                     }
                  }
               }
            }
         }
      }

      this.invokeNext(sc, documents);
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

   private void addActionListener(Application application, Node actionListener) {
      if (actionListener != null) {
         String listener = this.getNodeText(actionListener);
         if (listener != null) {
            Object instance = this.createInstance(listener, ActionListener.class, application.getActionListener(), actionListener);
            if (instance != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setActionListeners({0})", listener));
               }

               application.setActionListener((ActionListener)instance);
            }
         }
      }

   }

   private void setNavigationHandler(Application application, Node navigationHandler) {
      if (navigationHandler != null) {
         String handler = this.getNodeText(navigationHandler);
         if (handler != null) {
            Object instance = this.createInstance(handler, NavigationHandler.class, application.getNavigationHandler(), navigationHandler);
            if (instance != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setNavigationHandlers({0})", handler));
               }

               application.setNavigationHandler((NavigationHandler)instance);
            }
         }
      }

   }

   private void setStateManager(Application application, Node stateManager) {
      if (stateManager != null) {
         String manager = this.getNodeText(stateManager);
         if (manager != null) {
            Object instance = this.createInstance(manager, StateManager.class, application.getStateManager(), stateManager);
            if (instance != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setStateManagers({0})", manager));
               }

               application.setStateManager((StateManager)instance);
            }
         }
      }

   }

   private void setViewHandler(Application application, Node viewHandler) {
      if (viewHandler != null) {
         String handler = this.getNodeText(viewHandler);
         if (handler != null) {
            Object instance = this.createInstance(handler, ViewHandler.class, application.getViewHandler(), viewHandler);
            if (instance != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Calling Application.setViewHandlers({0})", handler));
               }

               application.setViewHandler((ViewHandler)instance);
            }
         }
      }

   }

   private void addELResolver(ApplicationAssociate associate, Node elResolver) {
      if (elResolver != null && associate != null) {
         List resolvers = associate.getELResolversFromFacesConfig();
         if (resolvers == null) {
            resolvers = new ArrayList();
            associate.setELResolversFromFacesConfig((List)resolvers);
         }

         String elResolverClass = this.getNodeText(elResolver);
         if (elResolverClass != null) {
            Object elRes = this.createInstance(elResolverClass, ELResolver.class, (Object)null, elResolver);
            if (elRes != null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to ELResolver chain", elResolverClass));
               }

               ((List)resolvers).add((ELResolver)elRes);
            }
         }
      }

   }

   private void addPropertyResolver(ApplicationAssociate associate, Node propertyResolver) {
      if (propertyResolver != null && associate != null) {
         Object resolverImpl = associate.getLegacyPRChainHead();
         if (resolverImpl == null) {
            resolverImpl = new DummyPropertyResolverImpl();
         }

         String resolver = this.getNodeText(propertyResolver);
         if (resolver != null) {
            resolverImpl = this.createInstance(resolver, PropertyResolver.class, resolverImpl, propertyResolver);
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, MessageFormat.format("Adding ''{0}'' to PropertyResolver chain", resolverImpl));
            }
         }

         if (resolverImpl != null) {
            associate.setLegacyPRChainHead((PropertyResolver)resolverImpl);
         }
      }

   }

   private void addVariableResolver(ApplicationAssociate associate, Node variableResolver) {
      if (variableResolver != null && associate != null) {
         Object resolverImpl = associate.getLegacyVRChainHead();
         if (resolverImpl == null) {
            resolverImpl = new ChainAwareVariableResolver();
         }

         String resolver = this.getNodeText(variableResolver);
         if (resolver != null) {
            resolverImpl = this.createInstance(resolver, VariableResolver.class, resolverImpl, variableResolver);
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
                  if ("base-name".equals(n.getLocalName())) {
                     baseName = this.getNodeText(n);
                  } else if ("var".equals(n.getLocalName())) {
                     var = this.getNodeText(n);
                  } else if ("description".equals(n.getLocalName())) {
                     if (descriptions == null) {
                        descriptions = new ArrayList(2);
                     }

                     descriptions.add(n);
                  } else if ("display-name".equals(n.getLocalName())) {
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

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
