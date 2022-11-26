package org.apache.openjpa.persistence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import org.apache.openjpa.util.ExceptionInfo;
import org.apache.openjpa.util.Exceptions;

public class RollbackException extends javax.persistence.RollbackException implements Serializable, ExceptionInfo {
   private transient Throwable[] _nested;

   public RollbackException(Exception e) {
      super(e.getMessage());
      this._nested = new Throwable[]{e};
   }

   public int getType() {
      return 2;
   }

   public int getSubtype() {
      return 0;
   }

   public boolean isFatal() {
      return true;
   }

   public Throwable getCause() {
      return PersistenceExceptions.getCause(this._nested);
   }

   public Throwable[] getNestedThrowables() {
      return this._nested;
   }

   public Object getFailedObject() {
      return null;
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
      out.writeObject(Exceptions.replaceNestedThrowables(this._nested));
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._nested = (Throwable[])((Throwable[])in.readObject());
   }
}
