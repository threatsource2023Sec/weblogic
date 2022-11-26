package org.jboss.weld.logging;

import javax.enterprise.inject.spi.ObserverMethod;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface BootstrapLogger extends WeldLogger {
   BootstrapLogger LOG = (BootstrapLogger)Logger.getMessageLogger(BootstrapLogger.class, Category.BOOTSTRAP.getName());
   BootstrapLogger TRACKER_LOG = (BootstrapLogger)Logger.getMessageLogger(BootstrapLogger.class, Category.BOOTSTRAP_TRACKER.getName());

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 100,
      value = "Weld initialized. Validating beans"
   )
   void validatingBeans();

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 101,
      value = "Transactional services not available. Injection of @Inject UserTransaction not available. Transactional observers will be invoked synchronously."
   )
   void jtaUnavailable();

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 103,
      value = "Enabled alternatives for {0}: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void enabledAlternatives(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 104,
      value = "Enabled decorator types for {0}: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void enabledDecorators(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 105,
      value = "Enabled interceptor types for {0}: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void enabledInterceptors(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 106,
      value = "Bean: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundBean(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 107,
      value = "Interceptor: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundInterceptor(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 108,
      value = "Decorator: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundDecorator(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 109,
      value = "ObserverMethod: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundObserverMethod(Object var1);

   @Message(
      id = 110,
      value = "Cannot set the annotation type to null (if you want to stop the type being used, call veto()):  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException annotationTypeNull(Object var1);

   @Message(
      id = 111,
      value = "Bean type is not STATELESS, STATEFUL or SINGLETON:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException beanTypeNotEjb(Object var1);

   @Message(
      id = 112,
      value = "Class {0} has both @Interceptor and @Decorator annotations",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanIsBothInterceptorAndDecorator(Object var1);

   @Message(
      id = 113,
      value = "BeanDeploymentArchive must not be null:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException deploymentArchiveNull(Object var1);

   @Message(
      id = 114,
      value = "Must start the container with a deployment"
   )
   IllegalArgumentException deploymentRequired();

   @Message(
      id = 116,
      value = "Manager has not been initialized"
   )
   IllegalStateException managerNotInitialized();

   @Message(
      id = 117,
      value = "Required service {0} has not been specified for {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unspecifiedRequiredService(Object var1, Object var2);

   @Message(
      id = 118,
      value = "Only normal scopes can be passivating. Scope {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException passivatingNonNormalScopeIllegal(Object var1);

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 119,
      value = "Not generating any bean definitions from {0} because of underlying class loading error: Type {1} not found.  If this is unexpected, enable DEBUG logging to see the full error.",
      format = Format.MESSAGE_FORMAT
   )
   void ignoringClassDueToLoadingError(Object var1, Object var2);

   @Message(
      id = 123,
      value = "Error loading {0} defined in {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException errorLoadingBeansXmlEntry(Object var1, Object var2, @Cause Throwable var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 124,
      value = "Using {0} threads for bootstrap",
      format = Format.MESSAGE_FORMAT
   )
   void threadsInUse(Object var1);

   @Message(
      id = 125,
      value = "Invalid thread pool size: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException invalidThreadPoolSize(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 126,
      value = "Timeout shutting down thread pool {0} at {1}",
      format = Format.MESSAGE_FORMAT
   )
   void timeoutShuttingDownThreadPool(Object var1, Object var2);

   @Message(
      id = 127,
      value = "Invalid thread pool type: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException invalidThreadPoolType(Object var1);

   @Message(
      id = 128,
      value = "Invalid value for property {0}: {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException invalidPropertyValue(Object var1, Object var2);

   @Message(
      id = 130,
      value = "Cannot replace AnnotatedType for {0} with AnnotatedType for {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException annotatedTypeJavaClassMismatch(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 132,
      value = "Disabled alternative (ignored): {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundDisabledAlternative(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 133,
      value = "Specialized bean (ignored): {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundSpecializedBean(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 134,
      value = "Producer (method or field) of specialized bean (ignored): {0}",
      format = Format.MESSAGE_FORMAT
   )
   void foundProducerOfSpecializedBean(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 135,
      value = "Legacy deployment metadata provided by the integrator. Certain functionality will not be available."
   )
   void legacyDeploymentMetadataProvided();

   @LogMessage(
      level = Level.ERROR
   )
   @Message(
      id = 136,
      value = "Exception(s) thrown during observer of BeforeShutdown: "
   )
   void exceptionThrownDuringBeforeShutdownObserver();

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 137,
      value = "Exception while loading class '{0}' : {1}",
      format = Format.MESSAGE_FORMAT
   )
   void exceptionWhileLoadingClass(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 138,
      value = "Error while loading class '{0}' : {1}",
      format = Format.MESSAGE_FORMAT
   )
   void errorWhileLoadingClass(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 139,
      value = "Ignoring portable extension class {0} because of underlying class loading error: Type {1} not found. Enable DEBUG logging level to see the full error.",
      format = Format.MESSAGE_FORMAT
   )
   void ignoringExtensionClassDueToLoadingError(String var1, String var2);

   @Message(
      id = 140,
      value = "Calling Bootstrap method after container has already been initialized. For correct order, see CDI11Bootstrap's documentation."
   )
   IllegalStateException callingBootstrapMethodAfterContainerHasBeenInitialized();

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 141,
      value = "Falling back to the default observer method resolver due to {0}",
      format = Format.MESSAGE_FORMAT
   )
   void notUsingFastResolver(ObserverMethod var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 142,
      value = "Exception loading annotated type using ClassFileServices. Falling back to the default implementation. {0}",
      format = Format.MESSAGE_FORMAT
   )
   void exceptionLoadingAnnotatedType(String var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 0,
      value = "No PAT observers resolved for {0}. Skipping.",
      format = Format.MESSAGE_FORMAT
   )
   void patSkipped(SlimAnnotatedType var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 0,
      value = "Sending PAT using the default event resolver: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void patDefaultResolver(SlimAnnotatedType var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 0,
      value = "Sending PAT using the fast event resolver: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void patFastResolver(SlimAnnotatedType var1);

   @Message(
      id = 143,
      value = "Container lifecycle event method invoked outside of extension observer method invocation."
   )
   IllegalStateException containerLifecycleEventMethodInvokedOutsideObserver();

   @Message(
      id = 144,
      value = "CDI API version mismatch. CDI 1.0 API detected on classpath. Weld requires version 1.1 or better."
   )
   IllegalStateException cdiApiVersionMismatch();

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 145,
      value = "Bean identifier index built:\n  {0}",
      format = Format.MESSAGE_FORMAT
   )
   void beanIdentifierIndexBuilt(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 146,
      value = "BeforeBeanDiscovery.addAnnotatedType(AnnotatedType<?>) used for {0} is deprecated from CDI 1.1!",
      format = Format.MESSAGE_FORMAT
   )
   void deprecatedAddAnnotatedTypeMethodUsed(Class var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 147,
      value = "Decorator {0} declares inappropriate constructor therefore will not available as a managed bean!",
      format = Format.MESSAGE_FORMAT
   )
   void decoratorWithNonCdiConstructor(String var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 148,
      value = "ProcessAnnotatedType.setAnnotatedType() called by {0}: {1} replaced by {2}",
      format = Format.MESSAGE_FORMAT
   )
   void setAnnotatedTypeCalled(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 149,
      value = "ProcessBeanAttributes.setBeanAttributes() called by {0}: {1} replaced by {2}",
      format = Format.MESSAGE_FORMAT
   )
   void setBeanAttributesCalled(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 150,
      value = "ProcessInjectionPoint.setInjectionPoint() called by {0}: {1} replaced by {2}",
      format = Format.MESSAGE_FORMAT
   )
   void setInjectionPointCalled(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 151,
      value = "ProcessInjectionTarget.setInjectionTarget() called by {0}: {1} replaced by {2}",
      format = Format.MESSAGE_FORMAT
   )
   void setInjectionTargetCalled(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 152,
      value = "ProcessProducer.setProducer() called by {0}: {1} replaced by {2}",
      format = Format.MESSAGE_FORMAT
   )
   void setProducerCalled(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 153,
      value = "AfterTypeDiscovery.addAnnotatedType() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addAnnotatedTypeCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 154,
      value = "AfterBeanDiscovery.addBean() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addBeanCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 155,
      value = "AfterBeanDiscovery.addObserverMethod() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addObserverMethodCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 156,
      value = "AfterBeanDiscovery.addContext() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addContext(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 157,
      value = "AfterBeanDiscovery.addDefinitionError() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addDefinitionErrorCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 158,
      value = "BeforeBeanDiscovery.addQualifier() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addQualifierCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 159,
      value = "BeforeBeanDiscovery.addScope() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addScopeCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 160,
      value = "BeforeBeanDiscovery.addStereoType() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addStereoTypeCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 161,
      value = "BeforeBeanDiscovery.addInterceptorBindingCalled() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addInterceptorBindingCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 162,
      value = "BeforeBeanDiscovery.addAnnotatedType() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addAnnotatedTypeCalledInBBD(Object var1, Object var2);

   @Message(
      id = 163,
      value = "Non-unique bean deployment identifier detected: {0}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException nonuniqueBeanDeploymentIdentifier(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 164,
      value = "ProcessAnnotatedType.veto() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void annotatedTypeVetoed(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 165,
      value = "ProcessBeanAttributes.veto() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void beanAttributesVetoed(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 166,
      value = "AfterTypeDiscovery.{3} modified by {0} {2} {1}",
      format = Format.MESSAGE_FORMAT
   )
   void typeModifiedInAfterTypeDiscovery(Object var1, Object var2, Object var3, Object var4);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 167,
      value = "Class {0} is annotated with @{1} but it does not declare an appropriate constructor therefore is not registered as a bean!",
      format = Format.MESSAGE_FORMAT
   )
   void annotatedTypeNotRegisteredAsBeanDueToMissingAppropriateConstructor(String var1, String var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 168,
      value = "Extension bean deployed: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void extensionBeanDeployed(Object var1);

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 169,
      value = "Jandex cannot distinguish inner and static nested classes! Update Jandex to 2.0.3.Final version or newer to improve scanning performance.",
      format = Format.MESSAGE_FORMAT
   )
   void usingOldJandexVersion();

   @Message(
      id = 170,
      value = "{0} observer cannot call both the configurator and set methods. Extension {1} \nStackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException configuratorAndSetMethodBothCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 171,
      value = "BeforeBeanDiscovery.configureQualifier() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureQualifierCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 172,
      value = "BeforeBeanDiscovery.configureInterceptorBinding() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureInterceptorBindingCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 173,
      value = "ProcessProducer.configureProducer() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureProducerCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 174,
      value = "ProcessBeanAttributes.configureBeanAttributes() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureBeanAttributesCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 175,
      value = "ProcessBeanAttributes.isIgnoreFinalMethods() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void ignoreFinalMethodsCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 176,
      value = "ProcessAnnotatedType.configureAnnotatedType() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureAnnotatedTypeCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 177,
      value = "ProcessObserverMethod.configureObserverMethod() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureObserverMethodCalled(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 178,
      value = "ProcessInjectionPoint.configureInjectionPoint() called by {0} for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void configureInjectionPointCalled(Object var1, Object var2);

   @Message(
      id = 179,
      value = "{0} created by {1} cannot be processed",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException unableToProcessConfigurator(Object var1, Object var2, @Cause Throwable var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 180,
      value = "Drop unused bean metadata: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void dropUnusedBeanMetadata(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 181,
      value = "org.jboss.weld.executor.threadPoolType=COMMON detected but ForkJoinPool.commonPool() does not work with SecurityManager enabled, switching to {0} thread pool",
      format = Format.MESSAGE_FORMAT
   )
   void commonThreadPoolWithSecurityManagerEnabled(Object var1);
}
