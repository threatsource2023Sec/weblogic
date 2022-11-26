package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.IllegalStateException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ConfigurationLogger extends WeldLogger {
   ConfigurationLogger LOG = (ConfigurationLogger)Logger.getMessageLogger(ConfigurationLogger.class, Category.CONFIGURATION.getName());

   @Message(
      id = 1900,
      value = "Invalid configuration property value {0} for key {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException invalidConfigurationPropertyValue(Object var1, Object var2);

   @Message(
      id = 1901,
      value = "Configuration property type {0} does not match the required type {1} for configuration key {2}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException configurationPropertyTypeMismatch(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1902,
      value = "Following configuration was detected and applied: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void configurationInitialized(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1903,
      value = "Configuration key {0} already set to {1} in a source with higher priority, value {2} from {3} is ignored",
      format = Format.MESSAGE_FORMAT
   )
   void configurationKeyAlreadySet(Object var1, Object var2, Object var3, String var4);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1904,
      value = "Unsupported configuration key found and ignored: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void unsupportedConfigurationKeyFound(Object var1);

   @Message(
      id = 1905,
      value = "Configuration key {0} set to different values in the same source:\n - {1}\n - {2}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException configurationKeyHasDifferentValues(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1906,
      value = "ResourceLoader not specified for {0}, file properties will not be loaded",
      format = Format.MESSAGE_FORMAT
   )
   void resourceLoaderNotSpecifiedForArchive(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 1907,
      value = "Reading properties file: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void readingPropertiesFile(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1908,
      value = "Configuration property {0} can only be set by integrator - value {1} ignored",
      format = Format.MESSAGE_FORMAT
   )
   void cannotSetIntegratorOnlyConfigurationProperty(Object var1, Object var2);
}
