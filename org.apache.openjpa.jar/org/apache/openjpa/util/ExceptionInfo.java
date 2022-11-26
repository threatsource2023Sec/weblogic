package org.apache.openjpa.util;

public interface ExceptionInfo {
   int GENERAL = 0;
   int INTERNAL = 1;
   int STORE = 2;
   int UNSUPPORTED = 3;
   int USER = 4;

   String getMessage();

   Throwable getCause();

   void printStackTrace();

   int getType();

   int getSubtype();

   boolean isFatal();

   Throwable[] getNestedThrowables();

   Object getFailedObject();
}
