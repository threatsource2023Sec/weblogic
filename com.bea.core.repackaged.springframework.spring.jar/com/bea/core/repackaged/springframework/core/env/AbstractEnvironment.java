package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.core.convert.support.ConfigurableConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractEnvironment implements ConfigurableEnvironment {
   public static final String IGNORE_GETENV_PROPERTY_NAME = "spring.getenv.ignore";
   public static final String ACTIVE_PROFILES_PROPERTY_NAME = "spring.profiles.active";
   public static final String DEFAULT_PROFILES_PROPERTY_NAME = "spring.profiles.default";
   protected static final String RESERVED_DEFAULT_PROFILE_NAME = "default";
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Set activeProfiles = new LinkedHashSet();
   private final Set defaultProfiles = new LinkedHashSet(this.getReservedDefaultProfiles());
   private final MutablePropertySources propertySources = new MutablePropertySources();
   private final ConfigurablePropertyResolver propertyResolver;

   public AbstractEnvironment() {
      this.propertyResolver = new PropertySourcesPropertyResolver(this.propertySources);
      this.customizePropertySources(this.propertySources);
   }

   protected void customizePropertySources(MutablePropertySources propertySources) {
   }

   protected Set getReservedDefaultProfiles() {
      return Collections.singleton("default");
   }

   public String[] getActiveProfiles() {
      return StringUtils.toStringArray((Collection)this.doGetActiveProfiles());
   }

   protected Set doGetActiveProfiles() {
      synchronized(this.activeProfiles) {
         if (this.activeProfiles.isEmpty()) {
            String profiles = this.getProperty("spring.profiles.active");
            if (StringUtils.hasText(profiles)) {
               this.setActiveProfiles(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(profiles)));
            }
         }

         return this.activeProfiles;
      }
   }

   public void setActiveProfiles(String... profiles) {
      Assert.notNull(profiles, (String)"Profile array must not be null");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Activating profiles " + Arrays.asList(profiles));
      }

      synchronized(this.activeProfiles) {
         this.activeProfiles.clear();
         String[] var3 = profiles;
         int var4 = profiles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String profile = var3[var5];
            this.validateProfile(profile);
            this.activeProfiles.add(profile);
         }

      }
   }

   public void addActiveProfile(String profile) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Activating profile '" + profile + "'");
      }

      this.validateProfile(profile);
      this.doGetActiveProfiles();
      synchronized(this.activeProfiles) {
         this.activeProfiles.add(profile);
      }
   }

   public String[] getDefaultProfiles() {
      return StringUtils.toStringArray((Collection)this.doGetDefaultProfiles());
   }

   protected Set doGetDefaultProfiles() {
      synchronized(this.defaultProfiles) {
         if (this.defaultProfiles.equals(this.getReservedDefaultProfiles())) {
            String profiles = this.getProperty("spring.profiles.default");
            if (StringUtils.hasText(profiles)) {
               this.setDefaultProfiles(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(profiles)));
            }
         }

         return this.defaultProfiles;
      }
   }

   public void setDefaultProfiles(String... profiles) {
      Assert.notNull(profiles, (String)"Profile array must not be null");
      synchronized(this.defaultProfiles) {
         this.defaultProfiles.clear();
         String[] var3 = profiles;
         int var4 = profiles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String profile = var3[var5];
            this.validateProfile(profile);
            this.defaultProfiles.add(profile);
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public boolean acceptsProfiles(String... profiles) {
      Assert.notEmpty((Object[])profiles, (String)"Must specify at least one profile");
      String[] var2 = profiles;
      int var3 = profiles.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String profile = var2[var4];
         if (StringUtils.hasLength(profile) && profile.charAt(0) == '!') {
            if (!this.isProfileActive(profile.substring(1))) {
               return true;
            }
         } else if (this.isProfileActive(profile)) {
            return true;
         }
      }

      return false;
   }

   public boolean acceptsProfiles(Profiles profiles) {
      Assert.notNull(profiles, (String)"Profiles must not be null");
      return profiles.matches(this::isProfileActive);
   }

   protected boolean isProfileActive(String profile) {
      this.validateProfile(profile);
      Set currentActiveProfiles = this.doGetActiveProfiles();
      return currentActiveProfiles.contains(profile) || currentActiveProfiles.isEmpty() && this.doGetDefaultProfiles().contains(profile);
   }

   protected void validateProfile(String profile) {
      if (!StringUtils.hasText(profile)) {
         throw new IllegalArgumentException("Invalid profile [" + profile + "]: must contain text");
      } else if (profile.charAt(0) == '!') {
         throw new IllegalArgumentException("Invalid profile [" + profile + "]: must not begin with ! operator");
      }
   }

   public MutablePropertySources getPropertySources() {
      return this.propertySources;
   }

   public Map getSystemProperties() {
      try {
         return System.getProperties();
      } catch (AccessControlException var2) {
         return new ReadOnlySystemAttributesMap() {
            @Nullable
            protected String getSystemAttribute(String attributeName) {
               try {
                  return System.getProperty(attributeName);
               } catch (AccessControlException var3) {
                  if (AbstractEnvironment.this.logger.isInfoEnabled()) {
                     AbstractEnvironment.this.logger.info("Caught AccessControlException when accessing system property '" + attributeName + "'; its value will be returned [null]. Reason: " + var3.getMessage());
                  }

                  return null;
               }
            }
         };
      }
   }

   public Map getSystemEnvironment() {
      if (this.suppressGetenvAccess()) {
         return Collections.emptyMap();
      } else {
         try {
            return System.getenv();
         } catch (AccessControlException var2) {
            return new ReadOnlySystemAttributesMap() {
               @Nullable
               protected String getSystemAttribute(String attributeName) {
                  try {
                     return System.getenv(attributeName);
                  } catch (AccessControlException var3) {
                     if (AbstractEnvironment.this.logger.isInfoEnabled()) {
                        AbstractEnvironment.this.logger.info("Caught AccessControlException when accessing system environment variable '" + attributeName + "'; its value will be returned [null]. Reason: " + var3.getMessage());
                     }

                     return null;
                  }
               }
            };
         }
      }
   }

   protected boolean suppressGetenvAccess() {
      return SpringProperties.getFlag("spring.getenv.ignore");
   }

   public void merge(ConfigurableEnvironment parent) {
      Iterator var2 = parent.getPropertySources().iterator();

      while(var2.hasNext()) {
         PropertySource ps = (PropertySource)var2.next();
         if (!this.propertySources.contains(ps.getName())) {
            this.propertySources.addLast(ps);
         }
      }

      String[] parentActiveProfiles = parent.getActiveProfiles();
      int var6;
      if (!ObjectUtils.isEmpty((Object[])parentActiveProfiles)) {
         synchronized(this.activeProfiles) {
            String[] var4 = parentActiveProfiles;
            int var5 = parentActiveProfiles.length;

            for(var6 = 0; var6 < var5; ++var6) {
               String profile = var4[var6];
               this.activeProfiles.add(profile);
            }
         }
      }

      String[] parentDefaultProfiles = parent.getDefaultProfiles();
      if (!ObjectUtils.isEmpty((Object[])parentDefaultProfiles)) {
         synchronized(this.defaultProfiles) {
            this.defaultProfiles.remove("default");
            String[] var14 = parentDefaultProfiles;
            var6 = parentDefaultProfiles.length;

            for(int var15 = 0; var15 < var6; ++var15) {
               String profile = var14[var15];
               this.defaultProfiles.add(profile);
            }
         }
      }

   }

   public ConfigurableConversionService getConversionService() {
      return this.propertyResolver.getConversionService();
   }

   public void setConversionService(ConfigurableConversionService conversionService) {
      this.propertyResolver.setConversionService(conversionService);
   }

   public void setPlaceholderPrefix(String placeholderPrefix) {
      this.propertyResolver.setPlaceholderPrefix(placeholderPrefix);
   }

   public void setPlaceholderSuffix(String placeholderSuffix) {
      this.propertyResolver.setPlaceholderSuffix(placeholderSuffix);
   }

   public void setValueSeparator(@Nullable String valueSeparator) {
      this.propertyResolver.setValueSeparator(valueSeparator);
   }

   public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
      this.propertyResolver.setIgnoreUnresolvableNestedPlaceholders(ignoreUnresolvableNestedPlaceholders);
   }

   public void setRequiredProperties(String... requiredProperties) {
      this.propertyResolver.setRequiredProperties(requiredProperties);
   }

   public void validateRequiredProperties() throws MissingRequiredPropertiesException {
      this.propertyResolver.validateRequiredProperties();
   }

   public boolean containsProperty(String key) {
      return this.propertyResolver.containsProperty(key);
   }

   @Nullable
   public String getProperty(String key) {
      return this.propertyResolver.getProperty(key);
   }

   public String getProperty(String key, String defaultValue) {
      return this.propertyResolver.getProperty(key, defaultValue);
   }

   @Nullable
   public Object getProperty(String key, Class targetType) {
      return this.propertyResolver.getProperty(key, targetType);
   }

   public Object getProperty(String key, Class targetType, Object defaultValue) {
      return this.propertyResolver.getProperty(key, targetType, defaultValue);
   }

   public String getRequiredProperty(String key) throws IllegalStateException {
      return this.propertyResolver.getRequiredProperty(key);
   }

   public Object getRequiredProperty(String key, Class targetType) throws IllegalStateException {
      return this.propertyResolver.getRequiredProperty(key, targetType);
   }

   public String resolvePlaceholders(String text) {
      return this.propertyResolver.resolvePlaceholders(text);
   }

   public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
      return this.propertyResolver.resolveRequiredPlaceholders(text);
   }

   public String toString() {
      return this.getClass().getSimpleName() + " {activeProfiles=" + this.activeProfiles + ", defaultProfiles=" + this.defaultProfiles + ", propertySources=" + this.propertySources + "}";
   }
}
