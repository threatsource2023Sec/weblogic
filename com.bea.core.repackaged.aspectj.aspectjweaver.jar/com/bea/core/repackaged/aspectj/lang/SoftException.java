package com.bea.core.repackaged.aspectj.lang;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SoftException extends RuntimeException {
   private static final boolean HAVE_JAVA_14;
   Throwable inner;

   public SoftException(Throwable inner) {
      this.inner = inner;
   }

   public Throwable getWrappedThrowable() {
      return this.inner;
   }

   public Throwable getCause() {
      return this.inner;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream stream) {
      super.printStackTrace(stream);
      Throwable _inner = this.inner;
      if (!HAVE_JAVA_14 && null != _inner) {
         stream.print("Caused by: ");
         _inner.printStackTrace(stream);
      }

   }

   public void printStackTrace(PrintWriter stream) {
      super.printStackTrace(stream);
      Throwable _inner = this.inner;
      if (!HAVE_JAVA_14 && null != _inner) {
         stream.print("Caused by: ");
         _inner.printStackTrace(stream);
      }

   }

   static {
      boolean java14 = false;

      try {
         Class.forName("java.nio.Buffer");
         java14 = true;
      } catch (Throwable var2) {
      }

      HAVE_JAVA_14 = java14;
   }
}
