package weblogic.ejb.container;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;

public final class InternalException extends Exception {
   private static final long serialVersionUID = -190849337176816255L;
   public final Throwable detail;
   private transient boolean initCauseInvoked = false;

   public void printStackTrace(PrintStream printstream) {
      if (this.detail != null) {
         this.detail.printStackTrace(printstream);
      } else {
         super.printStackTrace(printstream);
      }

   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }

   public String toString() {
      return this.detail != null ? this.detail.toString() : super.toString();
   }

   private InternalException() {
      this.detail = null;
   }

   public InternalException(String message) {
      super(message);
      this.detail = null;
   }

   public InternalException(String message, Throwable th) {
      super(message);
      this.detail = th;
      this.initCauseInvoked = true;
   }

   public synchronized Throwable initCause(Throwable t) {
      if (this.initCauseInvoked) {
         return this;
      } else {
         this.initCauseInvoked = true;
         return super.initCause(t);
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.initCauseInvoked = true;
   }
}
