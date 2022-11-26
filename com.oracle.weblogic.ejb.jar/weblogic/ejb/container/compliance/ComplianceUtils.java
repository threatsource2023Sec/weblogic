package weblogic.ejb.container.compliance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ExceptionInfo;

final class ComplianceUtils {
   private ComplianceUtils() {
   }

   static boolean methodThrowsException(Method m, Class exceptionClass) {
      Class[] var2 = m.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (c.isAssignableFrom(exceptionClass)) {
            return true;
         }
      }

      return false;
   }

   static boolean methodThrowsException_correct(Method m, Class exceptionClass) {
      Class[] var2 = m.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (c.isAssignableFrom(exceptionClass)) {
            return true;
         }
      }

      return false;
   }

   static boolean checkApplicationException(Class exceptionClass, Class systemExceptionClass) {
      boolean ok = true;
      if (!systemExceptionClass.isAssignableFrom(exceptionClass) && !Exception.class.isAssignableFrom(exceptionClass)) {
         ok = false;
      }

      return ok;
   }

   static boolean methodThrowsExceptionAssignableFrom(Method m, Class exceptionClass) {
      Class[] var2 = m.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (exceptionClass.isAssignableFrom(c)) {
            return true;
         }
      }

      return false;
   }

   static boolean methodThrowsExactlyException(Method m, Class expnClass) {
      Class[] var2 = m.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (c.equals(expnClass)) {
            return true;
         }
      }

      return false;
   }

   static boolean localExposeThroughRemote(Method m) {
      Class returnType = m.getReturnType();
      if (!EJBLocalObject.class.isAssignableFrom(returnType) && !EJBLocalHome.class.isAssignableFrom(returnType)) {
         Class[] var2 = m.getParameterTypes();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class bmParam = var2[var4];
            if (EJBLocalObject.class.isAssignableFrom(bmParam) || EJBLocalHome.class.isAssignableFrom(bmParam)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   static boolean methodTakesNoArgs(Method m) {
      return m.getParameterTypes().length == 0;
   }

   static boolean returnTypesMatch(Method a, Method b) {
      return a.getReturnType().equals(b.getReturnType());
   }

   static void exceptionTypesMatch(Method interfaceMethod, Method implMethod) throws ExceptionTypeMismatchException {
      Class[] var2 = implMethod.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (!RuntimeException.class.isAssignableFrom(c) && !methodThrowsException(interfaceMethod, c)) {
            throw new ExceptionTypeMismatchException(implMethod, c);
         }
      }

   }

   static boolean classHasPublicNoArgCtor(Class cls) {
      Constructor[] var1 = cls.getConstructors();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Constructor c = var1[var3];
         if (c.getParameterTypes().length == 0 && Modifier.isPublic(c.getModifiers())) {
            return true;
         }
      }

      return false;
   }

   public static boolean isLegalRMIIIOPType(Class c) {
      boolean result = true;
      return result;
   }

   static boolean isApplicationException(BeanInfo beanInfo, Method m, Class c) {
      if (RemoteException.class.isAssignableFrom(c)) {
         return false;
      } else {
         if (beanInfo.isEJB30()) {
            if (Error.class.isAssignableFrom(c)) {
               return false;
            }

            if (!RuntimeException.class.isAssignableFrom(c)) {
               return true;
            }

            Map appExs = beanInfo.getDeploymentInfo().getApplicationExceptions();
            boolean needInherited = false;

            while(!c.equals(Object.class)) {
               String className = c.getName();
               if (appExs.containsKey(className)) {
                  boolean inherited = ((ExceptionInfo)appExs.get(className)).isInherited();
                  if (needInherited && (!needInherited || !inherited)) {
                     break;
                  }

                  return true;
               }

               c = c.getSuperclass();
               if (!needInherited) {
                  needInherited = true;
               }
            }
         } else {
            if (RuntimeException.class.isAssignableFrom(c)) {
               return false;
            }

            Class[] var3 = m.getExceptionTypes();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Class e = var3[var5];
               if (e.isAssignableFrom(c)) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
