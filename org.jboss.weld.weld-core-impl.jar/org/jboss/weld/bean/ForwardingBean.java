package org.jboss.weld.bean;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.util.bean.IsolatedForwardingBean;

public abstract class ForwardingBean extends IsolatedForwardingBean {
   public abstract Bean delegate();

   public String toString() {
      return "ForwardingBean " + this.getName() + " for " + this.delegate().toString();
   }

   protected BeanAttributes attributes() {
      return this.delegate();
   }
}
