package org.apache.log;

/** @deprecated */
public final class LogKit {
   /** @deprecated */
   public static ContextStack getCurrentContext() {
      return ContextStack.getCurrentContext();
   }

   /** @deprecated */
   public static Logger getLoggerFor(String category) {
      return Hierarchy.getDefaultHierarchy().getLoggerFor(category);
   }

   /** @deprecated */
   public static Priority getPriorityForName(String priority) {
      return Priority.getPriorityForName(priority);
   }

   /** @deprecated */
   public static void log(String message, Throwable t) {
      Hierarchy.getDefaultHierarchy().log(message, t);
   }

   /** @deprecated */
   public static void log(String message) {
      Hierarchy.getDefaultHierarchy().log(message);
   }

   /** @deprecated */
   public static void setDefaultLogTarget(LogTarget defaultLogTarget) {
      Hierarchy.getDefaultHierarchy().setDefaultLogTarget(defaultLogTarget);
   }

   private LogKit() {
   }
}
