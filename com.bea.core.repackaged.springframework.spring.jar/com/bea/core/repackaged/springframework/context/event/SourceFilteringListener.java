package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Type;

public class SourceFilteringListener implements GenericApplicationListener, SmartApplicationListener {
   private final Object source;
   @Nullable
   private GenericApplicationListener delegate;

   public SourceFilteringListener(Object source, ApplicationListener delegate) {
      this.source = source;
      this.delegate = (GenericApplicationListener)(delegate instanceof GenericApplicationListener ? (GenericApplicationListener)delegate : new GenericApplicationListenerAdapter(delegate));
   }

   protected SourceFilteringListener(Object source) {
      this.source = source;
   }

   public void onApplicationEvent(ApplicationEvent event) {
      if (event.getSource() == this.source) {
         this.onApplicationEventInternal(event);
      }

   }

   public boolean supportsEventType(ResolvableType eventType) {
      return this.delegate == null || this.delegate.supportsEventType(eventType);
   }

   public boolean supportsEventType(Class eventType) {
      return this.supportsEventType(ResolvableType.forType((Type)eventType));
   }

   public boolean supportsSourceType(@Nullable Class sourceType) {
      return sourceType != null && sourceType.isInstance(this.source);
   }

   public int getOrder() {
      return this.delegate != null ? this.delegate.getOrder() : Integer.MAX_VALUE;
   }

   protected void onApplicationEventInternal(ApplicationEvent event) {
      if (this.delegate == null) {
         throw new IllegalStateException("Must specify a delegate object or override the onApplicationEventInternal method");
      } else {
         this.delegate.onApplicationEvent(event);
      }
   }
}
