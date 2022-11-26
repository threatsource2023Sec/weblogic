package org.jboss.weld.interceptor.proxy;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.interceptor.reader.TargetClassInterceptorMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.interceptor.spi.model.InterceptionType;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptionContext implements Serializable {
   private static final Set METHOD_INTERCEPTION_TYPES;
   private static final long serialVersionUID = 7500722360133273633L;
   private final transient InterceptionModel interceptionModel;
   private final Map interceptorInstances;
   private final BeanManagerImpl manager;
   private final SlimAnnotatedType annotatedType;

   public static InterceptionContext forConstructorInterception(InterceptionModel interceptionModel, CreationalContext ctx, BeanManagerImpl manager, SlimAnnotatedType type) {
      return of(interceptionModel, ctx, manager, (Set)null, type);
   }

   public static InterceptionContext forNonConstructorInterception(InterceptionModel interceptionModel, CreationalContext ctx, BeanManagerImpl manager, SlimAnnotatedType type) {
      return of(interceptionModel, ctx, manager, METHOD_INTERCEPTION_TYPES, type);
   }

   private static InterceptionContext of(InterceptionModel interceptionModel, CreationalContext ctx, BeanManagerImpl manager, Set interceptionTypes, SlimAnnotatedType type) {
      return new InterceptionContext(initInterceptorInstanceMap(interceptionModel, ctx, manager, interceptionTypes), manager, interceptionModel, type);
   }

   private InterceptionContext(Map interceptorInstances, BeanManagerImpl manager, InterceptionModel interceptionModel, SlimAnnotatedType type) {
      this.interceptorInstances = interceptorInstances;
      this.manager = manager;
      this.interceptionModel = interceptionModel;
      this.annotatedType = type;
   }

   private static Map initInterceptorInstanceMap(InterceptionModel model, CreationalContext ctx, BeanManagerImpl manager, Set interceptionTypes) {
      Map interceptorInstances = new HashMap();
      Iterator var5 = model.getAllInterceptors().iterator();

      while(true) {
         while(var5.hasNext()) {
            InterceptorClassMetadata interceptor = (InterceptorClassMetadata)var5.next();
            if (interceptionTypes != null) {
               Iterator var7 = interceptionTypes.iterator();

               while(var7.hasNext()) {
                  InterceptionType interceptionType = (InterceptionType)var7.next();
                  if (interceptor.isEligible(interceptionType) && !interceptorInstances.containsKey(interceptor.getKey())) {
                     interceptorInstances.put(interceptor.getKey(), interceptor.getInterceptorFactory().create(ctx, manager));
                  }
               }
            } else {
               interceptorInstances.put(interceptor.getKey(), interceptor.getInterceptorFactory().create(ctx, manager));
            }
         }

         return WeldCollections.immutableMapView(interceptorInstances);
      }
   }

   public InterceptionModel getInterceptionModel() {
      return this.interceptionModel;
   }

   public Object getInterceptorInstance(InterceptorClassMetadata interceptorMetadata) {
      return Reflections.cast(this.interceptorInstances.get(interceptorMetadata.getKey()));
   }

   private Object readResolve() throws ObjectStreamException {
      InterceptionModel interceptionModel = (InterceptionModel)this.manager.getInterceptorModelRegistry().get(this.annotatedType);
      return new InterceptionContext(this.interceptorInstances, this.manager, interceptionModel, this.annotatedType);
   }

   public List buildInterceptorMethodInvocations(Object instance, Method method, InterceptionType interceptionType) {
      List interceptorList = this.interceptionModel.getInterceptors(interceptionType, method);
      List interceptorInvocations = new ArrayList(interceptorList.size());
      Iterator var6 = interceptorList.iterator();

      while(var6.hasNext()) {
         InterceptorClassMetadata interceptorMetadata = (InterceptorClassMetadata)var6.next();
         interceptorInvocations.addAll(interceptorMetadata.getInterceptorInvocation(this.getInterceptorInstance(interceptorMetadata), interceptionType).getInterceptorMethodInvocations());
      }

      TargetClassInterceptorMetadata targetClassInterceptorMetadata = this.getInterceptionModel().getTargetClassInterceptorMetadata();
      if (targetClassInterceptorMetadata != null && targetClassInterceptorMetadata.isEligible(interceptionType)) {
         interceptorInvocations.addAll(targetClassInterceptorMetadata.getInterceptorInvocation(instance, interceptionType).getInterceptorMethodInvocations());
      }

      return ImmutableList.copyOf((Collection)interceptorInvocations);
   }

   public List buildInterceptorMethodInvocationsForConstructorInterception() {
      List interceptorList = this.interceptionModel.getConstructorInvocationInterceptors();
      List interceptorInvocations = new ArrayList(interceptorList.size());
      Iterator var3 = interceptorList.iterator();

      while(var3.hasNext()) {
         InterceptorClassMetadata metadata = (InterceptorClassMetadata)var3.next();
         Object interceptorInstance = this.getInterceptorInstance(metadata);
         InterceptorInvocation invocation = metadata.getInterceptorInvocation(interceptorInstance, InterceptionType.AROUND_CONSTRUCT);
         interceptorInvocations.addAll(invocation.getInterceptorMethodInvocations());
      }

      return ImmutableList.copyOf((Collection)interceptorInvocations);
   }

   static {
      METHOD_INTERCEPTION_TYPES = ImmutableSet.of(InterceptionType.AROUND_INVOKE, InterceptionType.AROUND_TIMEOUT, InterceptionType.POST_CONSTRUCT, InterceptionType.PRE_DESTROY, InterceptionType.POST_ACTIVATE, InterceptionType.PRE_PASSIVATE);
   }
}
