package weblogic.security.jaspic;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.module.ServerAuthModule;

public interface AuthListener {
   void initializeCalled(ServerAuthModule var1, MessagePolicy var2, MessagePolicy var3, CallbackHandler var4, Map var5);

   void requestValidated(ServerAuthModule var1, MessageInfo var2, Subject var3, Subject var4);

   void responseSecured(ServerAuthModule var1, MessageInfo var2, Subject var3);
}
