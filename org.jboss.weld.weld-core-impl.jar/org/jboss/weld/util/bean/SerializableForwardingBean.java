package org.jboss.weld.util.bean;

import java.io.Serializable;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.bean.ForwardingBean;
import org.jboss.weld.serialization.BeanHolder;

public class SerializableForwardingBean extends ForwardingBean implements Serializable {
   private static final long serialVersionUID = 6857565199244590365L;
   private final BeanHolder holder;

   public static SerializableForwardingBean of(String contextId, Bean bean) {
      return new SerializableForwardingBean(contextId, bean);
   }

   public SerializableForwardingBean(String contextId, Bean bean) {
      this.holder = new BeanHolder(contextId, bean);
   }

   public Bean delegate() {
      return (Bean)this.holder.get();
   }
}
