package com.sun.faces.context;

import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseId;
import javax.faces.event.SystemEvent;

public class AjaxExceptionHandlerImpl extends ExceptionHandlerWrapper {
   private static final Logger LOGGER;
   private static final String LOG_BEFORE_KEY = "jsf.context.exception.handler.log_before";
   private static final String LOG_AFTER_KEY = "jsf.context.exception.handler.log_after";
   private static final String LOG_KEY = "jsf.context.exception.handler.log";
   private LinkedList unhandledExceptions;
   private LinkedList handledExceptions;
   private ExceptionQueuedEvent handled;

   public AjaxExceptionHandlerImpl(ExceptionHandler handler) {
      super(handler);
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
                  this.handlePartialResponseError(context.getContext(), unwrapped);
               } else if (t instanceof FacesException) {
                  this.handlePartialResponseError(context.getContext(), t);
               } else {
                  this.handlePartialResponseError(context.getContext(), new FacesException(t.getMessage(), t));
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

   public void processEvent(SystemEvent event) throws AbortProcessingException {
      if (event != null) {
         if (this.unhandledExceptions == null) {
            this.unhandledExceptions = new LinkedList();
         }

         this.unhandledExceptions.add((ExceptionQueuedEvent)event);
      }

   }

   public Iterable getUnhandledExceptionQueuedEvents() {
      return (Iterable)(this.unhandledExceptions != null ? this.unhandledExceptions : Collections.emptyList());
   }

   public Iterable getHandledExceptionQueuedEvents() {
      return (Iterable)(this.handledExceptions != null ? this.handledExceptions : Collections.emptyList());
   }

   private void handlePartialResponseError(FacesContext context, Throwable t) {
      if (!context.getResponseComplete()) {
         try {
            ExternalContext extContext = context.getExternalContext();
            extContext.setResponseContentType("text/xml");
            extContext.addResponseHeader("Cache-Control", "no-cache");
            PartialResponseWriter writer = context.getPartialViewContext().getPartialResponseWriter();
            writer.startDocument();
            writer.startError(t.getClass().toString());
            String msg;
            if (context.isProjectStage(ProjectStage.Production)) {
               msg = "See your server log for more information";
            } else if (t.getCause() != null) {
               msg = t.getCause().getMessage();
            } else {
               msg = t.getMessage();
            }

            writer.write(msg != null ? msg : "");
            writer.endError();
            writer.endDocument();
            if (LOGGER.isLoggable(Level.SEVERE)) {
               StringWriter sw = new StringWriter();
               PrintWriter pw = new PrintWriter(sw);
               t.printStackTrace(pw);
               LOGGER.log(Level.SEVERE, sw.toString());
            }

            context.responseComplete();
         } catch (IOException var8) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var8.toString(), var8);
            }
         }

      }
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
      if (LOGGER.isLoggable(Level.SEVERE)) {
         LOGGER.log(Level.SEVERE, key, new Object[]{t.getClass().getName(), phaseId.toString(), c != null ? c.getClientId(exceptionContext.getContext()) : "", t.getMessage()});
         LOGGER.log(Level.SEVERE, t.getMessage(), t);
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
   }
}
