package org.glassfish.grizzly.filterchain;

final class RerunChainAction extends AbstractNextAction {
   static final int TYPE = 3;

   RerunChainAction() {
      super(3);
   }
}
