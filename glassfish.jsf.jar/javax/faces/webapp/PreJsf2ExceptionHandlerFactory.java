package javax.faces.webapp;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UpdateModelException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseId;
import javax.faces.event.SystemEvent;

public class PreJsf2ExceptionHandlerFactory extends ExceptionHandlerFactory {
   public ExceptionHandler getExceptionHandler() {
      return new PreJsf2ExceptionHandler();
   }

   private static final class PreJsf2ExceptionHandler extends ExceptionHandler {
      private static final Logger LOGGER = Logger.getLogger("javax.faces.webapp", "javax.faces.LogStrings");
      private static final String LOG_BEFORE_KEY = "servere.webapp.prejsf2.exception.handler.log_before";
      private static final String LOG_AFTER_KEY = "servere.webapp.prejsf2.exception.handler.log_after";
      private static final String LOG_KEY = "servere.webapp.prejsf2.exception.handler.log";
      private LinkedList unhandledExceptions;
      private LinkedList handledExceptions;
      private ExceptionQueuedEvent handled;

      private PreJsf2ExceptionHandler() {
      }

      public ExceptionQueuedEvent getHandledExceptionQueuedEvent() {
         return this.handled;
      }

      public void handle() throws FacesException {
         Iterator i = this.getUnhandledExceptionQueuedEvents().iterator();

         while(i.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent)i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();

            try {
               Throwable t = context.getException();
               if (this.isRethrown(t, context.inBeforePhase() || context.inAfterPhase())) {
                  this.handled = event;
                  Throwable unwrapped = this.getRootCause(t);
                  if (unwrapped != null) {
                     throw new FacesException(unwrapped.getMessage(), unwrapped);
                  }

                  if (t instanceof FacesException) {
                     throw (FacesException)t;
                  }

                  throw new FacesException(t.getMessage(), t);
               }

               this.log(context);
            } finally {
               if (this.handledExceptions == null) {
                  this.handledExceptions = new LinkedList();
               }

               this.handledExceptions.add(event);
               i.remove();
            }
         }

      }

      public boolean isListenerForSource(Object source) {
         return source instanceof ExceptionQueuedEventContext;
      }

      public void processEvent(SystemEvent event) throws AbortProcessingException {
         if (event != null) {
            if (this.unhandledExceptions == null) {
               this.unhandledExceptions = new LinkedList();
            }

            this.unhandledExceptions.add((ExceptionQueuedEvent)event);
         }

      }

      public Throwable getRootCause(Throwable t) {
         if (t == null) {
            return null;
         } else if (this.shouldUnwrap(t.getClass())) {
            Throwable root = t.getCause();
            if (root != null) {
               Throwable tmp = this.getRootCause(root);
               return tmp == null ? root : tmp;
            } else {
               return t;
            }
         } else {
            return t;
         }
      }

      public Iterable getUnhandledExceptionQueuedEvents() {
         return (Iterable)(this.unhandledExceptions != null ? this.unhandledExceptions : Collections.emptyList());
      }

      public Iterable getHandledExceptionQueuedEvents() {
         return (Iterable)(this.handledExceptions != null ? this.handledExceptions : Collections.emptyList());
      }

      private boolean shouldUnwrap(Class c) {
         return FacesException.class.equals(c) || ELException.class.equals(c);
      }

      private boolean isRethrown(Throwable t, boolean isBeforeOrAfterPhase) {
         return !isBeforeOrAfterPhase && !(t instanceof AbortProcessingException) && !(t instanceof UpdateModelException);
      }

      private void log(ExceptionQueuedEventContext exceptionContext) {
         Throwable t = exceptionContext.getException();
         UIComponent c = exceptionContext.getComponent();
         if (t instanceof UpdateModelException) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = ((UpdateModelException)t).getFacesMessage();
            LOGGER.log(Level.SEVERE, message.getSummary(), t.getCause());
            context.addMessage(c.getClientId(context), message);
         } else {
            boolean beforePhase = exceptionContext.inBeforePhase();
            boolean afterPhase = exceptionContext.inAfterPhase();
            PhaseId phaseId = exceptionContext.getPhaseId();
            String key = this.getLoggingKey(beforePhase, afterPhase);
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, key, new Object[]{t.getClass().getName(), phaseId.toString(), c != null ? c.getClientId(exceptionContext.getContext()) : "", t.getMessage()});
               LOGGER.log(Level.SEVERE, t.getMessage(), t);
            }
         }

      }

      private String getLoggingKey(boolean beforePhase, boolean afterPhase) {
         if (beforePhase) {
            return "servere.webapp.prejsf2.exception.handler.log_before";
         } else {
            return afterPhase ? "servere.webapp.prejsf2.exception.handler.log_after" : "servere.webapp.prejsf2.exception.handler.log";
         }
      }

      // $FF: synthetic method
      PreJsf2ExceptionHandler(Object x0) {
         this();
      }
   }
}
