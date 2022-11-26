package org.apache.openjpa.lib.util;

import java.lang.reflect.AnnotatedElement;
import java.security.PrivilegedAction;

public abstract class J2DoPriv5Helper extends J2DoPrivHelper {
   public static final PrivilegedAction getAnnotationsAction(final AnnotatedElement element) {
      return new PrivilegedAction() {
         public Object run() {
            return element.getAnnotations();
         }
      };
   }

   public static final PrivilegedAction getDeclaredAnnotationsAction(final AnnotatedElement element) {
      return new PrivilegedAction() {
         public Object run() {
            return element.getDeclaredAnnotations();
         }
      };
   }

   public static final PrivilegedAction isAnnotationPresentAction(final AnnotatedElement element, final Class annotationClazz) {
      return new PrivilegedAction() {
         public Object run() {
            return element.isAnnotationPresent(annotationClazz) ? Boolean.TRUE : Boolean.FALSE;
         }
      };
   }
}
