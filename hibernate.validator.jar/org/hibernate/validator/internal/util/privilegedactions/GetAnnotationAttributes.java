package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.util.Map;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class GetAnnotationAttributes implements PrivilegedAction {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Annotation annotation;

   public static GetAnnotationAttributes action(Annotation annotation) {
      return new GetAnnotationAttributes(annotation);
   }

   private GetAnnotationAttributes(Annotation annotation) {
      this.annotation = annotation;
   }

   public Map run() {
      Method[] declaredMethods = this.annotation.annotationType().getDeclaredMethods();
      Map attributes = CollectionHelper.newHashMap(declaredMethods.length);
      Method[] var3 = declaredMethods;
      int var4 = declaredMethods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (!m.isSynthetic()) {
            m.setAccessible(true);
            String attributeName = m.getName();

            try {
               attributes.put(m.getName(), m.invoke(this.annotation));
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var9) {
               throw LOG.getUnableToGetAnnotationAttributeException(this.annotation.getClass(), attributeName, var9);
            }
         }
      }

      return CollectionHelper.toImmutableMap(attributes);
   }
}
