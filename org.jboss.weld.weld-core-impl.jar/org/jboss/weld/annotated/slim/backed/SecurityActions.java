package org.jboss.weld.annotated.slim.backed;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import org.jboss.weld.security.GetDeclaredConstructorsAction;
import org.jboss.weld.security.GetDeclaredFieldsAction;
import org.jboss.weld.security.GetDeclaredMethodsAction;

final class SecurityActions {
   private SecurityActions() {
   }

   static Method[] getDeclaredMethods(Class javaClass) {
      return System.getSecurityManager() != null ? (Method[])AccessController.doPrivileged(new GetDeclaredMethodsAction(javaClass)) : javaClass.getDeclaredMethods();
   }

   static Field[] getDeclaredFields(Class javaClass) {
      return System.getSecurityManager() != null ? (Field[])AccessController.doPrivileged(new GetDeclaredFieldsAction(javaClass)) : javaClass.getDeclaredFields();
   }

   static Constructor[] getDeclaredConstructors(Class javaClass) {
      return System.getSecurityManager() != null ? (Constructor[])AccessController.doPrivileged(new GetDeclaredConstructorsAction(javaClass)) : javaClass.getDeclaredConstructors();
   }
}
