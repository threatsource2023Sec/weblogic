package weblogic.diagnostics.type;

import weblogic.utils.StackTraceUtils;

public class StackTraceUtility {
   public static int getMatchingFrames(Exception stackException, String packageName) {
      int matchingFrames = 0;
      StackTraceElement[] elements = stackException.getStackTrace();

      for(int i = 0; i < elements.length; ++i) {
         StackTraceElement element = elements[i];
         String className = element.getClassName();
         if (!className.startsWith(packageName)) {
            break;
         }

         ++matchingFrames;
      }

      return matchingFrames;
   }

   public static String removeFrames(Exception stackException, int framesToRemove) {
      if (framesToRemove > 0) {
         StackTraceElement[] elements = stackException.getStackTrace();
         StackTraceElement[] newElements = new StackTraceElement[elements.length - framesToRemove];
         System.arraycopy(elements, framesToRemove, newElements, 0, newElements.length);
         stackException.setStackTrace(newElements);
      }

      return StackTraceUtils.throwable2StackTrace(stackException);
   }
}
