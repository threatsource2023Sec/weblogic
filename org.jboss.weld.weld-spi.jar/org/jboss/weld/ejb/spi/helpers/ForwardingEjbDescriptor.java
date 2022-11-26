package org.jboss.weld.ejb.spi.helpers;

import java.util.Collection;
import org.jboss.weld.ejb.spi.EjbDescriptor;

public abstract class ForwardingEjbDescriptor implements EjbDescriptor {
   protected abstract EjbDescriptor delegate();

   public Collection getLocalBusinessInterfaces() {
      return this.delegate().getLocalBusinessInterfaces();
   }

   public Collection getRemoteBusinessInterfaces() {
      return this.delegate().getRemoteBusinessInterfaces();
   }

   public Collection getRemoveMethods() {
      return this.delegate().getRemoveMethods();
   }

   public Class getBeanClass() {
      return this.delegate().getBeanClass();
   }

   public String getEjbName() {
      return this.delegate().getEjbName();
   }

   public boolean isMessageDriven() {
      return this.delegate().isMessageDriven();
   }

   public boolean isSingleton() {
      return this.delegate().isSingleton();
   }

   public boolean isStateful() {
      return this.delegate().isStateful();
   }

   public boolean isStateless() {
      return this.delegate().isStateless();
   }

   public boolean isPassivationCapable() {
      return this.delegate().isPassivationCapable();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof ForwardingEjbDescriptor ? this.delegate().equals(((ForwardingEjbDescriptor)ForwardingEjbDescriptor.class.cast(obj)).delegate()) : this.delegate().equals(obj);
      }
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
