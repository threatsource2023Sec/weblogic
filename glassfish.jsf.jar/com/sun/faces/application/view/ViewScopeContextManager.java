package com.sun.faces.application.view;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class ViewScopeContextManager {
   private static final Logger LOGGER;
   private boolean isCdiOneOneOrGreater;
   private Class viewScopedCDIEventFireHelperImplClass;
   private static final String ACTIVE_VIEW_CONTEXTS = "com.sun.faces.application.view.activeViewContexts";
   private static final String ACTIVE_VIEW_MAPS = "com.sun.faces.application.view.activeViewMaps";
   private final BeanManager beanManager;

   public ViewScopeContextManager() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      this.isCdiOneOneOrGreater = Util.isCdiOneOneOrLater(facesContext);

      try {
         this.viewScopedCDIEventFireHelperImplClass = Class.forName("com.sun.faces.application.view.ViewScopedCDIEventFireHelperImpl");
      } catch (ClassNotFoundException var3) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "CDI 1.1 events not enabled", var3);
         }
      }

      this.beanManager = Util.getCdiBeanManager(facesContext);
   }

   public void clear(FacesContext facesContext) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Clearing @ViewScoped CDI beans for current view map");
      }

      Map viewMap = facesContext.getViewRoot().getViewMap(false);
      Map contextMap = this.getContextMap(facesContext, false);
      if (contextMap != null) {
         this.destroyBeans(viewMap, contextMap);
      }

   }

   public void clear(FacesContext facesContext, Map viewMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Clearing @ViewScoped CDI beans for given view map: {0}");
      }

      Map contextMap = this.getContextMap(facesContext, viewMap);
      if (contextMap != null) {
         this.destroyBeans(viewMap, contextMap);
      }

   }

   public Object createBean(FacesContext facesContext, Contextual contextual, CreationalContext creational) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Creating @ViewScoped CDI bean using contextual: {0}", contextual);
      }

      if (!(contextual instanceof PassivationCapable)) {
         throw new IllegalArgumentException("ViewScoped bean " + contextual.toString() + " must be PassivationCapable, but is not.");
      } else {
         Object result = contextual.create(creational);
         if (result != null) {
            String name = this.getName(result);
            facesContext.getViewRoot().getViewMap(true).put(name, result);
            String passivationCapableId = ((PassivationCapable)contextual).getId();
            this.getContextMap(facesContext).put(passivationCapableId, new ViewScopeContextObject(passivationCapableId, name));
         }

         return result;
      }
   }

   private void destroyBeans(Map viewMap, Map contextMap) {
      ArrayList removalNameList = new ArrayList();
      if (contextMap != null) {
         Iterator removalNames;
         ViewScopeContextObject contextObject;
         for(removalNames = contextMap.entrySet().iterator(); removalNames.hasNext(); removalNameList.add(contextObject.getName())) {
            Map.Entry entry = (Map.Entry)removalNames.next();
            String passivationCapableId = (String)entry.getKey();
            Contextual contextual = this.beanManager.getPassivationCapableBean(passivationCapableId);
            contextObject = (ViewScopeContextObject)entry.getValue();
            CreationalContext creationalContext = this.beanManager.createCreationalContext(contextual);
            Object contextualInstance = viewMap.get(contextObject.getName());
            if (contextualInstance != null) {
               contextual.destroy(contextualInstance, creationalContext);
            }
         }

         removalNames = removalNameList.iterator();

         while(removalNames.hasNext()) {
            String name = (String)removalNames.next();
            viewMap.remove(name);
         }
      }

   }

   public Object getBean(FacesContext facesContext, Contextual contextual) {
      Object result = null;
      Map contextMap = this.getContextMap(facesContext);
      if (contextMap != null) {
         if (!(contextual instanceof PassivationCapable)) {
            throw new IllegalArgumentException("ViewScoped bean " + contextual.toString() + " must be PassivationCapable, but is not.");
         }

         ViewScopeContextObject contextObject = (ViewScopeContextObject)contextMap.get(((PassivationCapable)contextual).getId());
         if (contextObject != null) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Getting value for @ViewScoped bean with name: {0}", contextObject.getName());
            }

            result = facesContext.getViewRoot().getViewMap(true).get(contextObject.getName());
         }
      }

      return result;
   }

   private Map getContextMap(FacesContext facesContext) {
      return this.getContextMap(facesContext, true);
   }

   private Map getContextMap(FacesContext facesContext, boolean create) {
      Map result = null;
      ExternalContext externalContext = facesContext.getExternalContext();
      if (externalContext != null) {
         Map sessionMap = externalContext.getSessionMap();
         Object session = externalContext.getSession(create);
         if (session != null) {
            Map activeViewScopeContexts = (Map)sessionMap.get("com.sun.faces.application.view.activeViewContexts");
            Map viewMap = facesContext.getViewRoot().getViewMap(false);
            if (activeViewScopeContexts == null && create) {
               synchronized(session) {
                  activeViewScopeContexts = new ConcurrentHashMap();
                  sessionMap.put("com.sun.faces.application.view.activeViewContexts", activeViewScopeContexts);
               }
            }

            if (activeViewScopeContexts != null && create) {
               synchronized(activeViewScopeContexts) {
                  if (!((Map)activeViewScopeContexts).containsKey(System.identityHashCode(viewMap)) && create) {
                     this.copyViewScopeContextsFromSession((Map)activeViewScopeContexts, viewMap);
                     sessionMap.put("com.sun.faces.application.view.activeViewContexts", activeViewScopeContexts);
                  }
               }
            }

            if (activeViewScopeContexts != null) {
               result = (Map)((Map)activeViewScopeContexts).get(System.identityHashCode(viewMap));
            }
         }
      }

      return result;
   }

   private void copyViewScopeContextsFromSession(Map contexts, Map viewMap) {
      if (viewMap != null) {
         Set toReplace = new HashSet();
         Map resultMap = new ConcurrentHashMap();
         Iterator var5 = contexts.entrySet().iterator();

         while(true) {
            while(var5.hasNext()) {
               Map.Entry contextEntry = (Map.Entry)var5.next();
               Set beanNames = new HashSet();
               Iterator var8 = ((Map)contextEntry.getValue()).values().iterator();

               while(var8.hasNext()) {
                  ViewScopeContextObject viewObject = (ViewScopeContextObject)var8.next();
                  beanNames.add(viewObject.getName());
               }

               var8 = beanNames.iterator();

               while(var8.hasNext()) {
                  String name = (String)var8.next();
                  if (viewMap.keySet().contains(name)) {
                     toReplace.add(contextEntry.getKey());
                     break;
                  }
               }
            }

            var5 = toReplace.iterator();

            while(var5.hasNext()) {
               Object key = var5.next();
               Map contextObject = (Map)contexts.get(key);
               contexts.remove(key);
               resultMap.putAll(contextObject);
            }

            contexts.put(System.identityHashCode(viewMap), resultMap);
            return;
         }
      }
   }

   private Map getContextMap(FacesContext facesContext, Map viewMap) {
      Map result = null;
      ExternalContext externalContext = facesContext.getExternalContext();
      if (externalContext != null) {
         Map sessionMap = externalContext.getSessionMap();
         Map activeViewScopeContexts = (Map)sessionMap.get("com.sun.faces.application.view.activeViewContexts");
         if (activeViewScopeContexts != null) {
            result = (Map)activeViewScopeContexts.get(System.identityHashCode(viewMap));
         }
      }

      return result;
   }

   private String getName(Object instance) {
      String name = instance.getClass().getSimpleName().substring(0, 1).toLowerCase() + instance.getClass().getSimpleName().substring(1);
      Named named = (Named)instance.getClass().getAnnotation(Named.class);
      if (named != null && named.value() != null && !named.value().trim().equals("")) {
         name = named.value();
      }

      return name;
   }

   public void sessionDestroyed(HttpSessionEvent hse) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Cleaning up session for CDI @ViewScoped beans");
      }

      HttpSession session = hse.getSession();
      Map activeViewScopeContexts = (Map)session.getAttribute("com.sun.faces.application.view.activeViewContexts");
      if (activeViewScopeContexts != null) {
         Map activeViewMaps = (Map)session.getAttribute("com.sun.faces.application.view.activeViewMaps");
         if (activeViewMaps != null) {
            Iterator activeViewMapsIterator = activeViewMaps.values().iterator();

            while(activeViewMapsIterator.hasNext()) {
               Map instanceMap = (Map)activeViewMapsIterator.next();
               Map contextMap = (Map)activeViewScopeContexts.get(System.identityHashCode(instanceMap));
               this.destroyBeans(instanceMap, contextMap);
            }
         }

         activeViewScopeContexts.clear();
         session.removeAttribute("com.sun.faces.application.view.activeViewContexts");
      }

   }

   public void fireInitializedEvent(FacesContext facesContext, UIViewRoot root) {
      if (this.isCdiOneOneOrGreater && null != this.viewScopedCDIEventFireHelperImplClass) {
         BeanManager beanManager = Util.getCdiBeanManager(facesContext);
         if (null != beanManager) {
            Set availableBeans = beanManager.getBeans(this.viewScopedCDIEventFireHelperImplClass, new Annotation[0]);
            if (null != availableBeans && !availableBeans.isEmpty()) {
               Bean bean = beanManager.resolve(availableBeans);
               CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
               ViewScopedCDIEventFireHelper eventHelper = (ViewScopedCDIEventFireHelper)beanManager.getReference(bean, bean.getBeanClass(), creationalContext);
               eventHelper.fireInitializedEvent(root);
            }
         }
      }

   }

   public void fireDestroyedEvent(FacesContext facesContext, UIViewRoot root) {
      if (this.isCdiOneOneOrGreater && null != this.viewScopedCDIEventFireHelperImplClass) {
         BeanManager beanManager = Util.getCdiBeanManager(facesContext);
         if (null != beanManager) {
            Set availableBeans = beanManager.getBeans(this.viewScopedCDIEventFireHelperImplClass, new Annotation[0]);
            if (null != availableBeans && !availableBeans.isEmpty()) {
               Bean bean = beanManager.resolve(availableBeans);
               CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
               ViewScopedCDIEventFireHelper eventHelper = (ViewScopedCDIEventFireHelper)beanManager.getReference(bean, bean.getBeanClass(), creationalContext);
               eventHelper.fireDestroyedEvent(root);
            }
         }
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }
}
