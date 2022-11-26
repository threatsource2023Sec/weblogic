package org.jboss.weld.interceptor.spi.model;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;

public interface InterceptionModel {
   List getInterceptors(InterceptionType var1, Method var2);

   List getConstructorInvocationInterceptors();

   Set getAllInterceptors();

   boolean hasExternalConstructorInterceptors();

   boolean hasExternalNonConstructorInterceptors();

   boolean hasTargetClassInterceptors();

   TargetClassInterceptorMetadata getTargetClassInterceptorMetadata();

   Set getClassInterceptorBindings();

   Set getMemberInterceptorBindings(Member var1);
}
