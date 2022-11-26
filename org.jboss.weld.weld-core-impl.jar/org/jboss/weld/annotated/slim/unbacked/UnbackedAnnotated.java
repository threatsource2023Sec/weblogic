package org.jboss.weld.annotated.slim.unbacked;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.annotated.slim.BaseAnnotated;
import org.jboss.weld.util.reflection.Reflections;

public abstract class UnbackedAnnotated extends BaseAnnotated {
   private final Set annotations;
   private final Set typeClosure;

   public UnbackedAnnotated(Type baseType, Set typeClosure, Set annotations) {
      super(baseType);
      this.typeClosure = typeClosure;
      this.annotations = annotations;
   }

   public Annotation getAnnotation(Class annotationType) {
      Iterator var2 = this.annotations.iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         annotation = (Annotation)var2.next();
      } while(!annotation.annotationType().equals(annotationType));

      return (Annotation)Reflections.cast(annotation);
   }

   public Set getAnnotations() {
      return this.annotations;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.getAnnotation(annotationType) != null;
   }

   public Set getTypeClosure() {
      return this.typeClosure;
   }
}
