package com.bea.core.repackaged.springframework.remoting.support;

import java.util.HashSet;
import java.util.Set;

public abstract class RemoteInvocationUtils {
   public static void fillInClientStackTraceIfPossible(Throwable ex) {
      if (ex != null) {
         StackTraceElement[] clientStack = (new Throwable()).getStackTrace();
         Set visitedExceptions = new HashSet();

         for(Throwable exToUpdate = ex; exToUpdate != null && !visitedExceptions.contains(exToUpdate); exToUpdate = exToUpdate.getCause()) {
            StackTraceElement[] serverStack = exToUpdate.getStackTrace();
            StackTraceElement[] combinedStack = new StackTraceElement[serverStack.length + clientStack.length];
            System.arraycopy(serverStack, 0, combinedStack, 0, serverStack.length);
            System.arraycopy(clientStack, 0, combinedStack, serverStack.length, clientStack.length);
            exToUpdate.setStackTrace(combinedStack);
            visitedExceptions.add(exToUpdate);
         }
      }

   }
}
