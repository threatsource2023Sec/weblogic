package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.CreationException;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalStateException;

public class InterceptorLogger_$logger extends DelegatingBasicLogger implements InterceptorLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = InterceptorLogger_$logger.class.getName();
   private static final String interceptorAnnotationClassNotFound = "WELD-001700: Interceptor annotation class {0} not found, interception based on it is not enabled";
   private static final String invokingNextInterceptorInChain = "WELD-001701: Invoking next interceptor in chain: {0}";
   private static final String nullInterceptorBindings = "WELD-001702: Interceptor.getInterceptorBindings() returned null for {0}";
   private static final String unableToDetermineInterceptedBean = "WELD-001703: Unable to determine the @Intercepted Bean<?> for {0}";
   private static final String interceptedBeanCanOnlyBeInjectedIntoInterceptor = "WELD-001704: @Intercepted Bean<?> can only be injected into an interceptor: {0}";
   private static final String targetInstanceNotCreated = "WELD-001705: Target instance not created - one of the interceptor methods in the AroundConstruct chain did not invoke InvocationContext.proceed() for: {0}";
   private static final String interceptionFactoryNotReusable = "WELD-001706: InterceptionFactory.createInterceptedInstance() may only be called once";
   private static final String interceptionFactoryConfigureInvoked = "WELD-001707: InterceptionFactory.configure() was invoked for AnnotatedType: {0}";
   private static final String interceptionFactoryIgnoreFinalMethodsInvoked = "WELD-001708: InterceptionFactory.ignoreFinalMethods() was invoked for AnnotatedType: {0}. Final methods will be ignored during proxy generation!";
   private static final String interceptionFactoryNotRequired = "WELD-001709: InterceptionFactory skipped wrapper creation for AnnotatedType {0} because no @AroundInvoke interceptor was bound to it.";
   private static final String interceptionFactoryInternalContainerConstruct = "WELD-001710: InterceptionFactory skipped wrapper creation for an internal container construct of type {0}";
   private static final String interceptionFactoryNotOnInstance = "WELD-001711: InterceptionFactory is not supported on interfaces. Check InterceptionFactory<{0}>";
   private static final String catchingDebug = "Catching";

   public InterceptorLogger_$logger(Logger log) {
      super(log);
   }

   public final void interceptorAnnotationClassNotFound(Object param1) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.interceptorAnnotationClassNotFound$str(), param1);
   }

   protected String interceptorAnnotationClassNotFound$str() {
      return "WELD-001700: Interceptor annotation class {0} not found, interception based on it is not enabled";
   }

   public final void invokingNextInterceptorInChain(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.invokingNextInterceptorInChain$str(), param1);
   }

   protected String invokingNextInterceptorInChain$str() {
      return "WELD-001701: Invoking next interceptor in chain: {0}";
   }

   protected String nullInterceptorBindings$str() {
      return "WELD-001702: Interceptor.getInterceptorBindings() returned null for {0}";
   }

   public final DefinitionException nullInterceptorBindings(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.nullInterceptorBindings$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unableToDetermineInterceptedBean(Object injectionPoint) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.unableToDetermineInterceptedBean$str(), injectionPoint);
   }

   protected String unableToDetermineInterceptedBean$str() {
      return "WELD-001703: Unable to determine the @Intercepted Bean<?> for {0}";
   }

   protected String interceptedBeanCanOnlyBeInjectedIntoInterceptor$str() {
      return "WELD-001704: @Intercepted Bean<?> can only be injected into an interceptor: {0}";
   }

   public final IllegalArgumentException interceptedBeanCanOnlyBeInjectedIntoInterceptor(Object injectionPoint) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.interceptedBeanCanOnlyBeInjectedIntoInterceptor$str(), injectionPoint));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String targetInstanceNotCreated$str() {
      return "WELD-001705: Target instance not created - one of the interceptor methods in the AroundConstruct chain did not invoke InvocationContext.proceed() for: {0}";
   }

   public final CreationException targetInstanceNotCreated(Object constructor) {
      CreationException result = new CreationException(MessageFormat.format(this.targetInstanceNotCreated$str(), constructor));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptionFactoryNotReusable$str() {
      return "WELD-001706: InterceptionFactory.createInterceptedInstance() may only be called once";
   }

   public final IllegalStateException interceptionFactoryNotReusable() {
      IllegalStateException result = new IllegalStateException(this.interceptionFactoryNotReusable$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void interceptionFactoryConfigureInvoked(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.interceptionFactoryConfigureInvoked$str(), param1);
   }

   protected String interceptionFactoryConfigureInvoked$str() {
      return "WELD-001707: InterceptionFactory.configure() was invoked for AnnotatedType: {0}";
   }

   public final void interceptionFactoryIgnoreFinalMethodsInvoked(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.interceptionFactoryIgnoreFinalMethodsInvoked$str(), param1);
   }

   protected String interceptionFactoryIgnoreFinalMethodsInvoked$str() {
      return "WELD-001708: InterceptionFactory.ignoreFinalMethods() was invoked for AnnotatedType: {0}. Final methods will be ignored during proxy generation!";
   }

   public final void interceptionFactoryNotRequired(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.interceptionFactoryNotRequired$str(), param1);
   }

   protected String interceptionFactoryNotRequired$str() {
      return "WELD-001709: InterceptionFactory skipped wrapper creation for AnnotatedType {0} because no @AroundInvoke interceptor was bound to it.";
   }

   public final void interceptionFactoryInternalContainerConstruct(Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.interceptionFactoryInternalContainerConstruct$str(), type);
   }

   protected String interceptionFactoryInternalContainerConstruct$str() {
      return "WELD-001710: InterceptionFactory skipped wrapper creation for an internal container construct of type {0}";
   }

   protected String interceptionFactoryNotOnInstance$str() {
      return "WELD-001711: InterceptionFactory is not supported on interfaces. Check InterceptionFactory<{0}>";
   }

   public final IllegalStateException interceptionFactoryNotOnInstance(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.interceptionFactoryNotOnInstance$str(), param1));
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
