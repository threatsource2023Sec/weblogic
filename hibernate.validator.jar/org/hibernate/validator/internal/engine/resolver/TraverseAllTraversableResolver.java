package org.hibernate.validator.internal.engine.resolver;

import java.lang.annotation.ElementType;
import javax.validation.Path;
import javax.validation.TraversableResolver;

class TraverseAllTraversableResolver implements TraversableResolver {
   public boolean isReachable(Object traversableObject, Path.Node traversableProperty, Class rootBeanType, Path pathToTraversableObject, ElementType elementType) {
      return true;
   }

   public boolean isCascadable(Object traversableObject, Path.Node traversableProperty, Class rootBeanType, Path pathToTraversableObject, ElementType elementType) {
      return true;
   }
}
