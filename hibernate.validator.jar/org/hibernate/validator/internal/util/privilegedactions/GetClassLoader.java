package org.hibernate.validator.internal.util.privilegedactions;

import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;

public final class GetClassLoader implements PrivilegedAction {
   private final Class clazz;

   public static GetClassLoader fromContext() {
      return new GetClassLoader((Class)null);
   }

   public static GetClassLoader fromClass(Class clazz) {
      Contracts.assertNotNull(clazz, Messages.MESSAGES.classIsNull());
      return new GetClassLoader(clazz);
   }

   private GetClassLoader(Class clazz) {
      this.clazz = clazz;
   }

   public ClassLoader run() {
      return this.clazz != null ? this.clazz.getClassLoader() : Thread.currentThread().getContextClassLoader();
   }
}
