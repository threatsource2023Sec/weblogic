package org.python.google.common.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.FluentIterable;
import org.python.google.common.collect.ImmutableList;
import org.python.google.common.collect.UnmodifiableIterator;

@Beta
public final class Parameter implements AnnotatedElement {
   private final Invokable declaration;
   private final int position;
   private final TypeToken type;
   private final ImmutableList annotations;

   Parameter(Invokable declaration, int position, TypeToken type, Annotation[] annotations) {
      this.declaration = declaration;
      this.position = position;
      this.type = type;
      this.annotations = ImmutableList.copyOf((Object[])annotations);
   }

   public TypeToken getType() {
      return this.type;
   }

   public Invokable getDeclaringInvokable() {
      return this.declaration;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.getAnnotation(annotationType) != null;
   }

   @Nullable
   public Annotation getAnnotation(Class annotationType) {
      Preconditions.checkNotNull(annotationType);
      UnmodifiableIterator var2 = this.annotations.iterator();

      Annotation annotation;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         annotation = (Annotation)var2.next();
      } while(!annotationType.isInstance(annotation));

      return (Annotation)annotationType.cast(annotation);
   }

   public Annotation[] getAnnotations() {
      return this.getDeclaredAnnotations();
   }

   public Annotation[] getAnnotationsByType(Class annotationType) {
      return this.getDeclaredAnnotationsByType(annotationType);
   }

   public Annotation[] getDeclaredAnnotations() {
      return (Annotation[])this.annotations.toArray(new Annotation[this.annotations.size()]);
   }

   @Nullable
   public Annotation getDeclaredAnnotation(Class annotationType) {
      Preconditions.checkNotNull(annotationType);
      return (Annotation)FluentIterable.from((Iterable)this.annotations).filter(annotationType).first().orNull();
   }

   public Annotation[] getDeclaredAnnotationsByType(Class annotationType) {
      return (Annotation[])FluentIterable.from((Iterable)this.annotations).filter(annotationType).toArray(annotationType);
   }

   public boolean equals(@Nullable Object obj) {
      if (!(obj instanceof Parameter)) {
         return false;
      } else {
         Parameter that = (Parameter)obj;
         return this.position == that.position && this.declaration.equals(that.declaration);
      }
   }

   public int hashCode() {
      return this.position;
   }

   public String toString() {
      return this.type + " arg" + this.position;
   }
}
