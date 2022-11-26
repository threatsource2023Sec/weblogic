package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface MetadataLogger extends WeldLogger {
   MetadataLogger LOG = (MetadataLogger)Logger.getMessageLogger(MetadataLogger.class, Category.BOOTSTRAP.getName());

   @Message(
      id = 1100,
      value = "{0} can only be applied to an annotation.  It was applied to {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException metaAnnotationOnWrongType(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1101,
      value = "Member of array type or annotation type must be annotated @NonBinding:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   void nonBindingMemberType(Object var1);

   @Message(
      id = 1102,
      value = "Stereotype {0} not registered with container",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException stereotypeNotRegistered(Object var1);

   @Message(
      id = 1103,
      value = "Cannot declare qualifiers on stereotype {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException qualifierOnStereotype(Object var1);

   @Message(
      id = 1104,
      value = "Cannot specify a value for @Named stereotype {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException valueOnNamedStereotype(Object var1);

   @Message(
      id = 1105,
      value = "At most one scope type may be specified for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleScopes(Object var1);

   @Message(
      id = 1106,
      value = "BeanAttributes.getStereotypes() returned null for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException stereotypesNull(Object var1);

   @Message(
      id = 1107,
      value = "{0}() returned null for {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException qualifiersNull(Object var1, Object var2);

   @Message(
      id = 1108,
      value = "BeanAttributes.getTypes() returned null for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException typesNull(Object var1);

   @Message(
      id = 1109,
      value = "BeanAttributes.getScope() returned null for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException scopeNull(Object var1);

   @Message(
      id = 1110,
      value = "{0} defined on {1} is not a stereotype",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException notAStereotype(Object var1, Object var2);

   @Message(
      id = 1111,
      value = "{0} defined on {1} is not a qualifier",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException notAQualifier(Object var1, Object var2);

   @Message(
      id = 1112,
      value = "BeanAttributes.getTypes() may not return an empty set {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException typesEmpty(Object var1);

   @Message(
      id = 1113,
      value = "{0} defined on {1} is not a scope annotation",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException notAScope(Object var1, Object var2);

   @Message(
      id = 1114,
      value = "{0} returned null for {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException metadataSourceReturnedNull(Object var1, Object var2);

   @Message(
      id = 1115,
      value = "Parameter position {0} of parameter {1} is not valid",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidParameterPosition(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1116,
      value = "AnnotatedType ({0}) without a constructor",
      format = Format.MESSAGE_FORMAT
   )
   void noConstructor(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1117,
      value = "Member {0} ({1}) does not belong to the actual class hierarchy of the annotatedType {2} ({3})\n\tat {4}",
      format = Format.MESSAGE_FORMAT
   )
   void notInHierarchy(Object var1, Object var2, Object var3, Object var4, Object var5);

   @Message(
      id = 1118,
      value = "A type variable is not a valid bean type. Bean type {0} of bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException typeVariableIsNotAValidBeanType(Object var1, Object var2);

   @Message(
      id = 1119,
      value = "A parameterized type containing wildcard parameters is not a valid bean type. Bean type {0} of bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException parameterizedTypeContainingWildcardParameterIsNotAValidBeanType(Object var1, Object var2);

   @Message(
      id = 1120,
      value = "A bean that has a parameterized bean type containing type variables must be @Dependent scoped. Bean type {0} of bean {1}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped(Object var1, Object var2);

   @Message(
      id = 1121,
      value = "Member of array type or annotation type must be annotated @NonBinding:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException nonBindingMemberTypeException(Object var1);

   @Message(
      id = 1122,
      value = "Failed to deserialize annotated type identified with {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException annotatedTypeDeserializationFailure(AnnotatedTypeIdentifier var1);

   @Message(
      id = 1123,
      value = "{0} defined on {1} is not an interceptor binding",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException notAnInterceptorBinding(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1124,
      value = "Context.getScope() returned {0} which is not a scope annotation. Context: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void contextGetScopeIsNotAScope(Object var1, Object var2);

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 1125,
      value = "Illegal bean type {0} ignored on {1}",
      format = Format.MESSAGE_FORMAT
   )
   void illegalBeanTypeIgnored(Object var1, Object var2);

   @Message(
      id = 1126,
      value = "BeanAttributesConfigurator is not able to read {0} - missing BeanManager",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException beanAttributesConfiguratorCannotReadAnnotatedType(Object var1);
}
