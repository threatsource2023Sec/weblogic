package weblogic.admin.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.admin.plugin.exceptions.PluginManagementException;
import weblogic.admin.plugin.utils.CommonUtils;
import weblogic.nodemanager.plugin.NMEnvironment;

public class PluginManager {
   private static PluginManager _pluginManager = new PluginManager();
   private static final String UPPER_CASE_META_INF_PLUGINFACTORY = "META-INF/pluginfactory";
   private static final String LOWER_CASE_META_INF_PLUGINFACTORY = "meta-inf/pluginfactory";
   private static final String PLUGINS_DIR = "plugins";
   private static final String CAM_DIR = "cam";
   private Logger _pluginLogger = Logger.getLogger(PluginManager.class.getName());
   private Map _pluginFactoryToLoader;
   private PluginFactoryList _pluginFactories;
   private List _pluginJarFileLocations = null;
   private String _pluginJarFile = null;
   private final HashMap pluginCache = new HashMap();
   private static String[] _pluginsCamDirArray;
   private static String ALTERNATE_PLUGIN_DIRECTORY;
   private static String PLUGIN_JAR_FILE;
   private static ServiceLoader pluginFactoryServiceLoader = ServiceLoader.load(PluginFactory.class);

   private PluginManager() {
      if (this._pluginFactoryToLoader == null) {
         this._pluginFactoryToLoader = Collections.synchronizedMap(new HashMap(10));
      } else {
         this._pluginFactoryToLoader.clear();
      }

      this._pluginFactories = new PluginFactoryList();
      this._pluginLogger = Logger.getLogger(PluginManager.class.getName());
      _pluginsCamDirArray = CommonUtils.getPluginsCamDirs("plugins" + File.separator + "cam");
      if (this.foundPluginJarFileLocations()) {
         try {
            this.discoverPluginFactories();
         } catch (PluginManagementException var2) {
            if (this._pluginLogger.isLoggable(Level.WARNING)) {
               this._pluginLogger.log(Level.WARNING, "Error discovering the plugin factories from the classloader. " + var2.getCause());
            }

            if (this._pluginLogger.isLoggable(Level.FINE)) {
               var2.printStackTrace();
            }
         }
      }

   }

   public static PluginManager getInstance() {
      return _pluginManager;
   }

   public String[] getAvailableSystemComponents() {
      if (!this.rediscoverPluginFactoriesFromResourceFiles()) {
         return new String[0];
      } else {
         List factoryList = this.instantiatePluginFactories();
         if (factoryList == null) {
            return new String[0];
         } else {
            List typeList = new ArrayList();
            Iterator var3 = factoryList.iterator();

            while(var3.hasNext()) {
               PluginFactory factory = (PluginFactory)var3.next();
               typeList.add(factory.getSystemComponentType());
            }

            return (String[])typeList.toArray(new String[typeList.size()]);
         }
      }
   }

   public Plugin getPlugin(String systemComponentType, String pluginType) {
      synchronized(this.pluginCache) {
         Plugin plugin = this.getCachedPlugin(systemComponentType, pluginType);
         if (plugin == null) {
            plugin = this.createPlugin(systemComponentType, pluginType);
            this.putCachedPlugin(systemComponentType, pluginType, plugin);
         }

         return plugin;
      }
   }

   public Plugin createPlugin(String systemComponentType, String pluginType) {
      this._pluginLogger.info("NMEnvironment will be null for the " + systemComponentType + " PluginFactory.");
      return this.createPlugin(systemComponentType, pluginType, (NMEnvironment)null);
   }

   public Plugin createPlugin(String systemComponentType, String pluginType, NMEnvironment nmEnv) {
      if (!this.rediscoverPluginFactoriesFromResourceFiles()) {
         return null;
      } else {
         List factoryList = this.instantiatePluginFactories();
         if (factoryList == null) {
            return null;
         } else {
            try {
               Iterator var5 = factoryList.iterator();

               PluginFactory factory;
               String type;
               do {
                  if (!var5.hasNext()) {
                     return null;
                  }

                  factory = (PluginFactory)var5.next();
                  type = factory.getSystemComponentType();
               } while(!type.equals(systemComponentType));

               factory.setNMEnvironment(nmEnv);
               return factory.createPlugin(pluginType);
            } catch (Exception var8) {
               if (this._pluginLogger.isLoggable(Level.WARNING)) {
                  this._pluginLogger.log(Level.WARNING, "Cannot create Plugin for the type " + pluginType + ". " + var8.getCause());
               }

               if (this._pluginLogger.isLoggable(Level.FINE)) {
                  var8.printStackTrace();
               }

               return null;
            }
         }
      }
   }

   private List instantiatePluginFactories() {
      List factoryList = null;

      try {
         factoryList = this._pluginFactories.getFactories();
      } catch (PluginManagementException var3) {
         if (this._pluginLogger.isLoggable(Level.WARNING)) {
            this._pluginLogger.log(Level.WARNING, "Cannot instantiate the plugin factory class declared in the plugin factory resource file. " + var3.getCause());
         }

         if (this._pluginLogger.isLoggable(Level.FINE)) {
            var3.printStackTrace();
         }
      }

      return factoryList;
   }

   private boolean rediscoverPluginFactoriesFromResourceFiles() {
      if (this.pluginJarFileLocationsChanged() && this.foundPluginJarFileLocations()) {
         try {
            _pluginManager.discoverPluginFactories();
         } catch (PluginManagementException var2) {
            if (this._pluginLogger.isLoggable(Level.WARNING)) {
               this._pluginLogger.log(Level.WARNING, "Error discovering the plugin factories from the classloader. " + var2.getCause());
            }

            if (this._pluginLogger.isLoggable(Level.FINE)) {
               var2.printStackTrace();
            }

            return false;
         }
      }

      return true;
   }

   private boolean foundPluginJarFileLocations() {
      ALTERNATE_PLUGIN_DIRECTORY = System.getProperty("weblogic.alternatePluginDirectory", (String)null);
      PLUGIN_JAR_FILE = System.getProperty("weblogic.pluginJarFile", (String)null);
      this._pluginJarFileLocations = null;
      this._pluginJarFile = null;
      if (ALTERNATE_PLUGIN_DIRECTORY != null) {
         if ((new File(ALTERNATE_PLUGIN_DIRECTORY)).isDirectory()) {
            String[] dirs = CommonUtils.splitCompletely(ALTERNATE_PLUGIN_DIRECTORY, ",");
            String[] var2 = dirs;
            int var3 = dirs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String dir = var2[var4];
               File altDir = new File(dir);
               if (altDir.exists()) {
                  this._pluginJarFileLocations = new ArrayList();
                  this._pluginJarFileLocations.add(ALTERNATE_PLUGIN_DIRECTORY);
               } else if (this._pluginLogger.isLoggable(Level.WARNING)) {
                  this._pluginLogger.log(Level.WARNING, "The alternate system component plugin directory " + altDir.toString() + " does not exist.");
               }
            }
         }
      } else if (PLUGIN_JAR_FILE != null) {
         File pluginJarFile = new File(PLUGIN_JAR_FILE);

         try {
            if (pluginJarFile.exists() && pluginJarFile.isFile()) {
               this._pluginJarFile = PLUGIN_JAR_FILE;
            } else if (this._pluginLogger.isLoggable(Level.WARNING)) {
               this._pluginLogger.log(Level.WARNING, "The system component plugin jar file " + pluginJarFile.getCanonicalPath() + " does not exist.");
            }
         } catch (IOException var7) {
         }
      } else {
         if (_pluginsCamDirArray == null || _pluginsCamDirArray.length == 0) {
            if (this._pluginLogger.isLoggable(Level.FINE)) {
               this._pluginLogger.log(Level.FINE, "Cannot find any plugins/cam directory under " + CommonUtils.getMWHomeDir() + ".");
            }

            return false;
         }

         this._pluginJarFileLocations = new ArrayList(_pluginsCamDirArray.length);
         Collections.addAll(this._pluginJarFileLocations, _pluginsCamDirArray);
      }

      if (this._pluginJarFile != null) {
         PluginClassLoader.setPluginJarFileLocations(new String[]{this._pluginJarFile});
      } else if (this._pluginJarFileLocations != null && this._pluginJarFileLocations.size() != 0) {
         PluginClassLoader.setPluginJarFileLocations((String[])this._pluginJarFileLocations.toArray(new String[this._pluginJarFileLocations.size()]));
      }

      return true;
   }

   private boolean pluginJarFileLocationsChanged() {
      String newLocation = System.getProperty("weblogic.alternatePluginDirectory");
      if (newLocation != null && !this.pluginJarFileLocationExists(newLocation)) {
         return true;
      } else {
         newLocation = System.getProperty("weblogic.pluginJarFile");
         if (newLocation != null && !this.pluginJarFileLocationExists(newLocation)) {
            return true;
         } else {
            return !this._pluginFactories.initialized;
         }
      }
   }

   private boolean pluginJarFileLocationExists(String newLocation) {
      if (this._pluginJarFile != null && this._pluginJarFile.equals(newLocation)) {
         return true;
      } else {
         if (this._pluginJarFileLocations != null) {
            Iterator var2 = this._pluginJarFileLocations.iterator();

            while(var2.hasNext()) {
               String existingLocation = (String)var2.next();
               if (existingLocation.equals(newLocation)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static void setAlternatPluginDirectory(String alt) {
      ALTERNATE_PLUGIN_DIRECTORY = alt;
   }

   private static void setPluginJarFile(String pjf) {
      PLUGIN_JAR_FILE = pjf;
   }

   public void cleanupSystemProperties() {
      Properties properties;
      if (ALTERNATE_PLUGIN_DIRECTORY != null) {
         properties = System.getProperties();
         properties.remove("weblogic.alternatePluginDirectory");
         setAlternatPluginDirectory((String)null);
      }

      if (PLUGIN_JAR_FILE != null) {
         properties = System.getProperties();
         properties.remove("weblogic.pluginJarFile");
         setPluginJarFile((String)null);
      }

      if (this._pluginFactoryToLoader != null && this._pluginFactoryToLoader.size() != 0) {
         this._pluginFactoryToLoader.clear();
      }

      if (this._pluginFactories.loadedFactories()) {
         this._pluginFactories = new PluginFactoryList();
      }

   }

   private void discoverPluginFactories() throws PluginManagementException {
      PluginManagementException pme;
      try {
         if (PluginClassLoader.getPluginJarFileLocations() != null) {
            ClassLoader classLoader = PluginClassLoader.getClassLoader();
            Enumeration urls = classLoader.getResources("META-INF/pluginfactory");
            if (!urls.hasMoreElements()) {
               urls = classLoader.getResources("meta-inf/pluginfactory");
               if (!urls.hasMoreElements()) {
                  throw new InstantiationException("Cannot find the plugin factory resource file META-INF/pluginfactory or meta-inf/pluginfactory from the classloader. No System Component plugin jar file is loaded.");
               }
            }

            while(urls.hasMoreElements()) {
               URL next = (URL)urls.nextElement();
               BufferedReader reader = null;

               try {
                  reader = new BufferedReader(new InputStreamReader(next.openStream()));
                  String factoryClassName = reader.readLine();
                  if (factoryClassName == null) {
                     throw new InstantiationException("The plugin factory resource file " + next.toString() + " is empty. No plugin factory implementation class is defined.");
                  }

                  while(factoryClassName != null) {
                     factoryClassName = factoryClassName.trim();
                     if (factoryClassName.length() == 0) {
                        factoryClassName = reader.readLine();
                     } else {
                        if (this._pluginLogger.isLoggable(Level.FINE)) {
                           this._pluginLogger.log(Level.FINE, "Discovered factory class name " + factoryClassName);
                        }

                        this.registerPluginFactoryClass(factoryClassName, classLoader);
                        factoryClassName = reader.readLine();
                     }
                  }
               } finally {
                  if (reader != null) {
                     reader.close();
                  }

               }
            }

         }
      } catch (IOException var11) {
         pme = new PluginManagementException(var11.getMessage());
         pme.initCause(var11);
         throw pme;
      } catch (InstantiationException var12) {
         pme = new PluginManagementException(var12.getMessage());
         pme.initCause(var12);
         throw pme;
      }
   }

   private void registerPluginFactoryClass(String className, ClassLoader ldr) {
      if (ldr == null) {
         ldr = this.getClass().getClassLoader();
      }

      this._pluginFactoryToLoader.put(className, ldr);
      if (this._pluginFactories.loadedFactories()) {
         this._pluginFactories.reloadFactories();
      }

   }

   private Plugin getCachedPlugin(String systemComponentType, String pluginType) {
      HashMap map = (HashMap)this.pluginCache.get(systemComponentType);
      return map == null ? null : (Plugin)map.get(pluginType);
   }

   public void putCachedPlugin(String systemComponentType, String pluginType, Plugin plugin) {
      HashMap map = (HashMap)this.pluginCache.get(systemComponentType);
      if (map == null) {
         map = new HashMap();
         this.pluginCache.put(systemComponentType, map);
      }

      map.put(pluginType, plugin);
   }

   private class PluginFactoryList {
      private List pluginFactoriesList = null;
      private boolean initialized = false;

      PluginFactoryList() {
      }

      List getFactories() throws PluginManagementException {
         if (!this.initialized) {
            this.loadPluginFactories();
         }

         return this.pluginFactoriesList;
      }

      boolean loadedFactories() {
         return this.initialized;
      }

      void reloadFactories() {
         this.initialized = false;
      }

      private synchronized void loadPluginFactories() throws PluginManagementException {
         if (!this.initialized) {
            this.pluginFactoriesList = Collections.synchronizedList(new ArrayList(10));
            Set factoryToLoaderSet = PluginManager.this._pluginFactoryToLoader.entrySet();
            Iterator var2 = factoryToLoaderSet.iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();

               try {
                  this.loadPluginFactory((String)entry.getKey(), (ClassLoader)entry.getValue());
               } catch (PluginManagementException var5) {
                  if (PluginManager.this._pluginLogger.isLoggable(Level.WARNING)) {
                     PluginManager.this._pluginLogger.log(Level.WARNING, "Cannot load the plugin factory " + (String)entry.getKey() + " from the classloader. " + var5.getCause());
                  }

                  if (PluginManager.this._pluginLogger.isLoggable(Level.FINE)) {
                     var5.printStackTrace();
                  }
               }
            }

            var2 = PluginManager.pluginFactoryServiceLoader.iterator();

            while(var2.hasNext()) {
               PluginFactory pf = (PluginFactory)var2.next();
               this.pluginFactoriesList.add(pf);
            }

            this.initialized = true;
         }

      }

      private void loadPluginFactory(String factoryClassName, ClassLoader ldr) throws PluginManagementException {
         PluginManagementException pme;
         try {
            Class factoryClass = Class.forName(factoryClassName, true, ldr);
            Constructor constructor = factoryClass.getDeclaredConstructor();
            PluginFactory result = (PluginFactory)constructor.newInstance();
            if (PluginManager.this._pluginLogger.isLoggable(Level.FINE)) {
               PluginManager.this._pluginLogger.log(Level.FINE, "Creating plugin factory instance for the class " + factoryClassName + " ...");
            }

            this.pluginFactoriesList.add(result);
         } catch (NoSuchMethodException var6) {
            pme = new PluginManagementException(var6.getMessage());
            pme.initCause(var6);
            throw pme;
         } catch (IllegalAccessException var7) {
            pme = new PluginManagementException(var7.getMessage());
            pme.initCause(var7);
            throw pme;
         } catch (InstantiationException var8) {
            pme = new PluginManagementException(var8.getMessage());
            pme.initCause(var8);
            throw pme;
         } catch (InvocationTargetException var9) {
            pme = new PluginManagementException(var9.getMessage());
            pme.initCause(var9);
            throw pme;
         } catch (ClassNotFoundException var10) {
            pme = new PluginManagementException(var10.getMessage() + " in the plugin factory resource file.");
            pme.initCause(var10);
            throw pme;
         } catch (Throwable var11) {
            pme = new PluginManagementException(var11.getMessage());
            pme.initCause(var11);
            throw pme;
         }
      }
   }
}
