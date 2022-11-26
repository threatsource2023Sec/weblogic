package org.apache.openjpa.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import org.apache.openjpa.lib.util.Localizer;

public abstract class OpenJPAException extends RuntimeException implements Serializable, ExceptionInfo {
   private transient boolean _fatal;
   private transient Object _failed;
   private transient Throwable[] _nested;

   public OpenJPAException() {
      this._fatal = false;
      this._failed = null;
      this._nested = null;
   }

   public OpenJPAException(String msg) {
      super(msg);
      this._fatal = false;
      this._failed = null;
      this._nested = null;
   }

   public OpenJPAException(Localizer.Message msg) {
      super(msg.getMessage());
      this._fatal = false;
      this._failed = null;
      this._nested = null;
   }

   public OpenJPAException(Throwable cause) {
      this(cause.getMessage(), cause);
   }

   public OpenJPAException(String msg, Throwable cause) {
      super(msg);
      this._fatal = false;
      this._failed = null;
      this._nested = null;
      this.setCause(cause);
   }

   public OpenJPAException(Localizer.Message msg, Throwable cause) {
      super(msg.getMessage());
      this._fatal = false;
      this._failed = null;
      this._nested = null;
      this.setCause(cause);
   }

   public abstract int getType();

   public int getSubtype() {
      return 0;
   }

   public boolean isFatal() {
      return this._fatal;
   }

   public OpenJPAException setFatal(boolean fatal) {
      this._fatal = fatal;
      return this;
   }

   public Throwable getCause() {
      return this._nested != null && this._nested.length != 0 ? this._nested[0] : null;
   }

   public OpenJPAException setCause(Throwable nested) {
      if (this._nested != null) {
         throw new IllegalStateException();
      } else {
         if (nested != null) {
            this._nested = new Throwable[]{nested};
         }

         return this;
      }
   }

   public Throwable[] getNestedThrowables() {
      return this._nested == null ? Exceptions.EMPTY_THROWABLES : this._nested;
   }

   public OpenJPAException setNestedThrowables(Throwable[] nested) {
      this._nested = nested;
      return this;
   }

   public Object getFailedObject() {
      return this._failed;
   }

   public OpenJPAException setFailedObject(Object failed) {
      this._failed = failed;
      return this;
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
