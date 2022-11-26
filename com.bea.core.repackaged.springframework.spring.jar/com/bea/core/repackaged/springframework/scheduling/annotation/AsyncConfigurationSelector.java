package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.context.annotation.AdviceMode;
import com.bea.core.repackaged.springframework.context.annotation.AdviceModeImportSelector;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class AsyncConfigurationSelector extends AdviceModeImportSelector {
   private static final String ASYNC_EXECUTION_ASPECT_CONFIGURATION_CLASS_NAME = "com.bea.core.repackaged.springframework.scheduling.aspectj.AspectJAsyncConfiguration";

   @Nullable
   public String[] selectImports(AdviceMode adviceMode) {
      switch (adviceMode) {
         case PROXY:
            return new String[]{ProxyAsyncConfiguration.class.getName()};
         case ASPECTJ:
            return new String[]{"com.bea.core.repackaged.springframework.scheduling.aspectj.AspectJAsyncConfiguration"};
         default:
            return null;
      }
   }
}
