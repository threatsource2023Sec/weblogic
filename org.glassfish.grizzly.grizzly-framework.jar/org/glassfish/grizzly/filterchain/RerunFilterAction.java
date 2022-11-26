package org.glassfish.grizzly.filterchain;

public class RerunFilterAction extends AbstractNextAction {
   static final int TYPE = 4;

   RerunFilterAction() {
      super(4);
   }
}
