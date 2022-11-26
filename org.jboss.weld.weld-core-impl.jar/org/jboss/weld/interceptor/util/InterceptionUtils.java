package org.jboss.weld.interceptor.util;

import java.util.concurrent.Callable;
import org.jboss.weld.Container;
import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Environments;
import org.jboss.weld.interceptor.proxy.InterceptorException;
import org.jboss.weld.interceptor.proxy.LifecycleMixin;
import org.jboss.weld.interceptor.spi.model.InterceptionType;

public class InterceptionUtils {
   public static final String POST_CONSTRUCT = "lifecycle_mixin_$$_postConstruct";
   public static final String PRE_DESTROY = "lifecycle_mixin_$$_preDestroy";

   private InterceptionUtils() {
   }

   private static void executePostConstruct(Object proxy, Callable callback) {
      if (proxy instanceof LifecycleMixin) {
         LifecycleMixin lifecycleMixin = (LifecycleMixin)proxy;
         lifecycleMixin.lifecycle_mixin_$$_postConstruct();
      }

      if (callback != null) {
         try {
            callback.call();
         } catch (Exception var3) {
            throw new InterceptorException(var3);
         }
      }

   }

   public static void executePostConstruct(Object proxy) {
      executePostConstruct(proxy, (Callable)null);
   }

   private static void executePredestroy(Object proxy, Callable callback) {
      if (proxy instanceof LifecycleMixin) {
         LifecycleMixin lifecycleMixin = (LifecycleMixin)proxy;
         lifecycleMixin.lifecycle_mixin_$$_preDestroy();
      }

      if (callback != null) {
         try {
            callback.call();
         } catch (Exception var3) {
            throw new InterceptorException(var3);
         }
      }

   }

   public static void executePredestroy(Object proxy) {
      executePredestroy(proxy, (Callable)null);
   }

   static boolean isAnnotationClassExpected(InterceptionType interceptionType) {
      if (InterceptionType.POST_ACTIVATE.equals(interceptionType) || InterceptionType.PRE_PASSIVATE.equals(interceptionType)) {
         Environment environment = Container.getEnvironment();
         if (environment != null && (Environments.SE.equals(environment) || Environments.SERVLET.equals(environment))) {
            return false;
         }
      }

      return true;
   }
}
