package org.python.modules.jffi;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.python.objectweb.asm.AnnotationVisitor;
import org.python.objectweb.asm.Type;

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

   public static String human(Class n) {
      return n.getCanonicalName();
   }

   public static String humanShort(Class n) {
      return n.getSimpleName();
   }

   public static String sig(Class retval, Class... params) {
      return sigParams(params) + ci(retval);
   }

   public static String sig(Class[] retvalParams) {
      Class[] justParams = new Class[retvalParams.length - 1];
      System.arraycopy(retvalParams, 1, justParams, 0, justParams.length);
      return sigParams(justParams) + ci(retvalParams[0]);
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

   public static String pretty(Class retval, Class... params) {
      return prettyParams(params) + human(retval);
   }

   public static String prettyParams(Class... params) {
      StringBuilder signature = new StringBuilder("(");

      for(int i = 0; i < params.length; ++i) {
         signature.append(human(params[i]));
         if (i < params.length - 1) {
            signature.append(',');
         }
      }

      signature.append(")");
      return signature.toString();
   }

   public static String prettyShortParams(Class... params) {
      StringBuilder signature = new StringBuilder("(");

      for(int i = 0; i < params.length; ++i) {
         signature.append(humanShort(params[i]));
         if (i < params.length - 1) {
            signature.append(',');
         }
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

   public static Class[] params(Class cls1, Class cls2, Class clsFill, int times) {
      Class[] classes = new Class[times + 2];
      Arrays.fill(classes, clsFill);
      classes[0] = cls1;
      classes[1] = cls2;
      return classes;
   }

   public static String getAnnotatedBindingClassName(String javaMethodName, String typeName, boolean isStatic, int required, int optional, boolean multi, boolean framed) {
      String commonClassSuffix;
      if (multi) {
         commonClassSuffix = (isStatic ? "$s$" : "$i$") + javaMethodName;
      } else {
         commonClassSuffix = (isStatic ? "$s$" : "$i$") + required + "$" + optional + "$" + javaMethodName;
      }

      return typeName + commonClassSuffix;
   }

   public static void visitAnnotationFields(AnnotationVisitor visitor, Map fields) {
      Iterator var2 = fields.entrySet().iterator();

      while(true) {
         while(var2.hasNext()) {
            Map.Entry fieldEntry = (Map.Entry)var2.next();
            Object value = fieldEntry.getValue();
            if (value.getClass().isArray()) {
               Object[] values = (Object[])((Object[])value);
               AnnotationVisitor arrayV = visitor.visitArray((String)fieldEntry.getKey());

               for(int i = 0; i < values.length; ++i) {
                  arrayV.visit((String)null, values[i]);
               }

               arrayV.visitEnd();
            } else if (value.getClass().isEnum()) {
               visitor.visitEnum((String)fieldEntry.getKey(), ci(value.getClass()), value.toString());
            } else if (value instanceof Class) {
               visitor.visit((String)fieldEntry.getKey(), Type.getType((Class)value));
            } else {
               visitor.visit((String)fieldEntry.getKey(), value);
            }
         }

         return;
      }
   }

   public static Class getBoxType(Class type) {
      if (type == Integer.TYPE) {
         return Integer.class;
      } else if (type == Byte.TYPE) {
         return Byte.class;
      } else if (type == Short.TYPE) {
         return Short.class;
      } else if (type == Character.TYPE) {
         return Character.class;
      } else if (type == Long.TYPE) {
         return Long.class;
      } else if (type == Float.TYPE) {
         return Float.class;
      } else if (type == Double.TYPE) {
         return Double.class;
      } else if (type == Boolean.TYPE) {
         return Boolean.class;
      } else {
         throw new RuntimeException("Not a native type: " + type);
      }
   }
}
