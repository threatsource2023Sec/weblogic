package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.ForwardingDecorator;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.BeanHolder;
import org.jboss.weld.util.reflection.Reflections;

public class DecoratorMetadataBean extends AbstractBuiltInMetadataBean {
   public DecoratorMetadataBean(BeanManagerImpl beanManager) {
      super(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, Decorator.class, (String)null)), (Class)Reflections.cast(Decorator.class), beanManager);
   }

   protected Decorator newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      Contextual bean = this.getParentCreationalContext(creationalContext).getContextual();
      if (bean instanceof Decorator) {
         return DecoratorMetadataBean.SerializableProxy.of(this.getBeanManager().getContextId(), (Decorator)bean);
      } else {
         throw new IllegalArgumentException("Unable to inject " + bean + " into " + ip);
      }
   }

   private static class SerializableProxy extends ForwardingDecorator implements Serializable {
      private static final long serialVersionUID = 398927939412634913L;
      private BeanHolder holder;

      public static SerializableProxy of(String contextId, Bean bean) {
         return new SerializableProxy(contextId, bean);
      }

      protected SerializableProxy(String contextId, Bean bean) {
         this.holder = new BeanHolder(contextId, bean);
      }

      public Decorator delegate() {
         return (Decorator)this.holder.get();
      }
   }
}
