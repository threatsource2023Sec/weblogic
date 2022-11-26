package org.hibernate.validator.internal.util.annotation;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.NewProxyInstance;

public class AnnotationFactory {
   private AnnotationFactory() {
   }

   public static Annotation create(AnnotationDescriptor descriptor) {
      return (Annotation)run(NewProxyInstance.action((ClassLoader)run(GetClassLoader.fromClass(descriptor.getType())), (Class)descriptor.getType(), new AnnotationProxy(descriptor)));
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
