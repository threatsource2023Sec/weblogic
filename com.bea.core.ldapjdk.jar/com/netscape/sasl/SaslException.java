package com.netscape.sasl;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SaslException extends IOException {
   private Throwable exception;

   public SaslException() {
   }

   public SaslException(String var1) {
      super(var1);
   }

   public SaslException(String var1, Throwable var2) {
      super(var1);
      this.exception = var2;
   }

   public Throwable getException() {
      return this.exception;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream var1) {
      if (this.exception != null) {
         String var2 = super.toString();
         synchronized(var1) {
            var1.print(var2 + (var2.endsWith(".") ? "" : ".") + "  Root exception is ");
            this.exception.printStackTrace(var1);
         }
      } else {
         super.printStackTrace(var1);
      }

   }

   public void printStackTrace(PrintWriter var1) {
      if (this.exception != null) {
         String var2 = super.toString();
         synchronized(var1) {
            var1.print(var2 + (var2.endsWith(".") ? "" : ".") + "  Root exception is ");
            this.exception.printStackTrace(var1);
         }
      } else {
         super.printStackTrace(var1);
      }

   }

   public String toString() {
      String var1 = super.toString();
      if (this.exception != null && this.exception != this) {
         var1 = var1 + " [Root exception is " + this.exception.toString() + "]";
      }

      return var1;
   }
}
