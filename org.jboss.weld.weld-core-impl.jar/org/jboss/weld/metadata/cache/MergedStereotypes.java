package org.jboss.weld.metadata.cache;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.Stereotype;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotated;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.SharedObjectCache;

public class MergedStereotypes {
   private final Set possibleScopeTypes = new HashSet();
   private boolean beanNameDefaulted;
   private boolean alternative;
   private Set stereotypes = new HashSet();
   private final BeanManagerImpl manager;

   public static MergedStereotypes of(EnhancedAnnotated annotated, BeanManagerImpl manager) {
      return of(annotated.getMetaAnnotations(Stereotype.class), manager);
   }

   public static MergedStereotypes of(Set stereotypeAnnotations, BeanManagerImpl manager) {
      return new MergedStereotypes(stereotypeAnnotations, manager);
   }

   protected MergedStereotypes(Set stereotypeAnnotations, BeanManagerImpl manager) {
      this.manager = manager;
      this.merge(stereotypeAnnotations);
      this.stereotypes = SharedObjectCache.instance(manager).getSharedSet(this.stereotypes);
   }

   protected void merge(Set stereotypeAnnotations) {
      MetaAnnotationStore store = (MetaAnnotationStore)this.manager.getServices().get(MetaAnnotationStore.class);
      Iterator var3 = stereotypeAnnotations.iterator();

      while(var3.hasNext()) {
         Annotation stereotypeAnnotation = (Annotation)var3.next();
         StereotypeModel stereotype = store.getStereotype(stereotypeAnnotation.annotationType());
         if (stereotype == null) {
            throw MetadataLogger.LOG.stereotypeNotRegistered(stereotypeAnnotation);
         }

         if (stereotype.isAlternative()) {
            this.alternative = true;
         }

         if (stereotype.getDefaultScopeType() != null) {
            this.possibleScopeTypes.add(stereotype.getDefaultScopeType());
         }

         if (stereotype.isBeanNameDefaulted()) {
            this.beanNameDefaulted = true;
         }

         this.stereotypes.add(stereotypeAnnotation.annotationType());
         this.merge(stereotype.getInheritedStereotypes());
      }

   }

   public boolean isAlternative() {
      return this.alternative;
   }

   public Set getPossibleScopes() {
      return this.possibleScopeTypes;
   }

   public boolean isBeanNameDefaulted() {
      return this.beanNameDefaulted;
   }

   public Set getStereotypes() {
      return this.stereotypes;
   }

   public String toString() {
      return "Merged stereotype model; Any of the stereotypes is an alternative: " + this.alternative + "; possible scopes " + this.possibleScopeTypes;
   }
}
