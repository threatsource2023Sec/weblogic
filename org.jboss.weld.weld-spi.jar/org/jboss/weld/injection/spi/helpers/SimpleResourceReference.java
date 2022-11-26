package org.jboss.weld.injection.spi.helpers;

import java.io.Serializable;
import org.jboss.weld.injection.spi.ResourceReference;

public class SimpleResourceReference implements ResourceReference, Serializable {
   private static final long serialVersionUID = -4908795910866810739L;
   private final Object instance;

   public SimpleResourceReference(Object instance) {
      this.instance = instance;
   }

   public Object getInstance() {
      return this.instance;
   }

   public void release() {
   }
}
