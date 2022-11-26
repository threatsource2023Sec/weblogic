package org.jboss.weld.util;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ForwardingContext implements Context {
   protected abstract Context delegate();

   public Class getScope() {
      return this.delegate().getScope();
   }

   public Object get(Contextual contextual, CreationalContext creationalContext) {
      return this.delegate().get(contextual, creationalContext);
   }

   public Object get(Contextual contextual) {
      return this.delegate().get(contextual);
   }

   public boolean isActive() {
      return this.delegate().isActive();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      if (obj instanceof ForwardingContext) {
         ForwardingContext that = (ForwardingContext)obj;
         return this.delegate().equals(that.delegate());
      } else {
         return this.delegate().equals(obj);
      }
   }

   public String toString() {
      return this.delegate().toString();
   }

   public static Context unwrap(Context context) {
      return context instanceof ForwardingContext ? ((ForwardingContext)Reflections.cast(context)).delegate() : context;
   }
}
