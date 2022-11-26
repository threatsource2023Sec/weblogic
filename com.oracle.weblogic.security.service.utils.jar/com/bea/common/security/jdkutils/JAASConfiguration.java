package com.bea.common.security.jdkutils;

import java.util.ArrayList;
import java.util.Iterator;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public class JAASConfiguration extends Configuration {
   public static final String DEFAULT_CONFIGURATION_NAME = "OracleDefaultLoginConfiguration";
   private static boolean debug = false;
   private static ArrayList configurations = new ArrayList();
   private static String configFileImplName = null;
   private static int instanceCounter = 0;

   public static synchronized int getInstanceCounter() {
      return instanceCounter++;
   }

   public AppConfigurationEntry[] getAppConfigurationEntry(String configurationName) {
      Iterator jaasConfFile;
      synchronized(configurations) {
         jaasConfFile = configurations.iterator();

         while(jaasConfFile.hasNext()) {
            Configuration configuration = (Configuration)jaasConfFile.next();
            if (debug) {
               System.out.println("DEBUG JAASConfiguration: Trying JAAS configuration " + configuration);
            }

            AppConfigurationEntry[] entries = configuration.getAppConfigurationEntry(configurationName);
            if (entries != null) {
               return entries;
            }
         }
      }

      if (debug) {
         System.out.println("DEBUG JAASConfiguration: JAAS configuration not found, checking for property value");
      }

      String value = System.getProperty("java.security.auth.login.config");
      jaasConfFile = null;
      if (value != null) {
         if (debug) {
            System.out.println("DEBUG JAASConfiguration: property <java.security.auth.login.config> is set, loading and registering from property");
         }

         if (configFileImplName != null) {
            try {
               Configuration jaasConfFile = (Configuration)this.getClass().getClassLoader().loadClass(configFileImplName).newInstance();
               registerConfiguration(jaasConfFile);
               configFileImplName = null;
               if (debug) {
                  System.out.println("DEBUG JAASConfiguration: Trying newly registered configuration " + jaasConfFile);
               }

               AppConfigurationEntry[] entries = jaasConfFile.getAppConfigurationEntry(configurationName);
               if (entries != null) {
                  return entries;
               }
            } catch (Exception var7) {
               if (debug) {
                  System.out.println("DEBUG JAASConfiguration: Unable to load Configuration defined by property <java.security.auth.login.config>" + var7.getMessage());
               }
            }
         } else if (debug) {
            System.out.println("DEBUG JAASConfiguration: Cannot load Configuration from property");
         }
      }

      throw new IllegalArgumentException("No Configuration was registered that can handle the configuration named " + configurationName);
   }

   public void refresh() {
      synchronized(configurations) {
         Iterator i = configurations.iterator();

         while(i.hasNext()) {
            ((Configuration)i.next()).refresh();
         }

      }
   }

   public static void registerConfiguration(Configuration configuration) {
      if (debug) {
         System.out.println("DEBUG JAASConfiguration: Registering JAAS configuration " + configuration);
      }

      synchronized(configurations) {
         if (configuration == null) {
            throw new IllegalArgumentException("Configuration must not be null");
         } else if (configurations.contains(configuration)) {
            throw new IllegalArgumentException("Configuration already registered");
         } else {
            configurations.add(configuration);
         }
      }
   }

   public static void unregisterConfiguration(Configuration configuration) {
      if (debug) {
         System.out.println("DEBUG JAASConfiguration: Unregistering JAAS configuration " + configuration);
      }

      synchronized(configurations) {
         if (!configurations.contains(configuration)) {
            throw new IllegalArgumentException("Configuration was not registered");
         } else {
            configurations.remove(configuration);
         }
      }
   }

   public static void setJAASConfigFile(String classname) {
      if (debug) {
         System.out.println("DEBUG JAASConfiguration: Setting JAAS Configuration implementation name: " + classname);
      }

      configFileImplName = classname;
   }
}
