package org.jboss.weld.bootstrap;

import java.util.Set;
import org.jboss.weld.bootstrap.api.TypeDiscoveryConfiguration;
import org.jboss.weld.util.collections.ImmutableSet;

public class TypeDiscoveryConfigurationImpl implements TypeDiscoveryConfiguration {
   private final Set beanDefiningAnnotations;

   protected TypeDiscoveryConfigurationImpl(Set beanDefiningAnnotations) {
      this.beanDefiningAnnotations = ImmutableSet.copyOf(beanDefiningAnnotations);
   }

   public Set getKnownBeanDefiningAnnotations() {
      return this.beanDefiningAnnotations;
   }

   public String toString() {
      return "TypeDiscoveryConfigurationImpl [beanDefiningAnnotations=" + this.beanDefiningAnnotations + "]";
   }
}
