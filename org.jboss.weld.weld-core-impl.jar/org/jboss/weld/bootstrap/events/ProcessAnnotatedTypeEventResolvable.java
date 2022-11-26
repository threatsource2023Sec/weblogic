package org.jboss.weld.bootstrap.events;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.backed.BackedAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedType;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.util.collections.Sets;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;

public class ProcessAnnotatedTypeEventResolvable implements Resolvable {
   private static final Set QUALIFIERS;
   private final Set types;
   private final SlimAnnotatedType annotatedType;
   private final RequiredAnnotationDiscovery discovery;

   public static ProcessAnnotatedTypeEventResolvable of(ProcessAnnotatedTypeImpl event, RequiredAnnotationDiscovery discovery) {
      return event instanceof ProcessSyntheticAnnotatedType ? forProcessSyntheticAnnotatedType(event.getOriginalAnnotatedType(), discovery) : forProcessAnnotatedType(event.getOriginalAnnotatedType(), discovery);
   }

   public static ProcessAnnotatedTypeEventResolvable forProcessAnnotatedType(SlimAnnotatedType annotatedType, RequiredAnnotationDiscovery discovery) {
      ParameterizedType type = new ParameterizedTypeImpl(ProcessAnnotatedType.class, new Type[]{annotatedType.getJavaClass()}, (Type)null);
      return new ProcessAnnotatedTypeEventResolvable(Sets.newHashSet(Object.class, type), annotatedType, discovery);
   }

   public static ProcessAnnotatedTypeEventResolvable forProcessSyntheticAnnotatedType(SlimAnnotatedType annotatedType, RequiredAnnotationDiscovery discovery) {
      ParameterizedType type1 = new ParameterizedTypeImpl(ProcessAnnotatedType.class, new Type[]{annotatedType.getJavaClass()}, (Type)null);
      ParameterizedType type2 = new ParameterizedTypeImpl(ProcessSyntheticAnnotatedType.class, new Type[]{annotatedType.getJavaClass()}, (Type)null);
      return new ProcessAnnotatedTypeEventResolvable(Sets.newHashSet(Object.class, type1, type2), annotatedType, discovery);
   }

   protected ProcessAnnotatedTypeEventResolvable(Set types, SlimAnnotatedType annotatedType, RequiredAnnotationDiscovery discovery) {
      this.types = types;
      this.annotatedType = annotatedType;
      this.discovery = discovery;
   }

   public Set getTypes() {
      return this.types;
   }

   public Set getQualifiers() {
      return QUALIFIERS;
   }

   public boolean containsRequiredAnnotations(Collection requiredAnnotations) {
      if (this.annotatedType instanceof BackedAnnotatedType) {
         return this.containsAnnotation((BackedAnnotatedType)this.annotatedType, requiredAnnotations);
      } else if (this.annotatedType instanceof UnbackedAnnotatedType) {
         return this.containsAnnotation((UnbackedAnnotatedType)this.annotatedType, requiredAnnotations);
      } else {
         throw new IllegalArgumentException("Unknown SlimAnnotatedType implementation: " + this.annotatedType.getClass().toString());
      }
   }

   protected boolean containsAnnotation(UnbackedAnnotatedType annotatedType, Collection requiredAnnotations) {
      Iterator var3 = requiredAnnotations.iterator();

      Class requiredAnnotation;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         requiredAnnotation = (Class)var3.next();
      } while(!this.apply(annotatedType, requiredAnnotation));

      return true;
   }

   private static boolean isEqualOrAnnotated(Class requiredAnnotation, Annotation annotation) {
      return annotation.annotationType().equals(requiredAnnotation) || annotation.annotationType().isAnnotationPresent(requiredAnnotation);
   }

   protected boolean apply(UnbackedAnnotatedType annotatedType, Class requiredAnnotation) {
      Iterator var3 = annotatedType.getAnnotations().iterator();

      while(var3.hasNext()) {
         Annotation annotation = (Annotation)var3.next();
         if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
            return true;
         }

         if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
            return true;
         }
      }

      var3 = annotatedType.getFields().iterator();

      Iterator var5;
      Annotation annotation;
      while(var3.hasNext()) {
         AnnotatedField field = (AnnotatedField)var3.next();
         var5 = field.getAnnotations().iterator();

         while(var5.hasNext()) {
            annotation = (Annotation)var5.next();
            if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
               return true;
            }
         }
      }

      var3 = annotatedType.getConstructors().iterator();

      Iterator var7;
      Annotation annotation;
      AnnotatedParameter parameter;
      while(var3.hasNext()) {
         AnnotatedConstructor constructor = (AnnotatedConstructor)var3.next();
         var5 = constructor.getAnnotations().iterator();

         while(var5.hasNext()) {
            annotation = (Annotation)var5.next();
            if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
               return true;
            }
         }

         var5 = constructor.getParameters().iterator();

         while(var5.hasNext()) {
            parameter = (AnnotatedParameter)var5.next();
            var7 = parameter.getAnnotations().iterator();

            while(var7.hasNext()) {
               annotation = (Annotation)var7.next();
               if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
                  return true;
               }
            }
         }
      }

      var3 = annotatedType.getMethods().iterator();

      while(var3.hasNext()) {
         AnnotatedMethod method = (AnnotatedMethod)var3.next();
         var5 = method.getAnnotations().iterator();

         while(var5.hasNext()) {
            annotation = (Annotation)var5.next();
            if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
               return true;
            }
         }

         var5 = method.getParameters().iterator();

         while(var5.hasNext()) {
            parameter = (AnnotatedParameter)var5.next();
            var7 = parameter.getAnnotations().iterator();

            while(var7.hasNext()) {
               annotation = (Annotation)var7.next();
               if (isEqualOrAnnotated(requiredAnnotation, annotation)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   protected boolean containsAnnotation(BackedAnnotatedType annotatedType, Collection requiredAnnotations) {
      Iterator var3 = requiredAnnotations.iterator();

      Class requiredAnnotation;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         requiredAnnotation = (Class)var3.next();
      } while(!this.discovery.containsAnnotation(annotatedType, requiredAnnotation));

      return true;
   }

   public Class getJavaClass() {
      return null;
   }

   public Bean getDeclaringBean() {
      return null;
   }

   public boolean isDelegate() {
      return false;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.types == null ? 0 : this.types.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof ProcessAnnotatedTypeEventResolvable)) {
         return false;
      } else {
         ProcessAnnotatedTypeEventResolvable other = (ProcessAnnotatedTypeEventResolvable)obj;
         if (this.types == null) {
            if (other.types != null) {
               return false;
            }
         } else if (!this.types.equals(other.types)) {
            return false;
         }

         return true;
      }
   }

   static {
      QUALIFIERS = Collections.singleton(QualifierInstance.ANY);
   }
}
