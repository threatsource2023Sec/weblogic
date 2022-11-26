package org.glassfish.soteria.mechanisms.jaspic;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.auth.message.module.ServerAuthModule;

public class DefaultServerAuthConfig implements ServerAuthConfig {
   private String layer;
   private String appContext;
   private CallbackHandler handler;
   private Map providerProperties;
   private ServerAuthModule serverAuthModule;

   public DefaultServerAuthConfig(String layer, String appContext, CallbackHandler handler, Map providerProperties, ServerAuthModule serverAuthModule) {
      this.layer = layer;
      this.appContext = appContext;
      this.handler = handler;
      this.providerProperties = providerProperties;
      this.serverAuthModule = serverAuthModule;
   }

   public ServerAuthContext getAuthContext(String authContextID, Subject serviceSubject, Map properties) throws AuthException {
      return new DefaultServerAuthContext(this.handler, this.serverAuthModule);
   }

   public String getMessageLayer() {
      return this.layer;
   }

   public String getAuthContextID(MessageInfo messageInfo) {
      return this.appContext;
   }

   public String getAppContext() {
      return this.appContext;
   }

   public void refresh() {
   }

   public boolean isProtected() {
      return false;
   }

   public Map getProviderProperties() {
      return this.providerProperties;
   }
}
