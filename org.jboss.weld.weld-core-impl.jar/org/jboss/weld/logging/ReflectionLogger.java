package org.jboss.weld.logging;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.WeldException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ReflectionLogger extends WeldLogger {
   ReflectionLogger LOG = (ReflectionLogger)Logger.getMessageLogger(ReflectionLogger.class, Category.REFLECTION.getName());

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 600,
      value = "{0} is missing @Retention(RUNTIME). Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingRetention(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 601,
      value = "{0} is missing @Target. Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingTarget(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 602,
      value = "{0} is not declared @Target(TYPE, METHOD) or @Target(TYPE). Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingTargetTypeMethodOrTargetType(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 604,
      value = "{0} is not declared @Target(METHOD, FIELD, TYPE). Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingTargetMethodFieldType(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 605,
      value = "{0} is not declared @Target(METHOD, FIELD, TYPE, PARAMETER), @Target(METHOD, TYPE), @Target(METHOD), @Target(TYPE) or @Target(FIELD). Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingTargetMethodFieldTypeParameterOrTargetMethodTypeOrTargetMethodOrTargetTypeOrTargetField(Object var1);

   @Message(
      id = 606,
      value = "Unable to determine name of parameter"
   )
   IllegalArgumentException unableToGetParameterName();

   @Message(
      id = 607,
      value = "annotationMap cannot be null"
   )
   WeldException annotationMapNull();

   @Message(
      id = 608,
      value = "declaredAnnotationMap cannot be null"
   )
   WeldException declaredAnnotationMapNull();

   @Message(
      id = 610,
      value = "Unable to deserialize constructor. Declaring class {0}, index {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException unableToGetConstructorOnDeserialization(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 611,
      value = "Unable to deserialize method. Declaring class {0}, index {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException unableToGetMethodOnDeserialization(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 612,
      value = "Unable to deserialize field. Declaring class {0}, field name {1}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException unableToGetFieldOnDeserialization(Object var1, Object var2, @Cause Throwable var3);

   @Message(
      id = 614,
      value = "Incorrect number of AnnotatedParameters {0} on AnnotatedMethod {1}. AnnotatedMethod has {2} as parameters but should have {3} as parameters",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException incorrectNumberOfAnnotatedParametersMethod(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 616,
      value = "Instantiation through ReflectionFactory of {0} failed",
      format = Format.MESSAGE_FORMAT
   )
   WeldException reflectionfactoryInstantiationFailed(Object var1, @Cause Throwable var2);

   @Message(
      id = 617,
      value = "Instantiation through Unsafe of {0} failed",
      format = Format.MESSAGE_FORMAT
   )
   WeldException unsafeInstantiationFailed(Object var1, @Cause Throwable var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 619,
      value = "A lifecycle callback interceptor declares an interceptor binding with target other than ElementType.TYPE\n  {0}\n  Binding: {1}\n  Target: {2}",
      format = Format.MESSAGE_FORMAT
   )
   void lifecycleCallbackInterceptorWithInvalidBindingTarget(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 620,
      value = "{0} is not declared @Target(METHOD, FIELD, PARAMETER, TYPE). Weld will use this annotation, however this may make the application unportable.",
      format = Format.MESSAGE_FORMAT
   )
   void missingTargetMethodFieldParameterType(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 621,
      value = "Interceptor binding {0} with @Target defined as {1} should not be applied on interceptor binding {2} with @Target definition: {3}",
      format = Format.MESSAGE_FORMAT
   )
   void invalidInterceptorBindingTargetDeclaration(Object var1, Object var2, Object var3, Object var4);

   @Message(
      id = 622,
      value = "IllegalArgumentException invoking {2} on {1} ({0}) with parameters {3}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException illegalArgumentExceptionOnReflectionInvocation(Class var1, Object var2, Method var3, String var4, @Cause Throwable var5);

   @Message(
      id = 623,
      value = "Unknown type {0}.",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException unknownType(Type var1);

   @Message(
      id = 624,
      value = "Invalid type argument combination: {0}; {1}.",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException invalidTypeArgumentCombination(Type var1, Type var2);

   @Message(
      id = 625,
      value = "Unable to locate method: {0}",
      format = Format.MESSAGE_FORMAT
   )
   WeldException noSuchMethodWrapper(@Cause NoSuchMethodException var1, String var2);
}
