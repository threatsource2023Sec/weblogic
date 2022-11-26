package com.sun.faces.lifecycle;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Timer;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

public abstract class Phase {
   private static final Logger LOGGER;

   public void doPhase(FacesContext context, Lifecycle lifecycle, ListIterator listeners) {
      context.setCurrentPhaseId(this.getId());
      PhaseEvent event = null;
      if (listeners.hasNext()) {
         event = new PhaseEvent(context, this.getId(), lifecycle);
      }

      Timer timer = Timer.getInstance();
      if (timer != null) {
         timer.startTiming();
      }

      try {
         this.handleBeforePhase(context, listeners, event);
         if (!this.shouldSkip(context)) {
            this.execute(context);
         }
      } catch (Throwable var15) {
         this.queueException(context, var15);
      } finally {
         try {
            this.handleAfterPhase(context, listeners, event);
         } catch (Throwable var14) {
            this.queueException(context, var14);
         }

         if (timer != null) {
            timer.stopTiming();
            timer.logResult("Execution time for phase (including any PhaseListeners) -> " + this.getId().toString());
         }

         context.getExceptionHandler().handle();
      }

   }

   public abstract void execute(FacesContext var1) throws FacesException;

   public abstract PhaseId getId();

   protected void queueException(FacesContext ctx, Throwable t) {
      this.queueException(ctx, t, (String)null);
   }

   protected void queueException(FacesContext ctx, Throwable t, String booleanKey) {
      ExceptionQueuedEventContext extx = new ExceptionQueuedEventContext(ctx, t);
      if (booleanKey != null) {
         extx.getAttributes().put(booleanKey, Boolean.TRUE);
      }

      ctx.getApplication().publishEvent(ctx, ExceptionQueuedEvent.class, extx);
   }

   protected void handleAfterPhase(FacesContext context, ListIterator listenersIterator, PhaseEvent event) {
      try {
         Flash flash = context.getExternalContext().getFlash();
         flash.doPostPhaseActions(context);
      } catch (UnsupportedOperationException var7) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
         }
      }

      while(true) {
         PhaseListener listener;
         do {
            if (!listenersIterator.hasPrevious()) {
               return;
            }

            listener = (PhaseListener)listenersIterator.previous();
         } while(!this.getId().equals(listener.getPhaseId()) && !PhaseId.ANY_PHASE.equals(listener.getPhaseId()));

         try {
            listener.afterPhase(event);
         } catch (Exception var6) {
            this.queueException(context, var6, ExceptionQueuedEventContext.IN_AFTER_PHASE_KEY);
            return;
         }
      }
   }

   protected void handleBeforePhase(FacesContext context, ListIterator listenersIterator, PhaseEvent event) {
      try {
         Flash flash = context.getExternalContext().getFlash();
         flash.doPrePhaseActions(context);
      } catch (UnsupportedOperationException var7) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("ExternalContext.getFlash() throw UnsupportedOperationException -> Flash unavailable");
         }
      }

      while(true) {
         PhaseListener listener;
         do {
            if (!listenersIterator.hasNext()) {
               return;
            }

            listener = (PhaseListener)listenersIterator.next();
         } while(!this.getId().equals(listener.getPhaseId()) && !PhaseId.ANY_PHASE.equals(listener.getPhaseId()));

         try {
            listener.beforePhase(event);
         } catch (Exception var6) {
            this.queueException(context, var6, ExceptionQueuedEventContext.IN_BEFORE_PHASE_KEY);
            if (listenersIterator.hasPrevious()) {
               listenersIterator.previous();
            }

            return;
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
