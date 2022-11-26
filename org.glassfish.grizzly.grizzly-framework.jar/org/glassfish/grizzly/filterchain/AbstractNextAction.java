package org.glassfish.grizzly.filterchain;

abstract class AbstractNextAction implements NextAction {
   protected final int type;

   protected AbstractNextAction(int type) {
      this.type = type;
   }

   public final int type() {
      return this.type;
   }
}
