package javax.faces.component;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceHandler;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewMetadata;

public class UIViewRoot extends UIComponentBase implements UniqueIdVendor {
   public static final String METADATA_FACET_NAME = "javax_faces_metadata";
   public static final String VIEW_PARAMETERS_KEY = "javax.faces.component.VIEW_PARAMETERS_KEY";
   public static final String COMPONENT_TYPE = "javax.faces.ViewRoot";
   public static final String COMPONENT_FAMILY = "javax.faces.ViewRoot";
   public static final String VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS_PARAM_NAME = "javax.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS";
   public static final String UNIQUE_ID_PREFIX = "j_id";
   private static Lifecycle lifecycle;
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");
   private static final String LOCATION_IDENTIFIER_PREFIX = "javax_faces_location_";
   private static final Map LOCATION_IDENTIFIER_MAP = new LinkedHashMap(3, 1.0F);
   private boolean skipPhase;
   private boolean beforeMethodException;
   private ListIterator phaseListenerIterator;
   private List events = null;
   Map viewListeners;
   private Object[] values;

   public UIViewRoot() {
      this.setRendererType((String)null);
      this.setId(this.createUniqueId());
   }

   public boolean isInView() {
      return true;
   }

   public void setInView(boolean isInView) {
   }

   public String getFamily() {
      return "javax.faces.ViewRoot";
   }

   public String getRenderKitId() {
      return (String)this.getStateHelper().eval(UIViewRoot.PropertyKeys.renderKitId);
   }

   public void setRenderKitId(String renderKitId) {
      this.getStateHelper().put(UIViewRoot.PropertyKeys.renderKitId, renderKitId);
   }

   public String getViewId() {
      return (String)this.getStateHelper().get(UIViewRoot.PropertyKeys.viewId);
   }

   public void setViewId(String viewId) {
      this.getStateHelper().put(UIViewRoot.PropertyKeys.viewId, viewId);
   }

   public MethodExpression getBeforePhaseListener() {
      return (MethodExpression)this.getStateHelper().get(UIViewRoot.PropertyKeys.beforePhase);
   }

   public void setBeforePhaseListener(MethodExpression newBeforePhase) {
      this.getStateHelper().put(UIViewRoot.PropertyKeys.beforePhase, newBeforePhase);
   }

   public MethodExpression getAfterPhaseListener() {
      return (MethodExpression)this.getStateHelper().get(UIViewRoot.PropertyKeys.afterPhase);
   }

   public void setAfterPhaseListener(MethodExpression newAfterPhase) {
      this.getStateHelper().put(UIViewRoot.PropertyKeys.afterPhase, newAfterPhase);
   }

   public void removePhaseListener(PhaseListener toRemove) {
      this.getStateHelper().remove(UIViewRoot.PropertyKeys.phaseListeners, toRemove);
   }

   public void addPhaseListener(PhaseListener newPhaseListener) {
      this.getStateHelper().add(UIViewRoot.PropertyKeys.phaseListeners, newPhaseListener);
   }

   public List getPhaseListeners() {
      List result = (List)this.getStateHelper().get(UIViewRoot.PropertyKeys.phaseListeners);
      return result != null ? Collections.unmodifiableList(result) : Collections.unmodifiableList(Collections.emptyList());
   }

   public void addComponentResource(FacesContext context, UIComponent componentResource) {
      this.addComponentResource(context, componentResource, (String)null);
   }

   public void addComponentResource(FacesContext context, UIComponent componentResource, String target) {
      Map attributes = componentResource.getAttributes();
      if (target == null) {
         target = (String)attributes.get("target");
      }

      if (target == null) {
         target = "head";
      }

      List facetChildren = this.getComponentResources(context, target, true);
      String id = componentResource.getId();
      if (id != null) {
         Iterator var7 = facetChildren.iterator();

         while(var7.hasNext()) {
            UIComponent c = (UIComponent)var7.next();
            if (id.equals(c.getId())) {
               facetChildren.remove(c);
            }
         }
      }

      facetChildren.add(componentResource);
   }

   public List getComponentResources(FacesContext context, String target) {
      if (target == null) {
         throw new NullPointerException();
      } else {
         List resources = this.getComponentResources(context, target, false);
         return resources != null ? resources : Collections.emptyList();
      }
   }

   public List getComponentResources(FacesContext context) {
      List resources = new ArrayList();
      Iterator var3 = LOCATION_IDENTIFIER_MAP.keySet().iterator();

      while(var3.hasNext()) {
         String target = (String)var3.next();
         resources.addAll(this.getComponentResources(context, target));
      }

      return Collections.unmodifiableList(resources);
   }

   public void removeComponentResource(FacesContext context, UIComponent componentResource) {
      this.removeComponentResource(context, componentResource, (String)null);
   }

   public void removeComponentResource(FacesContext context, UIComponent componentResource, String target) {
      Map attributes = componentResource.getAttributes();
      if (target == null) {
         target = (String)attributes.get("target");
      }

      if (target == null) {
         target = "head";
      }

      List facetChildren = this.getComponentResources(context, target, false);
      if (facetChildren != null) {
         facetChildren.remove(componentResource);
      }

   }

   public void queueEvent(FacesEvent event) {
      if (event == null) {
         throw new NullPointerException();
      } else {
         if (this.events == null) {
            int len = PhaseId.VALUES.size();
            List events = new ArrayList(len);

            for(int i = 0; i < len; ++i) {
               events.add(new ArrayList(5));
            }

            this.events = events;
         }

         ((List)this.events.get(event.getPhaseId().getOrdinal())).add(event);
      }
   }

   public void broadcastEvents(FacesContext context, PhaseId phaseId) {
      if (null != this.events) {
         List eventsForPhaseId = (List)this.events.get(PhaseId.ANY_PHASE.getOrdinal());

         boolean hasMoreAnyPhaseEvents;
         boolean hasMoreCurrentPhaseEvents;
         do {
            FacesEvent event;
            UIComponent source;
            UIComponent compositeParent;
            if (null != eventsForPhaseId) {
               for(; !eventsForPhaseId.isEmpty(); eventsForPhaseId.remove(0)) {
                  event = (FacesEvent)eventsForPhaseId.get(0);
                  source = event.getComponent();
                  compositeParent = null;

                  try {
                     if (!UIComponent.isCompositeComponent(source)) {
                        compositeParent = UIComponent.getCompositeComponentParent(source);
                     }

                     if (compositeParent != null) {
                        compositeParent.pushComponentToEL(context, (UIComponent)null);
                     }

                     source.pushComponentToEL(context, (UIComponent)null);
                     source.broadcast(event);
                  } catch (AbortProcessingException var20) {
                     context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, new ExceptionQueuedEventContext(context, var20, source, phaseId));
                  } finally {
                     source.popComponentFromEL(context);
                     if (compositeParent != null) {
                        compositeParent.popComponentFromEL(context);
                     }

                  }
               }
            }

            if (null != (eventsForPhaseId = (List)this.events.get(phaseId.getOrdinal()))) {
               for(; !eventsForPhaseId.isEmpty(); eventsForPhaseId.remove(0)) {
                  event = (FacesEvent)eventsForPhaseId.get(0);
                  source = event.getComponent();
                  compositeParent = null;

                  try {
                     if (!UIComponent.isCompositeComponent(source)) {
                        compositeParent = getCompositeComponentParent(source);
                     }

                     if (compositeParent != null) {
                        compositeParent.pushComponentToEL(context, (UIComponent)null);
                     }

                     source.pushComponentToEL(context, (UIComponent)null);
                     source.broadcast(event);
                  } catch (AbortProcessingException var21) {
                     context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, new ExceptionQueuedEventContext(context, var21, source, phaseId));
                  } finally {
                     source.popComponentFromEL(context);
                     if (compositeParent != null) {
                        compositeParent.popComponentFromEL(context);
                     }

                  }
               }
            }

            hasMoreAnyPhaseEvents = null != (eventsForPhaseId = (List)this.events.get(PhaseId.ANY_PHASE.getOrdinal())) && !eventsForPhaseId.isEmpty();
            hasMoreCurrentPhaseEvents = null != this.events.get(phaseId.getOrdinal()) && !((List)this.events.get(phaseId.getOrdinal())).isEmpty();
         } while(hasMoreAnyPhaseEvents || hasMoreCurrentPhaseEvents);

      }
   }

   private void initState() {
      this.skipPhase = false;
      this.beforeMethodException = false;
      List listeners = (List)this.getStateHelper().get(UIViewRoot.PropertyKeys.phaseListeners);
      this.phaseListenerIterator = listeners != null ? listeners.listIterator() : null;
   }

   private void notifyBefore(FacesContext context, PhaseId phaseId) {
      if (this.getBeforePhaseListener() != null || this.phaseListenerIterator != null) {
         this.notifyPhaseListeners(context, phaseId, true);
      }

   }

   private void notifyAfter(FacesContext context, PhaseId phaseId) {
      if (this.getAfterPhaseListener() != null || this.phaseListenerIterator != null) {
         this.notifyPhaseListeners(context, phaseId, false);
      }

   }

   public void processRestoreState(FacesContext context, Object state) {
      if (context.getViewRoot() == null) {
         context.setViewRoot(this);
      }

      super.processRestoreState(context, state);
   }

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      FacesContext context = event.getFacesContext();
      if (event instanceof PostRestoreStateEvent && context.getPartialViewContext().isPartialRequest() && !context.getPartialViewContext().isRenderAll()) {
         ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
         Iterator var4 = this.getComponentResources(context).iterator();

         while(var4.hasNext()) {
            UIComponent resource = (UIComponent)var4.next();
            String name = (String)resource.getAttributes().get("name");
            String library = (String)resource.getAttributes().get("library");
            resourceHandler.markResourceRendered(context, name, library);
         }
      }

      super.processEvent(event);
   }

   public void processDecodes(FacesContext context) {
      this.initState();
      this.notifyBefore(context, PhaseId.APPLY_REQUEST_VALUES);

      try {
         if (!this.skipPhase) {
            if (context.getPartialViewContext().isPartialRequest() && !context.getPartialViewContext().isExecuteAll()) {
               context.getPartialViewContext().processPartial(PhaseId.APPLY_REQUEST_VALUES);
            } else {
               super.processDecodes(context);
            }

            this.broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
         }
      } finally {
         this.clearFacesEvents(context);
         this.notifyAfter(context, PhaseId.APPLY_REQUEST_VALUES);
      }

   }

   public void resetValues(FacesContext context, Collection clientIds) {
      this.visitTree(VisitContext.createVisitContext(context, clientIds, (Set)null), new DoResetValues());
   }

   public void encodeBegin(FacesContext context) throws IOException {
      this.initState();
      this.notifyBefore(context, PhaseId.RENDER_RESPONSE);
      if (!context.getResponseComplete()) {
         super.encodeBegin(context);
      }

   }

   public void encodeChildren(FacesContext context) throws IOException {
      if (context.getPartialViewContext().isAjaxRequest()) {
         context.getPartialViewContext().processPartial(PhaseId.RENDER_RESPONSE);
      } else {
         super.encodeChildren(context);
      }

   }

   public void encodeEnd(FacesContext context) throws IOException {
      super.encodeEnd(context);
      this.encodeViewParameters(context);
      this.notifyAfter(context, PhaseId.RENDER_RESPONSE);
   }

   public boolean getRendersChildren() {
      boolean value = super.getRendersChildren();
      FacesContext context = FacesContext.getCurrentInstance();
      if (context.getPartialViewContext().isAjaxRequest()) {
         value = true;
      }

      return value;
   }

   private void notifyPhaseListeners(FacesContext context, PhaseId phaseId, boolean isBefore) {
      PhaseEvent event = createPhaseEvent(context, phaseId);
      MethodExpression beforePhase = this.getBeforePhaseListener();
      MethodExpression afterPhase = this.getAfterPhaseListener();
      boolean hasPhaseMethodExpression = isBefore && null != beforePhase || !isBefore && null != afterPhase && !this.beforeMethodException;
      MethodExpression expression = isBefore ? beforePhase : afterPhase;
      if (hasPhaseMethodExpression) {
         try {
            expression.invoke(context.getELContext(), new Object[]{event});
            this.skipPhase = context.getResponseComplete() || context.getRenderResponse();
         } catch (Exception var14) {
            if (isBefore) {
               this.beforeMethodException = true;
            }

            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "severe.component.unable_to_process_expression", new Object[]{expression.getExpressionString(), isBefore ? "beforePhase" : "afterPhase"});
            }

            if (context.getAttributes().containsKey("javax.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS")) {
               ExceptionQueuedEventContext extx = new ExceptionQueuedEventContext(context, var14);
               String booleanKey = isBefore ? ExceptionQueuedEventContext.IN_BEFORE_PHASE_KEY : ExceptionQueuedEventContext.IN_AFTER_PHASE_KEY;
               extx.getAttributes().put(booleanKey, Boolean.TRUE);
               context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, extx);
            }

            return;
         }
      }

      if (this.phaseListenerIterator != null && !this.beforeMethodException) {
         while(true) {
            PhaseListener curListener;
            do {
               if (isBefore) {
                  if (!this.phaseListenerIterator.hasNext()) {
                     return;
                  }
               } else if (!this.phaseListenerIterator.hasPrevious()) {
                  return;
               }

               curListener = isBefore ? (PhaseListener)this.phaseListenerIterator.next() : (PhaseListener)this.phaseListenerIterator.previous();
            } while(phaseId != curListener.getPhaseId() && PhaseId.ANY_PHASE != curListener.getPhaseId());

            try {
               if (isBefore) {
                  curListener.beforePhase(event);
               } else {
                  curListener.afterPhase(event);
               }

               this.skipPhase = context.getResponseComplete() || context.getRenderResponse();
            } catch (Exception var13) {
               if (isBefore && this.phaseListenerIterator.hasPrevious()) {
                  this.phaseListenerIterator.previous();
               }

               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "severe.component.uiviewroot_error_invoking_phaselistener", curListener.getClass().getName());
               }

               if (context.getAttributes().containsKey("javax.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS") && (Boolean)context.getAttributes().get("javax.faces.VIEWROOT_PHASE_LISTENER_QUEUES_EXCEPTIONS")) {
                  ExceptionQueuedEventContext extx = new ExceptionQueuedEventContext(context, var13);
                  String booleanKey = isBefore ? ExceptionQueuedEventContext.IN_BEFORE_PHASE_KEY : ExceptionQueuedEventContext.IN_AFTER_PHASE_KEY;
                  extx.getAttributes().put(booleanKey, Boolean.TRUE);
                  context.getApplication().publishEvent(context, ExceptionQueuedEvent.class, extx);
               }

               return;
            }
         }
      }
   }

   private static PhaseEvent createPhaseEvent(FacesContext context, PhaseId phaseId) throws FacesException {
      if (lifecycle == null) {
         LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory("javax.faces.lifecycle.LifecycleFactory");
         String lifecycleId = context.getExternalContext().getInitParameter("javax.faces.LIFECYCLE_ID");
         if (lifecycleId == null) {
            lifecycleId = "DEFAULT";
         }

         lifecycle = lifecycleFactory.getLifecycle(lifecycleId);
      }

      return new PhaseEvent(context, phaseId, lifecycle);
   }

   public void processValidators(FacesContext context) {
      this.initState();
      this.notifyBefore(context, PhaseId.PROCESS_VALIDATIONS);

      try {
         if (!this.skipPhase) {
            if (context.getPartialViewContext().isPartialRequest() && !context.getPartialViewContext().isExecuteAll()) {
               context.getPartialViewContext().processPartial(PhaseId.PROCESS_VALIDATIONS);
            } else {
               super.processValidators(context);
            }

            this.broadcastEvents(context, PhaseId.PROCESS_VALIDATIONS);
         }
      } finally {
         this.clearFacesEvents(context);
         this.notifyAfter(context, PhaseId.PROCESS_VALIDATIONS);
      }

   }

   public void processUpdates(FacesContext context) {
      this.initState();
      this.notifyBefore(context, PhaseId.UPDATE_MODEL_VALUES);

      try {
         if (!this.skipPhase) {
            if (context.getPartialViewContext().isPartialRequest() && !context.getPartialViewContext().isExecuteAll()) {
               context.getPartialViewContext().processPartial(PhaseId.UPDATE_MODEL_VALUES);
            } else {
               super.processUpdates(context);
            }

            this.broadcastEvents(context, PhaseId.UPDATE_MODEL_VALUES);
         }
      } finally {
         this.clearFacesEvents(context);
         this.notifyAfter(context, PhaseId.UPDATE_MODEL_VALUES);
      }

   }

   public void processApplication(FacesContext context) {
      this.initState();
      this.notifyBefore(context, PhaseId.INVOKE_APPLICATION);

      try {
         if (!this.skipPhase) {
            this.broadcastEvents(context, PhaseId.INVOKE_APPLICATION);
         }
      } finally {
         this.clearFacesEvents(context);
         this.notifyAfter(context, PhaseId.INVOKE_APPLICATION);
      }

   }

   private void clearFacesEvents(FacesContext context) {
      if ((context.getRenderResponse() || context.getResponseComplete()) && this.events != null) {
         Iterator var2 = this.events.iterator();

         while(var2.hasNext()) {
            List eventList = (List)var2.next();
            if (eventList != null) {
               eventList.clear();
            }
         }

         this.events = null;
      }

   }

   public String createUniqueId() {
      return this.createUniqueId(this.getFacesContext(), (String)null);
   }

   public String createUniqueId(FacesContext context, String seed) {
      if (seed != null) {
         return "j_id" + seed;
      } else {
         Integer i = (Integer)this.getStateHelper().get(UIViewRoot.PropertyKeys.lastId);
         int lastId = i != null ? i : 0;
         StateHelper var10000 = this.getStateHelper();
         ++lastId;
         var10000.put(UIViewRoot.PropertyKeys.lastId, lastId);
         return "j_id" + lastId;
      }
   }

   public Locale getLocale() {
      Object result = this.getStateHelper().eval(UIViewRoot.PropertyKeys.locale);
      if (result != null) {
         Locale locale = null;
         if (result instanceof Locale) {
            locale = (Locale)result;
         } else if (result instanceof String) {
            locale = getLocaleFromString((String)result);
         }

         return locale;
      } else {
         FacesContext context = this.getFacesContext();
         return context.getApplication().getViewHandler().calculateLocale(context);
      }
   }

   private static Locale getLocaleFromString(String localeStr) throws IllegalArgumentException {
      if (null != localeStr && localeStr.length() >= 2) {
         Locale result = null;
         String lang = null;
         String country = null;
         String variant = null;
         char[] seps = new char[]{'-', '_'};
         int inputLength = localeStr.length();
         int i = 0;
         int j = 0;
         if (inputLength >= 2 && (i = indexOfSet(localeStr, seps, 0)) == -1) {
            if (2 != localeStr.length()) {
               throw new IllegalArgumentException("Illegal locale String: " + localeStr);
            }

            lang = localeStr.toLowerCase();
         }

         if (i != -1) {
            lang = localeStr.substring(0, i);
            if (inputLength >= 5 && -1 == (j = indexOfSet(localeStr, seps, i + 1))) {
               if (inputLength != 5) {
                  throw new IllegalArgumentException("Illegal locale String: " + localeStr);
               }

               country = localeStr.substring(i + 1);
            }

            if (j != -1) {
               country = localeStr.substring(i + 1, j);
               if (inputLength < 8) {
                  throw new IllegalArgumentException("Illegal locale String: " + localeStr);
               }

               variant = localeStr.substring(j + 1);
            }
         }

         if (variant != null && country != null && lang != null) {
            result = new Locale(lang, country, variant);
         } else if (lang != null && country != null) {
            result = new Locale(lang, country);
         } else if (lang != null) {
            result = new Locale(lang, "");
         }

         return result;
      } else {
         throw new IllegalArgumentException("Illegal locale String: " + localeStr);
      }
   }

   private static int indexOfSet(String str, char[] set, int fromIndex) {
      int result = -1;
      int i = fromIndex;

      for(int len = str.length(); i < len; ++i) {
         int j = 0;

         for(int innerLen = set.length; j < innerLen; ++j) {
            if (str.charAt(i) == set[j]) {
               result = i;
               break;
            }
         }

         if (-1 != result) {
            break;
         }
      }

      return result;
   }

   public void setLocale(Locale locale) {
      this.getStateHelper().put(UIViewRoot.PropertyKeys.locale, locale);
      FacesContext.getCurrentInstance().getELContext().setLocale(locale);
   }

   public Map getViewMap() {
      return this.getViewMap(true);
   }

   public Map getViewMap(boolean create) {
      Map viewMap = (Map)this.getTransientStateHelper().getTransient("com.sun.faces.application.view.viewMap");
      if (create && viewMap == null) {
         viewMap = new ViewMap(this.getFacesContext().getApplication().getProjectStage());
         this.getTransientStateHelper().putTransient("com.sun.faces.application.view.viewMap", viewMap);
         this.getFacesContext().getApplication().publishEvent(this.getFacesContext(), PostConstructViewMapEvent.class, UIViewRoot.class, this);
      }

      return (Map)viewMap;
   }

   public void subscribeToViewEvent(Class systemEvent, SystemEventListener listener) {
      if (systemEvent == null) {
         throw new NullPointerException();
      } else if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.viewListeners == null) {
            this.viewListeners = new HashMap(4, 1.0F);
         }

         List listeners = (List)this.viewListeners.get(systemEvent);
         if (listeners == null) {
            listeners = new CopyOnWriteArrayList();
            this.viewListeners.put(systemEvent, listeners);
         }

         ((List)listeners).add(listener);
      }
   }

   public void unsubscribeFromViewEvent(Class systemEvent, SystemEventListener listener) {
      if (systemEvent == null) {
         throw new NullPointerException();
      } else if (listener == null) {
         throw new NullPointerException();
      } else {
         if (this.viewListeners != null) {
            List listeners = (List)this.viewListeners.get(systemEvent);
            if (listeners != null) {
               listeners.remove(listener);
            }
         }

      }
   }

   public List getViewListenersForEventClass(Class systemEvent) {
      if (systemEvent == null) {
         throw new NullPointerException();
      } else {
         return this.viewListeners != null ? (List)this.viewListeners.get(systemEvent) : null;
      }
   }

   private void encodeViewParameters(FacesContext context) {
      ViewDeclarationLanguage vdl = context.getApplication().getViewHandler().getViewDeclarationLanguage(context, this.getViewId());
      if (vdl != null) {
         ViewMetadata metadata = vdl.getViewMetadata(context, this.getViewId());
         if (metadata != null) {
            Collection params = ViewMetadata.getViewParameters(this);
            if (params.isEmpty()) {
               return;
            }

            try {
               Iterator var5 = params.iterator();

               while(var5.hasNext()) {
                  UIViewParameter param = (UIViewParameter)var5.next();
                  param.encodeAll(context);
               }
            } catch (IOException var7) {
               throw new RuntimeException("Unexpected IOException", var7);
            }
         }

      }
   }

   public void restoreViewScopeState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         this.values = (Object[])((Object[])state);
         super.restoreState(context, this.values[0]);
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         String viewMapId = (String)this.getTransientStateHelper().getTransient("com.sun.faces.application.view.viewMapId");
         Object superState = super.saveState(context);
         if (superState != null || viewMapId != null) {
            this.values = new Object[]{superState, viewMapId};
         }

         return this.values;
      }
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else if (state != null) {
         this.values = (Object[])((Object[])state);
         if (!context.getAttributes().containsKey("com.sun.faces.application.view.restoreViewScopeOnly")) {
            super.restoreState(context, this.values[0]);
         }

         String viewMapId = (String)this.values[1];
         this.getTransientStateHelper().putTransient("com.sun.faces.application.view.viewMapId", viewMapId);
         Map viewMaps = (Map)context.getExternalContext().getSessionMap().get("com.sun.faces.application.view.activeViewMaps");
         if (viewMaps != null) {
            Map viewMap = (Map)viewMaps.get(viewMapId);
            this.getTransientStateHelper().putTransient("com.sun.faces.application.view.viewMap", viewMap);
         }

      }
   }

   private static String getIdentifier(String target) {
      String id = (String)LOCATION_IDENTIFIER_MAP.get(target);
      if (id == null) {
         id = "javax_faces_location_" + target;
         LOCATION_IDENTIFIER_MAP.put(target, id);
      }

      return id;
   }

   private List getComponentResources(FacesContext context, String target, boolean create) {
      String location = getIdentifier(target);
      UIComponent facet = this.getFacet(location);
      if (facet == null && create) {
         facet = context.getApplication().createComponent("javax.faces.ComponentResourceContainer");
         facet.setId(location);
         this.getFacets().put(location, facet);
      }

      return facet != null ? facet.getChildren() : null;
   }

   static {
      LOCATION_IDENTIFIER_MAP.put("head", "javax_faces_location_HEAD");
      LOCATION_IDENTIFIER_MAP.put("form", "javax_faces_location_FORM");
      LOCATION_IDENTIFIER_MAP.put("body", "javax_faces_location_BODY");
   }

   private static final class ViewMap extends HashMap {
      private static final long serialVersionUID = -1L;
      private ProjectStage stage;

      ViewMap(ProjectStage stage) {
         this.stage = stage;
      }

      public void clear() {
         FacesContext context = FacesContext.getCurrentInstance();
         context.getApplication().publishEvent(context, PreDestroyViewMapEvent.class, UIViewRoot.class, context.getViewRoot());
         super.clear();
      }

      public Object put(String key, Object value) {
         if (value != null && ProjectStage.Development.equals(this.stage) && !(value instanceof Serializable)) {
            UIViewRoot.LOGGER.log(Level.WARNING, "warning.component.uiviewroot_non_serializable_attribute_viewmap", new Object[]{key, value.getClass().getName()});
         }

         return super.put(key, value);
      }

      public void putAll(Map m) {
         Iterator var2 = m.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            this.put(k, v);
         }

      }
   }

   private static class DoResetValues implements VisitCallback {
      private DoResetValues() {
      }

      public VisitResult visit(VisitContext context, UIComponent target) {
         if (target instanceof EditableValueHolder) {
            ((EditableValueHolder)target).resetValue();
         }

         return VisitResult.ACCEPT;
      }

      // $FF: synthetic method
      DoResetValues(Object x0) {
         this();
      }
   }

   static enum PropertyKeys {
      renderKitId,
      viewId,
      locale,
      lastId,
      beforePhase,
      afterPhase,
      phaseListeners,
      resourceLibraryContracts;
   }
}
