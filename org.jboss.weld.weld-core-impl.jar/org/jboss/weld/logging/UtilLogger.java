package org.jboss.weld.logging;

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
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.WeldException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface UtilLogger extends WeldLogger {
   UtilLogger LOG = (UtilLogger)Logger.getMessageLogger(UtilLogger.class, Category.UTIL.getName());

   @Message(
      id = 804,
      value = "{0} is not an enum",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException classNotEnum(Object var1);

   @Message(
      id = 805,
      value = "Cannot have more than one post construct method annotated with @PostConstruct for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException tooManyPostConstructMethods(Object var1);

   @Message(
      id = 806,
      value = "Cannot have more than one pre destroy method annotated @PreDestroy for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException tooManyPreDestroyMethods(Object var1);

   @Message(
      id = 807,
      value = "Initializer method cannot be annotated @Produces {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException initializerCannotBeProducer(Object var1, Object var2);

   @Message(
      id = 808,
      value = "Initializer method cannot have parameters annotated @Disposes: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException initializerCannotBeDisposalMethod(Object var1, Object var2);

   @Message(
      id = 810,
      value = "Cannot place qualifiers on final fields:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException qualifierOnFinalField(Object var1);

   @Message(
      id = 812,
      value = "Cannot determine constructor to use for {0}. Possible constructors {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException ambiguousConstructor(Object var1, Object var2);

   @Message(
      id = 813,
      value = "injectableFields and initializerMethods must have the same size.\n\nInjectable Fields:  {0}\nInitializerMethods:  {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidQuantityInjectableFieldsAndInitializerMethods(Object var1, Object var2);

   @Message(
      id = 814,
      value = "Annotation {0} is not a qualifier",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException annotationNotQualifier(Object var1);

   @Message(
      id = 815,
      value = "Qualifier {0} is already present in the set {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException redundantQualifier(Object var1, Object var2);

   @Message(
      id = 816,
      value = "Cannot determine constructor to use for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException unableToFindConstructor(Object var1);

   @Message(
      id = 817,
      value = "Unable to find Bean Deployment Archive for {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToFindBeanDeploymentArchive(Object var1);

   @Message(
      id = 818,
      value = "Event type {0} is not allowed",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException eventTypeNotAllowed(Object var1);

   @Message(
      id = 819,
      value = "Cannot provide an event type parameterized with a type parameter {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException typeParameterNotAllowedInEventType(Object var1);

   @Message(
      id = 820,
      value = "Cannot proxy non-Class Type {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotProxyNonClassType(Object var1);

   @Message(
      id = 824,
      value = "Error getting field {0} on {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException accessErrorOnField(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 826,
      value = "Cannot access values() on annotation"
   )
   DeploymentException annotationValuesInaccessible(@Cause Throwable var1);

   @Message(
      id = 827,
      value = "Initializer method may not be a generic method: {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException initializerMethodIsGeneric(Object var1, Object var2);

   @Message(
      id = 833,
      value = "Resource injection point represents a method which doesn't follow JavaBean conventions {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException resourceSetterInjectionNotAJavabean(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 834,
      value = "Unable to inject resource - most probably incorrect InjectionServices SPI implementation: {0}\n\tat {1}",
      format = Format.MESSAGE_FORMAT
   )
   void unableToInjectResource(Object var1, Object var2);
}
