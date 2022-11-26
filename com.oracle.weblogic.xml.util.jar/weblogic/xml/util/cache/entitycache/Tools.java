package weblogic.xml.util.cache.entitycache;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Tools {
   static boolean suppressOut = false;
   static boolean suppressLog = false;
   static boolean suppressDbg = false;

   public static void log(String s) {
      if (!suppressOut && !suppressLog) {
         System.out.println("LOG: " + s);
      }

   }

   public static void debug(String s) {
      if (!suppressOut && !suppressDbg) {
         System.out.println("DBG: " + s);
      }

   }

   public static void px(Throwable e) {
      px(e, "");
   }

   public static void px(Throwable e, String s) {
      if (e != null) {
         System.out.println(s + e);
         e.printStackTrace();
         if (e instanceof ILinkableException) {
            px(((ILinkableException)e).getPrevious(), "   This exception is linked to: ");
         }

      }
   }

   public static boolean isSubclass(Class subclass, Class superclass) {
      if (subclass.equals(superclass)) {
         return true;
      } else {
         Class subSup = subclass.getSuperclass();
         return subSup == null ? false : isSubclass(subSup, superclass);
      }
   }

   public static boolean isSubtype(Class subclass, Class superclass) {
      if (subclass.equals(superclass)) {
         return true;
      } else {
         Class subSup = subclass.getSuperclass();
         if (subSup != null && isSubtype(subSup, superclass)) {
            return true;
         } else {
            Class[] subInt = subclass.getInterfaces();

            for(int i = 0; i < subInt.length; ++i) {
               if (isSubtype(subInt[i], superclass)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static boolean isInstance(Object obj, Class supertype) {
      if (obj == null) {
         return true;
      } else {
         Class sub = obj.getClass();
         return isSubtype(sub, supertype);
      }
   }

   public static boolean isInstanceProper(Object obj, Class superclass) {
      if (obj == null) {
         return true;
      } else {
         Class sub = obj.getClass();
         return isSubclass(sub, superclass);
      }
   }

   private static void dumpClass(Class c) {
      System.out.println("  Members from " + c.toString() + ":");
      Constructor[] constructors = c.getDeclaredConstructors();
      int l = constructors.length;
      int i;
      if (l != 0) {
         System.out.println("    Constructors:");

         for(i = 0; i < l; ++i) {
            Constructor con = constructors[i];
            System.out.println("      " + con.toString());
         }
      }

      Field[] fields = c.getDeclaredFields();
      l = fields.length;
      if (l != 0) {
         System.out.println("    Fields:");

         for(i = 0; i < l; ++i) {
            Field f = fields[i];
            System.out.println("      " + f.toString());
         }
      }

      Method[] methods = c.getDeclaredMethods();
      l = methods.length;
      if (l != 0) {
         System.out.println("    Methods:");

         for(i = 0; i < l; ++i) {
            Method m = methods[i];
            System.out.println("      " + m.toString());
         }
      }

      Class sup = c.getSuperclass();
      if (sup != null) {
         dumpClassI(sup);
      }

   }

   public static void dumpClassI(Class c) {
      System.out.println("Members for " + c.toString());
      System.out.println();
   }

   public interface ILinkableException {
      Throwable getPrevious();
   }
}
