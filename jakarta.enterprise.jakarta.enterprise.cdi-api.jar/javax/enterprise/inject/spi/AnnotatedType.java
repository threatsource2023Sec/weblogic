package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public interface AnnotatedType extends Annotated {
   Class getJavaClass();

   Set getConstructors();

   Set getMethods();

   Set getFields();

   default Set getAnnotations(Class annotationType) {
      Annotation[] annotationsByType = this.getJavaClass().getAnnotationsByType(annotationType);
      return new LinkedHashSet(Arrays.asList(annotationsByType));
   }
}
