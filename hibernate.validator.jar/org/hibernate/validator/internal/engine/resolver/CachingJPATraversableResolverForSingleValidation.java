package org.hibernate.validator.internal.engine.resolver;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import javax.validation.Path;
import javax.validation.TraversableResolver;

class CachingJPATraversableResolverForSingleValidation implements TraversableResolver {
   private final TraversableResolver delegate;
   private final HashMap traversables = new HashMap();

   public CachingJPATraversableResolverForSingleValidation(TraversableResolver delegate) {
      this.delegate = delegate;
   }

   public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class rootBeanType, Path pathToTraversableObject, ElementType elementType) {
      return traversableObject == null ? true : (Boolean)this.traversables.computeIfAbsent(new TraversableHolder(traversableObject, traversableProperty), (th) -> {
         return this.delegate.isReachable(traversableObject, traversableProperty, rootBeanType, pathToTraversableObject, elementType);
      });
   }

   public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class rootBeanType, Path pathToTraversableObject, ElementType elementType) {
      return true;
   }

   private static class TraversableHolder extends AbstractTraversableHolder {
      private TraversableHolder(Object traversableObject, Path.Node traversableProperty) {
         super(traversableObject, traversableProperty);
      }

      // $FF: synthetic method
      TraversableHolder(Object x0, Path.Node x1, Object x2) {
         this(x0, x1);
      }
   }
}
