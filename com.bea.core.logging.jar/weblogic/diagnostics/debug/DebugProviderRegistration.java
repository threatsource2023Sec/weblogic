package weblogic.diagnostics.debug;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;
import weblogic.utils.ArrayUtils;
import weblogic.utils.PropertyHelper;

public class DebugProviderRegistration implements PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final String DEBUG_SCOPES_ATTRIBUTE = "DebugScopes";
   private static final String ENABLED_ATTRIBUTE = "Enabled";
   private static final boolean DEBUG = false;
   private static Map debugProviders = new WeakHashMap();
   private DebugProvider debugProvider;
   private DebugLoggerRepository debugLoggerRepository;

   static synchronized DebugLoggerRepository registerDebugProvider(DebugProvider provider, DebugLoggerRepository dlr) throws DebugProviderRegistrationException {
      DebugProviderRegistration dpr = new DebugProviderRegistration(provider, dlr);
      return dpr.debugLoggerRepository;
   }

   public static synchronized DebugLoggerRepository registerDebugProvider(DebugProvider provider) throws DebugProviderRegistrationException {
      if (provider == null) {
         throw new IllegalArgumentException("DebugProvider cannot be null");
      } else {
         DebugProviderRegistration dpr = new DebugProviderRegistration(provider);
         return dpr.debugLoggerRepository;
      }
   }

   private DebugProviderRegistration(DebugProvider provider, DebugLoggerRepository dlr) throws DebugProviderRegistrationException {
      if (provider == null) {
         throw new IllegalArgumentException("DebugProvider cannot be null");
      } else {
         this.debugProvider = provider;

         try {
            this.debugLoggerRepository = dlr;
            this.debugLoggerRepository.setLogger(provider.getLogger());
            this.initializeDebugScopes();
            this.initializeDebugLoggers();
            Object debugConfig = provider.getDebugConfiguration();
            this.addPropertyChangeListener(debugConfig instanceof Map ? provider : debugConfig);
            debugProviders.put(provider, this);
         } catch (Exception var4) {
            throw new DebugProviderRegistrationException(var4.getMessage(), var4);
         }
      }
   }

   private DebugProviderRegistration(DebugProvider provider) throws DebugProviderRegistrationException {
      this(provider, new DebugLoggerRepository(provider.getCommandLineOverridePrefix()));
   }

   private void initializeDebugScopes() throws IOException, ClassNotFoundException, DebugScopeInitializationException, DebugBeanConfigurationException {
      this.debugProvider.intializeDebugScopes();
      DebugScopeTree tree = this.debugProvider.getDebugScopeTree();
      if (tree != null) {
         this.initializeDebugScopesFromConfig();
         this.initializeDebugScopesFromCmdLine();
         DebugScopeConfigurationHelper.configureDebugLoggers(this.debugProvider.getDebugScopeTree().getRootNode(), this.debugLoggerRepository);
      }
   }

   private void initializeDebugScopesFromConfig() throws DebugScopeInitializationException, DebugBeanConfigurationException {
      DebugScopeBean[] debugScopeBeans = null;

      try {
         Object config = this.debugProvider.getDebugConfiguration();
         Method m = config.getClass().getMethod("getDebugScopes");
         debugScopeBeans = (DebugScopeBean[])((DebugScopeBean[])m.invoke(config));
      } catch (Exception var6) {
         throw new DebugScopeInitializationException(var6);
      }

      if (debugScopeBeans != null) {
         for(int i = 0; i < debugScopeBeans.length; ++i) {
            this.addPropertyChangeListener(debugScopeBeans[i]);
            String debugScopeName = debugScopeBeans[i].getName();

            try {
               DebugScopeConfigurationHelper.configureDebugScope(this.debugProvider.getDebugScopeTree(), debugScopeName, debugScopeBeans[i].isEnabled());
            } catch (InvalidDebugScopeException var5) {
               DebugModuleLogger.logInvalidDebugScopeName(debugScopeName);
            }
         }

      }
   }

   private void initializeDebugScopesFromCmdLine() throws DebugScopeInitializationException, DebugBeanConfigurationException {
      String cmdLine = PropertyHelper.getProperty(this.debugProvider.getCommandLineOverridePrefix() + "DebugScopes", (String)null);
      if (cmdLine != null && !cmdLine.equals("")) {
         StringTokenizer cmdLineTokenizer = new StringTokenizer(cmdLine, ",");

         while(cmdLineTokenizer.hasMoreTokens()) {
            String token = cmdLineTokenizer.nextToken();
            String[] tokens = token.split("=", 2);
            if (tokens != null && tokens.length == 2) {
               String debugScopeName = tokens[0];
               boolean enabled = Boolean.valueOf(tokens[1]);

               try {
                  DebugScopeConfigurationHelper.configureDebugScope(this.debugProvider.getDebugScopeTree(), debugScopeName, enabled);
               } catch (Exception var8) {
                  DebugModuleLogger.logInvalidDebugScopeName(debugScopeName);
               }
            }
         }

      }
   }

   private void initializeDebugLoggers() throws DebugBeanConfigurationException {
      Object debugConfig = this.debugProvider.getDebugConfiguration();
      if (debugConfig instanceof Map) {
         this.configureDebugLoggers((Map)debugConfig);
      } else {
         try {
            DebugAttributesDiscoveryHelper.discoverDebugAttributes(debugConfig, new DebugAttributesDiscoveryCallback() {
               public void debugAttributeDiscovered(String attributeName, boolean enabledState) throws DebugAttributesDiscoveryException {
                  if (enabledState) {
                     DebugProviderRegistration.this.debugLoggerRepository.getDebugLogger(attributeName).setDebugEnabled(enabledState);
                  }

               }
            });
         } catch (Exception var3) {
         }
      }

   }

   public void propertyChange(PropertyChangeEvent evt) {
      String propertyName = evt.getPropertyName();
      Object newValue = evt.getNewValue();
      Object oldValue = evt.getOldValue();
      if (propertyName.equals("DebugScopes")) {
         ArrayUtils.computeDiff((DebugScopeBean[])((DebugScopeBean[])oldValue), (DebugScopeBean[])((DebugScopeBean[])newValue), this);

         try {
            DebugScopeConfigurationHelper.configureDebugLoggers(this.debugProvider.getDebugScopeTree().getRootNode(), this.debugLoggerRepository);
         } catch (Exception var9) {
            DebugModuleLogger.logErrorConfiguringDebugScopes(var9);
         }
      } else if (propertyName.equals("Enabled")) {
         String debugScopeName = ((DebugScopeBean)evt.getSource()).getName();
         boolean enabled = (Boolean)newValue;

         try {
            DebugScopeConfigurationHelper.configureDebugScope(this.debugProvider.getDebugScopeTree(), debugScopeName, enabled);
            DebugScopeConfigurationHelper.configureDebugLoggers(this.debugProvider.getDebugScopeTree().findDebugScopeNode(debugScopeName), this.debugLoggerRepository);
         } catch (Exception var8) {
            DebugModuleLogger.logErrorConfiguringDebugScopes(var8);
         }
      } else if (newValue instanceof Boolean) {
         DebugLogger logger = this.debugLoggerRepository.getDebugLogger(propertyName);
         logger.setDebugEnabled((Boolean)newValue);
      } else if (evt.getPropertyName().equals("DebugProperties") && newValue instanceof Map) {
         this.configureDebugLoggers((Map)newValue);
      }

   }

   public void addObject(Object added) {
      try {
         DebugScopeBean ds = (DebugScopeBean)added;
         DebugScopeConfigurationHelper.configureDebugScope(this.debugProvider.getDebugScopeTree(), ds.getName(), ds.isEnabled());
         this.addPropertyChangeListener(added);
      } catch (Exception var3) {
         DebugModuleLogger.logErrorConfiguringDebugScopes(var3);
      }

   }

   public void removeObject(Object removed) {
      try {
         DebugScopeBean ds = (DebugScopeBean)removed;
         DebugScopeConfigurationHelper.configureDebugScope(this.debugProvider.getDebugScopeTree(), ds.getName(), false);
         this.removePropertyChangeListener(removed);
      } catch (Exception var3) {
         DebugModuleLogger.logErrorConfiguringDebugScopes(var3);
      }

   }

   private void addPropertyChangeListener(Object bean) {
      Class cls = bean.getClass();

      try {
         Method m = cls.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
         m.invoke(bean, this);
      } catch (Exception var4) {
         DebugModuleLogger.logErrorAddingPropertyChangeListener(bean.toString(), var4);
      }

   }

   private void removePropertyChangeListener(Object bean) {
      Class cls = bean.getClass();

      try {
         Method m = cls.getMethod("removePropertyChangeListener", PropertyChangeListener.class);
         m.invoke(bean, this);
      } catch (Exception var4) {
         DebugModuleLogger.logErrorRemovingPropertyChangeListener(bean.toString(), var4);
      }

   }

   private void configureDebugLoggers(Map debugConfig) {
      Iterator var3 = debugConfig.entrySet().iterator();

      while(var3.hasNext()) {
         Object obj = var3.next();
         Map.Entry entry = (Map.Entry)obj;
         String key = (String)entry.getKey();
         Boolean value = (Boolean)entry.getValue();
         this.debugLoggerRepository.getDebugLogger(key).setDebugEnabled(value);
      }

   }
}
