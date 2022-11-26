package org.jboss.weld.bootstrap.events.configurator;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import javax.enterprise.inject.spi.Annotated;
import org.jboss.weld.util.Preconditions;

abstract class AnnotatedConfigurator {
   private final Annotated annotated;
   private final Set annotations;

   AnnotatedConfigurator(Annotated annotated) {
      this.annotated = annotated;
      this.annotations = new HashSet(annotated.getAnnotations());
   }

   public Annotated getAnnotated() {
      return this.annotated;
   }

   public AnnotatedConfigurator add(Annotation annotation) {
      Preconditions.checkArgumentNotNull(annotation);
      this.annotations.add(annotation);
      return this.self();
   }

   public AnnotatedConfigurator remove(Predicate predicate) {
      Preconditions.checkArgumentNotNull(predicate);
      Iterator iterator = this.annotations.iterator();

      while(iterator.hasNext()) {
         if (predicate.test(iterator.next())) {
            iterator.remove();
         }
      }

      return this.self();
   }

   public AnnotatedConfigurator removeAll() {
      this.annotations.clear();
      return this.self();
   }

   protected abstract AnnotatedConfigurator self();

   Set getAnnotations() {
      return this.annotations;
   }
}
