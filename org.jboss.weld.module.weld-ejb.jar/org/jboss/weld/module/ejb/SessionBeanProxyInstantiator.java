package org.jboss.weld.module.ejb;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.bean.proxy.EnterpriseTargetBeanInstance;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.producer.Instantiator;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.NewInstanceAction;

class SessionBeanProxyInstantiator implements Instantiator {
   private final Class proxyClass;
   private final SessionBeanImpl bean;

   SessionBeanProxyInstantiator(EnhancedAnnotatedType type, SessionBeanImpl bean) {
      this.bean = bean;
      this.proxyClass = (new EnterpriseProxyFactory(type.getJavaClass(), bean)).getProxyClass();
   }

   public Object newInstance(CreationalContext ctx, BeanManagerImpl manager) {
      try {
         Object instance = AccessController.doPrivileged(NewInstanceAction.of(this.proxyClass));
         if (!this.bean.getScope().equals(Dependent.class)) {
            ctx.push(instance);
         }

         ProxyFactory.setBeanInstance(this.bean.getBeanManager().getContextId(), instance, this.createEnterpriseTargetBeanInstance(), this.bean);
         return instance;
      } catch (PrivilegedActionException var4) {
         if (var4.getCause() instanceof InstantiationException) {
            throw new WeldException(BeanLogger.LOG.proxyInstantiationFailed(this), var4.getCause());
         } else if (var4.getCause() instanceof IllegalAccessException) {
            throw new WeldException(BeanLogger.LOG.proxyInstantiationBeanAccessFailed(this), var4.getCause());
         } else {
            throw new WeldException(var4.getCause());
         }
      } catch (Exception var5) {
         throw BeanLogger.LOG.sessionBeanProxyInstantiationFailed(this.bean, this.proxyClass, var5);
      }
   }

   protected EnterpriseTargetBeanInstance createEnterpriseTargetBeanInstance() {
      return (EnterpriseTargetBeanInstance)(!this.bean.getEjbDescriptor().isStateless() && !this.bean.getEjbDescriptor().isSingleton() ? new EnterpriseTargetBeanInstance(this.bean.getBeanClass(), new EnterpriseBeanProxyMethodHandler(this.bean)) : new InjectionPointPropagatingEnterpriseTargetBeanInstance(this.bean.getBeanClass(), new EnterpriseBeanProxyMethodHandler(this.bean), this.bean.getBeanManager()));
   }

   public boolean hasInterceptorSupport() {
      return false;
   }

   public boolean hasDecoratorSupport() {
      return false;
   }

   public SessionBean getBean() {
      return this.bean;
   }

   public Constructor getConstructor() {
      return null;
   }
}
