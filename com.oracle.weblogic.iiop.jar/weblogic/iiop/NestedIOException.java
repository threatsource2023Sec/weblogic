package weblogic.iiop;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import weblogic.utils.NestedThrowable;
import weblogic.utils.NestedThrowable.Util;

public final class NestedIOException extends IOException implements NestedThrowable {
   static final long serialVersionUID = 7225672770828528184L;
   private Throwable nested;

   public NestedIOException() {
   }

   public NestedIOException(String msg) {
      super(msg);
   }

   public NestedIOException(Throwable nested) {
      this.nested = nested;
   }

   public NestedIOException(String msg, Throwable nested) {
      super(msg);
      this.nested = nested;
   }

   public Throwable getNestedException() {
      return this.getNested();
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

   public void superPrintStackTrace(PrintWriter po) {
      super.printStackTrace(po);
   }

   public Object writeReplace() throws ObjectStreamException {
      return this.nested;
   }

   public String toString() {
      return Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      Util.printStackTrace(this, s);
   }

   public void printWriter(PrintWriter w) {
      Util.printStackTrace(this, w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
