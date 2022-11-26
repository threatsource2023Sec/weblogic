package org.jboss.weld.interceptor.reader;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jboss.weld.interceptor.proxy.InterceptorInvocation;
import org.jboss.weld.interceptor.spi.metadata.InterceptorMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionType;

public abstract class AbstractInterceptorMetadata implements InterceptorMetadata {
   protected final Map interceptorMethodMap;

   public AbstractInterceptorMetadata(Map interceptorMethodMap) {
      this.interceptorMethodMap = interceptorMethodMap;
   }

   public List getInterceptorMethods(InterceptionType interceptionType) {
      if (this.interceptorMethodMap != null) {
         List methods = (List)this.interceptorMethodMap.get(interceptionType);
         return methods == null ? Collections.emptyList() : methods;
      } else {
         return Collections.emptyList();
      }
   }

   public boolean isEligible(InterceptionType interceptionType) {
      if (this.interceptorMethodMap == null) {
         return false;
      } else {
         List interceptorMethods = (List)this.interceptorMethodMap.get(interceptionType);
         return interceptorMethods != null && !interceptorMethods.isEmpty();
      }
   }

   public InterceptorInvocation getInterceptorInvocation(Object interceptorInstance, InterceptionType interceptionType) {
      return new SimpleInterceptorInvocation(interceptorInstance, interceptionType, this.getInterceptorMethods(interceptionType), this.isTargetClassInterceptor());
   }

   protected abstract boolean isTargetClassInterceptor();
}
