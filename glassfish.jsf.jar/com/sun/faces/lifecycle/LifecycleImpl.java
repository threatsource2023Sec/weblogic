package com.sun.faces.lifecycle;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseListener;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.lifecycle.ClientWindowFactory;
import javax.faces.lifecycle.Lifecycle;

public class LifecycleImpl extends Lifecycle {
   private static Logger LOGGER;
   private Phase response = new RenderResponsePhase();
   private Phase[] phases;
   private List listeners;
   private boolean isClientWindowEnabled;
   private WebConfiguration config;

   public LifecycleImpl() {
      this.phases = new Phase[]{null, new RestoreViewPhase(), new ApplyRequestValuesPhase(), new ProcessValidationsPhase(), new UpdateModelValuesPhase(), new InvokeApplicationPhase(), this.response};
      this.listeners = new CopyOnWriteArrayList();
      this.isClientWindowEnabled = false;
   }

   public LifecycleImpl(FacesContext context) {
      this.phases = new Phase[]{null, new RestoreViewPhase(), new ApplyRequestValuesPhase(), new ProcessValidationsPhase(), new UpdateModelValuesPhase(), new InvokeApplicationPhase(), this.response};
      this.listeners = new CopyOnWriteArrayList();
      this.isClientWindowEnabled = false;
      ExternalContext extContext = context.getExternalContext();
      this.config = WebConfiguration.getInstance(extContext);
      context.getApplication().subscribeToEvent(PostConstructApplicationEvent.class, Application.class, new PostConstructApplicationListener());
   }

   private void postConstructApplicationInitialization() {
      String optionValue = this.config.getOptionValue(WebConfiguration.WebContextInitParameter.ClientWindowMode);
      this.isClientWindowEnabled = null != optionValue && !optionValue.equals(WebConfiguration.WebContextInitParameter.ClientWindowMode.getDefaultValue());
   }

   public void attachWindow(FacesContext context) {
      if (this.isClientWindowEnabled) {
         if (context == null) {
            throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context"));
         } else {
            ExternalContext extContext = context.getExternalContext();
            ClientWindow myWindow = extContext.getClientWindow();
            if (null == myWindow) {
               myWindow = this.createClientWindow(context);
               if (null != myWindow) {
                  myWindow.decode(context);
                  extContext.setClientWindow(myWindow);
               }
            }

         }
      }
   }

   private ClientWindow createClientWindow(FacesContext context) {
      ClientWindowFactory clientWindowFactory = null;
      if (Util.isUnitTestModeEnabled()) {
         clientWindowFactory = new ClientWindowFactoryImpl(false);
      } else {
         clientWindowFactory = (ClientWindowFactory)FactoryFinder.getFactory("javax.faces.lifecycle.ClientWindowFactory");
      }

      return ((ClientWindowFactory)clientWindowFactory).getClientWindow(context);
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

   private class PostConstructApplicationListener implements SystemEventListener {
      private PostConstructApplicationListener() {
      }

      public boolean isListenerForSource(Object source) {
         return source instanceof Application;
      }

      public void processEvent(SystemEvent event) throws AbortProcessingException {
         LifecycleImpl.this.postConstructApplicationInitialization();
      }

      // $FF: synthetic method
      PostConstructApplicationListener(Object x1) {
         this();
      }
   }
}
