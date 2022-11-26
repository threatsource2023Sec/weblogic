package weblogic.security.jaspic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.ClientAuthConfig;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.auth.message.module.ServerAuthModule;

public class SimpleAuthConfigProvider implements AuthConfigProvider {
   private static final MessagePolicy MANDATORY_REQUEST_POLICY = new MessagePolicy(new MessagePolicy.TargetPolicy[0], true);
   private static final MessagePolicy OPTIONAL_REQUEST_POLICY = new MessagePolicy(new MessagePolicy.TargetPolicy[0], false);
   private Map configurations = new HashMap();
   private Map properties;
   private AuthConfigFactory factory;
   private AuthListener listener;
   private Configuration defaultConfiguration;
   private String[] appContextIds;

   public SimpleAuthConfigProvider(Map properties, AuthConfigFactory factory) {
      this.properties = properties;
      this.factory = factory;
      if (this.properties != null && this.isSelfRegistering()) {
         this.appContextIds = this.parseAppContextIdsFromProps(this.properties);
         this.registerForEachAppContextId();
      }

   }

   private boolean isSelfRegistering() {
      boolean rc = false;
      if (this.factory != null) {
         rc = true;
      } else if (this.properties.containsKey("AppContextIDs")) {
         this.factory = AuthConfigFactory.getFactory();
         rc = true;
      }

      return rc;
   }

   private void registerForEachAppContextId() {
      if (this.appContextIds != null) {
         String layer = (String)this.properties.get("MessageLayer");
         if (layer == null || layer.isEmpty()) {
            layer = "HttpServlet";
         }

         for(int i = 0; i < this.appContextIds.length; ++i) {
            String appctx = this.appContextIds[i];
            this.factory.registerConfigProvider(this, layer, appctx, (String)null);
         }

      }
   }

   private String[] parseAppContextIdsFromProps(Map properties) {
      String value = (String)properties.get("AppContextIDs");
      String[] ids = value.split(",");
      return ids;
   }

   public void setListener(AuthListener listener) {
      this.listener = listener;
   }

   public ClientAuthConfig getClientAuthConfig(String layer, String appId, CallbackHandler callbackHandler) throws AuthException {
      return null;
   }

   public ServerAuthConfig getServerAuthConfig(String layer, String appId, CallbackHandler callbackHandler) throws AuthException {
      Configuration configuration = (Configuration)this.configurations.get(this.getKey(layer, appId));
      if (configuration == null) {
         String moduleClassname = (String)this.properties.get("ServerAuthModule");
         if (moduleClassname != null) {
            configuration = this.configure(layer, appId, moduleClassname);
         } else {
            configuration = this.defaultConfiguration;
         }
      }

      return configuration.createServerAuthConfig(callbackHandler, this.listener);
   }

   public void refresh() {
   }

   public Configuration configure(String layer, String appId, String moduleClassname) {
      Configuration configuration = new Configuration(layer, appId);
      Properties props = new Properties();
      props.putAll(this.properties);
      configuration.addServerAuthModule(moduleClassname, (MessagePolicy)null, (MessagePolicy)null, props);
      return configuration;
   }

   public Configuration createDefaultConfiguration() {
      this.defaultConfiguration = new Configuration((String)null, (String)null);
      return this.defaultConfiguration;
   }

   public Configuration createConfiguration(String layerName, String appId) {
      Configuration configuration = new Configuration(layerName, appId);
      this.configurations.put(this.getKey(layerName, appId), configuration);
      if (this.defaultConfiguration == null) {
         this.defaultConfiguration = configuration;
      }

      return configuration;
   }

   private String getKey(String layerName, String appId) {
      return layerName + ':' + appId;
   }

   public class ModuleConfiguration {
      private String className;
      private MessagePolicy requestPolicy;
      private MessagePolicy responsePolicy;
      private Map options = new HashMap();

      public ModuleConfiguration(String className, MessagePolicy requestPolicy, MessagePolicy responsePolicy) {
         this.className = className;
         this.requestPolicy = requestPolicy;
         this.responsePolicy = responsePolicy;
      }

      public void addOption(String key, Object value) {
         this.options.put(key, value);
      }

      public ServerAuthModule createModule(CallbackHandler callbackHandler, Map runtimeOptions, MessagePolicy requestPolicy) throws AuthException {
         try {
            Class moduleClass = Class.forName(this.className);
            if (!ServerAuthModule.class.isAssignableFrom(moduleClass)) {
               throw new AuthException(this.className + "is not a server auth module");
            } else {
               ServerAuthModule module = (ServerAuthModule)moduleClass.newInstance();
               module.initialize(requestPolicy, this.responsePolicy, callbackHandler, this.combinedOptions(this.options, runtimeOptions));
               return module;
            }
         } catch (ClassNotFoundException var6) {
            throw new AuthException("Unable to instantiate server auth module: " + var6);
         } catch (InstantiationException var7) {
            throw new AuthException("Unable to instantiate server auth module: " + var7);
         } catch (IllegalAccessException var8) {
            throw new AuthException("Unable to instantiate server auth module: " + var8);
         }
      }

      private Map combinedOptions(Map configurationOptions, Map runtimeOptions) {
         if (runtimeOptions == null) {
            return Collections.unmodifiableMap(configurationOptions);
         } else {
            Map combinedOptions = new HashMap(configurationOptions);
            combinedOptions.putAll(runtimeOptions);
            return combinedOptions;
         }
      }
   }

   public class Configuration {
      private List moduleConfigurations = new ArrayList();
      private String messageLayer;
      private String appContext;

      Configuration(String messageLayer, String appContext) {
         this.messageLayer = messageLayer;
         this.appContext = appContext;
      }

      public ModuleConfiguration addServerAuthModule(String className, MessagePolicy requestPolicy, MessagePolicy responsePolicy, Properties properties) {
         ModuleConfiguration configuration = SimpleAuthConfigProvider.this.new ModuleConfiguration(className, requestPolicy, responsePolicy);
         if (properties != null) {
            this.addPropertiesAsOptions(properties, configuration);
         }

         this.moduleConfigurations.add(configuration);
         return configuration;
      }

      private void addPropertiesAsOptions(Properties properties, ModuleConfiguration configuration) {
         Enumeration e = properties.keys();

         while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            String value = properties.getProperty(key);
            configuration.addOption(key, value);
         }

      }

      private SimpleServerAuthConfig createServerAuthConfig(CallbackHandler callbackHandler, AuthListener listener) {
         return new SimpleServerAuthConfig(this, callbackHandler);
      }

      String getAppContext() {
         return this.appContext;
      }

      public String getMessageLayer() {
         return this.messageLayer;
      }

      public ServerAuthContext createAuthContext(String authContextID, CallbackHandler callbackHandler, Map runtimeOptions) throws AuthException {
         ArrayList modules = new ArrayList();
         MessagePolicy passedRequestPolicy = Boolean.parseBoolean(authContextID) ? SimpleAuthConfigProvider.MANDATORY_REQUEST_POLICY : SimpleAuthConfigProvider.OPTIONAL_REQUEST_POLICY;
         Iterator var6 = this.moduleConfigurations.iterator();

         while(var6.hasNext()) {
            ModuleConfiguration configuration = (ModuleConfiguration)var6.next();
            modules.add(configuration.createModule(callbackHandler, runtimeOptions, passedRequestPolicy));
         }

         SimpleAuthContext context = new SimpleAuthContext((ServerAuthModule[])modules.toArray(new ServerAuthModule[modules.size()]));
         context.setListener(SimpleAuthConfigProvider.this.listener);
         return context;
      }
   }
}
