package org.jboss.weld.bean.builtin.ee;

import java.util.concurrent.Callable;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.builtin.AbstractStaticallyDecorableBuiltInBean;
import org.jboss.weld.bean.builtin.CallableMethodHandler;
import org.jboss.weld.bean.proxy.EnterpriseTargetBeanInstance;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractEEBean extends AbstractStaticallyDecorableBuiltInBean {
   private final Object proxy;

   protected AbstractEEBean(Class type, Callable callable, BeanManagerImpl beanManager) {
      super(beanManager, type);
      this.proxy = (new ProxyFactory(beanManager.getContextId(), type, this.getTypes(), this)).create(new EnterpriseTargetBeanInstance(type, new CallableMethodHandler(callable)));
   }

   protected Object newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      return this.proxy;
   }
}
