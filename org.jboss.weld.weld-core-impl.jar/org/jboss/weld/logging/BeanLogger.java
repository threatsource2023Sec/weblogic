package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.CreationException;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalProductException;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.exceptions.NullInstanceException;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.exceptions.WeldException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface BeanLogger extends WeldLogger {
   BeanLogger LOG = (BeanLogger)Logger.getMessageLogger(BeanLogger.class, Category.BEAN.getName());

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1,
      value = "Exactly one constructor ({0}) annotated with @Inject defined, using it as the bean constructor for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void foundOneInjectableConstructor(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 2,
      value = "Exactly one constructor ({0}) defined, using it as the bean constructor for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void foundDefaultConstructor(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 4,
      value = "Exactly one post construct method ({0}) for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void foundOnePostConstructMethod(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 6,
      value = "Exactly one pre destroy method ({0}) for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void foundOnePreDestroyMethod(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 7,
      value = "Created session bean proxy for {0}",
      format = Format.MESSAGE_FORMAT
   )
   void createdSessionBeanProxy(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 8,
      value = "Called {0} on {1} with parameters {2} which returned {3}",
      format = Format.MESSAGE_FORMAT
   )
   void callProxiedMethod(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 9,
      value = "Dynamic lookup of {0} is not supported",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException dynamicLookupOfBuiltInNotAllowed(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 10,
      value = "Using qualifiers {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void qualifiersUsed(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 12,
      value = "Building bean metadata for {0}",
      format = Format.MESSAGE_FORMAT
   )
   void creatingBean(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 14,
      value = "Using name {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void usingName(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 16,
      value = "Using scope {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void usingScope(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 18,
      value = "Executing producer field or method {0} on incomplete declaring bean {1} due to circular injection",
      format = Format.MESSAGE_FORMAT
   )
   void circularCall(Object var1, Object var2);

   @LogMessage(
      level = Level.ERROR
   )
   @Message(
      id = 19,
      value = "Error destroying an instance {0} of {1}",
      format = Format.MESSAGE_FORMAT
   )
   void errorDestroying(Object var1, Object var2);

   @Message(
      id = 23,
      value = "Type parameter must be a concrete type:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   String typeParameterMustBeConcrete(Object var1);

   @Message(
      id = 25,
      value = "Tried to create an EEResourceProducerField, but no @Resource, @PersistenceContext, @PersistenceUnit, @WebServiceRef or @EJB is present: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException invalidResourceProducerField(Object var1);

   @Message(
      id = 26,
      value = "Security Services not available - unable to obtain the Principal"
   )
   IllegalStateException securityServicesNotAvailable();

   @Message(
      id = 27,
      value = "Transaction Services not available - unable to obtain the UserTransaction"
   )
   IllegalStateException transactionServicesNotAvailable();

   @Message(
      id = 28,
      value = "Interception model must not be null"
   )
   IllegalArgumentException interceptionModelNull();

   @Message(
      id = 29,
      value = "InterceptionType must not be null"
   )
   IllegalArgumentException interceptionTypeNull();

   @Message(
      id = 30,
      value = "Method must not be null"
   )
   IllegalArgumentException methodNull();

   @Message(
      id = 31,
      value = "InterceptionType must not be lifecycle, but it is {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException interceptionTypeLifecycle(Object var1);

   @Message(
      id = 32,
      value = "InterceptionType must be lifecycle, but it is {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException interceptionTypeNotLifecycle(Object var1);

   @Message(
      id = 33,
      value = "Could not instantiate client proxy for {0}",
      format = Format.MESSAGE_FORMAT
   )
   String proxyInstantiationFailed(Object var1);

   @Message(
      id = 34,
      value = "Could not access bean correctly when creating client proxy for {0}",
      format = Format.MESSAGE_FORMAT
   )
   String proxyInstantiationBeanAccessFailed(Object var1);

   @Message(
      id = 35,
      value = "There was an error creating an id for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanIdCreationFailed(Object var1);

   @Message(
      id = 36,
      value = "Unexpected unwrapped custom decorator instance: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unexpectedUnwrappedCustomDecorator(Object var1);

   @Message(
      id = 37,
      value = "Cannot call EJB remove method directly on non-dependent scoped bean {0}",
      format = Format.MESSAGE_FORMAT
   )
   UnsupportedOperationException invalidRemoveMethodInvocation(Object var1);

   @Message(
      id = 38,
      value = "A bean class that is not a decorator has an injection point annotated @Delegate\n  at injection point {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException delegateNotOnDecorator(Object var1, Object var2);

   @Message(
      id = 39,
      value = "@Typed class {0} not present in the set of bean types of {1} [{2}]",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException typedClassNotInHierarchy(Object var1, Object var2, Object var3);

   @Message(
      id = 40,
      value = "All stereotypes must specify the same scope or the bean must declare a scope - declared on {0}, declared stereotypes [{1}], possible scopes {2}{3}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleScopesFoundFromStereotypes(Object var1, Object var2, Object var3, String var4);

   @Message(
      id = 41,
      value = "Specializing bean may not declare a bean name if it is declared by specialized bean\n  specializing: {0}\n  specialized: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException nameNotAllowedOnSpecialization(Object var1, Object var2);

   @Message(
      id = 42,
      value = "Cannot operate on non container provided decorator {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException nonContainerDecorator(Object var1);

   @Message(
      id = 43,
      value = "The following bean is not an EE resource producer:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException beanNotEeResourceProducer(Object var1);

   @Message(
      id = 44,
      value = "Unable to obtain instance from {0}",
      format = Format.MESSAGE_FORMAT
   )
   NullInstanceException nullInstance(Object var1);

   @Message(
      id = 45,
      value = "Unable to deserialize object - serialization proxy is required"
   )
   InvalidObjectException serializationProxyRequired();

   @Message(
      id = 46,
      value = "At most one scope may be specified on {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException onlyOneScopeAllowed(Object var1);

   @Message(
      id = 47,
      value = "Specializing bean must extend another bean:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException specializingBeanMustExtendABean(Object var1);

   @Message(
      id = 48,
      value = "Conflicting interceptor bindings found on {0}",
      format = Format.MESSAGE_FORMAT
   )
   String conflictingInterceptorBindings(Object var1);

   @Message(
      id = 49,
      value = "Unable to invoke {0} on {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException invocationError(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 50,
      value = "Cannot cast producer type {0} to bean type {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException producerCastError(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 52,
      value = "Cannot return null from a non-dependent producer method: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   IllegalProductException nullNotAllowedFromProducer(Object var1, Object var2);

   @Message(
      id = 53,
      value = "Producers cannot declare passivating scope and return a non-serializable class: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   IllegalProductException nonSerializableProductError(Object var1, Object var2);

   @Message(
      id = 54,
      value = "Producers cannot produce unserializable instances for injection into an injection point that requires a passivation capable dependency\n  Producer:  {0}\n\tat {1}\n  Injection Point:  {2}\n\tat {3}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   IllegalProductException unserializableProductInjectionError(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 59,
      value = "No delegate injection point defined for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException noDelegateInjectionPoint(Object var1);

   @Message(
      id = 60,
      value = "Too many delegate injection points defined for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException tooManyDelegateInjectionPoints(Object var1);

   @Message(
      id = 61,
      value = "The delegate type does not extend or implement the decorated type. \n  Decorated type: {0}\n  Decorator: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException delegateMustSupportEveryDecoratedType(Object var1, Object var2);

   @Message(
      id = 64,
      value = "Unable to process decorated type: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToProcessDecoratedType(Object var1);

   @Message(
      id = 66,
      value = "{0} has more than one @Dispose parameter \n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleDisposeParams(Object var1, Object var2);

   @Message(
      id = 67,
      value = "{0} is not allowed on same method as {1}, see {2}\n\tat {3}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException inconsistentAnnotationsOnMethod(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 68,
      value = "{0} method {1} is not a business method of {2}\n\tat {3}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException methodNotBusinessMethod(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 70,
      value = "Simple bean {0} cannot be a non-static inner class",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException simpleBeanAsNonStaticInnerClassNotAllowed(Object var1);

   @Message(
      id = 71,
      value = "Managed bean with a parameterized bean class must be @Dependent: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException managedBeanWithParameterizedBeanClassMustBeDependent(Object var1);

   @Message(
      id = 72,
      value = "Bean declaring a passivating scope must be passivation capable.  Bean:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException passivatingBeanNeedsSerializableImpl(Object var1);

   @Message(
      id = 73,
      value = "Bean class which has decorators cannot be declared final:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException finalBeanClassWithDecoratorsNotAllowed(Object var1);

   @Message(
      id = 75,
      value = "Normal scoped managed bean implementation class has a public field:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException publicFieldOnNormalScopedBeanNotAllowed(Object var1);

   @Message(
      id = 76,
      value = "Bean constructor must not have a parameter annotated with {0}: {1}\n\tat {2}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException parameterAnnotationNotAllowedOnConstructor(Object var1, Object var2, Object var3);

   @Message(
      id = 77,
      value = "Cannot declare multiple disposal methods for this producer method.\n\nProducer method:  {0}\nDisposal methods:  {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleDisposalMethods(Object var1, Object var2);

   @Message(
      id = 78,
      value = "Specialized producer method does not override another producer method: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerMethodNotSpecializing(Object var1, Object var2);

   @Message(
      id = 79,
      value = "Could not instantiate a proxy for a session bean:  {0}\n  Proxy: {1}",
      format = Format.MESSAGE_FORMAT
   )
   CreationException sessionBeanProxyInstantiationFailed(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 80,
      value = "Enterprise beans cannot be interceptors:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException ejbCannotBeInterceptor(Object var1);

   @Message(
      id = 81,
      value = "Enterprise beans cannot be decorators:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException ejbCannotBeDecorator(Object var1);

   @Message(
      id = 82,
      value = "Scope {0} is not allowed on stateless session beans for {1}. Only @Dependent is allowed.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException scopeNotAllowedOnStatelessSessionBean(Object var1, Object var2);

   @Message(
      id = 83,
      value = "Scope {0} is not allowed on singleton session beans for {1}. Only @Dependent and @ApplicationScoped is allowed.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException scopeNotAllowedOnSingletonBean(Object var1, Object var2);

   @Message(
      id = 84,
      value = "Specializing enterprise bean must extend another enterprise bean:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException specializingEnterpriseBeanMustExtendAnEnterpriseBean(Object var1);

   @Message(
      id = 85,
      value = "Cannot destroy null instance of {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotDestroyNullBean(Object var1);

   @Message(
      id = 86,
      value = "Cannot destroy session bean instance not created by the container:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotDestroyEnterpriseBeanNotCreated(Object var1);

   @Message(
      id = 87,
      value = "Message driven beans cannot be Managed Beans:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException messageDrivenBeansCannotBeManaged(Object var1);

   @Message(
      id = 88,
      value = "Observer method must be static or local business method:  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException observerMethodMustBeStaticOrBusiness(Object var1, Object var2);

   @Message(
      id = 89,
      value = "Unable to determine EJB for {0}, multiple EJBs with that class:  {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException tooManyEjbsForClass(Object var1, Object var2);

   @Message(
      id = 90,
      value = "A decorator has an abstract method that is not declared by any decorated type\n  Method: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException abstractMethodMustMatchDecoratedType(Object var1, Object var2);

   @Message(
      id = 94,
      value = "Injected field {0} cannot be annotated @Produces on {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectedFieldCannotBeProducer(Object var1, Object var2);

   @Message(
      id = 95,
      value = "Session bean with generic class {0} must be @Dependent scope",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException genericSessionBeanMustBeDependent(Object var1);

   @Message(
      id = 96,
      value = "Producer fields on session beans must be static. Field {0} declared on {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerFieldOnSessionBeanMustBeStatic(Object var1, Object var2);

   @Message(
      id = 97,
      value = "A producer method with a parameterized return type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerMethodWithTypeVariableReturnTypeMustBeDependent(Object var1, String var2);

   @Message(
      id = 98,
      value = "A producer method return type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerMethodCannotHaveAWildcardReturnType(Object var1, String var2);

   @Message(
      id = 99,
      value = "Cannot load class {0} during deserialization of proxy",
      format = Format.MESSAGE_FORMAT
   )
   WeldException cannotLoadClass(Object var1, @Cause Throwable var2);

   @Message(
      id = 1500,
      value = "Failed to deserialize proxy object with beanId {0}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException proxyDeserializationFailure(Object var1);

   @Message(
      id = 1501,
      value = "Method call requires a BeanInstance which has not been set for this proxy {0}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException beanInstanceNotSetOnProxy(Object var1);

   @Message(
      id = 1502,
      value = "Resource producer field [{0}] must be @Dependent scoped",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException nonDependentResourceProducerField(Object var1);

   @Message(
      id = 1503,
      value = "Bean class which has interceptors cannot be declared final:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException finalBeanClassWithInterceptorsNotAllowed(Object var1);

   @Message(
      id = 1504,
      value = "Intercepted bean method {0} (intercepted by {1}) cannot be declared final",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException finalInterceptedBeanMethodNotAllowed(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1505,
      value = "Method {0} cannot be intercepted by {1} - will be ignored by interceptors and should never be invoked upon the proxy instance!",
      format = Format.MESSAGE_FORMAT
   )
   void finalMethodNotIntercepted(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1506,
      value = "Created new client proxy of type {0} for bean {1} with ID {2}",
      format = Format.MESSAGE_FORMAT
   )
   void createdNewClientProxyType(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1507,
      value = "Located client proxy of type {0} for bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   void lookedUpClientProxy(Object var1, Object var2);

   @Message(
      id = 1508,
      value = "Cannot create an InjectionTarget from {0} as it is an interface",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionTargetCannotBeCreatedForInterface(Object var1);

   @Message(
      id = 1510,
      value = "Non passivation capable bean serialized with ProxyMethodHandler"
   )
   WeldException proxyHandlerSerializedForNonSerializableBean();

   @Message(
      id = 1511,
      value = "Specializing bean {0} does not have bean type {1} of specialized bean {2}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException specializingBeanMissingSpecializedType(Object var1, Object var2, Object var3);

   @Message(
      id = 1512,
      value = "{0} cannot be constructed for {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidInjectionPointType(Object var1, Object var2);

   @Message(
      id = 1513,
      value = "An implementation of AnnotatedCallable must implement either AnnotatedConstructor or AnnotatedMethod, {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidAnnotatedCallable(Object var1);

   @Message(
      id = 1514,
      value = "An implementation of AnnotatedMember must implement either AnnotatedConstructor, AnnotatedMethod or AnnotatedField, {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidAnnotatedMember(Object var1);

   @Message(
      id = 1515,
      value = "Unable to load annotated member {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToLoadMember(Object var1);

   @Message(
      id = 1516,
      value = "Resource producer field [{0}] must not have an EL name",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException namedResourceProducerField(Object var1);

   @Message(
      id = 1517,
      value = "The type of the resource producer field [{0}] does not match the resource type {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException invalidResourceProducerType(Object var1, Object var2);

   @Message(
      id = 1518,
      value = "Cannot create Producer implementation. Declaring bean missing for a non-static member {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException declaringBeanMissing(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1519,
      value = "An InjectionTarget is created for an abstract {0}. It will not be possible to produce instances of this type!",
      format = Format.MESSAGE_FORMAT
   )
   void injectionTargetCreatedForAbstractClass(Object var1);

   @Message(
      id = 1520,
      value = "Beans with different bean names {0}, {1} cannot be specialized by a single bean {2}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beansWithDifferentBeanNamesCannotBeSpecialized(Object var1, Object var2, Object var3);

   @Message(
      id = 1521,
      value = "InjectionPoint.getAnnotated() must return either AnnotatedParameter or AnnotatedField but {0} was returned for {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidAnnotatedOfInjectionPoint(Object var1, Object var2);

   @Message(
      id = 1522,
      value = "Unable to restore InjectionPoint. No matching InjectionPoint found on {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToRestoreInjectionPoint(Object var1);

   @Message(
      id = 1523,
      value = "Unable to restore InjectionPoint. Multiple matching InjectionPoints found on {0}:\n  - {1},\n  - {2}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToRestoreInjectionPointMultiple(Object var1, Object var2, Object var3);

   @Message(
      id = 1524,
      value = "Unable to load proxy class for bean {0} with class {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException unableToLoadProxyClass(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 1525,
      value = "Instance.destroy() is not supported. The underlying context {0} does not support destroying of contextual instances",
      format = Format.MESSAGE_FORMAT
   )
   UnsupportedOperationException destroyUnsupported(Object var1);

   @Message(
      id = 1526,
      value = "Managed bean declaring a passivating scope has a non-passivation capable decorator.  Bean:  {0}  Decorator: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException passivatingBeanHasNonPassivationCapableDecorator(Object var1, Object var2);

   @Message(
      id = 1527,
      value = "Managed bean declaring a passivating scope has a non-serializable interceptor.  Bean:  {0}  Interceptor: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException passivatingBeanHasNonPassivationCapableInterceptor(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1529,
      value = "An InjectionTarget is created for a {0} which does not have any appropriate constructor. It will not be possible to produce instances of this type!",
      format = Format.MESSAGE_FORMAT
   )
   void injectionTargetCreatedForClassWithoutAppropriateConstructor(Object var1);

   @Message(
      id = 1530,
      value = "Cannot produce an instance of {0}.",
      format = Format.MESSAGE_FORMAT
   )
   CreationException injectionTargetCannotProduceInstance(Object var1);

   @Message(
      id = 1531,
      value = "Instance.iterator().remove() is not supported."
   )
   UnsupportedOperationException instanceIteratorRemoveUnsupported();

   @Message(
      id = 1532,
      value = "A passivation capable bean cannot have a null id: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException passivationCapableBeanHasNullId(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1533,
      value = "An InjectionTarget is created for a non-static inner {0}. It will not be possible to produce instances of this type!",
      format = Format.MESSAGE_FORMAT
   )
   void injectionTargetCreatedForNonStaticInnerClass(Object var1);

   @Message(
      id = 1534,
      value = "Bean class which has decorators must have a public constructor without parameters: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException decoratedHasNoNoargsConstructor(Object var1);

   @Message(
      id = 1535,
      value = "Constructor without parameters cannot be private in bean class which has decorators: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException decoratedNoargsConstructorIsPrivate(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1536,
      value = "Found {0} constructors annotated with @Inject for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void foundInjectableConstructors(Object var1, Object var2);

   @Message(
      id = 1537,
      value = "An InjectionTarget is created for a {0} which does not have any appropriate constructor.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException injectionTargetCreatedForClassWithoutAppropriateConstructorException(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1538,
      value = "Created context instance for bean {0} identified as {1}",
      format = Format.MESSAGE_FORMAT
   )
   void createdContextInstance(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1539,
      value = "Created MH initializer body for decorator proxy: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void createdMethodHandlerInitializerForDecoratorProxy(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1540,
      value = "Adding method to enterprise proxy: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void addingMethodToEnterpriseProxy(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1541,
      value = "Adding method to proxy: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void addingMethodToProxy(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1542,
      value = "Retrieving/generating proxy class {0}",
      format = Format.MESSAGE_FORMAT
   )
   void generatingProxyClass(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1543,
      value = "Created Proxy class of type {0} supporting interfaces {1}",
      format = Format.MESSAGE_FORMAT
   )
   void createdProxyClass(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1544,
      value = "MethodHandler processing returning bean instance for {0}",
      format = Format.MESSAGE_FORMAT
   )
   void methodHandlerProcessingReturningBeanInstance(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1545,
      value = "MethodHandler processing call to {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void methodHandlerProcessingCall(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1546,
      value = "Setting new MethodHandler with bean instance for {0} on {1}",
      format = Format.MESSAGE_FORMAT
   )
   void settingNewMethodHandler(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1547,
      value = "Invoking interceptor chain for method {0} on {1}",
      format = Format.MESSAGE_FORMAT
   )
   void invokingInterceptorChain(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1548,
      value = "Invoking method {0} directly on {1}",
      format = Format.MESSAGE_FORMAT
   )
   void invokingMethodDirectly(Object var1, Object var2);

   @Message(
      id = 1549,
      value = "Unable to determine parent creational context of {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException unableToDetermineParentCreationalContext(Object var1);

   @Message(
      id = 1550,
      value = "A producer field with a parameterized type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerFieldWithTypeVariableBeanTypeMustBeDependent(Object var1, String var2);

   @Message(
      id = 1551,
      value = "A producer field type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerFieldCannotHaveAWildcardBeanType(Object var1, String var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1552,
      value = "An extension ({0}) has a non-static public field ({1}).",
      format = Format.MESSAGE_FORMAT
   )
   void extensionWithNonStaticPublicField(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1553,
      value = "Proxy activated after passivation for {0}",
      format = Format.MESSAGE_FORMAT
   )
   void activatedSessionBeanProxy(Object var1);

   @Message(
      id = 1554,
      value = "Bean.{0}() returned null for {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanMethodReturnsNull(Object var1, Object var2);

   @Message(
      id = 1555,
      value = "Decorator.{0}() returned null for {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException decoratorMethodReturnsNull(Object var1, Object var2);

   @Message(
      id = 1556,
      value = "Specializing {0} cannot specialize a non-managed bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException specializingManagedBeanCanExtendOnlyManagedBeans(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1557,
      value = "Unable to dump the proxy class file for {0}",
      format = Format.MESSAGE_FORMAT
   )
   void beanCannotBeDumped(Object var1, @Cause Throwable var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1558,
      value = "Unable to create directory {0} to dump the proxy classes.",
      format = Format.MESSAGE_FORMAT
   )
   void directoryCannotBeCreated(Object var1);

   @Message(
      id = 1559,
      value = "Bean builder {0} does not define a create lifecycle callback.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanBuilderInvalidCreateCallback(Object var1);

   @Message(
      id = 1560,
      value = "Bean builder {0} does not define a destroy lifecycle callback.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanBuilderInvalidDestroyCallback(Object var1);

   @Message(
      id = 1561,
      value = "Bean builder {0} does not define a BeanManager.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanBuilderInvalidBeanManager(Object var1);

   @Message(
      id = 1562,
      value = "A producer method return type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerMethodReturnTypeInvalidTypeVariable(Object var1, String var2);

   @Message(
      id = 1563,
      value = "A producer field type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException producerFieldTypeInvalidTypeVariable(Object var1, String var2);

   @Message(
      id = 1564,
      value = "Injection point metadata injected into a stateless session bean may only be accessed within its business method invocation",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException statelessSessionBeanInjectionPointMetadataNotAvailable();

   @Message(
      id = 1565,
      value = "Interceptor builder {0} does not define an interception function.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException noInterceptionFunction(Object var1);

   @Message(
      id = 1566,
      value = "Interceptor builder {0} does not define any InterceptionType.",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException noInterceptionType(Object var1);

   @Message(
      id = 1567,
      value = "Cannot create contextual instance of {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException cannotCreateContextualInstanceOfBuilderInterceptor(Object var1);

   @Message(
      id = 1568,
      value = "Unable to create ClassFile for: {1}.",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToCreateClassFile(Object var1, @Cause Throwable var2);

   @Message(
      id = 1569,
      value = "Cannot inject injection point metadata in a non @Dependent bean: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotInjectInjectionPointMetadataIntoNonDependent(Object var1);

   @Message(
      id = 1570,
      value = "Invalid BeanConfigurator setup - no callback was specified for {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException noCallbackSpecifiedForCustomBean(Object var1);

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 1571,
      value = "Proxy for {0} created in {1} because {2}.",
      format = Format.MESSAGE_FORMAT
   )
   void generatingProxyToDefaultPackage(Object var1, Object var2, Object var3);

   @Message(
      id = 1572,
      value = "Cannot create instance of session bean from Annotated Type {0} before AfterDeploymentValidation phase.",
      format = Format.MESSAGE_FORMAT
   )
   CreationException initABDnotInvoked(Object var1);

   @Message(
      id = 1573,
      value = "Cannot obtain contextual reference for {0} - producing WeldInstance does not exist anymore",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException cannotObtainHandlerContextualReference(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1574,
      value = "Cannot destroy contextual instance for {0} - producing WeldInstance does not exist anymore",
      format = Format.MESSAGE_FORMAT
   )
   void cannotDestroyHandlerContextualReference(Object var1);

   @Message(
      id = 1575,
      value = "WeldInstance.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldInstance<Object>.",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException selectByTypeOnlyWorksOnObject();

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1576,
      value = "Using {1} to instantiate a shared proxy class {0}; the deployment implementation [{2}] does not match the instantiator the proxy was created with",
      format = Format.MESSAGE_FORMAT
   )
   void creatingProxyInstanceUsingDifferentInstantiator(Object var1, Object var2, Object var3);
}
