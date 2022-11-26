package org.glassfish.hk2.runlevel.internal;

import org.glassfish.hk2.api.Descriptor;

public class WasCancelledException extends Exception {
   private static final long serialVersionUID = 4891861012857549581L;

   public WasCancelledException(Descriptor d) {
      super("A run-level service with name \"" + (d.getName() == null ? "" : d.getName()) + "\" and implementation " + d.getImplementation() + " was cancelled");
   }
}
