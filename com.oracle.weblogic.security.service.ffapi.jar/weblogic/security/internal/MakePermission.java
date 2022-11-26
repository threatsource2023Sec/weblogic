package weblogic.security.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Permission;
import weblogic.security.SecurityLogger;
import weblogic.security.service.SecurityServiceException;

public class MakePermission {
   private static final Class[] PARAMS0 = new Class[0];
   private static final Class[] PARAMS1 = new Class[]{String.class};
   private static final Class[] PARAMS2 = new Class[]{String.class, String.class};

   public static Permission makePermission(String classname, String target, String actions) throws SecurityServiceException {
      try {
         Class clss = Class.forName(classname, false, Thread.currentThread().getContextClassLoader());
         Constructor c;
         Constructor c;
         if (target == null && actions == null) {
            try {
               c = clss.getConstructor(PARAMS0);
               return (Permission)c.newInstance();
            } catch (NoSuchMethodException var11) {
               try {
                  c = clss.getConstructor(PARAMS1);
                  return (Permission)c.newInstance(target);
               } catch (NoSuchMethodException var10) {
                  Constructor c = clss.getConstructor(PARAMS2);
                  return (Permission)c.newInstance(target, actions);
               }
            }
         } else if (target != null && actions == null) {
            try {
               c = clss.getConstructor(PARAMS1);
               return (Permission)c.newInstance(target);
            } catch (NoSuchMethodException var12) {
               c = clss.getConstructor(PARAMS2);
               return (Permission)c.newInstance(target, actions);
            }
         } else {
            c = clss.getConstructor(PARAMS2);
            return (Permission)c.newInstance(target, actions);
         }
      } catch (ClassNotFoundException var13) {
         throw new SecurityServiceException(SecurityLogger.getCantFindPermission(classname), var13);
      } catch (NoSuchMethodException var14) {
         throw new SecurityServiceException(SecurityLogger.getNoAppropriateConstructor(classname), var14);
      } catch (InstantiationException var15) {
         throw new SecurityServiceException(SecurityLogger.getCantInstantiateClass(classname), var15);
      } catch (IllegalAccessException var16) {
         throw new SecurityServiceException(SecurityLogger.getNoPermissionToInstantiate(classname), var16);
      } catch (IllegalArgumentException var17) {
         throw new SecurityServiceException(SecurityLogger.getIncorrectArgForConstructor(classname), var17);
      } catch (InvocationTargetException var18) {
         throw new SecurityServiceException(SecurityLogger.getExcInConstructor(classname), var18);
      }
   }

   public static Permission makePermissionOld(String classname, String target, String actions) throws SecurityServiceException {
      try {
         Class clss = Class.forName(classname, false, Thread.currentThread().getContextClassLoader());
         Class[] argTypeList;
         Object[] argList;
         if (actions != null) {
            argTypeList = new Class[]{String.class, String.class};
            argList = new Object[]{target, actions};
         } else if (target != null) {
            argTypeList = new Class[]{String.class};
            argList = new Object[]{target};
         } else {
            argTypeList = new Class[0];
            argList = new Object[0];
         }

         Constructor constructor = clss.getConstructor(argTypeList);
         return (Permission)constructor.newInstance(argList);
      } catch (ClassNotFoundException var7) {
         throw new SecurityServiceException(SecurityLogger.getCantFindPermission(classname), var7);
      } catch (NoSuchMethodException var8) {
         throw new SecurityServiceException(SecurityLogger.getNoAppropriateConstructor(classname), var8);
      } catch (InstantiationException var9) {
         throw new SecurityServiceException(SecurityLogger.getCantInstantiateClass(classname), var9);
      } catch (IllegalAccessException var10) {
         throw new SecurityServiceException(SecurityLogger.getNoPermissionToInstantiate(classname), var10);
      } catch (IllegalArgumentException var11) {
         throw new SecurityServiceException(SecurityLogger.getIncorrectArgForConstructor(classname), var11);
      } catch (InvocationTargetException var12) {
         throw new SecurityServiceException(SecurityLogger.getExcInConstructor(classname), var12);
      }
   }
}
