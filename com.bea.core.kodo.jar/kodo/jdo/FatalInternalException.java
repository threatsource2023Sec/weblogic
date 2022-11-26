package kodo.jdo;

import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.jdo.JDOFatalInternalException;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

class FatalInternalException extends JDOFatalInternalException implements ExceptionInfo, Serializable {
   public FatalInternalException() {
      this("", (Throwable[])null);
   }

   public FatalInternalException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public int getType() {
      return 1;
   }

   public int getSubtype() {
      return 0;
   }

   public boolean isFatal() {
      return true;
   }

   public Throwable[] getNestedThrowables() {
      Throwable[] t = this.getNestedExceptions();
      return t == null ? Exceptions.EMPTY_THROWABLES : t;
   }

   public String toString() {
      return Exceptions.toString(this);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream out) {
      super.printStackTrace(out);
      Exceptions.printNestedThrowables(this, out);
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      Exceptions.printNestedThrowables(this, out);
   }

   public Object writeReplace() throws ObjectStreamException {
      return this.newSerializableInstance(this.getMessage(), Exceptions.replaceNestedThrowables(this.getNestedExceptions()));
   }

   protected FatalInternalException newSerializableInstance(String msg, Throwable[] nested) {
      return new FatalInternalException(msg, nested);
   }
}
