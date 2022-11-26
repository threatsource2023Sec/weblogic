package org.jboss.weld.util;

import java.lang.annotation.Annotation;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.InterceptorBindingModel;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;

public class InterceptorBindingSet extends AbstractSet {
   private BeanManagerImpl beanManager;
   private Set set = new HashSet();

   public InterceptorBindingSet(BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
   }

   public boolean add(Annotation annotation) {
      return this.contains(annotation) ? false : this.set.add(annotation);
   }

   public Iterator iterator() {
      return this.set.iterator();
   }

   public int size() {
      return this.set.size();
   }

   public boolean contains(Object o) {
      if (o instanceof Annotation) {
         Annotation annotation = (Annotation)o;
         MetaAnnotationStore metaAnnotationStore = (MetaAnnotationStore)this.beanManager.getServices().get(MetaAnnotationStore.class);
         InterceptorBindingModel interceptorBindingModel = metaAnnotationStore.getInterceptorBindingModel(annotation.annotationType());
         Iterator var5 = this.set.iterator();

         Annotation containedAnnotation;
         do {
            if (!var5.hasNext()) {
               return false;
            }

            containedAnnotation = (Annotation)var5.next();
         } while(!interceptorBindingModel.isEqual(annotation, containedAnnotation));

         return true;
      } else {
         return super.contains(o);
      }
   }
}
