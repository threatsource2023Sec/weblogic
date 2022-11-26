package com.sun.faces.lifecycle;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

public class LifecycleImpl extends Lifecycle {
   private static Logger LOGGER;
   private Phase response = new RenderResponsePhase();
   private Phase[] phases;
   private List listeners;

   public LifecycleImpl() {
      this.phases = new Phase[]{null, new RestoreViewPhase(), new ApplyRequestValuesPhase(), new ProcessValidationsPhase(), new UpdateModelValuesPhase(), new InvokeApplicationPhase(), this.response};
      this.listeners = new CopyOnWriteArrayList();
   }

   public void execute(FacesContext context) throws FacesException {
      if (context == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context"));
      } else {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("execute(" + context + ")");
         }

         int i = 1;

         for(int len = this.phases.length - 1; i < len && !context.getRenderResponse() && !context.getResponseComplete(); ++i) {
            this.phases[i].doPhase(context, this, this.listeners.listIterator());
         }

      }
   }

   public void render(FacesContext context) throws FacesException {
      if (context == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context"));
      } else {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("render(" + context + ")");
         }

         if (!context.getResponseComplete()) {
            this.response.doPhase(context, this, this.listeners.listIterator());
         }

      }
   }

   public void addPhaseListener(PhaseListener listener) {
      if (listener == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "listener"));
      } else {
         if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList();
         }

         if (this.listeners.contains(listener)) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "jsf.lifecycle.duplicate_phase_listener_detected", listener.getClass().getName());
            }
         } else {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "addPhaseListener({0},{1})", new Object[]{listener.getPhaseId().toString(), listener.getClass().getName()});
            }

            this.listeners.add(listener);
         }

      }
   }

   public PhaseListener[] getPhaseListeners() {
      return (PhaseListener[])this.listeners.toArray(new PhaseListener[this.listeners.size()]);
   }

   public void removePhaseListener(PhaseListener listener) {
      if (listener == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "listener"));
      } else {
         if (this.listeners.remove(listener) && LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "removePhaseListener({0})", new Object[]{listener.getClass().getName()});
         }

      }
   }

   static {
      LOGGER = FacesLogger.LIFECYCLE.getLogger();
   }
}
