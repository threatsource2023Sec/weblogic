package org.jboss.weld.serialization;

import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.Container;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;

public class BeanHolder extends AbstractSerializableHolder {
   private static final long serialVersionUID = 6039992808930111222L;
   private final String contextId;
   private final BeanIdentifier beanId;

   public static BeanHolder of(String contextId, Bean bean) {
      return new BeanHolder(contextId, bean);
   }

   public BeanHolder(String contextId, Bean bean) {
      super(bean);
      this.contextId = contextId;
      if (bean == null) {
         this.beanId = null;
      } else {
         this.beanId = ((ContextualStore)Container.instance(contextId).services().get(ContextualStore.class)).putIfAbsent(bean);
      }

   }

   protected Bean initialize() {
      return this.beanId == null ? null : (Bean)((ContextualStore)Container.instance(this.contextId).services().get(ContextualStore.class)).getContextual(this.beanId);
   }
}
