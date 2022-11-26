package org.jboss.weld.bean;

import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Decorator;
import org.jboss.weld.util.bean.IsolatedForwardingDecorator;

public abstract class ForwardingDecorator extends IsolatedForwardingDecorator {
   public abstract Decorator delegate();

   protected BeanAttributes attributes() {
      return this.delegate();
   }

   public String toString() {
      return "ForwardingDecorator wrapping " + this.delegate().toString();
   }
}
