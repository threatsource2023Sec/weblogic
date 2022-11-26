package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.core.Constants;
import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.PropertyPlaceholderHelper;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.util.Properties;

public class PropertyPlaceholderConfigurer extends PlaceholderConfigurerSupport {
   public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;
   public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;
   public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;
   private static final Constants constants = new Constants(PropertyPlaceholderConfigurer.class);
   private int systemPropertiesMode = 1;
   private boolean searchSystemEnvironment = !SpringProperties.getFlag("spring.getenv.ignore");

   public void setSystemPropertiesModeName(String constantName) throws IllegalArgumentException {
      this.systemPropertiesMode = constants.asNumber(constantName).intValue();
   }

   public void setSystemPropertiesMode(int systemPropertiesMode) {
      this.systemPropertiesMode = systemPropertiesMode;
   }

   public void setSearchSystemEnvironment(boolean searchSystemEnvironment) {
      this.searchSystemEnvironment = searchSystemEnvironment;
   }

   @Nullable
   protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
      String propVal = null;
      if (systemPropertiesMode == 2) {
         propVal = this.resolveSystemProperty(placeholder);
      }

      if (propVal == null) {
         propVal = this.resolvePlaceholder(placeholder, props);
      }

      if (propVal == null && systemPropertiesMode == 1) {
         propVal = this.resolveSystemProperty(placeholder);
      }

      return propVal;
   }

   @Nullable
   protected String resolvePlaceholder(String placeholder, Properties props) {
      return props.getProperty(placeholder);
   }

   @Nullable
   protected String resolveSystemProperty(String key) {
      try {
         String value = System.getProperty(key);
         if (value == null && this.searchSystemEnvironment) {
            value = System.getenv(key);
         }

         return value;
      } catch (Throwable var3) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not access system property '" + key + "': " + var3);
         }

         return null;
      }
   }

   protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
      StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);
      this.doProcessProperties(beanFactoryToProcess, valueResolver);
   }

   private final class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
      private final Properties props;

      private PropertyPlaceholderConfigurerResolver(Properties props) {
         this.props = props;
      }

      @Nullable
      public String resolvePlaceholder(String placeholderName) {
         return PropertyPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, this.props, PropertyPlaceholderConfigurer.this.systemPropertiesMode);
      }

      // $FF: synthetic method
      PropertyPlaceholderConfigurerResolver(Properties x1, Object x2) {
         this(x1);
      }
   }

   private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
      private final PropertyPlaceholderHelper helper;
      private final PropertyPlaceholderHelper.PlaceholderResolver resolver;

      public PlaceholderResolvingStringValueResolver(Properties props) {
         this.helper = new PropertyPlaceholderHelper(PropertyPlaceholderConfigurer.this.placeholderPrefix, PropertyPlaceholderConfigurer.this.placeholderSuffix, PropertyPlaceholderConfigurer.this.valueSeparator, PropertyPlaceholderConfigurer.this.ignoreUnresolvablePlaceholders);
         this.resolver = PropertyPlaceholderConfigurer.this.new PropertyPlaceholderConfigurerResolver(props);
      }

      @Nullable
      public String resolveStringValue(String strVal) throws BeansException {
         String resolved = this.helper.replacePlaceholders(strVal, this.resolver);
         if (PropertyPlaceholderConfigurer.this.trimValues) {
            resolved = resolved.trim();
         }

         return resolved.equals(PropertyPlaceholderConfigurer.this.nullValue) ? null : resolved;
      }
   }
}
