package kodo.jdo;

import java.io.ObjectStreamException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import javax.jdo.JDOObjectNotFoundException;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

class ObjectNotFoundException extends JDOObjectNotFoundException implements ExceptionInfo, Serializable {
   private static final Localizer _loc = Localizer.forPackage(ObjectNotFoundException.class);

   public ObjectNotFoundException(Object failed) {
      super(_loc.get("not-found", Exceptions.toString(failed)).getMessage());
   }

   public ObjectNotFoundException(Collection failed, Throwable[] nested) {
      super(_loc.get("not-found-multi", Exceptions.toString(failed)).getMessage(), nested);
   }

   public ObjectNotFoundException(String msg, Object failed) {
      super(msg, failed);
   }

   public ObjectNotFoundException(String msg, Throwable[] nested) {
      super(msg, nested);
   }

   public int getType() {
      return 2;
   }

   public int getSubtype() {
      return 2;
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
      return this.getFailedObject() == null ? this.newSerializableInstance(this.getMessage(), Exceptions.replaceNestedThrowables(this.getNestedExceptions())) : this.newSerializableInstance(this.getMessage(), Exceptions.replaceFailedObject(this.getFailedObject()));
   }

   protected ObjectNotFoundException newSerializableInstance(String msg, Object failed) {
      return new ObjectNotFoundException(msg, failed);
   }

   protected ObjectNotFoundException newSerializableInstance(String msg, Throwable[] nested) {
      return new ObjectNotFoundException(msg, nested);
   }
}
