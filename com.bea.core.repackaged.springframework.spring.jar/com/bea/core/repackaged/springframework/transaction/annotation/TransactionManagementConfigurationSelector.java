package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.context.annotation.AdviceMode;
import com.bea.core.repackaged.springframework.context.annotation.AdviceModeImportSelector;
import com.bea.core.repackaged.springframework.context.annotation.AutoProxyRegistrar;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public class TransactionManagementConfigurationSelector extends AdviceModeImportSelector {
   protected String[] selectImports(AdviceMode adviceMode) {
      switch (adviceMode) {
         case PROXY:
            return new String[]{AutoProxyRegistrar.class.getName(), ProxyTransactionManagementConfiguration.class.getName()};
         case ASPECTJ:
            return new String[]{this.determineTransactionAspectClass()};
         default:
            return null;
      }
   }

   private String determineTransactionAspectClass() {
      return ClassUtils.isPresent("javax.transaction.Transactional", this.getClass().getClassLoader()) ? "com.bea.core.repackaged.springframework.transaction.aspectj.AspectJJtaTransactionManagementConfiguration" : "com.bea.core.repackaged.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration";
   }
}
