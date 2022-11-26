package org.jboss.weld.bean.interceptor;

import java.io.Serializable;
import javax.enterprise.inject.spi.Interceptor;
import org.jboss.weld.interceptor.proxy.CustomInterceptorInvocation;
import org.jboss.weld.interceptor.proxy.InterceptorInvocation;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionType;

public class CustomInterceptorMetadata implements InterceptorClassMetadata {
   private final CdiInterceptorFactory factory;
   private final Class javaClass;
   private final String key;

   public static CustomInterceptorMetadata of(Interceptor interceptor) {
      return new CustomInterceptorMetadata(new CdiInterceptorFactory(interceptor), interceptor.getBeanClass(), interceptor.getName() != null && !interceptor.getName().isEmpty() ? interceptor.getName() : null);
   }

   private CustomInterceptorMetadata(CdiInterceptorFactory factory, Class javaClass, String key) {
      this.factory = factory;
      this.javaClass = javaClass;
      this.key = key;
   }

   public CdiInterceptorFactory getInterceptorFactory() {
      return this.factory;
   }

   public boolean isEligible(InterceptionType interceptionType) {
      return this.factory.getInterceptor().intercepts(javax.enterprise.inject.spi.InterceptionType.valueOf(interceptionType.name()));
   }

   public InterceptorInvocation getInterceptorInvocation(Object interceptorInstance, InterceptionType interceptionType) {
      return new CustomInterceptorInvocation(this.factory.getInterceptor(), interceptorInstance, javax.enterprise.inject.spi.InterceptionType.valueOf(interceptionType.name()));
   }

   public String toString() {
      return "CustomInterceptorMetadata [" + this.getJavaClass().getName() + "]";
   }

   public Class getJavaClass() {
      return this.javaClass;
   }

   public Serializable getKey() {
      return (Serializable)(this.key != null ? this.key : InterceptorClassMetadata.super.getKey());
   }
}
