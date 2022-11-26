package org.glassfish.grizzly.filterchain;

final class SuspendAction extends AbstractNextAction {
   static final int TYPE = 2;

   SuspendAction() {
      super(2);
   }
}
