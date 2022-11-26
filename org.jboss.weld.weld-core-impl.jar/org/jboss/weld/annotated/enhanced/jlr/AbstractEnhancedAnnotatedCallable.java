package org.jboss.weld.annotated.enhanced.jlr;

import java.util.Arrays;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.backed.BackedAnnotatedMember;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.resources.ClassTransformer;

public abstract class AbstractEnhancedAnnotatedCallable extends AbstractEnhancedAnnotatedMember implements EnhancedAnnotatedCallable {
   protected AbstractEnhancedAnnotatedCallable(AnnotatedCallable annotatedCallable, Map annotationMap, Map declaredAnnotationMap, ClassTransformer classTransformer, EnhancedAnnotatedType declaringType) {
      super(annotatedCallable, annotationMap, declaredAnnotationMap, classTransformer, declaringType);
   }

   protected static void validateParameterCount(AnnotatedCallable callable) {
      if (!(callable instanceof BackedAnnotatedMember)) {
         Class[] parameterTypes = null;
         if (callable instanceof AnnotatedConstructor) {
            parameterTypes = ((AnnotatedConstructor)AnnotatedConstructor.class.cast(callable)).getJavaMember().getParameterTypes();
         } else {
            parameterTypes = ((AnnotatedMethod)AnnotatedMethod.class.cast(callable)).getJavaMember().getParameterTypes();
         }

         if (callable.getParameters().size() != parameterTypes.length) {
            Class declaringClass = callable.getDeclaringType().getJavaClass();
            if (!declaringClass.isEnum() && !declaringClass.isMemberClass()) {
               throw ReflectionLogger.LOG.incorrectNumberOfAnnotatedParametersMethod(callable.getParameters().size(), callable, callable.getParameters(), Arrays.asList(parameterTypes));
            }
         }

      }
   }
}
