package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.context.annotation.AdviceMode;
import com.bea.core.repackaged.springframework.context.annotation.AdviceModeImportSelector;
import com.bea.core.repackaged.springframework.context.annotation.AutoProxyRegistrar;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CachingConfigurationSelector extends AdviceModeImportSelector {
   private static final String PROXY_JCACHE_CONFIGURATION_CLASS = "com.bea.core.repackaged.springframework.cache.jcache.config.ProxyJCacheConfiguration";
   private static final String CACHE_ASPECT_CONFIGURATION_CLASS_NAME = "com.bea.core.repackaged.springframework.cache.aspectj.AspectJCachingConfiguration";
   private static final String JCACHE_ASPECT_CONFIGURATION_CLASS_NAME = "com.bea.core.repackaged.springframework.cache.aspectj.AspectJJCacheConfiguration";
   private static final boolean jsr107Present;
   private static final boolean jcacheImplPresent;

   public String[] selectImports(AdviceMode adviceMode) {
      switch (adviceMode) {
         case PROXY:
            return this.getProxyImports();
         case ASPECTJ:
            return this.getAspectJImports();
         default:
            return null;
      }
   }

   private String[] getProxyImports() {
      List result = new ArrayList(3);
      result.add(AutoProxyRegistrar.class.getName());
      result.add(ProxyCachingConfiguration.class.getName());
      if (jsr107Present && jcacheImplPresent) {
         result.add("com.bea.core.repackaged.springframework.cache.jcache.config.ProxyJCacheConfiguration");
      }

      return StringUtils.toStringArray((Collection)result);
   }

   private String[] getAspectJImports() {
      List result = new ArrayList(2);
      result.add("com.bea.core.repackaged.springframework.cache.aspectj.AspectJCachingConfiguration");
      if (jsr107Present && jcacheImplPresent) {
         result.add("com.bea.core.repackaged.springframework.cache.aspectj.AspectJJCacheConfiguration");
      }

      return StringUtils.toStringArray((Collection)result);
   }

   static {
      ClassLoader classLoader = CachingConfigurationSelector.class.getClassLoader();
      jsr107Present = ClassUtils.isPresent("javax.cache.Cache", classLoader);
      jcacheImplPresent = ClassUtils.isPresent("com.bea.core.repackaged.springframework.cache.jcache.config.ProxyJCacheConfiguration", classLoader);
   }
}
