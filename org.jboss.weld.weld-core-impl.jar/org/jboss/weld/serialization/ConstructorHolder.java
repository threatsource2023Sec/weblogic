package org.jboss.weld.serialization;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.util.reflection.DeclaredMemberIndexer;

public class ConstructorHolder extends AbstractSerializableHolder implements PrivilegedAction {
   private static final long serialVersionUID = -6439218442811003152L;
   private final Class declaringClass;
   private final int index;

   public static ConstructorHolder of(Constructor constructor) {
      return new ConstructorHolder(constructor);
   }

   public ConstructorHolder(Constructor constructor) {
      super(constructor);
      this.declaringClass = constructor.getDeclaringClass();
      this.index = DeclaredMemberIndexer.getIndexForConstructor(constructor);
   }

   protected Constructor initialize() {
      return (Constructor)AccessController.doPrivileged(this);
   }

   public Constructor run() {
      try {
         return DeclaredMemberIndexer.getConstructorForIndex(this.index, this.declaringClass);
      } catch (Exception var2) {
         throw ReflectionLogger.LOG.unableToGetConstructorOnDeserialization(this.declaringClass, this.index, var2);
      }
   }
}
