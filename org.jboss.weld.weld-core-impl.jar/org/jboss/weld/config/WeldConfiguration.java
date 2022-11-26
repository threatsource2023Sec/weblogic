package org.jboss.weld.config;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.configuration.spi.ExternalConfiguration;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.ConfigurationLogger;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.security.GetSystemPropertyAction;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.reflection.Reflections;

public class WeldConfiguration implements Service {
   public static final String CONFIGURATION_FILE = "weld.properties";
   private static final String EXECUTOR_CONFIGURATION_FILE = "org.jboss.weld.executor.properties";
   private static final String BOOTSTRAP_CONFIGURATION_FILE = "org.jboss.weld.bootstrap.properties";
   private static final String UNSAFE_PROXIES_MARKER = "META-INF/org.jboss.weld.enableUnsafeProxies";
   private static final String SYSTEM_PROPETIES = "system properties";
   private static final String OBSOLETE_SYSTEM_PROPETIES = "obsolete system properties";
   private static final String EXTERNAL_CONFIGURATION_CLASS_NAME = "org.jboss.weld.configuration.spi.ExternalConfiguration";
   private final Map properties;
   private final File proxyDumpFilePath;
   private final Pattern proxyIgnoreFinalMethodsPattern;

   public WeldConfiguration(ServiceRegistry services, Deployment deployment) {
      Preconditions.checkArgumentNotNull(deployment, "deployment");
      this.properties = this.init(services, deployment);
      this.proxyDumpFilePath = this.initProxyDumpFilePath();
      this.proxyIgnoreFinalMethodsPattern = this.initProxyIgnoreFinalMethodsPattern();
      StringJoiner logOutputBuilder = new StringJoiner(", ", "{", "}");
      Iterator var4 = this.properties.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         logOutputBuilder.add(((ConfigurationKey)entry.getKey()).get() + "=" + entry.getValue());
      }

      ConfigurationLogger.LOG.configurationInitialized(logOutputBuilder.toString());
   }

   public String getStringProperty(ConfigurationKey key) {
      return (String)this.getProperty(key, String.class);
   }

   public Boolean getBooleanProperty(ConfigurationKey key) {
      return (Boolean)this.getProperty(key, Boolean.class);
   }

   public Long getLongProperty(ConfigurationKey key) {
      return (Long)this.getProperty(key, Long.class);
   }

   public Integer getIntegerProperty(ConfigurationKey key) {
      return (Integer)this.getProperty(key, Integer.class);
   }

   public File getProxyDumpFilePath() {
      return this.proxyDumpFilePath;
   }

   public boolean isFinalMethodIgnored(String className) {
      return this.proxyIgnoreFinalMethodsPattern != null ? this.proxyIgnoreFinalMethodsPattern.matcher(className).matches() : false;
   }

   public void cleanup() {
      if (this.properties != null) {
         this.properties.clear();
      }

   }

   static void merge(Map original, Map toMerge, String mergedSourceDescription) {
      Iterator var3 = toMerge.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         Object existing = original.get(entry.getKey());
         if (existing != null) {
            ConfigurationLogger.LOG.configurationKeyAlreadySet(((ConfigurationKey)entry.getKey()).get(), existing, entry.getValue(), mergedSourceDescription);
         } else {
            original.put(entry.getKey(), entry.getValue());
         }
      }

   }

   static void checkRequiredType(ConfigurationKey key, Class requiredType) {
      if (!key.isValidValueType(requiredType)) {
         throw ConfigurationLogger.LOG.configurationPropertyTypeMismatch(key.getDefaultValue().getClass(), requiredType, key.get());
      }
   }

   static String getSystemProperty(String key) {
      try {
         return (String)AccessController.doPrivileged(new GetSystemPropertyAction(key));
      } catch (Throwable var2) {
         return null;
      }
   }

   private Map init(ServiceRegistry services, Deployment deployment) {
      Map properties = this.readFileProperties(this.findPropertiesFiles(deployment, "weld.properties"));
      merge(properties, this.readObsoleteFileProperties(this.findPropertiesFiles(deployment, "org.jboss.weld.bootstrap.properties"), ImmutableMap.builder().put("concurrentDeployment", ConfigurationKey.CONCURRENT_DEPLOYMENT).put("preloaderThreadPoolSize", ConfigurationKey.PRELOADER_THREAD_POOL_SIZE).build()), "org.jboss.weld.bootstrap.properties");
      merge(properties, this.readObsoleteFileProperties(this.findPropertiesFiles(deployment, "org.jboss.weld.executor.properties"), ImmutableMap.builder().put("threadPoolSize", ConfigurationKey.EXECUTOR_THREAD_POOL_SIZE).put("threadPoolDebug", ConfigurationKey.EXECUTOR_THREAD_POOL_DEBUG).put("threadPoolType", ConfigurationKey.EXECUTOR_THREAD_POOL_TYPE).put("threadPoolKeepAliveTime", ConfigurationKey.EXECUTOR_THREAD_POOL_KEEP_ALIVE_TIME).build()), "org.jboss.weld.executor.properties");
      if (!this.findPropertiesFiles(deployment, "META-INF/org.jboss.weld.enableUnsafeProxies").isEmpty()) {
         merge(properties, ImmutableMap.of(ConfigurationKey.RELAXED_CONSTRUCTION, true), "META-INF/org.jboss.weld.enableUnsafeProxies");
      }

      merge(properties, this.getSystemProperties(), "system properties");
      merge(properties, this.getObsoleteSystemProperties(), "obsolete system properties");
      merge(properties, this.processExternalConfiguration(this.getExternalConfigurationOptions(services)), "ExternalConfiguration");
      return properties;
   }

   private File initProxyDumpFilePath() {
      String dumpPath = this.getStringProperty(ConfigurationKey.PROXY_DUMP);
      if (!dumpPath.isEmpty()) {
         File tmp = new File(dumpPath);
         if (!tmp.isDirectory() && !tmp.mkdirs()) {
            BeanLogger.LOG.directoryCannotBeCreated(tmp.toString());
            return null;
         } else {
            return tmp;
         }
      } else {
         return null;
      }
   }

   private Pattern initProxyIgnoreFinalMethodsPattern() {
      String ignore = this.getStringProperty(ConfigurationKey.PROXY_IGNORE_FINAL_METHODS);
      return !ignore.isEmpty() ? Pattern.compile(ignore) : null;
   }

   @SuppressFBWarnings(
      value = {"DMI_COLLECTION_OF_URLS"},
      justification = "Only local URLs involved"
   )
   private Set findPropertiesFiles(Deployment deployment, String fileName) {
      Set resourceLoaders = new HashSet();
      Set files = new HashSet();
      ResourceLoader deploymentResourceLoader = (ResourceLoader)deployment.getServices().get(ResourceLoader.class);
      if (deploymentResourceLoader != null) {
         resourceLoaders.add(deploymentResourceLoader);
      }

      Iterator var6 = deployment.getBeanDeploymentArchives().iterator();

      while(var6.hasNext()) {
         BeanDeploymentArchive archive = (BeanDeploymentArchive)var6.next();
         ResourceLoader resourceLoader = (ResourceLoader)archive.getServices().get(ResourceLoader.class);
         if (resourceLoader == null) {
            ConfigurationLogger.LOG.resourceLoaderNotSpecifiedForArchive(archive);
         } else {
            resourceLoaders.add(resourceLoader);
         }
      }

      var6 = resourceLoaders.iterator();

      while(var6.hasNext()) {
         ResourceLoader resourceLoader = (ResourceLoader)var6.next();
         URL file = resourceLoader.getResource(fileName);
         if (file != null) {
            files.add(file);
         }
      }

      return files;
   }

   private Map getSystemProperties() {
      Map found = new EnumMap(ConfigurationKey.class);
      ConfigurationKey[] var2 = ConfigurationKey.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ConfigurationKey key = var2[var4];
         String property = getSystemProperty(key.get());
         if (property != null) {
            this.processKeyValue(found, (ConfigurationKey)key, property);
         }
      }

      return found;
   }

   private Map getObsoleteSystemProperties() {
      Map found = new EnumMap(ConfigurationKey.class);
      String concurrentDeployment = getSystemProperty("org.jboss.weld.bootstrap.properties.concurrentDeployment");
      if (concurrentDeployment != null) {
         this.processKeyValue(found, (ConfigurationKey)ConfigurationKey.CONCURRENT_DEPLOYMENT, concurrentDeployment);
         found.put(ConfigurationKey.CONCURRENT_DEPLOYMENT, ConfigurationKey.CONCURRENT_DEPLOYMENT.convertValue(concurrentDeployment));
      }

      String preloaderThreadPoolSize = getSystemProperty("org.jboss.weld.bootstrap.properties.preloaderThreadPoolSize");
      if (preloaderThreadPoolSize != null) {
         found.put(ConfigurationKey.PRELOADER_THREAD_POOL_SIZE, ConfigurationKey.PRELOADER_THREAD_POOL_SIZE.convertValue(preloaderThreadPoolSize));
      }

      return found;
   }

   @SuppressFBWarnings(
      value = {"DMI_COLLECTION_OF_URLS"},
      justification = "Only local URLs involved"
   )
   private Map readFileProperties(Set files) {
      Map found = new EnumMap(ConfigurationKey.class);
      Iterator var3 = files.iterator();

      while(var3.hasNext()) {
         URL file = (URL)var3.next();
         ConfigurationLogger.LOG.readingPropertiesFile(file);
         Properties fileProperties = this.loadProperties(file);
         Iterator var6 = fileProperties.stringPropertyNames().iterator();

         while(var6.hasNext()) {
            String name = (String)var6.next();
            this.processKeyValue(found, (String)name, fileProperties.getProperty(name));
         }
      }

      return found;
   }

   @SuppressFBWarnings(
      value = {"DMI_COLLECTION_OF_URLS"},
      justification = "Only local URLs involved"
   )
   private Map readObsoleteFileProperties(Set files, Map nameToKeyMap) {
      if (files.isEmpty()) {
         return Collections.emptyMap();
      } else {
         Map found = new EnumMap(ConfigurationKey.class);
         Iterator var4 = files.iterator();

         while(var4.hasNext()) {
            URL file = (URL)var4.next();
            ConfigurationLogger.LOG.readingPropertiesFile(file);
            Properties fileProperties = this.loadProperties(file);
            Iterator var7 = fileProperties.stringPropertyNames().iterator();

            while(var7.hasNext()) {
               String name = (String)var7.next();
               ConfigurationKey key = (ConfigurationKey)nameToKeyMap.get(name);
               if (key != null) {
                  this.processKeyValue(found, (ConfigurationKey)key, fileProperties.getProperty(name));
               } else {
                  ConfigurationLogger.LOG.unsupportedConfigurationKeyFound(name + " in " + fileProperties);
               }
            }
         }

         return found;
      }
   }

   private Map processExternalConfiguration(Map externalConfiguration) {
      Map found = new EnumMap(ConfigurationKey.class);
      Iterator var3 = externalConfiguration.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         this.processKeyValue(found, (String)entry.getKey(), entry.getValue(), true);
      }

      return found;
   }

   private Map getExternalConfigurationOptions(ServiceRegistry services) {
      if (Reflections.isClassLoadable("org.jboss.weld.configuration.spi.ExternalConfiguration", WeldClassLoaderResourceLoader.INSTANCE)) {
         ExternalConfiguration externalConfiguration = (ExternalConfiguration)services.get(ExternalConfiguration.class);
         if (externalConfiguration != null) {
            return externalConfiguration.getConfigurationProperties();
         }
      }

      return Collections.emptyMap();
   }

   private void processKeyValue(Map properties, ConfigurationKey key, Object value) {
      if (value instanceof String) {
         value = key.convertValue((String)value);
      }

      if (key.isValidValue(value)) {
         Object previous = properties.put(key, value);
         if (previous != null && !previous.equals(value)) {
            throw ConfigurationLogger.LOG.configurationKeyHasDifferentValues(key.get(), previous, value);
         }
      } else {
         throw ConfigurationLogger.LOG.invalidConfigurationPropertyValue(value, key.get());
      }
   }

   private void processKeyValue(Map properties, String stringKey, Object value) {
      this.processKeyValue(properties, stringKey, value, false);
   }

   private void processKeyValue(Map properties, String stringKey, Object value, boolean integratorSource) {
      ConfigurationKey key = ConfigurationKey.fromString(stringKey);
      if (key != null) {
         if (key.isIntegratorOnly() && !integratorSource) {
            ConfigurationLogger.LOG.cannotSetIntegratorOnlyConfigurationProperty(stringKey, value);
         } else {
            this.processKeyValue(properties, key, value);
         }
      } else {
         ConfigurationLogger.LOG.unsupportedConfigurationKeyFound(stringKey);
      }

   }

   private Object getProperty(ConfigurationKey key, Class requiredType) {
      checkRequiredType(key, requiredType);
      Object property = this.properties.get(key);
      return property != null ? property : key.getDefaultValue();
   }

   private Properties loadProperties(URL url) {
      Properties properties = new Properties();

      try {
         properties.load(url.openStream());
         return properties;
      } catch (IOException var4) {
         throw new ResourceLoadingException(var4);
      }
   }
}
