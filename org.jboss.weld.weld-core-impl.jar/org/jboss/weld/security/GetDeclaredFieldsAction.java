package org.jboss.weld.security;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

public class GetDeclaredFieldsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetDeclaredFieldsAction(Class javaClass) {
      super(javaClass);
   }

   public Field[] run() {
      return this.javaClass.getDeclaredFields();
   }
}
