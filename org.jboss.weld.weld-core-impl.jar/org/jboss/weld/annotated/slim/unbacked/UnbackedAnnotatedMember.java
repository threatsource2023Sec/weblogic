package org.jboss.weld.annotated.slim.unbacked;

import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMember;
import org.jboss.weld.util.reflection.Reflections;

public abstract class UnbackedAnnotatedMember extends UnbackedAnnotated implements AnnotatedMember {
   private UnbackedAnnotatedType declaringType;

   public UnbackedAnnotatedMember(Type baseType, Set typeClosure, Set annotations, UnbackedAnnotatedType declaringType) {
      super(baseType, typeClosure, annotations);
      this.declaringType = declaringType;
   }

   public boolean isStatic() {
      return Reflections.isStatic(this.getJavaMember());
   }

   public UnbackedAnnotatedType getDeclaringType() {
      return this.declaringType;
   }
}
