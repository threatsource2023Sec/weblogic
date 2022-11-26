package org.jboss.weld.injection.spi.helpers;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.spi.ResourceInjectionServices;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;

public abstract class ForwardingResourceInjectionServices implements ResourceInjectionServices {
   protected abstract ResourceInjectionServices delegate();

   public ResourceReferenceFactory registerResourceInjectionPoint(InjectionPoint injectionPoint) {
      return this.delegate().registerResourceInjectionPoint(injectionPoint);
   }

   public ResourceReferenceFactory registerResourceInjectionPoint(String jndiName, String mappedName) {
      return this.delegate().registerResourceInjectionPoint(jndiName, mappedName);
   }
}
