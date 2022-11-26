package org.jboss.weld.logging;

import javax.naming.NamingException;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.contexts.ContextNotActiveException;
import org.jboss.weld.exceptions.AmbiguousResolutionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.UnsatisfiedResolutionException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface BeanManagerLogger extends WeldLogger {
   BeanManagerLogger LOG = (BeanManagerLogger)Logger.getMessageLogger(BeanManagerLogger.class, Category.BEAN_MANAGER.getName());

   @Message(
      id = 1300,
      value = "Unable to locate BeanManager"
   )
   NamingException cannotLocateBeanManager();

   @Message(
      id = 1301,
      value = "Annotation {0} is not a qualifier",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidQualifier(Object var1);

   @Message(
      id = 1302,
      value = "Duplicate qualifiers:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException duplicateQualifiers(Object var1);

   @Message(
      id = 1303,
      value = "No active contexts for scope type {0}",
      format = Format.MESSAGE_FORMAT
   )
   ContextNotActiveException contextNotActive(Object var1);

   @Message(
      id = 1304,
      value = "More than one context active for scope type {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException duplicateActiveContexts(Object var1);

   @Message(
      id = 1305,
      value = "The given type {0} is not a type of the bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException specifiedTypeNotBeanType(Object var1, Object var2);

   @Message(
      id = 1307,
      value = "Unable to resolve any beans of type {0} with qualifiers {1}",
      format = Format.MESSAGE_FORMAT
   )
   UnsatisfiedResolutionException unresolvableType(Object var1, Object var2);

   @Message(
      id = 1308,
      value = "Unable to resolve any beans for {0}",
      format = Format.MESSAGE_FORMAT
   )
   UnsatisfiedResolutionException unresolvableElement(Object var1);

   @Message(
      id = 1310,
      value = "No decorator types were specified in the set"
   )
   IllegalArgumentException noDecoratorTypes();

   @Message(
      id = 1311,
      value = "Interceptor bindings list cannot be empty"
   )
   IllegalArgumentException interceptorBindingsEmpty();

   @Message(
      id = 1312,
      value = "Duplicate interceptor binding type {0} found",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException duplicateInterceptorBinding(Object var1);

   @Message(
      id = 1313,
      value = "Trying to resolve interceptors with non-binding type {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException interceptorResolutionWithNonbindingType(Object var1);

   @Message(
      id = 1314,
      value = "{0} is expected to be a normal scope type",
      format = Format.MESSAGE_FORMAT
   )
   String nonNormalScope(Object var1);

   @Message(
      id = 1316,
      value = "{0} is not an interceptor binding type",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException notInterceptorBindingType(Object var1);

   @Message(
      id = 1317,
      value = "{0} is not a stereotype",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException notStereotype(Object var1);

   @Message(
      id = 1318,
      value = "Cannot resolve an ambiguous dependency between: {0}",
      format = Format.MESSAGE_FORMAT
   )
   AmbiguousResolutionException ambiguousBeansForDependency(Object var1);

   @Message(
      id = 1319,
      value = "Bean manager ID must not be null"
   )
   IllegalArgumentException nullBeanManagerId();

   @Message(
      id = 1325,
      value = "No instance of an extension {0} registered with the deployment",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException noInstanceOfExtension(Object var1);

   @Message(
      id = 1326,
      value = "Cannot create bean attributes - the argument must be either an AnnotatedField or AnnotatedMethod but {0} is not",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotCreateBeanAttributesForIncorrectAnnotatedMember(Object var1);

   @Message(
      id = 1327,
      value = "Unable to identify the correct BeanManager. The calling class {0} is placed in multiple bean archives",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException ambiguousBeanManager(Object var1);

   @Message(
      id = 1328,
      value = "Unable to identify the correct BeanManager. The calling class {0} is not placed in bean archive",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unsatisfiedBeanManager(Object var1);

   @Message(
      id = 1329,
      value = "Unable to identify the correct BeanManager"
   )
   IllegalStateException unableToIdentifyBeanManager();

   @Message(
      id = 1330,
      value = "BeanManager is not available."
   )
   IllegalStateException beanManagerNotAvailable();

   @Message(
      id = 1331,
      value = "Declaring bean cannot be null for the non-static member {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException nullDeclaringBean(Object var1);

   @Message(
      id = 1332,
      value = "BeanManager method {0} is not available during application initialization. Container state: {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException methodNotAvailableDuringInitialization(Object var1, Object var2);

   @Message(
      id = 1333,
      value = "BeanManager method {0} is not available after shutdown",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException methodNotAvailableAfterShutdown(Object var1);

   @Message(
      id = 1334,
      value = "Unsatisfied dependencies for type {1} with qualifiers {0} {2}",
      format = Format.MESSAGE_FORMAT
   )
   UnsatisfiedResolutionException injectionPointHasUnsatisfiedDependencies(Object var1, Object var2, Object var3);

   @Message(
      id = 1335,
      value = "Ambiguous dependencies for type {1} with qualifiers {0}\n Possible dependencies: {2}",
      format = Format.MESSAGE_FORMAT
   )
   AmbiguousResolutionException injectionPointHasAmbiguousDependencies(Object var1, Object var2, Object var3);

   @Message(
      id = 1336,
      value = "InjectionTargetFactory.configure() may not be called after createInjectionTarget() invocation. AnnotatedType used: {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToConfigureInjectionTargetFactory(Object var1);
}
