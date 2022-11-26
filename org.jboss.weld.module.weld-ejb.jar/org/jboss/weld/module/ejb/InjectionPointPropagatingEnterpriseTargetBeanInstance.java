package org.jboss.weld.module.ejb;

import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.Container;
import org.jboss.weld.bean.proxy.EnterpriseTargetBeanInstance;
import org.jboss.weld.bean.proxy.MethodHandler;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.injection.ThreadLocalStack;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.InjectionPointHolder;

class InjectionPointPropagatingEnterpriseTargetBeanInstance extends EnterpriseTargetBeanInstance {
   private static final long serialVersionUID = 166825647603520280L;
   private final InjectionPointHolder injectionPointHolder;
   private final String contextId;
   private transient CurrentInvocationInjectionPoint currentInvocationInjectionPoint;

   InjectionPointPropagatingEnterpriseTargetBeanInstance(Class baseType, MethodHandler methodHandler, BeanManagerImpl manager) {
      super(baseType, methodHandler);
      this.contextId = manager.getContextId();
      this.currentInvocationInjectionPoint = (CurrentInvocationInjectionPoint)manager.getServices().get(CurrentInvocationInjectionPoint.class);
      InjectionPoint ip = (InjectionPoint)((CurrentInjectionPoint)manager.getServices().get(CurrentInjectionPoint.class)).peek();
      if (ip != null) {
         this.injectionPointHolder = new InjectionPointHolder(manager.getContextId(), ip);
      } else {
         this.injectionPointHolder = null;
      }

   }

   public Object invoke(Object instance, Method method, Object... arguments) throws Throwable {
      ThreadLocalStack.ThreadLocalStackReference stack = null;
      if (this.injectionPointHolder != null) {
         stack = this.currentInvocationInjectionPoint.push(this.injectionPointHolder.get());
      } else {
         stack = this.currentInvocationInjectionPoint.push(EmptyInjectionPoint.INSTANCE);
      }

      Object var5;
      try {
         var5 = super.invoke(instance, method, arguments);
      } finally {
         stack.pop();
      }

      return var5;
   }

   private Object readResolve() throws ObjectStreamException {
      this.currentInvocationInjectionPoint = (CurrentInvocationInjectionPoint)Container.instance(this.contextId).services().get(CurrentInvocationInjectionPoint.class);
      return this;
   }
}
