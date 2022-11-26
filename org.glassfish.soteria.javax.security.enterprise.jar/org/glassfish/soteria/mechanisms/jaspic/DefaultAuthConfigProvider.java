package org.glassfish.soteria.mechanisms.jaspic;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.ClientAuthConfig;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.module.ServerAuthModule;

public class DefaultAuthConfigProvider implements AuthConfigProvider {
   private static final String CALLBACK_HANDLER_PROPERTY_NAME = "authconfigprovider.client.callbackhandler";
   private Map providerProperties;
   private ServerAuthModule serverAuthModule;

   public DefaultAuthConfigProvider(ServerAuthModule serverAuthModule) {
      this.serverAuthModule = serverAuthModule;
   }

   public DefaultAuthConfigProvider(Map properties, AuthConfigFactory factory) {
      this.providerProperties = properties;
      if (factory != null) {
         factory.registerConfigProvider(this, (String)null, (String)null, "Auto registration");
      }

   }

   public ServerAuthConfig getServerAuthConfig(String layer, String appContext, CallbackHandler handler) throws AuthException, SecurityException {
      return new DefaultServerAuthConfig(layer, appContext, handler == null ? this.createDefaultCallbackHandler() : handler, this.providerProperties, this.serverAuthModule);
   }

   public ClientAuthConfig getClientAuthConfig(String layer, String appContext, CallbackHandler handler) throws AuthException, SecurityException {
      return null;
   }

   public void refresh() {
   }

   private CallbackHandler createDefaultCallbackHandler() throws AuthException {
      String callBackClassName = System.getProperty("authconfigprovider.client.callbackhandler");
      if (callBackClassName == null) {
         throw new AuthException("No default handler set via system property: authconfigprovider.client.callbackhandler");
      } else {
         try {
            return (CallbackHandler)Thread.currentThread().getContextClassLoader().loadClass(callBackClassName).newInstance();
         } catch (Exception var3) {
            throw new AuthException(var3.getMessage());
         }
      }
   }
}
