package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.IllegalStateException;

public class ConfigurationLogger_$logger extends DelegatingBasicLogger implements ConfigurationLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ConfigurationLogger_$logger.class.getName();
   private static final String invalidConfigurationPropertyValue = "WELD-001900: Invalid configuration property value {0} for key {1}";
   private static final String configurationPropertyTypeMismatch = "WELD-001901: Configuration property type {0} does not match the required type {1} for configuration key {2}";
   private static final String configurationInitialized = "WELD-001902: Following configuration was detected and applied: {0}";
   private static final String configurationKeyAlreadySet = "WELD-001903: Configuration key {0} already set to {1} in a source with higher priority, value {2} from {3} is ignored";
   private static final String unsupportedConfigurationKeyFound = "WELD-001904: Unsupported configuration key found and ignored: {0}";
   private static final String configurationKeyHasDifferentValues = "WELD-001905: Configuration key {0} set to different values in the same source:\n - {1}\n - {2}";
   private static final String resourceLoaderNotSpecifiedForArchive = "WELD-001906: ResourceLoader not specified for {0}, file properties will not be loaded";
   private static final String readingPropertiesFile = "WELD-001907: Reading properties file: {0}";
   private static final String cannotSetIntegratorOnlyConfigurationProperty = "WELD-001908: Configuration property {0} can only be set by integrator - value {1} ignored";
   private static final String catchingDebug = "Catching";

   public ConfigurationLogger_$logger(Logger log) {
      super(log);
   }

   protected String invalidConfigurationPropertyValue$str() {
      return "WELD-001900: Invalid configuration property value {0} for key {1}";
   }

   public final IllegalStateException invalidConfigurationPropertyValue(Object value, Object key) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.invalidConfigurationPropertyValue$str(), value, key));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String configurationPropertyTypeMismatch$str() {
      return "WELD-001901: Configuration property type {0} does not match the required type {1} for configuration key {2}";
   }

   public final IllegalStateException configurationPropertyTypeMismatch(Object propertyType, Object requiredType, Object key) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.configurationPropertyTypeMismatch$str(), propertyType, requiredType, key));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void configurationInitialized(Object configuration) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configurationInitialized$str(), configuration);
   }

   protected String configurationInitialized$str() {
      return "WELD-001902: Following configuration was detected and applied: {0}";
   }

   public final void configurationKeyAlreadySet(Object configurationKey, Object value, Object ignoredValue, String mergedSourceDescription) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.configurationKeyAlreadySet$str(), new Object[]{configurationKey, value, ignoredValue, mergedSourceDescription});
   }

   protected String configurationKeyAlreadySet$str() {
      return "WELD-001903: Configuration key {0} already set to {1} in a source with higher priority, value {2} from {3} is ignored";
   }

   public final void unsupportedConfigurationKeyFound(Object key) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.unsupportedConfigurationKeyFound$str(), key);
   }

   protected String unsupportedConfigurationKeyFound$str() {
      return "WELD-001904: Unsupported configuration key found and ignored: {0}";
   }

   protected String configurationKeyHasDifferentValues$str() {
      return "WELD-001905: Configuration key {0} set to different values in the same source:\n - {1}\n - {2}";
   }

   public final IllegalStateException configurationKeyHasDifferentValues(Object key, Object value1, Object value2) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.configurationKeyHasDifferentValues$str(), key, value1, value2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void resourceLoaderNotSpecifiedForArchive(Object archive) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.resourceLoaderNotSpecifiedForArchive$str(), archive);
   }

   protected String resourceLoaderNotSpecifiedForArchive$str() {
      return "WELD-001906: ResourceLoader not specified for {0}, file properties will not be loaded";
   }

   public final void readingPropertiesFile(Object file) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.readingPropertiesFile$str(), file);
   }

   protected String readingPropertiesFile$str() {
      return "WELD-001907: Reading properties file: {0}";
   }

   public final void cannotSetIntegratorOnlyConfigurationProperty(Object key, Object value) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.cannotSetIntegratorOnlyConfigurationProperty$str(), key, value);
   }

   protected String cannotSetIntegratorOnlyConfigurationProperty$str() {
      return "WELD-001908: Configuration property {0} can only be set by integrator - value {1} ignored";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
