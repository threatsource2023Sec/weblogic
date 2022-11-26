package org.jboss.weld.bean.interceptor;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class CdiInterceptorFactory implements InterceptorFactory {
   private final Interceptor interceptor;

   public CdiInterceptorFactory(Interceptor interceptor) {
      this.interceptor = interceptor;
   }

   public Object create(CreationalContext ctx, BeanManagerImpl manager) {
      return Reflections.cast(manager.getReference(this.interceptor, this.interceptor.getBeanClass(), ctx, true));
   }

   public Interceptor getInterceptor() {
      return this.interceptor;
   }

   public int hashCode() {
      return this.interceptor.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof CdiInterceptorFactory) {
         CdiInterceptorFactory that = (CdiInterceptorFactory)obj;
         return this.interceptor.equals(that.interceptor);
      } else {
         return false;
      }
   }

   public String toString() {
      return "CdiInterceptorFactory [interceptor=" + this.interceptor + "]";
   }
}
