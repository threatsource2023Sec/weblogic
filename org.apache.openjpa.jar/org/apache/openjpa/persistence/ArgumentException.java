package org.apache.openjpa.persistence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

public class ArgumentException extends IllegalArgumentException implements Serializable, ExceptionInfo {
   private transient boolean _fatal;
   private transient Object _failed;
   private transient Throwable[] _nested;

   public ArgumentException(String msg, Throwable[] nested, Object failed, boolean fatal) {
      super(msg);
      this._fatal = false;
      this._failed = null;
      this._nested = null;
      this._nested = nested;
      this._failed = failed;
      this._fatal = fatal;
   }

   public ArgumentException(Localizer.Message msg, Throwable[] nested, Object failed, boolean fatal) {
      this(msg.getMessage(), nested, failed, fatal);
   }

   public int getType() {
      return 4;
   }

   public int getSubtype() {
      return 0;
   }

   public boolean isFatal() {
      return this._fatal;
   }

   public Throwable getCause() {
      return PersistenceExceptions.getCause(this._nested);
   }

   public Throwable[] getNestedThrowables() {
      return this._nested == null ? Exceptions.EMPTY_THROWABLES : this._nested;
   }

   public Object getFailedObject() {
      return this._failed;
   }

   public String toString() {
      return Exceptions.toString((ExceptionInfo)this);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public void printStackTrace(PrintStream out) {
      super.printStackTrace(out);
      Exceptions.printNestedThrowables(this, (PrintStream)out);
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      Exceptions.printNestedThrowables(this, (PrintWriter)out);
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeBoolean(this._fatal);
      out.writeObject(Exceptions.replaceFailedObject(this._failed));
      out.writeObject(Exceptions.replaceNestedThrowables(this._nested));
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._fatal = in.readBoolean();
      this._failed = in.readObject();
      this._nested = (Throwable[])((Throwable[])in.readObject());
   }
}
