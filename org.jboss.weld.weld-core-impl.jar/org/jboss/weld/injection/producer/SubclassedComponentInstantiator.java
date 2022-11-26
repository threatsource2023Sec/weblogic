package org.jboss.weld.injection.producer;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.ConstructorSignature;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.annotated.slim.AnnotatedTypeIdentifier;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeStore;
import org.jboss.weld.bean.proxy.InterceptedSubclassFactory;
import org.jboss.weld.injection.ConstructorInjectionPoint;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.ProxyClassConstructorInjectionPointWrapper;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.collections.WeldCollections;

public class SubclassedComponentInstantiator extends AbstractInstantiator {
   private final ConstructorInjectionPoint proxyClassConstructorInjectionPoint;
   private final Constructor componentClassConstructor;

   public static SubclassedComponentInstantiator forSubclassedEjb(EnhancedAnnotatedType componentType, EnhancedAnnotatedType subclass, Bean bean, BeanManagerImpl manager) {
      EnhancedAnnotatedConstructor componentConstructor = Beans.getBeanConstructor(componentType);
      EnhancedAnnotatedConstructor subclassConstructor = findMatchingConstructor(componentConstructor.getSignature(), subclass);
      ConstructorInjectionPoint cip = InjectionPointFactory.instance().createConstructorInjectionPoint(bean, componentType.getJavaClass(), subclassConstructor, manager);
      return new SubclassedComponentInstantiator(cip, componentConstructor.getJavaMember());
   }

   public static SubclassedComponentInstantiator forInterceptedDecoratedBean(EnhancedAnnotatedType type, Bean bean, AbstractInstantiator delegate, BeanManagerImpl manager) {
      return new SubclassedComponentInstantiator(type, bean, delegate.getConstructorInjectionPoint(), manager);
   }

   private static EnhancedAnnotatedConstructor findMatchingConstructor(ConstructorSignature componentConstructor, EnhancedAnnotatedType subclass) {
      return subclass.getDeclaredEnhancedConstructor(componentConstructor);
   }

   private SubclassedComponentInstantiator(ConstructorInjectionPoint proxyClassConstructorInjectionPoint, Constructor componentClassConstructor) {
      this.proxyClassConstructorInjectionPoint = proxyClassConstructorInjectionPoint;
      this.componentClassConstructor = componentClassConstructor;
   }

   protected SubclassedComponentInstantiator(EnhancedAnnotatedType type, Bean bean, ConstructorInjectionPoint originalConstructor, BeanManagerImpl manager) {
      EnhancedAnnotatedConstructor constructorForEnhancedSubclass = this.initEnhancedSubclass(manager, type, bean, originalConstructor);
      this.proxyClassConstructorInjectionPoint = new ProxyClassConstructorInjectionPointWrapper(bean, type.getJavaClass(), constructorForEnhancedSubclass, originalConstructor, manager);
      this.componentClassConstructor = originalConstructor.getAnnotated().getJavaMember();
   }

   protected EnhancedAnnotatedConstructor initEnhancedSubclass(BeanManagerImpl manager, EnhancedAnnotatedType type, Bean bean, ConstructorInjectionPoint originalConstructorInjectionPoint) {
      ClassTransformer transformer = (ClassTransformer)manager.getServices().get(ClassTransformer.class);
      EnhancedAnnotatedType enhancedSubclass = transformer.getEnhancedAnnotatedType(this.createEnhancedSubclass(type, bean, manager), ((AnnotatedTypeIdentifier)type.slim().getIdentifier()).getBdaId());
      return findMatchingConstructor(originalConstructorInjectionPoint.getSignature(), enhancedSubclass);
   }

   protected Class createEnhancedSubclass(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl manager) {
      Set models = this.getInterceptionModelsForType(type, manager, bean);
      Set enhancedMethodSignatures = new HashSet();
      Set interceptedMethodSignatures = models == null ? enhancedMethodSignatures : new HashSet();
      Iterator types = Beans.getInterceptableMethods(type).iterator();

      while(true) {
         while(true) {
            AnnotatedMethod method;
            do {
               if (!types.hasNext()) {
                  types = null;
                  Set types;
                  if (bean == null) {
                     types = Collections.singleton(type.getJavaClass());
                  } else {
                     types = bean.getTypes();
                  }

                  return (new InterceptedSubclassFactory(manager.getContextId(), type.getJavaClass(), types, bean, enhancedMethodSignatures, interceptedMethodSignatures)).getProxyClass();
               }

               method = (AnnotatedMethod)types.next();
               enhancedMethodSignatures.add(MethodSignatureImpl.of(method));
            } while(models == null);

            Iterator var9 = models.iterator();

            while(var9.hasNext()) {
               InterceptionModel model = (InterceptionModel)var9.next();
               if (!model.getInterceptors(InterceptionType.AROUND_INVOKE, method.getJavaMember()).isEmpty()) {
                  interceptedMethodSignatures.add(MethodSignatureImpl.of(method));
                  break;
               }
            }
         }
      }
   }

   private Set getInterceptionModelsForType(EnhancedAnnotatedType type, BeanManagerImpl manager, Bean bean) {
      if (bean != null && !manager.resolveDecorators(bean.getTypes(), bean.getQualifiers()).isEmpty()) {
         return null;
      } else {
         SlimAnnotatedTypeStore store = (SlimAnnotatedTypeStore)manager.getServices().get(SlimAnnotatedTypeStore.class);
         Set models = new HashSet();
         WeldCollections.addIfNotNull(models, manager.getInterceptorModelRegistry().get(type.slim()));
         Iterator var6 = store.get(type.getJavaClass()).iterator();

         while(var6.hasNext()) {
            SlimAnnotatedType slimType = (SlimAnnotatedType)var6.next();
            WeldCollections.addIfNotNull(models, manager.getInterceptorModelRegistry().get(slimType));
         }

         var6 = models.iterator();

         InterceptionModel model;
         do {
            if (!var6.hasNext()) {
               return models;
            }

            model = (InterceptionModel)var6.next();
         } while(!model.hasTargetClassInterceptors() || !model.getTargetClassInterceptorMetadata().isEligible(InterceptionType.AROUND_INVOKE));

         return null;
      }
   }

   public String toString() {
      return "SubclassedComponentInstantiator for " + this.proxyClassConstructorInjectionPoint.getType();
   }

   public boolean hasInterceptorSupport() {
      return false;
   }

   public boolean hasDecoratorSupport() {
      return false;
   }

   public ConstructorInjectionPoint getConstructorInjectionPoint() {
      return this.proxyClassConstructorInjectionPoint;
   }

   public Constructor getConstructor() {
      return this.componentClassConstructor;
   }
}
