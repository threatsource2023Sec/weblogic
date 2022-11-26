package weblogic.security;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import weblogic.security.auth.callback.URLCallback;

public class URLCallbackHandler implements CallbackHandler {
   private String url;
   private String username;
   private byte[] password;

   /** @deprecated */
   @Deprecated
   public URLCallbackHandler(String user, String pass) {
      this(user, pass.getBytes());
   }

   public URLCallbackHandler(String user, byte[] pass) {
      this.url = null;
      this.username = user;
      this.password = pass;
   }

   /** @deprecated */
   @Deprecated
   public URLCallbackHandler(String user, String pass, String URL) {
      this(user, pass.getBytes(), URL);
   }

   public URLCallbackHandler(String user, byte[] pass, String URL) {
      this.url = URL;
      this.username = user;
      this.password = pass;
   }

   public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
      for(int i = 0; i < callbacks.length; ++i) {
         if (callbacks[i] instanceof NameCallback) {
            NameCallback nc = (NameCallback)callbacks[i];
            nc.setName(this.username);
         } else if (callbacks[i] instanceof PasswordCallback) {
            PasswordCallback pc = (PasswordCallback)callbacks[i];
            if (this.password != null) {
               pc.setPassword((new String(this.password)).toCharArray());
            }
         } else {
            if (!(callbacks[i] instanceof URLCallback)) {
               throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }

            URLCallback uc = (URLCallback)callbacks[i];
            if (this.url != null) {
               uc.setURL(this.url);
            } else {
               uc.setURL(uc.getdefaultURL());
            }
         }
      }

   }
}
