package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationListener;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ErrorHandler;
import java.util.Iterator;
import java.util.concurrent.Executor;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
   @Nullable
   private Executor taskExecutor;
   @Nullable
   private ErrorHandler errorHandler;

   public SimpleApplicationEventMulticaster() {
   }

   public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
      this.setBeanFactory(beanFactory);
   }

   public void setTaskExecutor(@Nullable Executor taskExecutor) {
      this.taskExecutor = taskExecutor;
   }

   @Nullable
   protected Executor getTaskExecutor() {
      return this.taskExecutor;
   }

   public void setErrorHandler(@Nullable ErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
   }

   @Nullable
   protected ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public void multicastEvent(ApplicationEvent event) {
      this.multicastEvent(event, this.resolveDefaultEventType(event));
   }

   public void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType) {
      ResolvableType type = eventType != null ? eventType : this.resolveDefaultEventType(event);
      Executor executor = this.getTaskExecutor();
      Iterator var5 = this.getApplicationListeners(event, type).iterator();

      while(var5.hasNext()) {
         ApplicationListener listener = (ApplicationListener)var5.next();
         if (executor != null) {
            executor.execute(() -> {
               this.invokeListener(listener, event);
            });
         } else {
            this.invokeListener(listener, event);
         }
      }

   }

   private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
      return ResolvableType.forInstance(event);
   }

   protected void invokeListener(ApplicationListener listener, ApplicationEvent event) {
      ErrorHandler errorHandler = this.getErrorHandler();
      if (errorHandler != null) {
         try {
            this.doInvokeListener(listener, event);
         } catch (Throwable var5) {
            errorHandler.handleError(var5);
         }
      } else {
         this.doInvokeListener(listener, event);
      }

   }

   private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
      try {
         listener.onApplicationEvent(event);
      } catch (ClassCastException var6) {
         String msg = var6.getMessage();
         if (msg != null && !this.matchesClassCastMessage(msg, event.getClass())) {
            throw var6;
         }

         Log logger = LogFactory.getLog(this.getClass());
         if (logger.isTraceEnabled()) {
            logger.trace("Non-matching event type for listener: " + listener, var6);
         }
      }

   }

   private boolean matchesClassCastMessage(String classCastMessage, Class eventClass) {
      if (classCastMessage.startsWith(eventClass.getName())) {
         return true;
      } else if (classCastMessage.startsWith(eventClass.toString())) {
         return true;
      } else {
         int moduleSeparatorIndex = classCastMessage.indexOf(47);
         return moduleSeparatorIndex != -1 && classCastMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1);
      }
   }
}
