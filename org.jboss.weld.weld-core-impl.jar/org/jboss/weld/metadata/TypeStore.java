package org.jboss.weld.metadata;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.enterprise.context.NormalScope;
import javax.inject.Scope;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.util.collections.SetMultimap;

public class TypeStore implements Service {
   private final SetMultimap extraAnnotations = SetMultimap.newConcurrentSetMultimap(CopyOnWriteArraySet::new);
   private final Set extraScopes = new CopyOnWriteArraySet();

   public Set get(Class annotationType) {
      return (Set)this.extraAnnotations.get(annotationType);
   }

   public void add(Class annotationType, Annotation annotation) {
      if (annotation.annotationType().equals(Scope.class) || annotation.annotationType().equals(NormalScope.class)) {
         this.extraScopes.add(annotationType);
      }

      this.extraAnnotations.put(annotationType, annotation);
   }

   public boolean isExtraScope(Class annotation) {
      return this.extraScopes.contains(annotation);
   }

   public void cleanup() {
      this.extraAnnotations.clear();
   }
}
