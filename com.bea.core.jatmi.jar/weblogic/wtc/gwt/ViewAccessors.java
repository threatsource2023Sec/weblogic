package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import java.lang.reflect.Method;

public class ViewAccessors {
   public static Method getAccessor(Class c, String name, Class[] params) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/ViewAccessors/getAccessor/");
      }

      Method m = null;

      try {
         m = c.getMethod("get" + name, params);
         if (traceEnabled) {
            ntrace.doTrace("/ViewAccessors.getAccessor - got method for " + name + " (10)");
         }
      } catch (NoSuchMethodException var9) {
         char[] chars = name.toCharArray();
         chars[0] = Character.toUpperCase(chars[0]);

         try {
            m = c.getMethod("get" + new String(chars), params);
            if (traceEnabled) {
               ntrace.doTrace("/ViewAccessors/getAccessor - got method for " + new String(chars) + " (20)");
            }
         } catch (NoSuchMethodException var8) {
            if (traceEnabled) {
               ntrace.doTrace("]*/ViewAccessors/getAccessor - Error: could not find getter for field " + name);
            }

            return null;
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ViewAccessors/getAccessor");
      }

      return m;
   }

   private static String convertToField(String name) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/ViewAccessors/convertToField/name=" + name);
      }

      char[] chars = name.toCharArray();
      if (!Character.isUpperCase(chars[0])) {
         if (traceEnabled) {
            ntrace.doTrace("]/ViewAccessors/convertToField - return(10) " + name);
         }

         return name;
      } else if (chars.length > 1 && Character.isUpperCase(chars[1])) {
         if (traceEnabled) {
            ntrace.doTrace("]/ViewAccessors/convertToField - return(20) " + name);
         }

         return name;
      } else {
         chars[0] = Character.toLowerCase(chars[0]);
         String ret = new String(chars);
         if (traceEnabled) {
            ntrace.doTrace("]/ViewAccessors/convertToField - return(30) " + ret);
         }

         return ret;
      }
   }

   public static String getterNameToField(String methodName) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/ViewAccessors/getterNameToField/methodName=" + methodName);
      }

      if (methodName == null) {
         if (traceEnabled) {
            ntrace.doTrace("]/ViewAccessors/getterNameToField(10)");
         }

         return null;
      } else {
         int len = methodName.length();
         if (len < 3) {
            if (traceEnabled) {
               ntrace.doTrace("]/ViewAccessors/getterNameToField(20)");
            }

            return null;
         } else if (methodName.startsWith("get")) {
            if (len < 4) {
               if (traceEnabled) {
                  ntrace.doTrace("]/ViewAccessors/getterNameToField(30)");
               }

               return null;
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("]/ViewAccessors/getterNameToField(40)");
               }

               return convertToField(methodName.substring(3));
            }
         } else if (methodName.startsWith("is")) {
            if (traceEnabled) {
               ntrace.doTrace("]/ViewAccessors.getterNameToField(50)");
            }

            return convertToField(methodName.substring(2));
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/ViewAccessors/getterNameToField(60)");
            }

            return null;
         }
      }
   }

   public static String setterNameToField(String methodName) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/ViewAccessors/setterNameToField/methodName=" + methodName);
      }

      if (methodName != null && methodName.length() >= 4) {
         if (methodName.startsWith("set")) {
            if (traceEnabled) {
               ntrace.doTrace("]/ViewAccessors/setterNameToField(20)");
            }

            return convertToField(methodName.substring(3));
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/ViewAccessors/setterNameToField(30)");
            }

            return null;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/ViewAccessors/setterNameToField(10)");
         }

         return null;
      }
   }
}
