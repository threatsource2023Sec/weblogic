package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.aop.framework.AopProxyUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanClassLoaderAware, BeanFactoryAware {
   private final ListenerRetriever defaultRetriever = new ListenerRetriever(false);
   final Map retrieverCache = new ConcurrentHashMap(64);
   @Nullable
   private ClassLoader beanClassLoader;
   @Nullable
   private BeanFactory beanFactory;
   private Object retrievalMutex;

   public AbstractApplicationEventMulticaster() {
      this.retrievalMutex = this.defaultRetriever;
   }

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      if (beanFactory instanceof ConfigurableBeanFactory) {
         ConfigurableBeanFactory cbf = (ConfigurableBeanFactory)beanFactory;
         if (this.beanClassLoader == null) {
            this.beanClassLoader = cbf.getBeanClassLoader();
         }

         this.retrievalMutex = cbf.getSingletonMutex();
      }

   }

   private BeanFactory getBeanFactory() {
      if (this.beanFactory == null) {
         throw new IllegalStateException("ApplicationEventMulticaster cannot retrieve listener beans because it is not associated with a BeanFactory");
      } else {
         return this.beanFactory;
      }
   }

   public void addApplicationListener(ApplicationListener listener) {
      synchronized(this.retrievalMutex) {
         Object singletonTarget = AopProxyUtils.getSingletonTarget(listener);
         if (singletonTarget instanceof ApplicationListener) {
            this.defaultRetriever.applicationListeners.remove(singletonTarget);
         }

         this.defaultRetriever.applicationListeners.add(listener);
         this.retrieverCache.clear();
      }
   }

   public void addApplicationListenerBean(String listenerBeanName) {
      synchronized(this.retrievalMutex) {
         this.defaultRetriever.applicationListenerBeans.add(listenerBeanName);
         this.retrieverCache.clear();
      }
   }

   public void removeApplicationListener(ApplicationListener listener) {
      synchronized(this.retrievalMutex) {
         this.defaultRetriever.applicationListeners.remove(listener);
         this.retrieverCache.clear();
      }
   }

   public void removeApplicationListenerBean(String listenerBeanName) {
      synchronized(this.retrievalMutex) {
         this.defaultRetriever.applicationListenerBeans.remove(listenerBeanName);
         this.retrieverCache.clear();
      }
   }

   public void removeAllListeners() {
      synchronized(this.retrievalMutex) {
         this.defaultRetriever.applicationListeners.clear();
         this.defaultRetriever.applicationListenerBeans.clear();
         this.retrieverCache.clear();
      }
   }

   protected Collection getApplicationListeners() {
      synchronized(this.retrievalMutex) {
         return this.defaultRetriever.getApplicationListeners();
      }
   }

   protected Collection getApplicationListeners(ApplicationEvent event, ResolvableType eventType) {
      Object source = event.getSource();
      Class sourceType = source != null ? source.getClass() : null;
      ListenerCacheKey cacheKey = new ListenerCacheKey(eventType, sourceType);
      ListenerRetriever retriever = (ListenerRetriever)this.retrieverCache.get(cacheKey);
      if (retriever != null) {
         return retriever.getApplicationListeners();
      } else if (this.beanClassLoader == null || ClassUtils.isCacheSafe(event.getClass(), this.beanClassLoader) && (sourceType == null || ClassUtils.isCacheSafe(sourceType, this.beanClassLoader))) {
         synchronized(this.retrievalMutex) {
            retriever = (ListenerRetriever)this.retrieverCache.get(cacheKey);
            if (retriever != null) {
               return retriever.getApplicationListeners();
            } else {
               retriever = new ListenerRetriever(true);
               Collection listeners = this.retrieveApplicationListeners(eventType, sourceType, retriever);
               this.retrieverCache.put(cacheKey, retriever);
               return listeners;
            }
         }
      } else {
         return this.retrieveApplicationListeners(eventType, sourceType, (ListenerRetriever)null);
      }
   }

   private Collection retrieveApplicationListeners(ResolvableType eventType, @Nullable Class sourceType, @Nullable ListenerRetriever retriever) {
      List allListeners = new ArrayList();
      LinkedHashSet listeners;
      LinkedHashSet listenerBeans;
      synchronized(this.retrievalMutex) {
         listeners = new LinkedHashSet(this.defaultRetriever.applicationListeners);
         listenerBeans = new LinkedHashSet(this.defaultRetriever.applicationListenerBeans);
      }

      Iterator var7 = listeners.iterator();

      while(var7.hasNext()) {
         ApplicationListener listener = (ApplicationListener)var7.next();
         if (this.supportsEvent(listener, eventType, sourceType)) {
            if (retriever != null) {
               retriever.applicationListeners.add(listener);
            }

            allListeners.add(listener);
         }
      }

      if (!listenerBeans.isEmpty()) {
         BeanFactory beanFactory = this.getBeanFactory();
         Iterator var15 = listenerBeans.iterator();

         while(var15.hasNext()) {
            String listenerBeanName = (String)var15.next();

            try {
               Class listenerType = beanFactory.getType(listenerBeanName);
               if (listenerType == null || this.supportsEvent(listenerType, eventType)) {
                  ApplicationListener listener = (ApplicationListener)beanFactory.getBean(listenerBeanName, ApplicationListener.class);
                  if (!allListeners.contains(listener) && this.supportsEvent(listener, eventType, sourceType)) {
                     if (retriever != null) {
                        if (beanFactory.isSingleton(listenerBeanName)) {
                           retriever.applicationListeners.add(listener);
                        } else {
                           retriever.applicationListenerBeans.add(listenerBeanName);
                        }
                     }

                     allListeners.add(listener);
                  }
               }
            } catch (NoSuchBeanDefinitionException var13) {
            }
         }
      }

      AnnotationAwareOrderComparator.sort((List)allListeners);
      if (retriever != null && retriever.applicationListenerBeans.isEmpty()) {
         retriever.applicationListeners.clear();
         retriever.applicationListeners.addAll(allListeners);
      }

      return allListeners;
   }

   protected boolean supportsEvent(Class listenerType, ResolvableType eventType) {
      if (!GenericApplicationListener.class.isAssignableFrom(listenerType) && !SmartApplicationListener.class.isAssignableFrom(listenerType)) {
         ResolvableType declaredEventType = GenericApplicationListenerAdapter.resolveDeclaredEventType(listenerType);
         return declaredEventType == null || declaredEventType.isAssignableFrom(eventType);
      } else {
         return true;
      }
   }

   protected boolean supportsEvent(ApplicationListener listener, ResolvableType eventType, @Nullable Class sourceType) {
      GenericApplicationListener smartListener = listener instanceof GenericApplicationListener ? (GenericApplicationListener)listener : new GenericApplicationListenerAdapter(listener);
      return ((GenericApplicationListener)smartListener).supportsEventType(eventType) && ((GenericApplicationListener)smartListener).supportsSourceType(sourceType);
   }

   private class ListenerRetriever {
      public final Set applicationListeners = new LinkedHashSet();
      public final Set applicationListenerBeans = new LinkedHashSet();
      private final boolean preFiltered;

      public ListenerRetriever(boolean preFiltered) {
         this.preFiltered = preFiltered;
      }

      public Collection getApplicationListeners() {
         List allListeners = new ArrayList(this.applicationListeners.size() + this.applicationListenerBeans.size());
         allListeners.addAll(this.applicationListeners);
         if (!this.applicationListenerBeans.isEmpty()) {
            BeanFactory beanFactory = AbstractApplicationEventMulticaster.this.getBeanFactory();
            Iterator var3 = this.applicationListenerBeans.iterator();

            while(var3.hasNext()) {
               String listenerBeanName = (String)var3.next();

               try {
                  ApplicationListener listener = (ApplicationListener)beanFactory.getBean(listenerBeanName, ApplicationListener.class);
                  if (this.preFiltered || !allListeners.contains(listener)) {
                     allListeners.add(listener);
                  }
               } catch (NoSuchBeanDefinitionException var6) {
               }
            }
         }

         if (!this.preFiltered || !this.applicationListenerBeans.isEmpty()) {
            AnnotationAwareOrderComparator.sort((List)allListeners);
         }

         return allListeners;
      }
   }

   private static final class ListenerCacheKey implements Comparable {
      private final ResolvableType eventType;
      @Nullable
      private final Class sourceType;

      public ListenerCacheKey(ResolvableType eventType, @Nullable Class sourceType) {
         Assert.notNull(eventType, (String)"Event type must not be null");
         this.eventType = eventType;
         this.sourceType = sourceType;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else {
            ListenerCacheKey otherKey = (ListenerCacheKey)other;
            return this.eventType.equals(otherKey.eventType) && ObjectUtils.nullSafeEquals(this.sourceType, otherKey.sourceType);
         }
      }

      public int hashCode() {
         return this.eventType.hashCode() * 29 + ObjectUtils.nullSafeHashCode((Object)this.sourceType);
      }

      public String toString() {
         return "ListenerCacheKey [eventType = " + this.eventType + ", sourceType = " + this.sourceType + "]";
      }

      public int compareTo(ListenerCacheKey other) {
         int result = this.eventType.toString().compareTo(other.eventType.toString());
         if (result == 0) {
            if (this.sourceType == null) {
               return other.sourceType == null ? 0 : -1;
            }

            if (other.sourceType == null) {
               return 1;
            }

            result = this.sourceType.getName().compareTo(other.sourceType.getName());
         }

         return result;
      }
   }
}
