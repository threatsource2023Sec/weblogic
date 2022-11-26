package com.bea.core.repackaged.springframework.cache.annotation;

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
public abstract class AbstractCachingConfiguration implements ImportAware {
   @Nullable
   protected AnnotationAttributes enableCaching;
   @Nullable
   protected Supplier cacheManager;
   @Nullable
   protected Supplier cacheResolver;
   @Nullable
   protected Supplier keyGenerator;
   @Nullable
   protected Supplier errorHandler;

   public void setImportMetadata(AnnotationMetadata importMetadata) {
      this.enableCaching = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableCaching.class.getName(), false));
      if (this.enableCaching == null) {
         throw new IllegalArgumentException("@EnableCaching is not present on importing class " + importMetadata.getClassName());
      }
   }

   @Autowired(
      required = false
   )
   void setConfigurers(Collection configurers) {
      if (!CollectionUtils.isEmpty(configurers)) {
         if (configurers.size() > 1) {
            throw new IllegalStateException(configurers.size() + " implementations of CachingConfigurer were found when only 1 was expected. Refactor the configuration such that CachingConfigurer is implemented only once or not at all.");
         } else {
            CachingConfigurer configurer = (CachingConfigurer)configurers.iterator().next();
            this.useCachingConfigurer(configurer);
         }
      }
   }

   protected void useCachingConfigurer(CachingConfigurer config) {
      this.cacheManager = config::cacheManager;
      this.cacheResolver = config::cacheResolver;
      this.keyGenerator = config::keyGenerator;
      this.errorHandler = config::errorHandler;
   }
}
