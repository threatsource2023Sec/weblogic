package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.ForwardingInterceptor;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.BeanHolder;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptorMetadataBean extends AbstractBuiltInMetadataBean {
   public InterceptorMetadataBean(BeanManagerImpl beanManager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, Interceptor.class, (String)null)), (Class)Reflections.cast(Interceptor.class), beanManager);
   }

   protected Interceptor newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      Contextual bean = this.getParentCreationalContext(creationalContext).getContextual();
      if (bean instanceof Interceptor) {
         return InterceptorMetadataBean.SerializableProxy.of(this.getBeanManager().getContextId(), (Interceptor)bean);
      } else {
         throw new IllegalArgumentException("Unable to inject " + bean + " into " + ip);
      }
   }

   private static class SerializableProxy extends ForwardingInterceptor implements Serializable {
      private static final long serialVersionUID = 8482112157695944011L;
      private BeanHolder holder;

      public static SerializableProxy of(String contextId, Bean bean) {
         return new SerializableProxy(contextId, bean);
      }

      protected SerializableProxy(String contextId, Bean bean) {
         this.holder = new BeanHolder(contextId, bean);
      }

      public Interceptor delegate() {
         return (Interceptor)this.holder.get();
      }
   }
}
