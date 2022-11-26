package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public interface AnnotatedParameter extends Annotated {
   int getPosition();

   AnnotatedCallable getDeclaringCallable();

   default Parameter getJavaParameter() {
      Member member = this.getDeclaringCallable().getJavaMember();
      if (!(member instanceof Executable)) {
         throw new IllegalStateException("Parameter does not belong to an executable: " + member);
      } else {
         Executable executable = (Executable)member;
         return executable.getParameters()[this.getPosition()];
      }
   }

   default Set getAnnotations(Class annotationType) {
      Annotation[] annotationsByType = this.getJavaParameter().getAnnotationsByType(annotationType);
      return new LinkedHashSet(Arrays.asList(annotationsByType));
   }
}
