package org.glassfish.grizzly.utils;

public class DebugPoint {
   private final Exception stackTrace;
   private final String threadName;

   public DebugPoint(Exception stackTrace, String threadName) {
      this.stackTrace = stackTrace;
      this.threadName = threadName;
   }

   public Exception getStackTrace() {
      return this.stackTrace;
   }

   public String getThreadName() {
      return this.threadName;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(512);
      sb.append("Point [current-thread=").append(Thread.currentThread().getName());
      sb.append(", debug-point-thread=").append(this.threadName);
      sb.append(", stackTrace=\n");
      StackTraceElement[] trace = this.stackTrace.getStackTrace();

      for(int i = 0; i < trace.length; ++i) {
         sb.append("\tat ").append(trace[i]).append('\n');
      }

      return sb.toString();
   }
}
