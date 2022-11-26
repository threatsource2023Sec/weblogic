package org.jboss.weld.contexts;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public abstract class ForwardingContextual implements Contextual {
   protected abstract Contextual delegate();

   public Object create(CreationalContext creationalContext) {
      return this.delegate().create(creationalContext);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      this.delegate().destroy(instance, creationalContext);
   }

   public boolean equals(Object obj) {
      if (obj instanceof ForwardingContextual) {
         ForwardingContextual that = (ForwardingContextual)obj;
         return this.delegate().equals(that.delegate());
      } else {
         return this == obj || this.delegate().equals(obj);
      }
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }
}
