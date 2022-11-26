package org.hibernate.validator.internal.util.logging;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.PatternSyntaxException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintTarget;
import javax.validation.ElementKind;
import javax.validation.GroupDefinitionException;
import javax.validation.Path;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import javax.validation.valueextraction.ValueExtractor;
import javax.validation.valueextraction.ValueExtractorDeclarationException;
import javax.validation.valueextraction.ValueExtractorDefinitionException;
import javax.xml.stream.XMLStreamException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.logging.formatter.ArrayOfClassesObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ClassObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfClassesObjectFormatter;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.hibernate.validator.internal.util.logging.formatter.DurationFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ExecutableFormatter;
import org.hibernate.validator.internal.util.logging.formatter.ObjectArrayFormatter;
import org.hibernate.validator.internal.util.logging.formatter.TypeFormatter;
import org.hibernate.validator.internal.xml.mapping.ContainerElementTypePath;
import org.hibernate.validator.spi.scripting.ScriptEvaluationException;
import org.hibernate.validator.spi.scripting.ScriptEvaluatorNotFoundException;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class Log_$logger extends DelegatingBasicLogger implements Log, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = Log_$logger.class.getName();
   private static final Locale LOCALE;
   private static final String version = "HV000001: Hibernate Validator %s";
   private static final String ignoringXmlConfiguration = "HV000002: Ignoring XML configuration.";
   private static final String usingConstraintValidatorFactory = "HV000003: Using %s as constraint validator factory.";
   private static final String usingMessageInterpolator = "HV000004: Using %s as message interpolator.";
   private static final String usingTraversableResolver = "HV000005: Using %s as traversable resolver.";
   private static final String usingValidationProvider = "HV000006: Using %s as validation provider.";
   private static final String parsingXMLFile = "HV000007: %s found. Parsing XML based configuration.";
   private static final String unableToCloseInputStream = "HV000008: Unable to close input stream.";
   private static final String unableToCloseXMLFileInputStream = "HV000010: Unable to close input stream for %s.";
   private static final String unableToCreateSchema = "HV000011: Unable to create schema for %1$s: %2$s";
   private static final String getUnableToCreateAnnotationForConfiguredConstraintException = "HV000012: Unable to create annotation for configured constraint";
   private static final String getUnableToFindPropertyWithAccessException = "HV000013: The class %1$s does not have a property '%2$s' with access %3$s.";
   private static final String getInvalidBigDecimalFormatException = "HV000016: %s does not represent a valid BigDecimal format.";
   private static final String getInvalidLengthForIntegerPartException = "HV000017: The length of the integer part cannot be negative.";
   private static final String getInvalidLengthForFractionPartException = "HV000018: The length of the fraction part cannot be negative.";
   private static final String getMinCannotBeNegativeException = "HV000019: The min parameter cannot be negative.";
   private static final String getMaxCannotBeNegativeException = "HV000020: The max parameter cannot be negative.";
   private static final String getLengthCannotBeNegativeException = "HV000021: The length cannot be negative.";
   private static final String getInvalidRegularExpressionException = "HV000022: Invalid regular expression.";
   private static final String getErrorDuringScriptExecutionException = "HV000023: Error during execution of script \"%s\" occurred.";
   private static final String getScriptMustReturnTrueOrFalseException1 = "HV000024: Script \"%s\" returned null, but must return either true or false.";
   private static final String getScriptMustReturnTrueOrFalseException3 = "HV000025: Script \"%1$s\" returned %2$s (of type %3$s), but must return either true or false.";
   private static final String getInconsistentConfigurationException = "HV000026: Assertion error: inconsistent ConfigurationImpl construction.";
   private static final String getUnableToFindProviderException = "HV000027: Unable to find provider: %s.";
   private static final String getExceptionDuringIsValidCallException = "HV000028: Unexpected exception during isValid call.";
   private static final String getConstraintValidatorFactoryMustNotReturnNullException = "HV000029: Constraint factory returned null when trying to create instance of %s.";
   private static final String getNoValidatorFoundForTypeException = "HV000030: No validator could be found for constraint '%s' validating type '%s'. Check configuration for '%s'";
   private static final String getMoreThanOneValidatorFoundForTypeException = "HV000031: There are multiple validator classes which could validate the type %1$s. The validator classes are: %2$s.";
   private static final String getUnableToInitializeConstraintValidatorException = "HV000032: Unable to initialize %s.";
   private static final String getAtLeastOneCustomMessageMustBeCreatedException = "HV000033: At least one custom message must be created if the default error message gets disabled.";
   private static final String getInvalidJavaIdentifierException = "HV000034: %s is not a valid Java Identifier.";
   private static final String getUnableToParsePropertyPathException = "HV000035: Unable to parse property path %s.";
   private static final String getTypeNotSupportedForUnwrappingException = "HV000036: Type %s not supported for unwrapping.";
   private static final String getInconsistentFailFastConfigurationException = "HV000037: Inconsistent fail fast configuration. Fail fast enabled via programmatic API, but explicitly disabled via properties.";
   private static final String getInvalidPropertyPathException0 = "HV000038: Invalid property path.";
   private static final String getInvalidPropertyPathException2 = "HV000039: Invalid property path. Either there is no property %2$s in entity %1$s or it is not possible to cascade to the property.";
   private static final String getPropertyPathMustProvideIndexOrMapKeyException = "HV000040: Property path must provide index or map key.";
   private static final String getErrorDuringCallOfTraversableResolverIsReachableException = "HV000041: Call to TraversableResolver.isReachable() threw an exception.";
   private static final String getErrorDuringCallOfTraversableResolverIsCascadableException = "HV000042: Call to TraversableResolver.isCascadable() threw an exception.";
   private static final String getUnableToExpandDefaultGroupListException = "HV000043: Unable to expand default group list %1$s into sequence %2$s.";
   private static final String getAtLeastOneGroupHasToBeSpecifiedException = "HV000044: At least one group has to be specified.";
   private static final String getGroupHasToBeAnInterfaceException = "HV000045: A group has to be an interface. %s is not.";
   private static final String getSequenceDefinitionsNotAllowedException = "HV000046: Sequence definitions are not allowed as composing parts of a sequence.";
   private static final String getCyclicDependencyInGroupsDefinitionException = "HV000047: Cyclic dependency in groups definition";
   private static final String getUnableToExpandGroupSequenceException = "HV000048: Unable to expand group sequence.";
   private static final String getInvalidDefaultGroupSequenceDefinitionException = "HV000052: Default group sequence and default group sequence provider cannot be defined at the same time.";
   private static final String getNoDefaultGroupInGroupSequenceException = "HV000053: 'Default.class' cannot appear in default group sequence list.";
   private static final String getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException = "HV000054: %s must be part of the redefined default group sequence.";
   private static final String getWrongDefaultGroupSequenceProviderTypeException = "HV000055: The default group sequence provider defined for %s has the wrong type";
   private static final String getInvalidExecutableParameterIndexException = "HV000056: Method or constructor %1$s doesn't have a parameter with index %2$d.";
   private static final String getUnableToRetrieveAnnotationParameterValueException = "HV000059: Unable to retrieve annotation parameter value.";
   private static final String getInvalidLengthOfParameterMetaDataListException = "HV000062: Method or constructor %1$s has %2$s parameters, but the passed list of parameter meta data has a size of %3$s.";
   private static final String getUnableToInstantiateException1 = "HV000063: Unable to instantiate %s.";
   private static final String getUnableToInstantiateException2 = "HV000064: Unable to instantiate %1$s: %2$s.";
   private static final String getUnableToLoadClassException = "HV000065: Unable to load class: %s from %s.";
   private static final String getStartIndexCannotBeNegativeException = "HV000068: Start index cannot be negative: %d.";
   private static final String getEndIndexCannotBeNegativeException = "HV000069: End index cannot be negative: %d.";
   private static final String getInvalidRangeException = "HV000070: Invalid Range: %1$d > %2$d.";
   private static final String getInvalidCheckDigitException = "HV000071: A explicitly specified check digit must lie outside the interval: [%1$d, %2$d].";
   private static final String getCharacterIsNotADigitException = "HV000072: '%c' is not a digit.";
   private static final String getConstraintParametersCannotStartWithValidException = "HV000073: Parameters starting with 'valid' are not allowed in a constraint.";
   private static final String getConstraintWithoutMandatoryParameterException = "HV000074: %2$s contains Constraint annotation, but does not contain a %1$s parameter.";
   private static final String getWrongDefaultValueForPayloadParameterException = "HV000075: %s contains Constraint annotation, but the payload parameter default value is not the empty array.";
   private static final String getWrongTypeForPayloadParameterException = "HV000076: %s contains Constraint annotation, but the payload parameter is of wrong type.";
   private static final String getWrongDefaultValueForGroupsParameterException = "HV000077: %s contains Constraint annotation, but the groups parameter default value is not the empty array.";
   private static final String getWrongTypeForGroupsParameterException = "HV000078: %s contains Constraint annotation, but the groups parameter is of wrong type.";
   private static final String getWrongTypeForMessageParameterException = "HV000079: %s contains Constraint annotation, but the message parameter is not of type java.lang.String.";
   private static final String getOverriddenConstraintAttributeNotFoundException = "HV000080: Overridden constraint does not define an attribute with name %s.";
   private static final String getWrongAttributeTypeForOverriddenConstraintException = "HV000081: The overriding type of a composite constraint must be identical to the overridden one. Expected %1$s found %2$s.";
   private static final String getWrongAnnotationAttributeTypeException = "HV000082: Wrong type for attribute '%2$s' of annotation %1$s. Expected: %3$s. Actual: %4$s.";
   private static final String getUnableToFindAnnotationAttributeException = "HV000083: The specified annotation %1$s defines no attribute '%2$s'.";
   private static final String getUnableToGetAnnotationAttributeException = "HV000084: Unable to get attribute '%2$s' from annotation %1$s.";
   private static final String getNoValueProvidedForAnnotationAttributeException = "HV000085: No value provided for attribute '%1$s' of annotation @%2$s.";
   private static final String getTryingToInstantiateAnnotationWithUnknownAttributesException = "HV000086: Trying to instantiate annotation %1$s with unknown attribute(s): %2$s.";
   private static final String getPropertyNameCannotBeNullOrEmptyException = "HV000087: Property name cannot be null or empty.";
   private static final String getElementTypeHasToBeFieldOrMethodException = "HV000088: Element type has to be FIELD or METHOD.";
   private static final String getMemberIsNeitherAFieldNorAMethodException = "HV000089: Member %s is neither a field nor a method.";
   private static final String getUnableToAccessMemberException = "HV000090: Unable to access %s.";
   private static final String getHasToBeAPrimitiveTypeException = "HV000091: %s has to be a primitive type.";
   private static final String getNullIsAnInvalidTypeForAConstraintValidatorException = "HV000093: null is an invalid type for a constraint validator.";
   private static final String getMissingActualTypeArgumentForTypeParameterException = "HV000094: Missing actual type argument for type parameter: %s.";
   private static final String getUnableToInstantiateConstraintValidatorFactoryClassException = "HV000095: Unable to instantiate constraint factory class %s.";
   private static final String getUnableToOpenInputStreamForMappingFileException = "HV000096: Unable to open input stream for mapping file %s.";
   private static final String getUnableToInstantiateMessageInterpolatorClassException = "HV000097: Unable to instantiate message interpolator class %s.";
   private static final String getUnableToInstantiateTraversableResolverClassException = "HV000098: Unable to instantiate traversable resolver class %s.";
   private static final String getUnableToInstantiateValidationProviderClassException = "HV000099: Unable to instantiate validation provider class %s.";
   private static final String getUnableToParseValidationXmlFileException = "HV000100: Unable to parse %s.";
   private static final String getIsNotAnAnnotationException = "HV000101: %s is not an annotation.";
   private static final String getIsNotAConstraintValidatorClassException = "HV000102: %s is not a constraint validator class.";
   private static final String getBeanClassHasAlreadyBeenConfiguredInXmlException = "HV000103: %s is configured at least twice in xml.";
   private static final String getIsDefinedTwiceInMappingXmlForBeanException = "HV000104: %1$s is defined twice in mapping xml for bean %2$s.";
   private static final String getBeanDoesNotContainTheFieldException = "HV000105: %1$s does not contain the fieldType %2$s.";
   private static final String getBeanDoesNotContainThePropertyException = "HV000106: %1$s does not contain the property %2$s.";
   private static final String getAnnotationDoesNotContainAParameterException = "HV000107: Annotation of type %1$s does not contain a parameter %2$s.";
   private static final String getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException = "HV000108: Attempt to specify an array where single value is expected.";
   private static final String getUnexpectedParameterValueException = "HV000109: Unexpected parameter value.";
   private static final String getInvalidNumberFormatException = "HV000110: Invalid %s format.";
   private static final String getInvalidCharValueException = "HV000111: Invalid char value: %s.";
   private static final String getInvalidReturnTypeException = "HV000112: Invalid return type: %s. Should be a enumeration type.";
   private static final String getReservedParameterNamesException = "HV000113: %s, %s, %s are reserved parameter names.";
   private static final String getWrongPayloadClassException = "HV000114: Specified payload class %s does not implement javax.validation.Payload";
   private static final String getErrorParsingMappingFileException = "HV000115: Error parsing mapping file.";
   private static final String getIllegalArgumentException = "HV000116: %s";
   private static final String getUnableToNarrowNodeTypeException = "HV000118: Unable to cast %s (with element kind %s) to %s";
   private static final String usingParameterNameProvider = "HV000119: Using %s as parameter name provider.";
   private static final String getUnableToInstantiateParameterNameProviderClassException = "HV000120: Unable to instantiate parameter name provider class %s.";
   private static final String getUnableToDetermineSchemaVersionException = "HV000121: Unable to parse %s.";
   private static final String getUnsupportedSchemaVersionException = "HV000122: Unsupported schema version for %s: %s.";
   private static final String getMultipleGroupConversionsForSameSourceException = "HV000124: Found multiple group conversions for source group %s: %s.";
   private static final String getGroupConversionOnNonCascadingElementException = "HV000125: Found group conversions for non-cascading element at: %s.";
   private static final String getGroupConversionForSequenceException = "HV000127: Found group conversion using a group sequence as source at: %s.";
   private static final String unknownPropertyInExpressionLanguage = "HV000129: EL expression '%s' references an unknown property";
   private static final String errorInExpressionLanguage = "HV000130: Error in EL expression '%s'";
   private static final String getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException = "HV000131: A method return value must not be marked for cascaded validation more than once in a class hierarchy, but the following two methods are marked as such: %s, %s.";
   private static final String getVoidMethodsMustNotBeConstrainedException = "HV000132: Void methods must not be constrained or marked for cascaded validation, but method %s is.";
   private static final String getBeanDoesNotContainConstructorException = "HV000133: %1$s does not contain a constructor with the parameter types %2$s.";
   private static final String getInvalidParameterTypeException = "HV000134: Unable to load parameter of type '%1$s' in %2$s.";
   private static final String getBeanDoesNotContainMethodException = "HV000135: %1$s does not contain a method with the name '%2$s' and parameter types %3$s.";
   private static final String getUnableToLoadConstraintAnnotationClassException = "HV000136: The specified constraint annotation class %1$s cannot be loaded.";
   private static final String getMethodIsDefinedTwiceInMappingXmlForBeanException = "HV000137: The method '%1$s' is defined twice in the mapping xml for bean %2$s.";
   private static final String getConstructorIsDefinedTwiceInMappingXmlForBeanException = "HV000138: The constructor '%1$s' is defined twice in the mapping xml for bean %2$s.";
   private static final String getMultipleCrossParameterValidatorClassesException = "HV000139: The constraint '%1$s' defines multiple cross parameter validators. Only one is allowed.";
   private static final String getImplicitConstraintTargetInAmbiguousConfigurationException = "HV000141: The constraint %1$s used ConstraintTarget#IMPLICIT where the target cannot be inferred.";
   private static final String getCrossParameterConstraintOnMethodWithoutParametersException = "HV000142: Cross parameter constraint %1$s is illegally placed on a parameterless method or constructor '%2$s'.";
   private static final String getCrossParameterConstraintOnClassException = "HV000143: Cross parameter constraint %1$s is illegally placed on class level.";
   private static final String getCrossParameterConstraintOnFieldException = "HV000144: Cross parameter constraint %1$s is illegally placed on field '%2$s'.";
   private static final String getParameterNodeAddedForNonCrossParameterConstraintException = "HV000146: No parameter nodes may be added since path %s doesn't refer to a cross-parameter constraint.";
   private static final String getConstrainedElementConfiguredMultipleTimesException = "HV000147: %1$s is configured multiple times (note, <getter> and <method> nodes for the same method are not allowed)";
   private static final String evaluatingExpressionLanguageExpressionCausedException = "HV000148: An exception occurred during evaluation of EL expression '%s'";
   private static final String getExceptionOccurredDuringMessageInterpolationException = "HV000149: An exception occurred during message interpolation";
   private static final String getMultipleValidatorsForSameTypeException = "HV000150: The constraint %1$s defines multiple validators for the type %2$s: %3$s, %4$s. Only one is allowed.";
   private static final String getParameterConfigurationAlteredInSubTypeException = "HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method %2$s redefines the configuration of %1$s.";
   private static final String getParameterConstraintsDefinedInMethodsFromParallelTypesException = "HV000152: Two methods defined in parallel types must not declare parameter constraints, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
   private static final String getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException = "HV000153: The constraint %1$s used ConstraintTarget#%2$s but is not specified on a method or constructor.";
   private static final String getCrossParameterConstraintHasNoValidatorException = "HV000154: Cross parameter constraint %1$s has no cross-parameter validator.";
   private static final String getComposedAndComposingConstraintsHaveDifferentTypesException = "HV000155: Composed and composing constraints must have the same constraint type, but composed constraint %1$s has type %3$s, while composing constraint %2$s has type %4$s.";
   private static final String getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException = "HV000156: Constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s doesn't.";
   private static final String getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException = "HV000157: Return type of the attribute validationAppliesTo() of the constraint %s must be javax.validation.ConstraintTarget.";
   private static final String getValidationAppliesToParameterMustHaveDefaultValueImplicitException = "HV000158: Default value of the attribute validationAppliesTo() of the constraint %s must be ConstraintTarget#IMPLICIT.";
   private static final String getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException = "HV000159: Only constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s does.";
   private static final String getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException = "HV000160: Validator for cross-parameter constraint %s does not validate Object nor Object[].";
   private static final String getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException = "HV000161: Two methods defined in parallel types must not define group conversions for a cascaded method return value, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
   private static final String getMethodOrConstructorNotDefinedByValidatedTypeException = "HV000162: The validated type %1$s does not specify the constructor/method: %2$s";
   private static final String getParameterTypesDoNotMatchException = "HV000163: The actual parameter type '%1$s' is not assignable to the expected one '%2$s' for parameter %3$d of '%4$s'";
   private static final String getHasToBeABoxedTypeException = "HV000164: %s has to be a auto-boxed type.";
   private static final String getMixingImplicitWithOtherExecutableTypesException = "HV000165: Mixing IMPLICIT and other executable types is not allowed.";
   private static final String getValidateOnExecutionOnOverriddenOrInterfaceMethodException = "HV000166: @ValidateOnExecution is not allowed on methods overriding a superclass method or implementing an interface. Check configuration for %1$s";
   private static final String getOverridingConstraintDefinitionsInMultipleMappingFilesException = "HV000167: A given constraint definition can only be overridden in one mapping file. %1$s is overridden in multiple files";
   private static final String getNonTerminatedParameterException = "HV000168: The message descriptor '%1$s' contains an unbalanced meta character '%2$c' parameter.";
   private static final String getNestedParameterException = "HV000169: The message descriptor '%1$s' has nested parameters.";
   private static final String getCreationOfScriptExecutorFailedException = "HV000170: No JSR-223 scripting engine could be bootstrapped for language \"%s\".";
   private static final String getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000171: %s is configured more than once via the programmatic constraint declaration API.";
   private static final String getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000172: Property \"%2$s\" of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getMethodHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000173: Method %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getParameterHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000174: Parameter %3$s of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000175: The return value of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000176: Constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException = "HV000177: Cross-parameter constraints for the method or constructor %2$s of type %1$s are declared more than once via the programmatic constraint declaration API.";
   private static final String getMultiplierCannotBeNegativeException = "HV000178: Multiplier cannot be negative: %d.";
   private static final String getWeightCannotBeNegativeException = "HV000179: Weight cannot be negative: %d.";
   private static final String getTreatCheckAsIsNotADigitNorALetterException = "HV000180: '%c' is not a digit nor a letter.";
   private static final String getInvalidParameterCountForExecutableException = "HV000181: Wrong number of parameters. Method or constructor %1$s expects %2$d parameters, but got %3$d.";
   private static final String getNoUnwrapperFoundForTypeException = "HV000182: No validation value unwrapper is registered for type '%1$s'.";
   private static final String getUnableToInitializeELExpressionFactoryException = "HV000183: Unable to initialize 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath, or use ParameterMessageInterpolator instead";
   private static final String warnElIsUnsupported = "HV000185: Message contains EL expression: %1s, which is not supported by the selected message interpolator";
   private static final String getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException = "HV000189: The configuration of value unwrapping for property '%s' of bean '%s' is inconsistent between the field and its getter.";
   private static final String getUnableToCreateXMLEventReader = "HV000190: Unable to parse %s.";
   private static final String unknownJvmVersion = "HV000192: Couldn't determine Java version from value %1s; Not enabling features requiring Java 8";
   private static final String getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException = "HV000193: %s is configured more than once via the programmatic constraint definition API.";
   private static final String getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection = "HV000194: An empty element is only supported when a CharSequence is expected.";
   private static final String getUnableToReachPropertyToValidateException = "HV000195: Unable to reach the property to validate for the bean %s and the property path %s. A property is null along the way.";
   private static final String getUnableToConvertTypeToClassException = "HV000196: Unable to convert the Type %s to a Class.";
   private static final String getNoValueExtractorFoundForTypeException2 = "HV000197: No value extractor found for type parameter '%2$s' of type %1$s.";
   private static final String getNoValueExtractorFoundForUnwrapException = "HV000198: No suitable value extractor found for type %1$s.";
   private static final String usingClockProvider = "HV000200: Using %s as clock provider.";
   private static final String getUnableToInstantiateClockProviderClassException = "HV000201: Unable to instantiate clock provider class %s.";
   private static final String getUnableToGetCurrentTimeFromClockProvider = "HV000202: Unable to get the current time from the clock provider";
   private static final String getValueExtractorFailsToDeclareExtractedValueException = "HV000203: Value extractor type %1s fails to declare the extracted type parameter using @ExtractedValue.";
   private static final String getValueExtractorDeclaresExtractedValueMultipleTimesException = "HV000204: Only one type parameter must be marked with @ExtractedValue for value extractor type %1s.";
   private static final String getInvalidUnwrappingConfigurationForConstraintException = "HV000205: Invalid unwrapping configuration for constraint %2$s on %1$s. You can only define one of 'Unwrapping.Skip' or 'Unwrapping.Unwrap'.";
   private static final String getUnableToInstantiateValueExtractorClassException = "HV000206: Unable to instantiate value extractor class %s.";
   private static final String addingValueExtractor = "HV000207: Adding value extractor %s.";
   private static final String getValueExtractorForTypeAndTypeUseAlreadyPresentException = "HV000208: Given value extractor %2$s handles the same type and type use as previously given value extractor %1$s.";
   private static final String getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException = "HV000209: A composing constraint (%2$s) must not be given directly on the composed constraint (%1$s) and using the corresponding List annotation at the same time.";
   private static final String getUnableToFindTypeParameterInClass = "HV000210: Unable to find the type parameter %2$s in class %1$s.";
   private static final String getTypeIsNotAParameterizedNorArrayTypeException = "HV000211: Given type is neither a parameterized nor an array type: %s.";
   private static final String getInvalidTypeArgumentIndexException = "HV000212: Given type has no type argument with index %2$s: %1$s.";
   private static final String getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException = "HV000213: Given type has more than one type argument, hence an argument index must be specified: %s.";
   private static final String getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException = "HV000214: The same container element type of type %1$s is configured more than once via the programmatic constraint declaration API.";
   private static final String getParameterIsNotAValidCallException = "HV000215: Calling parameter() is not allowed for the current element.";
   private static final String getReturnValueIsNotAValidCallException = "HV000216: Calling returnValue() is not allowed for the current element.";
   private static final String getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException = "HV000217: The same container element type %2$s is configured more than once for location %1$s via the XML mapping configuration.";
   private static final String getParallelDefinitionsOfValueExtractorsException = "HV000218: Having parallel definitions of value extractors on a given class is not allowed: %s.";
   private static final String getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException = "HV000219: Unable to get the most specific value extractor for type %1$s as several most specific value extractors are declared: %2$s.";
   private static final String getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException = "HV000220: When @ExtractedValue is defined on a type parameter of a container type, the type attribute may not be set: %1$s.";
   private static final String getErrorWhileExtractingValuesInValueExtractorException = "HV000221: An error occurred while extracting values in value extractor %1$s.";
   private static final String getDuplicateDefinitionsOfValueExtractorException = "HV000222: The same value extractor %s is added more than once via the XML configuration.";
   private static final String getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException = "HV000223: Implicit unwrapping is not allowed for type %1$s as several maximally specific value extractors marked with @UnwrapByDefault are declared: %2$s.";
   private static final String getUnwrappingOfConstraintDescriptorNotSupportedYetException = "HV000224: Unwrapping of ConstraintDescriptor is not supported yet.";
   private static final String getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException = "HV000225: Only unbound wildcard type arguments are supported for the container type of the value extractor: %1$s.";
   private static final String getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException = "HV000226: Container element constraints and cascading validation are not supported on arrays: %1$s";
   private static final String getPropertyNotDefinedByValidatedTypeException = "HV000227: The validated type %1$s does not specify the property: %2$s";
   private static final String getNoValueExtractorFoundForTypeException3 = "HV000228: No value extractor found when narrowing down to the runtime type %3$s among the value extractors for type parameter '%2$s' of type %1$s.";
   private static final String getUnableToCastException = "HV000229: Unable to cast %1$s to %2$s.";
   private static final String usingScriptEvaluatorFactory = "HV000230: Using %s as script evaluator factory.";
   private static final String getUnableToInstantiateScriptEvaluatorFactoryClassException = "HV000231: Unable to instantiate script evaluator factory class %s.";
   private static final String getUnableToFindScriptEngineException = "HV000232: No JSR 223 script engine found for language \"%s\".";
   private static final String getErrorExecutingScriptException = "HV000233: An error occurred while executing the script: \"%s\".";
   private static final String logValidatorFactoryScopedConfiguration = "HV000234: Using %1$s as ValidatorFactory-scoped %2$s.";
   private static final String getUnableToCreateAnnotationDescriptor = "HV000235: Unable to create an annotation descriptor for %1$s.";
   private static final String getUnableToFindAnnotationDefDeclaredMethods = "HV000236: Unable to find the method required to create the constraint annotation descriptor.";
   private static final String getUnableToAccessMethodException = "HV000237: Unable to access method %3$s of class %2$s with parameters %4$s using lookup %1$s.";
   private static final String logTemporalValidationTolerance = "HV000238: Temporal validation tolerance set to %1$s.";
   private static final String getUnableToParseTemporalValidationToleranceException = "HV000239: Unable to parse the temporal validation tolerance property %s. It should be a duration represented in milliseconds.";
   private static final String logConstraintValidatorPayload = "HV000240: Constraint validator payload set to %1$s.";
   private static final String logUnknownElementInXmlConfiguration = "HV000241: Encountered unsupported element %1$s while parsing the XML configuration.";
   private static final String logUnableToLoadOrInstantiateJPAAwareResolver = "HV000242: Unable to load or instantiate JPA aware resolver %1$s. All properties will per default be traversable.";
   private static final String getConstraintValidatorDefinitionConstraintMismatchException = "HV000243: Constraint %2$s references constraint validator type %1$s, but this validator is defined for constraint type %3$s.";
   private static final String unableToGetXmlSchema = "HV000248: Unable to get an XML schema named %s.";

   public Log_$logger(Logger log) {
      super(log);
   }

   protected Locale getLoggingLocale() {
      return LOCALE;
   }

   public final void version(String version) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.version$str(), version);
   }

   protected String version$str() {
      return "HV000001: Hibernate Validator %s";
   }

   public final void ignoringXmlConfiguration() {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.ignoringXmlConfiguration$str(), new Object[0]);
   }

   protected String ignoringXmlConfiguration$str() {
      return "HV000002: Ignoring XML configuration.";
   }

   public final void usingConstraintValidatorFactory(Class constraintValidatorFactoryClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingConstraintValidatorFactory$str(), new ClassObjectFormatter(constraintValidatorFactoryClass));
   }

   protected String usingConstraintValidatorFactory$str() {
      return "HV000003: Using %s as constraint validator factory.";
   }

   public final void usingMessageInterpolator(Class messageInterpolatorClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingMessageInterpolator$str(), new ClassObjectFormatter(messageInterpolatorClass));
   }

   protected String usingMessageInterpolator$str() {
      return "HV000004: Using %s as message interpolator.";
   }

   public final void usingTraversableResolver(Class traversableResolverClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingTraversableResolver$str(), new ClassObjectFormatter(traversableResolverClass));
   }

   protected String usingTraversableResolver$str() {
      return "HV000005: Using %s as traversable resolver.";
   }

   public final void usingValidationProvider(Class validationProviderClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingValidationProvider$str(), new ClassObjectFormatter(validationProviderClass));
   }

   protected String usingValidationProvider$str() {
      return "HV000006: Using %s as validation provider.";
   }

   public final void parsingXMLFile(String fileName) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.parsingXMLFile$str(), fileName);
   }

   protected String parsingXMLFile$str() {
      return "HV000007: %s found. Parsing XML based configuration.";
   }

   public final void unableToCloseInputStream() {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.unableToCloseInputStream$str(), new Object[0]);
   }

   protected String unableToCloseInputStream$str() {
      return "HV000008: Unable to close input stream.";
   }

   public final void unableToCloseXMLFileInputStream(String fileName) {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.unableToCloseXMLFileInputStream$str(), fileName);
   }

   protected String unableToCloseXMLFileInputStream$str() {
      return "HV000010: Unable to close input stream for %s.";
   }

   public final void unableToCreateSchema(String fileName, String message) {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.unableToCreateSchema$str(), fileName, message);
   }

   protected String unableToCreateSchema$str() {
      return "HV000011: Unable to create schema for %1$s: %2$s";
   }

   protected String getUnableToCreateAnnotationForConfiguredConstraintException$str() {
      return "HV000012: Unable to create annotation for configured constraint";
   }

   public final ValidationException getUnableToCreateAnnotationForConfiguredConstraintException(RuntimeException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateAnnotationForConfiguredConstraintException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindPropertyWithAccessException$str() {
      return "HV000013: The class %1$s does not have a property '%2$s' with access %3$s.";
   }

   public final ValidationException getUnableToFindPropertyWithAccessException(Class beanClass, String property, ElementType elementType) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindPropertyWithAccessException$str(), new ClassObjectFormatter(beanClass), property, elementType));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidBigDecimalFormatException$str() {
      return "HV000016: %s does not represent a valid BigDecimal format.";
   }

   public final IllegalArgumentException getInvalidBigDecimalFormatException(String value, NumberFormatException e) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidBigDecimalFormatException$str(), value), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidLengthForIntegerPartException$str() {
      return "HV000017: The length of the integer part cannot be negative.";
   }

   public final IllegalArgumentException getInvalidLengthForIntegerPartException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthForIntegerPartException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidLengthForFractionPartException$str() {
      return "HV000018: The length of the fraction part cannot be negative.";
   }

   public final IllegalArgumentException getInvalidLengthForFractionPartException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthForFractionPartException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMinCannotBeNegativeException$str() {
      return "HV000019: The min parameter cannot be negative.";
   }

   public final IllegalArgumentException getMinCannotBeNegativeException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMinCannotBeNegativeException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMaxCannotBeNegativeException$str() {
      return "HV000020: The max parameter cannot be negative.";
   }

   public final IllegalArgumentException getMaxCannotBeNegativeException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMaxCannotBeNegativeException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getLengthCannotBeNegativeException$str() {
      return "HV000021: The length cannot be negative.";
   }

   public final IllegalArgumentException getLengthCannotBeNegativeException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getLengthCannotBeNegativeException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidRegularExpressionException$str() {
      return "HV000022: Invalid regular expression.";
   }

   public final IllegalArgumentException getInvalidRegularExpressionException(PatternSyntaxException e) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidRegularExpressionException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorDuringScriptExecutionException$str() {
      return "HV000023: Error during execution of script \"%s\" occurred.";
   }

   public final ConstraintDeclarationException getErrorDuringScriptExecutionException(String script, Exception e) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getErrorDuringScriptExecutionException$str(), script), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getScriptMustReturnTrueOrFalseException1$str() {
      return "HV000024: Script \"%s\" returned null, but must return either true or false.";
   }

   public final ConstraintDeclarationException getScriptMustReturnTrueOrFalseException(String script) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getScriptMustReturnTrueOrFalseException1$str(), script));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getScriptMustReturnTrueOrFalseException3$str() {
      return "HV000025: Script \"%1$s\" returned %2$s (of type %3$s), but must return either true or false.";
   }

   public final ConstraintDeclarationException getScriptMustReturnTrueOrFalseException(String script, Object executionResult, String type) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getScriptMustReturnTrueOrFalseException3$str(), script, executionResult, type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInconsistentConfigurationException$str() {
      return "HV000026: Assertion error: inconsistent ConfigurationImpl construction.";
   }

   public final ValidationException getInconsistentConfigurationException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInconsistentConfigurationException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindProviderException$str() {
      return "HV000027: Unable to find provider: %s.";
   }

   public final ValidationException getUnableToFindProviderException(Class providerClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindProviderException$str(), new ClassObjectFormatter(providerClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getExceptionDuringIsValidCallException$str() {
      return "HV000028: Unexpected exception during isValid call.";
   }

   public final ValidationException getExceptionDuringIsValidCallException(RuntimeException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getExceptionDuringIsValidCallException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstraintValidatorFactoryMustNotReturnNullException$str() {
      return "HV000029: Constraint factory returned null when trying to create instance of %s.";
   }

   public final ValidationException getConstraintValidatorFactoryMustNotReturnNullException(Class validatorClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstraintValidatorFactoryMustNotReturnNullException$str(), new ClassObjectFormatter(validatorClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoValidatorFoundForTypeException$str() {
      return "HV000030: No validator could be found for constraint '%s' validating type '%s'. Check configuration for '%s'";
   }

   public final UnexpectedTypeException getNoValidatorFoundForTypeException(Class constraintType, String validatedValueType, String path) {
      UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getNoValidatorFoundForTypeException$str(), new ClassObjectFormatter(constraintType), validatedValueType, path));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMoreThanOneValidatorFoundForTypeException$str() {
      return "HV000031: There are multiple validator classes which could validate the type %1$s. The validator classes are: %2$s.";
   }

   public final UnexpectedTypeException getMoreThanOneValidatorFoundForTypeException(Type type, Collection validatorClasses) {
      UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getMoreThanOneValidatorFoundForTypeException$str(), type, new CollectionOfObjectsToStringFormatter(validatorClasses)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInitializeConstraintValidatorException$str() {
      return "HV000032: Unable to initialize %s.";
   }

   public final ValidationException getUnableToInitializeConstraintValidatorException(Class validatorClass, RuntimeException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInitializeConstraintValidatorException$str(), new ClassObjectFormatter(validatorClass)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getAtLeastOneCustomMessageMustBeCreatedException$str() {
      return "HV000033: At least one custom message must be created if the default error message gets disabled.";
   }

   public final ValidationException getAtLeastOneCustomMessageMustBeCreatedException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAtLeastOneCustomMessageMustBeCreatedException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidJavaIdentifierException$str() {
      return "HV000034: %s is not a valid Java Identifier.";
   }

   public final IllegalArgumentException getInvalidJavaIdentifierException(String identifier) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidJavaIdentifierException$str(), identifier));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToParsePropertyPathException$str() {
      return "HV000035: Unable to parse property path %s.";
   }

   public final IllegalArgumentException getUnableToParsePropertyPathException(String propertyPath) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getUnableToParsePropertyPathException$str(), propertyPath));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getTypeNotSupportedForUnwrappingException$str() {
      return "HV000036: Type %s not supported for unwrapping.";
   }

   public final ValidationException getTypeNotSupportedForUnwrappingException(Class type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getTypeNotSupportedForUnwrappingException$str(), new ClassObjectFormatter(type)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInconsistentFailFastConfigurationException$str() {
      return "HV000037: Inconsistent fail fast configuration. Fail fast enabled via programmatic API, but explicitly disabled via properties.";
   }

   public final ValidationException getInconsistentFailFastConfigurationException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInconsistentFailFastConfigurationException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidPropertyPathException0$str() {
      return "HV000038: Invalid property path.";
   }

   public final IllegalArgumentException getInvalidPropertyPathException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidPropertyPathException0$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidPropertyPathException2$str() {
      return "HV000039: Invalid property path. Either there is no property %2$s in entity %1$s or it is not possible to cascade to the property.";
   }

   public final IllegalArgumentException getInvalidPropertyPathException(Class beanClass, String propertyName) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidPropertyPathException2$str(), new ClassObjectFormatter(beanClass), propertyName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getPropertyPathMustProvideIndexOrMapKeyException$str() {
      return "HV000040: Property path must provide index or map key.";
   }

   public final IllegalArgumentException getPropertyPathMustProvideIndexOrMapKeyException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyPathMustProvideIndexOrMapKeyException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorDuringCallOfTraversableResolverIsReachableException$str() {
      return "HV000041: Call to TraversableResolver.isReachable() threw an exception.";
   }

   public final ValidationException getErrorDuringCallOfTraversableResolverIsReachableException(RuntimeException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorDuringCallOfTraversableResolverIsReachableException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorDuringCallOfTraversableResolverIsCascadableException$str() {
      return "HV000042: Call to TraversableResolver.isCascadable() threw an exception.";
   }

   public final ValidationException getErrorDuringCallOfTraversableResolverIsCascadableException(RuntimeException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorDuringCallOfTraversableResolverIsCascadableException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToExpandDefaultGroupListException$str() {
      return "HV000043: Unable to expand default group list %1$s into sequence %2$s.";
   }

   public final GroupDefinitionException getUnableToExpandDefaultGroupListException(List defaultGroupList, List groupList) {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getUnableToExpandDefaultGroupListException$str(), new CollectionOfObjectsToStringFormatter(defaultGroupList), new CollectionOfObjectsToStringFormatter(groupList)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getAtLeastOneGroupHasToBeSpecifiedException$str() {
      return "HV000044: At least one group has to be specified.";
   }

   public final IllegalArgumentException getAtLeastOneGroupHasToBeSpecifiedException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getAtLeastOneGroupHasToBeSpecifiedException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getGroupHasToBeAnInterfaceException$str() {
      return "HV000045: A group has to be an interface. %s is not.";
   }

   public final ValidationException getGroupHasToBeAnInterfaceException(Class clazz) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getGroupHasToBeAnInterfaceException$str(), new ClassObjectFormatter(clazz)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getSequenceDefinitionsNotAllowedException$str() {
      return "HV000046: Sequence definitions are not allowed as composing parts of a sequence.";
   }

   public final GroupDefinitionException getSequenceDefinitionsNotAllowedException() {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getSequenceDefinitionsNotAllowedException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCyclicDependencyInGroupsDefinitionException$str() {
      return "HV000047: Cyclic dependency in groups definition";
   }

   public final GroupDefinitionException getCyclicDependencyInGroupsDefinitionException() {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getCyclicDependencyInGroupsDefinitionException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToExpandGroupSequenceException$str() {
      return "HV000048: Unable to expand group sequence.";
   }

   public final GroupDefinitionException getUnableToExpandGroupSequenceException() {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getUnableToExpandGroupSequenceException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidDefaultGroupSequenceDefinitionException$str() {
      return "HV000052: Default group sequence and default group sequence provider cannot be defined at the same time.";
   }

   public final GroupDefinitionException getInvalidDefaultGroupSequenceDefinitionException() {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getInvalidDefaultGroupSequenceDefinitionException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoDefaultGroupInGroupSequenceException$str() {
      return "HV000053: 'Default.class' cannot appear in default group sequence list.";
   }

   public final GroupDefinitionException getNoDefaultGroupInGroupSequenceException() {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getNoDefaultGroupInGroupSequenceException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException$str() {
      return "HV000054: %s must be part of the redefined default group sequence.";
   }

   public final GroupDefinitionException getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException(Class beanClass) {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getBeanClassMustBePartOfRedefinedDefaultGroupSequenceException$str(), new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongDefaultGroupSequenceProviderTypeException$str() {
      return "HV000055: The default group sequence provider defined for %s has the wrong type";
   }

   public final GroupDefinitionException getWrongDefaultGroupSequenceProviderTypeException(Class beanClass) {
      GroupDefinitionException result = new GroupDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultGroupSequenceProviderTypeException$str(), new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidExecutableParameterIndexException$str() {
      return "HV000056: Method or constructor %1$s doesn't have a parameter with index %2$d.";
   }

   public final IllegalArgumentException getInvalidExecutableParameterIndexException(Executable executable, int index) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidExecutableParameterIndexException$str(), new ExecutableFormatter(executable), index));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToRetrieveAnnotationParameterValueException$str() {
      return "HV000059: Unable to retrieve annotation parameter value.";
   }

   public final ValidationException getUnableToRetrieveAnnotationParameterValueException(Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToRetrieveAnnotationParameterValueException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidLengthOfParameterMetaDataListException$str() {
      return "HV000062: Method or constructor %1$s has %2$s parameters, but the passed list of parameter meta data has a size of %3$s.";
   }

   public final IllegalArgumentException getInvalidLengthOfParameterMetaDataListException(Executable executable, int nbParameters, int listSize) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidLengthOfParameterMetaDataListException$str(), new ExecutableFormatter(executable), nbParameters, listSize));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateException1$str() {
      return "HV000063: Unable to instantiate %s.";
   }

   public final ValidationException getUnableToInstantiateException(Class clazz, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateException1$str(), new ClassObjectFormatter(clazz)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateException2$str() {
      return "HV000064: Unable to instantiate %1$s: %2$s.";
   }

   public final ValidationException getUnableToInstantiateException(String message, Class clazz, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateException2$str(), message, new ClassObjectFormatter(clazz)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToLoadClassException$str() {
      return "HV000065: Unable to load class: %s from %s.";
   }

   public final ValidationException getUnableToLoadClassException(String className, ClassLoader loader, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToLoadClassException$str(), className, loader), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getStartIndexCannotBeNegativeException$str() {
      return "HV000068: Start index cannot be negative: %d.";
   }

   public final IllegalArgumentException getStartIndexCannotBeNegativeException(int startIndex) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getStartIndexCannotBeNegativeException$str(), startIndex));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getEndIndexCannotBeNegativeException$str() {
      return "HV000069: End index cannot be negative: %d.";
   }

   public final IllegalArgumentException getEndIndexCannotBeNegativeException(int endIndex) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getEndIndexCannotBeNegativeException$str(), endIndex));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidRangeException$str() {
      return "HV000070: Invalid Range: %1$d > %2$d.";
   }

   public final IllegalArgumentException getInvalidRangeException(int startIndex, int endIndex) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidRangeException$str(), startIndex, endIndex));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidCheckDigitException$str() {
      return "HV000071: A explicitly specified check digit must lie outside the interval: [%1$d, %2$d].";
   }

   public final IllegalArgumentException getInvalidCheckDigitException(int startIndex, int endIndex) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidCheckDigitException$str(), startIndex, endIndex));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCharacterIsNotADigitException$str() {
      return "HV000072: '%c' is not a digit.";
   }

   public final NumberFormatException getCharacterIsNotADigitException(char c) {
      NumberFormatException result = new NumberFormatException(String.format(this.getLoggingLocale(), this.getCharacterIsNotADigitException$str(), c));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstraintParametersCannotStartWithValidException$str() {
      return "HV000073: Parameters starting with 'valid' are not allowed in a constraint.";
   }

   public final ConstraintDefinitionException getConstraintParametersCannotStartWithValidException() {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintParametersCannotStartWithValidException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstraintWithoutMandatoryParameterException$str() {
      return "HV000074: %2$s contains Constraint annotation, but does not contain a %1$s parameter.";
   }

   public final ConstraintDefinitionException getConstraintWithoutMandatoryParameterException(String parameterName, String constraintName) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintWithoutMandatoryParameterException$str(), parameterName, constraintName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongDefaultValueForPayloadParameterException$str() {
      return "HV000075: %s contains Constraint annotation, but the payload parameter default value is not the empty array.";
   }

   public final ConstraintDefinitionException getWrongDefaultValueForPayloadParameterException(String constraintName) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultValueForPayloadParameterException$str(), constraintName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongTypeForPayloadParameterException$str() {
      return "HV000076: %s contains Constraint annotation, but the payload parameter is of wrong type.";
   }

   public final ConstraintDefinitionException getWrongTypeForPayloadParameterException(String constraintName, ClassCastException e) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForPayloadParameterException$str(), constraintName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongDefaultValueForGroupsParameterException$str() {
      return "HV000077: %s contains Constraint annotation, but the groups parameter default value is not the empty array.";
   }

   public final ConstraintDefinitionException getWrongDefaultValueForGroupsParameterException(String constraintName) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongDefaultValueForGroupsParameterException$str(), constraintName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongTypeForGroupsParameterException$str() {
      return "HV000078: %s contains Constraint annotation, but the groups parameter is of wrong type.";
   }

   public final ConstraintDefinitionException getWrongTypeForGroupsParameterException(String constraintName, ClassCastException e) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForGroupsParameterException$str(), constraintName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongTypeForMessageParameterException$str() {
      return "HV000079: %s contains Constraint annotation, but the message parameter is not of type java.lang.String.";
   }

   public final ConstraintDefinitionException getWrongTypeForMessageParameterException(String constraintName) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongTypeForMessageParameterException$str(), constraintName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getOverriddenConstraintAttributeNotFoundException$str() {
      return "HV000080: Overridden constraint does not define an attribute with name %s.";
   }

   public final ConstraintDefinitionException getOverriddenConstraintAttributeNotFoundException(String attributeName) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getOverriddenConstraintAttributeNotFoundException$str(), attributeName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongAttributeTypeForOverriddenConstraintException$str() {
      return "HV000081: The overriding type of a composite constraint must be identical to the overridden one. Expected %1$s found %2$s.";
   }

   public final ConstraintDefinitionException getWrongAttributeTypeForOverriddenConstraintException(Class expectedReturnType, Class currentReturnType) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getWrongAttributeTypeForOverriddenConstraintException$str(), new ClassObjectFormatter(expectedReturnType), new ClassObjectFormatter(currentReturnType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongAnnotationAttributeTypeException$str() {
      return "HV000082: Wrong type for attribute '%2$s' of annotation %1$s. Expected: %3$s. Actual: %4$s.";
   }

   public final ValidationException getWrongAnnotationAttributeTypeException(Class annotationClass, String attributeName, Class expectedType, Class currentType) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getWrongAnnotationAttributeTypeException$str(), new ClassObjectFormatter(annotationClass), attributeName, new ClassObjectFormatter(expectedType), new ClassObjectFormatter(currentType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindAnnotationAttributeException$str() {
      return "HV000083: The specified annotation %1$s defines no attribute '%2$s'.";
   }

   public final ValidationException getUnableToFindAnnotationAttributeException(Class annotationClass, String parameterName, NoSuchMethodException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindAnnotationAttributeException$str(), new ClassObjectFormatter(annotationClass), parameterName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToGetAnnotationAttributeException$str() {
      return "HV000084: Unable to get attribute '%2$s' from annotation %1$s.";
   }

   public final ValidationException getUnableToGetAnnotationAttributeException(Class annotationClass, String parameterName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToGetAnnotationAttributeException$str(), new ClassObjectFormatter(annotationClass), parameterName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoValueProvidedForAnnotationAttributeException$str() {
      return "HV000085: No value provided for attribute '%1$s' of annotation @%2$s.";
   }

   public final IllegalArgumentException getNoValueProvidedForAnnotationAttributeException(String parameterName, Class annotation) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getNoValueProvidedForAnnotationAttributeException$str(), parameterName, new ClassObjectFormatter(annotation)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getTryingToInstantiateAnnotationWithUnknownAttributesException$str() {
      return "HV000086: Trying to instantiate annotation %1$s with unknown attribute(s): %2$s.";
   }

   public final RuntimeException getTryingToInstantiateAnnotationWithUnknownAttributesException(Class annotationType, Set unknownParameters) {
      RuntimeException result = new RuntimeException(String.format(this.getLoggingLocale(), this.getTryingToInstantiateAnnotationWithUnknownAttributesException$str(), new ClassObjectFormatter(annotationType), unknownParameters));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getPropertyNameCannotBeNullOrEmptyException$str() {
      return "HV000087: Property name cannot be null or empty.";
   }

   public final IllegalArgumentException getPropertyNameCannotBeNullOrEmptyException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyNameCannotBeNullOrEmptyException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getElementTypeHasToBeFieldOrMethodException$str() {
      return "HV000088: Element type has to be FIELD or METHOD.";
   }

   public final IllegalArgumentException getElementTypeHasToBeFieldOrMethodException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getElementTypeHasToBeFieldOrMethodException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMemberIsNeitherAFieldNorAMethodException$str() {
      return "HV000089: Member %s is neither a field nor a method.";
   }

   public final IllegalArgumentException getMemberIsNeitherAFieldNorAMethodException(Member member) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMemberIsNeitherAFieldNorAMethodException$str(), member));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToAccessMemberException$str() {
      return "HV000090: Unable to access %s.";
   }

   public final ValidationException getUnableToAccessMemberException(String memberName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToAccessMemberException$str(), memberName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getHasToBeAPrimitiveTypeException$str() {
      return "HV000091: %s has to be a primitive type.";
   }

   public final IllegalArgumentException getHasToBeAPrimitiveTypeException(Class clazz) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getHasToBeAPrimitiveTypeException$str(), new ClassObjectFormatter(clazz)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNullIsAnInvalidTypeForAConstraintValidatorException$str() {
      return "HV000093: null is an invalid type for a constraint validator.";
   }

   public final ValidationException getNullIsAnInvalidTypeForAConstraintValidatorException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNullIsAnInvalidTypeForAConstraintValidatorException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMissingActualTypeArgumentForTypeParameterException$str() {
      return "HV000094: Missing actual type argument for type parameter: %s.";
   }

   public final IllegalArgumentException getMissingActualTypeArgumentForTypeParameterException(TypeVariable typeParameter) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMissingActualTypeArgumentForTypeParameterException$str(), typeParameter));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateConstraintValidatorFactoryClassException$str() {
      return "HV000095: Unable to instantiate constraint factory class %s.";
   }

   public final ValidationException getUnableToInstantiateConstraintValidatorFactoryClassException(String constraintValidatorFactoryClassName, ValidationException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateConstraintValidatorFactoryClassException$str(), constraintValidatorFactoryClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToOpenInputStreamForMappingFileException$str() {
      return "HV000096: Unable to open input stream for mapping file %s.";
   }

   public final ValidationException getUnableToOpenInputStreamForMappingFileException(String mappingFileName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToOpenInputStreamForMappingFileException$str(), mappingFileName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateMessageInterpolatorClassException$str() {
      return "HV000097: Unable to instantiate message interpolator class %s.";
   }

   public final ValidationException getUnableToInstantiateMessageInterpolatorClassException(String messageInterpolatorClassName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateMessageInterpolatorClassException$str(), messageInterpolatorClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateTraversableResolverClassException$str() {
      return "HV000098: Unable to instantiate traversable resolver class %s.";
   }

   public final ValidationException getUnableToInstantiateTraversableResolverClassException(String traversableResolverClassName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateTraversableResolverClassException$str(), traversableResolverClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateValidationProviderClassException$str() {
      return "HV000099: Unable to instantiate validation provider class %s.";
   }

   public final ValidationException getUnableToInstantiateValidationProviderClassException(String providerClassName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateValidationProviderClassException$str(), providerClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToParseValidationXmlFileException$str() {
      return "HV000100: Unable to parse %s.";
   }

   public final ValidationException getUnableToParseValidationXmlFileException(String file, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToParseValidationXmlFileException$str(), file), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getIsNotAnAnnotationException$str() {
      return "HV000101: %s is not an annotation.";
   }

   public final ValidationException getIsNotAnAnnotationException(Class annotationClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsNotAnAnnotationException$str(), new ClassObjectFormatter(annotationClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getIsNotAConstraintValidatorClassException$str() {
      return "HV000102: %s is not a constraint validator class.";
   }

   public final ValidationException getIsNotAConstraintValidatorClassException(Class validatorClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsNotAConstraintValidatorClassException$str(), new ClassObjectFormatter(validatorClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanClassHasAlreadyBeenConfiguredInXmlException$str() {
      return "HV000103: %s is configured at least twice in xml.";
   }

   public final ValidationException getBeanClassHasAlreadyBeenConfiguredInXmlException(Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanClassHasAlreadyBeenConfiguredInXmlException$str(), new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getIsDefinedTwiceInMappingXmlForBeanException$str() {
      return "HV000104: %1$s is defined twice in mapping xml for bean %2$s.";
   }

   public final ValidationException getIsDefinedTwiceInMappingXmlForBeanException(String name, Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getIsDefinedTwiceInMappingXmlForBeanException$str(), name, new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanDoesNotContainTheFieldException$str() {
      return "HV000105: %1$s does not contain the fieldType %2$s.";
   }

   public final ValidationException getBeanDoesNotContainTheFieldException(Class beanClass, String fieldName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainTheFieldException$str(), new ClassObjectFormatter(beanClass), fieldName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanDoesNotContainThePropertyException$str() {
      return "HV000106: %1$s does not contain the property %2$s.";
   }

   public final ValidationException getBeanDoesNotContainThePropertyException(Class beanClass, String getterName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainThePropertyException$str(), new ClassObjectFormatter(beanClass), getterName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getAnnotationDoesNotContainAParameterException$str() {
      return "HV000107: Annotation of type %1$s does not contain a parameter %2$s.";
   }

   public final ValidationException getAnnotationDoesNotContainAParameterException(Class annotationClass, String parameterName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAnnotationDoesNotContainAParameterException$str(), new ClassObjectFormatter(annotationClass), parameterName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException$str() {
      return "HV000108: Attempt to specify an array where single value is expected.";
   }

   public final ValidationException getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnexpectedParameterValueException$str() {
      return "HV000109: Unexpected parameter value.";
   }

   public final ValidationException getUnexpectedParameterValueException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnexpectedParameterValueException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final ValidationException getUnexpectedParameterValueException(ClassCastException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnexpectedParameterValueException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidNumberFormatException$str() {
      return "HV000110: Invalid %s format.";
   }

   public final ValidationException getInvalidNumberFormatException(String formatName, NumberFormatException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidNumberFormatException$str(), formatName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidCharValueException$str() {
      return "HV000111: Invalid char value: %s.";
   }

   public final ValidationException getInvalidCharValueException(String value) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidCharValueException$str(), value));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidReturnTypeException$str() {
      return "HV000112: Invalid return type: %s. Should be a enumeration type.";
   }

   public final ValidationException getInvalidReturnTypeException(Class returnType, ClassCastException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidReturnTypeException$str(), new ClassObjectFormatter(returnType)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getReservedParameterNamesException$str() {
      return "HV000113: %s, %s, %s are reserved parameter names.";
   }

   public final ValidationException getReservedParameterNamesException(String messageParameterName, String groupsParameterName, String payloadParameterName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReservedParameterNamesException$str(), messageParameterName, groupsParameterName, payloadParameterName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWrongPayloadClassException$str() {
      return "HV000114: Specified payload class %s does not implement javax.validation.Payload";
   }

   public final ValidationException getWrongPayloadClassException(Class payloadClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getWrongPayloadClassException$str(), new ClassObjectFormatter(payloadClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorParsingMappingFileException$str() {
      return "HV000115: Error parsing mapping file.";
   }

   public final ValidationException getErrorParsingMappingFileException(Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorParsingMappingFileException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getIllegalArgumentException$str() {
      return "HV000116: %s";
   }

   public final IllegalArgumentException getIllegalArgumentException(String message) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getIllegalArgumentException$str(), message));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToNarrowNodeTypeException$str() {
      return "HV000118: Unable to cast %s (with element kind %s) to %s";
   }

   public final ClassCastException getUnableToNarrowNodeTypeException(Class actualDescriptorType, ElementKind kind, Class expectedDescriptorType) {
      ClassCastException result = new ClassCastException(String.format(this.getLoggingLocale(), this.getUnableToNarrowNodeTypeException$str(), new ClassObjectFormatter(actualDescriptorType), kind, new ClassObjectFormatter(expectedDescriptorType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void usingParameterNameProvider(Class parameterNameProviderClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingParameterNameProvider$str(), new ClassObjectFormatter(parameterNameProviderClass));
   }

   protected String usingParameterNameProvider$str() {
      return "HV000119: Using %s as parameter name provider.";
   }

   protected String getUnableToInstantiateParameterNameProviderClassException$str() {
      return "HV000120: Unable to instantiate parameter name provider class %s.";
   }

   public final ValidationException getUnableToInstantiateParameterNameProviderClassException(String parameterNameProviderClassName, ValidationException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateParameterNameProviderClassException$str(), parameterNameProviderClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToDetermineSchemaVersionException$str() {
      return "HV000121: Unable to parse %s.";
   }

   public final ValidationException getUnableToDetermineSchemaVersionException(String file, XMLStreamException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToDetermineSchemaVersionException$str(), file), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnsupportedSchemaVersionException$str() {
      return "HV000122: Unsupported schema version for %s: %s.";
   }

   public final ValidationException getUnsupportedSchemaVersionException(String file, String version) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnsupportedSchemaVersionException$str(), file, version));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMultipleGroupConversionsForSameSourceException$str() {
      return "HV000124: Found multiple group conversions for source group %s: %s.";
   }

   public final ConstraintDeclarationException getMultipleGroupConversionsForSameSourceException(Class from, Collection tos) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMultipleGroupConversionsForSameSourceException$str(), new ClassObjectFormatter(from), new CollectionOfClassesObjectFormatter(tos)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getGroupConversionOnNonCascadingElementException$str() {
      return "HV000125: Found group conversions for non-cascading element at: %s.";
   }

   public final ConstraintDeclarationException getGroupConversionOnNonCascadingElementException(Object context) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getGroupConversionOnNonCascadingElementException$str(), context));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getGroupConversionForSequenceException$str() {
      return "HV000127: Found group conversion using a group sequence as source at: %s.";
   }

   public final ConstraintDeclarationException getGroupConversionForSequenceException(Class from) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getGroupConversionForSequenceException$str(), new ClassObjectFormatter(from)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unknownPropertyInExpressionLanguage(String expression, Exception e) {
      super.log.logf(FQCN, Level.WARN, e, this.unknownPropertyInExpressionLanguage$str(), expression);
   }

   protected String unknownPropertyInExpressionLanguage$str() {
      return "HV000129: EL expression '%s' references an unknown property";
   }

   public final void errorInExpressionLanguage(String expression, Exception e) {
      super.log.logf(FQCN, Level.WARN, e, this.errorInExpressionLanguage$str(), expression);
   }

   protected String errorInExpressionLanguage$str() {
      return "HV000130: Error in EL expression '%s'";
   }

   protected String getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException$str() {
      return "HV000131: A method return value must not be marked for cascaded validation more than once in a class hierarchy, but the following two methods are marked as such: %s, %s.";
   }

   public final ConstraintDeclarationException getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException(Executable executable1, Executable executable2) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException$str(), new ExecutableFormatter(executable1), new ExecutableFormatter(executable2)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getVoidMethodsMustNotBeConstrainedException$str() {
      return "HV000132: Void methods must not be constrained or marked for cascaded validation, but method %s is.";
   }

   public final ConstraintDeclarationException getVoidMethodsMustNotBeConstrainedException(Executable executable) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getVoidMethodsMustNotBeConstrainedException$str(), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanDoesNotContainConstructorException$str() {
      return "HV000133: %1$s does not contain a constructor with the parameter types %2$s.";
   }

   public final ValidationException getBeanDoesNotContainConstructorException(Class beanClass, Class[] parameterTypes) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainConstructorException$str(), new ClassObjectFormatter(beanClass), new ArrayOfClassesObjectFormatter(parameterTypes)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidParameterTypeException$str() {
      return "HV000134: Unable to load parameter of type '%1$s' in %2$s.";
   }

   public final ValidationException getInvalidParameterTypeException(String type, Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidParameterTypeException$str(), type, new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanDoesNotContainMethodException$str() {
      return "HV000135: %1$s does not contain a method with the name '%2$s' and parameter types %3$s.";
   }

   public final ValidationException getBeanDoesNotContainMethodException(Class beanClass, String methodName, Class[] parameterTypes) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanDoesNotContainMethodException$str(), new ClassObjectFormatter(beanClass), methodName, new ArrayOfClassesObjectFormatter(parameterTypes)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToLoadConstraintAnnotationClassException$str() {
      return "HV000136: The specified constraint annotation class %1$s cannot be loaded.";
   }

   public final ValidationException getUnableToLoadConstraintAnnotationClassException(String constraintAnnotationClassName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToLoadConstraintAnnotationClassException$str(), constraintAnnotationClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMethodIsDefinedTwiceInMappingXmlForBeanException$str() {
      return "HV000137: The method '%1$s' is defined twice in the mapping xml for bean %2$s.";
   }

   public final ValidationException getMethodIsDefinedTwiceInMappingXmlForBeanException(Method name, Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getMethodIsDefinedTwiceInMappingXmlForBeanException$str(), name, new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstructorIsDefinedTwiceInMappingXmlForBeanException$str() {
      return "HV000138: The constructor '%1$s' is defined twice in the mapping xml for bean %2$s.";
   }

   public final ValidationException getConstructorIsDefinedTwiceInMappingXmlForBeanException(Constructor name, Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstructorIsDefinedTwiceInMappingXmlForBeanException$str(), name, new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMultipleCrossParameterValidatorClassesException$str() {
      return "HV000139: The constraint '%1$s' defines multiple cross parameter validators. Only one is allowed.";
   }

   public final ConstraintDefinitionException getMultipleCrossParameterValidatorClassesException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getMultipleCrossParameterValidatorClassesException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getImplicitConstraintTargetInAmbiguousConfigurationException$str() {
      return "HV000141: The constraint %1$s used ConstraintTarget#IMPLICIT where the target cannot be inferred.";
   }

   public final ConstraintDeclarationException getImplicitConstraintTargetInAmbiguousConfigurationException(Class constraint) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getImplicitConstraintTargetInAmbiguousConfigurationException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCrossParameterConstraintOnMethodWithoutParametersException$str() {
      return "HV000142: Cross parameter constraint %1$s is illegally placed on a parameterless method or constructor '%2$s'.";
   }

   public final ConstraintDeclarationException getCrossParameterConstraintOnMethodWithoutParametersException(Class constraint, Executable executable) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnMethodWithoutParametersException$str(), new ClassObjectFormatter(constraint), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCrossParameterConstraintOnClassException$str() {
      return "HV000143: Cross parameter constraint %1$s is illegally placed on class level.";
   }

   public final ConstraintDeclarationException getCrossParameterConstraintOnClassException(Class constraint) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnClassException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCrossParameterConstraintOnFieldException$str() {
      return "HV000144: Cross parameter constraint %1$s is illegally placed on field '%2$s'.";
   }

   public final ConstraintDeclarationException getCrossParameterConstraintOnFieldException(Class constraint, Member field) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintOnFieldException$str(), new ClassObjectFormatter(constraint), field));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterNodeAddedForNonCrossParameterConstraintException$str() {
      return "HV000146: No parameter nodes may be added since path %s doesn't refer to a cross-parameter constraint.";
   }

   public final IllegalStateException getParameterNodeAddedForNonCrossParameterConstraintException(Path path) {
      IllegalStateException result = new IllegalStateException(String.format(this.getLoggingLocale(), this.getParameterNodeAddedForNonCrossParameterConstraintException$str(), path));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstrainedElementConfiguredMultipleTimesException$str() {
      return "HV000147: %1$s is configured multiple times (note, <getter> and <method> nodes for the same method are not allowed)";
   }

   public final ValidationException getConstrainedElementConfiguredMultipleTimesException(String location) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstrainedElementConfiguredMultipleTimesException$str(), location));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void evaluatingExpressionLanguageExpressionCausedException(String expression, Exception e) {
      super.log.logf(FQCN, Level.WARN, e, this.evaluatingExpressionLanguageExpressionCausedException$str(), expression);
   }

   protected String evaluatingExpressionLanguageExpressionCausedException$str() {
      return "HV000148: An exception occurred during evaluation of EL expression '%s'";
   }

   protected String getExceptionOccurredDuringMessageInterpolationException$str() {
      return "HV000149: An exception occurred during message interpolation";
   }

   public final ValidationException getExceptionOccurredDuringMessageInterpolationException(Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getExceptionOccurredDuringMessageInterpolationException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMultipleValidatorsForSameTypeException$str() {
      return "HV000150: The constraint %1$s defines multiple validators for the type %2$s: %3$s, %4$s. Only one is allowed.";
   }

   public final UnexpectedTypeException getMultipleValidatorsForSameTypeException(Class constraint, Type type, Class validatorClass1, Class validatorClass2) {
      UnexpectedTypeException result = new UnexpectedTypeException(String.format(this.getLoggingLocale(), this.getMultipleValidatorsForSameTypeException$str(), new ClassObjectFormatter(constraint), new TypeFormatter(type), new ClassObjectFormatter(validatorClass1), new ClassObjectFormatter(validatorClass2)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterConfigurationAlteredInSubTypeException$str() {
      return "HV000151: A method overriding another method must not redefine the parameter constraint configuration, but method %2$s redefines the configuration of %1$s.";
   }

   public final ConstraintDeclarationException getParameterConfigurationAlteredInSubTypeException(Executable superMethod, Executable subMethod) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParameterConfigurationAlteredInSubTypeException$str(), new ExecutableFormatter(superMethod), new ExecutableFormatter(subMethod)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterConstraintsDefinedInMethodsFromParallelTypesException$str() {
      return "HV000152: Two methods defined in parallel types must not declare parameter constraints, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
   }

   public final ConstraintDeclarationException getParameterConstraintsDefinedInMethodsFromParallelTypesException(Executable method1, Executable method2) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParameterConstraintsDefinedInMethodsFromParallelTypesException$str(), new ExecutableFormatter(method1), new ExecutableFormatter(method2)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException$str() {
      return "HV000153: The constraint %1$s used ConstraintTarget#%2$s but is not specified on a method or constructor.";
   }

   public final ConstraintDeclarationException getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException(Class constraint, ConstraintTarget target) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getParametersOrReturnValueConstraintTargetGivenAtNonExecutableException$str(), new ClassObjectFormatter(constraint), target));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCrossParameterConstraintHasNoValidatorException$str() {
      return "HV000154: Cross parameter constraint %1$s has no cross-parameter validator.";
   }

   public final ConstraintDefinitionException getCrossParameterConstraintHasNoValidatorException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getCrossParameterConstraintHasNoValidatorException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getComposedAndComposingConstraintsHaveDifferentTypesException$str() {
      return "HV000155: Composed and composing constraints must have the same constraint type, but composed constraint %1$s has type %3$s, while composing constraint %2$s has type %4$s.";
   }

   public final ConstraintDefinitionException getComposedAndComposingConstraintsHaveDifferentTypesException(Class composedConstraintClass, Class composingConstraintClass, ConstraintDescriptorImpl.ConstraintType composedConstraintType, ConstraintDescriptorImpl.ConstraintType composingConstraintType) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getComposedAndComposingConstraintsHaveDifferentTypesException$str(), new ClassObjectFormatter(composedConstraintClass), new ClassObjectFormatter(composingConstraintClass), composedConstraintType, composingConstraintType));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException$str() {
      return "HV000156: Constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s doesn't.";
   }

   public final ConstraintDefinitionException getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getGenericAndCrossParameterConstraintDoesNotDefineValidationAppliesToParameterException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException$str() {
      return "HV000157: Return type of the attribute validationAppliesTo() of the constraint %s must be javax.validation.ConstraintTarget.";
   }

   public final ConstraintDefinitionException getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustHaveReturnTypeConstraintTargetException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValidationAppliesToParameterMustHaveDefaultValueImplicitException$str() {
      return "HV000158: Default value of the attribute validationAppliesTo() of the constraint %s must be ConstraintTarget#IMPLICIT.";
   }

   public final ConstraintDefinitionException getValidationAppliesToParameterMustHaveDefaultValueImplicitException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustHaveDefaultValueImplicitException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException$str() {
      return "HV000159: Only constraints with generic as well as cross-parameter validators must define an attribute validationAppliesTo(), but constraint %s does.";
   }

   public final ConstraintDefinitionException getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidationAppliesToParameterMustNotBeDefinedForNonGenericAndCrossParameterConstraintException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException$str() {
      return "HV000160: Validator for cross-parameter constraint %s does not validate Object nor Object[].";
   }

   public final ConstraintDefinitionException getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException(Class constraint) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException$str(), new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException$str() {
      return "HV000161: Two methods defined in parallel types must not define group conversions for a cascaded method return value, if they are overridden by the same method, but methods %s and %s both define parameter constraints.";
   }

   public final ConstraintDeclarationException getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException(Executable method1, Executable method2) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException$str(), new ExecutableFormatter(method1), new ExecutableFormatter(method2)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMethodOrConstructorNotDefinedByValidatedTypeException$str() {
      return "HV000162: The validated type %1$s does not specify the constructor/method: %2$s";
   }

   public final IllegalArgumentException getMethodOrConstructorNotDefinedByValidatedTypeException(Class validatedType, Executable executable) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMethodOrConstructorNotDefinedByValidatedTypeException$str(), new ClassObjectFormatter(validatedType), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterTypesDoNotMatchException$str() {
      return "HV000163: The actual parameter type '%1$s' is not assignable to the expected one '%2$s' for parameter %3$d of '%4$s'";
   }

   public final IllegalArgumentException getParameterTypesDoNotMatchException(Class actualType, Type expectedType, int index, Executable executable) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getParameterTypesDoNotMatchException$str(), new ClassObjectFormatter(actualType), expectedType, index, new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getHasToBeABoxedTypeException$str() {
      return "HV000164: %s has to be a auto-boxed type.";
   }

   public final IllegalArgumentException getHasToBeABoxedTypeException(Class clazz) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getHasToBeABoxedTypeException$str(), new ClassObjectFormatter(clazz)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMixingImplicitWithOtherExecutableTypesException$str() {
      return "HV000165: Mixing IMPLICIT and other executable types is not allowed.";
   }

   public final IllegalArgumentException getMixingImplicitWithOtherExecutableTypesException() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMixingImplicitWithOtherExecutableTypesException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValidateOnExecutionOnOverriddenOrInterfaceMethodException$str() {
      return "HV000166: @ValidateOnExecution is not allowed on methods overriding a superclass method or implementing an interface. Check configuration for %1$s";
   }

   public final ValidationException getValidateOnExecutionOnOverriddenOrInterfaceMethodException(Executable executable) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getValidateOnExecutionOnOverriddenOrInterfaceMethodException$str(), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getOverridingConstraintDefinitionsInMultipleMappingFilesException$str() {
      return "HV000167: A given constraint definition can only be overridden in one mapping file. %1$s is overridden in multiple files";
   }

   public final ValidationException getOverridingConstraintDefinitionsInMultipleMappingFilesException(String constraintClassName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getOverridingConstraintDefinitionsInMultipleMappingFilesException$str(), constraintClassName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNonTerminatedParameterException$str() {
      return "HV000168: The message descriptor '%1$s' contains an unbalanced meta character '%2$c' parameter.";
   }

   public final MessageDescriptorFormatException getNonTerminatedParameterException(String messageDescriptor, char character) {
      MessageDescriptorFormatException result = new MessageDescriptorFormatException(String.format(this.getLoggingLocale(), this.getNonTerminatedParameterException$str(), messageDescriptor, character));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNestedParameterException$str() {
      return "HV000169: The message descriptor '%1$s' has nested parameters.";
   }

   public final MessageDescriptorFormatException getNestedParameterException(String messageDescriptor) {
      MessageDescriptorFormatException result = new MessageDescriptorFormatException(String.format(this.getLoggingLocale(), this.getNestedParameterException$str(), messageDescriptor));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCreationOfScriptExecutorFailedException$str() {
      return "HV000170: No JSR-223 scripting engine could be bootstrapped for language \"%s\".";
   }

   public final ConstraintDeclarationException getCreationOfScriptExecutorFailedException(String languageName, Exception e) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCreationOfScriptExecutorFailedException$str(), languageName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000171: %s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000172: Property \"%2$s\" of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, String propertyName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getPropertyHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), propertyName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMethodHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000173: Method %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getMethodHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, String method) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getMethodHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), method));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000174: Parameter %3$s of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getParameterHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, Executable executable, int parameterIndex) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getParameterHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), new ExecutableFormatter(executable), parameterIndex));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000175: The return value of method or constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, Executable executable) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000176: Constructor %2$s of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, String constructor) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstructorHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), constructor));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException$str() {
      return "HV000177: Cross-parameter constraints for the method or constructor %2$s of type %1$s are declared more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException(Class beanClass, Executable executable) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(beanClass), new ExecutableFormatter(executable)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getMultiplierCannotBeNegativeException$str() {
      return "HV000178: Multiplier cannot be negative: %d.";
   }

   public final IllegalArgumentException getMultiplierCannotBeNegativeException(int multiplier) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getMultiplierCannotBeNegativeException$str(), multiplier));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getWeightCannotBeNegativeException$str() {
      return "HV000179: Weight cannot be negative: %d.";
   }

   public final IllegalArgumentException getWeightCannotBeNegativeException(int weight) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getWeightCannotBeNegativeException$str(), weight));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getTreatCheckAsIsNotADigitNorALetterException$str() {
      return "HV000180: '%c' is not a digit nor a letter.";
   }

   public final IllegalArgumentException getTreatCheckAsIsNotADigitNorALetterException(int weight) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getTreatCheckAsIsNotADigitNorALetterException$str(), weight));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidParameterCountForExecutableException$str() {
      return "HV000181: Wrong number of parameters. Method or constructor %1$s expects %2$d parameters, but got %3$d.";
   }

   public final IllegalArgumentException getInvalidParameterCountForExecutableException(String executable, int expectedParameterCount, int actualParameterCount) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getInvalidParameterCountForExecutableException$str(), executable, expectedParameterCount, actualParameterCount));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoUnwrapperFoundForTypeException$str() {
      return "HV000182: No validation value unwrapper is registered for type '%1$s'.";
   }

   public final ValidationException getNoUnwrapperFoundForTypeException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNoUnwrapperFoundForTypeException$str(), type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInitializeELExpressionFactoryException$str() {
      return "HV000183: Unable to initialize 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath, or use ParameterMessageInterpolator instead";
   }

   public final ValidationException getUnableToInitializeELExpressionFactoryException(Throwable e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInitializeELExpressionFactoryException$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void warnElIsUnsupported(String expression) {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.warnElIsUnsupported$str(), expression);
   }

   protected String warnElIsUnsupported$str() {
      return "HV000185: Message contains EL expression: %1s, which is not supported by the selected message interpolator";
   }

   protected String getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException$str() {
      return "HV000189: The configuration of value unwrapping for property '%s' of bean '%s' is inconsistent between the field and its getter.";
   }

   public final ConstraintDeclarationException getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException(String property, Class beanClass) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getInconsistentValueUnwrappingConfigurationBetweenFieldAndItsGetterException$str(), property, new ClassObjectFormatter(beanClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToCreateXMLEventReader$str() {
      return "HV000190: Unable to parse %s.";
   }

   public final ValidationException getUnableToCreateXMLEventReader(String file, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateXMLEventReader$str(), file), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unknownJvmVersion(String vmVersionStr) {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.unknownJvmVersion$str(), vmVersionStr);
   }

   protected String unknownJvmVersion$str() {
      return "HV000192: Couldn't determine Java version from value %1s; Not enabling features requiring Java 8";
   }

   protected String getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException$str() {
      return "HV000193: %s is configured more than once via the programmatic constraint definition API.";
   }

   public final ValidationException getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException(Class annotationClass) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getConstraintHasAlreadyBeenConfiguredViaProgrammaticApiException$str(), new ClassObjectFormatter(annotationClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection$str() {
      return "HV000194: An empty element is only supported when a CharSequence is expected.";
   }

   public final ValidationException getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToReachPropertyToValidateException$str() {
      return "HV000195: Unable to reach the property to validate for the bean %s and the property path %s. A property is null along the way.";
   }

   public final ValidationException getUnableToReachPropertyToValidateException(Object bean, Path path) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToReachPropertyToValidateException$str(), bean, path));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToConvertTypeToClassException$str() {
      return "HV000196: Unable to convert the Type %s to a Class.";
   }

   public final ValidationException getUnableToConvertTypeToClassException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToConvertTypeToClassException$str(), type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoValueExtractorFoundForTypeException2$str() {
      return "HV000197: No value extractor found for type parameter '%2$s' of type %1$s.";
   }

   public final ConstraintDeclarationException getNoValueExtractorFoundForTypeException(Class type, TypeVariable typeParameter) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForTypeException2$str(), new ClassObjectFormatter(type), typeParameter));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoValueExtractorFoundForUnwrapException$str() {
      return "HV000198: No suitable value extractor found for type %1$s.";
   }

   public final ConstraintDeclarationException getNoValueExtractorFoundForUnwrapException(Type type) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForUnwrapException$str(), type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void usingClockProvider(Class clockProviderClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingClockProvider$str(), new ClassObjectFormatter(clockProviderClass));
   }

   protected String usingClockProvider$str() {
      return "HV000200: Using %s as clock provider.";
   }

   protected String getUnableToInstantiateClockProviderClassException$str() {
      return "HV000201: Unable to instantiate clock provider class %s.";
   }

   public final ValidationException getUnableToInstantiateClockProviderClassException(String clockProviderClassName, ValidationException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateClockProviderClassException$str(), clockProviderClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToGetCurrentTimeFromClockProvider$str() {
      return "HV000202: Unable to get the current time from the clock provider";
   }

   public final ValidationException getUnableToGetCurrentTimeFromClockProvider(Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToGetCurrentTimeFromClockProvider$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValueExtractorFailsToDeclareExtractedValueException$str() {
      return "HV000203: Value extractor type %1s fails to declare the extracted type parameter using @ExtractedValue.";
   }

   public final ValueExtractorDefinitionException getValueExtractorFailsToDeclareExtractedValueException(Class extractorType) {
      ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getValueExtractorFailsToDeclareExtractedValueException$str(), new ClassObjectFormatter(extractorType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getValueExtractorDeclaresExtractedValueMultipleTimesException$str() {
      return "HV000204: Only one type parameter must be marked with @ExtractedValue for value extractor type %1s.";
   }

   public final ValueExtractorDefinitionException getValueExtractorDeclaresExtractedValueMultipleTimesException(Class extractorType) {
      ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getValueExtractorDeclaresExtractedValueMultipleTimesException$str(), new ClassObjectFormatter(extractorType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidUnwrappingConfigurationForConstraintException$str() {
      return "HV000205: Invalid unwrapping configuration for constraint %2$s on %1$s. You can only define one of 'Unwrapping.Skip' or 'Unwrapping.Unwrap'.";
   }

   public final ConstraintDeclarationException getInvalidUnwrappingConfigurationForConstraintException(Member member, Class constraint) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getInvalidUnwrappingConfigurationForConstraintException$str(), member, new ClassObjectFormatter(constraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToInstantiateValueExtractorClassException$str() {
      return "HV000206: Unable to instantiate value extractor class %s.";
   }

   public final ValidationException getUnableToInstantiateValueExtractorClassException(String valueExtractorClassName, ValidationException e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateValueExtractorClassException$str(), valueExtractorClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void addingValueExtractor(Class valueExtractorClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.addingValueExtractor$str(), new ClassObjectFormatter(valueExtractorClass));
   }

   protected String addingValueExtractor$str() {
      return "HV000207: Adding value extractor %s.";
   }

   protected String getValueExtractorForTypeAndTypeUseAlreadyPresentException$str() {
      return "HV000208: Given value extractor %2$s handles the same type and type use as previously given value extractor %1$s.";
   }

   public final ValueExtractorDeclarationException getValueExtractorForTypeAndTypeUseAlreadyPresentException(ValueExtractor first, ValueExtractor second) {
      ValueExtractorDeclarationException result = new ValueExtractorDeclarationException(String.format(this.getLoggingLocale(), this.getValueExtractorForTypeAndTypeUseAlreadyPresentException$str(), first, second));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException$str() {
      return "HV000209: A composing constraint (%2$s) must not be given directly on the composed constraint (%1$s) and using the corresponding List annotation at the same time.";
   }

   public final ConstraintDeclarationException getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException(Class composedConstraint, Class composingConstraint) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getCannotMixDirectAnnotationAndListContainerOnComposedConstraintException$str(), new ClassObjectFormatter(composedConstraint), new ClassObjectFormatter(composingConstraint)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindTypeParameterInClass$str() {
      return "HV000210: Unable to find the type parameter %2$s in class %1$s.";
   }

   public final IllegalArgumentException getUnableToFindTypeParameterInClass(Class clazz, Object typeParameterReference) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getUnableToFindTypeParameterInClass$str(), new ClassObjectFormatter(clazz), typeParameterReference));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getTypeIsNotAParameterizedNorArrayTypeException$str() {
      return "HV000211: Given type is neither a parameterized nor an array type: %s.";
   }

   public final ValidationException getTypeIsNotAParameterizedNorArrayTypeException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getTypeIsNotAParameterizedNorArrayTypeException$str(), new TypeFormatter(type)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getInvalidTypeArgumentIndexException$str() {
      return "HV000212: Given type has no type argument with index %2$s: %1$s.";
   }

   public final ValidationException getInvalidTypeArgumentIndexException(Type type, int index) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getInvalidTypeArgumentIndexException$str(), new TypeFormatter(type), index));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException$str() {
      return "HV000213: Given type has more than one type argument, hence an argument index must be specified: %s.";
   }

   public final ValidationException getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException$str(), new TypeFormatter(type)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException$str() {
      return "HV000214: The same container element type of type %1$s is configured more than once via the programmatic constraint declaration API.";
   }

   public final ValidationException getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException$str(), new TypeFormatter(type)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParameterIsNotAValidCallException$str() {
      return "HV000215: Calling parameter() is not allowed for the current element.";
   }

   public final ValidationException getParameterIsNotAValidCallException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getParameterIsNotAValidCallException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getReturnValueIsNotAValidCallException$str() {
      return "HV000216: Calling returnValue() is not allowed for the current element.";
   }

   public final ValidationException getReturnValueIsNotAValidCallException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getReturnValueIsNotAValidCallException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException$str() {
      return "HV000217: The same container element type %2$s is configured more than once for location %1$s via the XML mapping configuration.";
   }

   public final ValidationException getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException(ConstraintLocation rootConstraintLocation, ContainerElementTypePath path) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementTypeHasAlreadyBeenConfiguredViaXmlMappingConfigurationException$str(), rootConstraintLocation, path));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getParallelDefinitionsOfValueExtractorsException$str() {
      return "HV000218: Having parallel definitions of value extractors on a given class is not allowed: %s.";
   }

   public final ValueExtractorDefinitionException getParallelDefinitionsOfValueExtractorsException(Class extractorImplementationType) {
      ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getParallelDefinitionsOfValueExtractorsException$str(), new ClassObjectFormatter(extractorImplementationType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException$str() {
      return "HV000219: Unable to get the most specific value extractor for type %1$s as several most specific value extractors are declared: %2$s.";
   }

   public final ConstraintDeclarationException getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException(Class valueType, Collection valueExtractors) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getUnableToGetMostSpecificValueExtractorDueToSeveralMaximallySpecificValueExtractorsDeclaredException$str(), new ClassObjectFormatter(valueType), new CollectionOfClassesObjectFormatter(valueExtractors)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException$str() {
      return "HV000220: When @ExtractedValue is defined on a type parameter of a container type, the type attribute may not be set: %1$s.";
   }

   public final ValueExtractorDefinitionException getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException(Class extractorImplementationType) {
      ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException$str(), new ClassObjectFormatter(extractorImplementationType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorWhileExtractingValuesInValueExtractorException$str() {
      return "HV000221: An error occurred while extracting values in value extractor %1$s.";
   }

   public final ValidationException getErrorWhileExtractingValuesInValueExtractorException(Class extractorImplementationType, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getErrorWhileExtractingValuesInValueExtractorException$str(), new ClassObjectFormatter(extractorImplementationType)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getDuplicateDefinitionsOfValueExtractorException$str() {
      return "HV000222: The same value extractor %s is added more than once via the XML configuration.";
   }

   public final ValueExtractorDeclarationException getDuplicateDefinitionsOfValueExtractorException(String className) {
      ValueExtractorDeclarationException result = new ValueExtractorDeclarationException(String.format(this.getLoggingLocale(), this.getDuplicateDefinitionsOfValueExtractorException$str(), className));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException$str() {
      return "HV000223: Implicit unwrapping is not allowed for type %1$s as several maximally specific value extractors marked with @UnwrapByDefault are declared: %2$s.";
   }

   public final ConstraintDeclarationException getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException(Class valueType, Collection valueExtractors) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getImplicitUnwrappingNotAllowedWhenSeveralMaximallySpecificValueExtractorsMarkedWithUnwrapByDefaultDeclaredException$str(), new ClassObjectFormatter(valueType), new CollectionOfClassesObjectFormatter(valueExtractors)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnwrappingOfConstraintDescriptorNotSupportedYetException$str() {
      return "HV000224: Unwrapping of ConstraintDescriptor is not supported yet.";
   }

   public final ValidationException getUnwrappingOfConstraintDescriptorNotSupportedYetException() {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnwrappingOfConstraintDescriptorNotSupportedYetException$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException$str() {
      return "HV000225: Only unbound wildcard type arguments are supported for the container type of the value extractor: %1$s.";
   }

   public final ValueExtractorDefinitionException getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException(Class valueExtractorClass) {
      ValueExtractorDefinitionException result = new ValueExtractorDefinitionException(String.format(this.getLoggingLocale(), this.getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException$str(), new ClassObjectFormatter(valueExtractorClass)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException$str() {
      return "HV000226: Container element constraints and cascading validation are not supported on arrays: %1$s";
   }

   public final ValidationException getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException(Type type) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException$str(), new TypeFormatter(type)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getPropertyNotDefinedByValidatedTypeException$str() {
      return "HV000227: The validated type %1$s does not specify the property: %2$s";
   }

   public final IllegalArgumentException getPropertyNotDefinedByValidatedTypeException(Class validatedType, String propertyName) {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.getLoggingLocale(), this.getPropertyNotDefinedByValidatedTypeException$str(), new ClassObjectFormatter(validatedType), propertyName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getNoValueExtractorFoundForTypeException3$str() {
      return "HV000228: No value extractor found when narrowing down to the runtime type %3$s among the value extractors for type parameter '%2$s' of type %1$s.";
   }

   public final ConstraintDeclarationException getNoValueExtractorFoundForTypeException(Type declaredType, TypeVariable declaredTypeParameter, Class valueType) {
      ConstraintDeclarationException result = new ConstraintDeclarationException(String.format(this.getLoggingLocale(), this.getNoValueExtractorFoundForTypeException3$str(), new TypeFormatter(declaredType), declaredTypeParameter, new ClassObjectFormatter(valueType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToCastException$str() {
      return "HV000229: Unable to cast %1$s to %2$s.";
   }

   public final ClassCastException getUnableToCastException(Object object, Class clazz) {
      ClassCastException result = new ClassCastException(String.format(this.getLoggingLocale(), this.getUnableToCastException$str(), object, new ClassObjectFormatter(clazz)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void usingScriptEvaluatorFactory(Class scriptEvaluatorFactoryClass) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.usingScriptEvaluatorFactory$str(), new ClassObjectFormatter(scriptEvaluatorFactoryClass));
   }

   protected String usingScriptEvaluatorFactory$str() {
      return "HV000230: Using %s as script evaluator factory.";
   }

   protected String getUnableToInstantiateScriptEvaluatorFactoryClassException$str() {
      return "HV000231: Unable to instantiate script evaluator factory class %s.";
   }

   public final ValidationException getUnableToInstantiateScriptEvaluatorFactoryClassException(String scriptEvaluatorFactoryClassName, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToInstantiateScriptEvaluatorFactoryClassException$str(), scriptEvaluatorFactoryClassName), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindScriptEngineException$str() {
      return "HV000232: No JSR 223 script engine found for language \"%s\".";
   }

   public final ScriptEvaluatorNotFoundException getUnableToFindScriptEngineException(String languageName) {
      ScriptEvaluatorNotFoundException result = new ScriptEvaluatorNotFoundException(String.format(this.getLoggingLocale(), this.getUnableToFindScriptEngineException$str(), languageName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getErrorExecutingScriptException$str() {
      return "HV000233: An error occurred while executing the script: \"%s\".";
   }

   public final ScriptEvaluationException getErrorExecutingScriptException(String script, Exception e) {
      ScriptEvaluationException result = new ScriptEvaluationException(String.format(this.getLoggingLocale(), this.getErrorExecutingScriptException$str(), script), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void logValidatorFactoryScopedConfiguration(Class configuredClass, String configuredElement) {
      super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.logValidatorFactoryScopedConfiguration$str(), new ClassObjectFormatter(configuredClass), configuredElement);
   }

   protected String logValidatorFactoryScopedConfiguration$str() {
      return "HV000234: Using %1$s as ValidatorFactory-scoped %2$s.";
   }

   protected String getUnableToCreateAnnotationDescriptor$str() {
      return "HV000235: Unable to create an annotation descriptor for %1$s.";
   }

   public final ValidationException getUnableToCreateAnnotationDescriptor(Class configuredClass, Throwable e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToCreateAnnotationDescriptor$str(), new ClassObjectFormatter(configuredClass)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToFindAnnotationDefDeclaredMethods$str() {
      return "HV000236: Unable to find the method required to create the constraint annotation descriptor.";
   }

   public final ValidationException getUnableToFindAnnotationDefDeclaredMethods(Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToFindAnnotationDefDeclaredMethods$str()), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String getUnableToAccessMethodException$str() {
      return "HV000237: Unable to access method %3$s of class %2$s with parameters %4$s using lookup %1$s.";
   }

   public final ValidationException getUnableToAccessMethodException(MethodHandles.Lookup lookup, Class clazz, String methodName, Object[] parameterTypes, Throwable e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToAccessMethodException$str(), lookup, new ClassObjectFormatter(clazz), methodName, new ObjectArrayFormatter(parameterTypes)), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void logTemporalValidationTolerance(Duration tolerance) {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.logTemporalValidationTolerance$str(), new DurationFormatter(tolerance));
   }

   protected String logTemporalValidationTolerance$str() {
      return "HV000238: Temporal validation tolerance set to %1$s.";
   }

   protected String getUnableToParseTemporalValidationToleranceException$str() {
      return "HV000239: Unable to parse the temporal validation tolerance property %s. It should be a duration represented in milliseconds.";
   }

   public final ValidationException getUnableToParseTemporalValidationToleranceException(String toleranceProperty, Exception e) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.getUnableToParseTemporalValidationToleranceException$str(), toleranceProperty), e);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void logConstraintValidatorPayload(Object payload) {
      super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.logConstraintValidatorPayload$str(), payload);
   }

   protected String logConstraintValidatorPayload$str() {
      return "HV000240: Constraint validator payload set to %1$s.";
   }

   protected String logUnknownElementInXmlConfiguration$str() {
      return "HV000241: Encountered unsupported element %1$s while parsing the XML configuration.";
   }

   public final ValidationException logUnknownElementInXmlConfiguration(String tag) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.logUnknownElementInXmlConfiguration$str(), tag));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void logUnableToLoadOrInstantiateJPAAwareResolver(String traversableResolverClassName) {
      super.log.logf(FQCN, Level.WARN, (Throwable)null, this.logUnableToLoadOrInstantiateJPAAwareResolver$str(), traversableResolverClassName);
   }

   protected String logUnableToLoadOrInstantiateJPAAwareResolver$str() {
      return "HV000242: Unable to load or instantiate JPA aware resolver %1$s. All properties will per default be traversable.";
   }

   protected String getConstraintValidatorDefinitionConstraintMismatchException$str() {
      return "HV000243: Constraint %2$s references constraint validator type %1$s, but this validator is defined for constraint type %3$s.";
   }

   public final ConstraintDefinitionException getConstraintValidatorDefinitionConstraintMismatchException(Class constraintValidatorImplementationType, Class registeredConstraintAnnotationType, Type declaredConstraintAnnotationType) {
      ConstraintDefinitionException result = new ConstraintDefinitionException(String.format(this.getLoggingLocale(), this.getConstraintValidatorDefinitionConstraintMismatchException$str(), new ClassObjectFormatter(constraintValidatorImplementationType), new ClassObjectFormatter(registeredConstraintAnnotationType), new TypeFormatter(declaredConstraintAnnotationType)));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToGetXmlSchema$str() {
      return "HV000248: Unable to get an XML schema named %s.";
   }

   public final ValidationException unableToGetXmlSchema(String schemaResourceName) {
      ValidationException result = new ValidationException(String.format(this.getLoggingLocale(), this.unableToGetXmlSchema$str(), schemaResourceName));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   static {
      LOCALE = Locale.ROOT;
   }
}
