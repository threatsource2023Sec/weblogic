package org.jboss.weld.logging;

import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.InvalidObjectException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface EventLogger extends WeldLogger {
   EventLogger LOG = (EventLogger)Logger.getMessageLogger(EventLogger.class, Category.EVENT.getName());

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 400,
      value = "Sending event {0} directly to observer {1}",
      format = Format.MESSAGE_FORMAT
   )
   void asyncFire(Object var1, Object var2);

   @LogMessage(
      level = Level.ERROR
   )
   @Message(
      id = 401,
      value = "Failure while notifying an observer {0} of event {1}.\n {2}",
      format = Format.MESSAGE_FORMAT
   )
   void asyncObserverFailure(Object var1, Object var2, Object var3);

   @Message(
      id = 403,
      value = "Proxy required"
   )
   InvalidObjectException serializationProxyRequired();

   @Message(
      id = 404,
      value = "Conditional observer method cannot be declared by a @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidScopedConditionalObserver(Object var1, Object var2);

   @Message(
      id = 405,
      value = "Observer method cannot have more than one event parameter annotated with @Observes or @ObservesAsync: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleEventParameters(Object var1, Object var2);

   @Message(
      id = 406,
      value = "Observer method cannot have a parameter annotated with @Disposes: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidDisposesParameter(Object var1, Object var2);

   @Message(
      id = 407,
      value = "Observer method cannot be annotated with @Produces: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidProducer(Object var1, Object var2);

   @Message(
      id = 408,
      value = "Observer method cannot be annotated with @Inject, observer methods are automatically injection points: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidInitializer(Object var1, Object var2);

   @Message(
      id = 409,
      value = "Observer method for container lifecycle event can only inject BeanManager: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidInjectionPoint(Object var1, Object var2);

   @Message(
      id = 410,
      value = "Observer method cannot define @WithAnnotations: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidWithAnnotations(Object var1, Object var2);

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 411,
      value = "Observer method {0} receives events for all annotated types. Consider restricting events using @WithAnnotations or a generic type with bounds.",
      format = Format.MESSAGE_FORMAT
   )
   void unrestrictedProcessAnnotatedTypes(Object var1);

   @Message(
      id = 412,
      value = "ObserverMethod.{0}() returned null for {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException observerMethodsMethodReturnsNull(Object var1, Object var2);

   @Message(
      id = 413,
      value = "{0} cannot be replaced by an observer method with a different bean class {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanClassMismatch(ObserverMethod var1, ObserverMethod var2);

   @Message(
      id = 414,
      value = "Observer method for container lifecycle event cannot be asynchronous. {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException asyncContainerLifecycleEventObserver(ObserverMethod var1, Object var2);

   @Message(
      id = 415,
      value = "Custom implementation of observer method does not override either notify(T) or notify(EventContext<T>): {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException notifyMethodNotImplemented(Object var1);

   @Message(
      id = 416,
      value = "None or multiple event parameters declared on: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException noneOrMultipleEventParametersDeclared(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 417,
      value = "The original observed type {0} is not assignable from {1} set by extension {2} - the observer method invocation may result in runtime exception!",
      format = Format.MESSAGE_FORMAT
   )
   void originalObservedTypeIsNotAssignableFrom(Object var1, Object var2, Object var3);

   @Message(
      id = 418,
      value = "Observer method for container lifecycle event cannot be static. {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException staticContainerLifecycleEventObserver(ObserverMethod var1, Object var2);

   @Message(
      id = 419,
      value = "{0} is not a valid notification mode for asynchronous observers",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidNotificationMode(Object var1);

   @Message(
      id = 420,
      value = "Asynchronous observer notification with timeout option requires an implementation of ExecutorServices which provides an instance of ScheduledExecutorServices.",
      format = Format.MESSAGE_FORMAT
   )
   UnsupportedOperationException noScheduledExecutorServicesProvided();

   @Message(
      id = 421,
      value = "Invalid input value for asynchronous observer notification timeout. Has to be parseable String, java.lang.Long or long. Original exception: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidInputValueForTimeout(Object var1);

   @Message(
      id = 422,
      value = "WeldEvent.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldEvent<Object>.",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException selectByTypeOnlyWorksOnObject();
}
