package weblogic.persistence;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import weblogic.j2ee.J2EELogger;

public final class JPA1QueryInterceptor implements InvocationHandlerInterceptor {
   private static final Set JPA_1_METHODS = new HashSet();

   public void preInvoke(Method method, Object[] args) {
      if (Query.class.equals(method.getDeclaringClass()) && !JPA_1_METHODS.contains(method)) {
         throw new UnsupportedOperationException(J2EELogger.getJPAUnsupportedOperationMsg());
      }
   }

   public Object postInvoke(Method method, Object[] args, Object result) {
      return result;
   }

   static {
      try {
         Class cls = Query.class;
         JPA_1_METHODS.add(cls.getMethod("executeUpdate", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("getResultList", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("getSingleResult", (Class[])null));
         JPA_1_METHODS.add(cls.getMethod("setFirstResult", Integer.TYPE));
         JPA_1_METHODS.add(cls.getMethod("setFlushMode", FlushModeType.class));
         JPA_1_METHODS.add(cls.getMethod("setHint", String.class, Object.class));
         JPA_1_METHODS.add(cls.getMethod("setMaxResults", Integer.TYPE));
         JPA_1_METHODS.add(cls.getMethod("setParameter", Integer.TYPE, Calendar.class, TemporalType.class));
         JPA_1_METHODS.add(cls.getMethod("setParameter", Integer.TYPE, Date.class, TemporalType.class));
         JPA_1_METHODS.add(cls.getMethod("setParameter", Integer.TYPE, Object.class));
         JPA_1_METHODS.add(cls.getMethod("setParameter", String.class, Calendar.class, TemporalType.class));
         JPA_1_METHODS.add(cls.getMethod("setParameter", String.class, Date.class, TemporalType.class));
         JPA_1_METHODS.add(cls.getMethod("setParameter", String.class, Object.class));
      } catch (NoSuchMethodException var1) {
         throw new AssertionError("Unable to traverse methods of JPA 1 Query interface");
      }
   }
}
