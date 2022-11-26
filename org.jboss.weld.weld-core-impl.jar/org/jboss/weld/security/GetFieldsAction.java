package org.jboss.weld.security;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;

public class GetFieldsAction extends AbstractReflectionAction implements PrivilegedAction {
   public GetFieldsAction(Class javaClass) {
      super(javaClass);
   }

   public Field[] run() {
      return this.javaClass.getFields();
   }
}
