package org.jboss.weld.util.annotated;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class AnnotatedTypeWrapper extends ForwardingAnnotatedType {
   private final AnnotatedType delegate;
   private final Set annotations;

   public AnnotatedTypeWrapper(AnnotatedType delegate, Annotation... additionalAnnotations) {
      this(delegate, true, additionalAnnotations);
   }

   public AnnotatedTypeWrapper(AnnotatedType delegate, boolean keepOriginalAnnotations, Annotation... additionalAnnotations) {
      this.delegate = delegate;
      ImmutableSet.Builder builder = ImmutableSet.builder();
      if (keepOriginalAnnotations) {
         builder.addAll((Iterable)delegate.getAnnotations());
      }

      Annotation[] var5 = additionalAnnotations;
      int var6 = additionalAnnotations.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Annotation annotation = var5[var7];
         builder.add(annotation);
      }

      this.annotations = builder.build();
   }

   public AnnotatedType delegate() {
      return this.delegate;
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
}
