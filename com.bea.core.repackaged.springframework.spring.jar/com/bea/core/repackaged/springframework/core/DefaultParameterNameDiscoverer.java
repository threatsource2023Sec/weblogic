package com.bea.core.repackaged.springframework.core;

public class DefaultParameterNameDiscoverer extends PrioritizedParameterNameDiscoverer {
   public DefaultParameterNameDiscoverer() {
      if (!GraalDetector.inImageCode()) {
         if (KotlinDetector.isKotlinReflectPresent()) {
            this.addDiscoverer(new KotlinReflectionParameterNameDiscoverer());
         }

         this.addDiscoverer(new StandardReflectionParameterNameDiscoverer());
         this.addDiscoverer(new LocalVariableTableParameterNameDiscoverer());
      }

   }
}
