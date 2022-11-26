package weblogic.ejb.container.injection;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;

public class InterceptorMethodHolder {
   private final LinkedList aroundInvokes = new LinkedList();
   private final LinkedList aroundTimeouts = new LinkedList();

   public InterceptorMethodHolder(Class clazz) {
      for(Class curClass = clazz; !curClass.equals(Object.class); curClass = curClass.getSuperclass()) {
         Method[] var3 = curClass.getDeclaredMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if (!InjectionUtils.isMethodOverridden(clazz, method)) {
               if (method.getAnnotation(AroundInvoke.class) != null) {
                  this.aroundInvokes.addFirst(method);
               }

               if (method.getAnnotation(AroundTimeout.class) != null) {
                  this.aroundTimeouts.addFirst(method);
               }
            }
         }
      }

   }

   public List getAroundInvokeMethods() {
      return this.aroundInvokes;
   }

   public List getAroundTimeoutMethods() {
      return this.aroundTimeouts;
   }
}
