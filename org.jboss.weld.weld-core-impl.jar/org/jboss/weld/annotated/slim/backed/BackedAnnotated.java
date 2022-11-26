package org.jboss.weld.annotated.slim.backed;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Set;
import org.jboss.weld.annotated.slim.BaseAnnotated;
import org.jboss.weld.resources.ReflectionCache;
import org.jboss.weld.resources.SharedObjectCache;
import org.jboss.weld.util.LazyValueHolder;

public abstract class BackedAnnotated extends BaseAnnotated {
   private final LazyValueHolder typeClosure;

   public BackedAnnotated(Type baseType, SharedObjectCache sharedObjectCache) {
      super(baseType);
      this.typeClosure = this.initTypeClosure(baseType, sharedObjectCache);
   }

   protected LazyValueHolder initTypeClosure(Type baseType, SharedObjectCache cache) {
      return cache.getTypeClosureHolder(baseType);
   }

   public Set getTypeClosure() {
      return (Set)this.typeClosure.get();
   }

   protected abstract AnnotatedElement getAnnotatedElement();

   protected abstract ReflectionCache getReflectionCache();

   public Set getAnnotations() {
      return this.getReflectionCache().getAnnotations(this.getAnnotatedElement());
   }
}
