package com.bea.core.repackaged.springframework.core.env;

import java.util.Map;

public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver {
   void setActiveProfiles(String... var1);

   void addActiveProfile(String var1);

   void setDefaultProfiles(String... var1);

   MutablePropertySources getPropertySources();

   Map getSystemProperties();

   Map getSystemEnvironment();

   void merge(ConfigurableEnvironment var1);
}
