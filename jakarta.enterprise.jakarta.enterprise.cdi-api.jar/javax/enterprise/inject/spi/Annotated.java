package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

public interface Annotated {
   Type getBaseType();

   Set getTypeClosure();

   Annotation getAnnotation(Class var1);

   Set getAnnotations(Class var1);

   Set getAnnotations();

   boolean isAnnotationPresent(Class var1);
}
