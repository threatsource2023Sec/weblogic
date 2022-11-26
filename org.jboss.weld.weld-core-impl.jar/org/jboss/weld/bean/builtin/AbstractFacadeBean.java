package org.jboss.weld.bean.builtin;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractFacadeBean extends AbstractDecorableBuiltInBean {
   private Class proxyClass;

   protected AbstractFacadeBean(BeanManagerImpl manager, Class type) {
      super(manager, type);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      super.destroy(instance, creationalContext);
      creationalContext.release();
   }

   protected Class getProxyClass() {
      return this.proxyClass;
   }

   public void initializeAfterBeanDiscovery() {
      this.proxyClass = (new ProxyFactory(this.getBeanManager().getContextId(), this.getType(), this.getTypes(), this)).getProxyClass();
   }

   protected List getDecorators(InjectionPoint ip) {
      return this.beanManager.resolveDecorators(Collections.singleton(ip.getType()), this.getQualifiers());
   }

   protected InjectionPoint getInjectionPoint(CurrentInjectionPoint cip) {
      InjectionPoint ip = super.getInjectionPoint(cip);
      if (ip == null) {
         ip = new DynamicLookupInjectionPoint(EmptyInjectionPoint.INSTANCE, this.getDefaultType(), Collections.emptySet());
      }

      return (InjectionPoint)ip;
   }

   protected abstract Type getDefaultType();
}
