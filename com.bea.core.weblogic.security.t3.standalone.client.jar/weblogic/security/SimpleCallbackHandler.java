package weblogic.security;

import java.io.UnsupportedEncodingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class SimpleCallbackHandler implements CallbackHandler {
   private String username = null;
   private char[] password = null;

   /** @deprecated */
   @Deprecated
   public SimpleCallbackHandler(String user, String pass) {
      this.username = user;
      if (pass != null) {
         this.password = pass.toCharArray();
      }

   }

   /** @deprecated */
   @Deprecated
   public SimpleCallbackHandler(String user, byte[] pass) {
      this.username = user;
      if (pass != null) {
         this.password = (new String(pass)).toCharArray();
      }

   }

   public SimpleCallbackHandler(String user, byte[] pass, String charsetName) {
      this.username = user;
      if (pass != null) {
         try {
            this.password = (new String(pass, charsetName)).toCharArray();
         } catch (UnsupportedEncodingException var5) {
            throw new RuntimeException(var5);
         }
      }

   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (callbacks[i] instanceof NameCallback) {
            NameCallback nc = (NameCallback)callbacks[i];
            nc.setName(this.username);
         } else {
            if (!(callbacks[i] instanceof PasswordCallback)) {
               throw new UnsupportedCallbackException(callbacks[i], "JRW");
            }

            PasswordCallback pc = (PasswordCallback)callbacks[i];
            if (this.password != null) {
               pc.setPassword(this.password);
            }
         }
      }

   }
}
