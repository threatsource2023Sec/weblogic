package org.jboss.weld.bean.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.InterceptionType;
import org.jboss.weld.ejb.spi.InterceptorBindings;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.logging.BeanLogger;

public class InterceptorBindingsAdapter implements InterceptorBindings {
   private InterceptionModel interceptionModel;

   public InterceptorBindingsAdapter(InterceptionModel interceptionModel) {
      if (interceptionModel == null) {
         throw BeanLogger.LOG.interceptionModelNull();
      } else {
         this.interceptionModel = interceptionModel;
      }
   }

   public Collection getAllInterceptors() {
      Set interceptorMetadataSet = this.interceptionModel.getAllInterceptors();
      return this.extractCdiInterceptors(interceptorMetadataSet);
   }

   public List getMethodInterceptors(InterceptionType interceptionType, Method method) {
      if (interceptionType == null) {
         throw BeanLogger.LOG.interceptionTypeNull();
      } else if (method == null) {
         throw BeanLogger.LOG.methodNull();
      } else {
         org.jboss.weld.interceptor.spi.model.InterceptionType internalInterceptionType = org.jboss.weld.interceptor.spi.model.InterceptionType.valueOf(interceptionType.name());
         if (internalInterceptionType.isLifecycleCallback()) {
            throw BeanLogger.LOG.interceptionTypeLifecycle(interceptionType.name());
         } else {
            return this.extractCdiInterceptors(this.interceptionModel.getInterceptors(internalInterceptionType, method));
         }
      }
   }

   public List getLifecycleInterceptors(InterceptionType interceptionType) {
      if (interceptionType == null) {
         throw BeanLogger.LOG.interceptionTypeNull();
      } else {
         org.jboss.weld.interceptor.spi.model.InterceptionType internalInterceptionType = org.jboss.weld.interceptor.spi.model.InterceptionType.valueOf(interceptionType.name());
         if (!internalInterceptionType.isLifecycleCallback()) {
            throw BeanLogger.LOG.interceptionTypeNotLifecycle(interceptionType.name());
         } else {
            return internalInterceptionType.equals(org.jboss.weld.interceptor.spi.model.InterceptionType.AROUND_CONSTRUCT) ? this.extractCdiInterceptors(this.interceptionModel.getConstructorInvocationInterceptors()) : this.extractCdiInterceptors(this.interceptionModel.getInterceptors(internalInterceptionType, (Method)null));
         }
      }
   }

   private List extractCdiInterceptors(Collection interceptorMetadatas) {
      ArrayList interceptors = new ArrayList();
      Iterator var3 = interceptorMetadatas.iterator();

      while(var3.hasNext()) {
         InterceptorClassMetadata interceptorMetadata = (InterceptorClassMetadata)var3.next();
         InterceptorFactory interceptorFactory = interceptorMetadata.getInterceptorFactory();
         if (interceptorFactory instanceof CdiInterceptorFactory) {
            CdiInterceptorFactory cdiInterceptorFactory = (CdiInterceptorFactory)interceptorFactory;
            interceptors.add(cdiInterceptorFactory.getInterceptor());
         }
      }

      return interceptors;
   }
}
