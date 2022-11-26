package org.jboss.weld.metadata.cache;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.util.Nonbinding;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotation;
import org.jboss.weld.util.collections.ImmutableSet;

public abstract class AbstractBindingModel extends AnnotationModel {
   private Set nonBindingMembers;

   public AbstractBindingModel(EnhancedAnnotation enhancedAnnotatedAnnotation) {
      super(enhancedAnnotatedAnnotation);
   }

   protected void init(EnhancedAnnotation annotatedAnnotation) {
      this.initNonBindingMembers(annotatedAnnotation);
      super.init(annotatedAnnotation);
   }

   protected void initNonBindingMembers(EnhancedAnnotation annotatedAnnotation) {
      Set enhancedMethods = annotatedAnnotation.getMembers(Nonbinding.class);
      if (enhancedMethods.isEmpty()) {
         this.nonBindingMembers = Collections.emptySet();
      } else {
         ImmutableSet.Builder nonBindingMembers = ImmutableSet.builder();
         Iterator var4 = enhancedMethods.iterator();

         while(var4.hasNext()) {
            EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var4.next();
            nonBindingMembers.add(method.slim());
         }

         this.nonBindingMembers = nonBindingMembers.build();
      }

   }

   public Set getNonBindingMembers() {
      return this.nonBindingMembers;
   }
}
