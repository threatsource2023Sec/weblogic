package org.jboss.weld.annotated.slim.backed;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedCallable;
import org.jboss.weld.resources.SharedObjectCache;

public abstract class BackedAnnotatedCallable extends BackedAnnotatedMember implements AnnotatedCallable {
   private final List parameters;
   private final Executable executable;

   public BackedAnnotatedCallable(Executable executable, Type baseType, BackedAnnotatedType declaringType, SharedObjectCache sharedObjectCache) {
      super(baseType, declaringType, sharedObjectCache);
      this.executable = executable;
      this.parameters = this.initParameters(executable, sharedObjectCache);
   }

   protected List initParameters(Executable member, SharedObjectCache sharedObjectCache) {
      return BackedAnnotatedParameter.forExecutable(member, this, sharedObjectCache);
   }

   public Executable getJavaMember() {
      return this.executable;
   }

   public List getParameters() {
      return this.parameters;
   }

   public Annotation getAnnotation(Class annotationType) {
      return this.executable.getAnnotation(annotationType);
   }

   protected AnnotatedElement getAnnotatedElement() {
      return this.executable;
   }

   public boolean isAnnotationPresent(Class annotationType) {
      return this.executable.isAnnotationPresent(annotationType);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.executable == null ? 0 : this.executable.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         BackedAnnotatedCallable other = (BackedAnnotatedCallable)obj;
         if (this.executable == null) {
            if (other.executable != null) {
               return false;
            }
         } else if (!this.executable.equals(other.executable)) {
            return false;
         }

         return true;
      }
   }
}
