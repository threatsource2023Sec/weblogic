package org.jboss.weld.bean.builtin;

import java.util.List;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractStaticallyDecorableBuiltInBean extends AbstractDecorableBuiltInBean {
   private List decorators;
   private Class proxyClass;

   protected AbstractStaticallyDecorableBuiltInBean(BeanManagerImpl beanManager, Class type) {
      super(beanManager, type);
   }

   protected List getDecorators(InjectionPoint ip) {
      return this.decorators;
   }

   protected Class getProxyClass() {
      if (this.proxyClass == null) {
         throw new IllegalStateException("No decorators were resolved for this bean at boot time however there are some now");
      } else {
         return this.proxyClass;
      }
   }

   public void initializeAfterBeanDiscovery() {
      this.decorators = this.beanManager.resolveDecorators(this.getTypes(), this.getQualifiers());
      if (!this.decorators.isEmpty()) {
         this.proxyClass = (new ProxyFactory(this.getBeanManager().getContextId(), this.getType(), this.getTypes(), this)).getProxyClass();
      }

   }
}
