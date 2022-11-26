package org.jboss.weld.security;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;

public class GetFieldAction extends AbstractReflectionAction implements PrivilegedExceptionAction {
   private final String fieldName;

   public GetFieldAction(Class javaClass, String fieldName) {
      super(javaClass);
      this.fieldName = fieldName;
   }

   public Field run() throws NoSuchFieldException {
      return this.javaClass.getField(this.fieldName);
   }
}
