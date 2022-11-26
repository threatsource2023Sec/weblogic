package org.glassfish.soteria.mechanisms.jaspic;

import java.util.Collections;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.auth.message.module.ServerAuthModule;

public class DefaultServerAuthContext implements ServerAuthContext {
   private final ServerAuthModule serverAuthModule;

   public DefaultServerAuthContext(CallbackHandler handler, ServerAuthModule serverAuthModule) throws AuthException {
      this.serverAuthModule = serverAuthModule;
      serverAuthModule.initialize((MessagePolicy)null, (MessagePolicy)null, handler, Collections.emptyMap());
   }

   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
      return this.serverAuthModule.validateRequest(messageInfo, clientSubject, serviceSubject);
   }

   public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
      return this.serverAuthModule.secureResponse(messageInfo, serviceSubject);
   }

   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
      this.serverAuthModule.cleanSubject(messageInfo, subject);
   }
}
