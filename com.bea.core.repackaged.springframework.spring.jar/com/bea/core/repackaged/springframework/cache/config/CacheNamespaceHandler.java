package com.bea.core.repackaged.springframework.cache.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class CacheNamespaceHandler extends NamespaceHandlerSupport {
   static final String CACHE_MANAGER_ATTRIBUTE = "cache-manager";
   static final String DEFAULT_CACHE_MANAGER_BEAN_NAME = "cacheManager";

   static String extractCacheManager(Element element) {
      return element.hasAttribute("cache-manager") ? element.getAttribute("cache-manager") : "cacheManager";
   }

   static BeanDefinition parseKeyGenerator(Element element, BeanDefinition def) {
      String name = element.getAttribute("key-generator");
      if (StringUtils.hasText(name)) {
         def.getPropertyValues().add("keyGenerator", new RuntimeBeanReference(name.trim()));
      }

      return def;
   }

   public void init() {
      this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenCacheBeanDefinitionParser());
      this.registerBeanDefinitionParser("advice", new CacheAdviceParser());
   }
}
