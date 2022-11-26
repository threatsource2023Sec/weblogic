package weblogic.security.auth.callback;

import javax.security.auth.callback.Callback;
import weblogic.security.service.ContextHandler;

public class ContextHandlerCallback implements Callback {
   private ContextHandler contextHandler = null;

   public ContextHandler getContextHandler() {
      return this.contextHandler;
   }

   public void setContextHandler(ContextHandler contextHandler) {
      this.contextHandler = contextHandler;
   }
}
