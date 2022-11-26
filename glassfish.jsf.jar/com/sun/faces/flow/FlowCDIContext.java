package com.sun.faces.flow;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowHandler;
import javax.faces.flow.FlowScoped;
import javax.faces.lifecycle.ClientWindow;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class FlowCDIContext implements Context, Serializable {
   private static final long serialVersionUID = -7144653402477623609L;
   private static final String FLOW_SCOPE_MAP_KEY = "com.sun.faces.FLOW_SCOPE_MAP";
   private static final Logger LOGGER;
   private transient Map flowIds;
   private static final String PER_SESSION_BEAN_MAP_LIST;
   private static final String PER_SESSION_CREATIONAL_LIST;

   FlowCDIContext(Map flowIds) {
      this.flowIds = new ConcurrentHashMap(flowIds);
   }

   private static void ensureBeanMapCleanupOnSessionDestroyed(Map sessionMap, String flowBeansForClientWindow) {
      List beanMapList = (List)sessionMap.get(PER_SESSION_BEAN_MAP_LIST);
      if (null == beanMapList) {
         beanMapList = new ArrayList();
         sessionMap.put(PER_SESSION_BEAN_MAP_LIST, beanMapList);
      }

      ((List)beanMapList).add(flowBeansForClientWindow);
   }

   private static void ensureCreationalCleanupOnSessionDestroyed(Map sessionMap, String creationalForClientWindow) {
      List beanMapList = (List)sessionMap.get(PER_SESSION_CREATIONAL_LIST);
      if (null == beanMapList) {
         beanMapList = new ArrayList();
         sessionMap.put(PER_SESSION_CREATIONAL_LIST, beanMapList);
      }

      ((List)beanMapList).add(creationalForClientWindow);
   }

   private final void assertNotReleased() {
      if (!this.isActive()) {
         throw new IllegalStateException();
      }
   }

   private Flow getCurrentFlow() {
      Flow result = null;
      FacesContext context = FacesContext.getCurrentInstance();
      result = getCurrentFlow(context);
      return result;
   }

   private static Flow getCurrentFlow(FacesContext context) {
      FlowHandler flowHandler = context.getApplication().getFlowHandler();
      if (null == flowHandler) {
         return null;
      } else {
         Flow result = flowHandler.getCurrentFlow(context);
         return result;
      }
   }

   public static void sessionDestroyed(HttpSessionEvent hse) {
      HttpSession session = hse.getSession();
      List beanMapList = (List)session.getAttribute(PER_SESSION_BEAN_MAP_LIST);
      if (null != beanMapList) {
         Iterator var3 = beanMapList.iterator();

         while(var3.hasNext()) {
            String cur = (String)var3.next();
            Map beanMap = (Map)session.getAttribute(cur);
            beanMap.clear();
            session.removeAttribute(cur);
         }

         session.removeAttribute(PER_SESSION_BEAN_MAP_LIST);
         beanMapList.clear();
      }

      List creationalList = (List)session.getAttribute(PER_SESSION_CREATIONAL_LIST);
      if (null != creationalList) {
         Iterator var9 = creationalList.iterator();

         while(var9.hasNext()) {
            String cur = (String)var9.next();
            Map beanMap = (Map)session.getAttribute(cur);
            beanMap.clear();
            session.removeAttribute(cur);
         }

         session.removeAttribute(PER_SESSION_CREATIONAL_LIST);
         creationalList.clear();
      }

   }

   static Map getCurrentFlowScopeAndUpdateSession() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      FlowScopeMapHelper mapHelper = new FlowScopeMapHelper(facesContext);
      return getCurrentFlowScopeAndUpdateSession(mapHelper);
   }

   private static Map getCurrentFlowScopeAndUpdateSession(FlowScopeMapHelper mapHelper) {
      Map flowScopedBeanMap = mapHelper.getFlowScopedBeanMapForCurrentFlow();
      Map result = null;
      if (mapHelper.isFlowExists()) {
         result = (Map)flowScopedBeanMap.get("com.sun.faces.FLOW_SCOPE_MAP");
         if (null == result) {
            result = new ConcurrentHashMap();
            flowScopedBeanMap.put("com.sun.faces.FLOW_SCOPE_MAP", result);
         }
      }

      mapHelper.updateSession();
      return (Map)result;
   }

   static void flowExited(Flow currentFlow, int depth) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      FlowScopeMapHelper mapHelper = new FlowScopeMapHelper(facesContext, currentFlow, depth);
      Map flowScopedBeanMap = mapHelper.getFlowScopedBeanMapForCurrentFlow();
      Map creationalMap = mapHelper.getFlowScopedCreationalMapForCurrentFlow();

      assert !flowScopedBeanMap.isEmpty();

      assert !creationalMap.isEmpty();

      BeanManager beanManager = Util.getCdiBeanManager(facesContext);
      Iterator var7 = flowScopedBeanMap.entrySet().iterator();

      while(var7.hasNext()) {
         Map.Entry entry = (Map.Entry)var7.next();
         String passivationCapableId = (String)entry.getKey();
         if (!"com.sun.faces.FLOW_SCOPE_MAP".equals(passivationCapableId)) {
            Contextual owner = beanManager.getPassivationCapableBean(passivationCapableId);
            Object bean = entry.getValue();
            CreationalContext creational = (CreationalContext)creationalMap.get(passivationCapableId);
            owner.destroy(bean, creational);
         }
      }

      flowScopedBeanMap.clear();
      creationalMap.clear();
      mapHelper.updateSession();
      if (Util.isCdiOneOneOrLater(facesContext)) {
         Class flowCDIEventFireHelperImplClass = null;

         try {
            flowCDIEventFireHelperImplClass = Class.forName("com.sun.faces.flow.FlowCDIEventFireHelperImpl");
         } catch (ClassNotFoundException var13) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "CDI 1.1 events not enabled", var13);
            }
         }

         if (null != flowCDIEventFireHelperImplClass) {
            Set availableBeans = beanManager.getBeans(flowCDIEventFireHelperImplClass, new Annotation[0]);
            if (null != availableBeans && !availableBeans.isEmpty()) {
               Bean bean = beanManager.resolve(availableBeans);
               CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
               FlowCDIEventFireHelper eventHelper = (FlowCDIEventFireHelper)beanManager.getReference(bean, bean.getBeanClass(), creationalContext);
               eventHelper.fireDestroyedEvent(currentFlow);
            }
         }
      }

   }

   static void flowEntered() {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      FlowScopeMapHelper mapHelper = new FlowScopeMapHelper(facesContext);
      mapHelper.createMaps();
      getCurrentFlowScopeAndUpdateSession(mapHelper);
      if (Util.isCdiOneOneOrLater(facesContext)) {
         Class flowCDIEventFireHelperImplClass = null;

         try {
            flowCDIEventFireHelperImplClass = Class.forName("com.sun.faces.flow.FlowCDIEventFireHelperImpl");
         } catch (ClassNotFoundException var8) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "CDI 1.1 events not enabled", var8);
            }
         }

         if (null != flowCDIEventFireHelperImplClass) {
            BeanManager beanManager = Util.getCdiBeanManager(facesContext);
            Set availableBeans = beanManager.getBeans(flowCDIEventFireHelperImplClass, new Annotation[0]);
            if (null != availableBeans && !availableBeans.isEmpty()) {
               Bean bean = beanManager.resolve(availableBeans);
               CreationalContext creationalContext = beanManager.createCreationalContext((Contextual)null);
               FlowCDIEventFireHelper eventHelper = (FlowCDIEventFireHelper)beanManager.getReference(bean, bean.getBeanClass(), creationalContext);
               eventHelper.fireInitializedEvent(getCurrentFlow(facesContext));
            }
         }
      }

   }

   public Object get(Contextual contextual, CreationalContext creational) {
      this.assertNotReleased();
      FacesContext facesContext = FacesContext.getCurrentInstance();
      FlowScopeMapHelper mapHelper = new FlowScopeMapHelper(facesContext);
      Object result = this.get(mapHelper, contextual);
      if (null == result) {
         Map flowScopedBeanMap = mapHelper.getFlowScopedBeanMapForCurrentFlow();
         Map creationalMap = mapHelper.getFlowScopedCreationalMapForCurrentFlow();
         String passivationCapableId = ((PassivationCapable)contextual).getId();
         synchronized(flowScopedBeanMap) {
            result = flowScopedBeanMap.get(passivationCapableId);
            if (null == result) {
               FlowHandler flowHandler = facesContext.getApplication().getFlowHandler();
               if (null == flowHandler) {
                  return null;
               }

               FlowBeanInfo fbi = (FlowBeanInfo)this.flowIds.get(contextual);
               if (fbi != null && !flowHandler.isActive(facesContext, fbi.definingDocumentId, fbi.id)) {
                  throw new ContextNotActiveException("Request to activate bean in flow '" + fbi + "', but that flow is not active.");
               }

               result = contextual.create(creational);
               if (null != result) {
                  flowScopedBeanMap.put(passivationCapableId, result);
                  creationalMap.put(passivationCapableId, creational);
                  mapHelper.updateSession();
               }
            }
         }
      }

      mapHelper = null;
      return result;
   }

   public Object get(Contextual contextual) {
      this.assertNotReleased();
      if (!(contextual instanceof PassivationCapable)) {
         throw new IllegalArgumentException("FlowScoped bean " + contextual.toString() + " must be PassivationCapable, but is not.");
      } else {
         FlowScopeMapHelper mapHelper = new FlowScopeMapHelper(FacesContext.getCurrentInstance());
         Object result = this.get(mapHelper, contextual);
         mapHelper = null;
         return result;
      }
   }

   private Object get(FlowScopeMapHelper mapHelper, Contextual contextual) {
      this.assertNotReleased();
      if (!(contextual instanceof PassivationCapable)) {
         throw new IllegalArgumentException("FlowScoped bean " + contextual.toString() + " must be PassivationCapable, but is not.");
      } else {
         String passivationCapableId = ((PassivationCapable)contextual).getId();
         return mapHelper.getFlowScopedBeanMapForCurrentFlow().get(passivationCapableId);
      }
   }

   public Class getScope() {
      return FlowScoped.class;
   }

   public boolean isActive() {
      return null != this.getCurrentFlow();
   }

   void beforeShutdown(@Observes BeforeShutdown event, BeanManager beanManager) {
   }

   static {
      LOGGER = FacesLogger.FLOW.getLogger();
      PER_SESSION_BEAN_MAP_LIST = FlowCDIContext.class.getPackage().getName() + ".PER_SESSION_BEAN_MAP_LIST";
      PER_SESSION_CREATIONAL_LIST = FlowCDIContext.class.getPackage().getName() + ".PER_SESSION_CREATIONAL_LIST";
   }

   private static class FlowScopeMapHelper {
      private transient String flowBeansForClientWindowKey;
      private transient String creationalForClientWindowKey;
      private final transient Map sessionMap;

      private FlowScopeMapHelper(FacesContext facesContext) {
         ExternalContext extContext = facesContext.getExternalContext();
         this.sessionMap = extContext.getSessionMap();
         Flow currentFlow = FlowCDIContext.getCurrentFlow(facesContext);
         int currentFlowDepth = FlowHandlerImpl.getFlowStack(facesContext).getCurrentFlowDepth();
         this.generateKeyForCDIBeansBelongToAFlow(facesContext, currentFlow, currentFlowDepth);
      }

      private FlowScopeMapHelper(FacesContext facesContext, Flow flow, int flowDepth) {
         ExternalContext extContext = facesContext.getExternalContext();
         this.sessionMap = extContext.getSessionMap();
         this.generateKeyForCDIBeansBelongToAFlow(facesContext, flow, flowDepth);
      }

      private void generateKeyForCDIBeansBelongToAFlow(FacesContext facesContext, Flow flow, int flowDepth) {
         if (null != flow) {
            ClientWindow curWindow = facesContext.getExternalContext().getClientWindow();
            if (null == curWindow) {
               throw new IllegalStateException("Unable to obtain current ClientWindow.  Is the ClientWindow feature enabled?");
            }

            String clientWindow = flow.getClientWindowFlowId(curWindow);
            this.flowBeansForClientWindowKey = clientWindow + ":" + flowDepth + "_beans";
            this.creationalForClientWindowKey = clientWindow + ":" + flowDepth + "_creational";
         } else {
            this.flowBeansForClientWindowKey = this.creationalForClientWindowKey = null;
         }

      }

      private void createMaps() {
         this.getFlowScopedBeanMapForCurrentFlow();
         this.getFlowScopedCreationalMapForCurrentFlow();
      }

      private boolean isFlowExists() {
         return null != this.flowBeansForClientWindowKey && null != this.creationalForClientWindowKey;
      }

      public String getCreationalForClientWindowKey() {
         return this.creationalForClientWindowKey;
      }

      public String getFlowBeansForClientWindowKey() {
         return this.flowBeansForClientWindowKey;
      }

      private Map getFlowScopedBeanMapForCurrentFlow() {
         if (null == this.flowBeansForClientWindowKey && null == this.creationalForClientWindowKey) {
            return Collections.emptyMap();
         } else {
            Map result = (Map)this.sessionMap.get(this.flowBeansForClientWindowKey);
            if (null == result) {
               result = new ConcurrentHashMap();
               this.sessionMap.put(this.flowBeansForClientWindowKey, result);
               FlowCDIContext.ensureBeanMapCleanupOnSessionDestroyed(this.sessionMap, this.flowBeansForClientWindowKey);
            }

            return (Map)result;
         }
      }

      private Map getFlowScopedCreationalMapForCurrentFlow() {
         if (null == this.flowBeansForClientWindowKey && null == this.creationalForClientWindowKey) {
            return Collections.emptyMap();
         } else {
            Map result = (Map)this.sessionMap.get(this.creationalForClientWindowKey);
            if (null == result) {
               result = new ConcurrentHashMap();
               this.sessionMap.put(this.creationalForClientWindowKey, result);
               FlowCDIContext.ensureCreationalCleanupOnSessionDestroyed(this.sessionMap, this.creationalForClientWindowKey);
            }

            return (Map)result;
         }
      }

      private void updateSession() {
         if (null != this.flowBeansForClientWindowKey || null != this.creationalForClientWindowKey) {
            this.sessionMap.put(this.flowBeansForClientWindowKey, this.getFlowScopedBeanMapForCurrentFlow());
            this.sessionMap.put(this.creationalForClientWindowKey, this.getFlowScopedCreationalMapForCurrentFlow());
            Object obj = this.sessionMap.get(FlowCDIContext.PER_SESSION_BEAN_MAP_LIST);
            if (null != obj) {
               this.sessionMap.put(FlowCDIContext.PER_SESSION_BEAN_MAP_LIST, obj);
            }

            obj = this.sessionMap.get(FlowCDIContext.PER_SESSION_CREATIONAL_LIST);
            if (null != obj) {
               this.sessionMap.put(FlowCDIContext.PER_SESSION_CREATIONAL_LIST, obj);
            }

         }
      }

      // $FF: synthetic method
      FlowScopeMapHelper(FacesContext x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      FlowScopeMapHelper(FacesContext x0, Flow x1, int x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   static class FlowBeanInfo {
      String definingDocumentId;
      String id;

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            FlowBeanInfo other = (FlowBeanInfo)obj;
            if (this.definingDocumentId == null) {
               if (other.definingDocumentId != null) {
                  return false;
               }
            } else if (!this.definingDocumentId.equals(other.definingDocumentId)) {
               return false;
            }

            if (this.id == null) {
               if (other.id != null) {
                  return false;
               }
            } else if (!this.id.equals(other.id)) {
               return false;
            }

            return true;
         }
      }

      public int hashCode() {
         int hash = 7;
         hash = 79 * hash + (this.definingDocumentId != null ? this.definingDocumentId.hashCode() : 0);
         hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
         return hash;
      }

      public String toString() {
         return "FlowBeanInfo{definingDocumentId=" + this.definingDocumentId + ", id=" + this.id + '}';
      }
   }
}
