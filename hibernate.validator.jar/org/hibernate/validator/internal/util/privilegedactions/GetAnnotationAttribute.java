package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public final class GetAnnotationAttribute implements PrivilegedAction {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Annotation annotation;
   private final String attributeName;
   private final Class type;

   public static GetAnnotationAttribute action(Annotation annotation, String attributeName, Class type) {
      return new GetAnnotationAttribute(annotation, attributeName, type);
   }

   private GetAnnotationAttribute(Annotation annotation, String attributeName, Class type) {
      this.annotation = annotation;
      this.attributeName = attributeName;
      this.type = type;
   }

   public Object run() {
      try {
         Method m = this.annotation.getClass().getMethod(this.attributeName);
         m.setAccessible(true);
         Object o = m.invoke(this.annotation);
         if (this.type.isAssignableFrom(o.getClass())) {
            return o;
         } else {
            throw LOG.getWrongAnnotationAttributeTypeException(this.annotation.annotationType(), this.attributeName, this.type, o.getClass());
         }
      } catch (NoSuchMethodException var3) {
         throw LOG.getUnableToFindAnnotationAttributeException(this.annotation.annotationType(), this.attributeName, var3);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         throw LOG.getUnableToGetAnnotationAttributeException(this.annotation.annotationType(), this.attributeName, var4);
      }
   }
}
