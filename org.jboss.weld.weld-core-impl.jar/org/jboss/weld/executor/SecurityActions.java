package org.jboss.weld.executor;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ExecutorService;

final class SecurityActions {
   private SecurityActions() {
   }

   static void shutdown(final ExecutorService executorService) {
      if (System.getSecurityManager() != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               executorService.shutdown();
               return null;
            }
         });
      } else {
         executorService.shutdown();
      }

   }

   static void shutdownNow(final ExecutorService executorService) {
      if (System.getSecurityManager() != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               executorService.shutdownNow();
               return null;
            }
         });
      } else {
         executorService.shutdownNow();
      }

   }
}
