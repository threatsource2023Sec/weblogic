package org.jboss.weld.util.bean;

import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ForwardingBeanAttributes implements BeanAttributes {
   protected abstract BeanAttributes attributes();

   public Set getTypes() {
      return this.attributes().getTypes();
   }

   public Set getQualifiers() {
      return this.attributes().getQualifiers();
   }

   public Class getScope() {
      return this.attributes().getScope();
   }

   public String getName() {
      return this.attributes().getName();
   }

   public Set getStereotypes() {
      return this.attributes().getStereotypes();
   }

   public boolean isAlternative() {
      return this.attributes().isAlternative();
   }

   public int hashCode() {
      return this.attributes().hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof ForwardingBeanAttributes ? this.attributes().equals(((ForwardingBeanAttributes)Reflections.cast(obj)).attributes()) : this.attributes().equals(obj);
   }
}
