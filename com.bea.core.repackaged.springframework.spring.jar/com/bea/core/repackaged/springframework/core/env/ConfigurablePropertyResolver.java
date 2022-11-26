package com.bea.core.repackaged.springframework.core.env;

import com.bea.core.repackaged.springframework.core.convert.support.ConfigurableConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface ConfigurablePropertyResolver extends PropertyResolver {
   ConfigurableConversionService getConversionService();

   void setConversionService(ConfigurableConversionService var1);

   void setPlaceholderPrefix(String var1);

   void setPlaceholderSuffix(String var1);

   void setValueSeparator(@Nullable String var1);

   void setIgnoreUnresolvableNestedPlaceholders(boolean var1);

   void setRequiredProperties(String... var1);

   void validateRequiredProperties() throws MissingRequiredPropertiesException;
}
