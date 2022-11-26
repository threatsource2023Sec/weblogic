package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ResourceUtils;

public abstract class ResourcePatternUtils {
   public static boolean isUrl(@Nullable String resourceLocation) {
      return resourceLocation != null && (resourceLocation.startsWith("classpath*:") || ResourceUtils.isUrl(resourceLocation));
   }

   public static ResourcePatternResolver getResourcePatternResolver(@Nullable ResourceLoader resourceLoader) {
      if (resourceLoader instanceof ResourcePatternResolver) {
         return (ResourcePatternResolver)resourceLoader;
      } else {
         return resourceLoader != null ? new PathMatchingResourcePatternResolver(resourceLoader) : new PathMatchingResourcePatternResolver();
      }
   }
}
