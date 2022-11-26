package org.jboss.weld.interceptor.reader;

import java.util.function.Function;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.InterceptorImpl;
import org.jboss.weld.bean.interceptor.CustomInterceptorMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;

public class InterceptorMetadataReader {
   private final BeanManagerImpl manager;
   private final ComputingCache plainInterceptorMetadataCache;
   private final ComputingCache cdiInterceptorMetadataCache;
   private final Function interceptorToInterceptorMetadataFunction;

   public InterceptorMetadataReader(final BeanManagerImpl manager) {
      this.manager = manager;
      ComputingCacheBuilder cacheBuilder = ComputingCacheBuilder.newBuilder();
      this.plainInterceptorMetadataCache = cacheBuilder.build(new Function() {
         public InterceptorClassMetadata apply(Class key) {
            EnhancedAnnotatedType type = ((ClassTransformer)manager.getServices().get(ClassTransformer.class)).getEnhancedAnnotatedType(key, manager.getId());
            InterceptorFactory factory = PlainInterceptorFactory.of(key, manager);
            return new InterceptorMetadataImpl(key, factory, InterceptorMetadataUtils.buildMethodMap(type, false, manager));
         }
      });
      this.cdiInterceptorMetadataCache = cacheBuilder.build(new Function() {
         public InterceptorClassMetadata apply(Interceptor key) {
            return CustomInterceptorMetadata.of(key);
         }
      });
      this.interceptorToInterceptorMetadataFunction = this::getCdiInterceptorMetadata;
   }

   public InterceptorClassMetadata getPlainInterceptorMetadata(Class clazz) {
      return (InterceptorClassMetadata)this.plainInterceptorMetadataCache.getCastValue(clazz);
   }

   public TargetClassInterceptorMetadata getTargetClassInterceptorMetadata(EnhancedAnnotatedType type) {
      return TargetClassInterceptorMetadata.of(InterceptorMetadataUtils.buildMethodMap(type, true, this.manager));
   }

   public InterceptorClassMetadata getCdiInterceptorMetadata(Interceptor interceptor) {
      if (interceptor instanceof InterceptorImpl) {
         InterceptorImpl interceptorImpl = (InterceptorImpl)interceptor;
         return interceptorImpl.getInterceptorMetadata();
      } else {
         return (InterceptorClassMetadata)this.cdiInterceptorMetadataCache.getCastValue(interceptor);
      }
   }

   public Function getInterceptorToInterceptorMetadataFunction() {
      return this.interceptorToInterceptorMetadataFunction;
   }

   public void cleanAfterBoot() {
      this.plainInterceptorMetadataCache.clear();
      this.cdiInterceptorMetadataCache.clear();
   }
}
