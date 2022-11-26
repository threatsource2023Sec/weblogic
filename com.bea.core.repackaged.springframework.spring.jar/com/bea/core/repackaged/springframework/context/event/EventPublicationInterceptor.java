package com.bea.core.repackaged.springframework.context.event;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.context.ApplicationEvent;
import com.bea.core.repackaged.springframework.context.ApplicationEventPublisher;
import com.bea.core.repackaged.springframework.context.ApplicationEventPublisherAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Constructor;

public class EventPublicationInterceptor implements MethodInterceptor, ApplicationEventPublisherAware, InitializingBean {
   @Nullable
   private Constructor applicationEventClassConstructor;
   @Nullable
   private ApplicationEventPublisher applicationEventPublisher;

   public void setApplicationEventClass(Class applicationEventClass) {
      if (ApplicationEvent.class != applicationEventClass && ApplicationEvent.class.isAssignableFrom(applicationEventClass)) {
         try {
            this.applicationEventClassConstructor = applicationEventClass.getConstructor(Object.class);
         } catch (NoSuchMethodException var3) {
            throw new IllegalArgumentException("ApplicationEvent class [" + applicationEventClass.getName() + "] does not have the required Object constructor: " + var3);
         }
      } else {
         throw new IllegalArgumentException("'applicationEventClass' needs to extend ApplicationEvent");
      }
   }

   public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
      this.applicationEventPublisher = applicationEventPublisher;
   }

   public void afterPropertiesSet() throws Exception {
      if (this.applicationEventClassConstructor == null) {
         throw new IllegalArgumentException("Property 'applicationEventClass' is required");
      }
   }

   public Object invoke(MethodInvocation invocation) throws Throwable {
      Object retVal = invocation.proceed();
      Assert.state(this.applicationEventClassConstructor != null, "No ApplicationEvent class set");
      ApplicationEvent event = (ApplicationEvent)this.applicationEventClassConstructor.newInstance(invocation.getThis());
      Assert.state(this.applicationEventPublisher != null, "No ApplicationEventPublisher available");
      this.applicationEventPublisher.publishEvent(event);
      return retVal;
   }
}
