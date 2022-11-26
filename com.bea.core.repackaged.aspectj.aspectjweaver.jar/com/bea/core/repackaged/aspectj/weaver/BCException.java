package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.context.CompilationAndWeavingContext;
import java.io.PrintStream;
import java.io.PrintWriter;

public class BCException extends RuntimeException {
   Throwable thrown;

   public BCException() {
   }

   public BCException(String s) {
      super(s + "\n" + CompilationAndWeavingContext.getCurrentContext());
   }

   public BCException(String s, Throwable thrown) {
      this(s);
      this.thrown = thrown;
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream s) {
      this.printStackTrace(new PrintWriter(s));
   }

   public void printStackTrace(PrintWriter s) {
      super.printStackTrace(s);
      if (null != this.thrown) {
         s.print("Caused by: ");
         s.print(this.thrown.getClass().getName());
         String message = this.thrown.getMessage();
         if (null != message) {
            s.print(": ");
            s.print(message);
         }

         s.println();
         this.thrown.printStackTrace(s);
      }

   }
}
