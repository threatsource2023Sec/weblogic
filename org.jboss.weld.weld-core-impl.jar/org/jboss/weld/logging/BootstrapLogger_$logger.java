package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

public class BootstrapLogger_$logger extends DelegatingBasicLogger implements BootstrapLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = BootstrapLogger_$logger.class.getName();
   private static final String validatingBeans = "WELD-000100: Weld initialized. Validating beans";
   private static final String jtaUnavailable = "WELD-000101: Transactional services not available. Injection of @Inject UserTransaction not available. Transactional observers will be invoked synchronously.";
   private static final String enabledAlternatives = "WELD-000103: Enabled alternatives for {0}: {1}";
   private static final String enabledDecorators = "WELD-000104: Enabled decorator types for {0}: {1}";
   private static final String enabledInterceptors = "WELD-000105: Enabled interceptor types for {0}: {1}";
   private static final String foundBean = "WELD-000106: Bean: {0}";
   private static final String foundInterceptor = "WELD-000107: Interceptor: {0}";
   private static final String foundDecorator = "WELD-000108: Decorator: {0}";
   private static final String foundObserverMethod = "WELD-000109: ObserverMethod: {0}";
   private static final String annotationTypeNull = "WELD-000110: Cannot set the annotation type to null (if you want to stop the type being used, call veto()):  {0}";
   private static final String beanTypeNotEjb = "WELD-000111: Bean type is not STATELESS, STATEFUL or SINGLETON:  {0}";
   private static final String beanIsBothInterceptorAndDecorator = "WELD-000112: Class {0} has both @Interceptor and @Decorator annotations";
   private static final String deploymentArchiveNull = "WELD-000113: BeanDeploymentArchive must not be null:  {0}";
   private static final String deploymentRequired = "WELD-000114: Must start the container with a deployment";
   private static final String managerNotInitialized = "WELD-000116: Manager has not been initialized";
   private static final String unspecifiedRequiredService = "WELD-000117: Required service {0} has not been specified for {1}";
   private static final String passivatingNonNormalScopeIllegal = "WELD-000118: Only normal scopes can be passivating. Scope {0}";
   private static final String ignoringClassDueToLoadingError = "WELD-000119: Not generating any bean definitions from {0} because of underlying class loading error: Type {1} not found.  If this is unexpected, enable DEBUG logging to see the full error.";
   private static final String errorLoadingBeansXmlEntry = "WELD-000123: Error loading {0} defined in {1}";
   private static final String threadsInUse = "WELD-000124: Using {0} threads for bootstrap";
   private static final String invalidThreadPoolSize = "WELD-000125: Invalid thread pool size: {0}";
   private static final String timeoutShuttingDownThreadPool = "WELD-000126: Timeout shutting down thread pool {0} at {1}";
   private static final String invalidThreadPoolType = "WELD-000127: Invalid thread pool type: {0}";
   private static final String invalidPropertyValue = "WELD-000128: Invalid value for property {0}: {1}";
   private static final String annotatedTypeJavaClassMismatch = "WELD-000130: Cannot replace AnnotatedType for {0} with AnnotatedType for {1}";
   private static final String foundDisabledAlternative = "WELD-000132: Disabled alternative (ignored): {0}";
   private static final String foundSpecializedBean = "WELD-000133: Specialized bean (ignored): {0}";
   private static final String foundProducerOfSpecializedBean = "WELD-000134: Producer (method or field) of specialized bean (ignored): {0}";
   private static final String legacyDeploymentMetadataProvided = "WELD-000135: Legacy deployment metadata provided by the integrator. Certain functionality will not be available.";
   private static final String exceptionThrownDuringBeforeShutdownObserver = "WELD-000136: Exception(s) thrown during observer of BeforeShutdown: ";
   private static final String exceptionWhileLoadingClass = "WELD-000137: Exception while loading class '{0}' : {1}";
   private static final String errorWhileLoadingClass = "WELD-000138: Error while loading class '{0}' : {1}";
   private static final String ignoringExtensionClassDueToLoadingError = "WELD-000139: Ignoring portable extension class {0} because of underlying class loading error: Type {1} not found. Enable DEBUG logging level to see the full error.";
   private static final String callingBootstrapMethodAfterContainerHasBeenInitialized = "WELD-000140: Calling Bootstrap method after container has already been initialized. For correct order, see CDI11Bootstrap's documentation.";
   private static final String notUsingFastResolver = "WELD-000141: Falling back to the default observer method resolver due to {0}";
   private static final String exceptionLoadingAnnotatedType = "WELD-000142: Exception loading annotated type using ClassFileServices. Falling back to the default implementation. {0}";
   private static final String patSkipped = "No PAT observers resolved for {0}. Skipping.";
   private static final String patDefaultResolver = "Sending PAT using the default event resolver: {0}";
   private static final String patFastResolver = "Sending PAT using the fast event resolver: {0}";
   private static final String containerLifecycleEventMethodInvokedOutsideObserver = "WELD-000143: Container lifecycle event method invoked outside of extension observer method invocation.";
   private static final String cdiApiVersionMismatch = "WELD-000144: CDI API version mismatch. CDI 1.0 API detected on classpath. Weld requires version 1.1 or better.";
   private static final String beanIdentifierIndexBuilt = "WELD-000145: Bean identifier index built:\n  {0}";
   private static final String deprecatedAddAnnotatedTypeMethodUsed = "WELD-000146: BeforeBeanDiscovery.addAnnotatedType(AnnotatedType<?>) used for {0} is deprecated from CDI 1.1!";
   private static final String decoratorWithNonCdiConstructor = "WELD-000147: Decorator {0} declares inappropriate constructor therefore will not available as a managed bean!";
   private static final String setAnnotatedTypeCalled = "WELD-000148: ProcessAnnotatedType.setAnnotatedType() called by {0}: {1} replaced by {2}";
   private static final String setBeanAttributesCalled = "WELD-000149: ProcessBeanAttributes.setBeanAttributes() called by {0}: {1} replaced by {2}";
   private static final String setInjectionPointCalled = "WELD-000150: ProcessInjectionPoint.setInjectionPoint() called by {0}: {1} replaced by {2}";
   private static final String setInjectionTargetCalled = "WELD-000151: ProcessInjectionTarget.setInjectionTarget() called by {0}: {1} replaced by {2}";
   private static final String setProducerCalled = "WELD-000152: ProcessProducer.setProducer() called by {0}: {1} replaced by {2}";
   private static final String addAnnotatedTypeCalled = "WELD-000153: AfterTypeDiscovery.addAnnotatedType() called by {0} for {1}";
   private static final String addBeanCalled = "WELD-000154: AfterBeanDiscovery.addBean() called by {0} for {1}";
   private static final String addObserverMethodCalled = "WELD-000155: AfterBeanDiscovery.addObserverMethod() called by {0} for {1}";
   private static final String addContext = "WELD-000156: AfterBeanDiscovery.addContext() called by {0} for {1}";
   private static final String addDefinitionErrorCalled = "WELD-000157: AfterBeanDiscovery.addDefinitionError() called by {0} for {1}";
   private static final String addQualifierCalled = "WELD-000158: BeforeBeanDiscovery.addQualifier() called by {0} for {1}";
   private static final String addScopeCalled = "WELD-000159: BeforeBeanDiscovery.addScope() called by {0} for {1}";
   private static final String addStereoTypeCalled = "WELD-000160: BeforeBeanDiscovery.addStereoType() called by {0} for {1}";
   private static final String addInterceptorBindingCalled = "WELD-000161: BeforeBeanDiscovery.addInterceptorBindingCalled() called by {0} for {1}";
   private static final String addAnnotatedTypeCalledInBBD = "WELD-000162: BeforeBeanDiscovery.addAnnotatedType() called by {0} for {1}";
   private static final String nonuniqueBeanDeploymentIdentifier = "WELD-000163: Non-unique bean deployment identifier detected: {0}";
   private static final String annotatedTypeVetoed = "WELD-000164: ProcessAnnotatedType.veto() called by {0} for {1}";
   private static final String beanAttributesVetoed = "WELD-000165: ProcessBeanAttributes.veto() called by {0} for {1}";
   private static final String typeModifiedInAfterTypeDiscovery = "WELD-000166: AfterTypeDiscovery.{3} modified by {0} {2} {1}";
   private static final String annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor = "WELD-000167: Class {0} is annotated with @{1} but it does not declare an appropriate constructor therefore is not registered as a bean!";
   private static final String extensionBeanDeployed = "WELD-000168: Extension bean deployed: {0}";
   private static final String usingOldJandexVersion = "WELD-000169: Jandex cannot distinguish inner and static nested classes! Update Jandex to 2.0.3.Final version or newer to improve scanning performance.";
   private static final String configuratorAndSetMethodBothCalled = "WELD-000170: {0} observer cannot call both the configurator and set methods. Extension {1} \nStackTrace:";
   private static final String configureQualifierCalled = "WELD-000171: BeforeBeanDiscovery.configureQualifier() called by {0} for {1}";
   private static final String configureInterceptorBindingCalled = "WELD-000172: BeforeBeanDiscovery.configureInterceptorBinding() called by {0} for {1}";
   private static final String configureProducerCalled = "WELD-000173: ProcessProducer.configureProducer() called by {0} for {1}";
   private static final String configureBeanAttributesCalled = "WELD-000174: ProcessBeanAttributes.configureBeanAttributes() called by {0} for {1}";
   private static final String ignoreFinalMethodsCalled = "WELD-000175: ProcessBeanAttributes.isIgnoreFinalMethods() called by {0} for {1}";
   private static final String configureAnnotatedTypeCalled = "WELD-000176: ProcessAnnotatedType.configureAnnotatedType() called by {0} for {1}";
   private static final String configureObserverMethodCalled = "WELD-000177: ProcessObserverMethod.configureObserverMethod() called by {0} for {1}";
   private static final String configureInjectionPointCalled = "WELD-000178: ProcessInjectionPoint.configureInjectionPoint() called by {0} for {1}";
   private static final String unableToProcessConfigurator = "WELD-000179: {0} created by {1} cannot be processed";
   private static final String dropUnusedBeanMetadata = "WELD-000180: Drop unused bean metadata: {0}";
   private static final String commonThreadPoolWithSecurityManagerEnabled = "WELD-000181: org.jboss.weld.executor.threadPoolType=COMMON detected but ForkJoinPool.commonPool() does not work with SecurityManager enabled, switching to {0} thread pool";
   private static final String catchingDebug = "Catching";

   public BootstrapLogger_$logger(Logger log) {
      super(log);
   }

   public final void validatingBeans() {
      super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.validatingBeans$str(), new Object[0]);
   }

   protected String validatingBeans$str() {
      return "WELD-000100: Weld initialized. Validating beans";
   }

   public final void jtaUnavailable() {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.jtaUnavailable$str(), new Object[0]);
   }

   protected String jtaUnavailable$str() {
      return "WELD-000101: Transactional services not available. Injection of @Inject UserTransaction not available. Transactional observers will be invoked synchronously.";
   }

   public final void enabledAlternatives(Object param1, Object param2) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.enabledAlternatives$str(), param1, param2);
   }

   protected String enabledAlternatives$str() {
      return "WELD-000103: Enabled alternatives for {0}: {1}";
   }

   public final void enabledDecorators(Object param1, Object param2) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.enabledDecorators$str(), param1, param2);
   }

   protected String enabledDecorators$str() {
      return "WELD-000104: Enabled decorator types for {0}: {1}";
   }

   public final void enabledInterceptors(Object param1, Object param2) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.enabledInterceptors$str(), param1, param2);
   }

   protected String enabledInterceptors$str() {
      return "WELD-000105: Enabled interceptor types for {0}: {1}";
   }

   public final void foundBean(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundBean$str(), param1);
   }

   protected String foundBean$str() {
      return "WELD-000106: Bean: {0}";
   }

   public final void foundInterceptor(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundInterceptor$str(), param1);
   }

   protected String foundInterceptor$str() {
      return "WELD-000107: Interceptor: {0}";
   }

   public final void foundDecorator(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundDecorator$str(), param1);
   }

   protected String foundDecorator$str() {
      return "WELD-000108: Decorator: {0}";
   }

   public final void foundObserverMethod(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundObserverMethod$str(), param1);
   }

   protected String foundObserverMethod$str() {
      return "WELD-000109: ObserverMethod: {0}";
   }

   protected String annotationTypeNull$str() {
      return "WELD-000110: Cannot set the annotation type to null (if you want to stop the type being used, call veto()):  {0}";
   }

   public final IllegalArgumentException annotationTypeNull(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.annotationTypeNull$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanTypeNotEjb$str() {
      return "WELD-000111: Bean type is not STATELESS, STATEFUL or SINGLETON:  {0}";
   }

   public final IllegalStateException beanTypeNotEjb(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.beanTypeNotEjb$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanIsBothInterceptorAndDecorator$str() {
      return "WELD-000112: Class {0} has both @Interceptor and @Decorator annotations";
   }

   public final DefinitionException beanIsBothInterceptorAndDecorator(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanIsBothInterceptorAndDecorator$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String deploymentArchiveNull$str() {
      return "WELD-000113: BeanDeploymentArchive must not be null:  {0}";
   }

   public final IllegalArgumentException deploymentArchiveNull(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.deploymentArchiveNull$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String deploymentRequired$str() {
      return "WELD-000114: Must start the container with a deployment";
   }

   public final IllegalArgumentException deploymentRequired() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.deploymentRequired$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String managerNotInitialized$str() {
      return "WELD-000116: Manager has not been initialized";
   }

   public final IllegalStateException managerNotInitialized() {
      IllegalStateException result = new IllegalStateException(String.format(this.managerNotInitialized$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unspecifiedRequiredService$str() {
      return "WELD-000117: Required service {0} has not been specified for {1}";
   }

   public final IllegalStateException unspecifiedRequiredService(Object service, Object target) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unspecifiedRequiredService$str(), service, target));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String passivatingNonNormalScopeIllegal$str() {
      return "WELD-000118: Only normal scopes can be passivating. Scope {0}";
   }

   public final DefinitionException passivatingNonNormalScopeIllegal(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.passivatingNonNormalScopeIllegal$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void ignoringClassDueToLoadingError(Object param1, Object param2) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.ignoringClassDueToLoadingError$str(), param1, param2);
   }

   protected String ignoringClassDueToLoadingError$str() {
      return "WELD-000119: Not generating any bean definitions from {0} because of underlying class loading error: Type {1} not found.  If this is unexpected, enable DEBUG logging to see the full error.";
   }

   protected String errorLoadingBeansXmlEntry$str() {
      return "WELD-000123: Error loading {0} defined in {1}";
   }

   public final DeploymentException errorLoadingBeansXmlEntry(Object param1, Object param2, Throwable cause) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.errorLoadingBeansXmlEntry$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void threadsInUse(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.threadsInUse$str(), param1);
   }

   protected String threadsInUse$str() {
      return "WELD-000124: Using {0} threads for bootstrap";
   }

   protected String invalidThreadPoolSize$str() {
      return "WELD-000125: Invalid thread pool size: {0}";
   }

   public final DeploymentException invalidThreadPoolSize(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.invalidThreadPoolSize$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void timeoutShuttingDownThreadPool(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.timeoutShuttingDownThreadPool$str(), param1, param2);
   }

   protected String timeoutShuttingDownThreadPool$str() {
      return "WELD-000126: Timeout shutting down thread pool {0} at {1}";
   }

   protected String invalidThreadPoolType$str() {
      return "WELD-000127: Invalid thread pool type: {0}";
   }

   public final DeploymentException invalidThreadPoolType(Object param1) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.invalidThreadPoolType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidPropertyValue$str() {
      return "WELD-000128: Invalid value for property {0}: {1}";
   }

   public final DeploymentException invalidPropertyValue(Object param1, Object param2) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.invalidPropertyValue$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String annotatedTypeJavaClassMismatch$str() {
      return "WELD-000130: Cannot replace AnnotatedType for {0} with AnnotatedType for {1}";
   }

   public final IllegalArgumentException annotatedTypeJavaClassMismatch(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.annotatedTypeJavaClassMismatch$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void foundDisabledAlternative(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundDisabledAlternative$str(), param1);
   }

   protected String foundDisabledAlternative$str() {
      return "WELD-000132: Disabled alternative (ignored): {0}";
   }

   public final void foundSpecializedBean(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundSpecializedBean$str(), param1);
   }

   protected String foundSpecializedBean$str() {
      return "WELD-000133: Specialized bean (ignored): {0}";
   }

   public final void foundProducerOfSpecializedBean(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.foundProducerOfSpecializedBean$str(), param1);
   }

   protected String foundProducerOfSpecializedBean$str() {
      return "WELD-000134: Producer (method or field) of specialized bean (ignored): {0}";
   }

   public final void legacyDeploymentMetadataProvided() {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.legacyDeploymentMetadataProvided$str(), new Object[0]);
   }

   protected String legacyDeploymentMetadataProvided$str() {
      return "WELD-000135: Legacy deployment metadata provided by the integrator. Certain functionality will not be available.";
   }

   public final void exceptionThrownDuringBeforeShutdownObserver() {
      super.log.logf(FQCN, Level.ERROR, (Throwable)null, this.exceptionThrownDuringBeforeShutdownObserver$str(), new Object[0]);
   }

   protected String exceptionThrownDuringBeforeShutdownObserver$str() {
      return "WELD-000136: Exception(s) thrown during observer of BeforeShutdown: ";
   }

   public final void exceptionWhileLoadingClass(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.exceptionWhileLoadingClass$str(), param1, param2);
   }

   protected String exceptionWhileLoadingClass$str() {
      return "WELD-000137: Exception while loading class '{0}' : {1}";
   }

   public final void errorWhileLoadingClass(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.errorWhileLoadingClass$str(), param1, param2);
   }

   protected String errorWhileLoadingClass$str() {
      return "WELD-000138: Error while loading class '{0}' : {1}";
   }

   public final void ignoringExtensionClassDueToLoadingError(String className, String missingDependency) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.ignoringExtensionClassDueToLoadingError$str(), className, missingDependency);
   }

   protected String ignoringExtensionClassDueToLoadingError$str() {
      return "WELD-000139: Ignoring portable extension class {0} because of underlying class loading error: Type {1} not found. Enable DEBUG logging level to see the full error.";
   }

   protected String callingBootstrapMethodAfterContainerHasBeenInitialized$str() {
      return "WELD-000140: Calling Bootstrap method after container has already been initialized. For correct order, see CDI11Bootstrap's documentation.";
   }

   public final IllegalStateException callingBootstrapMethodAfterContainerHasBeenInitialized() {
      IllegalStateException result = new IllegalStateException(String.format(this.callingBootstrapMethodAfterContainerHasBeenInitialized$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void notUsingFastResolver(ObserverMethod observer) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.notUsingFastResolver$str(), observer);
   }

   protected String notUsingFastResolver$str() {
      return "WELD-000141: Falling back to the default observer method resolver due to {0}";
   }

   public final void exceptionLoadingAnnotatedType(String message) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.exceptionLoadingAnnotatedType$str(), message);
   }

   protected String exceptionLoadingAnnotatedType$str() {
      return "WELD-000142: Exception loading annotated type using ClassFileServices. Falling back to the default implementation. {0}";
   }

   public final void patSkipped(SlimAnnotatedType type) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.patSkipped$str(), type);
   }

   protected String patSkipped$str() {
      return "No PAT observers resolved for {0}. Skipping.";
   }

   public final void patDefaultResolver(SlimAnnotatedType type) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.patDefaultResolver$str(), type);
   }

   protected String patDefaultResolver$str() {
      return "Sending PAT using the default event resolver: {0}";
   }

   public final void patFastResolver(SlimAnnotatedType type) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.patFastResolver$str(), type);
   }

   protected String patFastResolver$str() {
      return "Sending PAT using the fast event resolver: {0}";
   }

   protected String containerLifecycleEventMethodInvokedOutsideObserver$str() {
      return "WELD-000143: Container lifecycle event method invoked outside of extension observer method invocation.";
   }

   public final IllegalStateException containerLifecycleEventMethodInvokedOutsideObserver() {
      IllegalStateException result = new IllegalStateException(String.format(this.containerLifecycleEventMethodInvokedOutsideObserver$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cdiApiVersionMismatch$str() {
      return "WELD-000144: CDI API version mismatch. CDI 1.0 API detected on classpath. Weld requires version 1.1 or better.";
   }

   public final IllegalStateException cdiApiVersionMismatch() {
      IllegalStateException result = new IllegalStateException(String.format(this.cdiApiVersionMismatch$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void beanIdentifierIndexBuilt(Object info) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.beanIdentifierIndexBuilt$str(), info);
   }

   protected String beanIdentifierIndexBuilt$str() {
      return "WELD-000145: Bean identifier index built:\n  {0}";
   }

   public final void deprecatedAddAnnotatedTypeMethodUsed(Class clazz) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.deprecatedAddAnnotatedTypeMethodUsed$str(), clazz);
   }

   protected String deprecatedAddAnnotatedTypeMethodUsed$str() {
      return "WELD-000146: BeforeBeanDiscovery.addAnnotatedType(AnnotatedType<?>) used for {0} is deprecated from CDI 1.1!";
   }

   public final void decoratorWithNonCdiConstructor(String clazzName) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.decoratorWithNonCdiConstructor$str(), clazzName);
   }

   protected String decoratorWithNonCdiConstructor$str() {
      return "WELD-000147: Decorator {0} declares inappropriate constructor therefore will not available as a managed bean!";
   }

   public final void setAnnotatedTypeCalled(Object extensionName, Object original, Object newer) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.setAnnotatedTypeCalled$str(), extensionName, original, newer);
   }

   protected String setAnnotatedTypeCalled$str() {
      return "WELD-000148: ProcessAnnotatedType.setAnnotatedType() called by {0}: {1} replaced by {2}";
   }

   public final void setBeanAttributesCalled(Object extensionName, Object original, Object newer) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.setBeanAttributesCalled$str(), extensionName, original, newer);
   }

   protected String setBeanAttributesCalled$str() {
      return "WELD-000149: ProcessBeanAttributes.setBeanAttributes() called by {0}: {1} replaced by {2}";
   }

   public final void setInjectionPointCalled(Object extensionName, Object original, Object newer) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.setInjectionPointCalled$str(), extensionName, original, newer);
   }

   protected String setInjectionPointCalled$str() {
      return "WELD-000150: ProcessInjectionPoint.setInjectionPoint() called by {0}: {1} replaced by {2}";
   }

   public final void setInjectionTargetCalled(Object extensionName, Object original, Object newer) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.setInjectionTargetCalled$str(), extensionName, original, newer);
   }

   protected String setInjectionTargetCalled$str() {
      return "WELD-000151: ProcessInjectionTarget.setInjectionTarget() called by {0}: {1} replaced by {2}";
   }

   public final void setProducerCalled(Object extensionName, Object original, Object newer) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.setProducerCalled$str(), extensionName, original, newer);
   }

   protected String setProducerCalled$str() {
      return "WELD-000152: ProcessProducer.setProducer() called by {0}: {1} replaced by {2}";
   }

   public final void addAnnotatedTypeCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addAnnotatedTypeCalled$str(), extensionName, type);
   }

   protected String addAnnotatedTypeCalled$str() {
      return "WELD-000153: AfterTypeDiscovery.addAnnotatedType() called by {0} for {1}";
   }

   public final void addBeanCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addBeanCalled$str(), extensionName, type);
   }

   protected String addBeanCalled$str() {
      return "WELD-000154: AfterBeanDiscovery.addBean() called by {0} for {1}";
   }

   public final void addObserverMethodCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addObserverMethodCalled$str(), extensionName, type);
   }

   protected String addObserverMethodCalled$str() {
      return "WELD-000155: AfterBeanDiscovery.addObserverMethod() called by {0} for {1}";
   }

   public final void addContext(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addContext$str(), extensionName, type);
   }

   protected String addContext$str() {
      return "WELD-000156: AfterBeanDiscovery.addContext() called by {0} for {1}";
   }

   public final void addDefinitionErrorCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addDefinitionErrorCalled$str(), extensionName, type);
   }

   protected String addDefinitionErrorCalled$str() {
      return "WELD-000157: AfterBeanDiscovery.addDefinitionError() called by {0} for {1}";
   }

   public final void addQualifierCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addQualifierCalled$str(), extensionName, type);
   }

   protected String addQualifierCalled$str() {
      return "WELD-000158: BeforeBeanDiscovery.addQualifier() called by {0} for {1}";
   }

   public final void addScopeCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addScopeCalled$str(), extensionName, type);
   }

   protected String addScopeCalled$str() {
      return "WELD-000159: BeforeBeanDiscovery.addScope() called by {0} for {1}";
   }

   public final void addStereoTypeCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addStereoTypeCalled$str(), extensionName, type);
   }

   protected String addStereoTypeCalled$str() {
      return "WELD-000160: BeforeBeanDiscovery.addStereoType() called by {0} for {1}";
   }

   public final void addInterceptorBindingCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addInterceptorBindingCalled$str(), extensionName, type);
   }

   protected String addInterceptorBindingCalled$str() {
      return "WELD-000161: BeforeBeanDiscovery.addInterceptorBindingCalled() called by {0} for {1}";
   }

   public final void addAnnotatedTypeCalledInBBD(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.addAnnotatedTypeCalledInBBD$str(), extensionName, type);
   }

   protected String addAnnotatedTypeCalledInBBD$str() {
      return "WELD-000162: BeforeBeanDiscovery.addAnnotatedType() called by {0} for {1}";
   }

   protected String nonuniqueBeanDeploymentIdentifier$str() {
      return "WELD-000163: Non-unique bean deployment identifier detected: {0}";
   }

   public final DeploymentException nonuniqueBeanDeploymentIdentifier(Object info) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.nonuniqueBeanDeploymentIdentifier$str(), info));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void annotatedTypeVetoed(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.annotatedTypeVetoed$str(), extensionName, type);
   }

   protected String annotatedTypeVetoed$str() {
      return "WELD-000164: ProcessAnnotatedType.veto() called by {0} for {1}";
   }

   public final void beanAttributesVetoed(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.beanAttributesVetoed$str(), extensionName, type);
   }

   protected String beanAttributesVetoed$str() {
      return "WELD-000165: ProcessBeanAttributes.veto() called by {0} for {1}";
   }

   public final void typeModifiedInAfterTypeDiscovery(Object extensionName, Object type, Object operation, Object types) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.typeModifiedInAfterTypeDiscovery$str(), new Object[]{extensionName, type, operation, types});
   }

   protected String typeModifiedInAfterTypeDiscovery$str() {
      return "WELD-000166: AfterTypeDiscovery.{3} modified by {0} {2} {1}";
   }

   public final void annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor(String clazzName, String annotationName) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor$str(), clazzName, annotationName);
   }

   protected String annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor$str() {
      return "WELD-000167: Class {0} is annotated with @{1} but it does not declare an appropriate constructor therefore is not registered as a bean!";
   }

   public final void extensionBeanDeployed(Object extension) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.extensionBeanDeployed$str(), extension);
   }

   protected String extensionBeanDeployed$str() {
      return "WELD-000168: Extension bean deployed: {0}";
   }

   public final void usingOldJandexVersion() {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.usingOldJandexVersion$str(), new Object[0]);
   }

   protected String usingOldJandexVersion$str() {
      return "WELD-000169: Jandex cannot distinguish inner and static nested classes! Update Jandex to 2.0.3.Final version or newer to improve scanning performance.";
   }

   protected String configuratorAndSetMethodBothCalled$str() {
      return "WELD-000170: {0} observer cannot call both the configurator and set methods. Extension {1} \nStackTrace:";
   }

   public final IllegalStateException configuratorAndSetMethodBothCalled(Object observerName, Object extension) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.configuratorAndSetMethodBothCalled$str(), observerName, extension));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void configureQualifierCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureQualifierCalled$str(), extensionName, type);
   }

   protected String configureQualifierCalled$str() {
      return "WELD-000171: BeforeBeanDiscovery.configureQualifier() called by {0} for {1}";
   }

   public final void configureInterceptorBindingCalled(Object extensionName, Object type) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureInterceptorBindingCalled$str(), extensionName, type);
   }

   protected String configureInterceptorBindingCalled$str() {
      return "WELD-000172: BeforeBeanDiscovery.configureInterceptorBinding() called by {0} for {1}";
   }

   public final void configureProducerCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureProducerCalled$str(), extensionName, bean);
   }

   protected String configureProducerCalled$str() {
      return "WELD-000173: ProcessProducer.configureProducer() called by {0} for {1}";
   }

   public final void configureBeanAttributesCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureBeanAttributesCalled$str(), extensionName, bean);
   }

   protected String configureBeanAttributesCalled$str() {
      return "WELD-000174: ProcessBeanAttributes.configureBeanAttributes() called by {0} for {1}";
   }

   public final void ignoreFinalMethodsCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.ignoreFinalMethodsCalled$str(), extensionName, bean);
   }

   protected String ignoreFinalMethodsCalled$str() {
      return "WELD-000175: ProcessBeanAttributes.isIgnoreFinalMethods() called by {0} for {1}";
   }

   public final void configureAnnotatedTypeCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureAnnotatedTypeCalled$str(), extensionName, bean);
   }

   protected String configureAnnotatedTypeCalled$str() {
      return "WELD-000176: ProcessAnnotatedType.configureAnnotatedType() called by {0} for {1}";
   }

   public final void configureObserverMethodCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureObserverMethodCalled$str(), extensionName, bean);
   }

   protected String configureObserverMethodCalled$str() {
      return "WELD-000177: ProcessObserverMethod.configureObserverMethod() called by {0} for {1}";
   }

   public final void configureInjectionPointCalled(Object extensionName, Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configureInjectionPointCalled$str(), extensionName, bean);
   }

   protected String configureInjectionPointCalled$str() {
      return "WELD-000178: ProcessInjectionPoint.configureInjectionPoint() called by {0} for {1}";
   }

   protected String unableToProcessConfigurator$str() {
      return "WELD-000179: {0} created by {1} cannot be processed";
   }

   public final DeploymentException unableToProcessConfigurator(Object configurator, Object extensionName, Throwable cause) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.unableToProcessConfigurator$str(), configurator, extensionName), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void dropUnusedBeanMetadata(Object bean) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.dropUnusedBeanMetadata$str(), bean);
   }

   protected String dropUnusedBeanMetadata$str() {
      return "WELD-000180: Drop unused bean metadata: {0}";
   }

   public final void commonThreadPoolWithSecurityManagerEnabled(Object threadPoolType) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.commonThreadPoolWithSecurityManagerEnabled$str(), threadPoolType);
   }

   protected String commonThreadPoolWithSecurityManagerEnabled$str() {
      return "WELD-000181: org.jboss.weld.executor.threadPoolType=COMMON detected but ForkJoinPool.commonPool() does not work with SecurityManager enabled, switching to {0} thread pool";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
