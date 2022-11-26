package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import javax.naming.NamingException;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.contexts.ContextNotActiveException;
import org.jboss.weld.exceptions.AmbiguousResolutionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.UnsatisfiedResolutionException;

public class BeanManagerLogger_$logger extends DelegatingBasicLogger implements BeanManagerLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = BeanManagerLogger_$logger.class.getName();
   private static final String cannotLocateBeanManager = "WELD-001300: Unable to locate BeanManager";
   private static final String invalidQualifier = "WELD-001301: Annotation {0} is not a qualifier";
   private static final String duplicateQualifiers = "WELD-001302: Duplicate qualifiers:  {0}";
   private static final String contextNotActive = "WELD-001303: No active contexts for scope type {0}";
   private static final String duplicateActiveContexts = "WELD-001304: More than one context active for scope type {0}";
   private static final String specifiedTypeNotBeanType = "WELD-001305: The given type {0} is not a type of the bean {1}";
   private static final String unresolvableType = "WELD-001307: Unable to resolve any beans of type {0} with qualifiers {1}";
   private static final String unresolvableElement = "WELD-001308: Unable to resolve any beans for {0}";
   private static final String noDecoratorTypes = "WELD-001310: No decorator types were specified in the set";
   private static final String interceptorBindingsEmpty = "WELD-001311: Interceptor bindings list cannot be empty";
   private static final String duplicateInterceptorBinding = "WELD-001312: Duplicate interceptor binding type {0} found";
   private static final String interceptorResolutionWithNonbindingType = "WELD-001313: Trying to resolve interceptors with non-binding type {0}";
   private static final String nonNormalScope = "WELD-001314: {0} is expected to be a normal scope type";
   private static final String notInterceptorBindingType = "WELD-001316: {0} is not an interceptor binding type";
   private static final String notStereotype = "WELD-001317: {0} is not a stereotype";
   private static final String ambiguousBeansForDependency = "WELD-001318: Cannot resolve an ambiguous dependency between: {0}";
   private static final String nullBeanManagerId = "WELD-001319: Bean manager ID must not be null";
   private static final String noInstanceOfExtension = "WELD-001325: No instance of an extension {0} registered with the deployment";
   private static final String cannotCreateBeanAttributesForIncorrectAnnotatedMember = "WELD-001326: Cannot create bean attributes - the argument must be either an AnnotatedField or AnnotatedMethod but {0} is not";
   private static final String ambiguousBeanManager = "WELD-001327: Unable to identify the correct BeanManager. The calling class {0} is placed in multiple bean archives";
   private static final String unsatisfiedBeanManager = "WELD-001328: Unable to identify the correct BeanManager. The calling class {0} is not placed in bean archive";
   private static final String unableToIdentifyBeanManager = "WELD-001329: Unable to identify the correct BeanManager";
   private static final String beanManagerNotAvailable = "WELD-001330: BeanManager is not available.";
   private static final String nullDeclaringBean = "WELD-001331: Declaring bean cannot be null for the non-static member {0}";
   private static final String methodNotAvailableDuringInitialization = "WELD-001332: BeanManager method {0} is not available during application initialization. Container state: {1}";
   private static final String methodNotAvailableAfterShutdown = "WELD-001333: BeanManager method {0} is not available after shutdown";
   private static final String injectionPointHasUnsatisfiedDependencies = "WELD-001334: Unsatisfied dependencies for type {1} with qualifiers {0} {2}";
   private static final String injectionPointHasAmbiguousDependencies = "WELD-001335: Ambiguous dependencies for type {1} with qualifiers {0}\n Possible dependencies: {2}";
   private static final String unableToConfigureInjectionTargetFactory = "WELD-001336: InjectionTargetFactory.configure() may not be called after createInjectionTarget() invocation. AnnotatedType used: {0}";
   private static final String catchingDebug = "Catching";

   public BeanManagerLogger_$logger(Logger log) {
      super(log);
   }

   protected String cannotLocateBeanManager$str() {
      return "WELD-001300: Unable to locate BeanManager";
   }

   public final NamingException cannotLocateBeanManager() {
      NamingException result = new NamingException(String.format(this.cannotLocateBeanManager$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidQualifier$str() {
      return "WELD-001301: Annotation {0} is not a qualifier";
   }

   public final IllegalArgumentException invalidQualifier(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidQualifier$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String duplicateQualifiers$str() {
      return "WELD-001302: Duplicate qualifiers:  {0}";
   }

   public final IllegalArgumentException duplicateQualifiers(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.duplicateQualifiers$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String contextNotActive$str() {
      return "WELD-001303: No active contexts for scope type {0}";
   }

   public final ContextNotActiveException contextNotActive(Object param1) {
      ContextNotActiveException result = new ContextNotActiveException(MessageFormat.format(this.contextNotActive$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String duplicateActiveContexts$str() {
      return "WELD-001304: More than one context active for scope type {0}";
   }

   public final IllegalStateException duplicateActiveContexts(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.duplicateActiveContexts$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String specifiedTypeNotBeanType$str() {
      return "WELD-001305: The given type {0} is not a type of the bean {1}";
   }

   public final IllegalArgumentException specifiedTypeNotBeanType(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.specifiedTypeNotBeanType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unresolvableType$str() {
      return "WELD-001307: Unable to resolve any beans of type {0} with qualifiers {1}";
   }

   public final UnsatisfiedResolutionException unresolvableType(Object param1, Object param2) {
      UnsatisfiedResolutionException result = new UnsatisfiedResolutionException(MessageFormat.format(this.unresolvableType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unresolvableElement$str() {
      return "WELD-001308: Unable to resolve any beans for {0}";
   }

   public final UnsatisfiedResolutionException unresolvableElement(Object param1) {
      UnsatisfiedResolutionException result = new UnsatisfiedResolutionException(MessageFormat.format(this.unresolvableElement$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noDecoratorTypes$str() {
      return "WELD-001310: No decorator types were specified in the set";
   }

   public final IllegalArgumentException noDecoratorTypes() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.noDecoratorTypes$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorBindingsEmpty$str() {
      return "WELD-001311: Interceptor bindings list cannot be empty";
   }

   public final IllegalArgumentException interceptorBindingsEmpty() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.interceptorBindingsEmpty$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String duplicateInterceptorBinding$str() {
      return "WELD-001312: Duplicate interceptor binding type {0} found";
   }

   public final IllegalArgumentException duplicateInterceptorBinding(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.duplicateInterceptorBinding$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorResolutionWithNonbindingType$str() {
      return "WELD-001313: Trying to resolve interceptors with non-binding type {0}";
   }

   public final IllegalArgumentException interceptorResolutionWithNonbindingType(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.interceptorResolutionWithNonbindingType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonNormalScope$str() {
      return "WELD-001314: {0} is expected to be a normal scope type";
   }

   public final String nonNormalScope(Object param1) {
      return MessageFormat.format(this.nonNormalScope$str(), param1);
   }

   protected String notInterceptorBindingType$str() {
      return "WELD-001316: {0} is not an interceptor binding type";
   }

   public final IllegalArgumentException notInterceptorBindingType(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.notInterceptorBindingType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notStereotype$str() {
      return "WELD-001317: {0} is not a stereotype";
   }

   public final IllegalArgumentException notStereotype(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.notStereotype$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ambiguousBeansForDependency$str() {
      return "WELD-001318: Cannot resolve an ambiguous dependency between: {0}";
   }

   public final AmbiguousResolutionException ambiguousBeansForDependency(Object param1) {
      AmbiguousResolutionException result = new AmbiguousResolutionException(MessageFormat.format(this.ambiguousBeansForDependency$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nullBeanManagerId$str() {
      return "WELD-001319: Bean manager ID must not be null";
   }

   public final IllegalArgumentException nullBeanManagerId() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.nullBeanManagerId$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noInstanceOfExtension$str() {
      return "WELD-001325: No instance of an extension {0} registered with the deployment";
   }

   public final IllegalArgumentException noInstanceOfExtension(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.noInstanceOfExtension$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotCreateBeanAttributesForIncorrectAnnotatedMember$str() {
      return "WELD-001326: Cannot create bean attributes - the argument must be either an AnnotatedField or AnnotatedMethod but {0} is not";
   }

   public final IllegalArgumentException cannotCreateBeanAttributesForIncorrectAnnotatedMember(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotCreateBeanAttributesForIncorrectAnnotatedMember$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ambiguousBeanManager$str() {
      return "WELD-001327: Unable to identify the correct BeanManager. The calling class {0} is placed in multiple bean archives";
   }

   public final IllegalStateException ambiguousBeanManager(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.ambiguousBeanManager$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unsatisfiedBeanManager$str() {
      return "WELD-001328: Unable to identify the correct BeanManager. The calling class {0} is not placed in bean archive";
   }

   public final IllegalStateException unsatisfiedBeanManager(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unsatisfiedBeanManager$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToIdentifyBeanManager$str() {
      return "WELD-001329: Unable to identify the correct BeanManager";
   }

   public final IllegalStateException unableToIdentifyBeanManager() {
      IllegalStateException result = new IllegalStateException(String.format(this.unableToIdentifyBeanManager$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanManagerNotAvailable$str() {
      return "WELD-001330: BeanManager is not available.";
   }

   public final IllegalStateException beanManagerNotAvailable() {
      IllegalStateException result = new IllegalStateException(String.format(this.beanManagerNotAvailable$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nullDeclaringBean$str() {
      return "WELD-001331: Declaring bean cannot be null for the non-static member {0}";
   }

   public final IllegalArgumentException nullDeclaringBean(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.nullDeclaringBean$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String methodNotAvailableDuringInitialization$str() {
      return "WELD-001332: BeanManager method {0} is not available during application initialization. Container state: {1}";
   }

   public final IllegalStateException methodNotAvailableDuringInitialization(Object param1, Object state) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.methodNotAvailableDuringInitialization$str(), param1, state));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String methodNotAvailableAfterShutdown$str() {
      return "WELD-001333: BeanManager method {0} is not available after shutdown";
   }

   public final IllegalStateException methodNotAvailableAfterShutdown(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.methodNotAvailableAfterShutdown$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasUnsatisfiedDependencies$str() {
      return "WELD-001334: Unsatisfied dependencies for type {1} with qualifiers {0} {2}";
   }

   public final UnsatisfiedResolutionException injectionPointHasUnsatisfiedDependencies(Object param1, Object param2, Object param3) {
      UnsatisfiedResolutionException result = new UnsatisfiedResolutionException(MessageFormat.format(this.injectionPointHasUnsatisfiedDependencies$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasAmbiguousDependencies$str() {
      return "WELD-001335: Ambiguous dependencies for type {1} with qualifiers {0}\n Possible dependencies: {2}";
   }

   public final AmbiguousResolutionException injectionPointHasAmbiguousDependencies(Object param1, Object param2, Object param3) {
      AmbiguousResolutionException result = new AmbiguousResolutionException(MessageFormat.format(this.injectionPointHasAmbiguousDependencies$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToConfigureInjectionTargetFactory$str() {
      return "WELD-001336: InjectionTargetFactory.configure() may not be called after createInjectionTarget() invocation. AnnotatedType used: {0}";
   }

   public final IllegalStateException unableToConfigureInjectionTargetFactory(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToConfigureInjectionTargetFactory$str(), param1));
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
