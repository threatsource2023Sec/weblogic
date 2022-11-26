package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.framework.AopProxyUtils;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.SmartInitializingSingleton;
import com.bea.core.repackaged.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import com.bea.core.repackaged.springframework.cache.Cache;
import com.bea.core.repackaged.springframework.cache.CacheManager;
import com.bea.core.repackaged.springframework.context.expression.AnnotatedElementKey;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.function.SingletonSupplier;
import com.bea.core.repackaged.springframework.util.function.SupplierUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public abstract class CacheAspectSupport extends AbstractCacheInvoker implements BeanFactoryAware, InitializingBean, SmartInitializingSingleton {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Map metadataCache = new ConcurrentHashMap(1024);
   private final CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();
   @Nullable
   private CacheOperationSource cacheOperationSource;
   private SingletonSupplier keyGenerator = SingletonSupplier.of(SimpleKeyGenerator::new);
   @Nullable
   private SingletonSupplier cacheResolver;
   @Nullable
   private BeanFactory beanFactory;
   private boolean initialized = false;

   public void configure(@Nullable Supplier errorHandler, @Nullable Supplier keyGenerator, @Nullable Supplier cacheResolver, @Nullable Supplier cacheManager) {
      this.errorHandler = new SingletonSupplier(errorHandler, SimpleCacheErrorHandler::new);
      this.keyGenerator = new SingletonSupplier(keyGenerator, SimpleKeyGenerator::new);
      this.cacheResolver = new SingletonSupplier(cacheResolver, () -> {
         return SimpleCacheResolver.of((CacheManager)SupplierUtils.resolve(cacheManager));
      });
   }

   public void setCacheOperationSources(CacheOperationSource... cacheOperationSources) {
      Assert.notEmpty((Object[])cacheOperationSources, (String)"At least 1 CacheOperationSource needs to be specified");
      this.cacheOperationSource = (CacheOperationSource)(cacheOperationSources.length > 1 ? new CompositeCacheOperationSource(cacheOperationSources) : cacheOperationSources[0]);
   }

   public void setCacheOperationSource(@Nullable CacheOperationSource cacheOperationSource) {
      this.cacheOperationSource = cacheOperationSource;
   }

   @Nullable
   public CacheOperationSource getCacheOperationSource() {
      return this.cacheOperationSource;
   }

   public void setKeyGenerator(KeyGenerator keyGenerator) {
      this.keyGenerator = SingletonSupplier.of((Object)keyGenerator);
   }

   public KeyGenerator getKeyGenerator() {
      return (KeyGenerator)this.keyGenerator.obtain();
   }

   public void setCacheResolver(@Nullable CacheResolver cacheResolver) {
      this.cacheResolver = SingletonSupplier.ofNullable((Object)cacheResolver);
   }

   @Nullable
   public CacheResolver getCacheResolver() {
      return (CacheResolver)SupplierUtils.resolve(this.cacheResolver);
   }

   public void setCacheManager(CacheManager cacheManager) {
      this.cacheResolver = SingletonSupplier.of((Object)(new SimpleCacheResolver(cacheManager)));
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   public void afterPropertiesSet() {
      Assert.state(this.getCacheOperationSource() != null, "The 'cacheOperationSources' property is required: If there are no cacheable methods, then don't use a cache aspect.");
   }

   public void afterSingletonsInstantiated() {
      if (this.getCacheResolver() == null) {
         Assert.state(this.beanFactory != null, "CacheResolver or BeanFactory must be set on cache aspect");

         try {
            this.setCacheManager((CacheManager)this.beanFactory.getBean(CacheManager.class));
         } catch (NoUniqueBeanDefinitionException var2) {
            throw new IllegalStateException("No CacheResolver specified, and no unique bean of type CacheManager found. Mark one as primary or declare a specific CacheManager to use.");
         } catch (NoSuchBeanDefinitionException var3) {
            throw new IllegalStateException("No CacheResolver specified, and no bean of type CacheManager found. Register a CacheManager bean or remove the @EnableCaching annotation from your configuration.");
         }
      }

      this.initialized = true;
   }

   protected String methodIdentification(Method method, Class targetClass) {
      Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
      return ClassUtils.getQualifiedMethodName(specificMethod);
   }

   protected Collection getCaches(CacheOperationInvocationContext context, CacheResolver cacheResolver) {
      Collection caches = cacheResolver.resolveCaches(context);
      if (caches.isEmpty()) {
         throw new IllegalStateException("No cache could be resolved for '" + context.getOperation() + "' using resolver '" + cacheResolver + "'. At least one cache should be provided per cache operation.");
      } else {
         return caches;
      }
   }

   protected CacheOperationContext getOperationContext(CacheOperation operation, Method method, Object[] args, Object target, Class targetClass) {
      CacheOperationMetadata metadata = this.getCacheOperationMetadata(operation, method, targetClass);
      return new CacheOperationContext(metadata, args, target);
   }

   protected CacheOperationMetadata getCacheOperationMetadata(CacheOperation operation, Method method, Class targetClass) {
      CacheOperationCacheKey cacheKey = new CacheOperationCacheKey(operation, method, targetClass);
      CacheOperationMetadata metadata = (CacheOperationMetadata)this.metadataCache.get(cacheKey);
      if (metadata == null) {
         KeyGenerator operationKeyGenerator;
         if (StringUtils.hasText(operation.getKeyGenerator())) {
            operationKeyGenerator = (KeyGenerator)this.getBean(operation.getKeyGenerator(), KeyGenerator.class);
         } else {
            operationKeyGenerator = this.getKeyGenerator();
         }

         Object operationCacheResolver;
         if (StringUtils.hasText(operation.getCacheResolver())) {
            operationCacheResolver = (CacheResolver)this.getBean(operation.getCacheResolver(), CacheResolver.class);
         } else if (StringUtils.hasText(operation.getCacheManager())) {
            CacheManager cacheManager = (CacheManager)this.getBean(operation.getCacheManager(), CacheManager.class);
            operationCacheResolver = new SimpleCacheResolver(cacheManager);
         } else {
            operationCacheResolver = this.getCacheResolver();
            Assert.state(operationCacheResolver != null, "No CacheResolver/CacheManager set");
         }

         metadata = new CacheOperationMetadata(operation, method, targetClass, operationKeyGenerator, (CacheResolver)operationCacheResolver);
         this.metadataCache.put(cacheKey, metadata);
      }

      return metadata;
   }

   protected Object getBean(String beanName, Class expectedType) {
      if (this.beanFactory == null) {
         throw new IllegalStateException("BeanFactory must be set on cache aspect for " + expectedType.getSimpleName() + " retrieval");
      } else {
         return BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.beanFactory, expectedType, beanName);
      }
   }

   protected void clearMetadataCache() {
      this.metadataCache.clear();
      this.evaluator.clear();
   }

   @Nullable
   protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
      if (this.initialized) {
         Class targetClass = this.getTargetClass(target);
         CacheOperationSource cacheOperationSource = this.getCacheOperationSource();
         if (cacheOperationSource != null) {
            Collection operations = cacheOperationSource.getCacheOperations(method, targetClass);
            if (!CollectionUtils.isEmpty(operations)) {
               return this.execute(invoker, method, new CacheOperationContexts(operations, method, args, target, targetClass));
            }
         }
      }

      return invoker.invoke();
   }

   protected Object invokeOperation(CacheOperationInvoker invoker) {
      return invoker.invoke();
   }

   private Class getTargetClass(Object target) {
      return AopProxyUtils.ultimateTargetClass(target);
   }

   @Nullable
   private Object execute(CacheOperationInvoker invoker, Method method, CacheOperationContexts contexts) {
      if (contexts.isSynchronized()) {
         CacheOperationContext context = (CacheOperationContext)contexts.get(CacheableOperation.class).iterator().next();
         if (this.isConditionPassing(context, CacheOperationExpressionEvaluator.NO_RESULT)) {
            Object key = this.generateKey(context, CacheOperationExpressionEvaluator.NO_RESULT);
            Cache cache = (Cache)context.getCaches().iterator().next();

            try {
               return this.wrapCacheValue(method, cache.get(key, () -> {
                  return this.unwrapReturnValue(this.invokeOperation(invoker));
               }));
            } catch (Cache.ValueRetrievalException var10) {
               throw (CacheOperationInvoker.ThrowableWrapper)var10.getCause();
            }
         } else {
            return this.invokeOperation(invoker);
         }
      } else {
         this.processCacheEvicts(contexts.get(CacheEvictOperation.class), true, CacheOperationExpressionEvaluator.NO_RESULT);
         Cache.ValueWrapper cacheHit = this.findCachedItem(contexts.get(CacheableOperation.class));
         List cachePutRequests = new LinkedList();
         if (cacheHit == null) {
            this.collectPutRequests(contexts.get(CacheableOperation.class), CacheOperationExpressionEvaluator.NO_RESULT, cachePutRequests);
         }

         Object cacheValue;
         Object returnValue;
         if (cacheHit != null && !this.hasCachePut(contexts)) {
            cacheValue = cacheHit.get();
            returnValue = this.wrapCacheValue(method, cacheValue);
         } else {
            returnValue = this.invokeOperation(invoker);
            cacheValue = this.unwrapReturnValue(returnValue);
         }

         this.collectPutRequests(contexts.get(CachePutOperation.class), cacheValue, cachePutRequests);
         Iterator var8 = cachePutRequests.iterator();

         while(var8.hasNext()) {
            CachePutRequest cachePutRequest = (CachePutRequest)var8.next();
            cachePutRequest.apply(cacheValue);
         }

         this.processCacheEvicts(contexts.get(CacheEvictOperation.class), false, cacheValue);
         return returnValue;
      }
   }

   @Nullable
   private Object wrapCacheValue(Method method, @Nullable Object cacheValue) {
      return method.getReturnType() != Optional.class || cacheValue != null && cacheValue.getClass() == Optional.class ? cacheValue : Optional.ofNullable(cacheValue);
   }

   @Nullable
   private Object unwrapReturnValue(Object returnValue) {
      return ObjectUtils.unwrapOptional(returnValue);
   }

   private boolean hasCachePut(CacheOperationContexts contexts) {
      Collection cachePutContexts = contexts.get(CachePutOperation.class);
      Collection excluded = new ArrayList();
      Iterator var4 = cachePutContexts.iterator();

      while(var4.hasNext()) {
         CacheOperationContext context = (CacheOperationContext)var4.next();

         try {
            if (!context.isConditionPassing(CacheOperationExpressionEvaluator.RESULT_UNAVAILABLE)) {
               excluded.add(context);
            }
         } catch (VariableNotAvailableException var7) {
         }
      }

      return cachePutContexts.size() != excluded.size();
   }

   private void processCacheEvicts(Collection contexts, boolean beforeInvocation, @Nullable Object result) {
      Iterator var4 = contexts.iterator();

      while(var4.hasNext()) {
         CacheOperationContext context = (CacheOperationContext)var4.next();
         CacheEvictOperation operation = (CacheEvictOperation)context.metadata.operation;
         if (beforeInvocation == operation.isBeforeInvocation() && this.isConditionPassing(context, result)) {
            this.performCacheEvict(context, operation, result);
         }
      }

   }

   private void performCacheEvict(CacheOperationContext context, CacheEvictOperation operation, @Nullable Object result) {
      Object key = null;
      Iterator var5 = context.getCaches().iterator();

      while(var5.hasNext()) {
         Cache cache = (Cache)var5.next();
         if (operation.isCacheWide()) {
            this.logInvalidating(context, operation, (Object)null);
            this.doClear(cache);
         } else {
            if (key == null) {
               key = this.generateKey(context, result);
            }

            this.logInvalidating(context, operation, key);
            this.doEvict(cache, key);
         }
      }

   }

   private void logInvalidating(CacheOperationContext context, CacheEvictOperation operation, @Nullable Object key) {
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Invalidating " + (key != null ? "cache key [" + key + "]" : "entire cache") + " for operation " + operation + " on method " + context.metadata.method);
      }

   }

   @Nullable
   private Cache.ValueWrapper findCachedItem(Collection contexts) {
      Object result = CacheOperationExpressionEvaluator.NO_RESULT;
      Iterator var3 = contexts.iterator();

      while(var3.hasNext()) {
         CacheOperationContext context = (CacheOperationContext)var3.next();
         if (this.isConditionPassing(context, result)) {
            Object key = this.generateKey(context, result);
            Cache.ValueWrapper cached = this.findInCaches(context, key);
            if (cached != null) {
               return cached;
            }

            if (this.logger.isTraceEnabled()) {
               this.logger.trace("No cache entry for key '" + key + "' in cache(s) " + context.getCacheNames());
            }
         }
      }

      return null;
   }

   private void collectPutRequests(Collection contexts, @Nullable Object result, Collection putRequests) {
      Iterator var4 = contexts.iterator();

      while(var4.hasNext()) {
         CacheOperationContext context = (CacheOperationContext)var4.next();
         if (this.isConditionPassing(context, result)) {
            Object key = this.generateKey(context, result);
            putRequests.add(new CachePutRequest(context, key));
         }
      }

   }

   @Nullable
   private Cache.ValueWrapper findInCaches(CacheOperationContext context, Object key) {
      Iterator var3 = context.getCaches().iterator();

      Cache cache;
      Cache.ValueWrapper wrapper;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         cache = (Cache)var3.next();
         wrapper = this.doGet(cache, key);
      } while(wrapper == null);

      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Cache entry for key '" + key + "' found in cache '" + cache.getName() + "'");
      }

      return wrapper;
   }

   private boolean isConditionPassing(CacheOperationContext context, @Nullable Object result) {
      boolean passing = context.isConditionPassing(result);
      if (!passing && this.logger.isTraceEnabled()) {
         this.logger.trace("Cache condition failed on method " + context.metadata.method + " for operation " + context.metadata.operation);
      }

      return passing;
   }

   private Object generateKey(CacheOperationContext context, @Nullable Object result) {
      Object key = context.generateKey(result);
      if (key == null) {
         throw new IllegalArgumentException("Null key returned for cache operation (maybe you are using named params on classes without debug info?) " + context.metadata.operation);
      } else {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Computed cache key '" + key + "' for operation " + context.metadata.operation);
         }

         return key;
      }
   }

   private static final class CacheOperationCacheKey implements Comparable {
      private final CacheOperation cacheOperation;
      private final AnnotatedElementKey methodCacheKey;

      private CacheOperationCacheKey(CacheOperation cacheOperation, Method method, Class targetClass) {
         this.cacheOperation = cacheOperation;
         this.methodCacheKey = new AnnotatedElementKey(method, targetClass);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof CacheOperationCacheKey)) {
            return false;
         } else {
            CacheOperationCacheKey otherKey = (CacheOperationCacheKey)other;
            return this.cacheOperation.equals(otherKey.cacheOperation) && this.methodCacheKey.equals(otherKey.methodCacheKey);
         }
      }

      public int hashCode() {
         return this.cacheOperation.hashCode() * 31 + this.methodCacheKey.hashCode();
      }

      public String toString() {
         return this.cacheOperation + " on " + this.methodCacheKey;
      }

      public int compareTo(CacheOperationCacheKey other) {
         int result = this.cacheOperation.getName().compareTo(other.cacheOperation.getName());
         if (result == 0) {
            result = this.methodCacheKey.compareTo(other.methodCacheKey);
         }

         return result;
      }

      // $FF: synthetic method
      CacheOperationCacheKey(CacheOperation x0, Method x1, Class x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private class CachePutRequest {
      private final CacheOperationContext context;
      private final Object key;

      public CachePutRequest(CacheOperationContext context, Object key) {
         this.context = context;
         this.key = key;
      }

      public void apply(@Nullable Object result) {
         if (this.context.canPutToCache(result)) {
            Iterator var2 = this.context.getCaches().iterator();

            while(var2.hasNext()) {
               Cache cache = (Cache)var2.next();
               CacheAspectSupport.this.doPut(cache, this.key, result);
            }
         }

      }
   }

   protected class CacheOperationContext implements CacheOperationInvocationContext {
      private final CacheOperationMetadata metadata;
      private final Object[] args;
      private final Object target;
      private final Collection caches;
      private final Collection cacheNames;
      @Nullable
      private Boolean conditionPassing;

      public CacheOperationContext(CacheOperationMetadata metadata, Object[] args, Object target) {
         this.metadata = metadata;
         this.args = this.extractArgs(metadata.method, args);
         this.target = target;
         this.caches = CacheAspectSupport.this.getCaches(this, metadata.cacheResolver);
         this.cacheNames = this.createCacheNames(this.caches);
      }

      public CacheOperation getOperation() {
         return this.metadata.operation;
      }

      public Object getTarget() {
         return this.target;
      }

      public Method getMethod() {
         return this.metadata.method;
      }

      public Object[] getArgs() {
         return this.args;
      }

      private Object[] extractArgs(Method method, Object[] args) {
         if (!method.isVarArgs()) {
            return args;
         } else {
            Object[] varArgs = ObjectUtils.toObjectArray(args[args.length - 1]);
            Object[] combinedArgs = new Object[args.length - 1 + varArgs.length];
            System.arraycopy(args, 0, combinedArgs, 0, args.length - 1);
            System.arraycopy(varArgs, 0, combinedArgs, args.length - 1, varArgs.length);
            return combinedArgs;
         }
      }

      protected boolean isConditionPassing(@Nullable Object result) {
         if (this.conditionPassing == null) {
            if (StringUtils.hasText(this.metadata.operation.getCondition())) {
               EvaluationContext evaluationContext = this.createEvaluationContext(result);
               this.conditionPassing = CacheAspectSupport.this.evaluator.condition(this.metadata.operation.getCondition(), this.metadata.methodKey, evaluationContext);
            } else {
               this.conditionPassing = true;
            }
         }

         return this.conditionPassing;
      }

      protected boolean canPutToCache(@Nullable Object value) {
         String unless = "";
         if (this.metadata.operation instanceof CacheableOperation) {
            unless = ((CacheableOperation)this.metadata.operation).getUnless();
         } else if (this.metadata.operation instanceof CachePutOperation) {
            unless = ((CachePutOperation)this.metadata.operation).getUnless();
         }

         if (StringUtils.hasText(unless)) {
            EvaluationContext evaluationContext = this.createEvaluationContext(value);
            return !CacheAspectSupport.this.evaluator.unless(unless, this.metadata.methodKey, evaluationContext);
         } else {
            return true;
         }
      }

      @Nullable
      protected Object generateKey(@Nullable Object result) {
         if (StringUtils.hasText(this.metadata.operation.getKey())) {
            EvaluationContext evaluationContext = this.createEvaluationContext(result);
            return CacheAspectSupport.this.evaluator.key(this.metadata.operation.getKey(), this.metadata.methodKey, evaluationContext);
         } else {
            return this.metadata.keyGenerator.generate(this.target, this.metadata.method, this.args);
         }
      }

      private EvaluationContext createEvaluationContext(@Nullable Object result) {
         return CacheAspectSupport.this.evaluator.createEvaluationContext(this.caches, this.metadata.method, this.args, this.target, this.metadata.targetClass, this.metadata.targetMethod, result, CacheAspectSupport.this.beanFactory);
      }

      protected Collection getCaches() {
         return this.caches;
      }

      protected Collection getCacheNames() {
         return this.cacheNames;
      }

      private Collection createCacheNames(Collection caches) {
         Collection names = new ArrayList();
         Iterator var3 = caches.iterator();

         while(var3.hasNext()) {
            Cache cache = (Cache)var3.next();
            names.add(cache.getName());
         }

         return names;
      }
   }

   protected static class CacheOperationMetadata {
      private final CacheOperation operation;
      private final Method method;
      private final Class targetClass;
      private final Method targetMethod;
      private final AnnotatedElementKey methodKey;
      private final KeyGenerator keyGenerator;
      private final CacheResolver cacheResolver;

      public CacheOperationMetadata(CacheOperation operation, Method method, Class targetClass, KeyGenerator keyGenerator, CacheResolver cacheResolver) {
         this.operation = operation;
         this.method = BridgeMethodResolver.findBridgedMethod(method);
         this.targetClass = targetClass;
         this.targetMethod = !Proxy.isProxyClass(targetClass) ? AopUtils.getMostSpecificMethod(method, targetClass) : this.method;
         this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
         this.keyGenerator = keyGenerator;
         this.cacheResolver = cacheResolver;
      }
   }

   private class CacheOperationContexts {
      private final MultiValueMap contexts;
      private final boolean sync;

      public CacheOperationContexts(Collection operations, Method method, Object[] args, Object target, Class targetClass) {
         this.contexts = new LinkedMultiValueMap(operations.size());
         Iterator var7 = operations.iterator();

         while(var7.hasNext()) {
            CacheOperation op = (CacheOperation)var7.next();
            this.contexts.add(op.getClass(), CacheAspectSupport.this.getOperationContext(op, method, args, target, targetClass));
         }

         this.sync = this.determineSyncFlag(method);
      }

      public Collection get(Class operationClass) {
         Collection result = (Collection)this.contexts.get(operationClass);
         return (Collection)(result != null ? result : Collections.emptyList());
      }

      public boolean isSynchronized() {
         return this.sync;
      }

      private boolean determineSyncFlag(Method method) {
         List cacheOperationContexts = (List)this.contexts.get(CacheableOperation.class);
         if (cacheOperationContexts == null) {
            return false;
         } else {
            boolean syncEnabled = false;
            Iterator var4 = cacheOperationContexts.iterator();

            while(var4.hasNext()) {
               CacheOperationContext cacheOperationContextx = (CacheOperationContext)var4.next();
               if (((CacheableOperation)cacheOperationContextx.getOperation()).isSync()) {
                  syncEnabled = true;
                  break;
               }
            }

            if (syncEnabled) {
               if (this.contexts.size() > 1) {
                  throw new IllegalStateException("@Cacheable(sync=true) cannot be combined with other cache operations on '" + method + "'");
               } else if (cacheOperationContexts.size() > 1) {
                  throw new IllegalStateException("Only one @Cacheable(sync=true) entry is allowed on '" + method + "'");
               } else {
                  CacheOperationContext cacheOperationContext = (CacheOperationContext)cacheOperationContexts.iterator().next();
                  CacheableOperation operation = (CacheableOperation)cacheOperationContext.getOperation();
                  if (cacheOperationContext.getCaches().size() > 1) {
                     throw new IllegalStateException("@Cacheable(sync=true) only allows a single cache on '" + operation + "'");
                  } else if (StringUtils.hasText(operation.getUnless())) {
                     throw new IllegalStateException("@Cacheable(sync=true) does not support unless attribute on '" + operation + "'");
                  } else {
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }
   }
}
