package org.jboss.weld.util;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.jboss.weld.security.GetDeclaredConstructorAction;

final class SecurityActions {
   private SecurityActions() {
   }

   static Constructor getDeclaredConstructor(Class javaClass, Class... parameterTypes) throws NoSuchMethodException, PrivilegedActionException {
      return System.getSecurityManager() != null ? (Constructor)AccessController.doPrivileged(GetDeclaredConstructorAction.of(javaClass)) : javaClass.getDeclaredConstructor(parameterTypes);
   }
}
