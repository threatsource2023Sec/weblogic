package com.sun.faces.application.view;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.util.LRUMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ViewScopeManager implements HttpSessionListener, ViewMapListener {
   private static final Logger LOGGER = Logger.getLogger(ViewScopeManager.class.getName());
   public static final String ACTIVE_VIEW_MAPS = "com.sun.faces.application.view.activeViewMaps";
   public static final String ACTIVE_VIEW_MAPS_SIZE = "com.sun.faces.application.view.activeViewMapsSize";
   public static final String VIEW_MAP = "com.sun.faces.application.view.viewMap";
   public static final String VIEW_MAP_ID = "com.sun.faces.application.view.viewMapId";
   public static final String VIEW_SCOPE_MANAGER = "com.sun.faces.application.view.viewScopeManager";
   private ViewScopeContextManager contextManager;
   private boolean distributable;

   public ViewScopeManager() {
      FacesContext context = FacesContext.getCurrentInstance();

      try {
         this.contextManager = new ViewScopeContextManager();
      } catch (Throwable var3) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "CDI @ViewScoped bean functionality unavailable");
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "CDI @ViewScoped manager unavailable", var3);
         }
      }

      WebConfiguration config = WebConfiguration.getInstance(context.getExternalContext());
      this.distributable = config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableDistributable);
   }

   public void clear(FacesContext facesContext) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Clearing @ViewScoped beans from current view map");
      }

      if (this.contextManager != null) {
         this.contextManager.clear(facesContext);
      }

      this.destroyBeans(facesContext, facesContext.getViewRoot().getViewMap(false));
   }

   public void clear(FacesContext facesContext, Map viewMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Clearing @ViewScoped beans from view map: {0}", viewMap);
      }

      if (this.contextManager != null) {
         this.contextManager.clear(facesContext, viewMap);
      }

      this.destroyBeans(facesContext, viewMap);
   }

   private void destroyBeans(ApplicationAssociate applicationAssociate, Map viewMap) {
      Iterator var3 = viewMap.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         String name = (String)entry.getKey();
         Object bean = entry.getValue();

         try {
            if (applicationAssociate != null) {
               BeanManager beanManager = applicationAssociate.getBeanManager();
               if (beanManager != null && beanManager.isManaged(name)) {
                  beanManager.destroy(name, bean);
               }
            }
         } catch (Exception var8) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Error calling @PreDestroy on bean with name: " + name, var8);
            }
         }
      }

   }

   public void destroyBeans(FacesContext facesContext, Map viewMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Destroying @ViewScoped beans from view map: {0}", viewMap);
      }

      ApplicationAssociate applicationAssociate = ApplicationAssociate.getInstance(facesContext.getExternalContext());
      if (applicationAssociate != null) {
         this.destroyBeans(applicationAssociate, viewMap);
      }

   }

   ViewScopeContextManager getContextManager() {
      return this.contextManager;
   }

   public static ViewScopeManager getInstance(FacesContext facesContext) {
      if (!facesContext.getExternalContext().getApplicationMap().containsKey("com.sun.faces.application.view.viewScopeManager")) {
         facesContext.getExternalContext().getApplicationMap().put("com.sun.faces.application.view.viewScopeManager", new ViewScopeManager());
      }

      return (ViewScopeManager)facesContext.getExternalContext().getApplicationMap().get("com.sun.faces.application.view.viewScopeManager");
   }

   public boolean isListenerForSource(Object source) {
      return source instanceof UIViewRoot;
   }

   public void processEvent(SystemEvent se) throws AbortProcessingException {
      if (se instanceof PreDestroyViewMapEvent) {
         this.processPreDestroyViewMap(se);
      }

      if (se instanceof PostConstructViewMapEvent) {
         this.processPostConstructViewMap(se);
      }

   }

   private void processPostConstructViewMap(SystemEvent se) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Handling PostConstructViewMapEvent");
      }

      UIViewRoot viewRoot = (UIViewRoot)se.getSource();
      Map viewMap = viewRoot.getViewMap(false);
      if (viewMap != null) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         if (viewRoot.isTransient() && facesContext.isProjectStage(ProjectStage.Development)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "@ViewScoped beans are not supported on stateless views", "@ViewScoped beans are not supported on stateless views");
            facesContext.addMessage(viewRoot.getClientId(facesContext), message);
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "@ViewScoped beans are not supported on stateless views");
            }
         }

         Object session = facesContext.getExternalContext().getSession(true);
         if (session != null) {
            Map sessionMap = facesContext.getExternalContext().getSessionMap();
            Integer size = (Integer)sessionMap.get("com.sun.faces.application.view.activeViewMapsSize");
            if (size == null) {
               size = 25;
            }

            if (sessionMap.get("com.sun.faces.application.view.activeViewMaps") == null) {
               sessionMap.put("com.sun.faces.application.view.activeViewMaps", Collections.synchronizedMap(new LRUMap(size)));
            }

            Map viewMaps = (Map)sessionMap.get("com.sun.faces.application.view.activeViewMaps");
            synchronized(viewMaps) {
               String viewMapId = UUID.randomUUID().toString();

               while(true) {
                  if (!viewMaps.containsKey(viewMapId)) {
                     if (viewMaps.size() == size) {
                        String eldestViewMapId = (String)viewMaps.keySet().iterator().next();
                        Map eldestViewMap = (Map)viewMaps.remove(eldestViewMapId);
                        this.removeEldestViewMap(facesContext, eldestViewMap);
                     }

                     viewMaps.put(viewMapId, viewMap);
                     viewRoot.getTransientStateHelper().putTransient("com.sun.faces.application.view.viewMapId", viewMapId);
                     viewRoot.getTransientStateHelper().putTransient("com.sun.faces.application.view.viewMap", viewMap);
                     if (this.distributable) {
                        sessionMap.put("com.sun.faces.application.view.activeViewMaps", viewMaps);
                     }
                     break;
                  }

                  viewMapId = UUID.randomUUID().toString();
               }
            }

            if (null != this.contextManager) {
               this.contextManager.fireInitializedEvent(facesContext, viewRoot);
            }
         }
      }

   }

   private void processPreDestroyViewMap(SystemEvent se) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Handling PreDestroyViewMapEvent");
      }

      UIViewRoot viewRoot = (UIViewRoot)se.getSource();
      Map viewMap = viewRoot.getViewMap(false);
      if (viewMap != null && !viewMap.isEmpty()) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         if (this.contextManager != null) {
            this.contextManager.clear(facesContext, viewMap);
            this.contextManager.fireDestroyedEvent(facesContext, viewRoot);
         }

         this.destroyBeans(facesContext, viewMap);
      }

   }

   public void sessionCreated(HttpSessionEvent se) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Creating session for @ViewScoped beans");
      }

   }

   public void sessionDestroyed(HttpSessionEvent hse) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Cleaning up session for @ViewScoped beans");
      }

      if (this.contextManager != null) {
         this.contextManager.sessionDestroyed(hse);
      }

      HttpSession session = hse.getSession();
      Map activeViewMaps = (Map)session.getAttribute("com.sun.faces.application.view.activeViewMaps");
      if (activeViewMaps != null) {
         Iterator activeViewMapsIterator = activeViewMaps.values().iterator();
         ApplicationAssociate applicationAssociate = ApplicationAssociate.getInstance(hse.getSession().getServletContext());

         while(activeViewMapsIterator.hasNext()) {
            Map viewMap = (Map)activeViewMapsIterator.next();
            this.destroyBeans(applicationAssociate, viewMap);
         }

         activeViewMaps.clear();
         session.removeAttribute("com.sun.faces.application.view.activeViewMaps");
         session.removeAttribute("com.sun.faces.application.view.activeViewMapsSize");
      }

   }

   private void removeEldestViewMap(FacesContext facesContext, Map eldestViewMap) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "Removing eldest view map: {0}", eldestViewMap);
      }

      if (this.contextManager != null) {
         this.contextManager.clear(facesContext, eldestViewMap);
      }

      this.destroyBeans(facesContext, eldestViewMap);
   }
}
