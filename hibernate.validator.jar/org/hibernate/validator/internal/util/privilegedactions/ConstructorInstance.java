package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class ConstructorInstance implements PrivilegedAction {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Constructor constructor;
   private final Object[] initArgs;

   public static ConstructorInstance action(Constructor constructor, Object... initArgs) {
      return new ConstructorInstance(constructor, initArgs);
   }

   private ConstructorInstance(Constructor constructor, Object... initArgs) {
      this.constructor = constructor;
      this.initArgs = initArgs;
   }

   public Object run() {
      try {
         return this.constructor.newInstance(this.initArgs);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException var2) {
         throw LOG.getUnableToInstantiateException(this.constructor.getDeclaringClass(), var2);
      }
   }
}
