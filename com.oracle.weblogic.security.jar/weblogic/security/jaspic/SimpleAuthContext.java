package weblogic.security.jaspic;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.auth.message.module.ServerAuthModule;

class SimpleAuthContext implements ServerAuthContext {
   private ServerAuthModule[] modules;
   private AuthListener listener;
   static AuthListener NULL_AUTH_LISTENER = new NullAuthListener();

   public SimpleAuthContext(ServerAuthModule... modules) throws AuthException {
      this.listener = NULL_AUTH_LISTENER;
      if (modules.length == 0) {
         throw new AuthException("No matching ServerAuthModule found");
      } else {
         this.modules = modules;
      }
   }

   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
      ServerAuthModule[] var5 = this.modules;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ServerAuthModule module = var5[var7];
         module.initialize(requestPolicy, responsePolicy, handler, options);
         this.listener.initializeCalled(module, requestPolicy, responsePolicy, handler, options);
      }

   }

   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
      AuthStatus[] status = new AuthStatus[this.modules.length];
      boolean resultSuccess = false;
      boolean resultSendSuccess = false;

      for(int i = 0; i < this.modules.length; ++i) {
         status[i] = this.modules[i].validateRequest(messageInfo, clientSubject, serviceSubject);
         this.listener.requestValidated(this.modules[i], messageInfo, clientSubject, serviceSubject);
         if (status[i] == AuthStatus.SUCCESS) {
            resultSuccess = true;
         } else {
            if (status[i] != AuthStatus.SEND_SUCCESS) {
               return status[i];
            }

            resultSendSuccess = true;
         }
      }

      if (resultSendSuccess) {
         return AuthStatus.SEND_SUCCESS;
      } else if (resultSuccess) {
         return AuthStatus.SUCCESS;
      } else {
         return AuthStatus.SEND_FAILURE;
      }
   }

   public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
      AuthStatus[] status = new AuthStatus[this.modules.length];

      for(int i = 0; i < this.modules.length; ++i) {
         status[i] = this.modules[i].secureResponse(messageInfo, serviceSubject);
         this.listener.responseSecured(this.modules[i], messageInfo, serviceSubject);
         if (status[i] != AuthStatus.SEND_SUCCESS) {
            if (status[i] == AuthStatus.SUCCESS) {
               return AuthStatus.SEND_FAILURE;
            }

            return status[i];
         }
      }

      return AuthStatus.SEND_SUCCESS;
   }

   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
      for(int i = 0; i < this.modules.length; ++i) {
         this.modules[i].cleanSubject(messageInfo, subject);
      }

   }

   public void setListener(AuthListener listener) {
      this.listener = listener == null ? NULL_AUTH_LISTENER : listener;
   }

   static class NullAuthListener implements AuthListener {
      public void initializeCalled(ServerAuthModule sam, MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) {
      }

      public void requestValidated(ServerAuthModule sam, MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) {
      }

      public void responseSecured(ServerAuthModule sam, MessageInfo messageInfo, Subject serviceSubject) {
      }
   }
}
