package org.jboss.weld.ejb.spi.helpers;

import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;

public abstract class ForwadingBusinessInterfaceDescriptor implements BusinessInterfaceDescriptor {
   protected abstract BusinessInterfaceDescriptor delegate();

   public Class getInterface() {
      return this.delegate().getInterface();
   }

   public boolean equals(Object obj) {
      return this.delegate().equals(obj);
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
