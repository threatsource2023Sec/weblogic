package com.bea.logging;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import weblogic.utils.PlatformConstants;

public class ThrowableWrapper implements Serializable {
   private static final long serialVersionUID = -8149374172437309921L;
   private String message = null;
   private StackTraceElement[] stackTrace = null;
   private ThrowableWrapper causeInfo = null;
   private transient WeakReference throwable;

   public ThrowableWrapper(Throwable th) {
      if (th == null) {
         throw new AssertionError("Throwable cannot be null");
      } else {
         this.message = th.toString();
         this.stackTrace = th.getStackTrace();
         if (th.getCause() != null) {
            this.causeInfo = new ThrowableWrapper(th.getCause());
         }

         this.throwable = new WeakReference(th);
      }
   }

   public String getMessage() {
      return this.message;
   }

   public StackTraceElement[] getStackTrace() {
      return this.stackTrace;
   }

   public Throwable getThrowable() {
      return (Throwable)this.throwable.get();
   }

   public Throwable cloneThrowable() {
      Throwable cause = this.causeInfo == null ? null : this.causeInfo.cloneThrowable();
      Throwable th = (new Throwable(this.message)).initCause(cause);
      th.setStackTrace(this.stackTrace);
      return th;
   }

   public String toString() {
      return this.toString(-1);
   }

   public String toString(int stackDepth) {
      int originalStackDepth = stackDepth;
      StringBuilder buffer = new StringBuilder();
      synchronized(buffer) {
         buffer.append(this.message + PlatformConstants.EOL);
         if (stackDepth > this.stackTrace.length || stackDepth == -1) {
            stackDepth = this.stackTrace.length;
         }

         for(int i = 0; i < stackDepth; ++i) {
            buffer.append("\tat " + this.stackTrace[i] + PlatformConstants.EOL);
         }

         if (stackDepth < this.stackTrace.length) {
            buffer.append("\tTruncated. see log file for complete stacktrace" + PlatformConstants.EOL);
         }

         if (this.causeInfo != null) {
            buffer.append("Caused By: " + this.causeInfo.toString(originalStackDepth));
         }

         return buffer.toString();
      }
   }
}
