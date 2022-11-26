package org.glassfish.hk2.runlevel.internal;

import org.glassfish.hk2.api.Descriptor;

public class WouldBlockException extends Exception {
   private static final long serialVersionUID = 3273389390415705962L;

   public WouldBlockException(Descriptor d) {
      super("This descriptor would block: " + d);
   }
}
