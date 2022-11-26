package javax.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

public class UIViewRoot extends UIComponentBase {
   public static final String COMPONENT_TYPE = "javax.faces.ViewRoot";
   public static final String COMPONENT_FAMILY = "javax.faces.ViewRoot";
   public static final String UNIQUE_ID_PREFIX = "j_id";
   private static Lifecycle lifecycle;
   private static final Logger LOGGER = Logger.getLogger("javax.faces", "javax.faces.LogStrings");
   private int lastId = 0;
   private boolean skipPhase;
   private boolean beforeMethodException;
   private ListIterator phaseListenerIterator;
   private String renderKitId = null;
   private String viewId = null;
   private MethodExpression beforePhase = null;
   private MethodExpression afterPhase = null;
   private List phaseListeners = null;
   private List events = null;
   private Locale locale = null;
   private Object[] values;

   public UIViewRoot() {
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "javax.faces.ViewRoot";
   }

   public String getRenderKitId() {
      String result;
      if (null != this.renderKitId) {
         result = this.renderKitId;
      } else {
         ValueExpression vb = this.getValueExpression("renderKitId");
         FacesContext context = this.getFacesContext();
         if (vb != null) {
            try {
               result = (String)vb.getValue(context.getELContext());
            } catch (ELException var5) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "severe.component.unable_to_process_expression", new Object[]{vb.getExpressionString(), "renderKitId"});
               }

               result = null;
            }
         } else {
            result = null;
         }
      }

      return result;
   }

   public void setRenderKitId(String renderKitId) {
      this.renderKitId = renderKitId;
   }

   public String getViewId() {
      return this.viewId;
   }

   public void setViewId(String viewId) {
      this.viewId = viewId;
   }

   public MethodExpression getBeforePhaseListener() {
      return this.beforePhase;
   }

   public void setBeforePhaseListener(MethodExpression newBeforePhase) {
      this.beforePhase = newBeforePhase;
   }

   public MethodExpression getAfterPhaseListener() {
      return this.afterPhase;
   }

   public void setAfterPhaseListener(MethodExpression newAfterPhase) {
      this.afterPhase = newAfterPhase;
   }

   public void removePhaseListener(PhaseListener toRemove) {
      if (null != this.phaseListeners) {
         this.phaseListeners.remove(toRemove);
      }

   }

   public void addPhaseListener(PhaseListener newPhaseListener) {
      if (null == this.phaseListeners) {
         this.phaseListeners = new ArrayList();
      }

      this.phaseListeners.add(newPhaseListener);
   }

   public void queueEvent(FacesEvent event) {
      if (event == null) {
         throw new NullPointerException();
      } else {
         int len = PhaseId.VALUES.size();
         if (this.events == null) {
            List events = new ArrayList(len);

            for(int i = 0; i < len; ++i) {
               events.add(new ArrayList(5));
            }

            this.events = events;
         }

         ((List)this.events.get(event.getPhaseId().getOrdinal())).add(event);
      }
   }

   private void broadcastEvents(FacesContext context, PhaseId phaseId) {
      if (null != this.events) {
         List eventsForPhaseId = (List)this.events.get(PhaseId.ANY_PHASE.getOrdinal());

         boolean hasMoreAnyPhaseEvents;
         boolean hasMoreCurrentPhaseEvents;
         do {
            FacesEvent event;
            UIComponent source;
            if (null != eventsForPhaseId) {
               for(; !eventsForPhaseId.isEmpty(); eventsForPhaseId.remove(0)) {
                  event = (FacesEvent)eventsForPhaseId.get(0);
                  source = event.getComponent();

                  try {
                     source.broadcast(event);
                  } catch (AbortProcessingException var12) {
                     if (LOGGER.isLoggable(Level.SEVERE)) {
                        UIComponent component = event.getComponent();
                        String id = "";
                        if (component != null) {
                           id = component.getId();
                           if (id == null) {
                              id = component.getClientId(context);
                           }
                        }

                        LOGGER.log(Level.SEVERE, "error.component.abortprocessing_thrown", new Object[]{event.getClass().getName(), phaseId.toString(), id});
                        LOGGER.log(Level.SEVERE, var12.toString(), var12);
                     }
                  }
               }
            }

            if (null != (eventsForPhaseId = (List)this.events.get(phaseId.getOrdinal()))) {
               for(; !eventsForPhaseId.isEmpty(); eventsForPhaseId.remove(0)) {
                  event = (FacesEvent)eventsForPhaseId.get(0);
                  source = event.getComponent();

                  try {
                     source.broadcast(event);
                  } catch (AbortProcessingException var11) {
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
      this.phaseListenerIterator = this.phaseListeners != null ? this.phaseListeners.listIterator() : null;
   }

   private void notifyBefore(FacesContext context, PhaseId phaseId) {
      if (null != this.beforePhase || null != this.phaseListenerIterator) {
         this.notifyPhaseListeners(context, phaseId, true);
      }

   }

   private void notifyAfter(FacesContext context, PhaseId phaseId) {
      if (null != this.afterPhase || null != this.phaseListenerIterator) {
         this.notifyPhaseListeners(context, phaseId, false);
      }

   }

   public void processDecodes(FacesContext context) {
      this.initState();
      this.notifyBefore(context, PhaseId.APPLY_REQUEST_VALUES);
      if (!this.skipPhase) {
         super.processDecodes(context);
         this.broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
      }

      this.clearFacesEvents(context);
      this.notifyAfter(context, PhaseId.APPLY_REQUEST_VALUES);
   }

   public void encodeBegin(FacesContext context) throws IOException {
      this.initState();
      this.notifyBefore(context, PhaseId.RENDER_RESPONSE);
      if (!this.skipPhase) {
         super.encodeBegin(context);
      }

   }

   public void encodeEnd(FacesContext context) throws IOException {
      super.encodeEnd(context);
      this.notifyAfter(context, PhaseId.RENDER_RESPONSE);
   }

   private void notifyPhaseListeners(FacesContext context, PhaseId phaseId, boolean isBefore) {
      PhaseEvent event = createPhaseEvent(context, phaseId);
      boolean hasPhaseMethodExpression = isBefore && null != this.beforePhase || !isBefore && null != this.afterPhase && !this.beforeMethodException;
      MethodExpression expression = isBefore ? this.beforePhase : this.afterPhase;
      if (hasPhaseMethodExpression) {
         try {
            expression.invoke(context.getELContext(), new Object[]{event});
            this.skipPhase = context.getResponseComplete() || context.getRenderResponse();
         } catch (Exception var10) {
            if (isBefore) {
               this.beforeMethodException = true;
            }

            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Exception", var10);
               LOGGER.log(Level.SEVERE, "severe.component.unable_to_process_expression", new Object[]{expression.getExpressionString(), isBefore ? "beforePhase" : "afterPhase"});
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
            } catch (Exception var9) {
               if (isBefore && this.phaseListenerIterator.hasPrevious()) {
                  this.phaseListenerIterator.previous();
               }

               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "severe.component.uiviewroot_error_invoking_phaselistener", curListener.getClass().getName());
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
            super.processValidators(context);
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
            super.processUpdates(context);
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
      return "j_id" + this.lastId++;
   }

   public Locale getLocale() {
      Locale result = null;
      if (null != this.locale) {
         result = this.locale;
      } else {
         ValueExpression vb = this.getValueExpression("locale");
         FacesContext context = this.getFacesContext();
         if (vb != null) {
            Object resultLocale = null;

            try {
               resultLocale = vb.getValue(context.getELContext());
            } catch (ELException var6) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "severe.component.unable_to_process_expression", new Object[]{vb.getExpressionString(), "locale"});
               }
            }

            if (null == resultLocale) {
               result = context.getApplication().getViewHandler().calculateLocale(context);
            } else if (resultLocale instanceof Locale) {
               result = (Locale)resultLocale;
            } else if (resultLocale instanceof String) {
               result = getLocaleFromString((String)resultLocale);
            }
         } else {
            result = context.getApplication().getViewHandler().calculateLocale(context);
         }
      }

      return result;
   }

   private static Locale getLocaleFromString(String localeStr) throws IllegalArgumentException {
      if (null != localeStr && localeStr.length() >= 2) {
         Locale result = null;
         String lang = null;
         String country = null;
         String variant = null;
         char[] seps = new char[]{'-', '_'};
         int i = 0;
         int j = 0;
         int inputLength = localeStr.length();
         if (inputLength >= 2 && (i = indexOfSet(localeStr, seps, 0)) == -1) {
            if (localeStr.length() != 2) {
               throw new IllegalArgumentException("Illegal locale String: " + localeStr);
            }

            lang = localeStr.toLowerCase();
         }

         if (i != -1) {
            lang = localeStr.substring(0, i);
            if (inputLength >= 5 && (j = indexOfSet(localeStr, seps, i + 1)) == -1) {
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

         if (result != -1) {
            break;
         }
      }

      return result;
   }

   public void setLocale(Locale locale) {
      this.locale = locale;
      FacesContext.getCurrentInstance().getELContext().setLocale(locale);
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[8];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.renderKitId;
      this.values[2] = this.viewId;
      this.values[3] = this.locale;
      this.values[4] = this.lastId;
      this.values[5] = saveAttachedState(context, this.beforePhase);
      this.values[6] = saveAttachedState(context, this.afterPhase);
      this.values[7] = saveAttachedState(context, this.phaseListeners);
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.renderKitId = (String)this.values[1];
      this.viewId = (String)this.values[2];
      this.locale = (Locale)this.values[3];
      this.lastId = (Integer)this.values[4];
      this.beforePhase = (MethodExpression)restoreAttachedState(context, this.values[5]);
      this.afterPhase = (MethodExpression)restoreAttachedState(context, this.values[6]);
      this.phaseListeners = TypedCollections.dynamicallyCastList((List)restoreAttachedState(context, this.values[7]), PhaseListener.class);
   }
}
