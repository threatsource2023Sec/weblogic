package org.jboss.weld.logging;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.WeldException;

public class ReflectionLogger_$logger extends DelegatingBasicLogger implements ReflectionLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ReflectionLogger_$logger.class.getName();
   private static final String missingRetention = "WELD-000600: {0} is missing @Retention(RUNTIME). Weld will use this annotation, however this may make the application unportable.";
   private static final String missingTarget = "WELD-000601: {0} is missing @Target. Weld will use this annotation, however this may make the application unportable.";
   private static final String missingTargetTypeMethodOrTargetType = "WELD-000602: {0} is not declared @Target(TYPE, METHOD) or @Target(TYPE). Weld will use this annotation, however this may make the application unportable.";
   private static final String missingTargetMethodFieldType = "WELD-000604: {0} is not declared @Target(METHOD, FIELD, TYPE). Weld will use this annotation, however this may make the application unportable.";
   private static final String missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField = "WELD-000605: {0} is not declared @Target(METHOD, FIELD, TYPE, PARAMETER), @Target(METHOD, TYPE), @Target(METHOD), @Target(TYPE) or @Target(FIELD). Weld will use this annotation, however this may make the application unportable.";
   private static final String unableToGetParameterName = "WELD-000606: Unable to determine name of parameter";
   private static final String annotationMapNull = "WELD-000607: annotationMap cannot be null";
   private static final String declaredAnnotationMapNull = "WELD-000608: declaredAnnotationMap cannot be null";
   private static final String unableToGetConstructorOnDeserialization = "WELD-000610: Unable to deserialize constructor. Declaring class {0}, index {1}";
   private static final String unableToGetMethodOnDeserialization = "WELD-000611: Unable to deserialize method. Declaring class {0}, index {1}";
   private static final String unableToGetFieldOnDeserialization = "WELD-000612: Unable to deserialize field. Declaring class {0}, field name {1}";
   private static final String incorrectNumberOfAnnotatedParametersMethod = "WELD-000614: Incorrect number of AnnotatedParameters {0} on AnnotatedMethod {1}. AnnotatedMethod has {2} as parameters but should have {3} as parameters";
   private static final String reflectionfactoryInstantiationFailed = "WELD-000616: Instantiation through ReflectionFactory of {0} failed";
   private static final String unsafeInstantiationFailed = "WELD-000617: Instantiation through Unsafe of {0} failed";
   private static final String lifecycleCallbackInterceptorWithInvalidBindingTarget = "WELD-000619: A lifecycle callback interceptor declares an interceptor binding with target other than ElementType.TYPE\n  {0}\n  Binding: {1}\n  Target: {2}";
   private static final String missingTargetMethodFieldParameterType = "WELD-000620: {0} is not declared @Target(METHOD, FIELD, PARAMETER, TYPE). Weld will use this annotation, however this may make the application unportable.";
   private static final String invalidInterceptorBindingTargetDeclaration = "WELD-000621: Interceptor binding {0} with @Target defined as {1} should not be applied on interceptor binding {2} with @Target definition: {3}";
   private static final String illegalArgumentExceptionOnReflectionInvocation = "WELD-000622: IllegalArgumentException invoking {2} on {1} ({0}) with parameters {3}";
   private static final String unknownType = "WELD-000623: Unknown type {0}.";
   private static final String invalidTypeArgumentCombination = "WELD-000624: Invalid type argument combination: {0}; {1}.";
   private static final String noSuchMethodWrapper = "WELD-000625: Unable to locate method: {0}";
   private static final String catchingDebug = "Catching";

   public ReflectionLogger_$logger(Logger log) {
      super(log);
   }

   public final void missingRetention(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingRetention$str(), param1);
   }

   protected String missingRetention$str() {
      return "WELD-000600: {0} is missing @Retention(RUNTIME). Weld will use this annotation, however this may make the application unportable.";
   }

   public final void missingTarget(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingTarget$str(), param1);
   }

   protected String missingTarget$str() {
      return "WELD-000601: {0} is missing @Target. Weld will use this annotation, however this may make the application unportable.";
   }

   public final void missingTargetTypeMethodOrTargetType(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingTargetTypeMethodOrTargetType$str(), param1);
   }

   protected String missingTargetTypeMethodOrTargetType$str() {
      return "WELD-000602: {0} is not declared @Target(TYPE, METHOD) or @Target(TYPE). Weld will use this annotation, however this may make the application unportable.";
   }

   public final void missingTargetMethodFieldType(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingTargetMethodFieldType$str(), param1);
   }

   protected String missingTargetMethodFieldType$str() {
      return "WELD-000604: {0} is not declared @Target(METHOD, FIELD, TYPE). Weld will use this annotation, however this may make the application unportable.";
   }

   public final void missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField$str(), param1);
   }

   protected String missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField$str() {
      return "WELD-000605: {0} is not declared @Target(METHOD, FIELD, TYPE, PARAMETER), @Target(METHOD, TYPE), @Target(METHOD), @Target(TYPE) or @Target(FIELD). Weld will use this annotation, however this may make the application unportable.";
   }

   protected String unableToGetParameterName$str() {
      return "WELD-000606: Unable to determine name of parameter";
   }

   public final IllegalArgumentException unableToGetParameterName() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.unableToGetParameterName$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String annotationMapNull$str() {
      return "WELD-000607: annotationMap cannot be null";
   }

   public final WeldException annotationMapNull() {
      WeldException result = new WeldException(String.format(this.annotationMapNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String declaredAnnotationMapNull$str() {
      return "WELD-000608: declaredAnnotationMap cannot be null";
   }

   public final WeldException declaredAnnotationMapNull() {
      WeldException result = new WeldException(String.format(this.declaredAnnotationMapNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToGetConstructorOnDeserialization$str() {
      return "WELD-000610: Unable to deserialize constructor. Declaring class {0}, index {1}";
   }

   public final WeldException unableToGetConstructorOnDeserialization(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.unableToGetConstructorOnDeserialization$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToGetMethodOnDeserialization$str() {
      return "WELD-000611: Unable to deserialize method. Declaring class {0}, index {1}";
   }

   public final WeldException unableToGetMethodOnDeserialization(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.unableToGetMethodOnDeserialization$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToGetFieldOnDeserialization$str() {
      return "WELD-000612: Unable to deserialize field. Declaring class {0}, field name {1}";
   }

   public final WeldException unableToGetFieldOnDeserialization(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.unableToGetFieldOnDeserialization$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String incorrectNumberOfAnnotatedParametersMethod$str() {
      return "WELD-000614: Incorrect number of AnnotatedParameters {0} on AnnotatedMethod {1}. AnnotatedMethod has {2} as parameters but should have {3} as parameters";
   }

   public final DefinitionException incorrectNumberOfAnnotatedParametersMethod(Object param1, Object param2, Object param3, Object param4) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.incorrectNumberOfAnnotatedParametersMethod$str(), param1, param2, param3, param4));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String reflectionfactoryInstantiationFailed$str() {
      return "WELD-000616: Instantiation through ReflectionFactory of {0} failed";
   }

   public final WeldException reflectionfactoryInstantiationFailed(Object param1, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.reflectionfactoryInstantiationFailed$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unsafeInstantiationFailed$str() {
      return "WELD-000617: Instantiation through Unsafe of {0} failed";
   }

   public final WeldException unsafeInstantiationFailed(Object param1, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.unsafeInstantiationFailed$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void lifecycleCallbackInterceptorWithInvalidBindingTarget(Object interceptor, Object binding, Object elementTypes) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.lifecycleCallbackInterceptorWithInvalidBindingTarget$str(), interceptor, binding, elementTypes);
   }

   protected String lifecycleCallbackInterceptorWithInvalidBindingTarget$str() {
      return "WELD-000619: A lifecycle callback interceptor declares an interceptor binding with target other than ElementType.TYPE\n  {0}\n  Binding: {1}\n  Target: {2}";
   }

   public final void missingTargetMethodFieldParameterType(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.missingTargetMethodFieldParameterType$str(), param1);
   }

   protected String missingTargetMethodFieldParameterType$str() {
      return "WELD-000620: {0} is not declared @Target(METHOD, FIELD, PARAMETER, TYPE). Weld will use this annotation, however this may make the application unportable.";
   }

   public final void invalidInterceptorBindingTargetDeclaration(Object param1, Object param2, Object param3, Object param4) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.invalidInterceptorBindingTargetDeclaration$str(), new Object[]{param1, param2, param3, param4});
   }

   protected String invalidInterceptorBindingTargetDeclaration$str() {
      return "WELD-000621: Interceptor binding {0} with @Target defined as {1} should not be applied on interceptor binding {2} with @Target definition: {3}";
   }

   protected String illegalArgumentExceptionOnReflectionInvocation$str() {
      return "WELD-000622: IllegalArgumentException invoking {2} on {1} ({0}) with parameters {3}";
   }

   public final WeldException illegalArgumentExceptionOnReflectionInvocation(Class clazz, Object instance, Method method, String parameters, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.illegalArgumentExceptionOnReflectionInvocation$str(), clazz, instance, method, parameters), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unknownType$str() {
      return "WELD-000623: Unknown type {0}.";
   }

   public final IllegalArgumentException unknownType(Type type) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.unknownType$str(), type));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidTypeArgumentCombination$str() {
      return "WELD-000624: Invalid type argument combination: {0}; {1}.";
   }

   public final IllegalArgumentException invalidTypeArgumentCombination(Type type1, Type type2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidTypeArgumentCombination$str(), type1, type2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noSuchMethodWrapper$str() {
      return "WELD-000625: Unable to locate method: {0}";
   }

   public final WeldException noSuchMethodWrapper(NoSuchMethodException cause, String message) {
      WeldException result = new WeldException(MessageFormat.format(this.noSuchMethodWrapper$str(), message), cause);
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
