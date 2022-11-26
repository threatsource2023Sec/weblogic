package weblogic.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class BaseCallbackHandler implements CallbackHandler {
   private List strategies = new ArrayList();
   protected String message = "Unrecognized Callback";

   protected void addCallbackStrategies(CallbackStrategy... strategies) {
      this.strategies.addAll(Arrays.asList(strategies));
   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      if (callbacks != null) {
         Callback[] var2 = callbacks;
         int var3 = callbacks.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Callback callback = var2[var4];
            this.handle(callback);
         }

      }
   }

   private void handle(Callback callback) throws UnsupportedCallbackException {
      Iterator var2 = this.strategies.iterator();

      CallbackStrategy strategy;
      do {
         if (!var2.hasNext()) {
            throw new UnsupportedCallbackException(callback, this.message + ": " + callback.getClass());
         }

         strategy = (CallbackStrategy)var2.next();
      } while(!strategy.mayHandle(callback));

      strategy.handle(callback);
   }

   public interface CallbackStrategy {
      boolean mayHandle(Callback var1);

      void handle(Callback var1);
   }
}
