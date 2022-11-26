package com.sun.faces.cdi;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

@Dependent
public class InjectionPointGenerator {
   @Inject
   private InjectionPoint injectionPoint;

   public InjectionPoint getInjectionPoint() {
      return this.injectionPoint;
   }
}
