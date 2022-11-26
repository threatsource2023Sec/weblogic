package weblogic.utils.classfile;

import java.lang.reflect.Method;

public class CodeGenHelper {
   public static String getFieldDescriptor(Class c) {
      StringBuilder buf = new StringBuilder();
      appendFieldDescriptor(c, buf);
      return buf.toString();
   }

   private static void appendFieldDescriptor(Class c, StringBuilder buf) {
      while(c.isArray()) {
         buf.append('[');
         c = c.getComponentType();
      }

      if (c.isPrimitive()) {
         if (c == Byte.TYPE) {
            buf.append('B');
         } else if (c == Character.TYPE) {
            buf.append('C');
         } else if (c == Double.TYPE) {
            buf.append('D');
         } else if (c == Float.TYPE) {
            buf.append('F');
         } else if (c == Integer.TYPE) {
            buf.append('I');
         } else if (c == Long.TYPE) {
            buf.append('J');
         } else if (c == Short.TYPE) {
            buf.append('S');
         } else if (c == Boolean.TYPE) {
            buf.append('Z');
         } else if (c == Void.TYPE) {
            buf.append('V');
         }
      } else {
         buf.append('L').append(c.getName().replace('.', '/')).append(';');
      }

   }

   public static String getMethodDescriptor(Method m) {
      return getMethodDescriptor(m.getParameterTypes(), m.getReturnType());
   }

   static String getMethodDescriptor(Class[] params, Class returnType) {
      StringBuilder buf = new StringBuilder("(");
      int i = 0;

      for(int paramsLength = params.length; i < paramsLength; ++i) {
         appendFieldDescriptor(params[i], buf);
      }

      buf.append(')');
      appendFieldDescriptor(returnType, buf);
      return buf.toString();
   }
}
