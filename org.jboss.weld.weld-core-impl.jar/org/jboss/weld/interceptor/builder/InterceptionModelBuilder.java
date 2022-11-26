package org.jboss.weld.interceptor.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.ImmutableSet;

public class InterceptionModelBuilder {
   private boolean isModelBuilt = false;
   private boolean hasExternalNonConstructorInterceptors;
   private final ImmutableSet.Builder methodsIgnoringGlobalInterceptors = ImmutableSet.builder();
   private final ImmutableSet.Builder allInterceptors = ImmutableSet.builder();
   private final Map globalInterceptors = new EnumMap(InterceptionType.class);
   private final Map methodBoundInterceptors = new EnumMap(InterceptionType.class);
   private TargetClassInterceptorMetadata targetClassInterceptorMetadata;
   private final ImmutableMap.Builder memberInterceptorBindings = ImmutableMap.builder();
   private Set classInterceptorBindings;

   public InterceptionModel build() {
      this.checkModelNotBuilt();
      this.isModelBuilt = true;
      return new InterceptionModelImpl(this);
   }

   public void interceptMethod(javax.enterprise.inject.spi.InterceptionType interceptionType, Method method, Collection interceptors, Set interceptorBindings) {
      this.checkModelNotBuilt();
      InterceptionType weldInterceptionType = InterceptionType.valueOf(interceptionType);
      if (weldInterceptionType.isLifecycleCallback()) {
         throw new IllegalArgumentException("Illegal interception type: " + interceptionType);
      } else {
         if (null == this.methodBoundInterceptors.get(weldInterceptionType)) {
            this.methodBoundInterceptors.put(weldInterceptionType, new HashMap());
         }

         List interceptorsList = (List)((Map)this.methodBoundInterceptors.get(weldInterceptionType)).get(method);
         if (interceptorsList == null) {
            interceptorsList = new ArrayList();
            ((Map)this.methodBoundInterceptors.get(weldInterceptionType)).put(method, interceptorsList);
         }

         ((List)interceptorsList).addAll(interceptors);
         this.intercept(weldInterceptionType, interceptors);
         if (interceptorBindings != null) {
            this.memberInterceptorBindings.put(method, interceptorBindings);
         }

      }
   }

   public void interceptGlobal(javax.enterprise.inject.spi.InterceptionType interceptionType, Constructor constructor, Collection interceptors, Set interceptorBindings) {
      this.checkModelNotBuilt();
      InterceptionType weldInterceptionType = InterceptionType.valueOf(interceptionType);
      List interceptorsList = (List)this.globalInterceptors.get(weldInterceptionType);
      if (interceptorsList == null) {
         interceptorsList = new ArrayList();
         this.globalInterceptors.put(weldInterceptionType, interceptorsList);
      }

      ((List)interceptorsList).addAll(interceptors);
      this.intercept(weldInterceptionType, interceptors);
      if (constructor != null) {
         this.memberInterceptorBindings.put(constructor, interceptorBindings);
      }

   }

   private void intercept(InterceptionType interceptionType, Collection interceptors) {
      if (interceptionType != InterceptionType.AROUND_CONSTRUCT) {
         this.hasExternalNonConstructorInterceptors = true;
      }

      this.allInterceptors.addAll((Iterable)interceptors);
   }

   public void addMethodIgnoringGlobalInterceptors(Method method) {
      this.checkModelNotBuilt();
      this.methodsIgnoringGlobalInterceptors.add(method);
   }

   boolean isHasExternalNonConstructorInterceptors() {
      return this.hasExternalNonConstructorInterceptors;
   }

   Set getMethodsIgnoringGlobalInterceptors() {
      return this.methodsIgnoringGlobalInterceptors.build();
   }

   Set getAllInterceptors() {
      return this.allInterceptors.build();
   }

   Map getGlobalInterceptors() {
      if (this.globalInterceptors.isEmpty()) {
         return Collections.emptyMap();
      } else {
         ImmutableMap.Builder builder = ImmutableMap.builder();
         Iterator var2 = this.globalInterceptors.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            builder.put(entry.getKey(), ImmutableList.copyOf((Collection)entry.getValue()));
         }

         return builder.build();
      }
   }

   Map getMethodBoundInterceptors() {
      if (this.methodBoundInterceptors.isEmpty()) {
         return Collections.emptyMap();
      } else {
         ImmutableMap.Builder builder = ImmutableMap.builder();
         Iterator var2 = this.methodBoundInterceptors.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            ImmutableMap.Builder metadataBuilder = ImmutableMap.builder();
            Iterator var5 = ((Map)entry.getValue()).entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry metadataEntry = (Map.Entry)var5.next();
               metadataBuilder.put(metadataEntry.getKey(), ImmutableList.copyOf((Collection)metadataEntry.getValue()));
            }

            builder.put(entry.getKey(), metadataBuilder.build());
         }

         return builder.build();
      }
   }

   private void checkModelNotBuilt() {
      if (this.isModelBuilt) {
         throw new IllegalStateException("InterceptionModelBuilder cannot be reused");
      }
   }

   TargetClassInterceptorMetadata getTargetClassInterceptorMetadata() {
      return this.targetClassInterceptorMetadata;
   }

   public void setTargetClassInterceptorMetadata(TargetClassInterceptorMetadata targetClassInterceptorMetadata) {
      this.targetClassInterceptorMetadata = targetClassInterceptorMetadata;
   }

   Collection getClassInterceptorBindings() {
      return this.classInterceptorBindings;
   }

   public void setClassInterceptorBindings(Set classInterceptorBindings) {
      this.classInterceptorBindings = classInterceptorBindings;
   }

   Map getMemberInterceptorBindings() {
      return this.memberInterceptorBindings.build();
   }
}
