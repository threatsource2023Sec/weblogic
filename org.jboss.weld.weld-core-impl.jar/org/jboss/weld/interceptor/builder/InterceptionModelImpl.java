package org.jboss.weld.interceptor.builder;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.util.collections.ImmutableSet;

class InterceptionModelImpl implements InterceptionModel {
   private final Map globalInterceptors;
   private final Map methodBoundInterceptors;
   private final Set methodsIgnoringGlobalInterceptors;
   private final Set allInterceptors;
   private final boolean hasExternalNonConstructorInterceptors;
   private final TargetClassInterceptorMetadata targetClassInterceptorMetadata;
   private final Map memberInterceptorBindings;
   private final Set classInterceptorBindings;

   InterceptionModelImpl(InterceptionModelBuilder builder) {
      this.hasExternalNonConstructorInterceptors = builder.isHasExternalNonConstructorInterceptors();
      this.globalInterceptors = builder.getGlobalInterceptors();
      this.methodBoundInterceptors = builder.getMethodBoundInterceptors();
      this.methodsIgnoringGlobalInterceptors = builder.getMethodsIgnoringGlobalInterceptors();
      this.allInterceptors = builder.getAllInterceptors();
      this.targetClassInterceptorMetadata = builder.getTargetClassInterceptorMetadata();
      this.memberInterceptorBindings = builder.getMemberInterceptorBindings();
      this.classInterceptorBindings = ImmutableSet.copyOf(builder.getClassInterceptorBindings());
   }

   public List getInterceptors(InterceptionType interceptionType, Method method) {
      if (InterceptionType.AROUND_CONSTRUCT.equals(interceptionType)) {
         throw new IllegalStateException("Cannot use getInterceptors() for @AroundConstruct interceptor lookup. Use getConstructorInvocationInterceptors() instead.");
      } else if (interceptionType.isLifecycleCallback() && method != null) {
         throw new IllegalArgumentException("On a lifecycle callback, the associated method must be null");
      } else if (!interceptionType.isLifecycleCallback() && method == null) {
         throw new IllegalArgumentException("Around-invoke and around-timeout interceptors are defined for a given method");
      } else if (interceptionType.isLifecycleCallback()) {
         return this.globalInterceptors.containsKey(interceptionType) ? (List)this.globalInterceptors.get(interceptionType) : Collections.emptyList();
      } else {
         ArrayList returnedInterceptors = new ArrayList();
         if (!this.methodsIgnoringGlobalInterceptors.contains(method) && this.globalInterceptors.containsKey(interceptionType)) {
            returnedInterceptors.addAll((Collection)this.globalInterceptors.get(interceptionType));
         }

         Map map = (Map)this.methodBoundInterceptors.get(interceptionType);
         if (map != null) {
            List list = (List)map.get(method);
            if (list != null) {
               returnedInterceptors.addAll(list);
            }
         }

         return returnedInterceptors;
      }
   }

   public Set getAllInterceptors() {
      return this.allInterceptors;
   }

   public List getConstructorInvocationInterceptors() {
      return this.globalInterceptors.containsKey(InterceptionType.AROUND_CONSTRUCT) ? (List)this.globalInterceptors.get(InterceptionType.AROUND_CONSTRUCT) : Collections.emptyList();
   }

   public boolean hasExternalConstructorInterceptors() {
      return !this.getConstructorInvocationInterceptors().isEmpty();
   }

   public boolean hasExternalNonConstructorInterceptors() {
      return this.hasExternalNonConstructorInterceptors;
   }

   public boolean hasTargetClassInterceptors() {
      return this.targetClassInterceptorMetadata != null && this.targetClassInterceptorMetadata != TargetClassInterceptorMetadata.EMPTY_INSTANCE;
   }

   public TargetClassInterceptorMetadata getTargetClassInterceptorMetadata() {
      return this.targetClassInterceptorMetadata;
   }

   public Set getClassInterceptorBindings() {
      return this.classInterceptorBindings;
   }

   public Set getMemberInterceptorBindings(Member member) {
      return (Set)this.memberInterceptorBindings.get(member);
   }
}
