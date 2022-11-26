package javax.security.auth.message.config;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;

public interface AuthConfigProvider {
   ClientAuthConfig getClientAuthConfig(String var1, String var2, CallbackHandler var3) throws AuthException;

   ServerAuthConfig getServerAuthConfig(String var1, String var2, CallbackHandler var3) throws AuthException;

   void refresh();
}
