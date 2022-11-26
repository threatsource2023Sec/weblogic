package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public interface AnnotatedField extends AnnotatedMember {
   Field getJavaMember();

   default Set getAnnotations(Class annotationType) {
      Annotation[] annotationsByType = this.getJavaMember().getAnnotationsByType(annotationType);
      return new LinkedHashSet(Arrays.asList(annotationsByType));
   }
}
