package org.jboss.weld.bean.proxy;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.security.GetConstructorAction;
import org.jboss.weld.security.GetDeclaredFieldAction;
import org.jboss.weld.security.GetDeclaredFieldsAction;
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

   static Constructor getConstructor(Class javaClass, Class... parameterTypes) throws NoSuchMethodException {
      if (System.getSecurityManager() != null) {
         try {
            return (Constructor)AccessController.doPrivileged(GetConstructorAction.of(javaClass, parameterTypes));
         } catch (PrivilegedActionException var3) {
            if (var3.getCause() instanceof NoSuchMethodException) {
               throw (NoSuchMethodException)var3.getCause();
            } else {
               throw new WeldException(var3.getCause());
            }
         }
      } else {
         return javaClass.getConstructor(parameterTypes);
      }
   }

   static Field getDeclaredField(Class javaClass, String name) throws NoSuchFieldException {
      if (System.getSecurityManager() != null) {
         try {
            return (Field)AccessController.doPrivileged(new GetDeclaredFieldAction(javaClass, name));
         } catch (PrivilegedActionException var3) {
            if (var3.getCause() instanceof NoSuchFieldException) {
               throw (NoSuchFieldException)var3.getCause();
            } else {
               throw new WeldException(var3.getCause());
            }
         }
      } else {
         return javaClass.getDeclaredField(name);
      }
   }

   static boolean hasDeclaredField(Class javaClass, String name) {
      Field[] fields;
      if (System.getSecurityManager() == null) {
         fields = javaClass.getDeclaredFields();
      } else {
         fields = (Field[])AccessController.doPrivileged(new GetDeclaredFieldsAction(javaClass));
      }

      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         if (field.getName().equals(name)) {
            return true;
         }
      }

      return false;
   }
}
