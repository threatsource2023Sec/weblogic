package kodo.jdo;

import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.jdo.JDOUserException;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

public class UserException extends JDOUserException implements ExceptionInfo, Serializable {
   public UserException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   public UserException(Localizer.Message msg, Throwable[] nested, Object failed) {
      this(msg.getMessage(), nested, failed);
   }

   public int getType() {
      return 4;
   }

   public int getSubtype() {
      return 0;
   }

   public boolean isFatal() {
      return false;
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
      return this.newSerializableInstance(this.getMessage(), Exceptions.replaceNestedThrowables(this.getNestedExceptions()), Exceptions.replaceFailedObject(this.getFailedObject()));
   }

   protected UserException newSerializableInstance(String msg, Throwable[] nested, Object failed) {
      return new UserException(msg, nested, failed);
   }
}
