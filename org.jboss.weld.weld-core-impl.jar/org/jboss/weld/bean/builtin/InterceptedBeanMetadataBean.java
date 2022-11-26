package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Intercepted;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.literal.InterceptedLiteral;
import org.jboss.weld.logging.InterceptorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.bean.SerializableForwardingBean;

public class InterceptedBeanMetadataBean extends BeanMetadataBean {
   public InterceptedBeanMetadataBean(BeanManagerImpl beanManager) {
      this(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, Bean.class, Intercepted.class.getSimpleName())), beanManager);
   }

   protected InterceptedBeanMetadataBean(BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(identifier, beanManager);
   }

   protected Bean newInstance(InjectionPoint ip, CreationalContext ctx) {
      this.checkInjectionPoint(ip);
      WeldCreationalContext interceptorContext = this.getParentCreationalContext(ctx);
      WeldCreationalContext interceptedBeanContext = this.getParentCreationalContext(interceptorContext);
      Contextual interceptedContextual = interceptedBeanContext.getContextual();
      if (interceptedContextual instanceof Bean) {
         Bean bean = (Bean)interceptedContextual;
         return (Bean)(bean instanceof Serializable ? bean : SerializableForwardingBean.of(this.getBeanManager().getContextId(), bean));
      } else {
         InterceptorLogger.LOG.unableToDetermineInterceptedBean(ip);
         return null;
      }
   }

   protected void checkInjectionPoint(InjectionPoint ip) {
      if (!(ip.getBean() instanceof Interceptor)) {
         throw InterceptorLogger.LOG.interceptedBeanCanOnlyBeInjectedIntoInterceptor(ip);
      }
   }

   public Set getQualifiers() {
      return Collections.singleton(InterceptedLiteral.INSTANCE);
   }

   public String toString() {
      return "Implicit Bean [javax.enterprise.inject.spi.Bean] with qualifiers [@Intercepted]";
   }
}
