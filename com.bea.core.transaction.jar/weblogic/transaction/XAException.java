package weblogic.transaction;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class XAException extends javax.transaction.xa.XAException implements NestedThrowable {
   private static final long serialVersionUID = 2830485510404675300L;
   public static final int XAER_RMRETRY = 200;
   Throwable nested = null;

   public XAException() {
   }

   public XAException(String s) {
      super(s);
   }

   public XAException(String s, Throwable t) {
      super(s);
      this.nested = t;
   }

   public XAException(int errCode, String errMsg, Throwable t) {
      super(errMsg);
      this.errorCode = errCode;
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
      return NestedThrowable.Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      NestedThrowable.Util.printStackTrace(this, (PrintStream)s);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
