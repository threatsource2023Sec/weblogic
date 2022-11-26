package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.context.annotation.Bean;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.ImportAware;
import com.bea.core.repackaged.springframework.context.annotation.Role;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.event.TransactionalEventListenerFactory;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.util.Collection;

@Configuration
public abstract class AbstractTransactionManagementConfiguration implements ImportAware {
   @Nullable
   protected AnnotationAttributes enableTx;
   @Nullable
   protected PlatformTransactionManager txManager;

   public void setImportMetadata(AnnotationMetadata importMetadata) {
      this.enableTx = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableTransactionManagement.class.getName(), false));
      if (this.enableTx == null) {
         throw new IllegalArgumentException("@EnableTransactionManagement is not present on importing class " + importMetadata.getClassName());
      }
   }

   @Autowired(
      required = false
   )
   void setConfigurers(Collection configurers) {
      if (!CollectionUtils.isEmpty(configurers)) {
         if (configurers.size() > 1) {
            throw new IllegalStateException("Only one TransactionManagementConfigurer may exist");
         } else {
            TransactionManagementConfigurer configurer = (TransactionManagementConfigurer)configurers.iterator().next();
            this.txManager = configurer.annotationDrivenTransactionManager();
         }
      }
   }

   @Bean(
      name = {"com.bea.core.repackaged.springframework.transaction.config.internalTransactionalEventListenerFactory"}
   )
   @Role(2)
   public static TransactionalEventListenerFactory transactionalEventListenerFactory() {
      return new TransactionalEventListenerFactory();
   }
}
