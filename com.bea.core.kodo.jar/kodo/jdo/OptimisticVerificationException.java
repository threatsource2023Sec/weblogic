package kodo.jdo;

import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.jdo.JDOOptimisticVerificationException;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

class OptimisticVerificationException extends JDOOptimisticVerificationException implements ExceptionInfo, Serializable {
   public OptimisticVerificationException(String msg, Object failed) {
      super(msg, failed);
   }

   public OptimisticVerificationException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public int getType() {
      return 2;
   }

   public int getSubtype() {
      return 3;
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
      return this.getFailedObject() == null ? this.newSerializableInstance(this.getMessage(), Exceptions.replaceNestedThrowables(this.getNestedExceptions())) : this.newSerializableInstance(this.getMessage(), Exceptions.replaceFailedObject(this.getFailedObject()));
   }

   protected OptimisticVerificationException newSerializableInstance(String msg, Throwable[] nested) {
      return new OptimisticVerificationException(msg, nested);
   }

   protected OptimisticVerificationException newSerializableInstance(String msg, Object failed) {
      return new OptimisticVerificationException(msg, failed);
   }
}
