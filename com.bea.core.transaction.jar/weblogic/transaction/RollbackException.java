package weblogic.transaction;

import java.io.PrintStream;
import java.io.PrintWriter;
import weblogic.utils.NestedThrowable.Util;

public final class RollbackException extends javax.transaction.RollbackException implements weblogic.utils.NestedThrowable {
   private static final long serialVersionUID = -4681718495620913775L;
   Throwable nested = null;

   public RollbackException() {
   }

   public RollbackException(String s) {
      super(s);
   }

   public RollbackException(String s, Throwable t) {
      super(s);
      this.initCause(t);
      this.nested = t;
   }

   public Throwable getNested() {
      return this.nested;
   }

   public String superToString() {
      return super.toString();
   }

   public void superPrintStackTrace(PrintStream ps) {
      super.printStackTrace(ps);
   }

   public void superPrintStackTrace(PrintWriter pw) {
      super.printStackTrace(pw);
   }

   public String toString() {
      return Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      Util.printStackTrace(this, s);
   }

   public void printStackTrace(PrintWriter w) {
      Util.printStackTrace(this, w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
