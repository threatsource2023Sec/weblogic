package org.stringtemplate.v4.misc;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.ST;

public class STMessage {
   public ST self;
   public ErrorType error;
   public Object arg;
   public Object arg2;
   public Object arg3;
   public Throwable cause;

   public STMessage(ErrorType error) {
      this.error = error;
   }

   public STMessage(ErrorType error, ST self) {
      this(error);
      this.self = self;
   }

   public STMessage(ErrorType error, ST self, Throwable cause) {
      this(error, self);
      this.cause = cause;
   }

   public STMessage(ErrorType error, ST self, Throwable cause, Object arg) {
      this(error, self, cause);
      this.arg = arg;
   }

   public STMessage(ErrorType error, ST self, Throwable cause, Token where, Object arg) {
      this(error, self, cause, where);
      this.arg = arg;
   }

   public STMessage(ErrorType error, ST self, Throwable cause, Object arg, Object arg2) {
      this(error, self, cause, arg);
      this.arg2 = arg2;
   }

   public STMessage(ErrorType error, ST self, Throwable cause, Object arg, Object arg2, Object arg3) {
      this(error, self, cause, arg, arg2);
      this.arg3 = arg3;
   }

   public String toString() {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      String msg = String.format(this.error.message, this.arg, this.arg2, this.arg3);
      pw.print(msg);
      if (this.cause != null) {
         pw.print("\nCaused by: ");
         this.cause.printStackTrace(pw);
      }

      return sw.toString();
   }
}
