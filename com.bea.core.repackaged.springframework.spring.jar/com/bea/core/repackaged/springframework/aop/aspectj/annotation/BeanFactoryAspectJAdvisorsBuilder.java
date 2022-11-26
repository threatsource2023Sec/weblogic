package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.beans.factory.ListableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactoryAspectJAdvisorsBuilder {
   private final ListableBeanFactory beanFactory;
   private final AspectJAdvisorFactory advisorFactory;
   @Nullable
   private volatile List aspectBeanNames;
   private final Map advisorsCache;
   private final Map aspectFactoryCache;

   public BeanFactoryAspectJAdvisorsBuilder(ListableBeanFactory beanFactory) {
      this(beanFactory, new ReflectiveAspectJAdvisorFactory(beanFactory));
   }

   public BeanFactoryAspectJAdvisorsBuilder(ListableBeanFactory beanFactory, AspectJAdvisorFactory advisorFactory) {
      this.advisorsCache = new ConcurrentHashMap();
      this.aspectFactoryCache = new ConcurrentHashMap();
      Assert.notNull(beanFactory, (String)"ListableBeanFactory must not be null");
      Assert.notNull(advisorFactory, (String)"AspectJAdvisorFactory must not be null");
      this.beanFactory = beanFactory;
      this.advisorFactory = advisorFactory;
   }

   public List buildAspectJAdvisors() {
      List aspectNames = this.aspectBeanNames;
      if (aspectNames == null) {
         synchronized(this) {
            aspectNames = this.aspectBeanNames;
            if (aspectNames == null) {
               List advisors = new ArrayList();
               List aspectNames = new ArrayList();
               String[] beanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(this.beanFactory, Object.class, true, false);
               String[] var18 = beanNames;
               int var19 = beanNames.length;

               for(int var7 = 0; var7 < var19; ++var7) {
                  String beanName = var18[var7];
                  if (this.isEligibleBean(beanName)) {
                     Class beanType = this.beanFactory.getType(beanName);
                     if (beanType != null && this.advisorFactory.isAspect(beanType)) {
                        aspectNames.add(beanName);
                        AspectMetadata amd = new AspectMetadata(beanType, beanName);
                        if (amd.getAjType().getPerClause().getKind() == PerClauseKind.SINGLETON) {
                           MetadataAwareAspectInstanceFactory factory = new BeanFactoryAspectInstanceFactory(this.beanFactory, beanName);
                           List classAdvisors = this.advisorFactory.getAdvisors(factory);
                           if (this.beanFactory.isSingleton(beanName)) {
                              this.advisorsCache.put(beanName, classAdvisors);
                           } else {
                              this.aspectFactoryCache.put(beanName, factory);
                           }

                           advisors.addAll(classAdvisors);
                        } else {
                           if (this.beanFactory.isSingleton(beanName)) {
                              throw new IllegalArgumentException("Bean with name '" + beanName + "' is a singleton, but aspect instantiation model is not singleton");
                           }

                           MetadataAwareAspectInstanceFactory factory = new PrototypeAspectInstanceFactory(this.beanFactory, beanName);
                           this.aspectFactoryCache.put(beanName, factory);
                           advisors.addAll(this.advisorFactory.getAdvisors(factory));
                        }
                     }
                  }
               }

               this.aspectBeanNames = aspectNames;
               return advisors;
            }
         }
      }

      if (aspectNames.isEmpty()) {
         return Collections.emptyList();
      } else {
         List advisors = new ArrayList();
         Iterator var3 = aspectNames.iterator();

         while(var3.hasNext()) {
            String aspectName = (String)var3.next();
            List cachedAdvisors = (List)this.advisorsCache.get(aspectName);
            if (cachedAdvisors != null) {
               advisors.addAll(cachedAdvisors);
            } else {
               MetadataAwareAspectInstanceFactory factory = (MetadataAwareAspectInstanceFactory)this.aspectFactoryCache.get(aspectName);
               advisors.addAll(this.advisorFactory.getAdvisors(factory));
            }
         }

         return advisors;
      }
   }

   protected boolean isEligibleBean(String beanName) {
      return true;
   }
}
