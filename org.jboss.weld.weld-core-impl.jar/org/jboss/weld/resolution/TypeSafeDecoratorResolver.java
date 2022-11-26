package org.jboss.weld.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.Decorator;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;

public class TypeSafeDecoratorResolver extends AbstractTypeSafeBeanResolver {
   private final AssignabilityRules rules = DelegateInjectionPointAssignabilityRules.instance();

   public TypeSafeDecoratorResolver(BeanManagerImpl manager, Iterable decorators) {
      super(manager, decorators);
   }

   protected boolean matches(Resolvable resolvable, Decorator bean) {
      return this.rules.matches(Collections.singleton(bean.getDelegateType()), resolvable.getTypes()) && Beans.containsAllQualifiers(QualifierInstance.of(bean.getDelegateQualifiers(), this.getStore()), resolvable.getQualifiers()) && this.getBeanManager().getEnabled().isDecoratorEnabled(bean.getBeanClass());
   }

   protected Iterable getAllBeans(Resolvable resolvable) {
      return this.getAllBeans();
   }

   protected List sortResult(Set matchedDecorators) {
      List sortedDecorators = new ArrayList(matchedDecorators);
      Collections.sort(sortedDecorators, this.getBeanManager().getEnabled().getDecoratorComparator());
      return sortedDecorators;
   }
}
