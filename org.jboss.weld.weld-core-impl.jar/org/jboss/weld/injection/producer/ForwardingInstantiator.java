package org.jboss.weld.injection.producer;

import java.lang.reflect.Constructor;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.manager.BeanManagerImpl;

public class ForwardingInstantiator implements Instantiator {
   private final Instantiator delegate;

   public ForwardingInstantiator(Instantiator delegate) {
      this.delegate = delegate;
   }

   protected Instantiator delegate() {
      return this.delegate;
   }

   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      return this.delegate().newInstance(ctx, manager);
   }

   public boolean hasInterceptorSupport() {
      return this.delegate().hasInterceptorSupport();
   }

   public boolean hasDecoratorSupport() {
      return this.delegate().hasDecoratorSupport();
   }

   public Constructor getConstructor() {
      return this.delegate().getConstructor();
   }
}
