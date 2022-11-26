package org.jboss.weld.interceptor.spi.metadata;

import org.jboss.weld.interceptor.proxy.InterceptorInvocation;
import org.jboss.weld.interceptor.spi.model.InterceptionType;

public interface InterceptorMetadata {
   boolean isEligible(InterceptionType var1);

   InterceptorInvocation getInterceptorInvocation(Object var1, InterceptionType var2);
}
