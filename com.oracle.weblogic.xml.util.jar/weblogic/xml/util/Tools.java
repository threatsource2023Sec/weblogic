package weblogic.xml.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Tools {
   static Debug.DebugFacility dbg = null;

   public static Debug.DebugFacility getDebug() {
      return dbg;
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

   public static String getEntityDescriptor(String publicID, String systemID) {
      return getEntityDescriptor(publicID, systemID, (String)null);
   }

   public static String getEntityDescriptor(String publicID, String systemID, String root) {
      String descr = "Public ID = \"" + publicID + "\", System ID = \"" + systemID + "\"";
      if (root != null && root.length() > 0) {
         descr = descr + ", Root tag = \"" + root + "\"";
      }

      return descr;
   }

   static {
      Debug.DebugSpec debugSpec = Debug.getDebugSpec();
      debugSpec.name = "xml.utils";
      dbg = Debug.makeDebugFacility(debugSpec);
   }

   public interface ILinkableException {
      Exception getPrevious();
   }
}
