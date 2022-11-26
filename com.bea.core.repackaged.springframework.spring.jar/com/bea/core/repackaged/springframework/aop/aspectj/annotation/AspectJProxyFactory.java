package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJProxyUtils;
import com.bea.core.repackaged.springframework.aop.aspectj.SimpleAspectInstanceFactory;
import com.bea.core.repackaged.springframework.aop.framework.ProxyCreatorSupport;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AspectJProxyFactory extends ProxyCreatorSupport {
   private static final Map aspectCache = new ConcurrentHashMap();
   private final AspectJAdvisorFactory aspectFactory = new ReflectiveAspectJAdvisorFactory();

   public AspectJProxyFactory() {
   }

   public AspectJProxyFactory(Object target) {
      Assert.notNull(target, "Target object must not be null");
      this.setInterfaces(ClassUtils.getAllInterfaces(target));
      this.setTarget(target);
   }

   public AspectJProxyFactory(Class... interfaces) {
      this.setInterfaces(interfaces);
   }

   public void addAspect(Object aspectInstance) {
      Class aspectClass = aspectInstance.getClass();
      String aspectName = aspectClass.getName();
      AspectMetadata am = this.createAspectMetadata(aspectClass, aspectName);
      if (am.getAjType().getPerClause().getKind() != PerClauseKind.SINGLETON) {
         throw new IllegalArgumentException("Aspect class [" + aspectClass.getName() + "] does not define a singleton aspect");
      } else {
         this.addAdvisorsFromAspectInstanceFactory(new SingletonMetadataAwareAspectInstanceFactory(aspectInstance, aspectName));
      }
   }

   public void addAspect(Class aspectClass) {
      String aspectName = aspectClass.getName();
      AspectMetadata am = this.createAspectMetadata(aspectClass, aspectName);
      MetadataAwareAspectInstanceFactory instanceFactory = this.createAspectInstanceFactory(am, aspectClass, aspectName);
      this.addAdvisorsFromAspectInstanceFactory(instanceFactory);
   }

   private void addAdvisorsFromAspectInstanceFactory(MetadataAwareAspectInstanceFactory instanceFactory) {
      List advisors = this.aspectFactory.getAdvisors(instanceFactory);
      Class targetClass = this.getTargetClass();
      Assert.state(targetClass != null, "Unresolvable target class");
      advisors = AopUtils.findAdvisorsThatCanApply(advisors, targetClass);
      AspectJProxyUtils.makeAdvisorChainAspectJCapableIfNecessary(advisors);
      AnnotationAwareOrderComparator.sort(advisors);
      this.addAdvisors(advisors);
   }

   private AspectMetadata createAspectMetadata(Class aspectClass, String aspectName) {
      AspectMetadata am = new AspectMetadata(aspectClass, aspectName);
      if (!am.getAjType().isAspect()) {
         throw new IllegalArgumentException("Class [" + aspectClass.getName() + "] is not a valid aspect type");
      } else {
         return am;
      }
   }

   private MetadataAwareAspectInstanceFactory createAspectInstanceFactory(AspectMetadata am, Class aspectClass, String aspectName) {
      Object instanceFactory;
      if (am.getAjType().getPerClause().getKind() == PerClauseKind.SINGLETON) {
         Object instance = this.getSingletonAspectInstance(aspectClass);
         instanceFactory = new SingletonMetadataAwareAspectInstanceFactory(instance, aspectName);
      } else {
         instanceFactory = new SimpleMetadataAwareAspectInstanceFactory(aspectClass, aspectName);
      }

      return (MetadataAwareAspectInstanceFactory)instanceFactory;
   }

   private Object getSingletonAspectInstance(Class aspectClass) {
      Object instance = aspectCache.get(aspectClass);
      if (instance == null) {
         synchronized(aspectCache) {
            instance = aspectCache.get(aspectClass);
            if (instance == null) {
               instance = (new SimpleAspectInstanceFactory(aspectClass)).getAspectInstance();
               aspectCache.put(aspectClass, instance);
            }
         }
      }

      return instance;
   }

   public Object getProxy() {
      return this.createAopProxy().getProxy();
   }

   public Object getProxy(ClassLoader classLoader) {
      return this.createAopProxy().getProxy(classLoader);
   }
}
