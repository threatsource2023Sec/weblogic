package org.jboss.weld.security;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;

public class GetDeclaredFieldAction extends AbstractReflectionAction implements PrivilegedExceptionAction {
   protected final String fieldName;

   public GetDeclaredFieldAction(Class javaClass, String fieldName) {
      super(javaClass);
      this.fieldName = fieldName;
   }

   public Field run() throws NoSuchFieldException {
      return this.javaClass.getDeclaredField(this.fieldName);
   }
}
