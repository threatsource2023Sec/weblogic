package org.glassfish.grizzly.servlet;

import java.io.Serializable;

class DispatchTargetsInfo implements Serializable {
   private final String[] targets = new String[2];
   private final boolean[] named = new boolean[2];

   void addDispatchTarget(String target, boolean isNamed) {
      this.targets[0] = this.targets[1];
      this.targets[1] = target;
      this.named[0] = this.named[1];
      this.named[1] = isNamed;
   }

   public String getLastDispatchTarget() {
      return this.targets[0];
   }

   public boolean isLastNamedDispatchTarget() {
      return this.named[0];
   }
}
