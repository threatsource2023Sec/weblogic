package weblogic.servlet.security.css;

import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.BaseCallbackHandler;
import weblogic.security.auth.callback.ContextHandlerCallback;
import weblogic.servlet.provider.WlsSecurityProvider;
import weblogic.servlet.security.internal.ServletCallbackHandler;

public class CSSServletCallbackHandler extends ServletCallbackHandler {
   public CSSServletCallbackHandler(String username, Object password, HttpServletRequest req, HttpServletResponse rsp) {
      super(username, password, req);
      this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new ContextHandlerCallbackStrategy(req, rsp)});
   }

   class ContextHandlerCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private HttpServletRequest request;
      private HttpServletResponse response;

      public ContextHandlerCallbackStrategy(HttpServletRequest request, HttpServletResponse response) {
         this.request = request;
         this.response = response;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof ContextHandlerCallback;
      }

      public void handle(Callback callback) {
         ContextHandlerCallback ch = (ContextHandlerCallback)callback;
         ch.setContextHandler(WlsSecurityProvider.getContextHandler(this.request, this.response));
      }
   }
}
