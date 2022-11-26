package weblogic.persistence;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.reflect.DynamicProxyUtils;

public final class JPA1EntityManagerFactoryInterceptor implements InvocationHandlerInterceptor {
   private static final JPA1EntityManagerFactoryInterceptor INSTANCE = new JPA1EntityManagerFactoryInterceptor();
   private static final Set JPA_1_METHODS = new HashSet();

   private JPA1EntityManagerFactoryInterceptor() {
   }

   public static JPA1EntityManagerFactoryInterceptor getInstance() {
      return INSTANCE;
   }

   public void preInvoke(Method method, Object[] args) {
      if (EntityManagerFactory.class.equals(method.getDeclaringClass()) && !JPA_1_METHODS.contains(method)) {
         throw new UnsupportedOperationException(J2EELogger.getJPAUnsupportedOperationMsg());
      }
   }

   public Object postInvoke(Method method, Object[] args, Object result) {
      if (result instanceof EntityManager) {
         InterceptingInvocationHandlerImpl ih = new InterceptingInvocationHandlerImpl(result, new JPA1EntityManagerInterceptor());
         Class[] ifaces = DynamicProxyUtils.getAllInterfaces(result.getClass(), EntityManager.class);

         try {
            result = Proxy.newProxyInstance(result.getClass().getClassLoader(), ifaces, ih);
         } catch (IllegalArgumentException var7) {
            ifaces = new Class[]{EntityManager.class};
            result = Proxy.newProxyInstance(result.getClass().getClassLoader(), ifaces, ih);
         }
      }

      return result;
   }

   static {
      try {
         Class cls = EntityManagerFactory.class;
         JPA_1_METHODS.add(cls.getMethod("close", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("createEntityManager", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("createEntityManager", Map.class));
         JPA_1_METHODS.add(cls.getMethod("isOpen", (Class[])null));
      } catch (NoSuchMethodException var1) {
         throw new AssertionError("Unable to traverse methods of JPA 1 Query interface");
      }
   }
}
