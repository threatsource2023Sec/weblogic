package org.jboss.weld.injection.spi.helpers;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.spi.JpaInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

public abstract class ForwardingJpaInjectionServices implements JpaInjectionServices {
   protected abstract JpaInjectionServices delegate();

   public ResourceReferenceFactory registerPersistenceContextInjectionPoint(InjectionPoint injectionPoint) {
      return this.delegate().registerPersistenceContextInjectionPoint(injectionPoint);
   }

   public ResourceReferenceFactory registerPersistenceUnitInjectionPoint(InjectionPoint injectionPoint) {
      return this.delegate().registerPersistenceUnitInjectionPoint(injectionPoint);
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }
}
