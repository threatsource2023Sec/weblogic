package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractEnhancedAnnotatedMember extends AbstractEnhancedAnnotated implements EnhancedAnnotatedMember {
   private String toString;
   private final EnhancedAnnotatedType declaringType;

   protected AbstractEnhancedAnnotatedMember(AnnotatedMember annotatedMember, Map annotationMap, Map declaredAnnotationMap, ClassTransformer classTransformer, EnhancedAnnotatedType declaringType) {
      super(annotatedMember, annotationMap, declaredAnnotationMap, classTransformer);
      this.declaringType = declaringType;
   }

   public boolean isStatic() {
      return Reflections.isStatic((Member)this.getDelegate());
   }

   public boolean isFinal() {
      return Reflections.isFinal((Member)this.getDelegate());
   }

   public boolean isTransient() {
      return Reflections.isTransient((Member)this.getDelegate());
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.getJavaMember().getModifiers());
   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.getJavaMember().getModifiers());
   }

   public boolean isPackagePrivate() {
      return Reflections.isPackagePrivate(this.getJavaMember().getModifiers());
   }

   public Package getPackage() {
      return this.getJavaMember().getDeclaringClass().getPackage();
   }

   public String getName() {
      return this.getJavaMember().getName();
   }

   public String toString() {
      if (this.toString != null) {
         return this.toString;
      } else {
         this.toString = "Abstract annotated member " + this.getName();
         return this.toString;
      }
   }

   public Member getJavaMember() {
      return (Member)this.getDelegate();
   }

   public EnhancedAnnotatedType getDeclaringType() {
      return this.declaringType;
   }
}
