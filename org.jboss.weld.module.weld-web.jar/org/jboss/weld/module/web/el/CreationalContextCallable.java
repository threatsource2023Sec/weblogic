package org.jboss.weld.module.web.el;

import javax.enterprise.context.spi.Contextual;

class CreationalContextCallable {
   private ELCreationalContext ctx;

   ELCreationalContext get() {
      if (this.ctx == null) {
         this.ctx = new ELCreationalContext((Contextual)null);
      }

      return this.ctx;
   }

   boolean exists() {
      return this.ctx != null;
   }
}
