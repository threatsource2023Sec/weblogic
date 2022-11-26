package org.jboss.weld.injection.producer;

import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;

public class InjectionTargetInitializationContext {
   private final EnhancedAnnotatedType enhancedAnnotatedType;
   private final BasicInjectionTarget injectionTarget;

   public InjectionTargetInitializationContext(EnhancedAnnotatedType enhancedAnnotatedType, BasicInjectionTarget injectionTarget) {
      this.enhancedAnnotatedType = enhancedAnnotatedType;
      this.injectionTarget = injectionTarget;
   }

   public void initialize() {
      this.injectionTarget.initializeAfterBeanDiscovery(this.enhancedAnnotatedType);
   }

   public BasicInjectionTarget getInjectionTarget() {
      return this.injectionTarget;
   }

   public String toString() {
      return "InjectionTargetInitializationContext for " + this.injectionTarget;
   }
}
