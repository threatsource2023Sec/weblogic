package com.bea.core.repackaged.springframework.cache.annotation;

import com.bea.core.repackaged.springframework.cache.interceptor.AbstractFallbackCacheOperationSource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationCacheOperationSource extends AbstractFallbackCacheOperationSource implements Serializable {
   private final boolean publicMethodsOnly;
   private final Set annotationParsers;

   public AnnotationCacheOperationSource() {
      this(true);
   }

   public AnnotationCacheOperationSource(boolean publicMethodsOnly) {
      this.publicMethodsOnly = publicMethodsOnly;
      this.annotationParsers = Collections.singleton(new SpringCacheAnnotationParser());
   }

   public AnnotationCacheOperationSource(CacheAnnotationParser annotationParser) {
      this.publicMethodsOnly = true;
      Assert.notNull(annotationParser, (String)"CacheAnnotationParser must not be null");
      this.annotationParsers = Collections.singleton(annotationParser);
   }

   public AnnotationCacheOperationSource(CacheAnnotationParser... annotationParsers) {
      this.publicMethodsOnly = true;
      Assert.notEmpty((Object[])annotationParsers, (String)"At least one CacheAnnotationParser needs to be specified");
      this.annotationParsers = new LinkedHashSet(Arrays.asList(annotationParsers));
   }

   public AnnotationCacheOperationSource(Set annotationParsers) {
      this.publicMethodsOnly = true;
      Assert.notEmpty((Collection)annotationParsers, (String)"At least one CacheAnnotationParser needs to be specified");
      this.annotationParsers = annotationParsers;
   }

   @Nullable
   protected Collection findCacheOperations(Class clazz) {
      return this.determineCacheOperations((parser) -> {
         return parser.parseCacheAnnotations(clazz);
      });
   }

   @Nullable
   protected Collection findCacheOperations(Method method) {
      return this.determineCacheOperations((parser) -> {
         return parser.parseCacheAnnotations(method);
      });
   }

   @Nullable
   protected Collection determineCacheOperations(CacheOperationProvider provider) {
      Collection ops = null;
      Iterator var3 = this.annotationParsers.iterator();

      while(var3.hasNext()) {
         CacheAnnotationParser annotationParser = (CacheAnnotationParser)var3.next();
         Collection annOps = provider.getCacheOperations(annotationParser);
         if (annOps != null) {
            if (ops == null) {
               ops = annOps;
            } else {
               Collection combined = new ArrayList(((Collection)ops).size() + annOps.size());
               combined.addAll((Collection)ops);
               combined.addAll(annOps);
               ops = combined;
            }
         }
      }

      return (Collection)ops;
   }

   protected boolean allowPublicMethodsOnly() {
      return this.publicMethodsOnly;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof AnnotationCacheOperationSource)) {
         return false;
      } else {
         AnnotationCacheOperationSource otherCos = (AnnotationCacheOperationSource)other;
         return this.annotationParsers.equals(otherCos.annotationParsers) && this.publicMethodsOnly == otherCos.publicMethodsOnly;
      }
   }

   public int hashCode() {
      return this.annotationParsers.hashCode();
   }

   @FunctionalInterface
   protected interface CacheOperationProvider {
      @Nullable
      Collection getCacheOperations(CacheAnnotationParser var1);
   }
}
