package org.python.util;

import java.util.Arrays;

public class CodegenUtils {
   public static String c(String p) {
      return p.replace('/', '.');
   }

   public static String p(Class n) {
      return n.getName().replace('.', '/');
   }

   public static String ci(Class n) {
      if (n.isArray()) {
         n = n.getComponentType();
         if (n.isPrimitive()) {
            if (n == Byte.TYPE) {
               return "[B";
            } else if (n == Boolean.TYPE) {
               return "[Z";
            } else if (n == Short.TYPE) {
               return "[S";
            } else if (n == Character.TYPE) {
               return "[C";
            } else if (n == Integer.TYPE) {
               return "[I";
            } else if (n == Float.TYPE) {
               return "[F";
            } else if (n == Double.TYPE) {
               return "[D";
            } else if (n == Long.TYPE) {
               return "[J";
            } else {
               throw new RuntimeException("Unrecognized type in compiler: " + n.getName());
            }
         } else {
            return "[" + ci(n);
         }
      } else if (n.isPrimitive()) {
         if (n == Byte.TYPE) {
            return "B";
         } else if (n == Boolean.TYPE) {
            return "Z";
         } else if (n == Short.TYPE) {
            return "S";
         } else if (n == Character.TYPE) {
            return "C";
         } else if (n == Integer.TYPE) {
            return "I";
         } else if (n == Float.TYPE) {
            return "F";
         } else if (n == Double.TYPE) {
            return "D";
         } else if (n == Long.TYPE) {
            return "J";
         } else if (n == Void.TYPE) {
            return "V";
         } else {
            throw new RuntimeException("Unrecognized type in compiler: " + n.getName());
         }
      } else {
         return "L" + p(n) + ";";
      }
   }

   public static String sig(Class retval, Class... params) {
      return sigParams(params) + ci(retval);
   }

   public static String sig(Class retval, String descriptor, Class... params) {
      return sigParams(descriptor, params) + ci(retval);
   }

   public static String sigParams(Class... params) {
      StringBuilder signature = new StringBuilder("(");

      for(int i = 0; i < params.length; ++i) {
         signature.append(ci(params[i]));
      }

      signature.append(")");
      return signature.toString();
   }

   public static String sigParams(String descriptor, Class... params) {
      StringBuilder signature = new StringBuilder("(");
      signature.append(descriptor);

      for(int i = 0; i < params.length; ++i) {
         signature.append(ci(params[i]));
      }

      signature.append(")");
      return signature.toString();
   }

   public static Class[] params(Class... classes) {
      return classes;
   }

   public static Class[] params(Class cls, int times) {
      Class[] classes = new Class[times];
      Arrays.fill(classes, cls);
      return classes;
   }

   public static Class[] params(Class cls1, Class clsFill, int times) {
      Class[] classes = new Class[times + 1];
      Arrays.fill(classes, clsFill);
      classes[0] = cls1;
      return classes;
   }
}
