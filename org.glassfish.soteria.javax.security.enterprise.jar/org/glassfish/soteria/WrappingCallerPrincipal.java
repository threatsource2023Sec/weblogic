package org.glassfish.soteria;

import java.security.Principal;
import javax.security.enterprise.CallerPrincipal;

public class WrappingCallerPrincipal extends CallerPrincipal {
   private final Principal wrapped;

   public WrappingCallerPrincipal(Principal wrapped) {
      super(wrapped.getName());
      this.wrapped = wrapped;
   }

   public Principal getWrapped() {
      return this.wrapped;
   }
}
