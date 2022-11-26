package weblogic.security.jaspic;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;

class SimpleServerAuthConfig implements ServerAuthConfig {
   private SimpleAuthConfigProvider.Configuration configuration;
   private CallbackHandler callbackHandler;
   private static final String KEY_MUST_AUTHENTICATE = "javax.security.auth.message.MessagePolicy.isMandatory";

   SimpleServerAuthConfig(SimpleAuthConfigProvider.Configuration configuration, CallbackHandler callbackHandler) {
      this.configuration = configuration;
      this.callbackHandler = callbackHandler;
   }

   public String getAppContext() {
      return this.configuration.getAppContext();
   }

   public String getMessageLayer() {
      return this.configuration.getMessageLayer();
   }

   public ServerAuthContext getAuthContext(String authContextID, Subject serviceSubject, Map options) throws AuthException {
      return this.configuration.createAuthContext(authContextID, this.callbackHandler, options);
   }

   public String getAuthContextID(MessageInfo messageInfo) {
      Map map = messageInfo.getMap();
      return map.containsKey("javax.security.auth.message.MessagePolicy.isMandatory") ? "true" : "false";
   }

   public boolean isProtected() {
      return false;
   }

   public void refresh() {
   }
}
