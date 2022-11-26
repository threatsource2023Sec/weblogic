package org.jboss.weld.bean.builtin;

import java.util.Collections;
import java.util.List;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.DecorableBean;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.injection.CurrentInjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Decorators;

public abstract class AbstractDecorableBuiltInBean extends AbstractBuiltInBean implements DecorableBean {
   private final CurrentInjectionPoint cip;

   protected AbstractDecorableBuiltInBean(BeanManagerImpl beanManager, Class type) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, type, (String)null)), beanManager, type);
      this.cip = (CurrentInjectionPoint)beanManager.getServices().get(CurrentInjectionPoint.class);
   }

   public Object create(CreationalContext creationalContext) {
      InjectionPoint ip = this.getInjectionPoint(this.cip);
      List decorators = this.getDecorators(ip);
      Object instance = this.newInstance(ip, creationalContext);
      if (decorators == null) {
         decorators = this.beanManager.resolveDecorators(Collections.singleton(ip.getType()), this.getQualifiers());
      }

      return decorators.isEmpty() ? instance : Decorators.getOuterDelegate(this, instance, creationalContext, this.getProxyClass(), (InjectionPoint)this.cip.peek(), this.getBeanManager(), decorators);
   }

   protected abstract Object newInstance(InjectionPoint var1, CreationalContext var2);

   protected abstract List getDecorators(InjectionPoint var1);

   protected abstract Class getProxyClass();

   protected InjectionPoint getInjectionPoint(CurrentInjectionPoint cip) {
      InjectionPoint ip = (InjectionPoint)cip.peek();
      return EmptyInjectionPoint.INSTANCE.equals(ip) ? null : ip;
   }

   public Class getBeanClass() {
      return this.getClass();
   }

   public List getDecorators() {
      return this.beanManager.resolveDecorators(this.getTypes(), this.getQualifiers());
   }
}
