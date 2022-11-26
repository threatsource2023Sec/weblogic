package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

public class MetadataLogger_$logger extends DelegatingBasicLogger implements MetadataLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = MetadataLogger_$logger.class.getName();
   private static final String metaAnnotationOnWrongType = "WELD-001100: {0} can only be applied to an annotation.  It was applied to {1}";
   private static final String nonBindingMemberType = "WELD-001101: Member of array type or annotation type must be annotated @NonBinding:  {0}";
   private static final String stereotypeNotRegistered = "WELD-001102: Stereotype {0} not registered with container";
   private static final String qualifierOnStereotype = "WELD-001103: Cannot declare qualifiers on stereotype {0}";
   private static final String valueOnNamedStereotype = "WELD-001104: Cannot specify a value for @Named stereotype {0}";
   private static final String multipleScopes = "WELD-001105: At most one scope type may be specified for {0}";
   private static final String stereotypesNull = "WELD-001106: BeanAttributes.getStereotypes() returned null for {0}";
   private static final String qualifiersNull = "WELD-001107: {0}() returned null for {1}";
   private static final String typesNull = "WELD-001108: BeanAttributes.getTypes() returned null for {0}";
   private static final String scopeNull = "WELD-001109: BeanAttributes.getScope() returned null for {0}";
   private static final String notAStereotype = "WELD-001110: {0} defined on {1} is not a stereotype";
   private static final String notAQualifier = "WELD-001111: {0} defined on {1} is not a qualifier";
   private static final String typesEmpty = "WELD-001112: BeanAttributes.getTypes() may not return an empty set {0}";
   private static final String notAScope = "WELD-001113: {0} defined on {1} is not a scope annotation";
   private static final String metadataSourceReturnedNull = "WELD-001114: {0} returned null for {1}";
   private static final String invalidParameterPosition = "WELD-001115: Parameter position {0} of parameter {1} is not valid";
   private static final String noConstructor = "WELD-001116: AnnotatedType ({0}) without a constructor";
   private static final String notInHierarchy = "WELD-001117: Member {0} ({1}) does not belong to the actual class hierarchy of the annotatedType {2} ({3})\n\tat {4}";
   private static final String typeVariableIsNotAValidBeanType = "WELD-001118: A type variable is not a valid bean type. Bean type {0} of bean {1}";
   private static final String parameterizedTypeContainingWildcardParameterIsNotAValidBeanType = "WELD-001119: A parameterized type containing wildcard parameters is not a valid bean type. Bean type {0} of bean {1}";
   private static final String beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped = "WELD-001120: A bean that has a parameterized bean type containing type variables must be @Dependent scoped. Bean type {0} of bean {1}";
   private static final String nonBindingMemberTypeException = "WELD-001121: Member of array type or annotation type must be annotated @NonBinding:  {0}";
   private static final String annotatedTypeDeserializationFailure = "WELD-001122: Failed to deserialize annotated type identified with {0}";
   private static final String notAnInterceptorBinding = "WELD-001123: {0} defined on {1} is not an interceptor binding";
   private static final String contextGetScopeIsNotAScope = "WELD-001124: Context.getScope() returned {0} which is not a scope annotation. Context: {1}";
   private static final String illegalBeanTypeIgnored = "WELD-001125: Illegal bean type {0} ignored on {1}";
   private static final String beanAttributesConfiguratorCannotReadAnnotatedType = "WELD-001126: BeanAttributesConfigurator is not able to read {0} - missing BeanManager";
   private static final String catchingDebug = "Catching";

   public MetadataLogger_$logger(Logger log) {
      super(log);
   }

   protected String metaAnnotationOnWrongType$str() {
      return "WELD-001100: {0} can only be applied to an annotation.  It was applied to {1}";
   }

   public final DefinitionException metaAnnotationOnWrongType(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.metaAnnotationOnWrongType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void nonBindingMemberType(Object param1) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.nonBindingMemberType$str(), param1);
   }

   protected String nonBindingMemberType$str() {
      return "WELD-001101: Member of array type or annotation type must be annotated @NonBinding:  {0}";
   }

   protected String stereotypeNotRegistered$str() {
      return "WELD-001102: Stereotype {0} not registered with container";
   }

   public final IllegalStateException stereotypeNotRegistered(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.stereotypeNotRegistered$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String qualifierOnStereotype$str() {
      return "WELD-001103: Cannot declare qualifiers on stereotype {0}";
   }

   public final DefinitionException qualifierOnStereotype(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.qualifierOnStereotype$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String valueOnNamedStereotype$str() {
      return "WELD-001104: Cannot specify a value for @Named stereotype {0}";
   }

   public final DefinitionException valueOnNamedStereotype(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.valueOnNamedStereotype$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleScopes$str() {
      return "WELD-001105: At most one scope type may be specified for {0}";
   }

   public final DefinitionException multipleScopes(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleScopes$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String stereotypesNull$str() {
      return "WELD-001106: BeanAttributes.getStereotypes() returned null for {0}";
   }

   public final DefinitionException stereotypesNull(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.stereotypesNull$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String qualifiersNull$str() {
      return "WELD-001107: {0}() returned null for {1}";
   }

   public final DefinitionException qualifiersNull(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.qualifiersNull$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String typesNull$str() {
      return "WELD-001108: BeanAttributes.getTypes() returned null for {0}";
   }

   public final DefinitionException typesNull(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.typesNull$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String scopeNull$str() {
      return "WELD-001109: BeanAttributes.getScope() returned null for {0}";
   }

   public final DefinitionException scopeNull(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.scopeNull$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notAStereotype$str() {
      return "WELD-001110: {0} defined on {1} is not a stereotype";
   }

   public final DefinitionException notAStereotype(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.notAStereotype$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notAQualifier$str() {
      return "WELD-001111: {0} defined on {1} is not a qualifier";
   }

   public final DefinitionException notAQualifier(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.notAQualifier$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String typesEmpty$str() {
      return "WELD-001112: BeanAttributes.getTypes() may not return an empty set {0}";
   }

   public final DefinitionException typesEmpty(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.typesEmpty$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notAScope$str() {
      return "WELD-001113: {0} defined on {1} is not a scope annotation";
   }

   public final DefinitionException notAScope(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.notAScope$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String metadataSourceReturnedNull$str() {
      return "WELD-001114: {0} returned null for {1}";
   }

   public final IllegalArgumentException metadataSourceReturnedNull(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.metadataSourceReturnedNull$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidParameterPosition$str() {
      return "WELD-001115: Parameter position {0} of parameter {1} is not valid";
   }

   public final IllegalArgumentException invalidParameterPosition(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidParameterPosition$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void noConstructor(Object param1) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.noConstructor$str(), param1);
   }

   protected String noConstructor$str() {
      return "WELD-001116: AnnotatedType ({0}) without a constructor";
   }

   public final void notInHierarchy(Object memberName, Object member, Object annotatedTypeJavaClassName, Object annotatedType, Object stackElement) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.notInHierarchy$str(), new Object[]{memberName, member, annotatedTypeJavaClassName, annotatedType, stackElement});
   }

   protected String notInHierarchy$str() {
      return "WELD-001117: Member {0} ({1}) does not belong to the actual class hierarchy of the annotatedType {2} ({3})\n\tat {4}";
   }

   protected String typeVariableIsNotAValidBeanType$str() {
      return "WELD-001118: A type variable is not a valid bean type. Bean type {0} of bean {1}";
   }

   public final DefinitionException typeVariableIsNotAValidBeanType(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.typeVariableIsNotAValidBeanType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String parameterizedTypeContainingWildcardParameterIsNotAValidBeanType$str() {
      return "WELD-001119: A parameterized type containing wildcard parameters is not a valid bean type. Bean type {0} of bean {1}";
   }

   public final DefinitionException parameterizedTypeContainingWildcardParameterIsNotAValidBeanType(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.parameterizedTypeContainingWildcardParameterIsNotAValidBeanType$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped$str() {
      return "WELD-001120: A bean that has a parameterized bean type containing type variables must be @Dependent scoped. Bean type {0} of bean {1}";
   }

   public final DefinitionException beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String nonBindingMemberTypeException$str() {
      return "WELD-001121: Member of array type or annotation type must be annotated @NonBinding:  {0}";
   }

   public final DefinitionException nonBindingMemberTypeException(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.nonBindingMemberTypeException$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String annotatedTypeDeserializationFailure$str() {
      return "WELD-001122: Failed to deserialize annotated type identified with {0}";
   }

   public final IllegalStateException annotatedTypeDeserializationFailure(AnnotatedTypeIdentifier identifier) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.annotatedTypeDeserializationFailure$str(), identifier));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String notAnInterceptorBinding$str() {
      return "WELD-001123: {0} defined on {1} is not an interceptor binding";
   }

   public final DefinitionException notAnInterceptorBinding(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.notAnInterceptorBinding$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void contextGetScopeIsNotAScope(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.contextGetScopeIsNotAScope$str(), param1, param2);
   }

   protected String contextGetScopeIsNotAScope$str() {
      return "WELD-001124: Context.getScope() returned {0} which is not a scope annotation. Context: {1}";
   }

   public final void illegalBeanTypeIgnored(Object type, Object annotated) {
      super.log.logv(FQCN, Level.INFO, (Throwable)null, this.illegalBeanTypeIgnored$str(), type, annotated);
   }

   protected String illegalBeanTypeIgnored$str() {
      return "WELD-001125: Illegal bean type {0} ignored on {1}";
   }

   protected String beanAttributesConfiguratorCannotReadAnnotatedType$str() {
      return "WELD-001126: BeanAttributesConfigurator is not able to read {0} - missing BeanManager";
   }

   public final IllegalStateException beanAttributesConfiguratorCannotReadAnnotatedType(Object type) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.beanAttributesConfiguratorCannotReadAnnotatedType$str(), type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
