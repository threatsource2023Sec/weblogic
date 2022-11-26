package com.sun.faces.lifecycle;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Timer;
import com.sun.faces.util.Util;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

public abstract class Phase {
   private static Logger LOGGER;

   public void doPhase(FacesContext context, Lifecycle lifecycle, ListIterator listeners) {
      PhaseEvent event = null;
      if (listeners.hasNext()) {
         event = new PhaseEvent(context, this.getId(), lifecycle);
      }

      Timer timer = Timer.getInstance();
      if (timer != null) {
         timer.startTiming();
      }

      this.handleBeforePhase(context, listeners, event);
      Exception ex = null;

      try {
         if (!this.shouldSkip(context)) {
            this.execute(context);
         }
      } catch (Exception var11) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "jsf.lifecycle.phase.exception", new Object[]{this.getId().toString(), context.getViewRoot() != null ? context.getViewRoot().getViewId() : "", event});
         }

         ex = var11;
      } finally {
         this.handleAfterPhase(context, listeners, event);
         if (timer != null) {
            timer.stopTiming();
            timer.logResult("Execution time for phase (including any PhaseListeners) -> " + this.getId().toString());
         }

      }

      if (ex != null) {
         if (!(ex instanceof FacesException)) {
            ex = new FacesException((Throwable)ex);
         }

         throw (FacesException)ex;
      }
   }

   public abstract void execute(FacesContext var1) throws FacesException;

   public abstract PhaseId getId();

   protected void handleAfterPhase(FacesContext context, ListIterator listenersIterator, PhaseEvent event) {
      while(listenersIterator.hasPrevious()) {
         PhaseListener listener = (PhaseListener)listenersIterator.previous();
         if (this.getId().equals(listener.getPhaseId()) || PhaseId.ANY_PHASE.equals(listener.getPhaseId())) {
            try {
               listener.afterPhase(event);
            } catch (Exception var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.lifecycle.phaselistener.exception", new Object[]{listener.getClass().getName() + ".afterPhase()", this.getId().toString(), context.getViewRoot() != null ? context.getViewRoot().getViewId() : "", var6});
                  LOGGER.warning(Util.getStackTraceString(var6));
                  return;
               }
            }
         }
      }

   }

   protected void handleBeforePhase(FacesContext context, ListIterator listenersIterator, PhaseEvent event) {
      while(listenersIterator.hasNext()) {
         PhaseListener listener = (PhaseListener)listenersIterator.next();
         if (this.getId().equals(listener.getPhaseId()) || PhaseId.ANY_PHASE.equals(listener.getPhaseId())) {
            try {
               listener.beforePhase(event);
            } catch (Exception var6) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.lifecycle.phaselistener.exception", new Object[]{listener.getClass().getName() + ".beforePhase()", this.getId().toString(), context.getViewRoot() != null ? context.getViewRoot().getViewId() : "", var6});
                  LOGGER.warning(Util.getStackTraceString(var6));
               }

               if (listenersIterator.hasPrevious()) {
                  listenersIterator.previous();
               }

               return;
            }
         }
      }

   }

   private boolean shouldSkip(FacesContext context) {
      if (context.getResponseComplete()) {
         return true;
      } else {
         return context.getRenderResponse() && !PhaseId.RENDER_RESPONSE.equals(this.getId());
      }
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
