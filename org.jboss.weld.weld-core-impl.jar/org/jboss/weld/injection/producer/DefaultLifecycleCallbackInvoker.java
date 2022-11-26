package org.jboss.weld.injection.producer;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.interceptor.util.InterceptionUtils;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.BeanMethods;
import org.jboss.weld.util.collections.ImmutableList;

public class DefaultLifecycleCallbackInvoker implements LifecycleCallbackInvoker {
   private final List accessiblePostConstructMethods;
   private final List accessiblePreDestroyMethods;

   public static DefaultLifecycleCallbackInvoker of(EnhancedAnnotatedType type) {
      return new DefaultLifecycleCallbackInvoker(type);
   }

   public DefaultLifecycleCallbackInvoker(EnhancedAnnotatedType type) {
      this.accessiblePostConstructMethods = this.initMethodList(BeanMethods.getPostConstructMethods(type));
      this.accessiblePreDestroyMethods = this.initMethodList(BeanMethods.getPreDestroyMethods(type));
   }

   private List initMethodList(List methods) {
      return (List)methods.stream().map((method) -> {
         return (Method)AccessController.doPrivileged(new GetAccessibleCopyOfMember(method.getJavaMember()));
      }).collect(ImmutableList.collector());
   }

   public void postConstruct(Object instance, Instantiator instantiator) {
      if (instantiator != null && instantiator.hasInterceptorSupport()) {
         InterceptionUtils.executePostConstruct(instance);
      } else {
         this.invokeMethods(this.accessiblePostConstructMethods, instance);
      }

   }

   public void preDestroy(Object instance, Instantiator instantiator) {
      if (instantiator != null && instantiator.hasInterceptorSupport()) {
         InterceptionUtils.executePredestroy(instance);
      } else {
         this.invokeMethods(this.accessiblePreDestroyMethods, instance);
      }

   }

   private void invokeMethods(List methods, Object instance) {
      Iterator var3 = methods.iterator();

      while(var3.hasNext()) {
         Method method = (Method)var3.next();

         try {
            method.invoke(instance);
         } catch (Exception var6) {
            throw BeanLogger.LOG.invocationError(method, instance, var6);
         }
      }

   }

   public boolean hasPreDestroyMethods() {
      return !this.accessiblePreDestroyMethods.isEmpty();
   }

   public boolean hasPostConstructMethods() {
      return !this.accessiblePostConstructMethods.isEmpty();
   }
}
