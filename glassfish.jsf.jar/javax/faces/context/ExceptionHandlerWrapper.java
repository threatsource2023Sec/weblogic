package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.FacesWrapper;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.SystemEvent;

public abstract class ExceptionHandlerWrapper extends ExceptionHandler implements FacesWrapper {
   private ExceptionHandler wrapped;

   /** @deprecated */
   @Deprecated
   public ExceptionHandlerWrapper() {
   }

   public ExceptionHandlerWrapper(ExceptionHandler wrapped) {
      this.wrapped = wrapped;
   }

   public ExceptionHandler getWrapped() {
      return this.wrapped;
   }

   public ExceptionQueuedEvent getHandledExceptionQueuedEvent() {
      return this.getWrapped().getHandledExceptionQueuedEvent();
   }

   public void handle() throws FacesException {
      this.getWrapped().handle();
   }

   public boolean isListenerForSource(Object source) {
      return this.getWrapped().isListenerForSource(source);
   }

   public void processEvent(SystemEvent event) throws AbortProcessingException {
      this.getWrapped().processEvent(event);
   }

   public Throwable getRootCause(Throwable t) {
      return this.getWrapped().getRootCause(t);
   }

   public Iterable getHandledExceptionQueuedEvents() {
      return this.getWrapped().getHandledExceptionQueuedEvents();
   }

   public Iterable getUnhandledExceptionQueuedEvents() {
      return this.getWrapped().getUnhandledExceptionQueuedEvents();
   }
}
