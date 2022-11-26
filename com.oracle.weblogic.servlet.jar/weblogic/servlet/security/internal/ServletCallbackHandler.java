package weblogic.servlet.security.internal;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.TextInputCallback;
import javax.servlet.http.HttpServletRequest;
import weblogic.security.BaseCallbackHandler;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.auth.callback.URLCallback;

public class ServletCallbackHandler extends SimpleCallbackHandler {
   public ServletCallbackHandler(String username, Object password, HttpServletRequest request) {
      super(username, password == null ? null : ((String)password).toCharArray());
      this.addCallbackStrategies(new BaseCallbackHandler.CallbackStrategy[]{new TextInputCallbackStrategy(request), new URLCallbackStrategy(request)});
   }

   class URLCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private HttpServletRequest request;

      public URLCallbackStrategy(HttpServletRequest request) {
         this.request = request;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof URLCallback;
      }

      public void handle(Callback callback) {
         URLCallback uc = (URLCallback)callback;
         uc.setURL(this.request.getRequestURL().toString());
      }
   }

   class TextInputCallbackStrategy implements BaseCallbackHandler.CallbackStrategy {
      private HttpServletRequest request;

      public TextInputCallbackStrategy(HttpServletRequest request) {
         this.request = request;
      }

      public boolean mayHandle(Callback callback) {
         return callback instanceof TextInputCallback;
      }

      public void handle(Callback callback) {
         TextInputCallback tic = (TextInputCallback)callback;
         String field = tic.getPrompt();
         if (field != null) {
            String value = this.request.getParameter(field);
            tic.setText(value == null ? tic.getDefaultText() : value);
         }

      }
   }
}
