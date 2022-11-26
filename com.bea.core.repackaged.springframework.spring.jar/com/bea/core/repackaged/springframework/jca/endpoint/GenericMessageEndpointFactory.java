package com.bea.core.repackaged.springframework.jca.endpoint;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;
import javax.resource.ResourceException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.transaction.xa.XAResource;

public class GenericMessageEndpointFactory extends AbstractMessageEndpointFactory {
   @Nullable
   private Object messageListener;

   public void setMessageListener(Object messageListener) {
      this.messageListener = messageListener;
   }

   protected Object getMessageListener() {
      Assert.state(this.messageListener != null, "No message listener set");
      return this.messageListener;
   }

   public MessageEndpoint createEndpoint(XAResource xaResource) throws UnavailableException {
      GenericMessageEndpoint endpoint = (GenericMessageEndpoint)super.createEndpoint(xaResource);
      ProxyFactory proxyFactory = new ProxyFactory(this.getMessageListener());
      DelegatingIntroductionInterceptor introduction = new DelegatingIntroductionInterceptor(endpoint);
      introduction.suppressInterface(MethodInterceptor.class);
      proxyFactory.addAdvice(introduction);
      return (MessageEndpoint)proxyFactory.getProxy();
   }

   protected AbstractMessageEndpointFactory.AbstractMessageEndpoint createEndpointInternal() throws UnavailableException {
      return new GenericMessageEndpoint();
   }

   public static class InternalResourceException extends RuntimeException {
      public InternalResourceException(ResourceException cause) {
         super(cause);
      }
   }

   private class GenericMessageEndpoint extends AbstractMessageEndpointFactory.AbstractMessageEndpoint implements MethodInterceptor {
      private GenericMessageEndpoint() {
         super();
      }

      public Object invoke(MethodInvocation methodInvocation) throws Throwable {
         Throwable endpointEx = null;
         boolean applyDeliveryCalls = !this.hasBeforeDeliveryBeenCalled();
         if (applyDeliveryCalls) {
            try {
               this.beforeDelivery((Method)null);
            } catch (ResourceException var14) {
               throw this.adaptExceptionIfNecessary(methodInvocation, var14);
            }
         }

         Object var4;
         try {
            var4 = methodInvocation.proceed();
         } catch (Throwable var13) {
            endpointEx = var13;
            this.onEndpointException(var13);
            throw var13;
         } finally {
            if (applyDeliveryCalls) {
               try {
                  this.afterDelivery();
               } catch (ResourceException var15) {
                  if (endpointEx == null) {
                     throw this.adaptExceptionIfNecessary(methodInvocation, var15);
                  }
               }
            }

         }

         return var4;
      }

      private Exception adaptExceptionIfNecessary(MethodInvocation methodInvocation, ResourceException ex) {
         return (Exception)(ReflectionUtils.declaresException(methodInvocation.getMethod(), ex.getClass()) ? ex : new InternalResourceException(ex));
      }

      protected ClassLoader getEndpointClassLoader() {
         return GenericMessageEndpointFactory.this.getMessageListener().getClass().getClassLoader();
      }

      // $FF: synthetic method
      GenericMessageEndpoint(Object x1) {
         this();
      }
   }
}
