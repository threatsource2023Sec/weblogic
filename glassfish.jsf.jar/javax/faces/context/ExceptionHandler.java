package javax.faces.context;

import javax.faces.FacesException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public abstract class ExceptionHandler implements SystemEventListener {
   public abstract void handle() throws FacesException;

   public abstract ExceptionQueuedEvent getHandledExceptionQueuedEvent();

   public abstract Iterable getUnhandledExceptionQueuedEvents();

   public abstract Iterable getHandledExceptionQueuedEvents();

   public abstract void processEvent(SystemEvent var1) throws AbortProcessingException;

   public abstract boolean isListenerForSource(Object var1);

   public abstract Throwable getRootCause(Throwable var1);
}
