package org.jboss.weld.security;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;

public class FieldLookupAction extends GetDeclaredFieldAction implements PrivilegedExceptionAction {
   public FieldLookupAction(Class javaClass, String fieldName) {
      super(javaClass, fieldName);
   }

   public Field run() throws NoSuchFieldException {
      return lookupField(this.javaClass, this.fieldName);
   }

   public static Field lookupField(Class javaClass, String fieldName) throws NoSuchFieldException {
      Class inspectedClass = javaClass;

      while(inspectedClass != null) {
         Class[] var3 = inspectedClass.getInterfaces();
         int var4 = var3.length;
         int var5 = 0;

         while(var5 < var4) {
            Class inspectedInterface = var3[var5];

            try {
               return lookupField(inspectedInterface, fieldName);
            } catch (NoSuchFieldException var9) {
               ++var5;
            }
         }

         try {
            return inspectedClass.getDeclaredField(fieldName);
         } catch (NoSuchFieldException var8) {
            inspectedClass = inspectedClass.getSuperclass();
         }
      }

      throw new NoSuchFieldException();
   }
}
