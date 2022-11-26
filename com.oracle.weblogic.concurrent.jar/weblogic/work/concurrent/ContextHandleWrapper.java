package weblogic.work.concurrent;

import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class ContextHandleWrapper {
   private final ContextHandle handle;
   private final ContextProvider contextSetupProcessor;

   ContextHandleWrapper(ContextProvider contextSetupProcessor, ContextHandle contextHandleForSetup) {
      this.handle = contextSetupProcessor.setup(contextHandleForSetup);
      this.contextSetupProcessor = contextSetupProcessor;
   }

   public void restore() {
      this.contextSetupProcessor.reset(this.handle);
   }
}
