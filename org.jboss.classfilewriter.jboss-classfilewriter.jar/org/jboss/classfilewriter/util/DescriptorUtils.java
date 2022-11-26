package org.jboss.classfilewriter.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DescriptorUtils {
   static final String VOID_CLASS_DESCRIPTOR = "V";
   static final String BYTE_CLASS_DESCRIPTOR = "B";
   static final String CHAR_CLASS_DESCRIPTOR = "C";
   static final String DOUBLE_CLASS_DESCRIPTOR = "D";
   static final String FLOAT_CLASS_DESCRIPTOR = "F";
   static final String INT_CLASS_DESCRIPTOR = "I";
   static final String LONG_CLASS_DESCRIPTOR = "J";
   static final String SHORT_CLASS_DESCRIPTOR = "S";
   static final String BOOLEAN_CLASS_DESCRIPTOR = "Z";

   public static String makeDescriptor(String className) {
      String repl = className.replace('.', '/');
      return 'L' + repl + ';';
   }

   public static String makeDescriptor(Class c) {
      if (Void.TYPE.equals(c)) {
         return "V";
      } else if (Byte.TYPE.equals(c)) {
         return "B";
      } else if (Character.TYPE.equals(c)) {
         return "C";
      } else if (Double.TYPE.equals(c)) {
         return "D";
      } else if (Float.TYPE.equals(c)) {
         return "F";
      } else if (Integer.TYPE.equals(c)) {
         return "I";
      } else if (Long.TYPE.equals(c)) {
         return "J";
      } else if (Short.TYPE.equals(c)) {
         return "S";
      } else if (Boolean.TYPE.equals(c)) {
         return "Z";
      } else {
         return c.isArray() ? c.getName().replace('.', '/') : makeDescriptor(c.getName());
      }
   }

   public static String makeDescriptor(Constructor c) {
      StringBuilder desc = new StringBuilder("(");
      Class[] var2 = c.getParameterTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class p = var2[var4];
         desc.append(makeDescriptor(p));
      }

      desc.append(")");
      desc.append("V");
      return desc.toString();
   }

   public static String[] parameterDescriptors(String methodDescriptor) {
      int i = 1;
      List ret = new ArrayList();

      int start;
      for(int arraystart = -1; methodDescriptor.charAt(i) != ')'; ++i) {
         String type = null;
         if (methodDescriptor.charAt(i) == '[') {
            if (arraystart == -1) {
               arraystart = i;
            }
         } else {
            if (methodDescriptor.charAt(i) == 'L') {
               for(start = i++; methodDescriptor.charAt(i) != ';'; ++i) {
               }

               if (arraystart == -1) {
                  type = methodDescriptor.substring(start, i + 1);
               } else {
                  type = methodDescriptor.substring(arraystart, i + 1);
               }
            } else if (arraystart == -1) {
               type = methodDescriptor.charAt(i) + "";
            } else {
               type = methodDescriptor.substring(arraystart, i + 1);
            }

            arraystart = -1;
            ret.add(type);
         }
      }

      String[] r = new String[ret.size()];

      for(start = 0; start < ret.size(); ++start) {
         r[start] = (String)ret.get(start);
      }

      return r;
   }

   public static String[] parameterDescriptors(Method m) {
      return parameterDescriptors(m.getParameterTypes());
   }

   public static String[] parameterDescriptors(Class[] parameters) {
      String[] ret = new String[parameters.length];

      for(int i = 0; i < ret.length; ++i) {
         ret[i] = makeDescriptor(parameters[i]);
      }

      return ret;
   }

   public static String returnType(String methodDescriptor) {
      return methodDescriptor.substring(methodDescriptor.lastIndexOf(41) + 1, methodDescriptor.length());
   }

   public static boolean isPrimitive(String descriptor) {
      return descriptor.length() == 1;
   }

   public static boolean isWide(String descriptor) {
      if (!isPrimitive(descriptor)) {
         return false;
      } else {
         char c = descriptor.charAt(0);
         return c == 'D' || c == 'J';
      }
   }

   public static boolean isWide(Class cls) {
      return cls == Double.TYPE || cls == Long.TYPE;
   }

   public static String methodDescriptor(Method m) {
      StringBuilder desc = new StringBuilder("(");
      Class[] var2 = m.getParameterTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class p = var2[var4];
         desc.append(makeDescriptor(p));
      }

      desc.append(")");
      desc.append(makeDescriptor(m.getReturnType()));
      return desc.toString();
   }

   public static String methodDescriptor(String[] parameters, String returnType) {
      StringBuilder desc = new StringBuilder("(");
      String[] var3 = parameters;
      int var4 = parameters.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String p = var3[var5];
         desc.append(p);
      }

      desc.append(")");
      desc.append(returnType);
      return desc.toString();
   }

   public static String validateDescriptor(String descriptor) {
      if (descriptor.length() == 0) {
         throw new RuntimeException("descriptors may not be empty");
      } else {
         if (descriptor.length() > 1) {
            if (descriptor.startsWith("L")) {
               if (!descriptor.endsWith(";")) {
                  throw new RuntimeException(descriptor + " is not a valid descriptor");
               }
            } else if (!descriptor.startsWith("[")) {
               throw new RuntimeException(descriptor + " is not a valid descriptor");
            }
         } else {
            char type = descriptor.charAt(0);
            switch (type) {
               case 'B':
               case 'C':
               case 'D':
               case 'F':
               case 'I':
               case 'J':
               case 'S':
               case 'V':
               case 'Z':
                  break;
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'L':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  throw new RuntimeException(descriptor + " is not a valid descriptor");
            }
         }

         return descriptor;
      }
   }
}
