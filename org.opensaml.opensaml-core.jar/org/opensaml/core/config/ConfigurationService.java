package org.opensaml.core.config;

import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.config.provider.MapBasedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationService {
   @Nonnull
   public static final String DEFAULT_PARTITION_NAME = "default";
   @Nonnull
   public static final String PROPERTY_PARTITION_NAME = "opensaml.config.partitionName";
   private static ServiceLoader configPropertiesLoader = ServiceLoader.load(ConfigurationPropertiesSource.class);
   private static Configuration configuration;

   protected ConfigurationService() {
   }

   public static Object get(@Nonnull Class configClass) {
      String partitionName = getPartitionName();
      return getConfiguration().get(configClass, partitionName);
   }

   public static void register(@Nonnull Class configClass, @Nonnull Object configInstance) {
      String partitionName = getPartitionName();
      getConfiguration().register(configClass, configInstance, partitionName);
   }

   public static Object deregister(@Nonnull Class configClass) {
      String partitionName = getPartitionName();
      return getConfiguration().deregister(configClass, partitionName);
   }

   @Nullable
   public static Properties getConfigurationProperties() {
      Logger log = getLogger();
      log.trace("Resolving configuration propreties source");
      Iterator iter = configPropertiesLoader.iterator();

      ConfigurationPropertiesSource source;
      Properties props;
      do {
         if (!iter.hasNext()) {
            log.trace("Unable to resolve non-null configuration properties from any ConfigurationPropertiesSource");
            return null;
         }

         source = (ConfigurationPropertiesSource)iter.next();
         log.trace("Evaluating configuration properties implementation: {}", source.getClass().getName());
         props = source.getProperties();
      } while(props == null);

      log.trace("Resolved non-null configuration properties using implementation: {}", source.getClass().getName());
      return props;
   }

   public static void setConfiguration(@Nonnull Configuration newConfiguration) {
      configuration = (Configuration)Constraint.isNotNull(newConfiguration, "Configuration cannot be null");
   }

   @Nonnull
   @NotEmpty
   protected static String getPartitionName() {
      Logger log = getLogger();
      Properties configProperties = getConfigurationProperties();
      String partitionName = null;
      if (configProperties != null) {
         partitionName = configProperties.getProperty("opensaml.config.partitionName", "default");
      } else {
         partitionName = "default";
      }

      log.trace("Resolved effective configuration partition name '{}'", partitionName);
      return partitionName;
   }

   @Nonnull
   protected static Configuration getConfiguration() {
      if (configuration == null) {
         Class var0 = ConfigurationService.class;
         synchronized(ConfigurationService.class) {
            ServiceLoader loader = ServiceLoader.load(Configuration.class);
            Iterator iter = loader.iterator();
            if (iter.hasNext()) {
               configuration = (Configuration)iter.next();
            } else {
               configuration = new MapBasedConfiguration();
            }
         }
      }

      return configuration;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(ConfigurationService.class);
   }
}
