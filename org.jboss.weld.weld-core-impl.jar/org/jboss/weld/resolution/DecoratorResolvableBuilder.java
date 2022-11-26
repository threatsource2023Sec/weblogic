package org.jboss.weld.resolution;

import org.jboss.weld.manager.BeanManagerImpl;

public class DecoratorResolvableBuilder extends ResolvableBuilder {
   public DecoratorResolvableBuilder(BeanManagerImpl manager) {
      super(manager);
   }

   public Resolvable create() {
      if (this.qualifierInstances.size() == 0) {
         this.qualifierInstances.add(QualifierInstance.DEFAULT);
      }

      return new ResolvableBuilder.ResolvableImpl(this.rawType, this.types, this.declaringBean, this.qualifierInstances, true);
   }
}
