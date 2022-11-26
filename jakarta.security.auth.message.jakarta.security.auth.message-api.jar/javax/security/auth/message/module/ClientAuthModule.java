package javax.security.auth.message.module;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.ClientAuth;
import javax.security.auth.message.MessagePolicy;

public interface ClientAuthModule extends ClientAuth {
   void initialize(MessagePolicy var1, MessagePolicy var2, CallbackHandler var3, Map var4) throws AuthException;

   Class[] getSupportedMessageTypes();
}
