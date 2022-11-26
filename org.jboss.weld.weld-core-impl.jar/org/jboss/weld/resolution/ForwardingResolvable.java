package org.jboss.weld.resolution;

import java.util.Set;
import javax.enterprise.inject.spi.Bean;

public abstract class ForwardingResolvable implements Resolvable {
   protected abstract Resolvable delegate();

   public Set getQualifiers() {
      return this.delegate().getQualifiers();
   }

   public Set getTypes() {
      return this.delegate().getTypes();
   }

   public Class getJavaClass() {
      return this.delegate().getJavaClass();
   }

   public Bean getDeclaringBean() {
      return this.delegate().getDeclaringBean();
   }

   public boolean isDelegate() {
      return this.delegate().isDelegate();
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }
}
