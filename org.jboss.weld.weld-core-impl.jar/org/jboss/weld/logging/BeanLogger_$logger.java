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
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalProductException;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.InvalidObjectException;
import org.jboss.weld.exceptions.NullInstanceException;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.exceptions.WeldException;

public class BeanLogger_$logger extends DelegatingBasicLogger implements BeanLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = BeanLogger_$logger.class.getName();
   private static final String foundOneInjectableConstructor = "WELD-000001: Exactly one constructor ({0}) annotated with @Inject defined, using it as the bean constructor for {1}";
   private static final String foundDefaultConstructor = "WELD-000002: Exactly one constructor ({0}) defined, using it as the bean constructor for {1}";
   private static final String foundOnePostConstructMethod = "WELD-000004: Exactly one post construct method ({0}) for {1}";
   private static final String foundOnePreDestroyMethod = "WELD-000006: Exactly one pre destroy method ({0}) for {1}";
   private static final String createdSessionBeanProxy = "WELD-000007: Created session bean proxy for {0}";
   private static final String callProxiedMethod = "WELD-000008: Called {0} on {1} with parameters {2} which returned {3}";
   private static final String dynamicLookupOfBuiltInNotAllowed = "WELD-000009: Dynamic lookup of {0} is not supported";
   private static final String qualifiersUsed = "WELD-000010: Using qualifiers {0} for {1}";
   private static final String creatingBean = "WELD-000012: Building bean metadata for {0}";
   private static final String usingName = "WELD-000014: Using name {0} for {1}";
   private static final String usingScope = "WELD-000016: Using scope {0} for {1}";
   private static final String circularCall = "WELD-000018: Executing producer field or method {0} on incomplete declaring bean {1} due to circular injection";
   private static final String errorDestroying = "WELD-000019: Error destroying an instance {0} of {1}";
   private static final String typeParameterMustBeConcrete = "WELD-000023: Type parameter must be a concrete type:  {0}";
   private static final String invalidResourceProducerField = "WELD-000025: Tried to create an EEResourceProducerField, but no @Resource, @PersistenceContext, @PersistenceUnit, @WebServiceRef or @EJB is present: {0}";
   private static final String securityServicesNotAvailable = "WELD-000026: Security Services not available - unable to obtain the Principal";
   private static final String transactionServicesNotAvailable = "WELD-000027: Transaction Services not available - unable to obtain the UserTransaction";
   private static final String interceptionModelNull = "WELD-000028: Interception model must not be null";
   private static final String interceptionTypeNull = "WELD-000029: InterceptionType must not be null";
   private static final String methodNull = "WELD-000030: Method must not be null";
   private static final String interceptionTypeLifecycle = "WELD-000031: InterceptionType must not be lifecycle, but it is {0}";
   private static final String interceptionTypeNotLifecycle = "WELD-000032: InterceptionType must be lifecycle, but it is {0}";
   private static final String proxyInstantiationFailed = "WELD-000033: Could not instantiate client proxy for {0}";
   private static final String proxyInstantiationBeanAccessFailed = "WELD-000034: Could not access bean correctly when creating client proxy for {0}";
   private static final String beanIdCreationFailed = "WELD-000035: There was an error creating an id for {0}";
   private static final String unexpectedUnwrappedCustomDecorator = "WELD-000036: Unexpected unwrapped custom decorator instance: {0}";
   private static final String invalidRemoveMethodInvocation = "WELD-000037: Cannot call EJB remove method directly on non-dependent scoped bean {0}";
   private static final String delegateNotOnDecorator = "WELD-000038: A bean class that is not a decorator has an injection point annotated @Delegate\n  at injection point {0}\n\tat {1}\n  StackTrace:";
   private static final String typedClassNotInHierarchy = "WELD-000039: @Typed class {0} not present in the set of bean types of {1} [{2}]";
   private static final String multipleScopesFoundFromStereotypes = "WELD-000040: All stereotypes must specify the same scope or the bean must declare a scope - declared on {0}, declared stereotypes [{1}], possible scopes {2}{3}";
   private static final String nameNotAllowedOnSpecialization = "WELD-000041: Specializing bean may not declare a bean name if it is declared by specialized bean\n  specializing: {0}\n  specialized: {1}";
   private static final String nonContainerDecorator = "WELD-000042: Cannot operate on non container provided decorator {0}";
   private static final String beanNotEeResourceProducer = "WELD-000043: The following bean is not an EE resource producer:  {0}";
   private static final String nullInstance = "WELD-000044: Unable to obtain instance from {0}";
   private static final String serializationProxyRequired = "WELD-000045: Unable to deserialize object - serialization proxy is required";
   private static final String onlyOneScopeAllowed = "WELD-000046: At most one scope may be specified on {0}";
   private static final String specializingBeanMustExtendABean = "WELD-000047: Specializing bean must extend another bean:  {0}";
   private static final String conflictingInterceptorBindings = "WELD-000048: Conflicting interceptor bindings found on {0}";
   private static final String invocationError = "WELD-000049: Unable to invoke {0} on {1}";
   private static final String producerCastError = "WELD-000050: Cannot cast producer type {0} to bean type {1}";
   private static final String nullNotAllowedFromProducer = "WELD-000052: Cannot return null from a non-dependent producer method: {0}\n\tat {1}\n  StackTrace:";
   private static final String nonSerializableProductError = "WELD-000053: Producers cannot declare passivating scope and return a non-serializable class: {0}\n\tat {1}\n  StackTrace:";
   private static final String unserializableProductInjectionError = "WELD-000054: Producers cannot produce unserializable instances for injection into an injection point that requires a passivation capable dependency\n  Producer:  {0}\n\tat {1}\n  Injection Point:  {2}\n\tat {3}\n  StackTrace:";
   private static final String noDelegateInjectionPoint = "WELD-000059: No delegate injection point defined for {0}";
   private static final String tooManyDelegateInjectionPoints = "WELD-000060: Too many delegate injection points defined for {0}";
   private static final String delegateMustSupportEveryDecoratedType = "WELD-000061: The delegate type does not extend or implement the decorated type. \n  Decorated type: {0}\n  Decorator: {1}";
   private static final String unableToProcessDecoratedType = "WELD-000064: Unable to process decorated type: {0}";
   private static final String multipleDisposeParams = "WELD-000066: {0} has more than one @Dispose parameter \n\tat {1}\n  StackTrace:";
   private static final String inconsistentAnnotationsOnMethod = "WELD-000067: {0} is not allowed on same method as {1}, see {2}\n\tat {3}\n  StackTrace:";
   private static final String methodNotBusinessMethod = "WELD-000068: {0} method {1} is not a business method of {2}\n\tat {3}\n  StackTrace:";
   private static final String simpleBeanAsNonStaticInnerClassNotAllowed = "WELD-000070: Simple bean {0} cannot be a non-static inner class";
   private static final String managedBeanWithParameterizedBeanClassMustBeDependent = "WELD-000071: Managed bean with a parameterized bean class must be @Dependent: {0}";
   private static final String passivatingBeanNeedsSerializableImpl = "WELD-000072: Bean declaring a passivating scope must be passivation capable.  Bean:  {0}";
   private static final String finalBeanClassWithDecoratorsNotAllowed = "WELD-000073: Bean class which has decorators cannot be declared final:  {0}";
   private static final String publicFieldOnNormalScopedBeanNotAllowed = "WELD-000075: Normal scoped managed bean implementation class has a public field:  {0}";
   private static final String parameterAnnotationNotAllowedOnConstructor = "WELD-000076: Bean constructor must not have a parameter annotated with {0}: {1}\n\tat {2}\n  StackTrace:";
   private static final String multipleDisposalMethods = "WELD-000077: Cannot declare multiple disposal methods for this producer method.\n\nProducer method:  {0}\nDisposal methods:  {1}";
   private static final String producerMethodNotSpecializing = "WELD-000078: Specialized producer method does not override another producer method: {0}\n\tat {1}\n  StackTrace:";
   private static final String sessionBeanProxyInstantiationFailed = "WELD-000079: Could not instantiate a proxy for a session bean:  {0}\n  Proxy: {1}";
   private static final String ejbCannotBeInterceptor = "WELD-000080: Enterprise beans cannot be interceptors:  {0}";
   private static final String ejbCannotBeDecorator = "WELD-000081: Enterprise beans cannot be decorators:  {0}";
   private static final String scopeNotAllowedOnStatelessSessionBean = "WELD-000082: Scope {0} is not allowed on stateless session beans for {1}. Only @Dependent is allowed.";
   private static final String scopeNotAllowedOnSingletonBean = "WELD-000083: Scope {0} is not allowed on singleton session beans for {1}. Only @Dependent and @ApplicationScoped is allowed.";
   private static final String specializingEnterpriseBeanMustExtendAnEnterpriseBean = "WELD-000084: Specializing enterprise bean must extend another enterprise bean:  {0}";
   private static final String cannotDestroyNullBean = "WELD-000085: Cannot destroy null instance of {0}";
   private static final String cannotDestroyEnterpriseBeanNotCreated = "WELD-000086: Cannot destroy session bean instance not created by the container:  {0}";
   private static final String messageDrivenBeansCannotBeManaged = "WELD-000087: Message driven beans cannot be Managed Beans:  {0}";
   private static final String observerMethodMustBeStaticOrBusiness = "WELD-000088: Observer method must be static or local business method:  {0}\n\tat {1}\n  StackTrace:";
   private static final String tooManyEjbsForClass = "WELD-000089: Unable to determine EJB for {0}, multiple EJBs with that class:  {1}";
   private static final String abstractMethodMustMatchDecoratedType = "WELD-000090: A decorator has an abstract method that is not declared by any decorated type\n  Method: {0}\n\tat {1}\n  StackTrace:";
   private static final String injectedFieldCannotBeProducer = "WELD-000094: Injected field {0} cannot be annotated @Produces on {1}";
   private static final String genericSessionBeanMustBeDependent = "WELD-000095: Session bean with generic class {0} must be @Dependent scope";
   private static final String producerFieldOnSessionBeanMustBeStatic = "WELD-000096: Producer fields on session beans must be static. Field {0} declared on {1}";
   private static final String producerMethodWithTypeVariableReturnTypeMustBeDependent = "WELD-000097: A producer method with a parameterized return type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String producerMethodCannotHaveAWildcardReturnType = "WELD-000098: A producer method return type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String cannotLoadClass = "WELD-000099: Cannot load class {0} during deserialization of proxy";
   private static final String proxyDeserializationFailure = "WELD-001500: Failed to deserialize proxy object with beanId {0}";
   private static final String beanInstanceNotSetOnProxy = "WELD-001501: Method call requires a BeanInstance which has not been set for this proxy {0}";
   private static final String nonDependentResourceProducerField = "WELD-001502: Resource producer field [{0}] must be @Dependent scoped";
   private static final String finalBeanClassWithInterceptorsNotAllowed = "WELD-001503: Bean class which has interceptors cannot be declared final:  {0}";
   private static final String finalInterceptedBeanMethodNotAllowed = "WELD-001504: Intercepted bean method {0} (intercepted by {1}) cannot be declared final";
   private static final String finalMethodNotIntercepted = "WELD-001505: Method {0} cannot be intercepted by {1} - will be ignored by interceptors and should never be invoked upon the proxy instance!";
   private static final String createdNewClientProxyType = "WELD-001506: Created new client proxy of type {0} for bean {1} with ID {2}";
   private static final String lookedUpClientProxy = "WELD-001507: Located client proxy of type {0} for bean {1}";
   private static final String injectionTargetCannotBeCreatedForInterface = "WELD-001508: Cannot create an InjectionTarget from {0} as it is an interface";
   private static final String proxyHandlerSerializedForNonSerializableBean = "WELD-001510: Non passivation capable bean serialized with ProxyMethodHandler";
   private static final String specializingBeanMissingSpecializedType = "WELD-001511: Specializing bean {0} does not have bean type {1} of specialized bean {2}";
   private static final String invalidInjectionPointType = "WELD-001512: {0} cannot be constructed for {1}";
   private static final String invalidAnnotatedCallable = "WELD-001513: An implementation of AnnotatedCallable must implement either AnnotatedConstructor or AnnotatedMethod, {0}";
   private static final String invalidAnnotatedMember = "WELD-001514: An implementation of AnnotatedMember must implement either AnnotatedConstructor, AnnotatedMethod or AnnotatedField, {0}";
   private static final String unableToLoadMember = "WELD-001515: Unable to load annotated member {0}";
   private static final String namedResourceProducerField = "WELD-001516: Resource producer field [{0}] must not have an EL name";
   private static final String invalidResourceProducerType = "WELD-001517: The type of the resource producer field [{0}] does not match the resource type {1}";
   private static final String declaringBeanMissing = "WELD-001518: Cannot create Producer implementation. Declaring bean missing for a non-static member {0}";
   private static final String injectionTargetCreatedForAbstractClass = "WELD-001519: An InjectionTarget is created for an abstract {0}. It will not be possible to produce instances of this type!";
   private static final String beansWithDifferentBeanNamesCannotBeSpecialized = "WELD-001520: Beans with different bean names {0}, {1} cannot be specialized by a single bean {2}";
   private static final String invalidAnnotatedOfInjectionPoint = "WELD-001521: InjectionPoint.getAnnotated() must return either AnnotatedParameter or AnnotatedField but {0} was returned for {1}";
   private static final String unableToRestoreInjectionPoint = "WELD-001522: Unable to restore InjectionPoint. No matching InjectionPoint found on {0}";
   private static final String unableToRestoreInjectionPointMultiple = "WELD-001523: Unable to restore InjectionPoint. Multiple matching InjectionPoints found on {0}:\n  - {1},\n  - {2}";
   private static final String unableToLoadProxyClass = "WELD-001524: Unable to load proxy class for bean {0} with class {1}";
   private static final String destroyUnsupported = "WELD-001525: Instance.destroy() is not supported. The underlying context {0} does not support destroying of contextual instances";
   private static final String passivatingBeanHasNonPassivationCapableDecorator = "WELD-001526: Managed bean declaring a passivating scope has a non-passivation capable decorator.  Bean:  {0}  Decorator: {1}";
   private static final String passivatingBeanHasNonPassivationCapableInterceptor = "WELD-001527: Managed bean declaring a passivating scope has a non-serializable interceptor.  Bean:  {0}  Interceptor: {1}";
   private static final String injectionTargetCreatedForClassWithoutAppropriateConstructor = "WELD-001529: An InjectionTarget is created for a {0} which does not have any appropriate constructor. It will not be possible to produce instances of this type!";
   private static final String injectionTargetCannotProduceInstance = "WELD-001530: Cannot produce an instance of {0}.";
   private static final String instanceIteratorRemoveUnsupported = "WELD-001531: Instance.iterator().remove() is not supported.";
   private static final String passivationCapableBeanHasNullId = "WELD-001532: A passivation capable bean cannot have a null id: {0}";
   private static final String injectionTargetCreatedForNonStaticInnerClass = "WELD-001533: An InjectionTarget is created for a non-static inner {0}. It will not be possible to produce instances of this type!";
   private static final String decoratedHasNoNoargsConstructor = "WELD-001534: Bean class which has decorators must have a public constructor without parameters: {0}";
   private static final String decoratedNoargsConstructorIsPrivate = "WELD-001535: Constructor without parameters cannot be private in bean class which has decorators: {0}\n\tat {1}\n  StackTrace:";
   private static final String foundInjectableConstructors = "WELD-001536: Found {0} constructors annotated with @Inject for {1}";
   private static final String injectionTargetCreatedForClassWithoutAppropriateConstructorException = "WELD-001537: An InjectionTarget is created for a {0} which does not have any appropriate constructor.";
   private static final String createdContextInstance = "WELD-001538: Created context instance for bean {0} identified as {1}";
   private static final String createdMethodHandlerInitializerForDecoratorProxy = "WELD-001539: Created MH initializer body for decorator proxy: {0}";
   private static final String addingMethodToEnterpriseProxy = "WELD-001540: Adding method to enterprise proxy: {0}";
   private static final String addingMethodToProxy = "WELD-001541: Adding method to proxy: {0}";
   private static final String generatingProxyClass = "WELD-001542: Retrieving/generating proxy class {0}";
   private static final String createdProxyClass = "WELD-001543: Created Proxy class of type {0} supporting interfaces {1}";
   private static final String methodHandlerProcessingReturningBeanInstance = "WELD-001544: MethodHandler processing returning bean instance for {0}";
   private static final String methodHandlerProcessingCall = "WELD-001545: MethodHandler processing call to {0} for {1}";
   private static final String settingNewMethodHandler = "WELD-001546: Setting new MethodHandler with bean instance for {0} on {1}";
   private static final String invokingInterceptorChain = "WELD-001547: Invoking interceptor chain for method {0} on {1}";
   private static final String invokingMethodDirectly = "WELD-001548: Invoking method {0} directly on {1}";
   private static final String unableToDetermineParentCreationalContext = "WELD-001549: Unable to determine parent creational context of {0}";
   private static final String producerFieldWithTypeVariableBeanTypeMustBeDependent = "WELD-001550: A producer field with a parameterized type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String producerFieldCannotHaveAWildcardBeanType = "WELD-001551: A producer field type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String extensionWithNonStaticPublicField = "WELD-001552: An extension ({0}) has a non-static public field ({1}).";
   private static final String activatedSessionBeanProxy = "WELD-001553: Proxy activated after passivation for {0}";
   private static final String beanMethodReturnsNull = "WELD-001554: Bean.{0}() returned null for {1}";
   private static final String decoratorMethodReturnsNull = "WELD-001555: Decorator.{0}() returned null for {1}";
   private static final String specializingManagedBeanCanExtendOnlyManagedBeans = "WELD-001556: Specializing {0} cannot specialize a non-managed bean {1}";
   private static final String beanCannotBeDumped = "WELD-001557: Unable to dump the proxy class file for {0}";
   private static final String directoryCannotBeCreated = "WELD-001558: Unable to create directory {0} to dump the proxy classes.";
   private static final String beanBuilderInvalidCreateCallback = "WELD-001559: Bean builder {0} does not define a create lifecycle callback.";
   private static final String beanBuilderInvalidDestroyCallback = "WELD-001560: Bean builder {0} does not define a destroy lifecycle callback.";
   private static final String beanBuilderInvalidBeanManager = "WELD-001561: Bean builder {0} does not define a BeanManager.";
   private static final String producerMethodReturnTypeInvalidTypeVariable = "WELD-001562: A producer method return type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String producerFieldTypeInvalidTypeVariable = "WELD-001563: A producer field type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:";
   private static final String statelessSessionBeanInjectionPointMetadataNotAvailable = "WELD-001564: Injection point metadata injected into a stateless session bean may only be accessed within its business method invocation";
   private static final String noInterceptionFunction = "WELD-001565: Interceptor builder {0} does not define an interception function.";
   private static final String noInterceptionType = "WELD-001566: Interceptor builder {0} does not define any InterceptionType.";
   private static final String cannotCreateContextualInstanceOfBuilderInterceptor = "WELD-001567: Cannot create contextual instance of {0}";
   private static final String unableToCreateClassFile = "WELD-001568: Unable to create ClassFile for: {1}.";
   private static final String cannotInjectInjectionPointMetadataIntoNonDependent = "WELD-001569: Cannot inject injection point metadata in a non @Dependent bean: {0}";
   private static final String noCallbackSpecifiedForCustomBean = "WELD-001570: Invalid BeanConfigurator setup - no callback was specified for {0}";
   private static final String generatingProxyToDefaultPackage = "WELD-001571: Proxy for {0} created in {1} because {2}.";
   private static final String initABDnotInvoked = "WELD-001572: Cannot create instance of session bean from Annotated Type {0} before AfterDeploymentValidation phase.";
   private static final String cannotObtainHandlerContextualReference = "WELD-001573: Cannot obtain contextual reference for {0} - producing WeldInstance does not exist anymore";
   private static final String cannotDestroyHandlerContextualReference = "WELD-001574: Cannot destroy contextual instance for {0} - producing WeldInstance does not exist anymore";
   private static final String selectByTypeOnlyWorksOnObject = "WELD-001575: WeldInstance.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldInstance<Object>.";
   private static final String creatingProxyInstanceUsingDifferentInstantiator = "WELD-001576: Using {1} to instantiate a shared proxy class {0}; the deployment implementation [{2}] does not match the instantiator the proxy was created with";
   private static final String catchingDebug = "Catching";

   public BeanLogger_$logger(Logger log) {
      super(log);
   }

   public final void foundOneInjectableConstructor(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundOneInjectableConstructor$str(), param1, param2);
   }

   protected String foundOneInjectableConstructor$str() {
      return "WELD-000001: Exactly one constructor ({0}) annotated with @Inject defined, using it as the bean constructor for {1}";
   }

   public final void foundDefaultConstructor(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundDefaultConstructor$str(), param1, param2);
   }

   protected String foundDefaultConstructor$str() {
      return "WELD-000002: Exactly one constructor ({0}) defined, using it as the bean constructor for {1}";
   }

   public final void foundOnePostConstructMethod(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundOnePostConstructMethod$str(), param1, param2);
   }

   protected String foundOnePostConstructMethod$str() {
      return "WELD-000004: Exactly one post construct method ({0}) for {1}";
   }

   public final void foundOnePreDestroyMethod(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundOnePreDestroyMethod$str(), param1, param2);
   }

   protected String foundOnePreDestroyMethod$str() {
      return "WELD-000006: Exactly one pre destroy method ({0}) for {1}";
   }

   public final void createdSessionBeanProxy(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.createdSessionBeanProxy$str(), param1);
   }

   protected String createdSessionBeanProxy$str() {
      return "WELD-000007: Created session bean proxy for {0}";
   }

   public final void callProxiedMethod(Object param1, Object param2, Object param3, Object param4) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.callProxiedMethod$str(), new Object[]{param1, param2, param3, param4});
   }

   protected String callProxiedMethod$str() {
      return "WELD-000008: Called {0} on {1} with parameters {2} which returned {3}";
   }

   protected String dynamicLookupOfBuiltInNotAllowed$str() {
      return "WELD-000009: Dynamic lookup of {0} is not supported";
   }

   public final IllegalArgumentException dynamicLookupOfBuiltInNotAllowed(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.dynamicLookupOfBuiltInNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void qualifiersUsed(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.qualifiersUsed$str(), param1, param2);
   }

   protected String qualifiersUsed$str() {
      return "WELD-000010: Using qualifiers {0} for {1}";
   }

   public final void creatingBean(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.creatingBean$str(), param1);
   }

   protected String creatingBean$str() {
      return "WELD-000012: Building bean metadata for {0}";
   }

   public final void usingName(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.usingName$str(), param1, param2);
   }

   protected String usingName$str() {
      return "WELD-000014: Using name {0} for {1}";
   }

   public final void usingScope(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.usingScope$str(), param1, param2);
   }

   protected String usingScope$str() {
      return "WELD-000016: Using scope {0} for {1}";
   }

   public final void circularCall(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.circularCall$str(), param1, param2);
   }

   protected String circularCall$str() {
      return "WELD-000018: Executing producer field or method {0} on incomplete declaring bean {1} due to circular injection";
   }

   public final void errorDestroying(Object param1, Object param2) {
      super.log.logv(FQCN, Level.ERROR, (Throwable)null, this.errorDestroying$str(), param1, param2);
   }

   protected String errorDestroying$str() {
      return "WELD-000019: Error destroying an instance {0} of {1}";
   }

   protected String typeParameterMustBeConcrete$str() {
      return "WELD-000023: Type parameter must be a concrete type:  {0}";
   }

   public final String typeParameterMustBeConcrete(Object param1) {
      return MessageFormat.format(this.typeParameterMustBeConcrete$str(), param1);
   }

   protected String invalidResourceProducerField$str() {
      return "WELD-000025: Tried to create an EEResourceProducerField, but no @Resource, @PersistenceContext, @PersistenceUnit, @WebServiceRef or @EJB is present: {0}";
   }

   public final IllegalStateException invalidResourceProducerField(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.invalidResourceProducerField$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String securityServicesNotAvailable$str() {
      return "WELD-000026: Security Services not available - unable to obtain the Principal";
   }

   public final IllegalStateException securityServicesNotAvailable() {
      IllegalStateException result = new IllegalStateException(String.format(this.securityServicesNotAvailable$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String transactionServicesNotAvailable$str() {
      return "WELD-000027: Transaction Services not available - unable to obtain the UserTransaction";
   }

   public final IllegalStateException transactionServicesNotAvailable() {
      IllegalStateException result = new IllegalStateException(String.format(this.transactionServicesNotAvailable$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptionModelNull$str() {
      return "WELD-000028: Interception model must not be null";
   }

   public final IllegalArgumentException interceptionModelNull() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.interceptionModelNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptionTypeNull$str() {
      return "WELD-000029: InterceptionType must not be null";
   }

   public final IllegalArgumentException interceptionTypeNull() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.interceptionTypeNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String methodNull$str() {
      return "WELD-000030: Method must not be null";
   }

   public final IllegalArgumentException methodNull() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.methodNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptionTypeLifecycle$str() {
      return "WELD-000031: InterceptionType must not be lifecycle, but it is {0}";
   }

   public final IllegalArgumentException interceptionTypeLifecycle(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.interceptionTypeLifecycle$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String interceptionTypeNotLifecycle$str() {
      return "WELD-000032: InterceptionType must be lifecycle, but it is {0}";
   }

   public final IllegalArgumentException interceptionTypeNotLifecycle(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.interceptionTypeNotLifecycle$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String proxyInstantiationFailed$str() {
      return "WELD-000033: Could not instantiate client proxy for {0}";
   }

   public final String proxyInstantiationFailed(Object param1) {
      return MessageFormat.format(this.proxyInstantiationFailed$str(), param1);
   }

   protected String proxyInstantiationBeanAccessFailed$str() {
      return "WELD-000034: Could not access bean correctly when creating client proxy for {0}";
   }

   public final String proxyInstantiationBeanAccessFailed(Object param1) {
      return MessageFormat.format(this.proxyInstantiationBeanAccessFailed$str(), param1);
   }

   protected String beanIdCreationFailed$str() {
      return "WELD-000035: There was an error creating an id for {0}";
   }

   public final DefinitionException beanIdCreationFailed(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanIdCreationFailed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unexpectedUnwrappedCustomDecorator$str() {
      return "WELD-000036: Unexpected unwrapped custom decorator instance: {0}";
   }

   public final IllegalStateException unexpectedUnwrappedCustomDecorator(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unexpectedUnwrappedCustomDecorator$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidRemoveMethodInvocation$str() {
      return "WELD-000037: Cannot call EJB remove method directly on non-dependent scoped bean {0}";
   }

   public final UnsupportedOperationException invalidRemoveMethodInvocation(Object param1) {
      UnsupportedOperationException result = new UnsupportedOperationException(MessageFormat.format(this.invalidRemoveMethodInvocation$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String delegateNotOnDecorator$str() {
      return "WELD-000038: A bean class that is not a decorator has an injection point annotated @Delegate\n  at injection point {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException delegateNotOnDecorator(Object ip, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.delegateNotOnDecorator$str(), ip, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String typedClassNotInHierarchy$str() {
      return "WELD-000039: @Typed class {0} not present in the set of bean types of {1} [{2}]";
   }

   public final DefinitionException typedClassNotInHierarchy(Object param1, Object param2, Object types) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.typedClassNotInHierarchy$str(), param1, param2, types));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleScopesFoundFromStereotypes$str() {
      return "WELD-000040: All stereotypes must specify the same scope or the bean must declare a scope - declared on {0}, declared stereotypes [{1}], possible scopes {2}{3}";
   }

   public final DefinitionException multipleScopesFoundFromStereotypes(Object declaredOn, Object stereotypes, Object possibleScopes, String stack) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleScopesFoundFromStereotypes$str(), declaredOn, stereotypes, possibleScopes, stack));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nameNotAllowedOnSpecialization$str() {
      return "WELD-000041: Specializing bean may not declare a bean name if it is declared by specialized bean\n  specializing: {0}\n  specialized: {1}";
   }

   public final DefinitionException nameNotAllowedOnSpecialization(Object specializing, Object specialized) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.nameNotAllowedOnSpecialization$str(), specializing, specialized));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonContainerDecorator$str() {
      return "WELD-000042: Cannot operate on non container provided decorator {0}";
   }

   public final IllegalStateException nonContainerDecorator(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.nonContainerDecorator$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanNotEeResourceProducer$str() {
      return "WELD-000043: The following bean is not an EE resource producer:  {0}";
   }

   public final IllegalStateException beanNotEeResourceProducer(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.beanNotEeResourceProducer$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nullInstance$str() {
      return "WELD-000044: Unable to obtain instance from {0}";
   }

   public final NullInstanceException nullInstance(Object param1) {
      NullInstanceException result = new NullInstanceException(MessageFormat.format(this.nullInstance$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String serializationProxyRequired$str() {
      return "WELD-000045: Unable to deserialize object - serialization proxy is required";
   }

   public final InvalidObjectException serializationProxyRequired() {
      InvalidObjectException result = new InvalidObjectException(String.format(this.serializationProxyRequired$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String onlyOneScopeAllowed$str() {
      return "WELD-000046: At most one scope may be specified on {0}";
   }

   public final DefinitionException onlyOneScopeAllowed(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.onlyOneScopeAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String specializingBeanMustExtendABean$str() {
      return "WELD-000047: Specializing bean must extend another bean:  {0}";
   }

   public final DefinitionException specializingBeanMustExtendABean(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.specializingBeanMustExtendABean$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String conflictingInterceptorBindings$str() {
      return "WELD-000048: Conflicting interceptor bindings found on {0}";
   }

   public final String conflictingInterceptorBindings(Object param1) {
      return MessageFormat.format(this.conflictingInterceptorBindings$str(), param1);
   }

   protected String invocationError$str() {
      return "WELD-000049: Unable to invoke {0} on {1}";
   }

   public final WeldException invocationError(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.invocationError$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerCastError$str() {
      return "WELD-000050: Cannot cast producer type {0} to bean type {1}";
   }

   public final WeldException producerCastError(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.producerCastError$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nullNotAllowedFromProducer$str() {
      return "WELD-000052: Cannot return null from a non-dependent producer method: {0}\n\tat {1}\n  StackTrace:";
   }

   public final IllegalProductException nullNotAllowedFromProducer(Object param1, Object stackElement) {
      IllegalProductException result = new IllegalProductException(MessageFormat.format(this.nullNotAllowedFromProducer$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonSerializableProductError$str() {
      return "WELD-000053: Producers cannot declare passivating scope and return a non-serializable class: {0}\n\tat {1}\n  StackTrace:";
   }

   public final IllegalProductException nonSerializableProductError(Object param1, Object stackElement) {
      IllegalProductException result = new IllegalProductException(MessageFormat.format(this.nonSerializableProductError$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unserializableProductInjectionError$str() {
      return "WELD-000054: Producers cannot produce unserializable instances for injection into an injection point that requires a passivation capable dependency\n  Producer:  {0}\n\tat {1}\n  Injection Point:  {2}\n\tat {3}\n  StackTrace:";
   }

   public final IllegalProductException unserializableProductInjectionError(Object producer, Object producerStackElement, Object ip, Object ipStackElement) {
      IllegalProductException result = new IllegalProductException(MessageFormat.format(this.unserializableProductInjectionError$str(), producer, producerStackElement, ip, ipStackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noDelegateInjectionPoint$str() {
      return "WELD-000059: No delegate injection point defined for {0}";
   }

   public final DefinitionException noDelegateInjectionPoint(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.noDelegateInjectionPoint$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String tooManyDelegateInjectionPoints$str() {
      return "WELD-000060: Too many delegate injection points defined for {0}";
   }

   public final DefinitionException tooManyDelegateInjectionPoints(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.tooManyDelegateInjectionPoints$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String delegateMustSupportEveryDecoratedType$str() {
      return "WELD-000061: The delegate type does not extend or implement the decorated type. \n  Decorated type: {0}\n  Decorator: {1}";
   }

   public final DefinitionException delegateMustSupportEveryDecoratedType(Object decoratedType, Object decorator) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.delegateMustSupportEveryDecoratedType$str(), decoratedType, decorator));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToProcessDecoratedType$str() {
      return "WELD-000064: Unable to process decorated type: {0}";
   }

   public final IllegalStateException unableToProcessDecoratedType(Object decoratedType) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToProcessDecoratedType$str(), decoratedType));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleDisposeParams$str() {
      return "WELD-000066: {0} has more than one @Dispose parameter \n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException multipleDisposeParams(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleDisposeParams$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String inconsistentAnnotationsOnMethod$str() {
      return "WELD-000067: {0} is not allowed on same method as {1}, see {2}\n\tat {3}\n  StackTrace:";
   }

   public final DefinitionException inconsistentAnnotationsOnMethod(Object param1, Object param2, Object param3, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.inconsistentAnnotationsOnMethod$str(), param1, param2, param3, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String methodNotBusinessMethod$str() {
      return "WELD-000068: {0} method {1} is not a business method of {2}\n\tat {3}\n  StackTrace:";
   }

   public final DefinitionException methodNotBusinessMethod(Object methodType, Object param1, Object param2, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.methodNotBusinessMethod$str(), methodType, param1, param2, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String simpleBeanAsNonStaticInnerClassNotAllowed$str() {
      return "WELD-000070: Simple bean {0} cannot be a non-static inner class";
   }

   public final DefinitionException simpleBeanAsNonStaticInnerClassNotAllowed(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.simpleBeanAsNonStaticInnerClassNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String managedBeanWithParameterizedBeanClassMustBeDependent$str() {
      return "WELD-000071: Managed bean with a parameterized bean class must be @Dependent: {0}";
   }

   public final DefinitionException managedBeanWithParameterizedBeanClassMustBeDependent(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.managedBeanWithParameterizedBeanClassMustBeDependent$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingBeanNeedsSerializableImpl$str() {
      return "WELD-000072: Bean declaring a passivating scope must be passivation capable.  Bean:  {0}";
   }

   public final DeploymentException passivatingBeanNeedsSerializableImpl(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.passivatingBeanNeedsSerializableImpl$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String finalBeanClassWithDecoratorsNotAllowed$str() {
      return "WELD-000073: Bean class which has decorators cannot be declared final:  {0}";
   }

   public final DeploymentException finalBeanClassWithDecoratorsNotAllowed(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.finalBeanClassWithDecoratorsNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String publicFieldOnNormalScopedBeanNotAllowed$str() {
      return "WELD-000075: Normal scoped managed bean implementation class has a public field:  {0}";
   }

   public final DefinitionException publicFieldOnNormalScopedBeanNotAllowed(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.publicFieldOnNormalScopedBeanNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String parameterAnnotationNotAllowedOnConstructor$str() {
      return "WELD-000076: Bean constructor must not have a parameter annotated with {0}: {1}\n\tat {2}\n  StackTrace:";
   }

   public final DefinitionException parameterAnnotationNotAllowedOnConstructor(Object param1, Object param2, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.parameterAnnotationNotAllowedOnConstructor$str(), param1, param2, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleDisposalMethods$str() {
      return "WELD-000077: Cannot declare multiple disposal methods for this producer method.\n\nProducer method:  {0}\nDisposal methods:  {1}";
   }

   public final DefinitionException multipleDisposalMethods(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleDisposalMethods$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerMethodNotSpecializing$str() {
      return "WELD-000078: Specialized producer method does not override another producer method: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerMethodNotSpecializing(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerMethodNotSpecializing$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String sessionBeanProxyInstantiationFailed$str() {
      return "WELD-000079: Could not instantiate a proxy for a session bean:  {0}\n  Proxy: {1}";
   }

   public final CreationException sessionBeanProxyInstantiationFailed(Object sessionBean, Object proxyClass, Throwable cause) {
      CreationException result = new CreationException(MessageFormat.format(this.sessionBeanProxyInstantiationFailed$str(), sessionBean, proxyClass), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ejbCannotBeInterceptor$str() {
      return "WELD-000080: Enterprise beans cannot be interceptors:  {0}";
   }

   public final DefinitionException ejbCannotBeInterceptor(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.ejbCannotBeInterceptor$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ejbCannotBeDecorator$str() {
      return "WELD-000081: Enterprise beans cannot be decorators:  {0}";
   }

   public final DefinitionException ejbCannotBeDecorator(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.ejbCannotBeDecorator$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String scopeNotAllowedOnStatelessSessionBean$str() {
      return "WELD-000082: Scope {0} is not allowed on stateless session beans for {1}. Only @Dependent is allowed.";
   }

   public final DefinitionException scopeNotAllowedOnStatelessSessionBean(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.scopeNotAllowedOnStatelessSessionBean$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String scopeNotAllowedOnSingletonBean$str() {
      return "WELD-000083: Scope {0} is not allowed on singleton session beans for {1}. Only @Dependent and @ApplicationScoped is allowed.";
   }

   public final DefinitionException scopeNotAllowedOnSingletonBean(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.scopeNotAllowedOnSingletonBean$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String specializingEnterpriseBeanMustExtendAnEnterpriseBean$str() {
      return "WELD-000084: Specializing enterprise bean must extend another enterprise bean:  {0}";
   }

   public final DefinitionException specializingEnterpriseBeanMustExtendAnEnterpriseBean(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.specializingEnterpriseBeanMustExtendAnEnterpriseBean$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotDestroyNullBean$str() {
      return "WELD-000085: Cannot destroy null instance of {0}";
   }

   public final IllegalArgumentException cannotDestroyNullBean(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotDestroyNullBean$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotDestroyEnterpriseBeanNotCreated$str() {
      return "WELD-000086: Cannot destroy session bean instance not created by the container:  {0}";
   }

   public final IllegalArgumentException cannotDestroyEnterpriseBeanNotCreated(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotDestroyEnterpriseBeanNotCreated$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String messageDrivenBeansCannotBeManaged$str() {
      return "WELD-000087: Message driven beans cannot be Managed Beans:  {0}";
   }

   public final DefinitionException messageDrivenBeansCannotBeManaged(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.messageDrivenBeansCannotBeManaged$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String observerMethodMustBeStaticOrBusiness$str() {
      return "WELD-000088: Observer method must be static or local business method:  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException observerMethodMustBeStaticOrBusiness(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.observerMethodMustBeStaticOrBusiness$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String tooManyEjbsForClass$str() {
      return "WELD-000089: Unable to determine EJB for {0}, multiple EJBs with that class:  {1}";
   }

   public final IllegalStateException tooManyEjbsForClass(Object param1, Object param2) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.tooManyEjbsForClass$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String abstractMethodMustMatchDecoratedType$str() {
      return "WELD-000090: A decorator has an abstract method that is not declared by any decorated type\n  Method: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException abstractMethodMustMatchDecoratedType(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.abstractMethodMustMatchDecoratedType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String injectedFieldCannotBeProducer$str() {
      return "WELD-000094: Injected field {0} cannot be annotated @Produces on {1}";
   }

   public final DefinitionException injectedFieldCannotBeProducer(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectedFieldCannotBeProducer$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String genericSessionBeanMustBeDependent$str() {
      return "WELD-000095: Session bean with generic class {0} must be @Dependent scope";
   }

   public final DefinitionException genericSessionBeanMustBeDependent(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.genericSessionBeanMustBeDependent$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerFieldOnSessionBeanMustBeStatic$str() {
      return "WELD-000096: Producer fields on session beans must be static. Field {0} declared on {1}";
   }

   public final DefinitionException producerFieldOnSessionBeanMustBeStatic(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerFieldOnSessionBeanMustBeStatic$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerMethodWithTypeVariableReturnTypeMustBeDependent$str() {
      return "WELD-000097: A producer method with a parameterized return type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerMethodWithTypeVariableReturnTypeMustBeDependent(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerMethodWithTypeVariableReturnTypeMustBeDependent$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerMethodCannotHaveAWildcardReturnType$str() {
      return "WELD-000098: A producer method return type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerMethodCannotHaveAWildcardReturnType(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerMethodCannotHaveAWildcardReturnType$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotLoadClass$str() {
      return "WELD-000099: Cannot load class {0} during deserialization of proxy";
   }

   public final WeldException cannotLoadClass(Object param1, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.cannotLoadClass$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String proxyDeserializationFailure$str() {
      return "WELD-001500: Failed to deserialize proxy object with beanId {0}";
   }

   public final WeldException proxyDeserializationFailure(Object param1) {
      WeldException result = new WeldException(MessageFormat.format(this.proxyDeserializationFailure$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanInstanceNotSetOnProxy$str() {
      return "WELD-001501: Method call requires a BeanInstance which has not been set for this proxy {0}";
   }

   public final WeldException beanInstanceNotSetOnProxy(Object param1) {
      WeldException result = new WeldException(MessageFormat.format(this.beanInstanceNotSetOnProxy$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonDependentResourceProducerField$str() {
      return "WELD-001502: Resource producer field [{0}] must be @Dependent scoped";
   }

   public final DefinitionException nonDependentResourceProducerField(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.nonDependentResourceProducerField$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String finalBeanClassWithInterceptorsNotAllowed$str() {
      return "WELD-001503: Bean class which has interceptors cannot be declared final:  {0}";
   }

   public final DeploymentException finalBeanClassWithInterceptorsNotAllowed(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.finalBeanClassWithInterceptorsNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String finalInterceptedBeanMethodNotAllowed$str() {
      return "WELD-001504: Intercepted bean method {0} (intercepted by {1}) cannot be declared final";
   }

   public final DeploymentException finalInterceptedBeanMethodNotAllowed(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.finalInterceptedBeanMethodNotAllowed$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void finalMethodNotIntercepted(Object method, Object interceptor) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.finalMethodNotIntercepted$str(), method, interceptor);
   }

   protected String finalMethodNotIntercepted$str() {
      return "WELD-001505: Method {0} cannot be intercepted by {1} - will be ignored by interceptors and should never be invoked upon the proxy instance!";
   }

   public final void createdNewClientProxyType(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.createdNewClientProxyType$str(), param1, param2, param3);
   }

   protected String createdNewClientProxyType$str() {
      return "WELD-001506: Created new client proxy of type {0} for bean {1} with ID {2}";
   }

   public final void lookedUpClientProxy(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.lookedUpClientProxy$str(), param1, param2);
   }

   protected String lookedUpClientProxy$str() {
      return "WELD-001507: Located client proxy of type {0} for bean {1}";
   }

   protected String injectionTargetCannotBeCreatedForInterface$str() {
      return "WELD-001508: Cannot create an InjectionTarget from {0} as it is an interface";
   }

   public final DefinitionException injectionTargetCannotBeCreatedForInterface(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionTargetCannotBeCreatedForInterface$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String proxyHandlerSerializedForNonSerializableBean$str() {
      return "WELD-001510: Non passivation capable bean serialized with ProxyMethodHandler";
   }

   public final WeldException proxyHandlerSerializedForNonSerializableBean() {
      WeldException result = new WeldException(String.format(this.proxyHandlerSerializedForNonSerializableBean$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String specializingBeanMissingSpecializedType$str() {
      return "WELD-001511: Specializing bean {0} does not have bean type {1} of specialized bean {2}";
   }

   public final DefinitionException specializingBeanMissingSpecializedType(Object param1, Object param2, Object param3) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.specializingBeanMissingSpecializedType$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidInjectionPointType$str() {
      return "WELD-001512: {0} cannot be constructed for {1}";
   }

   public final IllegalArgumentException invalidInjectionPointType(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidInjectionPointType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidAnnotatedCallable$str() {
      return "WELD-001513: An implementation of AnnotatedCallable must implement either AnnotatedConstructor or AnnotatedMethod, {0}";
   }

   public final IllegalArgumentException invalidAnnotatedCallable(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidAnnotatedCallable$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidAnnotatedMember$str() {
      return "WELD-001514: An implementation of AnnotatedMember must implement either AnnotatedConstructor, AnnotatedMethod or AnnotatedField, {0}";
   }

   public final IllegalArgumentException invalidAnnotatedMember(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidAnnotatedMember$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToLoadMember$str() {
      return "WELD-001515: Unable to load annotated member {0}";
   }

   public final IllegalStateException unableToLoadMember(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToLoadMember$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String namedResourceProducerField$str() {
      return "WELD-001516: Resource producer field [{0}] must not have an EL name";
   }

   public final DefinitionException namedResourceProducerField(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.namedResourceProducerField$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidResourceProducerType$str() {
      return "WELD-001517: The type of the resource producer field [{0}] does not match the resource type {1}";
   }

   public final DefinitionException invalidResourceProducerType(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.invalidResourceProducerType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String declaringBeanMissing$str() {
      return "WELD-001518: Cannot create Producer implementation. Declaring bean missing for a non-static member {0}";
   }

   public final IllegalArgumentException declaringBeanMissing(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.declaringBeanMissing$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void injectionTargetCreatedForAbstractClass(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.injectionTargetCreatedForAbstractClass$str(), param1);
   }

   protected String injectionTargetCreatedForAbstractClass$str() {
      return "WELD-001519: An InjectionTarget is created for an abstract {0}. It will not be possible to produce instances of this type!";
   }

   protected String beansWithDifferentBeanNamesCannotBeSpecialized$str() {
      return "WELD-001520: Beans with different bean names {0}, {1} cannot be specialized by a single bean {2}";
   }

   public final DefinitionException beansWithDifferentBeanNamesCannotBeSpecialized(Object param1, Object param2, Object param3) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beansWithDifferentBeanNamesCannotBeSpecialized$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidAnnotatedOfInjectionPoint$str() {
      return "WELD-001521: InjectionPoint.getAnnotated() must return either AnnotatedParameter or AnnotatedField but {0} was returned for {1}";
   }

   public final IllegalArgumentException invalidAnnotatedOfInjectionPoint(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidAnnotatedOfInjectionPoint$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToRestoreInjectionPoint$str() {
      return "WELD-001522: Unable to restore InjectionPoint. No matching InjectionPoint found on {0}";
   }

   public final IllegalStateException unableToRestoreInjectionPoint(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToRestoreInjectionPoint$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToRestoreInjectionPointMultiple$str() {
      return "WELD-001523: Unable to restore InjectionPoint. Multiple matching InjectionPoints found on {0}:\n  - {1},\n  - {2}";
   }

   public final IllegalStateException unableToRestoreInjectionPointMultiple(Object param1, Object param2, Object param3) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToRestoreInjectionPointMultiple$str(), param1, param2, param3));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToLoadProxyClass$str() {
      return "WELD-001524: Unable to load proxy class for bean {0} with class {1}";
   }

   public final WeldException unableToLoadProxyClass(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.unableToLoadProxyClass$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String destroyUnsupported$str() {
      return "WELD-001525: Instance.destroy() is not supported. The underlying context {0} does not support destroying of contextual instances";
   }

   public final UnsupportedOperationException destroyUnsupported(Object param1) {
      UnsupportedOperationException result = new UnsupportedOperationException(MessageFormat.format(this.destroyUnsupported$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingBeanHasNonPassivationCapableDecorator$str() {
      return "WELD-001526: Managed bean declaring a passivating scope has a non-passivation capable decorator.  Bean:  {0}  Decorator: {1}";
   }

   public final DeploymentException passivatingBeanHasNonPassivationCapableDecorator(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.passivatingBeanHasNonPassivationCapableDecorator$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingBeanHasNonPassivationCapableInterceptor$str() {
      return "WELD-001527: Managed bean declaring a passivating scope has a non-serializable interceptor.  Bean:  {0}  Interceptor: {1}";
   }

   public final DeploymentException passivatingBeanHasNonPassivationCapableInterceptor(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.passivatingBeanHasNonPassivationCapableInterceptor$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void injectionTargetCreatedForClassWithoutAppropriateConstructor(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.injectionTargetCreatedForClassWithoutAppropriateConstructor$str(), param1);
   }

   protected String injectionTargetCreatedForClassWithoutAppropriateConstructor$str() {
      return "WELD-001529: An InjectionTarget is created for a {0} which does not have any appropriate constructor. It will not be possible to produce instances of this type!";
   }

   protected String injectionTargetCannotProduceInstance$str() {
      return "WELD-001530: Cannot produce an instance of {0}.";
   }

   public final CreationException injectionTargetCannotProduceInstance(Object param1) {
      CreationException result = new CreationException(MessageFormat.format(this.injectionTargetCannotProduceInstance$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String instanceIteratorRemoveUnsupported$str() {
      return "WELD-001531: Instance.iterator().remove() is not supported.";
   }

   public final UnsupportedOperationException instanceIteratorRemoveUnsupported() {
      UnsupportedOperationException result = new UnsupportedOperationException(String.format(this.instanceIteratorRemoveUnsupported$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivationCapableBeanHasNullId$str() {
      return "WELD-001532: A passivation capable bean cannot have a null id: {0}";
   }

   public final IllegalArgumentException passivationCapableBeanHasNullId(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.passivationCapableBeanHasNullId$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void injectionTargetCreatedForNonStaticInnerClass(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.injectionTargetCreatedForNonStaticInnerClass$str(), param1);
   }

   protected String injectionTargetCreatedForNonStaticInnerClass$str() {
      return "WELD-001533: An InjectionTarget is created for a non-static inner {0}. It will not be possible to produce instances of this type!";
   }

   protected String decoratedHasNoNoargsConstructor$str() {
      return "WELD-001534: Bean class which has decorators must have a public constructor without parameters: {0}";
   }

   public final DeploymentException decoratedHasNoNoargsConstructor(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.decoratedHasNoNoargsConstructor$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratedNoargsConstructorIsPrivate$str() {
      return "WELD-001535: Constructor without parameters cannot be private in bean class which has decorators: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DeploymentException decoratedNoargsConstructorIsPrivate(Object param1, Object stackElement) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.decoratedNoargsConstructorIsPrivate$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void foundInjectableConstructors(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundInjectableConstructors$str(), param1, param2);
   }

   protected String foundInjectableConstructors$str() {
      return "WELD-001536: Found {0} constructors annotated with @Inject for {1}";
   }

   protected String injectionTargetCreatedForClassWithoutAppropriateConstructorException$str() {
      return "WELD-001537: An InjectionTarget is created for a {0} which does not have any appropriate constructor.";
   }

   public final DefinitionException injectionTargetCreatedForClassWithoutAppropriateConstructorException(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.injectionTargetCreatedForClassWithoutAppropriateConstructorException$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void createdContextInstance(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.createdContextInstance$str(), param1, param2);
   }

   protected String createdContextInstance$str() {
      return "WELD-001538: Created context instance for bean {0} identified as {1}";
   }

   public final void createdMethodHandlerInitializerForDecoratorProxy(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.createdMethodHandlerInitializerForDecoratorProxy$str(), param1);
   }

   protected String createdMethodHandlerInitializerForDecoratorProxy$str() {
      return "WELD-001539: Created MH initializer body for decorator proxy: {0}";
   }

   public final void addingMethodToEnterpriseProxy(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.addingMethodToEnterpriseProxy$str(), param1);
   }

   protected String addingMethodToEnterpriseProxy$str() {
      return "WELD-001540: Adding method to enterprise proxy: {0}";
   }

   public final void addingMethodToProxy(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.addingMethodToProxy$str(), param1);
   }

   protected String addingMethodToProxy$str() {
      return "WELD-001541: Adding method to proxy: {0}";
   }

   public final void generatingProxyClass(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.generatingProxyClass$str(), param1);
   }

   protected String generatingProxyClass$str() {
      return "WELD-001542: Retrieving/generating proxy class {0}";
   }

   public final void createdProxyClass(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.createdProxyClass$str(), param1, param2);
   }

   protected String createdProxyClass$str() {
      return "WELD-001543: Created Proxy class of type {0} supporting interfaces {1}";
   }

   public final void methodHandlerProcessingReturningBeanInstance(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.methodHandlerProcessingReturningBeanInstance$str(), param1);
   }

   protected String methodHandlerProcessingReturningBeanInstance$str() {
      return "WELD-001544: MethodHandler processing returning bean instance for {0}";
   }

   public final void methodHandlerProcessingCall(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.methodHandlerProcessingCall$str(), param1, param2);
   }

   protected String methodHandlerProcessingCall$str() {
      return "WELD-001545: MethodHandler processing call to {0} for {1}";
   }

   public final void settingNewMethodHandler(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.settingNewMethodHandler$str(), param1, param2);
   }

   protected String settingNewMethodHandler$str() {
      return "WELD-001546: Setting new MethodHandler with bean instance for {0} on {1}";
   }

   public final void invokingInterceptorChain(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.invokingInterceptorChain$str(), param1, param2);
   }

   protected String invokingInterceptorChain$str() {
      return "WELD-001547: Invoking interceptor chain for method {0} on {1}";
   }

   public final void invokingMethodDirectly(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.invokingMethodDirectly$str(), param1, param2);
   }

   protected String invokingMethodDirectly$str() {
      return "WELD-001548: Invoking method {0} directly on {1}";
   }

   protected String unableToDetermineParentCreationalContext$str() {
      return "WELD-001549: Unable to determine parent creational context of {0}";
   }

   public final IllegalArgumentException unableToDetermineParentCreationalContext(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.unableToDetermineParentCreationalContext$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerFieldWithTypeVariableBeanTypeMustBeDependent$str() {
      return "WELD-001550: A producer field with a parameterized type with a type variable must be declared @Dependent scoped: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerFieldWithTypeVariableBeanTypeMustBeDependent(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerFieldWithTypeVariableBeanTypeMustBeDependent$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerFieldCannotHaveAWildcardBeanType$str() {
      return "WELD-001551: A producer field type may not contain a wildcard: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerFieldCannotHaveAWildcardBeanType(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerFieldCannotHaveAWildcardBeanType$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void extensionWithNonStaticPublicField(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.extensionWithNonStaticPublicField$str(), param1, param2);
   }

   protected String extensionWithNonStaticPublicField$str() {
      return "WELD-001552: An extension ({0}) has a non-static public field ({1}).";
   }

   public final void activatedSessionBeanProxy(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.activatedSessionBeanProxy$str(), param1);
   }

   protected String activatedSessionBeanProxy$str() {
      return "WELD-001553: Proxy activated after passivation for {0}";
   }

   protected String beanMethodReturnsNull$str() {
      return "WELD-001554: Bean.{0}() returned null for {1}";
   }

   public final DefinitionException beanMethodReturnsNull(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanMethodReturnsNull$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String decoratorMethodReturnsNull$str() {
      return "WELD-001555: Decorator.{0}() returned null for {1}";
   }

   public final DefinitionException decoratorMethodReturnsNull(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.decoratorMethodReturnsNull$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String specializingManagedBeanCanExtendOnlyManagedBeans$str() {
      return "WELD-001556: Specializing {0} cannot specialize a non-managed bean {1}";
   }

   public final DefinitionException specializingManagedBeanCanExtendOnlyManagedBeans(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.specializingManagedBeanCanExtendOnlyManagedBeans$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void beanCannotBeDumped(Object param1, Throwable cause) {
      super.log.logv(FQCN, Level.WARN, cause, this.beanCannotBeDumped$str(), param1);
   }

   protected String beanCannotBeDumped$str() {
      return "WELD-001557: Unable to dump the proxy class file for {0}";
   }

   public final void directoryCannotBeCreated(Object param1) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.directoryCannotBeCreated$str(), param1);
   }

   protected String directoryCannotBeCreated$str() {
      return "WELD-001558: Unable to create directory {0} to dump the proxy classes.";
   }

   protected String beanBuilderInvalidCreateCallback$str() {
      return "WELD-001559: Bean builder {0} does not define a create lifecycle callback.";
   }

   public final DefinitionException beanBuilderInvalidCreateCallback(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanBuilderInvalidCreateCallback$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanBuilderInvalidDestroyCallback$str() {
      return "WELD-001560: Bean builder {0} does not define a destroy lifecycle callback.";
   }

   public final DefinitionException beanBuilderInvalidDestroyCallback(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanBuilderInvalidDestroyCallback$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanBuilderInvalidBeanManager$str() {
      return "WELD-001561: Bean builder {0} does not define a BeanManager.";
   }

   public final DefinitionException beanBuilderInvalidBeanManager(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanBuilderInvalidBeanManager$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerMethodReturnTypeInvalidTypeVariable$str() {
      return "WELD-001562: A producer method return type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerMethodReturnTypeInvalidTypeVariable(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerMethodReturnTypeInvalidTypeVariable$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String producerFieldTypeInvalidTypeVariable$str() {
      return "WELD-001563: A producer field type may not be a type variable or an array type whose component type is a type variable: \n  {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException producerFieldTypeInvalidTypeVariable(Object param1, String stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.producerFieldTypeInvalidTypeVariable$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String statelessSessionBeanInjectionPointMetadataNotAvailable$str() {
      return "WELD-001564: Injection point metadata injected into a stateless session bean may only be accessed within its business method invocation";
   }

   public final IllegalStateException statelessSessionBeanInjectionPointMetadataNotAvailable() {
      IllegalStateException result = new IllegalStateException(this.statelessSessionBeanInjectionPointMetadataNotAvailable$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noInterceptionFunction$str() {
      return "WELD-001565: Interceptor builder {0} does not define an interception function.";
   }

   public final DefinitionException noInterceptionFunction(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.noInterceptionFunction$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noInterceptionType$str() {
      return "WELD-001566: Interceptor builder {0} does not define any InterceptionType.";
   }

   public final DefinitionException noInterceptionType(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.noInterceptionType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotCreateContextualInstanceOfBuilderInterceptor$str() {
      return "WELD-001567: Cannot create contextual instance of {0}";
   }

   public final IllegalStateException cannotCreateContextualInstanceOfBuilderInterceptor(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.cannotCreateContextualInstanceOfBuilderInterceptor$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToCreateClassFile$str() {
      return "WELD-001568: Unable to create ClassFile for: {1}.";
   }

   public final IllegalStateException unableToCreateClassFile(Object name, Throwable cause) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToCreateClassFile$str(), name), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotInjectInjectionPointMetadataIntoNonDependent$str() {
      return "WELD-001569: Cannot inject injection point metadata in a non @Dependent bean: {0}";
   }

   public final IllegalArgumentException cannotInjectInjectionPointMetadataIntoNonDependent(Object bean) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotInjectInjectionPointMetadataIntoNonDependent$str(), bean));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noCallbackSpecifiedForCustomBean$str() {
      return "WELD-001570: Invalid BeanConfigurator setup - no callback was specified for {0}";
   }

   public final IllegalStateException noCallbackSpecifiedForCustomBean(Object bean) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.noCallbackSpecifiedForCustomBean$str(), bean));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void generatingProxyToDefaultPackage(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.generatingProxyToDefaultPackage$str(), param1, param2, param3);
   }

   protected String generatingProxyToDefaultPackage$str() {
      return "WELD-001571: Proxy for {0} created in {1} because {2}.";
   }

   protected String initABDnotInvoked$str() {
      return "WELD-001572: Cannot create instance of session bean from Annotated Type {0} before AfterDeploymentValidation phase.";
   }

   public final CreationException initABDnotInvoked(Object bean) {
      CreationException result = new CreationException(MessageFormat.format(this.initABDnotInvoked$str(), bean));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotObtainHandlerContextualReference$str() {
      return "WELD-001573: Cannot obtain contextual reference for {0} - producing WeldInstance does not exist anymore";
   }

   public final IllegalStateException cannotObtainHandlerContextualReference(Object handler) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.cannotObtainHandlerContextualReference$str(), handler));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void cannotDestroyHandlerContextualReference(Object handler) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.cannotDestroyHandlerContextualReference$str(), handler);
   }

   protected String cannotDestroyHandlerContextualReference$str() {
      return "WELD-001574: Cannot destroy contextual instance for {0} - producing WeldInstance does not exist anymore";
   }

   protected String selectByTypeOnlyWorksOnObject$str() {
      return "WELD-001575: WeldInstance.select(Type subtype, Annotation... qualifiers) can be invoked only on an instance of WeldInstance<Object>.";
   }

   public final IllegalStateException selectByTypeOnlyWorksOnObject() {
      IllegalStateException result = new IllegalStateException(this.selectByTypeOnlyWorksOnObject$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void creatingProxyInstanceUsingDifferentInstantiator(Object proxyClass, Object newInstantiator, Object oldInstantiator) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.creatingProxyInstanceUsingDifferentInstantiator$str(), proxyClass, newInstantiator, oldInstantiator);
   }

   protected String creatingProxyInstanceUsingDifferentInstantiator$str() {
      return "WELD-001576: Using {1} to instantiate a shared proxy class {0}; the deployment implementation [{2}] does not match the instantiator the proxy was created with";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
