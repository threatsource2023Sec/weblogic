package weblogic.apache.org.apache.log.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StackIntrospector {
   private static CallStack c_callStack;

   private StackIntrospector() {
   }

   private static synchronized CallStack getCallStack() throws SecurityException {
      if (null == c_callStack) {
         c_callStack = new CallStack();
      }

      return c_callStack;
   }

   public static final Class getCallerClass(Class clazz) throws SecurityException {
      Class[] stack = getCallStack().get();

      for(int i = stack.length - 1; i >= 0; --i) {
         if (clazz.isAssignableFrom(stack[i])) {
            return stack[i + 1];
         }
      }

      return null;
   }

   public static final String getCallerMethod(Class clazz) {
      String className = clazz.getName();
      StringWriter sw = new StringWriter();
      Throwable throwable = new Throwable();
      throwable.printStackTrace(new PrintWriter(sw, true));
      StringBuffer buffer = sw.getBuffer();
      StringBuffer line = new StringBuffer();
      int length = buffer.length();
      boolean found = false;
      int state = 0;

      for(int i = 0; i < length; ++i) {
         char ch = buffer.charAt(i);
         switch (state) {
            case 0:
               if ('\n' == ch) {
                  state = 1;
               }
               break;
            case 1:
               if ('t' == ch) {
                  state = 2;
               }
               break;
            case 2:
               line.setLength(0);
               state = 3;
               break;
            case 3:
               if ('\n' != ch) {
                  line.append(ch);
               } else {
                  String method = line.toString();
                  boolean match = method.startsWith(className);
                  if (!found && match) {
                     found = true;
                  } else if (found && !match) {
                     return method;
                  }

                  state = 1;
               }
         }
      }

      return "";
   }

   public static final String getRecentStack(Class clazz, int entries) {
      String className = clazz.getName();
      StringWriter sw = new StringWriter();
      Throwable throwable = new Throwable();
      throwable.printStackTrace(new PrintWriter(sw, true));
      StringBuffer buffer = sw.getBuffer();
      StringBuffer line = new StringBuffer();
      StringBuffer stack = new StringBuffer();
      int length = buffer.length();
      boolean found = false;
      int state = 0;

      for(int i = 0; i < length; ++i) {
         char ch = buffer.charAt(i);
         switch (state) {
            case 0:
               if ('\n' == ch) {
                  state = 1;
               }
               break;
            case 1:
               if ('t' == ch) {
                  state = 2;
               }
               break;
            case 2:
               line.setLength(0);
               state = 3;
               break;
            case 3:
               if ('\n' != ch) {
                  line.append(ch);
               } else {
                  String method = line.toString();
                  boolean match = method.startsWith(className);
                  if (!found && match) {
                     found = true;
                  } else if (found && !match) {
                     stack.append(method);
                     --entries;
                     if (entries == 0) {
                        return stack.toString();
                     }

                     stack.append("\n");
                  }

                  state = 1;
               }
         }
      }

      return "";
   }

   private static final class CallStack extends SecurityManager {
      private CallStack() {
      }

      public Class[] get() {
         return this.getClassContext();
      }

      // $FF: synthetic method
      CallStack(Object x0) {
         this();
      }
   }
}
