package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import java.util.Map;

public class GenericApplicationListenerAdapter implements GenericApplicationListener, SmartApplicationListener {
   private static final Map eventTypeCache = new ConcurrentReferenceHashMap();
   private final ApplicationListener delegate;
   @Nullable
   private final ResolvableType declaredEventType;

   public GenericApplicationListenerAdapter(ApplicationListener delegate) {
      Assert.notNull(delegate, (String)"Delegate listener must not be null");
      this.delegate = delegate;
      this.declaredEventType = resolveDeclaredEventType(this.delegate);
   }

   public void onApplicationEvent(ApplicationEvent event) {
      this.delegate.onApplicationEvent(event);
   }

   public boolean supportsEventType(ResolvableType eventType) {
      if (this.delegate instanceof SmartApplicationListener) {
         Class eventClass = eventType.resolve();
         return eventClass != null && ((SmartApplicationListener)this.delegate).supportsEventType(eventClass);
      } else {
         return this.declaredEventType == null || this.declaredEventType.isAssignableFrom(eventType);
      }
   }

   public boolean supportsEventType(Class eventType) {
      return this.supportsEventType(ResolvableType.forClass(eventType));
   }

   public boolean supportsSourceType(@Nullable Class sourceType) {
      return !(this.delegate instanceof SmartApplicationListener) || ((SmartApplicationListener)this.delegate).supportsSourceType(sourceType);
   }

   public int getOrder() {
      return this.delegate instanceof Ordered ? ((Ordered)this.delegate).getOrder() : Integer.MAX_VALUE;
   }

   @Nullable
   private static ResolvableType resolveDeclaredEventType(ApplicationListener listener) {
      ResolvableType declaredEventType = resolveDeclaredEventType(listener.getClass());
      if (declaredEventType == null || declaredEventType.isAssignableFrom(ApplicationEvent.class)) {
         Class targetClass = AopUtils.getTargetClass(listener);
         if (targetClass != listener.getClass()) {
            declaredEventType = resolveDeclaredEventType(targetClass);
         }
      }

      return declaredEventType;
   }

   @Nullable
   static ResolvableType resolveDeclaredEventType(Class listenerType) {
      ResolvableType eventType = (ResolvableType)eventTypeCache.get(listenerType);
      if (eventType == null) {
         eventType = ResolvableType.forClass(listenerType).as(ApplicationListener.class).getGeneric();
         eventTypeCache.put(listenerType, eventType);
      }

      return eventType != ResolvableType.NONE ? eventType : null;
   }
}
