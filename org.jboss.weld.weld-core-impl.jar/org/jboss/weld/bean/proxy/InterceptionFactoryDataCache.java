package org.jboss.weld.bean.proxy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.unbacked.UnbackedAnnotatedType;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.injection.producer.InterceptionModelInitializer;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.AnnotatedTypes;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;

public class InterceptionFactoryDataCache implements Service {
   private static final AtomicLong INDEX = new AtomicLong();
   private final ComputingCache cache;

   public InterceptionFactoryDataCache(BeanManagerImpl beanManager) {
      this.cache = ComputingCacheBuilder.newBuilder().build((key) -> {
         ClassTransformer classTransformer = (ClassTransformer)beanManager.getServices().get(ClassTransformer.class);
         long idx = INDEX.incrementAndGet();
         String id = key.annotatedType.getJavaClass().getName() + "$$" + idx;
         UnbackedAnnotatedType slimAnnotatedType = classTransformer.getUnbackedAnnotatedType(key.annotatedType, beanManager.getId(), id);
         EnhancedAnnotatedType enhancedAnnotatedType = classTransformer.getEnhancedAnnotatedType(slimAnnotatedType);
         (new InterceptionModelInitializer(beanManager, enhancedAnnotatedType, Beans.getBeanConstructor(enhancedAnnotatedType), (Bean)null)).init();
         InterceptionModel interceptionModel = (InterceptionModel)beanManager.getInterceptorModelRegistry().get(slimAnnotatedType);
         boolean hasNonConstructorInterceptors = interceptionModel != null && (interceptionModel.hasExternalNonConstructorInterceptors() || interceptionModel.hasTargetClassInterceptors());
         if (!hasNonConstructorInterceptors) {
            return Optional.empty();
         } else {
            Set enhancedMethodSignatures = new HashSet();
            Set interceptedMethodSignatures = new HashSet();
            Iterator var12 = Beans.getInterceptableMethods(enhancedAnnotatedType).iterator();

            while(var12.hasNext()) {
               AnnotatedMethod method = (AnnotatedMethod)var12.next();
               enhancedMethodSignatures.add(MethodSignatureImpl.of(method));
               if (!interceptionModel.getInterceptors(InterceptionType.AROUND_INVOKE, method.getJavaMember()).isEmpty()) {
                  interceptedMethodSignatures.add(MethodSignatureImpl.of(method));
               }
            }

            InterceptedProxyFactory proxyFactory = new InterceptedProxyFactory(beanManager.getContextId(), enhancedAnnotatedType.getJavaClass(), Collections.singleton(enhancedAnnotatedType.getJavaClass()), enhancedMethodSignatures, interceptedMethodSignatures, "" + idx);
            InterceptionFactoryData data = new InterceptionFactoryData(proxyFactory, slimAnnotatedType, interceptionModel);
            return Optional.of(data);
         }
      });
   }

   public Optional getInterceptionFactoryData(AnnotatedType annotatedType) {
      Key key = new Key(AnnotatedTypes.createTypeId(annotatedType), annotatedType);

      Optional var3;
      try {
         var3 = (Optional)this.cache.getCastValue(key);
      } finally {
         key.cleanupAfterUse();
      }

      return var3;
   }

   public void cleanup() {
      this.cache.clear();
   }

   private static class Key {
      private final String typeId;
      private AnnotatedType annotatedType;

      Key(String typeId, AnnotatedType annotatedType) {
         this.typeId = typeId;
         this.annotatedType = annotatedType;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.typeId == null ? 0 : this.typeId.hashCode());
         return result;
      }

      void cleanupAfterUse() {
         this.annotatedType = null;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            Key other = (Key)obj;
            if (this.typeId == null) {
               if (other.typeId != null) {
                  return false;
               }
            } else if (!this.typeId.equals(other.typeId)) {
               return false;
            }

            return true;
         }
      }
   }

   public static class InterceptionFactoryData {
      private final InterceptedProxyFactory interceptedProxyFactory;
      private final SlimAnnotatedType slimAnnotatedType;
      private final InterceptionModel interceptionModel;

      InterceptionFactoryData(InterceptedProxyFactory interceptedProxyFactory, SlimAnnotatedType slimAnnotatedType, InterceptionModel interceptionModel) {
         this.interceptedProxyFactory = interceptedProxyFactory;
         this.slimAnnotatedType = slimAnnotatedType;
         this.interceptionModel = interceptionModel;
      }

      public InterceptedProxyFactory getInterceptedProxyFactory() {
         return this.interceptedProxyFactory;
      }

      public SlimAnnotatedType getSlimAnnotatedType() {
         return this.slimAnnotatedType;
      }

      public InterceptionModel getInterceptionModel() {
         return this.interceptionModel;
      }
   }
}
