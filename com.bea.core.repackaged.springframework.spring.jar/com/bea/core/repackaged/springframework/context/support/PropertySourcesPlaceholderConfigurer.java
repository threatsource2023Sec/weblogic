package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import com.bea.core.repackaged.springframework.context.EnvironmentAware;
import com.bea.core.repackaged.springframework.core.env.ConfigurablePropertyResolver;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.MutablePropertySources;
import com.bea.core.repackaged.springframework.core.env.PropertiesPropertySource;
import com.bea.core.repackaged.springframework.core.env.PropertySource;
import com.bea.core.repackaged.springframework.core.env.PropertySources;
import com.bea.core.repackaged.springframework.core.env.PropertySourcesPropertyResolver;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.io.IOException;
import java.util.Properties;

public class PropertySourcesPlaceholderConfigurer extends PlaceholderConfigurerSupport implements EnvironmentAware {
   public static final String LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME = "localProperties";
   public static final String ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME = "environmentProperties";
   @Nullable
   private MutablePropertySources propertySources;
   @Nullable
   private PropertySources appliedPropertySources;
   @Nullable
   private Environment environment;

   public void setPropertySources(PropertySources propertySources) {
      this.propertySources = new MutablePropertySources(propertySources);
   }

   public void setEnvironment(Environment environment) {
      this.environment = environment;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      if (this.propertySources == null) {
         this.propertySources = new MutablePropertySources();
         if (this.environment != null) {
            this.propertySources.addLast(new PropertySource("environmentProperties", this.environment) {
               @Nullable
               public String getProperty(String key) {
                  return ((Environment)this.source).getProperty(key);
               }
            });
         }

         try {
            PropertySource localPropertySource = new PropertiesPropertySource("localProperties", this.mergeProperties());
            if (this.localOverride) {
               this.propertySources.addFirst(localPropertySource);
            } else {
               this.propertySources.addLast(localPropertySource);
            }
         } catch (IOException var3) {
            throw new BeanInitializationException("Could not load properties", var3);
         }
      }

      this.processProperties(beanFactory, (ConfigurablePropertyResolver)(new PropertySourcesPropertyResolver(this.propertySources)));
      this.appliedPropertySources = this.propertySources;
   }

   protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
      propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
      propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
      propertyResolver.setValueSeparator(this.valueSeparator);
      StringValueResolver valueResolver = (strVal) -> {
         String resolved = this.ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal) : propertyResolver.resolveRequiredPlaceholders(strVal);
         if (this.trimValues) {
            resolved = resolved.trim();
         }

         return resolved.equals(this.nullValue) ? null : resolved;
      };
      this.doProcessProperties(beanFactoryToProcess, valueResolver);
   }

   /** @deprecated */
   @Deprecated
   protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
      throw new UnsupportedOperationException("Call processProperties(ConfigurableListableBeanFactory, ConfigurablePropertyResolver) instead");
   }

   public PropertySources getAppliedPropertySources() throws IllegalStateException {
      Assert.state(this.appliedPropertySources != null, "PropertySources have not yet been applied");
      return this.appliedPropertySources;
   }
}
