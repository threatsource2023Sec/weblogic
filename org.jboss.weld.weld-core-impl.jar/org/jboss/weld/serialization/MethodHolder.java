package org.jboss.weld.serialization;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.reflection.DeclaredMemberIndexer;

public class MethodHolder extends AbstractSerializableHolder implements PrivilegedAction {
   private static final long serialVersionUID = -3033089710155551280L;
   private final Class declaringClass;
   private final int index;

   public MethodHolder(Method method) {
      super(method);
      this.index = DeclaredMemberIndexer.getIndexForMethod(method);
      this.declaringClass = method.getDeclaringClass();
   }

   public static MethodHolder of(Method method) {
      return new MethodHolder(method);
   }

   public static MethodHolder of(AnnotatedMethod method) {
      return new MethodHolder(method.getJavaMember());
   }

   protected Method initialize() {
      return (Method)AccessController.doPrivileged(this);
   }

   public Method run() {
      try {
         return DeclaredMemberIndexer.getMethodForIndex(this.index, this.declaringClass);
      } catch (Exception var2) {
         throw ReflectionLogger.LOG.unableToGetMethodOnDeserialization(this.declaringClass, this.index, var2);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MethodHolder that = (MethodHolder)o;
         return Objects.equals(this.get(), that.get());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return ((Method)this.get()).hashCode();
   }
}
