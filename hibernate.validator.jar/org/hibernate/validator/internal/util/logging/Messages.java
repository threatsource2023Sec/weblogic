package org.hibernate.validator.internal.util.logging;

import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageBundle;
import org.jboss.logging.annotations.Message.Format;

@MessageBundle(
   projectCode = "HV"
)
public interface Messages {
   Messages MESSAGES = (Messages)org.jboss.logging.Messages.getBundle(Messages.class);

   @Message(
      value = "must not be null.",
      format = Format.NO_FORMAT
   )
   String mustNotBeNull();

   @Message("%s must not be null.")
   String mustNotBeNull(String var1);

   @Message("The parameter \"%s\" must not be null.")
   String parameterMustNotBeNull(String var1);

   @Message("The parameter \"%s\" must not be empty.")
   String parameterMustNotBeEmpty(String var1);

   @Message(
      value = "The bean type cannot be null.",
      format = Format.NO_FORMAT
   )
   String beanTypeCannotBeNull();

   @Message(
      value = "null is not allowed as property path.",
      format = Format.NO_FORMAT
   )
   String propertyPathCannotBeNull();

   @Message(
      value = "The property name must not be empty.",
      format = Format.NO_FORMAT
   )
   String propertyNameMustNotBeEmpty();

   @Message(
      value = "null passed as group name.",
      format = Format.NO_FORMAT
   )
   String groupMustNotBeNull();

   @Message(
      value = "The bean type must not be null when creating a constraint mapping.",
      format = Format.NO_FORMAT
   )
   String beanTypeMustNotBeNull();

   @Message(
      value = "The method name must not be null.",
      format = Format.NO_FORMAT
   )
   String methodNameMustNotBeNull();

   @Message(
      value = "The object to be validated must not be null.",
      format = Format.NO_FORMAT
   )
   String validatedObjectMustNotBeNull();

   @Message(
      value = "The method to be validated must not be null.",
      format = Format.NO_FORMAT
   )
   String validatedMethodMustNotBeNull();

   @Message(
      value = "The class cannot be null.",
      format = Format.NO_FORMAT
   )
   String classCannotBeNull();

   @Message(
      value = "Class is null.",
      format = Format.NO_FORMAT
   )
   String classIsNull();

   @Message(
      value = "The constructor to be validated must not be null.",
      format = Format.NO_FORMAT
   )
   String validatedConstructorMustNotBeNull();

   @Message(
      value = "The method parameter array cannot not be null.",
      format = Format.NO_FORMAT
   )
   String validatedParameterArrayMustNotBeNull();

   @Message(
      value = "The created instance must not be null.",
      format = Format.NO_FORMAT
   )
   String validatedConstructorCreatedInstanceMustNotBeNull();

   @Message("The input stream for #addMapping() cannot be null.")
   String inputStreamCannotBeNull();

   @Message("Constraints on the parameters of constructors of non-static inner classes are not supported if those parameters have a generic type due to JDK bug JDK-5087240.")
   String constraintOnConstructorOfNonStaticInnerClass();

   @Message("Custom parameterized types with more than one type argument are not supported and will not be checked for type use constraints.")
   String parameterizedTypesWithMoreThanOneTypeArgument();

   @Message("Hibernate Validator cannot instantiate AggregateResourceBundle.CONTROL. This can happen most notably in a Google App Engine environment or when running Hibernate Validator as Java 9 named module. A PlatformResourceBundleLocator without bundle aggregation was created. This only affects you in case you are using multiple ConstraintDefinitionContributor JARs. ConstraintDefinitionContributors are a Hibernate Validator specific feature. All Bean Validation features work as expected. See also https://hibernate.atlassian.net/browse/HV-1023.")
   String unableToUseResourceBundleAggregation();

   @Message(
      value = "The annotation type must not be null when creating a constraint definition.",
      format = Format.NO_FORMAT
   )
   String annotationTypeMustNotBeNull();

   @Message(
      value = "The annotation type must be annotated with @javax.validation.Constraint when creating a constraint definition.",
      format = Format.NO_FORMAT
   )
   String annotationTypeMustBeAnnotatedWithConstraint();
}
