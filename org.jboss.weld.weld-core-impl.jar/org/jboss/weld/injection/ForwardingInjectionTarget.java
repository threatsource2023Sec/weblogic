package org.jboss.weld.injection;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionTarget;

public abstract class ForwardingInjectionTarget implements InjectionTarget {
   protected abstract InjectionTarget delegate();

   public void inject(Object instance, CreationalContext ctx) {
      this.delegate().inject(instance, ctx);
   }

   public void postConstruct(Object instance) {
      this.delegate().postConstruct(instance);
   }

   public void preDestroy(Object instance) {
      this.delegate().preDestroy(instance);
   }

   public Object produce(CreationalContext ctx) {
      return this.delegate().produce(ctx);
   }

   public void dispose(Object instance) {
      this.delegate().dispose(instance);
   }

   public Set getInjectionPoints() {
      return this.delegate().getInjectionPoints();
   }
}
