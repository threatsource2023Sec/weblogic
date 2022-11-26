package weblogic.common;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import weblogic.utils.NestedThrowable;
import weblogic.utils.StackTraceUtils;

/** @deprecated */
@Deprecated
public class T3Exception extends IOException implements NestedThrowable {
   private Throwable nestedException;
   private String nestedStackTrace;

   /** @deprecated */
   @Deprecated
   protected void setNestedException(Throwable nestedException) {
      this.nestedException = nestedException;
   }

   /** @deprecated */
   @Deprecated
   public T3Exception(String s) {
      this(s, (Throwable)null);
   }

   public T3Exception() {
   }

   /** @deprecated */
   @Deprecated
   public T3Exception(String s, Throwable ne) {
      super(s);
      this.nestedException = ne;
      if (ne != null) {
         this.nestedStackTrace = StackTraceUtils.throwable2StackTrace(ne);
      }

   }

   /** @deprecated */
   @Deprecated
   public Throwable getNestedException() {
      return this.nestedException;
   }

   /** @deprecated */
   @Deprecated
   public String toString() {
      return this.nestedException == null ? super.toString() : super.toString() + "\n - with nested exception:\n[" + this.nestedException.toString() + "]";
   }

   /** @deprecated */
   @Deprecated
   public void printStackTrace(PrintStream s) {
      if (this.nestedStackTrace != null) {
         s.println(this.nestedStackTrace);
         s.println("--------------- nested within: ------------------");
      }

      super.printStackTrace(s);
   }

   /** @deprecated */
   @Deprecated
   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   /** @deprecated */
   @Deprecated
   public Throwable getNested() {
      return this.nestedException;
   }

   /** @deprecated */
   @Deprecated
   public String superToString() {
      return super.toString();
   }

   /** @deprecated */
   @Deprecated
   public void superPrintStackTrace(PrintStream ps) {
      super.printStackTrace(ps);
   }

   /** @deprecated */
   @Deprecated
   public void superPrintStackTrace(PrintWriter po) {
      super.printStackTrace(po);
   }
}
