package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.collections.SetMultimap;

public class EnhancedAnnotationImpl extends EnhancedAnnotatedTypeImpl implements EnhancedAnnotation {
   private final SetMultimap annotatedMembers;
   private final Class clazz;
   private final Set members;

   public static EnhancedAnnotation create(SlimAnnotatedType annotatedType, ClassTransformer classTransformer) {
      Class annotationType = annotatedType.getJavaClass();
      Map annotationMap = new HashMap();
      annotationMap.putAll(buildAnnotationMap(annotatedType.getAnnotations()));
      annotationMap.putAll(buildAnnotationMap(classTransformer.getTypeStore().get(annotationType)));
      return new EnhancedAnnotationImpl(annotatedType, annotationMap, annotationMap, classTransformer);
   }

   protected EnhancedAnnotationImpl(SlimAnnotatedType annotatedType, Map annotationMap, Map declaredAnnotationMap, ClassTransformer classTransformer) {
      super(annotatedType, annotationMap, declaredAnnotationMap, classTransformer);
      this.clazz = annotatedType.getJavaClass();
      this.members = new HashSet();
      this.annotatedMembers = SetMultimap.newSetMultimap();
      Iterator var5 = annotatedType.getMethods().iterator();

      while(var5.hasNext()) {
         AnnotatedMethod annotatedMethod = (AnnotatedMethod)var5.next();
         EnhancedAnnotatedMethod enhancedAnnotatedMethod = EnhancedAnnotatedMethodImpl.of(annotatedMethod, this, classTransformer);
         this.members.add(enhancedAnnotatedMethod);
         Iterator var8 = enhancedAnnotatedMethod.getAnnotations().iterator();

         while(var8.hasNext()) {
            Annotation annotation = (Annotation)var8.next();
            this.annotatedMembers.put(annotation.annotationType(), enhancedAnnotatedMethod);
         }
      }

   }

   protected Set getOverriddenMethods(EnhancedAnnotatedType annotatedType, Set methods, boolean skipOverridingBridgeMethods) {
      return Collections.emptySet();
   }

   public Set getMembers() {
      return Collections.unmodifiableSet(this.members);
   }

   public Set getMembers(Class annotationType) {
      return Collections.unmodifiableSet((Set)this.annotatedMembers.get(annotationType));
   }

   public String toString() {
      return this.getJavaClass().toString();
   }

   public Class getDelegate() {
      return this.clazz;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         EnhancedAnnotationImpl that = (EnhancedAnnotationImpl)this.cast(obj);
         return super.equals(that);
      }
   }
}
