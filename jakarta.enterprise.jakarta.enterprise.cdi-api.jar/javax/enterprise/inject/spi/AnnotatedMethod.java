package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public interface AnnotatedMethod extends AnnotatedCallable {
   Method getJavaMember();

   default Set getAnnotations(Class annotationType) {
      Annotation[] annotationsByType = this.getJavaMember().getAnnotationsByType(annotationType);
      return new LinkedHashSet(Arrays.asList(annotationsByType));
   }
}
