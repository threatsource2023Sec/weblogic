package org.glassfish.hk2.configuration.hub.internal;

import org.glassfish.hk2.configuration.hub.api.Instance;

public class InstanceImpl implements Instance {
   private final Object bean;
   private Object metadata;

   InstanceImpl(Object bean, Object metadata) {
      this.bean = bean;
      this.metadata = metadata;
   }

   public Object getBean() {
      return this.bean;
   }

   public synchronized Object getMetadata() {
      return this.metadata;
   }

   public void setMetadata(Object metadata) {
      this.metadata = metadata;
   }

   public String toString() {
      return "InstanceImpl(" + this.bean + "," + this.metadata + "," + System.identityHashCode(this) + ")";
   }
}
