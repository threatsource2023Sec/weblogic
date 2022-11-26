package org.jboss.weld.injection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.security.MethodLookupAction;
import org.jboss.weld.security.SetAccessibleAction;

final class SecurityActions {
   private SecurityActions() {
   }

   static void ensureAccessible(AccessibleObject accessibleObject) {
      if (accessibleObject != null && !accessibleObject.isAccessible()) {
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(SetAccessibleAction.of(accessibleObject));
         } else {
            accessibleObject.setAccessible(true);
         }
      }

   }

   static Method lookupMethod(Class javaClass, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
      if (System.getSecurityManager() != null) {
         try {
            return (Method)AccessController.doPrivileged(new MethodLookupAction(javaClass, methodName, parameterTypes));
         } catch (PrivilegedActionException var4) {
            if (var4.getCause() instanceof NoSuchMethodException) {
               throw (NoSuchMethodException)var4.getCause();
            } else {
               throw new WeldException(var4.getCause());
            }
         }
      } else {
         return MethodLookupAction.lookupMethod(javaClass, methodName, parameterTypes);
      }
   }

   static Method getAccessibleCopyOfMethod(Method method) {
      return System.getSecurityManager() != null ? (Method)AccessController.doPrivileged(new GetAccessibleCopyOfMember(method)) : (Method)GetAccessibleCopyOfMember.of(method);
   }
}
