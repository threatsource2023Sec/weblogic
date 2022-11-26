package com.sun.faces.context;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseId;
import javax.faces.event.SystemEvent;

public class ExceptionHandlerImpl extends ExceptionHandler {
   private static final Logger LOGGER;
   private static final String LOG_BEFORE_KEY = "jsf.context.exception.handler.log_before";
   private static final String LOG_AFTER_KEY = "jsf.context.exception.handler.log_after";
   private static final String LOG_KEY = "jsf.context.exception.handler.log";
   public static final Level INCIDENT_ERROR;
   private LinkedList unhandledExceptions;
   private LinkedList handledExceptions;
   private ExceptionQueuedEvent handled;
   private boolean errorPagePresent;

   public ExceptionHandlerImpl() {
      this.errorPagePresent = true;
   }

   public ExceptionHandlerImpl(boolean errorPagePresent) {
      this.errorPagePresent = errorPagePresent;
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
            if (this.isRethrown(t)) {
               this.handled = event;
               Throwable unwrapped = this.getRootCause(t);
               if (unwrapped != null) {
                  this.throwIt(context.getContext(), new FacesException(unwrapped.getMessage(), unwrapped));
               } else if (t instanceof FacesException) {
                  this.throwIt(context.getContext(), (FacesException)t);
               } else {
                  this.throwIt(context.getContext(), new FacesException(t.getMessage(), t));
               }

               if (LOGGER.isLoggable(INCIDENT_ERROR)) {
                  this.log(context);
               }
            } else {
               this.log(context);
            }
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

   private void throwIt(FacesContext ctx, FacesException fe) {
      boolean isDevelopment = ctx.isProjectStage(ProjectStage.Development);
      ExternalContext extContext = ctx.getExternalContext();
      Throwable wrapped = fe.getCause();

      try {
         extContext.responseReset();
      } catch (Exception var7) {
         if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Exception when handling error trying to reset the response.", wrapped);
         }
      }

      if (null != wrapped && wrapped instanceof FacesFileNotFoundException) {
         extContext.setResponseStatus(404);
      } else {
         extContext.setResponseStatus(500);
      }

      if (isDevelopment && !this.errorPagePresent) {
         RenderKitUtils.renderHtmlErrorPage(ctx, fe);
      } else {
         if (isDevelopment) {
            ctx.getExternalContext().getRequestMap().put("com.sun.faces.error.view", ctx.getViewRoot());
         }

         throw fe;
      }
   }

   private boolean shouldUnwrap(Class c) {
      return FacesException.class.equals(c) || ELException.class.equals(c);
   }

   private boolean isRethrown(Throwable t) {
      return !(t instanceof AbortProcessingException);
   }

   private void log(ExceptionQueuedEventContext exceptionContext) {
      UIComponent c = exceptionContext.getComponent();
      boolean beforePhase = exceptionContext.inBeforePhase();
      boolean afterPhase = exceptionContext.inAfterPhase();
      PhaseId phaseId = exceptionContext.getPhaseId();
      Throwable t = exceptionContext.getException();
      String key = this.getLoggingKey(beforePhase, afterPhase);
      Level level = LOGGER.isLoggable(INCIDENT_ERROR) && LOGGER.isLoggable(Level.SEVERE) ? INCIDENT_ERROR : Level.SEVERE;
      if (LOGGER.isLoggable(level)) {
         LOGGER.log(level, key, new Object[]{t.getClass().getName(), phaseId.toString(), c != null ? c.getClientId(exceptionContext.getContext()) : "", t.getMessage()});
         if (t.getMessage() != null) {
            LOGGER.log(level, t.getMessage(), t);
         } else {
            LOGGER.log(level, "No associated message", t);
         }
      }

   }

   private String getLoggingKey(boolean beforePhase, boolean afterPhase) {
      if (beforePhase) {
         return "jsf.context.exception.handler.log_before";
      } else {
         return afterPhase ? "jsf.context.exception.handler.log_after" : "jsf.context.exception.handler.log";
      }
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
      INCIDENT_ERROR = Level.parse(Integer.toString(Level.SEVERE.intValue() + 100));
   }
}
