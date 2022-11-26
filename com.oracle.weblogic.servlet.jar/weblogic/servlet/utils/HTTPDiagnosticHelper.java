package weblogic.servlet.utils;

import weblogic.servlet.internal.DebugHttpConciseLogger;

public final class HTTPDiagnosticHelper {
   public static void analyzeAndDumpStackTraceForNonWeblogicCaller(String methodName, int code) {
      if (methodName != null) {
         Exception exceptionToBeAnalyzed = new Exception("This is a diagnostic stack trace helper. Please ignore it.");
         StackTraceElement[] stackTraceArray = exceptionToBeAnalyzed.getStackTrace();
         if (stackTraceArray != null) {
            for(int i = 0; i < stackTraceArray.length; ++i) {
               StackTraceElement currentStacktraceElem = stackTraceArray[i];
               String className = currentStacktraceElem.getClassName();
               if (className != null && !className.startsWith("weblogic.") && i - 1 >= 0) {
                  StackTraceElement previousStacktraceElem = stackTraceArray[i - 1];
                  String previousElemClassName = previousStacktraceElem.getClassName();
                  String previousElemMethodName = previousStacktraceElem.getMethodName();
                  if (previousElemMethodName != null && previousElemClassName != null && previousElemMethodName.equals(methodName) && previousElemClassName.startsWith("weblogic.")) {
                     String diagnosticMessage = "Dumping stack trace for non weblogic call (" + className + ") to method " + methodName + "().";
                     if (code > -1) {
                        diagnosticMessage = diagnosticMessage + " with HTTP status " + code;
                     }

                     DebugHttpConciseLogger.debug(diagnosticMessage, exceptionToBeAnalyzed);
                     break;
                  }
               }
            }
         }

      }
   }
}
