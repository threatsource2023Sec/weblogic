package org.jboss.weld.interceptor.reader;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.util.collections.ImmutableSet;

public class TargetClassInterceptorMetadata extends AbstractInterceptorMetadata {
   public static final TargetClassInterceptorMetadata EMPTY_INSTANCE = new TargetClassInterceptorMetadata(Collections.emptyMap());
   private final Set interceptorMethods;

   public static TargetClassInterceptorMetadata of(Map interceptorMethodMap) {
      return interceptorMethodMap.isEmpty() ? EMPTY_INSTANCE : new TargetClassInterceptorMetadata(interceptorMethodMap);
   }

   private TargetClassInterceptorMetadata(Map interceptorMethodMap) {
      super(interceptorMethodMap);
      this.interceptorMethods = this.initInterceptorMethods(interceptorMethodMap);
   }

   private Set initInterceptorMethods(Map interceptorMethodMap) {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      Iterator var3 = interceptorMethodMap.values().iterator();

      while(var3.hasNext()) {
         List methodList = (List)var3.next();
         builder.addAll((Iterable)methodList);
      }

      return builder.build();
   }

   protected boolean isTargetClassInterceptor() {
      return true;
   }

   public boolean isInterceptorMethod(Method method) {
      return this.interceptorMethods.contains(method);
   }
}
