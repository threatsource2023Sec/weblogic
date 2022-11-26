package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.ImportAware;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.function.Supplier;

@Configuration
public abstract class AbstractAsyncConfiguration implements ImportAware {
   @Nullable
   protected AnnotationAttributes enableAsync;
   @Nullable
   protected Supplier executor;
   @Nullable
   protected Supplier exceptionHandler;

   public void setImportMetadata(AnnotationMetadata importMetadata) {
      this.enableAsync = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableAsync.class.getName(), false));
      if (this.enableAsync == null) {
         throw new IllegalArgumentException("@EnableAsync is not present on importing class " + importMetadata.getClassName());
      }
   }

   @Autowired(
      required = false
   )
   void setConfigurers(Collection configurers) {
      if (!CollectionUtils.isEmpty(configurers)) {
         if (configurers.size() > 1) {
            throw new IllegalStateException("Only one AsyncConfigurer may exist");
         } else {
            AsyncConfigurer configurer = (AsyncConfigurer)configurers.iterator().next();
            this.executor = configurer::getAsyncExecutor;
            this.exceptionHandler = configurer::getAsyncUncaughtExceptionHandler;
         }
      }
   }
}
