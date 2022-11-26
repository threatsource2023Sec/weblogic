package org.jboss.weld.logging;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.InconsistentSpecializationException;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.exceptions.UnserializableDependencyException;

public class ValidatorLogger_$logger extends DelegatingBasicLogger implements ValidatorLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ValidatorLogger_$logger.class.getName();
   private static final String beanSpecializedTooManyTimes = "WELD-001401: Two beans cannot specialize the same bean {0}";
   private static final String passivatingBeanWithNonserializableInterceptor = "WELD-001402: The bean {0} declares a passivating scope but has a non-serializable interceptor {1}";
   private static final String passivatingBeanWithNonserializableDecorator = "WELD-001403: The bean {0} declares a passivating scope but has a non-serializable decorator {1}";
   private static final String newWithQualifiers = "WELD-001404: The injection point is annotated with @New which cannot be combined with other qualifiers: {0}\n\tat {1}\n  StackTrace";
   private static final String injectionIntoNonBean = "WELD-001405: Cannot inject {0} in a class which is not a bean\n\tat {1}\n  StackTrace";
   private static final String injectionIntoNonDependentBean = "WELD-001406: Cannot inject injection point metadata in a non @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace";
   private static final String injectionPointWithTypeVariable = "WELD-001407: Cannot declare an injection point with a type variable: {0}\n\tat {1}\n  StackTrace";
   private static final String injectionPointHasUnsatisfiedDependencies = "WELD-001408: Unsatisfied dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n{4}";
   private static final String injectionPointHasAmbiguousDependencies = "WELD-001409: Ambiguous dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n  Possible dependencies: {4}\n";
   private static final String injectionPointHasNonProxyableDependencies = "WELD-001410: The injection point has non-proxyable dependencies: {0}\n\tat {1}\n  StackTrace";
   private static final String injectionPointHasNonSerializableDependency = "WELD-001413: The bean {0} declares a passivating scope but has a non-passivation-capable dependency {1}";
   private static final String ambiguousElName = "WELD-001414: Bean name is ambiguous. Name {0} resolves to beans: {1}";
   private static final String beanNameIsPrefix = "WELD-001415: Bean name is identical to a bean name prefix used elsewhere. Name {0}";
   private static final String interceptorSpecifiedTwice = "WELD-001416: Enabled interceptor class {0} specified twice:\n  - {1},\n  - {2}";
   private static final String interceptorClassDoesNotMatchInterceptorBean = "WELD-001417: Enabled interceptor class {0} ({1}) does not match an interceptor bean: the class is not found, or not annotated with @Interceptor and still not registered through a portable extension, or not annotated with @Dependent inside an implicit bean archive";
   private static final String decoratorSpecifiedTwice = "WELD-001418: Enabled decorator class {0} specified twice:\n  - {1},\n  - {2}";
   private static final String decoratorClassNotBeanClassOfDecorator = "WELD-001419: Enabled decorator class {0} is not the bean class of at least one decorator bean (detected decorator beans: {1})";
   private static final String alternativeStereotypeNotStereotype = "WELD-001420: Enabled alternative {0} is not a stereotype";
   private static final String alternativeStereotypeSpecifiedMultipleTimes = "WELD-001421: Cannot enable the same alternative stereotype {0} in beans.xml:\n  - {1},\n  - {2}";
   private static final String alternativeBeanClassNotAnnotated = "WELD-001422: Enabled alternative class {0} ({1}) does not match any bean, or is not annotated with @Alternative or an @Alternative stereotype, or does not declare a producer annotated with @Alternative or an @Alternative stereotype";
   private static final String disposalMethodsWithoutProducer = "WELD-001424: The following disposal methods were declared but did not resolve to a producer method: {0}";
   private static final String injectionPointHasWildcard = "WELD-001425: An injection point cannot have a wildcard type parameter: {0}\n\tat {1}\n  StackTrace";
   private static final String injectionPointMustHaveTypeParameter = "WELD-001426: An injection point must have a type parameter: {0}\n\tat {1}\n  StackTrace";
   private static final String nonFieldInjectionPointCannotUseNamed = "WELD-001427: Only field injection points can use the @Named qualifier with no value: {0}\n\tat {1}\n  StackTrace";
   private static final String decoratorsCannotHaveProducerMethods = "WELD-001428: A decorator cannot have producer methods, but at least one was found on {0}.";
   private static final String decoratorsCannotHaveProducerFields = "WELD-001429: A decorator cannot have producer fields, but at least one was found on {0}.";
   private static final String decoratorsCannotHaveDisposerMethods = "WELD-001430: A decorator cannot have disposer methods, but at least one was found on {0}.";
   private static final String interceptorsCannotHaveProducerMethods = "WELD-001431: An interceptor cannot have producer methods, but at least one was found on {0}.";
   private static final String interceptorsCannotHaveProducerFields = "WELD-001432: An interceptor cannot have producer fields, but at least one was found on {0}.";
   private static final String interceptorsCannotHaveDisposerMethods = "WELD-001433: An interceptor cannot have disposer methods, but at least one was found on {0}.";
   private static final String notProxyableUnknown = "WELD-001434: Normal scoped bean {0} is not proxyable for an unknown reason - {1}.";
   private static final String notProxyableNoConstructor = "WELD-001435: Normal scoped bean {0} is not proxyable because it has no no-args constructor - {1}.";
   private static final String notProxyablePrivateConstructor = "WELD-001436: Type {0} is not proxyable because it has a private constructor {1} - {2}.";
   private static final String notProxyableFinalType = "WELD-001437: Bean type {0} is not proxyable because it is final - {1}.";
   private static final String notProxyablePrimitive = "WELD-001438: Bean type {0} is not proxyable because it is a primitive - {1}.";
   private static final String notProxyableArrayType = "WELD-001439: Bean type {0} is not proxyable because it is an array type - {1}.";
   private static final String scopeAnnotationOnInjectionPoint = "WELD-001440: Scope type {0} used on injection point {1}\n\tat {2}\n  StackTrace";
   private static final String alternativeBeanClassNotClass = "WELD-001441: Enabled alternative {0} is not a class";
   private static final String alternativeStereotypeNotAnnotated = "WELD-001442: Enabled alternative {0} is not annotated @Alternative";
   private static final String pseudoScopedBeanHasCircularReferences = "WELD-001443: Pseudo scoped bean has circular dependencies. Dependency path: {0}";
   private static final String interceptorsCannotHaveObserverMethods = "WELD-001445: An interceptor cannot have observer methods, but at least one was found on {0}.";
   private static final String decoratorsCannotHaveObserverMethods = "WELD-001446: A decorator cannot have observer methods, but at least one was found on {0}.";
   private static final String interceptorMethodDoesNotReturnObject = "WELD-001447: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3}\n\tat {4}\n  StackTrace";
   private static final String interceptorMethodDoesNotHaveExactlyOneParameter = "WELD-001448: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have exactly one parameter\n\tat {3}\n  StackTrace";
   private static final String interceptorMethodDoesNotHaveCorrectTypeOfParameter = "WELD-001449: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but its single parameter is not a {3}\n\tat {4}\n  StackTrace";
   private static final String userTransactionInjectionIntoBeanWithContainerManagedTransactions = "WELD-001451: javax.transaction.UserTransaction cannot be injected into an enterprise bean with container-managed transactions: {0}\n\tat {1}\n  StackTrace";
   private static final String invalidBeanMetadataInjectionPointType = "WELD-001452: {0} is not a valid type for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace";
   private static final String invalidBeanMetadataInjectionPointTypeArgument = "WELD-001453: {0} is not a valid type argument for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace";
   private static final String invalidBeanMetadataInjectionPointQualifier = "WELD-001454: {0} cannot be used at a Bean metadata injection point of a bean which is not {1}, {2}\n\tat {3}\n  StackTrace";
   private static final String noDecoratedTypes = "WELD-001455: {0} does not declare any decorated types.";
   private static final String argumentNull1 = "WELD-001456: Argument {0} must not be null";
   private static final String alternativeClassSpecifiedMultipleTimes = "WELD-001457: Cannot enable the same alternative class {0} in beans.xml:\n  - {1},\n  - {2}";
   private static final String beanWithPassivatingScopeNotPassivationCapable = "WELD-001463: Bean declaring a passivating scope must be passivation capable.  Bean:  {0}";
   private static final String builtinBeanWithNonserializableDecorator = "WELD-001465: {0} for a built-in bean {1} must be passivation capable.";
   private static final String injectionIntoDisposerMethod = "WELD-001466: Invalid injection point found in a disposer method: {0}\n\tat {1}\n  StackTrace";
   private static final String interceptorMethodDoesNotReturnObjectOrVoid = "WELD-001467: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3} or {4}.\n\tat {5}\n  StackTrace";
   private static final String interceptorMethodDoesNotHaveVoidReturnType = "WELD-001468: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have a {3} return type.\n\tat {4}\n  StackTrace";
   private static final String interceptorMethodDoesNotHaveZeroParameters = "WELD-001469: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have zero parameters.\n";
   private static final String interceptorMethodShouldNotThrowCheckedExceptions = "WELD-001471: Interceptor method {0} defined on class {1} is not defined according to the specification. It should not throw {2}, which is a checked exception.\n\tat {3}\n  StackTrace";
   private static final String eventMetadataInjectedOutsideOfObserver = "WELD-001472: EventMetadata can only be injected into an observer method: {0}\n\tat {1}\n  StackTrace";
   private static final String beanNotPassivationCapable = "WELD-001473: javax.enterprise.inject.spi.Bean implementation {0} declared a normal scope but does not implement javax.enterprise.inject.spi.PassivationCapable. It won'''t be possible to inject this bean into a bean with a passivating scope (@SessionScoped, @ConversationScoped). This can be fixed by assigning the Bean implementation a unique id by implementing the PassivationCapable interface.";
   private static final String unsatisfiedDependencyBecauseClassIgnored = "WELD-001474: Class {0} is on the classpath, but was ignored because a class it references was not found: {1}.\n";
   private static final String unsatisfiedDependencyBecauseQualifiersDontMatch = "WELD-001475: The following beans match by type, but none have matching qualifiers:{0}\n";
   private static final String interceptorOrDecoratorMustBeDependent = "WELD-001476: {0} must be @Dependent";
   private static final String interceptorDecoratorInjectionPointHasNonSerializableDependency = "WELD-001477: The bean {0} declares a passivating scope but has a(n) {1} with a non-passivation-capable dependency {2}";
   private static final String interceptorEnabledForApplicationAndBeanArchive = "WELD-001478: Interceptor {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.";
   private static final String decoratorEnabledForApplicationAndBeanArchive = "WELD-001479: Decorator {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.";
   private static final String notProxyableFinalMethod = "WELD-001480: Bean type {0} is not proxyable because it contains a final method {1} - {2}.";
   private static final String notProxyableFinalMethodIgnored = "WELD-001481: Final method will be ignored during proxy generation and should never be invoked upon the proxy instance! {0} - {1}.";
   private static final String invalidInterceptionFactoryInjectionPoint = "WELD-001482: InterceptionFactory can only be injected in a parameter of a producer method: {0}\n\tat {1}\n  StackTrace";
   private static final String argumentNull0 = "WELD-001483: Argument must not be null";
   private static final String interceptorMethodDeclaresMultipleParameters = "WELD-001485: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} and it declares more than one parameter.\n\tat {3}\n  StackTrace";
   private static final String catchingDebug = "Catching";

   public ValidatorLogger_$logger(Logger log) {
      super(log);
   }

   protected String beanSpecializedTooManyTimes$str() {
      return "WELD-001401: Two beans cannot specialize the same bean {0}";
   }

   public final InconsistentSpecializationException beanSpecializedTooManyTimes(Object param1) {
      InconsistentSpecializationException result = new InconsistentSpecializationException(MessageFormat.format(this.beanSpecializedTooManyTimes$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingBeanWithNonserializableInterceptor$str() {
      return "WELD-001402: The bean {0} declares a passivating scope but has a non-serializable interceptor {1}";
   }

   public final DeploymentException passivatingBeanWithNonserializableInterceptor(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.passivatingBeanWithNonserializableInterceptor$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingBeanWithNonserializableDecorator$str() {
      return "WELD-001403: The bean {0} declares a passivating scope but has a non-serializable decorator {1}";
   }

   public final UnserializableDependencyException passivatingBeanWithNonserializableDecorator(Object param1, Object param2) {
      UnserializableDependencyException result = new UnserializableDependencyException(MessageFormat.format(this.passivatingBeanWithNonserializableDecorator$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String newWithQualifiers$str() {
      return "WELD-001404: The injection point is annotated with @New which cannot be combined with other qualifiers: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException newWithQualifiers(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.newWithQualifiers$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionIntoNonBean$str() {
      return "WELD-001405: Cannot inject {0} in a class which is not a bean\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionIntoNonBean(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionIntoNonBean$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionIntoNonDependentBean$str() {
      return "WELD-001406: Cannot inject injection point metadata in a non @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionIntoNonDependentBean(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionIntoNonDependentBean$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointWithTypeVariable$str() {
      return "WELD-001407: Cannot declare an injection point with a type variable: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionPointWithTypeVariable(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionPointWithTypeVariable$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasUnsatisfiedDependencies$str() {
      return "WELD-001408: Unsatisfied dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n{4}";
   }

   public final DeploymentException injectionPointHasUnsatisfiedDependencies(Object param1, Object param2, Object param3, Object param4, Object param5) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.injectionPointHasUnsatisfiedDependencies$str(), param1, param2, param3, param4, param5));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasAmbiguousDependencies$str() {
      return "WELD-001409: Ambiguous dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n  Possible dependencies: {4}\n";
   }

   public final DeploymentException injectionPointHasAmbiguousDependencies(Object param1, Object param2, Object param3, Object param4, Object param5) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.injectionPointHasAmbiguousDependencies$str(), param1, param2, param3, param4, param5));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasNonProxyableDependencies$str() {
      return "WELD-001410: The injection point has non-proxyable dependencies: {0}\n\tat {1}\n  StackTrace";
   }

   public final DeploymentException injectionPointHasNonProxyableDependencies(Object param1, Object stackElement, Throwable cause) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.injectionPointHasNonProxyableDependencies$str(), param1, stackElement), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasNonSerializableDependency$str() {
      return "WELD-001413: The bean {0} declares a passivating scope but has a non-passivation-capable dependency {1}";
   }

   public final UnserializableDependencyException injectionPointHasNonSerializableDependency(Object param1, Object param2) {
      UnserializableDependencyException result = new UnserializableDependencyException(MessageFormat.format(this.injectionPointHasNonSerializableDependency$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ambiguousElName$str() {
      return "WELD-001414: Bean name is ambiguous. Name {0} resolves to beans: {1}";
   }

   public final DeploymentException ambiguousElName(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.ambiguousElName$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanNameIsPrefix$str() {
      return "WELD-001415: Bean name is identical to a bean name prefix used elsewhere. Name {0}";
   }

   public final DeploymentException beanNameIsPrefix(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.beanNameIsPrefix$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorSpecifiedTwice$str() {
      return "WELD-001416: Enabled interceptor class {0} specified twice:\n  - {1},\n  - {2}";
   }

   public final DeploymentException interceptorSpecifiedTwice(Object param1, Object param2, Object param3) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.interceptorSpecifiedTwice$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorClassDoesNotMatchInterceptorBean$str() {
      return "WELD-001417: Enabled interceptor class {0} ({1}) does not match an interceptor bean: the class is not found, or not annotated with @Interceptor and still not registered through a portable extension, or not annotated with @Dependent inside an implicit bean archive";
   }

   public final DeploymentException interceptorClassDoesNotMatchInterceptorBean(Object value, Object location) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.interceptorClassDoesNotMatchInterceptorBean$str(), value, location));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorSpecifiedTwice$str() {
      return "WELD-001418: Enabled decorator class {0} specified twice:\n  - {1},\n  - {2}";
   }

   public final DeploymentException decoratorSpecifiedTwice(Object param1, Object param2, Object param3) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.decoratorSpecifiedTwice$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorClassNotBeanClassOfDecorator$str() {
      return "WELD-001419: Enabled decorator class {0} is not the bean class of at least one decorator bean (detected decorator beans: {1})";
   }

   public final DeploymentException decoratorClassNotBeanClassOfDecorator(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.decoratorClassNotBeanClassOfDecorator$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String alternativeStereotypeNotStereotype$str() {
      return "WELD-001420: Enabled alternative {0} is not a stereotype";
   }

   public final DeploymentException alternativeStereotypeNotStereotype(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeStereotypeNotStereotype$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String alternativeStereotypeSpecifiedMultipleTimes$str() {
      return "WELD-001421: Cannot enable the same alternative stereotype {0} in beans.xml:\n  - {1},\n  - {2}";
   }

   public final DeploymentException alternativeStereotypeSpecifiedMultipleTimes(Object param1, Object param2, Object param3) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeStereotypeSpecifiedMultipleTimes$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String alternativeBeanClassNotAnnotated$str() {
      return "WELD-001422: Enabled alternative class {0} ({1}) does not match any bean, or is not annotated with @Alternative or an @Alternative stereotype, or does not declare a producer annotated with @Alternative or an @Alternative stereotype";
   }

   public final DeploymentException alternativeBeanClassNotAnnotated(Object value, Object location) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeBeanClassNotAnnotated$str(), value, location));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String disposalMethodsWithoutProducer$str() {
      return "WELD-001424: The following disposal methods were declared but did not resolve to a producer method: {0}";
   }

   public final DefinitionException disposalMethodsWithoutProducer(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.disposalMethodsWithoutProducer$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointHasWildcard$str() {
      return "WELD-001425: An injection point cannot have a wildcard type parameter: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionPointHasWildcard(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionPointHasWildcard$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionPointMustHaveTypeParameter$str() {
      return "WELD-001426: An injection point must have a type parameter: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionPointMustHaveTypeParameter(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionPointMustHaveTypeParameter$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonFieldInjectionPointCannotUseNamed$str() {
      return "WELD-001427: Only field injection points can use the @Named qualifier with no value: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException nonFieldInjectionPointCannotUseNamed(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.nonFieldInjectionPointCannotUseNamed$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorsCannotHaveProducerMethods$str() {
      return "WELD-001428: A decorator cannot have producer methods, but at least one was found on {0}.";
   }

   public final DefinitionException decoratorsCannotHaveProducerMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.decoratorsCannotHaveProducerMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorsCannotHaveProducerFields$str() {
      return "WELD-001429: A decorator cannot have producer fields, but at least one was found on {0}.";
   }

   public final DefinitionException decoratorsCannotHaveProducerFields(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.decoratorsCannotHaveProducerFields$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorsCannotHaveDisposerMethods$str() {
      return "WELD-001430: A decorator cannot have disposer methods, but at least one was found on {0}.";
   }

   public final DefinitionException decoratorsCannotHaveDisposerMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.decoratorsCannotHaveDisposerMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorsCannotHaveProducerMethods$str() {
      return "WELD-001431: An interceptor cannot have producer methods, but at least one was found on {0}.";
   }

   public final DefinitionException interceptorsCannotHaveProducerMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorsCannotHaveProducerMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorsCannotHaveProducerFields$str() {
      return "WELD-001432: An interceptor cannot have producer fields, but at least one was found on {0}.";
   }

   public final DefinitionException interceptorsCannotHaveProducerFields(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorsCannotHaveProducerFields$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorsCannotHaveDisposerMethods$str() {
      return "WELD-001433: An interceptor cannot have disposer methods, but at least one was found on {0}.";
   }

   public final DefinitionException interceptorsCannotHaveDisposerMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorsCannotHaveDisposerMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notProxyableUnknown$str() {
      return "WELD-001434: Normal scoped bean {0} is not proxyable for an unknown reason - {1}.";
   }

   public final UnproxyableResolutionException notProxyableUnknown(Object param1, Object param2) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyableUnknown$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notProxyableNoConstructor$str() {
      return "WELD-001435: Normal scoped bean {0} is not proxyable because it has no no-args constructor - {1}.";
   }

   public final UnproxyableResolutionException notProxyableNoConstructor(Object param1, Object param2) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyableNoConstructor$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notProxyablePrivateConstructor$str() {
      return "WELD-001436: Type {0} is not proxyable because it has a private constructor {1} - {2}.";
   }

   public final String notProxyablePrivateConstructor(Object param1, Object param2, Object param3) {
      return MessageFormat.format(this.notProxyablePrivateConstructor$str(), param1, param2, param3);
   }

   protected String notProxyableFinalType$str() {
      return "WELD-001437: Bean type {0} is not proxyable because it is final - {1}.";
   }

   public final UnproxyableResolutionException notProxyableFinalType(Object param1, Object param2) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyableFinalType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notProxyablePrimitive$str() {
      return "WELD-001438: Bean type {0} is not proxyable because it is a primitive - {1}.";
   }

   public final UnproxyableResolutionException notProxyablePrimitive(Object param1, Object param2) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyablePrimitive$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notProxyableArrayType$str() {
      return "WELD-001439: Bean type {0} is not proxyable because it is an array type - {1}.";
   }

   public final UnproxyableResolutionException notProxyableArrayType(Object param1, Object param2) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyableArrayType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void scopeAnnotationOnInjectionPoint(Object param1, Object param2, Object stackElement) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.scopeAnnotationOnInjectionPoint$str(), param1, param2, stackElement);
   }

   protected String scopeAnnotationOnInjectionPoint$str() {
      return "WELD-001440: Scope type {0} used on injection point {1}\n\tat {2}\n  StackTrace";
   }

   protected String alternativeBeanClassNotClass$str() {
      return "WELD-001441: Enabled alternative {0} is not a class";
   }

   public final DeploymentException alternativeBeanClassNotClass(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeBeanClassNotClass$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String alternativeStereotypeNotAnnotated$str() {
      return "WELD-001442: Enabled alternative {0} is not annotated @Alternative";
   }

   public final DeploymentException alternativeStereotypeNotAnnotated(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeStereotypeNotAnnotated$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String pseudoScopedBeanHasCircularReferences$str() {
      return "WELD-001443: Pseudo scoped bean has circular dependencies. Dependency path: {0}";
   }

   public final DeploymentException pseudoScopedBeanHasCircularReferences(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.pseudoScopedBeanHasCircularReferences$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorsCannotHaveObserverMethods$str() {
      return "WELD-001445: An interceptor cannot have observer methods, but at least one was found on {0}.";
   }

   public final DefinitionException interceptorsCannotHaveObserverMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorsCannotHaveObserverMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorsCannotHaveObserverMethods$str() {
      return "WELD-001446: A decorator cannot have observer methods, but at least one was found on {0}.";
   }

   public final DefinitionException decoratorsCannotHaveObserverMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.decoratorsCannotHaveObserverMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDoesNotReturnObject$str() {
      return "WELD-001447: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3}\n\tat {4}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDoesNotReturnObject(Object param1, Object param2, Object param3, Object param4, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDoesNotReturnObject$str(), param1, param2, param3, param4, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDoesNotHaveExactlyOneParameter$str() {
      return "WELD-001448: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have exactly one parameter\n\tat {3}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDoesNotHaveExactlyOneParameter(Object param1, Object param2, Object param3, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDoesNotHaveExactlyOneParameter$str(), param1, param2, param3, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDoesNotHaveCorrectTypeOfParameter$str() {
      return "WELD-001449: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but its single parameter is not a {3}\n\tat {4}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDoesNotHaveCorrectTypeOfParameter(Object param1, Object param2, Object param3, Object param4, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDoesNotHaveCorrectTypeOfParameter$str(), param1, param2, param3, param4, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String userTransactionInjectionIntoBeanWithContainerManagedTransactions$str() {
      return "WELD-001451: javax.transaction.UserTransaction cannot be injected into an enterprise bean with container-managed transactions: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException userTransactionInjectionIntoBeanWithContainerManagedTransactions(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.userTransactionInjectionIntoBeanWithContainerManagedTransactions$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidBeanMetadataInjectionPointType$str() {
      return "WELD-001452: {0} is not a valid type for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace";
   }

   public final DefinitionException invalidBeanMetadataInjectionPointType(Object param1, Object param2, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidBeanMetadataInjectionPointType$str(), param1, param2, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidBeanMetadataInjectionPointTypeArgument$str() {
      return "WELD-001453: {0} is not a valid type argument for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace";
   }

   public final DefinitionException invalidBeanMetadataInjectionPointTypeArgument(Object param1, Object param2, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidBeanMetadataInjectionPointTypeArgument$str(), param1, param2, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidBeanMetadataInjectionPointQualifier$str() {
      return "WELD-001454: {0} cannot be used at a Bean metadata injection point of a bean which is not {1}, {2}\n\tat {3}\n  StackTrace";
   }

   public final DefinitionException invalidBeanMetadataInjectionPointQualifier(Object param1, Object param2, Object param3, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidBeanMetadataInjectionPointQualifier$str(), param1, param2, param3, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noDecoratedTypes$str() {
      return "WELD-001455: {0} does not declare any decorated types.";
   }

   public final DefinitionException noDecoratedTypes(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.noDecoratedTypes$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String argumentNull1$str() {
      return "WELD-001456: Argument {0} must not be null";
   }

   public final IllegalArgumentException argumentNull(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.argumentNull1$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String alternativeClassSpecifiedMultipleTimes$str() {
      return "WELD-001457: Cannot enable the same alternative class {0} in beans.xml:\n  - {1},\n  - {2}";
   }

   public final DeploymentException alternativeClassSpecifiedMultipleTimes(Object param1, Object param2, Object param3) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.alternativeClassSpecifiedMultipleTimes$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanWithPassivatingScopeNotPassivationCapable$str() {
      return "WELD-001463: Bean declaring a passivating scope must be passivation capable.  Bean:  {0}";
   }

   public final DeploymentException beanWithPassivatingScopeNotPassivationCapable(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.beanWithPassivatingScopeNotPassivationCapable$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String builtinBeanWithNonserializableDecorator$str() {
      return "WELD-001465: {0} for a built-in bean {1} must be passivation capable.";
   }

   public final UnserializableDependencyException builtinBeanWithNonserializableDecorator(Object param1, Object param2) {
      UnserializableDependencyException result = new UnserializableDependencyException(MessageFormat.format(this.builtinBeanWithNonserializableDecorator$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectionIntoDisposerMethod$str() {
      return "WELD-001466: Invalid injection point found in a disposer method: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException injectionIntoDisposerMethod(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionIntoDisposerMethod$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDoesNotReturnObjectOrVoid$str() {
      return "WELD-001467: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3} or {4}.\n\tat {5}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDoesNotReturnObjectOrVoid(Object param1, Object param2, Object param3, Object param4, Object param5, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDoesNotReturnObjectOrVoid$str(), param1, param2, param3, param4, param5, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDoesNotHaveVoidReturnType$str() {
      return "WELD-001468: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have a {3} return type.\n\tat {4}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDoesNotHaveVoidReturnType(Object param1, Object param2, Object param3, Object param4, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDoesNotHaveVoidReturnType$str(), param1, param2, param3, param4, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void interceptorMethodDoesNotHaveZeroParameters(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.interceptorMethodDoesNotHaveZeroParameters$str(), param1, param2, param3);
   }

   protected String interceptorMethodDoesNotHaveZeroParameters$str() {
      return "WELD-001469: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have zero parameters.\n";
   }

   public final void interceptorMethodShouldNotThrowCheckedExceptions(Object param1, Object param2, Object param3, Object stackElement) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.interceptorMethodShouldNotThrowCheckedExceptions$str(), new Object[]{param1, param2, param3, stackElement});
   }

   protected String interceptorMethodShouldNotThrowCheckedExceptions$str() {
      return "WELD-001471: Interceptor method {0} defined on class {1} is not defined according to the specification. It should not throw {2}, which is a checked exception.\n\tat {3}\n  StackTrace";
   }

   protected String eventMetadataInjectedOutsideOfObserver$str() {
      return "WELD-001472: EventMetadata can only be injected into an observer method: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException eventMetadataInjectedOutsideOfObserver(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.eventMetadataInjectedOutsideOfObserver$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void beanNotPassivationCapable(Object param1) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.beanNotPassivationCapable$str(), param1);
   }

   protected String beanNotPassivationCapable$str() {
      return "WELD-001473: javax.enterprise.inject.spi.Bean implementation {0} declared a normal scope but does not implement javax.enterprise.inject.spi.PassivationCapable. It won'''t be possible to inject this bean into a bean with a passivating scope (@SessionScoped, @ConversationScoped). This can be fixed by assigning the Bean implementation a unique id by implementing the PassivationCapable interface.";
   }

   protected String unsatisfiedDependencyBecauseClassIgnored$str() {
      return "WELD-001474: Class {0} is on the classpath, but was ignored because a class it references was not found: {1}.\n";
   }

   public final String unsatisfiedDependencyBecauseClassIgnored(Object param1, Object param2) {
      return MessageFormat.format(this.unsatisfiedDependencyBecauseClassIgnored$str(), param1, param2);
   }

   protected String unsatisfiedDependencyBecauseQualifiersDontMatch$str() {
      return "WELD-001475: The following beans match by type, but none have matching qualifiers:{0}\n";
   }

   public final String unsatisfiedDependencyBecauseQualifiersDontMatch(Object param1) {
      return MessageFormat.format(this.unsatisfiedDependencyBecauseQualifiersDontMatch$str(), param1);
   }

   protected String interceptorOrDecoratorMustBeDependent$str() {
      return "WELD-001476: {0} must be @Dependent";
   }

   public final DefinitionException interceptorOrDecoratorMustBeDependent(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorOrDecoratorMustBeDependent$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorDecoratorInjectionPointHasNonSerializableDependency$str() {
      return "WELD-001477: The bean {0} declares a passivating scope but has a(n) {1} with a non-passivation-capable dependency {2}";
   }

   public final UnserializableDependencyException interceptorDecoratorInjectionPointHasNonSerializableDependency(Object param1, Object param2, Object param3) {
      UnserializableDependencyException result = new UnserializableDependencyException(MessageFormat.format(this.interceptorDecoratorInjectionPointHasNonSerializableDependency$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void interceptorEnabledForApplicationAndBeanArchive(Object interceptor, Object beanArchive) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.interceptorEnabledForApplicationAndBeanArchive$str(), interceptor, beanArchive);
   }

   protected String interceptorEnabledForApplicationAndBeanArchive$str() {
      return "WELD-001478: Interceptor {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.";
   }

   public final void decoratorEnabledForApplicationAndBeanArchive(Object decorator, Object beanArchive) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.decoratorEnabledForApplicationAndBeanArchive$str(), decorator, beanArchive);
   }

   protected String decoratorEnabledForApplicationAndBeanArchive$str() {
      return "WELD-001479: Decorator {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.";
   }

   protected String notProxyableFinalMethod$str() {
      return "WELD-001480: Bean type {0} is not proxyable because it contains a final method {1} - {2}.";
   }

   public final UnproxyableResolutionException notProxyableFinalMethod(Object beanType, Method finalMethod, Object declaringBean) {
      UnproxyableResolutionException result = new UnproxyableResolutionException(MessageFormat.format(this.notProxyableFinalMethod$str(), beanType, finalMethod, declaringBean));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void notProxyableFinalMethodIgnored(Method invalidMethod, Object declaringBean) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.notProxyableFinalMethodIgnored$str(), invalidMethod, declaringBean);
   }

   protected String notProxyableFinalMethodIgnored$str() {
      return "WELD-001481: Final method will be ignored during proxy generation and should never be invoked upon the proxy instance! {0} - {1}.";
   }

   protected String invalidInterceptionFactoryInjectionPoint$str() {
      return "WELD-001482: InterceptionFactory can only be injected in a parameter of a producer method: {0}\n\tat {1}\n  StackTrace";
   }

   public final DefinitionException invalidInterceptionFactoryInjectionPoint(Object injectionPoint, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidInterceptionFactoryInjectionPoint$str(), injectionPoint, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String argumentNull0$str() {
      return "WELD-001483: Argument must not be null";
   }

   public final IllegalArgumentException argumentNull() {
      IllegalArgumentException result = new IllegalArgumentException(this.argumentNull0$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptorMethodDeclaresMultipleParameters$str() {
      return "WELD-001485: Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} and it declares more than one parameter.\n\tat {3}\n  StackTrace";
   }

   public final DefinitionException interceptorMethodDeclaresMultipleParameters(Object param1, Object param2, Object param3, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.interceptorMethodDeclaresMultipleParameters$str(), param1, param2, param3, stackElement));
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
