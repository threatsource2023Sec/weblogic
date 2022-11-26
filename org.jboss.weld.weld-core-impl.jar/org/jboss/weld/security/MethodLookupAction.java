package org.jboss.weld.security;

import java.lang.reflect.Method;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;

public class MethodLookupAction extends GetDeclaredMethodAction implements PrivilegedExceptionAction {
   public MethodLookupAction(Class javaClass, String methodName, Class[] parameterTypes) {
      super(javaClass, methodName, parameterTypes);
   }

   public Method run() throws NoSuchMethodException {
      return lookupMethod(this.javaClass, this.methodName, this.parameterTypes);
   }

   public static Method lookupMethod(Class javaClass, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
      Class inspectedClass = javaClass;

      while(inspectedClass != null) {
         Class[] var4 = inspectedClass.getInterfaces();
         int var5 = var4.length;
         int var6 = 0;

         while(var6 < var5) {
            Class inspectedInterface = var4[var6];

            try {
               return lookupMethod(inspectedInterface, methodName, parameterTypes);
            } catch (NoSuchMethodException var10) {
               ++var6;
            }
         }

         try {
            return inspectedClass.getDeclaredMethod(methodName, parameterTypes);
         } catch (NoSuchMethodException var9) {
            inspectedClass = inspectedClass.getSuperclass();
         }
      }

      throw new NoSuchMethodException(javaClass + ", method: " + methodName + ", paramTypes: " + Arrays.toString(parameterTypes));
   }
}
