package org.jboss.weld.ejb.spi.helpers;

import org.jboss.weld.ejb.api.SessionObjectReference;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.ejb.spi.InterceptorBindings;

public abstract class ForwardingEjbServices implements EjbServices {
   public abstract EjbServices delegate();

   public SessionObjectReference resolveEjb(EjbDescriptor ejbDescriptor) {
      return this.delegate().resolveEjb(ejbDescriptor);
   }

   public void registerInterceptors(EjbDescriptor ejbDescriptor, InterceptorBindings interceptorBindings) {
      this.delegate().registerInterceptors(ejbDescriptor, interceptorBindings);
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
