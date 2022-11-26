package org.python.netty.util;

import java.util.Arrays;

/** @deprecated */
@Deprecated
public class ResourceLeakException extends RuntimeException {
   private static final long serialVersionUID = 7186453858343358280L;
   private final StackTraceElement[] cachedStackTrace = this.getStackTrace();

   public ResourceLeakException() {
   }

   public ResourceLeakException(String message) {
      super(message);
   }

   public ResourceLeakException(String message, Throwable cause) {
      super(message, cause);
   }

   public ResourceLeakException(Throwable cause) {
      super(cause);
   }

   public int hashCode() {
      StackTraceElement[] trace = this.cachedStackTrace;
      int hashCode = 0;
      StackTraceElement[] var3 = trace;
      int var4 = trace.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackTraceElement e = var3[var5];
         hashCode = hashCode * 31 + e.hashCode();
      }

      return hashCode;
   }

   public boolean equals(Object o) {
      if (!(o instanceof ResourceLeakException)) {
         return false;
      } else {
         return o == this ? true : Arrays.equals(this.cachedStackTrace, ((ResourceLeakException)o).cachedStackTrace);
      }
   }
}
