package com.bea.core.repackaged.springframework.cache.config;

import com.bea.core.repackaged.springframework.beans.factory.config.TypedStringValue;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ReaderContext;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedList;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedMap;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheEvictOperation;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheInterceptor;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheOperation;
import com.bea.core.repackaged.springframework.cache.interceptor.CachePutOperation;
import com.bea.core.repackaged.springframework.cache.interceptor.CacheableOperation;
import com.bea.core.repackaged.springframework.cache.interceptor.NameMatchCacheOperationSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.xml.DomUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;

class CacheAdviceParser extends AbstractSingleBeanDefinitionParser {
   private static final String CACHEABLE_ELEMENT = "cacheable";
   private static final String CACHE_EVICT_ELEMENT = "cache-evict";
   private static final String CACHE_PUT_ELEMENT = "cache-put";
   private static final String METHOD_ATTRIBUTE = "method";
   private static final String DEFS_ELEMENT = "caching";

   protected Class getBeanClass(Element element) {
      return CacheInterceptor.class;
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      builder.addPropertyReference("cacheManager", CacheNamespaceHandler.extractCacheManager(element));
      CacheNamespaceHandler.parseKeyGenerator(element, builder.getBeanDefinition());
      List cacheDefs = DomUtils.getChildElementsByTagName(element, "caching");
      if (!cacheDefs.isEmpty()) {
         List attributeSourceDefinitions = this.parseDefinitionsSources(cacheDefs, parserContext);
         builder.addPropertyValue("cacheOperationSources", attributeSourceDefinitions);
      } else {
         builder.addPropertyValue("cacheOperationSources", new RootBeanDefinition("com.bea.core.repackaged.springframework.cache.annotation.AnnotationCacheOperationSource"));
      }

   }

   private List parseDefinitionsSources(List definitions, ParserContext parserContext) {
      ManagedList defs = new ManagedList(definitions.size());
      Iterator var4 = definitions.iterator();

      while(var4.hasNext()) {
         Element element = (Element)var4.next();
         defs.add(this.parseDefinitionSource(element, parserContext));
      }

      return defs;
   }

   private RootBeanDefinition parseDefinitionSource(Element definition, ParserContext parserContext) {
      Props prop = new Props(definition);
      ManagedMap cacheOpMap = new ManagedMap();
      cacheOpMap.setSource(parserContext.extractSource(definition));
      List cacheableCacheMethods = DomUtils.getChildElementsByTagName(definition, "cacheable");

      CacheableOperation.Builder builder;
      Object col;
      for(Iterator var6 = cacheableCacheMethods.iterator(); var6.hasNext(); ((Collection)col).add(builder.build())) {
         Element opElement = (Element)var6.next();
         String name = prop.merge(opElement, parserContext.getReaderContext());
         TypedStringValue nameHolder = new TypedStringValue(name);
         nameHolder.setSource(parserContext.extractSource(opElement));
         builder = (CacheableOperation.Builder)prop.merge(opElement, parserContext.getReaderContext(), new CacheableOperation.Builder());
         builder.setUnless(getAttributeValue(opElement, "unless", ""));
         builder.setSync(Boolean.valueOf(getAttributeValue(opElement, "sync", "false")));
         col = (Collection)cacheOpMap.get(nameHolder);
         if (col == null) {
            col = new ArrayList(2);
            cacheOpMap.put(nameHolder, col);
         }
      }

      List evictCacheMethods = DomUtils.getChildElementsByTagName(definition, "cache-evict");

      Object col;
      CacheEvictOperation.Builder builder;
      for(Iterator var16 = evictCacheMethods.iterator(); var16.hasNext(); ((Collection)col).add(builder.build())) {
         Element opElement = (Element)var16.next();
         String name = prop.merge(opElement, parserContext.getReaderContext());
         TypedStringValue nameHolder = new TypedStringValue(name);
         nameHolder.setSource(parserContext.extractSource(opElement));
         builder = (CacheEvictOperation.Builder)prop.merge(opElement, parserContext.getReaderContext(), new CacheEvictOperation.Builder());
         String wide = opElement.getAttribute("all-entries");
         if (StringUtils.hasText(wide)) {
            builder.setCacheWide(Boolean.valueOf(wide.trim()));
         }

         String after = opElement.getAttribute("before-invocation");
         if (StringUtils.hasText(after)) {
            builder.setBeforeInvocation(Boolean.valueOf(after.trim()));
         }

         col = (Collection)cacheOpMap.get(nameHolder);
         if (col == null) {
            col = new ArrayList(2);
            cacheOpMap.put(nameHolder, col);
         }
      }

      List putCacheMethods = DomUtils.getChildElementsByTagName(definition, "cache-put");

      CachePutOperation.Builder builder;
      Object col;
      for(Iterator var19 = putCacheMethods.iterator(); var19.hasNext(); ((Collection)col).add(builder.build())) {
         Element opElement = (Element)var19.next();
         String name = prop.merge(opElement, parserContext.getReaderContext());
         TypedStringValue nameHolder = new TypedStringValue(name);
         nameHolder.setSource(parserContext.extractSource(opElement));
         builder = (CachePutOperation.Builder)prop.merge(opElement, parserContext.getReaderContext(), new CachePutOperation.Builder());
         builder.setUnless(getAttributeValue(opElement, "unless", ""));
         col = (Collection)cacheOpMap.get(nameHolder);
         if (col == null) {
            col = new ArrayList(2);
            cacheOpMap.put(nameHolder, col);
         }
      }

      RootBeanDefinition attributeSourceDefinition = new RootBeanDefinition(NameMatchCacheOperationSource.class);
      attributeSourceDefinition.setSource(parserContext.extractSource(definition));
      attributeSourceDefinition.getPropertyValues().add("nameMap", cacheOpMap);
      return attributeSourceDefinition;
   }

   private static String getAttributeValue(Element element, String attributeName, String defaultValue) {
      String attribute = element.getAttribute(attributeName);
      return StringUtils.hasText(attribute) ? attribute.trim() : defaultValue;
   }

   private static class Props {
      private String key;
      private String keyGenerator;
      private String cacheManager;
      private String condition;
      private String method;
      @Nullable
      private String[] caches;

      Props(Element root) {
         String defaultCache = root.getAttribute("cache");
         this.key = root.getAttribute("key");
         this.keyGenerator = root.getAttribute("key-generator");
         this.cacheManager = root.getAttribute("cache-manager");
         this.condition = root.getAttribute("condition");
         this.method = root.getAttribute("method");
         if (StringUtils.hasText(defaultCache)) {
            this.caches = StringUtils.commaDelimitedListToStringArray(defaultCache.trim());
         }

      }

      CacheOperation.Builder merge(Element element, ReaderContext readerCtx, CacheOperation.Builder builder) {
         String cache = element.getAttribute("cache");
         String[] localCaches = this.caches;
         if (StringUtils.hasText(cache)) {
            localCaches = StringUtils.commaDelimitedListToStringArray(cache.trim());
         }

         if (localCaches != null) {
            builder.setCacheNames(localCaches);
         } else {
            readerCtx.error("No cache specified for " + element.getNodeName(), element);
         }

         builder.setKey(CacheAdviceParser.getAttributeValue(element, "key", this.key));
         builder.setKeyGenerator(CacheAdviceParser.getAttributeValue(element, "key-generator", this.keyGenerator));
         builder.setCacheManager(CacheAdviceParser.getAttributeValue(element, "cache-manager", this.cacheManager));
         builder.setCondition(CacheAdviceParser.getAttributeValue(element, "condition", this.condition));
         if (StringUtils.hasText(builder.getKey()) && StringUtils.hasText(builder.getKeyGenerator())) {
            throw new IllegalStateException("Invalid cache advice configuration on '" + element.toString() + "'. Both 'key' and 'keyGenerator' attributes have been set. These attributes are mutually exclusive: either set the SpEL expression used tocompute the key at runtime or set the name of the KeyGenerator bean to use.");
         } else {
            return builder;
         }
      }

      @Nullable
      String merge(Element element, ReaderContext readerCtx) {
         String method = element.getAttribute("method");
         if (StringUtils.hasText(method)) {
            return method.trim();
         } else if (StringUtils.hasText(this.method)) {
            return this.method;
         } else {
            readerCtx.error("No method specified for " + element.getNodeName(), element);
            return null;
         }
      }
   }
}
