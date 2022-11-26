package org.jboss.weld.interceptor.proxy;

import javax.interceptor.InvocationContext;

public interface InterceptorMethodInvocation {
   Object invoke(InvocationContext var1) throws Exception;

   boolean expectsInvocationContext();
}
