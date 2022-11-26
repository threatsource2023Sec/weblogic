package org.jboss.weld.logging;

import java.lang.reflect.Method;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.InconsistentSpecializationException;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.exceptions.UnserializableDependencyException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ValidatorLogger extends WeldLogger {
   ValidatorLogger LOG = (ValidatorLogger)Logger.getMessageLogger(ValidatorLogger.class, Category.VALIDATOR.getName());
   MessageCallback INJECTION_INTO_DISPOSER_METHOD = (t) -> {
      return LOG.injectionIntoDisposerMethod(t[0], t[1]);
   };
   MessageCallback INJECTION_INTO_NON_BEAN = (t) -> {
      return LOG.injectionIntoNonBean(t[0], t[1]);
   };
   MessageCallback INTERCEPTOR_SPECIFIED_TWICE = (t) -> {
      return LOG.interceptorSpecifiedTwice(t[0], t[1], t[2]);
   };
   MessageCallback DECORATOR_SPECIFIED_TWICE = (t) -> {
      return LOG.decoratorSpecifiedTwice(t[0], t[1], t[2]);
   };
   MessageCallback ALTERNATIVE_CLASS_SPECIFIED_MULTIPLE_TIMES = (t) -> {
      return LOG.alternativeClassSpecifiedMultipleTimes(t[0], t[1], t[2]);
   };
   MessageCallback ALTERNATIVE_STEREOTYPE_SPECIFIED_MULTIPLE_TIMES = (t) -> {
      return LOG.alternativeStereotypeSpecifiedMultipleTimes(t[0], t[1], t[2]);
   };
   LogMessageCallback INTERCEPTOR_ENABLED_FOR_APP_AND_ARCHIVE = (t) -> {
      LOG.interceptorEnabledForApplicationAndBeanArchive(t[0], t[1]);
   };
   LogMessageCallback DECORATOR_ENABLED_FOR_APP_AND_ARCHIVE = (t) -> {
      LOG.decoratorEnabledForApplicationAndBeanArchive(t[0], t[1]);
   };

   @Message(
      id = 1401,
      value = "Two beans cannot specialize the same bean {0}",
      format = Format.MESSAGE_FORMAT
   )
   InconsistentSpecializationException beanSpecializedTooManyTimes(Object var1);

   @Message(
      id = 1402,
      value = "The bean {0} declares a passivating scope but has a non-serializable interceptor {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException passivatingBeanWithNonserializableInterceptor(Object var1, Object var2);

   @Message(
      id = 1403,
      value = "The bean {0} declares a passivating scope but has a non-serializable decorator {1}",
      format = Format.MESSAGE_FORMAT
   )
   UnserializableDependencyException passivatingBeanWithNonserializableDecorator(Object var1, Object var2);

   @Message(
      id = 1404,
      value = "The injection point is annotated with @New which cannot be combined with other qualifiers: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException newWithQualifiers(Object var1, Object var2);

   @Message(
      id = 1405,
      value = "Cannot inject {0} in a class which is not a bean\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionIntoNonBean(Object var1, Object var2);

   @Message(
      id = 1406,
      value = "Cannot inject injection point metadata in a non @Dependent scoped bean: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionIntoNonDependentBean(Object var1, Object var2);

   @Message(
      id = 1407,
      value = "Cannot declare an injection point with a type variable: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionPointWithTypeVariable(Object var1, Object var2);

   @Message(
      id = 1408,
      value = "Unsatisfied dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n{4}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException injectionPointHasUnsatisfiedDependencies(Object var1, Object var2, Object var3, Object var4, Object var5);

   @Message(
      id = 1409,
      value = "Ambiguous dependencies for type {2} with qualifiers {1}\n  at injection point {0}\n  at {3}\n  Possible dependencies: {4}\n",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException injectionPointHasAmbiguousDependencies(Object var1, Object var2, Object var3, Object var4, Object var5);

   @Message(
      id = 1410,
      value = "The injection point has non-proxyable dependencies: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException injectionPointHasNonProxyableDependencies(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 1413,
      value = "The bean {0} declares a passivating scope but has a non-passivation-capable dependency {1}",
      format = Format.MESSAGE_FORMAT
   )
   UnserializableDependencyException injectionPointHasNonSerializableDependency(Object var1, Object var2);

   @Message(
      id = 1414,
      value = "Bean name is ambiguous. Name {0} resolves to beans: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException ambiguousElName(Object var1, Object var2);

   @Message(
      id = 1415,
      value = "Bean name is identical to a bean name prefix used elsewhere. Name {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException beanNameIsPrefix(Object var1);

   @Message(
      id = 1416,
      value = "Enabled interceptor class {0} specified twice:\n  - {1},\n  - {2}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException interceptorSpecifiedTwice(Object var1, Object var2, Object var3);

   @Message(
      id = 1417,
      value = "Enabled interceptor class {0} ({1}) does not match an interceptor bean: the class is not found, or not annotated with @Interceptor and still not registered through a portable extension, or not annotated with @Dependent inside an implicit bean archive",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException interceptorClassDoesNotMatchInterceptorBean(Object var1, Object var2);

   @Message(
      id = 1418,
      value = "Enabled decorator class {0} specified twice:\n  - {1},\n  - {2}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException decoratorSpecifiedTwice(Object var1, Object var2, Object var3);

   @Message(
      id = 1419,
      value = "Enabled decorator class {0} is not the bean class of at least one decorator bean (detected decorator beans: {1})",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException decoratorClassNotBeanClassOfDecorator(Object var1, Object var2);

   @Message(
      id = 1420,
      value = "Enabled alternative {0} is not a stereotype",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeStereotypeNotStereotype(Object var1);

   @Message(
      id = 1421,
      value = "Cannot enable the same alternative stereotype {0} in beans.xml:\n  - {1},\n  - {2}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeStereotypeSpecifiedMultipleTimes(Object var1, Object var2, Object var3);

   @Message(
      id = 1422,
      value = "Enabled alternative class {0} ({1}) does not match any bean, or is not annotated with @Alternative or an @Alternative stereotype, or does not declare a producer annotated with @Alternative or an @Alternative stereotype",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeBeanClassNotAnnotated(Object var1, Object var2);

   @Message(
      id = 1424,
      value = "The following disposal methods were declared but did not resolve to a producer method: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException disposalMethodsWithoutProducer(Object var1);

   @Message(
      id = 1425,
      value = "An injection point cannot have a wildcard type parameter: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionPointHasWildcard(Object var1, Object var2);

   @Message(
      id = 1426,
      value = "An injection point must have a type parameter: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionPointMustHaveTypeParameter(Object var1, Object var2);

   @Message(
      id = 1427,
      value = "Only field injection points can use the @Named qualifier with no value: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException nonFieldInjectionPointCannotUseNamed(Object var1, Object var2);

   @Message(
      id = 1428,
      value = "A decorator cannot have producer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException decoratorsCannotHaveProducerMethods(Object var1);

   @Message(
      id = 1429,
      value = "A decorator cannot have producer fields, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException decoratorsCannotHaveProducerFields(Object var1);

   @Message(
      id = 1430,
      value = "A decorator cannot have disposer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException decoratorsCannotHaveDisposerMethods(Object var1);

   @Message(
      id = 1431,
      value = "An interceptor cannot have producer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorsCannotHaveProducerMethods(Object var1);

   @Message(
      id = 1432,
      value = "An interceptor cannot have producer fields, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorsCannotHaveProducerFields(Object var1);

   @Message(
      id = 1433,
      value = "An interceptor cannot have disposer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorsCannotHaveDisposerMethods(Object var1);

   @Message(
      id = 1434,
      value = "Normal scoped bean {0} is not proxyable for an unknown reason - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyableUnknown(Object var1, Object var2);

   @Message(
      id = 1435,
      value = "Normal scoped bean {0} is not proxyable because it has no no-args constructor - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyableNoConstructor(Object var1, Object var2);

   @Message(
      id = 1436,
      value = "Type {0} is not proxyable because it has a private constructor {1} - {2}.",
      format = Format.MESSAGE_FORMAT
   )
   String notProxyablePrivateConstructor(Object var1, Object var2, Object var3);

   @Message(
      id = 1437,
      value = "Bean type {0} is not proxyable because it is final - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyableFinalType(Object var1, Object var2);

   @Message(
      id = 1438,
      value = "Bean type {0} is not proxyable because it is a primitive - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyablePrimitive(Object var1, Object var2);

   @Message(
      id = 1439,
      value = "Bean type {0} is not proxyable because it is an array type - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyableArrayType(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1440,
      value = "Scope type {0} used on injection point {1}\n\tat {2}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   void scopeAnnotationOnInjectionPoint(Object var1, Object var2, Object var3);

   @Message(
      id = 1441,
      value = "Enabled alternative {0} is not a class",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeBeanClassNotClass(Object var1);

   @Message(
      id = 1442,
      value = "Enabled alternative {0} is not annotated @Alternative",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeStereotypeNotAnnotated(Object var1);

   @Message(
      id = 1443,
      value = "Pseudo scoped bean has circular dependencies. Dependency path: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException pseudoScopedBeanHasCircularReferences(Object var1);

   @Message(
      id = 1445,
      value = "An interceptor cannot have observer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorsCannotHaveObserverMethods(Object var1);

   @Message(
      id = 1446,
      value = "A decorator cannot have observer methods, but at least one was found on {0}.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException decoratorsCannotHaveObserverMethods(Object var1);

   @Message(
      id = 1447,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3}\n\tat {4}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDoesNotReturnObject(Object var1, Object var2, Object var3, Object var4, Object var5);

   @Message(
      id = 1448,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have exactly one parameter\n\tat {3}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDoesNotHaveExactlyOneParameter(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 1449,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but its single parameter is not a {3}\n\tat {4}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDoesNotHaveCorrectTypeOfParameter(Object var1, Object var2, Object var3, Object var4, Object var5);

   @Message(
      id = 1451,
      value = "javax.transaction.UserTransaction cannot be injected into an enterprise bean with container-managed transactions: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException userTransactionInjectionIntoBeanWithContainerManagedTransactions(Object var1, Object var2);

   @Message(
      id = 1452,
      value = "{0} is not a valid type for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidBeanMetadataInjectionPointType(Object var1, Object var2, Object var3);

   @Message(
      id = 1453,
      value = "{0} is not a valid type argument for a Bean metadata injection point {1}\n\tat {2}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidBeanMetadataInjectionPointTypeArgument(Object var1, Object var2, Object var3);

   @Message(
      id = 1454,
      value = "{0} cannot be used at a Bean metadata injection point of a bean which is not {1}, {2}\n\tat {3}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidBeanMetadataInjectionPointQualifier(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 1455,
      value = "{0} does not declare any decorated types.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException noDecoratedTypes(Object var1);

   @Message(
      id = 1456,
      value = "Argument {0} must not be null",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException argumentNull(Object var1);

   @Message(
      id = 1457,
      value = "Cannot enable the same alternative class {0} in beans.xml:\n  - {1},\n  - {2}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException alternativeClassSpecifiedMultipleTimes(Object var1, Object var2, Object var3);

   @Message(
      id = 1463,
      value = "Bean declaring a passivating scope must be passivation capable.  Bean:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException beanWithPassivatingScopeNotPassivationCapable(Object var1);

   @Message(
      id = 1465,
      value = "{0} for a built-in bean {1} must be passivation capable.",
      format = Format.MESSAGE_FORMAT
   )
   UnserializableDependencyException builtinBeanWithNonserializableDecorator(Object var1, Object var2);

   @Message(
      id = 1466,
      value = "Invalid injection point found in a disposer method: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionIntoDisposerMethod(Object var1, Object var2);

   @Message(
      id = 1467,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not return {3} or {4}.\n\tat {5}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDoesNotReturnObjectOrVoid(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6);

   @Message(
      id = 1468,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have a {3} return type.\n\tat {4}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDoesNotHaveVoidReturnType(Object var1, Object var2, Object var3, Object var4, Object var5);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1469,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} but it does not have zero parameters.\n",
      format = Format.MESSAGE_FORMAT
   )
   void interceptorMethodDoesNotHaveZeroParameters(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1471,
      value = "Interceptor method {0} defined on class {1} is not defined according to the specification. It should not throw {2}, which is a checked exception.\n\tat {3}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   void interceptorMethodShouldNotThrowCheckedExceptions(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 1472,
      value = "EventMetadata can only be injected into an observer method: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException eventMetadataInjectedOutsideOfObserver(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1473,
      value = "javax.enterprise.inject.spi.Bean implementation {0} declared a normal scope but does not implement javax.enterprise.inject.spi.PassivationCapable. It won'''t be possible to inject this bean into a bean with a passivating scope (@SessionScoped, @ConversationScoped). This can be fixed by assigning the Bean implementation a unique id by implementing the PassivationCapable interface.",
      format = Format.MESSAGE_FORMAT
   )
   void beanNotPassivationCapable(Object var1);

   @Message(
      id = 1474,
      value = "Class {0} is on the classpath, but was ignored because a class it references was not found: {1}.\n",
      format = Format.MESSAGE_FORMAT
   )
   String unsatisfiedDependencyBecauseClassIgnored(Object var1, Object var2);

   @Message(
      id = 1475,
      value = "The following beans match by type, but none have matching qualifiers:{0}\n",
      format = Format.MESSAGE_FORMAT
   )
   String unsatisfiedDependencyBecauseQualifiersDontMatch(Object var1);

   @Message(
      id = 1476,
      value = "{0} must be @Dependent",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorOrDecoratorMustBeDependent(Object var1);

   @Message(
      id = 1477,
      value = "The bean {0} declares a passivating scope but has a(n) {1} with a non-passivation-capable dependency {2}",
      format = Format.MESSAGE_FORMAT
   )
   UnserializableDependencyException interceptorDecoratorInjectionPointHasNonSerializableDependency(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1478,
      value = "Interceptor {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.",
      format = Format.MESSAGE_FORMAT
   )
   void interceptorEnabledForApplicationAndBeanArchive(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1479,
      value = "Decorator {0} is enabled for the application and for the bean archive {1}. It will only be invoked in the @Priority part of the chain.",
      format = Format.MESSAGE_FORMAT
   )
   void decoratorEnabledForApplicationAndBeanArchive(Object var1, Object var2);

   @Message(
      id = 1480,
      value = "Bean type {0} is not proxyable because it contains a final method {1} - {2}.",
      format = Format.MESSAGE_FORMAT
   )
   UnproxyableResolutionException notProxyableFinalMethod(Object var1, Method var2, Object var3);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1481,
      value = "Final method will be ignored during proxy generation and should never be invoked upon the proxy instance! {0} - {1}.",
      format = Format.MESSAGE_FORMAT
   )
   void notProxyableFinalMethodIgnored(Method var1, Object var2);

   @Message(
      id = 1482,
      value = "InterceptionFactory can only be injected in a parameter of a producer method: {0}\n\tat {1}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidInterceptionFactoryInjectionPoint(Object var1, Object var2);

   @Message(
      id = 1483,
      value = "Argument must not be null",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException argumentNull();

   @Message(
      id = 1485,
      value = "Method {0} defined on class {1} is not defined according to the specification. It is annotated with @{2} and it declares more than one parameter.\n\tat {3}\n  StackTrace",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException interceptorMethodDeclaresMultipleParameters(Object var1, Object var2, Object var3, Object var4);
}
