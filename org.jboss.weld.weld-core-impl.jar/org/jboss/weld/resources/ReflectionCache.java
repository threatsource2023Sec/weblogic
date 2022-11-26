package org.jboss.weld.resources;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;
import org.jboss.weld.bootstrap.api.Service;

public interface ReflectionCache extends Service {
   Set getAnnotations(AnnotatedElement var1);

   Set getDeclaredAnnotations(AnnotatedElement var1);

   Set getBackedAnnotatedTypeAnnotationSet(Class var1);

   AnnotationClass getAnnotationClass(Class var1);

   public interface AnnotationClass {
      Set getMetaAnnotations();

      boolean isScope();

      boolean isRepeatableAnnotationContainer();

      Annotation[] getRepeatableAnnotations(Annotation var1);
   }
}
