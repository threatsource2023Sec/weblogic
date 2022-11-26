package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.InvalidObjectException;

public class EventLogger_$logger extends DelegatingBasicLogger implements EventLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = EventLogger_$logger.class.getName();
   private static final String asyncFire = "WELD-000400: Sending event {0} directly to observer {1}";
   private static final String asyncObserverFailure = "WELD-000401: Failure while notifying an observer {0} of event {1}.\n {2}";
   private static final String serializationProxyRequired = "WELD-000403: Proxy required";
   private static final String invalidScopedConditionalObserver = "WELD-000404: Conditional observer method cannot be declared by a @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace:";
   private static final String multipleEventParameters = "WELD-000405: Observer method cannot have more than one event parameter annotated with @Observes or @ObservesAsync: {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidDisposesParameter = "WELD-000406: Observer method cannot have a parameter annotated with @Disposes: {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidProducer = "WELD-000407: Observer method cannot be annotated with @Produces: {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidInitializer = "WELD-000408: Observer method cannot be annotated with @Inject, observer methods are automatically injection points: {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidInjectionPoint = "WELD-000409: Observer method for container lifecycle event can only inject BeanManager: {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidWithAnnotations = "WELD-000410: Observer method cannot define @WithAnnotations: {0}\n\tat {1}\n  StackTrace:";
   private static final String unrestrictedProcessAnnotatedTypes = "WELD-000411: Observer method {0} receives events for all annotated types. Consider restricting events using @WithAnnotations or a generic type with bounds.";
   private static final String observerMethodsMethodReturnsNull = "WELD-000412: ObserverMethod.{0}() returned null for {1}";
   private static final String beanClassMismatch = "WELD-000413: {0} cannot be replaced by an observer method with a different bean class {1}";
   private static final String asyncContainerLifecycleEventObserver = "WELD-000414: Observer method for container lifecycle event cannot be asynchronous. {0}\n\tat {1}\n  StackTrace:";
   private static final String notifyMethodNotImplemented = "WELD-000415: Custom implementation of observer method does not override either notify(T) or notify(EventContext<T>): {0}";
   private static final String noneOrMultipleEventParametersDeclared = "WELD-000416: None or multiple event parameters declared on: {0}\n\tat {1}\n  StackTrace:";
   private static final String originalObservedTypeIsNotAssignableFrom = "WELD-000417: The original observed type {0} is not assignable from {1} set by extension {2} - the observer method invocation may result in runtime exception!";
   private static final String staticContainerLifecycleEventObserver = "WELD-000418: Observer method for container lifecycle event cannot be static. {0}\n\tat {1}\n  StackTrace:";
   private static final String invalidNotificationMode = "WELD-000419: {0} is not a valid notification mode for asynchronous observers";
   private static final String noScheduledExecutorServicesProvided = "WELD-000420: Asynchronous observer notification with timeout option requires an implementation of ExecutorServices which provides an instance of ScheduledExecutorServices.";
   private static final String invalidInputValueForTimeout = "WELD-000421: Invalid input value for asynchronous observer notification timeout. Has to be parseable String, java.lang.Long or long. Original exception: {0}";
   private static final String selectByTypeOnlyWorksOnObject = "WELD-000422: WeldEvent.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldEvent<Object>.";
   private static final String catchingDebug = "Catching";

   public EventLogger_$logger(Logger log) {
      super(log);
   }

   public final void asyncFire(Object param1, Object param2) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.asyncFire$str(), param1, param2);
   }

   protected String asyncFire$str() {
      return "WELD-000400: Sending event {0} directly to observer {1}";
   }

   public final void asyncObserverFailure(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.ERROR, (Throwable)null, this.asyncObserverFailure$str(), param1, param2, param3);
   }

   protected String asyncObserverFailure$str() {
      return "WELD-000401: Failure while notifying an observer {0} of event {1}.\n {2}";
   }

   protected String serializationProxyRequired$str() {
      return "WELD-000403: Proxy required";
   }

   public final InvalidObjectException serializationProxyRequired() {
      InvalidObjectException result = new InvalidObjectException(String.format(this.serializationProxyRequired$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidScopedConditionalObserver$str() {
      return "WELD-000404: Conditional observer method cannot be declared by a @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidScopedConditionalObserver(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidScopedConditionalObserver$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleEventParameters$str() {
      return "WELD-000405: Observer method cannot have more than one event parameter annotated with @Observes or @ObservesAsync: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException multipleEventParameters(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleEventParameters$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidDisposesParameter$str() {
      return "WELD-000406: Observer method cannot have a parameter annotated with @Disposes: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidDisposesParameter(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidDisposesParameter$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidProducer$str() {
      return "WELD-000407: Observer method cannot be annotated with @Produces: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidProducer(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidProducer$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidInitializer$str() {
      return "WELD-000408: Observer method cannot be annotated with @Inject, observer methods are automatically injection points: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidInitializer(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidInitializer$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidInjectionPoint$str() {
      return "WELD-000409: Observer method for container lifecycle event can only inject BeanManager: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidInjectionPoint(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidInjectionPoint$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidWithAnnotations$str() {
      return "WELD-000410: Observer method cannot define @WithAnnotations: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException invalidWithAnnotations(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidWithAnnotations$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unrestrictedProcessAnnotatedTypes(Object param1) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.unrestrictedProcessAnnotatedTypes$str(), param1);
   }

   protected String unrestrictedProcessAnnotatedTypes$str() {
      return "WELD-000411: Observer method {0} receives events for all annotated types. Consider restricting events using @WithAnnotations or a generic type with bounds.";
   }

   protected String observerMethodsMethodReturnsNull$str() {
      return "WELD-000412: ObserverMethod.{0}() returned null for {1}";
   }

   public final DefinitionException observerMethodsMethodReturnsNull(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.observerMethodsMethodReturnsNull$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanClassMismatch$str() {
      return "WELD-000413: {0} cannot be replaced by an observer method with a different bean class {1}";
   }

   public final DefinitionException beanClassMismatch(ObserverMethod originalObserverMethod, ObserverMethod observerMethod) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanClassMismatch$str(), originalObserverMethod, observerMethod));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String asyncContainerLifecycleEventObserver$str() {
      return "WELD-000414: Observer method for container lifecycle event cannot be asynchronous. {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException asyncContainerLifecycleEventObserver(ObserverMethod observer, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.asyncContainerLifecycleEventObserver$str(), observer, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notifyMethodNotImplemented$str() {
      return "WELD-000415: Custom implementation of observer method does not override either notify(T) or notify(EventContext<T>): {0}";
   }

   public final DefinitionException notifyMethodNotImplemented(Object observer) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.notifyMethodNotImplemented$str(), observer));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noneOrMultipleEventParametersDeclared$str() {
      return "WELD-000416: None or multiple event parameters declared on: {0}\n\tat {1}\n  StackTrace:";
   }

   public final IllegalArgumentException noneOrMultipleEventParametersDeclared(Object method, Object stackElement) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.noneOrMultipleEventParametersDeclared$str(), method, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void originalObservedTypeIsNotAssignableFrom(Object originalObservedType, Object observedType, Object extension) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.originalObservedTypeIsNotAssignableFrom$str(), originalObservedType, observedType, extension);
   }

   protected String originalObservedTypeIsNotAssignableFrom$str() {
      return "WELD-000417: The original observed type {0} is not assignable from {1} set by extension {2} - the observer method invocation may result in runtime exception!";
   }

   protected String staticContainerLifecycleEventObserver$str() {
      return "WELD-000418: Observer method for container lifecycle event cannot be static. {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException staticContainerLifecycleEventObserver(ObserverMethod observer, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.staticContainerLifecycleEventObserver$str(), observer, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidNotificationMode$str() {
      return "WELD-000419: {0} is not a valid notification mode for asynchronous observers";
   }

   public final IllegalArgumentException invalidNotificationMode(Object mode) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidNotificationMode$str(), mode));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noScheduledExecutorServicesProvided$str() {
      return "WELD-000420: Asynchronous observer notification with timeout option requires an implementation of ExecutorServices which provides an instance of ScheduledExecutorServices.";
   }

   public final UnsupportedOperationException noScheduledExecutorServicesProvided() {
      UnsupportedOperationException result = new UnsupportedOperationException(this.noScheduledExecutorServicesProvided$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidInputValueForTimeout$str() {
      return "WELD-000421: Invalid input value for asynchronous observer notification timeout. Has to be parseable String, java.lang.Long or long. Original exception: {0}";
   }

   public final IllegalArgumentException invalidInputValueForTimeout(Object nfe) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidInputValueForTimeout$str(), nfe));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String selectByTypeOnlyWorksOnObject$str() {
      return "WELD-000422: WeldEvent.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldEvent<Object>.";
   }

   public final IllegalStateException selectByTypeOnlyWorksOnObject() {
      IllegalStateException result = new IllegalStateException(this.selectByTypeOnlyWorksOnObject$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
