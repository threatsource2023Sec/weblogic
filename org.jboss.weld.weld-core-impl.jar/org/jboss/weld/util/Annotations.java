package org.jboss.weld.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;
import java.security.AccessController;
import org.jboss.weld.util.reflection.Reflections;

public class Annotations {
   public static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
   private static final String VALUE_MEMBER_NAME = "value";

   private Annotations() {
   }

   public static Method getRepeatableAnnotationAccessor(Class annotation) {
      Method value;
      if (System.getSecurityManager() == null) {
         value = Reflections.findDeclaredMethodByName(annotation, "value");
      } else {
         value = (Method)AccessController.doPrivileged(AccessControllers.action(() -> {
            return Reflections.findDeclaredMethodByName(annotation, "value");
         }));
      }

      if (value == null) {
         return null;
      } else if (!value.getReturnType().isArray()) {
         return null;
      } else {
         Repeatable repeatable = (Repeatable)value.getReturnType().getComponentType().getAnnotation(Repeatable.class);
         if (repeatable == null) {
            return null;
         } else {
            return !repeatable.value().equals(annotation) ? null : value;
         }
      }
   }
}
