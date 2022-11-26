package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.support.ConfigurableConversionService;
import com.bea.core.repackaged.springframework.core.convert.support.DefaultConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.PropertyPlaceholderHelper;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractPropertyResolver implements ConfigurablePropertyResolver {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private volatile ConfigurableConversionService conversionService;
   @Nullable
   private PropertyPlaceholderHelper nonStrictHelper;
   @Nullable
   private PropertyPlaceholderHelper strictHelper;
   private boolean ignoreUnresolvableNestedPlaceholders = false;
   private String placeholderPrefix = "${";
   private String placeholderSuffix = "}";
   @Nullable
   private String valueSeparator = ":";
   private final Set requiredProperties = new LinkedHashSet();

   public ConfigurableConversionService getConversionService() {
      ConfigurableConversionService cs = this.conversionService;
      if (cs == null) {
         synchronized(this) {
            cs = this.conversionService;
            if (cs == null) {
               cs = new DefaultConversionService();
               this.conversionService = (ConfigurableConversionService)cs;
            }
         }
      }

      return (ConfigurableConversionService)cs;
   }

   public void setConversionService(ConfigurableConversionService conversionService) {
      Assert.notNull(conversionService, (String)"ConversionService must not be null");
      this.conversionService = conversionService;
   }

   public void setPlaceholderPrefix(String placeholderPrefix) {
      Assert.notNull(placeholderPrefix, (String)"'placeholderPrefix' must not be null");
      this.placeholderPrefix = placeholderPrefix;
   }

   public void setPlaceholderSuffix(String placeholderSuffix) {
      Assert.notNull(placeholderSuffix, (String)"'placeholderSuffix' must not be null");
      this.placeholderSuffix = placeholderSuffix;
   }

   public void setValueSeparator(@Nullable String valueSeparator) {
      this.valueSeparator = valueSeparator;
   }

   public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
      this.ignoreUnresolvableNestedPlaceholders = ignoreUnresolvableNestedPlaceholders;
   }

   public void setRequiredProperties(String... requiredProperties) {
      String[] var2 = requiredProperties;
      int var3 = requiredProperties.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String key = var2[var4];
         this.requiredProperties.add(key);
      }

   }

   public void validateRequiredProperties() {
      MissingRequiredPropertiesException ex = new MissingRequiredPropertiesException();
      Iterator var2 = this.requiredProperties.iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         if (this.getProperty(key) == null) {
            ex.addMissingRequiredProperty(key);
         }
      }

      if (!ex.getMissingRequiredProperties().isEmpty()) {
         throw ex;
      }
   }

   public boolean containsProperty(String key) {
      return this.getProperty(key) != null;
   }

   @Nullable
   public String getProperty(String key) {
      return (String)this.getProperty(key, String.class);
   }

   public String getProperty(String key, String defaultValue) {
      String value = this.getProperty(key);
      return value != null ? value : defaultValue;
   }

   public Object getProperty(String key, Class targetType, Object defaultValue) {
      Object value = this.getProperty(key, targetType);
      return value != null ? value : defaultValue;
   }

   public String getRequiredProperty(String key) throws IllegalStateException {
      String value = this.getProperty(key);
      if (value == null) {
         throw new IllegalStateException("Required key '" + key + "' not found");
      } else {
         return value;
      }
   }

   public Object getRequiredProperty(String key, Class valueType) throws IllegalStateException {
      Object value = this.getProperty(key, valueType);
      if (value == null) {
         throw new IllegalStateException("Required key '" + key + "' not found");
      } else {
         return value;
      }
   }

   public String resolvePlaceholders(String text) {
      if (this.nonStrictHelper == null) {
         this.nonStrictHelper = this.createPlaceholderHelper(true);
      }

      return this.doResolvePlaceholders(text, this.nonStrictHelper);
   }

   public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
      if (this.strictHelper == null) {
         this.strictHelper = this.createPlaceholderHelper(false);
      }

      return this.doResolvePlaceholders(text, this.strictHelper);
   }

   protected String resolveNestedPlaceholders(String value) {
      return this.ignoreUnresolvableNestedPlaceholders ? this.resolvePlaceholders(value) : this.resolveRequiredPlaceholders(value);
   }

   private PropertyPlaceholderHelper createPlaceholderHelper(boolean ignoreUnresolvablePlaceholders) {
      return new PropertyPlaceholderHelper(this.placeholderPrefix, this.placeholderSuffix, this.valueSeparator, ignoreUnresolvablePlaceholders);
   }

   private String doResolvePlaceholders(String text, PropertyPlaceholderHelper helper) {
      return helper.replacePlaceholders(text, this::getPropertyAsRawString);
   }

   @Nullable
   protected Object convertValueIfNecessary(Object value, @Nullable Class targetType) {
      if (targetType == null) {
         return value;
      } else {
         ConversionService conversionServiceToUse = this.conversionService;
         if (conversionServiceToUse == null) {
            if (ClassUtils.isAssignableValue(targetType, value)) {
               return value;
            }

            conversionServiceToUse = DefaultConversionService.getSharedInstance();
         }

         return ((ConversionService)conversionServiceToUse).convert(value, targetType);
      }
   }

   @Nullable
   protected abstract String getPropertyAsRawString(String var1);
}
