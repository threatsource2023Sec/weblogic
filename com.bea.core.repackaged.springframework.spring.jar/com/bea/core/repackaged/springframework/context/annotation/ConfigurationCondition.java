package com.bea.core.repackaged.springframework.context.annotation;

public interface ConfigurationCondition extends Condition {
   ConfigurationPhase getConfigurationPhase();

   public static enum ConfigurationPhase {
      PARSE_CONFIGURATION,
      REGISTER_BEAN;
   }
}
