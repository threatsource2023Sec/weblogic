package org.jboss.weld.injection.producer;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.manager.api.WeldInjectionTarget;
import org.jboss.weld.manager.api.WeldInjectionTargetBuilder;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.InjectionTargets;

public class WeldInjectionTargetBuilderImpl implements WeldInjectionTargetBuilder, PrivilegedAction {
   private final InjectionTargetService injectionTargetService;
   private boolean resourceInjectionEnabled = true;
   private boolean targetClassLifecycleCallbacksEnabled = true;
   private boolean interceptorsEnabled = true;
   private boolean decorationEnabled = true;
   private Bean bean;
   private final EnhancedAnnotatedType type;
   private final BeanManagerImpl manager;

   public WeldInjectionTargetBuilderImpl(AnnotatedType type, BeanManagerImpl manager) {
      this.manager = manager;
      this.type = ((ClassTransformer)manager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(type, manager.getId());
      this.injectionTargetService = (InjectionTargetService)manager.getServices().get(InjectionTargetService.class);
   }

   public WeldInjectionTargetBuilder setResourceInjectionEnabled(boolean value) {
      this.resourceInjectionEnabled = value;
      return this;
   }

   public WeldInjectionTargetBuilder setTargetClassLifecycleCallbacksEnabled(boolean value) {
      this.targetClassLifecycleCallbacksEnabled = value;
      return this;
   }

   public WeldInjectionTargetBuilder setInterceptionEnabled(boolean value) {
      this.interceptorsEnabled = value;
      return this;
   }

   public WeldInjectionTargetBuilder setDecorationEnabled(boolean value) {
      this.decorationEnabled = value;
      return this;
   }

   public WeldInjectionTargetBuilder setBean(Bean bean) {
      this.bean = bean;
      return this;
   }

   public WeldInjectionTarget build() {
      return (WeldInjectionTarget)(System.getSecurityManager() != null ? (WeldInjectionTarget)AccessController.doPrivileged(this) : this.run());
   }

   public BasicInjectionTarget run() {
      BasicInjectionTarget injectionTarget = this.buildInternal();
      this.injectionTargetService.addInjectionTargetToBeInitialized(this.type, injectionTarget);
      this.injectionTargetService.validateProducer(injectionTarget);
      return injectionTarget;
   }

   private BasicInjectionTarget buildInternal() {
      Injector injector = this.buildInjector();
      LifecycleCallbackInvoker invoker = this.buildInvoker();
      NonProducibleInjectionTarget nonProducible = InjectionTargets.createNonProducibleInjectionTarget(this.type, this.bean, injector, invoker, this.manager);
      if (nonProducible != null) {
         return nonProducible;
      } else if (!this.interceptorsEnabled && !this.decorationEnabled) {
         return BasicInjectionTarget.create(this.type, this.bean, this.manager, injector, invoker);
      } else if (this.interceptorsEnabled && this.decorationEnabled) {
         return new BeanInjectionTarget(this.type, this.bean, this.manager, injector, invoker);
      } else {
         throw new IllegalStateException("Unsupported combination: [interceptorsEnabled=" + this.interceptorsEnabled + ", decorationEnabled=" + this.decorationEnabled + "]");
      }
   }

   private Injector buildInjector() {
      return (Injector)(this.resourceInjectionEnabled ? ResourceInjector.of(this.type, this.bean, this.manager) : DefaultInjector.of(this.type, this.bean, this.manager));
   }

   private LifecycleCallbackInvoker buildInvoker() {
      return (LifecycleCallbackInvoker)(this.targetClassLifecycleCallbacksEnabled ? DefaultLifecycleCallbackInvoker.of(this.type) : NoopLifecycleCallbackInvoker.getInstance());
   }

   public String toString() {
      return "WeldInjectionTargetBuilderImpl for " + this.type;
   }
}
