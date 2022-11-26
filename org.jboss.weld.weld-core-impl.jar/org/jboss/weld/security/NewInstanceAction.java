package org.jboss.weld.security;

import java.security.PrivilegedExceptionAction;

public class NewInstanceAction extends AbstractGenericReflectionAction implements PrivilegedExceptionAction {
   public static NewInstanceAction of(Class javaClass) {
      return new NewInstanceAction(javaClass);
   }

   public NewInstanceAction(Class javaClass) {
      super(javaClass);
   }

   public Object run() throws InstantiationException, IllegalAccessException {
      return this.javaClass.newInstance();
   }
}
