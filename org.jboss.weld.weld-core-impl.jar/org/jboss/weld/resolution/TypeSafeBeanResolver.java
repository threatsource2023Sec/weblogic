package org.jboss.weld.resolution;

import java.util.Set;
import org.jboss.weld.manager.BeanManagerImpl;

public class TypeSafeBeanResolver extends AbstractTypeSafeBeanResolver {
   public TypeSafeBeanResolver(BeanManagerImpl beanManager, Iterable beans) {
      super(beanManager, beans);
   }

   protected Set sortResult(Set matched) {
      return matched;
   }
}
