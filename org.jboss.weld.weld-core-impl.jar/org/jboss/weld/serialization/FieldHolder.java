package org.jboss.weld.serialization;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.jboss.weld.logging.ReflectionLogger;

public class FieldHolder extends AbstractSerializableHolder implements PrivilegedAction {
   private static final long serialVersionUID = 407021346356682729L;
   private final Class declaringClass;
   private final String fieldName;

   public FieldHolder(Field field) {
      super(field);
      this.declaringClass = field.getDeclaringClass();
      this.fieldName = field.getName();
   }

   protected Field initialize() {
      return (Field)AccessController.doPrivileged(this);
   }

   public Field run() {
      try {
         return this.declaringClass.getDeclaredField(this.fieldName);
      } catch (Exception var2) {
         throw ReflectionLogger.LOG.unableToGetFieldOnDeserialization(this.declaringClass, this.fieldName, var2);
      }
   }
}
