package weblogic.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.reflect.DynamicProxyUtils;

final class JPA1EntityManagerInterceptor implements InvocationHandlerInterceptor {
   private static final Set JPA_1_METHODS = new HashSet();

   public void preInvoke(Method method, Object[] args) {
      if (EntityManager.class.equals(method.getDeclaringClass()) && !JPA_1_METHODS.contains(method)) {
         throw new UnsupportedOperationException(J2EELogger.getJPAUnsupportedOperationMsg());
      }
   }

   public Object postInvoke(Method method, Object[] args, Object result) {
      if (result instanceof Query) {
         InterceptingInvocationHandlerImpl ih = new InterceptingInvocationHandlerImpl(result, new JPA1QueryInterceptor());
         Class[] ifaces = DynamicProxyUtils.getAllInterfaces(result.getClass(), Query.class);
         result = Proxy.newProxyInstance(result.getClass().getClassLoader(), ifaces, ih);
      }

      return result;
   }

   static {
      try {
         Class cls = EntityManager.class;
         JPA_1_METHODS.add(cls.getMethod("getTransaction", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("close", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("refresh", Object.class));
         JPA_1_METHODS.add(cls.getMethod("remove", Object.class));
         JPA_1_METHODS.add(cls.getMethod("merge", Object.class));
         JPA_1_METHODS.add(cls.getMethod("persist", Object.class));
         JPA_1_METHODS.add(cls.getMethod("flush", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("lock", Object.class, LockModeType.class));
         JPA_1_METHODS.add(cls.getMethod("joinTransaction", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("createNamedQuery", String.class));
         JPA_1_METHODS.add(cls.getMethod("createNativeQuery", String.class));
         JPA_1_METHODS.add(cls.getMethod("createNativeQuery", String.class, Class.class));
         JPA_1_METHODS.add(cls.getMethod("createNativeQuery", String.class, String.class));
         JPA_1_METHODS.add(cls.getMethod("createQuery", String.class));
         JPA_1_METHODS.add(cls.getMethod("clear", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("contains", Object.class));
         JPA_1_METHODS.add(cls.getMethod("find", Class.class, Object.class));
         JPA_1_METHODS.add(cls.getMethod("getDelegate", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("getFlushMode", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("getReference", Class.class, Object.class));
         JPA_1_METHODS.add(cls.getMethod("isOpen", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("setFlushMode", FlushModeType.class));
      } catch (NoSuchMethodException var1) {
         throw new AssertionError("Unable to traverse methods of JPA 1 EntityManager interface");
      }
   }
}
