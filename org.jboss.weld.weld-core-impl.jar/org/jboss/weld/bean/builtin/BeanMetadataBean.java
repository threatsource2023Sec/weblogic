package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.BeanIdentifiers;
import org.jboss.weld.bean.StringBeanIdentifier;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.bean.SerializableForwardingBean;
import org.jboss.weld.util.reflection.Reflections;

public class BeanMetadataBean extends AbstractBuiltInMetadataBean {
   public BeanMetadataBean(BeanManagerImpl beanManager) {
      this(new StringBeanIdentifier(BeanIdentifiers.forBuiltInBean(beanManager, Bean.class, (String)null)), beanManager);
   }

   protected BeanMetadataBean(BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(identifier, (Class)Reflections.cast(Bean.class), beanManager);
   }

   protected Bean newInstance(InjectionPoint ip, CreationalContext creationalContext) {
      Contextual contextual = this.getParentCreationalContext(creationalContext).getContextual();
      if (contextual instanceof Bean) {
         return (Bean)(contextual instanceof Serializable ? (Bean)Reflections.cast(contextual) : SerializableForwardingBean.of(this.getBeanManager().getContextId(), (Bean)contextual));
      } else {
         throw new IllegalArgumentException("Unable to determine Bean metadata for " + ip);
      }
   }
}
