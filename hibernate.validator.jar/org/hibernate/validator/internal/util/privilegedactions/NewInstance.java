package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class NewInstance implements PrivilegedAction {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Class clazz;
   private final String message;

   public static NewInstance action(Class clazz, String message) {
      return new NewInstance(clazz, message);
   }

   private NewInstance(Class clazz, String message) {
      this.clazz = clazz;
      this.message = message;
   }

   public Object run() {
      try {
         return this.clazz.getConstructor().newInstance();
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException var2) {
         throw LOG.getUnableToInstantiateException(this.message, this.clazz, var2);
      } catch (IllegalAccessException var3) {
         throw LOG.getUnableToInstantiateException(this.clazz, var3);
      } catch (RuntimeException var4) {
         throw LOG.getUnableToInstantiateException(this.clazz, var4);
      }
   }
}
