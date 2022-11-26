package org.glassfish.grizzly.filterchain;

final class ForkAction extends AbstractNextAction {
   static final int TYPE = 5;
   private final FilterChainContext context;

   ForkAction(FilterChainContext context) {
      super(5);
      this.context = context;
   }

   FilterChainContext getContext() {
      return this.context;
   }
}
