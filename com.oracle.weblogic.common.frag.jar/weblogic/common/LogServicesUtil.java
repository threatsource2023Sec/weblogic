package weblogic.common;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class LogServicesUtil {
   public static String encodeLogMsg(String msg) {
      if (msg == null) {
         return null;
      } else {
         StringBuffer encodedStr = new StringBuffer();

         for(int i = 0; i < msg.length(); ++i) {
            char c = msg.charAt(i);
            switch (c) {
               case '\n':
                  encodedStr.append("_");
                  break;
               case '\r':
                  encodedStr.append("_");
                  break;
               case '#':
                  encodedStr.append("*");
                  break;
               case '<':
                  encodedStr.append("{");
                  break;
               case '>':
                  encodedStr.append("}");
                  break;
               default:
                  encodedStr.append(c);
            }
         }

         return encodedStr.toString();
      }
   }

   public static Throwable encodeThrowable(Throwable t) {
      Set dejaVu = Collections.newSetFromMap(new IdentityHashMap());
      return encodeThrowable(t, dejaVu);
   }

   private static Throwable encodeThrowable(Throwable t, Set dejaVu) {
      if (t == null) {
         return null;
      } else {
         Throwable result = new Throwable(t.getClass().getName() + ": " + encodeLogMsg(t.getMessage()));
         dejaVu.add(t);
         Throwable cause = t.getCause();
         if (cause != null) {
            if (dejaVu.contains(cause)) {
               result.initCause(new Throwable("[CIRCULAR REFERENCE: " + cause.getClass().getName() + ": " + encodeLogMsg(cause.getMessage()) + "]"));
            } else {
               result.initCause(encodeThrowable(cause, dejaVu));
            }
         }

         StackTraceElement[] elements = t.getStackTrace();
         if (elements != null && elements.length > 0) {
            StackTraceElement[] encodedElements = new StackTraceElement[elements.length];

            for(int i = 0; i < elements.length; ++i) {
               String className = encodeLogMsg(elements[i].getClassName());
               String methodName = encodeLogMsg(elements[i].getMethodName());
               String fileName = encodeLogMsg(elements[i].getFileName());
               encodedElements[i] = new StackTraceElement(className, methodName, fileName, elements[i].getLineNumber());
            }

            result.setStackTrace(encodedElements);
         }

         return result;
      }
   }
}
